/*
 * Powered By Generator Util
 */
package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.component.BulletinComponent;
import com.cattsoft.baseplatform.func.sm.entity.Bulletin;
import com.cattsoft.baseplatform.func.sm.entity.query.BulletinQB;
import com.cattsoft.baseplatform.func.sm.service.BulletinService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("bulletinService")
@Transactional
public class BulletinServiceImpl implements BulletinService {
	
	Log log = LogFactory.getLog(BulletinServiceImpl.class);
	
	@Autowired
	private BulletinComponent bulletinComponent;

	@Override
	public Long createBulletin(Bulletin bulletin) {
		return bulletinComponent.createBulletin(bulletin);
	}

	@Override
	public void updateBulletin(Bulletin bulletin) {
		bulletinComponent.updateBulletin(bulletin);
	}

	@Override
	public void removeBulletin(Long bulletinId) {
		bulletinComponent.removeBulletin(bulletinId);
	}

	@Transactional(readOnly = true)
	@Override
	public Bulletin getBulletin(Long bulletinId) {
		return bulletinComponent.getBulletin(bulletinId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Bulletin> getAllBulletins() {
		return bulletinComponent.getAllBulletins();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Bulletin> getBulletins(BulletinQB queryBean) {
		return bulletinComponent.getBulletins(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<Bulletin>> getAllBulletinsPaging(PagingQueryBean<BulletinQB> qb) {
		return bulletinComponent.getAllBulletinsPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setBulletinComponent(BulletinComponent bulletinComponent) {
		this.bulletinComponent = bulletinComponent;
	}
}
