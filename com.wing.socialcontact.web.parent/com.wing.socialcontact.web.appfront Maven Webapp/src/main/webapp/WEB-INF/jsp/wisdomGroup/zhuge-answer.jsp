<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">诸葛解惑</title>
        <link rel="stylesheet" href="${path}/resource/css/zhuge-answer.css" />
        
    </head>
    <body>
    <div style="width: 100%; height:100%;">
        <div class="wrapper">
        <div class="scrollbar" id="scrollbar">
            <div class="search-box">
				      	<div id="search">搜索</div>
					</div>
             <jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
					 	<jsp:param name="bannerid" value="${bannerid}" />
			 </jsp:include>
             <div class="nav" id="cListdiv">
                   
             </div>
            <ul class="title">
    		<li class="tit-bor" onclick="initloadpage('2')">最新</li>
    		<li onclick="initloadpage('1')">高悬榜</li>
    		<li onclick="initloadpage('3')">最热</li>
    	    </ul>
             <div class="zg-item" id="contentdiv">
                 
             </div>
             <div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
		</div>
    </div>
    <div class="btms">
        <div class="active_A" onclick="openurl('${path}/m/reward/myCenterRewardPage.do')">我的悬赏</div>
        <div class="active_A" onclick="reward_add();">发布悬赏</div>
        <br class="clear"/>
    </div>
    </div>
       <script src="${path}/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
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
	zdy_ajax({
		url: "${path}/m/reward/selClassList.do",
	    showLoading:false,
		data:{},
		success: function(data,res){
			if(res.code == 0){
				var c = "";
				//c += '<a href="javascript:void(0)" class="active_A" onclick="openurl(\'${path}/library/m/live/listPage.do?type=1\')"><img src="http://tianjiu.oss-cn-beijing.aliyuncs.com/tianjiu/news/2017/8/19/a8d9fa29-9cd0-47a9-b270-625a369c07a5.jpg?x-oss-process=style/YS300200"/><span>俊卿解惑</span></a>';
				var oss = res.dataobj.ossurl;
				$.each(res.dataobj.classList, function(i, n){
					if(i<7) {
					c += '<a href="javascript:void(0)" class="active_A" onclick="openurl(\'${path}/m/reward/listPage.do?voType='+n.id+'\')"><img src="'+oss+n.imagePath+'"/><span>'+n.className+'</span></a>';
					}
				});
				c += '<a href="javascript:void(0)" onclick="class_more();" class="active_A"><img src="${path}/resource/img/icons/boss5.png"/><span>更多</span></a>';
				$("#cListdiv").append(c);
				initloadpage(tid);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/reward/search.do";
	});
	
});
//更多
var class_more=function(){
	var url = '${path}/m/reward/classPage.do';
	window.location.href=url;
	if (window.localStorage) {
		  localStorage.setItem("history_url", "${path}/m/reward/indexPage.do");
	}
}
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
			url: "${path}/m/reward/selRewardList.do",
			showLoading:false,
			data:{
				type:tid,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				/* console.log(data) */
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
						con += '<div class="items"><div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.createUserId+')"/><p><b>'+n.nickname+'</b>&nbsp;&nbsp;'+n.job+'/'+n.comName+'</p>'+
						'<p class="time">'+n.createTime+'</p></div><h2 onclick="openurl(\'${path}/m/reward/detailPage.do?id='+n.id+'\')">'+n.question+'</h2><div class="icon">'+
						'<span>'+n.reward+'</span><span>'+n.countNum+'</span></div></div>';
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
//发布悬赏
var reward_add=function(){
	var url = '${path}/m/reward/addRewardPage.do';
	window.location.href=url;
	if (window.localStorage) {
		  localStorage.setItem("history_url", "${path}/m/reward/indexPage.do");
	}
}
//查看他人主页
var open_user_center=function(userId){
	self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
}

</script>
</html>