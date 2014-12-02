/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingInfo;
import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.CropsResult;
import com.cattsoft.ny.base.entity.News;
import com.cattsoft.ny.base.entity.PickWorksInfo;
import com.cattsoft.ny.base.entity.querybean.NewsQB;
import com.cattsoft.ny.base.entity.querybean.PickWorkInfoDataQB;
import com.cattsoft.ny.base.entity.querybean.PickWorksInfoQB;
import com.cattsoft.ny.base.persistence.PickWorksInfoMapper;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Component
public class PickWorksInfoComponent {
	
	@Autowired
	private PickWorksInfoMapper pickWorksInfoMapper;

	/**
	 * 插入pick_works_info一条记录
	 * 
	 * @param pickWorksInfo 主键通过SEQUENCE生成，在xml中配置
	 * @return 插入记录的主键
	 */
	public Long createPickWorksInfo(PickWorksInfo pickWorksInfo) {
		pickWorksInfoMapper.insert(pickWorksInfo);
		return pickWorksInfo.getId();
	}

	/**
	 * 根据主键删除指定的pick_works_info记录
	 * 
	 * @param id 主键值
	 */
	public void removePickWorksInfo(Long id) {
		pickWorksInfoMapper.delete(id);
	}

	/**
	 * 更新指定的pick_works_info记录
	 * 
	 * @param pickWorksInfo
	 */
	public void updatePickWorksInfo(PickWorksInfo pickWorksInfo) {
		pickWorksInfoMapper.update(pickWorksInfo);
	}

	/**
	 * 根据主键查询一条pick_works_info记录
	 * 
	 * @param id 主键值
	 *
	 * @return 查询到的记录，如果无对应记录，返回null
	 */
	public PickWorksInfo getPickWorksInfo(Long id) {
		return pickWorksInfoMapper.select(id);
	}

	/**
	 * 根据查询对象查询pick_works_info结果列表
	 * 
	 * @return  pick_works_info记录列表
	 */
	public List<PickWorksInfo> getPickWorksInfos(PickWorksInfoQB queryBean) {
		return pickWorksInfoMapper.selectList(queryBean);
	}

	/**
	 * 查询所有pick_works_info记录
	 * 
	 * @return 所有 pick_works_info记录
	 */
	public List<PickWorksInfo> getAllPickWorksInfos() {
		return pickWorksInfoMapper.selectList(null);
	}

	/**
	 * 分页查询所有pick_works_info记录
	 * 
	 * @return 当前页的 pick_works_info记录
	 */
	public PagingResultBean<List<PickWorksInfo>> getAllPickWorksInfosPaging(PagingQueryBean<PickWorksInfoQB> pagingQueryBean) {
		// 分页查询列表
		List<PickWorksInfo> pickWorksInfoList = pickWorksInfoMapper.selectPage(pagingQueryBean);
		PagingResultBean<List<PickWorksInfo>> result = new PagingResultBean<List<PickWorksInfo>>();
		result.setResultList(pickWorksInfoList);

		// 查询记录数
		Integer count = pickWorksInfoMapper.selectCount(pagingQueryBean);
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
	public void proc(PickWorksInfo PickWorksInfo) {
		pickWorksInfoMapper.proc(PickWorksInfo);
	}
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setPickWorksInfoMapper(PickWorksInfoMapper pickWorksInfoMapper) {
		this.pickWorksInfoMapper = pickWorksInfoMapper;
	}

	public List<CropsResult> queryCropsResult(PickWorkInfoDataQB pickWorkInfoDataQB) {
		// TODO Auto-generated method stub
		return pickWorksInfoMapper.selectCrops(pickWorkInfoDataQB);
	}
	
	public List<CropsResult> queryCropsResultData(PickWorkInfoDataQB pickWorkInfoDataQB) {
		// TODO Auto-generated method stub
		return pickWorksInfoMapper.selectCropsData(pickWorkInfoDataQB);
	}


	public List<News> getNews(NewsQB newsQB) {
		// TODO Auto-generated method stub
		return pickWorksInfoMapper.selectNews(newsQB);
	}

}
