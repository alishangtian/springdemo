<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<form id="add_new_staff_form" name="add_new_staff_form">
	<input type="hidden" id="new_staffId" name="new_staffId" value="${staff.staffId}" />
	<table width="100%" class="query-form">
		<tr>
			<td>员工代码</td>
			<td><dt:input id="new_staffCode" name="new_staffCode"
					length="10" immValidate="true" required="true"
					prompt="员工代码" value="${staff.staffCode}"></dt:input>
			</td>
		</tr>
		<tr>
			<td>员工名称</td>
			<td><dt:input id="new_staffName" name="new_staffName"
					length="128" required="true" prompt="员工名称"
					value="${staff.staffName}"></dt:input></td>
		</tr>
		<tr>
			<td>所属部门</td>
			<td colspan="3"><dt:input name="new_deptName" id="new_deptName"
					disabled="true" required="true" prompt="所属部门"
					value="${staff.sysDept.deptName}"></dt:input><img
				id="new_dept_icon" class="search-icon" alt="选择部门"
				src="images/common/search.gif" /> <input id="new_deptId"
				name="new_deptId" type="hidden" value="${staff.deptId}" />
			</td>
		</tr>
		<tr>
			<td>移动电话</td>
			<td><dt:input id="new_mobileNbr" name="new_mobileNbr"
					length="30" inputType="mobile" immValidate="true" prompt="移动电话"
					value="${staff.mobileNbr}"></dt:input>
			</td>
		</tr>
		<tr>
			<td>固定电话</td>
			<td><dt:input id="new_fixNbr" name="new_fixNbr" length="30"
					inputType="tel" immValidate="true" prompt="固定电话"
					value="${staff.fixNbr}"></dt:input></td>
		</tr>
		<tr>
			<td>电子邮箱</td>
			<td><dt:input id="new_email" name="new_email" length="128"
					inputType="mail" immValidate="false" prompt="电子邮箱"
					value="${staff.email}"></dt:input>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	initStaffDetailPage();
</script>