(function( $, undefined ) {
$.widget( "dtui.dtspinner", $.ui.spinner, {
	options: {
		culture: null,
		incremental: true,
		max: null,
		min: null,
		numberFormat: null,
		page: 10,
		step: 1,
		value:"",

		change: null,
		spin: null,
		start: null,
		stop: null
	},
	_create: function() {
		this.element.val(this.options.value);
		this._super();
	},

	_getCreateOptions:function(){
		this._super();
	},
	
	_draw:function(){
		this.element.css("outline","none");
		this._super();
	},
	
	_keydown:function(event){
		this._super(event);
	},
	
	_start:function(event){
		this._super(event);
	},
	
	_repeat:function(i, steps, event){
		this._super(i, steps, event);
	},
	
	_spin:function( step, event ){
		this._super( step, event );
	},
	
	_stop:function(event){
		this._super(event);
	},
	
	_setOption:function(key, value){
		this._super(key, value);
	},
	
	_refresh:function(){
		this._super();
	},
	
	_value:function(value, allowAny){
		this._super(value, allowAny);
	},
	
	_destroy: function() {
		this._super();
	},
	
	_stepUp:function(steps){
		this._super(steps);
	},
	
	_stepDown:function(steps){
		this._super(steps);
	},
	_uiSpinnerHtml:function(){
		return this._super();
	},

	_buttonHtml:function(){
		return this._super();
	},

	_increment:function(i){
		return this._super(i);
	},
	
	_precision:function(){
		return this._super();
	},
	
	
	_precisionOf:function(num){
		return this._super(num);
	},
	
	_adjustValue:function(value){
		return this._super(value);
	},
	
	_parse:function(val){
		return this._super(val);
	},

	_format:function(value){
	    return this._super(value);
	},
	
	widget:function(){
		return this._super();
	}
	

});

})( jQuery );
