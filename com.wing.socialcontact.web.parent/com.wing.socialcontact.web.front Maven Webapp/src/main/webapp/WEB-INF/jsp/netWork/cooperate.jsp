<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="合作洽谈">
		<title>合作洽谈</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/cooperate.css?v=${sversion}" />
	    <style>
	       .listCont>.iconfont{
	          display:none;
	       }
	    </style>
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
					<div class="cList" id="cListdiv">
						
					</div>
					<div class="content tlist">
						<span onclick="initloadpage('2')" class="show-active">最新合作</span>
						<span  onclick="initloadpage('1')">金主悬赏</span>
					</div>
					<div class="tuhao" id="contentdiv">
						
					</div>
					<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
				</div>
			</div>
			<div class="c-footer">
				<span class="want active_A" onclick="openurl('${path}/m/business/selMyHzPage.do')">我的合作</span>
				<div class="active_A" onclick="business_add();">
					<i class="iconfont">&#xe637;</i>
					<span >发布合作</span>
				</div>
			</div>
		</div>
		
		<script src="${path}/resource/js/cooperate.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 10;
var end=false;
var tid = "2";
$(document).ready(function() {
	//滚动加载
	 $(window).scroll(function(){
	     var scrollTop=$(window).scrollTop();
	     var scrollHeight = $(document).height();
         var windowHeight = $(window).height();
         if (scrollTop>=scrollHeight-windowHeight) {
       	     content(tid); 
         };
         if(scrollTop>200){
				$(".tlist").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".tlist").addClass("fixed");
			}else{
				$(".tlist").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".tlist").removeClass("fixed");
			}
	    }) ;
	zdy_ajax({
		url: "${path}/m/business/selClassList.do",
	    showLoading:false,
		data:{},
		success: function(data,res){
			if(res.code == 0){
				var c = "";
				var oss = res.dataobj.ossurl;
				$.each(res.dataobj.classList, function(i, n){
					c += '<a href="#" class="cList-a active_A" onclick="openurl(\'${path}/m/business/listPage.do?bizType='+n.id+'\')"><img src="'+oss+n.imagePath+'"/><span>'+n.className+'</span></a>';
				});
				c += '<a href="#" onclick="class_more();" class="cList-a active_A"><img src="${path}/resource/img/icons/zh_03.png"/><span>更多</span></a>';
				$("#cListdiv").append(c);
				initloadpage(tid);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/business/search.do";
	});
	
});
//更多
var class_more=function(){
	var url = '${path}/m/business/classPage.do';
	window.location.href=url;
	if (window.localStorage) {
		  localStorage.setItem("history_url", "${path}/m/business/indexPage.do");
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
	if(!end&&initflag){
		initflag=false;
		zdy_ajax({
			url: "${path}/m/business/selBusinessList.do",
			showLoading:false,
			data:{
				type:tid,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					console.log(data)
					if(page==1 && !res.dataobj.length){
					//	   $('#contentdiv').html('<div  class="searchInfo">暂无更多数据</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					   }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					var con = "";
					$.each(res.dataobj, function(i, n){
						con += '<div class="tuhaoList active_A" onclick="openurl(\'${path}/m/business/detailPage.do?id='+n.id+'&type='+tid+'\')">';
						if(n.appealType==1){
						con += '<img src="${path}/resource/img/icons/gong.png" />';
						}else if(n.appealType==2){
							con += '<img src="${path}/resource/img/icons/qiu.png" />';
						}
						con += '<div class="listCont"><div class="listCont-l"><h2>'+firstCharacter(n.titles)+'</h2><div><i class="iconfont">&#xe639;</i>'+
						'<span>'+n.createTime+'</span></div><div><i class="iconfont" style="font-size:.30rem">&#xe61c;</i><span>'+n.lookCount+'人浏览</span>';
						if(tid=='1'){
							con += '&nbsp;&nbsp;&nbsp;<span>悬赏：'+n.reward+'J币</span>';
						}
						con += '</div></div><i class="iconfont" >&#xe605;</i></div></div>';
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
//发布合作
var business_add=function(){
	var url = '${path}/m/business/addBusinessPage.do';
	window.location.href=url;
	if (window.localStorage) {
		  localStorage.setItem("history_url", "${path}/m/business/indexPage.do");
	}
}
</script>
</html>