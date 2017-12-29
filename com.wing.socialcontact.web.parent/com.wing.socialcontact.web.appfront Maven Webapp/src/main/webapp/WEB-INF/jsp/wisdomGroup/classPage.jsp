<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="合作洽谈">
		<title>全部行业</title>
		<style>
		.nav {
			width: 100%;
			padding: 0 0.3rem;
			/* height: 3.05rem; */
			display: flex;
			/* justify-content: space-between; */
			/* align-items: center; */
			background: #fff;
			flex-wrap: wrap;
			box-sizing:border-box;
			padding-bottom:.30rem;
           }

			.nav a {
				font-size: .24rem;
				display: flex;
				flex-direction: column;
				align-items: center;
				width: 25%;
				margin-top:.30rem;
			}
			.nav a span{
				/* overflow: hidden;
			   text-overflow: ellipsis;
			   white-space: nowrap; */
			   width: 100%;
			    text-align: center;
			   
			}
			.nav a img {
				width: .53rem;
				height: .56rem;
				margin-bottom: .13rem;
			}
		</style>
	</head>

	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
					<div class="nav" id="cListdiv">
					</div>
				</div>
			</div>
		
		</div>
		
	</body>
<script type="text/javascript">
$(document).ready(function() {
	zdy_ajax({
		url: "${path}/m/reward/selAllClassList.do",
	    showLoading:false,
		data:{},
		success: function(data,res){
			if(res.code == 0){
				var c = "";
				var oss = res.dataobj.ossurl;
				$.each(res.dataobj.classList, function(i, n){
					c += '<a href="#" class="cList-a active_A" onclick="openurl(\'${path}/m/reward/listPage.do?voType='+n.id+'\')"><img src="'+oss+n.imagePath+'"/><span>'+n.className+'</span></a>';
				});
				$("#cListdiv").append(c);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
	
});

function openurl(url){
	document.location.href=url;
}

</script>
</html>