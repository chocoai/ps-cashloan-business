package com.adpanshi.cashloan.business.cl.service.impl;

import cn.jpush.api.push.model.Platform;
import com.adpanshi.cashloan.business.cl.domain.AppMsgTpl;
import com.adpanshi.cashloan.business.cl.domain.UserDeviceTokens;
import com.adpanshi.cashloan.business.cl.enums.AppMsgTpEnuml;
import com.adpanshi.cashloan.business.cl.enums.AppMsgTpEnuml.FIELD;
import com.adpanshi.cashloan.business.cl.enums.MobileTypeEnum;
import com.adpanshi.cashloan.business.cl.enums.UserDeviceTokensStateEnum;
import com.adpanshi.cashloan.business.cl.service.AppMsgService;
import com.adpanshi.cashloan.business.cl.service.AppMsgTplService;
import com.adpanshi.cashloan.business.cl.service.UserDeviceTokensService;
import com.adpanshi.cashloan.business.core.jiguang.PushApi;
import com.adpanshi.cashloan.business.core.umeng.beans.Extra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("appMsgService")
public class AppMsgServiceImpl implements AppMsgService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    UserDeviceTokensService userDeviceTokensService;

    @Resource
    AppMsgTplService appMsgTplService;

    @Override
    public int loanInform(long userId, String time) {
        Boolean flag = Boolean.FALSE;
        try {
            Extra extra = new Extra();
            extra.put(FIELD.TIME.getCode(), time);
            flag = JPushMsg(userId, AppMsgTpEnuml.TYPE.LOAN_INFORM, extra);
        } catch (Exception e) {
            logger.error("", e);
        }
        return flag ? 1 : 0;
    }

    @Override
    public int repayInform(long userId, String time, String loan) {
        Boolean flag = Boolean.FALSE;
        try {
            Extra extra = new Extra();
            extra.put(FIELD.TIME.getCode(), time);
            extra.put(FIELD.LOAN.getCode(), loan);
            flag = JPushMsg(userId, AppMsgTpEnuml.TYPE.REPAY_INFORM, extra);
        } catch (Exception e) {
            logger.error("", e);
        }
        return flag ? 1 : 0;
    }

    @Override
    public int overdue(long userId, String name, String phone) {
        Boolean flag = Boolean.FALSE;
        try {
            Extra extra = new Extra();
            extra.put(FIELD.NAME.getCode(), name);
            extra.put(FIELD.PHONE.getCode(), phone);
            flag = JPushMsg(userId, AppMsgTpEnuml.TYPE.OVERDUE, extra);
        } catch (Exception e) {
            logger.error("", e);
        }
        return flag ? 1 : 0;
    }


    @Override
    public int creditsUpgrade(Long userId, String credits, String validPeriod) {
        Boolean flag = Boolean.FALSE;
        try {
            Extra extra = new Extra();
            extra.put(FIELD.CREDITS.getCode(), credits);
            extra.put(FIELD.VALID_PERIOD.getCode(), validPeriod);
            flag = JPushMsg(userId, AppMsgTpEnuml.TYPE.CREDITS_UPGRADE, extra);
        } catch (Exception e) {
            logger.error("", e);
        }
        return flag ? 1 : 0;
    }

    /***********************  private method *************************/

    /**
     * <p>推送消息</p>
     *
     * @param userId 用户id
     * @param type   模板类型
     * @param extra  提示消息
     * @return boolean
     */
    protected boolean JPushMsg(Long userId, AppMsgTpEnuml.TYPE type, Extra extra) {
        Boolean flag = Boolean.FALSE;
        UserDeviceTokens device = getUserDeviceTokens(userId);
        if (null == device) {
            logger.error("------------->UserDeviceTokens中未找到userid={}的记录,停止对用户推送通知.", new Object[]{userId});
            return flag;
        }
        //找到对应的模板
        AppMsgTpl appmsgTpl = getAppMsgTpl(type.getCode());
        if (null == appmsgTpl) {
            logger.error("------------->AppMsgTpl中未找到type={},state={}的记录,停止对用户推送通知.", new Object[]{type.getCode(), AppMsgTpEnuml.STATE.ENABLE.getCode()});
            return flag;
        }
        logger.info("--------->" + type.getName() + "消息开始推送,id={},userId={},deviceTokens={}.", new Object[]{device.getId(), userId, device.getDeviceTokens()});
        flag = JPushMsg(appmsgTpl, device, extra, type);
        logger.info("--------->" + type.getName() + "消息推送{},id={},userId={},deviceTokens={}.", new Object[]{flag ? "成功" : "失败.", device.getId(), userId, device.getDeviceTokens()});
        return flag;
    }

    /**
     * <p>推送消息</p>
     *
     * @param appmsgTpl 模板对象
     * @param device    设备对象
     * @param extra     提示消息
     * @param type      类型描述
     * @return boolean
     */
    private boolean JPushMsg(AppMsgTpl appmsgTpl, UserDeviceTokens device, Extra extra, AppMsgTpEnuml.TYPE type) {
        logger.info("-------->deviceTokens={}", new Object[]{device.getDeviceTokens()});
        Boolean flag = Boolean.FALSE;
        //推送内容
        String content = resetBycontent(appmsgTpl.getContent(), extra);
        //推送平台
        Platform platform;
        if (MobileTypeEnum.IOS.getCode().equals(device.getMobileType())) {
            platform = Platform.ios();
        } else if (MobileTypeEnum.ANDROID.getCode().equals(device.getMobileType())) {
            platform = Platform.android();
        } else if (MobileTypeEnum.WINPHONE.getCode().equals(device.getMobileType())) {
            platform = Platform.winphone();
        } else {
            logger.error("------------->用户设备安装的系统类型[mobileType={}]，未识别,停止对用户推送通知.", new Object[]{device.getMobileType()});
            return flag;
        }
        //设备标识
        List<String> deviceTokens = new LinkedList<>();
        deviceTokens.add(device.getDeviceTokens());
        //推送
        PushApi pushApi = new PushApi();
        Map<String, Object>resultMap= pushApi.sendPushByRegistrationId(type.getName(), content, deviceTokens, platform);
        return Boolean.valueOf(String.valueOf(resultMap.get("success")));
    }

    /**
     * <p>根据给定条件查找最近一次登陆的设备</p>
     *
     * @param userId
     * @return {@link UserDeviceTokens}
     */
    protected UserDeviceTokens getUserDeviceTokens(long userId) {
        return userDeviceTokensService.getLastTimeByUserIdWithState(userId, UserDeviceTokensStateEnum.ENABLE.getCode());
    }

    /**
     * <p>根据给定type查找模板</p>
     *
     * @param type
     * @return AppMsgTpl
     */
    protected AppMsgTpl getAppMsgTpl(Integer type) {
        Integer state = AppMsgTpEnuml.STATE.ENABLE.getCode();
        return appMsgTplService.getByTypeWithState(type, state);
    }

    /**
     * <p>替换模板中的占位符</p>
     *
     * @param content 待处理的数据
     * @param extra   要替换的字段及值
     * @return 重置后的数据
     */
    protected String resetBycontent(String content, Extra extra) {
        if (null == content || null == extra) return content;
        Iterator<String> iterator = extra.keySet().iterator();
        while (iterator.hasNext()) {
            String code = iterator.next();
            if (content.indexOf(code) != -1) {
                content = content.replace(code, extra.get(code) + "");
            }
        }
        return content;
    }

}
