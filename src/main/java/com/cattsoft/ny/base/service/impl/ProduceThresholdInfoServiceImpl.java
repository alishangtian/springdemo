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
import com.cattsoft.ny.base.component.ProduceThresholdInfoComponent;
import com.cattsoft.ny.base.entity.ProduceThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdInfoQB;
import com.cattsoft.ny.base.service.ProduceThresholdInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("produceThresholdInfoService")
@Transactional
public class ProduceThresholdInfoServiceImpl implements ProduceThresholdInfoService {
	
	Log log = LogFactory.getLog(ProduceThresholdInfoServiceImpl.class);
	
	@Autowired
	private ProduceThresholdInfoComponent produceThresholdInfoComponent;

	@Override
	public Long createProduceThresholdInfo(ProduceThresholdInfo produceThresholdInfo) {
		return produceThresholdInfoComponent.createProduceThresholdInfo(produceThresholdInfo);
	}

	@Override
	public void updateProduceThresholdInfo(ProduceThresholdInfo produceThresholdInfo) {
		produceThresholdInfoComponent.updateProduceThresholdInfo(produceThresholdInfo);
	}

	@Override
	public void removeProduceThresholdInfo(Long id) {
		produceThresholdInfoComponent.removeProduceThresholdInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ProduceThresholdInfo getProduceThresholdInfo(Long id) {
		return produceThresholdInfoComponent.getProduceThresholdInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProduceThresholdInfo> getAllProduceThresholdInfos() {
		return produceThresholdInfoComponent.getAllProduceThresholdInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProduceThresholdInfo> getProduceThresholdInfos(ProduceThresholdInfoQB queryBean) {
		return produceThresholdInfoComponent.getProduceThresholdInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<ProduceThresholdInfo>> getAllProduceThresholdInfosPaging(PagingQueryBean<ProduceThresholdInfoQB> qb) {
		return produceThresholdInfoComponent.getAllProduceThresholdInfosPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceThresholdInfoComponent(ProduceThresholdInfoComponent produceThresholdInfoComponent) {
		this.produceThresholdInfoComponent = produceThresholdInfoComponent;
	}
}
