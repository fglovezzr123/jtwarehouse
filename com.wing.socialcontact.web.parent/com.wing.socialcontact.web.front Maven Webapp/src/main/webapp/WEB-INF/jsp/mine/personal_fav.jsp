<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>爱好</title>

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
				padding:.1rem
			}
			
			.uList li.bg {
				background: #0f88eb;
				color: #fff;
				border-radius: .1rem;
			}
			
			.uList li {
				margin-top: .30rem;
				margin-left: .30rem;
			}
			
			.M-footer {
				width: 100%;
				height: 1rem;
				text-align: center;
				line-height: 1rem;
				background: #0f88eb;
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
		    <c:forEach items="${favList}" var="curr">
		    	<c:if test="${curr.is_content == '1' }">
			      <li class="bg" value="${curr.id }">${curr.listValue}</li>
			    </c:if>
		    	<c:if test="${curr.is_content == '0' }">
			      <li  value="${curr.id }">${curr.listValue}  </li>
			    </c:if>
			</c:forEach>
		</ul>

		<div class="M-footer active_A"  id="add_fav">
			完成
		</div>
		<script type="text/javascript">
		
		var isrun=true;
		$(function(){  
			$("li").click(function(){
				 if(!$(this).hasClass("bg")){
				    $(this).addClass("bg");
				 }else{
				    $(this).removeClass("bg");
				 }
			 });
			
		
			 $("#add_fav").click(function(){
			    var flag=false;
				var result='';
			    $("li.bg").each(function(){
			    	if(flag){
			    		result+=','
			    	}else{
			    		flag=true;
			    	}
			    	result+=$(this).attr("value");
			    });
			    
			    if($("li.bg").length>20){
			    	alert("最多选择20个标签!");
			    	return;
			    }
			   
			    console.log(result)
			    if(isrun){
			    	isrun=false;
			    zdy_ajax({
					url: "${path}/m/my/add/addusers.do",
				    showLoading:false,
					data:{
						isfav:"1",
						favs:result
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
			    }
			});
			
		});
		
		
		</script>
	</body>

</html>