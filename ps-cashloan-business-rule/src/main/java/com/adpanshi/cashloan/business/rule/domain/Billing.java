package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:账单信息
 * @author ppchen
 * @date  2017年8月9日 下午1:59:39
 *
 *
 */
public class Billing implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
    * 主键Id
    */
    private Long id;
    
    /**
    * 用户id
    */
    private Long userId;
    
    /**
     * 账单类型  10-有盾认证，20-同盾风控
     */
    private String type;
    
    /**
     * 流水单
     */
    private String transId;
    
    /**
     * 数量
     */
    private Integer count;
    
    /**
     * 交易时间
     */
    private Date transactionTime;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

}
