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
public class EquipStateData extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.Long equipStateTypeId;
	private java.lang.Double value;
	private java.util.Date time;
	
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setEquipStateTypeId(java.lang.Long equipStateTypeId) {
		this.equipStateTypeId = equipStateTypeId;
	}
	
	public java.lang.Long getEquipStateTypeId() {
		return this.equipStateTypeId;
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
}