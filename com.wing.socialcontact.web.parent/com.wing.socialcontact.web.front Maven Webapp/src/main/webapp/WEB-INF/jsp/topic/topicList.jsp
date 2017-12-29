<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>话题PK</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkPk.css" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>
	</head>
	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
					<div class="search-box">
				      	<div id="search">搜索</div>
					</div>
					<jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="${bannerid}" />
					</jsp:include>
				</div>
				<div class="tlist">
					<span class="show-active" type="1" >最热</span>
					<span type="2" >最新</span>
					<span type="3" >最火</span>
				</div>
				<div class="content" id="contentdiv">
					
				</div>
				<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
				<div style="height:1rem"></div>
			   </div>
			</div>
			<div class="c-footer">
				<span class="want active_A" onclick="openurl('${path}/m/topic/myTopicPage.do')">我已发布的话题</span>
				<div class="active_A" onclick="topic_add();">
					<i class="iconfont">&#xe637;</i>
					<span >引入话题</span>
				</div>
			</div>
		</div>
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>		
	</body>
<script type="text/javascript">
var types = "1";
var page = 1;
var pageSize = 10;
var end=false;
$(document).ready(function() {
	loadTopic();
	$('.tlist span').click(function(e){
		var index = $(this).index();
		$(this).addClass('show-active').siblings().removeClass('show-active');
		types = $(this).attr("type");
		loadTopic();
    })
	//滚动加载
	 $(window).scroll(function(){
	   var scrollTop=$(this).scrollTop();
	   var scrollHeight = $(document).height();
      var windowHeight = $(window).height();
      if (scrollTop>=scrollHeight-windowHeight) {
    	  content(); 
      };
      if(scrollTop>=200){
				$(".tlist").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".tlist").addClass("fixed");
			}else{
				$(".tlist").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".tlist").removeClass("fixed");
			}
	    }) ;
	 $("#search").bind('touchstart',function(){
			window.location.href = "${path}/m/topic/search.do";
		});
});



//type 1：上拉加载   2：条件查询 
function loadTopic(){
	page = 1;
	end=false;
	$("#contentdiv").html('');
	$(".page_nodata").hide();
	$(".page_loading").show();
	content();
}
var initflag = true;
function content(){
	var titles = $("#search").val();
	if(!end&&initflag){
		initflag=false;
		zdy_ajax({
			showLoading:false,
			url: "${path}/m/topic/selTopicList.do",
			data:{
				types:types,
				titles:titles,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					if(res.dataobj.topicList.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   }else{
						   $(".page_nodata").hide(); 
					   };
					var s='';
					$.each(res.dataobj.topicList, function(i, n){
						if(n.isAd==1){
							s += '<div class="content-p"><h2 class="c-title" onclick="openurl(\''+n.url+'\')">'+n.topicDesc+'</h2>'+
							'<div class="imgBox2" onclick="openurl(\''+n.url+'\')"><img src="'+_oss_url+n.imagePath+'"/>'+
							'</div>';
							s += '</div></div>';
						}else{
							s += '<div class="contList active_A" onclick="openurl(\'${path}/m/topic/detailPage.do?id='+n.id+'\')"><div class="contList-l"><h2>'+n.topicDesc+'</h2>'+
							'<span><i></i><b>'+n.redCount+'人</b></span><span><i></i><b>'+n.blueCount+'人</b></span></div>'+
							'<div class="contList-r"><span><b>'+n.countNum+'</b>';
							if(types=="3"){
								s += '收藏';
							}else{
								s += '条评论';
							}
							s += '</span><i class="iconfont" >&#xe605;</i></div></div>';
						}
					});
					$("#contentdiv").append(s);
					page++;
					initflag = true;
				}
			}
		}); 
	}
}
function openurl(url){
	if(url!=''&&url!=null){
		document.location.href=url;
	}
}
var topic_add=function(){
	var url = '${path}/m/topic/topicAddPage.do';
	window.location.href=url;
	if (window.localStorage) {
		  localStorage.setItem("history_url", "${path}/m/topic/topicPage.do");
	}
}


</script>

</html>