package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-03 20:30:04
 * @history
 */
public interface PettyLoanSceneService extends BaseService<PettyLoanScene, Long>{
	
	/**
	 * <p>根据给定用户查询最新的一条场景</p>
	 * @param userId
	 * @param sceneTypes
	 * @param status
	 * @return PettyLoanScene
	 * */
	PettyLoanScene queryPettyLoanSceneByLast(Long userId, List<Integer> sceneTypes, Integer status);


	/**
	 * <p>场景新增的同时需要关联已上传的附件</p>
	 * @param pettyLoanScene 场景信息
	 * @param borrowMainId
	 * @param attachmentIds 附件集
	 * @return Boolean
	 * */
	Boolean savePettyLoanSceneAssociateAttachment(PettyLoanScene pettyLoanScene, Long borrowMainId, String... attachmentIds);

}