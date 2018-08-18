package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.CacheUtil;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * API System Controller
 *
 * @version 1.0.0
 * @date 2017年4月29日 下午3:47:24
 * Copyright 粉团网路 All Rights Reserved
 * @author 8452
 *
 */

@Controller
@Scope("prototype")
public class SysController extends BaseController {

    /**
     * 重加载系统配置数据
     * 
     * @throws Exception
     */
    @RequestMapping("/system/config/reload.htm")
    public void reload() throws Exception {
        // 调用缓存辅助类 重加载系统配置数据
        CacheUtil.initSysConfig();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * @Title: getConfigParam
     * @Description: 获取系统配置参数
     * @return void
     * @throws
     */
    @RequestMapping("/system/config/value")
    public void getConfigParam(@RequestParam(value="key",required=true) final String key) throws Exception {
    	// 调用缓存辅助类 重加载系统配置数据
    	Map<String,Object> data = new HashMap<>();
		data.put("lxtelno", key);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		returnMap.put(Constant.RESPONSE_DATA, data);
		ServletUtils.writeToResponse(response, returnMap);
    }
    
}
