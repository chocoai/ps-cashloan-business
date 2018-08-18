package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.ProfitAmount;
import com.adpanshi.cashloan.cl.model.ManageProfitAmountModel;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 分润资金Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:29:51
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ProfitAmountMapper extends BaseMapper<ProfitAmount,Long> {

	/**
	 * 可提现查询
	 * @param map
	 * @return
	 */
	List<ManageProfitAmountModel> findAmount(Map<String, Object> map);

	/**
	 * 管理员可提现查看
	 * @param map
	 * @return
	 */
	List<ManageProfitAmountModel> findSysAmount(Map<String, Object> map);

	/**
	 *  listNoCash
	 * @method: listNoCash
	 * @param
	 * @return: java.util.List<com.adpanshi.cashloan.cl.domain.ProfitAmount>
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:55
	 */
	List<ProfitAmount> listNoCash();
	
	/**
	 * 增加未打款金额
	 * @param map
	 * @return
	 */
	int addNocashedAmount(Map<String, Object> map);
}
