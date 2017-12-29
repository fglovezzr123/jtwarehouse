<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯列表">
		<title>资讯列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>
	</head>
	<body>
			 <div class="top_menu_bar ">
				<div class="top_menu_list">
			    </div>
			  </div>
			<div class="wrapper" id="wrapper" style="padding-top:.76rem;">
				<div class="scrollbar" id="scrollbar">
					<div class="search-box">
				      	<div id="search">搜索</div>
					</div>
					<jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="${bannerid}" />
					</jsp:include>
					<div id="tabs-container">
					   <div class="content" id="contentdiv">
					</div>
					</div>
					<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
				</div>
			</div>	
	</body>
<script type="text/javascript">
var tid = '${types}';
var page = 1;
var pageSize = 10;
var end=false;
var type = 1;
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/news/selClassList.do",
		data:{
			types:'${types}'
		},
		success: function(data,res){
			if(res.code == 0){
				 $('.top_menu_list').show()
				var s='';
				var bannerid = res.dataobj.bannerid;
				if(tid==1){
					s += '<a href="javascript:void(0)" id="1" class="title-t-active">推荐</a>';
				}else{
					s += '<a href="javascript:void(0)" id="1" >推荐</a >';
				}
				s += '<a href="javascript:void(0)">老板新闻三分钟</a>';
				$.each(res.dataobj.classList, function(i, n){
					if(tid==n.id){
						s += '<a href="javascript:void(0)"id="'+n.id+'" class="title-t-active">'+n.tagName+'</a>';
					}else{
						s += '<a href="javascript:void(0)"id="'+n.id+'">'+n.tagName+'</a>';
					}
				});
				$(".top_menu_list").append(s);
				$(".top_menu_list a").on('click',function(e){/* touchstart mousedown */
				        $(this).addClass('title-t-active').siblings().removeClass('title-t-active');
				        var tid=$(this).attr("id");
				        if(tid){
				        	if(tid=="1"){
				       		 window.location.href = "${path}/m/news/newsPage.do";
				          	}else{
				          		 window.location.href = "${path}/m/news/newsListPage.do?types="+tid;
				          	}
				        }else{
				        	window.location.href = "${path}/m/news/bossListPage.do"
				        }
				       
				    });
				   loadNews(2);	
			}
		}
	}); 
	//滚动加载
	
	 $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	       var scrollHeight = $(document).height();
       var windowHeight = $(window).height();
       if (scrollTop>=scrollHeight-windowHeight) {
     	     content(1); 
       };
	  });
	 $("#search").on('click',function(){
			window.location.href = "${path}/m/news/search.do";
	 });
	 
function content(type){
	var titles = $("#search").val();
	if(!end){
		zdy_ajax({
			showLoading:false,
			url: "${path}/m/news/selNewsListzx.do",
			data:{
				types:tid,
				newsTitle:titles,
				page : page,
				size:pageSize,
				isHot:0
			},
			success: function(data,res){
				if(res.code == 0){
					if(page==1 && !res.dataobj.list.length&&type==2){
						   $('#contentdiv').html('<div  class="searchInfo">未搜索到相关内容</div>');
						   $(".page_loading").hide();
					   }else if(res.dataobj.list.length==0 || res.dataobj.list.length<pageSize){
						   $(".page_loading").hide();
						   $(".page_nodata").show();
						   end=true;
					   };
					var con = "";
					$.each(res.dataobj.list, function(i, n){
						con += '<div class="content-p"><h2 class="c-title" id="'+n.id+'">'+n.newsTitle+'</h2>'+
						'<p class="c-der">'+n.summary+'</p><div class="imgBox2" id="'+n.id+'"><img src="'+_oss_url+n.imagePath+'"/>'+
						'</div><div class="info"><span>'+n.updateTime+'</span><span>摘自：'+n.source+'</span><span>';
						if(n.commentCount==null||n.commentCount==0){
							con += '评论：'+n.countNum;
						}else{
							con += '评论：'+n.commentCount;
						}
						con += '</span></div></div>';
					});
					$("#contentdiv").append(con);
					$('.content-p h2,.imgBox2').on('click',function(){
						window.location.href="${path}/m/news/detailPage.do?id="+$(this).attr('id')
					})
					page++;
				}
			}
		});
	}
	
	
}


//type 1：上拉加载   2：条件查询 
	function loadNews(_type){
		page = 1;
		end=false;
		type = _type;
		$("#contentdiv").empty();
		$(".page_nodata").hide();
		$(".page_loading").show();
		content(_type);
	}
	
});
</script>

</html>