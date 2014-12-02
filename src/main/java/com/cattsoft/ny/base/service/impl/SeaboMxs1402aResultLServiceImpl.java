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
import com.cattsoft.ny.base.component.SeaboMxs1402aResultLComponent;
import com.cattsoft.ny.base.entity.SeaboMxs1402aResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1402aResultLQB;
import com.cattsoft.ny.base.service.SeaboMxs1402aResultLService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("seaboMxs1402aResultLService")
@Transactional
public class SeaboMxs1402aResultLServiceImpl implements SeaboMxs1402aResultLService {
	
	Log log = LogFactory.getLog(SeaboMxs1402aResultLServiceImpl.class);
	
	@Autowired
	private SeaboMxs1402aResultLComponent seaboMxs1402aResultLComponent;

	@Override
	public Long createSeaboMxs1402aResultL(SeaboMxs1402aResultL seaboMxs1402aResultL) {
		return seaboMxs1402aResultLComponent.createSeaboMxs1402aResultL(seaboMxs1402aResultL);
	}

	@Override
	public void updateSeaboMxs1402aResultL(SeaboMxs1402aResultL seaboMxs1402aResultL) {
		seaboMxs1402aResultLComponent.updateSeaboMxs1402aResultL(seaboMxs1402aResultL);
	}

	@Override
	public void removeSeaboMxs1402aResultL(Long nodeid) {
		seaboMxs1402aResultLComponent.removeSeaboMxs1402aResultL(nodeid);
	}

	@Transactional(readOnly = true)
	@Override
	public SeaboMxs1402aResultL getSeaboMxs1402aResultL(Long nodeid) {
		return seaboMxs1402aResultLComponent.getSeaboMxs1402aResultL(nodeid);
	}

	@Transactional(readOnly = true)
	@Override
	public List<SeaboMxs1402aResultL> getAllSeaboMxs1402aResultLs() {
		return seaboMxs1402aResultLComponent.getAllSeaboMxs1402aResultLs();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<SeaboMxs1402aResultL> getSeaboMxs1402aResultLs(SeaboMxs1402aResultLQB queryBean) {
		return seaboMxs1402aResultLComponent.getSeaboMxs1402aResultLs(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<SeaboMxs1402aResultL>> getAllSeaboMxs1402aResultLsPaging(PagingQueryBean<SeaboMxs1402aResultLQB> qb) {
		return seaboMxs1402aResultLComponent.getAllSeaboMxs1402aResultLsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setSeaboMxs1402aResultLComponent(SeaboMxs1402aResultLComponent seaboMxs1402aResultLComponent) {
		this.seaboMxs1402aResultLComponent = seaboMxs1402aResultLComponent;
	}
}
