<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：悬赏管理
--%>



<div style="width: 750px;margin: 0 auto;" >

	 <table class="table table-nobordered " style="margin-top: 25px;">
	   <tr>
			    	<th style="width: 100px">悬赏问题：</th>
			    	<td >
			     		${r.question}
			    	</td>
			    	<tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td>
			     		<fmt:formatDate  value='${r.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">悬赏金额：</th>
			    	<td >
			     		${r.reward}J币
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">悬赏有效期时间：</th>
			    	<td >
			     		<fmt:formatDate  value="${r.startTime}" pattern="yyyy-MM-dd" />
						-
					   <fmt:formatDate  value="${r.endTime}" pattern="yyyy-MM-dd" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">发布人：</th>
			    	<td >
			     		${r.nickname}(${r.job}/${r.comName})
			    	</td>
			  </tr>
		</tr>
	</table>
	
	 <div class="divider"></div>
	 <div id="comments">
	    <c:forEach var="item" items="${list}">
	    	<table class="table table-bordered "  style="margin-top: 25px;">
		   <tr>
			<th style="text-align:left;" width="30%">${item.nickname}(${item.job}/${item.comName})</th>
		    <th  style="text-align:left;" width="55%"><fmt:formatDate  value='${item.createTime}' pattern='yyyy-MM-dd HH:mm:ss' /></th>
		    <th  style="text-align:left;" width="15%">
		       <c:if test="${item.isAccept==1}">已采纳</c:if>
		    </th>
		   </tr>
		   <tr>
		    <td colspan="2">${item.content}</td>
		    <td >回复数：${item.subcount}</td>
		   </tr>
		    <c:forEach var="ct" items="${item.subCommentList}">
		    <tr>
		    <th>${ct.nickname}(${ct.job}/${ct.industry})回复${item.nickname}</th>
						<th style="text-align:left;" colspan="2"><fmt:formatDate  value='${ct.createTime}' pattern='yyyy-MM-dd HH:mm:ss' /></th>
						
			</tr>
			<tr>
					<td colspan="3">${ct.cmeDesc}</td>
			</tr>
		  
		     </c:forEach>
		  </table>
	    </c:forEach>
	 </div>
	 <div>
	  <table class="table table-nobordered " style="margin-top: 25px;">
	   <tr>
			<th style="width: 45%"></th>
			<td>
			 <div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick = "showview('${r.id}')">编辑</button>
						 
			</div>
			</td>
	  </tr>
	</table>
	</div>
</div>

<script type="text/javascript">	
function showview(fid){
	if('${r.status}'=='5'){
		Msg.alert("提示","该悬赏已过期,不能修改！","warning",null);
		return;
	}else if('${r.status}'=='3'){
		Msg.alert("提示","该悬赏已取消,不能修改！","warning",null);
		return;
	}else if('${r.status}'=='4'){
		Msg.alert("提示","该悬赏已完成,不能修改！","warning",null);
		return;
	}
	$("#${param.rel}_show").dialog("close");
	MUI.openDialog('修改','reward/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:550});
}

</script>
