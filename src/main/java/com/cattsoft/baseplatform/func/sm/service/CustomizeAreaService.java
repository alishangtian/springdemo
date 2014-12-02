package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.CustomizeArea;

public interface CustomizeAreaService {
	/**
	 * 获取自定义区域根节点
	 * 
	 * @return
	 */
	CustomizeArea getRootArea();

	/**
	 * 获取自定义区域
	 * 
	 * @param areaId
	 * @return
	 */
	CustomizeArea getArea(Long areaId);

	/**
	 * 获取自定义区域的直属下级区域
	 * 
	 * @param areaId
	 * @return
	 */
	List<CustomizeArea> getSubAreas(Long areaId);

	/**
	 * 新增自定义区域
	 * 
	 * @param customizeArea
	 */
	void createArea(CustomizeArea customizeArea);

	/**
	 * 更新自定义区域
	 * 
	 * @param customizeArea
	 */
	void modifyArea(CustomizeArea customizeArea);

	/**
	 * 删除新增区域：级联删除所有下级区域
	 * 
	 * @param areaId
	 */
	void removeArea(Long areaId);

	/**
	 * 依据代码获取自定义区域
	 * 
	 * @param areaCode
	 * @return
	 */
	CustomizeArea getAreaByCode(String areaCode);
}
