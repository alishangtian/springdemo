package com.cattsoft.baseplatform.func.sm.web;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.Unit;
import com.cattsoft.baseplatform.func.sm.service.UnitService;

/**
 * 单位Action类
 */
public class UnitAction {
	private static final long serialVersionUID = 7860732831955161934L;

	private UnitService unitService;

	private List<Unit> unitList;

	private String unitType;

	/**
	 * 查询所有单位
	 * 
	 * @return
	 */
	public String getAllUnit() {
		unitList = unitService.getAllUnit();
		return "getAllUnit";
	}

	public String getUnitsByType() {
		unitList = unitService.getUnitsByType(unitType);
		return "getUnitsByType";
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<Unit> unitList) {
		this.unitList = unitList;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

}
