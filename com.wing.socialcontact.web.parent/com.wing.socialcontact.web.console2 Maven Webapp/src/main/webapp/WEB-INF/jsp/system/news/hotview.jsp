<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理--首页热推
--%>



<div style="width: 100%;margin: 0 auto;" >
	
	<div class="div_titlebold" ><c:out value="${n.newsTitle}" /></div>
	<div class="div_sendUser">&nbsp;&nbsp;<c:out value="${n.createTime}" /></div>	
	<div class="divider"></div>
		
	<div style="margin:10px;" >
		 ${n.content}
	</div>
	<%-- <div style="margin:10px;text-align:center;" align="center" >
	  <img  width="100%" height="100" src="${ossurl}${n.imagePath}"/> 
	</div>  --%>
	<div style="margin:20px;text-align:left;" >
	  评论数：<span id="count">${n.count}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;阅读人数：<span id="lookCountv">${n.lookCount}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <button  class="btn btn-primary" onclick="tzsl()">调整数量</button>
	</div> 
	<div class="divider"></div>
	
	<table class="table table-nobordered " style="margin-top: 25px;">
		<tr>
				<th width="180px">
					手机号：
				</th>
				<td colspan="3">
					<input type="text" id="mobile" class="easyui-numberbox"  />
				</td>
		</tr>
		<tr>
				<th>
					评论内容：
				</th>
				<td colspan="3">
					<textarea  id="cmeDesc" rows="2" cols="100" maxlength="200"></textarea>
					<br/>
					<button style="margin-top:16px" class="btn btn-primary" onclick="addComments()">评论</button>
				</td>
		</tr>
	 </table>	
	 <div class="divider"></div>
	 <div id="comments">
	    
	 </div>
	 
	  
	
	 
	
</div>

<script type="text/javascript">	
<%--cmeType是评论类别 1:资讯   2：合作 3：话题  4：活动 5:动态--%>
var cmeType =1;
var fkId="${n.id}";
$(function(){
	initform();
});

//保存属性
function initform() {
	$.ajax({
		url:"news/selComments.do?fkId="+fkId+"&cmeType="+cmeType,
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			 
			$.each(data, function(i, n){
				///jQuery.messager.alert('提示:',n.id); 
				var s='';
				$.each(data, function(i, n){
					s += '<table class="table table-bordered "  style="margin-top: 25px;">';
					s += '<tr>';
					s += '			<th style="text-align:left;" width="25%">'+n.nickname+'('+n.job+'/'+n.industry+')'+'</th>';
					s += '			<th colspan="2"  width="50%"> '+'回复数：'+n.subcount+';点赞数：'+n.count+'</th>';
					s += '			<th  width="25%">  <button  class="btn clear"  onclick="showHf(this)">回复</button></th>';
					s += '</tr>';
					s += '<tr>';
					s += '		<td colspan="4">'+n.cmeDesc+'</td>';
					s += '</tr>';
					$.each(n.subCommentList, function(i, m){	
						s += '<tr>';
						s += '<th>'+m.trueName+' 回复'+n.nickname+' </th>';
						s += '<th colspan="3"></th>';
						s += '</tr>';
						s += '<tr>';
						s += '		<td colspan="4">'+m.cmeDesc+'</td>';
						s += '</tr>';
					});
					s += '<tr style="display:none;">';
					s += '			<td >手机号:';
					s += '			<input type="text" style="width:100px" class="easyui-numberbox" />';
					s += '		    </td>';
					s += '			<td colspan="3">';
					s += '			<textarea   rows="1" cols="100" style="width: 150px; height:50px" maxlength="500"></textarea>';
					s += '			<button style="margin-top:16px" class="btn btn-primary" parentId=\"'+n.id+'\" onclick="saveHf(this)">回复评论</button>';
					s += '		    </td>';
					s += '	</tr>';
					s += '	</table>';
				});
				
				
				$("#comments").html(s);
			});
			
			
		}
	});
}

//保存属性
function addComments() {
	var mobile = $("#mobile").val();
	if(mobile==""){
		alert_back("手机号不能为空",function(){
			$("#mobile").focus();
		});
		return false;
	}
	var cmeDesc = $("#cmeDesc").val();
	if(cmeDesc==""){
		alert_back("评论不能为空",function(){
			$("#cmeDesc").focus();
		});
		return false;
	}
	$.ajax({
		url:"news/addComment.do?fkId="+fkId+"&cmeType="+cmeType+"&mobile="+mobile+"&cmeDesc="+cmeDesc,
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			if(data){
				$("#mobile").val("");
				$("#cmeDesc").val("");
				initform();
			}else{
				jQuery.messager.alert('提示:',"不存在该手机用户");
			}
			///jQuery.messager.alert('提示:',eval(data));
			
		}
	});
}

function showHf(obj) {
	 $(obj).hide();
	 $(obj).parent().parent().parent().find("tr:hidden").show();
};
function saveHf(obj) {
	var mobile= $(obj).parent().parent().find("input").val();
	if(mobile==""){
		alert_back("手机号不能为空",function(){
			$("#mobile").focus();
		});
		return false;
	}
	var cmeDesc= $(obj).parent().find("textarea").val();
	if(cmeDesc==""){
		alert_back("评论不能为空",function(){
			$("#cmeDesc").focus();
		});
		return false;
	}
	var parentId= $(obj).attr("parentId");
	$.ajax({
		url:"news/addComment.do?fkId="+fkId+"&cmeType="+cmeType+"&cmeDesc="+cmeDesc+"&parentId="+parentId+"&mobile="+mobile,
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			if(data){
				initform();
			}else{
				jQuery.messager.alert('提示:',"不存在该手机用户");
			}
			///jQuery.messager.alert('提示:',eval(data));
			
		}
	});
};
//调整数量
function tzsl(){
	var params = {closed: false,cache: false,modal:true,width:420,height:250,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('调整数量','news/adjustNumPage.do?id='+fkId,"tzslfornews",params)
}
</script>
