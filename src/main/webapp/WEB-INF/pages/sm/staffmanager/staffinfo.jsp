<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<table width="100%" class="query-form sumit-form">
	<caption>员工信息</caption>
		<tr>
			<th>员工代码</th>
			<td>${staffConver.entity.staffCode}</td>
			<th>员工名称</th>
			<td>${staffConver.entity.staffName}</td>
		</tr>
		<tr>
			<th>移动电话</th>
			<td>${staffConver.entity.mobileNbr}
			</td>
			<th>固定电话</th>
			<td>${staffConver.entity.fixNbr}</td>
		</tr>
		<tr>
			<th>电子邮箱</th>
			<td>${staffConver.entity.email}</td>
			<th>在岗状态</th>
			<td>${staffConver.idMap.sts}</td>
		</tr>
	</table>
<table width="100%" class="query-form sumit-form">
	<caption>所属客户信息</caption>
		<tr>
			<th>客户代码</th>
			<td>${staffConver.entity.sysDept.deptCode}</td>
			<th>客户名称</th>
			<td>${staffConver.entity.sysDept.deptName}</td>
		</tr>
		<tr>
			<th>邮编</th>
			<td>${staffConver.entity.sysDept.postCode}</td>
			<th>地址</th>
			<td>${staffConver.entity.sysDept.address}</td>
		</tr>
		<tr>
			<th>联系人</th>
			<td>${staffConver.entity.sysDept.linkMan}</td>
			<th>联系电话</th>
			<td>${staffConver.entity.sysDept.telNbr}</td>
		</tr>
		<tr>
			<th>传真</th>
			<td>${staffConver.entity.sysDept.faxNbr}</td>
			<th>电子邮箱</th>
			<td>${staffConver.entity.sysDept.emailAddr}</td>
		</tr>
		<tr>
			<th>网站地址</th>
			<td>${staffConver.entity.sysDept.webSite}</td>
			<th>备注</th>
			<td>${staffConver.entity.sysDept.remarks}</td>
		</tr>
	</table>
</div>
<div class="query-form-btns">
	<func-tags:funcItemAuth funcItemId="1010020102">
		<dt:button id="changeStaffInfoBtn" label="修改" click="openChangeStaff" />
	</func-tags:funcItemAuth>
	<func-tags:funcItemAuth funcItemId="1010020103">
		<dt:button id="retainStaffBtn" label="停薪留职" click="retainStaff" />
	</func-tags:funcItemAuth>
	<func-tags:funcItemAuth funcItemId="1010020104">
		<dt:button id="removeStaffBtn" label="离职" click="removeStaff" />
	</func-tags:funcItemAuth>
</div>