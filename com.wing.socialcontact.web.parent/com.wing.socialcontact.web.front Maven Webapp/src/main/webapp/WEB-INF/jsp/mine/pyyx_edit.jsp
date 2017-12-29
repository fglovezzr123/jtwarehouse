<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="编辑朋友印象">
		<title>
			编辑朋友印象
		</title>
		<style type="text/css">
			.uList {
				width: 100%;
				/*padding: 0 .30rem;*/
				background: #FFF;
				font-size: .24rem;
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;
				padding-bottom: .30rem;
			}
			
			.uList li {
				/*width: 2rem;*/
				height: .36rem;
				line-height: .36rem;
				text-align: center;
				padding: 0 .22rem;
				position: relative;
				color:#0f88eb;
				border:1px solid #0f88eb;
				border-radius:0.1rem;
				background:#fff;
				opacity:.5;
			}
			
			.uList li i {
				position: absolute;
				right: -.16rem;
				top: -.16rem;
				width: .32rem;
				height: .32rem;
				background-image: url(${path}/resource/img/images/delete.png);
				background-size: 100%;
				border-radius: 100%;
			}
			
			.uList li.bg {
				background: #0f88eb;
				color: #fff;
				border-radius:0.1rem;
				opacity:1;
			}
			
			.uList li {
				margin-top: .50rem;
				margin-left: .30rem;
				
			}
			
			.M-footer {
				width: 100%;
				height: 1rem;
				text-align: center;
				line-height: 1rem;
				background: #1087eb;
				color: #fff;
				font-size: .30rem;
				position: fixed;
				bottom: 0;
				left: 0;
			}
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<ul class="uList">
			<c:forEach items="${yxList }" var="yx">
				<li class="bg" id="${yx.impress_id }">${yx.list_value }(${yx.impresscount })</li>
			</c:forEach>
			<c:if test="${empty yxList}">
          	 <div class="jz">  暂无好友印象!</div> 
           </c:if>
		</ul>
		<div class="M-footer active_A" id="remove_impress">
			完成
		</div>
		<script type="text/javascript">
		var type="${type}";
		$(function(){  
			$("li").click(function(){
			   
				 if($(this).hasClass("bg")){
				     $(this).removeClass("bg");
				 }else{
				     $(this).addClass("bg");
				 }
			 });
			
		
			 $("#remove_impress").click(function(){
			    var flag=false;
				var result='';
			    $("li").each(function(){
			    	 if(!$(this).hasClass("bg")){
			    		 if(flag){
					    		result+=','
					    	}else{
					    		flag=true;
					    	}
					    	result+=$(this).attr("id");
					    	
					 }
			    });
			 
			    zdy_ajax({
					url: "${path}/m/my/removeusersImpress.do",
				    showLoading:false,
					data:{
						impress:result,
					    type:type
					},
					success: function(data,res){
						if(res.code == 0){
							 self.location=document.referrer;
						}else{
							alert(output.msg);
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				});
			});
			
		});
		
		
		</script>
	</body>

</html>