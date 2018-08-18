package com.adpanshi.cashloan.cl.service;

import java.util.List;
import java.util.Map;

/**
 * panNumber verify
 * @author
 */
public interface VerifyPanService {
    /**
    * @Description:  panNumber verify
    * @Param: [paramMap]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: Mr.Wange
    * @Date: 2018/7/24
    */
    Map<String, Object> verifyPanNumber(Map<String, Object> paramMap);

    /**
     * 获取未认证pan卡用户信息
     * @return
     */
    List<Map> getUserInfoList();
}
