package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.IdConverter;
import com.cattsoft.baseplatform.core.util.IdConvertionBean;
import com.cattsoft.baseplatform.core.util.IdDomainBean;
import com.cattsoft.baseplatform.func.sm.entity.Staff;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.StaffQuery;
import com.cattsoft.baseplatform.func.sm.service.HistoryDataQueryService;
import com.cattsoft.baseplatform.func.sm.service.StaffService;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * 员工管理
 * 
 * @author zhangweiqiang
 * 
 */
public class StaffManagerAction extends PagingSupportAction {

	private static final long serialVersionUID = 1L;
	private StaffService staffService;
	private HistoryDataQueryService historyDataQueryService;
	private PagingResultBean<List<Staff>> staffResult;
	private PagingResultBean<List<Staff>> staffResultHistory;
	private boolean result;
	private String msg;
	private static String AJAXSUCCESS = "ajaxsuccess";
	private Long staffIdHistory;
	private List<SysUser> sysUsersHistory;
	private Staff staff;
	@SuppressWarnings("rawtypes")
	private IdConverter idConverter;

	private IdConvertionBean<Staff> staffConver;

	public IdConvertionBean<Staff> getStaffConver() {
		return staffConver;
	}

	public void setStaffConver(IdConvertionBean<Staff> staffConver) {
		this.staffConver = staffConver;
	}

	public void setIdConverter(@SuppressWarnings("rawtypes") IdConverter idConverter) {
		this.idConverter = idConverter;
	}

	public PagingResultBean<List<Staff>> getStaffResult() {
		return staffResult;
	}

	public void setStaffResult(PagingResultBean<List<Staff>> staffResult) {
		this.staffResult = staffResult;
	}

	public PagingResultBean<List<Staff>> getStaffResultHistory() {
		return staffResultHistory;
	}

	public void setStaffResultHistory(PagingResultBean<List<Staff>> staffResultHistory) {
		this.staffResultHistory = staffResultHistory;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	public HistoryDataQueryService getHistoryDataQueryService() {
		return historyDataQueryService;
	}

	public void setHistoryDataQueryService(HistoryDataQueryService historyDataQueryService) {
		this.historyDataQueryService = historyDataQueryService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Long getStaffIdHistory() {
		return staffIdHistory;
	}

	public void setStaffIdHistory(Long staffIdHistory) {
		this.staffIdHistory = staffIdHistory;
	}

	public List<SysUser> getSysUsersHistory() {
		return sysUsersHistory;
	}

	public void setSysUsersHistory(List<SysUser> sysUsersHistory) {
		this.sysUsersHistory = sysUsersHistory;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * 查询员工列表
	 * 
	 * @return
	 */
	public String getStaffPaging() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StaffQuery staffQuery = new StaffQuery();
		staffQuery.setStaffName(request.getParameter("staffName"));
		if (request.getParameter("deptId") != null && !"".equals(request.getParameter("deptId"))) {
			staffQuery.setDeptId(NumberUtils.toLong(request.getParameter("deptId")));
		}
		PagingQueryBean<StaffQuery> qb = buildPagingQueryBean(request, staffQuery);
		staffResult = staffService.getStaffPaging(qb);
		return "getStaffPaging";
	}

	/**
	 * 查询员工详细信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getStaffDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long staffId = NumberUtils.toLong(request.getParameter("staffId"), -1);
		/* 进行键值对的转换 */
		List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
		IdDomainBean idDomain = new IdDomainBean();
		idDomain.setDomainCode(SysConstants.DomainCode.STAFF_STS);
		idDomain.setIdCode("sts");
		converFields.add(idDomain);
		this.staffConver = this.idConverter.convert(this.staffService.getStaff(staffId),
				converFields);

		return "getStaffDetail";
	}

	/**
	 * 查询员工用户列表
	 * 
	 * @return
	 */
	public String loadSysUsers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long staffId = NumberUtils.toLong(request.getParameter("staffId"), -1);
		request.setAttribute("sysUserList", staffService.loadSysUsers(staffId));
		return "loadSysUsers";
	}

	/**
	 * 加载员工详细信息页面
	 * 
	 * @return
	 */
	public String loadStaffDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long staffId = NumberUtils.toLong(request.getParameter("staffId"), -1);
		if (staffId == -1) {
			request.setAttribute("staff", new Staff());
		} else {
			request.setAttribute("staff", staffService.getStaff(staffId));
		}
		return "loadStaffDetail";
	}

	/**
	 * 员工离职
	 * 
	 * @return
	 */
	public String removeStaff() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long staffId = NumberUtils.toLong(request.getParameter("staffId"));
		staffService.removeStaff(staffId);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 员工停薪留职
	 * 
	 * @return
	 */
	public String retainStaff() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long staffId = NumberUtils.toLong(request.getParameter("staffId"));
		staffService.retainStaff(staffId);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 新增员工
	 * 
	 * @return
	 */
	public String createStaff() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Staff staff = buildStaffData(request);
		if (staffService.staffCodeBeenUsed(staff.getStaffCode())) {
			result = false;
			msg = "员工代码存在，请重新输入";
			return AJAXSUCCESS;
		}
		long staffId = staffService.createStaff(staff);
		result = true;
		msg = String.valueOf(staffId);
		return AJAXSUCCESS;
	}

	/**
	 * 更新员工信息
	 * 
	 * @return
	 */
	public String modifyStaff() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Staff staff = new Staff();
		Staff oldStaff = staffService.getStaff(NumberUtils.toLong(request
				.getParameter("new_staffId")));
		try {
			BeanUtilsBean.getInstance().copyProperties(staff, oldStaff);
		} catch (Exception e) {
			return ERROR;
		}
		staff = buildStaffData(request, staff);
		staffService.modifyStaff(staff);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 注销员工下的用户
	 * 
	 * @return
	 */
	public String removeSysUsers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String userIds = request.getParameter("userIds");
		List<Long> userIdList = new ArrayList<Long>();
		JSONArray array = JSONArray.fromObject(userIds);
		for (Object object : array) {
			userIdList.add(NumberUtils.toLong(String.valueOf(object), -1));
		}
		staffService.removeSysUsers(userIdList);
		result = true;
		return AJAXSUCCESS;
	}

	/**
	 * 构建用户数据
	 * 
	 * @param request
	 * @return
	 */
	private Staff buildStaffData(HttpServletRequest request) {
		Staff staff = new Staff();
		return buildStaffData(request, staff);
	}

	/**
	 * 构建用户数据
	 * 
	 * @param request
	 * @param sysDept
	 * @return
	 */
	private Staff buildStaffData(HttpServletRequest request, Staff staff) {
		if (request.getParameter("new_staffId") != null
				&& !"".equals(request.getParameter("new_staffId"))) {
			staff.setStaffId(NumberUtils.toLong(request.getParameter("new_staffId")));
		}
		staff.setStaffName(request.getParameter("new_staffName"));
		staff.setStaffCode(request.getParameter("new_staffCode"));
		staff.setDeptId(NumberUtils.toLong(request.getParameter("new_deptId"), -1));
		staff.setMobileNbr(request.getParameter("new_mobileNbr"));
		staff.setFixNbr(request.getParameter("new_fixNbr"));
		staff.setEmail(request.getParameter("new_email"));
		return staff;
	}

	/**
	 * 跳转到员工资料
	 * 
	 * @return
	 */
	public String historyResource() {
		return "historyResource";
	}

	/**
	 * 查询员工列表
	 * 
	 * @return
	 */
	public String getStaffPagingHistory() {
		HttpServletRequest request = ServletActionContext.getRequest();
		StaffQuery staffQuery = new StaffQuery();
		staffQuery.setStaffName(request.getParameter("staffName"));
		staffQuery.setStaffCode(request.getParameter("staffCode"));
		if (request.getParameter("deptId") != null && !"".equals(request.getParameter("deptId"))) {
			staffQuery.setDeptId(NumberUtils.toLong(request.getParameter("deptId")));
		}
		PagingQueryBean<StaffQuery> qb = buildPagingQueryBean(request, staffQuery);
		staffResultHistory = historyDataQueryService.getStaffPaging(qb);
		return "getStaffPagingHistory";
	}

	/**
	 * 查询员工使用过的用户信息
	 * 
	 * @return
	 */
	public String loadStaffUsers() {

		try {
			sysUsersHistory = historyDataQueryService.loadStaffUsers(staffIdHistory);
		} catch (Exception e) {
			sysUsersHistory = null;
		}

		return "loadStaffUsers";
	}

	/**
	 * 跳转到个人资料
	 * 
	 * @return
	 */
	public String jumpPersonInfoManager() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SysUser sysUser = (SysUser) session.getAttribute(SysConstants.LOGIN_USER);
		this.staff = sysUser.getStaff();

		/*
		 * personInfo=new Staff(); personInfo.setStaffId(Long.valueOf(10000));
		 * personInfo.setStaffCode("20110609"); personInfo.setStaffName("龙涛");
		 * SysDept dept=new SysDept(); dept.setDeptName("基础平台");
		 * personInfo.setSysDept(dept); personInfo.setFixNbr("1111");
		 * personInfo.setMobileNbr("2222");
		 * personInfo.setEmail("longtao@cattsoft.com");
		 */

		return "jumpPersonInfoManager";
	}

	/**
	 * 修改个人资料
	 * 
	 * @return
	 */
	public String editPersonInfo() {
		try {
			staffService.modifyStaff(staff);
		} catch (Exception e) {
			result = false;
		}
		result = true;
		return "editPersonInfo";
	}
}
