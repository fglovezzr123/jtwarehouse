<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>延期申请</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
	</head>
	<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
				<div class="leibie">
					<div class="items">
						<p>开始时间</p>
						<div class="it-r">
							<input type="text" name="" id="activityBeginDate" value="" placeholder="必填" />
							<i class="iconfont">&#xe605;</i>
						</div>
					</div>
					<div class="items">
						<p>结束时间</p>
						<div class="it-r">
							<input type="text" name="" id="stopActivityDate" value="" placeholder="必填" />
							<i class="iconfont">&#xe605;</i>
						</div>
					</div>
					<div class="items">
						<p>报名截止</p>
						<div class="it-r">
							<input type="text" name="" id="enrollUntil" value="" placeholder="必填" />
							<i class="iconfont">&#xe605;</i>
						</div>
					</div>
				</div>
				<div class="msg">
					<input value="延期原因：(200字)" readonly="readonly"/><textarea rows="5" cols="1" id="description"  ></textarea>
				</div>
		</div>
			<div class="M-footer active_A" onclick="add_info('${id}');">
				提交
			</div>
	</body>
		<script type="text/javascript">
		
		$(function(){
	 		   $('#description').on('keyup',function(){
	 		       var txtval = $('#description').val().length;
	 		      var str = parseInt(200-txtval);
	 		      console.log(str);
	 		        if(str > 0 ){
	 		      }else{
	 		          $('#description').val($('#description').val().substring(0,200)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
	 		        }
	 		    });
	 		})
		
			var add_info=function(id){
				
				 var startTime=$('#activityBeginDate').val();
		    	   if(isEmpty(startTime)){
		    		   alert("请选择活动开始时间",function(){
						});
						return;
		    	   }
		    	   var endTime=$('#stopActivityDate').val();
		    	   if(isEmpty(endTime)){
		    		   alert("请选择活动结束时间",function(){
						});
						return;
		    	   }
		    	   if(startTime>=endTime){
		    		   alert("活动开始时间不能大于活动结束时间",function(){
						});
						return;
		    	   }
		    	   var signupTime=$('#enrollUntil').val();
		    	   if(isEmpty(signupTime)){
		    		   alert("请选择报名截止时间",function(){
						});
						return;
		    	   }
		    	   if(signupTime>=startTime){
		    		   alert("报名截止时间不能大于活动开始时间",function(){
						});
						return;
		    	   }
		    	   var description=$('#description').val();
		    	   if(isEmpty(description)){
		    		   alert("请填写延期原因",function(){
						});
						return;
		    	   }
				zdy_ajax({
					url: "${path}/m/activity/delay.do",
				    showLoading:false,
					data:{
						activityId:id,
						startTime:startTime,
						endTime:endTime,
						signupTime:signupTime,
						description:description
					},
					success: function(data,res){
						if(res.code == 0){
							if(parent){
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								//parent.reload();
								parent.layer.close(index);
							}
						}else{
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
							alert(output.msg);
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				}); 
			}
			
			
			//活动起始日期
            
	         $(function () {
	 			var currYear = (new Date()).getFullYear();	
	 			var opt={};
	 			opt.date = {preset : 'date'};
	 			//opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
	 			opt.datetime = {preset : 'datetime'};
	 			opt.time = {preset : 'time'};
	 			opt.default1 = {
	 				theme: 'android-ics', //皮肤样式
	 		        display: 'bottom', //显示方式 
	 		        mode: 'scroller', //日期选择模式
	 				lang:'zh',
	 				dataFormat:'yy-mm-dd hh:ii',
	 				dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字 
	 		        hourText: '时',minuteText: "分", 
	 		        startYear:currYear, //开始年份
	 		        minWidth:60,
	 		        endYear:currYear + 5 //结束年份
	 			};
	 		  	var optDateTime = $.extend(opt['datetime'], opt['default1']);
	 		  	var optTime = $.extend(opt['time'], opt['default1']);
	 		    $("#activityBeginDate").mobiscroll(optDateTime).datetime(optDateTime);
	 		
	         });
	        //活动结束时间
	          $(function () {
	 			var currYear = (new Date()).getFullYear();	
	 			var opt={};
	 			opt.date = {preset : 'date'};
	 			opt.datetime = {preset : 'datetime'};
	 			opt.time = {preset : 'time'};
	 			opt.default1 = {
	 				theme: 'android-ics', //皮肤样式
	 		        display: 'bottom', //显示方式 
	 		        mode: 'scroller', //日期选择模式
	 				lang:'zh',
	 				dataFormat:'yy-mm-dd hh:ii',
	 				
	 				dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字 
	 		        hourText: '时',minuteText: "分", 
	 		        startYear:currYear, //开始年份
	 		        minWidth:60,
	 		        endYear:currYear + 5 //结束年份
	 			};
	 		  	var optDateTime = $.extend(opt['datetime'], opt['default1']);
	 		  	var optTime = $.extend(opt['time'], opt['default1']);
	 		    $("#stopActivityDate").mobiscroll(optDateTime).datetime(optDateTime);
	         });
	         //报名截至
	          $(function () {
	 			var currYear = (new Date()).getFullYear();	
	 			var opt={};
	 			opt.date = {preset : 'date'};
	 			opt.datetime = {preset : 'datetime'};
	 			opt.time = {preset : 'time'};
	 			opt.default1 = {
	 				theme: 'android-ics', //皮肤样式
	 		        display: 'bottom', //显示方式 
	 		        mode: 'scroller', //日期选择模式
	 				lang:'zh',
	 				dataFormat:'yy-mm-dd hh:ii',
	 				
	 				dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字 
	 		        hourText: '时',minuteText: "分", 
	 		        startYear:currYear, //开始年份
	 		        minWidth:60,
	 		        endYear:currYear + 5 //结束年份
	 			};
	 		  	var optDateTime = $.extend(opt['datetime'], opt['default1']);
	 		  	var optTime = $.extend(opt['time'], opt['default1']);
	 		    $("#enrollUntil").mobiscroll(optDateTime).datetime(optDateTime);
	         });
			
		</script>
	</body>

</html>