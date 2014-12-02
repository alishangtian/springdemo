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
import com.cattsoft.ny.base.component.EquipNetTopologyComponent;
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;
import com.cattsoft.ny.base.service.EquipNetTopologyService;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipNetTopologyService")
@Transactional
public class EquipNetTopologyServiceImpl implements EquipNetTopologyService {

	Log log = LogFactory.getLog(EquipNetTopologyServiceImpl.class);

	@Autowired
	private EquipNetTopologyComponent equipNetTopologyComponent;

	@Override
	public Long createEquipNetTopology(EquipNetTopology equipNetTopology) {
		return equipNetTopologyComponent
				.createEquipNetTopology(equipNetTopology);
	}

	@Override
	public void updateEquipNetTopology(EquipNetTopology equipNetTopology) {
		equipNetTopologyComponent.updateEquipNetTopology(equipNetTopology);
	}

	@Override
	public void removeEquipNetTopology(Long id) {
		equipNetTopologyComponent.removeEquipNetTopology(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipNetTopology getEquipNetTopology(Long id) {
		return equipNetTopologyComponent.getEquipNetTopology(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipNetTopology> getAllEquipNetTopologys() {
		return equipNetTopologyComponent.getAllEquipNetTopologys();
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipNetTopology> getEquipNetTopologys(
			EquipNetTopologyQB queryBean) {
		return equipNetTopologyComponent.getEquipNetTopologys(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipNetTopology>> getAllEquipNetTopologysPaging(
			PagingQueryBean<EquipNetTopologyQB> qb) {
		return equipNetTopologyComponent.getAllEquipNetTopologysPaging(qb);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setEquipNetTopologyComponent(
			EquipNetTopologyComponent equipNetTopologyComponent) {
		this.equipNetTopologyComponent = equipNetTopologyComponent;
	}

	@Override
	public void removeEquipNetTopologyByEquipId(Long id) {
		this.equipNetTopologyComponent.removeEquipNetTopologyByEquipId(id);
	}

	@Override
	public List<EquipNetTopology> findEquipNetTopologys(EquipNetTopologyQB query) {
		return this.equipNetTopologyComponent.findEquipNetTopologys(query);
	}
}
