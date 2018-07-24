package com.adpanshi.cashloan.business.core.common.context;

/**
 * 
 * TODO 后台列表数据导出 常量
 * @author
 * @date 2017年4月13日 下午14:40:52
 */
public final class ExportConstant {
	/** 未还款*/
	public static final String STATE_FINISH_PRE = "20";
	/** 已还款 */
	public static final String STATE_NOTPLAY_WAIT= "10";
	/** 东方联信语音-已通知 */
	public static final String VOICESTATE_NOTICED = "1";
	/** 东方联信语音-未通知 */
	public static final String VOICESTATE_NOT_NOTICED= "0";


	/** 导出暂定为5000条，如有需求，酌情更改*/
	/*** 分页起始*/
	public static final int STRAT_LIMIT = 0;
	
	/*** 分页结束*/
	public static final int END_LIMIT = 1000;

	/*** 最大导出容量*/
	public static final int TOTAL_LIMIT = 10000;
	
	/** 还款记录导出 表头*/
	public static final String[] EXPORT_REPAYLOG_LIST_HEARDERS = {"真实姓名","手机号码","订单号", "借款金额(元)", "应还逾期罚金(元)", "已还逾期罚金(元)", "逾期天数", "应还款金额(元)", 
		"已还款金额(元)", "还款账号", "流水号", "还款方式", "应还款日期", "实际还款日期"};
	/** 还款记录导出 属性数组*/
	public static final String[] EXPORT_REPAYLOG_LIST_FIELDS = {"realName","phone","orderNo", "borrowAmount", "repayPenalty", "penaltyAmout", "penaltyDay", "repayAmount",
		"repayLogAmount", "repayAccount", "serialNumber", "repayWay", "repayPlanTime", "repayTime"};
	
	/** 借款订单导出 表头*/
	public static final String[] EXPORT_BORROW_LIST_HEARDERS = {"真实姓名","手机号","订单号","借款金额(元)","借款期限","订单生成时间","综合费用",
			"实际到账金额","实际还款金额(元)","订单状态"};
	/** 借款订单导出 属性数组*/
	public static final String[] EXPORT_BORROW_LIST_FIELDS = {"realName", "phone", "orderNo", "amount","timeLimit","createTime", "fee",
			"realAmount", "repayAmount", "state"};
	
	/** 支付记录导出 表头*/
	public static final String[] EXPORT_PAYLOG_LIST_HEARDERS = {"收款人姓名","手机号码","金额","收款银行卡","借款时间","打款时间","业务","状态"};
	/** 支付记录导出 属性数组*/
	public static final String[] EXPORT_PAYLOG_LIST_FIELDS = {"realName","loginName","amount","cardNo","loanTime","updateTime","scenesStr","stateStr"};
	
	/** 支付对账记录导出 表头*/
	public static final String[] EXPORT_PAYCHECK_LIST_HEARDERS = {"订单号","支付方式","订单金额","支付方订单金额","错误类型","对账记录添加时间","支付业务","处理方式","处理结果"};
	/** 支付对账记录导出 属性*/
	public static final String[] EXPORT_PAYCHECK_LIST_FIELDS = {"orderNo","payTypeStr","orderAmount","realPayAmount","typeStr","processTime",
		"scenesStr","processWayStr","processResultStr",};
	
	/** 已逾期订单导出 表头*/
	public static final String[] EXPORT_OVERDUE_LIST_HEARDERS = {"真实姓名","手机号","订单号","借款金额(元)","借款期限","订单生成时间","综合费用",
			"实际到账金额","订单状态"};
	/** 已逾期订单导出 属性数组*/
	public static final String[] EXPORT_OVERDUE_LIST_FIELDS = {"realName", "phone", "orderNo", "amount","timeLimit","createTime", "fee",
			"realAmount", "state"};
	
	/** 已坏账订单导出 表头*/
	public static final String[] EXPORT_BADDEBT_LIST_HEARDERS = {"真实姓名","手机号","订单号","借款金额(元)","借款期限","订单生成时间","综合费用",
		"实际到账金额","实际还款金额(元)","订单状态"};
	/** 已坏账订单导出 属性数组*/
	public static final String[] EXPORT_BADDEBT_LIST_FIELDS = {"realName", "phone", "orderNo", "amount","timeLimit","createTime", "fee",
		"realAmount", "repayAmount", "state"};
	
	/** 催收订单导出 表头*/
	public static final String[] EXPORT_REPAYORDER_LIST_HEARDERS = {"真实姓名","手机号码","金额","借款时间","预计还款时间","逾期天数","逾期等级","罚息","催收人","订单状态"};
	/** 催收订单导出 属性*/
	public static final String[] EXPORT_REPAYORDER_LIST_FIELDS = {"borrowName","phone","amount","borrowTime","repayTime","penaltyDay","level",
		"penaltyAmout","userName","state"};
	
	/** 催收反馈导出 表头*/
	public static final String[] EXPORT_URGELOG_LIST_HEARDERS = {"真实姓名","手机号码","金额","借款时间","预计还款时间","逾期天数","逾期等级","罚息","催收人","订单状态",
		"承诺还款时间","催收反馈","催收时间"};
	/** 催收反馈导出 属性*/
	public static final String[] EXPORT_URGELOG_LIST_FIELDS = {"borrowName","phone","amount","borrowTime","repayTime","penaltyDay","level",
		"penaltyAmout","userName","state","promiseTime","remark","createTime"};
	
	/** 同盾审核记录导出 表头*/
	public static final String[] EXPORT_TONGDUNLOG_LIST_HEARDERS = {"真实姓名","手机号码","借款订单号","借款金额","风险报告编码","申请状态",
		"提交审核报告结果编码","提交审核返回信息","提交审核报告时间","查询审核报告结果编码","查询审核报告信息","风险结果","风险分数","查询审核报告时间"};
	/** 同盾审核记录导出 属性*/
	public static final String[] EXPORT_TONGDUNLOG_LIST_FIELDS = {"realName","phone","borrowNo","amount","reportId","stateStr",
		"submitCode","submitParams","createTime","queryCode","queryParams","rsState","rsScore","updateTime"};

	/** 日报记录导出 表头*/
	public static final String[] EXPORT_DAILYDATA_LIST_HEARDERS = {"日期","用户数","借款笔数","放款笔数","逾期笔数","催收次数",
	"坏账笔数","坏账金额","放款金额","还款金额","服务费金额","逾期金额","逾期罚息","新用户放款笔数","老用户人审放款笔数","老用户机审放款笔数",
	"当日应还款笔数","老用户逾期笔数","逾期率","老用户逾期率"};
	/** 日报记录导出 属性*/
	public static final String[] EXPORT_DAILYDATA_LIST_FIELDS = {"date","userCount","borrowCount","loanCount","overdueCount","urgeRepayCount",
	"badAmtCount","badAmt","loanAmt","repayAmt","serveFeeAmt","overdueAmt","overdueInterest","newUserLoanCount","oldUserLoanCount","oldUserAutoLoanCount",
	"repayAmtCount","oldUserOverdueCount","overdueRate","oldOverdueRate"};
	/**还款计划导出 表头*/
	public static final String[] 	EXPORT_BORROWREPAY_LIST_HEARDERS={"真实姓名","手机号码","身份证号","订单号","借款金额","逾期罚款","逾期天数","应还金额","应还款总额","应还款日期","实际还款日期","人工审核生成时间","审核人","还款状态"};
	/**还款计划导出 属性*/

	public static final String[] EXPORT_BORROWREPAY_LIST_FIELDS={"realName","phone","idNo","orderNo","borrowAmount","penaltyAmout","penaltyDay","repayAmount","repayTotal","repayPlanTime","repayTime","auditTime","auditName","state"};

	/**东方联信语音导出 表头*/
	public static final String[] 	EXPORT_LIANXIN_LIST_HEARDERS={"姓名","手机号码","借款时间","借款周期","应还款金额","应还款时间","还款状态","东方联信语音提醒情况",};
	/**东方联信语音导出 属性*/
	public static final String[] EXPORT_LIANXIN_LIST_FIELDS={"realName","phone","borrowTime","timeLimit","repayAmount","repayPlanTime","state","voiceState"};

	/**渠道信息统计导出 表头*/
	public static final String[] EXPORT_CHANNEL_LIST_HEADERS={"渠道名称","日期","注册量","实名认证","绑卡","运营商","紧急联系人","芝麻授信","申请人数","放款人数","放款金额","老用户"};
	/**渠道信息统计导出 属性*/
	public static final String[] EXPORT_CHANNEL_LIST_FIELDS={"name","time","registerCount","idMember","bankCardMember","phoneMember","contactMember","zhiMaMember","borrowMember","loanMember","payAccount","oldMember"};
	
	/**订单审批信息导出表头*/
	public static final String[] EXPORT_SHENPI_LIST_HEADERS={"订单号","真实姓名","手机号码","订单生成时间","审核人","人工审核时间","审批原因","备注"};
	/**订单审批信息导出属性*/
	public static final String[] EXPORT_SHENPI_LIST_FIELDS={"orderNo","realName","phone","createTime","auditName","auditTime","orderView","remark"};
	
	public static String change(String state){
		String stateStr = "";
		switch (state) {
			case STATE_FINISH_PRE:
				stateStr = "未还款";
				break;
			case STATE_NOTPLAY_WAIT:
				stateStr = "已还款";
				break;
		}
		return stateStr;
	}

	public static String changeVoiceState(String voiceState){
		String stateStr = "";
		if(voiceState!=null){
			switch (voiceState) {
				case VOICESTATE_NOTICED:
					stateStr = "已通知";
					break;
				case VOICESTATE_NOT_NOTICED:
					stateStr = "未通知";
					break;
				default:
					stateStr = "未通知";
			}
		}else{
			stateStr = "未通知";
		}
		return stateStr;
	}
}
