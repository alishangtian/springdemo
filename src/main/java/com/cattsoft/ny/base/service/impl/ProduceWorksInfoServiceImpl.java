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
import com.cattsoft.ny.base.component.ProduceWorksInfoComponent;
import com.cattsoft.ny.base.entity.ProduceWorksInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceWorksInfoQB;
import com.cattsoft.ny.base.service.ProduceWorksInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("produceWorksInfoService")
@Transactional
public class ProduceWorksInfoServiceImpl implements ProduceWorksInfoService {
	
	Log log = LogFactory.getLog(ProduceWorksInfoServiceImpl.class);
	
	@Autowired
	private ProduceWorksInfoComponent produceWorksInfoComponent;

	@Override
	public Long createProduceWorksInfo(ProduceWorksInfo produceWorksInfo) {
		return produceWorksInfoComponent.createProduceWorksInfo(produceWorksInfo);
	}

	@Override
	public void updateProduceWorksInfo(ProduceWorksInfo produceWorksInfo) {
		produceWorksInfoComponent.updateProduceWorksInfo(produceWorksInfo);
	}

	@Override
	public void removeProduceWorksInfo(Long id) {
		produceWorksInfoComponent.removeProduceWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ProduceWorksInfo getProduceWorksInfo(Long id) {
		return produceWorksInfoComponent.getProduceWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProduceWorksInfo> getAllProduceWorksInfos() {
		return produceWorksInfoComponent.getAllProduceWorksInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProduceWorksInfo> getProduceWorksInfos(ProduceWorksInfoQB queryBean) {
		return produceWorksInfoComponent.getProduceWorksInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<ProduceWorksInfo>> getAllProduceWorksInfosPaging(PagingQueryBean<ProduceWorksInfoQB> qb) {
		return produceWorksInfoComponent.getAllProduceWorksInfosPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setProduceWorksInfoComponent(ProduceWorksInfoComponent produceWorksInfoComponent) {
		this.produceWorksInfoComponent = produceWorksInfoComponent;
	}
}
