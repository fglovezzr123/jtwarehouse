<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>资讯</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/information.css"/>
	</head>
	<body class="T-infor">
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
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
					<div class="content" id="contentdiv">
							
					</div>
					<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			   </div>
					<%-- <div class="tonghao">
						<div class="th">
							<h2>老板新闻三分钟</h2>
							<a href="${path}/m/news/bossListPage.do" class="th-a">
								<span>更多</span>
								<i class="iconfont ty">&#xe605;</i>
							</a>
						</div>
						<div class="content" id="bcontentdiv">
							
						</div>
					</div> --%>
					
				</div>
			</div>
		</div>
	
	</body>
<script type="text/javascript">
var page = 1;
var pageSize = 10;
var end=false;
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
				s += '<a href="javascript:void(0)" class="title-t-active" id="1" >推荐</a>';
				s += '<a href="javascript:void(0)">老板新闻三分钟</a>';
				$.each(res.dataobj.classList, function(i, n){
					s += '<a href="javascript:void(0)"id="'+n.id+'">'+n.tagName+'</a>';
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
			}
		}
	}); 
	//滚动加载
	 $(window).scroll(function(){
	     var scrollTop=$(window).scrollTop();
	     var scrollHeight = $(document).height();
        var windowHeight = $(window).height();
        if (scrollTop>=scrollHeight-windowHeight) {
      	     content(); 
        };
	    }) ;
	 $("#search").bind('click',function(){
			window.location.href = "${path}/m/news/search.do";
	 });
	 initloadpage();
});
function initloadpage(){
	page = 1;
	end=false;
	$("#contentdiv").empty();
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
			url: "${path}/m/news/selNewsList.do",
			data:{
				newsTitle:titles,
				page : page,
				size:pageSize
			},
			success: function(data,res){
				if(res.code == 0){
					if(page==1 && !res.dataobj.list.length){
					   $(".page_loading").hide();
					   $(".page_nodata").show();
				   }else if(res.dataobj.list.length==0 || res.dataobj.list.length<pageSize){
					   $(".page_loading").hide();
					   $(".page_nodata").show();
					   end=true;
				   };
					var con = "";
					$.each(res.dataobj.list, function(i, n){
						con += '<div class="content-p"><h2 class="c-title" onclick="openurl(\'${path}/m/news/hotDetailPage.do?id='+n.id+'\')">'+n.newsTitle+'</h2>'+
						'<p class="c-der">'+n.summary+'</p>';
						if(n.imagePath!=null&&n.imagePath!=""){
							con += '<div class="imgBox2" onclick="openurl(\'${path}/m/news/hotDetailPage.do?id='+n.id+'\')"><img src="http://tianjiu.oss-cn-beijing.aliyuncs.com/'+n.imagePath+'"/></div>';
						}
						con += '<div class="info"><span>'+n.updateTime+'</span><span>摘自：'+n.source+'</span><span>';
						if(n.commentCount==null||n.commentCount==0){
							con += '评论：'+n.countNum;
						}else{
							con += '评论：'+n.commentCount;
						}
						con += '</span></div></div>';
					});
					$("#contentdiv").append(con);
					page++;
					initflag=true;
					/* var con2 = "";
					$.each(res.dataobj.blist, function(i, n){
						con2 += '<div class="content-p"><h2 class="c-title" onclick="openurl(\'${path}/m/news/summaryPage.do?id='+n.id+'\')">'+n.newsTitle+'</h2>'+
						'<p class="c-der">'+n.summary+'</p><div class="imgBox2" onclick="openurl(\'${path}/m/news/summaryPage.do?id='+n.id+'\')"><img src="http://tianjiu.oss-cn-beijing.aliyuncs.com/'+n.imagePath+'"/>'+
						'</div><div class="info"><span>'+n.updateTime+'</span><span>';
						if(n.isFree==2){
							con2 += '免费';
						}else if(n.isFree==1){
							con2 += '收费：'+n.charge+'J币';
						}
						con2 += '</span></div></div>';
					});
					$("#bcontentdiv").append(con2); */
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