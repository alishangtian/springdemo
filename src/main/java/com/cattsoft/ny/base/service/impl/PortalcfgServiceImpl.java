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
import com.cattsoft.ny.base.component.PortalcfgComponent;
import com.cattsoft.ny.base.entity.Portalcfg;
import com.cattsoft.ny.base.entity.querybean.PortalcfgQB;
import com.cattsoft.ny.base.service.PortalcfgService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("portalcfgService")
@Transactional
public class PortalcfgServiceImpl implements PortalcfgService {
	
	Log log = LogFactory.getLog(PortalcfgServiceImpl.class);
	
	@Autowired
	private PortalcfgComponent portalcfgComponent;

	@Override
	public Long createPortalcfg(Portalcfg portalcfg) {
		return portalcfgComponent.createPortalcfg(portalcfg);
	}

	@Override
	public void updatePortalcfg(Portalcfg portalcfg) {
		portalcfgComponent.updatePortalcfg(portalcfg);
	}

	@Override
	public void removePortalcfg(Long portalId) {
		portalcfgComponent.removePortalcfg(portalId);
	}

	@Transactional(readOnly = true)
	@Override
	public Portalcfg getPortalcfg(Long portalId) {
		return portalcfgComponent.getPortalcfg(portalId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Portalcfg> getAllPortalcfgs() {
		return portalcfgComponent.getAllPortalcfgs();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Portalcfg> getPortalcfgs(PortalcfgQB queryBean) {
		return portalcfgComponent.getPortalcfgs(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<Portalcfg>> getAllPortalcfgsPaging(PagingQueryBean<PortalcfgQB> qb) {
		return portalcfgComponent.getAllPortalcfgsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPortalcfgComponent(PortalcfgComponent portalcfgComponent) {
		this.portalcfgComponent = portalcfgComponent;
	}
}
