package com.adpanshi.cashloan.business.cl.service;

import java.util.Map;

/**
 * panNumber verify
 */
public interface VerifyPanService {
    /**
     * panNumber verify
     * @param paramMap
     * @return
     */
    int verifyPanNumber(Map<String, Object> paramMap);
}
