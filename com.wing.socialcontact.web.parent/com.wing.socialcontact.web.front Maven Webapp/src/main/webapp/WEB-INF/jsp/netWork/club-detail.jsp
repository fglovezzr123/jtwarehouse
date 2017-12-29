<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="会所详情">
		<title>会所详情</title>
		
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/club-detail.css?v=${sversion}" />
		<style type="text/css">
			#collectss img {
				height: 0.3rem;
				width: 0.3rem;
			}
		</style>
	</head>

	<body>
		<div class="wrapper">	
   	    </div>
        
        <div class="club-Btn active_A" id="collectss" onclick="toCollect($(this))">收藏</div>
    
	   <script>
	   var imgpathss=_oss_url;
	   zdy_ajax({
		   url:_path+"/club/m/detail.do",
		   showLoading:true,
		   data:{
			 'clubid':'${id}',
			
		   },
		   success:function(data){
			   var str1='';
			   console.log(data);
			   layer.close(layer.load(0,{type:3,shade:[0.8,'#393D49'],scrollbar:false}));
			 //分享设置
	        	var _title = "会所详情";
				var _imgUrl = "";
				if(data.title.length > 0){
					_title = data.title;
				}
				if(data.imgpath.length > 0){
					_imgUrl =imgpathss+data.imgpath;
				}
				var _link = _path+"/club/m/dePage.do?id="+data.id;
				wxsharefun(_link,_title,_imgUrl,"");
				   str1 +=   '<h3>'+data.title+'</h3>'+
							 '<img src="'+imgpathss+data.imgpath+'"/>'+
				             '<div class="clubd1 pad-top">'+
				             	 '<div>会所地址 ：</div>'+
				             	 '<div>'+data.address+'</div>'+
				             	 '<br class="clear"/>'+
				             '</div>'+
				             '<div class="clubd1 pad-btm">'+
				             	 '<div>联系方式：</div>'+
				             	 '<div>'+data.phone+'</div>'+
				             	 '<br class="clear"/>'+
				             '</div>'+
				             '<div class="club-pre">会所介绍</div>' +
				             '<div class="main-content">'+data.content+'</div>';
				 if(data.iscollection){
					 $('#collectss').text('');
					 $('#collectss').append('<span>取消收藏</span>');
					 //$('#collectss').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
				 }else{
					 $('#collectss').text('');
					 //$('#collectss').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
					 $('#collectss').append('<span>收藏</span>');
				 }    
			   $('.wrapper').append(str1);
			   
		   },
		   error:function(data){
			   
		   }
			
		}); 
	   
	   function toCollect(current){
		   if(current.text()=='取消收藏'){
			   zdy_ajax({
				   url:_path+"/mycollection/m/del.do",
				   showLoading:false,
				   data:{
					 'id':(window.location.search.substr(1).split('='))[1],
					 'type':2
					
				   },
				   success:function(data,be){
					 layer.msg('已取消收藏');
					 $('#collectss').text('');
					 $('#collectss').append('<span>收藏</span>');
					 //$('#collectss').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
				   },
				   error:function(data){
					   
				   }
					
				}); 
		   }else{
			   
		  
		   zdy_ajax({
			   url:_path+"/mycollection/m/add.do",
			   showLoading:false,
			   data:{
				 'id':(window.location.search.substr(1).split('='))[1],
				 'type':2
				
			   },
			   success:function(data,be){
				layer.msg("收藏成功");
				 $('#collectss').text('');
				 $('#collectss').append('<span>取消收藏</span>');
				 //$('#collectss').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
			   },
			   error:function(data){
				   
			   }
				
			}); 
		   };
	   }
	   </script>	 
	</body>

</html>