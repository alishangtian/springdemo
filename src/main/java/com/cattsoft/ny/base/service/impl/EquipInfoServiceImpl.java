package com.cattsoft.ny.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.component.EquipAttributeComponent;
import com.cattsoft.ny.base.component.EquipControlInfoComponent;
import com.cattsoft.ny.base.component.EquipInfoComponent;
import com.cattsoft.ny.base.component.EquipTypeComponent;
import com.cattsoft.ny.base.entity.EquipAttribute;
import com.cattsoft.ny.base.entity.EquipControlInfo;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipType;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipTypeQB;
import com.cattsoft.ny.base.service.EquipInfoService;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("equipInfoService")
@Transactional
public class EquipInfoServiceImpl implements EquipInfoService {

	Log log = LogFactory.getLog(EquipInfoServiceImpl.class);

	@Autowired
	private EquipInfoComponent equipInfoComponent;

	@Autowired
	private EquipAttributeComponent equipAttributeComponent;

	@Autowired
	private EquipTypeComponent equipTypeComponent;

	@Autowired
	private EquipControlInfoComponent equipControlInfoComponent;

	public EquipTypeComponent getEquipTypeComponent() {
		return equipTypeComponent;
	}

	public void setEquipTypeComponent(EquipTypeComponent equipTypeComponent) {
		this.equipTypeComponent = equipTypeComponent;
	}

	@Override
	public Long createEquipInfo(EquipInfo equipInfo) {
		return equipInfoComponent.createEquipInfo(equipInfo);
	}

	@Override
	public void updateEquipInfo(EquipInfo equipInfo) {
		equipInfoComponent.updateEquipInfo(equipInfo);
	}

	@Override
	public void removeEquipInfo(Long id) {
		equipInfoComponent.removeEquipInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipInfo getEquipInfo(Long id) {
		return equipInfoComponent.getEquipInfo(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipInfo> getAllEquipInfos() {
		return equipInfoComponent.getAllEquipInfos();
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipInfo> getEquipInfos(EquipInfoQB queryBean) {
		return equipInfoComponent.getEquipInfos(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipInfo>> getAllEquipInfosPaging(
			PagingQueryBean<EquipInfoQB> qb) {
		return equipInfoComponent.getAllEquipInfosPaging(qb);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipInfo> getEquipInfosOther(EquipInfoQB queryBean) {
		return equipInfoComponent.getEquipInfosOther(queryBean);
	}

	/*************************************************************/
	/* setter and getter */
	/*************************************************************/

	public void setEquipInfoComponent(EquipInfoComponent equipInfoComponent) {
		this.equipInfoComponent = equipInfoComponent;
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<EquipInfo>> getUserEquipInfosPaging(
			PagingQueryBean<EquipInfoQB> qb) {
		return equipInfoComponent.getUserEquipInfosPaging(qb);
	}

	@Override
	public Long createEquipAttribute(EquipAttribute equipAttribute) {
		return equipAttributeComponent.createEquipAttribute(equipAttribute);
	}

	@Transactional(readOnly = true)
	@Override
	public void removeEquipAttribute(Long id) {
		equipAttributeComponent.removeEquipAttribute(id);
	}

	@Override
	public void updateEquipAttribute(EquipAttribute equipAttribute) {
		equipAttributeComponent.updateEquipAttribute(equipAttribute);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipAttribute getEquipAttribute(Long id) {
		return equipAttributeComponent.getEquipAttribute(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipAttribute> getEquipAttributes(EquipAttribute equipAttribute) {
		return equipAttributeComponent.getEquipAttributes(equipAttribute);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipAttribute> getAllEquipAttributes() {
		return equipAttributeComponent.getAllEquipAttributes();
	}

	@Transactional(readOnly = true)
	@Override
	public EquipAttribute getEquipAttributeByEqidAndName(EquipAttribute ea) {
		return equipAttributeComponent.getEquipAttributeByEqidAndName(ea);
	}

	public EquipAttributeComponent getEquipAttributeComponent() {
		return equipAttributeComponent;
	}

	public void setEquipAttributeComponent(
			EquipAttributeComponent equipAttributeComponent) {
		this.equipAttributeComponent = equipAttributeComponent;
	}

	@Override
	public Long saveOneEquipType(EquipType et) {
		return equipTypeComponent.insert(et);
	}

	@Override
	public void deleteOneEquipType(Long id) {
		equipTypeComponent.deleteEquipType(id);
	}

	@Override
	public void updateOneEquipType(EquipType et) {
		equipTypeComponent.update(et);
	}

	@Override
	public EquipType getOneEquipType(Long id) {
		return equipTypeComponent.getEquipTypeById(id);
	}

	@Override
	public List<EquipType> getEquipTypesByPiD(Long pId) {
		return equipTypeComponent.getEquipTypesByPId(pId);
	}

	@Override
	public PagingResultBean<List<EquipType>> getPagingEquipTypes(
			PagingQueryBean<EquipTypeQB> pagingQueryBean) {
		return equipTypeComponent.getPagingEquipTypes(pagingQueryBean);
	}

	@Override
	public List<EquipType> getAllEquipTypes() {
		return equipTypeComponent.getAllEquipTypes();
	}

	@Override
	public EquipType findOneEquipType(EquipType et) {
		return this.equipTypeComponent.findOneEquipType(et);
	}

	@Override
	public List<EquipType> selectEquipTypes(EquipType et) {
		return this.equipTypeComponent.selectEquipTypes(et);
	}

	@Override
	public Long insertOneEquipControlInfo(EquipControlInfo eci) {
		return this.equipControlInfoComponent.insert(eci);
	}

	@Override
	public void deleteOneEquipControlInfo(Long id) {
		this.equipControlInfoComponent.delete(id);
	}

	@Override
	public void updateEquipControlInfo(EquipControlInfo eci) {
		this.equipControlInfoComponent.update(eci);
	}

	@Transactional(readOnly = true)
	@Override
	public EquipControlInfo findOneEquipControlInfo(Long id) {
		return this.equipControlInfoComponent.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipControlInfo> findEquipControlInfo(EquipControlInfoQB query) {
		return this.equipControlInfoComponent.find(query);
	}

	@Transactional(readOnly = true)
	@Override
	public List<EquipControlInfo> seletePage(
			PagingQueryBean<EquipControlInfoQB> query) {
		return this.equipControlInfoComponent.seletePage(query);
	}

	@Transactional(readOnly = true)
	@Override
	public Integer selectCount(PagingQueryBean<EquipControlInfoQB> query) {
		return this.equipControlInfoComponent.selectCount(query);
	}

	public EquipControlInfoComponent getEquipControlInfoComponent() {
		return equipControlInfoComponent;
	}

	public void setEquipControlInfoComponent(
			EquipControlInfoComponent equipControlInfoComponent) {
		this.equipControlInfoComponent = equipControlInfoComponent;
	}

	@Override
	public List<EquipInfo> findFreeControlEquips(EquipInfoQB queryBean) {
		return this.equipInfoComponent.findFreeControlEquips(queryBean);
	}

	@Override
	public List<EquipInfo> getUsefulEquipInfos(EquipInfoQB queryBean) {
		return this.equipInfoComponent.getUsefulEquipInfos(queryBean);
	}

	@Override
	public Integer cgqCountInfo(EquipInfoQB queryBean) {
		return this.equipInfoComponent.cgqCountInfo(queryBean);
	}

}
