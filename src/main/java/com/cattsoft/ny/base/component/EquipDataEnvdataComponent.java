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
import com.cattsoft.ny.base.entity.EquipDataEnvdata;
import com.cattsoft.ny.base.entity.EquipDataEnvdataInfo;
import com.cattsoft.ny.base.entity.querybean.EquipDataEnvdataQB;
import com.cattsoft.ny.base.persistence.EquipDataEnvdataMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipDataEnvdataComponent {
	
	@Autowired
	private EquipDataEnvdataMapper equipDataEnvdataMapper;

	/**
	 * 插入equip_data_envdata一条记录
	 * 
	 * @param equipDataEnvdata 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipDataEnvdata(EquipDataEnvdata equipDataEnvdata) {
		equipDataEnvdataMapper.insert(equipDataEnvdata);
		return equipDataEnvdata.getDataId();
	}

	/**
	 * 根据主键删除指定的equip_data_envdata记录
	 * 
	 * @param dataId 主键值
	 */
	public void removeEquipDataEnvdata(Long dataId) {
		equipDataEnvdataMapper.delete(dataId);
	}

	/**
	 * 更新指定的equip_data_envdata记录
	 * 
	 * @param equipDataEnvdata
	 */
	public void updateEquipDataEnvdata(EquipDataEnvdata equipDataEnvdata) {
		equipDataEnvdataMapper.update(equipDataEnvdata);
	}

	/**
	 * 根据主键查询一条equip_data_envdata记录
	 * 
	 * @param dataId 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipDataEnvdata getEquipDataEnvdata(Long dataId) {
		return equipDataEnvdataMapper.select(dataId);
	}

	/**
	 * 根据查询对象查询equip_data_envdata结果列表
	 * 
	 * @return  equip_data_envdata记录列表
	 */
	public List<EquipDataEnvdata> getEquipDataEnvdatas(EquipDataEnvdataQB queryBean) {
		return equipDataEnvdataMapper.selectList(queryBean);
	}
	public List<EquipDataEnvdata> getEquipDataEnvdatasTime(EquipDataEnvdataQB queryBean) {
		return equipDataEnvdataMapper.selectListTime(queryBean);
	}
	public List<EquipDataEnvdataInfo> getEquipDataEnvdatasInfo(Long typeid) {
		return equipDataEnvdataMapper.selectListInfo(typeid);
	}

	/**
	 * 查询所有equip_data_envdata记录
	 * 
	 * @return 所有 equip_data_envdata记录
	 */
	public List<EquipDataEnvdata> getAllEquipDataEnvdatas() {
		return equipDataEnvdataMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_data_envdata记录
	 * 
	 * @return 当前页的 equip_data_envdata记录
	 */
	public PagingResultBean<List<EquipDataEnvdata>> getAllEquipDataEnvdatasPaging(PagingQueryBean<EquipDataEnvdataQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipDataEnvdata> equipDataEnvdataList = equipDataEnvdataMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipDataEnvdata>> result = new PagingResultBean<List<EquipDataEnvdata>>();
		result.setResultList(equipDataEnvdataList);

		// 查询记录数
		Integer count = equipDataEnvdataMapper.selectCount(pagingQueryBean);
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
	public void proc(EquipDataEnvdata EquipDataEnvdata) {
		equipDataEnvdataMapper.proc(EquipDataEnvdata);
	}
	
	public List<EquipDataEnvdata> maxMinData(EquipDataEnvdataQB queryBean){
		return equipDataEnvdataMapper.maxEnvdata();
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipDataEnvdataMapper(EquipDataEnvdataMapper equipDataEnvdataMapper) {
		this.equipDataEnvdataMapper = equipDataEnvdataMapper;
	}
}
