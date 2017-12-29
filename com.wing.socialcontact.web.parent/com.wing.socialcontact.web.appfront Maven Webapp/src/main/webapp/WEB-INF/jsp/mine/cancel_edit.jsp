<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>延期申请</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css" />
	</head>
	
	<body style="background: #f2f3f4;">
		<div class="msg">
			<input value="取消原因：(200字)" readonly="readonly"/><textarea rows="3" cols="1" id="description"  ></textarea>
		</div>
		<div class="pst">
			<div class="M-footer active_A" onclick="add_info('${id}');">
				提交
			</div>
		</div>
		
		<script type="text/javascript">
		
		$(function(){
 		   $('#description').on('keyup',function(){
 		       var txtval = $('#description').val().length;
 		      var str = parseInt(200-txtval);
 		        if(str > 0 ){
 		      }else{
 		          $('#description').val($('#description').val().substring(0,200)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
 		        }
 		    });
 		})
			var add_info=function(id){
				
				var description = $("#description").val();
				if(isEmpty(description)){
					alert_back("请填写取消原因",function(){
						$("#description").focus();
					});
					return;
				}
				zdy_ajax({
					url: "${path}/m/activity/cancel.do",
				    showLoading:false,
					data:{
						activityId:id,
						description:description
					},
					success: function(data,res){
						if(res.code == 0){
							if(parent){
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);
							}
						}else{
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
							alert(output.msg);
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