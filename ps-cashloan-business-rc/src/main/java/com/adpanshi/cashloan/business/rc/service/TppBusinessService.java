package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rc.domain.TppBusiness;
import com.adpanshi.cashloan.business.rc.model.ManageTppBusinessModel;
import com.adpanshi.cashloan.business.rc.model.TppBusinessModel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 第三方征信接口信息Service
 *
 * @version 1.0.0
 * @date 2017-03-14 13:41:57
 */
public interface TppBusinessService extends BaseService<TppBusiness, Long> {

    /**
     * 查询所有
     *
     * @return List<TppBusinessModel>
     */
    List<TppBusinessModel> listAll();

    /**
     * 第三方征信接口信息分页查询
     *
     * @param params
     * @param currentPage
     * @param pageSize
     * @return Page<ManageTppBusinessModel>
     */
    Page<ManageTppBusinessModel> page(Map<String, Object> params, int currentPage, int pageSize);

    /**
     * 据条件查询集合
     *
     * @param paramMap
     * @return List<TppBusiness>
     */
    List<TppBusiness> listSelective(Map<String, Object> paramMap);

    /**
     * 添加第三方征信接口信息
     *
     * @param tppBusiness
     * @return boolean
     */
    boolean save(TppBusiness tppBusiness);

    /**
     * 修改第三方征信接口信息
     *
     * @param tppBusiness
     * @return boolean
     */
    boolean update(TppBusiness tppBusiness);

    /**
     * 修改第三方征信接口信息
     *
     * @param id
     * @param state
     * @return boolean
     */
    boolean updateState(Long id, String state);

    /**
     * 修改第三方征信接口信息
     *
     * @param business
     * @return boolean
     */
    boolean tppBusinessExist(TppBusiness business);

    /**
     * 根据Nid查找
     *
     * @param nid
     * @param tppId
     * @return TppBusiness
     */
    TppBusiness findByNid(String nid, Long tppId);
}
