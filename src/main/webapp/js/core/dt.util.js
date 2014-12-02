/**
 * 前端的公共操作方法集合
 * @charset utf-8
 * @param w
 * @author yangzhuang
 * @date 2014-03-26
 */
(function(w){
	if(!w.util) w.util = {};
	//定义局部变量 代替全局变量
	var messageBox$ = $.dtmessagebox;
	//ajax列表异步请求
	util.ajaxGridReq = function(url,data,callback){
		if(!url) return false;
		var body$ = $('body'),type = 'get',urlParames = url.split('?');
		body$.mask('处理中');
		if(data&&urlParames.length>1){
			type = 'post';
			urlParames[1] = encodeURIComponent(urlParames[1]);
			url = urlParames.join('?');
		}
		$.ajax({url : url,type : type,async : true,cache:false,data:data,
			success : function(msg) {
				body$.unmask();
				if(typeof msg === 'String'){
					if (msg == 'S') {
						messageBox$.success('操作成功！', '成功', succCallback);
					} else {
						messageBox$.error('操作失败！' + msg,'失败');
					}
				}else{
					var sucNum = msg.successNum,errNum = msg.errorNum,rtnMsg = msg.messageContent,errMsg='';
					if(!sucNum){
						messageBox$.error('系统出错了！'+msg);
						return false;
					}
					if(rtnMsg){
						errMsg = '<br>'+rtnMsg;
					}
					if(sucNum >0){
						if(errNum>0){
							messageBox$.warn('本次操作成功'+sucNum+'条，失败'+errNum+'条!'+errMsg,'操作提示',succCallback);
						}else{
							messageBox$.success('操作成功！','操作提示',succCallback);
						}
					}else if(sucNum == 0 && errNum > 0){
						messageBox$.error('操作失败！'+errMsg);
					}else{
						messageBox$.alert(errMsg);
					}
				}
			},
			error : function(e) {
				body$.unmask();
				messageBox$.error(e.statusText);
			}
		});
	};
	//ajax异步请求
	util.ajaxOneReq = function(url,data,callback,maskFlag){
		if(!url) return false;
		var body$ = $('body'),type = 'get',urlParames = url.split('?');
		if(!maskFlag) body$.mask('处理中');
		if(data&&urlParames.length>1){
			type = 'post';
			urlParames[1] = encodeURIComponent(urlParames[1]);
			url = urlParames.join('?');
		}
		if(data && typeof data == 'String') data = encodeURIComponent(data);
		$.ajax({url : url,type : type,async : true,cache:false,data:data,
			success : function(msg) {
				if(!maskFlag) body$.unmask();
				callback(msg);
			},
			error : function(e) {
				if(!maskFlag) body$.unmask();
				messageBox$.error(e.statusText);
			}
		});
	};
	//表单重置
	util.resetForm = function(formId){
		var formEle = document.getElementById(formId);
		if(formEle) formEle.reset();
	};
	//关闭弹出窗口
	util.closeWin=function(){
		w.opener = null;
		w.close();
	};
	//判断输入框的取值是否为字母 和数字
	util.isCharOrNum = function(inputId,prompt){
		var value = $('#'+inputId).val(),reg = /^[a-zA-Z0-9]+$/ig;
		if(reg.test(value)){
			return true;
		}else{
			messageBox$.error(prompt+'只能输入大小写字母或数字！');
			return false;
		}
	};
	//显示模态窗口
	util.showModelWin = function(url,width,height,opt_isCache){
		if(!opt_isCache){
			if(url.indexOf('?')>-1) url += '&dateCache='+(new Date()).getTime();
			else url += '?dateCache='+(new Date()).getTime();
		}
		return w.showModalDialog(url,w,'dialogHeight:'+height+'px;dialogWidth:'+width+'px;center: yes;help: no;resizable:yes;status:no;scroll:yes;');
	};
	//mobile校验手机 phone校验固定电话 
	util.checkTel = function (tel)  { 
	   var mobile = /^1[3|5|8]\d{9}$/ , phone = /^0\d{2,3}-?\d{7,8}$/; 
	   return mobile.test(tel) || phone.test(tel); 
	} ;
	//校验图片格式
	util.imgType = function (eleId){
		var fileEle = document.getElementById(eleId);
		if (!fileEle || !fileEle.value)	return false;
		var reg = /\.jpg$|\.png$/ig;
		if (reg.test(fileEle.value)) {
			return true;
		} else {
			return false;
		}
	};
	util.getBirthdayByIdNo = function(idNo) {
		if(!idNo) return false;
		idNo = idNo.replace(/^\s*|\s*$/ig,'');
		if(!idNo) return false;
		var idNoLen = idNo.length, tmpArray = [],re;
		if (idNoLen == 15) {
			re = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/i;
			tmpArray = idNo.match(re).slice(2,5);
			tmpArray[0] = '19'+tmpArray[0];
		} else if(idNoLen == 18) {
			re = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{4})$/i;
			tmpArray = idNo.match(re).slice(2,5);
		}
		return tmpArray.join('');
	};
	//校验身份证格式
	util.isIdCardNo = function(idNo) {
		if(!idNo) return false;
    	idNo = idNo.toUpperCase();  
		//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(idNo)))   
	    { 
	    	$.dtmessagebox.warn('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。'); 
	        return false; 
	    }
	    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
	    //下面分别分析出生日期和校验位 
	    var len = idNo.length, re,arrSplit = [],birthDay = null; 
	    if (len == 15) 
	    { 
	        re = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/g; 
	        arrSplit = idNo.match(re);
			arrSplit[2]='19'+arrSplit[2];
		}else if(len == 18){
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/); 
			arrSplit = idNo.match(re);
		}else{
			$.dtmessagebox.warn('输入的身份证号位数不正确！');   
			return false;
		}
		birthDay = arrSplit.slice(2,5).join('-'); 
	    if(!util.isDate(birthDay)){
	    	$.dtmessagebox.warn('输入的身份证号里出生日期不对！');   
	        return false; 
	    }
	    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
	    var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'],
			nTemp = 0,numArray = idNo.split('');
	    for(var i = 0; i < 17; i ++) 
	    {
	        nTemp += numArray[i] * arrInt[i]; 
	    }
		var endChar = arrCh[nTemp % 11];
		if(endChar){
			if(len == 15) return true;
			else if(len == 18 && endChar == numArray[17]){
				return true;
			}
		}
		$.dtmessagebox.warn('输入的身份证号格式错误！');
	    return false; 
	};
	/**
	 * 根据身份件类型改变身份证号是否必填，typeId身份件证类型控件ID numId身份证件号控件ID
	 * 当证件类型为“其它”时，非必填
	 */
	util.valiIdNumRequired = function (typeId,numId){
		//判断身份证件类型是否等于居民身份证
		if ($("#"+typeId).dtcombobox('selectValue')=='C'){
			$('#'+numId).dtinput("option","required",false);
			$('#'+numId).val('');
		}else {
			$('#'+numId).dtinput("option","required",true);
		}
	};
	
	/**
	 * 验证身份证件类型证件号出生日期
	 * @param typeId
	 * @param numId
	 * @param csrq
	 */
	util.valiIdCard = function(type, num, csrq) {
		if(type == '1'){
			if(util.isIdCardNo(num)){
				if(csrq != util.getBirthdayByIdNo(num)){
					$.dtmessagebox.warn("身份证件号与出生日期不匹配！");
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}else{
			return true;
		}
	};
	//验证字符串是否为日期 参数date格式为yyyy-mm-d，其他格式返回null,验证正确返回 字符串本身
	util.isDate = function(date){
		if(!date) return null;
		if(typeof date === 'object' && Object.prototype.toString.call(date) === '[object date]'){
			return date;
		}else if(typeof date == 'string'){
			var reg = /^(\d{4})-(\d{2})-(\d{2})$/,dateArr = date.match(reg),
				days={'1':31,'2':28,'3':31,'4':30,'5':31,'6':30,'7':31,'8':31,'9':30,'10':31,'11':30,'12':31};
			if(dateArr&&dateArr.length==4){
				var year = parseInt(dateArr[1],10),month = parseInt(dateArr[2],10),day=parseInt(dateArr[3],10);
				if(month>=0&&month<13&&day>0){
					if(month == 2&&year%4 == 0&&day<30){
						return date;
					}else if(day<=days[month]){
						return date;
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
		return null;
	};
	/* 比较两个日期字符串
	 * 字符串格式为：yyyy-mm-dd
	 * a>b返回1,a==b 返回0,a<b返回-1
	 * 参数如为空 则比较当前时间
	*/
	util.compareDate=function(a,opt_b){
		if(!a) return -1; 
		var aArray=[],bArray = [],aDate = null,bDate = null;
		if(!opt_b) bDate = new Date();
		aArray = a.split('-');
		if(!aArray&&aArray.length != 4) return false;
		aDate = new Date(aArray[1],aArray[2],aArray[3]);
		if(!bDate){
			bArray = opt_b.split('-');
			if(!bArray&&bArray.length != 4) return -1;
			bDate = new Date(bArray[1],bArray[2],bArray[3]);
		}
		if(aDate.getTime()>bDate.getTime()){
			return 1;
		}else if(aDate.getTime() == bDate.getTime()){
			return 0;
		}else {
			return -1;
		}
	};
	/**
	 * html解码
	 */
	util.htmlDecode = function(value){
		if(value && (value=='&nbsp;' || value=='&#160;' || (value.length===1 &&(value.charCodeAt(0)===160 || value.charCodeAt(0)===32)))) { return '';}
		return !value ? value : String(value).replace(/&gt;/g, '>').replace(/&lt;/g, '<').replace(/&quot;/g, '"').replace(/&amp;/g, '&');		
	};

	/**
	 * html编码
	 */
	util.htmlEncode = function (value){
		if(!value) return '';
		return String(value).replace(/&/g, '&amp;').replace(/\"/g, '&quot;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
	};
	
	
})(window);