package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.facade.StaginFacade;
import com.adpanshi.cashloan.business.cl.model.StaginRepaymentPlanData;
import com.adpanshi.cashloan.business.cl.service.BorrowMainService;
import com.adpanshi.cashloan.business.core.common.cache.RedissonClientUtil;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.model.StaginDetailModel;
import com.adpanshi.cashloan.business.core.model.StagingRecordModel;
import com.adpanshi.cashloan.business.rule.model.StaginRepaymentModel;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 ** @category 分期还款...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月22日下午9:33:10
 **/
@Scope("prototype")
@Controller
@RequestMapping(InstallmentPaymentController.ACTION_PATH)
public class InstallmentPaymentController extends BaseController{

	public static final Logger logger = LoggerFactory.getLogger(InstallmentPaymentController.class);
	
	protected final static String ACTION_PATH= "/com/adpanshi/cashloan/api/act/installment";
	
	@Autowired
	BorrowMainService borrowMainService;
	
	@Autowired
	StaginFacade staginFacade;
	
	/**
	 * <p>分期记录列表</p>
	 * */
	@RequestMapping(value = "/list.htm",method = RequestMethod.POST)
	public void list(@RequestParam(value="userId") long userId,@RequestParam(value = "current") int current,@RequestParam(value = "pageSize") int pageSize){
		Map<String,Object> result = new HashMap<String,Object>();
		Page<StagingRecordModel> page= borrowMainService.pageByUserId(userId, current, pageSize);
		Map<String, Object> data = new HashMap<>();
		data.put("list", page.getResult());
		result.put(Constant.RESPONSE_DATA, data); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * <p>分期详情</p>
	 */
	@RequestMapping(value = "/detail.htm", method = RequestMethod.POST)
	public void findProgress(@RequestParam(value="userId")long userId,@RequestParam(value="borMainId") long borMainId){
		Map<String,Object> result = new HashMap<String,Object>();
		StaginDetailModel staginDetail= staginFacade.getStaginDetailBy(userId, borMainId); 
		result.put(Constant.RESPONSE_DATA, staginDetail); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * <p>分期还款明细列表</p>
	 */
	@RequestMapping(value = "/repayment/detailList.htm", method = RequestMethod.POST)
	public void repaymentDetailList(@RequestParam(value="userId")long userId,@RequestParam(value="borMainId") long borMainId){
		Map<String,Object> result = new HashMap<String,Object>();
		List<StaginRepaymentModel> staginRepayments= staginFacade.queryRepaymentsByUserIdWithBorMainId(userId, borMainId);
		result.put(Constant.RESPONSE_DATA, staginRepayments); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * <p>分期还款计划列表</p>
	 * @param userId 用户id
	 * @param borMainId 主订单id
	 * @param repayment (true:已还款、false:未还款)
	 * */
	@RequestMapping(value = "/repayment/planList.htm", method = RequestMethod.POST)
	public void repaymentPlanList(@RequestParam(value="userId")long userId,@RequestParam(value="borMainId") long borMainId,Boolean repayment){
		Map<String,Object> result = new HashMap<String,Object>();
		StaginRepaymentPlanData staginRepaymentPlan= staginFacade.queryRepaymentPlanByUserIdWithBorMainId(userId, borMainId, repayment);
		result.put(Constant.RESPONSE_DATA, staginRepaymentPlan); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 主动还款
	 * @param userId 待还款的用户
	 * @param borrowOrderNos 待还款的订单数据集
	 * */
	@RequestMapping(value = "/active/repayment.htm", method = RequestMethod.POST)
	public void activeRepayment(@RequestParam(value="userId")long userId,@RequestParam(value="borrowOrderNos") String borrowOrderNos){
		String[] curBorrowOrders=borrowOrderNos.split(",");
		logger.info(userId + "分期主动还款(认证支付)-app同步查询订单" + borrowOrderNos+"userId为"+userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		RLock rlock=RedissonClientUtil.getInstance().getLock("repayment_"+userId+"_"+JSONObject.toJSONString(borrowOrderNos));
		Object object=null;
		try {
			rlock.lock(60, TimeUnit.SECONDS);
			object=staginFacade.activePayment(userId, curBorrowOrders);
			logger.info("---------------->[RLock]进入主动还款,订单号:{},加锁成功...",new Object[]{borrowOrderNos});
		}finally {
			rlock.unlock();
			logger.info("---------------->[RLock]进入主动还款,订单号:{},锁释放成功...",new Object[]{borrowOrderNos});
		}
		result.put(Constant.RESPONSE_DATA, object);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
}
