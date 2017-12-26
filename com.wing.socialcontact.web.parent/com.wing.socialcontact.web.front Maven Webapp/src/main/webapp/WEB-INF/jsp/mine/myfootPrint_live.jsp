<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
<title id="title">我的足迹直播</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/myfootPrint-meeting.css?v=${sversion}" />
<script type="text/javascript" src="${path}/resource/js/jsObject.Expand.js?v=${sversion}"></script>
</head>
<body>
<div class="wrapper">
    <div class="Print-tabBox">
       <div type="1" class="current">我报名的直播</div>
       <div type="3">我收藏的直播</div>
    </div>
    <div id="list1" class="cur" ></div>
    <div class="loadingbox" style="margin-top:.20rem">
			<div class="page_loading" style="display:block;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
	</div>
</div>
<script type="text/javascript">

var page =1;
var size=10;
var end=false;
var load=false;
var type=1;
$(function(){
	$(".Print-tabBox div").click(function(){
		type =  $(this).attr("type");
		if(type!=1&&type!=3){
			return;
		}
		$(".Print-tabBox div").removeClass("current");
		$(this).addClass("current");
		$("#list1").html('');
		end=false;
		page =1;
		loadLive();
	});
	
		if(!end){
	    //滚动加载
			  $(window).scroll(function(){
			       var scrollTop=$(this).scrollTop();
			        var scrollHeight = $(document).height();
			            var windowHeight = $(this).height();
			            if (scrollTop+windowHeight==scrollHeight) {
			            	loadLive(); 
			            };
			    })
		};
	loadLive();
});
function loadLive(){
	if(type!=1&&type!=3){
		   return;
	   }
	if(!load){
		if(type==1){
			zdy_ajax({
		  		url: "${path}/library/m/live/mysignuplist.do",
		  		dataType:"json",
		  		showLoading:true,
		  		data:{
					page:page,
					size:size
		  		},
		  		success:function(obj,data){
		  			 if(page==1 && !obj.length){
						   //$('#activitys').html('<div  class="searchInfo">没有数据</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					    }else if(obj.length==0 || obj.length<size){
							$(".page_loading").hide();
							$(".page_nodata").show();
						    end=true;
					    };
		  			appendItems( obj);
		  		}
		  	})
		}else{
			zdy_ajax({
		  		url: "${path}/mycollection/m/mycollection.do",
		  		dataType:"json",
		  		showLoading:true,
		  		data:{
					page:page,
					size:size,
					type:4
		  		},
		  		success:function(obj,data){
		  			 if(page==1 && !obj.length){
						   //$('#activitys').html('<div  class="searchInfo">没有数据</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					    }else if(obj.length==0 || obj.length<size){
							$(".page_loading").hide();
							$(".page_nodata").show();
						    end=true;
					    };
		  			appendItems( obj);
		  		}
		  	})
		}
	}
}

function appendItems(obj){
	if(type!=1&&type!=3){
		return;
	}
		var htmlStr ='';
		console.log(obj);
	$.each(obj, function(i, n){
		htmlStr +='<div class="print-meeting-box">'+
					     '<div style="width:40%;" onclick="detailLive(this)" data-id="'+n.id+'"><img src="'+n.imgpath+'"/></div>'+
					     '<div>'+
					          '<h3 onclick="detailLive(this)" data-id="'+n.id+'">'+n.title+'</h3>'+
					          '<div>'+
					               '<span>'
						               	+formatDate(new Date(n.start_time))+'-'
						               	+formatDate(new Date(n.end_time))+
						            '</span>'+
					               (type==3?'<span data-id="'+n.id+'" onclick="attentionDelLive(this)" class="active_A">取消收藏</span>':'')+
					               '<br class="clear"/>'+
					          '</div>'+
					     '</div>'+
					     '<br class="clear"/>'+
					'</div>';
	});
	$("#list1").append(htmlStr);
	
}
function attentionDelLive(obj){
	zdy_ajax({
  		url: "${path}/mycollection/m/del.do.do",
  		data:{
  			id: $(obj).attr("data-id"),
  			type:4
  			},
  		success:function(dataobj){
  				$(obj).parent().parent().parent().remove();
  				layer.msg("已取消收藏");
  		},
  		complete:function(){
  		}
  	})
}
function detailLive(obj){
	self.location.href = "${path}/library/m/live/detail.do?id="+$(obj).attr("data-id");
}

function formatDate(now) { 
	var year=now.getFullYear(); 
	var month=now.getMonth()+1;
	if(month<10){
		month="0"+month;
	}
	var day=now.getDate();
	if(day<10){
		day="0"+day;
	}
	var hour=now.getHours();
	if(hour<10){
		hour="0"+hour;
	}
	var minute=now.getMinutes();
	if(minute<10){
		minute="0"+minute;
	}
	var second=now.getSeconds();
	if(second<10){
		second="0"+second;
	}
	return month+"/"+day+" "+hour+":"+minute; 
	} 
</script>
</body>
</html>