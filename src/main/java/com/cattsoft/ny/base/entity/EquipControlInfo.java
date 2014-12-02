package com.cattsoft.ny.base.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 控制设备-端口-（风扇，日光灯）存储中间对应关系
 * 
 * @author John
 * 
 */
public class EquipControlInfo extends Entity {

	private static final long serialVersionUID = 3623630231286001705L;
	private java.lang.Long id;
	private java.lang.Long type;
	private java.lang.String name;
	private java.lang.Long port;
	private java.lang.String equipId;
    private String satuts;
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

	public java.lang.String getEquipId() {
		return equipId;
	}

	public void setEquipId(java.lang.String equipId) {
		this.equipId = equipId;
	}

	public java.lang.Long getType() {
		return type;
	}

	public void setType(java.lang.Long type) {
		this.type = type;
	}

	/**
	 * @return the port
	 */
	public java.lang.Long getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(java.lang.Long port) {
		this.port = port;
	}

	public String getSatuts() {
		return satuts;
	}

	public void setSatuts(String satuts) {
		this.satuts = satuts;
	}

}