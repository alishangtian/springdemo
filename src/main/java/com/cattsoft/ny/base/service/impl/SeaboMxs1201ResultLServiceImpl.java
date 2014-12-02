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
import com.cattsoft.ny.base.component.SeaboMxs1201ResultLComponent;
import com.cattsoft.ny.base.entity.SeaboMxs1201ResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1201ResultLQB;
import com.cattsoft.ny.base.service.SeaboMxs1201ResultLService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("seaboMxs1201ResultLService")
@Transactional
public class SeaboMxs1201ResultLServiceImpl implements SeaboMxs1201ResultLService {
	
	Log log = LogFactory.getLog(SeaboMxs1201ResultLServiceImpl.class);
	
	@Autowired
	private SeaboMxs1201ResultLComponent seaboMxs1201ResultLComponent;

	@Override
	public Long createSeaboMxs1201ResultL(SeaboMxs1201ResultL seaboMxs1201ResultL) {
		return seaboMxs1201ResultLComponent.createSeaboMxs1201ResultL(seaboMxs1201ResultL);
	}

	@Override
	public void updateSeaboMxs1201ResultL(SeaboMxs1201ResultL seaboMxs1201ResultL) {
		seaboMxs1201ResultLComponent.updateSeaboMxs1201ResultL(seaboMxs1201ResultL);
	}

	@Override
	public void removeSeaboMxs1201ResultL(Long nodeid) {
		seaboMxs1201ResultLComponent.removeSeaboMxs1201ResultL(nodeid);
	}

	@Transactional(readOnly = true)
	@Override
	public SeaboMxs1201ResultL getSeaboMxs1201ResultL(Long nodeid) {
		return seaboMxs1201ResultLComponent.getSeaboMxs1201ResultL(nodeid);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SeaboMxs1201ResultL> getAllSeaboMxs1201ResultLs() {
		return seaboMxs1201ResultLComponent.getAllSeaboMxs1201ResultLs();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<SeaboMxs1201ResultL> getSeaboMxs1201ResultLs(SeaboMxs1201ResultLQB queryBean) {
		return seaboMxs1201ResultLComponent.getSeaboMxs1201ResultLs(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SeaboMxs1201ResultL>> getAllSeaboMxs1201ResultLsPaging(PagingQueryBean<SeaboMxs1201ResultLQB> qb) {
		return seaboMxs1201ResultLComponent.getAllSeaboMxs1201ResultLsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setSeaboMxs1201ResultLComponent(SeaboMxs1201ResultLComponent seaboMxs1201ResultLComponent) {
		this.seaboMxs1201ResultLComponent = seaboMxs1201ResultLComponent;
	}
}
