<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%  String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>

<style type="text/css">
	
	li{list-style:none;}
	.uList {
		width: 90%;
/* 		padding: 0 .30rem; */
		background: #FFF;
		font-size: 12px;
		display: flex;
		flex-wrap: wrap;
		box-sizing: border-box;
		padding-bottom: .30rem;
	}
	
	.uList li {
		padding:.1rem
	}
	
	.uList li.epc_bg {
		background: #FF801B;
		color: #fff;
		border-radius: .1rem;
	}
	
	.uList li {
		margin-top: .30rem;
		margin-left: .30rem;
	}
	
</style>

<ul class="uList">
    <c:forEach items="${classes}" var="curr">
    	<%-- <c:if test="${curr.is_content == '1' }">
	      <li class="bg" value="${curr.id }">${curr.listValue}</li>
	    </c:if> 
    	<c:if test="${curr.is_content == '0' }">
	      <li  value="${curr.id }">${curr.listValue}  </li>
	    </c:if>--%>
	      <li onclick="selectClass1($(this))"  value="${curr.id }">${curr.className}  </li>
	</c:forEach>
</ul>

<div  style="margin-top: 10px;margin-bottom: 10px;margin-left: 50px;">
	  <button type="button" class="btn btn-primary"  onclick="selentryClass()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
</div>


<script type="text/javascript">

function selectClass1(th){
		if(!th.hasClass("epc_bg")){
			th.addClass("epc_bg");
		 }else{
			 th.removeClass("epc_bg");
		 }
}
function selentryClass(){
	$("#entryPrise_sel_Class_content").html('');
	var html = "";
    var result='';
    $("li.epc_bg").each(function(){
   		var value = $(this).attr("value");
   		html+="<button onclick='delPic(this)' class='up_sel_class' type='button' id='"+value+"'>"+$(this).html()+"</button>&nbsp;&nbsp;";
    });
   $("#entryPrise_sel_Class_content").append(html);
	$("#${param.rel}_selclass_add").dialog("close");
}
</script>