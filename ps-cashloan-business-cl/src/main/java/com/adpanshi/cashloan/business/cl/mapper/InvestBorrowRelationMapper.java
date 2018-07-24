package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.yincheng.InvestBorrowRelation;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 投资标与借款单对应表Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 21:10:24

 *
 *
 */
@RDBatisDao
public interface InvestBorrowRelationMapper {

    int save(InvestBorrowRelation relation);

    int saveAll(List<InvestBorrowRelation> relations);

    List<InvestBorrowRelation> listSelective(Map<String, Object> map);

    List<String> findExistOrderNo(@Param("list") Collection<String> orderNos);

}
