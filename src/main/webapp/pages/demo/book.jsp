<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<div class="ui-widget-content func-panel">
	<div class="ui-widget-header func-panel-header">客户电话簿信息</div>
	<div class="func-panel-content">
		<table border="0" width="100%">
			<tbody>
				<tr>
					<th>姓名</th>
					<td><input type="text" name="name" id="name"></td>
				</tr>
				<tr>
					<th>号码</th>
					<td><input type="text" name="telNum" id="telNum"></td>
				</tr>
				<tr>
					<th>昵称</th>
					<td><input type="text" name="nickName" id="nickName">
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<dt:button id="queryBtn" label="查  询"></dt:button>
					</td>
				</tr>
			</tbody>
		</table>
		<dt:grid id="book" jsonRoot="result" width="100%" height="100%" url="demo/demoActiom!doBookList.action"
			dataType="json" showPage="true" caption="电话簿查询"
			altRows="true" sortable="true" multiSelect="true">
			<dt:gridColumn name="name" sortable="true" label="姓名"></dt:gridColumn>
			<dt:gridColumn name="phone" sortable="true" label="号码"></dt:gridColumn>
			<dt:gridColumn name="nick" sortable="true" label="昵称"></dt:gridColumn>
		</dt:grid>
	</div>
</div>