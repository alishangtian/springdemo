<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择多部门</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<script type="text/javascript" src="<%=request.getContextPath()%>js/json2.js"></script>
<dt:comppath include="tree,button"></dt:comppath>
<script type="text/javascript" src="js/dt.datatag.js"></script>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>js/json2.js"></script>
<style type="text/css">
	.datatag{
		float: left;
		background-color: #A6C9E2;
		margin: 2px;
	}
	.datatag .ui-icon-close{
		padding: 0px;
		margin: 0px;
	}
</style>
<script type="text/javascript">
   initAlarmFormulaDataTag();	
   function initAlarmFormulaDataTag(){
		selectObjectDataTag = $("#selectDeptObjectDiv").datatag( {
			"data":[],
			"namefield": "deptName",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}
		});
	}
   //复选框触发事件
   function selectManyDeptChange(){
	   //清空已选对象
	   $("#selectDeptObjectDiv").empty();
	   //新增已选对象
	   var selectNodes = selectManyDeptTree.dttree("getCheckedNodes");
		for(var i=0; i<selectNodes.length; i++){
			var selectNode = selectNodes[i];
			var obj = new Object();
			obj.deptId = selectNode.deptId;
			obj.deptName = selectNode.deptName;
			selectObjectDataTag.datatag("add",obj);
		}
   }
   //提供获取数据函数
   function getSelectObjectData(){
	    var selectDaraArr = [];
		var selectData = selectObjectDataTag.datatag("getData");
		for(var i=0; i<selectData.length; i++){
			var selectDataObj = new Object();
			selectDataObj.id = selectData[i].deptId;
			selectDataObj.name = selectData[i].deptName;
			selectDaraArr.push(selectDataObj);
		}
		return JSON.stringify(selectDaraArr);
    }
</script>
</head>
<body>
	<dt:tree id="selectManyDeptTree" width="180" height="300px" isSimpleData="true" checkable="true"
	    url="sm/sysDeptManagerAction!getAllSysDeptData.action" treeNodeKey="deptId"
	    treeNodeParentKey="superDeptId" nameCol="deptName" onChange="selectManyDeptChange">
    </dt:tree>
    <table width="100%" align="center" class="condition">
		<tr>
			<th width="10%" align="left">已选对象：</th>
			<td width="90%" valign="middle">
				<div id="selectDeptObjectDiv" style="width: 95%; height: 60px"></div>
			</td>
		</tr>
	</table>
</body>
</html>