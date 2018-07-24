package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.yincheng.UserIdRelation;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 用户id与银程用户id关联表Dao
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-10-31 19:44:02
 */
@RDBatisDao
public interface UserIdRelationMapper extends BaseMapper<UserIdRelation, Long> {

    /**
     * 根据userId 获取相关对象
     *
     * @param userIdList
     * @return
     */
    List<UserIdRelation> selectByUserId(@Param("userIdList") Collection<Long> userIdList);

}
