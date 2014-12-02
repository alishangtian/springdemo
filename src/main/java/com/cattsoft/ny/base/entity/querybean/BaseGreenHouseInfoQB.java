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
public class BaseGreenHouseInfoQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.String crops;
	private java.lang.String name;
	private java.lang.String seedSource;
	private java.util.Date ctime;
	private java.lang.String gps;
	private java.lang.String acreage;
	private java.lang.String state;
	private java.lang.String remark;
	private java.lang.String uuid;
	private java.lang.Long baseId;
	private java.lang.String geoInfo;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setCrops(java.lang.String crops) {
		this.crops = crops;
	}
	
	public java.lang.String getCrops() {
		return this.crops;
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
	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}
	
	public java.util.Date getCtime() {
		return this.ctime;
	}
	public void setGps(java.lang.String gps) {
		this.gps = gps;
	}
	
	public java.lang.String getGps() {
		return this.gps;
	}
	public void setAcreage(java.lang.String acreage) {
		this.acreage = acreage;
	}
	
	public java.lang.String getAcreage() {
		return this.acreage;
	}
	public void setState(java.lang.String state) {
		this.state = state;
	}
	
	public java.lang.String getState() {
		return this.state;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	
	public java.lang.String getUuid() {
		return this.uuid;
	}
	public void setBaseId(java.lang.Long baseId) {
		this.baseId = baseId;
	}
	
	public java.lang.Long getBaseId() {
		return this.baseId;
	}
	public void setGeoInfo(java.lang.String geoInfo) {
		this.geoInfo = geoInfo;
	}
	
	public java.lang.String getGeoInfo() {
		return this.geoInfo;
	}
}