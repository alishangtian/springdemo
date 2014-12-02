package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipControlInfo;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoQB;
import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

/**
 * 
 * @author John
 * 
 */
@MyBatisRepository
public interface EquipControlInfoMapper {
	void insert(EquipControlInfo equipControlInfo);

	void delete(Long id);

	void update(EquipControlInfo equipControlInfo);

	EquipControlInfo findOne(Long id);

	List<EquipControlInfo> find(EquipControlInfoQB query);

	List<EquipControlInfo> selectPage(
			PagingQueryBean<EquipControlInfoQB> pageQuery);

	Integer selectCount(PagingQueryBean<EquipControlInfoQB> pageQuery);

}