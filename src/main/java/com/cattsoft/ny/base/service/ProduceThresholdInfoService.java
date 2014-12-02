/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.ProduceThresholdInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface ProduceThresholdInfoService {
	/**
	 * 增加produce_threshold_info信息
	 * 
	 * @param produceThresholdInfo produce_threshold_info信息
	 *
	 * @return produce_threshold_info标识
	 */
	Long createProduceThresholdInfo(ProduceThresholdInfo produceThresholdInfo);

	/**
	 * 修改produce_threshold_info信息
	 * 
	 * @param produceThresholdInfo produce_threshold_info信息
	 */
	void updateProduceThresholdInfo(ProduceThresholdInfo produceThresholdInfo);

	/**
	 * 删除produce_threshold_info信息
	 * 
	 * @param id produce_threshold_info标识
	 */
	void removeProduceThresholdInfo(Long id);

	/**
	 * 获取produce_threshold_info信息
	 * 
	 * @param id produce_threshold_info标识
	 * @return produce_threshold_info信息
	 */
	ProduceThresholdInfo getProduceThresholdInfo(Long id);

	/**
	 * 获取所有produce_threshold_info
	 * 
	 * @return 所有produce_threshold_info信息的列表
	 */
	List<ProduceThresholdInfo> getAllProduceThresholdInfos();
	
	/**
	 * 根据查询对象查询produce_threshold_info结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  produce_threshold_info记录列表
	 */
	List<ProduceThresholdInfo> getProduceThresholdInfos(ProduceThresholdInfoQB queryBean);

	/**
	 * 分页获取produce_threshold_info列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页produce_threshold_info列表
	 */
	PagingResultBean<List<ProduceThresholdInfo>> getAllProduceThresholdInfosPaging(PagingQueryBean<ProduceThresholdInfoQB> pagingQueryBean);
}
