package com.cattsoft.ny.base.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.ProduceAccident;
import com.cattsoft.ny.base.entity.querybean.ProduceAccidentQB;
import com.cattsoft.ny.base.persistence.ProduceAccidentMapper;

/**
 * 生产事故组件
 * 
 * @author maoxiaobing
 * 
 */
@Component
public class ProduceAccidentComponent {
	@Autowired
	private ProduceAccidentMapper produceAccidentMapper;

	public Long createProduceAccident(ProduceAccident produceAccident) {
		return this.produceAccidentMapper.create(produceAccident);
	}

	public void deleteProduceAccident(Long id) {
		this.produceAccidentMapper.delete(id);
	}

	public void updateProduceAccient(ProduceAccident produceAccident) {
		this.produceAccidentMapper.update(produceAccident);
	}

	public ProduceAccident findOneProduceAccident(Long id) {
		return this.produceAccidentMapper.findOne(id);
	}

	public List<ProduceAccident> findProduceAccident(
			ProduceAccident produceAccident) {
		return this.produceAccidentMapper.find(produceAccident);
	}

	public List<ProduceAccident> findPageProduceAccident(
			PagingQueryBean<ProduceAccidentQB> pagingQueryBean) {
		return this.produceAccidentMapper.findPage(pagingQueryBean);
	}

	public Integer findPageCount(ProduceAccidentQB produceAccidentQB) {
		return this.produceAccidentMapper.count(produceAccidentQB);
	}

	public ProduceAccidentMapper getProduceAccidentMapper() {
		return produceAccidentMapper;
	}

	public void setProduceAccidentMapper(
			ProduceAccidentMapper produceAccidentMapper) {
		this.produceAccidentMapper = produceAccidentMapper;
	}
}
