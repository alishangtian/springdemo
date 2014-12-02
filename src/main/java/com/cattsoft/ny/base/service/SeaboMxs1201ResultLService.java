/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.SeaboMxs1201ResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1201ResultLQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface SeaboMxs1201ResultLService {
	/**
	 * 增加seabo_mxs1201_result_l信息
	 * 
	 * @param seaboMxs1201ResultL seabo_mxs1201_result_l信息
	 *
	 * @return seabo_mxs1201_result_l标识
	 */
	Long createSeaboMxs1201ResultL(SeaboMxs1201ResultL seaboMxs1201ResultL);

	/**
	 * 修改seabo_mxs1201_result_l信息
	 * 
	 * @param seaboMxs1201ResultL seabo_mxs1201_result_l信息
	 */
	void updateSeaboMxs1201ResultL(SeaboMxs1201ResultL seaboMxs1201ResultL);

	/**
	 * 删除seabo_mxs1201_result_l信息
	 * 
	 * @param nodeid seabo_mxs1201_result_l标识
	 */
	void removeSeaboMxs1201ResultL(Long nodeid);

	/**
	 * 获取seabo_mxs1201_result_l信息
	 * 
	 * @param nodeid seabo_mxs1201_result_l标识
	 * @return seabo_mxs1201_result_l信息
	 */
	SeaboMxs1201ResultL getSeaboMxs1201ResultL(Long nodeid);

	/**
	 * 获取所有seabo_mxs1201_result_l
	 * 
	 * @return 所有seabo_mxs1201_result_l信息的列表
	 */
	List<SeaboMxs1201ResultL> getAllSeaboMxs1201ResultLs();
	
	/**
	 * 根据查询对象查询seabo_mxs1201_result_l结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  seabo_mxs1201_result_l记录列表
	 */
	List<SeaboMxs1201ResultL> getSeaboMxs1201ResultLs(SeaboMxs1201ResultLQB queryBean);

	/**
	 * 分页获取seabo_mxs1201_result_l列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页seabo_mxs1201_result_l列表
	 */
	PagingResultBean<List<SeaboMxs1201ResultL>> getAllSeaboMxs1201ResultLsPaging(PagingQueryBean<SeaboMxs1201ResultLQB> pagingQueryBean);
}
