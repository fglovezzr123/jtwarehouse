<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="钱包明细">
		<title>
			 <c:choose>
				<c:when test="${type eq 1 }">余额明细</c:when>
				<c:when test="${type eq 2 }">J币明细</c:when>
				<c:when test="${type eq 3 }">互助宝明细</c:when>
				<c:otherwise>其他</c:otherwise>
			</c:choose>
		</title>
		<style type="text/css">
			.mx{
				width: 100%;
				padding: 0 .30rem;
				box-sizing: border-box;
				background: #fff;
				font-size: .24rem;
				margin-bottom: .1rem;
			}
			.items{
				width: 100%;
				height: .76rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
			.items_bz{
			   width: 100%;
				display: flex;
				justify-content: space-between;
				padding:.22rem 0;
			}
			.items_bz span:nth-last-of-type(1){
			   max-width:80%;
			} 
		</style>
	</head>
	<body style="background: #f2f3f4;">
		<div id="result">
			<c:forEach items="${logList }" var="log">
				<div class="mx">
					<div class="items">
						<span>金额/数量</span>
						<span>
							<c:choose>
								<c:when test="${log.pdType eq 1 }">+</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
							${log.amount }
						</span>
					</div>
					<div class="items" style="display: none;">
						<span>商户名称</span>
						<span>天九集团</span>
					</div>
					<c:choose>
						<c:when test="${log.pdType eq 1 }">
							<div class="items">
								<span>交付方式</span>
								<c:choose>
									<c:when test="${log.payType eq 1}">
										<span>微信支付</span>
									</c:when>
									<c:otherwise>
										<span>线下支付</span>
									</c:otherwise>						
								</c:choose>
							</div>
							<div class="items">
								<span>交易状态</span>
								<span>
									<c:choose>
										<c:when test="${log.payStatus eq 0 }"><label style="color: gray;">失败</label></c:when>
										<c:when test="${log.payStatus eq 1 }"><label style="color: green;">成功</label></c:when>
										<c:when test="${log.payStatus eq 2 }"><label style="color: red;">失败</label></c:when>
									</c:choose>
								</span>
							</div>
						</c:when>
						<c:otherwise>
							<div class="items">
								<span>交付方式</span>
								<span>划扣</span>
							</div>
							<div class="items">
								<span>交易状态</span>
								<span><label style="color: green;">成功</label></span>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="items">
						<span>交易时间</span>
						<span><fmt:formatDate value="${log.createTime }" pattern="yyyy-MM-dd HH:mm"/></span>
					</div>
					<div class="items_bz">
						<span>备注说明</span>
						<span>${log.remark }</span>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="loadingbox" style="display: none;">
			<div class="page_loading" style="display:none;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
		<script type="text/javascript">
			var pageNum=1;
			var pageSize=10;
			var loadFlag=true;
			var isEnd=false;
			$(function(){
				load_log();
			});
			var load_log=function(){
				loadFlag=false;
				$(".page_loading").show().parent().show();
				zdy_ajax({
					url: "${path}/m/my/wallet_log_ajax_page.do",
					async:true,
					data:{
						pageNum:pageNum,
						pageSize:pageSize,
						type:'${type}'
					},
				    showLoading:false,
					success: function(dataobj,res){
						var str="";
						for(var i=0;i<dataobj.length;i++){
							var obj=dataobj[i];
							str+='<div class="mx">';
							str+='<div class="items">';
							str+='<span>金额/数量</span>';
							var pdType='-';
							if(obj.pdType == 1){
								pdType='+';
							}
							str+='<span>'+pdType+obj.amount+'</span>';
							str+='</div>';
							var sourceName=obj.sourceName;
							if(!isEmpty(sourceName)){
								str+='<div class="items">';
								str+='<span>商户名称</span>';
								str+='<span>'+sourceName+'</span>';
								str+='</div>';
							}
							//================
							if(obj.pdType == 1){
								str+='<div class="items">';
								str+='<span>交付方式</span>';
								str+='<span>钱包</span>';
								str+='</div>';
								str+='<div class="items">';
								str+='<span>交易状态</span>';
								var payStatus='';
								if(obj.payStatus == 0){
									payStatus='<label style="color: gray;">失败</label>';
								}else if(obj.payStatus == 1){
									payStatus='<label style="color: green;">成功</label>';
								}else if(obj.payStatus == 2){
									payStatus='<label style="color: red;">失败</label>';
								}else if(obj.payStatus == 3){
									payStatus='<label style="color: orange;">处理中</label>';
								}else{
									payStatus='<label style="color: green;">成功</label>';
								}
								str+='<span>'+payStatus+'</span>';
								str+='</div>';
							}else{
								str+='<div class="items">';
								str+='<span>交付方式</span>';
								str+='<span>划扣</span>';
								str+='</div>';
								str+='<div class="items">';
								str+='<span>交易状态</span>';
								var payStatus='<label style="color: green;">成功</label>';
								//提现需要特殊处理
								if(obj.pdType == 2){
									if(obj.payStatus == 0){
										payStatus='<label style="color: gray;">失败</label>';
									}else if(obj.payStatus == 1){
										payStatus='<label style="color: green;">成功</label>';
									}else if(obj.payStatus == 2){
										payStatus='<label style="color: red;">失败</label>';
									}else if(obj.payStatus == 3){
										payStatus='<label style="color: orange;">处理中</label>';
									}else{
										payStatus='<label style="color: green;">成功</label>';
									}
								}
								str+='<span>'+payStatus+'</span>';
								str+='</div>';
							}
							str+='<div class="items">';
							str+='<span>交易时间</span>';
							str+='<span>'+date_format(obj.createTime,"yyyy-MM-dd HH:mm")+'</span>';
							str+='</div>';
							str+='<div class="items_bz">';
							str+='<span>备注说明</span>';
							str+='<span>'+obj.remark+'</span>';
							str+='</div>';
							str+='</div>';
						}
						$("#result").append(str);
						$(".page_loading").hide().parent().hide();
						if(dataobj.length < pageSize){
							isEnd=true;
							$(".page_nodata").show().parent().show();
						}else{
							bind_event();
						}
						loadFlag=true;
					}
				});
			}
			
			var bind_event=function(){
				if(pageNum == 1){
					$(window).scroll(function(){
					    if($(window).scrollTop() >= $(document).height() - $(window).height()){
					        //alert("滚动到底部啦！");
					        if(loadFlag){
						        if(!isEnd){
						        	pageNum++;
						        	load_log(); 
						        }
					        }
					    }
					});
				}
			}
		</script>
	</body>
</html>