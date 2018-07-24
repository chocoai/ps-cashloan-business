package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.business.cl.domain.PettyLoanSceneBorrow;
import com.adpanshi.cashloan.business.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.business.cl.mapper.PettyLoanSceneBorrowMapper;
import com.adpanshi.cashloan.business.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.business.cl.mapper.UserAuthMapper;
import com.adpanshi.cashloan.business.cl.service.PettyLoanSceneService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.model.AttachmentExtra;
import com.adpanshi.cashloan.business.system.mapper.SysAttachmentMapper;
import com.adpanshi.cashloan.business.system.service.SysAttachmentService;
import com.adpanshi.cashloan.business.system.service.SysDictService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-03 20:30:04
 * @history
 */
@Service("pettyLoanSceneService")
public class PettyLoanSceneServiceImpl extends BaseServiceImpl<PettyLoanScene,Long> implements PettyLoanSceneService{

	final Logger logger = LoggerFactory.getLogger(PettyLoanSceneServiceImpl.class);
	   
    @Resource
    private PettyLoanSceneMapper pettyLoanSceneMapper;
    
    @Resource
    private PettyLoanSceneBorrowMapper  pettyLoanSceneBorrowMapper;
    
    @Resource
    private SysAttachmentMapper sysAttachmentMapper;
    
    @Resource
    private SysAttachmentService sysAttachmentService;
    
    @Resource
    private UserAuthMapper userAuthMapper;
    
    @Resource
    private SysDictService sysDictService;
    
    @Resource
    private BorrowMainMapper borrowMainMapper;
	
	@Override
	public BaseMapper<PettyLoanScene, Long> getMapper() {
		return pettyLoanSceneMapper;
	}


	@Override
	public Boolean savePettyLoanSceneAssociateAttachment(PettyLoanScene pettyLoanScene, Long borrowMainId,String... attachmentIds) {
		Boolean flag=Boolean.FALSE;
		if(null==pettyLoanScene||null==attachmentIds||attachmentIds.length==0)return flag;
		//业务变更需要(用户可以重复提交)、思索良久，每次调用接口时、先把场景与场景关联的附件先作废!这样效率更高!
		int expiryCont=pettyLoanSceneMapper.updateLoanSceneExpiryByUserIdWithSceneType(pettyLoanScene.getUserId(), EnumUtil.getKeysByClz(PettyloanSceneEnum.SCENE_TYPE.class));
		logger.info("--------->置为无效的场景有:{}条.",new Object[]{expiryCont});
		//附件可以不用做逻辑处理，因为附件是根据场景关联查询的！
		int count=pettyLoanSceneMapper.save(pettyLoanScene);
		if(count<1){
			logger.info("----------------->场景新增失败!");
			return flag;
		}
		if(!StringUtil.isBlank(borrowMainId)){
			BorrowMain main=borrowMainMapper.findById(borrowMainId);
			if(null!=main){
				PettyLoanSceneBorrow pettyLoanSceneBorrow=new PettyLoanSceneBorrow(pettyLoanScene.getId(),borrowMainId,pettyLoanScene.getUserId());
				pettyLoanSceneBorrow.setRemarks("申请订单");
				pettyLoanSceneBorrowMapper.save(pettyLoanSceneBorrow);
			}
		}
		Long[] attachIds=(Long[])ConvertUtils.convert(attachmentIds, Long.class);
		int updateCount=sysAttachmentMapper.updateTargetByIdList(new AttachmentExtra("cl_petty_loan_scene","id", pettyLoanScene.getId()), Arrays.asList(attachIds));
		if(updateCount<1){
			logger.error("场景插入后绑定附件时出错!pettyLoanScene.id={},attachmentIds={}.",new Object[]{pettyLoanScene.getId(),JSONObject.toJSONString(attachmentIds)});
			throw  new BussinessException(Constant.FAIL_CODE_VALUE+"", "系统异常!"); 
		}
		return updateCount>0;
	}
	
	@Override
	public PettyLoanScene queryPettyLoanSceneByLast(Long userId, List<Integer> sceneTypes, Integer status) {
		return pettyLoanSceneMapper.queryPettyLoanSceneByLast(userId, sceneTypes, status);
	}


}