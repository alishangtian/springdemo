/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.CropsResult;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.querybean.NewsQB;
import com.cattsoft.ny.base.entity.querybean.PickWorkInfoDataQB;
import com.cattsoft.ny.base.entity.querybean.PickWorksInfoQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface PickWorksInfoMapper {
	void insert(PickWorksInfo pickWorksInfo);

	void delete(Long id);

	void update(PickWorksInfo pickWorksInfo);

	PickWorksInfo select(Long id);

	List<PickWorksInfo> selectList(PickWorksInfoQB queryBean);

	List<PickWorksInfo> selectPage(PagingQueryBean<PickWorksInfoQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<PickWorksInfoQB> pagingQueryBean);

	Object proc(PickWorksInfo pickWorksInfo);

	List<CropsResult> selectCrops(PickWorkInfoDataQB pickWorkInfoDataQB);
	
	List<CropsResult> selectCropsData(PickWorkInfoDataQB pickWorkInfoDataQB);

	List<News> selectNews(NewsQB newsQB);
	
}