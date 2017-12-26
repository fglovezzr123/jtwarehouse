<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">优惠券名称：</label>
						<input	type="text" name="couponName" class="span2"/>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef" id="startTimef"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endTimef\')}'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"  id="endTimef"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startTimef\')}'})"  style="width:120px;" />
					</span>
					<span><label style="width: 100px;">优惠券币种：</label>
					<select name="couponCoinType"  style="width: 120px;">
			    	<option  value="" >全部</option>
						<option  value="1">J币</option>
						<option  value="2">RMB</option>
					</select>
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float: left;"> 
					 <shiro:hasPermission name="coupon:add">
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="coupon/addPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="650" height="500" rel="${param.rel}_add"><span>新增</span>
							</a>
						</shiro:hasPermission>  	
						<%-- <shiro:hasPermission name="coupon:update">
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="coupon/updatePage.do?id={id}&rel=${param.rel}"
								target="dialog" width="700" height="500"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="coupon:delete">
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="coupon/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
				       </shiro:hasPermission> --%>
						</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
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
				url : "coupon/query.do", 
				fitColumns: false,
				columns : [ [
						 {
								field : "couponName",
								title : "优惠券名称",
								width : 150,
								align : "center",
						},
						{
							field : "couponType",
							title : "优惠券类型",
							width : 100,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.couponType==1){
									str = "代金券";
								}else if(rowData.couponType==2){
									str = "满减券";
								}else if(rowData.couponType==3){
									str = "兑换券";
								}
								return str;
							}
						},
						{
							field : "couponCoinType",
							title : "优惠券币种",
							width : 100,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.couponCoinType==1){
									str = "J币";
								}else if(rowData.couponCoinType==2){
									str = "RMB";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "优惠券创建时间",
							width : 150,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},{
							field : "couponAmount",
							title : "优惠券金额",
							width : 100,
							align : "center"
						},{
							field : "orderMinBuy",
							title : "订单最低消费",
							width : 100,
							align : "center"
						},
						{
							field : "useRange",
							title : "适用范围",
							width : 100,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.useRange==1){
									str = "全平台";
								}else if(rowData.useRange==2){
									str = "会议";
								}else if(rowData.useRange==3){
									str = "活动";
								}else if(rowData.useRange==4){
									str = "商城";
								}
								return str;
							}
						},{
							field : "useStore",
							title : "指定店铺 ",
							width : 180,
							align : "center"
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 150,
							formatter: function(rowIndex, rowData){
								var str = "";
								str += "<a href = 'javaScript:void(0)'  onclick = showviewc('"+rowData.id+"')>查看</a>&nbsp;&nbsp;"+
							     "<a href = 'javaScript:void(0)' onclick = deleteCou('"+rowData.id+"')>删除</a>&nbsp;&nbsp;<a href='javaScript:void(0)'  onclick=plsc('"+rowData.id+"')>批量生成</a>";
								return  str;
							}
						}
						
				] ],
				
			});
	}
	
	function showviewc(fid){
		var params = {closed: false,cache: false,modal:true,width:1150,height:600,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('优惠券生成记录','coupon/loadCG.do?id='+fid,"coupongenerateindex",params);
	}
	//批量生成
	function plsc(fid){
		MUI.openDialog('批量生成','coupon/batchGeneratePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_plsc',{width:450,height:300});
	}
	//删除
	function deleteCou(fid){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"id":fid};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("coupon/del.do",pm,dg,'');
	          }
	     });
	}
</script>














