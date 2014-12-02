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
import com.cattsoft.ny.base.component.PrdcConsumerAccidentsComponent;
import com.cattsoft.ny.base.entity.PrdcConsumerAccidents;
import com.cattsoft.ny.base.entity.querybean.PrdcConsumerAccidentsQB;
import com.cattsoft.ny.base.service.PrdcConsumerAccidentsService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("prdcConsumerAccidentsService")
@Transactional
public class PrdcConsumerAccidentsServiceImpl implements PrdcConsumerAccidentsService {
	
	Log log = LogFactory.getLog(PrdcConsumerAccidentsServiceImpl.class);
	
	@Autowired
	private PrdcConsumerAccidentsComponent prdcConsumerAccidentsComponent;

	@Override
	public Long createPrdcConsumerAccidents(PrdcConsumerAccidents prdcConsumerAccidents) {
		return prdcConsumerAccidentsComponent.createPrdcConsumerAccidents(prdcConsumerAccidents);
	}

	@Override
	public void updatePrdcConsumerAccidents(PrdcConsumerAccidents prdcConsumerAccidents) {
		prdcConsumerAccidentsComponent.updatePrdcConsumerAccidents(prdcConsumerAccidents);
	}

	@Override
	public void removePrdcConsumerAccidents(Long id) {
		prdcConsumerAccidentsComponent.removePrdcConsumerAccidents(id);
	}

	@Transactional(readOnly = true)
	@Override
	public PrdcConsumerAccidents getPrdcConsumerAccidents(Long id) {
		return prdcConsumerAccidentsComponent.getPrdcConsumerAccidents(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<PrdcConsumerAccidents> getAllPrdcConsumerAccidentss() {
		return prdcConsumerAccidentsComponent.getAllPrdcConsumerAccidentss();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<PrdcConsumerAccidents> getPrdcConsumerAccidentss(PrdcConsumerAccidentsQB queryBean) {
		return prdcConsumerAccidentsComponent.getPrdcConsumerAccidentss(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<PrdcConsumerAccidents>> getAllPrdcConsumerAccidentssPaging(PagingQueryBean<PrdcConsumerAccidentsQB> qb) {
		return prdcConsumerAccidentsComponent.getAllPrdcConsumerAccidentssPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPrdcConsumerAccidentsComponent(PrdcConsumerAccidentsComponent prdcConsumerAccidentsComponent) {
		this.prdcConsumerAccidentsComponent = prdcConsumerAccidentsComponent;
	}
}
