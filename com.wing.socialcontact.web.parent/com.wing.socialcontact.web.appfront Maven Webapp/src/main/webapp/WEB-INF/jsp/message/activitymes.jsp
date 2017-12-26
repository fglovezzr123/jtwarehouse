<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">活动消息</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css"/>
		  
        <link rel="stylesheet" href="${path}/resource/css/system-Messages.css" />

    </head>
    
  
    <body>
        <div class="wrapper">
            <c:forEach  var="sm" items="${smc}" >
            <div class="systemMessage">
            	<div>${sm.content}</div>
            </div>
			</c:forEach>
		  <c:if test="${empty smc}">
          	 <div class="jz">  暂无活动消息!</div> 
          </c:if>
        </div>
 	<script type="text/javascript">
		$(function(){  
			activitMessageReaded();
		});
		
		var systemMessageReaded = function(){
			var url = "${path}/m/message/activitMessageReaded.do";
			
			zdy_ajax({
				url: url,
				data:{},
			    showLoading:false,
				success: function(dataobj,res){
					var data = dataobj;
					if(res.code == 0){
						
					};
				},
			    error:function(e){
				   //alert(e);
			    }
		 }); 
			
		}
	
		
		</script>
    </body>
</html>