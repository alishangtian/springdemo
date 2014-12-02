/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.SeaboMxs1402aResultL;
import com.cattsoft.ny.base.entity.querybean.SeaboMxs1402aResultLQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface SeaboMxs1402aResultLService {
	/**
	 * 增加seabo_mxs1402a_result_l信息
	 * 
	 * @param seaboMxs1402aResultL seabo_mxs1402a_result_l信息
	 *
	 * @return seabo_mxs1402a_result_l标识
	 */
	Long createSeaboMxs1402aResultL(SeaboMxs1402aResultL seaboMxs1402aResultL);

	/**
	 * 修改seabo_mxs1402a_result_l信息
	 * 
	 * @param seaboMxs1402aResultL seabo_mxs1402a_result_l信息
	 */
	void updateSeaboMxs1402aResultL(SeaboMxs1402aResultL seaboMxs1402aResultL);

	/**
	 * 删除seabo_mxs1402a_result_l信息
	 * 
	 * @param nodeid seabo_mxs1402a_result_l标识
	 */
	void removeSeaboMxs1402aResultL(Long nodeid);

	/**
	 * 获取seabo_mxs1402a_result_l信息
	 * 
	 * @param nodeid seabo_mxs1402a_result_l标识
	 * @return seabo_mxs1402a_result_l信息
	 */
	SeaboMxs1402aResultL getSeaboMxs1402aResultL(Long nodeid);

	/**
	 * 获取所有seabo_mxs1402a_result_l
	 * 
	 * @return 所有seabo_mxs1402a_result_l信息的列表
	 */
	List<SeaboMxs1402aResultL> getAllSeaboMxs1402aResultLs();
	
	/**
	 * 根据查询对象查询seabo_mxs1402a_result_l结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  seabo_mxs1402a_result_l记录列表
	 */
	List<SeaboMxs1402aResultL> getSeaboMxs1402aResultLs(SeaboMxs1402aResultLQB queryBean);

	/**
	 * 分页获取seabo_mxs1402a_result_l列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页seabo_mxs1402a_result_l列表
	 */
	PagingResultBean<List<SeaboMxs1402aResultL>> getAllSeaboMxs1402aResultLsPaging(PagingQueryBean<SeaboMxs1402aResultLQB> pagingQueryBean);
}
