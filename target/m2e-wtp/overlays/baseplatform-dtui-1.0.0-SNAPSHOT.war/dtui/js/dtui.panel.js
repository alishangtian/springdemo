/**
 * 
 */

(function( $, undefined ) {
	var defClass = 'dtui-panel',
		defHeaderClass = 'dtui-panel-header',
		defTitleClass = 'dtui-panel-title',
		defContentClass = 'dtui-panel-content';
	
	$.widget("dtui.dtpanel", {
		options: {
			title: null,
			height: null,
			width: null,
			url: null,
			open : null,
			close : null,
			min : null,
			extend : null,
			ajax: false
		},
		_create: function(){
			this.refresh();
		},
		getData: function(){
			return this.options;
		},
		refresh: function(){
			this.element
				.addClass(defClass + ' ui-widget ui-helper-reset');
			if(this.options.width){
				this.element.width(this.options.width);
			}
			this._createHead();
			this._createContent();
		},
		/**
		 * 创建面板头部
		 */
		_createHead: function(){
			var self = this;
			
			if(this.options.url != null && this.options.url.length > 0){
				this._head = $("<div></div>")
					.addClass(defHeaderClass)
					.appendTo(this.element);
				this._title = $("<span></span>")
					.addClass(defTitleClass)
					.appendTo(this._head);
			}else{
				this._head = this.element.children("." + defHeaderClass);
				this._title = this.element.children("." + defTitleClass);
			}
			this._title.html(this.options.title);
			this._head.append(this._title)
				.addClass('ui-widget-header ui-helper-reset ui-helper-clearfix ui-corner-top');
			
			if(!this.options.buttons){
				this.options.buttons = [{
					type: "close"
				},{
					type: "min"
				}];
			}
			$.each(this.options.buttons, function(i, prop){
				self._head.append(self._createButton(prop));
			});
		},
		/**
		 * 创建最小化和关闭按钮
		 * @param prop
		 * @returns
		 */
		_createButton: function(config){
			var prop = this['_' + config.type]();
			var button = $('<a></a>')
				.addClass('dtui-panel-btn ui-corner-all '+prop.className);
			$('<span></span>')
				.addClass('ui-icon ' + prop.iconClass)
				.appendTo(button);
			
			button.click(function(event){
				prop.click.call(this, event);
			});
			this._hoverable(button);
			this[config.type+'Btn'] = button;
			return button;
		},
		/**
		 * 创建面板内容
		 */
		_createContent: function(){
			if(this.options.url!=null&&this.options.url.length>0){
				this._content = $("<div></div>")
					.addClass(defContentClass)
					.appendTo(this.element);
			}else{
				this._content = this.element.children("." + defContentClass);
			}
			if(this.options.height){
				this._content.height(this.options.height);
			}
			this._content.addClass('ui-widget-content ui-helper-reset ui-corner-bottom');
			if(this.options.url){
				if(this.ajax){
					this._content.load(this.options.url);
				}else{
					var html  = '<iframe frameborder="0" src="' + this.options.url + '" scrolling="auto" />';
					this.frame = $(html).appendTo(this._content);
				}
			}
		},
		/**
		 * 销毁面板
		 * @returns
		 */
		destroy: function(){
			this._content.unbind().removeClass().remove();
			$("*", this.element).unbind().removeClass().remove();
			this.element.unbind().removeClass().remove();
			return $.Widget.prototype.destroy.call( this );
		},
		/**
		 * 关闭
		 */
		_close: function(){
			var self = this;
			return {
				className: 'dtui-panel-close',
				iconClass: "ui-icon-close",
				click: function(){
					self.close();
				}
			};
		},
		/**
		 * 最小化
		 */
		_min: function(){
			var self = this;
			return {
				className: 'dtui-panel-min',
				iconClass: "ui-icon-minus",
				click: function(){
					if(self._mined){
						self.extend();
						self._mined = false;
					}else{
						self.min();
						self._mined = true;
					}
				}
			};
		},
		open: function(){
			this.element.slideDown("fast");
			this._trigger('open', null, this.element);
		},
		close: function(){
			this.element.slideUp("fast");
			this._trigger('close', null, this.element);
		},
		min: function(event){
			this._content.slideUp("fast");
			this._head.addClass('ui-corner-bottom');
			this._trigger('min', null, this.element);
		},
		extend: function(){
			this._content.slideDown("fast");
			this._head.removeClass('ui-corner-bottom');
			this._trigger('extend', null, this.element);
		},
		load: function(url){
			if(this.ajax){
				this._content.load(url);
			}else{
				this.frame.attr('src', url);
			}
		}
	});
})( jQuery );