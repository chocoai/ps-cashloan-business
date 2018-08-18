package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.cl.domain.PettyLoanSceneBorrow;
import com.adpanshi.cashloan.cl.mapper.PettyLoanSceneBorrowMapper;
import com.adpanshi.cashloan.cl.mapper.PettyLoanSceneMapper;
import com.adpanshi.cashloan.cl.service.PettyLoanSceneBorrowService;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-06 09:52:47
 * @history
 */
@Service("pettyLoanSceneBorrowService")
public class PettyLoanSceneBorrowServiceImpl extends BaseServiceImpl<PettyLoanSceneBorrow,Long> implements PettyLoanSceneBorrowService{

	final Logger logger = LoggerFactory.getLogger(PettyLoanSceneBorrowServiceImpl.class);
	   
    @Resource
    private PettyLoanSceneBorrowMapper pettyLoanSceneBorrowMapper;
    
    @Resource
    private PettyLoanSceneMapper pettyLoanSceneMapper;
	
	@Override
	public BaseMapper<PettyLoanSceneBorrow, Long> getMapper() {
		return pettyLoanSceneBorrowMapper;
	}

	@Override
	public int savePettyLoanSceneBorrow(Long userId, Long borrowMainId) {
		if(StringUtil.isEmpty(userId,borrowMainId)){
			logger.error("---------------->call savePettyLoanSceneBorrow()缺少必要参数.userId={},borrowMainId={}.",new Object[]{userId,borrowMainId});
			return 0;
		}
		PettyLoanScene pettyLoanScene= pettyLoanSceneMapper.findpettyLoanSceneByUserId(userId);
		if(null==pettyLoanScene || pettyLoanScene.getStatus()!=1 ){
			logger.error("---------------->租房合同&收入证明未上传或已失效，跳过后续逻辑.");
			return 0;
		}
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		map.put("borrowId", borrowMainId);
		map.put("pettyLoanSceneId", pettyLoanScene.getId());
		int count=pettyLoanSceneBorrowMapper.queryCount(map);
		if(count>0){
			logger.error("---------------->出现并发，请忽略，数据不做存储。");
			return 0;
		}
		PettyLoanSceneBorrow pettyLoanSceneBorrow=new PettyLoanSceneBorrow(pettyLoanScene.getId(),borrowMainId,userId);
		pettyLoanSceneBorrow.setRemarks("申请订单");
		return pettyLoanSceneBorrowMapper.save(pettyLoanSceneBorrow);
	}
}