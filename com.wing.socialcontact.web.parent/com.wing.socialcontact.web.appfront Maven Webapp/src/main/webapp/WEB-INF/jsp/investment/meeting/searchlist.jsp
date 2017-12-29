<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">投洽峰会</title>
<link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/tqfh.css?v=${sversion}" />
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
	<div class="search-box">
				      	<div id="search">搜索</div>
	</div>
	<%-- <c:choose>
		<c:when test="${empty list or list.size() eq 0}">
			<div class="dropload-noData" style="text-align:center;">暂无数据</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="item" items="${list}">
				<div class="wqhg">
					<h3><b>${item.titles}<c:if test="${not empty item.statusDesc and item.statusDesc!=''}">【${item.statusDesc}】</c:if></b></h3>
					<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
						<span>${fns:fmt(item.startTime,'yyyy.MM.dd HH:mm')}</span>
						<span style="width: 3rem;">${item.place}</span>
						<span><c:if test="${item.getExtProp('signupCount',0)>0}">${item.getExtProp('signupCount',0)}人参会</c:if></span>
					</div>
					<div class="vedio" data-id="${item.id}" onclick="detailMeeting(this)">
						<img src="${item.coverImg}" />
						<div class="zbz-b">
							<span>${item.getExtProp('wacthCount', 0)}次播放</span>
							<span>评论：${item.getExtProp('chatCount', 0)}条</span>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose> --%>
	<div id="meetinglist" >
		<%-- <div class="wqhg">
			<h3><b>${item.titles}<c:if test="${not empty item.statusDesc and item.statusDesc!=''}">【${item.statusDesc}】</c:if></b></h3>
			<div class="sp" data-id="${item.id}" onclick="detailMeeting(this)">
				<span>${fns:fmt(item.startTime,'yyyy.MM.dd HH:mm')}</span>
				<span style="width: 3rem;">${item.place}</span>
				<span><c:if test="${item.getExtProp('signupCount',0)>0}">${item.getExtProp('signupCount',0)}人参会</c:if></span>
			</div>
			<div class="vedio" data-id="${item.id}" onclick="detailMeeting(this)">
				<img src="${item.coverImg}" />
				<div class="zbz-b">
					<span>${item.getExtProp('wacthCount', 0)}次播放</span>
					<span>评论：${item.getExtProp('chatCount', 0)}条</span>
				</div>
			</div>
		</div> --%>
	</div>
	<div class="loadingbox">
		<div class="page_loading" style="display:block;">加载中…</div>
		<div class="page_nodata" style="display:none;">暂无更多数据</div>
	</div>
</div>
<script type="text/javascript">
var keywords = "${keywords}",page=1,end=false,size=10; 
$(document).ready(function() {
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/meeting/search.do";
	});
	loadlist();
	if(!end){
	    //滚动加载
			  $(window).scroll(function(){
			       var scrollTop=$(this).scrollTop();
			        var scrollHeight = $(document).height();
			            var windowHeight = $(this).height();
			            if (scrollTop+windowHeight==scrollHeight) {
			            	loadlist(); 
			            };
			    })
		}
})
function detailMeeting(obj){
	self.location.href = "${path}/m/meeting/detail/index.do?id="+$(obj).attr("data-id");
}

function loadlist(){
	console.log(keywords)
	if(!end){
		zdy_ajax({
		showLoading:false,
		url:'${path}/m/meeting/meetingsearch.do',
		data:{
			  "page":page,
			  "size":size,
			  "keywords":keywords
		   },
	   success: function(data1,data){
			var str='';
			if(data.code == 0){
			    if(page==1 && !data.dataobj.list.length){
				   $(".page_loading").hide();
				   $(".page_nodata").show();
			    }else if(data.dataobj.list.length==0 || data.dataobj.list.length<10){
					$(".page_loading").hide();
					$(".page_nodata").show();
				    end=true;
			    };
			    console.log(data);
				$.each(data.dataobj.list, function(i, n){
					str +='<div class="wqhg"><h3><b>'+n.titles+'【'+n.statusDesc+'】</b></h3>';
					str +='<div class="sp" data-id="'+n.id+'" onclick="detailMeeting(this)">';
					str +='<span>'+formatDate(new Date(n.startTime))+'</span>';
					str +='<span style="width: 3rem;">'+n.place+'</span>';
					str +='<span style="width: 3rem;">'+n.extProps.signupCount+'人参会</span></div>';
					str +='<div class="vedio" data-id="'+n.id+'" onclick="detailMeeting(this)">';
					str +='<img src="'+n.coverImg+'" />';
					str +='<div class="zbz-b">';
					str +='<span>'+n.extProps.wacthCount+'次播放</span>';
					str +='<span>评论'+n.extProps.chatCount+'条</span>';
					str +='</div></div></div>';
				});
				
				
			$("#meetinglist").append(str);
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
	return year+"."+month+"."+day+" "+hour+":"+minute; 
	} 
</script>
</body>
</html>