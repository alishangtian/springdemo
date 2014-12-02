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
import com.cattsoft.ny.base.component.SeaboMxn880StatusResultLComponent;
import com.cattsoft.ny.base.entity.SeaboMxn880StatusResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxn880StatusResultLQB;
import com.cattsoft.ny.base.service.SeaboMxn880StatusResultLService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("seaboMxn880StatusResultLService")
@Transactional
public class SeaboMxn880StatusResultLServiceImpl implements SeaboMxn880StatusResultLService {
	
	Log log = LogFactory.getLog(SeaboMxn880StatusResultLServiceImpl.class);
	
	@Autowired
	private SeaboMxn880StatusResultLComponent seaboMxn880StatusResultLComponent;

	@Override
	public Long createSeaboMxn880StatusResultL(SeaboMxn880StatusResultL seaboMxn880StatusResultL) {
		return seaboMxn880StatusResultLComponent.createSeaboMxn880StatusResultL(seaboMxn880StatusResultL);
	}

	@Override
	public void updateSeaboMxn880StatusResultL(SeaboMxn880StatusResultL seaboMxn880StatusResultL) {
		seaboMxn880StatusResultLComponent.updateSeaboMxn880StatusResultL(seaboMxn880StatusResultL);
	}

	@Override
	public void removeSeaboMxn880StatusResultL(Long nodeid) {
		seaboMxn880StatusResultLComponent.removeSeaboMxn880StatusResultL(nodeid);
	}

	@Transactional(readOnly = true)
	@Override
	public SeaboMxn880StatusResultL getSeaboMxn880StatusResultL(Long nodeid) {
		return seaboMxn880StatusResultLComponent.getSeaboMxn880StatusResultL(nodeid);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SeaboMxn880StatusResultL> getAllSeaboMxn880StatusResultLs() {
		return seaboMxn880StatusResultLComponent.getAllSeaboMxn880StatusResultLs();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<SeaboMxn880StatusResultL> getSeaboMxn880StatusResultLs(SeaboMxn880StatusResultLQB queryBean) {
		return seaboMxn880StatusResultLComponent.getSeaboMxn880StatusResultLs(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SeaboMxn880StatusResultL>> getAllSeaboMxn880StatusResultLsPaging(PagingQueryBean<SeaboMxn880StatusResultLQB> qb) {
		return seaboMxn880StatusResultLComponent.getAllSeaboMxn880StatusResultLsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setSeaboMxn880StatusResultLComponent(SeaboMxn880StatusResultLComponent seaboMxn880StatusResultLComponent) {
		this.seaboMxn880StatusResultLComponent = seaboMxn880StatusResultLComponent;
	}
}
