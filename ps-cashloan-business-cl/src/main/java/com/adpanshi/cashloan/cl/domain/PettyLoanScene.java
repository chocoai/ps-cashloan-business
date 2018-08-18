package com.adpanshi.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-03 20:30:04
 * @history
 */
public class PettyLoanScene implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public PettyLoanScene(){}
	
	public PettyLoanScene(Long userId, Integer sceneType){
		this.userId=userId;
		this.sceneType=sceneType;
	}
	
	public PettyLoanScene(Long userId, Integer status, Date startTime, Date validityTime){
		this.userId=userId;
		this.status=status;
		this.startTime=startTime;
		this.validityTime=validityTime;
		
	}

	/**
	 * PettyLoanScene
	 * @param userId
	 * @param status
	 * @param sceneType
	 * @param startTime
	 * @param validityTime
	 */
	public PettyLoanScene(Long userId, Integer status, Integer sceneType, Date startTime, Date validityTime){
		this(userId, status, startTime, validityTime);
		this.sceneType=sceneType;
	}

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
	 * @Fields sceneType:场景类型(1.租房贷、2.购房贷、3.车贷、4.自有房产)
	 */
	private Integer sceneType;
	
	/**居住城市*/
	private String livingCity;
	
	/**小区名称*/
	private String communityName;
	
	/**楼层单元*/
	private String floorUnit;
	
	/**租金*/
	private String rent;
	
	/**租房面积*/
	private String rentArea;
	
	/**押金方式*/
	private String depositType;
	
	/**是否抵压*/
	private String isPress;
	
	/**产权性质*/
	private String natureProperty;
	
	/**户型*/
	private String houseType;
	/**
	 * @Fields status:状态(0.无效、1.有效.)
	 */
	private Integer status;
	
	/**
	 * @Fields gmtCreateTime:创建时间
	 */
	private Date gmtCreateTime;
	
	/**
	 * @Fields gmtUpdateTimes:更新时间
	 */
	private Date gmtUpdateTimes;
	
	/**
	 * @Fields history:历史记录(记录每次借款-包含borrow_id每次借款都需要更新且记录borrowID)
	 */
	private String history;
	
	/**
	 * @Fields startTime:起始时间
	 */
	private Date startTime;
	
	/**
	 * @Fields validityTime:有效时间
	 */
	private Date validityTime;
	
	/**
	 * @Fields remarks:备注(给用户看)
	 */
	private String remarks;
	
	//columns END

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

	public Integer getSceneType() {
		return sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	public String getLivingCity() {
		return livingCity;
	}

	public void setLivingCity(String livingCity) {
		this.livingCity = livingCity;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getFloorUnit() {
		return floorUnit;
	}

	public void setFloorUnit(String floorUnit) {
		this.floorUnit = floorUnit;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getRentArea() {
		return rentArea;
	}

	public void setRentArea(String rentArea) {
		this.rentArea = rentArea;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getIsPress() {
		return isPress;
	}

	public void setIsPress(String isPress) {
		this.isPress = isPress;
	}

	public String getNatureProperty() {
		return natureProperty;
	}

	public void setNatureProperty(String natureProperty) {
		this.natureProperty = natureProperty;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}

	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}

	public Date getGmtUpdateTimes() {
		return gmtUpdateTimes;
	}

	public void setGmtUpdateTimes(Date gmtUpdateTimes) {
		this.gmtUpdateTimes = gmtUpdateTimes;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}