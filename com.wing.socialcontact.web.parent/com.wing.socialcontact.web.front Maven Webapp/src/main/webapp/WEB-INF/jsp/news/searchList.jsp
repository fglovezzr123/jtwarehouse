<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">资讯列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>

</head>
<body>
<div class="wrapper">
    <div class="search-box">
				      	<div id="search">搜索</div>
					</div>
	<div class="content" id="contentdiv" style="margin:0">
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#search").bind('click',function(){
		window.location.href = "${path}/m/news/search.do";
	});
	loadCoop(2);
	$("#search").text('${keywords}');
})
//type 1：上拉加载   2：条件查询 
function loadCoop(type){
	if(type==2){
		$("#contentdiv").empty();
	}
	zdy_ajax({
		url: "${path}/m/news/searchNewsList.do",
		data:{
			newsTitle:'${keywords}'
		},
		success: function(data,res){
			if(res.code == 0){
				if(!res.dataobj.length){
					   $('#contentdiv').html('<div  class="searchInfo">未搜索到相关内容</div>');
				}
				var con = "";
				$.each(res.dataobj, function(i, n){
					con += '<div class="content-p"><h2 class="c-title" onclick="openurl(\'${path}/m/news/detailPage.do?id='+n.id+'\')">'+n.newsTitle+'</h2>'+
					'<p class="c-der">'+n.summary+'</p><div class="imgBox2" onclick="openurl(\'${path}/m/news/detailPage.do?id='+n.id+'\')"><img src="'+_oss_url+n.imagePath+'"/>'+
					'</div><div class="info"><span>'+n.updateTime+'</span><span>'+n.source+'</span><span>评论';
					if(n.isHot==2){
						if(n.isFree==2){
							con += '免费';
						}else if(n.isFree==1){
							con += '收费：'+n.charge+'J币';
						}
					}else{
						if(n.commentCount==null||n.commentCount==0){
							con += n.countNum;
						}else{
							con += n.commentCount;
						}
					}
					con += '</span></div></div>';
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