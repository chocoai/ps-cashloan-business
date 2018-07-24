package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.domain.SceneBusiness;
import com.adpanshi.cashloan.business.rc.model.ManageSceneBusinessModel;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 场景与第三方征信接口关联关系Service
 *
 * @version 1.0.0
 * @date 2017-03-14 13:42:36
 */
public interface SceneBusinessService extends BaseService<SceneBusiness, Long> {
    /**
     * 场景与第三方征信接口关联关系分页查询
     *
     * @param paramMap
     * @param current
     * @param pageSize
     * @return Page<ManageSceneBusinessModel>
     */
    Page<ManageSceneBusinessModel> page(Map<String, Object> paramMap,
                                        int current, int pageSize);

    /**
     * 添加场景与第三方征信接口关联关系
     *
     * @param sceneBusiness
     * @return boolean
     */
    boolean save(SceneBusiness sceneBusiness);

    /**
     * 修改场景与第三方征信接口关联关系
     *
     * @param sceneBusiness
     * @return boolean
     */
    boolean update(SceneBusiness sceneBusiness);

    /**
     * 修改场景接口关系的状态
     *
     * @param id
     * @param state
     * @return boolean
     */
    boolean updateState(Long id, String state);

    /**
     * 校验场景接口关系是否存在
     *
     * @param scene
     * @param businessId
     * @return
     */
    boolean validExist(String scene, Long businessId, String type);
}
