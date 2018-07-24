package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.BorrowStagesAgreement;
import com.adpanshi.cashloan.business.cl.service.BorrowAgreementService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cc on 2017/7/25.
 * 借款协议信息提供接口
 */
@Scope("prototype")
@Controller
public class BorrowAgreementController extends BaseController {

    static final Logger logger = LoggerFactory.getLogger(BorrowAgreementController.class);

    @Resource
    private BorrowAgreementService borrowAgreementService;

    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/agreement/find.htm", method = RequestMethod.GET)
    public void findAgreement(
            @RequestParam(value="borrowMainId",required=false) Long borrowMainId,
            @RequestParam(value="userId",required=true) Long userId
    ) throws Exception {
        //预览合同  预留
//      data = borrowAgreementCService.previewBorrowAgreement(userId,day,borrowAmount);
        //查询实际合同
        BorrowStagesAgreement data = StringUtil.isBlank(borrowMainId)?null:borrowAgreementService.findByBowMainId(borrowMainId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "api查询合同成功");
        ServletUtils.writeToResponse(response, result);
    }




}
