package com.adpanshi.cashloan.business.cl.mapper.model;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * @author yecy
 * @date 2017-11-1 13:42
 */
@RDBatisDao
public interface PanVerifyModelMapper {

    /**
     * 插入pan卡信息Model
     *
     * @param paramMap
     * @return
     */
    int save(Map<String, Object> paramMap);

    /**
     *
     * @param panNo
     * @return
     */
    Map getPanInfo(String panNo);

    /**
     *
     * @return
     */
    List<Map> getUserPanList();

}
