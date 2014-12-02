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
import com.cattsoft.ny.base.component.ProduceAccidentComponent;
import com.cattsoft.ny.base.component.ProduceSeasonComponent;
import com.cattsoft.ny.base.entity.ProduceAccident;
import com.cattsoft.ny.base.entity.ProduceSeason;
import com.cattsoft.ny.base.entity.querybean.ProduceAccidentQB;
import com.cattsoft.ny.base.entity.querybean.ProduceSeasonQB;
import com.cattsoft.ny.base.service.ProduceSeasonService;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("produceSeasonService")
@Transactional
public class ProduceSeasonServiceImpl implements ProduceSeasonService {

	Log log = LogFactory.getLog(ProduceSeasonServiceImpl.class);

	@Autowired
	private ProduceSeasonComponent produceSeasonComponent;

	@Autowired
	private ProduceAccidentComponent produceAccidentComponent;

	@Override
	public Long createProduceSeason(ProduceSeason produceSeason) {
		return produceSeasonComponent.createProduceSeason(produceSeason);
	}

	@Override
	public void updateProduceSeason(ProduceSeason produceSeason) {
		produceSeasonComponent.updateProduceSeason(produceSeason);
	}

	@Override
	public void removeProduceSeason(Long id) {
		produceSeasonComponent.removeProduceSeason(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ProduceSeason getProduceSeason(Long id) {
		return produceSeasonComponent.getProduceSeason(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProduceSeason> getAllProduceSeasons() {
		return produceSeasonComponent.getAllProduceSeasons();
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProduceSeason> getProduceSeasons(ProduceSeasonQB queryBean) {
		return produceSeasonComponent.getProduceSeasons(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<ProduceSeason>> getAllProduceSeasonsPaging(
			PagingQueryBean<ProduceSeasonQB> qb) {
		return produceSeasonComponent.getAllProduceSeasonsPaging(qb);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setProduceSeasonComponent(
			ProduceSeasonComponent produceSeasonComponent) {
		this.produceSeasonComponent = produceSeasonComponent;
	}

	@Override
	public Long createProduceAccident(ProduceAccident produceAccident) {
		return this.produceAccidentComponent
				.createProduceAccident(produceAccident);
	}

	@Override
	public void deleteProduceAccident(Long id) {
		this.produceAccidentComponent.deleteProduceAccident(id);
	}

	@Override
	public void updateProduceAccient(ProduceAccident produceAccident) {
		this.produceAccidentComponent.updateProduceAccient(produceAccident);
	}

	@Override
	public ProduceAccident findOneProduceAccident(Long id) {
		return this.produceAccidentComponent.findOneProduceAccident(id);
	}

	@Override
	public List<ProduceAccident> findProduceAccident(
			ProduceAccident produceAccident) {
		return this.produceAccidentComponent
				.findProduceAccident(produceAccident);
	}

	@Override
	public PagingResultBean<List<ProduceAccident>> findPageProduceAccident(
			PagingQueryBean<ProduceAccidentQB> pagingQueryBean) {
		PagingResultBean<List<ProduceAccident>> pageData = new PagingResultBean<List<ProduceAccident>>();
		pageData.setResultList(this.produceAccidentComponent
				.findPageProduceAccident(pagingQueryBean));
		pagingQueryBean.getPagingInfo().setTotalRows(
				this.produceAccidentComponent.findPageCount(pagingQueryBean
						.getQueryBean()));
		pageData.setPagingInfo(pagingQueryBean.getPagingInfo());
		return pageData;
	}

	public ProduceAccidentComponent getProduceAccidentComponent() {
		return produceAccidentComponent;
	}

	public void setProduceAccidentComponent(
			ProduceAccidentComponent produceAccidentComponent) {
		this.produceAccidentComponent = produceAccidentComponent;
	}

}
