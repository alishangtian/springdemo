/*
 * Powered By Generator Util
 */

package com.cattsoft.ny.base.persistence;
import java.util.List;

import com.cattsoft.xhrd.core.persistence.mybatis.annotation.MyBatisRepository;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.ny.base.entity.Customer;
import com.cattsoft.ny.base.entity.querybean.CustomerQB;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@MyBatisRepository
public interface CustomerMapper {
	void insert(Customer customer);

	void delete(Long custId);

	void update(Customer customer);

	Customer select(Long custId);

	List<Customer> selectList(CustomerQB queryBean);

	List<Customer> selectPage(PagingQueryBean<CustomerQB> pagingQueryBean);

	Integer selectCount(PagingQueryBean<CustomerQB> pagingQueryBean);

	Object proc(Customer customer);
	
}