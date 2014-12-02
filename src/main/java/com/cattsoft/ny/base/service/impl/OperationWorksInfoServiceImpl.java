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
import com.cattsoft.ny.base.component.OperationWorksInfoComponent;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.OperationWorksInfo;
import com.cattsoft.ny.base.entity.querybean.OperationWorksInfoQB;
import com.cattsoft.ny.base.service.OperationWorksInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("operationWorksInfoService")
@Transactional
public class OperationWorksInfoServiceImpl implements OperationWorksInfoService {
	
	Log log = LogFactory.getLog(OperationWorksInfoServiceImpl.class);
	
	@Autowired
	private OperationWorksInfoComponent operationWorksInfoComponent;

	@Override
	public Long createOperationWorksInfo(OperationWorksInfo operationWorksInfo) {
		return operationWorksInfoComponent.createOperationWorksInfo(operationWorksInfo);
	}

	@Override
	public void updateOperationWorksInfo(OperationWorksInfo operationWorksInfo) {
		operationWorksInfoComponent.updateOperationWorksInfo(operationWorksInfo);
	}

	@Override
	public void removeOperationWorksInfo(Long id) {
		operationWorksInfoComponent.removeOperationWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public OperationWorksInfo getOperationWorksInfo(Long id) {
		return operationWorksInfoComponent.getOperationWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<OperationWorksInfo> getAllOperationWorksInfos() {
		return operationWorksInfoComponent.getAllOperationWorksInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<OperationWorksInfo> getOperationWorksInfos(OperationWorksInfoQB queryBean) {
		return operationWorksInfoComponent.getOperationWorksInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<OperationWorksInfo>> getAllOperationWorksInfosPaging(PagingQueryBean<OperationWorksInfoQB> qb) {
		return operationWorksInfoComponent.getAllOperationWorksInfosPaging(qb);
	}
	public List<News> getNews(){
		return operationWorksInfoComponent.getNews();
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setOperationWorksInfoComponent(OperationWorksInfoComponent operationWorksInfoComponent) {
		this.operationWorksInfoComponent = operationWorksInfoComponent;
	}
}
