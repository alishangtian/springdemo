/**
 * 部门树
 * 
 * @param contaner
 * @returns
 */
function initDeptTree(contaner, callBackFunc) {
	var setting = {
			data:{
				key:{
					name : "deptName"
				},
				simpleData:{
					enable: true,
					idkey: "deptId",
					pIdKey: "superDeptId",
					rootPId: null
				}
			},
			height:400,
//		nameCol : "deptName",
//		treeNodeKey: "deptId",
//		treeNodeParentKey: "superDeptId",
//		isSimpleData : true,
//		root : {},
		callback : {
			onClick: function(){
				if($.isFunction(callBackFunc)){
					callBackFunc.apply(this, arguments);
				}
			},
			beforeExpand : beforeExpand
		}
	};
	var param = {}, deptTree, url = "sm/sysDeptManagerAction!getSysDeptData.action";
	$.ajax({
		url : url,
		type : 'post',
		async : false,
		success : function(data) {
			$.each(data, function(i, prop){
				prop.isParent = true;
			});
			//deptTree = dt$.tree(contaner, setting, data);
			setting.nodes = data;
			deptTree = $(contaner).dttree(setting);
		},
		error : function(e) {
			alert(e);
		},
		data : param
	});
	function beforeExpand(treeId, treeNode) {
		if (treeNode.isParent
				&& (!treeNode.children || treeNode.children.length == 0)) {
			var param = {
				"isRoot" : false,
				"deptId" : treeNode.deptId
			};
			$.ajax({
				url : url,
				type : 'post',
				async : false,
				success : function(data) {
					var newNodes = data;
					if (newNodes && newNodes.length > 0) {
						$.each(newNodes, function(i, prop){
							prop.isParent = true;
						})
						//deptTree.addNodes(treeNode, newNodes, true);
						//deptTree.updateNode(treeNode);
						//deptTree.selectNode(treeNode.nodes[0]);
						deptTree.dttree('addNodes', treeNode, newNodes, true);
						deptTree.dttree('updateNode', treeNode);
						deptTree.dttree('selectNode', treeNode.children[0]);
					} else {
						treeNode.isParent = false;
						//deptTree.updateNode(treeNode);
						//deptTree.selectNode(treeNode);
						deptTree.dttree('updateNode', treeNode);
						deptTree.dttree('selectNode', treeNode);
					}
				},
				data : param
			});
			return false;
		}
		return true;
	}
	return deptTree;
}
/**
 * 以弹出页的形式展示部门树
 * 
 * @param callBackFunc
 */
function showDeptTree(callBackFunc) {
	var deptTree = null;
	$.dtdialog.showModal({
		title : '部门信息',
		minWidth : 300,
		minHeight : 400,
		autoOpen: true,
		buttons : {
	        	'确定':function(){
	        		if($.isFunction(callBackFunc)){
	        			callBackFunc(deptTree.dttree('getSelectedNode'));
	        		}
	        		$(this).dtdialog('close');
	        	},
	        	'取消': function() {
	        		$(this).dtdialog('close');
	        	}
		},
		open: function(){
			deptTree = initDeptTree(arguments[0].target);
		}
	});
}
/**
 * 处理后台返回的数据
 * @param data
 * @param success
 */
function doResult(data, message, success){
	if(data.result == true){
		$.dtmessagebox.alert(message, "提示", function(){
			if($.isFunction(success)){
				success();
			}
		});
	}else if(data.result == false){
		$.dtmessagebox.alert(data.msg);
	}else{
		$.dtmessagebox.alert(data);
	}
}