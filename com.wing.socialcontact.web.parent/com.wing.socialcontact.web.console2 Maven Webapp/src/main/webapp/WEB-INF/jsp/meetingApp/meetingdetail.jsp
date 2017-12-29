<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:0px;margin-left: 50px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
</style>
<div style="width:100%;">						
	<table class="table table-bordered" style="margin-top:0px;">
		<tr>
			<th style="width: 15%;vertical-align:middle">会议封面：</th>
			<td colspan=3>
				<div class="hyjb_box" style="margin:0 10px;">
					<ul class="hyjb_dl" style="margin:0px;">
				    	<li>
				    		<dl style="margin-bottom:0px;">
				            	<dt>
				            		<a href="javascript:void(0)">
		            			<c:choose>
			            			<c:when test="${not empty obj.coverImg and obj.coverImg!=''}">
				            			<img status="1" style="width:100%;height:100%;" 
				            				src="${obj.coverImg}" id="hb"/>
			            			</c:when>
			            			<c:otherwise>
				            			<img status="0" style="width:100%;height:100%;" 
				            				src="../../resource/images/tjy/djsc.png" id="hb" />
			            			</c:otherwise>
		            			</c:choose>
				            		</a>
				            	</dt>
				   	    	</dl>
				   	    </li>
				    </ul>
				</div>
			</td>
		</tr>
		<tr>
			<th style="width:15%;">会议主题：</th>
			<td>${obj.titles}</td>
			<th>主办方：</th>
			<td>${obj.sponsor}</td>
		</tr>
		<tr>
			<th style="width:15%;">会议类型：</th>
			<td style="width:35%;">
				<c:if test="${obj.types eq 1}">线上会议</c:if>
				<c:if test="${obj.types eq 2}">线下会议</c:if>
				<c:if test="${obj.types eq 3}">线上会议+线下会议</c:if>
			</td>
			<th>会议地点：</th>
			<td>${obj.place}</td>
		</tr>
		<tr>
			<th>会议时间：</th>
			<td>${fns:fmt(obj.startTime,'yyyy-MM-dd HH:mm:ss')}~${fns:fmt(obj.endTime,'yyyy-MM-dd HH:mm:ss')}</td>
			<th>报名时间：</th>
			<td>${fns:fmt(obj.startSignupTime,'yyyy-MM-dd HH:mm:ss')}~${fns:fmt(obj.endSignupTime,'yyyy-MM-dd HH:mm:ss')}</td>
		</tr>
		<tr>	
			<th>门票价格：</th>
			<c:choose>
			<c:when test="${empty obj or empty obj.ticketPrice or obj.ticketPrice ==0 }">
				<td>免费</td>
			</c:when>
			<c:otherwise>
				<td>${fns:fixed(obj.ticketPrice)}元</td>
			</c:otherwise>
			</c:choose>
			<th>是否显示：</th>
			<td>
				<c:if test="${obj.showEnable eq 1}">是</c:if>
				<c:if test="${obj.showEnable ne 1}">否</c:if>
			</td>
		</tr>
		<tr>
			<th>推荐投融宝首页：</th>
			<td>
				<c:if test="${obj.recommendEnable eq 1}">是</c:if>
				<c:if test="${obj.recommendEnable ne 1}">否</c:if>
			</td>
			<th>推荐投洽峰会首页</th>
			<td>
				<c:if test="${obj.investmentEnable eq 1}">是</c:if>
				<c:if test="${obj.investmentEnable ne 1}">否</c:if>
			</td>
		</tr>
		<tr>
			<th>人数上限：</th>
			<td>
			<c:choose>
			<c:when test="${not empty obj.upperlimit and obj.upperlimit > 0 }">
				${obj.upperlimit}
			</c:when>
			<c:otherwise>
				无限制
			</c:otherwise>
			</c:choose>
			</td>
			<th>权重：</th>
			<td>
				${obj.sort}
			</td>
			
		</tr>
		<tr>
			<th><p class="living">关联视频直播：</p></th>
			<td>${obj.webinarSubject}</td>
			<th>选集视频：</th>
			<td>
				<table style="margin:0px;width:100%;border:0;" class="table table-bordered" >
					
					<tbody id="projTbody">
					   
					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.meetingRelationList}" var="item">
						<tr data-id="${item.id}" style="border:0;">
							<td style="padding-bottom:0px;border:0px;">${item.webinarSubject}</td>
							
						</tr>
   						</c:forEach>
   					</c:if>		
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>会议嘉宾：</th>
			<td colspan=3>
				<div class="hyjb_box">
    				<div class="hyjb_tj">
   					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.meetingGuests}" var="item">
    					<ul class="hyjb_dl">
							<li>
								<dl>
									<dt><a href="javascript:void(0)"><img style="width:120px;height:120px;" src="${item.imgUrl}" /></a></dt>
								</dl>
							</li>
							<li class="hyjb_shuru" style="text-align:center;margin-left:0px;">${item.name}</li>
						</ul>
   						</c:forEach>
   					</c:if>
    				</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>会议详情：</th>
			<td colspan=3>${obj.contents}</td>
		</tr>
		<tr>
			<th>关联项目：</th>
			<td colspan=3>
				<table style="width:100%;" class="table table-bordered" >
					<thead>
						<tr>
							<th style="width:60%;text-align: center;">项目名称</th>
							<th style="width:40%;text-align: center;">项目类型</th>
						</tr>
					</thead>
					<tbody id="projTbody">
					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.meetingProjects}" var="item">
						<tr data-id="${item.id}">
							<td>${item.titles}</td>
							<td>${item.prjType}</td>
						</tr>
   						</c:forEach>
   					</c:if>		
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>往届会议：</th>
			<td colspan=3>
				<table style="width:100%;" class="table table-bordered" >
					<thead>
					<tr>
						<th style="width:100%;text-align: center;">会议名称</th>
					</tr>
					</thead>
					<tbody id="meetingTbody">
					<c:if test="${not empty obj}">
						<c:forEach items="${obj.meetingSuccessiveList}" var="item">
							<tr data-id="${item.id}">
								<td>${item.titles}</td>
							</tr>
						</c:forEach>
					</c:if>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>内容可视范围：</th>
			<td>
				<input type="checkbox" <c:if test="${contentYunYou==true}">checked</c:if> id="contentYunYou" name="contentYunYou" value="1"/>云友
				<input type="checkbox" <c:if test="${contentYunQin==true}">checked</c:if> id="contentYunQin" name="contentYunQin" value="2"/>云亲
				<input type="checkbox" <c:if test="${contentYunShang==true}">checked</c:if> id="contentYunShang" name="contentYunShang" value="4"/>云商
			</td>
			<th>视频可视范围</th>
			<td>
				<input type="checkbox" <c:if test="${videoYunYou==true}">checked</c:if>  id="videoYunYou" name="videoYunYou" value="1"/>云友
				<input type="checkbox" <c:if test="${videoYunQin==true}">checked</c:if> id="videoYunQin" name="videoYunQin" value="2"/>云亲
				<input type="checkbox" <c:if test="${videoYunShang==true}">checked</c:if> id="videoYunShang" name="videoYunShang" value="4"/>云商
			</td>
		</tr>
	</table>
</div>
