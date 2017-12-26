<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="我的好友">
		<title>删除好友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/businessFriend.css?v=${sversion}"/>
		<style type="text/css">
		.sort_box{ 
		  margin-bottom:1rem;
		}		
		.initials {
          top: 2px;
   
                 }
		</style>
	</head>
	
	<body>
		<div class="T-talkAbout" style="background: #f2f3f4;width: 100%; height: 100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
					<div id="letter"></div>
					<div class="sort_box">
					</div> 
					<div class="M-footer active_A">
			                                         删除
		            </div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		var fromUserId = ${sessionScope.me.id };
		 var qid = window.location.href.split("=")[1]
		 zdy_ajax({
			 url : _path + "/im/m/selGroupInfoById.do",
				showLoading : false,
				data : {
					groupId : qid
				},
			success: function(data,res){
				var data = data.users
				 console.log(data);
				 $.each(data,function(index){
					if(data[index].user_id==fromUserId ){
						var str = '<div class="sort_list active_A" user_id="'+data[index].user_id+'">'+
				          '<div class="num_logo">'+
			                '<img src="'+data[index].head_portrait+'" />'+
	                      '</div>'+
	                      '<div class="num_name">'+data[index].nickname+'</div>'+
                     '</div>';
			
		               $('.sort_box').prepend(str);
					}else{
						var str = '<div class="sort_list active_A" user_id="'+data[index].user_id+'">'+
				          '<div class="num_logo">'+
			                '<img src="'+data[index].head_portrait+'" />'+
	                      '</div>'+
	                      '<div class="num_name">'+data[index].nickname+'</div>'+
	                      '<input type="checkbox" name="jq" id="jq" value="" />'+
                     '</div>';
			
		           $('.sort_box').append(str);
					}
					
				}); 
				  $('.M-footer').on('click', function() {
					     var  strId = '';
					     var input= $('input[type=checkbox]');
					     var  len = $('input[type=checkbox]').length;
					     $.each(input,function(index){
					    	 if(input.eq(index).prop('checked')){
							 	  var follow_user = $(this).parents('.sort_list').attr('user_id');  
							 	  strId+=follow_user+','
					    	 }
					     });
					     if(strId){
					    	    var len = strId.length
								var str1= strId.slice(0,len-1);
					    	    delGroupUsers(str1)
					     }else{
					    	 layer.msg("请选择好友")
					     }
					   
				 }); 
				
			},
		    error:function(e){
			   //alert(e);
		    }
		});
		 function kkk(label){
			 var k =''
			 $.each(label,function(i){
				 k+=label[i].id+','
				})
			 var len = k.length
				var str2= k.slice(0,len-1);
	    	    return str2
		 }
		//删除群成员
		 function delGroupUsers(str1){
			console.log(str1)
				    zdy_ajax({
						url: _path+"/im/m/delGroupUsers.do",
					    showLoading:true,
					    data:{
					    	 groupId:qid,
					    	 userIds:str1,	    	
					    },
					    success: function(data,res){
							layer.msg(res.msg)
							setTimeout(function(){
							   history.back(-1);
						   },1500)
					    },
					    error:function(e){
						   //alert(e);
					    }
					}); 
		 }
		 
		
		 
		</script>
	</body>

</html>
