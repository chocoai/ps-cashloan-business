package com.adpanshi.cashloan.system.service.impl;

import com.adpanshi.cashloan.core.common.context.MessageConstant;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.model.UploadFileRes;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.FileTypeUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.system.domain.SysAttachment;
import com.adpanshi.cashloan.system.service.SysAttachmentService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-30 11:52:05
 * @history
 */
@Service(value = "sysAttachmentService")
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment,Long> implements SysAttachmentService{

	/**单个文件上传最大值5M*/
    final Long maxfileSize=5*1024*1024L;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	static List<String> imgs=Arrays.asList("bmp","jpg","jpeg","png","tiff","gif","pcx","tga","exif","fpx","svg","psd","cdr","pcd","dxf","ufo","eps","ai","raw");

	@Override
	public HttpServletResponse getImg(HttpServletRequest request, HttpServletResponse response) {
		InputStream imageIn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream output = null;
		try {
			//读取文件相对路径
			String path = request.getParameter("path");
			boolean  isFile = false;
			if(path==""||path==null){
				return response;
			}
			String url = path;
			if(!path.contains("/")){
				return response;
			}
			String[] names = path.split("/");
			String fileName = names[names.length-1];
			File file = new File(url);
			//如果图片不存在 返回Null
			if(!file.exists()){
				return response;
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			// 得到输出流
			output = response.getOutputStream();
			if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg") || url.toLowerCase().endsWith(".png")) {
				// 表明生成的响应是图片
				response.setContentType("image/jpeg");
			}else if (url.toLowerCase().endsWith(".bmp")) {
				response.setContentType("image/bmp");
			}else if(url.toLowerCase().endsWith(".pdf")){
				isFile = true;
				response.setContentType("application/pdf");
			}else if(url.toLowerCase().endsWith(".xls")){
				isFile = true;
				response.setContentType("application/vnd.ms-excel");
			}else if(url.toLowerCase().endsWith(".xlsm")){
				isFile = true;
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}else if(url.toLowerCase().endsWith(".xlsx")){
				isFile = true;
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}else if(url.toLowerCase().endsWith(".doc")){
				isFile = true;
				response.setContentType("application/msword");
			}else if(url.toLowerCase().endsWith(".docm")){
				isFile = true;
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			}else if(url.toLowerCase().endsWith(".docx")){
				isFile = true;
				response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			}else {
				return response;
			}
			//文件名称
			if(isFile){
				String agent  = request.getHeader("User-Agent").toLowerCase();
				if(agent.indexOf("MSIE") > -1 || agent.indexOf("edge") > -1 ||
						(agent.indexOf("trident") > -1 && agent.indexOf("rv") > -1)){
					//ie浏览器
					response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
				}else if (agent.indexOf("firefox") > -1){
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ new String(fileName.getBytes("utf-8"), "ISO8859-1")+"\"");
				}else {
					response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
				}
			}
			imageIn = new FileInputStream(file);
			// 输入缓冲流
			bis = new BufferedInputStream(imageIn);
			// 输出缓冲流
			bos = new BufferedOutputStream(output);
			// 缓冲字节数
			byte[] data = new byte[4096];
			int size;
			while ( ( size = bis.read(data) ) != -1) {
				bos.write(data,0,size);
			}
			bos.flush();// 清空输出缓冲流
			output.flush();
		} catch (IOException e) {
			logger.error("",e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bis!=null){bis.close();}
				if(imageIn!=null){imageIn.close();}
				if(bos!=null){bos.close();}
				if(output!=null){output.close();}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return response;
	}

	/**
	 * 保存文件
	 *
	 * @param file
	 * @param userId
	 * @param transType
	 * @throws
	 * @method: saveFile
	 * @return: com.fentuan.cashloan.core.common.model.UploadFileRes
	 * @Author: Mr.Wange
	 * @Date: 2018/7/25 14:14
	 */
	@Override
	public UploadFileRes saveFile(MultipartFile file, Long userId, String transType) {
		UploadFileRes model = new UploadFileRes();
		model.setCreateTime(DateUtil.getNow());

		// 文件名称
		String picName = file.getOriginalFilename();

		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File newFile = (File) fi.getStoreLocation();
		logger.debug("图片----------"+newFile);
		// 文件格式
		String fileType = FileTypeUtil.getFileType(newFile);
		if (StringUtil.isBlank(fileType) || !FileTypeUtil.isImage(newFile, fileType)) {
			model.setErrorMsg(MessageConstant.IMG_FORMAT_ERROR);
			logger.error(model.getErrorMsg());
			return model;
		}
		// 校验图片大小
		Long picSize = file.getSize();
		if (picSize.compareTo(10485760L) > 0) {
			model.setErrorMsg(MessageConstant.IMG_SIZE_LIMIT);
			logger.error(model.getErrorMsg());
			return model;
		}
		// 保存文件
		//wwpwan 上线后修改
		String s="/";
		String dateStr = DateUtil.dateStr(new Date(),DateUtil.DATEFORMAT_STR_012);
		String fileName = userId+"_"+System.currentTimeMillis()+"_"+picName;
		String filePath = "/data/htdocs/image/" + fileType + s + dateStr + s;
		if("head".equals(transType)){
			filePath = "/data/htdocs/image/head/" + dateStr + s;
		}

		File files = new File(filePath+fileName);
		if (!files.exists()) {
			try {
				files.mkdirs();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				model.setErrorMsg(MessageConstant.FILE_NOT_EXIST);
				return model;
			}
		}
		try {
			file.transferTo(files);
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage(), e);
		}

		// 转存文件
		model.setResPath(filePath+fileName);
		model.setFileName(picName);
		model.setFileFormat(fileType);
		model.setFileSize(new BigDecimal(picSize));
		return model;
	}

	@Override
	public BaseMapper<SysAttachment, Long> getMapper() {
		return null;
	}
}