<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">话题列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkPk.css" />
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
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/topic/search.do";
	});
	loadCoop(2);
	$("#search").html('${keywords}');
})
//type 1：上拉加载   2：条件查询 
function loadCoop(type){
	if(type==2){
		$("#contentdiv").empty();
	}
	zdy_ajax({
		url: "${path}/m/topic/searchTopicList.do",
		data:{
			titles:'${keywords}'
		},
		success: function(data,res){
			if(res.code == 0){
				if(!res.dataobj.length){
					   $('#contentdiv').html('<div  class="searchInfo">未搜索到相关内容</div>');
				}
				var con = "";
				$.each(res.dataobj, function(i, n){
					if(n.isAd==1){
						con += '<div class="contList active_A" onclick="openurl(\''+n.url+'\')">';
					}else{
						con += '<div class="contList active_A" onclick="openurl(\'${path}/m/topic/detailPage.do?id='+n.id+'\')">';
					}
					con += '<div class="contList-l"><h2>'+n.topicDesc+'</h2>'+
					'<span><i></i><b>'+n.redCount+'人</b></span><span><i></i><b>'+n.blueCount+'人</b></span></div>'+
					'<div class="contList-r"><span><b>'+n.countNum+'</b>条评论</span><i class="iconfont" >&#xe605;</i></div></div>';
				});
				$("#contentdiv").append(con);
			}
		}
	});
}
function openurl(url){
	if(url!=''&&url!=null){
		document.location.href=url;
	}
}
</script>
</body>
</html>