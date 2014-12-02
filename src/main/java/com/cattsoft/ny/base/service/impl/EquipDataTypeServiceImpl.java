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
import com.cattsoft.ny.base.component.EquipDataTypeComponent;
import com.cattsoft.ny.base.entity.EquipDataEnvdataTypeInfo;
import com.cattsoft.ny.base.entity.EquipDataType;
import com.cattsoft.ny.base.entity.querybean.EquipDataTypeQB;
import com.cattsoft.ny.base.service.EquipDataTypeService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipDataTypeService")
@Transactional
public class EquipDataTypeServiceImpl implements EquipDataTypeService {
	
	Log log = LogFactory.getLog(EquipDataTypeServiceImpl.class);
	
	@Autowired
	private EquipDataTypeComponent equipDataTypeComponent;

	@Override
	public Long createEquipDataType(EquipDataType equipDataType) {
		return equipDataTypeComponent.createEquipDataType(equipDataType);
	}

	@Override
	public void updateEquipDataType(EquipDataType equipDataType) {
		equipDataTypeComponent.updateEquipDataType(equipDataType);
	}

	@Override
	public void removeEquipDataType(Long id) {
		equipDataTypeComponent.removeEquipDataType(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipDataType getEquipDataType(Long id) {
		return equipDataTypeComponent.getEquipDataType(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipDataType> getAllEquipDataTypes() {
		return equipDataTypeComponent.getAllEquipDataTypes();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipDataType> getEquipDataTypes(EquipDataTypeQB queryBean) {
		return equipDataTypeComponent.getEquipDataTypes(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipDataType>> getAllEquipDataTypesPaging(PagingQueryBean<EquipDataTypeQB> qb) {
		return equipDataTypeComponent.getAllEquipDataTypesPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipDataTypeComponent(EquipDataTypeComponent equipDataTypeComponent) {
		this.equipDataTypeComponent = equipDataTypeComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipDataEnvdataTypeInfo> getEquipDataEnvdataTypeInfo(
			Long equipId) {
		return equipDataTypeComponent.getEquipDataEnvdataTypeInfo(equipId);
	}
}
