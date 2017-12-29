<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<input id="baseUrl"   value="${path }"  type="hidden"  />
<input id="resultCallback"   value="${callback}"  type="hidden"  />
<input id="resultData"  value="${data}"  type="hidden"   />
<input id="frameId"  value="${frameId}"  type="hidden"   />
<input id="inputId"  value="${inputId}"  type="hidden"   />
<input id="formId"  value="${formId}"  type="hidden"   />
<script type="text/javascript">
eval($("#resultCallback").val()+"("+$("#resultData").val()+",'"+$("#frameId").val()+"','"+$("#inputId").val()+"','"+$("#formId").val()+"')");
</script>
