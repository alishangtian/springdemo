package com.cattsoft.ny.base.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.ny.base.entity.Customer;
import com.cattsoft.ny.base.entity.querybean.CustomerQB;
import com.cattsoft.ny.base.service.CustomerService;

/**
 * 
 * 客户管理action Copyright:DATANG SOFTWARE CO.LTD<br>
 * 
 * @author Administrator
 * 
 */
public class CustomerInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Boolean flag = false ;
	private CustomerService customerService;
	private List<Customer> customers;
	private Customer customer;
	private CustomerQB customerQB;
	private PagingQueryBean<CustomerQB> cqb;
	private PagingResultBean<List<Customer>> customersInfoResult;
	private String custName;
	private Boolean flagName;
	private String msg;
	public String execute() {

		return "jumpCustomersInfoManager";
	}
	
	/**
	 * 
	 */
	public String aa(){
		return null;
	}
	
	/**
	 * 跳转新增客户界面准备数据
	 */
	public String createCustomerURL(){
		
		return "createCustomerURL";
	}
	
	/**
	 * 新增顾客
	 */
	public String createCustomer(){
		try {
			customer.setState("1");
			customer.setCtime(new Date());
			customerService.createCustomer(customer);
			this.flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.flag = false;
		}
		return "createCustomer";
	}
	
	/**
	 * 跳转修改客户界面准备数据
	 */
	public String updateCustomerURL(){
		customer = customerService.getCustomer(customer.getCustId());
		return "updateCustomerURL";
	}
	/**
	 * 修改顾客信息
	 */
	public String updateCustomer(){
		try {
			customerService.updateCustomer(customer);
			this.flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.flag = false;
		}
		return "updateCustomer";
	}
	
	/**
	 * 作废顾客信息
	 */
	public String updateCustomer2Unable(){
		try {
			String state = customer.getState();
			customer = customerService.getCustomer(customer.getCustId());
			customer.setState(state);
			customerService.updateCustomer(customer);
			this.flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.flag = false;
			e.printStackTrace();
		}
		return "updateCustomer2Unable";
	}
	
	/**
	 * 根据客户ID删除客户信息
	 */
	public String removeCustomerByCustId(){
		try {
			customerService.removeCustomer(customer.getCustId());
			this.flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.flag = false;
		}
		return "removeCustomerByCustId";
	}
	
	/**
	 * 根据客户ID获取客户信息
	 */
	public String getCustomerByCustId(){
		customer = customerService.getCustomer(customer.getCustId());
		return "getCustomerByCustId";
	}
	
	/**
	 * 根据客户属性组合keyValue 获取客户信息列表
	 */
	public String getCustomersByQueryBean(){
		this.customer = null == this.customer ? new Customer() : this.customer;
		HttpServletRequest request = ServletActionContext.getRequest();
		/* 查询所有满足条件的列表 */
		cqb = new PagingQueryBean<CustomerQB>();
		CustomerQB customerQB = new CustomerQB();
		customerQB.setName("".equals(customer.getName())?null:customer.getName());
		customerQB.setState("".equals(customer.getState())?null:customer.getState());
		customerQB.setContacts("".equals(customer.getContacts())?null:customer.getContacts());
		cqb = buildPagingQueryBean(request, customerQB);
		this.customersInfoResult = customerService.getAllCustomersPaging(cqb);
		return "getCustomersByQueryBean";
	}
	
	/**
	 * 获取所有customer
	 * 
	 */
	public  String getAllCustomers(){
		customers = customerService.getAllCustomers();
		return "getAllCustomers";
	}
	/**
	 * 查询客户名称是否存在
	 * @return
	 */
	public String valiName(){
		List<Customer> lc = customerService.getAllCustomers();
		for(Customer c : lc){
			if(custName.equals(c.getName()))
			{
				System.out.println("用户名存在");
				flag=true;
				break;
			}else{
				flag=false;
			}
		}
		
		return "valiName";
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public PagingResultBean<List<Customer>> getCustomersInfoResult() {
		return customersInfoResult;
	}

	public void setCustomersInfoResult(
			PagingResultBean<List<Customer>> customersInfoResult) {
		this.customersInfoResult = customersInfoResult;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public CustomerQB getCustomerQB() {
		return customerQB;
	}

	public void setCustomerQB(CustomerQB customerQB) {
		this.customerQB = customerQB;
	}

	public Boolean getFlagName() {
		return flagName;
	}

	public void setFlagName(Boolean flagName) {
		this.flagName = flagName;
	}

	public PagingQueryBean<CustomerQB> getCqb() {
		return cqb;
	}

	public void setCqb(PagingQueryBean<CustomerQB> cqb) {
		this.cqb = cqb;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
