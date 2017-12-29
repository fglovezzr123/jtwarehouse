<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="couponlog_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'couponlog_datagrid');">
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户名/电话：</label>
						<input	type="text" name="keyword" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">优惠券编码：</label>
						<input	type="text" name="couponCode" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">优惠券名称：</label>
						<input	type="text" name="couponName" class="span2"/>
					</span>
					<span><label style="width: 100px;">使用状态：</label>
					<select name="useStatus"  style="width: 120px;">
			    	<option  value="" >全部</option>
						<option  value="1">已使用</option>
						<option  value="2">未使用</option>
					</select>
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float:right">
						<c:if test="isff!=0"><button class="btn btn-primary btn-small" type="button" onclick="ffyhq('${fkId}')">发放优惠券</button>&nbsp;</c:if>
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
				
			</form>
		</div>
		<table id="couponlog_datagrid" toolbar="couponlog_toolbar"></table>

<script type="text/javascript">	
var _fkId = "${fkId}";
	$(function() {
		loadMaincg();			
	});
	
	function loadMaincg(){
		$('#couponlog_datagrid').datagrid(
			{
				url : "coupon/queryCL.do?fkId="+_fkId, 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "cgName",
								title : "优惠券名称",
								width : 150,
								align : "center",
						},
						 {
								field : "couponCode",
								title : "优惠券编码",
								width : 150,
								align : "center",
						},
						{
							field : "userName",
							title : "用户姓名",
							width : 150,
							align : "center",
						},
					 	{
							field : "mobile",
							title : "电话",
							width : 150,
							align : "center",
						},
						{
							field : "receiveTime",
							title : "领取时间",
							width : 150,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "useTime",
							title : "使用时间",
							width : 150,
							align : "center",
							formatter : function(value, row, index) {
								if(value==null){
									return "";
								}else{
									return new Date(value)
									.format("yyyy-MM-dd HH:mm");
								}
							}
						},
						{
							field : "useStatus",
							title : "状态",
							width : 100,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.useStatus==1){
									str = "已使用";
								}else if(rowData.useStatus==2){
									str = "未使用";
								}
								return str;
							}
						}
						
				] ],
				
			});
	}
	//删除
	function deleteCG(fid){
		    var pm = {"id":fid};
			var dg = $('#couponlog_datagrid');
			selectedTodo_doPost("coupon/grantCouponPage.do",pm,dg,'');
	}
	function ffyhq(fid){
		var isff = '${isff}';
		if(isff=='0'){
			Msg.alert("提示","该批优惠券发放完毕！","warning",null);
			return;
		}
		var isgq =  '${isgq}';
		if(isgq=='0'){
			Msg.alert("提示","该批优惠券已过期，不能发放优惠券！","warning",null);
			return;
		}
		MUI.openDialog('发放优惠券','coupon/grantCouponPage.do?id='+fid+'&rel=couponlog','couponlog_add',{width:450,height:300});
	}
</script>














