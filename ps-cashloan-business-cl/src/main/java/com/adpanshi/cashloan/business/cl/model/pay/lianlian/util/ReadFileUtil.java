package com.adpanshi.cashloan.business.cl.model.pay.lianlian.util;

import com.adpanshi.cashloan.business.core.common.util.SftpUtilLianLian;
import com.jcraft.jsch.ChannelSftp;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/** 
 * ftp文件帮助类

 * @version 1.0
 * @date 2016年6月14日 下午5:48:43
 * Copyright 粉团网路  All Rights Reserved
 *
 *
 */
public class ReadFileUtil {
	
	private static final Logger logger = Logger.getLogger(ReadFileUtil.class);

	/**
	 * 文件后缀 - csv 格式
	 */
	public static final String FILE_SUFFIX_CSV = ".txt";

	/**
	 * 编码 - UTF-8
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 间隔符号
	 */
	public static final char SPACE_MARK = ',';

	/**
	 * 读取ftp文件流
	 * @param fileName
	 * @return
	 */
	public static List<String> getFile(String fileName){
		//连接sftp
		ChannelSftp sftp = SftpUtilLianLian.connect();
		//读取输入流
		InputStream in = SftpUtilLianLian.getFileStream(fileName, sftp);
		try {
			if (0 == in.available()) {

			}
		} catch (IOException e) {
			logger.error("读取文件为空", e);
		}
		return  read(in);
	}

	
	/**
     * 根据文件真实地址，读取csv文件 
     * @param fileName
     * @return
     * @throws IOException
     */
	public static List<String> read(String fileName) {
		// 存储数据集合
		List<String> list = new ArrayList<String>();
		InputStreamReader read = null;
		try {
			String encoding = "UTF-8";
			File file = new File(fileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int i = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if(i >0){
						list.add(lineTxt);
					}
					
					i++;
				}
			} else {
				logger.error("文件 (" + fileName + ") 不存在");
			}
		} catch (FileNotFoundException e) {
			logger.error("文件 (" + fileName + ") 不存在：", e);
		} catch (IOException e) {
			logger.error("对账文件读取异常:", e);
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				logger.error("文件流关闭异常:", e);
			}
		}
		return list;
	}
	
   
    
    /**
     * 读取压缩文件
     * 并解析压缩包中csv文件 
     * @param zipFileName
     * @return
     */
	public static List<String> readZip(String zipFileName) {
		return readZip(zipFileName, FILE_SUFFIX_CSV);
	}
    
    
	 /**
     * 读取压缩文件
     * 并解析压缩包中csv文件 
     * @param zipFileName
     * @param fileSuffix
     * @return
     */
    @SuppressWarnings("resource")
	public static List<String> readZip(String zipFileName, String fileSuffix) {
		List<String> list =  new ArrayList<String>();
    	ZipFile zf = null;
    	ZipInputStream zin = null;
		try {
			zf = new ZipFile(zipFileName);
			zin = new ZipInputStream(new FileInputStream(zipFileName));  
	        ZipEntry ze;  
	        // 遍历压缩包中的文件
	        while ((ze = zin.getNextEntry()) != null) {  
	        	// 判断是否为文件夹
	            if (ze.isDirectory()) {
	            	
	            } else { 
	            	// 只有非空文件 和 后缀为.csv的文件 才会被解析
	                long size = ze.getSize();  
	                String zeFileName = ze.getName();
	                if (size > 0 && zeFileName.endsWith(fileSuffix)) { 
	                	list.addAll(read(zf.getInputStream(ze)));
	                }  
	            }  
	        }  
		} catch (IOException e) {
			logger.error("压缩文件读取异常：", e);
		} finally {
			 try {
				 if (zin != null) {
					 zin.closeEntry();
				 }
				 if (zf != null) {
					 zf.close();
				 }
			} catch (IOException e) {
				logger.error("文件流关闭异常：", e);
			}
		}
        return list;
    }
	
    
    
    /**
     * 根据输入流解析文件内容
     * @param is
     * @return
     * @throws IOException
     */
	public static List<String> read(InputStream is) {
		// 存储数据集合
		List<String> list = new ArrayList<String>();
		try {
			InputStreamReader read = new InputStreamReader(is,ReadFileUtil.CHARSET_UTF8);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			int i = 0;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if(i >0){
					list.add(lineTxt);
				}
				i++;
			}
			read.close();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	
	public static List<String> readZip(InputStream inputStream) {
		List<String> list = new ArrayList<String>();
		try {
			ZipInputStream zis = new ZipInputStream(inputStream);
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				// 判断是否为文件夹
				if (ze.isDirectory()) {
					zis.closeEntry();
					continue;
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
				list.addAll(read(reader));
				zis.closeEntry();
			}
			inputStream.close();
			zis.close();
		} catch (Exception e) {
			logger.error("读取数据异常：", e);
		}
		return list;
	}
	
	/**
	 * 解析
	 * 
	 * @param reader
	 * @return
	 */
	public static List<String> read(BufferedReader reader) {
		// 存储数据集合
		List<String> list = new ArrayList<String>();
		try {
			String lineTxt = null;
			int i = 0;
			while ((lineTxt = reader.readLine()) != null) {
				if (i > 0) {
					list.add(lineTxt);
				}
				i++;
			}
			reader.close();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
    
}
