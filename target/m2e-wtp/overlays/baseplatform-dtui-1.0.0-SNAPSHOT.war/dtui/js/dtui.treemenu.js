/**
 * 下拉框(原来的 没有改动) 与html明显区别 可以接收 json 数据(key,value)
 */
(function($, undefined) {
	$.widget("dtui.dttreemenu", {
		options : {
			width : null,
			height : null,
			menuItems : null,
			url : '',
			nameField: 'name',
			simpleData: true,
			idKey: 'id',
			pIdKey: 'pid',
			childKey: 'children',
			showIcon: true,
			defaultExpand: false,
			click: null,
			INDENT: 20			//每级缩进量
		},
		_create : function() {
			if (!this.element.hasClass('treemenu')){
				this.element.addClass('treemenu');
			}
			if (this.options.menuItems){
				this._createMenuElems();
			}
			else if (this.options.url){
				this._getMenuItemsByUrl();	
				this._createMenuElems();
			}

			this._setupEvent();
			this._applyOption();
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
		 */
		_getMenuItemsByUrl : function() {
			var that = this;
			$.ajax({
				url : this.options.url,
				type : 'post',
				async : false,
				success : function(data) {
					that.options.menuItems = data;
				}
			});
		},
		_createMenuElem: function(menuItem, level, parMenuElem){
			var $li = $('<li></li>');
			$li.data('menudata', menuItem);
			var $a = $('<a></a>');
			if (level == 0){
				$li.addClass('toplevel ui-widget ui-helper-reset');
				$a.addClass('toplevel-header ui-state-default ui-corner-top');
			}
			var $ecIcon = $('<span class="expand-icon"></span>');//展开收缩图标span
			$a.append($('<span></span>').css({'width':level*this.options.INDENT}))	//缩进span
				.append($ecIcon);			//展开收缩图标span
			if (this.options.showIcon){
				$a.append($('<span class="ui-icon ui-icon-bullet"></span>'));					//节点图标span
			}
			$a.append($('<span></span>').text(menuItem[this.options.nameField]));	//文本span
			
			$li.append($a).appendTo(parMenuElem);
			
			var children = menuItem[this.options.childKey]; 
			if (children && children.length >0 ){
				var $ul = $('<ul></ul>');
				if (this.options.defaultExpand){
					$ecIcon.addClass('ui-icon ui-icon-triangle-1-s');
				}
				else {
					$ecIcon.addClass('ui-icon ui-icon-triangle-1-e');
					$ul.css('display','none');
				}
				
				if (level == 0){
					$ul.addClass('toplevel-content ui-helper-reset ui-widget-content ui-corner-bottom toplevel-content-active');
				}
				else {
					$ul.addClass('toplevel-content-active');
				}
				for (var i=0; i<children.length; i++){
					this._createMenuElem(children[i], level+1, $ul);
				}
				$ul.appendTo($li);
			}
		},
		_setupEvent: function(){
			var that = this;
			this._hoverable(this.element.find('li>a'));
			
			this.element.find('li>a').click(function() {
				that._menuClick(this);
			});
			/*
			this.element.delegate('li>a','click', function() {
				that._menuClick(this);
			});
			*/
		},
		_menuClick: function(itemlink){
			var $item = $(itemlink).parent();
			if (this.hasChild($item)) {
				var expanded = $(itemlink).find('span.expand-icon').hasClass('ui-icon-triangle-1-s');
				this.expandMenu($item, !expanded);
			}
			this._trigger('click', null, {item: $item[0], itemlink:itemlink});
		},
		hasChild: function(menuItem) {
			return menuItem.find('>ul').length>0;
		},
		expandMenu: function(menuItem, flag){
			if (flag) {
				menuItem.find('>ul').slideDown('fast');
				menuItem.find('>a>span.expand-icon')
					.removeClass('ui-icon-triangle-1-e')
					.addClass('ui-icon-triangle-1-s');
			}
			else {
				menuItem.find('>ul').slideUp('fast');
				menuItem.find('>a>span.expand-icon')
					.removeClass('ui-icon-triangle-1-s')
					.addClass('ui-icon-triangle-1-e');
			}
		},
		/**
		 * 创建全部菜单项
		 */
		_createMenuElems : function() {
			var o = this.options;
			this.element.html('');
			if (!o.menuItems || o.menuItems.length == 0)
				return;
			for (var i=0; i<o.menuItems.length; i++){
				this._createMenuElem(o.menuItems[i], 0, this.element);
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
			this._getMenuItemsByUrl();
			this._createMenuElems();
			this._setupEvent();
		},
		/**
		 * 依据数据重新创建下拉列表项
		 * 
		 * @param menuItems
		 */
		refreshMenuItems : function(menuItems) {
			this.element.html("");
			this.options.menuItems = menuItems;
			this._createMenuElems();
			this._setupEvent();
		}

	});
})(jQuery);