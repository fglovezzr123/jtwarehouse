<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="修改群名">
		<title>修改群名</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css?v=${sversion}" />
	</head>
	<body style="background: #f2f3f4;">
		<div class="msg">
			<textarea id="askInfo" name="" rows="" cols="" placeholder="请输入..." onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"></textarea>
			<p>0/15</p>
		</div>
		<div class="pst">
			<div id="joinGroup" class="M-footer active_A">
				完成
			</div>
		</div>
		<script type="text/javascript" charset="utf-8">
		$(document).ready(function(){
			   var qid= window.location.search.substr(1).split('=')[1]
			   console.log(qid)
			   $('#askInfo').on('keyup',function(){
				   if($(this).val().length>15){
					   layer.msg("字数超出限制")
					   var str = $(this).val().slice(0,15)
					   $(this).val(str)
				   }
				   $('.msg p').html($('#askInfo').val().length+"/15")
			   })
			   $('#joinGroup').click(function(){
				   if($('#askInfo').val()==''){
					   layer.msg('请输入请求信息');
					   return;
				   }else{
						   if($('#askInfo').val().length>15){
							  
						   }else{
							    zdy_ajax({
								url : _path+ "/im/m/updateMyGroupInfo.do",
								showLoading : false,
								data : {
									 id:qid,
									 groupName:$('#askInfo').val()
								},
								success : function(data, res) {
									layer.msg(res.msg);	  
								    setTimeout(function(){
									   history.back(-1);
								   },1500)		 
								},
								 error:function(data){
									   
								   }
							})		
					   }  }
			   });
		   })

		
		</script>
	</body>

</html>