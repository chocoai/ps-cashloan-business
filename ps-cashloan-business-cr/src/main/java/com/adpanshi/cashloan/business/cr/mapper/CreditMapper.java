package com.adpanshi.cashloan.business.cr.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.cr.domain.Credit;
import com.adpanshi.cashloan.business.cr.model.CreditModel;

import java.util.List;
import java.util.Map;

/**
 * 授信额度管理Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2016-12-15 15:47:24
 *
 *
 * 
 *
 */
@RDBatisDao
public interface CreditMapper extends BaseMapper<Credit,Long> {
	
	/**
	 * 更新额度
	 * @param map
	 * @return
	 */
	int updateAmount(Map<String, Object> map);

	/**
	 * 列表查询返回CreditModel
	 * @param searchMap
	 * @return
	 */
	List<CreditModel> page(Map<String, Object> searchMap);


	/**
	 * 查询用户所有额度类型
	 * @param searchMap
	 * @return
	 */
	List<CreditModel> listAll(Map<String, Object> searchMap);
	
	
	/**
	 * 用户信用额度查询
	 * @param searchMap
	 * @return
	 */
	List<CreditModel> creditList(Map<String, Object> searchMap);
	
	Map<String,Object> info(String userId);
}
