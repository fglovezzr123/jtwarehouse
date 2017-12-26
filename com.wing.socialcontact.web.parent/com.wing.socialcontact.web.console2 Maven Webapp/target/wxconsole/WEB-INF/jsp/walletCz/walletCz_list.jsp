<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 80px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 80px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					<span>
						<label style="width: 80px;">充值类型：</label>
						<select name="type"  style="width: 120px;">
						<option value="">全部</option>
						<option value="1">RMB余额</option>
						<option value="2">J币充值</option>
						<!-- 
						<option value="3">互助宝充值</option>
						 -->
					    </select>
					</span>
					<span>
						<label style="width: 80px;">支付状态：</label>
						<select name="status"  style="width: 120px;">
						<option value="">全部</option>
						<option value="0">待支付</option>
						<option value="1">成功</option>
						<option value="2">失败</option>
					    </select>
					</span>
				</div>

				<div class="search-toolbar">
					
				    <div  id="tijiaodiv1" style="display: inline;">
				    	<c:choose>
							<c:when test="${not empty listValue }">
								<font color="red">当前J币兑换比例为${listValue.listDesc}</font>
							</c:when>
							<c:otherwise>
								<font color="red">当前尚未设置J币兑换比例，请尽快设置</font>
							</c:otherwise>		    	
				    	</c:choose>
						<!-- <button class="btn btn-primary btn-small" type="button" id="tzjb_button">调整J币比例</button> -->
						<a class="easyui-linkbutton" href="walletCz/tzblPage.do?rel=${param.rel}" title="调整J币比例" target="dialog" width="600" height="300" rel="${param.rel}_tzjb"><span>调整J币比例</span>	</a>
					</div> 
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>	

<script type="text/javascript">	
	$(function() {
		loadMain();
		$("#tzjb_button").click(function(){
			var url="walletCz/tzblPage.do?walletCz/tzblPage.do?rel=${param.rel}";
			MUI.openDialog("调整J币比例",url,"${param.rel}_tzbl",{width:600,height:300});
		});
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "walletCz/query.do", 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "nickname",
							title : "用户姓名",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "type",
							title : "充值类型",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(value == 1){
									return 'RMB余额';
								}else if(value == 2){
									return 'J币充值';
								}else{
									return '互助宝充值';
								}
							}
						},
						{
							field : "amount",
							title : "充值数量",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "create_time",
							title : "充值时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
								.format("yyyy-MM-dd HH:mm:ss ");
							}
						},
						{
							field : "pay_status",
							title : "充值状态",
							width:$(this).width() * 0.1,
							align : "center",
							formatter: function(value,row,index){
								if (value=='0') {
									value="待支付";
								}else if (value=='1') {
									value="支付成功";
								}else if (value=='2') {
									value="支付失败";
								}else{
									value="";
								}
								return value;
							}
						}
						
				] ],
				
			});
	}
	
	function showviewb(fid){
		
	}
</script>














