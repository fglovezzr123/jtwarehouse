<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="发布活动">
		<title>发布活动</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/recon.css?v=${sversion}" />
		<script src="${path}/resource/js/upload.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
		<style>
		      .switchBtnOff{
		          background:url(${path}/resource/img/icons/OFF.png) no-repeat center right;
		          background-size:0.95rem 0.54rem;
		          background-position-x:96%
		      }
		      .switchBtnOn{
		          background:url(${path}/resource/img/icons/oN.png) no-repeat center right;
		          background-size:0.95rem 0.54rem;
		          background-position-x:96%
		      }
		       #activityMehto label{
		        line-height:0.8rem;
		      }
		      .action .t-img #activity-Description{
		         width:100%;
		         height:5rem;
		         overflow:scroll;
		         cursor:text;
		         color:#a9a9a9;
		         position:relative;
		         outline:0;
		        
		      }
		       .action .t-img #activity-Description img{
		          width:60%;
		          display:block;
		          margin-left:20%;
 		        }
 		        /*   #activity-Description:empty::before{  
			            color:lightgrey;  
			            content:attr(placeholder);  
                }   */
				   /*  /*焦点时内容为空
				    #activity-Description:focus::before{
				        content:none;
				    } */
			    input[type=radio]{
			       vertical-align:middle;
			       width:0.5rem;
			    }
			    .Z-item span {
					font-size: .24rem;
					color: #3B3B3B;
					line-height: .78rem;
				}
			    .Z-item {
					    padding: 0rem;
				}
				.Z-item input {
				    font-size: .24rem;
				    color: #8B8B8B;
				    text-align: end;
				    background: none;
				    overflow: scroll;
				    padding: 0.3rem;
				}
				    
		</style>
	</head>
    
	<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
				<div class="fm">
					<%-- <img src="${path}/resource/img/images/addphoto.png" class="active_A" /> --%>
					<!-- <div class="photo">
						<div class="photo-oper" id="photo_list2">
							<div class="addimagess active_A" onclick="doSelectPic('uploadPicFrame2','pic2');" id="file_button_2"></div>
							<iframe id="uploadPicFrame2" src="" style="display:none;"></iframe>
						</div>
					</div> -->
					<div class="photo">
						<div class="photo-oper" id="photo_list2">
							<div class="addimagess active_A" onclick="choosePic1()" id="file_button_2"></div>
							<iframe id="uploadPicFrame2" src="" style="display:none;"></iframe>
						</div>
					</div>
						<span style="color:#3b3b3b">活动封面图(必填)</span>
				</div>
				<div class="action">
					<input type="text" name="" id="activityName" value="" placeholder="请输入活动名称(必填)" />
					<div class="t-img">
					<!-- <textarea name="" rows="" id="activity-Description" cols="" placeholder="请丰富活动描述，让更 多人加入..."></textarea> -->
					<div id="activity-Description" class="textDivEdible" contenteditable ><span >请丰富活动描述，让更多人加入...</span></div> 
						<!-- <div class="photo">
							<div class="photo-oper" id="photo_list1">
								<div class="addimagess active_A" onclick="doSelectPic('uploadPicFrame1','pic1');" id="file_button_1"></div>
								<iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
							</div>
						</div> -->
						<div class="photo">
							<div class="photo-oper" id="photo_list1">
								<div class="addimagess active_A" onclick="choosePic2()" id="file_button_1"></div>
								<iframe id="uploadPicFrame1" src="" style="display:none;"></iframe>
							</div>
						</div>
					</div>
				</div>
				<div class="leibie">
					
					<div class="items">
						<p>活动方式</p>
						<div class="it-r" id="activityMehto">
							<label for="#online"><span>线上</span><input value="2" checked="checked" id="online" name="activityMehto" type="radio"/></label>
							<label for="#offline"><span>线下</span><input value="1" id="offLine" name="activityMehto" type="radio"/></label>
							<i class="iconfont"></i>
						</div>
					</div>
					<div class="items">
						<p>开始时间</p>
						<div class="it-r">
							<input type="text" name="" id="activityBeginDate" value="" placeholder="必填" />
							<i class="iconfont"></i>
						</div>
					</div>
					<div class="items">
						<p>结束时间</p>
						<div class="it-r">
							<input type="text" name="" id="stopActivityDate" value="" placeholder="必填" />
							<i class="iconfont"></i>
						</div>
					</div>
					<div class="Z-item">
						<span>活动分类、标签(必填)</span>
						<input type="hidden" id="classId" name="classId" />
						<input type="hidden" id="tag" name="tag" />
						<ul id="demo3" style="display: none;">
							<li data-val="以玩会友">以玩会友
								<ul>
									<c:forEach items="${tagList }" var="tag">
										<c:if test="${tag.classId eq '1'}">
											<li data-val="${tag.name}" classid="1" tagid="${tag.id}"  searchName1="以玩会友 ${tag.name}">${tag.name}
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
							<li data-val="以书会友">以书会友
								<ul>
									<c:forEach items="${tagList }" var="tag">
										<c:if test="${tag.classId eq '2'}">
											<li data-val="${tag.name}" classid="2" tagid="${tag.id}"  searchName1="以书会友 ${tag.name}">${tag.name}
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>
			
					<div class="Z-item">
						<span>地区(必填)</span>
						<input type="hidden" id="province" name="province" />
						<input type="hidden" id="city" name="city" />
						<input type="hidden" id="area" name="county" />
						<script type="text/javascript">
							var l_index = layer.load(0,{type:3,shade:[0.8,'#393D49'],scrollbar:false});
						</script>
						<ul id="demo2" style="display: none;">
							<c:forEach items="${provinceList}" var="province">
								<li data-val="${province.disName}">${province.disName}
									<ul>
										<c:forEach items="${cityList }" var="city">
										<c:if test="${city.superId eq province.id}">
										<li data-val="${city.disName}">${city.disName}
											<ul>
											<c:forEach items="${areaList}" var="area">
												<c:if test="${area.superId eq city.id }">
												<li data-val="${area.disName}" provinceid="${province.id}" cityid="${city.id}" areaid="${area.id}" searchName="${province.disName} ${city.disName} ${area.disName}">${area.disName}</li>
												</c:if>
											</c:forEach>
											</ul>
										</li>
										</c:if>
										</c:forEach>
									</ul>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="items">
						<p>详细地址</p>
						<div class="it-r">
							<input type="text" name="" id="addressDetail" value="" placeholder="详细地址" />
							<i class="iconfont"></i>
						</div>
					</div>
					
					<div class="items">
						<p>报名截止</p>
						<div class="it-r">
							<input type="text" name="" id="enrollUntil" value="" placeholder="必填" />
							<i class="iconfont"></i>
						</div>
					</div>
					<div class="items" id="activityzf"  style="display:none;">
						<p>活动收费</p>
						<div class="it-r">
							<input type="text" name="" id="admissionfee" readonly="readonly" value="0" placeholder="0" />
							<span>元</span>
							<span></span>
						
						</div>
					</div>
					
					<!-- <div yesOrno="0" class="items shiftOffOn switchBtnOff">
						<p>是否参与评论</p>
					
					</div> -->
				</div>
				<div class="cx">
					<div yesOrno="0" id="ableComOrNO" class="no shiftOffOn switchBtnOff">
						<p>是否允许评论</p>
					<%-- 	<img src="${path}/resource/img/icons/OFF.png" /> --%>
					</div>
					<div yesOrno="0" id="userCanSeeOrNo" class="no shiftOffOn switchBtnOff">
						<p>仅好友可见</p>
					
					</div>
				</div>
			</div>
			<div style="height:1rem"></div>
			<div id="submitActivity" class="submit active_A">发布</div>
		</div>
</body>
</html>		
<script type="text/javascript">

			$(function(){
				console.log(localStorage.activityurl)
				//initUploadPicFrame("uploadPicFrame1","pic1","upload1");
				//initUploadPicFrame("uploadPicFrame2","pic2","upload2");
				$('#activity-Description').on('focus',function(){
					$(this).find('span').hide()
				})
				$('#activity-Description').on('blur',function(){
					console.log($(this).text().length)
					if($(this).text().length>17){
					}else{
						$(this).find('span').show()
					}
					
				})
				
				 if(zfflag){
					   $("#activityzf").show();
				   }else{
					   $("#activityzf").hide();
				   }
				
				$('#demo3').mobiscroll().treelist({
			        theme: mobile_type||'',
			        lang: 'zh',
			        display: 'bottom',
			        fixedWidth: [240,240],
			        multiline:2,
			        placeholder: '请选择分类、标签 ...',
			        inputClass:'name',
			        labels: ['分类', '标签'],
			        //showLabel:true,
			        mode:'scroller',
			        row:3,
			        onSelect:function(valueText, inst){
			        	
			        	var obj=$('#demo3').find("li[searchName1='"+valueText+"']");
			        	var classid=obj.attr("classid");
			        	var tagid=obj.attr("tagid");
			        	$("#classId").val(classid);
			        	$("#tag").val(tagid);
			        },
			        onInit:function(inst){
			        }
			    });
				
				$('#demo2').mobiscroll().treelist({
			        theme: mobile_type||'',
			        lang: 'zh',
			        display: 'bottom',
			        fixedWidth: [160,160,160],
			        multiline:2,
			        placeholder: '请选择地区 ...',
			        inputClass:'name',
			        labels: ['省', '市', '区'],
			        //showLabel:true,
			        mode:'scroller',
			        row:3,
			        onSelect:function(valueText, inst){
			        	var obj=$('#demo2').find("li[searchName='"+valueText+"']");
			        	var provinceid=obj.attr("provinceid");
			        	var cityid=obj.attr("cityid");
			        	var areaid=obj.attr("areaid");
			        	$("#province").val(provinceid);
			        	$("#city").val(cityid);
			        	$("#area").val(areaid);
			        },
			        onInit:function(inst){
			        }
			    });
				
				layer.close(l_index);
				
				
			});
			function doSelectPic(frameId,pic) {
				var imgSize = 0;
				if("uploadPicFrame1" == frameId){
					imgSize=$("#photo_list1").find("div[class='imagess']").length;
					if(imgSize > 3){
						layer.msg("只能上传三张图片");
						return;
					}
				}else{
					imgSize=$("#photo_list2").find("div[class='imagess']").length;
					if(imgSize > 0){
						alert("只能上传一张图片");
						return;
					}
				}
				$("#"+pic, $("#"+frameId)[0].contentWindow.document).click();
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
	        	
	         $('.shiftOffOn').click(function(){
	        	   if($(this).find('input') && $('#admissionfee').is(':focus')  ){
	        		   return
	        	   }else{
	        		   if($(this).hasClass('switchBtnOff')){
		        		   $(this).removeClass('switchBtnOff').addClass('switchBtnOn');
		        		   $(this).attr('yesorno','1');
		        	   }else if($(this).hasClass('shiftOffOn')){
		        		   $(this).removeClass('switchBtnOn').addClass('switchBtnOff');
		        		   $(this).attr('yesorno','0');
		        	   } 
	        	   }
	        	  
	         });
	         console.log($('input:radio[name="activityMehto"]:checked').val());
	         //提交活动
	     $('#submitActivity').click(function(){
	    	 
	    	   var startTime=$('#activityBeginDate').val();
	    	   var endTime=$('#stopActivityDate').val();
	    	   var signupTime=$('#enrollUntil').val();
	    	   var nowdate = getNowFormatDate();
	    	   console.log(nowdate);
	    	   console.log(signupTime);
	    	   if(signupTime<=nowdate){
	    		   alert("报名截止时间不能小于当前时间",function(){
					});
					return;
	    	   }
	    	   var titles=$('#activityName').val();
	    	   if(isEmpty($.trim(titles) )){
	    		   alert("标题不能为空",function(){
					});
					return;
	    	   }
	    	   
	    	   if(titles.length>20){
	    		   alert("标题长度不能超过20字",function(){
					});
					return;
	    	   }
	    	   var imagePath=$('.up_pic_img').attr('srcpath');
	    	   if(isEmpty(imagePath)){
	    		   alert("请上传封面图",function(){
					});
					return;
	    	   }
	    	   var province=$("#province").val();
				var city=$("#city").val();
				var county=$("#area").val();
	    	   if(isEmpty(province)){
	    		   alert("请选择活动地点",function(){
					});
					return;
	    	   }
	    	   if(isEmpty(city)){
	    		   alert("请选择活动地点",function(){
					});
					return;
	    	   }
	    	   if(isEmpty(county)){
	    		   alert("请选择活动地点",function(){
					});
					return;
	    	   }
	    	   
	    	   var pattern=$('input:radio[name="activityMehto"]:checked').val();
	    	   if(isEmpty(pattern)){
	    		   alert("请选择活动方式",function(){
					});
					return;
	    	   }
	    	   var place=$('#addressDetail').val();
	    	   if(pattern==1){
		    	   if(isEmpty(place)){
		    		   alert("请填写详细地址",function(){
						});
						return;
		    	   }
	    	   }
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
	    	   var nowdate = getNowFormatDate();
	    	   if(signupTime<=nowdate){
	    		   alert("报名截止时间不能小于当前时间",function(){
					});
					return;
	    	   }
	    	   if(zfflag){
	    	   var ticketPrice=$('#admissionfee').val();
	    	   }else{
	    	   var ticketPrice=0;
	    	   }
	    	   if($.trim(ticketPrice)==null || $.trim(ticketPrice)==''){
	    		   alert("请输入门票价格",function(){
					});
					return;
	    		   //ticketPrice=0;
	    	   }
	    	   if (isNaN(ticketPrice)) { 
		    		alert_back("门票价格请输入数字",function(){
					});
	    		　　　　return ;
	    	   }
	    	   if (ticketPrice>=10000000) { 
		    		alert_back("门票价格最高为9999999.99元",function(){
					});
	    		　　　　return ;
	    	   }
	    	   var contents=$('#activity-Description').text().replace("请丰富活动描述，让更多人加入...","");
    		   console.log(contents);
    		   
	    	   if(isEmpty($.trim(contents))){
	    		   alert("请输入活动描述",function(){
					});
					return;
	    	   }
	    	   if(contents.length>1000){
	    		   alert("活动描述超出1000字",function(){
					});
					return;
	    	   }
	    	   var tag=$("#tag").val();
	    	   if(isEmpty(tag)){
	    		   alert("请选择分类、标签",function(){
					});
					return;
	    	   }
	    	   var classId=$("#classId").val();
	    	   if(isEmpty(classId)){
	    		   alert("请选择分类、标签",function(){
					});
					return;
	    	   }
	    	   var showUser=$('#userCanSeeOrNo').attr('yesorno');
	    	   var commentEnable=$('#ableComOrNO').attr('yesorno');
	    	  zdy_ajax({
					   url:_path+"/add/m/activity/activityAdd.do",
					   showLoading:false,
					   data:{
						   titles:titles,
						   contents:contents,
						   imagePath:imagePath,
						   province:province,
						   city:city,
						   place:place,
						   startTime:startTime,
						   endTime:endTime,
						   signupTime:signupTime,
						   showUser:showUser,
						   classId:classId,
						   tag:tag,
						   commentEnable:commentEnable,
						   pattern:pattern,
						   ticketPrice:ticketPrice,
						   county:county
						   
					   },
					   success:function(data,bc){
						   layer.msg("活动发布成功");
						   var last_url=localStorage.activityurl;
						   setTimeout(function(){
							    top.location.href=home_path+last_url;
						 	},500)
					   },
					   error:function(data){
					   }
					}); 
	         });
	         
	         
	         
	     function choosePic1(){
			    	 zdy_chooseImg(function(data,res){
			    	 	if(res.code == 0){
			    	 		//layer.msg("上传成功！", {icon: 6});
			    	 	//$("#img").attr("src", data.img_url);
			    	 		//$("#img").attr("id", data.pic_path);
			    	 		
			    	 		var imgHtml = "<img  srcpath='"+data.pic_path+"' src='"+data.img_url+"' class='up_pic_img' style='width:100%; height:100%;'/>";
							var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
			    	 		$(str).insertBefore("#file_button_2");
							var imgSize=$("#photo_list2").find("div[class='imagess']").length;
							if(imgSize == 1){
								$("#file_button_2").hide();
							}
			    	 	}
			    	 })
	    }
	     function choosePic2(){
			    	 zdy_chooseImg(function(data,res){
			    	 	if(res.code == 0){
			    	 		//layer.msg("上传成功！", {icon: 6});
			    	 	//$("#img").attr("src", data.img_url);
			    	 		//$("#img").attr("id", data.pic_path);
			    	 		
			    	 		var imgHtml = "<img  srcpath='"+data.pic_path+"' src='"+data.img_url+"' class='up_pic_img' style='width:100%; height:100%;'/>";
							var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
							$(str).insertBefore("#file_button_1");
							/* var imgSize=$("#photo_list1").find("div[class='imagess']").length; */
							var imgSize=$('#activity-Description').find('img').length+1;
							if(imgSize==1){
								
								$('#photo_list1 .imagess').remove();
								imgMixText(data.img_url,$('#activity-Description').find('img').length);
								
							}else if(imgSize==2){
							
								$('#photo_list1 .imagess').remove();
								imgMixText(data.img_url,$('#activity-Description').find('img').length);
								
							}else if(imgSize == 3){
							
								$('#photo_list1 .imagess').remove();
								imgMixText(data.img_url,$('#activity-Description').find('img').length);
							
								//$("#file_button_1").hide();
							}else if(imgSize > 3){
								
								layer.msg('只能上传三张图片');
								$('#photo_list1 .imagess').remove();
							}
			    	 	}
			    	 })
	    }
	         
	         
	   //上传完成后回调的方法
			function picUploadCallback(data,frameId,picId,formId){
				if (data.returnCode == "0"){
					var picUrl = data.picPath;
					if(picUrl.length > 0){
						var imgHtml = "<img  srcpath='"+data.picPath+"' src='"+data.img_url+"' class='up_pic_img' style='width:100%; height:100%;'/>";
						var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
						
						if("upload1" == formId){
							$(str).insertBefore("#file_button_1");
							/* var imgSize=$("#photo_list1").find("div[class='imagess']").length; */
							var imgSize=$('#activity-Description').find('img').length+1;
							if(imgSize==1){
								
								$('#photo_list1 .imagess').remove();
								imgMixText(data.img_url,$('#activity-Description').find('img').length);
								
							}else if(imgSize==2){
							
								$('#photo_list1 .imagess').remove();
								imgMixText(data.img_url,$('#activity-Description').find('img').length);
								
							}else if(imgSize == 3){
							
								$('#photo_list1 .imagess').remove();
								imgMixText(data.img_url,$('#activity-Description').find('img').length);
							
								//$("#file_button_1").hide();
							}else if(imgSize > 3){
								
								layer.msg('只能上传三张图片');
								$('#photo_list1 .imagess').remove();
							}
						}else{
							$(str).insertBefore("#file_button_2");
							var imgSize=$("#photo_list2").find("div[class='imagess']").length;
							if(imgSize == 1){
								$("#file_button_2").hide();
							}
						}
					}else{
						alert("上传失败,请稍后再试");
					}
				}else{
					if(data.msg != ""){
						alert(data.msg);
					}else{
						alert("上传失败,请稍后再试");
					}
				}
				initUploadPicFrame(frameId,picId,formId);
			}
			function delPic(data){
				$(data).parent().parent().find("div[id^='file_button_']").show();
				$(data).parent().remove();
			}
			//文本编辑器
	          function imgMixText(imgSRC,imgLength){
	        	       console.log(imgSRC);
	        		  $('#activity-Description').append( ' '+'<img id="'+'editorImg'+imgLength+'" src="'+imgSRC+'"/>'+' ');
	         }
			
	          function getNowFormatDate() {
	        	    var date = new Date();
	        	    var seperator1 = "/";
	        	    var seperator2 = ":";
	        	    var month = date.getMonth() + 1;
	        	    var strDate = date.getDate();
	        	    var houDate = date.getHours();
	        	    var minDate = date.getMinutes();
	        	    if (month >= 1 && month <= 9) {
	        	        month = "0" + month;
	        	    }
	        	    if (strDate >= 0 && strDate <= 9) {
	        	        strDate = "0" + strDate;
	        	    }
	        	    if (houDate >= 0 && houDate <= 9) {
	        	    	houDate = "0" + houDate;
	        	    }
	        	    if (minDate >= 0 && minDate <= 9) {
	        	    	minDate = "0" + minDate;
	        	    }
	        	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	        	            + " " + houDate + seperator2 + minDate;
	        	    return currentdate;
	          }
	          
	          
	          /* $(function(){
	    		   $('#activity-Description').on('keyup',function(){
	    		       var txtval = $('#activity-Description').val().length;
	    		      var str = parseInt(100-txtval);
	    		        if(str > 0 ){
	    		      }else{
	    		          $('#activity-Description').val($('#activity-Description').val().substring(0,1000)); //这里意思是当里面的文字小于等于0的时候，那么字数不能再增加，只能是600个字
	    		        }
	    		        //console.log($('#num_txt').html(str));
	    		    });
	    		}) */
		</script>
	

