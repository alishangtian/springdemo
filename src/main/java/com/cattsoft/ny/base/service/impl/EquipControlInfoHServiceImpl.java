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
import com.cattsoft.ny.base.component.EquipControlInfoHComponent;
import com.cattsoft.ny.base.entity.EquipControlInfoH;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoHQB;
import com.cattsoft.ny.base.service.EquipControlInfoHService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipControlInfoHService")
@Transactional
public class EquipControlInfoHServiceImpl implements EquipControlInfoHService {
	
	Log log = LogFactory.getLog(EquipControlInfoHServiceImpl.class);
	
	@Autowired
	private EquipControlInfoHComponent equipControlInfoHComponent;

	@Override
	public Long createEquipControlInfoH(EquipControlInfoH equipControlInfoH) {
		return equipControlInfoHComponent.createEquipControlInfoH(equipControlInfoH);
	}

	@Override
	public void updateEquipControlInfoH(EquipControlInfoH equipControlInfoH) {
		equipControlInfoHComponent.updateEquipControlInfoH(equipControlInfoH);
	}

	@Override
	public void removeEquipControlInfoH(Long id) {
		equipControlInfoHComponent.removeEquipControlInfoH(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipControlInfoH getEquipControlInfoH(Long id) {
		return equipControlInfoHComponent.getEquipControlInfoH(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipControlInfoH> getAllEquipControlInfoHs() {
		return equipControlInfoHComponent.getAllEquipControlInfoHs();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipControlInfoH> getEquipControlInfoHs(EquipControlInfoHQB queryBean) {
		return equipControlInfoHComponent.getEquipControlInfoHs(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipControlInfoH>> getAllEquipControlInfoHsPaging(PagingQueryBean<EquipControlInfoHQB> qb) {
		return equipControlInfoHComponent.getAllEquipControlInfoHsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipControlInfoHComponent(EquipControlInfoHComponent equipControlInfoHComponent) {
		this.equipControlInfoHComponent = equipControlInfoHComponent;
	}
}
