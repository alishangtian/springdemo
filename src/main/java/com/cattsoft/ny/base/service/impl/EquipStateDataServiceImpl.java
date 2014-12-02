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
import com.cattsoft.ny.base.component.EquipStateDataComponent;
import com.cattsoft.ny.base.entity.EquipStateData;
import com.cattsoft.ny.base.entity.EquipStateDataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipStateDataQB;
import com.cattsoft.ny.base.service.EquipStateDataService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipStateDataService")
@Transactional
public class EquipStateDataServiceImpl implements EquipStateDataService {
	
	Log log = LogFactory.getLog(EquipStateDataServiceImpl.class);
	
	@Autowired
	private EquipStateDataComponent equipStateDataComponent;

	@Override
	public Long createEquipStateData(EquipStateData equipStateData) {
		return equipStateDataComponent.createEquipStateData(equipStateData);
	}

	@Override
	public void updateEquipStateData(EquipStateData equipStateData) {
		equipStateDataComponent.updateEquipStateData(equipStateData);
	}

	@Override
	public void removeEquipStateData(Long id) {
		equipStateDataComponent.removeEquipStateData(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipStateData getEquipStateData(Long id) {
		return equipStateDataComponent.getEquipStateData(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipStateData> getAllEquipStateDatas() {
		return equipStateDataComponent.getAllEquipStateDatas();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipStateData> getEquipStateDatas(EquipStateDataQB queryBean) {
		return equipStateDataComponent.getEquipStateDatas(queryBean);
	}
	@Transactional(readOnly = true)
	@Override
	public List<EquipStateDataInfo> getEquipStateDatasInfo(EquipStateDataQB queryBean) {
		return equipStateDataComponent.getEquipStateDatasInfo(queryBean);
	}
	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipStateData>> getAllEquipStateDatasPaging(PagingQueryBean<EquipStateDataQB> qb) {
		return equipStateDataComponent.getAllEquipStateDatasPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipStateDataComponent(EquipStateDataComponent equipStateDataComponent) {
		this.equipStateDataComponent = equipStateDataComponent;
	}
}
