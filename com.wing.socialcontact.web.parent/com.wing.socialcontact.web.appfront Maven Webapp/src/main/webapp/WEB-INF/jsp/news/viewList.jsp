<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>商友专访</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/friendRecommend.css" />
		<script src="${path}/resource/js/libs/zepto.min.js" type="text/javascript" charset="utf-8"></script>
	    <style>
	            .tonghao .timgBox a img{
			             width:3.3rem;
			             height:1.65rem;
			         }
			         .tonghao .timgBox a{
			             width:3.3rem;
			             height:2rem;
			             margin-top:0.3rem
			         }
			         .tonghao{
			            padding:0 0.3rem;
			         }
	    </style>
	</head>

	<body>
		<div class="T-frecommend" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
					<div class="tonghao">
						<div class="timgBox" id="listdiv">
							
						</div>
						<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
					</div>
				</div>
			</div>
		</div>
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
		<script src="${path}/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 10;
var end=false;
$(document).ready(function() {
	//滚动加载
	$(window).scroll(function(){
		var scrollTop=$(this).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = $(window).height();
		if (scrollTop>=scrollHeight-windowHeight) {
      	     content(); 
        };
	    }) ;
	loadNews();
});

function openurl(url){
	document.location.href=url;
}
function loadNews(){
	page = 1;
	end=false;
	$("#listdiv").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	content();
}
function content(){
	if(!end){
		zdy_ajax({
			showLoading:false,
			url: "${path}/m/news/selViewList.do",
			data:{
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					if(page==1 && !res.dataobj.list.length){
						   $('#contentdiv').html('<div  class="searchInfo">未搜索到相关内容</div>');
						   $(".page_loading").hide();
					   }else if(res.dataobj.list.length==0 || res.dataobj.list.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					var s='';
					var oss = res.dataobj.ossurl;
					$.each(res.dataobj.list, function(i, n){
					     s += '<a href="#" onclick="openurl(\'${path}/m/news/viewDetailPage.do?id='+n.id+'\')"><img src="'+oss+n.imagePath+'" alt="" /> <span>'+n.newsTitle+'</span></a>';
					});
					$("#listdiv").append(s);
					page++;
				}
			}
		}); 
	}
}
</script>

</html>