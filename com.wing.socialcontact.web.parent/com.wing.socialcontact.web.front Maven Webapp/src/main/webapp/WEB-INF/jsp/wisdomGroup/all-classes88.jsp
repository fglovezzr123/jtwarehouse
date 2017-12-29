<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">全部分类</title>
		
		
		<link rel="stylesheet" href="${path}/resource/css/all-classes1.css" />
	
		
		
	</head>

	<body>
		
		<div class="wrapper" id="Class-list">
		
		</div>
        
        <script>

        zdy_ajax({
			   url:_path+"/library/m/classList.do",
			   showLoading:true,
			   data:{
				
			   },
			   success:function(data){
				  console.log(data);
				  var str='';
				  var strC='';
				  for(var i=0;i<data.length;i++){
					  strC='';
					  for(var b=0;b<data[i].son.length;b++){
						  
						   
					
							
						
					    	 strC += '<div class="class-item active_A" id="'+data[i].son[b].id+'">'+	
						 		'<span>'+data[i].son[b].name+'</span>'+
						 		'<span>'+data[i].son[b].count+'篇</span>'+
						 	  '</div>';
					    
						
					  }
					  
					  str += '<div class="allclass-box">'+
								 	'<h3>'+data[i].name+'</h3>'+
								 	'<div class="allclass-subBox">'+									     
								 	    strC+     
								 	  '<br class="clear"/>'+ 	
								 	'</div>'+
					'</div>' ;
				  }
				
				  $('#Class-list').prepend(str);
				  $('.class-item').bind('click',function(){
					  window.location.href="${path}/library/m/all-classes.do?id="+$(this).attr('id')+"&level=2";
				  })
			   },
			   error:function(data){
				   
			   }
				
			});
        </script>
		
	
	</body>

</html>