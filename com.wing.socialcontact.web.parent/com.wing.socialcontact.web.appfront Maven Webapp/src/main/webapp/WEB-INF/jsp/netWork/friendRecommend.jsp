<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="商友推荐">
		<title>商友推荐</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/friendRecommend.css?v=${sversion}" />
		 <style>
      .swiper-wrapper{
         height:auto;
      }
  </style>
	</head>

	<body>
		<div class="T-frecommend" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
				   <div class="search-box">
				      	<div id="search">搜索</div>
					</div>
					<jsp:include page="../commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="b8d51a3e8a9d41dabd72b9f0b7539ed4" />
					</jsp:include>
					<div class="t-cont" >
<!-- 						<a href="javascript:void(0)" class="active_A"  onclick="layer.msg('该功能暂未开通')"> -->
						<a href="${path}/wx/netWork/same-line.do" class="active_A">
							<img style="width:0.54rem;height:0.56rem;" src="${path}/resource/img/icons/fc_03.jpg" />
							<span>同行精英</span>
						</a>
<!-- 						<a href="javascript:void(0)" class="active_A"  onclick="layer.msg('该功能暂未开通')"> -->
						<a href="javascript:inSameCity()" class="active_A">
							<img style="width:0.44rem;height:0.59rem;" src="${path}/resource/img/icons/fc_05.jpg" />
							<span>同城商友</span>
						</a>
						<a href="${path}/wx/netWork/sameHobby-Circle.do" class="active_A">
							<img src="${path}/resource/img/icons/fc_07.png" />
							<span>同好圈子</span>
						</a>
						<a href="${path}/m/news/viewPage.do" class="active_A">
							<img style="width:0.55rem;height:0.54rem;" src="${path}/resource/img/icons/fr_09.png" />
							<span>商友专访</span>
						</a>
					</div>
					<div class="zhitong">
						<div class="zt">
							<h2>智同道合</h2>
							<span class="active_A" id="turnPage">换一换</span>
						</div>
						<div class="swiper-container1" >
							 <ul class="swiper-wrapper " id="sameHobby" >
							  <%--  <li class="swiper-slide">
									<img src="${path}/resource/img/icons/tou_03.png"/>
									<h2>李自然</h2>
									<span class="FDC">房地产</span>
									<div class="btnAdd active_A">
										<i class="iconfont h-icon">&#xe637;</i>
										<span>加好友</span>
									</div>
								</li> --%>
								
							</ul>
						</div>
					</div>
					<div class="tonghao">
						<div class="th">
							<h2>最新专访</h2>
							<a href="${path}/m/news/viewPage.do" class="th-a">
								<span>更多</span>
								<i class="iconfont ty">&#xe605;</i>
							</a>
						</div>
						<div class="timgBox" id="zxzfdiv">
							
						</div>
					</div>
					<div class="tonghao" style="border-top:1px solid #e6e6e6">
					    <div class="th">
							<h2>大咖来了</h2>
							
							<span id="changeGiant" class="th-a">换一换</span>
							
						</div>
						<div class="giantComing" id="giantComing">
						   
						  
						    <br style="clear:both"/>
						</div>    
					</div>
					
					<div class="tonghao" style="border-top:1px solid #e6e6e6">
					    <div class="th">
							<h2>二度人脉</h2>
							
								<span id="changeSecondCircle" class="th-a" >换一换</span>
								
							
						</div>
						<div class="secondCircle recon" >
						    <div class="tishi">先认证再添加好友</div>	 
						</div>
						<div class="secondCircle reconNo" style="display:none" >
						    <div class="tishi">请先添加好友</div>	 
						</div>
						<div class="secondCircle reconed" id="secondCircle">
						    <!--  <a href="javascript:void(0)"></a>
						     <a href="javascript:void(0)"></a>
						      <a href="javascript:void(0)"></a>
						     <a href="javascript:void(0)"></a> -->
						    
						</div>
						    
					</div>
					
				</div>
			</div>
		</div>

	<%-- <script src="${path}/resource/js/friendRecommended.js?v=${sversion}"
		type="text/javascript" charset="utf-8"></script> --%>
		<%-- <script src="${path}/resource/js/libs/outOfChating.js?v=${sversion}" type="text/javascript" charset="utf-8"></script> --%>
	<script>
	
		     $(document).ready(function(){
		    	  (function(){
			        	var pages=1;
			        	var secondPage=1;
			        	var giantPage=1;
			        	var giantPageA='';
			        	var secondPageA='';
			        	var hasData=false;
			        	sameHobby();
			        	secondCircle();
			        	giantComing();
			        	/* if(!circlelength){
			        		 $('#secondCircle').html('您还没有好友');
			        	} */
			        	
			        	//志同道合 数据获取
			        	$('#turnPage').bind('click',function(){
			        		if(!hasData){
			        			layer.msg('暂无数据');
			        			return;
			        		}else{
				        		pages++;
				        		sameHobby();
			        		}
			        	});
			        	function sameHobby(){
			        		$('#sameHobby').empty(); 
			        		zdy_ajax({
			  				   url:_path+"/im/m/selSameIdeasElite.do",
			  				   showLoading:false,
			  				   data:{
			  					  pageNum:pages,
			  					  pageSize:3
			  				   },
			  				   success:function(data){
			  					   if(data.length){
			  						 hasData=true;
			  					   }
			  					   if(data.length<3 && data.length){
			  						 console.log(pages);
			  						pages = 0;
			  					}else if(!data.length){
			  						if(pages != 1){
			  							pages = 1;
				  						sameHobby();
			  						}
			  					}
			  					
			  					 var str='';
			  					 for(var i=0;i<data.length;i++){
			  					 str += '<li class="swiper-slide">'+
												'<img class="getfriendInfor" userId="'+data[i].id+'" src="'+data[i].head_portrait+'"/>'+
												'<h2>'+data[i].nickname+'</h2>'+
												'<span class="FDC">'+data[i].industry_name+'</span>'+
												'<div class="btnAdd active_A">'+
													'<i class="iconfont h-icon">&#xe637;</i>'+
													'<span class="addFriend" id="'+data[i].id+'">加好友</span>'+
												'</div>'+
											'</li>';
			  					 };
			  					if(!screenflag){
			  						$('#sameHobby').append(str);
			  					}
			  					 var mySwiper1 = new Swiper('.swiper-container1', {
					  					    spaceBetween: 15,
					  			            slidesPerView: 2.3,
					  			            slidesOffsetBefore : 0,
					  			        });
			  					
			  					$('.addFriend').bind('click',function(){
			  						window.location.href="${path}/wx/businessFriend/applyToAddGroup.do?follow_user="+$(this).attr('id');
			  					});
			  					$('.getfriendInfor').bind('click',function(){
			  						window.location.href="${path}/wx/businessFriend/friendInfo.do?follow_user="+$(this).attr('userId');
			  					});
			  				   },
			  				   error:function(data){
			  					   
			  				   }
			  					
			  				});
			        	};	
			        	//二度人脉
			      var circlelength='';  	
			     function secondCircle(){
			    	 if(screenflag){
				    	 $('#secondCircle').html('暂无更多数据');
				    	 $('.reconNo').hide();
				    	 $('.recon').hide();
				    	 $('.reconed').show();
				    	 return;
	  					}
			        zdy_ajax({
							 url:_path+"/im/m/selTwoDegreeConnections.do",
							 data:{
									pageNum:secondPage,
									pageSize:4
								 },
						     showLoading:false,
						     success:function(data,abc){
						    	 
								    if(data.length==0){
								    	$('.reconNo').show()
								    	$('.recon').hide()
								    	$('.reconed').hide()
								    }else{
								    	$('.reconed').show()
								    	$('.recon').hide()
								    	$('.reconNo').hide()
								    }
									 circlelength=data.length;
									 if(data.length<4 && data.length ){
										 $('#secondCircle').html('');
				  						secondPage -= (secondPage--);
				  					  }else if(!data.length&&secondPage!=1){
				  						
				  						secondPage = 1;
				  						$('#secondCircle').html('');
				  						secondCircle();
				  						return;
				  					}
							 
								     var str='';
									 for(var i=0;i<data.length;i++){
										str += '<a id="'+data[i].friend_user+'" style="background:url('+data[i].head_portrait+') no-repeat center;background-size:100% 100%;" href="${path}/wx/businessFriend/friendInfo.do?follow_user='+data[i].friend_user+'"></a>';
										};
										
										$('#secondCircle').append(str);	 
							},
							error:function(data){
									   
							}
									
								});
			        	};
			        	
			        	
			        	
			        	//大咖来了
			        	
			            function giantComing(){
			            	
			        		zdy_ajax({
									 url:_path+"/im/m/selDKList.do",
									 data:{
											pageNum:giantPage,
											pageSize:4
										 },
								     showLoading:false,
								     success:function(data,abc){
									 console.log(data);
									 if(!data.length){
										 if(giantPage!=1){
											 giantPage=1;
											 giantComing();
										 }
										 
									 }else{
										 $('#giantComing').empty();
									 }
								     var str='';
									 for(var i=0;i<data.length;i++){
										
										 str += '<div class="concreat active_A" id="'+data[i].id+'">'+
											      '<div style="background:url('+data[i].head_portrait+') no-repeat center;background-size:100% 100%;" class="iconss">'+'</div>'+
											      '<div class="giantInfo">'+
											         '<span class="giantName">'+data[i].nickname+'</span>'+
											         '<br/>'+
											         '<span class="positions">'+data[i].job_name+'</span>'+
											      '</div>'+
											      '<br style="clear:both"/>'+
											   '</div>';
									   };
										
									   $('#giantComing').prepend(str);
									   $('#giantComing').append('<br style="clear:both"/>');
									   $('.concreat').bind('click',function(){
										   window.location.href="${path}/wx/businessFriend/friendInfo.do?follow_user="+$(this).attr('id');
									   });
									},
									error:function(data){
											   
									}
											
							 });
					       };
			        	
			        	
					       $('#changeSecondCircle').click(function(){
					    	 
					    	   secondPage++;
					    	   secondCircle();
					    	   
					       });
					       
					       $('#changeGiant').click(function(){
					    	   giantPage++;
					    	   giantComing();
					       });
					       
					     //最新专访
				         zxzf();
			        }());	 
			     
		     });

//最新专访	    
  function zxzf(){
   	zdy_ajax({
   		   url:"${path}/m/news/selNewViewList.do",
   		   showLoading:false,
   		   data:{
   			   
   		   },
   		   success:function(data,res){
   			   if(res.code == 0){
   					var c = "";
   					var oss = res.dataobj.ossurl;
   					$.each(res.dataobj.list, function(i, n){
   						 c += '<a href="#" onclick="openurl(\'${path}/m/news/viewDetailPage.do?id='+n.id+'\')"><img src="'+oss+n.imagePath+'" alt="" /> <span>'+n.newsTitle+'</span></a>';
   					});
   					$("#zxzfdiv").append(c);
   				}else{
   					alert(res.msg);
   				}					
   		   },
   		   error:function(data){
   			   
   		   }
   			
   		}); 
   }	
  function openurl(url){
 		document.location.href=url;
 	}	
  
  
  $('#search').click(function(){
	  window.location.href="${path}/wx/netWork/search-BussinessFD.do";
  });
  
  var inSameCity = function() {

		zdy_ajax({
			url : '${path}/m/my/is_address.do',
			type : 'post',
			dataType : 'json',
			success : function(data, res) {
				if ('0'==res.dataobj) {
					self.location.href = '${path}/m/my/add_address.do';
				} else if('1'==res.dataobj)  {
					self.location.href = '${path}/wx/netWork/bussinessFriend-inSameCity.do';
				} 
			}
		});

	}
		</script>
	</body>

</html>