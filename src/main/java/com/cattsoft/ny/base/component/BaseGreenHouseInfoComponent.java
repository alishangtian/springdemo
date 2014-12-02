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
import com.cattsoft.ny.base.entity.BaseGreenHouseInfo;
import com.cattsoft.ny.base.entity.querybean.BaseGreenHouseInfoQB;
import com.cattsoft.ny.base.persistence.BaseGreenHouseInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class BaseGreenHouseInfoComponent {
	
	@Autowired
	private BaseGreenHouseInfoMapper baseGreenHouseInfoMapper;

	/**
	 * 插入base_green_house_info一条记录
	 * 
	 * @param baseGreenHouseInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo) {
		baseGreenHouseInfoMapper.insert(baseGreenHouseInfo);
		return baseGreenHouseInfo.getId();
	}

	/**
	 * 根据主键删除指定的base_green_house_info记录
	 * 
	 * @param id 主键值
	 */
	public void removeBaseGreenHouseInfo(Long id) {
		baseGreenHouseInfoMapper.delete(id);
	}

	/**
	 * 更新指定的base_green_house_info记录
	 * 
	 * @param baseGreenHouseInfo
	 */
	public void updateBaseGreenHouseInfo(BaseGreenHouseInfo baseGreenHouseInfo) {
		baseGreenHouseInfoMapper.update(baseGreenHouseInfo);
	}

	/**
	 * 根据主键查询一条base_green_house_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public BaseGreenHouseInfo getBaseGreenHouseInfo(Long id) {
		return baseGreenHouseInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询base_green_house_info结果列表
	 * 
	 * @return  base_green_house_info记录列表
	 */
	public List<BaseGreenHouseInfo> getBaseGreenHouseInfos(BaseGreenHouseInfoQB queryBean) {
		return baseGreenHouseInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有base_green_house_info记录
	 * 
	 * @return 所有 base_green_house_info记录
	 */
	public List<BaseGreenHouseInfo> getAllBaseGreenHouseInfos() {
		return baseGreenHouseInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有base_green_house_info记录
	 * 
	 * @return 当前页的 base_green_house_info记录
	 */
	public PagingResultBean<List<BaseGreenHouseInfo>> getAllBaseGreenHouseInfosPaging(PagingQueryBean<BaseGreenHouseInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<BaseGreenHouseInfo> baseGreenHouseInfoList = baseGreenHouseInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<BaseGreenHouseInfo>> result = new PagingResultBean<List<BaseGreenHouseInfo>>();
		result.setResultList(baseGreenHouseInfoList);

		// 查询记录数
		Integer count = baseGreenHouseInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(BaseGreenHouseInfo BaseGreenHouseInfo) {
		baseGreenHouseInfoMapper.proc(BaseGreenHouseInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setBaseGreenHouseInfoMapper(BaseGreenHouseInfoMapper baseGreenHouseInfoMapper) {
		this.baseGreenHouseInfoMapper = baseGreenHouseInfoMapper;
	}
}
