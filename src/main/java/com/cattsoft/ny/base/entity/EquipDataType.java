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
public class EquipDataType extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.String name;
	private java.lang.String units;
	private java.util.Date time;
	private java.lang.String remark;
	private java.lang.Long equipInfoId;
	private java.lang.String acquType;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setUnits(java.lang.String units) {
		this.units = units;
	}
	
	public java.lang.String getUnits() {
		return this.units;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setEquipInfoId(java.lang.Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}
	
	public java.lang.Long getEquipInfoId() {
		return this.equipInfoId;
	}

	public java.lang.String getAcquType() {
		return acquType;
	}

	public void setAcquType(java.lang.String acquType) {
		this.acquType = acquType;
	}
 
}