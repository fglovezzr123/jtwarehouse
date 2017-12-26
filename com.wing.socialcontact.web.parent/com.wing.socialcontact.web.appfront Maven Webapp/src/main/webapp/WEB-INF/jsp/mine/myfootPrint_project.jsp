<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
<title id="title">我的足迹项目</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/myfootPrint-meeting.css?v=${sversion}" />
<script type="text/javascript" src="${path}/resource/js/jsObject.Expand.js?v=${sversion}"></script>
</head>
<body>
<div class="wrapper">
    <div class="Print-tabBox">
       <div type="1" class="<c:if test="${1 eq tab}">current</c:if>">我收藏的项目<%-- ${tab} --%></div>
       <div type="2" class="<c:if test="${2 eq tab}">current</c:if>">我有意向的项目</div>
       <div type="3" class="<c:if test="${3 eq tab}">current</c:if>">我自荐的项目</div>
    </div>
    <div id="list1" class="<c:if test="${1 eq tab}">cur</c:if>" style="display:<c:if test="${1 ne tab}">none;</c:if>" pageIndex="1"></div>
    <div id="list2" class="<c:if test="${2 eq tab}">cur</c:if>" style="display:<c:if test="${2 ne tab}">none;</c:if>" pageIndex="1"></div>
    <div id="list3" class="<c:if test="${3 eq tab}">cur</c:if>" style="display:<c:if test="${3 ne tab}">none;</c:if>" pageIndex="1"></div>
</div>
<script type="text/javascript">
$(function(){
	$(".Print-tabBox div").click(function(){
		var type =  $(this).attr("type");
		if(type!=1&&type!=2&&type!=3){
			return;
		}
		$(".Print-tabBox div").removeClass("current");
		$(this).addClass("current");
		
		$(".cur").removeClass("cur").hide();
		$("#list"+type).addClass("cur").show();
		loadProject(type)
	});
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
	    	   if($(".Print-tabBox div.current").length!=1){
			       me.resetload();
	    		   return;
	    	   }
	    	   var type = $(".Print-tabBox div.current").attr("type");
	    	   if(type!=1&&type!=2&&type!=3){
	    		   layer.msg('无更多数据了'); 
	    		   me.resetload();
	    		   return;
	    	   }
	    	   var pageIndex = parseInt($("#list"+type).attr("pageIndex")||0)+1;
	  	  		var pages = parseInt($("#list"+type).attr("pages")||1);
	  	  		if(pageIndex>pages){
	  	  			layer.msg('无更多数据了'); 
	  	  		 	me.resetload();
	    		  	return;
	  	  		}
	  	  	    setTimeout(function(){
	  	  	    	loadMore(type,pageIndex,function(){me.resetload()});
	  	  	   },500);
	      }
	});
	loadProject(${tab})
});
function loadProject(type){
   if(type!=1&&type!=2&&type!=3){
	   return;
   }
   if($("#list"+type).attr("hasLoad")==1){
	   return;
   }else{
	   $("#list"+type).attr("hasLoad",1)
   }
   loadMore(type,1);
}
function loadMore(type,pageIndex,callback){
 	zdy_ajax({
  		url: "${path}/m/project/listpage2.do",
  		dataType:"html",
  		showLoading:true,
  		data:{type: type, pageIndex: pageIndex},
  		success:function(data){
  			console.log(data)
  			$("#list"+type).append(data);
  			if(callback) callback();
  		}
  	})
}
function attentionDelMeeting(obj){
	event.stopPropagation();
	zdy_ajax({
  		url: "${path}/m/project/removeattention.do",
  		data:{id: $(obj).attr("data-id")},
  		dataType:"json",
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				$(obj).parent().parent().parent().remove();
  			}else{
  				alert(dataobj["result_msg"]||"取消收藏失败")
  			}
  		},
  		complete:function(){
  		}
  	})
}
function updatePages(type,pageNum,pages){
	if(type!=1&&type!=2&&type!=3){
	   return;
   	}	
	if(!pageNum||!pages){
		return;
	}
	$("#list"+type).attr("pageIndex",pageNum);
	$("#list"+type).attr("pages",pages);
}
function detailProject(obj){
	self.location.href = "${path}/m/project/detail/index.do?id="+$(obj).attr("data-id");
}
function detailProjectRecommend(obj){
	self.location.href = "${path}/m/project/recommend/detail/index.do?id="+$(obj).attr("data-id");
}
</script>
</body>
</html>