package com.adpanshi.cashloan.business.rule.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @category 存放用户安装的app应用
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-11 11:03:41
 * @history
 */
public class UserApps implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//columns START
	/**
	 * @Fields id:主键id
	 */
	private Long id;
	
	/**
	 * @Fields userId:用户id
	 */
	private Long userId;
	
	/**
	 * @Fields appName:应用名称
	 */
	private String appName;
	
	/**
	 * @Fields packageName:应用包名
	 */
	private String packageName;
	
	/**
	 * @Fields iexpress:系统内置标识(0:第三方应用,1.系统内置内应用)
	 */
	private Integer iexpress;
	
	/**
	 * @Fields systemType:系统类型(1.IOS,2.Android)
	 */
	private Integer systemType;
	
	/**
	 * @Fields state:状态(0:正常,1:已删除)
	 */
	private Integer state;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields remarks:备注
	 */
	private String remarks;

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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getIexpress() {
		return iexpress;
	}

	public void setIexpress(Integer iexpress) {
		this.iexpress = iexpress;
	}

	public Integer getSystemType() {
		return systemType;
	}

	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}

	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	//columns END
	
}