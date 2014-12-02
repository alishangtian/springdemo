/*
 * 
 * 横向菜单
 */

(function( $, undefined ) {

var 
	main_menu_class = 'main_menu',
	sub_menu_class = 'sub_menu';
	
$.widget('dtui.dthmenu', {
	version:'1.0.1',
	options: {
		url: false,
		activeMenu: 0,
		hoverSwitch: false,
		childrenField: 'children',
		parentField: 'parent',
		textField: 'title',
		idField: 'id',
		main_menu: '.' + main_menu_class,
		sub_menu: '.' + sub_menu_class,
		select: null
	},
	_create: function(){
		var options = this.options,
			url = options.url;
		
		if(url){
			this._load(url);
		}else{
			this._initNav();
		}
	},
	_initBarItem: function(elements){
		var items = elements.children('a');
		
		elements.addClass('ui-menubar-item')
			.attr( "role", "presentation" );
		this._focusable( items );
		this._hoverable( items );
		
		items.addClass('ui-button ui-widget ui-button-text-only ui-menubar-link')
			.attr('role', 'menuitem')
			.wrapInner('<span class="ui-button-text"></span>');
	},
	_initSubItem: function(items){
		var options = this.options;
		
		items.menu({
			direction: 'horizontal',
			select: function(event){
				if(options.select){
					options.select(event);
				}
			}
		})
			.hide()
			.addClass('ui-helper-clearfix');
	},
	_initNav: function(){
		var self = this,
			element = self.element,
			options = self.options,
			enable_index = options.activeMenu,
			main_element = $(options.main_menu, element),
			sub_element = $(options.sub_menu, element),
			main_enable = $('li:eq('+enable_index+')', main_element),
			sub_enable = $('ul:eq('+enable_index+')', sub_element);
			
		element.addClass('dtui-nav');
		main_element.addClass('ui-menubar ui-widget-header ui-helper-clearfix');
		
		self._initBarItem($('li', main_element));
		self._initSubItem($('ul', sub_element));
		
		self._open($('a', main_enable), sub_enable);
		
		main_element.on('mouseenter', 'li', function(event){
			var target = $(this);
			if(target.hasClass('ui-state-disabled')){
				return;
			}
			var	index = target.index(),
				on_sub = $('ul:eq('+index+')', sub_element);
			self._open($('a', target), on_sub);
		});
	},
	_open: function(target, menu){
		// 如果是已经打开的菜单，什么也不做
		if ( this.active && this.active[0] == menu[0] ) {
			return;
		}
		// 隐藏以显示的菜单
		if ( this.active ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
				
			this.activeBar
				.removeClass( "ui-state-active" );
		}
		
		this.active = menu
			.show()
			.removeAttr( "aria-hidden" )
			.attr( "aria-expanded", "true" )
			.menu("focus", event, menu.children( "li" ).first() )
			.focus()
			.focusin();
			
		this.activeBar = target
			.addClass('ui-state-active')
			.attr( "tabIndex", -1 );
		
	},
	_createElement: function(data){
		var o = this.options,
			main_element = $('<ul class="'+ main_menu_class +'">'),
			sub_element = $('<div class="'+ sub_menu_class +'">'),
			item, i, j;
		
		for(i=0; i<data.length; i++){
			item = data[i];
			main_element.append(this._createItem(item));
			sub_element.append(this._createItems(item[o.childrenField]));
		}
		
		this.element.append(main_element);
		this.element.append(sub_element);
	},
	_createItems: function(items){
		var o = this.options,
			subitem, buf, i;
			
		//创建子节点DOM元素
		buf = [];
		buf.push('<ul>');
		for(i=0; i<items.length; i++){
			subitem = items[i];
			buf.push(this._createItem(subitem));
		}
		buf.push('</ul>');
		return buf.join('');
	},
	_createItem: function(item){
		var buf = [],
			o = this.options,
			url = item.url ? item.url : '#';
		
		buf.push('<li>');
		buf.push('<a href="' + url + '">');
		buf.push(item[o.textField]);
		buf.push('</a></li>');
		return buf.join('');
	},
	_load: function(url){
		var self = this,
			o = this.options;
		
		$.getJSON(url, function(data) {
			var result = [],
				temp = [],
				item, i, j;
				
			//取得所有的根节点	
			for(i=0; i<data.length; i++){
				item = data[i];
				if(item[o.parentField] == undefined){
					if(!item[o.childrenField]){
						item[o.childrenField] = [];
					}
					result.push(item);
				}else{
					temp.push(item);
				}
			}
			
			//把所有的子节点添加到根节点上面
			for(i=0; i<temp.length; i++){
				item = temp[i];
				for(j=0; j<result.length; j++){
					if(item[o.parentField] == result[j][o.idField]){
						result[j][o.childrenField].push(item);
					}
				}
			}
			
			self._createElement(result);
			self._initNav();
		});
	},
	_query: function(ele){
		return ele.jquery ? ele : $(ele);
	},
	enable: function(item){
		this._query(item).removeClass('ui-state-disabled');
	},
	disable: function(item){
		this._query(item).addClass('ui-state-disabled');
	},
	addMenu: function(data){
		var self = this,
			element = self.element,
			o = self.options,
			main_element = $(o.main_menu, element),
			sub_element = $(o.sub_menu, element);
		
		var item = this._createItem(data);
		$item = $(item);
		self._initBarItem($item);
		$item.appendTo(main_element);
		
		var subItems = this._createItems(data[o.childrenField]);
		$subItems = $(subItems);
		$subItems.appendTo(sub_element);
		this._initSubItem($subItems);
	},
	removeMenu: function(menu){
		var $menu = this._query(menu);
		
		if($menu.hasClass('ui-menubar-item')){
			this._removeMenuBar($menu);
		}else if($menu.hasClass('ui-menu-item')){
			$menu.remove();
		}
	},
	_removeMenuBar: function($menu){
		var element = this.element,
			o = this.options;
		
			index = $menu.index(),
			$subItem = element.find(o.sub_menu+' ul:eq('+ index +')');
		
		if ( this.active && this.active[0] == $subItem[0] ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
			
			this.active = null;
		}
			
		$menu.remove();
		$subItem.remove();
	},
	getMenuInfo: function(ele){
		var element = this.element,
			o = this.options,
			$ele = this._query(ele),
			$aele = $ele.find('a:eq(0)'),
			info = {
				text: $aele.text(),
				url: $aele.attr('href')
			};
		if($ele.hasClass('ui-menubar-item')){
			var index = $ele.index(),
				sub_element = $(o.sub_menu, element);
			info.child = $('ul:eq('+index+')', sub_element);;
			
		}
		return info;
	},
	option: function(name){
		if(this.options[name]){
			return this.options[name];
		}else{
			return null;
		}
	}
});

})( jQuery );