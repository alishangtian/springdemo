/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.ProduceThresholdSetup;
import com.cattsoft.ny.base.entity.querybean.ProduceThresholdSetupQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface ProduceThresholdSetupService {
	/**
	 * 增加produce_threshold_setup信息
	 * 
	 * @param produceThresholdSetup produce_threshold_setup信息
	 *
	 * @return produce_threshold_setup标识
	 */
	Long createProduceThresholdSetup(ProduceThresholdSetup produceThresholdSetup);

	/**
	 * 修改produce_threshold_setup信息
	 * 
	 * @param produceThresholdSetup produce_threshold_setup信息
	 */
	void updateProduceThresholdSetup(ProduceThresholdSetup produceThresholdSetup);

	/**
	 * 删除produce_threshold_setup信息
	 * 
	 * @param id produce_threshold_setup标识
	 */
	void removeProduceThresholdSetup(Long id);

	/**
	 * 获取produce_threshold_setup信息
	 * 
	 * @param id produce_threshold_setup标识
	 * @return produce_threshold_setup信息
	 */
	ProduceThresholdSetup getProduceThresholdSetup(Long id);

	/**
	 * 获取所有produce_threshold_setup
	 * 
	 * @return 所有produce_threshold_setup信息的列表
	 */
	List<ProduceThresholdSetup> getAllProduceThresholdSetups();
	
	/**
	 * 根据查询对象查询produce_threshold_setup结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  produce_threshold_setup记录列表
	 */
	List<ProduceThresholdSetup> getProduceThresholdSetups(ProduceThresholdSetupQB queryBean);

	/**
	 * 分页获取produce_threshold_setup列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页produce_threshold_setup列表
	 */
	PagingResultBean<List<ProduceThresholdSetup>> getAllProduceThresholdSetupsPaging(PagingQueryBean<ProduceThresholdSetupQB> pagingQueryBean);
}
