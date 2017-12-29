$(document).ready(function(){
	   var foller = "follow_user";
	   var id_name= window.location.search.substr(1).split('=')[0]
	   var id= window.location.search.substr(1).split('=')[1]
	  
	  
	   if(foller == id_name){
		   console.log(11)
		   $('title').html("添加好友")
	   }else{
		   $('title').html("申请加群")
	   }
	   $('#joinGroup').click(function(){
		   if($('#askInfo').val()==''){
			   layer.msg('请输入请求信息');
			   return;
		   }else{
			   if(foller ==id_name ){
				   console.log(foller);
				 //添加好友
					zdy_ajax({
						url : _path+ "/im/m/addFriendRequest.do",
						showLoading : false,
						data : {
							"friendUserId":id,
							 "askmessage":$('#askInfo').val()
						},
						success : function(data, res) {
							
							$('#askInfo').val('')
						setTimeout(function(){
							   history.back(-1);
						   },1500)
							layer.msg(res.msg);
							
						   
						}
					});	
			   }else{
				
				   zdy_ajax({
					   url:_path+"/im/m/insertGroupRequest.do",
					   showLoading:false,
					   data:{
						   groupId:id,
						   askmessage:$('#askInfo').val()
					   },
					   success:function(data,bc){
						   
						 
						   $('#askInfo').val('')
						   setTimeout(function(){
							   history.back(-1);
							   console.log(11)
						   },1500)
						   layer.msg(bc.msg);
						   
						   
					   },
					   error:function(data){
						   
					   }
						
					});
			   }
		   }
		   
		   
	   });
   })
