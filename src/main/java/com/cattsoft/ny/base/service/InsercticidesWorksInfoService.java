/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.InsercticidesWorksInfo;
import com.cattsoft.ny.base.entity.querybean.InsercticidesWorksInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface InsercticidesWorksInfoService {
	/**
	 * 增加insercticides_works_info信息
	 * 
	 * @param insercticidesWorksInfo insercticides_works_info信息
	 *
	 * @return insercticides_works_info标识
	 */
	Long createInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo);

	/**
	 * 修改insercticides_works_info信息
	 * 
	 * @param insercticidesWorksInfo insercticides_works_info信息
	 */
	void updateInsercticidesWorksInfo(InsercticidesWorksInfo insercticidesWorksInfo);

	/**
	 * 删除insercticides_works_info信息
	 * 
	 * @param id insercticides_works_info标识
	 */
	void removeInsercticidesWorksInfo(Long id);

	/**
	 * 获取insercticides_works_info信息
	 * 
	 * @param id insercticides_works_info标识
	 * @return insercticides_works_info信息
	 */
	InsercticidesWorksInfo getInsercticidesWorksInfo(Long id);

	/**
	 * 获取所有insercticides_works_info
	 * 
	 * @return 所有insercticides_works_info信息的列表
	 */
	List<InsercticidesWorksInfo> getAllInsercticidesWorksInfos();
	
	/**
	 * 根据查询对象查询insercticides_works_info结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  insercticides_works_info记录列表
	 */
	List<InsercticidesWorksInfo> getInsercticidesWorksInfos(InsercticidesWorksInfoQB queryBean);

	/**
	 * 分页获取insercticides_works_info列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页insercticides_works_info列表
	 */
	PagingResultBean<List<InsercticidesWorksInfo>> getAllInsercticidesWorksInfosPaging(PagingQueryBean<InsercticidesWorksInfoQB> pagingQueryBean);
}
