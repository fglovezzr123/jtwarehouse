<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理--组织机构 -- 部门管理--查找带回(单选)
--%>

<div class="easyui-tabs" fit="true" border="false" >
	<div title="树状结构" 	style="padding:2px; ">
		 <ul id="libraryclass_lookup_tree" class="ztree"></ul>
	
	</div>
	<%-- <div   title="列表查询" 	style="padding:2px; ">
			<div id="${param.rel}_toolbar" class="search-div">
				<form  onsubmit="return datagridSearch(this,'${param.rel }_datagrid');"  >
					<div  class="search-content " >
						<span>
							<label>名称：</label>
							<input	type="text" name="name" />
						</span>
					
					
					</div>
				
					<div class="search-toolbar" >

						<span style="float:right">
							<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button" >清空</button>&nbsp;
						</span>
					
					</div>
				</form>
				
			</div>
			
			<table id="${param.rel }_datagrid"   toolbar="#${param.rel}_toolbar" ></table>
			
	
	</div> --%>
	
</div>

<script type="text/javascript">
<!--
	$(function(){
		$.ajax({
			url:"libraryclass/load/all.do",
			cache: false,
			dataType:"json",
			success:function(json){
				var setting = {
						view: {
							dblClickExpand: false,
							showIcon: false
						},
						data: {
							simpleData: {
								enable: true
							}
						}
				};
				var zNodes = new Array();
				
				$.each(json, function(i, d) {
					
					zNodes.push({id : d.id,name : d.content,
						//pId : d.parentId,open:true,click:"$.bringBack({id:'"+d.id+"',name:'"+d.content+"'})"});
						pId : d.parentId,open:true,click:"choosetag('"+d.id+"','"+d.content+"',"+d.level+")"});
					
				});
				
				$.fn.zTree.init($("#libraryclass_lookup_tree"), setting, zNodes);
		
			}
		});
		
	});
	
	
	function choosetag(id,content,level){
		if(level==1){
			Msg.alert("提示","请选择二级标签","error");
			return;
		}else{
			$.bringBack({id:id,name:content})
		}
	}
//-->
</script>
