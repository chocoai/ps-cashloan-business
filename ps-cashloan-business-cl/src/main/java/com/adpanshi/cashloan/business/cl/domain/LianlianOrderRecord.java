package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**@category 连连订单查询记录
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-14 10:08:24
 * @history
 */
public class LianlianOrderRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields userId:用户id(操作人)
	 */
	private Long userId;
	
	/**
	 * @Fields userName(操作用户)
	 * */
	private String userName;
	
	/**
	 * @Fields orderNo:订单号(借款信息表中的订单号)
	 */
	private String orderNo;
	
	/**
	 * <p>根据orderNo查找借款信息表中的订单号(cl_pay_log 中的  orderNo)</p>
	 * @Fields  noOrder 商户唯一订单号(发送连连实际订单号)
	 * */
	private String noOrder;
	
	/**
	 * @Fields retCode:交易结果编码
	 */
	private String retCode;
	
	/**
	 * @Fields retMsg:交易结果描述
	 */
	private String retMsg;
	
	/**
	 * @Fields resultPay:支付结果(SUCCESS:成功、WAITING:等待支付、PROCESSING:银行支付处理中、REFUND:退款、FAILURE:失败)
	 */
	private String resultPay;
	
	/**
	 * @Fields payType:支付类型（1.分期付、2.实时付）
	 */
	private Integer payType;
	
	/**
	 * @Fields returnParams:返回参数
	 */
	private String returnParams;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields gmtModifyTime:修改时间
	 */
	private Date gmtModifyTime;
	
	/**
	 * @Fields remarks:备注(对应result_pay描述)
	 */
	private String remarks;
	
	/**
	 * @Fields state:状态(0:正常、1:删除)
	 */
	private Integer state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getResultPay() {
		return resultPay;
	}

	public void setResultPay(String resultPay) {
		this.resultPay = resultPay;
	}
	
	public String getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(String returnParams) {
		this.returnParams = returnParams;
	}
	
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}

	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}

	public Date getGmtModifyTime() {
		return gmtModifyTime;
	}

	public void setGmtModifyTime(Date gmtModifyTime) {
		this.gmtModifyTime = gmtModifyTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	//columns END
}