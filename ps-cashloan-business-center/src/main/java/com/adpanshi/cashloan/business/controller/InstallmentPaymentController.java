package com.adpanshi.cashloan.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.facade.StaginFacade;
import com.adpanshi.cashloan.cl.service.BorrowMainService;
import com.adpanshi.cashloan.core.common.cache.RedissonClientUtil;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
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
	
	protected final static String ACTION_PATH="/api/act/installment";
	
	@Autowired
	BorrowMainService borrowMainService;
	
	@Autowired
	StaginFacade staginFacade;

	/**
	 * 主动还款
	 * @param userId 待还款的用户
	 * @param borrowOrderNos 待还款的订单数据集
	 * */
	@RequestMapping(value = "/active/repayment.htm", method = RequestMethod.POST)
	public void activeRepayment(@RequestParam(value="userId")long userId, @RequestParam(value="borrowOrderNos") String borrowOrderNos){
		String[] curBorrowOrders=borrowOrderNos.split(",");
		logger.info(userId + "分期主动还款(认证支付)-app同步查询订单" + borrowOrderNos+"userId为"+userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		RLock rlock=RedissonClientUtil.getInstance().getLock("repayment_"+userId+"_"+ JSONObject.toJSONString(borrowOrderNos));
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
