package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipAttribute;
import com.cattsoft.ny.base.entity.EquipControlInfo;
import com.cattsoft.ny.base.entity.EquipInfo;
import com.cattsoft.ny.base.entity.EquipType;
import com.cattsoft.ny.base.entity.querybean.EquipControlInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipInfoQB;
import com.cattsoft.ny.base.entity.querybean.EquipTypeQB;

/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface EquipInfoService {
	/**
	 * 增加equip_info信息
	 * 
	 * @param equipInfo
	 *            equip_info信息
	 * 
	 * @return equip_info标识
	 */
	Long createEquipInfo(EquipInfo equipInfo);

	/**
	 * 修改equip_info信息
	 * 
	 * @param equipInfo
	 *            equip_info信息
	 */
	void updateEquipInfo(EquipInfo equipInfo);

	/**
	 * 删除equip_info信息
	 * 
	 * @param id
	 *            equip_info标识
	 */
	void removeEquipInfo(Long id);

	/**
	 * 获取equip_info信息
	 * 
	 * @param id
	 *            equip_info标识
	 * @return equip_info信息
	 */
	EquipInfo getEquipInfo(Long id);

	/**
	 * 获取所有equip_info
	 * 
	 * @return 所有equip_info信息的列表
	 */
	List<EquipInfo> getAllEquipInfos();

	/**
	 * 根据查询对象查询equip_info结果列表
	 * 
	 * @param queryBean
	 *            查询对象
	 * 
	 * @return equip_info记录列表
	 */
	List<EquipInfo> getEquipInfos(EquipInfoQB queryBean);

	/**
	 * 查询可用的设备列表
	 * 
	 * @param queryBean
	 * @return
	 */
	List<EquipInfo> getUsefulEquipInfos(EquipInfoQB queryBean);

	/**
	 * 分页获取equip_info列表
	 * 
	 * @param pagingQueryBean
	 *            分页查询对象
	 * 
	 * @return 分页equip_info列表
	 */
	PagingResultBean<List<EquipInfo>> getAllEquipInfosPaging(
			PagingQueryBean<EquipInfoQB> pagingQueryBean);

	/**
	 * 查询没有关联的设备
	 */
	List<EquipInfo> getEquipInfosOther(EquipInfoQB queryBean);

	PagingResultBean<List<EquipInfo>> getUserEquipInfosPaging(
			PagingQueryBean<EquipInfoQB> qb);

	/**
	 * 保存设备属性
	 * 
	 * @Title: createEquipAttribute
	 * @author Mao Xiaobing
	 * @return Long 返回类型
	 * @throws
	 */
	public Long createEquipAttribute(EquipAttribute equipAttribute);

	/**
	 * 删除一个设备属性
	 * 
	 * @Title: removeEquipAttribute
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void removeEquipAttribute(Long id);

	/**
	 * 更新设备属性
	 * 
	 * @Title: updateEquipAttribute
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void updateEquipAttribute(EquipAttribute equipAttribute);

	/**
	 * 获取一个设备属性
	 * 
	 * @Title: getEquipAttribute
	 * @author Mao Xiaobing
	 * @return EquipAttribute 返回类型
	 * @throws
	 */
	public EquipAttribute getEquipAttribute(Long id);

	/**
	 * 获取设备属性列表
	 * 
	 * @Title: getEquipAttributes
	 * @author Mao Xiaobing
	 * @return List<EquipAttribute> 返回类型
	 * @throws
	 */
	public List<EquipAttribute> getEquipAttributes(EquipAttribute equipAttribute);

	/**
	 * 获取所有的设备属性
	 * 
	 * @Title: getAllEquipAttributes
	 * @author Mao Xiaobing
	 * @return List<EquipAttribute> 返回类型
	 * @throws
	 */
	public List<EquipAttribute> getAllEquipAttributes();

	/**
	 * 根据设备id和属性名称获取设备属性
	 * 
	 * @Title: getEquipAttributeByEqidAndName
	 * @author Mao Xiaobing
	 * @return EquipAttribute 返回类型
	 * @throws
	 */
	public EquipAttribute getEquipAttributeByEqidAndName(EquipAttribute ea);

	/**
	 * 保存一个设备类型
	 * 
	 * @Title: saveOneEquipType
	 * @author Mao Xiaobing
	 * @return Long 返回类型
	 * @throws
	 */
	public Long saveOneEquipType(EquipType et);

	/**
	 * 删除一个设备类型信息
	 * 
	 * @Title: deleteOneEquipType
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void deleteOneEquipType(Long id);

	/**
	 * 更新一个设备属性
	 * 
	 * @Title: updateOneEquipType
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void updateOneEquipType(EquipType et);

	/**
	 * 获取一个设备类型
	 * 
	 * @Title: getOneEquipType
	 * @author Mao Xiaobing
	 * @return EquipType 返回类型
	 * @throws
	 */
	public EquipType getOneEquipType(Long id);

	/**
	 * 根据父设备类型id获取子设备列表
	 * 
	 * @Title: getEquipTypeByPiD
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	public List<EquipType> getEquipTypesByPiD(Long pId);

	/**
	 * 获取设备列表分页数据
	 * 
	 * @Title: getPagingEquipTypes
	 * @author Mao Xiaobing
	 * @return PagingResultBean<List<EquipType>> 返回类型
	 * @throws
	 */
	public PagingResultBean<List<EquipType>> getPagingEquipTypes(
			PagingQueryBean<EquipTypeQB> pagingQueryBean);

	/**
	 * 获取所有的设备类型
	 * 
	 * @Title: getAllEquipTypes
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	public List<EquipType> getAllEquipTypes();

	/**
	 * 查找一个设备类型，查重用的
	 * 
	 * @Title: findOneEquipType
	 * @author Mao Xiaobing
	 * @return EquipType 返回类型
	 * @throws
	 */
	public EquipType findOneEquipType(EquipType et);

	/**
	 * 获取满足条件的非分页设备类型列表
	 * 
	 * @Title: selectEquipTypes
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	public List<EquipType> selectEquipTypes(EquipType et);

	/**
	 * 插入一条控制设备端口信息
	 * 
	 * @Title: insertOneEquipControlInfo
	 * @author Mao Xiaobing
	 * @return Long 返回类型
	 * @throws
	 */
	public Long insertOneEquipControlInfo(EquipControlInfo eci);

	/**
	 * 删除一条控制设备端口信息
	 * 
	 * @Title: deleteOneEquipControlInfo
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void deleteOneEquipControlInfo(Long id);

	/**
	 * 更新一条控制设备端口信息
	 * 
	 * @Title: updateEquipControlInfo
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void updateEquipControlInfo(EquipControlInfo eci);

	/**
	 * 查找一个控制设备端口信息
	 * 
	 * @Title: findOneEquipControlInfo
	 * @author Mao Xiaobing
	 * @return EquipControlInfo 返回类型
	 * @throws
	 */
	public EquipControlInfo findOneEquipControlInfo(Long id);

	/**
	 * 查找控制设备列表
	 * 
	 * @Title: findEquipControlInfo
	 * @author Mao Xiaobing
	 * @return List<EquipControlInfo> 返回类型
	 * @throws
	 */
	public List<EquipControlInfo> findEquipControlInfo(EquipControlInfoQB query);

	/**
	 * 分页查询控制设备列表
	 * 
	 * @Title: seletePage
	 * @author Mao Xiaobing
	 * @return List<EquipControlInfo> 返回类型
	 * @throws
	 */
	public List<EquipControlInfo> seletePage(
			PagingQueryBean<EquipControlInfoQB> query);

	/**
	 * 分页查询控制设备总数量
	 * 
	 * @Title: selectCount
	 * @author Mao Xiaobing
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer selectCount(PagingQueryBean<EquipControlInfoQB> query);

	/**
	 * 查询为被使用的某一用户的控制设备列表
	 * 
	 * @Title: findFreeControlEquips
	 * @author Mao Xiaobing
	 * @return List<EquipInfo> 返回类型
	 * @throws
	 */
	public List<EquipInfo> findFreeControlEquips(EquipInfoQB queryBean);

	/**
	 * 查询设备数量
	 */
	public Integer cgqCountInfo(EquipInfoQB queryBean);
}
