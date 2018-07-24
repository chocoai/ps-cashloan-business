package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.SysAttachmentEnum;
import com.adpanshi.cashloan.business.core.common.enums.SysAttachmentEnum.CLASSIFY;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.model.AttachmentExtra;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
import com.adpanshi.cashloan.business.system.mapper.SysAttachmentMapper;
import com.adpanshi.cashloan.business.system.service.SysAttachmentService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-30 11:52:05
 * @history
 */
@Service("sysAttachmentService")
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment,Long> implements SysAttachmentService {

	/**单个文件上传最大值5M*/
    final Long maxfileSize=5*1024*1024L;
	
	Logger logger =LoggerFactory.getLogger(getClass());
	
	static List<String> imgs=Arrays.asList("bmp","jpg","jpeg","png","tiff","gif","pcx","tga","exif","fpx","svg","psd","cdr","pcd","dxf","ufo","eps","ai","raw");
	
	@Resource
    private SysAttachmentMapper sysAttachmentMapper;

	@Resource
	private SysAttachmentService sysAttachmentService;

	@Override
	public BaseMapper<SysAttachment, Long> getMapper() {
		return sysAttachmentMapper;
	}

	@Override
	public SysAttachment getAttachmentByTargetData(AttachmentExtra extra) {
		if(null==extra) return null;
		if(!StringUtil.isNotEmptys(extra.getTargetId(),extra.getTargetTable())){
			logger.info("------------>Parameter cannot be null，parameters={}.",new Object[]{JSONObject.toJSONString(extra)});
			return null;
		}
		return sysAttachmentMapper.getAttachmentByTargetData(extra);
	}

	@Override
	public Boolean save(AttachmentExtra extra,SysAttachment attachment) {
		if(null==attachment){
			return Boolean.FALSE;
		}
		if(null!=extra){
			attachment.setTargetTable(extra.getTargetTable());
			attachment.setTargetId(extra.getTargetId());
			attachment.setTargetField(extra.getTargetField());
		}
		int count=sysAttachmentMapper.save(attachment);
		return count>0?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public SysAttachment uploadFile(MultipartFile file, String downloadDir, Long userId) {
		Date cdate=new Date();
		SysAttachment attachment =null;
		String picName = file.getOriginalFilename();
		// 校验图片大小
		Long fileSize = file.getSize();
		if(fileSize<=0)return attachment;
		if (fileSize.compareTo(maxfileSize) > 0) {
			logger.error("--------------------->文件超出5M大小限制");
			return attachment;
		}
		// 保存文件
		String fileType=file.getContentType();
		FileExtra fileExtra=resetFilePath(cdate, picName, fileType, downloadDir, userId);
		File files = new File(fileExtra.getFilePath());
		if (!files.exists()) {
			try {
				files.mkdirs();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return attachment;
			}
		}
		try {
			file.transferTo(files);
			if(null==attachment)attachment=new SysAttachment();
			attachment.setFilePath(fileExtra.getFilePath());
			attachment.setClassify(1);
			attachment.setOriginName(fileExtra.getOrginName());
			attachment.setSuffix(fileExtra.getSuffix());
			attachment.setAttachmentType(fileType);
			attachment.setSize(fileSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return attachment;
	}
	
	@Override
	public SysAttachment uploadFile(MultipartFile file, Long userId, Integer classify, Integer subClassify) {
		if(null==file||file.isEmpty()) throw  new BussinessException(Constant.FAIL_CODE_PARAM_INSUFFICIENT+"", "上传的附件不能为空!");
		Date cdate=new Date();
		SysAttachment attachment =null;
		String picName = file.getOriginalFilename();
		// 校验图片大小
		Long fileSize = file.getSize();
		if(fileSize<=0)return attachment;
		if (fileSize.compareTo(maxfileSize) > 0)  throw  new BussinessException(Constant.OTHER_CODE_VALUE+"", "上传的附件不能大于最大限制!");
		// 保存文件
		String fileType=file.getContentType();
		CLASSIFY classifyEnum=(CLASSIFY) EnumUtil.getEnumByKey(SysAttachmentEnum.CLASSIFY.class, classify);
		FileExtra fileExtra=resetFilePath(cdate, picName, fileType, Global.getValue(classifyEnum.getFileDirCode()), userId);
		File files = new File(fileExtra.getFilePath());
		if (!files.exists()) {
			try {
				files.mkdirs();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return attachment;
			}
		}
		try {
			file.transferTo(files);
			if(null==attachment)attachment=new SysAttachment();
			attachment.setFilePath(fileExtra.getFilePath());
			attachment.setClassify(classify);
			attachment.setSubClassify(subClassify);
			attachment.setOriginName(fileExtra.getOrginName());
			attachment.setSuffix(fileExtra.getSuffix());
			attachment.setAttachmentType(fileType);
			attachment.setSize(fileSize);
			attachment.setRemarks(classifyEnum.getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return attachment;
	}
	
	@Override
	public SysAttachment uploadFileWithSave(MultipartFile file, Long userId, Integer classify, Integer subClassify) {
		SysAttachment attachment= uploadFile(file, userId, classify, subClassify);
		int count=sysAttachmentMapper.save(attachment);
		return count>0?attachment:null;
	}
	
	@Override
	public List<SysAttachment> queryAttachmentByBorIdWithUserIdParams(AttachmentExtra targetMode, Long userId,Long borrowMainId, List<Integer> sceneTypes) {
		if(null==targetMode || StringUtil.isEmpty(userId,sceneTypes,borrowMainId)){
			logger.error("------------------->call queryAttachmentByBorIdWithUserIdParams mehthod 缺少必要参数.");
			return null;
		}
		return sysAttachmentMapper.queryAttachmentByBorIdWithUserIdParams(targetMode, userId, borrowMainId, sceneTypes);
	}
	
	@Override
	public List<SysAttachment> queryAttachmentByWithUserIdParams(AttachmentExtra targetMode, Long userId,List<Integer> sceneTypes) {
		if(null==targetMode || StringUtil.isEmpty(userId,sceneTypes)){
			logger.error("------------------->call queryAttachmentByBorIdWithUserIdParams mehthod 缺少必要参数.");
			return null;
		}
		return sysAttachmentMapper.queryAttachmentByWithUserIdParams(targetMode, userId, sceneTypes);
	}
	
	/**
  	 * 重置文件名
  	 * @param date
  	 * @param fileName
  	 * @param fileType(image/jpeg)
  	 * @param downloadDir
  	 * @param userId
  	 * @return 
  	 * */
  	private FileExtra resetFilePath(Date date,String fileName,String fileType,String downloadDir,Long userId){
  		FileExtra fileExtra=new FileExtra();
  		StringBuffer filePathSb=new StringBuffer();
  		StringBuffer fileNameSb=new StringBuffer();
		String separator=File.separator;
		String dateStr = DateUtil.dateStr(date,DateUtil.DATEFORMAT_STR_012);
		if(StringUtil.isNotEmptys(downloadDir) && (downloadDir.lastIndexOf(separator)!=-1 || !downloadDir.endsWith("/"))){
			downloadDir+=separator;
		}
		downloadDir+=(dateStr+separator);
		String newFileName=System.currentTimeMillis()+"_"+userId+"_"+fileName;
		newFileName=resetFileName(newFileName, fileType);
		fileNameSb.append(newFileName);
		filePathSb.append(downloadDir).append(fileNameSb.toString());
		fileType=resetFileType(fileType,newFileName);
		fileExtra.setFilePath(filePathSb.toString());
		fileExtra.setOrginName(fileNameSb.toString());
		String suffix=getSuffix(newFileName);
		fileExtra.setSuffix(StringUtil.isEmpty(suffix)?fileType:suffix);
  		return fileExtra;
  	}
  	
	public static String resetFileName(String fileName,String fileType){
		if(imgs.contains(fileName)) return fileName;
	  	for(String img:imgs){
	  		if(img.equalsIgnoreCase(fileName) || img.equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()))) return fileName;
	  	}
	  	if(!StringUtil.isEmpty(fileType)){
	  		if(fileType.contains("/")||fileType.contains("\\")){
	  			String tmpSuffix=fileType.substring(fileType.lastIndexOf("/")+1, fileType.length());
	  			if(StringUtil.isEmpty(tmpSuffix)){
	  				tmpSuffix=fileType.substring(fileType.lastIndexOf("\\")+1, fileType.length());
	  			}
	  			return fileName+"."+tmpSuffix;
	  		}else{
	  			return fileName+"."+fileType;
	  		}
	  	}
	  	return fileName;
	}
  	
  	/**
  	 * <p>重置文件类型<p/>
  	 * @param fileType(fileType(image/jpeg | multipart/form-data))
  	 * @param fileName
  	 * @return 
  	 * */
  	protected String resetFileType (String fileType,String fileName){
  		String newFileType=fileType;
  		if(StringUtils.isBlank(fileType)) return fileType;
  		fileType=getSuffix(fileName);
  		if(fileType.lastIndexOf(File.separator)!=-1){
  			newFileType=fileType.substring(fileType.lastIndexOf(File.separator)+1);
  		}else if(fileType.lastIndexOf("/")!=-1){
  			newFileType=fileType.substring(fileType.lastIndexOf("/")+1);
  		}
  		return newFileType;
  	}
  	
  	String getSuffix(String fileName){
  		if(imgs.contains(fileName))return fileName.substring(fileName.lastIndexOf("."), fileName.length());
  		for(String img:imgs){
  			if(img.equalsIgnoreCase(fileName) || img.equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()))) return fileName.substring(fileName.lastIndexOf("."), fileName.length());
  		}
  		return null;
  	}
  	
  	class FileExtra{
  		private String filePath;
  		private String orginName;
  		private String suffix;
  		
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public String getOrginName() {
			return orginName;
		}
		public void setOrginName(String orginName) {
			this.orginName = orginName;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
  		
  	}

	@Override
	public int remove(Long id) {
		if(null==id)return 0;
		SysAttachment attachment=new SysAttachment();
		attachment.setId(id);
		attachment.setStatus(0);
		return sysAttachmentMapper.update(attachment);
	}

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
//            String[] names = path.split("/");
//            String fileName = names[names.length-1];
//            String sftpConfigName = "img_sftp";
//            SftpUtil sftp = new SftpUtil(sftpConfigName);
//            imageIn = sftp.downFile(url);
//            //如果图片不存在 返回Null
//            if(imageIn==null){
//                return;
//            }
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
}