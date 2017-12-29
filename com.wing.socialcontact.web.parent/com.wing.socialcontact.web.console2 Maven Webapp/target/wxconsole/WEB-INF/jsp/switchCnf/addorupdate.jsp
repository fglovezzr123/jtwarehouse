<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
	.ke-icon-tel {
        background-image: url(resource/js/editor/themes/default/timg.jpg);
        width: 16px;
        height: 16px;
	}
	.distant1 {
	    color: green;
	    font-weight: bold;
	    text-shadow: 0 0 1px currentColor,-1px -1px 1px #030,0 -1px 1px #030,1px -1px 1px #030,1px 0 1px #030,1px 1px 1px #030,0 1px 1px #030,-1px 1px 1px #030,-1px 0 1px #030;
	}
</style>

<div style="width:100%;">						
<form  id="dataForm">

	<input type="hidden" name="id" value="${obj.id}"/>
	<input type="hidden" name="createTime"  value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="createUserId"  value="${obj.createUserId}">
	<input type="hidden" name="updateTime" value="${fns:fmt(obj.updateTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="updateUserId"  value="${obj.updateUserId}">
	<input type="hidden" name="deleted"  value="${obj.deleted}">

	<table class="table table-bordered" style="margin-top:0px;width:99%;">

		<tr>
			<th style="width:15%;">key<font color="red">*</font>：</th>
			<td colspan=3>
				<select  name="keyWord"  id="keyWord" >
					<c:forEach items="${pageContentTypeList}" var="p">
						<option id="${p.contentKey}" value="${p.contentKey}" <c:if test="${p.contentKey==obj.keyWord}">selected</c:if> >${p.contentKey} </option>
					</c:forEach>
				</select>
			<%--	<input type="text" maxlength="100" id="keyWord" name="keyWord" value="${obj.keyWord}" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"   class="span2" style="width:98%"/>--%>
			</td>
		</tr>
		<tr>
			<th>配置功能：</th>
			<th  colspan=3>
				<input type="checkbox"  <c:if test="${1==obj.collection}">checked</c:if> name="collection" value="1"/>收藏
				<input type="checkbox"  <c:if test="${2==obj.thumbUp}">checked</c:if>  name="thumbUp" value="2"/>点赞
				<input type="checkbox"   <c:if test="${4==obj.comment}">checked</c:if> name="comment" value="4"/>评论
				<input type="checkbox"  <c:if test="${8==obj.reward}">checked</c:if>  name="reward" value="8"/>打赏
				<input type="checkbox"  <c:if test="${16==obj.share}">checked</c:if>  name="share" value="16"/>分享
			</th>
		</tr>
		<tr>
			<th></th>
			<td colspan=3>
				<div  style="margin-top: 10px;margin-bottom: 10px;">
					  <button type="button" onclick="save()" class="btn btn-primary" >提交</button>
				</div>
			</td>
		 </tr>
	</table>
</form>
<span class="typefile" id="filePicker"></span>
</div>
<script src="http://malsup.github.io/jquery.form.js"></script>
<script type="text/javascript">

//保存
function save(){

	$("#dataForm").ajaxSubmit({
		url:"switchCnf/saveOrUpdate.do",
		type: 'post',	
		data: {},
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#datagrid").datagrid('reload');
				$("#addPage").dialog("destroy");
				$("#updatePage").dialog("destroy");
			}else{
				alert("操作失败");
			}
		}
	});
}
</script>