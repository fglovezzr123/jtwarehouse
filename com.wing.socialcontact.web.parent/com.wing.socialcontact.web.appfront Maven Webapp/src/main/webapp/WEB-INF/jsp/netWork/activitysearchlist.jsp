<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">活动</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
<%-- <link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css?v=${sversion}" /> --%>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/action3.css?v=${sversion}" />
<%-- <link rel="stylesheet" href="${path}/resource/css/tqfh.css?v=${sversion}" /> --%>
<script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<style>
.vedio a{
       display:block;
       height:0.7rem;
       width:2rem;
       text-align:center;
       line-height:0.7rem;
       color:white;
       background:#c4323f;
       margin:-2.4rem auto;
}
</style>
</head>
<body>
<div class="wrapper">
	 <div class="search-box">
      	<div id="search">搜索</div>
	</div>
	<div id="activitys"></div>
	<div class="loadingbox">
		<div class="page_loading" style="display:block;">加载中…</div>
		<div class="page_nodata" style="display:none;">暂无更多数据</div>
	</div>
</div>
<script type="text/javascript">
var keywords ='${keywords}';var classid='${classid}' ;var page =1; var pageSize = 10;var end=false;
$(document).ready(function() {
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/activity/activitysearch.do?classid="+classid;
	});
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
})
function detailactivity(obj){
	self.location.href = "${path}/m/activity/activityDetailPage.do?id="+$(obj).attr("data-id");
}

function  uploadlist(){
	console.log(keywords+classid)
	var className="以玩会友";
	if(!end){
		zdy_ajax({
		showLoading:false,
		url:'${path}/m/activity/selActivityList.do',
		data:{
			  "classId":classid,
			  "page":page,
			  "size":pageSize,
			  "titles":keywords
		   },
	   success: function(data1,data){
			var str='';
			if(data.code == 0){
			    if(page==1 && !data.dataobj.length){
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
</body>
</html>