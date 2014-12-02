/*
 * Powered By Generator Util
 */
package com.cattsoft.ny.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.component.CustomerComponent;
import com.cattsoft.ny.base.entity.Customer;
import com.cattsoft.ny.base.entity.querybean.CustomerQB;
import com.cattsoft.ny.base.service.CustomerService;
/**
 * 
 * Description: <br>
 * Date: <br>
 * Copyright (c) 2012 CATTSoft <br>
 * 
 * @author CHASE
 */
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	Log log = LogFactory.getLog(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerComponent customerComponent;

	@Override
	public Long createCustomer(Customer customer) {
		return customerComponent.createCustomer(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerComponent.updateCustomer(customer);
	}

	@Override
	public void removeCustomer(Long custId) {
		customerComponent.removeCustomer(custId);
	}

	@Transactional(readOnly = true)
	@Override
	public Customer getCustomer(Long custId) {
		return customerComponent.getCustomer(custId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Customer> getAllCustomers() {
		return customerComponent.getAllCustomers();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Customer> getCustomers(CustomerQB queryBean) {
		return customerComponent.getCustomers(queryBean);
	}

	@Transactional(readOnly = true)
	@Override
	public PagingResultBean<List<Customer>> getAllCustomersPaging(PagingQueryBean<CustomerQB> qb) {
		return customerComponent.getAllCustomersPaging(qb);
	}
	
	
	/*************************************************************/
	/*                      setter and getter                    */
	/*************************************************************/
	
	public void setCustomerComponent(CustomerComponent customerComponent) {
		this.customerComponent = customerComponent;
	}
}
