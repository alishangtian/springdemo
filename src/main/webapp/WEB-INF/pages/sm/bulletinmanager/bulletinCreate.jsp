<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>新增公告信息</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=basePath%>css/new.style.css"
	type="text/css" />
<script type="text/javascript">
	$(function() {
		$("#subAddBulletin").click(function() {
			if (!$.dtvalidate("#bulletinCreateForm", "caption")) {
				return;
			}
			if ($('#revokeTime').val() <= $('#publishTime').val()) {
				$.dtmessagebox.error("撤销时间不得晚于发布时间");
				return false;
			}
			var bulletinInfo = $("#bulletinCreateForm").serializeArray();
			$.ajax({
				url : "sm/BulletinAction!create.action",
				data : bulletinInfo,
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data) {
						if (data.flag) {
							$.dtmessagebox.alert("新增成功");
							window.opener.bulletingrid.dtpaginggrid("reload");
							window.close();
						} else {
							$.dtmessagebox.alert(data.msg);
						}
					} else {
						$.dtmessagebox.alert("提交失败");
					}
				}
			});
		});
		$("#cancleAddBulletin").click(function() {
			window.close();
		});
	});
</script>
</head>
<body>
	<form id="bulletinCreateForm" method="post">
		<table class="query-form">
			<tr>
				<th>公告标题：</th>
				<td><dt:input id="bulletinTitle" name="bulletinTitle"
						width="176px" height="30px"></dt:input></td>
			</tr>
			<tr>
				<th>公告内容：</th>
				<td><dt:ckeditor id="bulletinContentStr"
						name="bulletinContentStr" required="true" prompt="公告内容"
						browseUrl="ckeditorBrowse.action"
						uploadUrl="ckeditorUpload.action" width="95%" height="100"
						skin="moono"></dt:ckeditor></td>
			</tr>
			<tr>
				<th>发布时间：</th>
				<td><dt:dateInput id="publishTime" name="publishTime"
						showTime="true" showOnbutton="true" width="192px" height="30px" />
				</td>
			</tr>
			<tr>
				<th width="10%" align="left">撤销时间：</th>
				<td width="90%"><dt:dateInput id="revokeTime" name="revokeTime"
						showTime="true" showOnbutton="true" width="192px" height="30px" />
				</td>
			</tr>
			<tr>
				<th>优先级：</th>
				<td><select id="priority" name="priority"
					style="width: 168px; height: 30px; border: 1px solid #99CBD3">
						<option value="X">一般</option>
						<option value="Y">加急</option>
						<option value="Z">特急</option>
				</select></td>
			</tr>
			<tr>
				<th>关键字：</th>
				<td><dt:input id="keyword" name="keyword" width="176px"
						height="30px"></dt:input></td>
			</tr>
		</table>
	</form>
	<div class="query-form-btns">
		<dt:button id="subAddBulletin" label="提交"></dt:button>
		<dt:button id="cancleAddBulletin" label="取消"></dt:button>
	</div>
</body>
</html>