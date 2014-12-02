/**
 * 
 */

(function( $, undefined ) {
//alert('a');
	
//	$.fn.zTree.__destroy = $.fn.zTree.destroy;
//	$.fn.zTree.destroy = undefined;
//	delete $.fn.zTree.destroy;
	
	var treePrototype = {};
	
	$.extend(treePrototype, {
		options:  {
			/**原生ztree的属性
			treeId : "",
			treeObj : null,
			async : {
				autoParam : [],
				contentType : "application...",
				dataFilter : null,
				dataType : "text",
				enable : false,
				otherParam : [],
				type : "post",
				url : ""
			},
			callback : {
				beforeAsync : null,
				beforeCheck : null,
				beforeClick : null,
				beforeCollapse : null,
				beforeDblClick : null,
				beforeDrag : null,
				beforeDragOpen : null,
				beforeDrop : null,
				beforeEditName : null,
				beforeExpand : null,
				beforeMouseDown : null,
				beforeMouseUp : null,
				beforeRemove : null,
				beforeRename : null,
				beforeRightClick : null,
				onAsyncError : null,
				onAsyncSuccess : null,
				onCheck : null,
				onClick : null,
				onCollapse : null,
				onDblClick : null,
				onDrag : null,
				onDrop : null,
				onExpand : null,
				onMouseDown : null,
				onMouseUp : null,
				onNodeCreated : null,
				onRemove : null,
				onRename : null,
				onRightClick : null
			},
			check : {
				autoCheckTrigger : false,
				chkboxType : {"Y": "ps", "N": "ps"},
				chkStyle : "checkbox",
				enable : false,
				nocheckInherit : false,
				chkDisabledInherit : false,
				radioType : "level"
			},
			data : {
				keep : {
					leaf : false,
					parent : false
				},
				key : {
					checked : "checked",
					children : "children",
					name : "name",
					title : "",
					url : "url"
				},
				simpleData : {
					enable : false,
					idKey : "id",
					pIdKey : "pId",
					rootPId : null
				}
			},
			edit : {
				drag : {
					autoExpandTrigger : true,
					isCopy : true,
					isMove : true,
					prev : true,
					next : true,
					inner : true,
					borderMax : 10,
					borderMin : -5,
					minMoveSize : 5,
					maxShowNodeNum : 5,
					autoOpenTime : 500
				},
				editNameSelectAll : false,
				enable : false,
				removeTitle : "remove",
				renameTitle : "rename",
				showRemoveBtn : true,
				showRenameBtn : true
			},
			view : {
				addDiyDom : null,
				addHoverDom : null,
				autoCancelSelected : true,
				dblClickExpand : true,
				expandSpeed : "fast",
				fontCss : {},
				nameIsHTML : false,
				removeHoverDom : null,
				selectedMulti : true,
				showIcon : true,
				showLine : true,
				showTitle : true
			}
			*/
		},
		
		proxyObj: undefined,
	
		_create: function() {
			this.element.addClass('ztree ztree-ext ui-widget-content');
			if (this.options.width){
				this.element.css('width', this.options.width);
			}
			if (this.options.height){
				this.element.css('height', this.options.height);
			}
			this.proxyObj = $.fn.zTree.init(this.element, this.options, this.options.nodes);
			this.options = $.extend(this.options, this.proxyObj.setting);
		},
		
		option: function( key, value ) {
			this._super( key, value );
			
			if (arguments.length === 2 && typeof key === "string"){
				
				var parts = key.split( "." );
				key = parts.shift();
				if ( parts.length ) {
					var curOption = this.proxyObj.setting[key];
					for ( var i = 0; i < parts.length - 1; i++ ) {
						curOption[ parts[ i ] ] = curOption[ parts[ i ] ] || {};
						curOption = curOption[ parts[ i ] ];
					}
					key = parts.pop();
					if ( value === undefined ) {
						return curOption[ key ] === undefined ? null : curOption[ key ];
					}
					curOption[ key ] = value;
				} else {
					if ( value === undefined ) {
						return this.proxyObj.setting[key] === undefined ? null : this.proxyObj.setting[key];
					}
					this.proxyObj.setting[key] = value;
				}				
				this.proxyObj.setting[key] = value;
			}
		},
		
		
		getTree: function() {
			var treeId = this.element.attr('id');
			return $.fn.zTree.getZTreeObj(treeId);
		},
		
		addNodes: function(parNode, newNode, isSilent){
			return this.proxyObj.addNodes(parNode, newNode, isSilent);
		},
		
		removeNode: function(treeNode, callbackFlag){
			return this.proxyObj.removeNode(treeNode, callbackFlag);
		},
		
		removeChildNodes: function(parNode){
			return this.proxyObj.removeChildNodes(parNode);
		},
		
		expandAll : function(expandFlag){
			return this.proxyObj.expandAll(expandFlag);
		},
		
		expandNode : function(node, expandFlag, sonSign, focus, callbackFlag) {
			return this.proxyObj.expandNode(node, expandFlag, sonSign, focus, callbackFlag);
		},
		
		getNodes : function() {
			return this.proxyObj.getNodes();
		},
		
		getNodeByParam : function(key, value, parentNode) {
			return this.proxyObj.getNodeByParam(key, value, parentNode);
		},
		getNodeByTId : function(tId) {
			return this.proxyObj.getNodeByTId(tId);
		},
		getNodesByParam : function(key, value, parentNode) {
			return this.proxyObj.getNodesByParam(key, value, parentNode);
		},
		getNodesByParamFuzzy : function(key, value, parentNode) {
			return this.proxyObj.getNodesByParamFuzzy(key, value, parentNode);
		},
		getNodesByFilter: function(filter, isSingle, parentNode, invokeParam) {
			return this.proxyObj.getNodesByFilter(filter, isSingle, parentNode, invokeParam);
		},
		
		getNodeIndex : function(node) {
			return this.proxyObj.getNodeIndex(node);
		},
		//返回第一个选中的节点
		getSelectedNode: function() {
			var selNodes = this.getSelectedNodes();
			if (selNodes && selNodes.length>0)
				return selNodes[0];
			return null;
		},
		//返回所有选中的节点
		getSelectedNodes: function() {
			return this.proxyObj.getSelectedNodes();
		},
		
		isSelectedNode : function(node) {
			return this.proxyObj.isSelectedNode(node);
		},
		reAsyncChildNodes : function(parentNode, reloadType, isSilent) {
			this.proxyObj.reAsyncChildNodes(parentNode, reloadType, isSilent);
		},
		refresh : function() {
			this.proxyObj.refresh();
		},
		selectNode : function(node, addFlag) {
			this.proxyObj.selectNode(node, addFlag);
		},
		cancelSelectNode: function(node){
			this.proxyObj.cancelSelectNode(node);
		},
		transformTozTreeNodes : function(simpleNodes) {
			return this.proxyObj.transformTozTreeNodes(simpleNodes);
		},
		transformToArray : function(nodes) {
			return this.proxyObj.transformToArray(nodes);
		},
		updateNode : function(node, checkTypeFlag) {
			this.proxyObj.updateNode(node, checkTypeFlag);
		},
		_destroy: function(){
			var treeId = this.element.attr('id');
//			this.__destroy(treeId);
			$.fn.zTree.destroy(treeId);
		},
		
		//节点编辑的相关操作,来自于ztree.exedit.js
		cancelEditName : function(newName) {
			this.proxyObj.cancelEditName(newName);
		},
		copyNode : function(targetNode, node, moveType, isSilent) {
			return this.proxyObj.copyNode(targetNode, node, moveType, isSilent);
		},
		
		editName: function(node) {
			return this.proxyObj.editName(node);
		},
		
		moveNode : function(targetNode, node, moveType, isSilent) {
			return this.proxyObj.moveNode(targetNode, node, moveType, isSilent);
		},
		setEditable : function(editable) {
			return this.proxyObj.setEditable(editable);
		},
		
		//多选相关方法，来自于ztree.excheck.js
		checkNode : function(node, checked, checkTypeFlag, callbackFlag) {
			this.proxyObj.checkNode(node, checked, checkTypeFlag, callbackFlag);
		},

		checkAllNodes : function(checked) {
			this.proxyObj.checkAllNodes(checked);
		},

		getCheckedNodes : function(checked) {
			return this.proxyObj.getCheckedNodes(checked);
		},

		getChangeCheckedNodes : function() {
			return this.proxyObj.getChangeCheckedNodes();
		},

		setChkDisabled : function(node, disabled, inheritParent, inheritChildren) {
			this.proxyObj.setChkDisabled(node, disabled, inheritParent, inheritChildren);
		},

		updateNode : function(node, checkTypeFlag) {
			this.proxyObj.updateNode(node, checkTypeFlag);
		}		
	});
		
	$.widget( "dtui.dttree", treePrototype);

})( jQuery );