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
import com.cattsoft.ny.base.entity.EquipNetTopology;
import com.cattsoft.ny.base.entity.querybean.EquipNetTopologyQB;
import com.cattsoft.ny.base.persistence.EquipNetTopologyMapper;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipNetTopologyComponent {

	@Autowired
	private EquipNetTopologyMapper equipNetTopologyMapper;

	/**
	 * 插入equip_net_topology一条记录
	 * 
	 * @param equipNetTopology
	 *            主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipNetTopology(EquipNetTopology equipNetTopology) {
		equipNetTopologyMapper.insert(equipNetTopology);
		return equipNetTopology.getId();
	}

	/**
	 * 根据主键删除指定的equip_net_topology记录
	 * 
	 * @param id
	 *            主键值
	 */
	public void removeEquipNetTopology(Long id) {
		equipNetTopologyMapper.delete(id);
	}

	/**
	 * 根据设备id删除网络拓扑关系
	 * 
	 * @Title: removeEquipNetTopologyByEquipId
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void removeEquipNetTopologyByEquipId(Long id) {
		this.equipNetTopologyMapper.deleteByEquipId(id);
	}

	/**
	 * 更新指定的equip_net_topology记录
	 * 
	 * @param equipNetTopology
	 */
	public void updateEquipNetTopology(EquipNetTopology equipNetTopology) {
		equipNetTopologyMapper.update(equipNetTopology);
	}

	/**
	 * 根据主键查询一条equip_net_topology记录
	 * 
	 * @param id
	 *            主键值
	 * 
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipNetTopology getEquipNetTopology(Long id) {
		return equipNetTopologyMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_net_topology结果列表
	 * 
	 * @return equip_net_topology记录列表
	 */
	public List<EquipNetTopology> getEquipNetTopologys(
			EquipNetTopologyQB queryBean) {
		return equipNetTopologyMapper.selectList(queryBean);
	}

	/**
	 * 查询所有equip_net_topology记录
	 * 
	 * @return 所有 equip_net_topology记录
	 */
	public List<EquipNetTopology> getAllEquipNetTopologys() {
		return equipNetTopologyMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_net_topology记录
	 * 
	 * @return 当前页的 equip_net_topology记录
	 */
	public PagingResultBean<List<EquipNetTopology>> getAllEquipNetTopologysPaging(
			PagingQueryBean<EquipNetTopologyQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipNetTopology> equipNetTopologyList = equipNetTopologyMapper
				.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipNetTopology>> result = new PagingResultBean<List<EquipNetTopology>>();
		result.setResultList(equipNetTopologyList);

		// 查询记录数
		Integer count = equipNetTopologyMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);

		return result;
	}

	/**
	 * 查询网络拓扑列表
	 * 
	 * @Title: findEquipNetTopologys
	 * @author Mao Xiaobing
	 * @return List<EquipNetTopology> 返回类型
	 * @throws
	 */
	public List<EquipNetTopology> findEquipNetTopologys(
			EquipNetTopologyQB queryBean) {
		return this.equipNetTopologyMapper.find(queryBean);
	}

	/**
	 * 存储过程调用
	 * 
	 * @return
	 */
	public void proc(EquipNetTopology EquipNetTopology) {
		equipNetTopologyMapper.proc(EquipNetTopology);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setEquipNetTopologyMapper(
			EquipNetTopologyMapper equipNetTopologyMapper) {
		this.equipNetTopologyMapper = equipNetTopologyMapper;
	}
}
