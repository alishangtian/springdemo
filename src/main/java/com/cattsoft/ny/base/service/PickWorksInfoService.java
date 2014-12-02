/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.CropsResult;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.querybean.NewsQB;
import com.cattsoft.ny.base.entity.querybean.PickWorkInfoDataQB;
import com.cattsoft.ny.base.entity.querybean.PickWorksInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface PickWorksInfoService {
	/**
	 * 增加pick_works_info信息
	 * 
	 * @param pickWorksInfo pick_works_info信息
	 *
	 * @return pick_works_info标识
	 */
	Long createPickWorksInfo(PickWorksInfo pickWorksInfo);

	/**
	 * 修改pick_works_info信息
	 * 
	 * @param pickWorksInfo pick_works_info信息
	 */
	void updatePickWorksInfo(PickWorksInfo pickWorksInfo);

	/**
	 * 删除pick_works_info信息
	 * 
	 * @param id pick_works_info标识
	 */
	void removePickWorksInfo(Long id);

	/**
	 * 获取pick_works_info信息
	 * 
	 * @param id pick_works_info标识
	 * @return pick_works_info信息
	 */
	PickWorksInfo getPickWorksInfo(Long id);

	/**
	 * 获取所有pick_works_info
	 * 
	 * @return 所有pick_works_info信息的列表
	 */
	List<PickWorksInfo> getAllPickWorksInfos();
	
	/**
	 * 根据查询对象查询pick_works_info结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  pick_works_info记录列表
	 */
	List<PickWorksInfo> getPickWorksInfos(PickWorksInfoQB queryBean);

	/**
	 * 分页获取pick_works_info列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页pick_works_info列表
	 */
	PagingResultBean<List<PickWorksInfo>> getAllPickWorksInfosPaging(PagingQueryBean<PickWorksInfoQB> pagingQueryBean);
	/**
	 * @param baseId 
	 * 
	 */
	List<CropsResult> queryCropsResult(PickWorkInfoDataQB pickWorkInfoDataQB);
	
	List<CropsResult> queryCropsDataResult(PickWorkInfoDataQB pickWorkInfoDataQB);
	
	List<News> getNews(NewsQB newsQB);
}
