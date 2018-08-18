package com.adpanshi.cashloan.system.service;

import com.adpanshi.cashloan.core.common.model.UploadFileRes;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.model.AttachmentExtra;
import com.adpanshi.cashloan.system.domain.SysAttachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-30 11:52:05
 * @history
 */
public interface SysAttachmentService extends BaseService<SysAttachment,Long>{

	/** 
	 *  获取图片
	 * @method: getImg
	 * @param request
	 * @param response
	 * @return: javax.servlet.http.HttpServletResponse 
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/25 14:01
	 */ 
	HttpServletResponse getImg(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 保存文件
	 * @method: saveFile
	 * @param file
	 * @param userId
	 * @param transType
	 * @return: com.fentuan.cashloan.core.common.model.UploadFileRes
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/25 14:14
	 */
	UploadFileRes saveFile(MultipartFile file, Long userId, String transType);
}