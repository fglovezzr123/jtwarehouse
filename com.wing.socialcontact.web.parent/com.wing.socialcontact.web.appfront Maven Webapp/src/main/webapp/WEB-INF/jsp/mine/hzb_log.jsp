<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="钱包明细">
		<title>互助宝明细</title>
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
		</style>
	</head>
	<body style="background: #f2f3f4;">
		<div id="result">
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
					url: "${path}/m/my/hzb_log_ajax_page.do",
					async:true,
					data:{
						pageNum:pageNum,
						pageSize:pageSize
					},
				    showLoading:false,
					success: function(dataobj,res){
						var str="";
						for(var i=0;i<dataobj.length;i++){
							var obj=dataobj[i];
							str+='<div class="mx">';
							str+='<div class="items">';
							str+='<span>金额</span>';
							var pdType='-';
							if(obj.pdType == 1){
								pdType='+';
							}
							str+='<span>'+pdType+number_format(obj.managerMoney,0)+'</span>';
							str+='</div>';
							str+='<div class="items">';
							str+='<span>类型</span>';
							var type="";
							if(obj.type == 4){
								type = "增加互助宝余额";
							}else if(obj.type == 5){
								type = "扣除互助宝余额";
							}else if(obj.type == 7){
								type = "充值";
							}else{
								type = "消费";
							}
							str+='<span>'+type+'</span>';
							str+='</div>';
							str+='<div class="items">';
							str+='<span>交易时间</span>';
							str+='<span>'+date_format(obj.managerTime,"yyyy-MM-dd HH:mm")+'</span>';
							str+='</div>';
							str+='<div class="items">';
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