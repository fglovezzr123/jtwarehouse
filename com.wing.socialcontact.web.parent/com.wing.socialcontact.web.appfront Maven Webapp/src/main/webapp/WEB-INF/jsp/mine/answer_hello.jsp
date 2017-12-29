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
			<textarea id ="answermessage" name="" rows="" cols="" placeholder="请输入回复内容"></textarea>
			<p id="num_txt1">0/200</p>
		</div>
		<div class="pst">
			<div class="M-footer active_A" onclick="answer_hello();">
				回复
			</div>
		</div>
		
		<script type="text/javascript">
			var ugId="${ugId}";
			function isNull( str ){
				if ( str == "" ) return true;
				var regu = "^[ ]+$";
				var re = new RegExp(regu);
				return re.test(str);
			}
			var answer_hello=function(){
				var answermessage=$("#answermessage").val();
	            if(answermessage == null || answermessage == undefined || answermessage == ''){
					
					alert_back("回复内容不能为空",function(){
						$("#answermessage").focus();
					});
					return;
				}
				
				if(isNull(answermessage)){
					alert_back("回复内容不能为空",function(){
						$("#answermessage").focus();
					});
					return;
				}
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
								parent.reload();
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
			
			
			$(function(){
	    		   $('#answermessage').on('keyup',function(){
	    		       var txtval = $('#answermessage').val().length;
	    		       console.log(txtval);
	    		      var str = parseInt(200-txtval);
	    		      console.log(str);
	    		        if(str > 0 ){
	    		          $('#num_txt1').html(txtval+'/200');
	    		      }else{
	    		          $('#num_txt1').html('剩余可输入0字');
	    		          $('#answermessage').val($('#answermessage').val().substring(0,200)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
	    		        }
	    		        //console.log($('#num_txt').html(str));
	    		    });
	    		})
		</script>
	</body>

</html>