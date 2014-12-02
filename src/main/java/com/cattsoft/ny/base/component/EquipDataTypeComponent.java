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
import com.cattsoft.ny.base.entity.EquipDataEnvdataTypeInfo;
import com.cattsoft.ny.base.entity.EquipDataType;
import com.cattsoft.ny.base.entity.querybean.EquipDataTypeQB;
import com.cattsoft.ny.base.persistence.EquipDataTypeMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipDataTypeComponent {
	
	@Autowired
	private EquipDataTypeMapper equipDataTypeMapper;

	/**
	 * 插入equip_data_type一条记录
	 * 
	 * @param equipDataType 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipDataType(EquipDataType equipDataType) {
		equipDataTypeMapper.insert(equipDataType);
		return equipDataType.getId();
	}

	/**
	 * 根据主键删除指定的equip_data_type记录
	 * 
	 * @param id 主键值
	 */
	public void removeEquipDataType(Long id) {
		equipDataTypeMapper.delete(id);
	}

	/**
	 * 更新指定的equip_data_type记录
	 * 
	 * @param equipDataType
	 */
	public void updateEquipDataType(EquipDataType equipDataType) {
		equipDataTypeMapper.update(equipDataType);
	}

	/**
	 * 根据主键查询一条equip_data_type记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipDataType getEquipDataType(Long id) {
		return equipDataTypeMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_data_type结果列表
	 * 
	 * @return  equip_data_type记录列表
	 */
	public List<EquipDataType> getEquipDataTypes(EquipDataTypeQB queryBean) {
		return equipDataTypeMapper.selectList(queryBean);
	}

	/**
	 * 查询所有equip_data_type记录
	 * 
	 * @return 所有 equip_data_type记录
	 */
	public List<EquipDataType> getAllEquipDataTypes() {
		return equipDataTypeMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_data_type记录
	 * 
	 * @return 当前页的 equip_data_type记录
	 */
	public PagingResultBean<List<EquipDataType>> getAllEquipDataTypesPaging(PagingQueryBean<EquipDataTypeQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipDataType> equipDataTypeList = equipDataTypeMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipDataType>> result = new PagingResultBean<List<EquipDataType>>();
		result.setResultList(equipDataTypeList);

		// 查询记录数
		Integer count = equipDataTypeMapper.selectCount(pagingQueryBean);
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
	public void proc(EquipDataType EquipDataType) {
		equipDataTypeMapper.proc(EquipDataType);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipDataTypeMapper(EquipDataTypeMapper equipDataTypeMapper) {
		this.equipDataTypeMapper = equipDataTypeMapper;
	}
	
	public List<EquipDataEnvdataTypeInfo> getEquipDataEnvdataTypeInfo(Long equipId) {
		return equipDataTypeMapper.selectListDataInfo(equipId);
	}
	
}
