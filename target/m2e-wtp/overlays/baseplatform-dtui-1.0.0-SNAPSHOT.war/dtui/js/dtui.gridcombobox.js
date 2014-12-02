/**
 * 
 */
(function($,undefined){
	$.widget("dtui.dtgridcombobox", $.dtui.dtabscombobox,{
		options: {
			disabled: false,
			dropPanelWidth: "400px",	//覆盖父类的值
			//gridWidth: '400px',
			gridId:"",
			gridOption:{}
		},
		_init: function() {
			this._super();
			
			this._genSelectionObject();
			//this._refreshInputValue();
			this._selectGridRow();
		},
		/**
		 * 根据输入框的值生成_selection对象
		 */
		_genSelectionObject: function() {
			
			var valueArray = this._e.valueText.val().split(",");
			var textArray = this._e.inputText.val().split(",");
			if (valueArray.length != textArray.length){
				if (window.console) console.log('表格下拉框值和显示文本长度不一致！');
				return;
			}
			this._selection = [];
			for (var i=0; i<valueArray.length; i++){
				var rowObj = {};
				rowObj[this.options.valueField] = valueArray[i];
				rowObj[this.options.textField] = textArray[i];
				this._selection.push(rowObj);
			};			
		},
		_buildContent: function(){
			this._super();
			var self = this;
			//添加校验时的标识;  组件整体标识-- 文本框标识
			self.totalContainer.addClass("dtui-gridcombobox");
			self._e.inputText.addClass("dtui-gridcombobox-text");
			this._e.selectOption.height('auto');
			if(this.options.gridId){
				$("#gbox_"+this.options.gridId+"").appendTo(this._e.selectOption);
				this._e.table = $("#"+this.options.gridId+"");
			}
			else{
				this._e.table = $('<table></table>').attr('id', this.element.attr('id')+'_table')
					.appendTo(this._e.selectOption)
					.dtpaginggrid(this.options.gridOption);
			}
			this._e.table.dtpaginggrid('setGridWidth', this._e.selectOption.width() - 2, true);
			this.isMultiSelect = this._e.table.dtpaginggrid('option', 'multiselect');
		 },
		 
		_bindEvent: function() {
			this._super();
			var self = this;
			this._e.table.dtpaginggrid('option','onSelectRow',function(rowid,status,e){
				self.change(rowid,status);
			});
			
			this._e.table.dtpaginggrid('option','onSelectAll',function(rowids,status){
				//console.log(status);
				var rowObj = null;
				var t = self._e.table;
				var valueField = self.options.valueField;

				if (!self._selection) {
					self._selection = [];
				}
				for (var j = 0; j<rowids.length; j++){
					rowObj = t.dtpaginggrid('getRowData', rowids[j]);
					var index = self._findSelection(rowObj[valueField]);
					if (index >= 0){
						if (!status){
							self._selection.splice(index, 1);
						}
					}
					else {
						if (status) {
							self._selection.push(rowObj);
						}
					}										
				}
				self._refreshInputValue();
				self._trigger('change');
			});
			
			//用来处理翻页后的勾选状态
			this._e.table.dtpaginggrid('option','gridComplete',function(data){
				self._selectGridRow();
			});
			
			this._e.inputText.keydown(function(event){
				if (event.keyCode == 8) {
					if (self._selection && self._selection.length>0){
						self._selection = [];
						self._refreshInputValue();
						self._selectGridRow();
						self._trigger('change');
					}
				}
				event.keyCode = 0;
				return false;
			});

		},
		
		/**
		 * 根据_selection对象内容，设置表格的选择行
		 */
		_selectGridRow: function() {
			var self = this;
			var t = self._e.table;
			t.dtpaginggrid('resetSelection');			
			if (!self._selection || self._selection.length == 0) {
				return ;
			}
			//遍历数据，将已经选中的数据行设置为勾选状态
			
			var valueField = self.options.valueField;
			rowData = t.dtpaginggrid('getRowData');
			//console.log(self._selection);

			for (var i=0; i<this._selection.length; i++){
				t.dtpaginggrid('setSelection', this._selection[i][valueField], false);
			}
			
			/*
			$.each(rowData, function(i, rowObj){
				//console.log(rowObj);
				var index = self._findSelection(rowObj[valueField]);
				if (index >= 0){
					t.dtpaginggrid('setSelection', rowObj[valueField], false);
				}
			});
			*/
		},
		
		_findSelection: function(value){
			if (!this._selection || this._selection.length == 0){
				return -1;
			}
			var valueField = this.options.valueField;
			for (var i=0; i<this._selection.length; i++){
				if (value == this._selection[i][valueField]) {
					return i;
				}
			}			
		},
		
		change:function(rowid, status){
			var self=this,
				input_text=this._e.inputText,
				table = this._e.table,
				options = self.options;
			
			//console.log('rowid:'+rowid);
			//console.log(table.dtpaginggrid('getRowData', rowid));
			if(status){
				if(!this._selection || !this.isMultiSelect){
					this._selection = [];
				}
				self._selection.push(table.dtpaginggrid('getRowData', rowid));
			}else{
				if(self._selection){
					var result = [];
					$.each(this._selection, function(index, item){
						//alert(item[options.valueField]);
						if(rowid != item[options.valueField]){
							result.push(item);
						}
					});
					self._selection = result;
				}
			}
			this._refreshInputValue();
			//console.log(this._e.table.dtpaginggrid('option', 'multiselect'));
			if (!this.isMultiSelect) 
				this._hideOption();
			self._trigger('change');
		},
		/**
		 * 根据_selection对象刷新valueText和inputText文本框的值
		 */
		_refreshInputValue: function(){
			if(!this._selection || this._selection.length == 0) {
				this._e.inputText.val("");
				this._e.valueText.val("");
				return;
			}
			var textArr = [],
				valueArr = [],
				options = this.options;
			
			$.each(this._selection, function(index, item){
				textArr.push(item[options.textField]);
				valueArr.push(item[options.valueField]);
			});
			//console.log(this._e.inputText.val("tmd"));
			this._e.inputText.val(textArr.join(","));
			this._e.valueText.val(valueArr.join(","));
		},
		
		_clear: function(){
			if (this._selection && this._selection.length>0){
				this._selection = [];
				this._refreshInputValue();
				this._selectGridRow();
				return true;
			}
			return false;
		},
		
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
        _refreshContentState: function() {
			//使valueText和inputText中的内容与selection对象的内容保持一致
			this._genSelectionObject();
        	this._selectGridRow();
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
        	if (!value){
        		return "";
        	}
        	var valueArr = value.split(this.options.displaySplitChar),
        		textArr = [],
        		t = this._e.table;
        	for(var i = 0; i< valueArr.length; i++){
        		var rowObj = t.dtpaginggrid('getRowData', valueArr[i]);
        		var txt = rowObj ? rowObj[this.options.textField] : '';
        		textArr.push(txt);
        	}
        	return textArr.join(this.options.displaySplitChar);
        }
        
		
	});
})(jQuery);