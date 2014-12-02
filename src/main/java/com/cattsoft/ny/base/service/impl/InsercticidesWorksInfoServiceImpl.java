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
import com.cattsoft.ny.base.component.InsercticidesWorksInfoComponent;
import com.cattsoft.ny.base.entity.InsercticidesWorksInfo;
import com.cattsoft.ny.base.entity.querybean.InsercticidesWorksInfoQB;
import com.cattsoft.ny.base.service.InsercticidesWorksInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("insercticidesWorksInfoService")
@Transactional
public class InsercticidesWorksInfoServiceImpl implements InsercticidesWorksInfoService {
	
	Log log = LogFactory.getLog(InsercticidesWorksInfoServiceImpl.class);
	
	@Autowired
	private InsercticidesWorksInfoComponent insercticidesWorksInfoComponent;

	@Override
	public Long createInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo) {
		return insercticidesWorksInfoComponent.createInsercticidesWorksInfo(insercticidesWorksInfo);
	}

	@Override
	public void updateInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo) {
		insercticidesWorksInfoComponent.updateInsercticidesWorksInfo(insercticidesWorksInfo);
	}

	@Override
	public void removeInsercticidesWorksInfo(Long id) {
		insercticidesWorksInfoComponent.removeInsercticidesWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public InsercticidesWorksInfo getInsercticidesWorksInfo(Long id) {
		return insercticidesWorksInfoComponent.getInsercticidesWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<InsercticidesWorksInfo> getAllInsercticidesWorksInfos() {
	
		return insercticidesWorksInfoComponent.getAllInsercticidesWorksInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<InsercticidesWorksInfo> getInsercticidesWorksInfos(InsercticidesWorksInfoQB queryBean) {
		return insercticidesWorksInfoComponent.getInsercticidesWorksInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<InsercticidesWorksInfo>> getAllInsercticidesWorksInfosPaging(PagingQueryBean<InsercticidesWorksInfoQB> qb) {
		
		return insercticidesWorksInfoComponent.getAllInsercticidesWorksInfosPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setInsercticidesWorksInfoComponent(InsercticidesWorksInfoComponent insercticidesWorksInfoComponent) {
		this.insercticidesWorksInfoComponent = insercticidesWorksInfoComponent;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public InsercticidesWorksInfoComponent getInsercticidesWorksInfoComponent() {
		return insercticidesWorksInfoComponent;
	}
}
