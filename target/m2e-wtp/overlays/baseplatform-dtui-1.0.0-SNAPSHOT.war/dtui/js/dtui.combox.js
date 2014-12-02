/**
 * 下拉框(原来的 没有改动) 与html明显区别 可以接收 json 数据(key,value)
 */
(function($, undefined) {
	$.widget("dtui.dtcombox", {
		options : {
			width : null,
			height : null,
			name: null,
			datasource : null,
			url : '',
			keyField : '',
			valueField : '',
			selectIndex : '',
			selectValue : '',
			hasAll : false,
			allKey : '',
			allValue : '--全部--',
			hasNull : false,
			nullKey : '',
			nullValue : '--请选择--',
			onChange : ''
		},
		_create : function() {
			if (this.options.datasource){
				this._createOptionByDatasource();
			}
			else if (this.options.url){
				this._getDatasourceByUrl();	
				this._createOptionByDatasource();
			}

			if (this.options.onChange != null && this.options.onChange != "") {
				this.element.bind("change", this.options.onChange);
			}
			
			if (this.options.name){
				this.element.attr('name', this.options.name);
			}
			else {
				this.options.name = this.element.attr('name');
			}
			this._applyOption();
		},
		_init: function(){
			if (this.options.selectIndex != null
					&& this.options.selectIndex != "") {
				this.selectIndex(this.options.selectIndex);
			}
			if (this.options.selectValue != null
					&& this.options.selectValue != "") {
				this.selectValue(this.options.selectValue);
			}
		},
		_applyOption: function() {
			var o = this.options;
			this._setupDisabled(o.disabled);
			this._setupWidth(o.width);
			this._setupHeight(o.height);
		},

		_setupDisabled: function(disabled){
			if ( disabled ) {
				this.element.prop( "disabled", true );
			} else {
				this.element.prop( "disabled", false );
			}
		},

		_setupWidth: function(width){
			this.element.width(width);
		},
		_setupHeight: function(height){
			this.element.height(height);
		},
		_setOption : function(key, value) {
			this._super(key, value);
			if (key === "disabled") {
				this._setupDisabled(value);
				return;
			}
			if (key === "width") {
				this._setupWidth(value);
				return;
			}
			if (key === "height") {
				this._setupHeight(value);
				return;
			}
		},
		/**
		 * 设定构建下拉列表的数据
		 * 
		 * @param obj
		 */
		_getDatasource : function(obj) {
			$.ajax({
				url : this.options.url,
				type : 'post',
				async : false,
				success : function(datasource) {
					obj.options.datasource = datasource;
				}
			});
		},
		/**
		 * 依据请求地址获取构建下拉列表的数据
		 */
		_getDatasourceByUrl : function() {
			var obj = this;
			if (this.options.datasource == null) {
				if (this.options.url != null && this.options.url != "") {
					this._getDatasource(obj);
				}
			}
		},
		/**
		 * 创建下拉列表项
		 */
		_createOptionByDatasource : function() {
			if (this.options.datasource != null) {
				if (this.options.hasNull == true) {
					this.element.append("<option value='"
							+ this.options.nullKey + "'>"
							+ this.options.nullValue + "</option>");
				}
				if (this.options.hasAll == true) {
					this.element.append("<option value='" + this.options.allKey
							+ "'>" + this.options.allValue + "</option>");
				}
				var datasource = eval(this.options.datasource);
				if (this.options.keyField != null
						&& this.options.keyField != ''
						&& this.options.valueField != null
						&& this.options.valueField != '') {
					for ( var i = 0; i < datasource.length; i++) {
						this.element.append("<option value="
								+ datasource[i][this.options.keyField] + ">"
								+ datasource[i][this.options.valueField]
								+ "</option>");
					}
				} else {
					for ( var key in datasource) {
						this.element.append("<option value=" + key + ">"
								+ datasource[key] + "</option>");
					}
				}
			}
		},
		/**
		 * 依据请求地址重新创建下拉列表项
		 * 
		 * @param url
		 */
		refreshUrl : function(url) {
			this.element.html("");
			this.options.url = url;
			this._getDatasourceByUrl();
			this._createOptionByDatasource();
		},
		/**
		 * 依据数据重新创建下拉列表项
		 * 
		 * @param datasource
		 */
		refreshDatasource : function(datasource) {
			this.element.html("");
			this.options.datasource = datasource;
			this._createOptionByDatasource();
		},
		/**
		 * 依据下拉列表项的位置设定值或获取选中项的位置
		 * 
		 * @param index
		 * @returns
		 */
		selectIndex : function(index) {
			if (arguments.length) {
				if (index != null && index != "" && !isNaN(index)) {
					this.element.get(0).selectedIndex = index;
				}
			} else {
				return this.element.get(0).selectedIndex;
			}
		},
		/**
		 * 依据下拉列表项的键值设定显示框的值或获取选中项的值
		 * 
		 * @param value
		 * @returns
		 */
		selectValue : function(value) {
			if (arguments.length) {
				if (value != null && value != "") {
					this.element.get(0).value = value;
				}
			} else {
				return this.element.val();
			}
		},
		/**
		 * 获取选中的文本
		 * 
		 * @returns
		 */
		selectText : function() {
			return this.element.find("option:selected").text();
		}
	});
})(jQuery);