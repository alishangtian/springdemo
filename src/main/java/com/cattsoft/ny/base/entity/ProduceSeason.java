/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity;

import com.cattsoft.baseplatform.core.entity.Entity;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class ProduceSeason extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long houseId;
	private java.lang.Long thresholdInfoId;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private java.lang.String crops;
	private java.lang.String state;
	private java.util.Date createTime;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setHouseId(java.lang.Long houseId) {
		this.houseId = houseId;
	}
	
	public java.lang.Long getHouseId() {
		return this.houseId;
	}
	public void setThresholdInfoId(java.lang.Long thresholdInfoId) {
		this.thresholdInfoId = thresholdInfoId;
	}
	
	public java.lang.Long getThresholdInfoId() {
		return this.thresholdInfoId;
	}
	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public java.util.Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setCrops(java.lang.String crops) {
		this.crops = crops;
	}
	
	public java.lang.String getCrops() {
		return this.crops;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
}