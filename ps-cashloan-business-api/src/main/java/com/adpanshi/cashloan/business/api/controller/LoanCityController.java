package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.constant.LoanCityConstant;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.cl.model.loancity.BaseLoanCityModel;
import com.adpanshi.cashloan.business.cl.model.loancity.LoanCityDemandModel;
import com.adpanshi.cashloan.business.cl.service.BorrowMainService;
import com.adpanshi.cashloan.business.cl.service.LoanCityLogService;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 消贷同城响应接口
 *
 * @author yecy
 * @date 2018/1/1 19:11
 */
@Controller
@Scope("prototype")
@RequestMapping("com/adpanshi/cashloan/api/loancity")
public class LoanCityController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoanCityController.class);

    private LoanCityLogService loanCityLogService;
    private BorrowMainService borrowMainService;

    @Autowired
    public LoanCityController(LoanCityLogService loanCityLogService,
                              BorrowMainService borrowMainService) {
        this.loanCityLogService = loanCityLogService;
        this.borrowMainService = borrowMainService;
    }

    /**
     * 消贷同城推送需求接口
     */
    @RequestMapping(value = "demand.htm", method = RequestMethod.POST)
    @ResponseBody
    public void demand(@RequestBody String data) {
        logger.info("【响应消贷同城】需求接口参数：{}", data);
        if (!saveRespAndCheck(data, LoanCityDemandModel.class)) {
            return;
        }

        LoanCityDemandModel demandModel = JSONObject.parseObject(data, LoanCityDemandModel.class);
        String reqNo = demandModel.getRequireNo();

        // 判断需求编号是否存在
        LoanCityLog logTemp = loanCityLogService.getById(reqNo);
        if (logTemp != null) {
            logger.error("【响应消贷同城】需求已存在{}", reqNo);
            Map<String, Object> result = new HashMap<>();
            result.put(LoanCityConstant.RES_CODE, LoanCityConstant.RES_CODE_FAIL_EXIST);
            result.put(LoanCityConstant.RES_MSG, LoanCityConstant.RES_MSG_FAIL_EXIST);
            ServletUtils.writeToResponse(response, result);
            return;
        }

        // 保存需求
        LoanCityLog log = new LoanCityLog();
        BeanUtils.copyProperties(demandModel, log, "createTime");
        log.setCreateTime(new Date());
        loanCityLogService.insert(log);

        Map<String, Object> result = new HashMap<>();
        result.put(LoanCityConstant.RES_CODE, LoanCityConstant.RES_CODE_SUCCESS);
        result.put(LoanCityConstant.RES_MSG, LoanCityConstant.RES_MSG_SUCCESS);
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 消贷同城需求过期接口
     * <p>借款订单已放款的，不允许变更</p>
     */
    @RequestMapping(value = "expired.htm", method = RequestMethod.POST)
    @ResponseBody
    public void expired(@RequestBody String data) {
        logger.info("【响应消贷同城】需求过期接口参数：{}", data);
        updateState(data);

    }

    /**
     * 消贷同城需求撤销接口
     * <p>借款订单已放款的，不允许变更</p>
     */
    @RequestMapping(value = "rescind.htm", method = RequestMethod.POST)
    @ResponseBody
    public void rescind(@RequestBody String data) {
        logger.info("【响应消贷同城】需求撤销接口参数：{}", data);
        updateState(data);
    }

    /**
     * 消贷同城需求支付接口
     * <p>借款订单已放款的，不允许变更</p>
     * <p>需求状态不为未支付的，不允许变更</p>
     */
    @RequestMapping(value = "paid.htm", method = RequestMethod.POST)
    @ResponseBody
    public void paid(@RequestBody String data) {
        logger.info("【响应消贷同城】需求支付接口参数：{}", data);
        updateState(data);
    }


    private Boolean saveRespAndCheck(String data, Class<? extends BaseLoanCityModel> clzz) {
        // 保存响应信息
        BaseLoanCityModel baseModel = JSONObject.parseObject(data, clzz);

        String reqNo = baseModel.getRequireNo();

        if (!baseModel.checkSign()) {
            logger.error("【响应消贷同城】需求验签失败{}", reqNo);
            Map<String, Object> result = new HashMap<>();
            result.put(LoanCityConstant.RES_CODE, LoanCityConstant.RES_CODE_FAIL_SIGN);
            result.put(LoanCityConstant.RES_MSG, LoanCityConstant.RES_MSG_FAIL_SIGN);
            ServletUtils.writeToResponse(response, result);
            return false;
        }
        return true;
    }


    private void updateState(String data) {
        if (!saveRespAndCheck(data, BaseLoanCityModel.class)) {
            return;
        }
        BaseLoanCityModel stateModel = JSONObject.parseObject(data, BaseLoanCityModel.class);

        String reqNo = stateModel.getRequireNo();

        // 判断需求编号是否存在
        LoanCityLog logTemp = loanCityLogService.getById(reqNo);
        if (logTemp == null) {
            logger.error("【响应消贷同城】需求不存在{}", reqNo);
            Map<String, Object> result = new HashMap<>();
            result.put(LoanCityConstant.RES_CODE, LoanCityConstant.RES_CODE_FAIL_NOT_EXIST);
            result.put(LoanCityConstant.RES_MSG, LoanCityConstant.RES_MSG_FAIL_NOT_EXIST);
            ServletUtils.writeToResponse(response, result);
            return;
        }

        //如果推送的为支付接口，需求状态不为未支付的，不允许变更
        if (BaseLoanCityModel.LoanCityStateEnum.PAID.getCode().equals(stateModel.getState())
                && !BaseLoanCityModel.LoanCityStateEnum.UNPAID.getCode().equals(logTemp.getState())) {
            logger.error("【响应消贷同城】当前需求状态不为未支付的，不允许变更为支付{}", reqNo);
            Map<String, Object> result = new HashMap<>();
            result.put(LoanCityConstant.RES_CODE, LoanCityConstant.RES_CODE_FAIL_STATE);
            result.put(LoanCityConstant.RES_MSG, LoanCityConstant.RES_MSG_FAIL_STATE);
            ServletUtils.writeToResponse(response, result);
            return;
        }

        //判断需求对应的借款订单状态,状态为放款或完成的不允许撤销或过期
        Long mainId = logTemp.getBorrowMainId();
        if (mainId != null) {
            BorrowMain borrowMain = borrowMainService.getById(mainId);
            if (borrowMain != null) {
                String borrowState = borrowMain.getState();
                if (BorrowModel.STATE_FINISH.equals(borrowState) || BorrowModel.STATE_REPAY.equals(borrowState)) {
                    logger.error("【响应消贷同城】借款订单已放款，不允许撤销或过期，req:{},borrowMainId:{}", reqNo, mainId);
                    Map<String, Object> result = new HashMap<>();
                    result.put(LoanCityConstant.RES_CODE, LoanCityConstant.RES_CODE_FAIL_FINISH);
                    result.put(LoanCityConstant.RES_MSG, LoanCityConstant.RES_MSG_FAIL_FINISH);
                    ServletUtils.writeToResponse(response, result);
                    return;
                }
            }
        }

    }
}
