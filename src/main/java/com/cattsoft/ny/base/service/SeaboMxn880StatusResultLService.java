/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.SeaboMxn880StatusResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxn880StatusResultLQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface SeaboMxn880StatusResultLService {
	/**
	 * 增加seabo_mxn880_status_result_l信息
	 * 
	 * @param seaboMxn880StatusResultL seabo_mxn880_status_result_l信息
	 *
	 * @return seabo_mxn880_status_result_l标识
	 */
	Long createSeaboMxn880StatusResultL(SeaboMxn880StatusResultL seaboMxn880StatusResultL);

	/**
	 * 修改seabo_mxn880_status_result_l信息
	 * 
	 * @param seaboMxn880StatusResultL seabo_mxn880_status_result_l信息
	 */
	void updateSeaboMxn880StatusResultL(SeaboMxn880StatusResultL seaboMxn880StatusResultL);

	/**
	 * 删除seabo_mxn880_status_result_l信息
	 * 
	 * @param nodeid seabo_mxn880_status_result_l标识
	 */
	void removeSeaboMxn880StatusResultL(Long nodeid);

	/**
	 * 获取seabo_mxn880_status_result_l信息
	 * 
	 * @param nodeid seabo_mxn880_status_result_l标识
	 * @return seabo_mxn880_status_result_l信息
	 */
	SeaboMxn880StatusResultL getSeaboMxn880StatusResultL(Long nodeid);

	/**
	 * 获取所有seabo_mxn880_status_result_l
	 * 
	 * @return 所有seabo_mxn880_status_result_l信息的列表
	 */
	List<SeaboMxn880StatusResultL> getAllSeaboMxn880StatusResultLs();
	
	/**
	 * 根据查询对象查询seabo_mxn880_status_result_l结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  seabo_mxn880_status_result_l记录列表
	 */
	List<SeaboMxn880StatusResultL> getSeaboMxn880StatusResultLs(SeaboMxn880StatusResultLQB queryBean);

	/**
	 * 分页获取seabo_mxn880_status_result_l列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页seabo_mxn880_status_result_l列表
	 */
	PagingResultBean<List<SeaboMxn880StatusResultL>> getAllSeaboMxn880StatusResultLsPaging(PagingQueryBean<SeaboMxn880StatusResultLQB> pagingQueryBean);
}
