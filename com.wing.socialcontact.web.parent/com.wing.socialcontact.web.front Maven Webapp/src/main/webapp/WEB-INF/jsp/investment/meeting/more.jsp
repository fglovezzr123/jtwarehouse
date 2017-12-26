<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ taglib prefix="tojo" uri="/WEB-INF/tlds/tojo.tld" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">${titles}</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/tqfh.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/dropload.min.css?v=${sversion}" />
<script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resource/js/jsObject.Expand.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<style>
.vedio a{
       display:block;
       height:0.7rem;
       width:2rem;
       text-align:center;
       line-height:0.7rem;
       color:white;
       background:#c4323f;
      position: absolute;
    bottom: 0.2rem;
    margin-left: 2.4rem;
}
</style>
</head>
<body>
<div class="wrapper">
	<div class="wqhg" id="grid" pageNum="1" pages="${(dataGrid==null)?0:dataGrid.getPages()}">
		<a href="javascript:void(0);">
			<span>${titles}</span>
			<i class="iconfont" style="font-size: .26rem;"></i>
		</a>
		<c:if test="${not empty dataGrid}">
		<c:forEach var="item" items="${dataGrid.getRows()}">
			<h3><b>${item.titles}</b></h3>
			<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
				<span>${fns:fmt(item.startTime,'yy.MM.dd HH:mm')}&nbsp;</span>
				<span>${item.place}</span>
			</div>
			<div class="vedio" data-id="${item.id }" onclick="detailMeeting(this)">
				<img src="${item.coverImg}" />
				<tojo:mtg type="1" meeting="${item}"/>
			</div>
		</c:forEach>
		</c:if>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
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
	  	  		var pageNum = parseInt($("#grid").attr("pageNum")||0)+1;
	  	  		var pages   = parseInt($("#grid").attr("pages")||0);
	  	  		//pageNum=1;pages=1;
	  	  		if(pageNum>pages){
	  	  			layer.msg('无更多数据了'); 
	  	  		 	me.resetload();
	    		  	return;
	  	  		}
	  	  	    setTimeout(function(){
			  	  	zdy_ajax({
			  	  		url: "${path}/m/meeting/more/list.do",
			  	  		showLoading:true,
			  	  		data:{status: "${status}", pageIndex: pageNum},
			  	  		success:function(data){
			  	  			$("#grid").attr("pageNum",pageNum);
			  	  			$("#grid").attr("pages",data.pages||0);
			  	  			var list = data["rows"]||[];
			
			  	  			for(var i=0;i<list.length;i++)
			  	  			{
			  	  				$("#grid").append("<h3><b>"+list[i].titles+"</b></h3>");
			  	  				$("#grid").append(
			  	  						'<div class="sp" data-id="'+list[i].id+'" onclick="detailMeeting(this)">'+
			  	  						'<span>'+new Date(list[i].startTime).format("yy.MM.dd HH:mm")+'</span>'+
			  	  						'<span>'+list[i].place+'}</span>'+
			  	  						'</div>'
			  	  						);
			  	  				var $html = $('<div class="vedio" data-id="'+list[i].id+'" onclick="detailMeeting(this)">'+
		  	  									'<img src="'+list[i].coverImg+'" />'+
		  	  									'<a href="${path}/m/meeting/detail/index.do?id='+list[i].id+'" class="active_A" style="display:none;"></a>'+
		  	  								'</div>');
			  	  				var statusd = list[i].status;
			  	  				var signupStatus = list[i].extProps.signupStatus;
			  	  				var types = list[i].types;
			  	  				if(statusd==1){
			  	  					$html.find("a").css("display","block");
			  	  					$html.find("a").text("预告中");
			  	  					$("#grid").append($html);
			  	  				}else if(statusd==2){
			  	  					if(signupStatus==1){
				  	  					$html.find("a").css("display","block");
				  	  					$html.find("a").text("已报名");
				  	  					$("#grid").append($html);
			  	  					}else{
				  	  					$html.find("a").css("display","block");
				  	  					$html.find("a").attr("href","${path}/m/my/meeting/signup/index.do?id="+list[i].id);
				  	  					$html.find("a").text("立即报名");
				  	  					$("#grid").append($html);
			  	  					}
			  	  				}else if(statusd==3){
				  	  				$html.find("a").css("display","block");
			  	  					$html.find("a").text("报名结束");
			  	  					$("#grid").append($html);
			  	  				}else if(statusd==4){
			  	  					if(types==1||types==3){
			  	  						if(signupStatus==1){
				  	  						$html.find("a").css("display","block");
					  	  					$html.find("a").attr("href","${path}/m/meeting/liveuseweb.do?id="+list[i].id);
					  	  					$html.find("a").text("进入直播室");
					  	  					$("#grid").append($html);
			  	  						}else{
		  	  								$html.find("a").css("display","block");
					  	  					$html.find("a").text("直播中");
					  	  					$("#grid").append($html);
			  	  						}
			  	  					}else{
			  	  						$html.find("a").css("display","block");
				  	  					$html.find("a").text("进行中");
				  	  					$("#grid").append($html);
			  	  					}
			  	  				}else if(statusd==5){
				  	  				if((types==1||types==3)&&signupStatus==1){
				  	  				$html.find("a").css("display","block");
				  	  					$html.find("a").attr("href","${path}/m/meeting/liveuseweb.do?id="+list[i].id);
				  	  					$html.find("a").text("视频回放");
				  	  					$("#grid").append($html);
			  	  					}else{
				  	  					$html.find("a").css("display","block");
				  	  					$html.find("a").text("已结束");
				  	  					$("#grid").append($html);
			  	  					}
			  	  				}
			  	  			}
			  	  		},
			  	  		complete:function(){
			    		   me.resetload();
			  	  		}
			  	  	})
	  	  	   },500);
	      }
	});
})
function detailMeeting(obj){
	self.location.href = "${path}/m/meeting/detail/index.do?id="+$(obj).attr("data-id");
}
function signupRemind(obj){
	zdy_ajax({
		url :"${path}/m/meeting/signupremind.do?id="+$(obj).attr("data-id"), 
	    type : 'post', 
	    dataType : 'json', 
	    success : function(dataobj){
	    	var isSuccess = "0"===dataobj["result_code"]?true:false;
	    	if(!isSuccess){
		    	alert(dataobj.result_msg)
	    		return;
	    	}else{
	    		$(obj).text("已设置提醒");
	    		$(obj).removeAttr("onclick");
	    	}
	    }
	});
}
</script>
</body>
</html>