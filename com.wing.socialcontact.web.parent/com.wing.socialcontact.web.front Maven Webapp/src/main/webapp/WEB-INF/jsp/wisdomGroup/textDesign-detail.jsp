<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">文章详情</title>
<link rel="stylesheet" href="${path}/resource/css/text-design.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
<script type="text/javascript" src="http://cnstatic01.e.vhall.com/jssdk/dist/2.3.0/vhallSDK.js"></script>
<style>
 .zt {
		font-size: .24rem;
		height: .40rem;
		line-height: .40rem;
	}
	
	.wrapper {
		padding-top: .24rem;
		margin: 0;
	}
	.btn{
	  display:none;
	}
	.zt span:nth-child(1) {
		float: left;
		width: 40%;
		text-align: left;
	}
	
	.zt span:nth-child(2) {
		float: left;
		width: 20%;
		text-align: center;
	}
	
	.zt span:nth-child(3) {
		float: right;
		width: 40%;
		text-align: right;
	}
	#articleBlock+p {
		text-indent: 2em !important;
		font-size: .32rem !important;
	}
	
	#pager {
		position: fixed;
		bottom: 0.9rem;
		right: 0.3rem;
		display: none;
	}
	
	#pager ul.pages {
		display: block;
		border: none;
		text-transform: uppercase;
		font-size: 0.24rem;
		/* margin: 10px 0 50px; */
		padding: 0;
	}
	
	#pager ul.pages li {
		/* float: left; */
		padding: 0.08rem 0.14rem;
		text-decoration: none;
		/*border: 1px solid #dddddd;*/;
		border-left-width: 0;
		list-style: none;
		text-align: center;
		background-color: #eee;
		color: #888;
		margin: 0 0.06rem 0 0;
		-webkit-border-radius: 0.06rem;
		-moz-border-radius: 0.06rem;
		border-radius: 0.06rem;
	}
	#pager ul.pages li.pgEmpty {
		display: none 
	}	
	#pager ul.pages li.pgCurrent {
		color: #fff;
		font-weight: 700;
		background-color: #29c06d;
	}
	.top {
		position: fixed;
		right: 0.3rem;
		bottom: 2rem;
		line-height: .50rem;
		font-size: .24rem;
		color: #fff;
		background: rgba(22, 3, 33, 0.5);
		width: .60rem;
		text-align: center;
	}
	
	._pize {
		position: fixed;
		right: 0.3rem;
		bottom: 1rem;
		line-height: .50rem;
		font-size: .24rem;
		color: #fff;
		background: rgba(22, 3, 33, 0.5);
		text-align: center;
		min-width: .60rem
	}
	
	#tocollect img {
		height: 0.3rem;
		width: 0.3rem;
	}
	
	span {
		white-space: normal !important;
	}
	.tubiao {
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: .30rem;
		color: #666;
		height: .50rem;
	}
	
	.tubiao .dz {
		margin-left: 5.0rem;
	}
	
	.tubiao .mri {
		margin-right: .1rem;
	}
	
	.tubiao .iconfont {
		font-size: .36rem;
	}
	
	.tubiao .iconfont2 {
		display: inline-block;
		width: 0.32rem;
		height: 0.29rem;
		background-image: url(${path }/resource/img/icons/good.jpg);
		background-size: 100%;
		background-repeat: no-repeat;
	}
	
	.tubiao .iconfont-bad {
		display: inline-block;
		width: 0.32rem;
		height: 0.29rem;
		background-image: url(${path }/resource/img/icons/bad.jpg);
		background-size: 100%;
		background-repeat: no-repeat;
	}
	
	.tubiao .iconfont4 {
		display: inline-block;
		width: 0.31rem;
		height: 0.31rem;
		background-image: url(${path }/resource/img/icons/shang.jpg);
		background-size: 100%;
		background-repeat: no-repeat;
	}
	
	#vediosdiv {
		width: 100%;
		position: fixed;
	    background: #fff;
	    left: 0px;
	    top: 0;
	    padding: 0.3rem;
       box-sizing: border-box;
       z-index:10000;
	}
	.imgfg {
		position: absolute;
		z-index: -1;
	    height: 3.50rem;
	    width: 6.9rem;
	    left: 0.3rem;
	    top: 0.3rem;
	}
	
	.vimg {
        width:100%;
		height: 3.5rem;
	    object-fit: fill;
	}
	.vimg video{
	 object-fit: fill;
	}
	.share1{
		width: 100%;
	    height: 0.9rem;
	    position: fixed;
	    bottom: 0;
	    left: 0;
	    background: white;
	    font-size: 0.3rem;
	    display:flex;
	    align-items: center;
	}
	.share1 div{
	  height:100%;
	  flex:1;
	  text-align:center;
	  line-height: 0.9rem;
	}
	.bar{
		width: 100%;
		height: 3.5rem;
		position: absolute;
		left: 0;
		top: 0;
		background:rgba(0,0,0,0.5);
		z-index: 100;
    }
.bar img{
	width: .6rem;
	height: .6rem;
	position: absolute;
	left: 50%;
	top: 50%;
	margin-top: -0.3rem;
	margin-left: -0.3rem;
	
}
</style>

</head>
<body>
	<div class="wrapper" id="alldetail" style="display:none">	 
	 <jsp:include page="../commons/include_comment.jsp">
		<jsp:param name="id" value="${id}" />
		<jsp:param name="cmeType" value="7" />
	</jsp:include><!-- cmeType 1:资讯   2：合作 3：话题  4：活动 5:动态-->
	<div class='share1' id="share">
		<div class="active_A" id="tocollect" ></div>
		<div class="active_A fx" onclick="fx();" style="background:#0f88eb;color:#fff;">分 享</div>
		<div class="active_A zf" onclick="zf();">转发</div>
		<div class="active_A" onclick="comment_add();" id="pinglun" style="background:#0f88eb;color:#fff;">评论</div>
	</div>
	<div class="com-backdrop" style="display: none;"></div>
	<div class="tips-s-img" style="display: none;"></div>
	<div id="pager">
		<ul class="pages"></ul>
	</div>
	<div class="top" style="display: none;">顶部</div>
	<div class="_pize"></div>
	</div>
	<script>
		var isOk = false;
		var id = '${id}';
		var type = '${type}';
		var _cmeType = "7";
		var cOrn = false;
		var pagenumber = 1;
		var pagecount = 3
		var botHeight = 0.9 * parseInt($(window).width() / 7.5);
		var padd = 0.24 * parseInt($(window).width() / 7.5);
		var vHeight = 0.6* parseInt($(window).width() / 7.5);
		var client = $(window).height() - botHeight;
		var storage = window.localStorage;
		var comment;
		var thumbup;
		var reward;
		if (window.localStorage) {
			localStorage.setItem("libraryzfurl", _path+ "/library/m/librarydetail.do?type=1&id=" + id);
		}
		$('#alldetail').on('click', function() {
			$('#pager').hide()
			$('._pize').show()
			$('.top').show()
		})
		function zf() {
			window.location.href = _path + "/library/m/dynamiczf.do?type=1&id="
					+ id;
		}

		//回到顶部
		$('.top').on('click', function() {
			$(window).scrollTop(0)
		})
		//滚动位置
		$(window).scroll(function() {
			var str = window.location.href;
			str = str.substring(str.lastIndexOf('/') + 1)
			var _top = $(window).scrollTop()
			var pagenumber = Math.ceil(_top / client) + 1;
			if (pagenumber >= pagecount) {pagenumber = pagecount}
			$('._pize').text(pagenumber + '/' + pagecount)
			$('.page-number').eq(pagenumber).addClass('pgCurrent').siblings().removeClass('pgCurrent')
			var obj = {str : str,pagenumber : pagenumber,top1 : _top}
			storage.setItem(str, JSON.stringify(obj))
		})
		//通过文章ID获取文章
		zdy_ajax({
			url : _path + "/library/m/detail.do",
			showLoading : false,
			data : {
				'libraryid' : id
			},
			success : function(data, res) {
				/* console.log(data); */
				var str = "";
				id = data.id;
				var rewardCount = data.rewardCount;
				var tpCount = data.tpCount;
				if (data.iscollection) {
					cOrn = data.iscollection;
					$('#tocollect').text('');
					$('#tocollect').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
				} else {
					$('#tocollect').text('');
					$('#tocollect').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
				}
				;
				//评论
				comment = data.comment;
				reward = data.reward;
				thumbup = data.thumbup;
				//设置只显示一级分类   临时改
				var classname = data.tag;
				 classname = classname.substring(0, classname.indexOf('-'));
				var clicked = 'iconfont2';
				if (data.like_flag) {
					clicked = 'iconfont-bad';
				};
				str += '<div id="vediosdiv">';
				 if (!isEmpty(data.imgpath)) {
					str += '<img class="imgfg" id = "imgdiv" src="'+_oss_url+ data.imgpath + '"/>';
				 } 
				str += '<div class="vimg" id="vedios" onclick="vediosStart();"></div>';
				if (!isEmpty(data.audioFile)) {
					str += '<audio  controls="controls" id="audio"><source src="'+_oss_url+data.audioFile+'" /></audio>';
				}
				str += '</div><h5>' + data.title + '</h5>';
				str += '<div class="zt"><span>' + data.onename
						+ '</span><span>' + data.readtimes + '人阅读</span><span>'
						+ formatDate(new Date(data.createTime))
						+ '</span></div>';
				str += '<div class="tubiao">'
						+ '<div class="tb dz">'
						+ '<i class="iconfont '
						+ clicked
						+ '" onclick=insertLick(this,"'
						+ id
						+ '")></i>'
						+ '<span style="background: url(); padding-left:0.1rem" id="lick_'
						+ id
						+ '">'
						+ tpCount
						+ '</span>'
						+ '</div>'
						+ '<div class="tb mri">'
						+ '<i class="iconfont4" onclick=reward_add()></i>'
						+ '<span style="background: url(); padding-left:0.1rem" >'
						+ rewardCount + '</span>' + '</div>' + '</div>';
				if (!isEmpty(data.path)) {
					str += '<a style="color:0000FF;font-size: .28rem;line-height: .36rem;text-align: right;width: 100%;display: block;" href="'+_oss_url+data.path+'">'+ data.filename+'</a>'
				}
				str += '<p id="articleBlock">' + data.content + '</p>';
				 $("#alldetail").prepend(str).show();
				  var obj = res.dataobj.signObj;
					if (obj != null) {
							isOk = true;
					 }
				 if (isOk) {
						VHALL_SDK.init({
							account : obj.account,
							email : obj.email,
							username : obj.username,
							roomid : obj.roomid,
							app_key : obj.app_key,
							signedat : obj.signedat,
							sign : obj.sign,
							facedom : '',
							textdom : '',
							videoContent : '#vedios',
						});
						 VHALL_SDK.on('vhall_record_history_chat_msg',
								function(msg) {
									alert(JSON.stringify(msg))
								});
						VHALL_SDK.on('ready', function() {
							
						});
						VHALL_SDK.on('error', function(msg) {
						});
						VHALL_SDK.on("playerReady", function() {
						});
						VHALL_SDK.on("canPlayLines", function() {
						});
					} else {
						$("#vedios").hide();
						$("#imgdiv").hide();
					}
				    if($("#vediosdiv").height()){
				    	 console.log(1)
				    	 $(".wrapper").css({paddingTop:$("#vediosdiv").height()+vHeight+5})
				    }else{
				    	$(".wrapper").css({paddingTop:".24rem"})
				    	$("#vediosdiv").hide()
				    }
				   
				//分享设置
				var _title = "文章详情";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if (data.title.length > 0) {
					_title = data.title;
				}
				if (data.imgpath.length > 0) {
					_imgUrl = _oss_url + data.imgpath;
				}
				var _link = home_path + _path+ "/library/m/librarydetail.do?type=2&id=" + id;
				if (type != 1) {
					$("#share").remove();
				} else {
					wxsharefun(_link, _title, _imgUrl);
				}
				
				pagecount = Math.ceil(($('#alldetail').height() + padd)/ client)
				pizeCont(pagecount)
				if (pagecount == 1) {
					$('._pize').hide()
					$('.top').hide()
				} else {
					$('._pize').show()
					$('.top').show()
				}
				$('._pize').text('1/' + pagecount)
				var str = window.location.href;
				str = str.substring(str.lastIndexOf('/') + 1)
				var json = storage.getItem(str);
				if (json) {
					var jsonobj = JSON.parse(json)
					pagenumber = jsonobj.pagenumber
					$(window).scrollTop(jsonobj.top1)
				}
			},
			error : function(data) {
			}
		});
		function pizeCont(pagecount) {
			for (var i = 0; i < pagecount; i++) {
				var str = '<li class="page-number">' + (i + 1) + '</li>'
				$('.pages').append(str)
			}
			$('.page-number').on('click',function() {
				var index = $(this).index()
				$('#pager').hide()
				$('.top').show()
				$('._pize').show()
				$(this).addClass('pgCurrent').siblings().removeClass(
						'pgCurrent')
				$(window).scrollTop(index * client)
			})
		}
		//总页数处理
		$('._pize').on('click',function() {
				$(this).hide()
				var pize = $(this).text().split('/')[0]
				console.log(pize)
				$('.page-number').eq(pize - 1).addClass('pgCurrent').siblings().removeClass('pgCurrent')
				$('.top').hide()
				$('#pager').slideDown('slow')
		})

		//添加或取消收藏
		$('#tocollect').click(function() {
			                if (!cOrn) {
								zdy_ajax({
									url : _path + "/mycollection/m/add.do",
									showLoading : false,
									data : {
										'id' : id,
										'type' : 1
									},
									success : function(data, bc) {
										console.log(bc)
										layer.msg("收藏成功");
										cOrn = true;
										$('#tocollect').text('');
										$('#tocollect').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
									},
									error : function(data) {
									}
								});
							} else if (cOrn) {
								zdy_ajax({
									url : _path + "/mycollection/m/del.do",
									showLoading : false,
									data : {
										'id' : id,
										'type' : 1
									},
									success : function(data, bc) {
										layer.msg("已取消收藏");
										cOrn = false;
										$('#tocollect').text('');
										$('#tocollect').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
									},
									error : function(data) {
									}
								});
							}
							;
						});
		$(function() {
			//绑定遮罩层关闭事件
			$(".com-backdrop").click(function() {
				var obj = $(this);
				$(".tips-s-img").slideUp("slow", function() {
					obj.hide();
				});
			});
			$(".tips-s-img").click(function() {
				$(".com-backdrop").click();
			});
		});

		//分享
		var fx = function() {
			if (is_weixn()) {
				fx_flag = true;
				$(".com-backdrop").show();
				$(".tips-s-img").slideDown("slow");
			} else {
				layer_msg("分享功能只能在微信端使用");
			}
		};
		function formatDate(now) {
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			if (month < 10) {
				month = "0" + month;
			}
			var day = now.getDate();
			if (day < 10) {
				day = "0" + day;
			}
			var hour = now.getHours();
			if (hour < 10) {
				hour = "0" + hour;
			}
			var minute = now.getMinutes();
			if (minute < 10) {
				minute = "0" + minute;
			}
			var second = now.getSeconds();
			if (second < 10) {
				second = "0" + second;
			}
			return year + "/" + month + "/" + day + " " + hour + ":" + minute;
		}
		//点击评论按钮
		var comment_add = function() {
			if (comment == '2') {
				layer.msg("评论功能关闭！");
				return;
			}
			self.location.href = '${path}/m/comment/comment_add.do?fkId=' + id+ '&cmeType=' + _cmeType;
		}
		//点击打赏
		var reward_add = function() {
			if (reward == '2') {
				layer.msg("打赏功能关闭！");
				return;
			}
			self.location.href = '${path}/library/m/rewardPage.do?fkId=' + id;
		}
		//点赞
		function insertLick(obj, _id) {
			if (thumbup == '2') {
				layer.msg("点赞功能关闭！");
				return;
			}
			var oldLickCount = $("#lick_" + _id).html();
			zdy_ajax({
				url : "${path}/library/m/thumbup.do",
				data : {
					id : _id,
				},
				success : function(data, res) {
					//console.log(res);
					if (res.code == 0) {
						if (res.msg == 0) {
							$(obj).css({"background" : "url(${path}/resource/img/icons/bad.jpg) no-repeat left center","background-size" : "0.32rem 0.30rem"});
							alert("点赞成功！");
							$("#lick_" + _id).html(oldLickCount * 1 + 1);
						} else {
							$(obj).css({"background" : "url(${path}/resource/img/icons/good.jpg) no-repeat left center","background-size" : "0.32rem 0.30rem"});
							alert("点赞已取消！");
							$("#lick_" + _id).html(oldLickCount * 1 - 1);
						}
					}
				},
				error : function(e) {
					//alert(e);
				}
			});
		}
		function vediosStart() {
			$('.imgfg').remove();
			/* $(".bar").remove(); */
			
			//视频开始播放
		}

		
	</script>
</body>
</html>