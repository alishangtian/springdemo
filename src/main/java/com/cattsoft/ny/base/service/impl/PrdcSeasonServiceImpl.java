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
import com.cattsoft.ny.base.component.PrdcSeasonComponent;
import com.cattsoft.ny.base.entity.PrdcSeason;
import com.cattsoft.ny.base.entity.querybean.PrdcSeasonQB;
import com.cattsoft.ny.base.service.PrdcSeasonService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("prdcSeasonService")
@Transactional
public class PrdcSeasonServiceImpl implements PrdcSeasonService {
	
	Log log = LogFactory.getLog(PrdcSeasonServiceImpl.class);
	
	@Autowired
	private PrdcSeasonComponent prdcSeasonComponent;

	@Override
	public Long createPrdcSeason(PrdcSeason prdcSeason) {
		return prdcSeasonComponent.createPrdcSeason(prdcSeason);
	}

	@Override
	public void updatePrdcSeason(PrdcSeason prdcSeason) {
		prdcSeasonComponent.updatePrdcSeason(prdcSeason);
	}

	@Override
	public void removePrdcSeason(Long id) {
		prdcSeasonComponent.removePrdcSeason(id);
	}

	@Transactional(readOnly = true)
	@Override
	public PrdcSeason getPrdcSeason(Long id) {
		return prdcSeasonComponent.getPrdcSeason(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<PrdcSeason> getAllPrdcSeasons() {
		return prdcSeasonComponent.getAllPrdcSeasons();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<PrdcSeason> getPrdcSeasons(PrdcSeasonQB queryBean) {
		return prdcSeasonComponent.getPrdcSeasons(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<PrdcSeason>> getAllPrdcSeasonsPaging(PagingQueryBean<PrdcSeasonQB> qb) {
		return prdcSeasonComponent.getAllPrdcSeasonsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPrdcSeasonComponent(PrdcSeasonComponent prdcSeasonComponent) {
		this.prdcSeasonComponent = prdcSeasonComponent;
	}
}
