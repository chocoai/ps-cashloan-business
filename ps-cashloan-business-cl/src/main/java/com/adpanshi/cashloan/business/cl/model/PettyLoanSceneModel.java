package com.adpanshi.cashloan.business.cl.model;

import com.alibaba.fastjson.annotation.JSONField;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月27日下午9:02:34
 **/
public class PettyLoanSceneModel {
	
	/**起始时间*/
	private String startDate;
	
	/**截止时间*/
	private String endDate;
	
	/**居住城市*/
	@JSONField(name = "living_city")
	private String livingCity;
	
	/**小区名称*/
	@JSONField(name = "community_name")
	private String communityName;
	
	/**楼层单元*/
	@JSONField(name = "floor_unit")
	private String floorUnit;
	
	/**租金*/
	private String rent;
	
	/**租房面积*/
	@JSONField(name = "rent_area")
	private String rentArea;
	
	/**押金方式*/
	@JSONField(name = "deposit_type")
	private String depositType;
	
	/**是否抵压*/
	@JSONField(name = "is_press")
	private String isPress;
	
	/**产权性质*/
	@JSONField(name = "nature_property")
	private String natureProperty;
	
	/**户型*/
	@JSONField(name = "house_type")
	private String houseType;
	
	/**场景类型((1.租房贷、2.购房贷、3.车贷、4.自有房产))*/
	@JSONField(name = "scene_type")
	private Integer sceneType;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public Integer getSceneType() {
		return sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

}
