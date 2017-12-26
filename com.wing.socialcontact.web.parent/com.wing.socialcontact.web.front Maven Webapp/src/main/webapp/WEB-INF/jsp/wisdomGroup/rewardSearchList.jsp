<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">悬赏搜索列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>
		<script src="${path}/resource/js/libs/zepto.min.js" type="text/javascript" charset="utf-8"></script>
		<link rel="stylesheet" href="${path}/resource/css/zhuge-list.css?v=${sversion}" />
</head>
<body>
<div class="wrapper">
	
	<%-- <div class="jiutianSearch">
		<input type="text" placeholder='搜索' id="searchNews" />
		<img src="${path}/resource/img/icons/lens.png" />
		<br class="clear" />
	</div> --%>
	<div class="search-box">
      	<div id="search">搜索</div>
	</div>
	<div class="content" id="contentdiv">
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#search").bind('click',function(){
		window.location.href = "${path}/m/reward/search.do";
	});
	loadCoop(2);
//	$("#searchNews").val('${keywords}');
})
//type 1：上拉加载   2：条件查询 
function loadCoop(type){
	if(type==2){
		$("#contentdiv").empty();
	}
	zdy_ajax({
		url: "${path}/m/reward/searchRewardList.do",
		data:{
			title:'${keywords}'
		},
		success: function(data,res){
			if(res.code == 0){
				if(!res.dataobj.length){
					   $('#contentdiv').html('<div  class="searchInfo">未搜索到相关内容</div>');
				}
				var con = "";
				$.each(res.dataobj, function(i, n){
					con += '<div class="items"><div class="i-top"><img src="'+n.headPortrait+'" onclick="open_user_center('+n.createUserId+')"/>'+
					'<p><b>'+n.nickname+'</b>&nbsp;&nbsp;'+n.job+'/'+n.comName+'</p><p class="time">'+n.createTime+'</p></div><h2 onclick="openurl(\'${path}/m/reward/detailPage.do?id='+n.id+'\')">'+
					n.question+'</h2><div class="icon"><span>'+n.reward+'</span><span>'+n.countNum+'</span></div></div>';
				});
				$("#contentdiv").append(con);
			}
		}
	});
}
function openurl(url){
	document.location.href=url;
}
</script>
</body>
</html>