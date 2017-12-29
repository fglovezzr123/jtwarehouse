<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">投资兴趣：</label>
						<select id="className" name="className" >
						<option value="">全部</option>
						<option value="定增投资">定增投资</option>
						<option value="上市孵化">上市孵化</option>
						<option value="上市并购">上市并购</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">投资状态：</label>
						<select id="status"  name ="status">
							<option value="0">全部</option>
							<option value="1">待联系</option>
							<option value="2">待考虑</option>
							<option value="3">已投资</option>
							<option value="4">已拒绝</option>
						</select>	
					</span>
					<span>  
						<label style="width: 100px;">创建时间：</label>
						<input type="text"  id="stime"  name="stime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})" style="width: 150px;"/>至
						<input type="text"  id="etime"  name="etime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})" style="width: 150px;"/>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
						<%-- <shiro:hasPermission name="investmentIntention:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="investmentClass/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="400"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission> --%>
						</span> 
		
					<span style="float:right">
						<a id="exportBtn7"  class="easyui-linkbutton">导出报表</a>
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
			<form id="exportForm7" > 
				<input type="hidden" id="className1" name="className1" />
				<input type="hidden" id="status1" name="status1" />
				<input type="hidden" id="stime1" name="stime1" />
				<input type="hidden" id="etime1" name="etime1" />
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>

				

<script type="text/javascript">	
	$(function() {
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				url : "investmentIntention/query.do", 
				columns : [ [
				             
			             {		field : "className",
								title : "投资兴趣",
								width : $(this).width() * 0.18,
								align : "center",
						},
						{
							field : "position",
							title : "投资额度（元）",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "userName",
							title : "联系人",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "phone",
							title : "联系电话",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "status",
							title : "投资状态",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {

									switch(value){
									case 1 :  return   "待联系";
									case 2 :  return   "待考虑";
									case 3 :  return   "已投资";
									case 4 :  return   "已拒绝";
									}
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : $(this).width() * 0.15,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = update('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function update(fid){
		MUI.openDialog('编辑投资意向','investmentIntention/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:400});
	}
	
	
	$(function() {
		$("#exportBtn7").click(function(){
			var  className1=$("#className").val();
			var  status1=$("#status").val();
			var  stime1=$("#stime").val();
			var  etime1=$("#etime").val();
			$("#className1").val(className1);
			$("#status1").val(status1);
			$("#stime1").val(stime1);
			$("#etime1").val(etime1);
			$("#exportForm7").attr("action","investmentIntention/export.do");
			$("#exportForm7").attr("target","exportFram");
			$("#exportForm7").submit();
		});	
	});
</script>














