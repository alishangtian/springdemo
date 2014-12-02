/*
 * 工具条控件
 */
(function( $ ) {

$.widget( "dtui.dttoolbar", {
	options: {
		items: [], //每个按钮的配置项，具体参考按钮组件
		menuSelect : null
   	},
    _create: function(){
    	this.items = this._initItems();
    	this.element
			.addClass('dtui-toolbar ui-widget-header ui-helper-reset ui-helper-clearfix')
			.children('li')
				.addClass('dtui-toolbar-item');
    },
    _initItems: function(){
    	var self = this,
    		$items = this.element.children('li'),
    		eleLen = $items.length,
    		itemsOption = this.options.items,
    		oLen = itemsOption.length,
    		length = eleLen > oLen ? eleLen : oLen,
    		element;
    	
    	for(var i=0; i<length; i++){
    		element = $items[i];
    		element.option = itemsOption[i];
    		this._createItem(element);
    	}
    	$items.on('click', '>:first-child', function(event){
    		self._activeItem($(this).parent(), event);
    	});
    	self._on( {
			keydown: function( event ) {
				if ( event.keyCode == $.ui.keyCode.ESCAPE && self.active && self.active.menu( "left", event ) !== true ) {
					var active = self.active;
					self.active.blur();
					self._close( event );
					active.prev().focus();
				}
			},
			focusin: function( event ) {
				clearTimeout( self.closeTimer );
			},
			focusout: function( event ) {
				self.closeTimer = setTimeout( function() {
					self._close( event );
				}, 100);
			}
		});
    },
    _activeItem : function(item, event){
    	var self = this,
    		$menu = item
				.children(':nth-child(2)');
    	if($menu.length > 0){
    		if($menu.is( ":visible" ) && self.active && self.active[0] == $menu[0]){
    			self._close();
    		}else{
    			self._open(event, $menu)
    		}
    	}else{
    		self._close();
    	}
    },
    _createItem: function(item){
    	var self = this;
    	var $menu = $(item)
    		.children(':nth-child(2)')
    			.addClass('dtui-toolbar-menu');
    	if($menu.length > 0){
    		$menu.menu(item.option.menu).hide();
    	}
    	$(item)
    		.children(':first-child')
    			.dtbutton(item.option.button);
    },
    _open: function( event, menu ) {
		// on a single-button menubar, ignore reopening the same menu
		if ( this.active && this.active[0] == menu[0] ) {
			return;
		}
		// TODO refactor, almost the same as _close above, but don't remove tabIndex
		if ( this.active ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
			this.active
				.prev()
				.removeClass( "ui-state-active" );
		}
		// set tabIndex -1 to have the button skipped on shift-tab when menu is open (it gets focus)
		var button = menu.prev().addClass( "ui-state-active" ).attr( "tabIndex", -1 );
		this.active = menu
			.show()
			.position( {
				my: "left top",
				at: "left bottom",
				of: button
			})
			.removeAttr( "aria-hidden" )
			.attr( "aria-expanded", "true" )
			.menu("focus", event, menu.children( "li" ).first() )
			// TODO need a comment here why both events are triggered
			.focus()
			.focusin();
		this.open = true;
	},
    _close: function() {
		if ( !this.active || !this.active.length )
			return;
		this.active
			.menu( "collapseAll" )
			.hide()
			.attr( "aria-hidden", "true" )
			.attr( "aria-expanded", "false" );
		this.active
			.prev()
			.removeClass( "ui-state-active" )
			.removeAttr( "tabIndex" );
		this.active = null;
		this.open = false;
	}
});
}( jQuery ));
