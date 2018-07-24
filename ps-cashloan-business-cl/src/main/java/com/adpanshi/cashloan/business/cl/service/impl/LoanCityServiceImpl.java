package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.constant.LoanCityConstant;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityLog;
import com.adpanshi.cashloan.business.cl.domain.loancity.LoanCityReqLog;
import com.adpanshi.cashloan.business.cl.enums.LoanTypeEnum;
import com.adpanshi.cashloan.business.cl.mapper.BorrowMainModelMapper;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.LoanCityReqLogMapper;
import com.adpanshi.cashloan.business.cl.model.loancity.BaseLoanCityModel;
import com.adpanshi.cashloan.business.cl.model.loancity.LoanCityDemandModel;
import com.adpanshi.cashloan.business.cl.service.LoanCityService;
import com.adpanshi.cashloan.business.cl.util.loancity.PaymentKit;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.HttpUtil;
import com.adpanshi.cashloan.business.core.common.util.MapUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.rule.model.BorrowTemplateModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yecy
 * @date 2018/1/3 20:58
 */
@Service
public class LoanCityServiceImpl implements LoanCityService {

    @Autowired
    private LoanCityReqLogMapper loanCityReqLogMapper;
    @Autowired
    private LoanCityLogMapper loanCityLogMapper;
    @Autowired
    private BorrowMainMapper borrowMainMapper;
    @Autowired
    private BorrowMainModelMapper borrowMainModelMapper;

    private static final Logger logger = LoggerFactory.getLogger(LoanCityServiceImpl.class);
    // 去除小写字母l(因为容易与数字1混淆),大写字母O(因为容易与数字0混淆)
    private static final char[] value = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',     'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',     'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    @Override
    public void unPublish(Long borrowMainId, String mobile) {
        if (borrowMainId == null || mobile == null) {
            throw new NullPointerException();
        }
        logger.info("【推送消贷同城】开始推送未发布需求直接借款的用户。borrowMainId:{}", borrowMainId);
        String urlPath = BaseLoanCityModel.LoanCityServiceEnum.SKIPREQUIRENOTIFY.getCode();
        Map<String, String> data = new HashMap<>();
        //用户登录名就是用户的手机号码
        data.put("mobile", mobile);
        post(data, urlPath, borrowMainId);
    }

    @Override
    public void pushIdentifyCode(Long borrowMainId) {
        logger.info("【推送消贷同城】开始推送验证码。borrowMainId:{}", borrowMainId);
        Map<String, Object> map = new HashMap<>();
        map.put("borrowMainId", borrowMainId);
        LoanCityLog log = loanCityLogMapper.findSelective(map);
        if (log == null) {
            logger.error("借款订单不存在关联的需求。-----------------borrowMainId：{}");
            throw new BussinessException("借款订单不存在关联的需求。");
        }
        if (StringUtils.isEmpty(log.getIdentifyingCode())) {
            int codeLength = Global.getInt(LoanCityConstant.LOAN_CITY_CODE_LENGTH);
            String idCode = getIdCode(codeLength);
            log.setIdentifyingCode(idCode);
            int i = loanCityLogMapper.update(log);
            if (i == 0) {
                logger.error("消贷同城记录表中，更新验证码失败。-----------------borrowMainId：{}", borrowMainId);
                throw new BussinessException("消贷同城记录表中，更新验证码失败。");
            }
        }

        LoanCityDemandModel model = new LoanCityDemandModel();
        BeanUtils.copyProperties(log, model, "state");
        BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
        if (borrowMain == null) {
            logger.error("借款订单不存在。-----------------borrowMainId：{}", borrowMainId);
            throw new BussinessException("借款订单不存在。");
        }
        model.setAmount(borrowMain.getAmount().longValue());
        //目前只有消费分期，所以直接写死
        model.setBorrowType(LoanTypeEnum.RENT.getCode());
        //借款以天计算，不考虑周，所以直接写死
        model.setPeriodType(BaseLoanCityModel.periodType.DAY.getCode());

        String templateInfo = borrowMain.getTemplateInfo();
        BorrowTemplateModel template = JSONObject.parseObject(templateInfo, BorrowTemplateModel.class);

        model.setBorrowPeriod(Integer.parseInt(template.getCycle()));
        //yearRate = fee / (amount * time_limit) * 360; 年化率以一年360天进行计算
        BigDecimal fee = new BigDecimal(template.getFee());
        BigDecimal amount = new BigDecimal(borrowMain.getAmount());
        BigDecimal timeLimit = new BigDecimal(borrowMain.getTimeLimit());
        BigDecimal yearDay = new BigDecimal(360);
        int scale = 10;
        int roundModel = BigDecimal.ROUND_HALF_UP;//四舍五入
        BigDecimal yearRate = fee.divide(amount, scale, roundModel).divide(timeLimit, scale, roundModel).multiply(yearDay);
        model.setYearRate(yearRate.doubleValue());

        Map<String, String> data;
        try {
            data = MapUtil.convertBean(model);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            logger.error("bean conversion to map had errors.", e);
            throw new BussinessException("Errors in bean conversion to map.");
        }

        String urlPath = BaseLoanCityModel.LoanCityServiceEnum.GENERATENOTIFY.getCode();
        Boolean success = post(data, urlPath, borrowMainId);
        if (success) {
            // 发送成功，更改记录状态为'未支付'
            log.setState(BaseLoanCityModel.LoanCityStateEnum.UNPAID.getCode());
            loanCityLogMapper.update(log);
        }
    }

    @Override
    public void auditFail(Long borrowMainId) {
        logger.info("【推送消贷同城】开始推送审核不通过的订单。borrowMainId:{}", borrowMainId);
        Map<String, Object> map = new HashMap<>();
        map.put("borrowMainId", borrowMainId);
        LoanCityLog log = loanCityLogMapper.findSelective(map);
        if (log == null) {
            logger.info("借款订单未与需求相关联，可能是下单的时候开关未开启，所以直接返回。borrowMainId：{}", borrowMainId);
            return;
        }
        Map<String, String> data = new HashMap<>();
        data.put("requireNo", log.getRequireNo());

        String urlPath = BaseLoanCityModel.LoanCityServiceEnum.NOTPASSNOTIFY.getCode();
        Boolean success = post(data, urlPath, borrowMainId);
        if (success) {
            // 发送成功，更改记录状态为'未支付'
            log.setState(BaseLoanCityModel.LoanCityStateEnum.NOTPASS.getCode());
            loanCityLogMapper.update(log);
        }
    }

    private Boolean post(Map<String, String> data, String urlPath, Long borrowMainId) {
        String host = Global.getValue(LoanCityConstant.LOANCITY_HOST);
        if (StringUtils.isEmpty(host)) {
            logger.error("系统参数[{}]未配置，无法访问消贷同城。", LoanCityConstant.LOANCITY_HOST);
            throw new BussinessException("系统参数未配置，无法访问消贷同城。");
        }
        StringBuilder postUrl = new StringBuilder(host);
        if (!host.endsWith("/")) {
            postUrl.append("/");
        }
        postUrl.append(urlPath);

        String appid = Global.getValue(LoanCityConstant.LOANCITY_APPID);
        if (StringUtils.isEmpty(appid)) {
            logger.error("系统参数[{}]未配置，无法访问消贷同城。", LoanCityConstant.LOANCITY_APPID);
            throw new BussinessException("系统参数未配置，无法访问消贷同城。");
        }
        data.put("appid", appid);

        String secretKey = Global.getValue(LoanCityConstant.LOANCITY_PRIVATE_KEY);
        if (StringUtils.isEmpty(secretKey)) {
            logger.error("系统参数[{}]未配置，无法访问消贷同城。", LoanCityConstant.LOANCITY_PRIVATE_KEY);
            throw new BussinessException("系统参数未配置，无法访问消贷同城。");
        }
        String sign = PaymentKit.createSignWithSHA256(data, secretKey);
        data.put("sign", sign);

        LoanCityReqLog reqLog = new LoanCityReqLog();
        reqLog.setBorrowMainId(borrowMainId);
        reqLog.setParams(JSON.toJSONString(data));
        reqLog.setCreateTime(new Date());

        int maxRetries = Global.getInt(LoanCityConstant.LOANCITY_MAX_RETRIES);
        int retryInterval = Global.getInt(LoanCityConstant.LOANCITY_RETRY_INTERVAL);
        CloseableHttpClient client = HttpUtil.getRetryClient(maxRetries, retryInterval);
        String resp = HttpUtil.postClient(client, postUrl.toString(), data);
        JSONObject json = JSON.parseObject(resp);

        reqLog.setService(urlPath);
        reqLog.setRespParams(resp);
        reqLog.setRespTime(new Date());

        String resCode = json.getString(LoanCityConstant.RES_CODE);
        Boolean success = LoanCityConstant.RES_CODE_SUCCESS.equals(resCode);
        reqLog.setSuccess(success);
        loanCityReqLogMapper.save(reqLog);

        return success;
    }

    private String getIdCode(Integer size) {
        Random random = new Random();
        char[] c = new char[size];
        //绘制字符
        for (int i = 0; i < size; i++) {
            c[i] = value[random.nextInt(value.length)];
        }
        return String.valueOf(c);
    }

}
