<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">项目联营</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/callon.css?v=${sversion}" />
<style>
  .search-box #search{
      margin-top:0.12rem;
   }
</style>
</head>
<body>
<div class="wrapper" style="background:none;">
	<div class="search-box">
		<input type="text" name="search" id="search" placeholder="搜索" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
	</div>
	<input type="hidden" id="keywords" value="${keywords}"/>
	<c:choose>
		<c:when test="${empty list or list.size() eq 0}">
			<div class="dropload-noData" style="text-align:center;">暂无数据</div>
		</c:when>
		<c:otherwise>
			<div class="pro-item1 cur" id="prj1" pageIndex="1" pages="${pages}">
				<c:forEach var="item" items="${list}">
					<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
						<div>
							<p><strong>[${item.prjTypeName}]</strong>${item.prjName}</p>
							<p style="top:30px;text-overflow:ellipsis;white-space:wrap;overflow:hidden;">${item.prjDesc}</p>
						</div>
						<div style="background-image: url('${item.imgUrl}');"></div>
						<br class="clear" />
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
$(function() {
  	var deviceWidth = document.documentElement.clientWidth;
  	document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
	$('.wrapper').dropload({
	  	  distance: 50,
	  	  scrollArea : window,
	    	domDown : {
	           domClass   : 'dropload-down',
	           domRefresh : '<div class="dropload-refresh">↑上拉刷新</div>',
	           domUpdate  : '<div class="dropload-update">↑释放更新</div>',
	           domLoad    : '',
	           domNoData  : '<div class="dropload-noData">暂无数据</div>'
	       },
	       loadDownFn : function(me){
    	   		load(function(){ me.resetload();});
	       }
	});
	
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/project/collect/search.do";
	});
});
function showDetail(obj){
	window.location.href =  "${path}/m/project/recommend/detail/index.do?id="+$(obj).attr("data-id");
}
function load(callBack){
	var pageIndex = parseInt($("#prj1").attr("pageIndex")||0)+1;
	var pages = parseInt($("#prj1").attr("pages")||1);
	if(pageIndex>pages){
		layer.msg('无更多数据了'); 
		if(callBack){
			callBack();
		}
	  	return;
	}
	setTimeout(function(){
		zdy_ajax({
			url: "${path}/m/project/listpage.do",
			dataType:"html",
			data:{type: 2, pageIndex: pageIndex, keywords: $("#keywords").val()},
			success:function(data){
				$("#prj1").append(data);
				$("#prj1").attr("pageIndex",pageIndex);
				if(callBack){
					callBack();
				}
			}
		})
	},500);
}
</script>
</body>
</html>