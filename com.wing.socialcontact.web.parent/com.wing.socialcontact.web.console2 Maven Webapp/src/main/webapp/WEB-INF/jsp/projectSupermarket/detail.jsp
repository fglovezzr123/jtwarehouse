<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
	.ke-icon-tel {
		background-image: url(resource/js/editor/themes/default/timg.jpg);
		width: 16px;
		height: 16px;
	}
	.distant1 {
		color: green;
		font-weight: bold;
		text-shadow: 0 0 1px currentColor,-1px -1px 1px #030,0 -1px 1px #030,1px -1px 1px #030,1px 0 1px #030,1px 1px 1px #030,0 1px 1px #030,-1px 1px 1px #030,-1px 0 1px #030;
	}
</style>

<div style="width:100%;">
	<form  id="dataForm">

		<input type="hidden" name="id" value="${obj.id}"/>
		<input type="hidden" name="createTime"  value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
		<input type="hidden" name="createUserId"  value="${obj.createUserId}">
		<input type="hidden" name="updateTime" value="${fns:fmt(obj.updateTime,'yyyy-MM-dd HH:mm:ss')}">
		<input type="hidden" name="updateUserId"  value="${obj.updateUserId}">
		<input type="hidden" name="images" id="images">
		<table class="table table-bordered" style="margin-top:0px;width:99%;">

			<tr>
				<th style="width:15%;">项目名称：</th>
				<td colspan=3>
					${obj.name}
				</td>
			</tr>
			<tr>
				<th>项目介绍：</th>
				<td colspan=3>
					${obj.introduce}
				</td>
			</tr>

			<tr name="allplace">
				<th style="width: 120px">合作模式：</th>
				<td >
					${obj.cooperativeModeName}
				</td>
			</tr>
			<tr>
				<th>模式说明：</th>
				<td colspan=3>
					${obj.illustration}
				</td>
			</tr>
			<tr>
				<th>项目亮点：</th>
				<td colspan=3>
					${obj.highlights}
				</td>
			</tr>

			<tr name="allplace">
				<th style="width: 120px">省：</th>
				<td >
					${obj.provinceName}
				</td>
			</tr>

			<tr name="allplace">
				<th style="width: 120px">市：</th>
				<td>
					${obj.cityName}
				</td>
			</tr>
			<tr name="allplace">
				<th style="width: 120px">区：</th>
				<td>
					${obj.countyName}
				</td>
			</tr>

			<tr name="allplace">
				<th style="width: 120px">所在行业：</th>
				<td>
					${obj.industryName}
				</td>
			</tr>

			<tr>
				<th>团队介绍：</th>
				<td colspan=3>
					${obj.teamPresentation}
				</td>
			</tr>
			<tr>
				<th>产品介绍：</th>
				<td colspan=3>
					<div class="hyjb_box">
						<div class="hyjb_tj">
							<c:if test="${not empty obj}">
								<c:forEach items="${obj.projectSupermarketImagesList}" var="item">
									<ul class="hyjb_dl">
										<li>
											<dl>
												<dt><a href="javascript:void(0)"><img  class="guestImg" src="${item.imageUrl}" /></a></dt>
											</dl>
										</li>
									</ul>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<th>是否显示：</th>
				<td>
					<c:if test="${1==obj.isShow}">否</c:if>
					<c:if test="${0==obj.isShow}">是</c:if>
				</td>
			</tr>

			<tr>
				<th>是否置顶：</th>
				<td>
					<c:if test="${0==obj.isTop}">否</c:if>
					<c:if test="${1==obj.isTop}">是</c:if>
				</td>
			</tr>

			<tr>
				<th style="width:15%;">权重：</th>
				<td colspan=3>
					${obj.sort}
				</td>
			</tr>

			<tr>
				<th style="width:15%;">发布人：</th>
				<td colspan=3>
					${obj.mobile}
				</td>
			</tr>

			<%--<tr>
				<th></th>
				<td colspan=3>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						&lt;%&ndash;<button type="button" onclick="save()" class="btn btn-primary" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&ndash;%&gt;
						&lt;%&ndash;<button type="button" onclick="cancel()" class="btn clear" >取消</button>&ndash;%&gt;
					</div>
				</td>
			</tr>--%>
		</table>
	</form>
	<span class="typefile" id="filePicker"></span>
</div>
<script src="http://malsup.github.io/jquery.form.js"></script>