package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.EquipType;
import com.cattsoft.ny.base.entity.querybean.EquipTypeQB;
import com.cattsoft.ny.base.persistence.EquipTypeMapper;

/**
 * 设备类型组件
 * 
 * @author John
 * 
 */
@Component
public class EquipTypeComponent {

	@Autowired
	private EquipTypeMapper equipTypeMapper;

	/**
	 * 插入一条数据
	 * 
	 * @Title: insert
	 * @author Mao Xiaobing
	 * @return Long 返回类型
	 * @throws
	 */
	public Long insert(EquipType et) {
		equipTypeMapper.insert(et);
		return et.getId();
	}

	/**
	 * 更新一条数据
	 * 
	 * @Title: update
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void update(EquipType et) {
		equipTypeMapper.update(et);
	}

	/**
	 * 删除一条数据
	 * 
	 * @Title: deleteEquipType
	 * @author Mao Xiaobing
	 * @return void 返回类型
	 * @throws
	 */
	public void deleteEquipType(Long id) {
		equipTypeMapper.delete(id);
	}

	/**
	 * 根据id获取一条数据
	 * 
	 * @Title: getEquipTypeById
	 * @author Mao Xiaobing
	 * @return EquipType 返回类型
	 * @throws
	 */
	public EquipType getEquipTypeById(Long id) {
		return equipTypeMapper.select(id);
	}

	/**
	 * 根据父id获取子节点列表
	 * 
	 * @Title: getEquipTypesByPId
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	public List<EquipType> getEquipTypesByPId(Long pId) {
		return equipTypeMapper.selectByPId(pId);
	}

	/**
	 * 获取设备属性分页数据
	 * 
	 * @Title: getPagingEquipTypes
	 * @author Mao Xiaobing
	 * @return PagingResultBean<List<EquipType>> 返回类型
	 * @throws
	 */
	public PagingResultBean<List<EquipType>> getPagingEquipTypes(
			PagingQueryBean<EquipTypeQB> pagingQueryBean) {
		// 分页查询列表
		List<EquipType> equipTypeList = equipTypeMapper
				.selectPage(pagingQueryBean);
		PagingResultBean<List<EquipType>> result = new PagingResultBean<List<EquipType>>();
		result.setResultList(equipTypeList);

		// 查询记录数
		Integer count = equipTypeMapper.selectCount(pagingQueryBean);
		PagingInfo pagingInfo = pagingQueryBean.getPagingInfo();
		pagingInfo.setTotalRows(count);
		result.setPagingInfo(pagingInfo);

		return result;
	}

	/**
	 * 获取所有的设备类型
	 * 
	 * @Title: getAllEquipTypes
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	public List<EquipType> getAllEquipTypes() {
		return this.equipTypeMapper.selectList();
	}

	/**
	 * 查找一个设备类型，查重用的
	 * 
	 * @Title: findOneEquipType
	 * @author Mao Xiaobing
	 * @return EquipType 返回类型
	 * @throws
	 */
	public EquipType findOneEquipType(EquipType et) {
		return this.equipTypeMapper.findOneEquipType(et);
	}

	/**
	 * 获取满足条件的非分页数据列表
	 * 
	 * @Title: selectEquipTypes
	 * @author Mao Xiaobing
	 * @return List<EquipType> 返回类型
	 * @throws
	 */
	public List<EquipType> selectEquipTypes(EquipType et) {
		return this.equipTypeMapper.selectEquipTypes(et);
	}

	public EquipTypeMapper getEquipTypeMapper() {
		return equipTypeMapper;
	}

	public void setEquipTypeMapper(EquipTypeMapper equipTypeMapper) {
		this.equipTypeMapper = equipTypeMapper;
	}

}
