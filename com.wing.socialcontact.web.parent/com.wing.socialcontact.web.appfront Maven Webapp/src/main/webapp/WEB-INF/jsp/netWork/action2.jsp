<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="活动列表">
		<title>活动列表</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/action3.css?v=${sversion}" />
	</head>
	
	<body style="background: #f2f3f4;" >
		<div id="activitys"></div>
		<div class="loadingbox">
			<div class="page_loading" style="display:block;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
	</body>

</html>
<script>

var page=1;
var tagid = '${id}';
var end=false;
var pageSize = 5;
$(function(){
	uploadlist();
	
	if(!end){
    //滚动加载
		  $(window).scroll(function(){
		       var scrollTop=$(this).scrollTop();
		        var scrollHeight = $(document).height();
		            var windowHeight = $(this).height();
		            if (scrollTop+windowHeight==scrollHeight) {
		            	uploadlist(); 
		            };
		    })
	}
	
});

	function  uploadlist(){
		
		var className="以玩会友";
		if(!end){
			zdy_ajax({
			showLoading:false,
			url:'${path}/m/activity/selActivityList.do',
			data:{
				  "tag":tagid,
				  "page":page,
				  "size":pageSize
			   },
		   success: function(data1,data){
				var str='';
				//alert(JSON.stringify(data));
				if(data.code == 0){
				    if(page==1 && !data.dataobj.length){
					   //$('#activitys').html('<div  class="searchInfo">没有数据</div>');
					   $(".page_loading").hide();
					   $(".page_nodata").show();
				    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
						$(".page_loading").hide();
						$(".page_nodata").show();
					    end=true;
				    };
					$.each(data.dataobj, function(i, n){
						if(n.classId=="2"){
							className="以书会友";
						}
						if(n.titles.length>15){
							n.titles=n.titles.substring(0,15)+"...";
						}
						var price="<b>￥"+n.ticketPrice+"</b>元";
						if(n.ticketPrice==0){
							price="<b>免费</b>";
						}
  						str+= '<div class="A-cont"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><div class="img"><img src="'+_oss_url+n.imagePath+'"/></div><div class="ct"><h3>'
  							  +n.titles+'</h3><span>'+n.cou+'人报名</span><p>'+price+'</p></div><div class="xs"><span>'
  							  +className+'</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>'+n.proName+'&nbsp;&nbsp;'+n.cityName+'</span></div>'+
  							  '<div class="sj"><span>'+formatDate(new Date(n.startTime))+'至'+formatDate(new Date(n.endTime))+'</span></div></a></div>';
  							
					});
				$("#activitys").append(str);
				page++;
				}
			},
			error:function(e){
			}
		});
		}
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
		return year+"/"+month+"/"+day+" "+hour+":"+minute; 
		} 
</script>