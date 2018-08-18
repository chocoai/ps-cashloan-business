package com.adpanshi.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-18 16:42:01
 * @history
 */
public class AppSession implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//columns START
	/**
	 * @Fields id:id
	 */
	private Long id;
	
	/**
	 * @Fields token:token
	 */
	private String token;
	
	/**
	 * @Fields refreshToken:refreshToken
	 */
	private String refreshToken;
	
	/**
	 * @Fields userId:userId
	 */
	private Long userId;
	
	/**
	 * @Fields expireTime:expireTime
	 */
	private Date expireTime;
	
	/**
	 * @Fields lastAccessTime:lastAccessTime
	 */
	private Date lastAccessTime;
	
	/**
	 * @Fields status:status
	 */
	private Integer status;
	
	/**
	 * @Fields session:session
	 */
	private String session;
	
	/**
	 * @Fields errData:errData
	 */
	private String errData;
	
	/**
	 * @Fields loginType:loginType
	 */
	private Integer loginType;
	
	/**
	 * @Fields loginId:loginId
	 */
	private Long loginId;
	
	//columns END

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getErrData() {
		return errData;
	}

	public void setErrData(String errData) {
		this.errData = errData;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
}