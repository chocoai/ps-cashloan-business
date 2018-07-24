package com.adpanshi.cashloan.business.core.enjoysign;

import com.adpanshi.cashloan.business.core.common.util.base64.Base64Encoder;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ApiUtils {
	
	
	public static String getSignature(Map<String, String> parasMap, String appSecret) {
		/** 生成签名 */
		List<String> lists = new ArrayList<String>();
		for(String key:parasMap.keySet()){
			lists.add(key+"=" + parasMap.get(key));
		}
		String signature = getSignature(lists, appSecret);
		System.out.println("signature = " + signature);
		return signature;
	}
	
	/**
	 * 根据传入的参数计算安全签名
	 * 
	 * @param appId
	 * @param templateId
	 * @param orderId
	 * @param timestamp
	 * @param version
	 * @return
	 */
	public static String getSignature(String appId, String templateId, String orderId, String timestamp,
			String version, String appSecret) {

		List<String> lists = new ArrayList<String>();
		if (!StringUtils.isBlank(appId)) {
			lists.add("appId=" + appId);
		}
		if (!StringUtils.isBlank(orderId)) {
			lists.add("orderId=" + orderId);
		}
		if (!StringUtils.isBlank(templateId)) {
			lists.add("templateId=" + templateId);
		}
		if (!StringUtils.isBlank(timestamp)) {
			lists.add("timestamp=" + timestamp);
		}
		if (!StringUtils.isBlank(version)) {
			lists.add("version=" + version);
		}

		String signature = ApiUtils.getSignature(lists, appSecret);
		return signature;
	}

	/**
	 * 根据参数list计算出签名
	 * @return
	 */
	public static String getSignature(List<String> lists,String appSecret){
		
		Collections.sort(lists);
		
		String p = "";
		for(String s : lists){
			if(p.equals("")){
				p = p + s;
			}else{
				p = p + "&" + s;
			}
		}
		
		p = p + "&appsecret=" + appSecret;
		
		System.out.println(" p = " + p);
		
		String signature = sha1Hex(p).toUpperCase();
		
		System.out.println(" signature = " + signature);
		
		return signature;
		
	}
	
	
	

	public static String sha1Hex(String text)
	{
		MessageDigest md = null;
		String outStr = null;
		try
		{
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(text.getBytes());
			outStr = byteToString(digest);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
		return outStr;
	}

	private static String byteToString(byte[] digest)
	{
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < digest.length; i++)
		{
			String tempStr = Integer.toHexString(digest[i] & 0xff);
			if (tempStr.length() == 1)
			{
				buf.append("0").append(tempStr);
			}
			else
			{
				buf.append(tempStr);
			}
		}
		return buf.toString().toLowerCase();
	}
	

	/**
	 * 获取指定图片的Base64编码数据
	 * 
	 * @param rootdir
	 * @param imgPath
	 * @return 图片的Base64编码数据
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readImage(String imgPath) throws FileNotFoundException, IOException {
		String str = "";
		File f = new File(imgPath);
		FileImageInputStream input = null;
		byte[] b = null;
		if (f.exists()) {
			input = new FileImageInputStream(f);
			b = new byte[(int) f.length()];
			input.read(b);
			input.close();
			Base64Encoder encoder = new Base64Encoder();
			str = encoder.encode(b);
		}
		return "data:image/png;base64," + str;
	}
}
