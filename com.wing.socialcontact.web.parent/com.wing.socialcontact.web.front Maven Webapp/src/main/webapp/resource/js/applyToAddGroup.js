$(document).ready(function(){
	   var foller = "follow_user";
	   var id_name= window.location.search.substr(1).split('=')[0]
	   var id= window.location.search.substr(1).split('=')[1]
	   if(foller == id_name){
		   /*console.log(11)*/
		   $('title').html("添加好友")
	   }else{
		   $('title').html("申请加群")
	   }
	   $('#askInfo').on('input',function(){
		  /* console.log($.trim($('#askInfo').val()).length)*/
		   $('.msg p').html($.trim($('#askInfo').val()).length+"/200")
	   })
	  
	   $('#joinGroup').click(function(){
		   var value = $.trim($('#askInfo').val())
		  /* console.log(value)*/
		   if($('#askInfo').val()==''){
			   layer.msg('请输入请求信息');
			   return;
		   }else{
			   if(foller ==id_name ){
				   //添加好友
				   if($.trim($('#askInfo').val()).length>200){
					   layer.msg("请输入1-200个字符")
				   }else{
					   //判断填加好友数是否达到每天上限
						zdy_ajax({
							url : _path+'/im/m/is_sendFriend_max.do',
							type : 'post',
							dataType : 'json',
							success : function(data, res) {
								if ('0'==res.dataobj) {
									
									
									 //判断接收好友数是否达到每天上限
										zdy_ajax({
											url : _path+"/im/m/is_getFriend_max2.do?follow_user="+id,
											type : 'post',
											dataType : 'json',
											success : function(data, res) {
												if ('0'==res.dataobj) {
													  zdy_ajax({
															url : _path+ "/im/m/addFriendRequest.do",
															showLoading : false,
															data : {
																"friendUserId":id,
																 "askmessage":value
															},
															success : function(data, res) {
																$('#askInfo').val('')
																layer.msg(res.msg, {shift: -1},  function() {
	                                                                            history.go(-1);
	                                                            });
															 
															}
														});	
												} else if('1'==res.dataobj)  {
													alert("用户今日已达接收上线，请明日尝试。");
													//layer.msg("每日添加请求数达到上限!!提示用户：天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢");
													//setTimeout(function(){history.back(-1);},1500)	   
												} 
											}
										});
										
								} else if('1'==res.dataobj)  {
									alert_back("天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢!",function(){
										 history.back(-1);
									});	   
								} 
							}
						});
				   }	
			   }else{
				   if($.trim($('#askInfo').val()).length>200){
					   layer.msg("请输入1-200个字符")
				   }else{
					   zdy_ajax({
						   url:_path+"/im/m/insertGroupRequest.do",
						   showLoading:false,
						   data:{
							   groupId:id,
							   askmessage:value
						   },
						   success:function(data,res){
							   $('#askInfo').val('')
							   console.log(res)
							   layer.msg(res.msg, {shift: -1},  function() {
                                   history.go(-1);
                                });
							      
						   },
						   error:function(data){
							   
						   }
							
						}); 
				   }
				  
			   }
		   }
		   
		   
	   });
	   
	   
	   
	   if(foller ==id_name ){
		   //添加好友
			   //判断填加好友数是否达到每天上限
				zdy_ajax({
					url : _path+'/im/m/is_sendFriend_max.do',
					type : 'post',
					dataType : 'json',
					success : function(data, res) {
						if ('0'==res.dataobj) {
							 //判断接收好友数是否达到每天上限
								zdy_ajax({
									url : _path+"/im/m/is_getFriend_max2.do?follow_user="+id,
									type : 'post',
									dataType : 'json',
									success : function(data, res) {
										if ('0'==res.dataobj) {
											  zdy_ajax({
													url : _path+ "/im/m/addFriendRequest.do",
													showLoading : false,
													data : {
														"friendUserId":id,
														 "askmessage":value
													},
													success : function(data, res) {
														$('#askInfo').val('')
													layer.msg(res.msg);
													setTimeout(function(){
														   history.back(-1);
													   },1500)	   
													}
												});	
										} else if('1'==res.dataobj)  {
											alert_back("用户今日已达接收上线，请明日尝试！",function(){
												history.back(-1);
											});
											//layer.msg("每日添加请求数达到上限!!提示用户：天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢");
											//setTimeout(function(){history.back(-1);},1500)	   
										} 
									}
								});
								
						} else if('1'==res.dataobj)  {
							alert_back("天九共享网是个极其注意会员信息安全的平台，每天添加好友的数量限制也是对您的保护，谢谢!",function(){
								 history.back(-1);
							});	   
						} 
					}
				});
		   	
	   }
	   
	   
	
	   
	   
	   
   })
