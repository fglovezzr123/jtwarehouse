<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">合作洽谈列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/cooperateList.css?v=${sversion}" />

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
	<div class="content" id="contentdiv" style="margin-top:0">
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#search").bind('click',function(){
		window.location.href = "${path}/m/business/search.do";
	});
	loadCoop(2);
	$("#search").val('${keywords}');
})
//type 1：上拉加载   2：条件查询 
function loadCoop(type){
	if(type==2){
		$("#contentdiv").empty();
	}
	zdy_ajax({
		url: "${path}/m/business/searchCoopList.do",
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
					con += '<div class="cooperate-item active_A" onclick="openurl(\'${path}/m/business/detailPage.do?type=1&id='+n.id+'\')"><div class="cooperate-item-l">';
					if(n.appealType==1){
					con += '<div style="background:url(${path}/resource/img/icons/gong.png) no-repeat center;background-size:100% 100%"></div>';/* 供 */
					}else if(n.appealType==2){
						con += '<div class="asking" style="background:url(${path}/resource/img/icons/qiu.png) no-repeat center;background-size:100% 100%"></div>';/* 求 */
					}
					con += '</div><div class="cooperate-item-r"><h3>'+n.titles+'</h3><div>'+
					'<span>'+n.createTime+'</span><span>'+n.lookCount+'人浏览</span>';
					if(n.reward!=0){
						con += 	'<span>悬赏：'+n.reward+'J币</span>';
					}
					con += '<br class="clear"/></div></div><br class="clear"/></div>';
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