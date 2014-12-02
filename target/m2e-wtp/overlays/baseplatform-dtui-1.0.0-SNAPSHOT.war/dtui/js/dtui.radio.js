/**
 * 
 */

(function( $, undefined ) {
//alert('a');


$.widget( "dtui.dtradio", {
	version: "1.10.1",
	options: {
		disabled: null,
		name:'',
		text: '',
		value: '',
		checked: false,
		height: null,
		width: null,
		click:null
	},
	_create: function() {
		var self = this,
			o = this.options;
		var radioElement = $('<input type="radio">').attr({value:o.value, checked:o.checked, name:o.name}).bind('click', function(event){
				self._trigger('click',event);
			}),
			labelElement = $('<span></span>').text(o.text).bind('click',function(event){
				if (o.disabled) return;
				radioElement.prop('checked',true);
				self._trigger('click',event);
			}),
			linkElement = $('<a href="javascript:void(0)"></a>').append(labelElement),
			spanElement = $('<span></span>').addClass('dtui-radio').append(radioElement).append(linkElement);
		
		if (o.height) {
			this.element.height(o.height);
		}
		if (o.width) {
			this.element.width(o.width);
		}
		
		//保证单选按钮垂直居中
		this.element.addClass('dtui-radio-container');
		
		this.element.append(spanElement);
	},


	widget: function() {
		return this.element;
	},
	
	value: function(v){
		if (arguments.length ===0) {
			return this.options.value;
		}
		else {
			this.options.value = v;
		}
	},
	
	check: function(checked){
		if (arguments.length ===0) {
			return $(':radio', this.element).prop('checked');
		}
		else {
			$(':radio', this.element).prop('checked', checked);
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});

$.widget( "dtui.dtradiogroup", {
	version: "1.10.1",
	options: {
		label:"",
		disabled: null,
		name:'',
		required: false,
		items:[],
		col: 1,
		value: null,
		title: '',
		height: null,
		width: null,
		keyField:"id",
		valueField:"name",
		padding_left:15,
		change:null
	},
	
	_create: function() {
		var self = this,
			o = this.options;
		
		this._radioControls = [];
		this._starDiv = $.dtui.util.createStarElem(this.options.required);
		 
		var setElement = $('<fieldset></fieldset>').addClass('dtui-radiogroup-fieldset').addClass('ui-corner-all').append($('<legend></legend>').text(o.title));
		//setElement.css('float','left');
		
		if ($.isArray(o.items)) {
			$.each(o.items, function(i, item){
				self._createRadio(setElement, i, item[self.options.keyField], item[self.options.valueField]);
			});
		}
		else {
			var i=0;
			$.each(o.items, function(value, text){
				self._createRadio(setElement, i++, value, text);
			});
		}
		
		if (o.height) {
			//this.element.height(o.height);
			setElement.width(o.height);
		}
		if (o.width) {
			//this.element.width(o.width);
			setElement.width(o.width);
		}
		/*
		if (o.required) {
			this._starElem.css({'display':'inline-table'});
		}
		*/
		this.element.addClass('dtui-radiogroup-container');
//		setElement.addClass('dtui-radiogroup');
//		setElement.css({'width':'100%', 'height': '100%'});
		this.element.append(setElement);
		$("<label></label>")
		.text(this.options.label)
		.insertBefore(this.element);
		
		/*把radiogroup转换成 内联块级元素*/
		this.element.css("display","inline-block");
		
		
		/*用div 把控件和 星号 封装起来 同时把块级元素改成内联元素  */
		
		
		//this.element.addClass("input-init");
		var container=$("<div style='display:inline-block'></div>")
						.css({"height":this.element.height,"width":this.element.width,"margin":"0 auto"})
						.insertAfter(this.element);
		this._starDiv.appendTo(container)
				.css("padding-top",this.options.height/2);//set star middle
		var divinput=$("<div></div>").append(this.element).appendTo(container);   //set corner all
		if(this.options.required)
			//this.element.parent().css("margin-right",this.element.parent().siblings().outerWidth());
			divinput.css("margin-right",this._starDiv.width());		
	},

	_createRadio: function(setElement, i, value, text) {
		var self = this,
			o = this.options;
		var radioClick = function(event, ui) {
			self._trigger('change', event, $(this));
		};
		
		var radioControl = $('<div></div').addClass('dtui-radiogroup-item').prop('id', self.element.prop('id')+'_'+value).dtradio({
			value:value,
			checked: o.value && o.value ===value,
			name:o.name,
			text:text,
			click: radioClick
		});
		
		
		if ((i+1)%o.col !==1)
			radioControl.css('padding-left', o.padding_left);
		
		this._radioControls.push(radioControl);
		setElement.append(radioControl);
		if ((i+1)%o.col ===0)
			setElement.append('<p></p>');
	},

	widget: function() {
		return this.element;
	},
	
	value: function(v){
		if (arguments.length ===0) {
			var result = null;
			$.each(this._radioControls, function(i, radioControl){
				if (radioControl.dtradio('check')){
					result = radioControl.dtradio('value');
					return ;
				}
			});
			return result;
		}
		else {
			$.each(this._radioControls, function(i, radioControl){
				if (radioControl.dtradio('value') === v){
					radioControl.dtradio('check',true);
					return;
				}
			});
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});


})( jQuery );