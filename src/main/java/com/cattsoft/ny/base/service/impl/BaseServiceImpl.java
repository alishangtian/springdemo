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
import com.cattsoft.ny.base.component.BaseComponent;
import com.cattsoft.ny.base.entity.Base;
import com.cattsoft.ny.base.entity.querybean.BaseQB;
import com.cattsoft.ny.base.service.BaseService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {
	
	Log log = LogFactory.getLog(BaseServiceImpl.class);
	
	@Autowired
	private BaseComponent baseComponent;

	@Override
	public Long createBase(Base base) {
		return baseComponent.createBase(base);
	}

	@Override
	public void updateBase(Base base) {
		baseComponent.updateBase(base);
	}

	@Override
	public void removeBase(Long id) {
		baseComponent.removeBase(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Base getBase(Long id) {
		return baseComponent.getBase(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Base> getAllBases() {
		return baseComponent.getAllBases();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Base> getBases(BaseQB queryBean) {
		return baseComponent.getBases(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<Base>> getAllBasesPaging(PagingQueryBean<BaseQB> qb) {
		return baseComponent.getAllBasesPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setBaseComponent(BaseComponent baseComponent) {
		this.baseComponent = baseComponent;
	}
}
