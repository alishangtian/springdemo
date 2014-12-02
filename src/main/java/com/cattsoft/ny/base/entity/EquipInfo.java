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
public class EquipInfo extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.String deviceId;
	private java.lang.String state;
	private java.lang.String remark;
	private java.lang.String type;
	private java.lang.String name;
	private java.lang.String model;
	private java.lang.String factory;
	private java.lang.Boolean flag;
	private java.lang.String custId;
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setDeviceId(java.lang.String deviceId) {
		this.deviceId = deviceId;
	}
	
	public java.lang.String getDeviceId() {
		return this.deviceId;
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
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setModel(java.lang.String model) {
		this.model = model;
	}
	
	public java.lang.String getModel() {
		return this.model;
	}
	public void setFactory(java.lang.String factory) {
		this.factory = factory;
	}
	
	public java.lang.String getFactory() {
		return this.factory;
	}

	public java.lang.Boolean getFlag() {
		return flag;
	}

	public void setFlag(java.lang.Boolean flag) {
		this.flag = flag;
	}

	public java.lang.String getCustId() {
		return custId;
	}

	public void setCustId(java.lang.String custId) {
		this.custId = custId;
	}
	
}