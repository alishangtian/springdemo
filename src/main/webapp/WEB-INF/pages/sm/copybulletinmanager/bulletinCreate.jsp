<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>新增公告信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<script type="text/javascript">
/* $(function() {
	startTimeClose();
}); */
function startTimeClose() {
	$("#revokeTime").dtdatepicker('option','minDate', $("#publishTime").dtdatepicker('getDate'));
}
</script>
</head>
<body>
	<form id="bulletinCreateForm" method="post">
		<dt:tabs height="400" id="bulletinCreateTab" switchMode ="mouseover" active="0">
			<dt:tabsheet label="基本信息" id="bulletinCreateBaseInfoTab">
					<table width="100%" border="0" cellpadding="2" cellspacing="0">
						<tr>
							<td><table width="100%" align="center" class="condition">
									<tr>
										<th width="10%" align="left">公告标题：</th>
										<td>
											<dt:input id="bulletinTitle" name="bulletin.bulletinTitle" required="true" prompt="公告标题" length="128"></dt:input>
										</td> 
									</tr>
									<tr>
										<th width="10%" align="left">公告内容：</th>
										<td width="90%">
											 <dt:ckeditor id="bulletinContentStr" name="bulletin.bulletinContentStr" required="true" prompt="公告内容" browseUrl="ckeditorBrowse.action" uploadUrl="ckeditorUpload.action" width="95%" height="200" skin="moono"></dt:ckeditor>
										</td>
									</tr>
									<tr>
										<th width="10%" align="left">发布时间：</th>
										<td width="90%">
											<dt:dateInput id="publishTime" name="bulletin.publishTime"  required="true" prompt="发布时间" minDate="0d" showTime="true" onClose="startTimeClose()"></dt:dateInput>
										</td>
									</tr>
									<tr>
										<th width="10%" align="left">撤销时间：</th>
										<td width="90%">
											<dt:dateInput id="revokeTime" name="bulletin.revokeTime"  required="true" prompt="撤销时间" minDate="0d" showTime="true"></dt:dateInput>
										</td>
									</tr>
									<tr>
										<th width="10%" align="left">优先级：</th>
										<td width="90%">
											<s:radio id="priority" list="#{'X':'一般','Y':'加急','Z':'特急'}" value="'X'" required="true" name="bulletin.priority"></s:radio>
										</td>
									</tr>
									<tr>
										<th width="10%" align="left">关键字：</th>
										<td> 
											<dt:input id="keyword" name="bulletin.keyword"  length="255"></dt:input>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
			</dt:tabsheet>
			<dt:tabsheet label="公告发布" id="bulletinCreatePublishTab" url="sm/BulletinAction!preparePublish.action">&nbsp;</dt:tabsheet>
		</dt:tabs>
	</form>
</body>
</html>