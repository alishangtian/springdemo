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
import com.cattsoft.ny.base.component.PickWorksInfoComponent;
import com.cattsoft.ny.base.entity.CropsResult;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.querybean.NewsQB;
import com.cattsoft.ny.base.entity.querybean.PickWorkInfoDataQB;
import com.cattsoft.ny.base.entity.querybean.PickWorksInfoQB;
import com.cattsoft.ny.base.service.PickWorksInfoService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("pickWorksInfoService")
@Transactional
public class PickWorksInfoServiceImpl implements PickWorksInfoService {
	
	Log log = LogFactory.getLog(PickWorksInfoServiceImpl.class);
	
	@Autowired
	private PickWorksInfoComponent pickWorksInfoComponent;

	@Override
	public Long createPickWorksInfo(PickWorksInfo pickWorksInfo) {
		return pickWorksInfoComponent.createPickWorksInfo(pickWorksInfo);
	}

	@Override
	public void updatePickWorksInfo(PickWorksInfo pickWorksInfo) {
		pickWorksInfoComponent.updatePickWorksInfo(pickWorksInfo);
	}

	@Override
	public void removePickWorksInfo(Long id) {
		pickWorksInfoComponent.removePickWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public PickWorksInfo getPickWorksInfo(Long id) {
		return pickWorksInfoComponent.getPickWorksInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<PickWorksInfo> getAllPickWorksInfos() {
		return pickWorksInfoComponent.getAllPickWorksInfos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<PickWorksInfo> getPickWorksInfos(PickWorksInfoQB queryBean) {
		return pickWorksInfoComponent.getPickWorksInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<PickWorksInfo>> getAllPickWorksInfosPaging(PagingQueryBean<PickWorksInfoQB> qb) {
		return pickWorksInfoComponent.getAllPickWorksInfosPaging(qb);
	}
	@Override
	public List<CropsResult> queryCropsResult(PickWorkInfoDataQB pickWorkInfoDataQB) {
		// TODO Auto-generated method stub
		return pickWorksInfoComponent.queryCropsResult(pickWorkInfoDataQB);
	}
	
	public List<News> getNews(NewsQB newsQB){
		return pickWorksInfoComponent.getNews(newsQB);
	}

	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPickWorksInfoComponent(PickWorksInfoComponent pickWorksInfoComponent) {
		this.pickWorksInfoComponent = pickWorksInfoComponent;
	}

	@Override
	public List<CropsResult> queryCropsDataResult(
			PickWorkInfoDataQB pickWorkInfoDataQB) {
		// TODO Auto-generated method stub
		return pickWorksInfoComponent.queryCropsResultData(pickWorkInfoDataQB);
	}
	

	
}
