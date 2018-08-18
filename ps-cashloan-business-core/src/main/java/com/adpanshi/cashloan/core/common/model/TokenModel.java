package com.adpanshi.cashloan.core.common.model;

/**
 * @author 8452
 */
public class TokenModel {

	/** 用户id*/
	private String userid;
	/** 用户名字 */
	private String username;
	private String token;
	
	public TokenModel(String userid, String username, String token) {
		this.userid = userid;
		this.username = username;
		this.token = token;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
