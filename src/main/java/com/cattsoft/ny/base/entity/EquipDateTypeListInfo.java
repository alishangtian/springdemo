package com.cattsoft.ny.base.entity;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.Entity;

public class EquipDateTypeListInfo extends Entity{
	private static final long serialVersionUID = 3623630231286001705L;
	private List<EquipDataEnvdataTypeInfo> equipDataEnvdataTypeInfo;
	private String equipInfoName;
	private String baseName;
	private String houseName;
	public List<EquipDataEnvdataTypeInfo> getEquipDataEnvdataTypeInfo() {
		return equipDataEnvdataTypeInfo;
	}
	public void setEquipDataEnvdataTypeInfo(
			List<EquipDataEnvdataTypeInfo> equipDataEnvdataTypeInfo) {
		this.equipDataEnvdataTypeInfo = equipDataEnvdataTypeInfo;
	}
	public String getEquipInfoName() {
		return equipInfoName;
	}
	public void setEquipInfoName(String equipInfoName) {
		this.equipInfoName = equipInfoName;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	
}
