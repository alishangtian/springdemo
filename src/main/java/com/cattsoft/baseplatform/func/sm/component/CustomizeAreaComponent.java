package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import com.cattsoft.baseplatform.func.sm.entity.CustomizeArea;
import com.cattsoft.baseplatform.func.sm.persistence.CustomizeAreaMapper;

public class CustomizeAreaComponent {
	private CustomizeAreaMapper customizeAreaMapper;

	public void setCustomizeAreaMapper(CustomizeAreaMapper customizeAreaMapper) {
		this.customizeAreaMapper = customizeAreaMapper;
	}

	/**
	 * 获取自定义区域根节点
	 * 
	 * @return
	 */
	public CustomizeArea getRootArea() {
		return this.customizeAreaMapper.getRootArea();
	}

	/**
	 * 获取自定义区域
	 * 
	 * @param areaId
	 * @return
	 */
	public CustomizeArea getArea(Long areaId) {
		return this.customizeAreaMapper.getArea(areaId);
	}

	/**
	 * 获取自定义区域的直属下级区域
	 * 
	 * @param areaId
	 * @return
	 */
	public List<CustomizeArea> getSubAreas(Long areaId) {
		return this.customizeAreaMapper.getSubAreas(areaId);
	}

	/**
	 * 新增自定义区域
	 * 
	 * @param customizeArea
	 */
	public void createArea(CustomizeArea customizeArea) {
		this.customizeAreaMapper.insert(customizeArea);
	}

	/**
	 * 更新自定义区域
	 * 
	 * @param customizeArea
	 */
	public void updateArea(CustomizeArea customizeArea) {
		this.customizeAreaMapper.update(customizeArea);
	}

	/**
	 * 获取自定义区域及其所有下级区域
	 * 
	 * @param areaId
	 * @return
	 */
	public List<CustomizeArea> getAreaCascade(Long areaId) {
		return this.customizeAreaMapper.getAreaCascade(areaId);
	}

	/**
	 * 依据代码获取自定义区域
	 * 
	 * @param areaCode
	 * @return
	 */
	public CustomizeArea getAreaByCode(String areaCode) {
		return this.customizeAreaMapper.getAreaByCode(areaCode);
	}
}
