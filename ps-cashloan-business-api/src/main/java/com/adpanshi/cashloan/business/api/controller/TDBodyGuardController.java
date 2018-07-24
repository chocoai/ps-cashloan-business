package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.rule.service.TDBodyGuardService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/25 19:23:33
 * @desc 同盾信贷保镖Controller
 * Copyright 浙江盘石 All Rights Reserved
 */
@Controller
@Scope("prototype")
public class TDBodyGuardController extends BaseController {

    @Resource
    private TDBodyGuardService tdBodyGuardService;

    /**
     * 根据用户userId执行同盾信贷保镖接口
     * @param userId
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/tdBodyGuard/bodyGuardRisk.htm",method = RequestMethod.POST)
    public void bodyGuardRisk(@RequestParam(value = "userId") String userId,@RequestParam(value = "mobileType") String mobileType) {
        Map<String, Object> result = new HashMap<>();
        //根据用户userId
        String data = tdBodyGuardService.tdBodyGuardLogin(userId,mobileType);
        if(data!=null){
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }else{
            result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
        }
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        ServletUtils.writeToResponse(response, result);
    }
}
