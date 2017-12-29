<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%--
	模块：悬赏设置
--%>

<div style="width: 98%;margin: 0 auto;" >						
<form method="post" action="reward/updateRewardSet.do" onsubmit="return validateSubmitForm(this)">
	<input type="hidden" name="id" value="${s.id }"/>	
	<input type="hidden" name="createTime" value="<fmt:formatDate  value="${n.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
	<input id="createUserId" name="createUserId" value="${n.createUserId}"  type="hidden" />
	<input id="createUserName" name="createUserName" value="${n.createUserName}"  type="hidden" />		
	<table class="table table-bordered ">
	
			<tr>
				<th style="width: 120px">悬赏最低金额：</th>
				<td>
					<input type="text" name="minReward"  value="${s.minReward}" class="easyui-numberbox" required="true"  maxlength="8"  min="1" max="99999999"/>
					<span class="text-info">J币，发布悬赏金额不得小于悬赏最低金额</span>
				</td>
			</tr>
			<tr>
				<th>平台收费比例：</th>
				<td>
					<input type="text" name="chargePer"  value="${s.chargePer}" class="easyui-numberbox" required="true" max="99" maxlength="2" />%
					<span class="text-info">20%  100J币平台收手续费20J币</span>
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="submit" class="btn btn-primary" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				</td>
			 </tr>
			
			
	</table>
	
	<input type="hidden" name="currentCallback" value="refresh" /> 
</form>
</div>									
						
					
		

	

