package com.cattsoft.baseplatform.func.sm.entity;

import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.json.annotations.JSON;

import com.cattsoft.baseplatform.core.entity.BaseSysUser;

/**
 * 系统用户信息Entity
 * 
 * @author longtao
 * 
 */
public class SysUser extends BaseSysUser {

	private static final long serialVersionUID = -1737419188868726258L;
	// 用户关联员工详细信息
	private Staff staff;
	// 用户关联的部门详细信息
	private SysDept sysDept;
	/*
	// 用户标识
	private Long userId;
	// 登录名称
	private String loginName;
	*/
	// 用户类型A|C
	private String userType;
	// 用户失效时间
	private Timestamp userExpTime;
	// 登录密码
	private String password;
	// 上次密码
	private String oldPwd;
	// 密码变更时间
	private Timestamp pwdChgTime;
	// 密码失效时间
	private Timestamp pwdExpTime;
	// 用户状态A|P
	private String sts;
	private Timestamp stsTime;
	private Timestamp createTime;
	// 用户拥有权限列表
	private List<FuncNode> funcList;
	// 用户功能菜单信息
	private JSONArray userMenu;
	//登录用户id
	private String loginUserId;

	public String getUserOwnerName() {
		return (null != staff) ? staff.getStaffName() : (null != sysDept ? sysDept.getDeptName()
				: "");
	}

	public JSONArray getUserMenu() {
		return userMenu;
	}

	public void setUserMenu(JSONArray userMenu) {
		this.userMenu = userMenu;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public SysDept getSysDept() {
		return sysDept;
	}

	public void setSysDept(SysDept sysDept) {
		this.sysDept = sysDept;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getUserExpTime() {
		return userExpTime;
	}

	public void setUserExpTime(Timestamp userExpTime) {
		this.userExpTime = userExpTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getPwdChgTime() {
		return pwdChgTime;
	}

	public void setPwdChgTime(Timestamp pwdChgTime) {
		this.pwdChgTime = pwdChgTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getPwdExpTime() {
		return pwdExpTime;
	}

	public void setPwdExpTime(Timestamp pwdExpTime) {
		this.pwdExpTime = pwdExpTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getStsTime() {
		return stsTime;
	}

	public void setStsTime(Timestamp stsTime) {
		this.stsTime = stsTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public List<FuncNode> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<FuncNode> funcList) {
		this.funcList = funcList;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

}
