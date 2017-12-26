 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
        <title id="title">我参加的活动</title>
        <link rel="stylesheet" href="${path}/resource/css/myfootPrint-activity.css?v=${sversion}" />

    </head>
    <body>
        <div class="wrapper" >
              <div class="Print-tabBox">
                   <div class="current">我参加的活动</div>
                   <div><a href="${path}/m/activity/myAction.do">我的活动</a></div>
                   <div><a href="${path}/m/activity/mycolAction.do">我收藏的活动</a></div>
               </div>
              <div id="mylist"> </div> 
           </div>
				<div class="loadingbox" style="margin-top:0.2rem">
					<div class="page_loading" style="display:block;">加载中…</div>
					<div class="page_nodata" style="display:none;">暂无更多数据</div>
				</div> 

    </body>
</html>
<script>

var page=1;
var end=false;
$(function(){
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
});

var pageSize=6;
function  loadlist(){
	if(!end){
	zdy_ajax({
		url: '${path}/m/activity/mysignup.do',
			data:{
				"page":page,
				"size":pageSize
			},
			success: function(d,data){
				var str='';
				if(data.code == 0){
						if(page==1 && !data.dataobj.length==0){
							   //$('#mylist').html('<div  class="searchInfo">没有数据</div>');
							   $(".page_loading").hide();
							   $(".page_nodata").show();
							  // $(".page_nodata").hide();
						    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
								$(".page_loading").hide();
								$(".page_nodata").show();
							    end=true;
						    };
					$.each(data.dataobj, function(i, n){
						var status ='待确认';
						var isaccess="";
						if(n.status=='2'){
							status="已取消报名";
						}else 
						if(n.status=='3'){
							status="活动已取消";
						}else 
						if(n.status=='4'){
							status="已确认";
							isaccess='<a href="${path}/m/activity/signupok.do?id='+n.signupid+'"><button>查看报名</button></a>';
						}else 
						if(n.status=='5'){
							status="已拒绝";
						} 
						var orderStatus="未支付";
						if(n.orderStatus=='2'){
							orderStatus="已支付";
						}else 
						if(n.orderStatus=='3'){
							orderStatus="已退款";
						} 
					str+='<div class="print-tab-box"><div class="print-conetnt"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><div>'+
					n.titles+'</div></a><div>主办方：'+n.sponsor+'</div><div><span>'+n.proName+n.cityName+n.couName+'</span><span>'+
//					formatDate(new Date(n.start_time))+"至"+formatDate(new Date(n.end_time))+'</span>'+isaccess+'</div><div>参与状态 :'+status+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支付状态 :'+orderStatus+'</div></div></div>'
					formatDate(new Date(n.start_time))+"至"+formatDate(new Date(n.end_time))+'</span>'+isaccess+'</div><div>参与状态 :'+status+'</div></div></div>'
					});
					$("#mylist").append(str);
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