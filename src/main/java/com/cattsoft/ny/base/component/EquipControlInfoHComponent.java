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
import com.cattsoft.ny.base.entity.EquipControlInfoH;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoHQB;
import com.cattsoft.ny.base.persistence.EquipControlInfoHMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class EquipControlInfoHComponent {
	
	@Autowired
	private EquipControlInfoHMapper equipControlInfoHMapper;

	/**
	 * 插入equip_control_info_h一条记录
	 * 
	 * @param equipControlInfoH 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createEquipControlInfoH(EquipControlInfoH equipControlInfoH) {
		equipControlInfoHMapper.insert(equipControlInfoH);
		return equipControlInfoH.getId();
	}

	/**
	 * 根据主键删除指定的equip_control_info_h记录
	 * 
	 * @param id 主键值
	 */
	public void removeEquipControlInfoH(Long id) {
		equipControlInfoHMapper.delete(id);
	}

	/**
	 * 更新指定的equip_control_info_h记录
	 * 
	 * @param equipControlInfoH
	 */
	public void updateEquipControlInfoH(EquipControlInfoH equipControlInfoH) {
		equipControlInfoHMapper.update(equipControlInfoH);
	}

	/**
	 * 根据主键查询一条equip_control_info_h记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public EquipControlInfoH getEquipControlInfoH(Long id) {
		return equipControlInfoHMapper.select(id);
	}

	/**
	 * 根据查询对象查询equip_control_info_h结果列表
	 * 
	 * @return  equip_control_info_h记录列表
	 */
	public List<EquipControlInfoH> getEquipControlInfoHs(EquipControlInfoHQB queryBean) {
		return equipControlInfoHMapper.selectList(queryBean);
	}

	/**
	 * 查询所有equip_control_info_h记录
	 * 
	 * @return 所有 equip_control_info_h记录
	 */
	public List<EquipControlInfoH> getAllEquipControlInfoHs() {
		return equipControlInfoHMapper.selectList(null);
	}

	/**
	 * 分页查询所有equip_control_info_h记录
	 * 
	 * @return 当前页的 equip_control_info_h记录
	 */
	public PagingResultBean<List<EquipControlInfoH>> getAllEquipControlInfoHsPaging(PagingQueryBean<EquipControlInfoHQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipControlInfoH> equipControlInfoHList = equipControlInfoHMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipControlInfoH>> result = new PagingResultBean<List<EquipControlInfoH>>();
		result.setResultList(equipControlInfoHList);

		// 查询记录数
		Integer count = equipControlInfoHMapper.selectCount(pagingQueryBean);
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
	public void proc(EquipControlInfoH EquipControlInfoH) {
		equipControlInfoHMapper.proc(EquipControlInfoH);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setEquipControlInfoHMapper(EquipControlInfoHMapper equipControlInfoHMapper) {
		this.equipControlInfoHMapper = equipControlInfoHMapper;
	}
}
