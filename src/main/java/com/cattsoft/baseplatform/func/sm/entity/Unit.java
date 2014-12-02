package com.cattsoft.baseplatform.func.sm.entity;

import com.cattsoft.baseplatform.core.entity.Entity;

/**
 * 单位实体
 */
public class Unit extends Entity {
	private static final long serialVersionUID = -8798813852055617268L;
	private Long unitId;
	private String unitCode;
	private String unitName;
	private String unitType;
	private Long unitOrder;
	private Integer standardFlag;
	private Long convertRate;
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public Long getUnitOrder() {
		return unitOrder;
	}
	public void setUnitOrder(Long unitOrder) {
		this.unitOrder = unitOrder;
	}
	public Integer getStandardFlag() {
		return standardFlag;
	}
	public void setStandardFlag(Integer standardFlag) {
		this.standardFlag = standardFlag;
	}
	public Long getConvertRate() {
		return convertRate;
	}
	public void setConvertRate(Long convertRate) {
		this.convertRate = convertRate;
	}
	
}
