package com.adpanshi.cashloan.business.api.aspects;

import com.adpanshi.cashloan.business.core.common.anno.Checking;
import com.adpanshi.cashloan.business.core.common.anno.CheckingExtra;
import com.adpanshi.cashloan.business.core.common.anno.ValueExtra;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.model.BindingResult;
import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月13日下午9:14:23
 **/
@Component
@Configuration  
@EnableAspectJAutoProxy  
@Aspect
public class CheckingAspects {
	
	Logger logger =LoggerFactory.getLogger(getClass());
	
	/**基本类型的包装类*/
	private static Map<Class<?>,Object> packTypes;
	
	/**统一错误提示**/
	private final String DEFAULT_MSG="格式不正确或参数不合法!";
	
	 /**字段或值校验不通过提示-字段={},值={},原因={}-log4j用*/
	 final String CHECK_UNPASS_REASON="字段={},值={} 校验不通过,原因={}!";

	/**切入点*/
	@Pointcut("@annotation(com.adpanshi.cashloan.api.core.common.anno.Validate)")
	public void executePointcut() {}
	
    /**
     * 切面方法
     * @param joinPoint
     * @return Object
     * @throws Throwable
     */
	@Around("executePointcut()")
    public Object checkIsValid(ProceedingJoinPoint joinPoint) throws Throwable {
    	Object object=null;
    	try {
    		Object[] args = handlCheckReturnArgs(joinPoint);
    		object=((ProceedingJoinPoint) joinPoint).proceed(args);
		} catch (Exception e) {
			if(StringUtil.isEmpty(object)) object=new Object[]{DEFAULT_MSG};
			logger.error(DEFAULT_MSG,e);
		}
        return object;
    }
	
	/***
	 * <p>校验</p>
	 * @param joinPoint
	 * @return boolean
	 * */
	public Object[] handlCheckReturnArgs(ProceedingJoinPoint joinPoint){
		Object[] args=joinPoint.getArgs();
		try {
			if(!(args[0] instanceof BindingResult))throw new BussinessException("请检查BindingResult是否存在或是否位于方法参数下标为0的位置，异常请忽略程序将跳过入参校验.");
			List<CheckingExtra> checkingExtras = getCheckingAnnos(joinPoint,args);
			if(StringUtil.isEmpty(checkingExtras)) return args;	
			for(CheckingExtra ck:checkingExtras){
				Checking check=ck.getChecking();
				if(StringUtil.isEmpty(check.value())){
					logger.error(CHECK_UNPASS_REASON,new Object[]{check.value(),StringUtil.isEmpty(ck.getValueExtra())?ck.getValueExtra():ck.getValueExtra().getValue()},check);
					return resetArgsByErrorArgs(args);
				}
				ValueExtra valueExtra=ck.getValueExtra();
				Object valueObj=valueExtra.getValue();
				//如果不是基本类型(包括其包装类型)或String则直接跳过校验
				if(!isPrimite(valueObj)) continue;	
				Boolean pass=check.json()?handlCheckJSON(joinPoint, check, valueExtra):handlCheck(joinPoint, check, valueExtra);
				if(!pass) return resetArgsByErrorArgs(args);
				//如果reset为true、那么需要对值进行重置
				if(valueExtra.isReset()) args[valueExtra.getPoint()]=valueExtra.getValue();
			}
		} catch (Exception e) {
			logger.error(DEFAULT_MSG,e);
		}
		return args;
	}
	
	/**
	 * <p>处理校验规则[非JSON串]</p>
	 * @param joinPoint 
	 * @param checking 校验规则器
	 * @param valueExtra 校验的对象
	 * @return boolean 校验结果(true:校验通过、false:校验失败)
	 * */
	private boolean handlCheck(ProceedingJoinPoint joinPoint,Checking checking,ValueExtra valueExtra){
		Object checkValue=valueExtra.getValue();
		Boolean flag=Boolean.TRUE;
		if(checking.required() && StringUtil.isEmpty(checkValue)){
			logger.error(CHECK_UNPASS_REASON,new Object[]{checking.value(),checkValue,checking});
			return Boolean.FALSE;
		}
		if(StringUtil.isNotEmptys(checkValue) && checking.maxSize()>0 && checking.maxSize()<checkValue.toString().length()){
			if(!checking.truncate()){
				logger.error(CHECK_UNPASS_REASON,new Object[]{checking.value(),checkValue,checking});
				return Boolean.FALSE;
			}
			String truncatValut=StringUtil.truncate(checkValue.toString(), checking.maxSize()*2, false);
			valueExtra.setValue(truncatValut);
			valueExtra.setReset(true);
			logger.info("---------------->剪切前字符串为:{},剪切后的字符串:{}.",new Object[]{checkValue.toString(),truncatValut});
		}
		return flag;
	}
	
	/**
	 * <p>处理校验规则[JSON串]</p>
	 * @param joinPoint 
	 * @param checking 校验规则器
	 * @param valueExtra 校验的对象
	 * @return boolean 校验结果(true:校验通过、false:校验失败)
	 * */
	private boolean handlCheckJSON(ProceedingJoinPoint joinPoint,Checking checking,ValueExtra valueExtra){
		Object checkValue=valueExtra.getValue();
		Boolean flag=Boolean.TRUE;
		String[] values=checkValue.toString().split(",");
		for(int i=0;i<values.length;i++){
			Object value=values[i];
			if(checking.required() && StringUtil.isEmpty(checkValue)){
				logger.error(CHECK_UNPASS_REASON,new Object[]{checking.value(),value,checking});
				return Boolean.FALSE;
			}
			if(StringUtil.isNotEmptys(value) && checking.maxSize()>0 && checking.maxSize()<value.toString().length()){
				if(!checking.truncate()){
					logger.error(CHECK_UNPASS_REASON,new Object[]{checking.value(),value,checking});
					return Boolean.FALSE;
				}
				String truncatValut=StringUtil.truncate(value.toString(), checking.maxSize()*2, false);
				valueExtra.setReset(true);
				values[i]=truncatValut;
				logger.info("---------------->剪切前字符串为:{},剪切后的字符串:{}.",new Object[]{value,truncatValut});
			}
		}
		if(valueExtra.isReset())valueExtra.setValue(StringUtil.arrayToString(values, ","));
		return flag;
	}
	
	private Object[] resetArgsByErrorArgs(Object[] args){
		args[0]=new BindingResult(Constant.PARAMETER_CHECKING_CODE, Constant.PARAMETER_CHECKING_FAIL, true);
		return args;
	}
	
	/***
	 * <p>获取指定方法所有参数注解列表</p>
	 * @param joinPoint
	 * @return List<Annotation>
	 * @throws Exception 
	 * */
	private List<CheckingExtra> getCheckingAnnos(ProceedingJoinPoint joinPoint,Object[] args) throws Exception{
		Signature signature=joinPoint.getSignature();
    	String methodName = signature.getName();
        Object target = joinPoint.getTarget();
        Method method = getMethodByClassAndName(target.getClass(), methodName);
		Annotation[][] annoss=method.getParameterAnnotations();
        if(null==annoss || annoss.length<1)return null;
        Map<String,ValueExtra> filedsWithValue=getFiledsWithValue(target,args,methodName);
        List<CheckingExtra> checkList=new ArrayList<>();
        for(Annotation[] annos:annoss){
         	for(Annotation anno:annos){
         		if(anno instanceof Checking){
         			checkList.add(new CheckingExtra((Checking)anno));
         		}
         	}
        }
        if(StringUtil.isEmpty(checkList,filedsWithValue)) return checkList;
        Iterator<String> iterator= filedsWithValue.keySet().iterator();
    	while(iterator.hasNext()){
        	String key=iterator.next();
        	ValueExtra valueExtra=filedsWithValue.get(key);
        	for(CheckingExtra check:checkList){
        		if(check.getChecking().value().equals(key)){
            		check.setValueExtra(new ValueExtra(valueExtra.getValue(), valueExtra.getPoint()));
            		break;
            	}
        	}
        }
        return checkList;
	}
	
	/**
	 * <p>根据给定字段名及字段值进行封装至Map中</p>
	 * @param target 目标对象
	 * @param args   对数列表
	 * @param methodName 方法名称
	 * @return Map
	 * */
	private Map<String,ValueExtra> getFiledsWithValue(Object target,Object[] args,String methodName){
		Map<String,ValueExtra> map=new HashMap<>();
        String className = target.getClass().getName();
        try {
            String[] paramNames = getFieldsName(className, methodName);
            map=getFiledsValue(paramNames, args);
        } catch (Exception e) {
            logger.error(DEFAULT_MSG,e);
        }
        return map;
    }
	
    /**
     * 使用javassist来获取方法参数名称
     * @param className    类名
     * @param methodName   方法名
     * @return String[]
     * @throws Exception
     */
    private String[] getFieldsName(String className, String methodName) throws Exception {
        Class<?> clazz = Class.forName(className);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);
        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if(attr == null) return null;
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i=0;i<paramsArgsName.length;i++){
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }
    
    /**
     * <p>根据给定字段名及字段值进行封装至Map中</p>
     * @param fieldNames
     * @param fieldValues
     * @return Map
     * */
    private Map<String,ValueExtra> getFiledsValue(String[] fieldNames,Object[] fieldValues){
    	Map<String,ValueExtra> map=new HashMap<>();
        if(StringUtil.isEmpty(fieldNames,fieldValues)) return null;
        for (int i=0;i<fieldNames.length;i++){
            String name = fieldNames[i];
            Object value = fieldValues[i];
            map.put(name, new ValueExtra(value, i));
        }
        return map;
    }

    /**
     * 判断是否为基本类型[包装类型也在内](包括String)
     * @return  true：是;false：不是
     */
    boolean isPrimite(Object object){
    	if(StringUtil.isEmpty(object)) return true;
    	Class<?> clazz=object.getClass();
    	boolean primite=(clazz.isPrimitive() || clazz == String.class);
    	if(!primite) return packTypes.containsKey(clazz);
    	return primite;
    }
    
    /**
     * 根据类和方法名得到方法
     */
    private Method getMethodByClassAndName(Class<?> c, String methodName)throws Exception {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) return method;
        }
        return null;
    }
    
    static {
	   	 if(null==packTypes) packTypes=new HashMap<>();
	   	 packTypes.put(Integer.class, Integer.class);
	   	 packTypes.put(Byte.class, Byte.class);
	   	 packTypes.put(Short.class, Short.class);
	   	 packTypes.put(Float.class, Float.class);
	   	 packTypes.put(Double.class, Double.class);
	   	 packTypes.put(Character.class, Character.class);
	   	 packTypes.put(Long.class, Long.class);
	   	 packTypes.put(Boolean.class, Boolean.class);
   }
    
}
