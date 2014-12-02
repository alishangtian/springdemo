/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.component.EquipSensorsGhouseComponent;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipSensorsGhouse;
import com.cattsoft.ny.base.entity.querybean.EquipSensorsGhouseQB;
import com.cattsoft.ny.base.service.EquipSensorsGhouseService;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipSensorsGhouseService")
@Transactional
public class EquipSensorsGhouseServiceImpl implements EquipSensorsGhouseService {

	Log log = LogFactory.getLog(EquipSensorsGhouseServiceImpl.class);

	@Autowired
	private EquipSensorsGhouseComponent equipSensorsGhouseComponent;

	@Override
	public Long createEquipSensorsGhouse(EquipSensorsGhouse equipSensorsGhouse) {
		return equipSensorsGhouseComponent
				.createEquipSensorsGhouse(equipSensorsGhouse);
	}

	@Override
	public void updateEquipSensorsGhouse(EquipSensorsGhouse equipSensorsGhouse) {
		equipSensorsGhouseComponent
				.updateEquipSensorsGhouse(equipSensorsGhouse);
	}

	@Override
	public void removeEquipSensorsGhouse(Long id) {
		equipSensorsGhouseComponent.removeEquipSensorsGhouse(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipSensorsGhouse getEquipSensorsGhouse(Long id) {
		return equipSensorsGhouseComponent.getEquipSensorsGhouse(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipSensorsGhouse> getAllEquipSensorsGhouses() {
		return equipSensorsGhouseComponent.getAllEquipSensorsGhouses();
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipSensorsGhouse> getEquipSensorsGhouses(
			EquipSensorsGhouseQB queryBean) {
		return equipSensorsGhouseComponent.getEquipSensorsGhouses(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipSensorsGhouse>> getAllEquipSensorsGhousesPaging(
			PagingQueryBean<EquipSensorsGhouseQB> qb) {
		return equipSensorsGhouseComponent.getAllEquipSensorsGhousesPaging(qb);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setEquipSensorsGhouseComponent(
			EquipSensorsGhouseComponent equipSensorsGhouseComponent) {
		this.equipSensorsGhouseComponent = equipSensorsGhouseComponent;
	}

	/**
	 * 查询符合条件的设备中间表记录，列出相关的设备信息
	 */
	@Override
	public List<EquipInfo> getAllEquipDetail(EquipSensorsGhouseQB queryBean) {
		return equipSensorsGhouseComponent.getAllEquipSensorsGhouses(queryBean);
	}

	@Override
	public void removeEquipSensorsByEquipId(Long equipId) {
		equipSensorsGhouseComponent.removeEquipSensorsByEquipId(equipId);
	}

	@Override
	public EquipSensorsGhouse findOneEquipSensorsGhouse(
			EquipSensorsGhouseQB queryBean) {
		return this.equipSensorsGhouseComponent.findOne(queryBean);
	}

	@Override
	public List<EquipInfo> getEquipSensorGhouseControlEquip(
			EquipSensorsGhouseQB query) {
		return this.equipSensorsGhouseComponent.selectControlEquipInfo(query);
	}
}
