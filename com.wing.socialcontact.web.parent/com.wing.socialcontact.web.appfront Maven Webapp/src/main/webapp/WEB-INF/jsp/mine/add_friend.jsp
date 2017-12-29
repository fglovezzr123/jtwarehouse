<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>添加好友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css" />
	</head>
	
	<body style="background: #f2f3f4;">
		<div class="msg">
			<textarea id ="askmessage" name="" rows="" cols="" placeholder="请输入请求说明"></textarea>
			<p>0/200</p>
		</div>
		<div class="pst">
			<div class="M-footer active_A" onclick="add_friend();">
				完成
			</div>
		</div>
		
		<script type="text/javascript">
			var friendUserId="${userId}";
			var add_friend=function(){
				var askmessage=$("#askmessage").val();
				$.ajax({
					url: "${path}/im/m/addFriendRequest.do",
					type : 'post',
					dataType : "json",
					data:{
						friendUserId:friendUserId,
						askmessage:askmessage,
					},
					success: function(data,res){
						if(res == "success"){
							
						}else{
							
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				}); 
			}
			
			
			
			
			
		</script>
	</body>

</html>