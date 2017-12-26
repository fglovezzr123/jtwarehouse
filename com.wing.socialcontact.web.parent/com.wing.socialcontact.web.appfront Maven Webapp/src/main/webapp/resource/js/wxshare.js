
var _link ="" ;
var _title ="";
var _imgUrl ="";//默认
var _type ="3"; // 1 活动   2 资讯

var wx_flag = false;


var descri="";


/*$(function(){
	var title="天九共享网";
	var imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
	var link = window.location.href;
	var summary = "海量企业家共聚共享共赢的超O2O商务社交平台，促进百万企业家强强联手，抱团发展。";
	var type ="0";
	wxsharefun(link,title,imgUrl,type,summary);
});*/

function jhyshare(){
	//获取默认配置
	zdy_ajax({
		url : _path+'/m/news/configdetail.do',
		type : 'post',
		dataType : 'json',
		showLoading:false,
		data : {
			type:1
		},
		success : function(data,result) {
			descri = data.news.content;
			_link = data.news.url;
			_title =data.news.newsTitle;
			_imgUrl = data.ossurl+data.news.imagePath;
			getSignature();
		}
	});
}

function wxsharefun(linkurl,title,imgUrl,type,summary){
	console.log(linkurl+"=="+title+"=="+imgUrl+"=="+type+"=="+summary);
	if(!isEmpty(linkurl)){
		_link=linkurl;
	}
	if(!isEmpty(title)){
		_title=title;
	}
	if(!isEmpty(imgUrl)){
		_imgUrl=imgUrl;
	}
	if(!isEmpty(type)){
		_type=type;
	}
	if(!isEmpty(summary)){
		descri=summary;
	}else{
		descri =title;
	}
	getSignature();
}


function  getSignature(){
	if(is_weixn()){
		wx_flag=true;
		var _url = self.location.href;
		if (_url.indexOf("#") != -1) {
			_url = _url.substring(0, _url.indexOf("#"));
		}
		//获取签名
		zdy_ajax({
			url :  _path+'/m/sys/getSignature.do',
			type : 'post',
			dataType : 'json',
			showLoading:false,
			data : {
				url : _url
			},
			success : function(result) {
				//alert(_link+"----"+_title+"----"+_imgUrl);
				if (!isEmpty(result) && !isEmpty(result.signature)) {
					init_wx(result,_link,_title,_imgUrl);
				}
			}
		});
	}
}


var init_wx = function(obj,_link1,_title1,_imgUrl1) {
	var appid = obj.appId;
	wx.config({
		debug : false,
		appId : appid,
		//appId: 'wx34c5f34afae4c27b',
		timestamp : obj.timestamp,
		nonceStr : obj.nonceStr,
		signature : obj.signature,
		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
				'onMenuShareAppMessage', 'hideMenuItems',
				'hideAllNonBaseMenuItem', 'showMenuItems',
				'showAllNonBaseMenuItem' ]
	});
	
	wx.ready(function() {
		wx.checkJsApi({
			jsApiList : [ 'onMenuShareTimeline',
					'onMenuShareAppMessage' ],
			success : function(res) {
				if (res.checkResult.onMenuShareTimeline == false) {
					alert("您的微信版本过低，不支持分享功能");
				} else {
					send_fx(_link1,_title1,_imgUrl1);
				}
				if (res.checkResult.onMenuShareAppMessage == false) {
					alert("您的微信版本过低，不支持分享功能");
				} else {
					send_fx_f(_link1,_title1,_imgUrl1);
				}
			},
			fail : function(res) {
				alert(res.errMsg);
			}
		});
		//隐藏按钮
		hideWxMenus();
		showWxMenus();
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});

	wx.error(function(res) {
		alert(res.errMsg);
		// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});
}


var hideWxMenus = function() {
	//wx.hideAllNonBaseMenuItem();
	wx.hideMenuItems({
		menuList : [ 'menuItem:share:appMessage',
				'menuItem:share:timeline', 'menuItem:share:qq',
				'menuItem:share:QZone', 'menuItem:share:weiboApp',
				'menuItem:share:facebook', 'menuItem:share:QZone' ]
	// 要隐藏的菜单项，所有menu项见附录3
	});
}

var showWxMenus = function() {
	wx.showMenuItems({
		menuList : [ 'menuItem:share:appMessage',
				'menuItem:share:timeline' ]
	});
}




var send_fx = function(_link1,_title1,_imgUrl1) {
	wx.onMenuShareTimeline({
		title : "天九共享网", // 分享标题
		link : _link1, // 分享链接
		imgUrl : _imgUrl1, // 分享图标
		success : function(res) {
			layer.msg("已分享");
				if(!isEmpty(_type)){
					var _taskId = "";
					if(_type=='1'){//活动分享 task_0008
						_taskId='task_0008';
					}else if(_type=='2'){//资讯分享 task_0016
						_taskId='task_0016';
					}
					if(!isEmpty(_taskId)){
						zdy_ajax({
							url : '${path}/m/my/addLntAndEmp.do',
							type : 'post',
							dataType : 'json',
							data : {
								taskId : _taskId
							},
							success : function(data, res) {
								if (res.dataobj) {
									location.reload();
								} 
							}
						});
					}
				}else{
					location.reload();
				}
		},
		cancel : function(res) {
			alert("分享取消!!!");
		},
		fail : function(res) {
			alert('wx.onMenuShareTimeline:fail: '
					+ JSON.stringify(res));
		}
	});
	//setTimeout('layer.msg("请点击屏幕右上角菜单中的“分享到朋友圈”",10,1);',1000);
}

var send_fx_f = function(_link1,_title1,_imgUrl1) {
	var _desc = descri;
	/*if(descri!=""){
		_desc=descri;
	}*/
	wx.onMenuShareAppMessage({
		title : "天九共享网", // 分享标题
		desc : _desc, // 分享描述
		link : _link1, // 分享链接
		imgUrl : _imgUrl1, // 分享图标
		type : 'link', // 分享类型,music、video或link，不填默认为link
		success : function() {
			// 用户确认分享后执行的回调函数
				if(!isEmpty(_type)){
					var _taskId = "";
					if(_type=='1'){//活动分享 task_0008
						_taskId='task_0008';
					}else if(_type=='2'){//资讯分享 task_0016
						_taskId='task_0016';
					}
					if(!isEmpty(_taskId)){
						zdy_ajax({
							url : '${path}/m/my/addLntAndEmp.do',
							type : 'post',
							dataType : 'json',
							data : {
								taskId : _taskId
							},
							success : function(data, res) {
								if (res.dataobj) {
									$(".com-backdrop").click();
								} 
							}
						});
					}
				}else{
					$(".com-backdrop").click();
				}
				
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
			alert("分享取消!!!");
		}
	});
}