package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.model.AttachmentExtra;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
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
	 * <p>根据给定条件查询附件信息</p>
	 * @param extra 待查询的条件 (targetTable、targetId 必填，如果查询有多条会以gmtUpdate倒序且仅取1条最新的数据)
	 * @return SysAttachment
	 * */
	SysAttachment getAttachmentByTargetData(AttachmentExtra extra); 
	
	/**
	 * <p>文件落地后数据存储</p>
	 * @param extra
	 * @param attachment
	 * @param 
	 * */
	Boolean save(AttachmentExtra extra, SysAttachment attachment);

  	/**
  	 * <p>处理文件上传</p>
  	 * @param file 待处理的文件
  	 * @param downloadDir 文件落地目录
  	 * @param userId 上传用户id
  	 * @return UploadFileRes
  	 * */
  	SysAttachment uploadFile(MultipartFile file, String downloadDir, Long userId) ;

  	/**
	 * <p>根据给定条件查询订单使用的场景及租房收入证明合同</p>
	 * <p>用户单击订单时需要展示该订单所关联的场景及租收入证明附件信息</p>
	 * @param targetMode 待查询的目标对象
	 * @param userId  用户id
	 * @param borrowMainId 订单id
	 * @param sceneTypes 场景
	 * @return List<SysAttachment>
	 * */
  	List<SysAttachment> queryAttachmentByBorIdWithUserIdParams(AttachmentExtra targetMode, Long userId, Long borrowMainId, List<Integer> sceneTypes);

  	/**
	 * <p>根据给定条件查询订单使用的场景及租房收入证明合同</p>
	 * <p>用户单击订单时需要展示该订单所关联的场景及租收入证明附件信息</p>
	 * @param targetMode 待查询的目标对象
	 * @param userId  用户id
	 * @param sceneTypes 场景
	 * @return List<SysAttachment>
	 * */
	List<SysAttachment> queryAttachmentByWithUserIdParams(AttachmentExtra targetMode, Long userId, List<Integer> sceneTypes);


  	/**
  	 * <p>处理文件上传</p>
  	 * @param file 待处理的文件
  	 * @param userId 上传用户id
  	 * @param classify 分类
  	 * @param subClassify 分类下的子类
  	 * @return SysAttachment
  	 * */
  	SysAttachment uploadFile(MultipartFile file, Long userId, Integer classify, Integer subClassify) ;

  	/**
  	 * <p>处理文件上传且入库</p>
  	 * @param file 待处理的文件
  	 * @param userId 上传用户id
  	 * @param classify 分类
  	 * @param subClassify 分类下的子类
  	 * @return SysAttachment
  	 * */
  	SysAttachment uploadFileWithSave(MultipartFile file, Long userId, Integer classify, Integer subClassify) ;
  	
  	/**
  	 * <p>根据给定id进行逻辑删除</p>
  	 * @param id
  	 * @return 受影响的行数
  	 * */
  	int remove(Long id);

	/**
	 *
	 * @param response
	 * @return
	 */
	HttpServletResponse getImg(HttpServletRequest request, HttpServletResponse response);
	
}