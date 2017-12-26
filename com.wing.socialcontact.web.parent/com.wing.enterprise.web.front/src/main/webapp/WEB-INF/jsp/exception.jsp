<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/commons/jstl_message_tld.jsp"%>
<%
	response.addHeader("exception", "1");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title></title>
<meta http-equiv=content-type content="text/html; charset=utf-8">
<link href="favicon.ico" rel="SHORTCUT ICON" />
<style type=text/css>
	td{
		font-size: 12px;
		font-family: tahoma,sans-serif
	}
</style>
</head>
<body style="font-size: 12px;font-family: tahoma,sans-serif;background-color: #cccccc;margin-left:0px;">
	<table height="95%" cellspacing=0 cellpadding=0 width="100%" align=center border=0>
		<tbody>
			<tr valign=center align=middle>
				<td>
					<table cellspacing=0 cellpadding=0 width=280 bgcolor=#ffffff style="margin-left:-20px;"
						border=0>
						<tbody>
							<tr>
								<td align=middle bgcolor=#eeeeee height=50 style="margin-left:10px;">
									<p style="margin-left:10px;">
										<strong>${message }<br><br></strong>
									</p>
								</td>
							</tr>
							<tr>
								<td align=left height=80>
									<p id=lid2 style="margin-left:10px;">提示：</p>
									<ul>
										<c:choose>
										<c:when test="${not empty tips}">
										<li id=list1>${tips}</li>
										</c:when>
										<c:otherwise>
										<li id=list1>可稍后再试，或刷新页面重新试试。</li>
										<li id=list2>联系管理员。</li>
										</c:otherwise>
										</c:choose>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
