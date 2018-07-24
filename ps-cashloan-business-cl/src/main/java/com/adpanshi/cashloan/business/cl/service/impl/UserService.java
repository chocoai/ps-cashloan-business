package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.Channel;
import com.adpanshi.cashloan.business.cl.domain.ProfitAmount;
import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.domain.UserInvite;
import com.adpanshi.cashloan.business.cl.mapper.ProfitAmountMapper;
import com.adpanshi.cashloan.business.cl.mapper.UserInviteMapper;
import com.adpanshi.cashloan.business.cl.model.SmsModel;
import com.adpanshi.cashloan.business.cl.model.user.AppSessionBean;
import com.adpanshi.cashloan.business.cl.service.*;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.common.util.RandomUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.core.model.CloanUserModel;
import com.adpanshi.cashloan.business.core.model.UserModel;
import com.adpanshi.cashloan.business.core.service.CloanUserService;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.mapper.CreditMapper;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tool.util.BeanUtil;
import tool.util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lsk on 2016/7/27.
 */
@Service("clUserService_")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserService {
	
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private AppSessionService appSessionService;

    @Resource
    protected SmsService smsService;
    
    @Resource
	private UserEquipmentInfoService userEquipmentInfoService;
    
	@Resource
	private UserAuthService userAuthService;

	@Resource
	private BankCardService bankCardService;
	
	@Resource
    UserDeviceTokensService userDeviceTokensService;
	
	@Resource
	UserMapper userMapper;
	
	@Resource
	UserBaseInfoMapper userBaseInfoMapper;
	
	@Resource
	CreditMapper creditMapper;
	
	@Resource
	ProfitAmountMapper profitAmountMapper;
	
	@Resource
	UserInviteMapper userInviteMapper;


    public UserService() {
        super();
    }
    
    public UserModel getUserByid() {
		
    	return null;
	}
    
    public CloanUserModel getUserByid(Long userId) {
    	
    	return userMapper.getModel(userId);
	}
    
    
    /**
     * @Title: updateBlackBox
     * @Description: 更新用户设备指纹
     * @return Map
     * @throws
     */
    public Map updateBlackBox(Long userId, String blackBox){
		Map ret = new LinkedHashMap();
        CloanUserModel userByid = getUserByid(userId);
        if(null != userByid){
        	userEquipmentInfoService.save(userByid.getLoginName(),blackBox);
        	ret.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        	ret.put(Constant.RESPONSE_CODE_MSG, "设备指纹更新成功");
        }else{
        	ret.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        	ret.put(Constant.RESPONSE_CODE_MSG, "用户不存在");
        }
        return ret;
	
    }

	@Transactional
    public Map registerUser(HttpServletRequest request, String firstName, String lastName, String middleName, String phone, String email, String pwd, String invitationCode,
                            String registerCoordinate,String registerAddr,String regClient, String channelCode) {
        try {
            String msg = null;
            if(StringUtil.isBlank(lastName)){
                msg = "Last name is not null.";
            }else if(StringUtil.isBlank(firstName)){
                msg = "First name is not null.";
            }else if(!StringUtil.isPhone(phone)){
                msg = "Phone was entered incorrectly.";
            }else if(!StringUtil.isMail(email)){
                msg = "E-mail was entered incorrectly.";
            }else if(StringUtil.isNotEmpty(email) && !StringUtil.isMail(email)){
                msg = "E-mail was entered incorrectly.";
            }else if(StringUtil.isBlank(pwd)){
                msg = "Password is not null.";
            }else if(pwd.length() < 32){
                msg = "Password was entered incorrectly.";
            }

            if (StringUtil.isNotEmpty(msg) ) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", msg);
                logger.error(ret.toString());
                return ret;
            }
            
            CloanUserService cloanUserService = (CloanUserService) BeanUtil.getBean("cloanUserService");
            long todayCount = cloanUserService.todayCount();
            String dayRegisterMax_ = Global.getValue("day_register_max");
            if(StringUtil.isNotBlank(dayRegisterMax_)){
            	int dayRegisterMax = Integer.parseInt(dayRegisterMax_);
            	if(dayRegisterMax > 0 && todayCount >= dayRegisterMax){
            		 Map ret = new LinkedHashMap();
                     ret.put("success", false);
                     ret.put("msg", "今日注册用户数已达上限，请明日再来");
                    logger.error(ret.toString());
                     return ret;
            	}
            }

            User invitor = null;
            if (!StringUtil.isEmpty(invitationCode)) {
                invitor =userMapper.queryUserByInvitation(invitationCode);
                if (invitor == null) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", "邀请人不存在");
                    logger.error(ret.toString());
                    return ret;
                }
            }
            if(StringUtil.isPhone(phone)){
                User old = userMapper.getUserByPhone(phone);
                if (old != null) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", MessageConstant.PHONE_REGISTERED);
                    logger.error(ret.toString());
                    return ret;
                }
            }
            if(StringUtil.isMail(email)) {
                User old = userMapper.getUserByEmail(email);
                if (old != null) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", MessageConstant.EMAIL_REGISTERED);
                    logger.error(ret.toString());
                    return ret;
                }
            }

            // 渠道
            long channelId = 0;
            if(StringUtil.isNotBlank(channelCode)){
            	ChannelService channelService = (ChannelService) BeanUtil.getBean("channelService");
            	Channel channel = channelService.findByCode(channelCode);
            	 if (channel != null) {
            		 channelId=channel.getId();
                 }
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            //把数据放入cl_user（用户账户信息表）
            User saveUser=new User();
            saveUser.setLoginName(phone);
            saveUser.setLoginNameEmail(email);
            saveUser.setLoginPwd(pwd);
            saveUser.setInvitationCode(randomInvitationCode(6));
            saveUser.setRegistTime(new Date());
            saveUser.setUuid(uuid);
            saveUser.setLevel(3);
            saveUser.setRegisterClient(regClient);
            saveUser.setChannelId(channelId);
            //默认将其设置成有效数据
            saveUser.setState(1);
            userMapper.save(saveUser);
            long userId = saveUser.getId();
            logger.info("注册信息插入cl_user表成功，userId="+userId);

            //把数据放入cl_user_base_info（用户详情表）
            UserBaseInfo saveUserBaseInfo=new UserBaseInfo();
            saveUserBaseInfo.setUserId(userId);
            saveUserBaseInfo.setFirstName(firstName);
            if(StringUtil.isNotBlank(middleName)){
                saveUserBaseInfo.setMiddleName(middleName.trim());
            }
            saveUserBaseInfo.setLastName(lastName);
            saveUserBaseInfo.setRealName(StringUtil.jointName(firstName,middleName,lastName));
            saveUserBaseInfo.setPhone(phone);
            saveUserBaseInfo.setRegisterCoordinate(registerCoordinate);
            saveUserBaseInfo.setRegisterAddr(registerAddr);
            saveUserBaseInfo.setState("20");
            userBaseInfoMapper.save(saveUserBaseInfo);
            logger.info("注册信息插入cl_user_base_info表成功");

            //初始化arc_credit（授信额度表）
            Credit saveCredit=new Credit();
            saveCredit.setConsumerNo(String.valueOf(userId));
            saveCredit.setTotal(0d);
            saveCredit.setUnuse(0d);
            saveCredit.setState("10");
            saveCredit.setGrade("0");
            saveCredit.setUsed(0.00);
            creditMapper.save(saveCredit);
            logger.info("初始化arc_credit（授信额度表）成功");

            //初始化cl_profit_amount（分润资金表）
            ProfitAmount saveProfitAmount=new ProfitAmount();
            saveProfitAmount.setUserId(userId);
            saveProfitAmount.setState("10");
            saveProfitAmount.setTotal(0d);
            profitAmountMapper.save(saveProfitAmount);
            logger.info("初始化cl_profit_amount（分润资金表）成功");

            //为了兼容升级前的版本，当header中xiaoe这个字段时使用新的逻辑
            String newVersion = request.getHeader("xiaoe");
            //初始化cl_user_auth（用户认证状态表）
            String defaultState="10";
            UserAuth saveUserAuth=new UserAuth(defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState, defaultState);
            saveUserAuth.setUserId(userId);
            userAuthService.insert(saveUserAuth);
            logger.info("初始化cl_user_auth（用户认证状态表）成功");

            if (invitor != null) {
                //初始化cl_user_invite（邀请记录表）
            	UserInvite saveUserInvite=new UserInvite();
            	saveUserInvite.setInviteTime(new Date());
            	saveUserInvite.setInviteId(userId);
            	saveUserInvite.setInviteName(phone);
            	saveUserInvite.setUserId(invitor.getId());
            	saveUserInvite.setUserName(invitor.getLoginName());
            	userInviteMapper.save(saveUserInvite);
                logger.info("初始化cl_user_invite（邀请记录表）成功");
            }
            //2017.5.6 仅用于demo演示环境
            //demoUser(userId);
            Map result = new LinkedHashMap();
            result.put("success", true);
            result.put("msg", "注册成功");
            result.put("userId", userId);
            logger.info("用户注册成功，userId="+userId);
            return result;
        } catch (Exception e) {
            logger.error("注册失败", e.getMessage());
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            ret.put("msg", "注册失败");
            logger.error(ret.toString());
            return ret;
        }
    }
	/**
	 * 给注册用户添加芝麻和银行卡演示数据
	 * @param userId
	 */
	private void demoUser(long userId){
        if(!StringUtil.isBlank(Global.getValue("app_register_demo"))&&Global.getValue("app_register_demo").equals("1")){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bankCardState", "30");
			map.put("zhimaState", "30");
			map.put("phoneState", "30");
			map.put("userId", userId);
			userAuthService.updateByUserId(map);
			BankCard card = new BankCard();
			card.setCardNo("6212261202001978888");
			card.setBindTime(DateUtil.getNow());
			card.setUserId(userId);
			card.setBank("中国农业银行");
			card.setAgreeNo(OrderNoUtil.getSerialNumber());
		    bankCardService.save(card);
		    
		}
        
	}
	
	
    private String randomInvitationCode(int len) {
        while (true) {
            String str = RandomUtil.randomNumAlph(len);
            if (userMapper.queryUserByInvitation(str) == null) {
                return str;
            }
        }
    }

    public Object forgetPwd(String phone, String email, String newPwd, String vcode,String signMsg) {
            if (!StringUtil.isEmpty(vcode)) {
                String msg = smsService.validateSmsCode(phone, email, SmsModel.SMS_TYPE_FINDREG , vcode);
                if (msg != null) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", msg);
                    logger.error(ret.toString());
                    return ret;
                }
            }
            int flg;
            if(StringUtil.isNotBlank(phone)){
                flg = userMapper.updateUserByLoginName(phone, newPwd);
            }else{
                flg = userMapper.updateUserByLoginName2(email, newPwd);
            }
            if (flg==1) {
                Map ret = new LinkedHashMap();
                ret.put("success", true);
                ret.put("msg", "密码已修改");
                logger.error(ret.toString());
                return ret;
            } else {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", MessageConstant.PASSWORD_FAILURE);
                return ret;
            }
    }

    public Map login(final HttpServletRequest request, final String loginName, final String loginPwd, String blackBox,Integer mobileType,String deviceTokens) {
        try {
        	User user;
        	if(StringUtil.isMail(loginName)){
        	    user = userMapper.getUserByEmail(loginName);
            }else{
                user=userMapper.getUserByPhone(loginName);
            }
            if (user == null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", MessageConstant.ACCOUNT_NOT_EXIST);
                return ret;
            }
            Integer state =user.getState();
            if (state == null){
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", MessageConstant.ACCOUNT_STATUS_ABNORMAL);
                return ret;
            }
            if (state != UserModel.USER_STATE_VALID){
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                if (state == UserModel.USER_STATE_UNVALID) {
                    ret.put("msg", MessageConstant.ACCOUNT_STATUS_INVALID);
                }else if (state == UserModel.USER_STATE_DELETED) {
                    ret.put("msg", MessageConstant.ACCOUNT_CANCELLED);
                }else{
                    ret.put("msg", MessageConstant.ACCOUNT_STATUS_ABNORMAL);
                }
                return ret;
            }
            String dbPwd =user.getLoginPwd();
            if (dbPwd.equalsIgnoreCase(loginPwd)) {
                AppSessionBean session = appSessionService.create(loginName,1);
                userEquipmentInfoService.save(loginName,blackBox);
                //生成-deviceTokens与用户绑定.  TODO 
                userDeviceTokensService.saveUserDeviceTokens(Long.parseLong(session.getUserId()), deviceTokens,mobileType);
                Map ret = new LinkedHashMap();
                ret.put("success", true);
                ret.put("msg", "登录成功");
                ret.put("data", session.getFront());
                return ret;
            }
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            ret.put("msg", MessageConstant.USER_NAME_OR_PASSWORD_ERROR);
            return ret;
        } catch (Exception e) {
            logger.error("登录异常", e);
            Map ret = new LinkedHashMap();
            ret.put("code", 500);
            ret.put("msg", MessageConstant.SYSTEM_EXCEPTIONS);
            return ret;
        }
    }
    
    /**
     * <p>设置交易密码</p>
     * */
    public boolean setUserPassword(Long userId){
    	User user = userMapper.findByPrimary(userId);
    	boolean isPwd = false;
    	if (user != null && StringUtils.isNotEmpty(user.getTradePwd())) {
    		isPwd = true;
    	}
    	return isPwd;
    }
}
