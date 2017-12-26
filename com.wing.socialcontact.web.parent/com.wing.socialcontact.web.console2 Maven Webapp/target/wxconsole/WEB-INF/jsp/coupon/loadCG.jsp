<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
				
		<div id="coupongenerate_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'coupongenerate_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">优惠券单号：</label>
						<input	type="text" name="batchNum" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">优惠券名称：</label>
						<input	type="text" name="couponName" class="span2"/>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"    id="stime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'etime\')}'})"   style="width:120px;"  />  
						至
					<input type="text" name="endTimef"     id="etime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'stime\')}'})"   style="width:120px;" />
					</span>
				</div>
				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
				
			</form>
		</div>
		<table id="coupongenerate_datagrid" toolbar="coupongenerate_toolbar"></table>

<script type="text/javascript">	
var _fkIdg = "${fkId}";
	$(function() {
		loadMaincg();			
	});
	
	function loadMaincg(){
		$('#coupongenerate_datagrid').datagrid(
			{
				url : "coupon/queryCG.do?fkId="+_fkIdg, 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "batchNum",
								title : "优惠券单号",
								width : 150,
								align : "center",
						},
						 {
								field : "cgName",
								title : "优惠券名称",
								width : 150,
								align : "center",
						},
						{
							field : "createTime",
							title : "生成时间",
							width : 150,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "yxq",
							title : "优惠券有效期",
							width : 180,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "couponNum",
							title : "优惠券数量",
							width : 100,
							align : "center"
						},{
							field : "receiveNum",
							title : "领取数量",
							width : 100,
							align : "center"
						}
						,{
							field : "remainNum",
							title : "剩余数量 ",
							width : 100,
							align : "center"
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								var str = "";
								str += "<a href = 'javaScript:void(0)' onclick = showviewcl('"+rowData.id+"')>查看</a>&nbsp;&nbsp;"
								+"<a href = 'javaScript:void(0)' onclick = deleteCG('"+rowData.id+"')>删除</a>";
								return  str;
							}
						}
						
				] ],
				
			});
	}
	
	function showviewcl(fid){
		var params = {closed: false,cache: false,modal:true,width:1150,height:600,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('优惠券领取记录','coupon/loadCL.do?id='+fid,"couponlogindex",params);
	}
	//删除
	function deleteCG(fid){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"id":fid};
					var dg = $('#coupongenerate_datagrid');
					selectedTodo_doPost("coupon/delCG.do",pm,dg,'');
	          }
	     });
	}
</script>














