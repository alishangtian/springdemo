/*
 * 
 * 横向菜单
 */

(function( $, undefined ) {

var 
	main_menu_class = 'dt-navmain',
	sub_menu_class = 'dt-navsub';
	
$.widget('dtui.dtnavbar', {
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
		select: null,
		showSubMenu: false,
		menuItems: null
	},
	_create: function(){
		var options = this.options,
			url = options.url,
			menuItems = options.menuItems;
		
		if(url){
			this._load(url);
		}else if(menuItems){
			this._createElement(menuItems);
			this._initNav();
		}else{
			this._initNav();
		}
	},
	_initBarItem: function(elements){
		var items = elements.children('a'),
			self = this;
		
		elements.addClass('ui-menubar-item')
			.attr( "role", "presentation" );
		
		items.addClass('ui-button ui-button-text-only dt-navbar-item ui-corner-all ui-state-default')
			.attr('role', 'menuitem')
			.wrapInner('<span class="ui-button-text"></span>')
			.click(function(){
				var $link = $(this);
				self._trigger('select', null, {item: $link.parent()[0], itemlink:$link[0]});
			});
	},
	_initSubItem: function(items){
		var self = this,
			options = this.options;
		
		items
			.hide()
			.addClass('ui-helper-clearfix');
		
		items
			.children('li')
				.children('a').click(function(event){
					event.stopPropagation();
					if(options.showSubMenu){
						if(self.activeSubMenu
								&& self.activeSubMenu.parent()[0] != this){
							self.activeSubMenu.hide();
						}
						self.activeSubMenu = $(this).parent().children('.dt-navsub-menu').toggle();
					}
					self._trigger('select', null, {item: $(this).parent()[0], itemlink:this});
				}).end()
				.addClass('dt-navsub-item')
				.children('ul')
					.addClass('dt-navsub-menu')
					.menu({
						select: function(event, obj){
							var $link = $(obj.item).children('a');
							self._trigger('select', null, {item: obj.item, itemlink:$link[0]});
						}
					})
					.hide()
					.end();
		items.each(function(){
			$(this)
				.children('li:not(:last)')
					.after('<li class="dt-navsub-gap"><span>|</span></li>');
		});
					
		$(document).click(function(){
			if(self.activeSubMenu){
				self.activeSubMenu.hide();
			}
		});
	},
	_initNav: function(){
		var self = this,
			element = self.element,
			options = self.options,
			enable_index = options.activeMenu,
			main_element = this.main_element = $(options.main_menu, element),
			sub_element = this.sub_element = $(options.sub_menu, element),
			main_enable = $('li:eq('+enable_index+')', main_element),
			sub_enable = $('ul:eq('+enable_index+')', sub_element);
			this._hasSub = false;
			
		element.addClass('dt-nav ui-widget');
		main_element.addClass('dt-navbar ui-widget-header ui-helper-clearfix');
		$('ul', main_element).addClass('ui-helper-reset');
		
		self._initBarItem($('li', main_element));
		if(sub_element.length > 0){
			self._initSubItem(sub_element.children('ul'));
			element.addClass('dt-navbar-hassub');
			this._hasSub = true;
		}
		sub_element.addClass('ui-widget-content');
		
		self._open($('a', main_enable), sub_enable);
		main_element.on('mouseenter', 'li', function(event){
			var target = $(this);
			if(target.hasClass('ui-state-disabled')){
				return;
			}
			var	index = target.index(),
				on_sub = sub_element.children(':eq('+index+')');
			self._open($('a', target), on_sub);
		});
	},
	_open: function(target, menu){
		target
			.addClass('dt-navbar-item-active ui-state-active');
			
		if(this._hasSub){
			target
				.addClass('ui-corner-top')
				.removeClass('ui-corner-all');
		}
		// 如果是已经打开的菜单，什么也不做
		if ( this.active && this.active[0] && this.active[0] == menu[0] ) {
			return;
		}
		// 隐藏以显示的菜单
		if ( this.active ) {
			this.active.hide();
		}
		if(this.activeBar){
			this.activeBar.removeClass('dt-navbar-item-active ui-state-active');
			if(this._hasSub){
				this.activeBar
					.addClass('ui-corner-all')
					.removeClass( 'ui-corner-top' );
			}
		}
		this.active = menu.show();
		this.activeBar = target
			.attr( "tabIndex", -1 );
		
	},
	_createElement: function(data){
		var o = this.options,
			$nav_main = $('<div class="'+ main_menu_class +'"><ul></ul></div'),
			main_element = $('ul', $nav_main),
			sub_element = $('<div class="'+ sub_menu_class +'"></div>'),
			item, i;
		
		for(i=0; i<data.length; i++){
			item = data[i];
			this._createItem(main_element, item, 0);
			sub_element.append(this._createItems(item[o.childrenField], 1, true));
		}
		
		this.element.append($nav_main);
		this.element.append(sub_element);
	},
	_createItems: function(items, level, depth){
		var $list = $('<ul></ul>'),
			subitem, i;
		for(i=0; i<items.length; i++){
			subitem = items[i];
			this._createItem($list, subitem, level, depth);
		}
		return $list;
	},
	_createItem: function($list, item, level, depth){
		var o = this.options,
			url = item.url ? item.url : 'javascript:void(0)',
			level = level ? level : 0;
		
		$listItem = $('<li></li>').appendTo($list)
			.data('menudata', item)
			.data('level', level);
		$link = $('<a href="' + url + '">'+item[o.textField]+'</a>')
			.appendTo($listItem);
		
		if(depth && item[o.childrenField]){
			$listItem.append(this._createItems(item[o.childrenField], level+1));
		}
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
				for(j=0; j<data.length; j++){
					if(item[o.parentField] == data[j][o.idField]){
						data[j][o.childrenField].push(item);
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
		
		var item = this._createItem(main_element, data);
		self._initBarItem(item);
		
		var subItems = this._createItems(data[o.childrenField], 0, true);
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
	},
	addExtBar: function(content){
		var $extBar = $('.dt-nav-extbar', this.main_element);
		if(!$extBar.length){
			$extBar = $('<div class="dt-nav-extbar"></div>').appendTo(this.main_element)
		}
		$(content).appendTo($extBar);
	}
});

})( jQuery );