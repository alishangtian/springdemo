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
import com.cattsoft.ny.base.component.EquipDataEnvdataComponent;
import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataEnvdataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipDataEnvdataQB;
import com.cattsoft.ny.base.service.EquipDataEnvdataService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipDataEnvdataService")
@Transactional
public class EquipDataEnvdataServiceImpl implements EquipDataEnvdataService {
	
	Log log = LogFactory.getLog(EquipDataEnvdataServiceImpl.class);
	
	@Autowired
	private EquipDataEnvdataComponent equipDataEnvdataComponent;

	@Override
	public Long createEquipDataEnvdata(EquipDataEnvdata equipDataEnvdata) {
		return equipDataEnvdataComponent.createEquipDataEnvdata(equipDataEnvdata);
	}

	@Override
	public void updateEquipDataEnvdata(EquipDataEnvdata equipDataEnvdata) {
		equipDataEnvdataComponent.updateEquipDataEnvdata(equipDataEnvdata);
	}

	@Override
	public void removeEquipDataEnvdata(Long dataId) {
		equipDataEnvdataComponent.removeEquipDataEnvdata(dataId);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipDataEnvdata getEquipDataEnvdata(Long dataId) {
		return equipDataEnvdataComponent.getEquipDataEnvdata(dataId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipDataEnvdata> getAllEquipDataEnvdatas() {
		return equipDataEnvdataComponent.getAllEquipDataEnvdatas();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<EquipDataEnvdata> getEquipDataEnvdatas(EquipDataEnvdataQB queryBean) {
		return equipDataEnvdataComponent.getEquipDataEnvdatas(queryBean);
	}
	@Transactional(readOnly = true)
	@Override
	public List<EquipDataEnvdata> getEquipDataEnvdatasTime(EquipDataEnvdataQB queryBean) {
		return equipDataEnvdataComponent.getEquipDataEnvdatasTime(queryBean);
	}
	@Transactional(readOnly = true)
	@Override
	public List<EquipDataEnvdataInfo> getEquipDataEnvdatasInfo(Long typeid) {
		return equipDataEnvdataComponent.getEquipDataEnvdatasInfo(typeid);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipDataEnvdata>> getAllEquipDataEnvdatasPaging(PagingQueryBean<EquipDataEnvdataQB> qb) {
		return equipDataEnvdataComponent.getAllEquipDataEnvdatasPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipDataEnvdataComponent(EquipDataEnvdataComponent equipDataEnvdataComponent) {
		this.equipDataEnvdataComponent = equipDataEnvdataComponent;
	}

	@Override
	public List<EquipDataEnvdata> getMaxMinData(EquipDataEnvdataQB queryBean) {
		// TODO Auto-generated method stub
		return equipDataEnvdataComponent.maxMinData(queryBean);
	}
}
