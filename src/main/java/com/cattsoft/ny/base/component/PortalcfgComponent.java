package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Portalcfg;
import com.cattsoft.ny.base.entity.querybean.PortalcfgQB;
import com.cattsoft.ny.base.persistence.PortalcfgMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class PortalcfgComponent {
	
	@Autowired
	private PortalcfgMapper portalcfgMapper;

	/**
	 * 插入portalcfg一条记录
	 * 
	 * @param portalcfg 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createPortalcfg(Portalcfg portalcfg) {
		portalcfgMapper.insert(portalcfg);
		return portalcfg.getPortalId();
	}

	/**
	 * 根据主键删除指定的portalcfg记录
	 * 
	 * @param portalId 主键值
	 */
	public void removePortalcfg(Long portalId) {
		portalcfgMapper.delete(portalId);
	}

	/**
	 * 更新指定的portalcfg记录
	 * 
	 * @param portalcfg
	 */
	public void updatePortalcfg(Portalcfg portalcfg) {
		portalcfgMapper.update(portalcfg);
	}

	/**
	 * 根据主键查询一条portalcfg记录
	 * 
	 * @param portalId 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public Portalcfg getPortalcfg(Long portalId) {
		return portalcfgMapper.select(portalId);
	}

	/**
	 * 根据查询对象查询portalcfg结果列表
	 * 
	 * @return  portalcfg记录列表
	 */
	public List<Portalcfg> getPortalcfgs(PortalcfgQB queryBean) {
		return portalcfgMapper.selectList(queryBean);
	}

	/**
	 * 查询所有portalcfg记录
	 * 
	 * @return 所有 portalcfg记录
	 */
	public List<Portalcfg> getAllPortalcfgs() {
		return portalcfgMapper.selectList(null);
	}

	/**
	 * 分页查询所有portalcfg记录
	 * 
	 * @return 当前页的 portalcfg记录
	 */
	public PagingResultBean<List<Portalcfg>> getAllPortalcfgsPaging(PagingQueryBean<PortalcfgQB> pagingQueryBean) {
		// 分页查询列表
		List<Portalcfg> portalcfgList = portalcfgMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<Portalcfg>> result = new PagingResultBean<List<Portalcfg>>();
		result.setResultList(portalcfgList);

		// 查询记录数
		Integer count = portalcfgMapper.selectCount(pagingQueryBean);
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
	public void proc(Portalcfg Portalcfg) {
		portalcfgMapper.proc(Portalcfg);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPortalcfgMapper(PortalcfgMapper portalcfgMapper) {
		this.portalcfgMapper = portalcfgMapper;
	}
}
