package com.cattsoft.ny.base.entity.querybean;

import com.cattsoft.baseplatform.core.entity.QueryBean;

/**
 * 
 * @author John
 * 
 */
public class EquipControlInfoQB extends QueryBean {
	private static final long serialVersionUID = 1L;

	private java.lang.Long id;
	private java.lang.Long type;
	private java.lang.String name;
	private java.lang.Long port;
	private java.lang.String equipId;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getType() {
		return type;
	}

	public void setType(java.lang.Long type) {
		this.type = type;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getEquipId() {
		return equipId;
	}

	public void setEquipId(java.lang.String equipId) {
		this.equipId = equipId;
	}

	/**
	 * @return the port
	 */
	public java.lang.Long getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(java.lang.Long port) {
		this.port = port;
	}

}