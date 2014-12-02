<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<form id="add_new_dept_form" name="add_new_dept_form">
	<input type="hidden" id="new_deptId" name="new_deptId"
		value="${sysDept.deptId}" />
	<table style="width: 100%; height: auto;" class="view-table"
		cellpadding="0" cellspacing="0">
		<tr>
			<td>部门代码</td>
			<td><dt:input id="new_deptCode" name="new_deptCode" length="12"
					immValidate="true" required="true" prompt="部门代码"
					value="${sysDept.deptCode}"></dt:input>
			</td>
			<td>部门名称</td>
			<td><dt:input id="new_deptName" name="new_deptName" length="30"
					immValidate="true" required="true" prompt="部门名称"
					value="${sysDept.deptName}"></dt:input>
			</td>
		</tr>
		<tr>
			<td>上级部门</td>
			<td colspan="3"><dt:input name="new_super_deptName"
					id="new_super_deptName" disabled="true" required="true"
					prompt="上级部门" value="${sysDept.superDeptId}"></dt:input><img
				id="parent_dept_icon" class="search-icon" alt="选择部门" title="选择部门"
				src="images/common/search.gif" /> <input id="new_superDeptId"
				name="new_superDeptId" type="hidden" value="${sysDept.superDeptId}" />
			</td>
		</tr>
		<tr>
			<td>邮编</td>
			<td><dt:input id="new_postCode" name="new_postCode" length="6"
					inputType="zipcode" immValidate="true" prompt="邮编"
					value="${sysDept.postCode}"></dt:input></td>
			<td>联系电话</td>
			<td><dt:input id="new_telNbr" name="new_telNbr" length="30"
					inputType="tel" immValidate="true" prompt="联系电话"
					value="${sysDept.telNbr}"></dt:input></td>
		</tr>
		<tr>
			<td>传真</td>
			<td><dt:input id="new_faxNbr" name="new_faxNbr" length="30"
					inputType="tel" immValidate="true" prompt="传真" 
					value="${sysDept.faxNbr}"></dt:input>
			</td>
			<td>联系人</td>
			<td><dt:input id="new_linkMan" name="new_linkMan" length="30"
					immValidate="true" prompt="联系人" value="${sysDept.linkMan}"></dt:input>
			</td>
		</tr>
		<tr>
			<td>邮箱</td>
			<td><dt:input id="new_emailAddr" name="new_emailAddr"
					length="30" inputType="email" immValidate="true" prompt="邮箱"
					value="${sysDept.emailAddr}"></dt:input></td>
			<td>网站地址</td>
			<td><dt:input id="new_webSite" name="new_webSite" length="30"
					inputType="url" immValidate="true" prompt="网站地址"
					value="${sysDept.webSite}"></dt:input>
			</td>
		</tr>
		<tr>
			<td>地址</td>
			<td><dt:input id="new_address" name="new_address" length="255"
					immValidate="true" prompt="地址" value="${sysDept.address}"></dt:input>
			</td>
			<td>备注</td>
			<td><dt:input id="new_remarks" name="new_remarks" length="255"
					immValidate="true" prompt="备注" value="${sysDept.remarks}"></dt:input>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	$(initAddNewDeptPage);
</script>