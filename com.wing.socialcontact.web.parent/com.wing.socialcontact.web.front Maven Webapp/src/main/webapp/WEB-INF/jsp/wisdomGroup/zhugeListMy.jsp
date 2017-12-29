<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">我的问答列表</title>
        <link rel="stylesheet" href="${path}/resource/css/zhuge-list.css" />
        <style type="text/css">
        	
        </style>
    </head>
    <body>
    	<ul class="title">
    		<li class="tit-bor" onclick="initloadpage('2')">最新</li>
    		<li onclick="initloadpage('1')">高悬榜</li>
    		<li onclick="initloadpage('3')">最热</li>
    	</ul>
    	<div id="contentdiv"></div>
    	<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
    </body>
<script> 

var page = 1;
var pageSize = 10;
var end=false;
var tid = "2";
$(document).ready(function() {
	$('.title li').on('click', function() {
		var index = $(this).index();
		$(this).addClass('tit-bor').siblings().removeClass('tit-bor')
	})
	//滚动加载
	 $(window).scroll(function(){
	     var scrollTop=$(window).scrollTop();
	     var scrollHeight = $(document).height();
         var windowHeight = $(window).height();
         if (scrollTop>=scrollHeight-windowHeight) {
        	 if(!end){
        		 content(tid); 
        	 }
         };
         if(scrollTop>=200){
				$(".title").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".title").addClass("fixed");
			}else{
				$(".title").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".title").removeClass("fixed");
			}
	 }) ;
	initloadpage(tid);
});

function initloadpage(_tid){
	page = 1;
	end=false;
	tid = _tid;
	$("#contentdiv").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	content(_tid);
}
var initflag = true;
function content(tid){
	console.log("content");
	if(!end&&initflag){
		initflag=false;
		zdy_ajax({
			url: "${path}/m/reward/selMyRewardList.do",
			showLoading:false,
			data:{
				type:tid,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					if(page==1 && !res.dataobj.length){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					var con = "";
					$.each(res.dataobj, function(i, n){
						con += '<div class="items"><div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.createUserId+')"/>'+
						'<p><b>'+n.nickname+'</b>&nbsp;&nbsp;'+n.job+'/'+n.comName+'</p><p class="time">'+n.createTime+'</p></div><h2 onclick="openurl(\'${path}/m/reward/myDetailPage.do?id='+n.id+'\')">'+
						n.question+'</h2><div class="icon"><span>'+n.reward+'</span><span>'+n.countNum+'</span></div></div>';
					});
					$("#contentdiv").append(con);
					page++;
					initflag=true;
				}
			}
		});
	}
}
function openurl(url){
	document.location.href=url;
}
//查看他人主页
var open_user_center=function(userId){
	self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
}
</script> 
</html>