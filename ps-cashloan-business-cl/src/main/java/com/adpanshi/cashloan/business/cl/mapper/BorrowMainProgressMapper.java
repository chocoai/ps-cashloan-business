package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.BorrowMainProgress;
import com.adpanshi.cashloan.business.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.business.cl.model.ProgressModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 借款主信息表Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 16:09:17

 *
 *
 */
@RDBatisDao
public interface BorrowMainProgressMapper extends BaseMapper<BorrowMainProgress, Long> {

    /**
     * 借款进度查询
     * @param bpMap
     * @return
     */
    List<BorrowProgressModel> listProgress(Map<String, Object> bpMap);
    
    /**
     * <p>根据userID、borrowId查询借款进度</p>
     * @param params
     * @return
     * */
    List<ProgressModel> queryProgressByBorIdWithUserId(Map<String, Object> params);

}
