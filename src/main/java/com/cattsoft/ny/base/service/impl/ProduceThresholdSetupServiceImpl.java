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
import com.cattsoft.ny.base.component.ProduceThresholdSetupComponent;
import com.cattsoft.ny.base.entity.ProduceThresholdSetup;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdSetupQB;
import com.cattsoft.ny.base.service.ProduceThresholdSetupService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("produceThresholdSetupService")
@Transactional
public class ProduceThresholdSetupServiceImpl implements ProduceThresholdSetupService {
	
	Log log = LogFactory.getLog(ProduceThresholdSetupServiceImpl.class);
	
	@Autowired
	private ProduceThresholdSetupComponent produceThresholdSetupComponent;

	@Override
	public Long createProduceThresholdSetup(ProduceThresholdSetup produceThresholdSetup) {
		return produceThresholdSetupComponent.createProduceThresholdSetup(produceThresholdSetup);
	}

	@Override
	public void updateProduceThresholdSetup(ProduceThresholdSetup produceThresholdSetup) {
		produceThresholdSetupComponent.updateProduceThresholdSetup(produceThresholdSetup);
	}

	@Override
	public void removeProduceThresholdSetup(Long id) {
		produceThresholdSetupComponent.removeProduceThresholdSetup(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ProduceThresholdSetup getProduceThresholdSetup(Long id) {
		return produceThresholdSetupComponent.getProduceThresholdSetup(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProduceThresholdSetup> getAllProduceThresholdSetups() {
		return produceThresholdSetupComponent.getAllProduceThresholdSetups();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProduceThresholdSetup> getProduceThresholdSetups(ProduceThresholdSetupQB queryBean) {
		return produceThresholdSetupComponent.getProduceThresholdSetups(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<ProduceThresholdSetup>> getAllProduceThresholdSetupsPaging(PagingQueryBean<ProduceThresholdSetupQB> qb) {
		return produceThresholdSetupComponent.getAllProduceThresholdSetupsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceThresholdSetupComponent(ProduceThresholdSetupComponent produceThresholdSetupComponent) {
		this.produceThresholdSetupComponent = produceThresholdSetupComponent;
	}
}
