<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<table class="condition" style="width: 100%;" align="center">
	<tr>
		<th width="20%">部门代码</th>
		<td width="30%">${sysDept.deptCode}</td>
		<th width="20%">部门名称</th>
		<td width="30%">${sysDept.deptName}</td>
	</tr>
	<tr>
		<th>邮编</th>
		<td>${sysDept.postCode}</td>
		<th>联系电话</th>
		<td>${sysDept.telNbr}</td>
	</tr>
	<tr>
		<th>传真</th>
		<td>${sysDept.faxNbr}</td>
		<th>联系人</th>
		<td>${sysDept.linkMan}</td>
	</tr>
	<tr>
		<th>邮箱</th>
		<td>${sysDept.emailAddr}</td>
		<th>网站地址</th>
		<td>${sysDept.webSite}</td>
	</tr>
	<tr>
		<th>地址</th>
		<td>${sysDept.address}</td>
		<th>备注</th>
		<td>${sysDept.remarks}</td>
	</tr>
</table>
<div class="tab-foot">
	<func-tags:funcItemAuth funcItemId="1010010102">
		<dt:button id="changeSysDeptInfoBtn" label="修改"
			click="openChangeSysDept" />&nbsp;&nbsp;
	</func-tags:funcItemAuth>
	<func-tags:funcItemAuth funcItemId="1010010103">
		<dt:button id="removeSysDeptInfoBtn" label="注销" click="removeDept" />&nbsp;&nbsp;
	</func-tags:funcItemAuth>
</div>
