/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipNetTopologyService {
	/**
	 * 增加equip_net_topology信息
	 * 
	 * @param equipNetTopology
	 *            equip_net_topology信息
	 * 
	 * @return equip_net_topology标识
	 */
	Long createEquipNetTopology(EquipNetTopology equipNetTopology);

	/**
	 * 修改equip_net_topology信息
	 * 
	 * @param equipNetTopology
	 *            equip_net_topology信息
	 */
	void updateEquipNetTopology(EquipNetTopology equipNetTopology);

	/**
	 * 删除equip_net_topology信息
	 * 
	 * @param id
	 *            equip_net_topology标识
	 */
	void removeEquipNetTopology(Long id);

	/**
	 * 根据设备id删除设备拓扑关系
	 * 
	 * @Title: removeEquipNetTopologyByEquipId
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	void removeEquipNetTopologyByEquipId(Long id);

	/**
	 * 获取equip_net_topology信息
	 * 
	 * @param id
	 *            equip_net_topology标识
	 * @return equip_net_topology信息
	 */
	EquipNetTopology getEquipNetTopology(Long id);

	/**
	 * 获取所有equip_net_topology
	 * 
	 * @return 所有equip_net_topology信息的列表
	 */
	List<EquipNetTopology> getAllEquipNetTopologys();

	/**
	 * 根据查询对象查询equip_net_topology结果列表
	 * 
	 * @param queryBean
	 *            查询对象
	 * 
	 * @return equip_net_topology记录列表
	 */
	List<EquipNetTopology> getEquipNetTopologys(EquipNetTopologyQB queryBean);

	/**
	 * 分页获取equip_net_topology列表
	 * 
	 * @param pagingQueryBean
	 *            分页查询对象
	 * 
	 * @return 分页equip_net_topology列表
	 */
	PagingResultBean<List<EquipNetTopology>> getAllEquipNetTopologysPaging(
			PagingQueryBean<EquipNetTopologyQB> pagingQueryBean);

	/**
	 * 获取网络拓扑列表
	 * 
	 * @Title: findEquipNetTopologys
	 * @author Mao Xiaobing
	 * @return List<EquipNetTopology> 返回类型
	 * @throws
	 */
	public List<EquipNetTopology> findEquipNetTopologys(EquipNetTopologyQB query);
}
