/*
 * Powered By Generator Util
 */
package com.cattsoft.baseplatform.func.sm.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Bulletin;
import com.cattsoft.baseplatform.func.sm.entity.query.BulletinQB;
import com.cattsoft.baseplatform.func.sm.persistence.BulletinMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class BulletinComponent {
	
	@Autowired
	private BulletinMapper bulletinMapper;

	/**
	 * 插入bulletin一条记录
	 * 
	 * @param bulletin 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createBulletin(Bulletin bulletin) {
		bulletinMapper.insert(bulletin);
		return bulletin.getBulletinId();
	}

	/**
	 * 根据主键删除指定的bulletin记录
	 * 
	 * @param bulletinId 主键值
	 */
	public void removeBulletin(Long bulletinId) {
		bulletinMapper.delete(bulletinId);
	}

	/**
	 * 更新指定的bulletin记录
	 * 
	 * @param bulletin
	 */
	public void updateBulletin(Bulletin bulletin) {
		bulletinMapper.update(bulletin);
	}

	/**
	 * 根据主键查询一条bulletin记录
	 * 
	 * @param bulletinId 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public Bulletin getBulletin(Long bulletinId) {
		return bulletinMapper.select(bulletinId);
	}

	/**
	 * 根据查询对象查询bulletin结果列表
	 * 
	 * @return  bulletin记录列表
	 */
	public List<Bulletin> getBulletins(BulletinQB queryBean) {
		return bulletinMapper.selectList(queryBean);
	}

	/**
	 * 查询所有bulletin记录
	 * 
	 * @return 所有 bulletin记录
	 */
	public List<Bulletin> getAllBulletins() {
		return bulletinMapper.selectList(null);
	}

	/**
	 * 分页查询所有bulletin记录
	 * 
	 * @return 当前页的 bulletin记录
	 */
	public PagingResultBean<List<Bulletin>> getAllBulletinsPaging(PagingQueryBean<BulletinQB> pagingQueryBean) {
		// 分页查询列表
		List<Bulletin> bulletinList = bulletinMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<Bulletin>> result = new PagingResultBean<List<Bulletin>>();
		result.setResultList(bulletinList);

		// 查询记录数
		Integer count = bulletinMapper.selectCount(pagingQueryBean);
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
	public void proc(Bulletin Bulletin) {
		bulletinMapper.proc(Bulletin);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setBulletinMapper(BulletinMapper bulletinMapper) {
		this.bulletinMapper = bulletinMapper;
	}
}
