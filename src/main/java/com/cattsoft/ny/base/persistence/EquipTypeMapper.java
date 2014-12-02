package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.EquipType;
import com.cattsoft.ny.base.entity.querybean.EquipTypeQB;
import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

/**
 * 设备属性
 * 
 * @author John
 * 
 */
@MyBatisRepository
public interface EquipTypeMapper {
	/**
	 * 新建设备类型信息
	 * 
	 * @Title: insert
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	void insert(EquipType equipType);

	/**
	 * 删除设备类型信息
	 * 
	 * @Title: delete
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	void delete(Long id);

	/**
	 * 更新设备类型信息
	 * 
	 * @Title: update
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	void update(EquipType equipType);

	/**
	 * 获取单个设备类型信息
	 * 
	 * @Title: select
	 * @author Mao Xiaobing
	 * @return EquipType 返回类型
	 * @throws
	 */
	EquipType select(Long id);

	/**
	 * 根据父id获取子设备类型列表
	 * 
	 * @Title: selectByPId
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	List<EquipType> selectByPId(Long pId);

	/**
	 * 获取设备类型分页数据
	 * 
	 * @Title: selectPage
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	List<EquipType> selectPage(PagingQueryBean<EquipTypeQB> pagingQueryBean);

	/**
	 * 获取分页数据总数
	 * 
	 * @Title: selectCount
	 * @author Mao Xiaobing
	 * @return Integer 返回类型
	 * @throws
	 */
	Integer selectCount(PagingQueryBean<EquipTypeQB> pagingQueryBean);

	/**
	 * 获取所有的设备类型
	 * 
	 * @Title: selectList
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	List<EquipType> selectList();

	/**
	 * 查找一个equipType,查重用的
	 * 
	 * @Title: findOneEquipType
	 * @author Mao Xiaobing
	 * @return EquipType 返回类型
	 * @throws
	 */
	EquipType findOneEquipType(EquipType et);

	/**
	 * 查询列表
	 * 
	 * @Title: selectEquipTypes
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	List<EquipType> selectEquipTypes(EquipType et);

}