<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">知识秘书</title>
		<link rel="stylesheet" href="${path}/resource/css/boss-articles.css" />
		
	</head>

	<body>
		
		<div class="wrapper">

			<jsp:include page="../commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="51a3f1cbf6de491ea8e3242397f1e9ca" />
		   </jsp:include>

			<div class="itemss" id="sysRec">
				<a class="active_A" href="${path}/wx/wisdomGroup/all-classes88.do"><span>更多</span><span></span></a>
				<br class="clear" />
			</div>

			<div class="headline">
				<a href="javascript:void(0)">他山之石</a>
				<a href="${path}/library/m/all-classes.do?id=d143714e0fd74cfc83b225e3935868a5">更多 <span>></span></a>
				<br class="clear" />
			</div>
			<div class="content" id="othersView">


				<br class="clear" />
			</div>

			<div class="headline">
				<a href="javascript:void(0)">老板内参</a>
				<a href="${path}/library/m/all-classes.do?id=8467aa44d58b4efbaa83e055ae53eea8">更多 <span>></span></a>
				<br class="clear" />
			</div>
			<div class="content" id="bossAdvicer">

				<br class="clear" />
			</div>

		

			<div class="headline" style="border-bottom:1px solid #e7e7e7 ">
				<a href="javascript:void(0)">藏经阁</a>
				<a href="${path}/library/m/all-classes.do?id=0a3bc42823e44ded935f243b31cac6d9">更多 <span>></span></a>
				<br class="clear" />
			</div>

			<div class="collections" id="attics">
				

			</div>

		</div>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				
				
				zdy_ajax({
				   url:_path+"/library/m/recommendclass.do",
				   showLoading:false,
				   success:function(data){
					   var str='';
					   for(var i=0;i<4;i++){
						   if(data[i].name.length>4){
							   data[i].name=data[i].name.substring(0,3)+"..."
						   }
						  str += '<a class="active_A" id="'+data[i].id+'" href="${path}/library/m/all-classes.do?id='+data[i].id+'">'+
						         '<span>'+
						          data[i].name+
						         '</span>'+
						         '<span>'+
						          data[i].count+
						         '</span>'+
						         '</a>';
					   }
					   jQuery('#sysRec').prepend(str);
				   },
				   error:function(data){
					   
				   }
					
				});
				
				
				//藏经阁 内文章点击进入详情页面
				
					 
				
				//藏经阁
				zdy_ajax({
					   url:_path+"/library/m/list.do",
					   data:{
						   'classid':'0a3bc42823e44ded935f243b31cac6d9'
					   },
					   showLoading:false,
					   success:function(data,abc){
						   var str='';
						   var tagArr;
						   var articleTag;
						   var atricledata='';
						   if(data.length>2){
							   atricledata=2;
						   }else{
							   atricledata=data.length;
						   };
						   for(var i=0;i<atricledata;i++){
							   articleTag='';
							   if(data[i].tag){
								   tagArr=data[i].tag.split(',');
								   for(var b=0;b<tagArr.length;b++){
									   articleTag += '<span class="active_A">'+tagArr[b]+'</span>';
								   }
							   }
							  
							    str += '<ul id="'+data[i].id+'"  class="active_A">'+
											'<li>'+
												data[i].title+
											'</li>'+
											'<li>'+
											    articleTag+
												'<span class="active_A howmuchs">'+data[i].readtimes+'人阅读</span>'+
												'<br class="clear" />'+
											'</li>'+
										'</ul>';
						   }
						  $("#attics").append(str);
						  $("#attics ul").bind('click',function(){
							  document.location.href="${path}/library/m/librarydetail.do?type=1&id="+jQuery(this).attr('id');
						  });
					   },
					   error:function(data){
						   
					   }
						
					});
				
				
				//老板内参
				zdy_ajax({
					   url:_path+"/library/m/list.do",
					   data:{
						   'classid':'8467aa44d58b4efbaa83e055ae53eea8'
					   },
					   showLoading:false,
					   success:function(data,abc){
						   var str='';
						   //限制显示最多4个
						    var dataLen;
						   if(data.length > 2){
							   dataLen=2; 
						   }else{
							   dataLen=data.length;
						   }
						   // console.log(data);
						   for(var i=0;i<dataLen;i++){
							   str += '<a href="${path}/library/m/librarydetail.do?type=1&id='+data[i].id+'">'+
								              '<img src="'+_oss_url+data[i].imgpath+'" />'+
                                              '<p>'+data[i].title+'</p>'+
							          '</a>';
						   }
						   
						 $("#bossAdvicer").prepend(str);
					   },
					   error:function(data){
						   
					   }
						
					});
				
				
				//他山之石
				zdy_ajax({
					   url:_path+"/library/m/list.do",
					   data:{
						   'classid':'d143714e0fd74cfc83b225e3935868a5'
					   },
					   showLoading:false,
					   success:function(data,abc){
						   var str='';
						   var dataLen;
						  
						   //限制显示最多4个
						   if(data.length > 4){
							   dataLen=4; 
						   }else{
							   dataLen=data.length;
						   }
						 
						   for(var i=0;i<dataLen;i++){
							   str += '<a href="${path}/library/m/librarydetail.do?type=1&id='+data[i].id+'">'+
								              '<img src="'+_oss_url+data[i].imgpath+'" />'+
                                              '<p>'+data[i].title+'</p>'+
							          '</a>';
						   }
						   
						 jQuery('#othersView').prepend(str);
					   },
					   error:function(data){
						   
					   }
						
					});
			
			})
		</script>
		
	</body>

</html>