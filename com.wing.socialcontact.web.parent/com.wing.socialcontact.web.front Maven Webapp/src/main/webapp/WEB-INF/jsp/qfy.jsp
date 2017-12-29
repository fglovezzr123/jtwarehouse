<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=3, minimum-scale=1, width=device-width, height=device-height"/>
    <title>企服云-首页</title>
    <link rel="stylesheet" href="${path}/resource/qfy/css/common.css?v=${sversion}"/>
    <script type="text/javascript" src="${path}/resource/qfy/js/jquery-1.8.3.min.js?v=${sversion}"></script>
    <script type="text/javascript" src="${path}/resource/qfy/js/rem.js?v=${sversion}"></script>
</head>
<body>
<div class="wrap">
	<header>企服云</header>
	<section>
		<div class="lsy_sskbox"><input id="field1" type="text" placeholder="搜索需要的服务" /></div>
		<div class="lsy_banner lineheight0"><img src="${path}/resource/qfy/images/lsy_banner.jpg" /></div>
		<ul class="lsy_nrnav_list">
			<li class="lsy_nav_jflm"><a>金服联盟</a></li>
			<li class="lsy_nav_yxlm"><a>营销联盟</a></li>
			<li class="lsy_nav_zxlm"><a>资讯联盟</a></li>
		</ul>
		<div class="lsy_ggpic lineheight0"><img src="${path}/resource/qfy/images/lsy_gg_pic.jpg" /></div>
		<div class="lsy_hotfw">
			<h2>热门服务</h2>
			<ul class="clear">
				<li><a>投融资方案</a></li>
				<li><a>股权方案</a></li>
				<li><a>风险管控</a></li>
				<li><a>保险</a></li>
				<li><a>品牌营销</a></li>
				<li><a>SNS 营销</a></li>
				<li><a>大数据营销</a></li>
				<li><a>销售管理</a></li>
				<li><a>战略规划</a></li>
				<li><a>管理培训</a></li>
				<li><a>信息化资讯</a></li>
				<li><a>运营资讯</a></li>
			</ul>
		</div>
		<div class="lsy_jxqf">
			<h2>精选企服</h2>
			<dl class="clear">
				<dt><img src="${path}/resource/qfy/images/lsy_qf_pic1.jpg" /></dt>
				<dd>
					<p class="clear"><b>正略集团</b><span>已服务<i>79</i>家企客户</span></p>
					<p class="clear">
						<a>人力资源</a>
						<a>培训</a>
						<a>绩效薪酬</a>
						<a>集团管控</a>
					</p>
				</dd>
			</dl>
			<dl class="clear">
				<dt><img src="${path}/resource/qfy/images/lsy_qf_pic2.jpg" /></dt>
				<dd>
					<p class="clear"><b>汗哲管理资讯集团</b><span>已服务<i>79</i>家企客户</span></p>
					<p>
						<a>战略规划</a>
						<a>软件与云服务</a>
						<a>组织与岗位</a>
					</p>
				</dd>
			</dl>
		</div>
	</section>
	<footer>
		<ul class="clear">
			<li class="lsy_nav_sy onclick"><a>首页</a></li>
			<li class="lsy_nav_fl"><a>分类</a></li>
			<li class="lsy_nav_xx"><a>消息</a></li>
			<li class="lsy_nav_wd"><a>我的</a></li>
		</ul>
	</footer>
</div>
</body>
</html>
