package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayModelMapper;
import com.adpanshi.cashloan.business.cl.model.BorrowAndRepayModel;
import com.adpanshi.cashloan.business.cl.service.BorrowRepayModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yecy
 * @date 2017/12/20 16:01
 */
@Service("borrowRepayModelService")
public class BorrowRepayModelServiceImpl implements BorrowRepayModelService {

    private BorrowRepayModelMapper borrowRepayModelMapper;

    @Autowired
    public BorrowRepayModelServiceImpl(BorrowRepayModelMapper borrowRepayModelMapper){
        this.borrowRepayModelMapper = borrowRepayModelMapper;
    }

    @Override
    public List<BorrowAndRepayModel> findExpireToRepay(String state) {
        return findExpireToRepay(state,null);
    }

    @Override
    public List<BorrowAndRepayModel> findExpireToRepay(String state, Date afterRepayTime) {
        return findExpireToRepay(state,null,afterRepayTime);
    }

    @Override
    public List<BorrowAndRepayModel> findExpireToRepay(String state, Date beforeRepayTime, Date afterRepayTime) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(state)){
            map.put("state",state);
        }
        if (beforeRepayTime != null){
            map.put("beforeRepayTime",beforeRepayTime);
        }
        if (afterRepayTime != null){
            map.put("afterRepayTime",afterRepayTime);
        }

        return borrowRepayModelMapper.findExpireToRepay(map);
    }
}
