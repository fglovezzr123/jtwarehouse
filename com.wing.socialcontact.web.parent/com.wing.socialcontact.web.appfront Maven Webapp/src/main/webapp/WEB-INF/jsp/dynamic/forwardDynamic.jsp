<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="发布动态">
		<title>转发动态</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
	</head>
	<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="h_tit">
				<h3 class="h_active">发动态</h3>
			</div>
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
			<div class="p-content show" >
				<div class="pl">
					<textarea name="dyContent" id="dyContent" rows="66" cols="30" placeholder="分享此时新鲜事..." maxLength=200></textarea>
					<p><span id="dyContentLength">0</span>/200</p>
				</div>
				<div class="p-head">
                    <h3 class="hv">上传图片</h3>
                    <h3>上传语音</h3>
                </div>
                <ul class="ul-hide">
					<li class="show-active">
						<div class="photo" id="photoList">
							<div class="photo-oper" id="photo_list1">
								<div class="addimagess active_A" onclick="doSelectPic1();" id="file_button_1"></div>
								<iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
							</div>
							<div class="photo-oper" id="photo_list2" style="display:none;">
								<div class="addimagess active_A" onclick="doSelectPic2();" id="file_button_2"></div>
								<iframe id="uploadPicFrame2" src="" style="display:none;"></iframe>
							</div>
							<div class="photo-oper" id="photo_list3" style="display:none;">
								<div class="addimagess active_A" onclick="doSelectPic3();" id="file_button_3"></div>
								<iframe id="uploadPicFrame3" src="" style="display:none;"></iframe>
							</div>
						</div>
					</li>
					<li>
						<div class="photo">
	                	<div class="fb_box">
	                        <div class="fb_yyt" id="fb_yyt" style="display: none;">
	                            <span class="fb_yyt_tu" id="fb_yyt_tu" >
	                            <img src="${path}/resource/img/images/yy01.png">
	                            <i></i>
	                            </span>
	                            <span class="fb_yyt_m" id="fb_yyt_m">3</span>
	                            <div class="clearfix"></div>
	                        </div>
	                        <div class="fb_speak" id="fb_speak">
	                            <div class="fb_speak_p" id="fb_speak_p" ></div>
	                            <div class="fb_speak_bt" id="fb_speak_bt">
	                               <div></div>
	                            </div>
	                        </div>
	                    </div>
						<div class="fb_ptb">
							<div class="fb_ptb_shu">需支付<input id="mediaP" name="mediaPrice" type="number">个平台币</div>
	                        <div class="fb_ptb_ts">(不填或填0，视为本条动态信息免费)</div>
						</div>
					</div>
				</li>
				</ul>
				<form class="label" action="">
					<div class="lx">
						<label for="">对所有用户开放</label><input type="radio" name="visitType"  value="2" checked/>
					</div>
					<div class="lx">
						<label for="">仅对我的好友开放</label><input type="radio" name="visitType"  value="1" />
					</div>
				</form>
				<div style="height: 0.9rem;"></div>
			</div>
			</div>
			</div>
			<div class="submit active_A" id="submit_button">发布</div>
			<audio id="audio_id" src="${path}/resource/img/Kalimba.mp3">
			</audio>
		</div>
		<script src="${path}/resource/js/upload.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		$("#bf_button").click(function(){
			var audio=$("#audio_id")[0];
			audio.play();
		});		
		
		var r = /^(0|[1-9]\d*)$/
		//document.getElementById("dyContent").wxImeEmojiFix(); // 原生用法
		function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
		}
		//判断是否有emoji
		function isEmojiCharacter(substring) {  
		    for ( var i = 0; i < substring.length; i++) {  
		        var hs = substring.charCodeAt(i);  
		        if (0xd800 <= hs && hs <= 0xdbff) {  
		            if (substring.length > 1) {  
		                var ls = substring.charCodeAt(i + 1);  
		                var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;  
		                if (0x1d000 <= uc && uc <= 0x1f77f) {  
		                    return true;  
		                }  
		            }  
		        } else if (substring.length > 1) {  
		            var ls = substring.charCodeAt(i + 1);  
		            if (ls == 0x20e3) {  
		                return true;  
		            }  
		        } else {  
		            if (0x2100 <= hs && hs <= 0x27ff) {  
		                return true;  
		            } else if (0x2B05 <= hs && hs <= 0x2b07) {  
		                return true;  
		            } else if (0x2934 <= hs && hs <= 0x2935) {  
		                return true;  
		            } else if (0x3297 <= hs && hs <= 0x3299) {  
		                return true;  
		            } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030  
		                    || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b  
		                    || hs == 0x2b50) {  
		                return true;  
		            }  
		        }  
		    }  
		}
		
		
			$('.h_tit h3').on('click', function() {
				var index = $(this).index();
				$(this).addClass('h_active').siblings().removeClass('h_active');
				$('.p-content').eq(index).addClass('show').siblings().removeClass('show');
				
			})
			
			var localId = ""; //本地语音id
			var serverId=""; //服务器语音id
			var download_localId = ""; //下载后语音id
			var mediaSeconds = 0; //语音描述
			var mediaPrice = 0;//语音价格
			
			var t; //定时器
			var t_minute = 0; //计时分钟数
			var t_seconds = 0; // 计时秒数
			
			var startFlag = true;
			
			function setShowFb_yyt(){
				if(mediaSeconds > 10){
					$("#fb_yyt_tu").css("width",mediaSeconds+"%");
					$("#fb_yyt_tu i").css({'left':'calc(100% - 0.2rem)'})
				}
				if(mediaSeconds > 50){
					$("#fb_yyt_tu").css("width","50%");
				}
				$("#fb_yyt_m").html(mediaSeconds+"\"");
				$("#fb_yyt").show();
			}
			
			function setHideFb_yyt(){
				if(mediaSeconds > 10){
					$("#fb_yyt_tu").css("width",mediaSeconds+"%");
				}
				$("#fb_yyt_m").html(0+"\"");
				$("#fb_yyt").hide();
			}
			
			function clickStartRecord(){
				$("#fb_speak_bt").removeClass('fb_speak_bt');
				$("#fb_speak_bt").addClass('fb_speak_bt2');
				startRecord();
				mediaSeconds = 0;
				setHideFb_yyt();
				timedCount();
				startFlag = false;
			}
			
			function clickEndRecord(){
				$("#fb_speak_bt").removeClass('fb_speak_bt2');
				$("#fb_speak_bt").addClass('fb_speak_bt');
				stopCount();
				stopRecord();
				$("#fb_speak_p").html("");
				setShowFb_yyt();
				startFlag = true;
			}
			startFlag = true;
			$(function(){
				var vuleStr = "${dyContent}"
				$("#dyContentLength").html(vuleStr.length)
				$("#dyContent").val("${dyContent}");
				initDynamicPic(${picList});
				
				localId = ""; //本地语音id
				serverId=""; //服务器语音id
				download_localId = ""; //下载后语音id
				mediaSeconds = 0; //语音描述
				mediaPrice = 0;//语音价格
				t_minute = 0; //计时分钟数
				t_seconds = 0; // 计时秒数
				
				$('.p-head h3').on('click', function() {
					var index = $(this).index();
					$(this).addClass('hv').siblings().removeClass('hv');
					$('.ul-hide li').eq(index).addClass('show-active').siblings().removeClass('show-active');
				});
				
				var fb_speak_bt = document.getElementById("fb_speak_bt");
				
				fb_speak_bt.addEventListener("click",function(event){
					if(!is_weixn()){
						layer.msg("请在微信中打开此系统！");
						return;
					}
					if(startFlag){
						clickStartRecord();
					}else{
						clickEndRecord();
					}
					
				});
				
				
				
				$('#fb_yyt').on('click', function() {
					playVoice();
				});
				
				var flag = false;
				//initUploadPicFrame("uploadPicFrame1","pic","upload1");
				//initUploadPicFrame("uploadPicFrame2","pic","upload2");
				//initUploadPicFrame("uploadPicFrame3","pic","upload3");

				$("#dyContent").bind("input propertychange", function() {
					var len = $(this).val().length;
					$("#dyContentLength").text(len);
				});
				
				$("#mediaP").bind("input propertychange", function() {
					if(!r.test($("#mediaP").val())){
						$("#mediaP").val("");
					}
					if(isNaN($("#mediaP").val())){
						$("#mediaP").val("");
					}
				});
				
				$("#submit_button").click(function(){
					if(flag){
						return;
					}else{
						flag = true;
					}
					if(!startFlag){
						alert_back("请先暂停录音!",function(){
						});
						flag = false;
						return;
					}
					var dyContent=jQuery.trim($("#dyContent").val());
					
					if(isEmpty(dyContent)){
						alert_back("动态内容不能为空",function(){
							$("#dyContent").focus();
						});
						flag = false;
						return;
					}
					
					if(isEmojiCharacter(dyContent)){
						alert_back("动态内容不能含表情",function(){
							$("#dyContent").focus();
						});
						return;
					}
					
					if(jQuery.trim($("#mediaP").val())==null || jQuery.trim($("#mediaP").val()) == "" || jQuery.trim($("#mediaP").val()).length == 0){
						
					}else{
						if(isNaN($("#mediaP").val())){
							alert_back("请输入0-99999999之间的整数！",function(){
								$("#mediaPs").focus();
							});
							flag = false;
							return;
						}else if(!r.test($("#mediaP").val())){
							alert_back("请输入0-99999999之间的整数！",function(){
								$("#mediaPs").focus();
							});
							flag = false;
							return;
						}else if(parseInt($("#mediaP").val())>99999999){
							alert_back("请输入0-99999999之间的整数！",function(){
								$("#mediaPs").focus();
							});
							flag = false;
							return;
						}else{
							mediaPrice = $("#mediaP").val();
						}
						if(isEmpty(localId)){
							alert_back("您没有录制语音，不能发布！",function(){
								
							});
							flag = false;
							return;
						}
						
					}
					
					
					var visitType = $("input[name='visitType']:checked").val();
					if(isEmpty(visitType)){
						alert_back("请选择公开类型",function(){
						});
						
						flag = false;
						return;
					}
					
					//组装证件照片
					var imgSize=$("#photo_list1").find("div[class='imagess']").length;
					var imgSize2=$("#photo_list2").find("div[class='imagess']").length;
					var imgSize3=$("#photo_list3").find("div[class='imagess']").length;
					
					var zjImgerJson="";
					var num=0;
					if(imgSize != 0){
						$("#photo_list1").find("div[class='imagess']").find("img").each(function(){
							if(num > 0){
								zjImgerJson+=",";
							}
							var imgUrl=$(this).attr("srcpath");
							zjImgerJson+=imgUrl;
							num++;
						});
					}
					if(imgSize2 != 0){
						$("#photo_list2").find("div[class='imagess']").find("img").each(function(){
							if(num > 0){
								zjImgerJson+=",";
							}
							var imgUrl=$(this).attr("srcpath");
							zjImgerJson+=imgUrl;
							num++;
						});
					}
					
					if(imgSize3 != 0){
						$("#photo_list3").find("div[class='imagess']").find("img").each(function(){
							if(num > 0){
								zjImgerJson+=",";
							}
							var imgUrl=$(this).attr("srcpath");
							zjImgerJson+=imgUrl;
							num++;
						});
					}
/* 					var zjImgerJson="[";
					var num=0;
					if(imgSize != 0){
						$("#photo_list1").find("div[class='imagess']").find("img").each(function(){
							if(num > 0){
								zjImgerJson+=",";
							}
							var imgUrl=$(this).attr("srcpath");
							var type=1;
							zjImgerJson+="{";
							zjImgerJson+="\"imgUrl\":\""+imgUrl+"\",";
							zjImgerJson+="\"type\":\""+type+"\"";
							zjImgerJson+="}";
							num++;
						});
					}
					if(imgSize2 != 0){
						$("#photo_list2").find("div[class='imagess']").find("img").each(function(){
							if(num > 0){
								zjImgerJson+=",";
							}
							var imgUrl=$(this).attr("srcpath");
							var type=1;
							zjImgerJson+="{";
							zjImgerJson+="\"imgUrl\":\""+imgUrl+"\",";
							zjImgerJson+="\"type\":\""+type+"\"";
							zjImgerJson+="}";
							num++;
						});
					}
					
					if(imgSize3 != 0){
						$("#photo_list3").find("div[class='imagess']").find("img").each(function(){
							if(num > 0){
								zjImgerJson+=",";
							}
							var imgUrl=$(this).attr("srcpath");
							var type=1;
							zjImgerJson+="{";
							zjImgerJson+="\"imgUrl\":\""+imgUrl+"\",";
							zjImgerJson+="\"type\":\""+type+"\"";
							zjImgerJson+="}";
							num++;
						});
					}
					
					zjImgerJson+="]"; */
					
					if((imgSize+imgSize2+imgSize3) == 0 && isEmpty(localId)){
						alert_back("动态图片和语音不能同时为空！",function(){
						});
						flag = false;
						return;
					}
					
					if(!isEmpty(localId)){
						//检查网络情况
						zdy_ajax({
							url: "${path}/m/dynamic/isReward.do",
							data:{
								dynamicId:localId,
							},
							success: function(data,res){
								if(res.code == 0){
									wx.uploadVoice({
									    localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
									    isShowProgressTips: 1,// 默认为1，显示进度提示
									    success: function (res) {
									        serverId = res.serverId; // 返回音频的服务器端ID
									        zdy_ajax({
							    				url: '${path}/m/upload/wxUploadVoice.do',
							    				data : {
							    					serverId : serverId
							    				},
							    				success: function(data,res){
							    					//上传成功
							    					var audio = $("#audio_id")[0];
						    					    audio.src = data.img_url;
						    					    zdy_ajax({
						    							url: '${path}/add/m/dynamic/insertForwardDynamic.do',
						    							data:{
						    								dyContent:$("#dyContent").val(),
						    								visitType:visitType,
						    								zjImgerJson:zjImgerJson,
						    								mediaSeconds:mediaSeconds,
						    								mediaId:serverId,
						    								mediaPrice:mediaPrice,
						    								fromDynamicId:'${fromDynamicId}'
						    							},
						    							success: function(data,output){
						    								flag = false;
						    								if(output.code == 0){
						    									alert_back("转发成功",function(){
						    										var last_url="${fromurl}";
						    										top.location.href=last_url;
						    									});
						    								}else{
						    									alert_back(output.msg,function(){
						    										var last_url="${fromurl}";
						    										top.location.href=last_url;
						    									});
						    								}
						    							},
						    							error:function(e){
						    								//alert(e);
						    								flag = false;
						    							}
						    						});
							    				}
							    			}); 
									    }
								 });
								}
							}
						}); 
					}else{
						
						zdy_ajax({
							url: '${path}/add/m/dynamic/insertForwardDynamic.do',
							data:{
								dyContent:$("#dyContent").val(),
								visitType:visitType,
								zjImgerJson:zjImgerJson,
								mediaSeconds:mediaSeconds,
								mediaId:serverId,
								mediaPrice:mediaPrice,
								fromDynamicId:'${fromDynamicId}'
							},
							success: function(data,output){
								flag = false;
								if(output.code == 0){
									alert_back("转发成功",function(){
										var last_url="${fromurl}";
										top.location.href=last_url;
									});
								}else{
									alert_back(output.msg,function(){
										var last_url="${fromurl}";
										top.location.href=last_url;
									});
								}
							},
							error:function(e){
								//alert(e);
								flag = false;
							}
						});
					}
					flag = false;
				});
				
				wx.onVoiceRecordEnd({
				    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
				    complete: function (res) {
				       localId = res.localId;
				    }
				});
				
			});
			
			//录音
			function startRecord (){
				//alert("开始录音。。。");
				wx.startRecord();
			};
			//停止录音
			function stopRecord() {
				wx.stopRecord({
				    success: function (res) {
				        localId = res.localId;
				        $("#fb_yyt_tu").find('img').attr('src','${path}/resource/img/images/yy01.png')
				    }
				});
			};
			//播放录音
			function playVoice(){
				var yytSrc =  $("#fb_yyt_tu").find('img').attr("src");
				if(yytSrc == '${path}/resource/img/images/yy03.gif'){
					wx.stopVoice({
					    localId: localId // 需要停止的音频的本地ID，由stopRecord接口获得
					});
					$("#fb_yyt_tu").find('img').attr('src','${path}/resource/img/images/yy01.png');
				}else{
					wx.playVoice({
					    localId: localId // 需要播放的音频的本地ID，由stopRecord接口获得
					});
					//alert("播放录音。。。");
					$("#fb_yyt_tu").find('img').attr('src','${path}/resource/img/images/yy03.gif');
				}
				
		    	wx.onVoicePlayEnd({
				    success: function (res) {
				        //结束播放语音
				       $("#fb_yyt_tu").find('img').attr('src','${path}/resource/img/images/yy01.png');
				    }
				});
			};
			//上传录音
			function uploadVoice() {
				
				 wx.uploadVoice({
					    localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
					    isShowProgressTips: 1,// 默认为1，显示进度提示
					    success: function (res) {
					        serverId = res.serverId; // 返回音频的服务器端ID
					        //alert("上传录音。。。");
					        zdy_ajax({
			    				url: '${path}/m/upload/wxUploadVoice.do',
			    				data : {
			    					serverId : serverId
			    				},
			    				success: function(data,res){
			    					//上传成功
			    					var audio = document.getElementById("audio_id");
		    					    audio.src = data.img_url;
			    				}
			    			}); 
					    }
					});
			};
			
			//下载播放录音
			function downloadVoice(){
				wx.downloadVoice({
				 	serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
				    isShowProgressTips: 1,// 默认为1，显示进度提示
				    success: function (res) {
				    	download_localId = res.localId; // 返回音频的本地ID
				    	//alert("下载录音。。。");
				    	wx.playVoice({
						    localId: download_localId // 需要播放的音频的本地ID，由stopRecord接口获得
						});
				    }
				});
			};
			
			//启动定时器
			function timedCount() 
			{ 
				mediaSeconds++;
				t_seconds++ ;
				if(t_seconds == 60){
					clickEndRecord();
					return;
					t_seconds=0;
					t_minute++;
				}
				var showTimeLen = "00:";
				
				if(t_minute > 9){
					showTimeLen +=t_minute+":"; 
				}else{
					showTimeLen +="0"+t_minute+":";
				}
				
				if(t_seconds > 9){
					showTimeLen +=t_seconds; 
				}else{
					showTimeLen +="0"+t_seconds;
				}
				
				$("#fb_speak_p").html(showTimeLen);
				
				t=setTimeout("timedCount()",1000);
			} 
			
			//清除定时器
			function stopCount() 
			{ 
				t_seconds=0;
				t_minute = 0;
				clearTimeout(t);
			}
			
		
			function getByteLen(val) {
	            var len = 0;
	            for (var i = 0; i < val.length; i++) {
	                var a = val.charAt(i);
	                if (a.match(/[^\x00-\xff]/ig) != null) {
	                    len += 1;
	                }
	                else {
	                    len += 1;
	                }
	            }
	            return len;
	        }
			function doSelectPic1() {
				
				//检查网络情况
				zdy_ajax({
					url: "${path}/m/dynamic/isReward.do",
					data:{
						dynamicId:'0',
					},
					success: function(data,res){
						if(res.code == 0){
							zdy_chooseImg(function(data,res){
					    	 	if(res.code == 0){
					    	 		var imgHtml = "<img  srcpath='"+ data.pic_path+ "?x-oss-process=style/YSMAX640' src='"+ data.img_url+ "?x-oss-process=style/YSMAX640' class='up_pic_img' width=100% height=100%'/>";
									var str = '<div class="imagess">' + imgHtml
										+ '<i onclick="delPic(this);"></i></div>';
									$(str).insertBefore("#file_button_1");
									var imgSize = $("#photo_list1").find(
											"div[class='imagess']").length;
									if (imgSize == 3) {
										$("#file_button_1").hide();
										$("#photo_list2").show();
									}
					    	 	}
					    	 });
						}
					}
				}); 
			}
			function doSelectPic2() {
				//检查网络情况
				zdy_ajax({
					url: "${path}/m/dynamic/isReward.do",
					data:{
						dynamicId:'0',
					},
					success: function(data,res){
						if(res.code == 0){
							zdy_chooseImg(function(data,res){
					    	 	if(res.code == 0){
					    	 		var imgHtml = "<img  srcpath='"+data.pic_path+"?x-oss-process=style/YSMAX640' src='"+data.img_url+"?x-oss-process=style/YSMAX640' class='up_pic_img' style='width:100%; height:100%;'/>";
									var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
									$(str).insertBefore("#file_button_2");
									var imgSize = $("#photo_list2").find(
											"div[class='imagess']").length;
									if (imgSize == 3) {
										$("#file_button_2").hide();
										$("#photo_list3").show();
									}
					    	 	}
					    	 });
						}
					}
				}); 
			}
			function doSelectPic3() {
				//检查网络情况
				zdy_ajax({
					url: "${path}/m/dynamic/isReward.do",
					data:{
						dynamicId:'0',
					},
					success: function(data,res){
						if(res.code == 0){
							zdy_chooseImg(function(data,res){
				    	 	if(res.code == 0){
				    	 		var imgHtml = "<img  srcpath='"+data.pic_path+"?x-oss-process=style/YSMAX640' src='"+data.img_url+"?x-oss-process=style/YSMAX640' class='up_pic_img' style='width:100%; height:100%;'/>";
								var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
								$(str).insertBefore("#file_button_3");
								var imgSize = $("#photo_list3").find(
										"div[class='imagess']").length;
								if (imgSize == 3) {
									$("#file_button_3").hide();
								}
				    	 	}
				    	 });
						}
					}
				}); 
				
			}

			//上传完成后回调的方法
			function picUploadCallback(data, frameId, picId, formId) {
				if (data.returnCode == "0") {
					var picUrl = data.picPath;
					if (picUrl.length > 0) {
						var imgHtml = "<img  srcpath='"
								+ data.picPath
								+ "' src='"
								+ data.img_url
								+ "' class='up_pic_img' width=100% height=100%'/>";
						var str = '<div class="imagess">' + imgHtml
								+ '<i onclick="delPic(this);"></i></div>';
						if ("uploadPicFrame1" == frameId) {
							$(str).insertBefore("#file_button_1");
							var imgSize = $("#photo_list1").find(
									"div[class='imagess']").length;
							if (imgSize == 3) {
								$("#file_button_1").hide();
								$("#photo_list2").show();
							}
						}
						if ("uploadPicFrame2" == frameId) {
							$(str).insertBefore("#file_button_2");
							var imgSize = $("#photo_list2").find(
									"div[class='imagess']").length;
							if (imgSize == 3) {
								$("#file_button_2").hide();
								$("#photo_list3").show();
							}

						}
						if ("uploadPicFrame3" == frameId) {
							$(str).insertBefore("#file_button_3");
							var imgSize = $("#photo_list3").find(
									"div[class='imagess']").length;
							if (imgSize == 3) {
								$("#file_button_3").hide();
							}

						}

					} else {
						alert("上传失败,请稍后再试");
					}
				} else {
					if (data.msg != "") {
						alert(data.msg);
					} else {
						alert("上传失败,请稍后再试");
					}
				}
				initUploadPicFrame(frameId, picId, formId);
				layer.close(upload_load);
			}

			function delPic(data) {
				$(data).parent().parent().find("div[id^='file_button']").show();
				$(data).parent().remove();
				var imgSize1 = $("#photo_list1").find("div[class='imagess']").length;
				imgSize2 = $("#photo_list2").find("div[class='imagess']").length;
				imgSize3 = $("#photo_list3").find("div[class='imagess']").length;
				if (imgSize2 == 0 && imgSize1 != 3) {
					$("#photo_list2").hide();
				} else {
					$("#photo_list2").show();
				}
				if (imgSize3 == 0 && imgSize2 != 3) {
					$("#photo_list3").hide();
				} else {
					$("#photo_list3").show();
				}
			}
			$('#fb_yyt i').on("click",function(event){
				 event.stopPropagation(); 
				$("#fb_yyt_tu").find('img').attr('src','${path}/resource/img/images/yy01.png')
				 $('#fb_yyt').hide();
				 delVoice()
			})
			//删除语音
			function delVoice(){
				localId = ""; //本地语音id
				serverId=""; //服务器语音id
				download_localId = ""; //下载后语音id
				mediaSeconds = 0; //语音描述
				mediaPrice = 0;//语音价格
				t_minute = 0; //计时分钟数
				t_seconds = 0; // 计时秒数
				startFlag = true;
			}
			
			function initDynamicPic(picList){
				if(picList != null && picList.length > 0 ){
					$.each(picList, function(i, n){
						var dynamicPic = picList[i];
						var imgHtml = "<img  srcpath='"
							+ dynamicPic.picUrl
							+ "' src='${ossurl}"+dynamicPic.picUrl
							+ "' class='up_pic_img' width=100% height=100%'/>";
						var str = '<div class="imagess">' + imgHtml
								+ '<i onclick="delPic(this);"></i></div>';
								
						var imgSize = $("#photo_list1").find(
								"div[class='imagess']").length;
						
						var imgSize2 = $("#photo_list2").find(
						"div[class='imagess']").length;	
						
						var imgSize3 = $("#photo_list3").find(
						"div[class='imagess']").length;
						
						if(imgSize < 3){
							$(str).insertBefore("#file_button_1");
							imgSize++;
							
							if(imgSize == 3){
								$("#file_button_1").hide();
								$("#photo_list2").show();
							}
							return;
						}
						if(imgSize == 3 && picList.length > 3 && imgSize2 < 3){
							$(str).insertBefore("#file_button_2");
							imgSize2++;
							if (imgSize2 == 3 ) {
								$("#file_button_2").hide();
								$("#photo_list3").show();
								
							}
							return;
						}
						
						if(imgSize == 3 && picList.length > 6 && imgSize2 == 3 && imgSize3 < 3){
							$(str).insertBefore("#file_button_3");
							imgSize3++;
							if (imgSize3 == 3) {
								$("#file_button_3").hide();
							}
						}
						
					});
				}
				
			}
		</script>
	</body>

</html>