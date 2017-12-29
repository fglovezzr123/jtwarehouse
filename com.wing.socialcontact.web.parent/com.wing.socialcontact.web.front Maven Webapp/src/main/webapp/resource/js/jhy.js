//栏目模板定义js
var lmmb=[
	{"id":"1","name":"栏目1","code":"lm1","view_fun":"lm1_show"},
	{"id":"2","name":"栏目2","code":"lm2","view_fun":"lm2_show"},
	{"id":"3","name":"栏目3","code":"lm3","view_fun":"lm3_show"},
	{"id":"4","name":"栏目4","code":"lm4","view_fun":"lm4_show"},
	{"id":"5","name":"栏目5","code":"lm5","view_fun":"lm5_show"},
	{"id":"6","name":"栏目6","code":"lm6","view_fun":"lm6_show"},
	{"id":"7","name":"栏目7","code":"lm7","view_fun":"lm7_show"}
];

//栏目渲染js
function lm1_show(contentid,data,callback){
	var str="";
	str+='<div class="lm1">';
	str+='<div class="lm1-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	str+='<div class="lm1-box">';
	$.each(data.elementList,function(i,element){
		str+='<div class="item" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',1);">';
		str+='<div class="img1">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='</div>';
		str+='<p>'+element.titleOne+'</p>';
		str+='</div>';
	});
	str+='</div>';
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(1);
}
function lm2_show(contentid,data,callback){
	var str="";
	str+='<div class="lm2">';
	str+='<div class="lm2-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	str+='<div class="lm2-box">';
	$.each(data.elementList,function(i,element){
		str+='<div class="img1" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',2);">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='<div class="lm2-position">';
		str+='<p>'+element.titleOne+'</p>';
		str+='<p>'+element.titleTwo+'</p>';
		str+='</div>';
		str+='</div>';
	});
	str+='</div>';
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(2);
}
function lm3_show(contentid,data,callback){
	var str="";
	str+='<div class="lm3">';
	str+='<div class="lm3-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	$.each(data.elementList,function(i,element){
		str+='<div class="img-text" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',3);">';
		str+='<div class="lm3-img">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='</div>';
		str+='<div class="lm3-tx">';
		str+='<h2>'+element.titleOne+'</h2>';
		str+='<p>'+element.titleTwo+'</p>';
		str+='</div>';
		str+='</div>';
	});
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(3);
}
function lm4_show(contentid,data,callback){
	var str="";
	str+='<div class="lm4">';
	str+='<div class="lm4-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	str+='<div class="swiper-container1">';
	str+='<ul class="swiper-wrapper">';
	$.each(data.elementList,function(i,element){
		str+='<li class="swiper-slide" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',4);">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='<p>'+element.titleOne+'</p>';
		str+='<p>'+element.titleTwo+'</p>';
		str+='</li>';
	});
	str+='</ul>';
	str+='</div>';
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(4);
}
function lm5_show(contentid,data,callback){
	var str="";
	str+='<div class="lm5">';
	str+='<div class="lm3-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	$.each(data.elementList,function(i,element){
		str+='<div class="img-text" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',5);">';
		str+='<div class="lm3-img">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='</div>';
		str+='<div class="lm3-tx">';
		str+='<h2>'+element.titleOne+'</h2>';
		str+='<p>'+element.titleTwo+'</p>';
		str+='</div>';
		str+='</div>';
	});
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(5);
}
function lm6_show(contentid,data,callback){
	var str="";
	str+='<div class="lm6">';
	str+='<div class="lm6-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	str+='<div class="lm6-cont">';
	$.each(data.elementList,function(i,element){
		str+='<div class="item" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',6);">';
		str+='<div class="lm6-img">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='</div>';
		str+='<p>'+element.titleOne+'</p>';
		str+='<p>'+element.titleTwo+'</p>';
		str+='</div>';
	});
	str+='</div>';
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(6);
}
function lm7_show(contentid,data,callback){
	var str="";
	str+='<div class="lm7">';
	str+='<div class="lm6-header">';
	str+='<span>'+data.columnName+'</span>';
	if(!isEmpty(data.moreUrl)){
		str+='<a href="javascript:moreClick(\''+data.moreUrl+'\');">更多</a>';
	}
	str+='</div>';
	str+='<div class="lm7-cont">';
	$.each(data.elementList,function(i,element){
		str+='<div class="item" onclick="elementClick(this,\''+element.detailUrl+'\',\''+element.contentId+'\',7);">';
		str+='<div class="lm7-img">';
		str+='<img src="'+page_ossurl+element.imgUrl+'" width="100%" height="100%"/>';
		str+='</div>';
		str+='<p>'+element.titleOne+'</p>';
		str+='</div>';
	});
	str+='</div>';
	str+='</div>';
	$("#"+contentid).append(str);
	$.isFunction(callback) && callback(7);
}

function getMbByShowStyle(showStyle){
	var mb=null;
	$.each(lmmb,function(i,n){
		if(n.id == showStyle){
			mb = n;
			return false
		}
	});
	return mb;
}

function load_lm(data){
	//lm_div
	var lm_objs=null;
	if(!isEmpty(data)){
		lm_objs = eval(data);
		if(lm_objs.length == 0){
			//清空页面数据
			$("#lm_div").html("");
			return;
		}
		var layer_index=layer.load(1, {shade: [0.1,'#393D49']});
		$.each(lm_objs,function(i,obj){
			if(i == 0){
				//清空页面数据
				$("#lm_div").html("");
			}
			var showStyle=obj.showStyle;
			var mb=getMbByShowStyle(showStyle);
			$show_fun=eval('(' + mb.view_fun + ')');
			if($.isFunction($show_fun)){
				$show_fun("lm_div",obj,function(t){
					if(t == 4){
						 var swiper = new Swiper('.swiper-container1', {
							 slidesPerView: 'auto',
							 paginationClickable: true,
							 spaceBetween: 15
					    });
					}
				});
			}
		});
		layer.close(layer_index);
	}
}

function elementClick(obj,url,contentId,type){
	if(!isEmpty(viewFlag)){
		 return;
	}
	if(isEmpty(url)){
		 return;
	}
	if("zdy" != contentId){
		if(url.indexOf('http://') == -1){
			url = _path + url; 
		}
		if(!isEmpty(contentId)){
			url = url.replace(":id",contentId);
		}
	}
	self.location.href=url;
}

function moreClick(url){
	if(!isEmpty(viewFlag)){
		 return;
	}
	if(isEmpty(url)){
		 return;
	}
	self.location.href=url;
}