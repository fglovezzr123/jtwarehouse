 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="发布PK">
		<title>我的收藏</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/action2.css?v=${sversion}" />
	    <style>
	         .sj{
	             position:absolute;
	             top:2.5rem;
	             display:block;
	         }
	         .A-cont .img{
	             position:relative;
	         }
	         .psterCover{
	             width:100%;
	             height:100%;
	             background:black;
	             opacity:0.5;
	             position:absolute;
	         }
	         .sj span:nth-of-type(2){
	             color:white;
	             line-height:0.35rem;
	         }
	         .sj span:nth-of-type(1){
	            color:white
	         }
	    </style> 
	</head>
	
	<body style="background: #f2f3f4;" >
	            
				<div class="loadingbox"  id="activitys">
					<div class="page_loading" style="display:block;">加载中…</div>
					<div class="page_nodata" style="display:none;">暂无更多数据</div>
				</div>
				<script>

var page=1;
var tagid = '${id}';
var end = false;


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

var pageSize = 10;
	function  uploadlist(){
		
		if(!end){
		zdy_ajax({
			url:'${path}/mycollection/m/mycollection.do',
			data:{
				  "type":3,
				  "page":page,
				  "size":pageSize
			   },
		   success: function(d,data){
				var str='';
				//alert(JSON.stringify(data));
					if(page==1 && !data.dataobj.length){
					   //$('#activitys').html('<div  class="searchInfo">没有数据</div>');
					   $(".page_loading").hide();
					   $(".page_nodata").show();
				    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
						$(".page_loading").hide();
						$(".page_nodata").show();
					    end=true;
				    };
				    console.log(data);
					$.each(data.dataobj, function(i, n){
						var className="以玩会友";
						if(n.class_id=="2"){
							className="以书会友";
						}
						if(n.titles.length>10){
							n.titles=n.titles.substring(0,10)+"...";
						}
						var price="<b>￥"+n.ticket_price+"</b>元";
						if(n.ticket_price==0){
							price="<b>免费</b>";
						}
  						str+= '<div class="A-cont" id="1'+n.id+'"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><div class="img"><div class="psterCover"></div><img src="'+_oss_url+n.image_path+'"/></div><div class="ct"><h3>'
  							  +n.titles+'</h3><p>'+price+'</p></div></a><div class="xs"><span>'+formatDate(new Date(n.start_time))+'至'+formatDate(new Date(n.end_time))+'</span><span class="quxiao" id="'+n.id+'" >取消收藏</span></div><div class="sj"><span>'
  							  +n.sponsor+'&nbsp;&nbsp;&nbsp;'+n.proName+n.cityName+n.countyName+'</span><span class="tags">'
  							  +className+'</span></div></div>';
  							
					});
					$("#activitys").before(str);
					page++;
					$(".quxiao").bind('click',function(event){
						var id=jQuery(this).attr('id')+"";		
						zdy_ajax({
							   url:_path+"/mycollection/m/del.do",
							   showLoading:false,
							   data:{
								 'id':id,
								 'type':3
							   },
							   success:function(data,be){
								layer.msg('已取消收藏');
								$("#1"+id).remove();
							   },
							   error:function(data){
							   }
							}); 
						})
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
