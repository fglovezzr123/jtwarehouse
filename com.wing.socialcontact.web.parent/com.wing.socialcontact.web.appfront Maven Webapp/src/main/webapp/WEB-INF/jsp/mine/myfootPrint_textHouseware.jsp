<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

        <title id="title">文库收藏</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		
        <link rel="stylesheet" href="${path}/resource/css/myprint-text-houseware.css" />

    </head>
    
  
    <body>
        <div class="wrapper" id="collects">
             
              

        </div>
        <script>
          $(document).ready(function(){
        	  var pageNum=1;
        	  getMyCollection();
        	  
        	  $(window).scroll(function(){
			       var scrollTop=$(this).scrollTop();
			        var scrollHeight = $(document).height();
			            var windowHeight = $(this).height();
			            if (scrollTop+windowHeight==scrollHeight) {
			            	pageNum++;
			            	getMyCollection();
			            };
			    })
        	  
        	  function getMyCollection(){
        		  zdy_ajax({
					   url:_path+"/mycollection/m/mycollection.do",
					   data:{
						  type:'1',
						  size:'10',
						  page:pageNum
					   },
					   showLoading:false,
					   success:function(data,abc){
						 console.log(data);
						 if(!data.length){
							 layer.msg('暂无更多数据');
						 }
						 var str='';
						 for(var i=0;i<data.length;i++){
							 
							 if(!data[i].tag){
								 str +=   '<div id="'+data[i].id+'" class="print-activity active_A">'+
									                '<h3>'+
													    data[i].title+
													'</h3>'+
													'<p>'+
													'<span></span>'+
													'<span>'+data[i].readtimes+'人浏览</span>'+
													'</p>'+
											'</div>';
							 }else{
								 str +=  '<div id="'+data[i].id+'" class="print-activity active_A">'+
								                '<h3>'+
												    data[i].title+
												'</h3>'+
												'<p>'+
												'<span>'+data[i].tag+'</span>'+
												'<span>'+data[i].readtimes+'人浏览</span>'+
												'</p>'+
										'</div>';
							 }
							 
							 
						 }
						 
						 $('#collects').append(str);
						 $('.print-activity').bind('click',function(){
							 document.location.href="${path}/library/m/librarydetail.do?type=1&id="+jQuery(this).attr('id');
						 });
					   },
					   error:function(data){
						   
					   }
						
					})
        	  }
        	  
          })
              
        </script>
    </body>
</html>