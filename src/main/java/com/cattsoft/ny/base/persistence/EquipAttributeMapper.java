package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.ny.base.entity.EquipAttribute;
import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

/**
 * 设备属性
 * 
 * @author John
 * 
 */
@MyBatisRepository
public interface EquipAttributeMapper {
	void insert(EquipAttribute equipAttribute);

	void delete(Long id);

	void update(EquipAttribute equipAttribute);

	EquipAttribute select(Long id);
	
	EquipAttribute selectByEqidAndName(EquipAttribute equipAttribute);
	
	List<EquipAttribute> selectList(EquipAttribute equipAttribute);

}