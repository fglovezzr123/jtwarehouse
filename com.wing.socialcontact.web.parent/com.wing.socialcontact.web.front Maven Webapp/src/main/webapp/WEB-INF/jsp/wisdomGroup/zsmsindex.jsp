<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">知识秘书</title>
<link rel="stylesheet" href="${path}/resource/css/boss-articles.css?v=${sversion}" />

</head>

<body>

	<div class="wrapper">
		<jsp:include page="../commons/include_banner.jsp">
			<jsp:param name="bannerid" value="40a665bf0226467ca693a7f32dac7475" />
		</jsp:include>

		<div class="itemss" id="sysRec">
			<a class="active_A" href="${path}/wx/wisdomGroup/all-classes88.do">
				<span>更多</span>
				<span></span>
			</a>
			<br class="clear" />

		</div>


	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			recommend();
			loadlist();
		})

		function recommend() {
			zdy_ajax({
				url : _path + "/library/m/recommendclass.do",
				showLoading : false,
				success : function(data, res) {
					var str = '';
					for (var i = 0; i < 4; i++) {
						if (data[i].name.length > 4) {
							data[i].name = data[i].name.substring(0, 3) + "..."
						}
						str += '<a class="active_A" id="'
								+ data[i].id
								+ '" href="${path}/library/m/all-classes.do?id='
								+ data[i].id + '&level=2">' + '<span>' + data[i].name
								+ '</span>' + '<span>' + data[i].count
								+ '</span>' + '</a>';
					}
					jQuery('#sysRec').prepend(str);
				},
				error : function(data) {

				}

			});
		}

		//加载数据

		//查询前三个知识秘书一级分类

		function loadlist() {
			zdy_ajax({
				url : _path + "/library/m/onelevelclass.do",
				data : {
					position : '2'
				},
				showLoading : false,
				success : function(data, abc) {
					var a = 1;
					var b = 1;
					var str = "";
					$.each(data,function(i, n) {
										if (a < 3) {
											str += '<div class="headline"><a href="javascript:void(0)">'
													+ n.name
													+ '</a>'
													+ '<a href="${path}/library/m/all-classes.do?id='+n.id+'&level=1">更多 <span>></span></a>'
													+ '<br class="clear" /></div><div class="content" id="list'+
	  							 	  i+'"><br class="clear"/></div>';
										}
										if (a == 3) {
											str += '<div class="headline" style="border-bottom:1px solid #e7e7e7 "><a href="javascript:void(0)">'
													+ n.name
													+ '</a>'
													+ '<a href="${path}/library/m/all-classes.do?id='+n.id+'&level=1">更多 <span>></span></a>'
													+ '<br class="clear" /></div><div class="collections" id="list'+
	  							 	  i+'"><br class="clear"/></div>';
										}
										a++;
									});
					$("#sysRec").after(str);
					$.each(data,function(i, n) {
										if (b < 4) {
											if (i == 0) {
												uploadarticle1(i, n.id);
											} else if (i == 1) {
												uploadarticle2(i, n.id);
											} else if (i == 2) {
												uploadarticle3(i, n.id);
											}
										}
										b++;
									});
				},
				error : function(data) {
				}
			});
		}

		//最下方	 
		function uploadarticle3(c, id) {
			zdy_ajax({
				url : _path + "/library/m/selbyonelevelid.do",
				data : {
					'classid' : id
				},
				showLoading : false,
				success : function(data, abc) {
					var str = '';
					var tagArr;
					var articleTag;
					var atricledata = 2;
					if (data.length > 2) {
					} else {
						atricledata = data.length;
					}
					;
					console.log(data)
					for (var i = 0; i < atricledata; i++) {
						articleTag = '';
						if (data[i].onename) {
							tagArr = data[i].tag.split(',');
							for (var b = 0; b < tagArr.length; b++) {
								articleTag += '<span class="active_A">'
										+ data[i].onename +'-'+data[i].twoname+ '</span>';
							}
						}
						str += '<ul id="'+data[i].id+'"  class="active_A">'
								+ '<li>' + data[i].title + '</li>' + '<li>'
								+ articleTag
								+ '<span class="active_A howmuchs">'
								+ data[i].readtimes + '人阅读</span>'
								+ '<br class="clear" />' + '</li>' + '</ul>';
					}
					$("#list" + c).append(str);
					$("#list" + c + " ul")
							.bind(
									'click',
									function() {
										document.location.href = "${path}/library/m/librarydetail.do?type=1&id="
												+ jQuery(this).attr('id');
									});
				},
				error : function(data) {

				}

			});

		}

		function uploadarticle2(b, id) {
			zdy_ajax({
				url : _path + "/library/m/selbyonelevelid.do",
				data : {
					'classid' : id
				},
				showLoading : false,
				success : function(data, abc) {
					var str = '';
					//限制显示最多4个
					var dataLen;
					if (data.length > 2) {
						dataLen = 2;
					} else {
						dataLen = data.length;
					}
					console.log(data);
					for (var i = 0; i < dataLen; i++) {
						str += '<a href="${path}/library/m/librarydetail.do?type=1&id='
								+ data[i].id
								+ '">'
								+ '<img src="'+_oss_url+data[i].imgpath+'" />'
								+ '<p>' + data[i].title + '</p>' + '</a>';
					}

					$("#list" + b).prepend(str);
				},
				error : function(data) {

				}

			});
		}

		function uploadarticle1(b, id) {
			zdy_ajax({
				url : _path + "/library/m/selbyonelevelid.do",
				data : {
					'classid' : id
				},
				showLoading : false,
				success : function(data, abc) {
					var str = '';
					var dataLen;

					//限制显示最多4个
					if (data.length > 4) {
						dataLen = 4;
					} else {
						dataLen = data.length;
					}
					console.log(data)
					for (var i = 0; i < dataLen; i++) {
						str += '<a href="${path}/library/m/librarydetail.do?type=1&id='
								+ data[i].id
								+ '">'
								+ '<img src="'+_oss_url+data[i].imgpath+'" />'
								+ '<p>' + data[i].title + '</p>' + '</a>';
					}

					jQuery("#list" + b).prepend(str);
				},
				error : function(data) {

				}

			});
		}
	</script>

</body>

</html>