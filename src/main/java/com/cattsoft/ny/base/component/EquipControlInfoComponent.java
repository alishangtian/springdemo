package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipControlInfo;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoQB;
import com.cattsoft.ny.base.persistence.EquipControlInfoMapper;

/**
 * 
 * @author John
 * 
 */
@Component
public class EquipControlInfoComponent {

	@Autowired
	private EquipControlInfoMapper equipControlInfoMapper;

	public Long insert(EquipControlInfo eci) {
		this.equipControlInfoMapper.insert(eci);
		return eci.getId();
	}

	public void delete(Long id) {
		this.equipControlInfoMapper.delete(id);
	}

	public void update(EquipControlInfo eci) {
		this.equipControlInfoMapper.update(eci);
	}

	public EquipControlInfo findOne(Long id) {
		return this.equipControlInfoMapper.findOne(id);
	}

	public List<EquipControlInfo> find(EquipControlInfoQB query) {
		return this.equipControlInfoMapper.find(query);
	}

	public List<EquipControlInfo> seletePage(
			PagingQueryBean<EquipControlInfoQB> query) {
		return this.equipControlInfoMapper.selectPage(query);
	}

	public Integer selectCount(PagingQueryBean<EquipControlInfoQB> query) {
		return this.equipControlInfoMapper.selectCount(query);
	}

	public EquipControlInfoMapper getEquipControlInfoMapper() {
		return equipControlInfoMapper;
	}

	public void setEquipControlInfoMapper(
			EquipControlInfoMapper equipControlInfoMapper) {
		this.equipControlInfoMapper = equipControlInfoMapper;
	}

}
