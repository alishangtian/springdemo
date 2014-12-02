/**
 * 此组件在combobox基础上做的升级
 */
(function($,undefined){

	$.widget("dtui.dttreecombobox", $.dtui.dtabscombobox, {
		options: {
			treeId: null,
			setting:{}
		},
		/**
		 * 构建下拉树内容
		 */
		_buildContent: function(){
			var self = this;
			//添加校验时的标识;  组件整体标识-- 文本框标识
			self.totalContainer.addClass("dtui-treecombobox");
			self._e.inputText.addClass("dtui-treecombobox-text");
			//this._e.inputText.attr("readonly",this.options.readonly);
			if(this.options.treeId){
				this._e.tree = $('#'+this.options.treeId);
				this._e.tree.width('auto');
			}
			else {
				//必须指定id属性，否则页面中多个树型下拉框时会有问题
				this._e.tree = $('<ul></ul>').attr('id', this.element.attr('id')+'_tree').dttree(this.options.setting);
			}
			this._e.tree.appendTo(this._e.selectOption);
		},
		
		_bindEvent: function() {
			var self = this;
			this._super();
			this._e.tree.dttree('option','callback.onClick', function(event, treeId, treeNode, clickFlag){
				var text = treeNode[self.options.textField];
				var value = treeNode[self.options.valueField];
				//console.log('value='+value+',text='+text);
				self._e.inputText.val(text);
				//self._e.valueText.data("data", treeNode);
				self._e.valueText.val(value);
				self._hideOption();
				self._trigger('change');
			});
			this._e.tree.dttree('option','callback.onAsyncSuccess', function(event, treeId, treeNode, msg){
				self._refreshTreeNodeState(treeNode);
			});
		},
		
		/**
		 * 根据当前文本框的值，选中树中对应的节点
		 * @param treeNode
		 */
		_refreshTreeNodeState: function(treeNode){
			if (this.value()){
				var nodes = this._e.tree.dttree('getNodesByParam', this.options.valueField, this.value(), treeNode);
				if (nodes.length>0)
					this._e.tree.dttree('selectNode',nodes[0]);
			}
		},
		
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
       _refreshContentState: function() {
    	   this._refreshTreeNodeState(null);
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
			var nodes = this._e.tree.dttree('getNodesByParam', this.options.valueField, value);
			if (nodes.length>0){
				return nodes[0][this.options.textField];
			}
			return value;
        }	

	});
})(jQuery);