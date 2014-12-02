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
public class EquipStateTypeQB extends QueryBean {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;
	private java.lang.String name;
	private java.lang.Long equipInfoId;
	private java.lang.String units;
	
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
	public void setEquipInfoId(java.lang.Long equipInfoId) {
		this.equipInfoId = equipInfoId;
	}
	
	public java.lang.Long getEquipInfoId() {
		return this.equipInfoId;
	}
	public void setUnits(java.lang.String units) {
		this.units = units;
	}
	
	public java.lang.String getUnits() {
		return this.units;
	}
}