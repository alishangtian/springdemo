/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.ProduceSeason;
import com.cattsoft.ny.base.entity.querybean.ProduceSeasonQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface ProduceSeasonMapper {
	void insert(ProduceSeason produceSeason);

	void delete(Long id);

	void update(ProduceSeason produceSeason);

	ProduceSeason select(Long id);

	List<ProduceSeason> selectList(ProduceSeasonQB queryBean);

	List<ProduceSeason> selectPage(PagingQueryBean<ProduceSeasonQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<ProduceSeasonQB> pagingQueryBean);

	Object proc(ProduceSeason produceSeason);
	
}