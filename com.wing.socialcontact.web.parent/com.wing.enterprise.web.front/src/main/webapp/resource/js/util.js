jQuery.prototype.serializeObject=function(){  
    var obj=new Object();  
    $.each(this.serializeArray(),function(index,param){  
        if(!(param.name in obj)){  
            obj[param.name]=param.value;  
        }  
    });  
    return obj;  
};  


//获取页面传值 ?传值的参数
function Utilrequest(paras){    
		var url = location.href;     
		var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");     
		var paraObj = {}     
		for (i=0; j=paraString[i]; i++){     
			paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);     
		}     
		var returnValue = paraObj[paras.toLowerCase()];     
		if(typeof(returnValue)=="undefined"){     
			return "";     
		}else{     
			return returnValue;    
		}  
}
//时钟
function Clock() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}
//时间人性格式化
function changetime(dateTimeStamp){
	var minute = 1000 * 60;
	var hour = minute * 60;
	var day = hour * 24;	 	
	var now = new Date();
	var old;
	var diffValue;
	if(!+[1,]){
		//"ie浏览器";
		dateTimeStampt=dateTimeStamp.replace("T"," ");
		
		old= new Date(Date.parse(dateTimeStamp.replace(/-/g,   "/")));
		diffValue = now.getTime() - (old.getTime()-3600000);
	}else{
		old=new Date(dateTimeStamp);
		diffValue = now.getTime() - old.getTime();
	}
	var dayC =diffValue/day;
	var hourC =diffValue/hour;
	var minC =diffValue/minute;
	 	
	 if(dayC>=3){
		  
		 if(now.getFullYear()==old.getFullYear()){
			 	time= dateTimeStamp.replace("T"," ");
				return time.substring(time.indexOf("-")+1,time.length);
			
			 
		 }else{
			 return dateTimeStamp.replace("T"," ");
		 }
	 }else if(dayC>=2){
		 return "前天 "+dateTimeStamp.split("T")[1];
	 }else if(dayC>=1){
		 return "昨天 "+dateTimeStamp.split("T")[1];
	 }else if(hourC>=1){
	 	return parseInt(hourC) +"小时前";
	 }else if(minC>=1){
	 	return parseInt(minC) +"分钟前";
	 }else{
	 	return Math.floor(diffValue/1000)+"秒前";
	 }	 
}


//重载  setTimeout 方法，提供参数传递, 定时提醒需要修改
/*var __sto = setTimeout;
window.setTimeout = function(callback,timeout,param){
	var args = Array.prototype.slice.call(arguments,2);
	var _cb = function(){
		callback.apply(null,args);
	}
	__sto(_cb,timeout);
}*/

/**
 * 自定义单选
 * @param input	单选元素this
 */
function myRadio(input){
	var $in=$(input);
	var $form=$in.closest("form");
	$("input[radioType='"+$in.attr("radioType")+"']",$form).prop("checked",false);
	$in.prop("checked",true);
}

/**
 * 对form表单需要加密的内容进行加密
 * 
 * @param {} $form form jquery对象
 */
function encryptePassword($form){
	
	var key = RSAUtils.getKeyPair($("#exponent",$form).val(), '', $("#modulus",$form).val());  
	
	$("[encryptTo]",$form).each(function(){
		var enName=$(this).attr("encryptTo");
		var password = RSAUtils.encryptedString(key, encodeURIComponent($(this).val())); 
		if($("input[name='"+enName+"']",$form).length>0){
			$("input[name='"+enName+"']",$form).val(password);
		}else{
			$(this).after('<input type="hidden" name="'+enName+'" value="'+password+'" />');
		}
		
		$(this).removeAttr("name");
	});
	return true;
}

/**
 * 共用提示框
 */
var tiper = (function(){
	var wrapEle = $('<div class="widget-tiper"><div class="widget-tiper-inner"><div class="tiper-head"></div><div class="tiper-body"></div><div class="tiper-foot"></div></div></div>');
	var maskEle = $('<div class="widget-tiper-mask"></div>');
	var init = false;
	return function(title, content, button, callback){
		init || wrapEle.appendTo($('body')) && maskEle.appendTo($('body'));
		wrapEle.addClass('widget-tiper-active');
		maskEle.addClass('widget-tiper-active');
		wrapEle.find('.tiper-head').html(title);
		wrapEle.find('.tiper-body').html(content);
		wrapEle.find('.tiper-foot').empty();
		if($.isArray(button)){
			for(var i = 0; i < button.length; i++){
				$('<span class="tiper-button '+button[i].style+'">'+button[i].text+'</span>').click(function(){
					wrapEle.removeClass('widget-tiper-active');
					maskEle.removeClass('widget-tiper-active');
					$.isFunction(callback) && callback($(this).index());
				}).appendTo(wrapEle.find('.tiper-foot'));
			}
		}
		wrapEle.css({
			'left' : ($(window).width() - wrapEle.width())/2,
			'top' : ($(window).height() - wrapEle.height())/2
		});
		init = true;
	}
})();
var loadingElement = $('<div class="scroll-loading" style="display: none;"><span id="loadingElementContent"></span></div>');
loadingElement.appendTo($('body'));
window.hideloading = function(){
	loadingElement.hide();
}
window.showloading = function(time,message){
	if(message){
		$("#loadingElementContent").html(message);
	}else{
		$("#loadingElementContent").html("数据加载中...");
	}
	loadingElement.show();
	if(time>0){
		setTimeout("hideloading()",time);
	}
}

window.alert = function(message, callback){
	if(typeof(message) == 'string'){
		tiper('提示', message, [{style: 'alert_t', text: '确定'}], callback);
	}else{
		if(message&&message.title){
			tiper('提示', message.title, [{style: 'alert_t', text: '确定'}], message.callback);
		}else{
			tiper('提示', JSON.stringify(message), [{style: 'alert_t', text: '确定'}], callback);
		}
	}
}
window.confirm = function(message, callback){
	tiper('确定', message, [{style: 'cancal_t', text: '取消'},{style: 'define_t', text: '确定'}], callback);
}
/**
 * 带回调的提示框
 */
var alert_back=function(message,callback){
	alert({
		title:message,
		callback:callback
	});
}
/***全部替换11***/
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}
var isEmpty=function(s){
	if(s){
		return false;
	}
	return true;
}

var isMobile=function(phone){
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	if(myreg.test(phone)) 
	{ 
	    return false; 
	} 
	return true;
}

/*
 * 自定义方法
 * 封装ajax，添加加载中效果、统一错误处理、证书等 
 */
$.ajaxSettings_zdy = {
    type: 'POST',
    dataType:'json',
    timeout : 60000,
    showLoading:true
}

function zdy_ajax(options){
	var _options = jQuery.extend($.ajaxSettings_zdy, options);
	var _index = "";
    if(_options.showLoading == true){
    	//_index = layer.load(0,{type:3,shade:[0.8,'#393D49'],scrollbar:false});
    	_index = layer.load(1, {shade: [0.1,'#393D49']});
    }
	var _seccess = _options.success;
	var _error = _options.error;
	var _beforeSend = _options.beforeSend;
	var _complete = _options.complete;
	_options.beforeSend = function(){
		jQuery.isFunction(_beforeSend) && _beforeSend();
	}
	_options.error = function(XMLHttpRequest, textStatus, errorThrown){
		layer.close(_index);
		if(textStatus == 'error' && XMLHttpRequest.status == 0){
			//alert("网络异常，请检查手机网络"+XMLHttpRequest.status);
			layer.msg("网络异常，请检查手机网络");
			//$(".page_loading").hide();
		}else if(textStatus == 'timeout'){
			layer.msg("网络不给力，请稍后重试");
		}
		jQuery.isFunction(_error) && _error();
	}
	_options.success = function(data,textStatus, jqXHR){
		var contentType = jqXHR.getResponseHeader("Content-Type");
		if(contentType.indexOf("json")>-1){
			if(data.code == 0){
				jQuery.isFunction(_seccess) && _seccess(data.dataobj,data);
			}else{
				if(data.code == 302){
					gotologin();
				}else{
					layer.msg(data.msg);
				}
			}
		}else{
			var timeout = jqXHR.getResponseHeader("timeout");
			if(1==timeout&&false){
				gotologin();
			}else{
				jQuery.isFunction(_seccess) && _seccess(data,textStatus,jqXHR);
			}
		}
	}
	_options.complete = function(){
	    if(_options.showLoading == true){
			layer.close(_index);
	    }
		jQuery.isFunction(_complete) && _complete();
	}
	jQuery.ajax(_options);
}

function getUrlParam1(url,name){
	if(url.match(new RegExp("(^|&|\\?)"+ name +"=([^&]*)(&|$)"))){
		return RegExp.$2.split('#')[0];
	}
}

function number_format(number, decimals, dec_point, thousands_sep) {
    /*
    * 参数说明：
    * number：要格式化的数字
    * decimals：保留几位小数
    * dec_point：小数点符号
    * thousands_sep：千分位符号
    * */
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.ceil(n * k) / k;
        };
 
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    var re = /(-?\d+)(\d{3})/;
    while (re.test(s[0])) {
        s[0] = s[0].replace(re, "$1" + sep + "$2");
    }
 
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}
