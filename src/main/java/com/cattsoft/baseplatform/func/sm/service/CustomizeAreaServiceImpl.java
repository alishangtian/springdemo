package com.cattsoft.baseplatform.func.sm.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.util.DateUtil;
import com.cattsoft.baseplatform.func.sm.component.CustomizeAreaComponent;
import com.cattsoft.baseplatform.func.sm.entity.CustomizeArea;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;

@Transactional
public class CustomizeAreaServiceImpl implements CustomizeAreaService {
	private CustomizeAreaComponent customizeAreaComponent;

	public void setCustomizeAreaComponent(CustomizeAreaComponent customizeAreaComponent) {
		this.customizeAreaComponent = customizeAreaComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public CustomizeArea getRootArea() {
		return this.customizeAreaComponent.getRootArea();
	}

	@Transactional(readOnly = true)
	@Override
	public CustomizeArea getArea(Long areaId) {
		return this.customizeAreaComponent.getArea(areaId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<CustomizeArea> getSubAreas(Long areaId) {
		return this.customizeAreaComponent.getSubAreas(areaId);
	}

	@Override
	public void createArea(CustomizeArea customizeArea) {
		Timestamp time = DateUtil.getCurrentTimestamp();
		customizeArea.setSts(SysConstants.Status.STS_A);
		customizeArea.setStsTime(time);
		customizeArea.setCreateTime(time);
		this.customizeAreaComponent.createArea(customizeArea);
	}

	@Override
	public void modifyArea(CustomizeArea customizeArea) {
		this.customizeAreaComponent.updateArea(customizeArea);
	}

	@Override
	public void removeArea(Long areaId) {
		List<CustomizeArea> areas = this.customizeAreaComponent.getAreaCascade(areaId);
		for (CustomizeArea area : areas) {
			area.setSts(SysConstants.Status.STS_P);
			this.customizeAreaComponent.updateArea(area);
		}
	}

	@Override
	public CustomizeArea getAreaByCode(String areaCode) {
		return this.customizeAreaComponent.getAreaByCode(areaCode);
	}

}
