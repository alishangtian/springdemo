package com.cattsoft.baseplatform.func.sm.persistence;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.CustomizeArea;

public interface CustomizeAreaMapper {
	/**
	 * 获取自定义区域根节点
	 * 
	 * @return
	 */
	CustomizeArea getRootArea();

	/**
	 * 获取自定义区域
	 * 
	 * @return
	 */
	CustomizeArea getArea(Long areaId);

	/**
	 * 获取自定义区域的直属下级区域
	 * 
	 * @param areaPid
	 * @return
	 */
	List<CustomizeArea> getSubAreas(Long areaId);

	/**
	 * 新增自定义区域
	 * 
	 * @param customizeArea
	 */
	void insert(CustomizeArea customizeArea);

	/**
	 * 更新自定义区域
	 * 
	 * @param customizeArea
	 */
	void update(CustomizeArea customizeArea);

	/**
	 * 获取自定义区域及其所有下级区域
	 * 
	 * @param areaId
	 * @return
	 */
	List<CustomizeArea> getAreaCascade(Long areaId);

	/**
	 * 依据代码获取自定义区域
	 * 
	 * @return
	 */
	CustomizeArea getAreaByCode(String areaCode);

}
