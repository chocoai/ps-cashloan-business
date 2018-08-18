package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.jms.task.domain.JmsActiveTaskDomain;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.domain.Sms;
import com.adpanshi.cashloan.cl.domain.SmsTpl;
import com.adpanshi.cashloan.cl.domain.UrgeRepayOrder;
import com.adpanshi.cashloan.cl.mapper.*;
import com.adpanshi.cashloan.cl.model.BorrowRepayModel;
import com.adpanshi.cashloan.cl.model.SmsModel;
import com.adpanshi.cashloan.cl.service.ClSmsService;
import com.adpanshi.cashloan.core.chuanglan.SmsApi;
import com.adpanshi.cashloan.core.chuanglan.enums.SmsEnum;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.DateUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.core.mapper.UserMapper;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.rule.mapper.ClBorrowMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 短信服务
 * @author
 */
@Service("clSmsService")
public class ClSmsServiceImpl extends BaseServiceImpl<Sms, Long> implements ClSmsService {

	public static final Logger logger = LoggerFactory.getLogger(ClSmsServiceImpl.class);

	@Resource
	private SmsMapper smsMapper;
	@Resource
	private SmsTplMapper smsTplMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private BorrowRepayMapper borrowRepayMapper;
	@Resource
	private ClBorrowMapper clBorrowMapper;
	@Resource
	private UrgeRepayOrderMapper urgeRepayOrderMapper;
	@Resource
	private UserBaseInfoService userBaseInfoService;
	@Resource
	private BorrowMainMapper borrowMainMapper;
	@Resource
	private JmsActiveTaskDomain jmsActiveTaskDomain;


	@Override
	public BaseMapper<Sms, Long> getMapper() {
		return smsMapper;
	}

	@Override
	public long findTimeDifference(String phone, String type) {
		int countdown = Global.getInt("sms_countdown");
		Map<String,Object> data = new HashMap<>();
		if(StringUtil.isMail(phone)){
			data.put("email", phone);
		}else{
			data.put("phone", phone);
		}
		data.put("smsType", type);
		Sms sms = smsMapper.findTimeMsg(data);
		long times = 0;
		if (sms != null) {
			Date d1 = sms.getSendTime();
			Date d2 = DateUtil.getNow();
			long diff = d2.getTime() - d1.getTime();
			if (diff < countdown*1000) {
				times = countdown-(diff/1000);
			}else {
				times = 0;
			}
		}
		return times;
	}

	@Override
	public int countDayTime(String phone, String email, String type) {
		String mostTimes = Global.getValue("sms_day_most_times");
		int mostTime = JSONObject.parseObject(mostTimes).getIntValue(type);

		Map<String,Object> data = new HashMap<>();
		if(StringUtil.isNotBlank(phone)){
			data.put("phone", phone);
		}else{
			data.put("email",email);
		}
		data.put("smsType", type);
		int times = smsMapper.countDayTime(data);

		return mostTime - times;
	}

	@Override
	public long sendSms(String phone, String type) {
		Map<String, Object> search = new HashMap<>();
		search.put("type", type);
		search.put("state", "10");
		SmsTpl tpl = smsTplMapper.findSelective(search);
		if (tpl!=null) {
			Map<String, Object> payload = new HashMap<>();
			int vcode = (int) (Math.random() * 9000) + 1000;
			Map<String,Object> map = new HashMap<>();
			map.put("vcode",vcode);
			map.put("timelimit",Global.getValue("sms_time_limit"));
			payload.put("mobile", phone);
			payload.put("message", changeMessage(type,map));

			return sendSmsByType(tpl.getNumber(),payload,phone,type,String.valueOf(vcode));
		}
		return 0;
	}

	@Override
	public long sendEmailSms(String email, String type) {
		Map<String, Object> searchEmail = new HashMap<>(16);
		searchEmail.put("type", type);
		searchEmail.put("state", "10");
		SmsTpl tpl = smsTplMapper.findSelective(searchEmail);
		if (tpl==null) {
			return 0;
		}
		Map<String, Object> payload = new HashMap<>(16);
		int vCode = (int) (Math.random() * 9000) + 1000;
		Map<String,Object> map = new HashMap<>(16);
		map.put("vcode",vCode);
		map.put("timelimit",Global.getValue("sms_time_limit"));
		payload.put("email", email);
		payload.put("message", changeMessage(type,map).replace("{$product}",Global.getValue("title")));

		return sendSmsEmailByType(tpl.getNumber(),payload,email,type,String.valueOf(vCode));
	}

	@Override
	public int smsBatch(String id) {
		logger.info("[逾期短信批量推送][ids:"+id+"]");
		final long[] ids = StringUtil.toLongs(id.split(","));
		new Thread(){
			public void run() {
				Map<String, Object> search = new HashMap<>();
				search.put("type", "overdue");
				search.put("state", "10");
				String smsNo = smsTplMapper.findSelective(search).getNumber();
				if (smsNo!=null) {
					for (int i = 0; i < ids.length; i++) {
						try {
							logger.debug(ids[i]+"---");
							UrgeRepayOrder uro = urgeRepayOrderMapper.findByPrimary(ids[i]);
							Map<String,Object> map = new HashMap<>();
							map.put("phone", uro.getPhone().subSequence(7, 11));
							map.put("realName", uro.getBorrowName());
							Map<String, Object> payload = new HashMap<>();
							payload.put("mobile", uro.getPhone());
							payload.put("message", changeMessage("overdue",map));
							//TODO 通过手机号，查找userID>>>devics
							User user=userMapper.getUserByPhone(uro.getPhone());
							sendSmsByType(smsNo, payload, uro.getPhone(),"overdue","");
						} catch (Exception e) {
							logger.error("",e);
							//如果这里不try catch,如果批量发送时，如果其中有一条短信发送失败，那么后面的也就没法玩了!
							continue;
						}
					}
				}else {
					return;
				}
			}
		}.start();
		return 1;
	}


	@Override
	public int verifySms(String phone,  String email, String type, String code) {
		if ("dev".equals(Global.getValue("app_environment")) && "0000".equals(code)) {
			return 1;
		}
		if(StringUtil.isBlank(phone)){
			if(StringUtil.isBlank(email)){
				logger.error("--------->短信验证参数不完整...");
				return 0;
			}else if(!StringUtil.isMail(email)){
				logger.error("---->email={}，不是一个正确的邮箱。",new Object[]{email});
				return 0;
			}
			logger.info("------------>短信验证:email={},type={},code={}.",new Object[]{ email,type,code});
		}else{
//            if (!StringUtil.isPhone(phone)) {
//                logger.error("---->phone={}，不是一个正确的手机号。",new Object[]{phone});
//                return 0;
//            }
			logger.info("------------>短信验证:phone={},type={},code={}.",new Object[]{ phone,type,code});
		}
		if(StringUtil.isBlank(type) || StringUtil.isBlank(code)){
			logger.error("--------->短信验证参数不完整...");
			return 0;
		}
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("phone", phone);
		data.put("email",email);
		data.put("smsType", type);
		Sms sms = smsMapper.findTimeMsg(data);
		if (sms != null) {
			String mostTimes = Global.getValue("sms_day_most_times");
			int mostTime = JSONObject.parseObject(mostTimes).getIntValue("verifyTime");
			data = new HashMap<>();
			data.put("verifyTime", sms.getVerifyTime()+1);
			data.put("id", sms.getId());
			smsMapper.updateSelective(data);
			if (StringUtil.equals("20", sms.getState())||sms.getVerifyTime()>mostTime) {
				logger.info("---------->短信验证:sms.getState={},sms.getVerifyTime={},mostTime={}.",new Object[]{sms.getState(),sms.getVerifyTime(),mostTime});
				return 0;
			}
			String sms_time_limit = Global.getValue("sms_time_limit");
			long timeLimit = Long.parseLong(sms_time_limit);
			Date d1 = sms.getSendTime();
			Date d2 = DateUtil.getNow();
			long diff = d2.getTime() - d1.getTime();
			if (diff > timeLimit * 60 * 1000) {
				return -1;
			}
			if (sms.getCode().equals(code)) {
				Map<String,Object> map = new HashMap<>();
				map.put("id", sms.getId());
				map.put("state", "20");
				smsMapper.updateSelective(map);
				return 1;
			}
		}
		logger.info("------------>短信验证:未找到记录data={}.",new Object[]{JSONObject.toJSONString(data)});
		return 0;
	}

	protected String changeMessage(String smsType, Map<String,Object> map) {
		String message = "";
		String[] date;
		String time = "";
		if(map.get("time")!=null){
			date = map.get("time").toString().split(" ");
			time = date[1]+" "+date[2]+" "+date[5];
		}
		if ("overdue".equals(smsType)) {
			message = ret(smsType);
			message = message.replace("{$day}", StringUtil.isNull(map.get("day")));
//			message = message.replace("{$phone}",StringUtil.isNull(map.get("phone")));
		}
		if ("loanInform".equals(smsType)) {
			message = ret(smsType);
			message = message.replace("{$time}",time);
		}
		if ("repayInform".equals(smsType)) {
			message = ret(smsType);
			message = message.replace("{$time}",time);
			message = message.replace("{$loan}", StringUtil.isNull(map.get("loan")));
		}
		if ("repayBefore".equals(smsType)) {
			message = ret(smsType);
//			message = message.replace("{$name}", StringUtil.isNull(map.get("realName")));
			message = message.replace("{$time}",time);
			message = message.replace("{$loan}", StringUtil.isNull(map.get("loan")));
		}
		if ("refuse".equals(smsType)) {
			message = ret(smsType);
			message = message.replace("{$day}", StringUtil.isNull(map.get("day")));
		}
		if ("activePayment".equals(smsType)) {
			message = ret(smsType);
			message = message.replace("{$cardNo}", StringUtil.isNull(map.get("cardNo")));
			message = message.replace("{$time}",time);
			message = message.replace("{$loan}", StringUtil.isNull(map.get("loan")));
			message = message.replace("{$bank}", StringUtil.isNull(map.get("bank")));
		}
		if ("payment".equals(smsType)) {
			message = ret(smsType);
//			message = message.replace("{$orderNo}", StringUtil.isNull(map.get("orderNo")));
			message = message.replace("{$time}",time);
			message = message.replace("{$loan}", StringUtil.isNull(map.get("loan")));
		}
		if("creditsUpgrade".equals(smsType)){
			message = ret(smsType);
			message = message.replace("{$credits}", StringUtil.isNull(map.get("credits")));
			message = message.replace("{$validPeriod}", StringUtil.isNull(map.get("validPeriod")));
		}
		if("register".equals(smsType)||"findReg".equals(smsType)||"bindCard".equals(smsType)||"verifyCode".equals(smsType)){
			message = ret(smsType);
			message = message.replace("{$vcode}", StringUtil.isNull(map.get("vcode")));
			message = message.replace("{$timelimit}", StringUtil.isNull(map.get("timelimit")));
		}
		logger.info("短信发送内容:" + message);
		return message;
	}

	public String change(String code){
		String message = null;
		if ("register".equals(code)) {
			message = ret("register");
		}else if ("findReg".equals(code)) {
			message = ret("findReg");
		}else if ("bindCard".equals(code)) {
			message = ret("bindCard");
		}else if ("findPay".equals(code)) {
			message = ret("findPay");
		}else if ("overdue".equals(code)) {
			message = ret("overdue");
		}else if ("loanInform".equals(code)) {
			message = ret("loanInform");
		}else if ("repayInform".equals(code)) {
			message = ret("repayInform");
		}else if ("repayBefore".equals(code)) {
			message = ret("repayBefore");
		}else if ("refuse".equals(code)) {
			message = ret("refuse");
		}else if("activePayment".equals(code)){
			message = ret("activePayment");
		}else if("creditsUpgrade".equals(code)){
			message = ret("creditsUpgrade");
		}
		return message;
	}

	public String ret(String type){
		Map<String, Object> search = new HashMap<>();
		search.put("type", type);
		search.put("state", "10");
		SmsTpl tpl = smsTplMapper.findSelective(search);
		return tpl.getTpl();
	}

	@Override
	public int findUser(String phone) {
		return findUser(phone,"phone");
	}

	@Override
	public int findUser(String key, String type) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String keyPhone = "phone";
		String keyEmail = "email";
		if(StringUtil.equals(keyPhone,type)){
			paramMap.put("loginName", key);
		}else if(StringUtil.equals(keyEmail,type)){
			paramMap.put("loginNameEmail", key);
		}
		User user =  userMapper.findSelective(paramMap);
		if (StringUtil.isNotBlank(user)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int loanInform(long userId, long borrowId) {
		Map<String, Object> search = new HashMap<String, Object>();
		User user = userMapper.findByPrimary(userId);
		search.put("borrowId", borrowId);
		Borrow br = clBorrowMapper.findByPrimary(borrowId);
		if (user != null && br != null) {
			Map<String, Object> searchTpl = new HashMap<String, Object>();
			searchTpl.put("type", "loanInform");
			searchTpl.put("state", "10");
			SmsTpl tpl = smsTplMapper.findSelective(searchTpl);
			if (tpl != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("time", br.getCreateTime());
				Map<String, Object> payload = new HashMap<String, Object>();
				payload.put("mobile", user.getLoginName());
				payload.put("message", changeMessage("loanInform", map));
				return sendSmsByType(tpl.getNumber(),payload,user.getLoginName(),"loanInform","");

			}
		}
		return 0;
	}

	@Override
	public int repayInform(long userId,long borrowId) {
		Map<String, Object> search = new HashMap<>();
		User user = userMapper.findByPrimary(userId);
		search.put("borrowId", borrowId);
		Borrow borrow = clBorrowMapper.findByPrimary(borrowId);
		//增加分期功能，所以代扣时的‘日期’和‘借款金额’应为主借款订单创建时间 @author yecy 20171226
		Long mainId = borrow.getBorrowMainId();
		// 因为cl_borrow_main表是后期添加的，所以cl_borrow表中之前的数据，没有main_id,这些数据只有单期，且id与main表中的id相同，所以直接用id操作
		if (mainId == null || mainId.equals(0L)) {
			mainId = borrow.getId();
		}
		BorrowMain br = borrowMainMapper.findById(mainId);
		if (user!=null&&br!=null) {
			Map<String, Object> searchTpl = new HashMap<String, Object>();
			searchTpl.put("type", "repayInform");
			searchTpl.put("state", "10");
			SmsTpl tpl = smsTplMapper.findSelective(searchTpl);
			if (tpl!=null) {
				Map<String, Object>  map = new HashMap<>();
				map.put("time", br.getCreateTime());
				map.put("loan", br.getAmount());
				Map<String, Object> payload = new HashMap<>();
				payload.put("mobile", user.getLoginName());
				payload.put("message", changeMessage("repayInform",map));
				return sendSmsByType(tpl.getNumber(),payload,user.getLoginName(),"repayInform","");
			}
		}
		return 0;
	}

	@Override
	public int overdue(long borrowId,long userId){
		Map<String, Object> search = new HashMap<>();
		search.put("type", "overdue");
		search.put("state", "10");
		String smsNo = smsTplMapper.findSelective(search).getNumber();
		if (smsNo!=null) {
			BorrowRepayModel brm = borrowRepayMapper.findOverdue(borrowId);
			Map<String,Object> map = new HashMap<>();
//			map.put("phone", brm.getPhone().subSequence(7, 11));
//			map.put("realName", brm.getRealName());
			map.put("day",brm.getPenaltyDay());
			Map<String, Object> payload = new HashMap<>();
			payload.put("mobile", brm.getPhone());
			payload.put("message", changeMessage("overdue",map));
			payload.put("orderNo",brm.getOrderNo());
			User user=userMapper.getUserByPhone(brm.getPhone());
			if(null==user){
				logger.error("用户未查询到,停止对phone={}的用户推送通知.",new Object[]{brm.getPhone()});
			}else{
//				appMsgService.overdue(user.getId(), brm.getRealName(), brm.getPhone().subSequence(7, 11)+"");
			}
			return sendSmsByType(smsNo,payload,brm.getPhone(),"overdue","");
		}else {
			return 0;
		}
	}

	@Override
	public int repayBefore(long userId, long borrowId) {
		Map<String, Object> search = new HashMap<>();
		search.put("type", "repayBefore");
		search.put("state", "10");
		String smsNo = smsTplMapper.findSelective(search).getNumber();
		if (smsNo!=null) {
			BorrowRepayModel brm = borrowRepayMapper.findOverdue(borrowId);
			Map<String,Object> map = new HashMap<>();
//			map.put("realName", brm.getRealName());
			map.put("time", brm.getRepayTime());
//			map.put("phone", brm.getPhone());
			map.put("loan",brm.getAmount());
			Map<String, Object> payload = new HashMap<>();
			payload.put("mobile", brm.getPhone());
			payload.put("orderNo", brm.getOrderNo());
			payload.put("message", changeMessage("repayBefore",map));

			User user=userMapper.getUserByPhone(brm.getPhone());
//			if(null==user){
//				logger.error("用户未查询到,停止对phone={}的用户推送通知.",new Object[]{brm.getPhone()});
//			}else{
//				appMsgService.repayBefore(user.getId(), brm.getRealName(), DateUtil.dateStr(brm.getRepayTime(),"M月dd日"), brm.getPhone().subSequence(7, 11)+"");
//			}
			return sendSmsByType(smsNo,payload,brm.getPhone(),"repayBefore","");
		}
		return 0;
	}

	@Override
	public int refuse(long userId, int day, String orderNo) {
//		if ("dev".equals(Global.getValue("app_environment"))) {
//			logger.info("ClSmsServiceImpl-refuse-测试环境-返回0");
//			return 0;
//		}

        try{
            Map<String, Object> search = new HashMap<>();
            search.put("type", "refuse");
            search.put("state", "10");
            String smsNo = smsTplMapper.findSelective(search).getNumber();
            if (smsNo!=null) {
                UserBaseInfo bi = userBaseInfoService.findByUserId(userId);
                Map<String,Object> map = new HashMap<>();
    //			map.put("realName", bi.getRealName());
                map.put("day",day);
                Map<String, Object> payload = new HashMap<>();
                payload.put("mobile", bi.getPhone());
                payload.put("orderNo", orderNo);
                payload.put("message", changeMessage("refuse",map));
    //			appMsgService.refuse(userId, bi.getRealName());
                return sendSmsByType(smsNo,payload,bi.getPhone(),"refuse","");
            }
        } catch (Exception e) {
            logger.error("生成消息异常，异常原因:",e);
        }
		return 0;
	}

	/**
	 * 立即还款成功
	 */
	@Override
	public int activePayment(long userId, long borrowMainId, Date settleTime, Double repayAmount, String repayOrderNo) {
		try {
			BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
			User user = userMapper.findByPrimary(userId);
			// 根据付款的订单号，推算出用户还款的期数
			logger.info("立即还款短信通知:userId={},borrowMainId={},settleTime={},repayAmount={},repayOrderNo={}",new Object[]{userId,borrowMainId, JSONObject.toJSONString(settleTime),repayAmount,repayOrderNo});
			String periods = getRepayPeriods(repayOrderNo);

			if (user != null && borrowMain != null) {
				Map<String, Object> searchTpl = new HashMap<String, Object>();
				searchTpl.put("type", "activePayment");
				searchTpl.put("state", "10");
				SmsTpl tpl = smsTplMapper.findSelective(searchTpl);
				if (tpl != null) {
//					String orderNo = borrowMain.getOrderNo();
					Map<String, Object> map = new HashMap<>();

					map.put("cardNo",borrowMain.getCardNo().substring(borrowMain.getCardNo().length()-4));
					map.put("time", settleTime);
					map.put("loan", repayAmount);
					map.put("bank",borrowMain.getBank());
//					map.put("periods", periods);
					Map<String, Object> payload = new HashMap<>();
					payload.put("orderNo", repayOrderNo);
					payload.put("mobile", user.getLoginName());
					payload.put("message", changeMessage("activePayment", map));
//					appMsgService.activePayment(userId, orderNo, DateUtil.dateStr(settleTime, "M月dd日"), repayAmount
//							.toString(),periods);
					return sendSmsByType(tpl.getNumber(), payload, user.getLoginName(), "activePayment", "");
				}
			}
		} catch (Exception e) {
			logger.error("立即还款成功，短信发送失败，异常原因:",e);
		}
		return 0;
	}

	/**
	 * 放款成功
	 */
	@Override
	public int payment(Long userId, Long borrowMainId, Date date, Double amount, String orderNo) {
		try {
			BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
			User user = userMapper.findByPrimary(userId);

			if (user != null && borrowMain != null) {
				Map<String, Object> searchTpl = new HashMap<String, Object>();
				searchTpl.put("type", "payment");
				searchTpl.put("state", "10");
				SmsTpl tpl = smsTplMapper.findSelective(searchTpl);
				if (tpl != null) {
					Map<String, Object> map = new HashMap<>();

					map.put("time", borrowMain.getRepayTime());
					map.put("loan", amount);
//					map.put("periods", periods);
					Map<String, Object> payload = new HashMap<>();
					payload.put("orderNo", orderNo);
					payload.put("mobile", user.getLoginName());
					payload.put("message", changeMessage("payment", map));
//					appMsgService.activePayment(userId, orderNo, DateUtil.dateStr(settleTime, "M月dd日"), repayAmount
//							.toString(),periods);
					return sendSmsByType(tpl.getNumber(), payload, user.getLoginName(), "payment", "");
				}
			}
		} catch (Exception e) {
			logger.error("立即还款成功，短信发送失败，异常原因:",e);
		}
		return 0;
	}

	@Override
	public List<Sms> getResendSmsList(Map<String, Object> map) {
		return smsMapper.findResendList(map);
	}

	@Override
	public void updateByIds(Map<String, Object> smsInfo) {
		smsMapper.updateByIds(smsInfo);
	}

	@Override
	public int sendMsg(Sms sms) {
		Map<String, Object> payload = new HashMap<>();
		payload.put("mobile",sms.getPhone());
		payload.put("message",sms.getContent());
		payload.put("orderNo",sms.getOrderNo());
		payload.put("smsPlatform",sms.getSmsPlatform());
		return sendSmsByType(null,payload,sms.getPhone(),sms.getSmsType(),sms.getCode());
	}

	@Override
	public void updateByMsgids(Map<String, Object> tyhSmsInfo) {
		smsMapper.updateByMsgids(tyhSmsInfo);
	}


	private int sendSmsByType(String smsNo, Map<String, Object> payload, String phone,String type,String vcode) {
		payload.put("vcode",vcode);
		payload.put("type",type);
		if(Global.getValue("app_environment").equals("dev")){
			SmsApi api = new SmsApi();
			return result(api.sendMsg(payload),phone,type);
		}else{
			payload.put("smsPlatform",Global.getValue("sms_platform"));
			payload.put("smsType",type);
			Integer retCode = jmsActiveTaskDomain.addSmsNotify(payload).getRet_code();
			if(retCode != 100){
				return 0;
			}
			return 1;
		}
	}

	private int sendSmsEmailByType(String smsNo, Map<String, Object> payload, String email,String type,String vcode) {
		return result(sendSmsTypeEmail(payload, vcode, email), email, type);
	}

	private String sendSmsTypeEmail(Map<String, Object> payload, String vCode, String email){
		// 用户名（必填）
		String emailMaster = Global.getValue("email_master");
		final JSONObject emailObject = JSONObject.parseObject(emailMaster);
		String host = (String)emailObject.get("smtp_host");
		String port = (String)emailObject.get("smtp_port");
		String auth = (String)emailObject.get("smtp_auth");
		String mailName = (String)emailObject.get("mail_add");
		String pwd = (String)emailObject.get("password");
		String subject = (String)emailObject.get("title");

		// 定时发送时间（可选）yyyyMMddHHmm
		String sendTime = "";
		// 短信内容（必填）
		String content = (String) payload.get("message");
		logger.info("[邮箱发送][发送邮箱:"+email+"][发送内容:"+content+"]");

		String sendhRes =null;

		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		//发送端口
		props.put("mail.smtp.port", port);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", auth);
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(mailName));
			// 加载收件人地址
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));
			// 加载标题
			message.setSubject(subject);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();

			//加上"text/html; charset=utf-8"，表示支持html格式的邮件
			contentPart.setContent(content, "text/html; charset=utf-8");


			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, mailName, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			sendhRes = "{\"result_code\":0,\"code\":\"0\",\"tempParame\":{\"code\":\""+vCode+"\"},\"res\":{\"result\":\""+content+"\"}}";
			JSONObject jsonObject = JSONObject.parseObject(sendhRes);
			if (jsonObject.getInteger("result_code")==0){
				jsonObject.put("content",content);
				jsonObject.put("vcode",vCode);
				sendhRes = jsonObject.toJSONString();
			}
		} catch (Exception e) {
			logger.error("应用异常", e.getMessage());
		}

		return sendhRes;
	}

	//新平台短信
	private int result(String result,String phoneOrEmail,String type){
		JSONObject resultJson = JSONObject.parseObject(result);
		logger.info("------------->result:{},phone:{},type={}",new Object[]{result,phoneOrEmail,type});
		if(null!=resultJson && !resultJson.isEmpty()){
			if ((resultJson.getInteger("code") != null && resultJson.getInteger("code") == 0)||
					(resultJson.getInteger("status") !=null && resultJson.getInteger("status") == 0)) {
				return resultToSmsType(resultJson, phoneOrEmail, type);
			}
		}
		return errorSms(resultJson, phoneOrEmail, type);
	}


	private int resultToSmsType(JSONObject resultJson, String phoneOrEmail, String type){
		Sms sms = new Sms();
		if(StringUtil.isMail(phoneOrEmail)){
			sms.setEmail(phoneOrEmail);
		}else{
			sms.setPhone(phoneOrEmail);
		}
		sms.setSendTime(DateUtil.getNow());
		sms.setContent(resultJson.getString("content"));
		sms.setResp("短信已发送");
		sms.setSmsType(type);
		sms.setCode(resultJson.getString("vcode"));
		sms.setOrderNo(resultJson.getString("orderNo"));
		if(resultJson.getString("msgid")!=null) {
			sms.setMsgid(resultJson.getString("msgid"));
		}
		sms.setState("10");
		sms.setSmsPlatform(resultJson.getString("smsPlatform"));
		sms.setVerifyTime(0);
		return smsMapper.save(sms);
	}

	private int errorSms(JSONObject resultJson, String phoneOrEmail, String type){
		Sms sms = new Sms();
		if(StringUtil.isMail(phoneOrEmail)){
			sms.setEmail(phoneOrEmail);
		}else {
			sms.setPhone(phoneOrEmail);
		}
		sms.setSendTime(DateUtil.getNow());
		String content;

		content = resultJson.getString("error");
		sms.setContent(resultJson.getString("content"));
		logger.error("[新短信平台发送异常][phone:"+phoneOrEmail+",异常信息:"+content+"]");
		sms.setSmsPlatform(resultJson.getString("smsPlatform"));
		sms.setRespTime(DateUtil.getNow());
		sms.setResp(content);
		sms.setSmsType(type);
		sms.setCode(resultJson.getString("vcode"));
		sms.setOrderNo(resultJson.getString("orderNo"));
		sms.setState("20");
		sms.setVerifyTime(0);
		if(resultJson.getString("msgid")!=null) {
			sms.setMsgid(resultJson.getString("msgid"));
		}
		smsMapper.save(sms);
		return 0;
	}

	@Override
	public int creditsUpgrade(Long userId,String credits,String validPeriod) {
		Map<String, Object> search = new HashMap<>();
		search.put("type", "creditsUpgrade");
		search.put("state", "10");
		String smsNo = smsTplMapper.findSelective(search).getNumber();
		if (smsNo!=null) {
			Map<String,Object> map = new HashMap<>();
			UserBaseInfo userInfo=userBaseInfoService.findByUserId(userId);
			if(null==userInfo){
				logger.error("------------------>未查询到userId={}.的详情信息...",new Object[]{userId});
				return 0;
			}
			map.put("credits", credits);
			map.put("validPeriod", validPeriod);

			Map<String, Object> payload = new HashMap<>();
			payload.put("credits", credits);
			payload.put("message", changeMessage("creditsUpgrade",map));
			payload.put("mobile", userInfo.getPhone());
			return sendSmsByType(smsNo,payload,userInfo.getPhone(),"creditsUpgrade","");
		}else {
			return 0;
		}
	}

	private String getRepayPeriods(String repayOrderNo) {
		//订单号中含有'X'的是新的分期业务，不含的为单期借款老数据
		StringBuilder msg = new StringBuilder();
		String[] orderNos = repayOrderNo.split("X");
		if (orderNos.length > 1) {
			String stages = orderNos[1].split("Y")[0];
			String[] stage = stages.split("N");
			String startStage = stage[0];
			if (stage.length > 1) {
				String endStage = stage[1];
				msg.append("第").append(startStage).append("-").append(endStage).append("期");
			} else {
				msg.append("第").append(startStage).append("期");
			}
			return msg.toString();

		} else {
			return msg.toString();
		}
	}

	@Override
	public Page<Sms> getList(Map<String, Object> paramMap, int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		return (Page<Sms>)smsMapper.listSelective(paramMap);
	}


	@Override
	public int chuanglanSms(String result, String type) {
		JSONObject resultJson = JSONObject.parseObject(result);
		logger.info("------------->result:{},phone:{},type={}",new Object[]{result,String.valueOf(resultJson.get("phone")),type});
		if(null!=resultJson && !resultJson.isEmpty()){
			if (resultJson.getInteger("code") == 0) {
				return resultToSmsType(resultJson, String.valueOf(resultJson.get("phone")), type);
			}
		}
		return errorSms(resultJson, String.valueOf(resultJson.get("phone")), type);
	}

	@Override
	public int updateByMsgid(String msgid, String status) {
		Map<String,Object> map = new HashMap<>();
		if(status.equals(SmsEnum.STATUS_SUCCESS.getCode())){
			map.put("respState", SmsModel.RESEND_STATE_SUCCESS);
			map.put("resp","短信发送成功");
		}else{
			map.put("respState",SmsModel.RESEND_STATE_FAIL);
			map.put("resp",SmsEnum.getByEnumKey(status).getName());
		}
		map.put("msgid",msgid);
		map.put("respTime",new Date());
		return smsMapper.updateByMsgid(map);
	}
}
