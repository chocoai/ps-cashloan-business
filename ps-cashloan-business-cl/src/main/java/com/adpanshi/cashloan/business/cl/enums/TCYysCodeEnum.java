package com.adpanshi.cashloan.business.cl.enums;

/**
 * saas风控接口返回状态对应关系
 * @author ppchen
 * 2017年8月21日 下午5:05:33
 * 
 */
public enum TCYysCodeEnum {

	STATUS_INIT(20000, "授权成功"), // 初始状态
	STATUS_PART_ERR(10014, "报告数据部分异常"), // 报告数据部分异常，异常的业务数据类型及对应异常月份见“fail”字段
	STATUS_NOINPUT(10000, "发送短信验码/获取图片验证码 请输入验证码"), //
	STATUS_SPIDER_BEGIN(10007, "交互结束开始爬取"), // (第一步：联通可能发送)
	STATUS_VERIFY_SUCCESS(10002, "第一次验证成功，等待第二次验证"), // 移动
	STATUS_FAIL_PASSWORD(10008, "服务密码错误导致登录失败"), //
	STATUS_SIMPLE_PASSWORD(10021, "简单密码或者初始密码无法登录"), //
	STATUS_LIMIT_OPERATION(10023, "网站限制，需要30分钟之后才能登录操作"), //
	STATUS_FAIL_NOEND(10024, "详单验证失败，正在爬取基本信息，可能取详单的缓存"), //
	STATUS_SENDSMS_FAIL(10022, "短信发送失败"), // 失败
	STATUS_CARRIER_FAIL(50001, "运营商异常"), // 失败 异常carrier
	STATUS_ID_CARD(10012, "验证失败 身份证或姓名错误"), //
	STATUS_NOTAVAILABLE(50002, "某省份爬虫暂时不可用"), // 不可用

	STATUS_FAIL_SMS(10006, "短信验证码错误"), //
	STATUS_INPUT_IN(10001, "已输入"), //
	STATUS_NOINPUT_NOSEND(10004, "吉林电信手动出发发送短信，请输入验证码"), //
	STATUS_SMS_INPUT(10010, "验证失败 重新输入原来的验证码"), //
	STATUS_SMS_SEND(10011, "验证失败 已重新发送新验证码 请输入新验证码"), //
	STATUS_SPIDER_SUCCESS(10003, "爬取结束 开始存储"), //
	STATUS_TIMEOUT(10005, "验证超时，结束订单"), //
	STATUS_SAVE_SUCCESS(10009, "存储成功");
	private int key;
	private String message;

	private TCYysCodeEnum(int key, String message) {
		this.key = key;
		this.message = message;
	}

	public int getKey() {
		return this.key;
	}

	public String getmessage() {
		return this.message;
	}

	public static String getYysCodeMessage(int key) {
		for (TCYysCodeEnum c : TCYysCodeEnum.values()) {
			if (c.getKey() == key)
				return c.getmessage();
		}
		return "saas风控系统异常";
	}
}
