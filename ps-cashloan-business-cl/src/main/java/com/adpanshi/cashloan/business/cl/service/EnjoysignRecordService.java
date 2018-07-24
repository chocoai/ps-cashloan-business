package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.EnjoysignRecord;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;

import java.util.List;

/***
 ** @category 1号签服务接口...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月28日下午2:48:17
 **/
public interface EnjoysignRecordService {
	
	/**
	 * <p>发起签署并进行后台签章</p>
	 * @param borrowMain
	 * @param orderId 订单号
	 * @return int 受影响的行
	 * */
	int startSignWithAutoSilentSign(BorrowMain borrowMain, String orderId);
	
	/***
	 * 新增
	 * @param enjoysignRecord
	 * @return int
	 * */
	int saveEnjoysignRecord(EnjoysignRecord enjoysignRecord);
	
	/**
	 * <p>更新</p>
	 * @param enjoysignRecord 待更新的对象(id 必填)
	 * @return int 受影响的行数
	 * */
	int updateSelective(EnjoysignRecord enjoysignRecord);

	/**
	 * <p>根据borrowMainId and status获取订单号</p>
	 * @param statusList  状态集
	 * @param borrowMainId 借款订单id
	 * @return String
	 * */
	String getOrderIdByBorrowIdWithStatus(List<Integer> statusList, String borrowMainId);

	/**
	 * <p>(1号签服务器)根据给定borrowId and status 查询合同URL </p>
	 * @param statusList
	 * @param borrowMainId
	 * @return String
	 * */
	String getRemoteContractURLByOrderId(List<Integer> statusList, String borrowMainId);

}
