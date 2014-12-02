/**
 * 头部菜单组件
 */
(function( $ ) {
$.widget( "ui.topmenubar", {
	options: {
		menuclick: function(){},
		submenuclick: function(){},
      	data: []
   	},
	_create: function() {
		this._createFirstMenuItem();
		$("<div class='clear'></div>").appendTo(this.element);
		this._selectMenuByIndex(0);
		this._showSubMenuByIndex(0);
	},
	_createFirstMenuItem: function(){
		var menuContent = $("<div class='leve1F_tabs'></div>"),
			menu = $("<ul></ul>"),
			subMenuContent = $("<div class='Leve2F_nav' style='position:absolute;display:inline;width:1260px;'></div>"),
			menuWidth = 0, showCount = 1,
			overflow = false,
			self = this;
		menuContent.appendTo(this.element);
		menu.appendTo(menuContent);
		subMenuContent.appendTo(this.element);
		
		$.each(this.options.data, function(i, prop){
			var menuItem = $('<li index="' + i + '"><a href="#">' + prop.menuName + '</a></li>');
			menuItem.data("data", prop);
			menuItem.appendTo(menu)
			.click(function(){
				if($.isFunction(self.options.menuclick)){
					self.options.menuclick.call(menuItem);
				}
			});
			$("a:first", menuItem).click(function(){
				return false;
			});
			self._createSubMenuItem(i, prop.subMenu, subMenuContent);
			menuWidth += menuItem.width();
			
			if(overflow == false && (menuWidth  > menu.width())){
				showCount = i;
				overflow = true;
			}
			if(overflow == true){
				menuItem.hide();
			}
		});
		
		if(overflow == true){
			this._createOverflowButton(menuContent, menu, showCount);
		}
//		upItem.appendTo(menuContent);
		$("li", menu).bind("mouseenter", function(event){
			$("li>a", menu).removeClass("current");
			$("ul[parentIndex]", self.element).hide();
			var index = $(this).attr("index");
			self._selectMenuByIndex(index);
		});
	},
	_createSubMenuItem: function(parentMenuIndex, data, container){
		/*var self = this;
		var subMenuContainer = $("<ul parentIndex='" + parentMenuIndex + "'></ul>").appendTo(container);
		subMenuContainer.hide();
		$.each(data, function(i, prop){
			var menuItem = $('<a href="#">' + prop.menuName + '</a>');
			$("<li></li>").append(menuItem).appendTo(subMenuContainer);
			menuItem.data("data", prop);
			menuItem.click(function(){
				$("a", subMenuContainer).removeClass("current");
				$(this).addClass("current");
				if($.isFunction(self.options.submenuclick)){
					self.options.submenuclick.call(menuItem);
				}
				return false;
			});
		});*/
		var menuWidth = 0, showCount = 1, overflow = false;
		var self = this;
		var subMenuContainer = $("<ul parentIndex='" + parentMenuIndex + "'></ul>");
		var subMenuDiv=$("<div style='float:left;position:relative;width:98%;_width:97%;'></div>");
		subMenuContainer.appendTo(subMenuDiv);
		subMenuDiv.appendTo(container);
		
		$.each(data, function(i, prop){
			var menuItem = $('<li index="' + i + '"><a href="#">' + prop.menuName + '</a></li>');
			menuItem.data("data", prop);
			menuItem.appendTo(subMenuContainer).click(function(){
				$("a", subMenuContainer).removeClass("current");
				$(this).addClass("current");
				if($.isFunction(self.options.submenuclick)){
					self.options.submenuclick.call(menuItem);
				}
				return false;
			});
			
			
            menuWidth += menuItem.width();
            
			if(overflow == false && (menuWidth  > subMenuContainer.width())){
				showCount = i-1;
				menuItem.prev().hide();
				overflow = true;
			}
			
			if(overflow == true){
				menuItem.hide();
			}
		});
		
		if(overflow == true){
			this._createOverflowButton(container, subMenuContainer, showCount);
		}
		
		subMenuContainer.hide();
		
	},
	_selectMenuByIndex: function(index){
		$("a:first", $("li[index='" + index + "']", this.element)).addClass("current");
		this._showSubmenu($("ul[parentIndex='" + index + "']", this.element), $("a:first", $("li[index='" + index + "']", this.element)));
	},
	_showSubmenu: function(subMenu, container){
		subMenu.show();
//		subMenu.offset({
//			left: container.offset().left
//		});
	},
	_showSubMenuByIndex: function(index){
		$("a:first", $("ul[parentIndex='" + index + "']", this.element)).triggerHandler("click");
	},
	_createOverflowButton: function(menuContent, menu, showCount){
		
		var menuOperBar = $("<div class='overflow_buttons'></div>"), allMenu = $("<div title='更多...' class='allmenu_buttons'></div>");
		menuOperBar.append(allMenu).appendTo(menuContent);
		var menuListDiv = $("<div class='moremenu-panel'></div>").appendTo(menuOperBar);
		
		$("li", menu).each(function(i, menuItem){
			var menuData = $(menuItem).data("data");
			var item = $("<div itemIndex='" + i + "' class='moremenu-inner'>" + menuData.menuName + "</div>");
			item.appendTo(menuListDiv).hover(function(){
				$(this).addClass("moremenu-select");
			},function(){
				$(this).removeClass("moremenu-select");
			}).click(function(event){
				event.stopPropagation();
				var index = $(this).attr("itemIndex");
				if(index/1 + 1 > showCount){//隐藏的菜单
					$("li", menu).each(function(k, li){
						var liIndex = $(li).attr("index");
						if((liIndex/1 < index/1 + 1 - showCount) || liIndex/1 > index/1){//隐藏当前菜单两边的菜单
							$(li).hide();
						}else{
							$(li).show();
						}
					});
				}else{
					$("li", menu).each(function(k, li){
						var liIndex = $(li).attr("index");
						if(liIndex/1 + 1 > showCount){//隐藏当前菜单两边的菜单
							$(li).hide();
						}else{
							$(li).show();
						}
					});
				}
				$("li[index=" + index + "]", menu).trigger("mouseenter");
				allMenu.trigger("click");
			});
		});
		
		allMenu.toggle(function(event){
			//*****解决ie7 z-index的bug begin*******//
			var zIndexNumber = 1000;
			$('div').each(function() {
				$(this).css('zIndex', zIndexNumber);
				zIndexNumber -= 1;
			});
			//*****解决ie7 z-index的bug end*******//
			event.stopPropagation();
			menuListDiv.css({
				right: 5,
				top: 15
			});
			menuListDiv.show();
		},function(event){
			event.stopPropagation();
			menuListDiv.hide();
		});
	}
});
}( jQuery ));
