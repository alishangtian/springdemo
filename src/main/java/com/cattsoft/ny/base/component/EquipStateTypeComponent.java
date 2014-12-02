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
import com.cattsoft.ny.base.entity.EquipStateType;
import com.cattsoft.ny.base.entity.querybean.EquipStateTypeQB;
import com.cattsoft.ny.base.persistence.EquipStateTypeMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipStateTypeComponent {
	
	@Autowired
	private EquipStateTypeMapper equipStateTypeMapper;

	/**
	 * 插入equip_state_type一条记录
	 * 
	 * @param equipStateType 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipStateType(EquipStateType equipStateType) {
		equipStateTypeMapper.insert(equipStateType);
		return equipStateType.getId();
	}

	/**
	 * 根据主键删除指定的equip_state_type记录
	 * 
	 * @param id 主键值
	 */
	public void removeEquipStateType(Long id) {
		equipStateTypeMapper.delete(id);
	}

	/**
	 * 更新指定的equip_state_type记录
	 * 
	 * @param equipStateType
	 */
	public void updateEquipStateType(EquipStateType equipStateType) {
		equipStateTypeMapper.update(equipStateType);
	}

	/**
	 * 根据主键查询一条equip_state_type记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipStateType getEquipStateType(Long id) {
		return equipStateTypeMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_state_type结果列表
	 * 
	 * @return  equip_state_type记录列表
	 */
	public List<EquipStateType> getEquipStateTypes(EquipStateTypeQB queryBean) {
		return equipStateTypeMapper.selectList(queryBean);
	}

	/**
	 * 查询所有equip_state_type记录
	 * 
	 * @return 所有 equip_state_type记录
	 */
	public List<EquipStateType> getAllEquipStateTypes() {
		return equipStateTypeMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_state_type记录
	 * 
	 * @return 当前页的 equip_state_type记录
	 */
	public PagingResultBean<List<EquipStateType>> getAllEquipStateTypesPaging(PagingQueryBean<EquipStateTypeQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipStateType> equipStateTypeList = equipStateTypeMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipStateType>> result = new PagingResultBean<List<EquipStateType>>();
		result.setResultList(equipStateTypeList);

		// 查询记录数
		Integer count = equipStateTypeMapper.selectCount(pagingQueryBean);
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
	public void proc(EquipStateType EquipStateType) {
		equipStateTypeMapper.proc(EquipStateType);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipStateTypeMapper(EquipStateTypeMapper equipStateTypeMapper) {
		this.equipStateTypeMapper = equipStateTypeMapper;
	}
}
