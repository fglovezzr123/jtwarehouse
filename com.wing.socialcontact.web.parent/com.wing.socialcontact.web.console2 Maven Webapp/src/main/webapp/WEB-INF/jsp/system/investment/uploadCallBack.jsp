<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<script type="text/javascript" src="<%=basePath%>resource/js/jquery/jquery-1.10.2.js"></script>
<input id="baseUrl"   value="<%=basePath%>"  type="hidden"  />
<input id="resultCallback"   value="${callback}"  type="hidden"  />
<input id="resultData"  value="${data}"  type="hidden"   />
<script type="text/javascript">
eval($("#resultCallback").val()+"("+$("#resultData").val()+")");
</script>
