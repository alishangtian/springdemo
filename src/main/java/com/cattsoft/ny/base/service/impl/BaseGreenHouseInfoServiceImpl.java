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
import com.cattsoft.ny.base.component.BaseGreenHouseInfoComponent;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.service.BaseGreenHouseInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("baseGreenHouseInfoService")
@Transactional
public class BaseGreenHouseInfoServiceImpl implements BaseGreenHouseInfoService {
	
	Log log = LogFactory.getLog(BaseGreenHouseInfoServiceImpl.class);
	
	@Autowired
	private BaseGreenHouseInfoComponent baseGreenHouseInfoComponent;

	@Override
	public Long createBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo) {
		return baseGreenHouseInfoComponent.createBaseGreenHouseInfo(baseGreenHouseInfo);
	}

	@Override
	public void updateBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo) {
		baseGreenHouseInfoComponent.updateBaseGreenHouseInfo(baseGreenHouseInfo);
	}

	@Override
	public void removeBaseGreenHouseInfo(Long id) {
		baseGreenHouseInfoComponent.removeBaseGreenHouseInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public BaseGreenHouseInfo getBaseGreenHouseInfo(Long id) {
		return baseGreenHouseInfoComponent.getBaseGreenHouseInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<BaseGreenHouseInfo> getAllBaseGreenHouseInfos() {
		return baseGreenHouseInfoComponent.getAllBaseGreenHouseInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<BaseGreenHouseInfo> getBaseGreenHouseInfos(BaseGreenHouseInfoQB queryBean) {
		return baseGreenHouseInfoComponent.getBaseGreenHouseInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<BaseGreenHouseInfo>> getAllBaseGreenHouseInfosPaging(PagingQueryBean<BaseGreenHouseInfoQB> qb) {
		return baseGreenHouseInfoComponent.getAllBaseGreenHouseInfosPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setBaseGreenHouseInfoComponent(BaseGreenHouseInfoComponent baseGreenHouseInfoComponent) {
		this.baseGreenHouseInfoComponent = baseGreenHouseInfoComponent;
	}
}
