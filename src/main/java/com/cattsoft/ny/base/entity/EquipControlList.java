package com.cattsoft.ny.base.entity;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.Entity;

public class EquipControlList extends Entity{
	private static final long serialVersionUID = 1L;
	private List<EquipControlInfo> equipControlInfoList;
	private String controName;
	private String controOnOff;
	private String parentName;
	public List<EquipControlInfo> getEquipControlInfoList() {
		return equipControlInfoList;
	}
	public void setEquipControlInfoList(List<EquipControlInfo> equipControlInfoList) {
		this.equipControlInfoList = equipControlInfoList;
	}
	public String getControName() {
		return controName;
	}
	public void setControName(String controName) {
		this.controName = controName;
	}
	public String getControOnOff() {
		return controOnOff;
	}
	public void setControOnOff(String controOnOff) {
		this.controOnOff = controOnOff;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
}
