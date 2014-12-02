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
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.persistence.EquipInfoMapper;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipInfoComponent {

	@Autowired
	private EquipInfoMapper equipInfoMapper;

	/**
	 * 插入equip_info一条记录
	 * 
	 * @param equipInfo
	 *            主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipInfo(EquipInfo equipInfo) {
		equipInfoMapper.insert(equipInfo);
		return equipInfo.getId();
	}

	/**
	 * 根据主键删除指定的equip_info记录
	 * 
	 * @param id
	 *            主键值
	 */
	public void removeEquipInfo(Long id) {
		equipInfoMapper.delete(id);
	}

	/**
	 * 更新指定的equip_info记录
	 * 
	 * @param equipInfo
	 */
	public void updateEquipInfo(EquipInfo equipInfo) {
		equipInfoMapper.update(equipInfo);
	}

	/**
	 * 根据主键查询一条equip_info记录
	 * 
	 * @param id
	 *            主键值
	 * 
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipInfo getEquipInfo(Long id) {
		return equipInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_info结果列表
	 * 
	 * @return equip_info记录列表
	 */
	public List<EquipInfo> getEquipInfos(EquipInfoQB queryBean) {
		return equipInfoMapper.selectList(queryBean);
	}

	/**
	 * 根据查询对象查询可用的设备列表
	 * 
	 * @return equip_info记录列表
	 */
	public List<EquipInfo> getUsefulEquipInfos(EquipInfoQB queryBean) {
		return equipInfoMapper.selectUsefulList(queryBean);
	}

	/**
	 * 查询没有关联的记录
	 */

	public List<EquipInfo> getEquipInfosOther(EquipInfoQB queryBean) {
		return equipInfoMapper.selectOtherList(queryBean);
	}

	/**
	 * 查询所有equip_info记录
	 * 
	 * @return 所有 equip_info记录
	 */
	public List<EquipInfo> getAllEquipInfos() {
		return equipInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_info记录
	 * 
	 * @return 当前页的 equip_info记录
	 */
	public PagingResultBean<List<EquipInfo>> getAllEquipInfosPaging(
			PagingQueryBean<EquipInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipInfo> equipInfoList = equipInfoMapper
				.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipInfo>> result = new PagingResultBean<List<EquipInfo>>();
		result.setResultList(equipInfoList);

		// 查询记录数
		Integer count = equipInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(EquipInfo EquipInfo) {
		equipInfoMapper.proc(EquipInfo);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setEquipInfoMapper(EquipInfoMapper equipInfoMapper) {
		this.equipInfoMapper = equipInfoMapper;
	}

	/**
	 * 分页查询所有equip_info记录
	 * 
	 * @return 当前页的 equip_info记录
	 */
	public PagingResultBean<List<EquipInfo>> getUserEquipInfosPaging(
			PagingQueryBean<EquipInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipInfo> equipInfoList = equipInfoMapper
				.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipInfo>> result = new PagingResultBean<List<EquipInfo>>();
		result.setResultList(equipInfoList);

		// 查询记录数
		Integer count = equipInfoMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);
		return result;
	}

	/**
	 * 查询空闲的控制设备列表
	 * 
	 * @Title: findFreeControlEquips
	 * @author Mao Xiaobing
	 * @return List<EquipInfo> 返回类型
	 * @throws
	 */
	public List<EquipInfo> findFreeControlEquips(EquipInfoQB queryBean) {
		return this.equipInfoMapper.findFreeControlEquip(queryBean);
	}
	
	/**
	 * 根据温室id 和类型 查询设备数量
	 */
	public Integer cgqCountInfo(EquipInfoQB queryBean){
		return this.equipInfoMapper.cgqCountInfo(queryBean);
	}
}
