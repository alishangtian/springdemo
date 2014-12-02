package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.Unit;

/**
 * 单位服务接口类
 */
public interface UnitService {
	/**
	 * 查询所有单位
	 * 
	 * @return
	 */
	public List<Unit> getAllUnit();
	
	/**
	 * 根据单位类型查询单位列表
	 * @param unitType
	 * @return
	 */
	public List<Unit> getUnitsByType(String unitType);

}
