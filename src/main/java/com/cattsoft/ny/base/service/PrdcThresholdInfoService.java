/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.PrdcThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.PrdcThresholdInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface PrdcThresholdInfoService {
	/**
	 * 增加prdc_threshold_info信息
	 * 
	 * @param prdcThresholdInfo prdc_threshold_info信息
	 *
	 * @return prdc_threshold_info标识
	 */
	Long createPrdcThresholdInfo(PrdcThresholdInfo prdcThresholdInfo);

	/**
	 * 修改prdc_threshold_info信息
	 * 
	 * @param prdcThresholdInfo prdc_threshold_info信息
	 */
	void updatePrdcThresholdInfo(PrdcThresholdInfo prdcThresholdInfo);

	/**
	 * 删除prdc_threshold_info信息
	 * 
	 * @param id prdc_threshold_info标识
	 */
	void removePrdcThresholdInfo(Long id);

	/**
	 * 获取prdc_threshold_info信息
	 * 
	 * @param id prdc_threshold_info标识
	 * @return prdc_threshold_info信息
	 */
	PrdcThresholdInfo getPrdcThresholdInfo(Long id);

	/**
	 * 获取所有prdc_threshold_info
	 * 
	 * @return 所有prdc_threshold_info信息的列表
	 */
	List<PrdcThresholdInfo> getAllPrdcThresholdInfos();
	
	/**
	 * 根据查询对象查询prdc_threshold_info结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  prdc_threshold_info记录列表
	 */
	List<PrdcThresholdInfo> getPrdcThresholdInfos(PrdcThresholdInfoQB queryBean);

	/**
	 * 分页获取prdc_threshold_info列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页prdc_threshold_info列表
	 */
	PagingResultBean<List<PrdcThresholdInfo>> getAllPrdcThresholdInfosPaging(PagingQueryBean<PrdcThresholdInfoQB> pagingQueryBean);
}
