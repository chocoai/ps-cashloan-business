import com.adpanshi.cashloan.core.common.util.StringUtil;

/***
 ** @category OGNL扩展-提供给mybatis调用(必须是public的静态方法)
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月8日上午11:12:58
 **/
public class OGNL {
	
	/**
	 * <p>空值校验</p>
	 * <p>mybatis中调用说明:以前mybatis判断传入参数是否为null或空时都是这样判断</p>
	 * <p>< if test="userId !=null and userId !=''"/>易写错且不支持所有参数格式校验</p>
	 * <p>可以改成这样:< if test="@OGNL@isEmpty(userId)"/>即可</p>
	 * @param objects 待校验的参数
	 * @return boolean 校验结果
	 * */
	public static boolean isEmpty(Object ...objects){
		return StringUtil.isEmpty(objects);
	}
	
	/**
	 * <p>非空校验</p>
	 * <p>mybatis中调用说明:以前mybatis判断传入参数是否为null或空时都是这样判断</p>
	 * <p>< if test="userId !=null and userId !=''"/>易写错且不支持所有参数格式校验</p>
	 * <p>可以改成这样:< if test="@OGNL@isNotEmpty(userId)"/>即可</p>
	 * @param objects 待校验的参数
	 * @return boolean 校验结果
	 * */
	public static boolean isNotEmpty(Object ...objects){
		return !StringUtil.isEmpty(objects);
	}
}
