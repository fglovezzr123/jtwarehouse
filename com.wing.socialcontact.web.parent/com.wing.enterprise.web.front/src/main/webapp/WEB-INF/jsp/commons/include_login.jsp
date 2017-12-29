<!-- 必须登录 -->
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.wing.socialcontact.common.model.Member"%>
<%@page import="com.wing.socialcontact.util.Constants"%>
<% 
String path2 = request.getContextPath(); 
Member me=(Member)request.getSession().getAttribute("me");
String userid = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_ID);
String headpic = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_HDPIC);
String nickname = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_NICKNAME);
// if(null == me){
// 	response.sendRedirect(path2+"/m/app/loginPage.do");
// }

if(null == me){
%>  
<script>
gotologin();
</script>
   
<%
}
%> 
<%if(null != me){
%>  
<script>
localStorage.setItem("mId", '${me.getId()}');
</script>
   
<%
}
%>   