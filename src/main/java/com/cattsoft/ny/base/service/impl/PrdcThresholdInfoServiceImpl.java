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
import com.cattsoft.ny.base.component.PrdcThresholdInfoComponent;
import com.cattsoft.ny.base.entity.PrdcThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.PrdcThresholdInfoQB;
import com.cattsoft.ny.base.service.PrdcThresholdInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("prdcThresholdInfoService")
@Transactional
public class PrdcThresholdInfoServiceImpl implements PrdcThresholdInfoService {
	
	Log log = LogFactory.getLog(PrdcThresholdInfoServiceImpl.class);
	
	@Autowired
	private PrdcThresholdInfoComponent prdcThresholdInfoComponent;

	@Override
	public Long createPrdcThresholdInfo(PrdcThresholdInfo prdcThresholdInfo) {
		return prdcThresholdInfoComponent.createPrdcThresholdInfo(prdcThresholdInfo);
	}

	@Override
	public void updatePrdcThresholdInfo(PrdcThresholdInfo prdcThresholdInfo) {
		prdcThresholdInfoComponent.updatePrdcThresholdInfo(prdcThresholdInfo);
	}

	@Override
	public void removePrdcThresholdInfo(Long id) {
		prdcThresholdInfoComponent.removePrdcThresholdInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public PrdcThresholdInfo getPrdcThresholdInfo(Long id) {
		return prdcThresholdInfoComponent.getPrdcThresholdInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<PrdcThresholdInfo> getAllPrdcThresholdInfos() {
		return prdcThresholdInfoComponent.getAllPrdcThresholdInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<PrdcThresholdInfo> getPrdcThresholdInfos(PrdcThresholdInfoQB queryBean) {
		return prdcThresholdInfoComponent.getPrdcThresholdInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<PrdcThresholdInfo>> getAllPrdcThresholdInfosPaging(PagingQueryBean<PrdcThresholdInfoQB> qb) {
		return prdcThresholdInfoComponent.getAllPrdcThresholdInfosPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPrdcThresholdInfoComponent(PrdcThresholdInfoComponent prdcThresholdInfoComponent) {
		this.prdcThresholdInfoComponent = prdcThresholdInfoComponent;
	}
}
