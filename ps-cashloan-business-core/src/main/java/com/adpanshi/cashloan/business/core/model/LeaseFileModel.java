package com.adpanshi.cashloan.business.core.model;

import java.util.Date;
import java.util.List;

/***
 ** @category 租房合同附件...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月5日下午2:46:19
 **/
public class LeaseFileModel{
	
	/**状态(0.无效、1.有效.)*/
	private Integer status;
	/**起始时间*/
	private Date startTime;
	/**失效时间*/
	private Date validityTime;
	/**附件列表*/
	private List<?> dataList;
	
	/**场景*/
	private Object scene;
	
	public LeaseFileModel(){}
	
	public LeaseFileModel(Date startTime,Date validityTime,List<?> dataList){
		this.startTime=startTime;
		this.validityTime=validityTime;
		this.dataList=dataList;
	}
	
	public LeaseFileModel(Date startTime,Date validityTime,List<?> dataList,Integer status){
		this(startTime, validityTime, dataList);
		this.status=status;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public Object getScene() {
		return scene;
	}

	public void setScene(Object scene) {
		this.scene = scene;
	}
	
}
