package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.SceneBusiness;
import com.adpanshi.cashloan.business.rc.mapper.SceneBusinessMapper;
import com.adpanshi.cashloan.business.rc.model.ManageSceneBusinessModel;
import com.adpanshi.cashloan.business.rc.service.SceneBusinessService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 场景与第三方征信接口关联关系ServiceImpl
 *
 * @version 1.1.0
 * @date 2017-03-14 13:42:36
 * @updateDate 2017/12/22
 * @updator huangqin
 */

@Service("sceneBusinessService")
public class SceneBusinessServiceImpl extends BaseServiceImpl<SceneBusiness, Long> implements SceneBusinessService {

    @Resource
    private SceneBusinessMapper sceneBusinessMapper;

    @Override
    public BaseMapper<SceneBusiness, Long> getMapper() {
        return sceneBusinessMapper;
    }

    @Override
    public Page<ManageSceneBusinessModel> page(Map<String, Object> paramMap, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        return (Page<ManageSceneBusinessModel>) sceneBusinessMapper.list(paramMap);
    }

    @Override
    public boolean save(SceneBusiness sceneBusiness) {
        sceneBusiness.setState("10");
        sceneBusiness.setAddTime(DateUtil.getNow());
        return sceneBusinessMapper.save(sceneBusiness) > 0;
    }

    @Override
    public boolean update(SceneBusiness sceneBusiness) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", sceneBusiness.getId());
        paramMap.put("scene", sceneBusiness.getScene());
        paramMap.put("businessId", sceneBusiness.getBusinessId());
        paramMap.put("getWay", sceneBusiness.getGetWay());
        paramMap.put("period", sceneBusiness.getPeriod());
        return sceneBusinessMapper.updateSelective(paramMap) > 0;
    }

    @Override
    public boolean updateState(Long id, String state) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("state", state);
        return sceneBusinessMapper.updateSelective(paramMap) > 0;
    }

    @Override
    public boolean validExist(String scene, Long businessId, String type) {
        if (StringUtil.isNotBlank(scene) && StringUtil.isNotBlank(type) && businessId != null && businessId > 0) {
            Map<String, Object> params = new HashMap<>();
            params.put("scene", scene);
            params.put("businessId", businessId);
            params.put("type", type);
            return sceneBusinessMapper.listSelective(params).size() > 0;
        } else {
            return false;
        }
    }
}