package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.func.sm.component.UnitComponent;
import com.cattsoft.baseplatform.func.sm.entity.Unit;

/**
 * 单位服务类
 */
@Transactional
public class UnitServiceImpl implements UnitService {
	private UnitComponent unitComponent;

	public void setUnitComponent(UnitComponent unitComponent) {
		this.unitComponent = unitComponent;
	}

	/**
	 * 查询所有单位
	 * 
	 * @return
	 */
	@Override
	public List<Unit> getAllUnit() {
		return unitComponent.getAllUnit();
	}
	
	@Override
	public List<Unit> getUnitsByType(String unitType){
		return unitComponent.getUnitsByType(unitType);
	}
}
