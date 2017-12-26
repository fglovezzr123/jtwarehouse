<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="好友印象">
		<title>印象</title>

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
				width: .90rem;
				height: .36rem;
				line-height: .36rem;
				text-align: center;
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
			<c:forEach items="${impressList}" var="yxk">
				<c:set var="c" value="0"/>
				<c:forEach items="${userFriendimpresss}" var="yx">
					<c:if test="${yxk.id eq yx.impress_id}">
						<c:set var="c" value="1"/>
					</c:if>
				</c:forEach>
				<c:choose>
					<c:when test="${c eq 1}">
						<!-- <li class="bg" id="${yxk.id }">${yxk.listValue }</li> -->
					</c:when>
					<c:otherwise>
						<li id="${yxk.id }">${yxk.listValue }</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
		</ul>

		<div class="M-footer active_A"  id="add_impress">
			完成
		</div>
		<script type="text/javascript">
		var type="${type}";
		var url="";
		var userId="${userId}";
		$(function(){  
			$("li").click(function(){
				 if(!$(this).hasClass("bg")){
				    $(this).addClass("bg");
				 }else{
				    $(this).removeClass("bg");
				 }
			 });
			
		
			 $("#add_impress").click(function(){
			    var flag=false;
				var result='';
			    $("li.bg").each(function(){
			    	if(flag){
			    		result+=','
			    	}else{
			    		flag=true;
			    	}
			    	result+=$(this).attr("id");
			    });
			 
			    if(type==0){
			    	url="${path}/m/my/addusersImpress0.do";
			    }else{
			    	url="${path}/m/my/addusersImpress.do";
			    }
			    zdy_ajax({
					url: url,
				    showLoading:false,
					data:{
						impress:result,
					    type:type,
					    userId:userId
					},
					success: function(data,res){
						if(res.code == 0){
							if(parent){
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								var ret='';
								  $("li.bg").each(function(){
									  ret+='<span class="tags">'+$(this).text()+'</span>';
								   });
								 if(type==0){
									 //parent.$("#type0").append(ret);
								 }else if(type==1){
									// parent.$("#type1").html(ret);
								 }else if(type==2){
									 //parent.$("#type2").html(ret);
								 }
								 
								parent.reload();
								parent.layer.close(index);
							}
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