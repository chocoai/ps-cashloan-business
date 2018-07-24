package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.xinyan.beans.XinyanReqParams;
import com.adpanshi.cashloan.business.core.xinyan.enums.XinyanRetunCodeEnum;
import com.adpanshi.cashloan.business.rule.domain.XinyanBlacklist;


/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-21 16:33:33
 * @history
 */
public interface XinyanBlacklistService {
    
	/**
	 * <p>根据给定idNo查找最近一条记录</p>
	 * @param idNo 身份证号
	 * @return XinyanBlacklist
	 * */
	XinyanBlacklist getXinyanBlacklistByLasterIdNo(String idNo);
	
	/**
	 * <p>更新</p>
	 * @param xinyanBlacklist 待更新的对象(id 必填)
	 * */
	int updateSelective(XinyanBlacklist xinyanBlacklist);
	
	/**
	 * <p>查询用户是否是黑名单(调用新颜负面拉黑接口)</p>
	 * @param reqParams 待请求的参数
	 * @return XinyanBlacklist 新颜接口返回的响应结果
	 * */
	XinyanBlacklist queryBlackByReqParams(XinyanReqParams reqParams);
	
	/**
	 * <p>查询用户是否是黑名单(调用新颜负面拉黑接口)</p>
	 * @param id_no 
	 * @param id_name
	 * @return XinyanBlacklist 新颜接口返回的响应结果
	 * */
	XinyanBlacklist queryBlackByIdNoWithIdName(String id_no, String id_name);

	/**
	 * <p>查询用户是否是黑名单(调用新颜负面拉黑接口)</p>
	 * @param userId
	 * @return XinyanBlacklist 新颜接口返回的响应结果
	 * */
	XinyanBlacklist queryBlackByUserId(Long userId);


	/**
	 * <p>1.规则是否命中（比如：是建议拉黑、并且逾期天数是16-30天的 或者是 31-60天）</p>
	 * @param xinyanBlacklist
	 * @param code
	 * @param overDueDay 最大逾期天数
	 * @return boolean (true:已命中、false:未命中)
	 * */
	boolean whetherHit(XinyanBlacklist xinyanBlacklist, XinyanRetunCodeEnum code, int overDueDay);
	
	int save(XinyanBlacklist xinyanBlacklist);
}