package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.yincheng.InvestBorrowRelation;
import com.adpanshi.cashloan.business.cl.mapper.InvestBorrowRelationMapper;
import com.adpanshi.cashloan.business.cl.service.InvestBorrowRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 投资标与借款单对应表ServiceImpl
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 21:10:24
 */

@Service("investBorrowRelationService")
public class InvestBorrowRelationServiceImpl implements InvestBorrowRelationService {

    private static final Logger logger = LoggerFactory.getLogger(InvestBorrowRelationServiceImpl.class);

    @Resource
    private InvestBorrowRelationMapper investBorrowRelationMapper;


    @Override
    public int save(InvestBorrowRelation relation) {
        return investBorrowRelationMapper.save(relation);
    }

    @Override
    public int saveAll(List<InvestBorrowRelation> relations) {
        if (CollectionUtils.isEmpty(relations)) {
            return 0;
        }
        return investBorrowRelationMapper.saveAll(relations);
    }

    @Override
    public List<InvestBorrowRelation> listSelective(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return new ArrayList<>();
        }
        return investBorrowRelationMapper.listSelective(map);
    }

    @Override
    public List<String> findExistOrderNo(Collection<String> orderNos) {
        if (CollectionUtils.isNotEmpty(orderNos)) {
            return investBorrowRelationMapper.findExistOrderNo(orderNos);
        }
        return new ArrayList<>();

    }
}
