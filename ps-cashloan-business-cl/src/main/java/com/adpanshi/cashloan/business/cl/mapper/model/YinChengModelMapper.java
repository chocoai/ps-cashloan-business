package com.adpanshi.cashloan.business.cl.mapper.model;

import com.adpanshi.cashloan.business.cl.model.yincheng.RepayModel;
import com.adpanshi.cashloan.business.cl.model.yincheng.UserInfoModel;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @author yecy
 * @date 2017-11-1 13:42
 */
@RDBatisDao
public interface YinChengModelMapper {

    /**
     * 获取用户信息Model
     *
     * @param id cl_user表中id
     * @return
     */
    UserInfoModel findUserModel(Long id);

    /**
     * 获取还款Model
     *
     * @param id cl_borrow_repay表中id
     * @return
     */
    RepayModel findRepayModel(Long id);

    /**
     * 获取还款Model
     *
     * @param paramMap 还款id列表,key为ids
     * @return List
     */
    List<RepayModel> listRepayModel(Map<String, Object> paramMap);
}
