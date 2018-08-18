package com.adpanshi.cashloan.core.common.util;

import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.system.domain.SysConfig;
import com.adpanshi.cashloan.system.service.SysConfigService;
import org.apache.log4j.Logger;
import tool.util.BeanUtil;
import tool.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 缓存帮助类
 * 

 * @version 1.0.0
 * @date 2017年1月7日 上午10:40:22
 * Copyright 粉团网路 资产匹配系统 All Rights Reserved
 *
 * 
 *
 */
public class CacheUtil {

    private static final Logger logger = Logger.getLogger(CacheUtil.class);

    /**
     * 初始化系统参数配置
     */
	public static void initSysConfig() {

        logger.info("初始化系统配置Config...");

        // 系统配置Config缓存
        SysConfigService sysConfigService = (SysConfigService) BeanUtil.getBean("sysConfigService");

        Map<String, String> configMap = new HashMap<String, String>();

        List<SysConfig> sysConfigs = sysConfigService.findAll();
        for (SysConfig sysConfig : sysConfigs) {
            if (null != sysConfig && StringUtil.isNotBlank(sysConfig.getCode())) {
                configMap.put(sysConfig.getCode(),sysConfig.getValue());
            }
        }
        Global.del();
        Global.set(configMap);
    }

 
}
