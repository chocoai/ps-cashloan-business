package com.adpanshi.cashloan.business.core.enjoysign;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.HttpUtil;
import com.adpanshi.cashloan.business.core.common.util.ReflectUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.enjoysign.beans.*;
import com.adpanshi.cashloan.business.core.enjoysign.constants.EnjoysignConstant;
import com.adpanshi.cashloan.business.core.enjoysign.enums.CaTypeEnum;
import com.adpanshi.cashloan.business.core.enjoysign.enums.EnjoysignConfigEnum;
import com.adpanshi.cashloan.business.core.enjoysign.enums.EnjoysignSwitchEnum;
import com.adpanshi.cashloan.business.core.enjoysign.enums.EnjoysignType;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/***
 ** @category 1号签工具类...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月25日下午8:21:28
 **/
public class EnjoysignUtil {
	
	/**
	 * 请求响应成功标识
	 * */
	private static final int RESPONSE_STATUS_SUCCESS=200;
	
	private static final String CHARSET = "UTF-8";
	
	/**
	 * <p>三方合同</p>
	 * @1.甲方 (借款人)
	 * @2.乙方 (出借人)
	 * @3.丙方 (居间服务人[杭州闪银网络技术有限公司-小额钱袋])
	 * 
	 * 签章顺序: 1.丙方、2.乙方、3.甲方 (这里需要注意的是、顺序必须是固定的、
	 * 甲乙丙控件的key(比如:borName代表借款人)必须是明确的、且控件名称必需要与 PartyA、PartyB、PartyC 操持一致.
	 * */
	
	static Logger logger =LoggerFactory.getLogger(EnjoysignUtil.class);
	
	//-------------------------------------------  以下是下载指定订单编号对应的合同接口----------------------------------------------------->
	
	public SysAttachment downloadToSysAttachment(String orderId,String downloadDir){
		SysAttachment attachment=null;
		/** 下载合同接口地址 */
		String API_DOWN_SIGN_URI = Global.getValue(EnjoysignConfigEnum.API_SERVER_URI.getCode()) + Global.getValue(EnjoysignConfigEnum.DOWNLOADPDF.getCode());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(!getConfigIsEnable(map, orderId)) return attachment;
			String orderInfoJson = JSONObject.toJSONString(map);
			logger.info("----------------------->download接口中输入的参数：{}.",new Object[]{orderInfoJson});
			Map<String, String> parasMap = new HashMap<String, String>();
			// 接口的第一个参数:orderInfoJson
			parasMap.put("orderInfoJson", orderInfoJson);
			// 调用接口的二个参数：账号必须是该订单对应合同的收件人之一，否则没有权限下载合同
			
			parasMap.put("account", Global.getValue(Constant.COMPANY_EMAIL));
			HttpResponse response=HttpUtil.httpPostByAttachment(API_DOWN_SIGN_URI, parasMap);
			if(null==response) return attachment;
			if (response.getStatusLine().getStatusCode() != RESPONSE_STATUS_SUCCESS) {
				logger.info("------------------>response code:"+response.getStatusLine().getStatusCode());
			}
			Header[] headers=response.getHeaders("Content-Disposition");
			if(null==headers || headers.length==0){
				return attachment;
			}
			HttpEntity entity=response.getEntity();
			//判断response实体中返回的类型
			if ("application/octet-stream".equals(entity.getContentType().getValue()))
			{
				String newPath=DateUtil.dateToString(new Date(), DateUtil.YYYYMMDD)+File.separator;
				
				downloadDir=downloadDir+newPath;  // TODO 每天产生一个日志文件(YYYYMMdd)
				
				String disposition=headers[0].getValue();
				String decodedFileName = new String(disposition.getBytes("ISO8859-1"), CHARSET);
				String realName = decodedFileName.replaceFirst("attachment;filename=", "");
				String fileName=resetRealName(realName, orderId);
				String filenameDir = downloadDir + fileName;
				InputStream is=entity.getContent();
				int size=is.available();
				if (saveFile(entity, filenameDir).getIsSuccess()) {
					logger.info("----------------------->1号签合同下载成功，文件输出目录为：{}.",new Object[]{filenameDir});
					attachment=new SysAttachment();
					attachment.setFilePath(filenameDir);
					String suffix=getFileNameSuffix(realName);
					attachment.setClassify(1);
					attachment.setOriginName(fileName);
					attachment.setRemarks("1号签合同下载");
					attachment.setSuffix(suffix);
					attachment.setAttachmentType(getFileType(suffix));
					attachment.setSize(Long.parseLong(size+""));
					return attachment;
				}
				logger.info("----------------------->合同下载失败");
			} else{
				// 如果返回来的是信息，下载失败，显示失败原因
				String resString = EntityUtils.toString(entity);
				logger.error("------------------->合同下载失败，失败原因:{}.",new Object[]{resString});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attachment;
	}
	
	
	/**
	 * 把HttpResponse实体中的数据流写到文件中
	 * @param entity
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static SignatoryResult saveFile(HttpEntity entity, String filePath) throws IOException {
		if (entity == null || filePath == null) {
			logger.error("---------------->输入参数为空,entity={},filePath={}.",new Object[]{entity,filePath});
			return new SignatoryResult(EnjoysignConstant.FAIL, "输入参数为空", null);
		}
		InputStream inStream = null;
		FileOutputStream fileout = null;
		try {
			inStream = entity.getContent();
			logger.info("----------------->下载的文件size为：{}.",new Object[]{inStream.available()});
			File file = new File(filePath);
			file.getParentFile().mkdirs();

			fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[10485760];
			int ch = 0;
			while ((ch = inStream.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("---------------->saveFile error: " + e);
			return new SignatoryResult(EnjoysignConstant.FAIL, "saveFile error: " + e, null);
		} finally {
			inStream.close();
			fileout.flush();
			fileout.close();
		}
		return new SignatoryResult(EnjoysignConstant.SUCCESS, "saveFile success", null);
	}
	
	//-------------------------------------------以下是发起合同之后自动签署接口----------------------------------------------------->
	
		/**
		 * 发起合同之后各方签署人自动调用后台签署(三方合同会按默认顺序进行签署-模版中指定)
		 * @param templateDataResult 模板控件对象
		 * @param map 提交到1号签服务器进行签名的参数集
		 * @return SignatoryResult
		 */
		public static SignatoryResult startSignWithAutoSilentSign(SignatoryResult templateDataResult,Map<String,Object> map){
			SignatoryResult result=null;
			if(StringUtil.isEmpty(templateDataResult) || !templateDataResult.getIsSuccess()) return result;
			try {
					String api_startsign_url=Global.getValue(EnjoysignConfigEnum.API_SERVER_URI.getCode())+Global.getValue(EnjoysignConfigEnum.STARTSIGN.getCode());
					/** 调用发起签署接口创建合同，并为设置为自动签署的一方完成签署 */
					Map<String, String> parasMap = new HashMap<String, String>();
					String signCommonJson = JSONObject.toJSONString(map);
					parasMap.put("signCommonJson", signCommonJson);
					System.out.println("startsign接口输入：" + signCommonJson);
					String returnJson = HttpUtil.doPost(api_startsign_url, parasMap);
					System.out.println("startsign接口返回：returnJson=" + returnJson);
					result = getServiceResult(returnJson,SignstatusResult.class); 
					if (result.getIsSuccess()) {
						logger.info("----------------->startsign接口返回成功！");
					} else {
						logger.info("----------------->startsign接口返回失败！");
					}
			} catch (Exception e) {
				logger.info("", e);
			}
			return result;
		}
		
		/**
		 * <p>组装请求参数</p>
		 * @param templateDataResult 模板控件对象
		 * @param partyData  封装好的甲乙丙三方要签名的数据值
		 * @param orderId 订单号
		 * @param map 提交到1号签服务器进行签名的参数集
		 * @return boolean 
		 * */
		public static boolean getRequiredParameter(SignatoryResult templateDataResult, PartyData partyData, Map<String,Object> map, String orderId){
			Boolean flag=Boolean.FALSE;
			if(!getConfigIsEnable(map, orderId))return flag;
			// 设置当前签署人的编号，对于startsign接口，该值总是为0
			map.put("currentSigerIndex", 0);
			/**
			 * 个人调用证书的规则是 姓名+身份证号
			 * 为企业调用证书的规则是企业名称+社会统一信用代码
			 * */
			List<JSONObject> signatories = new ArrayList<JSONObject>();
			String objStr=JSONObject.toJSONString(templateDataResult.getObj());
			List<Signatory> signatorys= JSONObject.parseArray(objStr, Signatory.class);
			for(int i=0;i<signatorys.size();i++){
				List<JSONObject> signDatalist = new ArrayList<JSONObject>();
				Signatory signatory=(Signatory)signatorys.get(i);
				List<SignData> signDatas= signatory.getSignDatas();
				JSONObject party = new JSONObject();
				party.put("sigerIndex", signatory.getSigerIndex());// 设置签署人在模板中的编号
				party.put("isAutoSign", 1);// 设置是否在startsign中完成该签署人的签署，即自动签署。
				int sigerIndex=signatory.getSigerIndex().intValue();
				party.put("authority", 0);// CA证书的发证机构,目前只支持0：CFCA。默认为0。
				if(sigerIndex==0){
					//丙方-居间服务方
					String CREDIT_CODE=Global.getValue(EnjoysignConstant.UNIFIED_SOCIAL_CREDIT_CODE); //企业调用证书的规则是企业名称+社会统一信用代码
					String companyName=Global.getValue(Constant.COMPANY_NAME);
					party.put("name", companyName);// 设置申请或使用的CA证书中的公司名称
					party.put("idCardNo", CREDIT_CODE);// 设置申请或使用的CA证书中的证件号码，-企业用的是-社会统一信用代码
					party.put("account", Global.getObject(Constant.COMPANY_EMAIL));
					party.put("phone", Global.getObject(Constant.COMPANY_LINKMAN));
					//签署人在签署时使用的CA证书类型- 企业用的是企业证书
					party.put("caType",CaTypeEnum.COMPANY_TEMPORARY_CA.getKey());
					party.put("idCardType", "8");// 设置申请或使用的CA证书中的证件类型："8"为企业营业执照
				}else if(sigerIndex==1){
					//乙方-出借人
					party.put("name", Global.getValue(Constant.BUSINESS_ENTITY));// 设置申请或使用的CA证书中的公司名称
					// 若account为邮箱，需要设置签署人的手机号码。备注：不需要签字的审阅人可以不设手机号码。
					party.put("account", Global.getObject(Constant.LENDER_LINKMAN)); 
					//签署人在签署时使用的CA证书类型- 个人用的是个人临时证书
					party.put("caType",CaTypeEnum.INDIVIDUAL_TEMPORARY_CA.getKey());
					party.put("idCardNo", Global.getValue(Constant.BUSINESS_ENTITY_IDCARD));
					party.put("idCardType", "0");// 设置申请或使用的CA证书中的证件类型："0" 居民身份证
				}else if(sigerIndex==2){
					//甲方-借款人
					party.put("account", partyData.getPartyA().getBorPhone());//设置该签署人的账号(借款方-账号默认为借款人的手机号)。  
					//签署人在签署时使用的CA证书类型- 个人用的是个人临时证书
					party.put("caType",CaTypeEnum.INDIVIDUAL_TEMPORARY_CA.getKey());
					party.put("idCardType", "0");// 设置申请或使用的CA证书中的证件类型："0" 居民身份证
				}
				//甲乙丙三方签署的控件列表(变量集)
				for(int k=0;k<signDatas.size();k++){
					SignData signData=signDatas.get(k);
					JSONObject tmpSignData = new JSONObject();
					tmpSignData.put("id", signData.getId());
					tmpSignData.put("type", signData.getType());
					if(sigerIndex==0){
						PartyC partyC= partyData.getPartyC();
						Object value=ReflectUtil.invokeGetMethod(PartyC.class, partyC, signData.getKey());
						tmpSignData.put("value", value);
					}else if(sigerIndex==1){
						PartyB partyB= partyData.getPartyB();
						Object value=ReflectUtil.invokeGetMethod(PartyB.class, partyB, signData.getKey());
						if(signData.getType().equals(EnjoysignType.IMG.getCode())){
							value="signonclick:"+value;
						}else if(signData.getType().equals(EnjoysignType.SIGNET.getCode())){
							value="signonclick:"+value;
						}
						tmpSignData.put("value", value);
					}else if(sigerIndex==2){
						PartyA partyA= partyData.getPartyA();
						party.put("name",partyA.getBorName());
						Object value=ReflectUtil.invokeGetMethod(PartyA.class, partyA, signData.getKey());
						if(signData.getType().equals(EnjoysignType.IMG.getCode())){
							 value="signonclick:"+value;
						}else if(signData.getType().equals(EnjoysignType.SIGNET.getCode())){
							 value="signonclick:"+value;
						}
						party.put("name", partyA.getBorName());// 设置申请或使用的CA证书中的公司名称
						party.put("idCardNo", partyA.getBorIDCard());
						tmpSignData.put("value", value);
					}
					signDatalist.add(tmpSignData);
				}
				party.put("signDatas", signDatalist);
				signatories.add(party);
			}
			map.put("signatories", signatories); 
			flag=Boolean.TRUE;
			return flag;
		}
	
	//-------------------------------------------以下是在线查看合同接口----------------------------------------------------->
	
	/**
	 * <p>根据给定orderId查询合同URL</p>
	 * @param orderId 订单号
	 * @return String 1号签返回的合同URL连接地址
	 * */
	public static String getContractURLByOrderId(String orderId){
		SignatoryResult result=getContractByOrderId(orderId);
		if(StringUtil.isEmpty(result)|| StringUtil.isEmpty(result.getObj()) || !result.getIsSuccess()) return null;
		return String.valueOf(result.getObj());
	}
	
	/**
	 * <p>根据给定订单号查询合同</p>
	 * @param orderId
	 * @return SignatoryResult 
	 * */
	public static SignatoryResult getContractByOrderId(String orderId){
		/** 查看合同API接口view的URL */
		String api_view_url = Global.getValue(EnjoysignConfigEnum.API_SERVER_URI.getCode()) + Global.getValue(EnjoysignConfigEnum.VIEW_URI.getCode());
		SignatoryResult result=null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(!getConfigIsEnable(map, orderId))return null;
			String orderInfoJson = JSONObject.toJSONString(map);
			// 备注：view接口将返回一个1号签合同查看页面的有效链接URL。开发人员需要在自己的程序中使用该URL，将系统页面跳转至1号签合同查看页面
			try {
				// 调用查看合同接口
				Map<String, String> parasMap = new HashMap<String, String>();
				parasMap.put("orderInfoJson", orderInfoJson);
				String email=Global.getValue(Constant.COMPANY_EMAIL);
				parasMap.put("account", email);
				logger.info("-------------->view接口输入：orderInfoJson={},account={}.",new Object[]{ orderInfoJson ,email}); 
				String returnJson = HttpUtil.doPost(api_view_url, parasMap);
				logger.info("-------------->view接口返回：returnJson={}.",new Object[]{returnJson});
				result= getServiceResult(returnJson,String.class);
				String viewSignURL = (String) result.getObj();
				logger.error("------------------------->签署人" + email + "查看该合同的页面有效链接URL为：" + viewSignURL);
			} catch (Exception e) {
				logger.error("------------------------->API接口view出现异常，详情：" ,e);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
	}

	//-------------------------------------------以下是获取模板签署人控件列表接口----------------------------------------------------->
	
	/**
	 * <p>获取默认模板的签署人签署控件列表</p>
	 * @return SignatoryResult
	 * */
	public static SignatoryResult getSignatorys() {
		return getSignatorys(Global.getValue(EnjoysignConfigEnum.TEMPLATE_ID.getCode()));
	}
	
	/**
	 * <p>根据给定 templateId 获取模板签署人签署控件列表</p>
	 * @param templateId 模板id
	 * @return SignatoryResult
	 * */
	public static SignatoryResult getSignatorys(String templateId) {
		SignatoryResult result=null;
		boolean enable=isEnableByConfigEnum();
		if(!enable) return result;
		try {
			String clientUri=Global.getValue(EnjoysignConfigEnum.API_SERVER_URI.getCode())+Global.getValue(EnjoysignConfigEnum.EXPORTWIDGETS_URI.getCode());
			String timeStamp = String.valueOf(System.currentTimeMillis());
			/**
			 * 使用appSecret为参数列表生成签权签名
			 * 注：生成签名里的timestamp一定要和API接口中传入的timestamp参数的值是相同的。
			 */
			List<String> lists = new ArrayList<String>();
			String appId=Global.getValue(EnjoysignConfigEnum.APPID.getCode());
			String version=Global.getValue(EnjoysignConfigEnum.API_VERSION.getCode());
			
			lists.add(EnjoysignConfigEnum.APPID.getName()+"="+ appId);
			lists.add(EnjoysignConfigEnum.TEMPLATE_ID.getName()+"=" +templateId);
			lists.add("timestamp=" + timeStamp);
			lists.add(EnjoysignConfigEnum.API_VERSION.getName()+"=" + version);
			String signature = ApiUtils.getSignature(lists, Global.getValue(EnjoysignConfigEnum.APP_SECRET.getCode()));
			
			logger.info("---------------> signature ={}.",new Object[]{signature});

			// 调用查看合同接口
			Map<String, String> parasMap = new HashMap<String, String>();
			parasMap.put(EnjoysignConfigEnum.APPID.getName(),appId);// 设置appId
			parasMap.put(EnjoysignConfigEnum.TEMPLATE_ID.getName(),templateId);// 设置模板编号
			parasMap.put(EnjoysignConfigEnum.API_VERSION.getName(),version);// 设置version
			parasMap.put("timestamp", timeStamp);// 设置时间戳
			parasMap.put("signature", signature);// 设置鉴权签名
			String returnJson = HttpUtil.postClient(clientUri, parasMap);
			logger.info("--------------->调用1号签exportwidgets接口返回:{}.",new Object[]{returnJson});
			result = getServiceResult(returnJson,SignData.class); 
			if (!result.getIsSuccess()) {
				logger.info("-------------->调用exportwidgets接口失败，原因为：{}",new Object[]{result.getResultMsg()});
			} 
		} catch (Exception e) {
			logger.error("",e);
		}
		return result;
	}
	
	/***
	 * <p>根据给定的json串转换</p>
	 * @param returnJson 待转换的json
	 * @param clz 类型(根据clz转换)
	 * @return SignatoryResult
	 * */
	public static SignatoryResult getServiceResult(String returnJson,Class<?> clz){
		if (returnJson == null) {
			return new SignatoryResult(EnjoysignConstant.FAIL, "getServiceResult输入参数为空",null);
		}
		try {
			JSONObject rObject = JSONObject.parseObject(returnJson);
			if (rObject != null) {
				Object resultCode = rObject.get("resultCode");
				Object resultMsg = rObject.get("resultMsg");
				Object obj = rObject.get("obj");
				if (StringUtil.isEmpty(resultCode,resultMsg)) {
					return new SignatoryResult(EnjoysignConstant.FAIL,"API接口返回结果中没有包括有效的resultCode或者resultMsg", null,clz);
				} else if (!StringUtil.isEmpty(resultCode) && resultCode.toString().equals(EnjoysignConstant.SUCCESS)) {
					return new SignatoryResult(resultCode.toString(), resultMsg.toString(), obj,clz);
				} else {
					return new SignatoryResult(resultCode.toString(), resultMsg.toString(),obj,clz);
				}
			} else {
				return new SignatoryResult(EnjoysignConstant.FAIL, "API接口返回结果不是一个合法的json字符串", null,clz);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return null;
	} 
	
	//--------------------------------------- private method ---------------------------------------------------->
	
	/***
	 * <p>1号签配置是否启用或存在，如果存在则存储到map中并进行签名</p>
	 * @param map 
	 * @param orderId 订单号
	 * @return boolean (true:启用、false:未启用或不存在)
	 * */
	private static boolean getConfigIsEnable(Map<String,Object> map,String orderId){ 
		String appId=Global.getValue(EnjoysignConfigEnum.APPID.getCode());
		String templateId=Global.getValue(EnjoysignConfigEnum.TEMPLATE_ID.getCode());
		String version=Global.getValue(EnjoysignConfigEnum.API_VERSION.getCode());
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String appSecret=Global.getValue(EnjoysignConfigEnum.APP_SECRET.getCode());
		if(StringUtil.isEmpty(appId,version,templateId,appSecret)){
			logger.error("------------------------->1号签某些配置未启用或不存在，请联系技术人员.");
			return Boolean.FALSE;
		}
		map.clear();
		map.put(EnjoysignConfigEnum.APPID.getName(),appId);// 设置appId
		map.put(EnjoysignConfigEnum.TEMPLATE_ID.getName(), templateId);
		map.put("orderId", orderId);
		map.put(EnjoysignConfigEnum.API_VERSION.getName(), version);
		map.put("timestamp", timeStamp);
		String signature = ApiUtils.getSignature(appId, templateId,orderId, timeStamp, version, appSecret);
		map.put("signature", signature);
		return Boolean.TRUE;
	}
	
	/**
	 * 判断1号签的配置是否启用
	 * @return boolean
	 * */
	static boolean isEnableByConfigEnum(){
		EnjoysignConfigEnum[] configs=EnjoysignConfigEnum.values();
		for(EnjoysignConfigEnum config:configs){
			String value=Global.getValue(config.getCode());
			if(StringUtils.isBlank(value)){
				logger.error("---------->>> code={}的配置,不存在或未启用。", new Object[]{config.getCode()});
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	/**
	 * <p>重置文件名</p>
	 * @param realName
	 * @param orderId
	 * @return String
	 * */
	private static String resetRealName(String realName,String orderId){
		String cur=getFileNameSuffix(realName);
		cur=StringUtil.isEmpty(cur)?orderId:orderId+cur;
		return cur;
	}
	
	/**
	 * <p>获取文件名后缀</p>
	 * */
	private static String getFileNameSuffix(String realName){
		if(StringUtil.isEmpty(realName)) return null;
		String cur=null;
		int point=realName.lastIndexOf(".");
		if(point!=-1){
			String suffix= realName.substring(point);
			cur=suffix;
		}
		return cur;
	}
	
	/**
	 * <p>获取文件类型</p>
	 * */
	public static String getFileType(String suffix){
		int point=suffix.lastIndexOf(".");
		if(point!=-1){
			 return suffix.substring(point+1);
		}
		return suffix;
	}
	
	/**
	 * <p>1号签总开关</p>
	 * @return Boolean true 打开、false关闭
	 * */
	public static boolean isSwithOpen(){
		String switchExist= Global.getValue(EnjoysignConstant.ENJOYSIGN_SWITCHS);
		if(!StringUtil.isNotEmptys(switchExist)){
			logger.info("----------------------->1号签开关、未启用、或不存在，跳过后续业务逻辑...");
			return Boolean.FALSE;
		}
		if(EnjoysignSwitchEnum.OPEN.getCode().equals(switchExist)){
			return Boolean.TRUE;
		}
		logger.info("----------------------->1号签开关、未打开，跳过后续业务逻辑...");
		return Boolean.FALSE;
	}

	/**
	 * 通过filePath获取文件转换为byte类型
	 * */
	public static byte[] getFileByType(String path){
		byte[] byt = null;
		if (StringUtils.isNotEmpty(path)) {
			File file = new File(path);
			if (file.isFile() && file.exists()) {
				try{
					InputStream input = new FileInputStream(file);
					byt = new byte[input.available()];
					input.read(byt);
				} catch (IOException e) {
					logger.error("------>服务器读取借款合同异常", e);
					return new byte[0];
				}
			}
		}
		return byt;
	}

	/**
	 * 将byte转化为文件保存在本地服务上
	 *
	 * @return String filePath
	 * */
	public static String saveTypeToFile(String fileName,byte[] fileByte){
		String filePath = null;
		if(fileByte	!= null){
			try{
				filePath = "/data/htdocs/pdf/"+fileName;
				File file = new File(filePath);
				if(file.exists()){
					file.delete();
				}
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(fileByte,0,fileByte.length);
				//关闭缓存流
				fos.flush();
				fos.close();
			}catch (Exception e){
				logger.error("------>保存第三方获取电子签文档异常",e);
				return null;
			}
		}
		return filePath;
	}

}
