package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.enums.LianlianServiceEnum;
import com.adpanshi.cashloan.business.cl.model.SmsModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.AuthSignModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.RiskItems;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.LianLianHelper;
import com.adpanshi.cashloan.business.cl.service.BankCardService;
import com.adpanshi.cashloan.business.cl.service.BorrowRepayService;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.service.impl.SmsService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.service.CloanUserService;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.DateUtil;
import tool.util.NumberUtil;
import tool.util.ReflectUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 银行卡Controller
*
* @author
* @version 1.0.0
* @date 2017-02-15 14:37:14
*
*
*
*
*/
@Scope("prototype")
@Controller
public class BankCardController extends BaseController {

   @SuppressWarnings("unused")
   private static final Logger logger = LoggerFactory.getLogger(BankCardController.class);

   @Resource
   private BankCardService bankCardService;

   @Resource
   private CloanUserService cloanUserService;

   @Resource
   private UserBaseInfoService userInfoService;

   @Resource
   private UserAuthService userAuthService;

   @Resource
   private BorrowRepayService borrowRepayService;

    @Resource
    protected SmsService smsService;

   /**
    * @description 获取单个用户的所有绑定银行卡
    * @param userId

    * @return void
    * @since 1.0.0
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/bankCard/getBankCardList.htm", method = RequestMethod.GET)
   public void getBankCardList(@RequestParam(value = "userId") String userId) {

       BankCard card = bankCardService.getBankCardByUserId(NumberUtil.getLong(userId));

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_DATA, card);
       result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
       ServletUtils.writeToResponse(response, result);
   }

    /**
     * @description 通过修改数据解除用户银行卡绑定
     * @param userId
     * @return void
     * @since 1.0.0
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/bankCard/unBindBankCard.htm", method = RequestMethod.GET)
    public void unBindBankCard(@RequestParam(value = "userId") String userId) {
       int num = bankCardService.unBindBankCard(NumberUtil.getLong(userId));
       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_DATA, num);
       result.put(Constant.RESPONSE_CODE_MSG, "解绑成功");
       ServletUtils.writeToResponse(response, result);
    }

   /**
    * @description 验证码
    * @param userId

    * @return void
    * @since  1.0.0
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/bankCard/getCaptcha.htm",method=RequestMethod.GET)
   public void getCaptcha(@RequestParam(value="userId",required=true)String userId){
       Map<String,Object> returnMap=new HashMap<String,Object>();
       returnMap.put(Constant.RESPONSE_CODE,Constant.SUCCEED_CODE_VALUE);
       returnMap.put(Constant.RESPONSE_CODE_MSG,"短信发送成功");
       ServletUtils.writeToResponse(response, returnMap);
   }

    /**
     * 银行卡卡bin查询
     * @param cardNo
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/bankCard/cardBin.htm", method = RequestMethod.POST)
    public void saveOrUpdate(
            @RequestParam(value = "cardNo", required = true) String cardNo) {
        LianLianHelper helper = new LianLianHelper();
        Map data = helper.queryCardBin(cardNo);
        Map<String,Object> result=new HashMap<>(16);
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response,result);
    }

   /**
    * 请求签约
    * @param bankName
    * @param bankId
    * @param cardNo
    * @param userId
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/bankCard/authSign.htm", method = RequestMethod.POST)
   public void saveOrUpdate(
           @RequestParam(value = "bankName", required = true) String bankName,
           @RequestParam(value = "bankId", required = true) String bankId,
           @RequestParam(value = "cardNo", required = true) String cardNo,
           @RequestParam(value = "userId", required = true) String userId) {

       if(!StringUtil.isNumber(cardNo) || cardNo.length() <15 || cardNo.length() > 30   ){
           Map<String,Object> result=new HashMap<>(16);
           result.put(Constant.RESPONSE_CODE,Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG,"银行卡号格式有误");
           ServletUtils.writeToResponse(response,result);
           return ;
       }

       //@remarks: 追加银行卡校验.bug 692 @author: nmnl @date: 2018-01-21
       boolean bl = false;
       LianLianHelper helper = new LianLianHelper();
       Map<String,String> cardBinMap = helper.queryCardBin(cardNo);
       if(StringUtil.isNotBlank(cardBinMap) && StringUtil.equals(bankId,cardBinMap.get("cardBankId"))){
           bl = true;
       }

       //@remarks: 追加银行卡校验.bug 692. @author: nmnl @date: 2018-01-21
       Map<String, String> authSignMap = new HashMap<>(16);
       if (bl) {
           User user = cloanUserService.getById(NumberUtil.getLong(userId));
           UserBaseInfo baseInfo = userInfoService.findByUserId(NumberUtil.getLong(userId));

           String orderNo  = OrderNoUtil.getSerialNumber();
           AuthSignModel authSign = new AuthSignModel(orderNo,LianlianServiceEnum.SERVICE.BANK_CARD_BIND);
           authSign.setUser_id(user.getUuid());
           authSign.setId_no(baseInfo.getIdNo());
           authSign.setAcct_name(baseInfo.getRealName());
           authSign.setCard_no(cardNo);

           RiskItems riskItems = new RiskItems();
           riskItems.setFrms_ware_category("2010");
           riskItems.setUser_info_mercht_userno(user.getUuid());
           riskItems.setUser_info_bind_phone(baseInfo.getPhone());
           riskItems.setUser_info_dt_register(DateUtil.dateStr3(user.getRegistTime()));
           riskItems.setUser_info_full_name(baseInfo.getRealName());
           riskItems.setUser_info_id_type("0");
           riskItems.setUser_info_id_no(baseInfo.getIdAddr());
           riskItems.setUser_info_identify_state("1");
           riskItems.setUser_info_identify_type("1");
           authSign.setRisk_item(JSONObject.toJSONString(riskItems));
           authSign.sign();
           Map<String, String> map = ReflectUtil.fieldValueToMap(authSign,authSign.reqParamNames());

           authSignMap.put("authSignData", JSON.toJSONString(map));
           logger.info("BankCardController-saveOrUpdate():连连支付绑卡请求报文："+JSON.toJSONString(map));
           authSignMap.put("binCard", "yes");
       } else {
           authSignMap.put("binCard", "no");
           authSignMap.put("binCardInfo", "银行卡名称与卡号不匹配!");
        }
       Map<String,Object> result=new HashMap<>(16);
       result.put(Constant.RESPONSE_DATA, authSignMap);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 签约响应回调
    *
    * @param vcode
    * @param userId
    * @param bank
    * @param phone
    * @param userName
    * @param cardNo
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/bankCard/authSignReturn.htm", method = RequestMethod.POST)
   public void authSignReturn(
           @RequestParam(value = "vcode", required = true) String vcode,
           @RequestParam(value = "userId", required = true) Long userId,
           @RequestParam(value = "bank", required = true) String bank,
           @RequestParam(value = "phone", required = true) String phone,
           @RequestParam(value = "userName", required = true) String userName,
           @RequestParam(value = "cardNo", required = true) String cardNo) {
       Map<String, Object> result = new HashMap<>(16);

       UserBaseInfo userBaseInfo = userInfoService.findByUserId(userId);
       boolean errFlg = false;
       if(userBaseInfo == null){
           result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.USER_NOT_EXIST);
           errFlg = true;
       }else if(!userName.trim().toLowerCase().equals(userBaseInfo.getRealName().toLowerCase())){
           result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.USER_NAME_NOT_EQUAL);
           errFlg = true;
       }else if(!phone.trim().equals(userBaseInfo.getPhone())){
           result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.PHONE_NOT_EQUAL);
           errFlg = true;
       }
       if(errFlg){
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           ServletUtils.writeToResponse(response, result);
           return;
       }

       String msg = smsService.validateSmsCode(phone, null, SmsModel.SMS_TYPE_BINDCARD , vcode);
       if (msg != null) {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, msg);
           logger.error(result.toString());
           ServletUtils.writeToResponse(response, result);
           return;
       }

       BankCard bankCard = bankCardService.getBankCardByUserId(userId);

       boolean flag;
       if (null == bankCard) {
           BankCard card = new BankCard();
           card.setCardNo(cardNo);
           card.setBindTime(DateUtil.getNow());
           card.setUserId(userId);
           card.setBank(bank);
           flag = bankCardService.save(card);
           logger.info("新增银行卡信息："+JSONObject.toJSONString(card));
       } else {
           Map<String, Object> paramMap = new HashMap<>(16);
           paramMap.put("id", bankCard.getId());
           paramMap.put("bank", bank);
           paramMap.put("cardNo", cardNo);
           flag = bankCardService.updateSelective(paramMap);
           logger.error("更新银行卡信息"+paramMap.toString());
       }

       if (flag) {
           Map<String, Object> paramMap = new HashMap<>(16);
           paramMap.put("userId", userId);
           paramMap.put("bankCardState", "30");
           userAuthService.updateByUserId(paramMap);
           logger.error("更新银行卡认证信息"+paramMap.toString());
       }

       // 签约成功之后 ，查询是否有未还款的借款，进行重新授权
       borrowRepayService.authSignApply(userId);

       int code = Constant.SUCCEED_CODE_VALUE;
       String codeMsg = "保存成功";
       if (!flag) {
           code = Constant.FAIL_CODE_VALUE;
           codeMsg = "保存失败";
       }
       result.put(Constant.RESPONSE_CODE, code);
       result.put(Constant.RESPONSE_CODE_MSG, codeMsg);
       logger.error(result.toString());

       ServletUtils.writeToResponse(response, result);
   }
}
