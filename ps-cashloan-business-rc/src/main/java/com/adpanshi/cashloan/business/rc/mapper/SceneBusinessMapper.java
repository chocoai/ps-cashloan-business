package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.domain.SceneBusiness;
import com.adpanshi.cashloan.business.rc.model.ManageSceneBusinessModel;
import com.adpanshi.cashloan.business.rc.model.TppServiceInfoModel;

import java.util.List;
import java.util.Map;

/**
 * 场景与第三方征信接口关联关系Dao
 *
 * @version 1.0.0
 * @date 2017-03-14 13:42:36
 */
@RDBatisDao
public interface SceneBusinessMapper extends BaseMapper<SceneBusiness, Long> {

    /**
     * 条件查询List
     *
     * @param paramMap
     * @return
     */
    List<ManageSceneBusinessModel> list(Map<String, Object> paramMap);

    /**
     * 查询第三方接口信息
     *
     * @return
     */
    List<TppServiceInfoModel> findTppServiceInfo();
}