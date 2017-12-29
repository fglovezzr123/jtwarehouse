<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>我发布的话题</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkPk.css" />
		
	</head>

	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="content" id="contentdiv">
					
				</div>
				<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
			</div>
		</div>
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>		
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 10;
var end=false;
$(document).ready(function() {
	loadTopic();
	//滚动加载
	 $(window).scroll(function(){
	   var scrollTop=$(this).scrollTop();
	   var scrollHeight = $(document).height();
     var windowHeight = $(window).height();
     if (scrollTop>=scrollHeight-windowHeight) {
   	     content(); 
     };
	}) ;
});
//type 1：上拉加载   2：条件查询 
function loadTopic(){
	page = 1;
	end=false;
	$("#contentdiv").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	content();
}
function content(){
	if(!end){
		zdy_ajax({
			url: "${path}/m/topic/selMyTopic.do",
			data:{
				page : page,
				size:pageSize
				},
			success: function(data,res){
				if(res.code == 0){
					var s='';
					if(page==1 &&!res.dataobj.topicList.length){
						   //$('#contentdiv').html('<div  class="searchInfo">未搜索到相关内容</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.topicList.length==0 || res.dataobj.topicList.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					$.each(res.dataobj.topicList, function(i, n){
						s += '<div class="contList active_A" onclick="openurl(\'${path}/m/topic/detailPage.do?id='+n.id+'\')"><div class="contList-l"><h2>'+n.topicDesc+'</h2>'+
						'<span><i></i><b>'+n.redCount+'人</b></span><span><i></i><b>'+n.blueCount+'人</b></span></div>'+
						'<div class="contList-r"><span><b>'+n.countNum+'</b>条评论';
						s += '</span><i class="iconfont" >&#xe605;</i></div></div>';
					});
					$("#contentdiv").append(s);
					page++;
					
				}
			}
		}); 
	}
}
function openurl(url){
	document.location.href=url;
}

</script>

</html>