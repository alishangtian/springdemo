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
import com.cattsoft.ny.base.entity.EquipStateData;
import com.cattsoft.ny.base.entity.EquipStateDataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipStateDataQB;
import com.cattsoft.ny.base.persistence.EquipStateDataMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipStateDataComponent {
	
	@Autowired
	private EquipStateDataMapper equipStateDataMapper;

	/**
	 * 插入equip_state_data一条记录
	 * 
	 * @param equipStateData 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipStateData(EquipStateData equipStateData) {
		equipStateDataMapper.insert(equipStateData);
		return equipStateData.getId();
	}

	/**
	 * 根据主键删除指定的equip_state_data记录
	 * 
	 * @param id 主键值
	 */
	public void removeEquipStateData(Long id) {
		equipStateDataMapper.delete(id);
	}

	/**
	 * 更新指定的equip_state_data记录
	 * 
	 * @param equipStateData
	 */
	public void updateEquipStateData(EquipStateData equipStateData) {
		equipStateDataMapper.update(equipStateData);
	}

	/**
	 * 根据主键查询一条equip_state_data记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipStateData getEquipStateData(Long id) {
		return equipStateDataMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_state_data结果列表
	 * 
	 * @return  equip_state_data记录列表
	 */
	public List<EquipStateData> getEquipStateDatas(EquipStateDataQB queryBean) {
		return equipStateDataMapper.selectList(queryBean);
	}
	public List<EquipStateDataInfo> getEquipStateDatasInfo(EquipStateDataQB queryBean) {
		return equipStateDataMapper.selectListInfo(queryBean);
	}
	/**
	 * 查询所有equip_state_data记录
	 * 
	 * @return 所有 equip_state_data记录
	 */
	public List<EquipStateData> getAllEquipStateDatas() {
		return equipStateDataMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_state_data记录
	 * 
	 * @return 当前页的 equip_state_data记录
	 */
	public PagingResultBean<List<EquipStateData>> getAllEquipStateDatasPaging(PagingQueryBean<EquipStateDataQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipStateData> equipStateDataList = equipStateDataMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipStateData>> result = new PagingResultBean<List<EquipStateData>>();
		result.setResultList(equipStateDataList);

		// 查询记录数
		Integer count = equipStateDataMapper.selectCount(pagingQueryBean);
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
	public void proc(EquipStateData EquipStateData) {
		equipStateDataMapper.proc(EquipStateData);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipStateDataMapper(EquipStateDataMapper equipStateDataMapper) {
		this.equipStateDataMapper = equipStateDataMapper;
	}
}
