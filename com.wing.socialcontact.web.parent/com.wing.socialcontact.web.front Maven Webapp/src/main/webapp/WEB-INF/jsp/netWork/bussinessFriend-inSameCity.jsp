<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">同城商友</title>
		
		<link rel="stylesheet" href="${path}/resource/css/tonghang.css?v=${sversion}" />
		 
		<style>
			#sameIndustry{
			    margin-top:0
			 }
		</style> 
	</head>

	<body>
		
		<div class="wrapper">
            <div id="sameIndustry">	   
            </div>	
		</div>
	
	
		<script>
		    $(document).ready(function(){
		    	var curPageNum=1;
		    	getData();
		    	function getData(){	
				    	zdy_ajax({
		  				   url:_path+"/im/m/selCityElite.do",
		  				   showLoading:false,
		  				   data:{
		  					 pageNum:curPageNum,
		  					 pageSize:20
		  				   },
		  				   success:function(data,bc){
// 		  					 if(screenflag){
// 		  							layer.msg('暂无更多数据');
// 		  							return;
// 			  					}
		  					  console.log(data);
		  					  var str='';
		  					  if(!data.length){
		  						layer.msg('暂无更多数据');
		  					  };
		  					  for(var i=0;i<data.length;i++){
		  						  str += '<div class="belongItem">'+
							  				    	'<div>'+
							  			    		'<div userId="'+data[i].id+'" class="iconsssss active_A" style="background:url('+data[i].head_portrait+') no-repeat center;background-size:0.7rem 0.7rem;">'+'</div>'+
							  			    	'</div>'+
							  			    	'<div>'+
							  			    		'<h1>'+data[i].nickname+'</h1>'+
							  			    		'<span>'+data[i].job_name+'/'+data[i].com_name+'</span>'+
							  			    		'<button class="addinggg active_A" id="'+data[i].id+'" >添加</button>'+
							  			    	'</div>'+
							  			    	'<br class="clear"/>'+
							  			  '</div>';
		  					  }
		  					    $('#sameIndustry').append(str);
		  					    $('.addinggg').bind('click',function(){
		  					         var that = $(this)
		  					    	 //判断填加好友数是否达到每天上限
		  							zdy_ajax({
		  								url : _path+'/im/m/is_sendFriend_max.do',
		  								type : 'post',
		  								dataType : 'json',
		  								success : function(data, res) {
		  									if ('0'==res.dataobj) {
		  										 //判断接收好友数是否达到每天上限
		  										zdy_ajax({
		  											url : _path+"/im/m/is_getFriend_max2.do?follow_user="+that.attr('id'),
		  											type : 'post',
		  											dataType : 'json',
		  											success : function(data, res) {
		  												if ('0'==res.dataobj) {
		  			  			  					    	window.location.href="${path}/wx/businessFriend/friendInfo.do?follow_user="+that.attr('id');
		  												} else if('1'==res.dataobj)  {
		  													alert("用户今日已达接收上线，请明日尝试。");
		  													//layer.msg("每日添加请求数达到上限!!提示用户：天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢");
		  													//setTimeout(function(){history.back(-1);},1500)	   
		  												} 
		  											}
		  										});
		  									} else if('1'==res.dataobj)  {
		  										alert_back("天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢!",function(){
		  										});	   
		  									} 
		  								}
		  							});
		  					    });
		  					    
		  					  $('.iconsssss').bind('click',function(){
		  						//判断填加好友数是否达到每天上限
			  					var that = $(this)
								window.location.href="${path}/wx/businessFriend/friendInfo.do?follow_user="+that.attr('userId');

		  						/*
		  							zdy_ajax({
		  								url : _path+'/im/m/is_sendFriend_max.do',
		  								type : 'post',
		  								dataType : 'json',
		  								success : function(data, res) {
		  									if ('0'==res.dataobj) {
		  										 //判断接收好友数是否达到每天上限
		  										zdy_ajax({
		  											url : _path+"/im/m/is_getFriend_max2.do?follow_user="+that.attr('userId'),
		  											type : 'post',
		  											dataType : 'json',
		  											success : function(data, res) {
		  												if ('0'==res.dataobj) {
		  													window.location.href="${path}/wx/businessFriend/friendInfo.do?follow_user="+that.attr('userId');
		  												} else if('1'==res.dataobj)  {
		  													alert("用户今日已达接收上线，请明日尝试。");
		  													//layer.msg("每日添加请求数达到上限!!提示用户：天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢");
		  													//setTimeout(function(){history.back(-1);},1500)	   
		  												} 
		  											}
		  										});
		  			  					    	
		  									} else if('1'==res.dataobj)  {
		  										alert_back("天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢!",function(){
		  										});	   
		  									} 
		  								}
		  							});*/
		  					    });
		  				   },
		  				   error:function(data){
		  					   
		  				   }
		  					
		  				});
		    	      
		    	
		    		
		     } ;
		     
		   //获取行业
		     
		     
		  //滚动加载
		  /*   $(window).scroll(function(){
			       var scrollTop=$(this).scrollTop();
			        var scrollHeight = $(document).height();
			            var windowHeight = $(this).height();
			            if (scrollTop+windowHeight==scrollHeight) {
			            	curPageNum++;
			            	getData(); 
			            	
			            };
			    })*/
		  })
		</script>
	</body>

</html>