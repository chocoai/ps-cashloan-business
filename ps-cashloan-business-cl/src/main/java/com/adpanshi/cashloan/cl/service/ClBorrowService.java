package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.rule.model.*;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 借款信息表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 *
 */
public interface ClBorrowService extends BaseService<Borrow, Long> {
	
	/**
	 * 修改借款状态
	 * @param id
	 * @param state
	 * @return
	 */
	int modifyState(long id, String state) ;

	/**
	 * 查询
	 * @param searchMap
	 * @return
	 */
	List<Borrow> findBorrowByMap(Map<String, Object> searchMap);

	/**
	 * 查询最新10条借款信息
	 * @return
	 */
	List<IndexModel> listIndex();

	/**
	 * 借款记录查看
	 * @param searchMap
	 * @return
	 */
	List<BorrowMain> findBorrow(Map<String, Object> searchMap);

	/**
	 * 分页查询
	 * @param searchMap
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<ClBorrowModel> page(Map<String, Object> searchMap, int current,
                             int pageSize);

	/**
	 * 关联用户的借款分页查询后台列表显示
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> listModel(Map<String, Object> params,
                                      int currentPage, int pageSize);

	/**
	 * 关联用户的借款分页查询后台列表显示 && 机审通过的所有订单
	 * @author:M
	 * @date:2017-07-10
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowModel> automaticListModel(Map<String, Object> params,
                                               int currentPage, int pageSize);

	/**
	 * 修改数据
	 * @param data
	 * @return
	 */
	int updateSelective(Map<String, Object> data);


    /**
     * 查询可借款用户
     * @return
     */
	List<ManageBorrowTestModel> seleteUser();

	/**
	 * 首页信息查询
	 * @param userId
	 * @return
	 */
	Map<String,Object> findIndex(String userId);

	/**
	 * 额度评估
	 * @param userId
	 * @return
	 */
	Map<String,Object> assessment(String userId);

	/**
	 * 批量保存
	 */
	int saveAll(List<Borrow> borrowList);

	/**
	 * 根据主借款订单id，判断分期账单是否全部还清
	 *
	 * @param mainId
	 * @return
	 * @author yecy 20171226
	 */
	Boolean isAllFinished(Long mainId);

	Borrow findBorrowByMainId(long borrowId);
}
