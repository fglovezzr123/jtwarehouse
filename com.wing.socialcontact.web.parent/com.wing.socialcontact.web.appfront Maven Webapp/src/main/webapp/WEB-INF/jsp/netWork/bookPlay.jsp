<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="以书会友">
		<title>以书会友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/bookPlay.css?v=${sversion}" />
		
	</head>

	<body>
		<div class="T-fPlay" style="background: #f2f3f4;width: 100%;padding-bottom:1rem;">
			<div class="search-box">
				      	<div id="search">搜索</div>
					</div>
			<jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
				<jsp:param name="bannerid" value="38c72bfc4f774ebe89860ca7834c98a0" />
			</jsp:include>
			<div class="b-imgBox"  id="tagslist">
			</div>
			<div class="tonghao" id="tonghaoid"  style="display:none;">
				<div class="th">
					<!-- <h2 id="recommendtag"></h2> -->
					<h2 >总裁读书会</h2>
				</div>
				<div class="timgBox" id="recomlist">
					
				</div>
			</div>
			<div  id="activitylist" >
			</div>
			<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			</div>
			<div class="c-footer">
				<span class="want active_A"><a href="${path}/m/my/myfootPrint_activity.do" class="anniu">我的赴约</a></span>
				<div class="active_A " >
				<a href="${path}/m/activity/addActivityPage.do" style="width:100%;height:100%;display:block;color:white">
					<i class="iconfont">&#xe637;</i>
					<span>约会召集</span>
				</a>
				</div>
			</div>
		</div>
		<%-- <script src="${path}/resource/js/bookPlay.js?v=${sversion}" type="text/javascript" charset="utf-8"></script> --%>
	</body>

</html>
<script>
var page = 1;
var end=false;
var pageSize = 10;
//推荐标签   （ 一个）
var recomtag='';
$(document).ready(function() {
	if(screenflag){
		$("#tonghaoid").hide();
	}else{
		$("#tonghaoid").show();
	}
	loadActivityTags(1,"2",3);
	initload();
	localStorage.activityurl="${path}/m/activity/bookPlayPage.do";
	//滚动加载
	  /* $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	       var scrollHeight = $(document).height();
	       var windowHeight = $(this).height();
		   //console.log(scrollHeight+" "+(scrollTop+windowHeight+2));
            if (scrollTop+windowHeight+2 >=scrollHeight) {
            	//loadActivitys(); 
            	$(".page_loading").show();
            	$(".page_nodata").show();
            };
	    }) */
	    $("#search").bind('touchstart',function(){
			window.location.href = "${path}/m/activity/activitysearch.do?classid=2";
		});
	    $(window).scroll(function(){
		    if($(window).scrollTop() >= $(document).height() - $(window).height()){
		        //alert("滚动到底部啦！");
		        if(!end){
		        	loadActivitys();
			        console.log("++++++++++++++++"+curPageNum);
		        }
		    }
		    //console.log($(window).scrollTop()+"  "+($(document).height() - $(window).height()));
		});
});

function loadActivityTags(recommnend,classId,topNum){
	zdy_ajax({
		showLoading:false,
		url: "${path}/m/activity/selTagsList.do",
		data:{
			recommnend:recommnend,
			classId:classId,
			topNum:topNum
			
		},
		success: function(data,res){
			if(res.code == 0){
				var s='';
				var num=1;
				var screencou = 3;
				if(screenflag){
					screencou=5;
				}
				$.each(res.dataobj, function(i, n){
					if(num<screencou){
						recomtag=n.id;
						//$("#recommendtag").html(n.name);
						if(n.recommend==1){
							s += '<a href="${path}/m/activity/activitylistPage.do?id='+n.id+'"><img src="'+imgReplaceStyle(_oss_url+n.imagePath,'YS300200')+'" /><span>'+n.name+'</span></a>';
							num++;
						}
					}
					});
				if(num==2){
					$("#tagslist").css("justify-content","flex-start");
				}
				if(!screenflag){
					s += '<a href="${path}/library/m/live/listPage.do?type=4"><img src="http://tianjiu.oss-cn-beijing.aliyuncs.com/tianjiu/news/2017/8/19/a8d9fa29-9cd0-47a9-b270-625a369c07a5.jpg?x-oss-process=style/YS300200" /><span>总裁读书会</span></a>';
					s += '<a href="${path}/library/m/live/listPage.do?type=3"><img src="http://tianjiu.oss-cn-beijing.aliyuncs.com/tianjiu/news/2017/8/19/a8d9fa29-9cd0-47a9-b270-625a369c07a5.jpg?x-oss-process=style/YS300200" /><span>商界冠军直播秀</span></a>';
				}
				$("#tagslist").append(s);
				loadrecom();
			}
		}
	}); 
}

function initload(){
	var page=1;
	end=false;
	$("#activitylist").empty();
	$(".page_nodata").hide();
	$(".page_loading").show();
	loadActivitys();
}

//type 1：上拉加载   2：条件查询 
function loadActivitys(){
	//var titles = $("#search").val();
	console.log(page);
	if(!end){
		zdy_ajax({
			showLoading:false,
			url: "${path}/m/activity/selActivityList.do",
			data:{
				page : page,
				classId:'2',
				size:pageSize,
				recommendList:1
			},
			success: function(data,res){
				console.log(res);
				if(res.code == 0){
				    if(page==1 && !res.dataobj.length){
					  // $('#activitylist').html('<div  class="searchInfo">未搜索到相关内容</div>');
					   $(".page_loading").hide();
					   $(".page_nodata").show();
				    }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						$(".page_loading").hide();
						$(".page_nodata").show();
					    end=true;
				    };
					var s='';
					var statusName = '';
					$.each(res.dataobj, function(i, n){
						if(n.status == 1){
							statusName = '待审核';
						}else if(n.status == 2){
							statusName = '报名中';
						}else if(n.status == 3){
							statusName = '报名结束';
						}else if(n.status == 4){
							statusName = '进行中';
						}else if(n.status == 5){
							statusName = '已结束';
						}
						s +='<div class="h-cont"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><div class="cont-l"><img src="'+imgReplaceStyle(_oss_url+n.imagePath,'YS200200')+'" /></div><div class="cont-r"><h2><b>'
							+'['+statusName+']</b>'+n.titles+'</h2><div class="icontt"><div class="cc"><i class="iconfont">&#xe602;</i><span>'
							+n.cityName+'</span></div></br><div class="cc"><i class="iconfont iconfont2">&#xe639;</i><span>'+formatDate(new Date(n.startTime))
							+'</span></div></br><div class="cc"><i class="iconfont">&#xe61c;</i><span><b>'+n.cou+'人</b>报名</div></div></a>';//</span>/* <span><b>'+n.collect+'人</b>收藏</span> */
						if(n.status==2){
							s +='<a href="${path}/m/my/signupPage.do?id='+n.id+'"><button class="active_A">赴约</button></a>';
						}
						s +='</div></div>';
					});
					$("#activitylist").append(s);
					page++;
				}
			}
		});
	}
}

function  loadrecom(){
	//$("#recomlist").empty();
	zdy_ajax({
		showLoading:false,
		url: "${path}/library/m/live/list.do",
		data:{
			page : 1,
			type:4,
			size:3
		},
		success: function(data,res){
			console.log(data);
			if(res.code == 0){
				var s='';
				$.each(res.dataobj, function(i, n){
					s +='<a href="${path}/library/m/live/detail.do?id='+n.id+'"><img src="'+n.imgpath+'" /><span>'
						+n.title+'</span></a>';
				});
				$("#recomlist").append(s);
			}
		}
	});
}
/* function  loadrecom(){
	//$("#recomlist").empty();
	zdy_ajax({
		showLoading:false,
		url: "${path}/m/activity/selActivityList.do",
		data:{
			page : 1,
			tag:recomtag,
			size:3
		},
		success: function(data,res){
			if(res.code == 0){
				var s='';
				$.each(res.dataobj, function(i, n){
					s +='<a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><img src="'+imgReplaceStyle(_oss_url+n.imagePath,'YS150250')+'" /><span>'
						+n.titles+'</span></a>';
				});
				$("#recomlist").append(s);
			}
		}
	});
} */
/* <a href="#">
<img src="${path}/resource/img/icons/book_03.jpg"  />
<span>我们都爱高尔夫(32人)</span>
</a> */

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
	return year+"/"+month+"/"+day; /* "+hour+":"+minute;  */
	}  
</script>