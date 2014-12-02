/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface BaseGreenHouseInfoService {
	/**
	 * 增加base_green_house_info信息
	 * 
	 * @param baseGreenHouseInfo base_green_house_info信息
	 *
	 * @return base_green_house_info标识
	 */
	Long createBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo);

	/**
	 * 修改base_green_house_info信息
	 * 
	 * @param baseGreenHouseInfo base_green_house_info信息
	 */
	void updateBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo);

	/**
	 * 删除base_green_house_info信息
	 * 
	 * @param id base_green_house_info标识
	 */
	void removeBaseGreenHouseInfo(Long id);

	/**
	 * 获取base_green_house_info信息
	 * 
	 * @param id base_green_house_info标识
	 * @return base_green_house_info信息
	 */
	BaseGreenHouseInfo getBaseGreenHouseInfo(Long id);

	/**
	 * 获取所有base_green_house_info
	 * 
	 * @return 所有base_green_house_info信息的列表
	 */
	List<BaseGreenHouseInfo> getAllBaseGreenHouseInfos();
	
	/**
	 * 根据查询对象查询base_green_house_info结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  base_green_house_info记录列表
	 */
	List<BaseGreenHouseInfo> getBaseGreenHouseInfos(BaseGreenHouseInfoQB queryBean);

	/**
	 * 分页获取base_green_house_info列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页base_green_house_info列表
	 */
	PagingResultBean<List<BaseGreenHouseInfo>> getAllBaseGreenHouseInfosPaging(PagingQueryBean<BaseGreenHouseInfoQB> pagingQueryBean);
}
