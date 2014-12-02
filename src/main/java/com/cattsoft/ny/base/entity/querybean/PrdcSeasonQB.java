/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity.querybean;

import com.cattsoft.baseplatform.core.entity.QueryBean;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public class PrdcSeasonQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long houseId;
	private java.lang.Long thresholdInfoId;
	private java.util.Date beginTime;
	private java.util.Date beginTime0;
	private java.util.Date endTime;
	private java.util.Date endTime0;
	private java.lang.String crops;
	private java.lang.String state;
	private java.util.Date createTime;
	private java.lang.String name;
	private java.lang.String seedSource;
	private java.lang.String thresholdInfoName;
	
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
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setSeedSource(java.lang.String seedSource) {
		this.seedSource = seedSource;
	}
	
	public java.lang.String getSeedSource() {
		return this.seedSource;
	}

	public java.util.Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getBeginTime0() {
		return beginTime0;
	}

	public void setBeginTime0(java.util.Date beginTime0) {
		this.beginTime0 = beginTime0;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public java.util.Date getEndTime0() {
		return endTime0;
	}

	public void setEndTime0(java.util.Date endTime0) {
		this.endTime0 = endTime0;
	}

	public java.lang.String getThresholdInfoName() {
		return thresholdInfoName;
	}

	public void setThresholdInfoName(java.lang.String thresholdInfoName) {
		this.thresholdInfoName = thresholdInfoName;
	}



	
}