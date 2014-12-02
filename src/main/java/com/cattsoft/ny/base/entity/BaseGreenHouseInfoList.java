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
public class BaseGreenHouseInfoList extends Entity {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long houseId;
	private java.lang.String houseName;
	private java.lang.Long baseId;
    private String baseName;
    private Integer cgqCount;
    private Integer kzqCount;
	public java.lang.Long getHouseId() {
		return houseId;
	}
	public void setHouseId(java.lang.Long houseId) {
		this.houseId = houseId;
	}
	public java.lang.String getHouseName() {
		return houseName;
	}
	public void setHouseName(java.lang.String houseName) {
		this.houseName = houseName;
	}
	public java.lang.Long getBaseId() {
		return baseId;
	}
	public void setBaseId(java.lang.Long baseId) {
		this.baseId = baseId;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getCgqCount() {
		return cgqCount;
	}
	public void setCgqCount(Integer cgqCount) {
		this.cgqCount = cgqCount;
	}
	public Integer getKzqCount() {
		return kzqCount;
	}
	public void setKzqCount(Integer kzqCount) {
		this.kzqCount = kzqCount;
	}
    
    
}