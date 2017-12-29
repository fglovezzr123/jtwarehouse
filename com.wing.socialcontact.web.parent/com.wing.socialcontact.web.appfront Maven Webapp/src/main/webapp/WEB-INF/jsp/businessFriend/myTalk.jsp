
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="我的群聊">
		<title>我的群聊</title>		
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/myTalk.css?v=${sversion}"/>	
	</head>
	<body>
		<div class="T-myTalk" style="background: #f2f3f4;width: 100%; height: 100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
				<div class="myTalk1 active_A">
				   		<div class="myTalk-l">
				   			<span class="iconsssss"></span>
				   			<span>群通知</span>
				   		</div>
				   		<span class="time1" style="display:none"></span>
				   		<i class="iconfont info-R">&#xe605;</i>
				   </div>		   
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				zdy_ajax({
					url: _path+"/im/m/selMyGroupInfoList.do",
				    showLoading:true,
				    data:{
				    	'pageNum':1,
				    	'pageSize':50
				    },
					success: function(data,res){
						console.log(res);
						var data = data.groupInfoList;
						
						$.each(data,function(index){
							  var headpath='';
							  if(!data[index].head_portrait){
								  headpath='${path}/resource/img/icons/qun.png';
							  }else{
								  headpath=data[index].head_portrait;
							  }
							 var str = '<div nickName="'+data[index].group_name+'" class="myTalk active_A ccc" creator="'+data[index].creator+'" qid="'+data[index].group_id+'"><div class="myTalk-l"><span class="iconsssss"><img src="'+headpath+'" /></span><span>'+data[index].group_name+'</span></div><span class="time"></span></div>' 
        					 $('.scrollbar').append(str) 
						});
						$('.myTalk').on('click',function(){
							var index = $(this).index();
							var qid = $(this).attr("qid");
							var objId = new Object();
							objId.nickName=$(this).attr('nickName');
							var objStr = JSON.stringify(objId);
							sessionStorage.setItem('user_info',objStr); 
							window.location.href = "${path}/wx/businessFriend/talkAboutQl.do?qid="+qid
							
						})
						/* console.log(res.dataobj.newRequestCount) */
						if(res.dataobj.newRequestCount){
							$('.time1').css({'display':'block'})
						}else{
							$('.time1').css({'display':'none'})
						}
						$('.myTalk1').on('click',function(){
							window.location.href = _path+ "/wx/businessFriend/mag.do"})	
					},
				    error:function(e){
					   //alert(e);
				    }
				});
			});	
		</script>
	</body>

</html>

