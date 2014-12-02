/*
 * 弹出面板菜单
 */

(function( $, undefined ) {
	
$.widget('dtui.dtmenupanel', {
	version:'1.0.1',
	options: {
		width: "160px",
		panelWidth: "320px",
		subHeaderWidth: "120px"
	},
	_create: function(){
		var self = this,
			element = this.element,
			options = this.options;
		
		element
			.css({width: options.width})
			.addClass('ui-widget dtui-menupanel ui-widget-content ui-helper-reset')
			.children('div')
				.addClass('dtui-menupanel-item ui-helper-clearfix')
				.filter(':last')
					.addClass('dtui-menupanel-item-last')
				.end()
				.children('h4')
					.addClass('dtui-menupanel-header ui-widget-header ui-helper-reset')
					.on('mouseover', function(){
						var $ele = $(this);
						self._clearTimer();
						self._timer = setTimeout(function(){
							self._active($ele.parent());
						}, 100);
					})
					.append('<span class="ui-icon ui-icon-carat-1-e"></span>')
				.end()
				.children('div')
					.addClass('dtui-menupanel-panel  ui-widget-content ui-helper-reset')
					.css({left: options.width, width: options.panelWidth})
					.on('mouseleave ', function(){
						self._timer = setTimeout(function(){
							self._hideActive();
						}, 200);
					})
					.find('ul')
						.addClass('ui-helper-reset')
						.find('li')
							.addClass('dtui-menupanel-group ui-helper-clearfix')
					.end()
					.find('li h5')
						.addClass('dtui-menupanel-subheader ui-helper-reset')
						.css({width: options.subHeaderWidth})
					.end()
					.find('li div')
						.addClass('dtui-menupanel-subpanel')
				.end();
	},
	_active: function(item){
		var self = this;
		this._hideActive();
		self.activeItem = item;
		item
			.children('.dtui-menupanel-header')
				.addClass('ui-state-active')
			.end()
			.children('.dtui-menupanel-panel')
				.show();
	},
	_hideActive: function(){
		var item = this.activeItem;
		if(!item) return;
		item
			.children('.dtui-menupanel-header')
				.removeClass('ui-state-active')
			.end()
			.children('.dtui-menupanel-panel')
				.hide()
				.off('mouseover');
	},
	_clearTimer: function(){
		if(this._timer){
			clearTimeout(this._timer);
		}
	}
});

})( jQuery );