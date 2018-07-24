package com.adpanshi.cashloan.business.core.common.model;

/**
 * @auther huangqin
 * @data 2017-12-16
 * @dsscription 自定义的用来存储登录用户的信息
 * @since 2017-12-16 16:10:23
 * @version 1.0.0
 * */
public class AuthUserRole {

	/**用户ID*/
    private Long userId;
	/**登录名称*/
    private String userName;
	/**真实名字*/
	private String realName;
	/**角色ID*/
	private Long roleId;
	/**角色名称*/
	private String  roleName;
	/**工号*/
	private String jobNum;
	/**移动电话*/
	private String phone;
	/**移动电话*/
	private String mobile;
	/**所属部门*/
	private String officeId;
	/**状态*/
	private Integer status;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}