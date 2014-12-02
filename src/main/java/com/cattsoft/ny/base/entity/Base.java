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
public class Base extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long custId;
	private java.lang.Long regionId;
	private java.lang.String name;
	private java.util.Date ctime;
	private java.lang.String state;
	private java.lang.String remark;
	private java.lang.String uuid;
	private java.lang.String geoInfo;
	private java.lang.String introduction;
	private java.lang.String pic;
	private java.lang.String area;
	private java.lang.String products;
	private java.lang.String address;
	private boolean isParent;
	private java.lang.String stateName;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setCustId(java.lang.Long custId) {
		this.custId = custId;
	}
	
	public java.lang.Long getCustId() {
		return this.custId;
	}
	public void setRegionId(java.lang.Long regionId) {
		this.regionId = regionId;
	}
	
	public java.lang.Long getRegionId() {
		return this.regionId;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}
	
	public java.util.Date getCtime() {
		return this.ctime;
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

	public java.lang.String getGeoInfo() {
		return geoInfo;
	}

	public void setGeoInfo(java.lang.String geoInfo) {
		this.geoInfo = geoInfo;
	}

	public java.lang.String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(java.lang.String introduction) {
		this.introduction = introduction;
	}

	public java.lang.String getPic() {
		return pic;
	}

	public void setPic(java.lang.String pic) {
		this.pic = pic;
	}

	public java.lang.String getArea() {
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	public java.lang.String getProducts() {
		return products;
	}

	public void setProducts(java.lang.String products) {
		this.products = products;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public boolean getParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public java.lang.String getStateName() {
		return stateName;
	}

	public void setStateName(java.lang.String stateName) {
		this.stateName = stateName;
	}
	
}