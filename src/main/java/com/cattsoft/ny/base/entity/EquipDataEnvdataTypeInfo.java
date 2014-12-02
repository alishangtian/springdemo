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
public class EquipDataEnvdataTypeInfo extends Entity {
	private static final long serialVersionUID = 1L;
	private java.lang.Long id;
	private String  name;
	private String  units;
	private java.lang.Long equipInfoId;
	private String acquType;
	private java.util.Date time;
	private java.lang.Long dataId;
	private java.util.Date cTime;
	private java.lang.Long  equipDataTypeId;
	private java.lang.Float value;
	private String equipInfoName;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
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
	public java.lang.Long getEquipInfoId() {
		return equipInfoId;
	}
	public void setEquipInfoId(java.lang.Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}
 
	public java.util.Date getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public java.lang.Long getDataId() {
		return dataId;
	}
	public void setDataId(java.lang.Long dataId) {
		this.dataId = dataId;
	}
	public java.util.Date getcTime() {
		return cTime;
	}
	public void setcTime(java.util.Date cTime) {
		this.cTime = cTime;
	}
	public java.lang.Long getEquipDataTypeId() {
		return equipDataTypeId;
	}
	public void setEquipDataTypeId(java.lang.Long equipDataTypeId) {
		this.equipDataTypeId = equipDataTypeId;
	}
	public java.lang.Float getValue() {
		return value;
	}
	public void setValue(java.lang.Float value) {
		this.value = value;
	}
	public String getAcquType() {
		return acquType;
	}
	public void setAcquType(String acquType) {
		this.acquType = acquType;
	}
	public String getEquipInfoName() {
		return equipInfoName;
	}
	public void setEquipInfoName(String equipInfoName) {
		this.equipInfoName = equipInfoName;
	}
	 
	
}