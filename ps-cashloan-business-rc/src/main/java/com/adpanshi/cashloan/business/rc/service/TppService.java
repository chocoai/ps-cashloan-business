package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.domain.Tpp;
import com.adpanshi.cashloan.business.rc.model.ManageTppModel;
import com.adpanshi.cashloan.business.rc.model.TppModel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 第三方征信信息Service
 *
 * @version 1.0.0
 * @date 2017-03-14 13:41:05
 */
public interface TppService extends BaseService<Tpp, Long> {

    /**
     * 获取分页列表
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return Page<ManageTppModel>
     */
    Page<ManageTppModel> page(Map<String, Object> params, int currentPage, int pageSize);

    /**
     * 获取列表信息
     *
     * @param
     * @return List<TppModel>
     */
    List<TppModel> listAll();

    /**
     * 添加第三方征信tpp
     *
     * @param tpp
     * @return boolean
     */
    boolean save(Tpp tpp);

    /**
     * 更新第三方征信tpp
     *
     * @param tpp
     * @return boolean
     */
    boolean update(Tpp tpp);

    /**
     * 更新第三方征信状态
     *
     * @param id
     * @param state
     * @return boolean
     */
    boolean updateState(Long id, String state);
}
