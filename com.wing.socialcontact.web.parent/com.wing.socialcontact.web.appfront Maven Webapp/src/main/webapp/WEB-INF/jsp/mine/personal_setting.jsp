<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

<head>
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"
	name="viewport">

<title id="title">个人设置</title>
<link rel="stylesheet" href="${path}/resource/css/personal-set.css" />

</head>

<body>
	<div class="content active_A" id="personalIcon">
		<div class="content-item">
			<div class="con-l">头像</div>
			<div class="con-r" style="background-image:url('')">
				<img style="width:1.12rem;height:1.12rem;border-radius: 50%;"
					src="${user.head_portrait}" />
			</div>

		</div>

	</div>

	<div class="content">
			
		<div class="content-item bottom-border active_A">
			<div class="con-l">用户姓名</div>
			<div class="con-r" id="nickname"  style="background-image:url('')">${user.nickname}</div>
		</div>
		<div class="content-item bottom-border active_A " >
			<div class="con-l">性别</div>
			<div >
				<ul id="demo" style="display: none;">
				    <li id="1" data-val="男">男</li>
				    <li id="2" data-val="女">女</li>
				</ul>  
			</div>

		</div>

		<div class="content-item bottom-border active_A clear">
			<div class="con-l">出生日期</div>
			<div>
				<input type="text" data-role="datebox" class="con-r" id="birthday" name="birthday" placeholder="请选择您的生日" value="<fmt:formatDate value='${user.birthday}' pattern='yyyy-MM-dd'/>"/>
			</div>
		</div>

		<div class="content-item bottom-border active_A clear">
			<div class="con-l" style="width:10%">地区</div>
			<script type="text/javascript">
				var l_index = layer.load(0, {
					type : 3,
					shade : [ 0.8, '#393D49' ],
					scrollbar : false
				});
			</script>
			<%--<div class="con-r">${user.province}&nbsp;&nbsp;${user.city}&nbsp;&nbsp;${user.county} --%>
			<div><ul id="demo2" style="display: none;"><c:forEach items="${provinceList}" var="province"><li data-val="${province.disName}">${province.disName}<ul><c:forEach items="${cityList }" var="city"><c:if test="${city.superId eq province.id}"><li data-val="${city.disName}">${city.disName}<ul><c:forEach items="${areaList}" var="area"><c:if test="${area.superId eq city.id }"><li data-val="${area.disName}" provinceid="${province.id}" cityid="${city.id}" areaid="${area.id}" searchName="${province.disName} ${city.disName} ${area.disName}">${area.disName}</li></c:if></c:forEach></ul></li></c:if></c:forEach></ul></li></c:forEach></ul></div>
   		</div>

		<%--  <div class="content-item active_A clear" onclick="personal_edit(2)">
			<div class="con-l">电话</div>
			<div class="con-r" id="mobile">${user.mobile}</div>
		</div>--%>
	</div>
	
	<c:choose>
		<c:when test="${user.is_realname eq 1 }">
			<div class="content">
				
				<div class="content-item bottom-border active_A">
					<div class="con-l">职位</div>
					<div class="con-r" style="background-image:url('')">${user.jobName}</div>
				</div>
				<div class="content-item bottom-border active_A" style="height:auto;">
					<div class="con-l">公司名称</div>
					<div class="con-r" style="background-image:url('');height:auto">${user.com_name}</div>
					<br style="clear:both"/>
				</div>
				<div class="content-item bottom-border active_A">
					<div class="con-l">行业</div>
					<div class="con-r" style="background-image:url('')">${user.industryName}</div>
				</div>
				<div class="content-item bottom-border active_A">
					<div class="con-l">认证电话</div>
					<div class="con-r" style="background-image:url('')">${user.recon_mobile}</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${user.recon_status eq 2 }">
				<div class="content">
				<div class="content-item bottom-border active_A">
					<div class="con-l">职位</div>
					<div class="con-r" style="background-image:url('')">${user.jobName}</div>
				</div>
				<div class="content-item bottom-border active_A" style="height:auto">
					<div class="con-l">公司名称</div>
					<div class="con-r" style="background-image:url('');width:80%">${user.com_name}</div>
				</div>
				<div class="content-item bottom-border active_A" >
					<div class="con-l">行业</div>
					<div class="con-r" style="background-image:url('')">${user.industryName}</div>
				</div>
				<div class="content-item bottom-border active_A">
					<div class="con-l">认证电话</div>
					<div class="con-r" style="background-image:url('')">${user.recon_mobile}</div>
				</div>
			</div>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>


	<div class="content">
		<div style="height:auto;background: url(${path}/resource/img/icons/arrow.png) no-repeat center right;background-position-x:94.5%; background-size: 0.36rem 0.36rem;"  class="content-item bottom-border active_A"
			onclick="personal_edit(3)">
			<div class="con-l">个人简介</div>
			<div style="height:auto;text-align:left;background:none;line-height:0.4rem;margin-top:0.2rem;margin-bottom:0.2rem;" class="con-r" id="user_profile">${user.user_profile}</div>
            <br style="clear:both"/> 
		</div>

		<div class="content-item bottom-border clear">
			<div class="con-l">我的爱好</div>
			<!-- <div class="con-r active_A" id="con-r">
					<img src="${path}/resource/img/icons/edition.png" /> 编辑
				</div> -->

		</div>

		<div id="lovedoing" style="height:auto" class="content-item bottom-border clear">
		    <div id="addFavorite0">
			<c:forEach var="uf" items="${userFavs}">
				<div class="lovedoing">${uf.fav_name}</div>
			</c:forEach>
			   
			</div>
			<div class="lovedoing active_A" style="margin-bottom:0px" id="addFavorite" onclick="personal_fav()" >+</div>
             <br style="clear:both"/>  
		</div>
<!--  
		<div class="content-item bottom-border clear active_A"
			onclick="personal_edit(4)"
			style="padding-top:0.3rem;padding-bottom:0.3rem;">
			<div class="con-l active_A" style="float:left">个人签名</div>
			<div class="con-r" id="user_signature">${user.user_signature}</div>

		</div>-->

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var currYear = new Date().getFullYear();
		    $('#birthday').mobiscroll().date({
			    theme: mobile_type||'',
				preset: 'date',
			    lang: 'zh',
			    display: 'bottom',
			 	dateFormat: 'yy-mm-dd', // 日期格式
			 	//showLabel:true,
			 	mode:'scroller',
			    maxDate: new Date(),
			    minDate: new Date(new Date().setFullYear(currYear - 120)),
			    onSelect : function(valueText, inst) {
			    	var birth=$('#birthday').val();
			    	zdy_ajax({
						url : "${path}/m/my/add/addusers.do",
						showLoading : false,
						data : {
							birthday : birth
						},
						success : function(data, res) {
							if (res.code == 0) {
								//alert(res.msg);
							} else {
								alert(res.msg);
							}
						},
						error : function(e) {
							//alert(e);
						}
					});
			    }
			});
		});

		var personal_edit = function(flag) {
			if(navigator.onLine){ 
				self.location.href = "${path}/m/my/personal_edit.do?flag=" + flag;
			} 
			else { 
				offlinedeal();
			}
		}
		var personal_edit2 = function(flag) {
			layer.open({
				type : 2,
				//skin: 'layui-layer-lan',
				title: false,
  				closeBtn: 0, //不显示关闭按钮
				fix : true,
				shadeClose : true,
				maxmin : false,
				area : [ '100%', '100%' ],
				content : '${path}/m/my/personal_edit2.do?flag=' + flag,
				shift : 2,
				scrollbar : false,
				success : function(layero, index) {
					show_zzc(1);

				},
				end : function() {
					hide_zzc(1);
				},
				cancel : function(cancel) {
				}
			});
		}
		
		var personal_fav = function() {
			if(navigator.onLine){ 
				self.location.href = "${path}/m/my/personal_fav.do";
			} 
			else { 
				offlinedeal();
			}
			
		}
		

		function offlinedeal(){
			layer.open({
				type : 1,
				//skin: 'layui-layer-lan',
				title: false,
					closeBtn: 0, //不显示关闭按钮
				fix : true,
				shadeClose : true,
				maxmin : false,
				area : [ '100%', '100%' ],
				content : '<div class="wrapper1"><div class="right-signal1">网络异常，请检查手机网络</div><div class="active_A name-btn1" onclick="reloadjsp()">刷新</div></div>',
				shift : 2,
				scrollbar : false,
				success : function(layero, index) {
				},
				end : function() {
				},
				cancel : function(cancel) {
				}
			});
		}

		var show_zzc = function(t) {
			$("body").bind("touchmove", function(event) {
				event.preventDefault();
			});
			if (t && t == 1) {
				scrollTop = $(document).scrollTop();
				$(document).scrollTop(0);
				$(window).bind("scroll", function() {
					$(document).scrollTop(0);
				});
			}

		}

		var hide_zzc = function(t) {
			$("body").unbind("touchmove");
			if (t && t == 1) {
				$(window).unbind("scroll");
				$(document).scrollTop(scrollTop);
			}

		}

		var reload = function() {
			self.location.href = self.location.href;
		}

		$('#demo2').mobiscroll().treelist(
				{
					theme : mobile_type || '',
					lang : 'zh',
					display : 'bottom',
					fixedWidth : [ 160, 160, 160 ],
					multiline : 2,
					placeholder : '请选择地区 ...',
					inputClass : 'con-r aa',
					labels : [ '省', '市', '区' ],
					//showLabel:true,
					mode : 'scroller',
					row : 3,
			        defaultValue:['北京市','北京市','东城区'],
					onSelect : function(valueText, inst) {
						var obj = $('#demo2').find(
								"li[searchName='" + valueText + "']");
						console.log(valueText);
						console.log(obj);
						console.log(obj.attr("provinceid"));
						var provinceid = obj.attr("provinceid");
						var cityid = obj.attr("cityid");
						var areaid = obj.attr("areaid");

						zdy_ajax({
							url : "${path}/m/my/add/addusers.do",
							showLoading : false,
							data : {
								province : provinceid,
								city : cityid,
								county : areaid,
							},
							success : function(data, res) {
								if (res.code == 0) {
									//alert(res.msg);
								} else {
									alert(res.msg);
								}
							},
							error : function(e) {
								//alert(e);
							}
						});
					},
					onInit : function(inst) {
		        		$("#demo2_dummy").val('${user.province}${user.city}${user.county}');

					}
				});
		
		$("#demo").mobiscroll().treelist({
	        theme: mobile_type||'',
	        lang: 'zh',
	        display: 'bottom',
	        multiline:2,
	        placeholder: '请选择性别 ...',
	        inputClass:'con-r',
	        //showLabel:true,
	        mode:'scroller',
	        row:3,
	        defaultValue: [Math.floor($('#demo li').length/2)],
	        onSelect:function(valueText, inst){
	        	var sex=$('#demo').find("li[data-val='"+valueText+"']").attr("id");
	        	zdy_ajax({
					url : "${path}/m/my/add/addusers.do",
					showLoading : false,
					data : {
						sex : sex
					},
					success : function(data, res) {
						if (res.code == 0) {
							//alert(res.msg);
						} else {
							alert(res.msg);
						}
					},
					error : function(e) {
						//alert(e);
					}
				});
	        	
	        },
	        onInit:function(inst){
	        	var sex=${user.sex};
	        	if(sex==1){
	        		$("#demo_dummy").val('男');
	        	}else if(sex==2){
	        		$("#demo_dummy").val('女');
	        	}
	        }
	    });
		layer.close(l_index);
	</script>
</body>

</html>