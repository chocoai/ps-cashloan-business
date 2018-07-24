package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.TongdunRespDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 同盾审核报告详细信息Dao
 *

 * @version 1.0.0
 * @date 2017-05-17 10:11:29
 *
 *
 *
 *
 */
@RDBatisDao
public interface TongdunRespDetailMapper {

    /**
     * <p>新增</p>
     * @param tableName 待操作的表
     * @param tongdunRespDetail 待新增的对象
     * @return  int 受影响的行数
     * */
    int save(@Param("tableName") String tableName, @Param("item") TongdunRespDetail tongdunRespDetail);


    /**
     * <p>更新</p>
     * @param tableName 待操作的表
     * @param tongdunRespDetail 待新增的对象
     * @return  int 受影响的行数
     * */
    int update(@Param("tableName") String tableName, @Param("item") TongdunRespDetail tongdunRespDetail);

    int updateSelective(Map<String, Object> params);

    TongdunRespDetail findByPrimary(@Param("tableName") String tableName, @Param("id") Long id);

    TongdunRespDetail findSelective(Map<String, Object> params);

    List<TongdunRespDetail> listSelective(Map<String, Object> params);
}
