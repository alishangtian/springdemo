/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.GreenHouse;
import com.cattsoft.ny.base.entity.querybean.GreenHouseQB;
import com.cattsoft.ny.base.persistence.GreenHouseMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class GreenHouseComponent {
	
	@Autowired
	private GreenHouseMapper greenHouseMapper;

	/**
	 * 插入green_house一条记录
	 * 
	 * @param greenHouse 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createGreenHouse(GreenHouse greenHouse) {
		greenHouseMapper.insert(greenHouse);
		return greenHouse.getId();
	}

	/**
	 * 根据主键删除指定的green_house记录
	 * 
	 * @param id 主键值
	 */
	public void removeGreenHouse(Long id) {
		greenHouseMapper.delete(id);
	}

	/**
	 * 更新指定的green_house记录
	 * 
	 * @param greenHouse
	 */
	public void updateGreenHouse(GreenHouse greenHouse) {
		greenHouseMapper.update(greenHouse);
	}

	/**
	 * 根据主键查询一条green_house记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public GreenHouse getGreenHouse(Long id) {
		return greenHouseMapper.select(id);
	}

	/**
	 * 根据查询对象查询green_house结果列表
	 * 
	 * @return  green_house记录列表
	 */
	public List<GreenHouse> getGreenHouses(GreenHouseQB queryBean) {
		return greenHouseMapper.selectList(queryBean);
	}

	/**
	 * 查询所有green_house记录
	 * 
	 * @return 所有 green_house记录
	 */
	public List<GreenHouse> getAllGreenHouses() {
		return greenHouseMapper.selectList(null);
	}

	/**
	 * 分页查询所有green_house记录
	 * 
	 * @return 当前页的 green_house记录
	 */
	public PagingResultBean<List<GreenHouse>> getAllGreenHousesPaging(PagingQueryBean<GreenHouseQB> pagingQueryBean) {
		// 分页查询列表
		List<GreenHouse> greenHouseList = greenHouseMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<GreenHouse>> result = new PagingResultBean<List<GreenHouse>>();
		result.setResultList(greenHouseList);

		// 查询记录数
		Integer count = greenHouseMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);

		return result;
	}

	/**
	 * 存储过程调用
	 * 
	 * @return
	 */
	public void proc(GreenHouse GreenHouse) {
		greenHouseMapper.proc(GreenHouse);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setGreenHouseMapper(GreenHouseMapper greenHouseMapper) {
		this.greenHouseMapper = greenHouseMapper;
	}
}
