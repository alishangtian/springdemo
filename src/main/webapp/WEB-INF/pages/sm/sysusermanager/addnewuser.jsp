<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="dt" uri="/dttag"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<form id="add_new_user_form" name="add_new_user_form">
<div id="new_user_table">
	<table class="query-form" width="100%">
		<tr>
			<td>登录名称</td>
			<td><dt:input id="new_loginName" height="25px" width="150px" name="new_loginName"></dt:input>
			</td>
			<td>失效时间</td>
			<td><dt:dateInput id="new_userExpTime" height="25px" width="150px" name="new_userExpTime" required="true" prompt="失效时间" showOnbutton="true" minDate="1d"></dt:dateInput>
			</td>
		</tr>
		<tr>
			<td>归属信息</td>
			<td colspan="3"><input type="radio" id="new_ownerType_S" checked="checked" name="new_ownerType" value="S" />员工
				<input id="new_ownerType_D" type="radio" name="new_ownerType" value="D" />部门
			</td>
		</tr>
	</table>
	<div class="query-form-btns">
		<dt:button id="new_nextBtn" label="下一步" width="100" click="addUserNext1"></dt:button>
	</div>
</div>
<div id="new_staff_table" style="display:none;">
	<table class="query-form" width="100%">
		<tbody id="new_staff_select">
			<tr>
				<td colspan="2"><input type="radio" id="new_staff_old_radio" name="new_staff_type" value="o" onclick="hide1()"/>在职员工</td>
				<td colspan="2"><input type="radio" id="new_staff_new_radio" checked="checked" name="new_staff_type" value="n"  onclick="hide2()"/>新员工</td>
			</tr>
		</tbody>
		<tbody id="new_staff_info_block">
			<tr>
				<td>员工代码</td>
				<td><dt:input id="new_staffCode" name="new_staffCode" length="10" immValidate="true" required="true" prompt="员工代码"></dt:input></td>
				<td>员工姓名</td>
				<td><dt:input id="new_add_staffName" name="new_add_staffName" length="128" immValidate="true" required="true" prompt="员工姓名"></dt:input></td>
			</tr>
			<tr>
				<td>所属部门</td>
				<td><dt:input name="new_add_deptId" id="new_add_deptId" disabled="true" required="true" prompt="所属部门"></dt:input>
					<img id="staff_searchDept_icon" class="search-icon" alt="选择部门" title="选择客户" src="images/common/search.gif" onclick="deptTreewin"/> 
					<input id="new_add_deptId_hid" name="new_add_deptId_hid" type="hidden" />
				</td>
				<td>电子邮箱</td>
				<td><dt:input id="new_add_email" name="new_add_email" length="128" immValidate="true" inputType="mail" prompt="电子邮箱"></dt:input></td>
			</tr>
			<tr>
				<td>移动电话</td>
				<td><dt:input id="new_add_mobileNbr" name="new_add_mobileNbr" length="30" immValidate="true" inputType="mobile" prompt="移动电话"></dt:input></td>
				<td>固定电话</td>
				<td><dt:input id="new_add_fixNbr" name="new_add_fixNbr" length="30" immValidate="true" inputType="tel" prompt="固定电话"></dt:input></td>
			</tr>
			</tbody>
			<tbody id="new_dept_info_block">
				<tr>
					<td>选择部门</td>
					<td colspan="3"><dt:input id="new_dept_deptId"
							name="new_dept_deptId" disabled="true" required="true"
							prompt="所属部门"></dt:input><img id="dept_searchDept_icon"
						class="search-icon" alt="选择部门" src="images/common/search.gif" onclick="deptTreewin()"/> <input
						id="new_dept_deptId_hid" name="new_dept_deptId_hid" type="hidden" />
					</td>
				</tr>
			</tbody>
			<tbody id="new_old_staff_info_block">
				<tr>
					<td colspan="4">员工姓名：<dt:input id="new_oldStaff_query"></dt:input>
						<dt:button id="new_QueryBtn" label="查询" click="queryStaffList"></dt:button>
						<input type="hidden" id="new_oldStaffId" name="new_oldStaffId" />
					</td>
				</tr>
				<tr>
					<td colspan="4"><dt:grid id="staffQueryResult"
							onSelectRow="selectStaffInfo" jsonRoot="resultList" width="100%"
							dataType="json" showPager="true" shrinkToFit="true" sortable="true">
							<dt:gridColumn name="staffId" hidden="true" key="true"></dt:gridColumn>
							<dt:gridColumn name="staffCode" sortable="true" label="员工代码"></dt:gridColumn>
							<dt:gridColumn name="staffName" sortable="true" label="员工姓名"></dt:gridColumn>
							<dt:gridColumn name="sysDept.deptName" sortable="true"
								label="所属部门"></dt:gridColumn>
						</dt:grid></td>
				</tr>
			</tbody>
		</table>
		<div class="query-form-btns user-btns">
			<dt:button id="new_preBtn" label="上一步" width="100" click="addUserPre1"></dt:button> 
			<dt:button id="new_submitBtn" label="确定" width="100" click="createSysUser"></dt:button>
		</div>
</div>
</form>