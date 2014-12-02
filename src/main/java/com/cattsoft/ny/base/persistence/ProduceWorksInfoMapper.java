/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.ProduceWorksInfo;
import com.cattsoft.ny.base.entity.querybean.ProduceWorksInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface ProduceWorksInfoMapper {
	void insert(ProduceWorksInfo produceWorksInfo);

	void delete(Long id);

	void update(ProduceWorksInfo produceWorksInfo);

	ProduceWorksInfo select(Long id);

	List<ProduceWorksInfo> selectList(ProduceWorksInfoQB queryBean);

	List<ProduceWorksInfo> selectPage(PagingQueryBean<ProduceWorksInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<ProduceWorksInfoQB> pagingQueryBean);

	Object proc(ProduceWorksInfo produceWorksInfo);
	
}