<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<c:forEach var="item" items="${list}">
<div class="item active_A" data-id="${item.id}" onclick="showDetail(this)">
	<div>
		<p><strong>[${item.prjTypeName}]</strong>${item.prjName}</p>
		<p style="top:30px;text-overflow:ellipsis;white-space:wrap;overflow:hidden;">${item.prjDesc}</p>
	</div>
	<div style="background-image: url('${item.imgUrl}');"></div>
	<br class="clear" />
</div>
</c:forEach>