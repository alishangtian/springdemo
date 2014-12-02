/**
 * 左边菜单组件
 */
(function( $ ) {
$.widget( "ui.leftmenubar", {
	options: {
		nameCol: null,
		nodesCol: null,
		root: {},
		data: [],
		leafClass: "listleaf",
		buttons: [],
		callback: {
			click: null
		}
   	},
	_create: function() {
		this.load(this.options.data);
	},
	load: function(data){
		this.destroy();
		this.element.addClass("F_tree");
		this.options.data = data;
		this._createMenuItem(this.options.data, this.element);
	},
	destroy: function(){
		$("*", this.element).unbind().removeClass().remove();
		return $.Widget.prototype.destroy.call( this );
	},
	_createMenuItem: function(data, target){
		var menu = $("<ul></ul>"), self = this;
		menu.appendTo(target);
		$.each(data, function(i, prop){
			var li = $("<li></li>"), a = $("<a href='javascript:void(0)'>" + prop.menuName + "</a>");
			li.appendTo(menu);
			li.append(a);
			if(prop.subMenu && prop.subMenu.length > 0){
				self._createBranches(li);
				self._createMenuItem(prop.subMenu, li);
			}else{
				self._createLeaf(li, prop);
			}
		});
	},
	_createBranches: function(li){
		li.addClass("branches");
		$("a", li).click(function(event){
			event.preventDefault();
			$("ul", li).toggle();
		});
	},
	_createLeaf: function(li, prop){
		var self = this;
		li.addClass(this.options.leafClass);
		li.hover(function(){
			$(this).addClass("selected");
		},function(){
			$(this).removeClass("selected");
		}).click(function(event){
			event.preventDefault();
			if($.isFunction(self.options.callback.click)){
				self.options.callback.click(li, null, prop);
			}
		});
		if(this.options.buttons && this.options.buttons.length > 0){
			$.each(this.options.buttons, function(i, conf){
				var button = $("<button></button>");
				button.addClass(conf.classname);
				button.attr("title", conf.title);
				button.click(function(event){
					event.stopPropagation();
					if($.isFunction(conf.click)){
						conf.click.call(li, this, prop);
					}
				});
				li.append(button);
			});
		}
	}
});
}( jQuery ));
