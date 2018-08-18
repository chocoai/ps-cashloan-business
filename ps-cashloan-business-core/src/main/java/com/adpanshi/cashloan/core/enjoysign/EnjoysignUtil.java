package com.adpanshi.cashloan.core.enjoysign;

import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.enjoysign.constants.EnjoysignConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/***
 ** @category 1号签工具类...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月25日下午8:21:28
 **/
public class EnjoysignUtil {

	static Logger logger = LoggerFactory.getLogger(EnjoysignUtil.class);

	
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
		//1号签总开关(1:开启、2.关闭)
		if("1".equals(switchExist)){
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
