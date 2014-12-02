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
public class EquipDataEnvdataInfo extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long dataId;
	private java.lang.Float value;
	private java.util.Date ctime;
	private java.lang.Long equipDataTypeId;
	private String name;
	private String units;
	public void setDataId(java.lang.Long dataId) {
		this.dataId = dataId;
	}
	
	public java.lang.Long getDataId() {
		return this.dataId;
	}
	public void setValue(java.lang.Float value) {
		this.value = value;
	}
	
	public java.lang.Float getValue() {
		return this.value;
	}
	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}
	
	public java.util.Date getCtime() {
		return this.ctime;
	}
	public void setEquipDataTypeId(java.lang.Long equipDataTypeId) {
		this.equipDataTypeId = equipDataTypeId;
	}
	
	public java.lang.Long getEquipDataTypeId() {
		return this.equipDataTypeId;
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