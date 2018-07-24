package com.adpanshi.cashloan.business.core.xinyan.beans;
/***
 ** @category 新颜-业务返回数据...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日下午5:17:12
 **/
public class XinyanReturnData {
	
	/**查询结果码*/
	private String code;
	
	/**查询结果描述*/
	private String desc;
	
	/**商户请求订单号*/
	private String trans_id;
	
	/**交易流水号*/
	private String trade_no;
	
	/**收费标示*/
	private String fee;
	
	/**身份证*/
	private String id_no;
	
	/**姓名*/
	private String id_name;
	
	/**核查结果详情(当code=0 时有值，其他状态为空)*/
	private String result_detail;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getId_name() {
		return id_name;
	}
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}
	public String getResult_detail() {
		return result_detail;
	}
	public void setResult_detail(String result_detail) {
		this.result_detail = result_detail;
	}
	
}
