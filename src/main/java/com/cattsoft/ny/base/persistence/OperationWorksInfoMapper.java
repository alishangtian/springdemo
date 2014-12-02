/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.OperationWorksInfo;
import com.cattsoft.ny.base.entity.querybean.OperationWorksInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface OperationWorksInfoMapper {
	void insert(OperationWorksInfo operationWorksInfo);

	void delete(Long id);

	void update(OperationWorksInfo operationWorksInfo);

	OperationWorksInfo select(Long id);

	List<OperationWorksInfo> selectList(OperationWorksInfoQB queryBean);

	List<OperationWorksInfo> selectPage(PagingQueryBean<OperationWorksInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<OperationWorksInfoQB> pagingQueryBean);

	Object proc(OperationWorksInfo operationWorksInfo);

	List<News> selectNews();
	
}