<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<table>
	<tbody>
		<tr>
			<td><label> 临时号码: </label></td>
			<td><dt:input id="number" inputType="text"></dt:input>
				<dt:button id="add" label="添加"></dt:button>
			</td>
		</tr>
		<tr>
			<td valign="top">信息接收人:</td>
			<td><dt:input id="telnums" inputType="textarea" prompt="信息接收人" required="true"  height="100"></dt:input>
				<br /><dt:button id="del" label="删除"></dt:button> &nbsp;&nbsp;&nbsp; 
				<dt:button id="empty" label="清空"></dt:button>
			</td>
			<td valign="top">短信内容 : <br /> <br />
				<dt:button id="template" label="短信模板"></dt:button>
			</td>
			<td><dt:input id="content" inputType="textarea" prompt="短信内容" required="true" maxVal="69" immValidate="true" height="100"></dt:input>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="4">
				<dt:button id="send" label="发送"></dt:button>
			</td>
		</tr>

	</tbody>
</table>