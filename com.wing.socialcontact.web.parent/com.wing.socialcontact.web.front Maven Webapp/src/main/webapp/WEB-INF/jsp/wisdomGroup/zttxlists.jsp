<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">与总统谈心</title>
        <style type="text/css">
          .wrapper{
        	    position: absolute;
			    top: 4.53rem;
			    right: 0;
			    bottom: 0;
            }
           #bannerid{
        	    position: absolute;
			    top: 0.78rem;
			    right: 0;
			    bottom: 0;
            }
        	.items{
        		width: 100%;
        		padding: 0.25rem 0.3rem 0.3rem 0.3rem;
        		box-sizing: border-box;
        		background: #fff;
        		font-size: .28rem;
        		display: flex;
        		border-bottom: solid 1px #f2f3f4;
        	}
        	.items .imgBox{
        		width: 2.25rem;
        		height: 1.35rem;
        	}
        	.items .imgBox img{
        		width: 100%;
        	}
        	.items .wenzi{
        		width: 4.45rem;
        		padding-left:.2rem ;
        	}
        	.items .wenzi .w_t{
        		width: 100%;
        		height: 0.9rem;
        		line-height: .44rem;
        		word-break: break-all;
			    text-overflow: ellipsis;
			     display: -webkit-box;
			    -webkit-box-orient: vertical;
			    -webkit-line-clamp: 2;
			    overflow: hidden;
        	}
        	.items .wenzi p{
        		height: .45rem;
        		display: flex;
        		justify-content: space-between;
        		align-items: center;
        		font-size: .24rem;	
        	}
        	.items .wenzi p span:nth-of-type(2){
        		color: #808080;
        	}
        	.items .wenzi p span:nth-of-type(2){
        		color: #0F88EB;
        	}   
        </style>
    </head>
    <body>
    	 <div class="search-box1" >
			<input type="text" name="search" id="search"  placeholder="搜索" />
			<span id="searchingBTN" onclick="search()">搜索</span>
		 </div>
		 <div id="bannerid">
			 <jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
					<jsp:param name="bannerid" value="0e21860b1bf94b8fa690eba546d0ed03" />
			 </jsp:include>
		 </div>
		 <div class="wrapper"  id="livelists">
		 </div>
		 <script type="text/javascript">
		 	var type=2;
		 	var page=1;
		 	var size=10;
		 	var isend = false;
		 	var key="";
		 	$(function(){
		 		loadlists();
		 		if(!isend){
		 		    //滚动加载
		 				  $(window).scroll(function(){
		 				       var scrollTop=$(this).scrollTop();
		 				        var scrollHeight = $(document).height();
		 				            var windowHeight = $(this).height();
		 				            if (scrollTop+windowHeight==scrollHeight) {
		 				            	loadlists(); 
		 				            };
		 				    })
		 			}
		 	});
		 	function search(){
		 		page=1;
		 		isend = false;
		 		$("#livelists").empty();
		 		key=$("#search").val();
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
		 		if(isend){
		 			return;
		 		}
		 		zdy_ajax({
		 			url:_path+"/library/m/live/list.do",
	  			   	showLoading:true,
		 			data:{
		 				type:type,
		 				key:key,
		 				page:page,
		 				size:size
		 			},
		 			success:function(data,res){
		 				console.log(res);
					    var str='';
					    if(data.length==0){
					    	layer.msg("暂无更多数据");
					    	isend=true;
					    	return;
					    }
					    for(var i=0;i<data.length;i++){
					    	var price = "免费";
					    	if(data[i].ticketPrice>0){
					    		price = data[i].ticketPrice+"J币";
					    	}
						    	str += '<a href="${path}/library/m/live/detail.do?id='+data[i].id+'"><div class="items"><div class="imgBox"><img src="'+imgReplaceStyle(data[i].imgpath,'YS640384')+'"/></div>'+
						    	'<div class="wenzi"><div class="w_t">'+data[i].title
						    	+'</div><p><span>'+formatDate(new Date(data[i].startTime))
						    	+'</span><span>'+price
						    	+'</span></p></div></div></a>';
						}
					         $('#livelists').append(str);
					         page++;
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
    </body>
</html>