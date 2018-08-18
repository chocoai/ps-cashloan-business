package com.adpanshi.cashloan.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.model.SmsModel;
import com.adpanshi.cashloan.cl.service.BankCardService;
import com.adpanshi.cashloan.cl.service.UserAuthService;
import com.adpanshi.cashloan.cl.service.impl.SmsService;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.MessageConstant;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.rule.domain.BankCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.DateUtil;
import tool.util.NumberUtil;

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
*/
@Scope("prototype")
@Controller
public class BankCardController extends BaseController {

   @SuppressWarnings("unused")
   private static final Logger logger = LoggerFactory.getLogger(BankCardController.class);

   @Resource
   private BankCardService bankCardService;

   @Resource
   private UserBaseInfoService userInfoService;

   @Resource
   private UserAuthService userAuthService;

    @Resource
    protected SmsService smsService;

    /**
     * @description 获取单个用户的所有绑定银行卡
     * @param userId

     * @return void
     * @since 1.0.0
     */
    @RequestMapping(value = "/api/act/mine/bankCard/getBankCardList.htm", method = RequestMethod.GET)
    public void getBankCardList(@RequestParam(value = "userId") String userId) {

        BankCard card = bankCardService.getBankCardByUserId(NumberUtil.getLong(userId));

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_DATA, card);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response, result);
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
   @RequestMapping(value = "/api/act/mine/bankCard/authSignReturn.htm", method = RequestMethod.POST)
   public void authSignReturn(
           @RequestParam(value = "vcode", required = true) String vcode,
           @RequestParam(value = "userId", required = true) Long userId,
           @RequestParam(value = "bank", required = true) String bank,
           @RequestParam(value = "phone", required = true) String phone,
           @RequestParam(value = "userName", required = true) String userName,
           @RequestParam(value = "cardNo", required = true) String cardNo,
           @RequestParam(value = "ifscCode", required = true ) String ifscCode) {
       //4个字母  7个数字
       Map<String, Object> result = new HashMap<>(16);

   //	String ifscCodeRegex = "^[a-zA-Z]{4}[0-9]{7}$";
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
       }else if(ifscCode == null){
           result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.IFSC_CODE_NOT_EXIST);
           errFlg = true;
       }
       if(errFlg){
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           ServletUtils.writeToResponse(response, result);
           return;
       }

       /*String msg = smsService.validateSmsCode(phone, null, SmsModel.SMS_TYPE_BINDCARD , vcode);
       if (msg != null) {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, msg);
           logger.error(result.toString());
           ServletUtils.writeToResponse(response, result);
           return;
       }*/

       BankCard bankCard = bankCardService.getBankCardByUserId(userId);

       boolean flag;
       if (null == bankCard) {
           BankCard card = new BankCard();
           card.setCardNo(cardNo);
           card.setBindTime(DateUtil.getNow());
           card.setUserId(userId);
           card.setBank(bank);
           card.setIfscCode(ifscCode);
           flag = bankCardService.save(card);
           logger.info("新增银行卡信息："+ JSONObject.toJSONString(card));
       } else {
           Map<String, Object> paramMap = new HashMap<>(16);
           paramMap.put("id", bankCard.getId());
           paramMap.put("bank", bank);
           paramMap.put("cardNo", cardNo);
           paramMap.put("ifscCode",ifscCode);
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
