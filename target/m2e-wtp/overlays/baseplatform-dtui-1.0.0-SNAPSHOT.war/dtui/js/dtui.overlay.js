(function($, undefined) {
	$.widget( "dtui.dtoverlay", {
		version: "1.10.1",
		options: {
			appendTo: "body",
		    color: '#000',
		    opacity: 0.5,
		    show:true,
		    text: '',
		    showIcon: true,
		    focusElem:null
		},

		_create: function() {
			var maskElem = this._appendTo();
			//var maskElem = this.element;
			//this.overlay = $("<div>")
			this.overlay = this.element
				//.addClass("ui-widget-overlay ui-front")
				.addClass("loadmask")
				.appendTo( maskElem );
			
			var msgcontainer = this.msgContainer = $("<div>")
				.addClass("loadmask-msg")
				.appendTo( maskElem );
			this.msg =$("<div>")
				.addClass(this.options.showIcon ? "mask_lading" : "normal")
				.text(this.options.text)
				.appendTo( this.msgContainer );
			
			msgcontainer.css({
				left: (maskElem.width()-msgcontainer.width())/2,
				top : (maskElem.height()-msgcontainer.height())/2,
			});
			
			if (this.options.focusElem){
				$(this.options.focusElem).appendTo(this.overlay);
			}
			this._on( this.overlay, {
				mousedown: "_keepFocus"
			});
		},
		
		_init: function() {
			if (this.options.show){
				this.show();
			}
			else {
				this.hide();
			}
		},

		_keepFocus: function( event ) {
			var focusElem = this.options.focusElem;
			function checkFocus() {
				var activeElement = this.document[0].activeElement,
					isActive = focusElem === activeElement ||
						$.contains( focusElem, activeElement );
				if ( !isActive ) {
					if (this.options.focusElem)
						$(this.options.focusElem).focus();
				}
			}
			event.preventDefault();
			checkFocus.call( this );
			// support: IE
			// IE <= 8 doesn't prevent moving focus even with event.preventDefault()
			// so we check again later
			this._delay( checkFocus );
		},
		
		_appendTo: function() {
			var element = this.options.appendTo;
			if ( element && (element.jquery || element.nodeType) ) {
				return $( element );
			}
			return this.document.find( element || "body" ).eq( 0 );
		},

		_destroy: function() {
			this.overlay.remove();
			this.msgContainer.remove();
			this.overlay = null;
		},

		widget: function() {
			return this.overlay;
		},

		disable: $.noop,
		enable: $.noop,
		show: function() {
			this.overlay.show();
			this.msgContainer.show();
		},
		hide: function() {
			this.overlay.hide();
			this.msgContainer.hide();
		},
		close: function() {
			this.hide();
			this._destroy();
		}
	});
	
	$.fn.extend({
		mask: function(text, showIcon, color){
			var o = {text: text, appendTo: this},
				overlay  = this.find('div.loadmask');
			if (showIcon !== undefined){
				o.showIcon = showIcon;
			}
			
			if (color !== undefined){
				o.color = color;
			}
			
			if (overlay.length === 0){
				overlay = $('<div>').dtoverlay(o);
			}
			else {
				overlay.dtoverlay('option', o);
			}
			//overlay.dtoverlay('show');
		},

		unmask: function() {
			var overlay = this.find('div.loadmask');
			if (overlay.length > 0){
				overlay.dtoverlay('close');
			}
		}
	});

})(jQuery);