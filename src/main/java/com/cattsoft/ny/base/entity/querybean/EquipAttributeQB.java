/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.entity.querybean;

import com.cattsoft.baseplatform.core.entity.QueryBean;

/**
 * 设备属性查询bean
 * 
 * @author John
 * 
 */
public class EquipAttributeQB extends QueryBean {
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;
	private java.lang.String name;
	private java.lang.String value;
	private java.lang.String equipId;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public java.lang.String getEquipId() {
		return equipId;
	}

	public void setEquipId(java.lang.String equipId) {
		this.equipId = equipId;
	}

}