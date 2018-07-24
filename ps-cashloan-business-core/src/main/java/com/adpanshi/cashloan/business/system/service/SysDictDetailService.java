package com.adpanshi.cashloan.business.system.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


/**
 * 数据字典详情Service
 *
 * @version 1.0
 * @Update_Date 2017/12/21
 * @Updtaor huangqin
 * @since 2014-11-03
 */
public interface SysDictDetailService extends BaseService<SysDictDetail, Long> {

    /**
     * 字典详情信息表删除
     *
     * @param id 主键ID
     * @return boolean
     */
    boolean deleteSysDictDetail(Long id);

    /**
     * 详情个数查询
     *
     * @param id
     * @return Long
     */
    Long getItemCountMap(Long id);

    /**
     * 新增或者修改字典详情信息
     *
     * @param dictDetail
     * @return boolean
     */
    boolean addOrModify(SysDictDetail dictDetail);

    /**
     * 字典详情分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param id
     * @return Page<SysDictDetail>
     */
    Page<SysDictDetail> getDictDetailList(int currentPage, int pageSize, Long id);

    /**
     * 根据code查询字典
     * @param data
     * @return List<SysDictDetail>
     */
    List<SysDictDetail> listByTypeCode(Map<String, Object> data);

    /**
	 * <p>根据父级的typeCode及子级的itemCode查找对应的字典详情</p>
	 * @param typeCode 父类编码
	 * @param itemCode 子类编码
	 * @return
	 * */
	SysDictDetail getDetailByTypeCodeWithItemCode(String typeCode, String itemCode);

	/**
	 * <p> 根据场景类型、userId、查询最用户最近一次提交的场景</p>
	 * @param typeCode
	 * @param userId
	 * @return
	 * */
	SysDictDetail getLastDictDetailByTypeCodeWithUserId(String typeCode, Long userId);

	/**
	 * 查询所有信息
	 * @return
	 */
	List<Map<String,Object>> queryAllDic();
}
