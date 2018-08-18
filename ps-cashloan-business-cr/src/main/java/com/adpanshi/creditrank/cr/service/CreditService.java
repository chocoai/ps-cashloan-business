package com.adpanshi.creditrank.cr.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.system.domain.SysUser;
import com.adpanshi.creditrank.cr.domain.Credit;
import com.adpanshi.creditrank.cr.model.CreditModel;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 授信额度管理Service
 * 
 * @author
 * @version 1.0.0
 * @date 2016-12-15 15:47:24
 *
 *
 * 
 *
 */
public interface CreditService extends BaseService<Credit, Long>{

	/**
	 * 分页查询
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<CreditModel> page(Map<String, Object> searchMap, int current, int pageSize);

	/**
	 * 修改数据
	 * @param sysUser
	 * @param remark
	 * @param unuse
	 * @param id
	 * @return
	 */
	int updateSelective(long id, double unuse, SysUser sysUser, String remark);

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	Credit findByPrimary(long id);

	/**
	 * 修改
	 * @param param
	 * @return
	 */
	int updateSelective(Map<String, Object> param);

	/**
	 * 查询所有
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<CreditModel> listAll(Map<String, Object> searchMap, int current,
                              int pageSize);

	/**
	 * 查询用户
	 * @param consumerNo
	 * @return
	 */
	Credit findByConsumerNo(String consumerNo);

	/**
	 * 冻结解冻账户
	 * @param id
	 * @param state
	 * @param sysUser 
	 * @return
	 */
	int updateState(long id, String state, SysUser sysUser);
	
	/**
	 * 查询用户所有额度类型
	 * @param paramMap
	 * @return
	 */
	Credit findSelective(Map<String, Object> paramMap);
}
