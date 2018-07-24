package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.yincheng.InvestBorrowRelation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 投资标与借款单对应表Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 21:10:24

 *
 *
 */
public interface InvestBorrowRelationService {

    int save(InvestBorrowRelation relation);

    int saveAll(List<InvestBorrowRelation> relations);

    List<InvestBorrowRelation> listSelective(Map<String, Object> map);

    /**
     * 查询订单是否已存在
     * @param orderNos
     * @return 已存在的订单
     */
    List<String> findExistOrderNo(Collection<String> orderNos);

}
