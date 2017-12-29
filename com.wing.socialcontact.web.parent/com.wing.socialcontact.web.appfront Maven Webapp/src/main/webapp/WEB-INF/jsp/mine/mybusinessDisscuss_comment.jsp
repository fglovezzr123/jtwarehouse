<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

        <title id="title">我的合作评论</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		
        <link rel="stylesheet" href="${path}/resource/css/printActiveComment.css" />

    </head>
    
  
    <body>
        <div class="wrapper">
        <c:forEach  var="subComment" items="${commentList}" >
            <c:if test="${!empty subComment.fkMap}">
             <a href="${path}/m/business/detailPage.do?id=${subComment.fkMap.id}&type=2"><div class="print-activity">
                 <h3>
                    ${subComment.fkMap.titles}<font color="blue">查看详情</font><span><fmt:formatDate value='${subComment.fkMap.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span>
                 </h3>
                 <p>
                   <span>评论内容：</span> ${subComment.cmeDesc}
                   </br><font color="#52a1c0">查看详情</font>
                 </p>
             </div>
            </c:if>
         </c:forEach>
         <c:if test="${empty commentList}">
          	 <div class="jz">  暂无合作评论!</div> 
         </c:if>
        </div>
	
    </body>
</html>