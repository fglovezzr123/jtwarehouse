<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
<title id="title">个人主页</title>
<link rel="stylesheet" href="${path}/resource/css/personal-Home.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/index.css?v=${sversion}" />
<style>
  .changePoster{
        position:absolute;
        top:3rem;
        right:0.3rem;
        padding-left:0.2rem;
        padding-right:0.2rem;
        color:white;
        background:black;
        opacity:0.5;
        border-radius:10%;
        padding-bottom:0.05rem;
    }
     .fb_yyt_m{
     font-size: .28rem;
    float: left;
    margin-left: 0.28em;
    height: 0.36rem;
    line-height: 0.36rem;
    display: inline-block;
    border-radius:0.06rem;
   }
   .fb_yyt_tu{
          border-radius:0.06rem;  
   }
   
   /*文章部分*/
     		.item{
     			padding: 0.15rem .2rem;
     			background: #F2F3F4;
     			display: flex;
     		}
     		.item .it-img{
     			/*width: 2.5rem;*/
     			width: 36.23%;
     			/* height: 1.5rem; */
     		}
     		.item .it-img img{
     			width: 100%;
     		}
     		.item .it-cont{
     			/*width: 4.4rem;*/
     			width: 63.77%;
     			font-size: .26rem;
     			padding-left: .15rem;
     			position: relative;
     			box-sizing: border-box;
     		}
     		.item .it-cont h3{
     			font-size: .25rem;
     			font-weight: normal;
     			
			    word-break: break-all;
			     text-overflow: ellipsis;
			     display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
			     -webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
			     -webkit-line-clamp: 2; /** 显示的行数 **/
			     overflow: hidden;  /** 隐藏超出的内容 **/
     		}
     		.item .it-cont .bot{
     			width: 100%;
     			display: flex;
     			justify-content: space-between;
     			font-size: .24rem;
     			position: absolute;
     			/* bottom: 0; */
     			left: 0;
     			padding-left: .15rem;
     			box-sizing: border-box;
     		}
     		.item .it-cont .bot span{
     			line-height: 2.5;
     		}
</style>
</head>


<body>
	<div class="wrapper" style="padding-bottom: 0rem;">
		<c:choose>
			<c:when test="${empty tjyuser.homepagePic }">
				<div onclick="bg_add();" id='background' style="background:url(${path}/resource/img/images/background.jpg) no-repeat center;width:100%;height:3.85rem;color:white;font-size:0.35rem;line-height:0.88rem;background-size:cover; "></div>
			</c:when>
			<c:otherwise>
				<div onclick="bg_add();" id='background' style="background:url(${ossurl}${tjyuser.homepagePic}) no-repeat center;width:100%;height:3.85rem;color:white;font-size:0.35rem;line-height:0.88rem;background-size:100%; "></div>
			</c:otherwise>
		</c:choose>
		<div class="changePoster" onclick="bg_add();">更换封面</div>
		<div class="">
			<c:choose>
				<c:when test="${empty tjyuser.headPortrait }">
			    	<div id="user-icon"  style="background:url(${path}/resource/img/icons/weixinHeader.jpg) no-repeat center;height:1.54rem;width:1.54rem; margin:0 auto; background-size:100%;border-radius:50%;border:0.07rem solid white;margin-top:-0.77rem;"></div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${fn:indexOf(tjyuser.headPortrait,'http') ne -1 }">
							<div id="user-icon"  style="background:url(${tjyuser.headPortrait}) no-repeat center;height:1.54rem;width:1.54rem; margin:0 auto; background-size:100%;border-radius:50%;border:0.07rem solid white;margin-top:-0.77rem;"></div>
						</c:when>
						<c:otherwise>
							<div id="user-icon"  style="background:url(${tjyuser.headPortrait}) no-repeat center;height:1.54rem;width:1.54rem; margin:0 auto; background-size:100%;border-radius:50%;border:0.07rem solid white;margin-top:-0.77rem;"></div>
<%-- 							<div id="user-icon"  style="background:url(${path}${tjyuser.headPortrait}) no-repeat center;height:1.54rem;width:1.54rem; margin:0 auto; background-size:100%;border-radius:50%;border:0.07rem solid white;margin-top:-0.77rem;"></div> --%>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<div id="focus">
				<div class="names">
				 <span>${tjyuser.nickname}</span>
				  <c:choose>
						<c:when test="${tjyuser.honorFlag == 'honor_001' }">
						<img src="${path}/resource/img/icons/tjjr.png"  class="tj" style="margin-left: .1rem;vertical-align: middle;"/>
						</c:when>
						<c:when test="${tjyuser.honorFlag == 'honor_002' }">
							<img src="${path}/resource/img/icons/tjyq.png"  class="tj" style="margin-left: .1rem;vertical-align: middle;" />
						</c:when>
						<c:when test="${tjyuser.honorFlag == 'honor_003'}">
							<img src="${path}/resource/img/icons/tjhb.png"  class="tj" style="margin-left: .1rem;vertical-align: middle;"/>
						</c:when>
						<c:when test="${tjyuser.honorFlag == 'honor_004'}">
							<img src="${path}/resource/img/icons/tjfws.png"  class="tj" style="margin-left: .1rem;vertical-align: middle;"/>
						</c:when>
					</c:choose>	 
				</div>
				<c:if test="${tjyuser.reconStatus==2}">
					<div style="margin-bottom:0.2rem">${tjyuser.job}/${tjyuser.comName}</div>
				</c:if>
				<!-- 	<div id="focusIt" class="active_A">+ 关注</div> -->
			</div>
		</div>

		<div id="numbers" class="whites">

			<ul>
				<li class="active_A" style="border:none">关 注
					<div>
						<b id="guanzhu">100</b>
					</div>
				</li>
				<li class="active_A">好 友
					<div>
						<b id="haoyou">100</b>
					</div>
				</li>
				<li class="active_A">粉 丝
					<div>
						<b id="fensi">100</b>
					</div>
				</li>
				<li class="active_A" >访问量
					<div>
						<b><fmt:formatNumber type="number"
								value="${tjyuser.visitQuantity} " maxFractionDigits="0" /></b>
					</div>
				</li>
			</ul>
		</div>


		<div id="tabBar">
			<div>
				<div class="current" id="tabBar1">介绍</div>
			</div>

			<div>
				<div id="tabBar2" >动态</div>
			</div>
			<br class="clear" />
		</div>

		<div id="jieshao">
			<div class="text-box whites">
				<div class="text-head">
					<div class="text-name">您的公司</div>
					<div class="text-name2" style="width:70%">${tjyuser.comName}</div>
				</div>
				<br style="clear:both" />
			</div>

			<div class="text-box margins whites non-borderBtm">
				<div class="text-head">
					<div class="text-name">公司简介</div>
				</div>
				<br style="clear:both" />
			</div>

			<div class="presence border-bottom whites" style="padding-bottom:0.13rem">${tjyuser.comProfile}</div>
			<!--   <div class="infor presence border-bottom whites">
	             	<ul>
	             		<li>网&nbsp&nbsp&nbsp&nbsp&nbsp址:</li>
	             		<li>www.baidu.com</li>
	             	</ul>
	             	<br style="clear:both"/>
	             </div> 

			<div class="infor presence active_A border-bottom whites">
				<ul>
					<li>所在城区&nbsp;:${tjyuser.province}&nbsp;${tjyuser.city}&nbsp;${tjyuser.county}</li>
				</ul>
				<br style="clear:both" />
			</div>

			<div class="infor presence border-bottom whites"
				style="padding-bottom:0.9rem">
				<ul>
					<li>详细地址&nbsp;:</li>
					<li>${tjyuser.address}</li>
				</ul>
				<br style="clear:both" />
			</div>
-->
			<div class="text-box  margins whites non-borderBtm">
				<div class="text-head">
					<div class="text-name">个人简介</div>
				</div>
				<br style="clear:both" />
			</div>

			<div class="presence border-bottom paddingBtm whites">${tjyuser.userProfile}</div>

			<div class="myTags whites">
				<div class="text-box  whites non-borderBtm" >
					<div class="text-head">
						<div class="text-name">朋友印象</div>
					    <div class="con-r active_A" id="con-r" onclick="personal_impress_edit(0);">
							<img src="${path}/resource/img/icons/edition.png" onclick="personal_impress_edit(0);"/> 编辑
						</div> 
					</div>
					<br style="clear:both" />
				</div>
				<div class="mytag1" >
					<div id="type0">
					    <c:forEach  var="vf0" items="${userFriendimpresss0}" >
							<span class="tags">${vf0.list_value}(${vf0.impresscount})</span> 
						</c:forEach>
						<c:if test="${empty userFriendimpresss0}">
						        暂未获得任何印象
						</c:if>
					</div>
					<!-- <div class="active_A" id="ADD1" onclick="personal_impress(0);">+</div> -->
					<br style="clear:both" />
				</div>
				<div class="grayline"></div>
				<div class="text-box  whites non-borderBtm">
					<div class="text-head">
						<div class="text-name">我需要</div>
					</div>
					<br style="clear:both" />
				</div>
				<div class="mytag1">
					<div id="type1">
						<c:forEach  var="vf1" items="${userFriendimpresss1}" >
							<span class="tags">${vf1.listValue}</span> 
						</c:forEach>
					</div>	
					<div class="active_A" id="ADD1" onclick="personal_impress(1);">+</div>
					<br style="clear:both" />
				</div>
				<div class="grayline"></div>
				<div class="text-box whites non-borderBtm">
					<div class="text-head">
						<div class="text-name">我能提供</div>
					</div>
					<br style="clear:both" />
				</div>
				<div class="mytag1">
					<div id="type2">
					  	<c:forEach  var="vf2" items="${userFriendimpresss2}" >
							<span class="tags">${vf2.listValue}</span> 
						</c:forEach>
					</div>
					<div class="active_A" id="ADD1" onclick="personal_impress(2);">+</div>
					<br style="clear:both" />
				</div>
				<div class="grayline"></div>
			</div>
		</div>
		<div id="dongtai" class="dc" style="display:none;">

		</div>
		<div class="loadingbox" id="loadingbox" style="display:none;">
			<div class="page_loading" style="display:block;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
	</div>
	<script type="text/javascript">
	    var userId="${tjyuser.id}";
	    var curPageNum=1;
	    var flag = false;
		$(document).ready(function() {
			//滚动加载
			$(window).scroll(function(){
			      var scrollTop=$(this).scrollTop();
			       var scrollHeight = $(document).height();
			           var windowHeight = $(this).height();
			           if (scrollTop+windowHeight==scrollHeight) {
			        	    if(flag){
			        	     	curPageNum++;
					           	showDynamicInfo(); 
			        	    }
			           };
			 });

			//语音相关
			var nowPlayMedia={};
			var configdata = {};
			if(is_weixn()){
				zdy_ajax({
					url: '${path}/m/sys/getSignature.do',
					async: false,
					data:{
						url:window.location.href
					},
					success: function(data,output){
						if(output.code == 0){
							configdata = output.dataobj;
						}
					},
					error:function(e){
					}
				});
				
				
				var jsApiList = [];
				jsApiList.push("startRecord");
				jsApiList.push("stopRecord");
				jsApiList.push("onVoiceRecordEnd");
				jsApiList.push("playVoice");
				jsApiList.push("pauseVoice");
				jsApiList.push("stopVoice");
				jsApiList.push("onVoicePlayEnd");
				jsApiList.push("uploadVoice");
				jsApiList.push("downloadVoice");
				
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: localStorage.appid, // 必填，企业号的唯一标识，此处填写企业号corpid
				    timestamp:configdata.timestamp , // 必填，生成签名的时间戳
				    nonceStr: configdata.nonceStr, // 必填，生成签名的随机串
				    signature: configdata.signature,// 必填，签名，见附录1
				    jsApiList: jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){
				});
				
			}
			
			//关注个数
			zdy_ajax({
				url : "${path}/im/m/selMyFollowCount.do",
				data : {userId:userId},
				success : function(data,res) {
					if (res.code == 0) {
						$("#guanzhu").html(res.dataobj);
					} 
				}
			});
			//粉丝个数
			zdy_ajax({
				url : "${path}/im/m/selMyFansCount.do",
				data : {userId:userId},
				success : function(data,res) {
					if (res.code == 0) {
						///alert(res.dataobj);
						$("#fensi").html(res.dataobj);
					} 
				}
			});
			//好友个数
			zdy_ajax({
				url : "${path}/im/m/selMyFriendCount.do",
				data : {userId:userId},
				success : function(data,res) {
					if (res.code == 0) {
						//alert(res.dataobj);
						$("#haoyou").html(res.dataobj);
					}
				}
			});

		});

		var bg_add = function() {

	    	self.location.href='${path}/m/my/background_add.do';
		}

		
		var show_zzc = function(t) {
			$("body").bind("touchmove", function(event) {
				event.preventDefault();
			});
			if (t && t == 1) {
				scrollTop = $(document).scrollTop();
				$(document).scrollTop(0);
				$(window).bind("scroll", function() {
					$(document).scrollTop(0);
				});
			}

		}

		var hide_zzc = function(t) {
			$("body").unbind("touchmove");
			if (t && t == 1) {
				$(window).unbind("scroll");
				$(document).scrollTop(scrollTop);
			}

		}

		var reload = function() {
			self.location.href = self.location.href;
		}

		$("#tabBar1").click(function() {
			$("#tabBar1").addClass("current");
			$("#tabBar2").removeClass("current");
			$("#jieshao").show();
			$("#dongtai").hide();
			$("#loadingbox").hide();
			flag = false;
		});
		$("#tabBar2").click(function() {
			$("#tabBar2").addClass("current");
			$("#tabBar1").removeClass("current");
			$("#jieshao").hide();
			$("#dongtai").show();
			$("#loadingbox").show();
			//个人动态
			$("#dongtai").empty();
			curPageNum=1;
			showDynamicInfo(); 
			flag = true;
		});
		
		var personal_impress = function(type) {
			self.location.href=_path+ "/m/my/personal_impress2.do?type=" + type;
		}
		
		var personal_impress_edit = function(type) {
			
			self.location.href=_path+ "/m/my/pyyx_edit.do?type=" + type;
			
		}
		
		var pyyx_add=function(t){
			if(t == 1){
				self.location.href="${path}/m/my/pyyx_add.do";
			}
		}
		
		var pyyx_edit=function(t){
			if(t == 1){
				self.location.href="${path}/m/my/pyyx_edit.do";
			}
		}
		
		//查看他人主页
		var open_user_center=function(userId){
			self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
		}
		

        
		/* function showimgs(id,img){	
			if(is_weixn()){
				var imgs = [];
				$("#img_box_"+id+" img").each(function(){
					imgs.push($(this).data("imgurl"));
				});
				wx.previewImage({
				    current: img, // 当前显示图片的http链接
				    urls: imgs // 需要预览的图片http链接列表
				});
			}
		} */
		function showimgs(id,img,dy_type,dy_url){	
			if(dy_type==1){
				window.location.href=dy_url;
			}else{
				if(is_weixn()){
					var imgs = [];
					$("#img_box_"+id+" img").each(function(){
						imgs.push($(this).data("imgurl"));
					});
					wx.previewImage({
					    current: img, // 当前显示图片的http链接
					    urls: imgs // 需要预览的图片http链接列表
					});
				}
			}
		}
		var showDynamicInfo = function(){
			var url = "${path}/m/dynamic/selectMyDynamicList.do";
			
			zdy_ajax({
				url: url,
				data:{
					pageNum:curPageNum,
					pageSize:15
				},
			    showLoading:false,
				success: function(dataobj,res){
					var data = dataobj.dynamicList;
					if(res.code == 0){
						//console.log(data);
						if(data.length==0 || data.length<10){
							$(".page_loading").hide();
							$(".page_nodata").show();
						}
						$.each(data, function(i, n){
							var dynamic = data[i];
							/* var honor_flag = dynamic.dynamicMap.honor_flag,tjIdImg="";
						
							if(honor_flag=="honor_001"){
								tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
							}else if(honor_flag=="honor_002"){
								tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
							}else if(honor_flag=="honor_003"){
								tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
							}else if(honor_flag=="honor_004"){
								tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
							} */
							var str = '<div class="msgBox" id="msgBox_'+dynamic.dynamicMap.id+'">'+
							'<div class="msg-l" style="height:0.8rem;width:0.8rem;;background:url('+dynamic.dynamicMap.head_portrait+') no-repeat center;background-size:100% 100%;border-radius:50%;" id="'+dynamic.dynamicMap.user_id+'">'+
							   '</div>'+
							   '<div class="msg-r">'+
									'<div class="t">'+
										'<h2>'+dynamic.dynamicMap.nickname+'</h2>'+
										/* tjIdImg+ */
										'<span class="time">'+dynamic.dynamicMap.minute_flag+'</span>'+
									'</div>'+
									'<p class="ph">'+dynamic.dynamicMap.job_name+' /'+dynamic.dynamicMap.com_name+'</p>'+
									'<p onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'") class="p2">'+dynamic.dynamicMap.dy_content+'</p>';
									if(dynamic.dynamicMap.articleid!= undefined && dynamic.dynamicMap.articleid!= "undefined" && dynamic.dynamicMap.articleid!= null && dynamic.dynamicMap.articleid!= ""){
										str += '<div   onclick=jumparticle("'+dynamic.dynamicMap.articleid+'"); class="item"><div class="it-img"><img src="'+_oss_url+dynamic.dynamicMap.aimgpath+'"/></div><div class="it-cont">'+
										'<h3>'+dynamic.dynamicMap.atitle+'</h3><div class="bot"><span>'+formatDate(new Date(dynamic.dynamicMap.adate))+'</span><span>摘自:'+dynamic.dynamicMap.aclassname+'</span></div></div></div>';
									}
									str +='<div id="img_box_'+dynamic.dynamicMap.id+'">';
									if(dynamic.picList.length != 0 && dynamic.picList.length != 1){
										str += '<div class="imgBox_3">';
										$.each(dynamic.picList, function(k, n){
											if(k%3 == 0 && k !=0){
												str += '</div>';
												str += '<div class="imgBox_3">';
											}
											//str +='<img src="'+dataobj.ossurl+dynamic.picList[k].picUrl+'" />';
											var img200 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[k].picUrl,'YS200200');
											var img640 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[k].picUrl,'YSMAX1024');
											str +='<div class="insideIMGbox"  onclick=showimgs("'+dynamic.dynamicMap.id+'","'+img640+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'")><span></span><img class="lazy"  data-original="'+img200+'" data-imgurl="'+img640+'" /></div>';
										});
										str += '</div>';
									}
									if(dynamic.picList.length == 1){
										/* str += '<div class="imgBox_1">'+
										'<img src="'+dataobj.ossurl+dynamic.picList[0].picUrl+'" />'+
									'</div>'; */
										var img640 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[0].picUrl,'YSMAX1024');
										str += '<div class="imgBox_1" >'+
										'<img class="lazy" data-original="'+img640+'" data-imgurl="'+img640+'"  onclick=showimgs("'+dynamic.dynamicMap.id+'","'+img640+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'") />'+
									    '</div>';
									}
									str+='</div>';
									
									if(dynamic.dynamicMap.media_id != '' && dynamic.dynamicMap.media_seconds != '' && dynamic.dynamicMap.media_seconds > 0){
										var media_length = dynamic.dynamicMap.media_seconds;
										var length_media = media_length > 10 ? media_length:10;
										str +='<div class="fb_yyt" id="fb_yyt" onclick=playMedia("'+_oss_url+dynamic.dynamicMap.media_id+'","'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.media_price+'","'+dynamic.dynamicMap.overtime_flag+'",this)>'+
			                            '<span class="fb_yyt_tu" style="width:'+length_media+'%"><img src="${path}/resource/img/images/yy01.png"></span>'+
			                            '<span class="fb_yyt_m">'+media_length+'\'</span>'+
			                            '<div class="clearfix"></div>';
			                            
			                            if(dynamic.dynamicMap.overtime_flag == 0){
			                            	 if(dynamic.dynamicMap.media_price > 0){
			 	                            	if(dynamic.dynamicMap.pay_flag == 1){
			 	                            		 str+='<div class="t">'+
			 			                            	'<h2></h2>'+
			 											'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音已付费</span>'+
			 										'</div>';
			 	                            	}else if(dynamic.dynamicMap.pay_flag == 0){
			 	                            		str+='<div class="t">'+
			 		                            	'<h2></h2>'+
			 										'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
			 									'</div>';
			 	                            	}else if(dynamic.dynamicMap.pay_flag == -1){
			 	                            		str+='<div class="t" id="'+dynamic.dynamicMap.id+'1">'+
			 		                            	'<h2></h2>'+
			 										'<span class="time">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
			 									'</div>';
			 	                            	}
			 	                            }else{
			 	                            	 str+='<div class="t">'+
			 		                            	'<h2></h2>'+
			 										'<span class="time">免费</span>'+
			 									'</div>';
			 	                            }
			                            }else{
			                            	
			                            	if(dynamic.dynamicMap.media_id.indexOf(".mp3") == -1){
				                            	str+='<div class="t" id="'+dynamic.dynamicMap.id+'1">'+
					                            	'<h2></h2>'+
													'<span class="time">该语音已经超过3天，不能收听！</span>'+
												'</div>';
			                            	}else{
			                            		
			                            		if(dynamic.dynamicMap.media_price > 0){
				 	                            	if(dynamic.dynamicMap.pay_flag == 1){
				 	                            		 str+='<div class="t">'+
				 			                            	'<h2></h2>'+
				 											'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音已付费</span>'+
				 										'</div>';
				 	                            	}else if(dynamic.dynamicMap.pay_flag == 0){
				 	                            		str+='<div class="t">'+
				 		                            	'<h2></h2>'+
				 										'<span class="time" id="'+dynamic.dynamicMap.id+'1">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
				 									'</div>';
				 	                            	}else if(dynamic.dynamicMap.pay_flag == -1){
				 	                            		str+='<div class="t" id="'+dynamic.dynamicMap.id+'1">'+
				 		                            	'<h2></h2>'+
				 										'<span class="time">该语音需要付'+dynamic.dynamicMap.media_price+'J币收听</span>'+
				 									'</div>';
				 	                            	}
				 	                            }else{
				 	                            	 str+='<div class="t">'+
				 		                            	'<h2></h2>'+
				 										'<span class="time">免费</span>'+
				 									'</div>';
				 	                            }
			                            	}
			                            }
			                       		str+= '</div>';
									}
				                     
									var clicked='iconfonthead';
				                    if(dynamic.dynamicMap.like_flag){  
				                    	clicked='iconfont-badhead'
				                    };
									str+='<div class="tubiao">'+
										'<div class="tb">'+
											'<i class=" iconfont1" onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'")></i>'+
											'<span>'+dynamic.commonCount+'</span>'+
										'</div>'+
										'<div class="tb">'+
										    '<i class="iconfont  '+clicked+'" goodid="'+dynamic.dynamicMap.id+'" onclick=insertLick($(this).attr("goodid"),$(this))>'+dynamic.lickCount+'</i>'+
											//'<i class="iconfont '+clicked+'" onclick=insertLick("'+dynamic.dynamicMap.id+'")></i>'+
											//'<span id="lick_'+dynamic.dynamicMap.id+'">'+dynamic.lickCount+'</span>'+
										'</div>'+
										'<div class="tb mri">'+
										'<i class=" iconfont5" onclick=delDynamic("msgBox_'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.id+'")></i>'+
										'</div>'+
									'</div>'+
									'<div class="pl">';
									if(dynamic.commonCount !=0){
										str+='<p class="c"><b>'+dynamic.commonUser.nickname+'</b>：<span>'+dynamic.pcomment.cmeDesc+'</span></p>';
										if(dynamic.subCommonCount != 0){
											str+='<p class="c"><b>'+dynamic.subCommonUser.nickname+'&nbsp;</b>回复<b>&nbsp;'+dynamic.commonUser.nickname+'</b>:<span>'+dynamic.subcomment.cmeDesc+'</span></p>';
										}
									}
									str+='<p class="more" onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'")>查看所有评论</p>'+
									'</div>'+
									'</div>'+
									'</div>';
									
							$("#dongtai").append(str);
							$("img.lazy").lazyload({
						         effect : "fadeIn"
						         ,threshold : 400
						         //,placeholder : "${path}/resource/img/lazyload.gif"
						    });
						});
						
						/*if(data.length == 0){
							$("#dongtai").append('<h3 style="font-size: .28rem;line-height: .50rem;">你尚未发布动态！</h3>');
						}*/
					};
					
					
				},
			    error:function(e){
				   //alert(e);
			    }
		 }); 
			
		}
		
		//查看评论内容
		function lookCommentInfo(id,dy_type,ad_url){
			if(dy_type==1){
				window.location.href=ad_url;
			}else{
				window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
			}
			
		}
		//点赞
		function insertLick(_id,th){
		var oldLickCount=th.html();
		if(th.hasClass("iconfonthead")){
			th.removeClass("iconfonthead").addClass("iconfont-badhead");
		}else{
			th.removeClass("iconfont-badhead").addClass("iconfonthead");
		}
		zdy_ajax({
			url: "${path}/m/dynamic/thumbup.do",
			data:{
				id:_id,
			},
			success: function(data,res){	
				if(res.code == 0){
					if(res.msg==0){
						alert("点赞成功！");
						th.html(oldLickCount*1+1);
					}else{
						alert("点赞已取消！");
						th.html(oldLickCount*1-1);
					}
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
}
		
		function delDynamic(_id,dynamicId){
			confirm("是否确认删除？",function(t){
				if(t == 1){
					zdy_ajax({
						url: '${path}/m/dynamic/delDynamic.do',
						type: 'post',
						dataType: 'json',
						data:{
							dynamicId:dynamicId
						},
						success: function(output){
							$("#"+_id).remove();
							alert("删除成功！");
						}
					});
				}
			});
			
		}
		
		function playMedia(mediaId, dynamicId, price, overtime_flag, k) {
			if (is_weixn()) {
				if(mediaId.indexOf(".mp3") != -1){
					var audio=$("#audio_id")[0];
					audio.src=mediaId;
					audio.play();
					audio.onplay=function(){
						$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif');
					}
					audio.onended=function(){
						$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
					}
					//暂停时使用
					audio.onpause=function(){
						$(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
					}
				}else{
					if(overtime_flag == 1){
						alert_back("该语音已超过3天，不能播放！",function(){
						});
						return;
					}
					if(!isEmpty(nowPlayMedia)){
						if(!isEmpty(nowPlayMedia.localId)){
							wx.stopVoice({
							    localId: nowPlayMedia.localId 
							});
						}
					}
					nowPlayMedia.mediaId = mediaId;
					nowPlayMedia.dynamicId = dynamicId;
					downloadVoice(mediaId,k);
				}
			} else {
				layer.msg("请在微信中打开此系统！");
			}
		}
		
		//下载播放录音
		function downloadVoice(serverId,k){
			wx.downloadVoice({
			 	serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
			    isShowProgressTips: 1,// 默认为1，显示进度提示
			    success: function (res) {
			    	var download_localId = res.localId; // 返回音频的本地ID
			    	//开始播放语音
			    	$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif')
			    	wx.playVoice({
					    localId: download_localId // 需要播放的音频的本地ID，由stopRecsord接口获得
					});
			    	wx.onVoicePlayEnd({
					    success: function (res) {
					        //结束播放语音
					        $(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
					    }
					});
			    }
			});
		};
		
		//测试聚合页使用
		function test(){
			self.location.href="${path}/m/jhy/view.do?id=11";
		}
		
		function formatDate(now) { 
			var year=now.getFullYear(); 
			var month=now.getMonth()+1;
			if(month<10){
				month="0"+month;
			}
			var day=now.getDate();
			if(day<10){
				day="0"+day;
			}
			var hour=now.getHours();
			if(hour<10){
				hour="0"+hour;
			}
			var minute=now.getMinutes();
			if(minute<10){
				minute="0"+minute;
			}
			var second=now.getSeconds();
			if(second<10){
				second="0"+second;
			}
			return month+"/"+day+" "+hour+":"+minute; 
			} 
		
		function jumparticle(articleid){
			window.location.href= _path+"/library/m/librarydetail.do?type=1&id="+articleid; 
		}
	</script>
	<audio id="audio_id" src="">
	</audio>
</body>
</html>