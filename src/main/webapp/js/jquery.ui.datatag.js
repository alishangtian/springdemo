(function($,undefined){
	$.widget("ui.datatag", {
		options: {
			id: "",
			name: "",
			data: null,
			classname: "",
			width: null,
			namefield: "",
			onclose: null
		},
		_create: function(){
			this.reset(this.options.data);
		},
		_createTag: function(container, data){
			var close = $("<span class='ui-icon ui-icon-close ui-icon-close-default' style='float:left;'></span>"), self = this;
			//container.html(data[this.options.namefield]).append(close);
			var param=$("<span style='float:left;'>"+data[this.options.namefield]+"</span>");
			container.append(param).append(close);
			close.click(function(event){
				if($.isFunction(self.options.onclose)){
					if(self.options.onclose.call(self, data) == false){
						return;
					}
				}
				$(this).unbind().removeClass().remove();
				container.unbind().removeClass().remove();
			});
		},
		add: function(data){
			var tag = $("<div tag='true'></div>");
			if(this.options.classname){
				tag.addClass(this.options.classname);
			}else{
				tag.css("float", "left");
			}
			if(this.options.width){
				tag.width(this.options.width);
			}
			tag.data("data", data);
			this._createTag(tag, data);
			this.element.append(tag);
		},
		reset: function(data){
			this._destroy();
			if(!data){
				data = this.options.data;
			}
			var self = this;
			$.each(data, function(i, prop){
				self.add(prop);
			});
		},
		_destroy: function(){
			$("*", this.element).unbind().removeClass().remove();
			this.element.unbind().html("");
		},
		getData: function(){
			var result = [];
			$("div[tag]", this.element).each(function(){
				result.push($(this).data("data"));
			});
			return result;
		}
	});
})(jQuery);