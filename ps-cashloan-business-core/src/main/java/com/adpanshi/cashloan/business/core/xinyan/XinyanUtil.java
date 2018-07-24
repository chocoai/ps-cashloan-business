package com.adpanshi.cashloan.business.core.xinyan;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.SwitchEnum;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.HttpsUtil;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.common.util.ReflectUtil;
import com.adpanshi.cashloan.business.core.common.util.base64.Base64Util;
import com.adpanshi.cashloan.business.core.xinyan.beans.XinyanReqParams;
import com.adpanshi.cashloan.business.core.xinyan.constants.XinyanConstant;
import com.adpanshi.cashloan.business.core.xinyan.enums.IndustryTypeEnum;
import com.adpanshi.cashloan.business.core.xinyan.enums.XinyanConfigEnum;
import com.adpanshi.cashloan.business.core.xinyan.enums.XinyanConfigEnum.MERCHANTS_DATA;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 ** @category 新颜工具类...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日下午12:04:58
 **/
public class XinyanUtil {

	static Logger logger =LoggerFactory.getLogger(XinyanUtil.class);

	//配置大于15天则命中
	public static final int OVERDUEDAY=15;
	
	/**
	 * <p>查询用户是否是黑名单</p>
	 * @param reqParams 待请求的参数
	 * @return String 响应结果
	 * */
	public static String queryBlackByReqParams(XinyanReqParams reqParams){
		String result=null;
		if(null==reqParams){
			logger.info("-------------------------->The request parameter cannot be empty. parameter={}.",new Object[]{JSONObject.toJSONString(reqParams)});
			return result;
		}
		//@1.总开关
		if(!isSwitch()){
			logger.info("-------------------------->新颜负面拉黑-总开关未启用或不存在,跳过后续逻辑.");
			return result;
		}
		//@2.常用配置是否打开
		if(!configIsOpen()){
			logger.info("-------------------------->新颜负面拉黑-某些配置未启用或不存在,跳过后续逻辑.");
			return result;
		}
		try {
			reqParams.setMember_id(Global.getValue(XinyanConfigEnum.MERCHANTS_DATA.MERCHANTS.getCode()));
			reqParams.setTerminal_id(Global.getValue(XinyanConfigEnum.MERCHANTS_DATA.TERMINAL_NUMBER.getCode()));
			reqParams.setTrans_id(OrderNoUtil.getSerialNumber(XinyanConstant.PREFIX));
			reqParams.setTrade_date(DateUtil.dateToString(new Date(), DateUtil.yyyyMMddHHmmss));
			reqParams.setIndustry_type(IndustryTypeEnum.INTERNET_FINANCE.B19.getCode());
			Map<String,Object> params=ReflectUtil.clzToMap(reqParams);
			String paramsJSON=JSONObject.toJSONString(params);
			logger.info("-------------------------->新颜请求参数，明文:{}.",new Object[]{paramsJSON});
			String base64EncodeStr=Base64Util.base64Encode(paramsJSON);
			logger.info("-------------------------->新颜请求参数，base64 编码:{}.",new Object[]{base64EncodeStr});
			 //检查私钥文件是否存在
			String privateKeyPath=Global.getValue(XinyanConfigEnum.MERCHANTS_DATA.PRIVATE_KEY.getCode());
			
	        File privateKeyFile=new File(privateKeyPath);
	        if (!privateKeyFile.exists()){
	        	logger.info("-------------------------->商户私钥文件不存在,跳过后续业务逻辑.");
	            return result;
	        }
	        //私钥密码
	        String privateKeyPwd=Global.getValue(XinyanConfigEnum.MERCHANTS_DATA.PRIVATE_KEY_PWD.getCode());
	        //私钥加密
	        
	        String data_content= XinyanRsaCodingUtil.encryptByPriPfxFile(base64EncodeStr,privateKeyPath,privateKeyPwd);
	        //准备开始发起请求
	        String host=Global.getValue(XinyanConfigEnum.MERCHANTS_DATA.HOST.getCode());
	        String blackUrl=Global.getValue(XinyanConfigEnum.MERCHANTS_DATA.BLACK_URL.getCode());
	        String reqUrl=host+blackUrl;
	        //组装参数
	        Map<String,String> paramsMap=new HashMap<String,String>();
	        paramsMap.put("member_id",reqParams.getMember_id());
	        paramsMap.put("terminal_id",reqParams.getTerminal_id());
	        paramsMap.put("data_content",data_content);
	        paramsMap.put("data_type",Constant.JSON);
	        
	        logger.info("------------------------------>新颜请求参数:{}.",new Object[]{JSONObject.toJSONString(paramsMap)});
	        
	        result=HttpsUtil.postClient(reqUrl,paramsMap);
		} catch (Exception e) {
			logger.error("-------------",e);
		}
		return result;
	}
	
	//--------------------------------------------------->  private method  <---------------------------------------------------
	
	/**
	 * 新颜-常用配置是否启用或打开
	 * @return true:正常 、false:个别或所有配置未启用或不存在
	 * */
	protected static boolean configIsOpen(){
		Boolean flag=Boolean.TRUE;
		MERCHANTS_DATA[] merchantsDatas=XinyanConfigEnum.MERCHANTS_DATA.values();
		for(MERCHANTS_DATA merchantsData:merchantsDatas){
			String value=Global.getValue(merchantsData.getCode());
			if(StringUtils.isBlank(value)){
				logger.error("--------------------> code={}的配置,不存在或未启用。", new Object[]{merchantsData.getCode()});
				return Boolean.FALSE;
			}
		}
		return flag;
	}
	
	/**
	 * 新颜-总开关是否打开
	 * @return true:正常、false:总开关未打开或不存在
	 * */
	public static boolean isSwitch(){
		String SWITCH=Global.getValue(XinyanConstant.SWITCH_CODE);
		if(StringUtils.isBlank(SWITCH)){
			logger.error("--------------------> 新颜总开关code={}的配置,不存在或未启用。", new Object[]{XinyanConstant.SWITCH_CODE});
			return Boolean.FALSE;
		}
		return SWITCH.equals(SwitchEnum.ON.getCode());
	}
	
	/**
	 * 新颜-规则开关是否打开
	 * @return true:正常、false:总开关未打开或不存在
	 * */
	public static boolean isRuleSwitch(){
		String RULE_SWITCH=Global.getValue(XinyanConstant.RULE_SWITCH);
		if(StringUtils.isBlank(RULE_SWITCH)){
			logger.error("--------------------> 新颜规则总开关code={}的配置,不存在或未启用。", new Object[]{XinyanConstant.RULE_SWITCH});
			return Boolean.FALSE;
		}
		return RULE_SWITCH.equals(SwitchEnum.ON.getCode());
	}
}
