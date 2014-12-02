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
import com.cattsoft.ny.base.component.EquipStateTypeComponent;
import com.cattsoft.ny.base.entity.EquipStateType;
import com.cattsoft.ny.base.entity.querybean.EquipStateTypeQB;
import com.cattsoft.ny.base.service.EquipStateTypeService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipStateTypeService")
@Transactional
public class EquipStateTypeServiceImpl implements EquipStateTypeService {
	
	Log log = LogFactory.getLog(EquipStateTypeServiceImpl.class);
	
	@Autowired
	private EquipStateTypeComponent equipStateTypeComponent;

	@Override
	public Long createEquipStateType(EquipStateType equipStateType) {
		return equipStateTypeComponent.createEquipStateType(equipStateType);
	}

	@Override
	public void updateEquipStateType(EquipStateType equipStateType) {
		equipStateTypeComponent.updateEquipStateType(equipStateType);
	}

	@Override
	public void removeEquipStateType(Long id) {
		equipStateTypeComponent.removeEquipStateType(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipStateType getEquipStateType(Long id) {
		return equipStateTypeComponent.getEquipStateType(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipStateType> getAllEquipStateTypes() {
		return equipStateTypeComponent.getAllEquipStateTypes();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipStateType> getEquipStateTypes(EquipStateTypeQB queryBean) {
		return equipStateTypeComponent.getEquipStateTypes(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipStateType>> getAllEquipStateTypesPaging(PagingQueryBean<EquipStateTypeQB> qb) {
		return equipStateTypeComponent.getAllEquipStateTypesPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipStateTypeComponent(EquipStateTypeComponent equipStateTypeComponent) {
		this.equipStateTypeComponent = equipStateTypeComponent;
	}
}
