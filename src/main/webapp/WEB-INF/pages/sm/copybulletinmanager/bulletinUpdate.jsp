<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告变更</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<script type="text/javascript">
	$(function() {
		$('#bulletinUpdateTitle').val('【变更】 '+$('#bulletinUpdateTitle').val());
		$('#bulletinUpdateId').val($('#bulletinUpdateBulletinId').val());
		startTimeClose();
	});
	function startTimeClose() {
		revokeTimeUpdate.dtdatepicker('option','minDate', publishTimeEdit.dtdatepicker('getDate'));
	}
</script>
</head>
<body>
	<form id="bulletinUpdateForm" method="post">
		<table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr>
				<td><table width="100%" align="center" class="condition">
						<s:hidden id="bulletinUpdateBulletinId" name="bulletin.bulletinId"></s:hidden>
						<s:hidden id="bulletinUpdateId" name="id"></s:hidden>
						<tr>
							<th width="10%" align="left">公告标题：</th>
							<td width="90%">
								<dt:input id="bulletinUpdateTitle"  name="bulletin.bulletinTitle" required="true" prompt="公告标题" length="128"></dt:input>
							</td>
						</tr>
						<tr>
							<th width="10%" align="left">公告内容：</th>
							<td width="90%">
								 <dt:ckeditor id="bulletinUpdateContentStrUpdate" name="bulletin.bulletinContentStr" required="true" prompt="公告内容" browseUrl="ckeditorBrowse.action" uploadUrl="ckeditorUpload.action" width="95%" height="300" skin="kama"></dt:ckeditor>
							</td>
						</tr>
						<tr>
							<th width="10%" align="left">发布时间：</th>
							<td width="90%">
								<dt:dateInput id="publishTimeUpdate" name="bulletin.publishTime"  required="true" prompt="发布时间" showOnbutton="true" minDate="0d" showTime="true" onClose="startTimeClose()"></dt:dateInput>
							</td>
						</tr>
						<tr>
							<th width="10%" align="left">撤销时间：</th>
							<td width="90%">
								<dt:dateInput id="revokeTimeUpdate" name="bulletin.revokeTime"   required="true" prompt="撤销时间" showOnbutton="true" minDate="0d" showTime="true"></dt:dateInput>
							</td>
						</tr>
						<tr>
							<th width="10%" align="left">优先级：</th>
							<td width="90%">
								<s:radio id="priorityUpdate" list="#{'X':'一般','Y':'加急','Z':'特急'}" value="'Z'" required="true" name="bulletin.priority"></s:radio>
							</td>
						</tr>
						<tr>
							<th width="10%" align="left">关键字：</th>
							<td width="90%">
								<dt:input id="keywordUpdate" name="bulletin.keyword"  length="255"></dt:input>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>