package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.Unit;
import com.cattsoft.baseplatform.func.sm.persistence.UnitMapper;

/**
 * 单位组件类
 */
public class UnitComponent {

	private UnitMapper unitMapper;

	public void setUnitMapper(UnitMapper unitMapper) {
		this.unitMapper = unitMapper;
	}

	/**
	 * 查询所有单位
	 * 
	 * @return
	 */
	public List<Unit> getAllUnit() {
		return unitMapper.selectAllUnit();
	}

	public List<Unit> getUnitsByType(String unitType) {
		return unitMapper.selectUnitsByType(unitType);
	}

}
