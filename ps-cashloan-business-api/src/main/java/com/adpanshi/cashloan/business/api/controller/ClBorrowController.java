package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.facade.BorrowFacade;
import com.adpanshi.cashloan.business.cl.facade.IndexFacade;
import com.adpanshi.cashloan.business.cl.model.BorrowConfirmModel;
import com.adpanshi.cashloan.business.cl.model.IndexPageModel;
import com.adpanshi.cashloan.business.cl.model.RepayShowModel;
import com.adpanshi.cashloan.business.cl.service.BorrowMainService;
import com.adpanshi.cashloan.business.cl.service.BorrowTemplateService;
import com.adpanshi.cashloan.business.cl.service.ClBorrowService;
import com.adpanshi.cashloan.business.core.common.cache.RedissonClientUtil;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.exception.SimpleMessageException;
import com.adpanshi.cashloan.business.core.common.util.*;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.rule.model.ClBorrowModel;
import com.adpanshi.cashloan.business.rule.model.IndexModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 借款申请风控接口执行记录
 * @author 8452
 * @version 1.0
 * @date 2017年4月11日下午5:40:14
 * Copyright 粉团网路 现金贷  All Rights Reserved
 */
@Scope("prototype")
@Controller
public class ClBorrowController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(ClBorrowController.class);
    @Resource
    private ClBorrowService clBorrowService;
    @Resource
    private BorrowFacade borrowFacade;
    @Resource
    private BorrowMainService borrowMainService;
    @Resource
    private BorrowTemplateService borrowTemplateService;
    @Resource
    private IndexFacade indexService;

    /**
     * 查询借款列表
     *
     * @param userId
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/findAll.htm")
    public void findAll(
            @RequestParam(value = "userId") long userId) throws Exception {
        logger.info(" 查询借款列表: userId " + userId);
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("userId", userId);
        List<BorrowMain> list = clBorrowService.findBorrow(searchMap);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", list);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 查询借款
     *
     * @param userId
     * @param current
     * @param pageSize
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/borrow/page.htm", method = RequestMethod.POST)
    public void page(
            @RequestParam(value = "userId") long userId,
            @RequestParam(value = "current") int current,
            @RequestParam(value = "pageSize") int pageSize) throws Exception {
        Map<String, Object> searchMap = new HashMap<>(16);
        searchMap.put("userId", userId);
        Page<ClBorrowModel> page = clBorrowService.page(searchMap, current, pageSize);
        Map<String, Object> data = new HashMap<>(16);
        data.put("list", page.getResult());
        Map<String, Object> result = new HashMap<>(16);
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 查询订单
     *
     * @param borrowId
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/findBorrow.htm", method = RequestMethod.POST)
    public void findBorrow(
            @RequestParam(value = "borrowId", required = false) Long borrowId) throws Exception {
        Borrow borrow = clBorrowService.getById(borrowId);
        ClBorrowModel data = new ClBorrowModel();
        BeanUtils.copyProperties(borrow, data);
        Map<String, Object> result = new HashMap<>(16);
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 首页信息查询
     *
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/borrow/findIndex.htm", method = RequestMethod.POST)
    public String findIndex() throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> data = clBorrowService.findIndex(userId);
        Object code;
        Object msg;
        if (houseIncome(data)) {
            code = Constant.SUCCEED_CODE_VALUE;
            msg = "查询成功";
        } else {
            code = Constant.OTHER_CODE_VALUE;
            String toast = Global.getValue("house_income_toast");
            msg = StringUtil.isNotEmptys(toast) ? toast : "请先完善租房收入合同!";
        }
        Map<String, Object> result = new HashMap<>(16);
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, code);
        result.put(Constant.RESPONSE_CODE_MSG, msg);
        ServletUtils.writeToResponse(response, result);
        return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, data).toJson().toJSONString();
    }

    /**
     * <p>考虑到租房合同这块是没用用原生、而且app也不需要发布、故直接查询-用户如果没有完善租房收入证明时给错误提示</p>
     */
    boolean houseIncome(Map<String, Object> data) {
        Boolean flag = Boolean.TRUE;
        if (null == data || data.isEmpty()) return flag;
        if (null != data.get("auth")) {
//			 auth={total=8, qualified_new=1, result=7, qualified_five=1, house_income=25, qualified=0}
            try {
                String json = JSONObject.toJSONString(data.get("auth"));
                if (StringUtil.isNotEmptys(json)) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    Object five = jsonObj.get("qualified_five");
                    Object qualified = jsonObj.get("qualified");
                    if (StringUtil.isNotEmptys(five, qualified) && Integer.parseInt(five.toString()) == 1 && Integer.parseInt(qualified.toString()) == 0) {
                        return Boolean.FALSE;
                    }
                }
            } catch (Exception e) {
                logger.error("-------------->json解释异常", e);
            }
        }
        return flag;
    }

    /**
     * 首页轮播信息
     *
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/borrow/listIndex.htm", method = RequestMethod.POST)
    public void listIndex() throws Exception {
        List<IndexModel> list = clBorrowService.listIndex();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("list", list);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 选择借款金额和期限
     * app里选择借款金额和期限，返回实际到账金额、服务费、服务费明细
     *
     * @param amount
     * @param timeLimit
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/borrow/choice.htm")
    public void choice(@RequestParam(value = "amount") double amount,
                       @RequestParam(value = "timeLimit") String timeLimit,
                       @RequestParam(value = "userId") String userId) {
        Map<String, Object> result = new HashMap<>(16);
        if (!(amount > 0) || StringUtil.isBlank(timeLimit)) {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败，请核对参数");
        } else {
            result.put(Constant.RESPONSE_DATA, borrowTemplateService.choice(amount, timeLimit, userId));
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }

        ServletUtils.writeToResponse(response, result);
    }


    /**
     * 借款申请  新
     *
     * @param amount
     * @param timeLimit
//     * @param tradePwd
     * @param cardId
     * @param client
     * @param address
     * @param coordinate
     * @param borrowType 借款意图(用户勾选的借款意图)
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/save.htm")
    public void save(
            @RequestParam(value = "amount") double amount,
            @RequestParam(value = "timeLimit") String timeLimit,
            @RequestParam(value = "cardId") long cardId,
            @RequestParam(value = "client") String client,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "coordinate") String coordinate,
            @RequestParam(value = "ip", required = false) String ip,
            @RequestParam(value = "borrowType")Integer borrowType,
            @RequestParam(value = "channelId")Long channelId
    ) throws Exception {

        String mobileType = request.getParameter("mobileType");
        long userId = Long.parseLong(request.getParameter("userId"));
        Map<String, Object> result = new HashMap<>(16);
        BorrowMain borrow = new BorrowMain(userId, amount, timeLimit, cardId, client, address, coordinate, ip,borrowType, channelId);
        logger.info(userId + " mobileType 借款 新->" + mobileType);

        //加锁
        RLock redisLock = RedissonClientUtil.getRedisLock(ClBorrowController.class, "save", userId);
        try {
            //@remarks: 日志输出.引导.更改锁占用的时间,35秒. @date:20171003 @author:nmnl
            logger.info("用户" + userId + "是否被上锁true/false: " + redisLock.isLocked());
            redisLock.lock(120, TimeUnit.SECONDS);
            logger.info("用户" + userId + "进入锁-进入借款申请审核: " + DateUtil.dateToString(new Date(), DateUtil.DATEFORMAT_STR_001));

            borrow = borrowFacade.rcBorrowApplyForAPI(borrow, null, mobileType);
        } finally {
            logger.info("用户" + userId + "释放锁-进入借款申请审核: " + DateUtil.dateToString(new Date(), DateUtil.DATEFORMAT_STR_001));
            redisLock.forceUnlock();
        }

        if (borrow != null && borrow.getId() > 0) {
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.APPLICATION_APPROVED);
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("state", borrow.getState());
            //返回值中增加borrowId ,方便app端跳转 @author yecy 20171226
            dataMap.put("borrowMainId", borrow.getId().toString());
            //后加wwpwan
            result.put(Constant.RESPONSE_DATA, dataMap);
        } else {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.APPLICATION_FAILED);
        }

        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 首页信息查询(新)
     *
     * @author yecy
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/borrow/index.htm", method = RequestMethod.POST)
    public void findIndexNew(Long userId) {
        IndexPageModel indexPage = indexService.getIndex(userId);
        // 增加是否可以调用评估额度接口字段 @author yecy 20180417
        Boolean canAssessment;
        try {
            canAssessment = borrowFacade.isCanCreateBorrow(userId);
        }catch (SimpleMessageException e){
            // 异常捕获，不做处理，只是设置为不允许调用评估接口
            canAssessment = false;
            indexPage.setErrorMsg(e.getMessage());
        }
        indexPage.setCanAssessment(canAssessment);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, JSON.toJSON(indexPage));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 首页还款计划查询
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/borrow/repayDetail.htm", method = RequestMethod.POST)
    public void repayDetail(@RequestParam Double amount, @RequestParam Integer timeLimit) {

        RepayShowModel repayPlan = indexService.getRepayPlan(amount, timeLimit);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, JSON.toJSON(repayPlan));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 首页轮播信息(手机尾号与借款金额)
     *
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/borrow/listIndexWithPhone.htm", method = RequestMethod.POST)
    public void listIndexWithPhone() {
        List<IndexModel> list = indexService.listIndexWithPhone();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, JSON.toJSON(list));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 用户可借额度评估
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/assessment.htm", method = RequestMethod.POST)
    public void getMaxBorrowAmount(@RequestParam String userId) {
        Map<String, Object> map = clBorrowService.assessment(userId);
        Map<String, Object> result = new HashMap<>(16);
        result.put(Constant.RESPONSE_DATA, map);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    /**
     * 首页点击下单,借款确认界面接口
     *
     * @param userId    用户id
     * @param maxAmount 用户最大可借金额
     * @param sceneType 借款场景
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/confirm.htm", method = RequestMethod.POST)
    public void confirmBorrow(@RequestParam Long userId, @RequestParam Double maxAmount,Integer sceneType) {
        BorrowConfirmModel confirmBorrow = indexService.getConfirmBorrow(userId, maxAmount);
        confirmBorrow.setSceneType(sceneType);
        Map<String, Object> result = new HashMap<>(16);
        result.put(Constant.RESPONSE_DATA, JSON.toJSON(confirmBorrow));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }


}
