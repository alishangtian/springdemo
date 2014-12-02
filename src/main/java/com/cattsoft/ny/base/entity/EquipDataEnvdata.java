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
public class EquipDataEnvdata extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long dataId;
	private java.lang.Float value;
	private java.util.Date ctime;
	private java.lang.Long equipDataTypeId;
	private java.lang.Float max;
	private java.lang.Float min;
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

	public java.lang.Float getMax() {
		return max;
	}

	public void setMax(java.lang.Float max) {
		this.max = max;
	}

	public java.lang.Float getMin() {
		return min;
	}

	public void setMin(java.lang.Float min) {
		this.min = min;
	}
	
}