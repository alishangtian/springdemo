package com.cattsoft.baseplatform.func.sm.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.cattsoft.baseplatform.core.entity.PagingQueryBean;
import com.cattsoft.baseplatform.core.entity.PagingResultBean;
import com.cattsoft.baseplatform.core.util.IdConverter;
import com.cattsoft.baseplatform.core.util.IdDomainBean;
import com.cattsoft.baseplatform.func.sm.entity.SysDept;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;
import com.cattsoft.baseplatform.func.sm.entity.query.SysDeptQuery;
import com.cattsoft.baseplatform.func.sm.service.HistoryDataQueryService;
import com.cattsoft.baseplatform.func.sm.service.SysDeptService;
import com.cattsoft.baseplatform.func.sm.util.SysConstants;
import com.cattsoft.baseplatform.web.action.PagingSupportAction;

/**
 * 组织部门管理
 * 
 * @author zhangweiqiang
 * 
 */
public class SysDeptManagerAction extends PagingSupportAction {

	private static final long serialVersionUID = 1L;
	private SysDeptService sysDeptService;
	private HistoryDataQueryService historyDataQueryService;
	private PagingResultBean<List<SysDept>> sysDeptResult;
	private PagingResultBean<List<SysDept>> sysDeptResultHistory;
	private boolean result;
	private String msg;
	private Long deptIdHistory;
	private List<SysUser> sysUsersHistory;
	private List<SysDept> sysDeptList;
	@SuppressWarnings("rawtypes")
	private IdConverter idConverter;

	public void setIdConverter(
			@SuppressWarnings("rawtypes") IdConverter idConverter) {
		this.idConverter = idConverter;
	}

	public List<SysDept> getSysDeptList() {
		return sysDeptList;
	}

	public void setSysDeptList(List<SysDept> sysDeptList) {
		this.sysDeptList = sysDeptList;
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

	public PagingResultBean<List<SysDept>> getSysDeptResult() {
		return sysDeptResult;
	}

	public void setSysDeptResult(PagingResultBean<List<SysDept>> sysDeptResult) {
		this.sysDeptResult = sysDeptResult;
	}

	public PagingResultBean<List<SysDept>> getSysDeptResultHistory() {
		return sysDeptResultHistory;
	}

	public void setSysDeptResultHistory(
			PagingResultBean<List<SysDept>> sysDeptResultHistory) {
		this.sysDeptResultHistory = sysDeptResultHistory;
	}

	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}

	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}

	public HistoryDataQueryService getHistoryDataQueryService() {
		return historyDataQueryService;
	}

	public void setHistoryDataQueryService(
			HistoryDataQueryService historyDataQueryService) {
		this.historyDataQueryService = historyDataQueryService;
	}

	public Long getDeptIdHistory() {
		return deptIdHistory;
	}

	public void setDeptIdHistory(Long deptIdHistory) {
		this.deptIdHistory = deptIdHistory;
	}

	public List<SysUser> getSysUsersHistory() {
		return sysUsersHistory;
	}

	public void setSysUsersHistory(List<SysUser> sysUsersHistory) {
		this.sysUsersHistory = sysUsersHistory;
	}

	/**
	 * 查询部门列表
	 * 
	 * @return
	 */
	public String getSysDeptPaging() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysDeptQuery sysDeptQuery = new SysDeptQuery();
		sysDeptQuery.setDeptName(request.getParameter("deptName"));
		PagingQueryBean<SysDeptQuery> qb = buildPagingQueryBean(request,
				sysDeptQuery);
		sysDeptResult = sysDeptService.getSysDeptPaging(qb);
		return "getSysDeptPaging";
	}

	/**
	 * 查询部门详细信息
	 * 
	 * @return
	 */
	public String getSysDeptDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long deptId = NumberUtils.toLong(request.getParameter("deptId"), -1);
		request.setAttribute("sysDept", sysDeptService.getSysDept(deptId));
		return "getSysDeptDetail";
	}

	/**
	 * 查询部门员工列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadStaffs() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long deptId = NumberUtils.toLong(request.getParameter("deptId"), -1);

		/* 进行键值对的转换 */
		List<IdDomainBean> converFields = new ArrayList<IdDomainBean>();
		IdDomainBean idDomain = new IdDomainBean();
		idDomain.setDomainCode(SysConstants.DomainCode.STAFF_STS);
		idDomain.setIdCode("sts");
		converFields.add(idDomain);
		request.setAttribute("staffList", this.idConverter.convert(
				sysDeptService.loadStaffs(deptId), converFields));
		return "loadStaffs";
	}

	/**
	 * 查询部门用户列表
	 * 
	 * @return
	 */
	public String loadSysUsers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long deptId = NumberUtils.toLong(request.getParameter("deptId"), -1);
		request.setAttribute("sysUserList", sysDeptService.loadSysUsers(deptId));
		return "loadSysUsers";
	}

	/**
	 * 加载部门详细信息页面
	 * 
	 * @return
	 */
	public String loadSysDeptDetail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long deptId = NumberUtils.toLong(request.getParameter("deptId"), -1);
		if (deptId == -1) {
			request.setAttribute("sysDept", new SysDept());
		} else {
			request.setAttribute("sysDept", sysDeptService.getSysDept(deptId));
		}
		return "loadSysDeptDetail";
	}

	/**
	 * 注销用户
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
		sysDeptService.removeSysUsers(userIdList);
		result = true;
		return "removeSysUsers";
	}

	/**
	 * 新增部门
	 * 
	 * @return
	 */
	public String createSysDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysDept sysDept = buildSysDeptData(request);
		if (sysDeptService.deptCodeBeenUsed(sysDept.getDeptCode())) {
			result = false;
			msg = "部门代码存在，请重新输入";
			return "createSysDept";
		}
		long deptId = sysDeptService.createSysDept(sysDept);
		result = true;
		msg = String.valueOf(deptId);
		return "createSysDept";
	}

	/**
	 * 更新部门信息
	 * 
	 * @return
	 */
	public String modifySysDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysDept sysDept = new SysDept();
		SysDept oldSysDept = sysDeptService.getSysDept(NumberUtils
				.toLong(request.getParameter("new_deptId")));
		try {
			BeanUtilsBean.getInstance().copyProperties(sysDept, oldSysDept);
		} catch (Exception e) {
			return ERROR;
		}
		sysDept = buildSysDeptData(request, sysDept);
		sysDeptService.modifySysDept(sysDept);
		result = true;
		return "modifySysDept";
	}

	/**
	 * 注销部门
	 * 
	 * @return
	 */
	public String removeSysDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		long deptId = NumberUtils.toLong(request.getParameter("deptId"), -1);
		if (sysDeptService.hasStaff(deptId)) {
			result = false;
			msg = "部门下有未离职的员工，不允许注销该部门";
			return "removeSysDept";
		}
		sysDeptService.removeSysDept(deptId);
		result = true;
		return "removeSysDept";
	}

	/**
	 * 构建用户数据
	 * 
	 * @param request
	 * @return
	 */
	private SysDept buildSysDeptData(HttpServletRequest request) {
		SysDept sysDept = new SysDept();
		return buildSysDeptData(request, sysDept);
	}

	/**
	 * 构建用户数据
	 * 
	 * @param request
	 * @param sysDept
	 * @return
	 */
	private SysDept buildSysDeptData(HttpServletRequest request, SysDept sysDept) {
		if (request.getParameter("new_deptId") != null
				&& !"".equals(request.getParameter("new_deptId"))) {
			sysDept.setDeptId(NumberUtils.toLong(request
					.getParameter("new_deptId")));
		}
		sysDept.setDeptCode(request.getParameter("new_deptCode"));
		sysDept.setDeptName(request.getParameter("new_deptName"));
		sysDept.setSuperDeptId(NumberUtils.toLong(
				request.getParameter("new_superDeptId"), -1));
		sysDept.setPostCode(request.getParameter("new_postCode"));
		sysDept.setTelNbr(request.getParameter("new_telNbr"));
		sysDept.setFaxNbr(request.getParameter("new_faxNbr"));
		sysDept.setLinkMan(request.getParameter("new_linkMan"));
		sysDept.setEmailAddr(request.getParameter("new_emailAddr"));
		sysDept.setWebSite(request.getParameter("new_webSite"));
		sysDept.setAddress(request.getParameter("new_address"));
		sysDept.setRemarks(request.getParameter("new_remarks"));
		return sysDept;
	}

	/**
	 * 跳转到部门历史资料
	 * 
	 * @return
	 */
	public String historyResource() {
		return "historyResource";
	}

	/**
	 * 查询部门历史资料
	 * 
	 * @return
	 */
	public String getSysDeptPagingHistory() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysDeptQuery sysDeptQuery = new SysDeptQuery();
		sysDeptQuery.setDeptName(request.getParameter("deptName"));
		sysDeptQuery.setDeptCode(request.getParameter("deptCode"));

		PagingQueryBean<SysDeptQuery> qb = buildPagingQueryBean(request,
				sysDeptQuery);
		sysDeptResultHistory = historyDataQueryService.getSysDeptPaging(qb);
		return "getSysDeptPagingHistory";
	}

	/**
	 * 查询部门使用过的用户信息
	 * 
	 * @return
	 */
	public String loadDeptUsers() {

		try {
			if (null == deptIdHistory) {
				sysUsersHistory = new ArrayList<SysUser>();
			} else {
				sysUsersHistory = historyDataQueryService
						.loadDeptUsers(deptIdHistory);
			}
		} catch (Exception e) {
			sysUsersHistory = new ArrayList<SysUser>();
		}

		return "loadDeptUsers";
	}

	/**
	 * 获取部门树：供其他模块使用
	 * 
	 * @return
	 */
	public String getSysDeptData() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String isRoot = request.getParameter("isRoot");
		if (isRoot == null || "".equals(isRoot)) {// 根节点
			sysDeptList = new ArrayList<SysDept>();
			sysDeptList.add(sysDeptService.getRootSysDept());
		} else {
			long deptId = NumberUtils.toInt(request.getParameter("deptId"), -1);
			sysDeptList = sysDeptService.getChildrenSysDept(deptId);
		}
		return "getSysDeptData";
	}

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	public String getAllSysDeptData() {
		sysDeptList = sysDeptService.getAllSysDeptData();
		return "getAllSysDeptData";
	}

}
