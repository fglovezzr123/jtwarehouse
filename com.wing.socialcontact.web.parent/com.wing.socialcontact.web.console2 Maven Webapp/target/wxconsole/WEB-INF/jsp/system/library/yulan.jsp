<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
--%>
<style>

	.vimg {
		width:100%;
		height: 3.5rem;
		object-fit: fill;
	}
	.vimg video{
		object-fit: fill;
	}
	.share1{
		width: 100%;
		height: 0.9rem;
		position: fixed;
		bottom: 0;
		left: 0;
		background: white;
		font-size: 0.3rem;
		display:flex;
		align-items: center;
	}
	.share1 div{
		height:100%;
		flex:1;
		text-align:center;
		line-height: 0.9rem;
	}
	.bar{
		width: 100%;
		height: 3.5rem;
		position: absolute;
		left: 0;
		top: 0;
		background:rgba(0,0,0,0.5);
		z-index: 100;
	}
	.bar img{
		width: .6rem;
		height: .6rem;
		position: absolute;
		left: 50%;
		top: 50%;
		margin-top: -0.3rem;
		margin-left: -0.3rem;

	}
</style>
<div style="width: 100%;margin: 0 auto;" >
	<div id="vediosdiv">
		<c:if test="${dto.imgpath ne '' && dto.imgpath ne null }">
			<img class="imgfg" id="imgdiv" src="${ossurl}${dto.imgpath}">
		</c:if>

		<c:if test="${dto.audioFile ne '' && dto.audioFile ne null }">
			<audio  controls="controls" id="audio">
				<source src="${ossurl}${dto.audioFile}">
			</audio>
		</c:if>
	</div>

	<div class="div_titlebold" ><c:out value="${dto.title}" /></div>
	<div class="div_sendUser"><span>${dto.onename}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${dto.readtimes}人阅读&nbsp;&nbsp;&nbsp;&nbsp;</span><span>${fns:fmt(dto.createTime,'yyyy-MM-dd HH:mm:ss')}</span></div>
	<div class="tubiao">
		<div class="tb dz"><i class="iconfont"></i><span style="background: url();padding-left:0.1rem" id="lick_"> </span></div>
	<div class="div_sendUser">
		<a style="" href="${ossurl}${dto.path}">${dto.filename}</a>
	</div>

	<div class="divider"></div>
	<div style="margin:10px;" >
		${dto.content}
	</div>
</div>

