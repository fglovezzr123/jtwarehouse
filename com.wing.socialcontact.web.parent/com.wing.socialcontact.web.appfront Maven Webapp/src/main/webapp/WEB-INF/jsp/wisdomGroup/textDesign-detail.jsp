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

<script type="text/javascript" src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.0/vhallSDK.js"></script>
<style>
.zt {
	font-size: .24rem;
	height: .40rem;
	line-height: .40rem;
}
.wrapper{
	 padding-top:0.24rem;
	 margin:0;
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
#vedios video{
  height:auto!important;
}
#articleBlock+p{
	text-indent: 2em!important;
    font-size: .32rem!important;
}
#pager{
	position: fixed;
	bottom:0.9rem;
	right:0.3rem;
	display:none;
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
	padding:0.08rem 0.14rem;
	text-decoration: none; 
	/*border: 1px solid #dddddd;*/;
	border-left-width: 0;
	list-style: none;
	text-align:center;
	background-color: #eee;
	color: #888;
	margin: 0 0.06rem 0 0;
	-webkit-border-radius: 0.06rem;
	-moz-border-radius: 0.06rem;
	border-radius: 0.06rem;
}
#pager ul.pages li.pgNext {
}
#pager ul.pages li.pgEmpty {
	display: none 
	/*border:1px solid #eee;
     color:#eee;
   */;
}
#pager ul.pages li.pgCurrent {
	color: #fff;
	font-weight: 700;
	background-color: #29c06d;
}
.top{
	position:fixed;
	right:0.3rem;
	bottom:2rem;
	line-height:.50rem;
	font-size:.24rem;
	color:#fff;
	background:rgba(22,3,33,0.5);
	width:.60rem;
    text-align:center;
}
._pize{
    position:fixed;
	right:0.3rem;
	bottom:1rem;
	line-height:.50rem;
	font-size:.24rem;
	color:#fff;
	background:rgba(22,3,33,0.5);
	text-align:center;
	min-width:.60rem
}
#tocollect img {
	height: 0.3rem;
	width: 0.3rem;
}
span{
 white-space: normal!important;
}
</style>

</head>
<body>
	<div class="wrapper" id="alldetail"></div>
	<div class='share' id="share">
		<div><div class="active_A" style="width:100%;height:100%;background:white" id="tocollect"></div></div>
		<div class="active_A" onclick="fx();">分 享</div>
		<div class="active_A" onclick="zf();">转发</div>
		<br class="clear" />
	</div>
	<div class="com-backdrop" style="display: none;"></div>
	<div class="tips-s-img" style="display: none;"></div>
	<div id="pager"><ul class="pages"></ul></div>
	<div class="top" style="display: none;">顶部</div>
	<div class="_pize"></div>
	<script>
		var isOk = false;
		var id = '${id}';
		var type ='${type}';
		var cOrn = false;
		var pagenumber= 1;
		var pagecount = 3
		var botHeight= 0.9*parseInt($(window).width()/7.5);
		var client = $(window).height()-botHeight;
		var storage=window.localStorage;
		$(function(){
			if (window.localStorage) {
				  localStorage.setItem("libraryzfurl",_path+"/library/m/librarydetail.do?type=2&id=" + id);
			}
		})
		/* $(document body).on('click',function(){$('#pager').hide()})
		  */
		  $('#alldetail').on('click',function(){
			  $('#pager').hide()
			  $('._pize').show()
			  $('.top').show()
	       })
		/*
			文章转发动态
		*/
		
		function zf(){
			window.location.href= _path+"/library/m/dynamiczf.do?type=1&id="+id; 
		}
		
		//回到顶部
	    $('.top').on('click',function(){
	    	$(window).scrollTop(0)
	    })
		 //滚动位置
	     $(window).scroll(function(){
	    	 var str = window.location.href;
	 		 str = str.substring(str.lastIndexOf('/')+1)
               var _top = $(window).scrollTop()
               var pagenumber = Math.ceil(_top/client)+1;
               $('._pize').text(pagenumber+'/'+pagecount)
               /* console.log(pagenumber);  */
               $('.page-number').eq(pagenumber).addClass('pgCurrent').siblings().removeClass('pgCurrent')
               var obj ={str:str,pagenumber:pagenumber,top1:_top}
               storage.setItem(str,JSON.stringify(obj))
         }) 
		//通过文章ID获取文章
		zdy_ajax({
			url : _path + "/library/m/detail.do",
			showLoading : false,
			data : {
				'libraryid' : id
			},
			success : function(data, res) {
				console.log(data);
				var str = "";
				id = data.id;
				if (data.iscollection) {
					cOrn = data.iscollection;
					$('#tocollect').text('');
					$('#tocollect').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
				}else{
					$('#tocollect').text('');
					 $('#tocollect').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
				};
				//设置只显示一级分类   临时改
				var classname = data.tag;
				classname =classname.substr(0, classname.indexOf('-'));
				str += '<h5>' + data.title + '</h5>';
				//str += '<div class="zt"><span>' + data.tag + '</span><span>'
				str += '<div class="zt"><span>' + data.onename + '</span><span>'+ data.readtimes + '人阅读</span><span>'+ formatDate(new Date(data.createTime))+ '</span></div>'
				 if (!isEmpty(data.path)) {
					str += '<div class="zt" style="float:right;"><a style="float:right;color:0000FF" href="'+_oss_url+data.path+'">'+ data.filename + '</a></div>'
				}
				str += '<div class="vimg" id="vedios"></div></br><p id="articleBlock">'+ data.content + '</p>';
				$("#alldetail").append(str);
				//分享设置
	        	var _title = "文章详情";
				var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
				if(data.title.length > 0){
					_title = data.title;
				}
				if(data.imgpath.length > 0){
					_imgUrl =_oss_url+ data.imgpath;
				}
				var _link = home_path+_path+"/library/m/librarydetail.do?type=2&id=" + id;
				if (type != 1) {
					$("#share").remove();
				}else{
					wxsharefun(_link,_title,_imgUrl);
				}
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
				} else {
					$("#vedios").hide();
				} 
				 pagecount =Math.ceil($('#alldetail').height()/client)
			   /*   console.log(pagecount) */
			     pizeCont(pagecount)
			     if(pagecount==1){
			    	  $('._pize').hide()
			    	  $('.top').hide()
			     }else{
			    	  $('._pize').show()
			    	  $('.top').show()
			     }
			     $('._pize').text('1/'+pagecount)
			     var str = window.location.href;
                  str = str.substring(str.lastIndexOf('/')+1)
				 var json=storage.getItem(str);
				 if(json){
					var jsonobj = JSON.parse(json)
					 pagenumber= jsonobj.pagenumber
					 $(window).scrollTop(jsonobj.top1)
				}
			},
			error : function(data) {
			}
		});
       function pizeCont(pagecount){
    	   for(var i = 0;i<pagecount;i++){
    		   var str = '<li class="page-number">'+(i+1)+'</li>'
    		   $('.pages').append(str)
    	   }  
    	   $('.page-number').on('click',function(){
        	   var index = $(this).index()
        	    $('#pager').hide()
        	    $('.top').show()
        	    $('._pize').show()
        	   $(this).addClass('pgCurrent').siblings().removeClass('pgCurrent')
        	   $(window).scrollTop(index*client)
           })
       }
       //总页数处理
       $('._pize').on('click',function(){
    	   $(this).hide()
    	   var pize = $(this).text().split('/')[0]
    	   console.log(pize)
    	   $('.page-number').eq(pize-1).addClass('pgCurrent').siblings().removeClass('pgCurrent')
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
			};
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
	</script>
</body>
</html>