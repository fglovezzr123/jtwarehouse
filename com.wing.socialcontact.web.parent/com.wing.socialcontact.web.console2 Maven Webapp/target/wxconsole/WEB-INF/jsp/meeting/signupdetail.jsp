<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<div style="width:100%;">						
	<table class="table table-bordered" style="margin-top:0px;">
		<tr>
			<td colspan="4" style="vertical-align:middle;text-align: left;"><b>个人信息</b></td>
		</tr>
		<tr>
			<th style="width:15%;">姓名：</th>
			<td style="width:35%;">${tjyUser.nickname}</td>
			<th style="width:15%;">性别：</th>
			<td>${(2 ne wxUser.sex)?'男':'女'}</td>
		</tr>
		<tr>
			<th style="width:15%;">出生日期：</th>
			<td><fmt:formatDate value='${wxUser.birthday}' pattern='yyyy-MM-dd'/></td>
			<th style="width:15%;">手机号：</th>
			<td>${tjyUser.mobile}</td>
		</tr>
		<tr>
			<td colspan="4" style="vertical-align:middle;text-align: left;"><b>企业信息</b></td>
		</tr>
		<tr>
			<th style="width:15%;">企业名称：</th>
			<td>${tjyUser.comName}</td>
			<th style="width:15%;">职位：</th>
			<td>${tjyUser.job}</td>
			<!--th style="width:15%;">注册资金：</th>
			<td>${fns:fixed(obj.regCapital)}${ not empty obj.regCapital?'万元':''}</td-->
		</tr>
		<tr>
			<th style="width:15%;">所属行业：</th>
			<td>${tjyUser.industry}</td>
			<th style="width:15%;">所在地区：</th>
			<td>${tjyUser.province} ${tjyUser.city} ${tjyUser.region}</td>
		</tr>
		<!--tr>
			<th style="width:15%;">实缴资本：</th>
			<td>${fns:fixed(obj.payCapital)}${ not empty obj.payCapital?'万元':''}</td>
			<th style="width:15%;">总资产：</th>
			<td>${fns:fixed(obj.totalAssets) }${ not empty obj.totalAssets?'万元':''}</td>
		</tr>
		<tr>
			<th style="width:15%;">主营业务：</th>
			<td>${obj.mainBusiness }</td>
			<th style="width:15%;">年销售额：</th>
			<td>${fns:fixed(obj.annualSales) }${ not empty obj.annualSales?'万元':''}</td>
		</tr-->
		<tr>
			<td colspan="4" style="vertical-align:middle;text-align: left;"><b>报名信息</b></td>
		</tr>
		<tr>
			<th style="width:15%;">参会方式：</th>
			<td colspan=3>${ 1 eq obj.attendType?'线上会议':'线下会议'}</td>
		</tr>
		<tr>
			<th style="width:15%;">其它需求：</th>
			<td colspan=3>${obj.otherReq}</td>
		</tr>
		<tr>
			<th style="width:15%;">天九联系人：</th>
			<td>${obj.tjLinkMan}</td>
			<th style="width:15%;">推荐人：</th>
			<td>${obj.recLinkMan}</td>
		</tr>
	</table>
</div>
