package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.LianlianOrderRecord;
import com.adpanshi.cashloan.business.cl.enums.LianlianPayTypeEnum;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.OrderRecodeMode;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.system.domain.SysUser;

import java.util.List;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-14 10:08:24
 * @history
 */
public interface LianlianOrderRecordService extends BaseService<LianlianOrderRecord, Long>{
    
	/**
	 * <p>根据给定orderNo去连连查询订单</p>
	 * @param payType 支付类型(1.分期付款、2.实时付款)
	 * @param orderNo
	 * @return OrderRecordModel
	 * */
	OrderRecodeMode queryOrderNoByLianLian(LianlianPayTypeEnum payType, String orderNo);

	/**
	 * <p>根据给定orderNo查询(默认以gmt_modify_time倒序排序)</p>
	 * @param orderNo 可选(当orderNo 为null时将会查询所有记录)
	 * @return List<LianlianOrderRecord>
	 * */
	List<LianlianOrderRecord> queryByOrderNo(Integer payType, String orderNo);

	/**
	 * <p>根据给定orderNo查询最近一条记录</p>
	 * @param payType 支付类型（1.分期付、2.实时付）
	 * @param orderNo 订单号
	 * @return LianlianOrderRecord
	 * */
	LianlianOrderRecord queryByLastOrderNo(Integer payType, String orderNo);

	/**
	 * <p>如果查询的订单号在本地存在且已经交易成功，那么直接取本地数据、否则再次查询连连并把查询到的数据记录入库</p>
	 * @param payType 支付方式（1.分期付、2.实时付）
	 * @param sysUser 操作用户
	 * @param orderNo 订单号
	 * @param payAgain (true:再次支付逻辑、false:订单查询逻辑)
	 * @return Boolean(是否交易成功)
	 * */
	Boolean getQueryLocalInLianlianByOrderNo(LianlianPayTypeEnum payType, SysUser sysUser, String orderNo, Boolean payAgain);


}