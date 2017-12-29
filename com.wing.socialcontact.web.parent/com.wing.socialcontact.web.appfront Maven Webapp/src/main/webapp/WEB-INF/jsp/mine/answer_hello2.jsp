<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>个人设置</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css" />
	</head>
	
	<body style="background: #f2f3f4;">
		<div class="msg">
		
		    <c:if test="${not empty userGreetings.answermessage}">
				<textarea id ="answermessage" name="" rows=""  disabled="disabled" cols="" >${userGreetings.answermessage}</textarea>
		    </c:if>
		    <c:if test="${empty userGreetings.answermessage}">
				<textarea id ="answermessage" name="" rows=""  disabled="disabled" cols="" placeholder="尚未回复"></textarea>
		    </c:if>
		</div>
		
		
		<script type="text/javascript">
			var ugId="${ugId}";
			var answer_hello=function(){
				var answermessage=$("#answermessage").val();
				$.ajax({
					url: "${path}/m/my/answerusersgreetings.do",
					type : 'post',
					dataType : "json",
					data:{
						ugId:ugId,
						answermessage:answermessage,
					},
					success: function(data,res){
						///alert(res);
						if(res == "success"){
							if(parent){
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);
							}
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