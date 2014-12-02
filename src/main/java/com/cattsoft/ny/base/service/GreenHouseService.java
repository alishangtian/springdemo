/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.GreenHouse;
import com.cattsoft.ny.base.entity.querybean.GreenHouseQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface GreenHouseService {
	/**
	 * 增加green_house信息
	 * 
	 * @param greenHouse green_house信息
	 *
	 * @return green_house标识
	 */
	Long createGreenHouse(GreenHouse greenHouse);

	/**
	 * 修改green_house信息
	 * 
	 * @param greenHouse green_house信息
	 */
	void updateGreenHouse(GreenHouse greenHouse);

	/**
	 * 删除green_house信息
	 * 
	 * @param id green_house标识
	 */
	void removeGreenHouse(Long id);

	/**
	 * 获取green_house信息
	 * 
	 * @param id green_house标识
	 * @return green_house信息
	 */
	GreenHouse getGreenHouse(Long id);

	/**
	 * 获取所有green_house
	 * 
	 * @return 所有green_house信息的列表
	 */
	List<GreenHouse> getAllGreenHouses();
	
	/**
	 * 根据查询对象查询green_house结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  green_house记录列表
	 */
	List<GreenHouse> getGreenHouses(GreenHouseQB queryBean);

	/**
	 * 分页获取green_house列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页green_house列表
	 */
	PagingResultBean<List<GreenHouse>> getAllGreenHousesPaging(PagingQueryBean<GreenHouseQB> pagingQueryBean);
}
