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
public class EquipStateDataInfo extends Entity {
	private static final long serialVersionUID = 1L;
	private java.lang.Long id;
	private java.lang.Long equipInfoId;
	private java.lang.Double value;
	private java.util.Date time;
	private String name;
	private String units;
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	 
	public void setValue(java.lang.Double value) {
		this.value = value;
	}
	
	public java.lang.Double getValue() {
		return this.value;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}

	public java.lang.Long getEquipInfoId() {
		return equipInfoId;
	}

	public void setEquipInfoId(java.lang.Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
}