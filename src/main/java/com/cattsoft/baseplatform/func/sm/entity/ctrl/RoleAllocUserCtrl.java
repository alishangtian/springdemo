package com.cattsoft.baseplatform.func.sm.entity.ctrl;

import com.cattsoft.baseplatform.core.entity.Entity;
import com.cattsoft.baseplatform.func.sm.entity.SysUser;

public class RoleAllocUserCtrl extends Entity {

	private static final long serialVersionUID = -8142056165606037391L;
	private SysUser sysUser;
	private boolean checked;

	public RoleAllocUserCtrl(SysUser sysUser, boolean checked) {
		this.sysUser = sysUser;
		this.checked = checked;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
