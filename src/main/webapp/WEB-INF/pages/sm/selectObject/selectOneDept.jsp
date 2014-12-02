<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择单部门</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<dt:comppath include="tree,button"></dt:comppath>
<script type="text/javascript">
   //提供获取数据函数
   function getSelectObjectData(){
	    var retValue = '';
	    var selectNode = selectOneDeptTree.dttree("getSelectedNode");
	    if(selectNode!=null){
	    	retValue = '{id:'+selectNode.deptId+',name:'+selectNode.deptName+'}';
	    }
		return retValue;
    }
</script>
</head>
<body>
	<dt:tree id="selectOneDeptTree" width="180" height="500px" isSimpleData="true"
	    url="sm/sysDeptManagerAction!getAllSysDeptData.action" treeNodeKey="deptId"
	    treeNodeParentKey="superDeptId" nameCol="deptName">
    </dt:tree>
</body>
</html>