var DtDataTag = Class.create($,{
	initialize: function(selector, options) {
		this.selector = selector;
	    this.jqobj = $(selector);
	    this.jqobj.datatag(options);		       
	},
	reset: function(data){
		this.jqobj.datatag("reset", data);
	},
	add: function(data){
		return this.jqobj.datatag("add", data);
	},
	getData: function(){
		return this.jqobj.datatag("getData");
	}
});
$.datatag=function(selector, options) {
   	return new DtDataTag(selector, options);
};
