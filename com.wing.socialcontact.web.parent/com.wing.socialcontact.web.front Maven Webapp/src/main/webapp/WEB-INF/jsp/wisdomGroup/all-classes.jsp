<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">文章列表</title>
	    <link rel="stylesheet" href="${path}/resource/css/all-classes.css" />
	</head>
	<body>
	        <div class="search-box1" >
				<input type="text" name="search" id="search"  placeholder="搜索" />
				<span id="searchingBTN" onclick="search()">搜索</span>
		    </div>
			<div class="main-tab">
				<div class="main-tab1 active_A" id="theAll">全部文档</div>
				<div class="main-tab1 active_A" id="hotest">最热</div>
				<div class="main-tab1 active_A" id="thisday">今天</div>
			</div>
			<div class="main-content">
			
			</div>
	</body>
    <script type="text/javascript">
		 	var key='',page=1,size=10,isend = false,today='',readtimes='',classid='${classid}';isload=false;
		 		loadlists(1);
		 		if(!isend){
 				   $(window).scroll(function(){
 				       var scrollTop=$(this).scrollTop();
 				        var scrollHeight = $(document).height();
 				            var windowHeight = $(this).height();
 				            if (scrollTop+windowHeight==scrollHeight) {
 				            	loadlists(); 
 				            };
 				     })
		 		}
		 	
		 	$('.main-tab .main-tab1').on("click",function(){
		 		page=1;
		 		isend = false;
		 		var id=$(this).attr('id')
		 		if(id == "theAll"){
		 			today='';
		 			classid='';
		 		}else if(id == "hotest"){
		 			readtimes=1;
		 			today='';
		 		}else{
		 			today=1;
		 		}
		 		$(".main-content").empty();
		 		loadlists();
		 	})
		 	function search(){
		 		page=1;
		 		isend = false;
		 		$(".main-content").empty();
		 		key=$("#search").val().trim();
		 		loadlists();	
		 	} 
		 	$('#search').on('keyup',function(){
				var valLen = $('#search').val().trim().length
				if(valLen>0){
					 $(this).css({backgroundImage:'url()'})
				}else{
					$(this).css({backgroundImage:'url(${path}/resource/img/icons/len.png)'})
				}
			})
		  function loadlists(){
		  		if(isload){
		  			return;
		  		}
		  		isload=true;
		 		if(isend){
		 			return;
		 		}
		 		console.log(page+"--"+readtimes+"--"+today);
			 		zdy_ajax({
			 			url:_path+"/library/m/list.do",
		  			   	showLoading:true,
			 			data:{
							   'key':key,
							   'classid':classid,
							   'today':today,
							   'readtimes':readtimes,
							   'page':page,
							   'size':size,
			 			},
			 			success:function(data,res){
						    if(data.length==0){
						    	layer.msg("暂无更多数据");
						    	isend=true;
						    	isload=false;
						    	return;
						    }
						    $.each(data, function(i,n) {
								var str='<div class="item book-item1" data-id="'+n.id+'">'+
									       '<div class="it-img">'+
								             '<img src="'+_oss_url+n.imgpath+'"/>'+
							               '</div>'+
						                   '<div class="it-cont">'+
							                 '<h3>'+n.title+'</h3>'+
							                 '<div class="bot">'+
								              '<span>'+n.readtimes+'人阅读</span>'+
								              '<span>'+formatDate(new Date(n.createTime))+'</span>'+
							                  '</div>'+
						                   '</div>'+
						                 '</div>'
								$('.main-content').append(str)
							});
						    page++;
						    isload=false;
						    $('.book-item1').bind('click',function(){
						    	 document.location.href="${path}/library/m/librarydetail.do?type=1&id="+jQuery(this).attr('data-id');
						     });
						 },
					   	error:function(data,res){
					   		layer.msg(res.msg);
					   	}
			 		});
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
</html>