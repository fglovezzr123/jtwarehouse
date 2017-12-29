<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>

<div style="width: 100%;margin: 0 auto;" >
	<div class="div_titlebold" ><c:out value="${n.newsTitle}" /></div>
	<div class="div_sendUser">&nbsp;&nbsp;${fns:fmt(n.createTime,'yyyy-MM-dd HH:mm:ss')}</div>	
	<div class="divider"></div>
	<div style="margin:10px;text-align:center;" align="center" >
	  <img  width="100%" height="100" src="${ossurl}${n.imagePath}"/> 
	</div> 
		
	<div style="margin:10px;" >
		 ${n.content}
	</div>
</div>		