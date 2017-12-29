<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="动态详情">
<title>动态详情</title>
<link rel="stylesheet" type="text/css"
	href="${path}/resource/css/informationDetail.css?v=${sversion}" />
	<link rel="stylesheet" type="text/css" href="${path}/resource/css/index.css?v=${sversion}" />
  <style>
      #photo_list1 img{
         height:1rem;
      }
      .dc{
       margin-top:0;
      }
		/*文章部分*/
     		.item{
     			padding: 0.15rem .15rem;
     			background: #F2F3F4;
     			display: flex;
   			    height: 1.2rem;	
     		}
     		.item .it-img{
     			width: 36.23%;
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
			     line-height: .27rem;
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
     		.ul-hide li {
              height:auto;
               }
		</style>
</head>

<body style="background: #f2f3f4;width: 100%; height:100%;">
	
		<div class="dc">
			<ul class="ul-hide">
				<li class="show-active" id="dynamicPage">
				</li>
			</ul>
		</div>
		<div id="includeJsp" style="display: black">
		<jsp:include page="../commons/include_comment.jsp">
			<jsp:param name="id" value="${id}" />
			<jsp:param name="cmeType" value="5" />
		</jsp:include><%--cmeType 1:资讯   2：合作 3：话题  4：活动 --%>
		</div>
	<audio id="audio_id" src="">
		</audio>	
</body>
<script type="text/javascript">
	$(function(){
		//语音相关
		if(is_weixn()){
			
			var configdata = {};
			zdy_ajax({
				url: '${path}/m/sys/getSignature.do',
				async: false,
				type: 'POST',
				data:{
					url:window.location.href
				},
				success: function(data,output){
					if(output.code == 0){
						configdata = output.dataobj;
					}
				},
				error:function(e){
					//alert(e);
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
			jsApiList.push("previewImage");
			
			
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
			
			wx.error(function(res){
				//alert(res);
			});
		}
	});

	/* function showimgs(id,img){	
		if(is_weixn()){
			var imgs = [];
			$("#img_box_"+id+" img").each(function(){
				imgs.push($(this).attr("src"));
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
	$(document).ready(function() {
		var url = "${path}/m/dynamic/selectDynamicById.do";
		zdy_ajax({
			url: url,
			data:{
				dynamicId:'${id}'
			},
		    showLoading:false,
			success: function(dataobj,res){
				console.log(dataobj);
				var data = dataobj.dynamicList;
				if(res.code == 0){
				    //alert(data.length);
					//console.log(data);
					if(data.length==0 || data.length<10){
						$(".page_loading").hide();
						$(".page_nodata").show();
					}
					$.each(data, function(i, n){
						var dynamic = data[i];
						//分享设置
						var _title = "动态详情";
						var _imgUrl = "";
						if(dynamic.dynamicMap.dy_content.length > 0){
							_title = dynamic.dynamicMap.dy_content;
						}
						if(dynamic.picList.length > 0){
							_imgUrl =_oss_url+dynamic.picList[0].picUrl;
						}
						var _link = home_path+_path+"/m/dynamic/commentPage.do?id="+dynamic.dynamicMap.id;
						wxsharefun(_link,_title,_imgUrl);
						/*if(dynamic.dynamicMap.allow_comment == "0"){
							$("#includeJsp").hide();
						}else{
							$("#includeJsp").show();
						}*/
						var str = '<div class="msgBox">'+
						   '<div class="msg-l" style="background-image:url('+dynamic.dynamicMap.head_portrait+')" id="'+dynamic.dynamicMap.user_id+'">'+
						   '</div>'+
						   '<div class="msg-r">'+
								'<div class="t" data-id="'+dynamic.dynamicMap.user_id+'" onclick=showUserDetial(this)>'+
									'<h2 >'+dynamic.dynamicMap.nickname+'</h2>'+
									'<span class="time">'+dynamic.dynamicMap.minute_flag+'</span>'+
								'</div>'+
								'<p class="ph" data-id="'+dynamic.dynamicMap.user_id+'" onclick=showUserDetial(this)>'+dynamic.dynamicMap.job_name+' /'+dynamic.dynamicMap.com_name+'</p>'+
								'<h3 onclick=lookCommentInfo("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'")>'+dynamic.dynamicMap.dy_content+'</h3>';
								if(dynamic.dynamicMap.articleid!= undefined && dynamic.dynamicMap.articleid!= "undefined" && dynamic.dynamicMap.articleid!= null && dynamic.dynamicMap.articleid!= ""){
									str += '<div   onclick=jumparticle("'+dynamic.dynamicMap.articleid+'"); class="item"><div class="it-img"><img src="'+_oss_url+dynamic.dynamicMap.aimgpath+'"/></div><div class="it-cont">'+
									'<h3>'+dynamic.dynamicMap.atitle+'</h3><div class="bot"><span>'+formatDate(new Date(dynamic.dynamicMap.adate))+'</span><span>摘自:'+dynamic.dynamicMap.aclassname+'</span></div></div></div>';
									
								}
								str += '<div id="img_box_'+dynamic.dynamicMap.id+'">';
								if(dynamic.picList.length != 0 && dynamic.picList.length != 1){
									str += '<div class="imgBox_3">';
									$.each(dynamic.picList, function(k, n){
										if(k%3 == 0 && k !=0){
											str += '</div>';
											str += '<div class="imgBox_3">';
										}
										str +='<div class="insideIMGbox" style="background:url('+dataobj.ossurl+dynamic.picList[k].picUrl+') no-repeat center;background-size:100% 100%"  onclick=showimgs("'+dynamic.dynamicMap.id+'","'+dataobj.ossurl+dynamic.picList[k].picUrl+'")><img style="display:none" class="lazy" src="'+dataobj.ossurl+dynamic.picList[k].picUrl+'" /></div>';
									});
									str += '</div>';
								}
								/* console.log(dynamic.picList) */
								if(dynamic.picList.length == 1 && dynamic.picList[0].picUrl != null && dynamic.picList[0].picUrl != ""){
									var img640 = imgReplaceStyle(dataobj.ossurl+dynamic.picList[0].picUrl,'YSMAX1024');
									str += '<div class="imgBox_1" >'+
									'<img class="lazy" data-original="'+img640+'" data-imgurl="'+img640+'" onclick=showimgs("'+dynamic.dynamicMap.id+'","'+img640+'","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'") />'+
								'</div>';
								}
								str+='</div>';
								
								if(dynamic.dynamicMap.media_id != '' && dynamic.dynamicMap.media_seconds != '' && dynamic.dynamicMap.media_seconds > 0){
									var media_length = dynamic.dynamicMap.media_seconds;
									var length_media = media_length > 10 ? media_length:10;
									str +='<div class="fb_yyt" id="fb_yyt" onclick=playMedia("'+_oss_url+dynamic.dynamicMap.media_id+'","'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.media_price+'","'+dynamic.dynamicMap.overtime_flag+'",this)>'+
		                            '<span class="fb_yyt_tu" style="width:'+length_media+'%" ><img src="${path}/resource/img/images/yy01.png"></span>'+
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
			                    var clicked='iconfont2';
			                    if(dynamic.dynamicMap.like_flag){  
			                    	clicked='iconfont-bad'
			                    };
			                    
								str+='<div class="tubiao">'+
									'<div class="tb">'+
										'<i class=" iconfont1" onclick=_comment_add("'+dynamic.dynamicMap.allow_comment+'")></i>'+
										'<span style="background: url(); padding-left:0.1rem">'+dynamic.commonCount+'</span>'+
									'</div>'+
									'<div class="tb">'+
										'<i class="iconfont '+clicked+'" onclick=insertLick("'+dynamic.dynamicMap.id+'")></i>'+
										'<span style="background: url(); padding-left:0.1rem" id="lick_'+dynamic.dynamicMap.id+'">'+dynamic.lickCount+'</span>'+
									'</div>';
								if(dynamic.dynamicMap.dy_type !=1){
									str+='<div class="tb">'+
										'<i class=" iconfont3" onclick=insertForward("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.media_id+'","'+dynamic.dynamicMap.articleid+'")></i>'+
										'<span id="forward_'+dynamic.dynamicMap.id+'">'+dynamic.forwardCount+'</span>'+
									'</div>';
								}
									
								str+='<div class="tb mri">'+
										'<i class="iconfont4" onclick=insertGratuity("'+dynamic.dynamicMap.id+'","'+dynamic.dynamicMap.allow_reword+'")></i>'+
									'</div>'+
								'</div>';
								str+='</div>'+'</div>';
						 $("#dynamicPage").append(str);
						 
						 
						 $("img.lazy").lazyload({
					         effect : "fadeIn"
					         ,threshold : 400
					         //,placeholder : "${path}/resource/img/lazyload.gif"
					    });

						 $('.msg-l').bind('click',function(){
							   window.location.href="${path}/m/my/personal_home2.do?userId="+$(this).attr('id');
						 });
					});
					 $(".iconfont").bind('click',function(){
					   	 if($(this).hasClass('iconfont-bad')){
					   	     $(this).removeClass('iconfont-bad').addClass('iconfont2');
					   	 }else if($(this).hasClass('iconfont2')){
					   		$(this).removeClass('iconfont2').addClass('iconfont-bad');
					   	 }
					   	 
					     
					 })
				}
				
			},
		    error:function(e){
			   //alert(e);
		    }
	 }); 
	});
	
	//查看评论内容
	function lookCommentInfo(id){
		window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
	}
	//点赞
	function insertLick(_id,classes){
			var oldLickCount=$("#lick_"+_id).html();
			zdy_ajax({
				url: "${path}/m/dynamic/thumbup.do",
				data:{
					id:_id,
				},
				success: function(data,res){
					//console.log(res);
					if(res.code == 0){
						if(res.msg==0){
							alert("点赞成功！");
							$("#lick_"+_id).html(oldLickCount*1+1);
						}else{
							alert("点赞已取消！");
							$("#lick_"+_id).html(oldLickCount*1-1);
						}
					}
				},
			    error:function(e){
				   //alert(e);
			    }
			}); 
	}



	function insertGratuity(_id,allowreword){
		zdy_ajax({
			url: "${path}/m/dynamic/isReward.do",
			data:{
				dynamicId:_id,
			},
			success: function(data,res){
				
				if(res.msg==0){
					window.self.location='${path}/m/dynamic/rewardPage.do?dynamicId='+_id+'&fromUrl='+window.self.location;
				}else{
					alert("你已打赏过此动态");
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
	}

	//转发
	var insertForward=function(_id,mediaId,articleid){
		if (window.localStorage) {
			  localStorage.setItem("libraryzfurl",_path+"/m/dynamic/commentPage.do?id=${id}");
		}
		if(mediaId == undefined || mediaId == "undefined" || mediaId == null || mediaId == ""){
			var oldForwardCount=$("#forward_"+_id).html();
			zdy_ajax({
				url: "${path}/m/dynamic/isForward.do",
				data:{
					dynamicId:_id,
				},
				success: function(data,res){
					if(res.code == 0){
						if(res.msg==0){
							$("#forward_"+_id).html(oldForwardCount*1+1);
							if(articleid == undefined || articleid == "undefined" || articleid == null || articleid == ""){
								window.self.location.href = "${path}/m/dynamic/forwardDynamic.do?dynamicId="+_id+"&fromurl="+window.self.location;
							}else{
								window.location.href= _path+"/library/m/dynamiczf.do?type=2&id="+articleid+"&fromDynamicId="+_id; 
							}
							
						}else{
							alert("你已转发过此动态");
						}
					}
				},
			    error:function(e){
				   //alert(e);
			    }
			}); 
		}else{
			alert("语音动态不能转发");
		}
	}

	var show_zzc=function(t){
		$("body").bind("touchmove",function(event){
			event.preventDefault();
		});
		if(t && t == 1){
			scrollTop=$(document).scrollTop();
			$(document).scrollTop(0);
			$(window).bind("scroll",function(){
				$(document).scrollTop(0);
			});
		}
	}

	var hide_zzc=function(t){
		$("body").unbind("touchmove");
		if(t && t == 1){
			$(window).unbind("scroll");
			$(document).scrollTop(scrollTop);
		}
		
	}



	function playMedia(mediaId,dynamicId,price,overtime_flag,k){
		if(is_weixn()){
			var url = "${path}/m/dynamic/isPayofMedia.do";
			zdy_ajax({
				url: url,
				data:{
					dynamicId:dynamicId
				},
			    showLoading:false,
				success: function(obj,res){
					//console.log(res.code);
					if(res.code == 0){
						if(res.msg == 1){
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
						}else{
							confirm("确定要支付"+price+"个J币吗？",function(t){
								if(t == 1){
									//window.self.location="${path}/m/dynamic/paymediaPage.do?dynamicId="+dynamicId;	
									
									 zdy_ajax({
											url: '${path}/m/dynamic/paymedia.do',
											data:{
												dynamicId:dynamicId,
												jcount:price
											},
											success: function(data,output){
												if(output.code == 0){
													alert_back(output.msg,function(){
														$("#"+dynamicId+"1").html("该语音已付费");
													});
													
												}else{
													alert_back(output.msg,function(){
													});
												}
											},
											error:function(e){
											}
										});
									
									
									
								}
							});
						}
					}
				},
			    error:function(e){
				   //alert(e);
			    }
		 	}); 
		}else{
			layer.msg("请在微信中打开此系统！");
		}
	}

	//下载播放录音
	function downloadVoice(serverId,k){
		//alert(serverId);
		wx.downloadVoice({
		 	serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
		    isShowProgressTips: 1,// 默认为1，显示进度提示
		    success: function (res) {
		    	var download_localId = res.localId; // 返回音频的本地ID
		    	nowPlayMedia.localId = res.localId;
		    	//开始播放语音
		    	$(k).find('img').attr('src','${path}/resource/img/images/yy03.gif');
		    	wx.playVoice({
				    localId: download_localId // 需要播放的音频的本地ID，由stopRecsord接口获得
				});
		    	wx.onVoicePlayEnd({
				    success: function (res) {
				        //结束播放语音
				        $(k).find('img').attr('src','${path}/resource/img/images/yy01.png');
	                    //在此加入结束动态效果
				       
				    }
				});
		    }
		});
	};

	function showUserDetial(obj){
		window.location.href="${path}/m/my/personal_home2.do?userId="+$(obj).data("id");
	}
	
	function jumparticle(articleid){
		window.location.href= _path+"/library/m/librarydetail.do?type=1&id="+articleid; 
	}
	function lookCommentInfo(id,dy_type,ad_url){
		if(dy_type==1){
			window.location.href=ad_url;
		}
		
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
	
	function _comment_add(allowComment){
		/*if(allowComment == "0"){
			alert("此动态不允许评论!");
			return ;
		}else{
			comment_add();
		}*/
		comment_add();
	}
</script>
</html>