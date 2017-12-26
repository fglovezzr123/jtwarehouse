<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
<title id="title">我的足迹会议</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/myfootPrint-meeting.css?v=${sversion}" />
<script type="text/javascript" src="${path}/resource/js/jsObject.Expand.js?v=${sversion}"></script>
</head>
<body>
<div class="wrapper">
    <div class="Print-tabBox">
       <div type="1" class="current">我报名的会议</div>
       <div type="2">我参加的会议</div>
       <div type="3">我收藏的会议</div>
    </div>
    <div id="list1" class="cur" pageIndex="1"></div>
    <div id="list2" style="display:none;" pageIndex="1"></div>
	<div id="list3" style="display:none;" pageIndex="1"></div>    
	<div class="loadingbox">
						<div class="page_loading" style="display:block;">加载中…</div>
						<div class="page_nodata" style="display:none;">暂无更多数据</div>
					</div>
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
		$(".page_loading").show();
		$(".page_nodata").hide();
		loadMeeting(type)
	});
	$('.wrapper').dropload({
	  	  distance: 50,
	  	  scrollArea : window,
	    	domDown : {
	           domClass   : 'dropload-down',
	           domRefresh : '<div class="dropload-refresh">↑上拉刷新</div>',
	           domUpdate  : '<div class="dropload-update">↑释放更新</div>',
	           domLoad    : '',
	           domNoData  : '<div class="dropload-noData">暂无更多数据</div>'
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
			  	  	zdy_ajax({
			  	  		url: "${path}/m/meeting/listpage.do",
			  	  		dataType:"html",
			  	  		showLoading:true,
			  	  		data:{type: type, pageIndex: pageIndex},
			  	  		success:function(data){
			  	  			appendItems(type, data);
			  	  		},
			  	  		complete:function(){
			    		   me.resetload();
			  	  		}
			  	  	})
	  	  	   },500);
	      }
	});
	loadMeeting(1)
});
function loadMeeting(type){
   if(type!=1&&type!=2&&type!=3){
	   return;
   }
   if($("#list"+type).attr("hasLoad")==1){
	   console.log(1)
	    $(".page_loading").hide();
		$(".page_nodata").show();
	   return;
   }else{
	   $("#list"+type).attr("hasLoad",1)
   }
  
   zdy_ajax({
  		url: "${path}/m/meeting/listpage.do",
  		dataType:"json",
  		showLoading:false,
  		data:{type: type, pageIndex: 1},
  		success:function(data){
  			appendItems(type, data);
  		}
  	})
}
function appendItems(type,data){
	if(type!=1&&type!=2&&type!=3){
		return;
	}
	$(".page_loading").hide();
	$(".page_nodata").show();
	if(!data||data.pages==0||data.rows.length==0){
		return;
	}
	if(data.pages==0||data.pageNum>data.pages){
		return;
	}
	var $list = $("#list"+type);
	$list.attr("pageIndex",data.pageNum);
	$list.attr("pages",data.pages);
	var rows = data.rows;
	console.log(rows);
	var htmlStr =''
	for(var i=0;i<rows.length;i++){
		htmlStr = '<div class="print-meeting-box">'+
					     '<div style="width:40%;" onclick="detailMeeting(this)" data-id="'+rows[i].id+'"><img src="'+rows[i].coverImg+'"/></div>'+
					     '<div>'+
					          '<h3 onclick="detailMeeting(this)" data-id="'+rows[i].id+'">'+rows[i].titles+'</h3>'+
					          '<div>'+
					               '<span>'
						               	+new Date(rows[i].startTime).format('yyyy/MM/dd HH:mm')+'-'
						               	+new Date(rows[i].endTime).format('yyyy/MM/dd HH:mm')+
						            '</span>'+
					               (type==3?'<span data-id="'+rows[i].id+'" onclick="attentionDelMeeting(this)" class="active_A">取消收藏</span>':'')+
					               '<br class="clear"/>'+
					          '</div>'+
					     '</div>'+
					     '<br class="clear"/>'+
					'</div>';
	}
		$list.append(htmlStr);
}
function attentionDelMeeting(obj){
	zdy_ajax({
  		url: "${path}/m/meeting/removeattention.do",
  		data:{id: $(obj).attr("data-id")},
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				$(obj).parent().parent().parent().remove();
  				$(obj).find("span").text("收藏");
  				$(obj).attr("onclick","attentionMeeting(this)");
  			}else{
  				alert(dataobj["result_msg"]||"取消收藏失败")
  			}
  		},
  		complete:function(){
  		}
  	})
}
function detailMeeting(obj){
	self.location.href = "${path}/m/meeting/detail/index.do?id="+$(obj).attr("data-id");
}
</script>
</body>
</html>