package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.PettyLoanScene;
import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.enums.PettyloanSceneEnum;
import com.adpanshi.cashloan.business.cl.enums.UserAuthEnum;
import com.adpanshi.cashloan.business.cl.service.PettyLoanSceneService;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.service.impl.CallSaasService;
import com.adpanshi.cashloan.business.core.common.cache.RedissonClientUtil;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.model.AttachmentExtra;
import com.adpanshi.cashloan.business.core.model.FileModel;
import com.adpanshi.cashloan.business.core.model.LeaseFileModel;
import com.adpanshi.cashloan.business.system.domain.SysAttachment;
import com.adpanshi.cashloan.business.system.mapper.SysAttachmentMapper;
import com.adpanshi.cashloan.business.system.service.SysDictService;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 ** @category 用户认证控制器...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月3日下午9:32:41
 **/
@Controller
@Scope("prototype")
@RequestMapping(UserAuthController.ACTION_PATH)
public class UserAuthController extends BaseController{
	
    final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    protected final static String ACTION_PATH= "/com/adpanshi/cashloan/api/act/user/userAuth";
    
    @Autowired
    UserAuthService userAuthService;
    
    @Autowired
    PettyLoanSceneService pettyLoanSceneService;
    
    @Autowired
    SysAttachmentMapper sysAttachmentMapper;
    
    @Autowired
    SysDictService sysDictService;
    
    @Autowired
    CallSaasService callSaasService;

    /**
     * <p>租房合同页面</p>
     * */
    @RequestMapping(value = "/tenementIncome.htm", method = RequestMethod.POST)
  	public void tenementIncome(@RequestParam(value="userId")Long userId)throws Exception {
    	List<Map<String, Object>> depositTypes= sysDictService.getDictsCache(PettyloanSceneEnum.DEPOSIT_TYPE);
    	List<Map<String, Object>> houseTypes= sysDictService.getDictsCache(PettyloanSceneEnum.HOUSE_TYPE);
    	List<Map<String, Object>> naturePropertys= sysDictService.getDictsCache(PettyloanSceneEnum.NATURE_PROPERTY);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put(PettyloanSceneEnum.DEPOSIT_TYPE, depositTypes);
		data.put(PettyloanSceneEnum.HOUSE_TYPE, houseTypes);
		data.put(PettyloanSceneEnum.NATURE_PROPERTY, naturePropertys);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
    }
    

  	/**
     * 租房合同&收入证明认证状态(如果是已认证或认证中的则显示合同及收入证明)
  	 */
  	@RequestMapping(value = "/tenementIncomeISAuth.htm", method = RequestMethod.POST)
  	public void tenementIncomeISAuth(@RequestParam(value = "userId") Long userId)throws Exception {
  		logger.info("--------------->userId={}.",new Object[]{userId});
  		Map<String,Object> data=new HashMap<>();
  		RLock rlock=RedissonClientUtil.getInstance().getLock("tenementIncomeISAuth_"+userId);
  		LeaseFileModel leaseFile=null;
		try {
			rlock.lock(50, TimeUnit.SECONDS);
			UserAuth userAuth=userAuthService.findSelective(userId);
	  		if(null==userAuth)throw new BussinessException("非法用户！");
	  		String tenementIncomeState = userAuth.getTenementIncomeState();
	  		data.put("userId", userId);
	  		if(StringUtil.isEmpty(tenementIncomeState))tenementIncomeState=UserAuthEnum.TENEMENT_INCOME_STATE.UN_AUTH.getCode();
	  		//根据用户ID查找合同与收入证明图片
	  		if(UserAuthEnum.TENEMENT_INCOME_STATE.CERTIFICATION.getCode().equals(userAuth.getTenementIncomeState()) || 
	  				UserAuthEnum.TENEMENT_INCOME_STATE.AUTHENTICATED.getCode().equals(userAuth.getTenementIncomeState())){
	  			//先查询最新的一条消费场景 
	  			PettyLoanScene pettyLoanScene= pettyLoanSceneService.queryPettyLoanSceneByLast(userId, EnumUtil.getKeysByClz(PettyloanSceneEnum.SCENE_TYPE.class), 1);
	  			if(null!=pettyLoanScene){
	  				List<String> tenementIncomeStates=Arrays.asList(UserAuthEnum.TENEMENT_INCOME_STATE.CERTIFICATION.getCode(),UserAuthEnum.TENEMENT_INCOME_STATE.AUTHENTICATED.getCode());
	  				List<SysAttachment> sysAttachments=sysAttachmentMapper.queryAttachmentByPettyLoanScene(new AttachmentExtra("cl_petty_loan_scene", "id",pettyLoanScene.getId()), userId, tenementIncomeStates);
	  				List<FileModel> fileModels= FileModel.handleAttachment(sysAttachments);
	  				leaseFile=new LeaseFileModel(pettyLoanScene.getStartTime(),pettyLoanScene.getValidityTime(),fileModels,pettyLoanScene.getStatus());
	  			}
	  		}
	  		data.put("status", tenementIncomeState);
		}finally {
			rlock.unlock();
		}
		data.put("LeaseFile", leaseFile);
  		Map<String,Object> result=new HashMap<>();
  		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
  		ServletUtils.writeToResponse(response,result);
  	}
  	
}
