package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.Unit;

/**
 * 单位映射类
 */
public interface UnitMapper {
	/**
	 * 查询所有单位
	 * 
	 * @return
	 */
	List<Unit> selectAllUnit();

	List<Unit> selectUnitsByType(String unitType);
}
