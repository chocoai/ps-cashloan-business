package com.adpanshi.cashloan.business.cl.util;

import com.adpanshi.cashloan.business.cl.model.StaginOrderModel;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.MathUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 ** @category 分期连连订单生成规则器(仅针对分期还款)...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月29日上午10:15:16
 **/
public class StaginOrderUtil {
	
	/*2）批量还款（主动还款）兼容单笔还款
	连连订单号为$mainOrderNo+期数范围
	例如：cl_borrow_main中记录分6期：
	需要将第3期到第5期批量还款发起的连连订单号order_no 为 $mainOrderNo+’X3N5Y’;
	(失败重试也是在Y后+1，同上)*/
	
	/**批次-标识*/
	public static final String BATCH="N";
	
	/**期数-标识*/
	public static final String STAGIN="X";
	
	/**顺序-标识*/
	public static final String ORDER="Y";
	
	/**
	 * <p>根据给定orderNos构造StaginOrder</p>
	 * @param orderNos
	 * @return 
	 * */
	public static StaginOrderModel buildStaginOrder(String ...orderNos){
		if(!(orderNos instanceof String[]))throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "不是一个标准的订单号!");
	    List<Integer> list=new ArrayList<>();
	    String first=null;
		for(String order:orderNos){
			if(order.contains(".")||order.contains(","))throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "订单号不合法!");
			String tmpOrderNo=order.substring(order.lastIndexOf(STAGIN)+1);
			if(!isNumber(tmpOrderNo))throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "订单号不合法!");
			list.add(Integer.parseInt(tmpOrderNo));
			if(null==first)first=order;
		}
		sort(list);
		boolean pass=MathUtil.isIncreseArray(list);
		if(!pass){
			throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "勾选的分期订单不是有序的!");
		}
		return new StaginOrderModel(pass, list,first.substring(0, first.lastIndexOf(STAGIN)+1));
	}
	
	/**
	 * <p>根据给定订单集合生成规则订单后缀</p>
	 * @param staginOrder 已构建好的StaginOrder
	 * @return 生成后的形如(X2N6Y)格式
	 * */
	public static String genRepayOrderNoSuffix(StaginOrderModel staginOrder){
		StringBuffer sb=new StringBuffer();
		if(staginOrder.isPass() && staginOrder.getArrays().size()>0){
			List<Integer> list=staginOrder.getArrays();
			sb.append(STAGIN).append(list.get(0)).append(BATCH).append(list.get(list.size()-1)).append(ORDER);
			return sb.toString();
		}
		throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "订单生成失败!");
	}
	
	/**
	 * <p>根据给定订单集合生成规则订单后缀</p>
	 * @param orderNos (子订单号,多个订单以逗号隔开)
	 * 		R17122818071331543187456X1,R17122818071331543187456X2,R17122818071331543187456X3
	 * 		注意:这里是针对同一订单不同分期的订单生成规则(注意各分期订单号X前缀是有规律的)
	 * @return 生成后的形如(X2N6Y)格式
	 * */
	public static String genRepayOrderNoSuffix(String ...orderNos){
		StringBuffer sb=new StringBuffer();
		StaginOrderModel staginOrder= buildStaginOrder(orderNos);
		List<Integer> list=staginOrder.getArrays();
		sb.append(STAGIN).append(list.get(0)).append(BATCH).append(list.get(list.size()-1)).append(ORDER);
		return sb.toString();
	}
	
	/**
	 * <p>根据给定订单集合重置订单后缀</p>
	 * @param orderNos (子订单号,多个订单以逗号隔开)
	 * 		R17122818071331543187456X1,R17122818071331543187456X2,R17122818071331543187456X3
	 * 		注意:这里是针对同一订单不同分期的订单生成规则(注意各分期订单号X前缀是有规律的)
	 * @return 生成后的形如(R17122818071331543187456X2N6Y)格式
	 * */
	public static String resetRepayOrderNoSuffix(String ...orderNos){
		StringBuffer sb=new StringBuffer();
		StaginOrderModel staginOrder= buildStaginOrder(orderNos);
		String prefix=staginOrder.getOrderNoPrefix();
		if(prefix.endsWith(STAGIN)){
			prefix=prefix.substring(0, prefix.lastIndexOf(STAGIN));
		}
		sb.append(prefix);
		List<Integer> list=staginOrder.getArrays();
		sb.append(STAGIN).append(list.get(0)).append(BATCH).append(list.get(list.size()-1)).append(ORDER);
		return sb.toString();
	}
	
	/**
	 * <p>根据给定订单集合生成规则订单</p>
	 * @param prefix 订单前缀(一般以)
	 * @param staginOrder 
	 * @return 生成后的形如(P17122818071331543187456X2N6Y)格式
	 * */
	public static String genRepayOrderNo(String prefix,StaginOrderModel staginOrder){
		return prefix+genRepayOrderNoSuffix(staginOrder);
	}
	
	/**
	 * <p>根据给定订单集合生成规则订单</p>
	 * @param prefix 订单前缀(一般以)
	 * @param orderNos (子订单号,多个订单以逗号隔开)
	 * 		R17122818071331543187456X1,R17122818071331543187456X2,R17122818071331543187456X3
	 * 		注意:这里是针对同一订单不同分期的订单生成规则(注意各分期订单号X前缀是有规律的)
	 * @return 生成后的形如(P17122818071331543187456X2N6Y)格式
	 * */
	public static String genRepayOrderNo(String prefix,String ...orderNos){
		return prefix+genRepayOrderNoSuffix(orderNos);
	}
	
	/**
	 * <p>根据给定订单生成序列号</p>
	 * <p>只有当target与source都一致时才会在订单号原有基础上生成序列号否则直接返回target</p>
	 * @param target 前端勾选后生成的订单号
	 * @param source 数据库查询出来的订单号(发起失败重试后的订单号)
	 * 		R17122818071331543187456X2N6Y
	 * 		注意:这里是针对批量生成后的订单号处理-每次发起连连支付失败后需要在订单号Y号面递增
	 * @return 生成后的形如(R17122818071331543187456X2N6Y1)格式
	 * */
	public static String genSequenceRepayOrderNo(String target,String source){
		if(StringUtil.isEmpty(target,source))throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "给定订单号不能为空!");
		if(!target.equals(source)) return target;
		if(target.lastIndexOf(ORDER)==-1)throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "给定的订单号格式不正确!");
		StringBuffer sb=new StringBuffer();
		int beginIndex=target.lastIndexOf(ORDER)+1;
		String number=target.substring(beginIndex);
		if(StringUtil.isBlank(number)){	//这种情况是首次支付发起失败时的处理
			sb.append(target.substring(0, beginIndex)).append(1);
			return sb.toString();
		}
		if(!isNumber(number))throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "老铁、给定的订单号格式不正确!");
		sb.append(target.substring(0, beginIndex)).append(Integer.parseInt(number)+1);
		return sb.toString();
	}
	
	public static void main(String[]args){
		String[] orderNos=new String[]{"R17122818071331543187456X6","R17122818071331543187456X5","R17122818071331543187456X4"};
//		String prefix="P2018010222195959";
//		String newOrder=genRepayOrderNo(prefix, orderNos);
//		System.out.println("newOrder:"+newOrder);
		/*StaginOrderModel staginOrder= buildStaginOrder(orderNos);
		System.out.println("StaginOrder:"+JSONObject.toJSONString(staginOrder));
		String newOrderNo=genRepayOrderNoSuffix(staginOrder);
		System.out.println("newOrderNo:"+newOrderNo);
		List<String> result=logicReckonDecreasing(staginOrder);
		System.out.println("result:"+JSONObject.toJSONString(result));*/
//					   K18031044141625407963136X1N1Y2
		String target="K18031044141625407963136X1N1Y";
		String source="K18031044141625407963136X1N1Y";
		String str=genSequenceRepayOrderNo(target, source);
		System.out.println(str);
	}
	
	/**
	 * <p>逻辑推算得到勾选分期之前的分期(比如分6期:用户勾选3、4、5期、那么得到的将是1、2期的订单号)</p>
	 * <p>注意如果返回null则表示、勾选的订单号不需要推算</p>
	 * <p>逻辑推算得到递减的订单号</p>
	 * @param staginOrder 待推算的数据
	 * @return List 推算后得到的订单号
	 * */
	public static List<String> logicReckonDecreasing(StaginOrderModel staginOrder){
		List<Integer> numbers=staginOrder.getArrays();
		if(null==numbers ||numbers.size()==0) throw new BussinessException(Constant.PARAMETER_CHECKING_CODE+"", "给定的参数不合法!");
		List<Integer> result= MathUtil.decreasing(numbers.get(0));
		if(null==result||result.size()==0)return null;
		String prefix=staginOrder.getOrderNoPrefix();
		List<String> logicOrderNos=new ArrayList<>();
		for(Integer num:result){
			logicOrderNos.add(prefix+num);
		}
		return logicOrderNos;
	}
	
	/**
	 * <p>校验str是否是1-9开头的数字</p>
	 * @param str 待校验的字符
	 * @return 
	 * */
	public static boolean isNumber(String str){
		String rex = "^[1-9][0-9]?$";//--必须是1-9开头的一位或两位数字。
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	static void sort(List<Integer> orderNos){
		if(null==orderNos || orderNos.size()==0) return;
		Collections.sort(orderNos, new Comparator<Integer>() {
			@Override
			public int compare(Integer obj1, Integer obj2) {
				return Integer.compare(obj1,obj2);
			}
		});
	}
	
}
