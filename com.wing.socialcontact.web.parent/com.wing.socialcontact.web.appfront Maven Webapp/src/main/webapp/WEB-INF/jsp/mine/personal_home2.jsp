<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%-- <%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%> --%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"
	name="viewport">
<title id="title">个人主页</title>
<link rel="stylesheet" href="${path}/resource/css/personal-Home.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/index.css?v=${sversion}" />
<style>
#guanzhuinfo .names {
	background: #1087eb;
	height: 0.5rem;
	line-height: 0.5rem;
	color: white;
	border-radius: 0.2rem;
	text-align: center
}

#focus .names img {
	height: 0.35rem;
}

#focus .names span {
	font-size: 0.24rem color:#808080
}

.fb_yyt_m {
	font-size: .28rem;
	float: left;
	margin-left: 0.28em;
	height: 0.36rem;
	line-height: 0.36rem;
	display: inline-block;
}

.fb_yyt_tu {
	border-radius: 0.06rem;
}
.layui-layer-iframe {
	overflow-y: initial;
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
	<div class="wrapper">
		<div id='bg'>
			<c:choose>
				<c:when test="${not empty tjyuser.homepagePic}">
					<img id="background" alt="" src="${ossurl}${tjyuser.homepagePic}">
				</c:when>
				<c:otherwise>
					<img id="background" alt="" src="${path}/resource/img/images/background.jpg">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="">
			<div style="text-align:center;">
				<c:choose>
					<c:when test="${not empty tjyuser.headPortrait}">
						<c:choose>
							<c:when
								test="${fn:indexOf(tjyuser.headPortrait,'http://') ne -1 }">
								<img id="user-icon" alt="" src="${tjyuser.headPortrait}" />
							</c:when>
							<c:otherwise>
								<img id="user-icon" alt="" src="${tjyuser.headPortrait}" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<img id="user-icon" alt=""
							src="${path}/resource/img/icons/weixinHeader.jpg" />
					</c:otherwise>
				</c:choose>
			</div>
			<div id="focus">
				<div class="names">${tjyuser.nickname}
					<c:choose>
						<c:when test="${tjyuser.isRealname eq 1 }">
							<%-- <span style="background:url(${path}/resource/img/icons/reconssssss.png) no-repeat center left;background-size:0.4rem;padding-left:0.5rem"><!-- 认证通过 --></span> --%>
							<img src="${path}/resource/img/icons/reconssssss.png" />
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${tjyuser.reconStatus eq 1 }">
									<span>认证中</span>
								</c:when>
								<c:when test="${tjyuser.reconStatus eq 2 }">
									<%-- <span style="background:url(${path}/resource/img/icons/reconssssss.png) no-repeat center left;background-size:0.4rem;padding-left:0.5rem;"><!-- 认证通过 --></span> --%>
									<img src="${path}/resource/img/icons/reconssssss.png" />
								</c:when>
								<c:when test="${tjyuser.reconStatus eq 3 }">
									<span>认证不通过</span>
								</c:when>
								<c:otherwise>
									<span>未认证</span>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>

				<c:choose>
					<c:when test="${tjyuser.isRealname eq 1 }">
						<div>${tjyuser.job}/${tjyuser.comName}</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${tjyuser.reconStatus eq 2 }">
								<div>${tjyuser.industry}/${tjyuser.comName}</div>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>
			<br>
			<div id="guanzhuinfo"></div>

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
				<li class="active_A">访问量
					<div>
						<b><fmt:formatNumber type="number"
								value="${tjyuser.visitQuantity}" maxFractionDigits="0" /></b>
					</div>
				</li>
			</ul>
		</div>
		<div id="tabBar">
			<div>
				<div class="current" id="tabBar1">介绍</div>
			</div>
			<div>
				<div id="tabBar2">动态</div>
			</div>
			<br class="clear" />
		</div>
		<div id="jieshao">
			<div class="text-box whites">
				<div class="text-head">
					<div class="text-name">公司</div>
					<div class="text-name2">${tjyuser.comName}</div>
				</div>
				<br style="clear:both" />
			</div>
			<div class="text-box margins whites non-borderBtm">
				<div class="text-head">
					<div class="text-name">公司简介</div>
				</div>
				<br style="clear:both" />
			</div>
			<div class="presence border-bottom whites"
				style="padding-bottom:0.13rem">${tjyuser.comProfile}</div>
			<!--   
			<div class="infor presence border-bottom whites">
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
			<div class="text-box margins whites non-borderBtm">
				<div class="text-head">
					<div class="text-name">共同好友</div>
					<div class="text-name2 active_A"
						style="text-align:right;background:none;width:auto"
						onclick="common_friend()">更多</div>
				</div>
				<br style="clear:both" />
			</div>
			<div id="sameFriends" class="paddingBtm whites">

				<!-- <div class="friendBox active_A">
					<ul>
						<li></li>
						<li>王网</li>
						<li>北京网购CEO</li>
					</ul>
				</div>
				<br style="clear:both" /> -->
			</div>
			<div class="myTags whites">
				<div class="text-box  whites non-borderBtm">
					<div class="text-head">
						<div class="text-name">朋友印象</div>
					</div>
					<br style="clear:both" />
				</div>
				<div class="mytag1">
					<div id="type0">
						<c:forEach var="vf0" items="${userFriendimpresss0}">
							<span class="tags">${vf0.list_value}(${vf0.impresscount})</span>
						</c:forEach>
						<c:if test="${empty userFriendimpresss0}">
						        暂未获得任何印象
						</c:if>
					</div>
					<div class="active_A" id="ADD1" onclick="personal_impress(0);">+</div>
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
						<c:forEach var="vf1" items="${userFriendimpresss1}">
							<span class="tags">${vf1.listValue}</span>
						</c:forEach>
					</div>
					<br style="clear:both" />
				</div>
				<div class="grayline"></div>
				<div class="text-box   whites non-borderBtm">
					<div class="text-head">
						<div class="text-name">我能提供</div>
					</div>
					<br style="clear:both" />
				</div>
				<div class="mytag1">
					<div id="type2">
						<c:forEach var="vf2" items="${userFriendimpresss2}">
							<span class="tags">${vf2.listValue}</span>
						</c:forEach>
					</div>
					<br style="clear:both" />
				</div>
				<div class="grayline"></div>
			</div>
		</div>
		<div id="dongtai" class="dc" style="display:none;"></div>
		<div class="loadingbox" id="loadingbox" style="display:none;">
			<div class="page_loading" style="display:block;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
		<c:if test="${isFriend=='0'}">
			<div class="wave">
				<div class="active_A" style="color:#808080" onclick="say_hello()">打招呼</div>
				<div class="active_A" onclick="add_friend()">
					<img src="${path}/resource/img/icons/addAsF.png" />加好友
				</div>
				<br style="clear:both" />
			</div>
		</c:if>
	</div>
	<script type="text/javascript">
		var userId = "${tjyuser.id}";
		var curPageNum = 1;
		var flag = false;
		var isFollowUser = ${isFollowUser};
		var isFriend = ${isFriend};
		//语音相关
		var nowPlayMedia = {};

		$(document).ready(function() {
			//滚动加载
			$(window).scroll(function() {
				var scrollTop = $(this).scrollTop();
				var scrollHeight = $(document).height();
				var windowHeight = $(this).height();
				if (scrollTop + windowHeight == scrollHeight) {
					if (flag) {
						curPageNum++;
						showDynamicInfo();
					}
				}
				;
			});

			var configdata = {};
			if (is_weixn()) {
				zdy_ajax({
					url : '${path}/m/sys/getSignature.do',
					async : false,
					data : {
						url : window.location.href
					},
					success : function(data, output) {
						if (output.code == 0) {
							configdata = output.dataobj;
						}
					},
					error : function(e) {
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
					debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId : localStorage.appid, // 必填，企业号的唯一标识，此处填写企业号corpid
					timestamp : configdata.timestamp, // 必填，生成签名的时间戳
					nonceStr : configdata.nonceStr, // 必填，生成签名的随机串
					signature : configdata.signature,// 必填，签名，见附录1
					jsApiList : jsApiList
				// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});

				wx.ready(function() {
				});

			}

			//共同好友
			commentfriend();
			//关注个数
			zdy_ajax({
				url : "${path}/im/m/selMyFollowCount.do",
				data : {
					userId : userId
				},
				success : function(data, res) {
					if (res.code == 0) {
						$("#guanzhu").html(res.dataobj);
					}
				}
			});
			//粉丝个数
			zdy_ajax({
				url : "${path}/im/m/selMyFansCount.do",
				data : {
					userId : userId
				},
				success : function(data, res) {
					if (res.code == 0) {
						///alert(res.dataobj);
						$("#fensi").html(res.dataobj);
					}
				}
			});
			//好友个数
			zdy_ajax({
				url : "${path}/im/m/selMyFriendCount.do",
				data : {
					userId : userId
				},
				success : function(data, res) {
					if (res.code == 0) {
						//alert(res.dataobj);
						$("#haoyou").html(res.dataobj);
					}
				}
			});

		});

		var bg_add = function() {
			layer.open({
				type : 2,
				//skin: 'layui-layer-lan',
				title : '',
				fix : true,
				shadeClose : true,
				maxmin : false,
				area : [ '100%', '100%' ],
				content : '${path}/m/my/background_add.do',
				shift : 2,
				scrollbar : false,
				success : function(layero, index) {
					show_zzc(1);
				},
				end : function() {
					hide_zzc(1);
				},
				cancel : function(cancel) {
				}
			});
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
		});
		$("#tabBar2").click(function() {
			$("#tabBar2").addClass("current");
			$("#tabBar1").removeClass("current");
			$("#jieshao").hide();
			$("#dongtai").show();
			$("#loadingbox").show();

			//个人动态
			$("#dongtai").empty();
			curPageNum = 1;
			showDynamicInfo();
			flag = true;
		});

		var personal_impress = function(type) {
			if (isFriend == '0') {
				alert("不是好友不能给商友添加朋友印象！");
			} else {
				layer.open({
					type : 2,
					//skin: 'layui-layer-lan',
					title : '',
					fix : true,
					shadeClose : true,
					closeBtn : 0,
					maxmin : false,
					area : [ '100%', '100%' ],
					content : '${path}/m/my/personal_impress.do?type=' + type
							+ '&userId=' + userId,
					shift : 2,
					scrollbar : false,
					success : function(layero, index) {
						show_zzc(1);
					},
					end : function() {
						hide_zzc(1);
					},
					cancel : function(cancel) {
					}
				});
			}

		}

		var pyyx_add = function(t) {
			if (t == 1) {
				self.location.href = "${path}/m/my/pyyx_add.do";
			}
		}

		var pyyx_edit = function(t) {
			if (t == 1) {
				self.location.href = "${path}/m/my/pyyx_edit.do";
			}
		}

		var add_friend = function() {
			self.location.href = _path
					+ "/wx/businessFriend/applyToAddGroup.do?follow_user="
					+ userId;
		}

		var common_friend = function() {
			self.location.href = "${path}/m/my/commonfriend.do?userId="
					+ userId;
		}

		var say_hello = function() {

			zdy_ajax({
				url : '${path}/m/my/is_noanswer.do',
				type : 'post',
				dataType : 'json',
				data : {
					followUser : userId
				},
				success : function(data, res) {
					if ('0'==res.dataobj) {
						self.location.href = '${path}/m/my/say_hello.do?userId='
								+ userId;
					} else if('1'==res.dataobj)  {
						alert("你已经打过招呼！");
					} else if('2'==res.dataobj)  {
						alert("你已超过今日打招呼数量！");
					}
				}
			});

		}

		/* function showimgs(id, img) {
			if (is_weixn()) {
				var imgs = [];
				$("#img_box_" + id + " img").each(function() {
					imgs.push($(this).data("imgurl"));
				});
				wx.previewImage({
					current : img, // 当前显示图片的http链接
					urls : imgs
				// 需要预览的图片http链接列表
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

		var showDynamicInfo = function() {
			var url = "${path}/m/dynamic/selectMyVisitUserDynamicList.do";
			zdy_ajax({
				url : url,
				data : {
					pageNum : curPageNum,
					pageSize : 10,
					visitUserId : userId
				},
				showLoading : false,
				success : function(dataobj, res) {
					var data = dataobj.dynamicList;
					if (res.code == 0) {
						//alert(data.length);
						//console.log(data);
						if (data.length == 0 || data.length < 10) {
							$(".page_loading").hide();
							$(".page_nodata").show();
						}
						$
								.each(
										data,
										function(i, n) {
											var dynamic = data[i];
											var str = '<div class="msgBox">'
													+ '<div class="msg-l" style="height:0.8rem;width:0.8rem;;background:url('
													+ dynamic.dynamicMap.head_portrait
													+ ') no-repeat center;background-size:100% 100%;border-radius:50%;" id="'
													+ dynamic.dynamicMap.user_id
													+ '">'
													+ '</div>'
													+ '<div class="msg-r">'
													+ '<div class="t" data-id="'
													+ dynamic.dynamicMap.user_id
													+ '" onclick=showUserDetial(this)>'
													+ '<h2 >'
													+ dynamic.dynamicMap.nickname
													+ '</h2>'
													+ '<span class="time">'
													+ dynamic.dynamicMap.minute_flag
													+ '</span>'
													+ '</div>'
													+ '<p class="ph" data-id="'
													+ dynamic.dynamicMap.user_id
													+ '" onclick=showUserDetial(this)>'
													+ dynamic.dynamicMap.job_name
													+ ' /'
													+ dynamic.dynamicMap.com_name
													+ '</p>'
													+ '<p onclick=lookCommentInfo("'
													+ dynamic.dynamicMap.id
													+ '","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'") class="p2">'
													+ dynamic.dynamicMap.dy_content
													+ '</p>';
											if(dynamic.dynamicMap.articleid!= undefined && dynamic.dynamicMap.articleid!= "undefined" && dynamic.dynamicMap.articleid!= null && dynamic.dynamicMap.articleid!= ""){
												str += '<div   onclick=jumparticle("'+dynamic.dynamicMap.articleid+'"); class="item"><div class="it-img"><img src="'+_oss_url+dynamic.dynamicMap.aimgpath+'"/></div><div class="it-cont">'+
												'<h3>'+dynamic.dynamicMap.atitle+'</h3><div class="bot"><span>'+formatDate(new Date(dynamic.dynamicMap.adate))+'</span><span>摘自:'+dynamic.dynamicMap.aclassname+'</span></div></div></div>';
											}
											str +='<div id="img_box_'+dynamic.dynamicMap.id+'">';
											if (dynamic.picList.length != 0
													&& dynamic.picList.length != 1) {
												str += '<div class="imgBox_3">';
												$
														.each(
																dynamic.picList,
																function(k, n) {
																	if (k % 3 == 0
																			&& k != 0) {
																		str += '</div>';
																		str += '<div class="imgBox_3">';
																	}
																	var img200 = imgReplaceStyle(
																			dataobj.ossurl
																					+ dynamic.picList[k].picUrl,
																			'YS200200');
																	var img640 = imgReplaceStyle(
																			dataobj.ossurl
																					+ dynamic.picList[k].picUrl,
																			'YSMAX1024');
																	str += '<div class="insideIMGbox"  onclick=showimgs("'
																			+ dynamic.dynamicMap.id
																			+ '","'
																			+ img640
																			+ '","'+dynamic.dynamicMap.dy_type+'","'+dynamic.dynamicMap.ad_url+'")><span></span><img class="lazy" data-original="'+img200+'" data-imgurl="'+img640+'" /></div>';
																});
												str += '</div>';
											}
											if (dynamic.picList.length == 1) {
												var img640 = imgReplaceStyle(
														dataobj.ossurl
																+ dynamic.picList[0].picUrl,
														'YSMAX1024');
												str += '<div class="imgBox_1" >'
														+ '<img class="lazy" data-original="'
														+ img640
														+ '" data-imgurl="'
														+ img640
														+ '" onclick=showimgs("'
														+ dynamic.dynamicMap.id
														+ '","'
														+ img640
														+ '") />' + '</div>';
											}
											str += '</div>';

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
											var clicked = 'iconfonthead';
											if (dynamic.dynamicMap.like_flag) {
												clicked = 'iconfont-badhead'
											}
											;

											str += '<div class="tubiao">'
													+ '<div class="tb">'
													+ '<i class=" iconfont1" onclick=lookCommentInfo("'
													+ dynamic.dynamicMap.id
													+ '")></i>'
													+ '<span>'
													+ dynamic.commonCount
													+ '</span>'
													+ '</div>'
													+ '<div class="tb">'
													+ '<i class="iconfont '
													+ clicked
													+ '" goodid="'
													+ dynamic.dynamicMap.id
													+ '" onclick=insertLick($(this).attr("goodid"),$(this))>'
													+ dynamic.lickCount
													+ '</i>'
													+
													//'<i class="iconfont '+clicked+'" onclick=insertLick("'+dynamic.dynamicMap.id+'")></i>'+
													//'<span id="lick_'+dynamic.dynamicMap.id+'">'+dynamic.lickCount+'</span>'+
													'</div>'
													+ '<div class="tb">'
													+ '<i class=" iconfont3" onclick=insertForward("'
													+ dynamic.dynamicMap.id
													+ '","'
													+ dynamic.dynamicMap.media_id
													+ '")></i>'
													+ '<span id="forward_'+dynamic.dynamicMap.id+'">'
													+ dynamic.forwardCount
													+ '</span>'
													+ '</div>'
													+ '<div class="tb mri">'
													+ '<i class="iconfont4" onclick=insertGratuity("'
													+ dynamic.dynamicMap.id
													+ '")></i>'
													+ '</div>'
													+ '</div>';

											if (dynamic.commonCount != 0) {
												str += '<div class="pl">'
														+ '<p class="c"><b>'
														+ dynamic.commonUser.nickname
														+ '</b>：<span>'
														+ dynamic.pcomment.cmeDesc
														+ '</span></p>';
												if (dynamic.subCommonCount != 0) {
													str += '<p class="c"><b>'
															+ dynamic.subCommonUser.nickname
															+ '&nbsp;</b>回复<b>&nbsp;'
															+ dynamic.commonUser.nickname
															+ '</b>:<span>'
															+ dynamic.subcomment.cmeDesc
															+ '</span></p>';
												}
												str += '<p class="more" onclick=lookCommentInfo("'
														+ dynamic.dynamicMap.id
														+ '")>查看所有评论</p>'
														+ '</div>';
											}
											str += '</div>' + '</div>';
											$("#dongtai").append(str);
											$('.msg-l').bind('click',
													function() {
														// window.location.href="${path}/m/my/personal_home2.do?userId="+$(this).attr('id');
													});
										});
						$(".iconfont").bind(
								'click',
								function() {
									if ($(this).hasClass('iconfont-bad')) {
										$(this).removeClass('iconfont-bad')
												.addClass('iconfont2');
									} else if ($(this).hasClass('iconfont2')) {
										$(this).removeClass('iconfont2')
												.addClass('iconfont-bad');
									}

								})
						curPageNum++;

						$("img.lazy").lazyload({
							effect : "fadeIn",
							threshold : 400
						//,placeholder : "${path}/resource/img/lazyload.gif"
						});
						isEnd = true;
					}

				},
				error : function(e) {
					//alert(e);
				}
			});

		}

		function initGz(isFollowUser) {
			if (isFollowUser) {
				var gz = '<div class="names" style="text-align:center;width:3rem;margin:0 auto .2rem" onclick=\"deleteGz('
						+ userId + ')\">关注中</div>';
				$("#guanzhuinfo").html(gz);
			} else {
				var gz = '<div class="names" style="text-align:center;width:3rem;margin:0 auto .2rem" onclick=\"addGz('
						+ userId + ')\">+  关注</div>';
				$("#guanzhuinfo").html(gz);
			}
		}

		initGz(isFollowUser);

		function addGz(userId) {
			if (isEmpty(userId)) {
				alert("参数有误");
			} else {
				zdy_ajax({
					url : '${path}/im/m/addMyFollowUser.do',
					type : 'post',
					dataType : 'json',
					data : {
						followUser : userId
					},
					success : function(output) {
						var gz = '<div class="names" style="text-align:center;width:3rem;margin:0 auto .2rem" onclick=\"deleteGz('
								+ userId + ')\">关注中</div>';
						$("#guanzhuinfo").html(gz);
					}
				});
			}
		}
		function deleteGz(userId) {
			if (isEmpty(userId)) {
				alert("参数有误");
			} else {
				confirm(
						"是否取消关注",
						function(t) {
							if (t == 1) {
								zdy_ajax({
									url : '${path}/im/m/delMyFollowUser.do',
									type : 'post',
									dataType : 'json',
									data : {
										followUser : userId
									},
									success : function(output) {
										var gz = '<div class="names" style="text-align:center;width:3rem;margin:0 auto .2rem" onclick=\"addGz('
												+ userId + ')\">+   关注</div>';
										$("#guanzhuinfo").html(gz);
									}
								});
							}
						});
			}
		}
		var commentfriend = function() {
			var url = "${path}/im/m/selCommonFriendList.do";

			zdy_ajax({
				url : url,
				data : {
					aUserId : userId,
					pageNum : 1,
					pageSize : 2
				},
				showLoading : false,
				success : function(dataobj, res) {
					//console.log(dataobj.length);

					if (res.code == 0) {
						if (!dataobj.length) {
							$("#sameFriends").html("暂无共同好友");
							$("#sameFriends").css("text-align", "center");
							return;
						}
						var str = '';
						$.each(dataobj, function(i, n) {
							str += '<div class="friendBox active_A" >'
									+ '<ul onclick="friend_info('
									+ n.friend_user + ');" >'
									+ '	<li style="background-image:url('
									+ n.head_portrait + ')"></li>' + '	<li>'
									+ n.nickname + '</li>' + '	<li>'
									+ n.industry_name + n.job_name + '</li>'
									+ '</ul>' + '</div>'
						});
						str += '<br style="clear:both" />';
					}
					;

					$("#sameFriends").html(str);

				},
				error : function(e) {
					//alert(e);
				}
			});

		}

		var friend_info = function(fid) {
			window.location.href = _path
					+ "/wx/businessFriend/friendInfo.do?follow_user=" + fid;
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
		function insertLick(_id, th) {
			var oldLickCount = th.html();
			if (th.hasClass("iconfonthead")) {
				th.removeClass("iconfonthead").addClass("iconfont-badhead");
			} else {
				th.removeClass("iconfont-badhead").addClass("iconfonthead");
			}
			zdy_ajax({
				url : "${path}/m/dynamic/thumbup.do",
				data : {
					id : _id,
				},
				success : function(data, res) {
					if (res.code == 0) {
						if (res.msg == 0) {
							alert("点赞成功！");
							th.html(oldLickCount * 1 + 1);
						} else {
							alert("点赞已取消！");
							th.html(oldLickCount * 1 - 1);
						}
					}
				},
				error : function(e) {
					//alert(e);
				}
			});
		}

		function insertGratuity(_id) {
			zdy_ajax({
				url : "${path}/m/dynamic/isReward.do",
				data : {
					dynamicId : _id,
				},
				success : function(data, res) {
					if (res.code == 0) {
						if (res.msg == 0) {
							window.self.location = '${path}/m/dynamic/rewardPage.do?dynamicId='
									+ _id + '&fromUrl=' + window.self.location;
						} else {
							alert("你已打赏过此动态");
						}
					}
				},
				error : function(e) {
					//alert(e);
				}
			});
		}

		//转发
		var insertForward = function(_id, mediaId) {
			if (mediaId == undefined || mediaId == "undefined"
					|| mediaId == null || mediaId == "") {
				var oldForwardCount = $("#forward_" + _id).html();
				zdy_ajax({
					url : "${path}/m/dynamic/isForward.do",
					data : {
						dynamicId : _id
					},
					success : function(data, res) {
						if (res.code == 0) {
							if (res.msg == 0) {
								//$("#forward_"+_id).html(oldForwardCount*1+1);
								window.self.location.href = "${path}/m/dynamic/forwardDynamic.do?dynamicId="
										+ _id
										+ "&fromurl="
										+ window.self.location;
							} else {
								alert("你已转发过此动态");
							}
						}
					},
					error : function(e) {
						//alert(e);
					}
				});
			} else {
				alert("语音动态不能转发");
			}
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

		function playMedia(mediaId, dynamicId, price, overtime_flag, k) {
			if (is_weixn()) {
				var url = "${path}/m/dynamic/isPayofMedia.do";
				zdy_ajax({
					url : url,
					data : {
						dynamicId : dynamicId
					},
					showLoading : false,
					success : function(obj, res) {
						//console.log(res.code);
						if (res.code == 0) {
							if (res.msg == 1) {
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
								confirm(
										"确定要支付" + price + "个J币吗？",
										function(t) {
											if (t == 1) {
												//window.self.location="${path}/m/dynamic/paymediaPage.do?dynamicId="+dynamicId;	

												zdy_ajax({
													url : '${path}/m/dynamic/paymedia.do',
													data : {
														dynamicId : dynamicId,
														jcount : price
													},
													success : function(data,
															output) {
														if (output.code == 0) {
															alert_back(
																	output.msg,
																	function() {
																		$(
																				"#"
																						+ mediaId)
																				.html(
																						"该语音已付费");
																	});

														} else {
															alert_back(
																	output.msg,
																	function() {
																	});
														}
													},
													error : function(e) {
													}
												});

											}
										});
							}
						}
					},
					error : function(e) {
						//alert(e);
					}
				});
			} else {
				layer.msg("请在微信中打开此系统！");
			}
		}
		//下载播放录音
		function downloadVoice(serverId, k) {
			wx.downloadVoice({
				serverId : serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
				isShowProgressTips : 1,// 默认为1，显示进度提示
				success : function(res) {
					var download_localId = res.localId; // 返回音频的本地ID
					nowPlayMedia.localId = res.localId;
					//开始播放语音
					$(k).find('img').attr('src',
							'${path}/resource/img/images/yy03.gif')
					wx.playVoice({
						localId : download_localId
					// 需要播放的音频的本地ID，由stopRecsord接口获得
					});
					wx.onVoicePlayEnd({
						success : function(res) {
							//结束播放语音
							$(k).find('img').attr('src',
									'${path}/resource/img/images/yy01.png');
							//在此加入结束动态效果
							nowPlayMedia = {};
						}
					});
				}
			});
		};

		function showUserDetial(obj) {
			//window.location.href="${path}/m/my/personal_home2.do?userId="+$(obj).data("id");
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