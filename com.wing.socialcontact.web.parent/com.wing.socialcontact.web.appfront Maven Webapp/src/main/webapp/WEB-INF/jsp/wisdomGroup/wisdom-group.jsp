<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">智囊团</title>
		<link rel="stylesheet" href="${path}/resource/css/wisdom-group.css" />
		<link rel="stylesheet" href="${path}/resource/css/boss-articles.css" />
		<style>
			.banner_ul img {
				width: 100%;
				height: 100%;
			}
		</style>
	</head>

	<body style="">	
		<div class="wrapper">
			<jsp:include page="../commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="2142b885a7654fc391167350a2f5b125" />
		   </jsp:include>
			<div class="menu">
				<!-- <a class="active_A" href="javascript:void(0)"></a> -->
				<a class="active_A" href="${path}/wx/wisdomGroup/boss-articles.do">
				    知识秘书
				</a>
				<a class="active_A" href="${path}/m/news/htmlDetailPage.do?id=6441e97b64c84c21b8499f3681f759c3">
				  咨询联盟
				</a>
				<!-- <br class="clear" /> -->
			</div>
			<div class="zhuge-Top">
			 
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
				<div class="headline">
				<a href="javascript:void(0)">大咖说</a>
				<a href="${path}/library/m/all-classes.do?id=fd69353092fa473ca8852aa72d7d4994">更多 <span>></span></a>
				<br class="clear" />
			</div>
			<div class="content" id="dakashuo">


				<br class="clear" />
			</div>

			<div class="headline">
				<a href="javascript:void(0)">俊卿悄悄话</a>
				<a href="${path}/library/m/all-classes.do?id=c993ec37dec341d28c205bfdba6441b1">更多 <span>></span></a>
				<br class="clear" />
			</div>
			<div class="content" id="qiaoqiaohua">

				<br class="clear" />
			</div>

				<div class="zhuge-head">
					<ul>
						<li>最新文库</li>
						<li class="active_A" id="newArticle">更多<span>></span></li>
						<br class="clear" />
					</ul>
				</div>

				<div class="book-box">
					
				</div>

			</div>

		</div>
		<script type="text/javascript">
		 var imgpathss=_oss_url;
		 zdy_ajax({
			   url:_path+"/library/m/list.do",
			   data:{
				
			   },
			   showLoading:false,
			   success:function(data,abc){
			    var str='';
			    var showlength='';
			    if(data.length>3){
			    	    showlength=3;
			    }else{
			    	 showlength=data.length;
			    }
			 
			  for(var i=0;i<showlength;i++){
				 
			 
			    	
			    	str += '<div class="book-item active_A" id="'+data[i].id+'">' +
								'<div>'+'<img style="height:25%" src="'+imgpathss+data[i].imgpath+'" />'+'</div>'+
								'<div>'+
									'<h1>'+data[i].title+'</h1>'+
									'<p>'+
										/* data[i].content+ */
									'</p>'+
								'</div>'+
								'<br class="clear" />'+
							'</div>';
			    	
			   
			  }
			         $('.book-box').append(str);
				     $('.book-item').bind('click',function(){
				    	 document.location.href="${path}/library/m/librarydetail.do?type=1&id="+jQuery(this).attr('id');
				     });
				     $('#newArticle').bind('click',function(){
				    	 window.location.href="${path}/wx/wisdomGroup/boss-articles.do";
				     });
			   },
			   error:function(data){
				   
			   }
				
			})
			
			//大咖说
				zdy_ajax({
					   url:_path+"/library/m/list.do",
					   data:{
						   'classid':'fd69353092fa473ca8852aa72d7d4994'
					   },
					   showLoading:false,
					   success:function(data,abc){
						   var str='';
						   var dataLen;
						  
						   //限制显示最多4个
						   if(data.length > 2){
							   dataLen=2; 
						   }else{
							   dataLen=data.length;
						   }
						 
						   for(var i=0;i<dataLen;i++){
							   str += '<a href="${path}/library/m/librarydetail.do?type=1&id='+data[i].id+'">'+
								              '<img src="'+_oss_url+data[i].imgpath+'" />'+
                                              '<p>'+data[i].title+'</p>'+
							          '</a>';
						   }
						   
						 jQuery('#dakashuo').prepend(str);
					   },
					   error:function(data){
						   
					   }
						
					});
		 
		//俊卿悄悄话
			zdy_ajax({
				   url:_path+"/library/m/list.do",
				   data:{
					   'classid':'c993ec37dec341d28c205bfdba6441b1'
				   },
				   showLoading:false,
				   success:function(data,abc){
					   var str='';
					   var dataLen;
					  
					   //限制显示最多4个
					   if(data.length > 2){
						   dataLen=2; 
					   }else{
						   dataLen=data.length;
					   }
					 
					   for(var i=0;i<dataLen;i++){
						   str += '<a href="${path}/library/m/librarydetail.do?type=1&id='+data[i].id+'">'+
							              '<img src="'+_oss_url+data[i].imgpath+'" />'+
                                       '<p>'+data[i].title+'</p>'+
						          '</a>';
					   }
					   
					 jQuery('#qiaoqiaohua').prepend(str);
				   },
				   error:function(data){
					   
				   }
					
				});
			
		</script>
		
	</body>

</html>