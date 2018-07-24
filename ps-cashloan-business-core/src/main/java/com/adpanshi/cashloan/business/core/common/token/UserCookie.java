package com.adpanshi.cashloan.business.core.common.token;

import javax.servlet.http.Cookie;

public class UserCookie extends Cookie{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6019668914034455129L;
	
	protected static int MAX_AGE = 60*60;
	
	protected static int MIN_AGE = 0;

	public UserCookie(String name, String value,boolean ho,int ma) {
		super(name, value);
		this.setHttpOnly(ho);
		this.setMaxAge(ma);
		this.setPath("/");
	}
	
	public static UserCookie  CookieAdd(String name, String value){
		return new UserCookie(name, value,false,MAX_AGE);
	}

	public static UserCookie  CookieClear(String name){
		return new UserCookie(name, "",true,MIN_AGE);
	}
	
}
