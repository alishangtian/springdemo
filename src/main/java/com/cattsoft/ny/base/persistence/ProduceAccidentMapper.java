package com.cattsoft.ny.base.persistence;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.ProduceAccident;
import com.cattsoft.ny.base.entity.querybean.ProduceAccidentQB;
import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

/**
 * 生产事故mapper类
 * 
 * @author maoxiaobing
 * 
 */
@MyBatisRepository
public interface ProduceAccidentMapper {
	/**
	 * 插入一条生产事故
	 * 
	 * @param produceAccident
	 * @return
	 */
	public Long create(ProduceAccident produceAccident);

	/**
	 * 更新一条生产事故
	 * 
	 * @param produceAccident
	 */
	public void update(ProduceAccident produceAccident);

	/**
	 * 删除生产事故
	 * 
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 查找一条生产事故
	 * 
	 * @param id
	 * @return
	 */
	public ProduceAccident findOne(Long id);

	/**
	 * 查找生产事故
	 * 
	 * @param produceAccident
	 * @return
	 */
	public List<ProduceAccident> find(ProduceAccident produceAccident);

	/**
	 * 查找分页生产事故
	 * 
	 * @param pagingQueryBean
	 * @return
	 */
	public List<ProduceAccident> findPage(
			PagingQueryBean<ProduceAccidentQB> pagingQueryBean);

	/**
	 * 查询事故数量
	 * 
	 * @param produceAccidentQB
	 * @return
	 */
	public Integer count(ProduceAccidentQB produceAccidentQB);
}