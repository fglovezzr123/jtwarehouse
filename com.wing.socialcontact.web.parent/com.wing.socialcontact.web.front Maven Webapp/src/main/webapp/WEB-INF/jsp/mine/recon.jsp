<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="用户认证">
		<title>用户认证</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/recon.css" />
	</head>
	<body>
		<div class="Z-register-login" style="background: #f2f3f4;width: 100%;padding-bottom:1rem;">
			<div class="Z-item">
				<span>姓名</span>
				<input type="text" id="trueName" name="trueName" value="" class="name" placeholder="请输入你的姓名" maxlength="10" onblur="clearEmojiCharacter(this);"/>
			</div>
			<div class="Z-item" style="height:auto">
				<span>公司名称</span>
				<input style="height:auto;width:70%" type="text" id="comName" name="comName" value="" class="name" placeholder="请输入公司的名称" maxlength="50" onblur="clearEmojiCharacter(this);"/>
			</div>
			<div class="Z-item">
				<span>职位</span>
				<ul id="zw_treelist" style="display: none;">  
				    <c:forEach items="${zwList}" var="curr">
				    	<li id="${curr.id }" data-val="${curr.listValue}"><c:out value="${curr.listValue}"></c:out></li>
				    </c:forEach>
				</ul>  
			    <input type="hidden" id="zw" name="job" value="${tjyUser.job }"/>
			</div>
			<div class="Z-item">
				<span>注册资金(万元)</span>
				<input type="text" id="reconCapital" name="reconCapital" class="name" placeholder="请输入注册资金" maxlength="4" onkeyup="clearNoNum2(this);" onpaste="return false"/>
			</div>
			<div class="Z-item">
				<span>行业</span>
				<ul id="hy_treelist" style="display: none;">  
				    <c:forEach items="${hyList}" var="curr">
				    	<li id="${curr.id }" data-val="${curr.listValue}"><c:out value="${curr.listValue}"></c:out></li>
				    </c:forEach>
				</ul>  
			    <input type="hidden" id="hy" name="industry" value="${tjyUser.industry }"/>
			</div>
			<div class="Z-item">
				<span>地区</span>
				<input type="hidden" id="province" name="province" value="${tjyUser.province }"/>
				<input type="hidden" id="city" name="city" value="${tjyUser.city }"/>
				<input type="hidden" id="area" name="county" value="${tjyUser.county }"/>
				<script type="text/javascript">
					var l_index = layer.load(0,{type:3,shade:[0.8,'#393D49'],scrollbar:false});
				</script>
				<ul id="demo2" style="display: none;"><c:forEach items="${provinceList}" var="province"><li data-val="${province.disName}">${province.disName}<ul><c:forEach items="${cityList }" var="city"><c:if test="${city.superId eq province.id}"><li data-val="${city.disName}">${city.disName}<ul><c:forEach items="${areaList}" var="area"><c:if test="${area.superId eq city.id }"><li data-val="${area.disName}" provinceid="${province.id}" cityid="${city.id}" areaid="${area.id}" searchName="${province.disName} ${city.disName} ${area.disName}">${area.disName}</li></c:if></c:forEach></ul></li></c:if></c:forEach></ul></li></c:forEach></ul></div>
			<div class="Z-item" style="display: none;">
				<span>具体地址</span>
				<input type="text" id="address" name="address" class="name" value="${tjyUser.address }" placeholder="请输入你的具体地址" maxlength="100" onkeyup="clearEmojiCharacter(this);"/>
			</div>
			<div class="gsj">
				<p>公司简介</p>
				<textarea id="comProfile" name="comProfile" rows="" cols="" placeholder="请简述公司概况，不得超过200字" class="nerong" maxlength="200" onblur="clearEmojiCharacter(this);">${tjyUser.comProfile }</textarea>
				<span><label id="zs_label">${fn:length(tjyUser.comProfile)}</label>/200</span>
			</div>
			<div class="p-head">请上传营业执照、法人身份证正面、反面共三张照片</div>
			<div class="photo">
				<div class="photo-oper" id="photo_list1">
					<div class="addimagess active_A" id="file_button_1"></div>
				</div>
			</div>
			<div class="p-head" style="display: none;">银联认证</div>
			<div class="photo phlast" style="display: none;">
				<div class="photo-oper" id="photo_list2">
					<div class="addimagess active_A" id="file_button_2"></div>
				</div>
			</div>
		</div>
		<div style="height:.3rem;"></div>
		<div class="submit active_A" id="submit_button">提 交</div>
		<script type="text/javascript">
			$(function(){
				$("#zw_treelist").mobiscroll().treelist({
			        theme: mobile_type||'',
			        lang: 'zh',
			        display: 'bottom',
			        multiline:2,
			        placeholder: '请选择职位 ',
			        inputClass:'name',
			        //showLabel:true,
			        mode:'scroller',
			        row:3,
			        defaultValue: [Math.floor($('#zw_treelist li').length/2)],
			        onSelect:function(valueText, inst){
			        	var id=$('#zw_treelist').find("li[data-val='"+valueText+"']").attr("id");
			        	$("#zw").val(id);
			        },
			        onInit:function(inst){
			        }
			    });
				$("#hy_treelist").mobiscroll().treelist({
			        theme: mobile_type||'',
			        lang: 'zh',
			        display: 'bottom',
			        multiline:2,
			        placeholder: '请选择行业 ',
			        inputClass:'name',
			        //showLabel:true,
			        mode:'scroller',
			        row:3,
			        defaultValue: [Math.floor($('#hy_treelist li').length/2)],
			        onSelect:function(valueText, inst){
			        	var id=$('#hy_treelist').find("li[data-val='"+valueText+"']").attr("id");
			        	$("#hy").val(id);
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
			        placeholder: '请选择地区 ',
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
				
				var saveFlag = true;
				$("#submit_button").click(function(){
					if(!saveFlag){
						return;
					}
					var trueName=$("#trueName").val();
					if(isEmpty(trueName)){
						alert_back("姓名不能为空",function(){
							$("#trueName").focus();
						});
						return;
					}
					var comName=$("#comName").val();
					if(isEmpty(comName)){
						alert_back("公司名称不能为空",function(){
							$("#comName").focus();
						});
						return;
					}
					var zw=$("#zw").val();
					if(isEmpty(zw)){
						alert_back("职位不能为空",function(){
							//$("#zw").focus();
						});
						return;
					}
					var reconCapital=$("#reconCapital").val();
					if(isEmpty(reconCapital)){
						alert_back("注册资金不能为空",function(){
							//$("#zw").focus();
						});
						return;
					}
					if(reconCapital*1 < 1){
						alert_back("注册资金不能小于1",function(){
							//$("#zw").focus();
						});
						return;
					}
					if(reconCapital*1 > 9999){
						alert_back("注册资金不能大于9999",function(){
							//$("#zw").focus();
						});
						return;
					}
					var hy=$("#hy").val();
					if(isEmpty(hy)){
						alert_back("行业不能为空",function(){
							//$("#hy").focus();
						});
						return;
					}
					var province=$("#province").val();
					var city=$("#city").val();
					var area=$("#area").val();
					if(isEmpty(area)){
						alert_back("地区不能为空",function(){
							//$("#hy").focus();
						});
						return;
					}
					var address=$("#address").val();
					//if(isEmpty(address)){
					//	alert_back("详细地址不能为空",function(){
					//		$("#address").focus();
					//	});
					//	return;
					//}
					var comProfile=$("#comProfile").val();
					if(isEmpty(comProfile)){
						alert_back("公司简介不能为空",function(){
							$("#comProfile").focus();
						});
						return;
					}
					//组装证件照片
					var imgSize=$("#photo_list1").find("div[class='imagess']").length;
					if(imgSize < 3){
						alert_back("营业执照、法人身份证件上传不全",function(){
							//$("#comProfile").focus();
						});
						return;
					}
					var zjImgerJson="";
					var num=0;
					$("#photo_list1").find("div[class='imagess']").find("img").each(function(){
						//最多存储3张
						if(num >= 3){
							return;
						}
						if(num > 0){
							zjImgerJson+=";";
						}
						var imgUrl=$(this).attr("srcpath");
						var type=1;
						//zjImgerJson+="{";
						//zjImgerJson+="\"id\":\"\",";
						//zjImgerJson+="\"imgUrl\":\""+imgUrl+"\",";
						//zjImgerJson+="\"type\":\""+type+"\"";
						//zjImgerJson+="}";
						zjImgerJson+="0"+","+imgUrl+","+type;
						num++;
					});
					
					//var imgSize2=$("#photo_list2").find("div[class='imagess']").length;
					//if(imgSize2 < 1){
					//	alert_back("请上传银联认证图片",function(){
					//		//$("#comProfile").focus();
					//	});
					//	return;
					//}
					num = 0;
					$("#photo_list2").find("div[class='imagess']").find("img").each(function(){
						//只存储一张
						if(num > 0){
							return;
						}
						var imgUrl=$(this).attr("srcpath");
						var type=2;
						zjImgerJson+=";";
						//zjImgerJson+="{";
						//zjImgerJson+="\"id\":\"\",";
						//zjImgerJson+="\"imgUrl\":\""+imgUrl+"\",";
						//zjImgerJson+="\"type\":\""+type+"\"";
						//zjImgerJson+="}";
						zjImgerJson+="0"+","+imgUrl+","+type;
						num++;
					});
					saveFlag = false;
					zdy_ajax({
						url: '${path}/m/my/add/reconSave.do',
						type: 'post',
						dataType: 'json',
						data:{
							trueName:trueName,
							comName:comName,
							job:zw,
							reconCapital:reconCapital,
							industry:hy,
							province:province,
							city:city,
							county:area,
							address:address,
							comProfile:comProfile,
							zjImgerJson:zjImgerJson
						},
						success: function(output){
							saveFlag = true;
							alert_back("认证资料已提交",function(){
								var last_url="${path}/m/my/my_center.do";
								top.location.href=last_url;
							});
						}
					});
				});
				
				$("#comProfile").keyup(function(){
					var l=$(this).val().length;
					$("#zs_label").text(l);
				});
				
				$("div[id^='file_button_']").click(function(){
					var type=$(this).attr("id").replace("file_button_","");
					 zdy_chooseImg(function(data,res){
			    	 	if(res.code == 0){
			    	 		var imgHtml = "<img  srcpath='"+data.pic_path+"' src='"+data.img_url+"' class='up_pic_img' style='width:100%; height:100%;'/>";
							var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
			    	 		if(type == 1){
								var imgSize=$("#photo_list1").find("div[class='imagess']").length;
								if(imgSize < 3){
				    	 			$(str).insertBefore("#file_button_1");
								}
								if(imgSize >= 2){
									$("#file_button_1").hide();
								}
			    	 		}else{
								var imgSize=$("#photo_list2").find("div[class='imagess']").length;
								if(imgSize == 0){
				    	 			$(str).insertBefore("#file_button_2");
								}
								if(imgSize >= 0){
									$("#file_button_2").hide();
								}
			    	 		}
			    	 	}
					 },"usercenter");
				});
			});
			
			function delPic(data){
				var id=$(data).parent().find("img").attr("id");
				if(isEmpty(id)){
					$(data).parent().parent().find("div[id^='file_button_']").show();
					$(data).parent().remove();
				}else{
					confirm("是否确认删除？",function(t){
						if(t == 1){
							zdy_ajax({
								url: '${path}/m/my/reconPhotoDelete.do',
								type: 'post',
								dataType: 'json',
								data:{
									id:id
								},
								success: function(output){
									$(data).parent().parent().find("div[id^='file_button_']").show();
									$(data).parent().remove();
								}
							});
						}
					});
				}
			}
		</script>
	</body>
</html>