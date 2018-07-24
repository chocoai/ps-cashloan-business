package com.adpanshi.cashloan.business.core.xinyan.beans;

/***
 ** @category 请求参数...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日上午9:47:29
 **/
public class XinyanReqParams {

	
	//---------------------------->  Y:请求时、必填字段、N:非必填字段.   <----------------------------
	
	/**[Y]商户号新颜提供给商户的唯一编号*/
	private String member_id ;
	
	/**[Y]终端号新颜提供给商户的唯一终端编号*/
	private String terminal_id ;
	
	/**[Y]加密类型json*/
	private String data_type;
	
	/**[Y]加密数据由请求参数的集合加密参数，具体参照业务请求参数*/
	private String data_content;
	
	/**[Y]商户请求订单号系统保持唯一*/
	private String trans_id ;
	
	/**[Y]交易时间格式：yyyyMMddHHmmss*/
	private String trade_date;
	
	/***[Y]行业类型详见附录：行业类别*/
	private String industry_type;
	
	/**[Y]身份证号被查询人身份证号*/
	private String id_no;
	
	/**[Y]姓名被查询人姓名*/
	private String id_name; 
	
	/**[N]放款时间格式：yyyy-MM-dd*/
	private String loan_time ;
	
	/**[N]逾期天数核查人逾期时间*/
	private String overdue_day;
	
	/**[N]逾期金额核查人逾期⾦金金额*/
	private String overdue_amt;
	
	/**[N]贷款机构核查人逾期机构名称*/
	private String member_name;
	
	
	public XinyanReqParams(){}
	
	/**
	 * <p>构造-Base64加密对象</p>
	 * @param id_no 身份证号
	 * @param id_name 姓名(被查询人姓名)
	 * */
	public XinyanReqParams(String id_no,String id_name){
		this.id_no=id_no;
		this.id_name=id_name;
	}
	
	/**
	 * <p>构造-Base64加密对象</p>
	 * @param member_id 商户号
	 * @param terminal_id 终端号
	 * @param id_no 身份证号
	 * @param id_name 姓名(被查询人姓名)
	 * @param trans_id 订单号
	 * @param trade_date 交易时间(格式:yyyyMMddHHmmss)
	 * @param industry_type 行业类别
	 * *//*
	public XinyanReqParams(String member_id,String terminal_id,String id_no,String id_name,String trans_id,String trade_date,String industry_type){
		this.member_id=member_id;
		this.terminal_id=terminal_id;
		this.id_no=id_no;
		this.id_name=id_name;
		this.trans_id=trans_id;
		this.trade_date=trade_date;
		this.industry_type=industry_type;
	}*/

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getData_content() {
		return data_content;
	}

	public void setData_content(String data_content) {
		this.data_content = data_content;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}

	public String getIndustry_type() {
		return industry_type;
	}

	public void setIndustry_type(String industry_type) {
		this.industry_type = industry_type;
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

	public String getLoan_time() {
		return loan_time;
	}

	public void setLoan_time(String loan_time) {
		this.loan_time = loan_time;
	}

	public String getOverdue_day() {
		return overdue_day;
	}

	public void setOverdue_day(String overdue_day) {
		this.overdue_day = overdue_day;
	}

	public String getOverdue_amt() {
		return overdue_amt;
	}

	public void setOverdue_amt(String overdue_amt) {
		this.overdue_amt = overdue_amt;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	
}
