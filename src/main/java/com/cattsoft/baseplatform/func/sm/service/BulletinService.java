/*
 * Powered By Generator Util
 */
package com.cattsoft.baseplatform.func.sm.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.func.sm.entity.Bulletin;
import com.cattsoft.baseplatform.func.sm.entity.query.BulletinQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
public interface BulletinService {
	/**
	 * 增加bulletin信息
	 * 
	 * @param bulletin bulletin信息
	 *
	 * @return bulletin标识
	 */
	Long createBulletin(Bulletin bulletin);

	/**
	 * 修改bulletin信息
	 * 
	 * @param bulletin bulletin信息
	 */
	void updateBulletin(Bulletin bulletin);

	/**
	 * 删除bulletin信息
	 * 
	 * @param bulletinId bulletin标识
	 */
	void removeBulletin(Long bulletinId);

	/**
	 * 获取bulletin信息
	 * 
	 * @param bulletinId bulletin标识
	 * @return bulletin信息
	 */
	Bulletin getBulletin(Long bulletinId);

	/**
	 * 获取所有bulletin
	 * 
	 * @return 所有bulletin信息的列表
	 */
	List<Bulletin> getAllBulletins();
	
	/**
	 * 根据查询对象查询bulletin结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  bulletin记录列表
	 */
	List<Bulletin> getBulletins(BulletinQB queryBean);

	/**
	 * 分页获取bulletin列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页bulletin列表
	 */
	PagingResultBean<List<Bulletin>> getAllBulletinsPaging(PagingQueryBean<BulletinQB> pagingQueryBean);
}
