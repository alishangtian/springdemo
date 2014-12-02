/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service;

import java.util.List;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
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
public interface CustomerService {
	/**
	 * 增加customer信息
	 * 
	 * @param customer customer信息
	 *
	 * @return customer标识
	 */
	Long createCustomer(Customer customer);

	/**
	 * 修改customer信息
	 * 
	 * @param customer customer信息
	 */
	void updateCustomer(Customer customer);

	/**
	 * 删除customer信息
	 * 
	 * @param custId customer标识
	 */
	void removeCustomer(Long custId);

	/**
	 * 获取customer信息
	 * 
	 * @param custId customer标识
	 * @return customer信息
	 */
	Customer getCustomer(Long custId);

	/**
	 * 获取所有customer
	 * 
	 * @return 所有customer信息的列表
	 */
	List<Customer> getAllCustomers();
	
	/**
	 * 根据查询对象查询customer结果列表
	 * @param queryBean  查询对象
	 *
	 * @return  customer记录列表
	 */
	List<Customer> getCustomers(CustomerQB queryBean);

	/**
	 * 分页获取customer列表
	 * 
	 * @param pagingQueryBean  分页查询对象
	 *
	 * @return 分页customer列表
	 */
	PagingResultBean<List<Customer>> getAllCustomersPaging(PagingQueryBean<CustomerQB> pagingQueryBean);
}
