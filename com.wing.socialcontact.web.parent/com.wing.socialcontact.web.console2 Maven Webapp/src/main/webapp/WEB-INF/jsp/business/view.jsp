<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 750px;margin: 0 auto;" >
		 <table class="table table-nobordered " style="margin-top: 25px;">
		    <tr>
			    	<th style="width: 100px">合作标题：</th>
			    	<td >
			     		${b.titles}
			    	</td>
			  </tr>
			 <tr>
			    	<th style="width: 100px">发布人：</th>
			    	<td >
			     		${b.createUserName}
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td >
			     		${b.createTime}
			    	</td>
			  </tr>
			   
			   <tr>
			    	<th style="width: 100px">合作分类：</th>
			    	<td >
			     	${b.className}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">有效期：</th>
			    	<td >
			     		${b.startTime}
						-
					  ${b.endTime}
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">悬赏金额：</th>
			    	<td >
			     		${b.reward}J币
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">合作诉求：</th>
			    	<td >
			     		${b.appealSummary}
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">合作说明：</th>
			    	<td >
			     	${b.appealDesc}
			    	</td>
			  </tr>
		</table>	 	
		 <div class="divider"></div>
	 <div id="comments">
	    <c:forEach var="item" items="${list}">
	    	<table class="table table-bordered "  style="margin-top: 25px;">
		   <tr>
			<th style="text-align:left;" width="30%">${item.nickname}(${item.job}/${item.comName})</th>
		    <th  style="text-align:left;" width="55%">${item.createTime}</th>
		    <th  style="text-align:left;" width="15%">
		       <c:if test="${item.isAccept==1}">已采纳</c:if>
		    </th>
		   </tr>
		   <tr>
		    <td colspan="2">${item.content}</td>
		    <td >回复数：${item.subcount} 赞：${item.count}</td>
		   </tr>
		    <c:forEach var="ct" items="${item.subCommentList}">
		    <tr>
		    <th>${ct.nickname}(${ct.job}/${ct.industry})回复${item.nickname}</th>
						<th style="text-align:left;" colspan="2">${ct.createTime}</th>
						
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
						  <button type="button" class="btn btn-primary" onclick = "showview('${b.id}')">编辑</button>
						 
			</div>
			</td>
	  </tr>
	</table>   
</div>

<script type="text/javascript">	
function showview(fid){
	if('${b.rewardFinish}'=='3'){
		Msg.alert("提示","该合作已过期,不能修改！","warning",null);
		return;
	}
	$("#${param.rel}_show").dialog("close");
	MUI.openDialog('修改','business/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:550});
}

</script>
