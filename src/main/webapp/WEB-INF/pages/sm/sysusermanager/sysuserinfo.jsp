<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<div style="height: 400px;">
	<div class="ui-widget-header func-header">用户资料</div>
	<s:if test="sysUser!=null">
		<table class="query-form sumit-form" width="100%">
			<caption>用户资料</caption>
			<tr>
				<th>登录名称</th>
				<td><s:property value="sysUser.loginName" /></td>
				<th>用户失效时间</th>
				<td id="tdUserExpTime"><s:date name="sysUser.userExpTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<th>密码设置时间</th>
				<td><s:date name="sysUser.pwdChgTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				<th>密码失效时间</th>
				<td><s:date name="sysUser.pwdExpTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
	</s:if>
	<s:if test="sysUser!=null && sysUser.staff!=null">
		<table class="query-form sumit-form" width="100%">
			<caption>用户资料</caption>
			<tr>
				<th>员工代码</th>
				<td><s:property value="sysUser.staff.staffCode" />
				</td>
				<th>员工姓名</th>
				<td><s:property value="sysUser.staff.staffName" />
				</td>
			</tr>
			<tr>
				<th>固定电话</th>
				<td><s:property value="sysUser.staff.fixNbr" /></td>
				<th>移动电话</th>
				<td><s:property value="sysUser.staff.mobileNbr" />
				</td>
			</tr>
			<tr>
				<th>所属部门</th>
				<td><s:property
						value="sysUser.staff.sysDept.deptName" /></td>
				<th>电子邮箱</th>
				<td><s:property value="sysUser.staff.email" /></td>
			</tr>
		</table>
	</s:if>
	<s:if test="sysUser!=null && sysUser.sysDept!=null">
		<table class="condition" style="width: 100%;" align="center">
			<tr>
				<th>部门代码</th>
				<td><s:property value="sysUser.sysDept.deptCode" />
				</td>
				<th>部门名称</th>
				<td><s:property value="sysUser.sysDept.deptName" />
				</td>
			</tr>
			<tr>
				<th>邮编</th>
				<td><s:property value="sysUser.sysDept.postCode" />
				</td>
				<th>地址</th>
				<td><s:property value="sysUser.sysDept.address" />
				</td>
			</tr>
			<tr>
				<th>联系人</th>
				<td><s:property value="sysUser.sysDept.linkMan" />
				</td>
				<th>联系电话</th>
				<td><s:property value="sysUser.sysDept.telNbr" />
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td><s:property value="sysUser.sysDept.fixNbr" />
				</td>
				<th>电子邮箱</th>
				<td><s:property value="sysUser.sysDept.emailAddr" />
				</td>
			</tr>
			<tr>
				<th>网站地址</th>
				<td><s:property value="sysUser.sysDept.webSite" />
				</td>
				<th>备注</th>
				<td><s:property value="sysUser.sysDept.remarks" />
				</td>
			</tr>
		</table>
	</s:if>
</div>
<div class="tab-foot">
	<func-tags:funcItemAuth funcItemId="1020010102">
		<dt:button id="changeSysUserExpTimeBtn" label="更改用户失效时间" width="200"
			click="onChangeSysUserExpTime" height="30"></dt:button>
	</func-tags:funcItemAuth>
</div>