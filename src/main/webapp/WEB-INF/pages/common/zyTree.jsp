<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<script type="text/javascript">
	function expandAsyn(){
		var nodes = dttreen.dttree('getNodes');
		if(nodes != null && nodes.length > 0){
			dttreen.expandNode(nodes[0], true,false ,true);
		}
	}
</script>
<dt:tree id="dttreen" width="180px" height="400px"
	onAsyncSuccess="expandAsyn"
	url="busi/produceWorksInfoManager!findBaseHouseTree.action"
	urlParam="id,status" nameCol="name"
	isParentDefaultValue="true" onClick="clickOper">
</dt:tree>
<script type="text/javascript">
	(function(w){
		var treeHeight = document.getElementsByTagName('body')[0].clientHeight;
		if(parent!=w)
			treeHeight = parent.document.getElementById('worktab').clientHeight;
		document.getElementById('dttreen').style.height=(treeHeight-55)+'px';
	})(window);
</script>