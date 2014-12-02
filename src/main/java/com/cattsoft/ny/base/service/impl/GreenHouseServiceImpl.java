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
import com.cattsoft.ny.base.component.GreenHouseComponent;
import com.cattsoft.ny.base.entity.GreenHouse;
import com.cattsoft.ny.base.entity.querybean.GreenHouseQB;
import com.cattsoft.ny.base.service.GreenHouseService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("greenHouseService")
@Transactional
public class GreenHouseServiceImpl implements GreenHouseService {
	
	Log log = LogFactory.getLog(GreenHouseServiceImpl.class);
	
	@Autowired
	private GreenHouseComponent greenHouseComponent;

	@Override
	public Long createGreenHouse(GreenHouse greenHouse) {
		return greenHouseComponent.createGreenHouse(greenHouse);
	}

	@Override
	public void updateGreenHouse(GreenHouse greenHouse) {
		greenHouseComponent.updateGreenHouse(greenHouse);
	}

	@Override
	public void removeGreenHouse(Long id) {
		greenHouseComponent.removeGreenHouse(id);
	}

	@Transactional(readOnly = true)
	@Override
	public GreenHouse getGreenHouse(Long id) {
		return greenHouseComponent.getGreenHouse(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<GreenHouse> getAllGreenHouses() {
		return greenHouseComponent.getAllGreenHouses();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<GreenHouse> getGreenHouses(GreenHouseQB queryBean) {
		return greenHouseComponent.getGreenHouses(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<GreenHouse>> getAllGreenHousesPaging(PagingQueryBean<GreenHouseQB> qb) {
		return greenHouseComponent.getAllGreenHousesPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setGreenHouseComponent(GreenHouseComponent greenHouseComponent) {
		this.greenHouseComponent = greenHouseComponent;
	}
}
