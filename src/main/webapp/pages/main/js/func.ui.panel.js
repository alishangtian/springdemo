(function($,undefined){
	$.widget("ui.panel", {
		options: {
			title: "",
			height: "",
			buttons: null,
			useIframe: true,
			url: "",
			html: "",
			load: null,
			show: null,
			slideUpClick:"",
			slideDownClick:"",
			closeClick:""
		},
		_create: function(){
			this.refresh();
		},
		getData: function(){
			return this.options;
		},
		refresh: function(){
			this._box = $("<div class='box'></div>");
			this.element.append(this._box);
			this._createHead();
			this._createContent();
//			this._box.resizable({
//				handles: "s"
//			});
		},
		_createHead: function(){
			var self = this;
			this._head = $("<div class='boxHead'></div>");
			this._title = $("<div class='bhTitle'>" + this.options.title + "</div>");
			this._head.append(this._title).appendTo(this._box);
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
		_createButton: function(prop){
			var button = $("<button></button>");
			if(this["_" + prop.type]){
				var p = this["_" + prop.type]();
				button.addClass(p.classname);
				if($.isFunction(p.click)){
					button.click(p.click);
				}else{
					button.toggle.apply(button, p.click);
				}
			}else{
				button.addClass(prop.classname);
				if($.isFunction(prop.click)){
					button.click(function(event){
						prop.click.call(this);
					});
				}
			}
			return button;
		},
		_createContent: function(){
			this._content = $("<div class='boxContent'></div>");
			if(this.options.height){
				this._content.height(this.options.height);
			}
			this._content.appendTo(this._box);
			if(this.options.url){
				var html = '<iframe frameborder="0" src="' + this.options.url + '" scrolling="no"'
				+ 'style="display:block;z-index:999;overflow:hidden;'
				+ 'width:100%;'
				+ 'height:' + this._content.height() + 'px;" />';
				this._content.append($(html));
			}
		},
		destroy: function(){
			this._content.unbind().removeClass().remove();
			$("*", this._box).unbind().removeClass().remove();
			this.element.unbind().removeClass().remove();
			return $.Widget.prototype.destroy.call( this );
		},
		_close: function(){
			var self = this;
			return {
				classname: "btr",
				click: function(){
					self.destroy();
					
					//add closeClick 2012/11/15
					if(self.options.closeClick!=null&&self.options.closeClick!=""){
						if($.isFunction(self.options.closeClick)){
							self.options.closeClick(self.options.title,self);
						}else{
							//self.toggle.apply(self, self.options.closeClick);
						}
					}
				}
			};
		},
		_min: function(){
			var self = this;
			return {
				classname: "btl",
				click: [function(){
					self._content.slideUp("fast");
					
					//add slideUpClick 2012/11/15
					if(self.options.slideUpClick!=null&&self.options.slideUpClick!=""){
						if($.isFunction(self.options.slideUpClick)){
							self.options.slideUpClick(self);
						}else{
							//self.toggle.apply(self, self.options.slideUpClick);
						}
					}
				},function(){
					self._content.slideDown("fast");
					
					//add slideDownClick 2012/11/15
					if(self.options.slideDownClick!=null&&self.options.slideDownClick!=""){
						if($.isFunction(self.options.slideDownClick)){
							self.options.slideDownClick(self);
						}else{
							//self.toggle.apply(self, self.options.slideDownClick);
						}
					}
				}]
			};
		}
	});
})(jQuery);