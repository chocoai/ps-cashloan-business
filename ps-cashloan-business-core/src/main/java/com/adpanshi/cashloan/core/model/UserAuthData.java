package com.adpanshi.cashloan.core.model;

import java.util.List;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月15日下午12:49:03
 **/
public class UserAuthData {
	
	/**
	 * 可选认证项指数
	 * */
	private int optional;
	
	/**
	 * 必选认证项指数
	 * */
	private int required;
	
	/**
	 * 隐藏项指数
	 * */
	private int hidden;
	
	/**选择项认证指数*/
	private int choose;
	
	/**
	 * 总认证项指数
	 * */
	private int total;
	
	/**
	 * 可选认证项数据集
	 * */
	private List<UserAuthExtra> optionals;
	
	/**
	 * 必选认证项数据集
	 * */
	private List<UserAuthExtra> requireds;
	
	/**
	 * 隐藏认证项数据集
	 * */
	private List<UserAuthExtra> hiddens;
	
	/**选择项认证(至少有一项认证)*/
	private List<UserAuthExtra> chooses;
	
	/**当前用户已认证数据集*/
	private Object verified;

	public int getOptional() {
		return optional;
	}

	public void setOptional(int optional) {
		this.optional = optional;
	}

	public int getRequired() {
		return required;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<UserAuthExtra> getOptionals() {
		return optionals;
	}

	public void setOptionals(List<UserAuthExtra> optionals) {
		this.optionals = optionals;
	}

	public List<UserAuthExtra> getRequireds() {
		return requireds;
	}

	public void setRequireds(List<UserAuthExtra> requireds) {
		this.requireds = requireds;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public List<UserAuthExtra> getHiddens() {
		return hiddens;
	}

	public void setHiddens(List<UserAuthExtra> hiddens) {
		this.hiddens = hiddens;
	}

	public List<UserAuthExtra> getChooses() {
		return chooses;
	}

	public void setChooses(List<UserAuthExtra> chooses) {
		this.chooses = chooses;
	}

	public int getChoose() {
		return choose;
	}

	public void setChoose(int choose) {
		this.choose = choose;
	}

	public Object getVerified() {
		return verified;
	}

	public void setVerified(Object verified) {
		this.verified = verified;
	}
	
}
