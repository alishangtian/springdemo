/*
 * Powered By Generator Util
 */

package com.cattsoft.baseplatform.func.sm.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
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
@MyBatisRepository
public interface BulletinMapper {
	void insert(Bulletin bulletin);

	void delete(Long bulletinId);

	void update(Bulletin bulletin);

	Bulletin select(Long bulletinId);

	List<Bulletin> selectList(BulletinQB queryBean);

	List<Bulletin> selectPage(PagingQueryBean<BulletinQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<BulletinQB> pagingQueryBean);

	Object proc(Bulletin bulletin);
	
}