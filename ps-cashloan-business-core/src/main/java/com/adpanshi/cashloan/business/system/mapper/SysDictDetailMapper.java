package com.adpanshi.cashloan.business.system.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DAO接口

 * @version 1.0
 * @since 2014-11-03
 */
@RDBatisDao
public interface SysDictDetailMapper extends BaseMapper<SysDictDetail,Long> {

	
	
	int deleteByPrimary(Long id);
	
	
	Long getCount(Map<String, Object> data);

	/**
	 * @description
	 * @return

	 * @return List<Map<String,Object>>
	 * @since  1.0.0
	*/
	List<Map<String, Object>> queryAllDic();

	List<Map<String, Object>> getPageListMap(Map<String, Object> data);

	/**
	 * 根据父类ID获取详细值
	 * @param parentId
	 * @return
	 */
	List<String> getItemVlueByParentId(String parentId);
	
	/**
	 * 查询数据字典详情
	 * @param code
	 * @param parentName
	 * @return
	 */
	SysDictDetail findDetail(@Param("code") String code, @Param("parentName") String parentName);

	/**
	 * 根据父级类型名称查询字典列表
	 * @param parentName
	 * @return
	 */
	List<Map<String, Object>> queryAllDicByParentName(@Param("parentName") String parentName);

	/**
	 * 新增时查询额度类型名称
	 * @param data
	 * @return
	 */
	List<SysDictDetail> listByTypeCode(Map<String, Object> data);

	/**
	 * 修改时查询额度类型名称
	 * @param data
	 * @return
	 */
	List<SysDictDetail> listUpdateCode(Map<String, Object> data);

	/**
	 * 根据父级类型编码和子级名称查询字典code
	 * @param value
	 * @param parentCode
	 * @return
	 */
	String getCodeByValue(@Param("value") String value, @Param("parentCode") String parentCode);

	/**
	 * 根据父类ID获取详细值
	 * @param parentId
	 * @return
	 */
	List<String> getItemCodeByParentId(String parentId);

	/**
	 * 查询数据字典详情
	 * @param itemValue
	 * @return
	 */
	SysDictDetail findDetailByItemValue(@Param("itemValue") String itemValue);

	/**
	 * <p>根据父级的typeCode及子级的itemCode查找对应的字典详情</p>
	 * @param typeCode 父类编码
	 * @param itemCode 子类编码
	 * @return
	 * */
	SysDictDetail getDetailByTypeCodeWithItemCode(@Param("typeCode") String typeCode, @Param("itemCode") String itemCode);
	/**
	 * <p> 根据场景类型、userId、查询最用户最近一次提交的场景</p>
	 * @param typeCode
	 * @param userId
	 * @return
	 * */
	SysDictDetail getLastDictDetailByTypeCodeWithUserId(@Param("typeCode") String typeCode, @Param("userId") Long userId);
}
