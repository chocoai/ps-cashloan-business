package com.adpanshi.cashloan.business.system.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.system.domain.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 数据字典DAO接口
 * @version 1.0
 * @created 2014年9月23日 上午9:44:54
 */
@RDBatisDao
public interface SysDictMapper extends BaseMapper<SysDict,Long> {

	List<Map<String, Object>> getDictsCache(String typeDict) ;

	List<SysDict> getItemListByMap(Map<String, Object> map);

	Long getCount(Map<String, Object> arg);

	long deleteById(Long id);

	SysDict findByTypeCode(@Param("code") String typeCode);

}
