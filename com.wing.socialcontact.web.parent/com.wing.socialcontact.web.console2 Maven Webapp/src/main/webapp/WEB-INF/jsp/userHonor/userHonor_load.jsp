<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
	
<div id="cc" class="easyui-layout" data-options="fit:true">  
    <div id='center' data-options="region:'center',title:'',split:true" style="" border="false">			
		<div id="${param.rel}_toolbar" class="search-div">
			<form onsubmit="return datagridSearch(this,'${param.rel }_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">注册手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					<span>
						<label style="width: 120px;">app绑定手机号：</label>
						<input	type="text" name="bind_phone" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">认证手机号：</label>
						<input	type="text" name="recon_mobile" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">职位：</label>
						<select name="job"  style="width: 120px;">
						<option value="">全部</option>
						<c:forEach var="c" items="${jobs}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">行业：</label>
						<select name="industry"  style="width: 120px;">
						<option value="">全部</option>
						<c:forEach var="c" items="${industrys}">
						<option  value="${c.id}">${c.listValue}</option>
						</c:forEach>
						</select>
					</span>
					<span>
						<label style="width: 100px;">注册时间排序：</label>
						<select name="orderBy"  style="width: 120px;">
						<option value="1">倒序</option>
						<option value="2">正序</option>
						
						</select>
					</span>
				
					<span>
							<label style="width: 100px;">等级：</label>
							<select id="level" name="level">
									<option value=""></option>
									<c:forEach items="${userLevelList }" var="t">
									<option value="${t.level }">${t.level }</option>
									</c:forEach>
							</select>
						</span>
					<!-- <span>
						<label style="width: 100px;">地区：</label>
						<input	type="text" name="address" class="span2"/>
					</span> -->
				</div>

				<div class="search-toolbar">
					<!-- <span style="float: left;"> 
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="userhonor/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
						</span>  -->
			
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userhonor/addUserPage.do?rel=${param.rel }" title="新增" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>新增</span>
							</a>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改"
								href="userhonor/updateUserPage.do?id={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改</span>
							</a>
							<a class="easyui-linkbutton" icon="icon-edit" plain="true" title="修改等级"
								href="userEmpiricalLog/addPage4.do?userId={id}&rel=${param.rel}"
								target="dialog" width="800" height="600"
								rel="${param.rel}_update" warn="请先选择一条记录"><span>修改等级</span>
							</a>
					</span> 
		
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="submit" id="main_subimt">查询</button>&nbsp;
						<button class="btn btn-small clear" type="button">清空</button>&nbsp;
					</span>
		
				</div>
			</form>
		</div>
		<table id="${param.rel }_datagrid" toolbar="#${param.rel}_toolbar"></table>
	</div>
	
	<div id="south" data-options="region:'south',title:'',split:true" style="height:250px;" border="false">
    	<div id="tt" class="easyui-tabs" data-options="fit:true" style="width:auto;height:auto;" border="false">
		    
		    <div  title="好友列表" style="overflow:hidden;width: auto;height:auto;" id="q1">
		    	<div class="search-div" id="toolbar">
					<div class="search-content">
						<span>
							<label style="width: 80px;">用户姓名：</label>
							<input type="text" id="nickname" style="width: 100px;"/>
						</span>
						<span>
							<label style="width: 80px;">公司名称：</label>
							<input type="text" id="comName" style="width: 100px;"/>
						</span>
						<span>
							<label style="width: 80px;">职位：</label>
							<input type="text" id="jobName" style="width: 100px;"/>
						</span>
						<span>
							<label style="width: 80px;">地区：</label>
							<input type="text" id="cityName" style="width: 100px;"/>
						</span>
						<span>
							<label style="width: 80px;">行业：</label>
							<input type="text" id="industryName" style="width:100px;"/>
						</span>
						<span>
							<label style="width: 80px;">电话：</label>
							<input type="text" id="mobile" style="width: 100px;"/>
						</span>
						<span style="padding-left:20px;">
							<button class="btn btn-primary btn-small" type="button" onclick="loadUserFriends()">查询</button>&nbsp;
						</span>
					</div>
				</div>
		    	<table id="${param.rel }1_datagrid"  border="true"></table>
		    </div>
		   <div  title="群组列表" style="overflow:hidden;width: auto;height:auto;" id="q2">
		   <div class="search-div" id="toolbar2">
				<div class="search-content">
						<span>
							<label style="width: 80px;">群名称：</label>
							<input type="text" id="groupName" style="width: 100px;"/>
						</span>
						<span>
							<label style="width: 80px;">群人数：</label>
							<input type="text" id="userCount" style="width: 100px;"/>
						</span>
						<span>
							<label style="width: 80px;">私密群：</label>
							<select id="groupType" style="width: 100px;">
								<option value="" selected="selected">全部</option>
								<option value="2" >是</option>
								<option value="1" >否</option>
							</select>
						</span>
						
						<span style="padding-left:20px;">
							<button class="btn btn-primary btn-small" type="button" onclick="loadUserGroups()">查询</button>&nbsp;
						</span>
					</div>
				</div>
		    	<table id="${param.rel }2_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="动态" style="overflow:hidden;width: auto;height:auto;" id="q3">
		   <div class="search-div" id="toolbar2">
				<div class="search-content">
						<span>
							<label style="width: 100px;">动态内容：</label>
							<input type="text" id="dyContent"/>
						</span>
						<span>
							<label style="width: 100px;">查看数：</label>
							<input type="text" id="visitQuantity"/>
						</span>
						<span>
							<label style="width: 100px;">发布时间：</label>
							<input id="issuedDate" style="width:90px;" size=15 class="Wdate time-field" 
								onfocus="WdatePicker({readOnly:true})"
								onClick="WdatePicker({readOnly:true})" 
								readonly="readonly"/>
							
						</span>
						<span style="padding-left:20px;">
							<button class="btn btn-primary btn-small" type="button" onclick="loadDynamic()">查询</button>&nbsp;
						</span>
					</div>
				</div>
		    	<table id="${param.rel }3_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="发布活动" style="overflow:hidden;width: auto;height:auto;" id="q4">
		   <div class="search-div" id="toolbar2">
				<form id="loadActivity" onsubmit="return datagridSearch(this,'${param.rel }4_datagrid');" >
					<div class="search-content">
					<span>
						<label style="width: 100px;">活动主题：</label>
						<input type="text" name="titles"/>
					</span>
					<span>
						<label style="width: 100px;">活动方式：</label>
						<select name="pattern">
							<option value="0">参与方式</option>
							<option value="1">线下参与</option>
							<option value="2">线上参与</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">活动分类：</label>
						<select name="classId">
							<option value="">活动分类</option>
							<option value="1">以玩会友</option>
							<option value="2">以书会友</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">活动标签：</label>
						<select name="tag">
							<option value="">所有</option>
							<c:forEach items="${tag }" var="t">
							<option value="${t.id }">${t.name }</option>
							</c:forEach>
						</select>
					</span>
					<span>
						<label style="width: 100px;">是否显示：</label>
						<select name="showEnable">
							<option value="" >全部</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</span>
					<span>
						<label style="width: 100px;">创建时间：</label>
						<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
						
					</span>
					</div>
	
					<div class="search-toolbar">
						<span style="float:right">
							<button class="btn btn-primary btn-small" type="button" onclick="loadActivity2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
						</span>
			
					</div>
				</form>
				</div>
		    	<table id="${param.rel }4_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="参与活动" style="overflow:hidden;width: auto;height:auto;" id="q41">
		   <div class="search-div" id="toolbar41">
				<form id="loadActivityUser" onsubmit="return datagridSearch(this,'${param.rel }41_datagrid');" >
					<div class="search-content">
					<span>
					<label style="width: 100px;">姓名：</label>
					<input type="text" name="userName"/>
				</span>
				<span>
					<label style="width: 100px;">手机号码：</label>
					<input type="text" name="phone"/>
				</span>
				<span>
					<label style="width: 100px;">创建时间：</label>
					<input type="text"   name="createTime"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 150px;"/>
					
				</span>
					</div>
	
					<div class="search-toolbar">
						<span style="float:right">
							<button class="btn btn-primary btn-small" type="button" onclick="loadActivityUser2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
						</span>
					</div>
				</form>
				</div>
		    	<table id="${param.rel }41_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="发布话题PK" style="overflow:hidden;width: auto;height:auto;" id="q5">
		   <div class="search-div" id="toolbar2">
				<form id="loadTopic" onsubmit="return datagridSearch(this,'${param.rel }5_datagrid');" >
					<div class="search-content">
						<span>
							<label style="width: 100px;">PK话题：</label>
							<input	type="text" name="topicDesc" class="span2"/>
						</span>
						<span><label style="width: 100px;">话题状态：</label>
						<select name="status"  style="width: 120px;">
				    	<option  value="0" >全部</option>
							<option  value="1">显示</option>
							<option  value="2">不显示</option>
						</select>
						</span>
						<span><label style="width: 100px;">是否推荐：</label>
						<select name="isRecommend"  style="width: 120px;">
				    	<option  value="0" >全部</option>
							<option  value="1">推荐</option>
							<option  value="2">不推荐</option>
						</select>
						</span>
						<span><label style="width: 120px;">是否允许评论：</label>
						<select name="allowComment"  style="width: 120px;">
				    	<option  value="0" >全部</option>
							<option  value="1">允许</option>
							<option  value="2">不允许</option>
						</select>
						</span>
						<span><label style="width: 100px;">创建时间：</label>
						<input type="text" name="startTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
							至
						<input type="text" name="endTime"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
						</span>
					</div>
	
					<div class="search-toolbar">
						<span style="float:right">
							<button class="btn btn-primary btn-small" type="button" onclick="loadTopic2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
						</span>
		
					</div>
				</form>
				</div>
		    	<table id="${param.rel }5_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="参与话题PK" style="overflow:hidden;width: auto;height:auto;" id="q51">
		   <div class="search-div" id="toolbar2">
				<form id="loadTopicUser" onsubmit="return datagridSearch(this,'${param.rel }51_datagrid');" >
					<div class="search-content">
						<span>
							<label style="width: 100px;">PK话题：</label>
							<input	type="text" name="topicDesc" class="span2"/>
						</span>
						<span><label style="width: 100px;">话题状态：</label>
						<select name="status"  style="width: 120px;">
				    	<option  value="0" >全部</option>
							<option  value="1">显示</option>
							<option  value="2">不显示</option>
						</select>
						</span>
						<span><label style="width: 100px;">是否推荐：</label>
						<select name="isRecommend"  style="width: 120px;">
				    	<option  value="0" >全部</option>
							<option  value="1">推荐</option>
							<option  value="2">不推荐</option>
						</select>
						</span>
						<span><label style="width: 120px;">是否允许评论：</label>
						<select name="allowComment"  style="width: 120px;">
				    	<option  value="0" >全部</option>
							<option  value="1">允许</option>
							<option  value="2">不允许</option>
						</select>
						</span>
						<span><label style="width: 100px;">创建时间：</label>
						<input type="text" name="startTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
							至
						<input type="text" name="endTime"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
						</span>
					</div>
	
					<div class="search-toolbar">
						<span style="float:right">
							<button class="btn btn-primary btn-small" type="button" onclick="loadTopicUser2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
						</span>
			
					</div>
				</form>
				</div>
		    	<table id="${param.rel }51_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="发布合作" style="overflow:hidden;width: auto;height:auto;" id="q6">
		   <div class="search-div" id="toolbar2">
				<form id="loadBusiness" onsubmit="return datagridSearch(this,'${param.rel }6_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">合作标题：</label>
						<input	type="text" name="titles" class="span2"/>
					</span>
					<span><label style="width: 100px;">合作类别：</label>
					<select name="bizType"  id="bizType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${businesslist}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span><label style="width: 100px;">合作状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">审核中</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadBusiness2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
			
				</div>
			</form>
				</div>
		    	<table id="${param.rel }6_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="参与合作" style="overflow:hidden;width: auto;height:auto;" id="q61">
		   <div class="search-div" id="toolbar2">
				<form id="loadBusinessUser" onsubmit="return datagridSearch(this,'${param.rel }61_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">合作标题：</label>
						<input	type="text" name="titles" class="span2"/>
					</span>
					<span><label style="width: 100px;">合作类别：</label>
					<select name="bizType"  id="bizType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${businesslist}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span><label style="width: 100px;">合作状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">审核中</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadBusinessUser2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
								
		
				</div>
			</form>
				</div>
		    	<table id="${param.rel }61_datagrid"  border="true"></table>
		    </div>
		    
		
		   <div  title="发布悬赏" style="overflow:hidden;width: auto;height:auto;" id="q7">
		   <div class="search-div" id="toolbar2">
				<form id="loadReward" onsubmit="return datagridSearch(this,'${param.rel }7_datagrid');" >
				<div class="search-content">
					<span><label style="width: 100px;">行业：</label>
					<select name="voType"  id="voType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${rewardlist}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">问题：</label>
						<input	type="text" name="question" class="span2"/>
					</span>
					<span><label style="width: 100px;">有效期：</label>
					<input type="text" name="startTimeyx"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimeyx"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
					<br/>
					<span><label style="width: 100px;">状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">待审核</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
						<option  value="4">已到期</option>
						<option  value="5">已完成</option>
						<option  value="6">已取消</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadReward2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
			
		
				</div>
			</form>
				</div>
		    	<table id="${param.rel }7_datagrid"  border="true"></table>
		    </div>
		    
		   <div  title="收藏悬赏" style="overflow:hidden;width: auto;height:auto;" id="q71">
		   <div class="search-div" id="toolbar2">
				<form id="loadRewardUser" onsubmit="return datagridSearch(this,'${param.rel }71_datagrid');" >
				<div class="search-content">
					<span><label style="width: 100px;">行业：</label>
					<select name="voType"  id="voType" style="width: 120px;">
					    <option  value="0" >全部</option>
						<c:forEach var="c" items="${rewardlist}">
						<option  value="${c.id}">${c.className}</option>
						</c:forEach>	
					</select>
					</span>
					<span>
						<label style="width: 100px;">问题：</label>
						<input	type="text" name="question" class="span2"/>
					</span>
					<span><label style="width: 100px;">有效期：</label>
					<input type="text" name="startTimeyx"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimeyx"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
					<br/>
					<span><label style="width: 100px;">状态：</label>
					<select name="status"  style="width: 120px;">
			    	<option  value="0" >全部</option>
						<option  value="1">待审核</option>
						<option  value="2">审核成功</option>
						<option  value="3">审核失败</option>
						<option  value="4">已到期</option>
						<option  value="5">已完成</option>
						<option  value="6">已取消</option>
					</select>
					</span>
					<span><label style="width: 100px;">创建时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
				</div>

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadRewardUser2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
	
					
				</div>
			</form>
				</div>
		    	<table id="${param.rel }71_datagrid"  border="true"></table>
		    </div>
		    
		    
		  <div  title="评论" style="overflow:hidden;width: auto;height:auto;" id="q8">
		   <div class="search-div" id="toolbar2">
				<form id="loadComment" onsubmit="return datagridSearch(this,'${param.rel }8_datagrid');" >
				<div class="search-content">
					<!-- <span>
						<label style="width: 100px;">评论人：</label>
						<input	type="text" name="trueNamef" class="span2"/>
					</span> -->
					<span>
						<label style="width: 100px;">评论内容：</label>
						<input	type="text" name="cmeDesc" class="span2"/>
					</span>
					<span><label style="width: 100px;">评论类别：</label>
					<select name="cmeType"  style="width: 120px;">
			    	<option  value="" >全部</option>
						<option  value="1">资讯</option>
						<option  value="2">合作</option>
						<option  value="3">话题</option>
						<option  value="4">活动</option>
						<option  value="5">动态</option>
					</select>
					</span>
					<span><label style="width: 100px;">评论时间：</label>
					<input type="text" name="startTimef"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;"  />  
						至
					<input type="text" name="endTimef"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:120px;" />
					</span>
					<span>
						<label style="width: 100px;">评论主题内容：</label>
						<input	type="text" name="title" class="span2"/>
					</span>
					<!--<span>
						<label style="width: 100px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span> -->
				</div>

				<div class="search-toolbar">
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadComment2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
	
				</div>
			</form>
				</div>
		    	<table id="${param.rel }8_datagrid"  border="true"></table>
		    </div>
		    
		    
		  <div  title="互助宝" style="overflow:hidden;width: auto;height:auto;" id="q9">
		   
		    	<table id="${param.rel }9_datagrid"  border="true"></table>
		    </div>
		    
		  <div  title="J币" style="overflow:hidden;width: auto;height:auto;" id="q10">
		   <div class="search-div" id="toolbar2">
				<form id="loadJb" onsubmit="return datagridSearch(this,'${param.rel }10_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					
				
				</div>

				<div class="search-toolbar">
					<!-- <span style="float: left;"> 
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="walletLog/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
					</span> -->
					
					
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="walletLog/addJbPage.do?rel=${param.rel }" title="添加余额" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>添加余额</span>
							</a>
					</span> 
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadJb2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
					
		
				</div>
			</form>
				</div>
		    	<table id="${param.rel }10_datagrid"  border="true"></table>
		    </div>
		    
		  <div  title="余额" style="overflow:hidden;width: auto;height:auto;" id="q11">
		   <div class="search-div" id="toolbar2">
				<form id="loadRmb" onsubmit="return datagridSearch(this,'${param.rel }11_datagrid');" >
				<div class="search-content">
					<span>
						<label style="width: 100px;">用户姓名：</label>
						<input	type="text" name="nickname" class="span2"/>
					</span>
					<span>
						<label style="width: 100px;">手机号：</label>
						<input	type="text" name="mobile" class="span2"/>
					</span>
					
				
				</div>

				<div class="search-toolbar">
					<!-- <span style="float: left;"> 
					   <a  class="easyui-linkbutton"  icon="icon-remove"	plain="true"
						href="walletLog/del.do" target="selectedTodo"  title="确定要删除吗?" warn="至少勾选一条记录">批量删除</a>
					</span> -->
					
					
					<span style="float: left;"> 
							<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="walletLog/addRmbPage.do?rel=${param.rel }" title="添加余额" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>添加余额</span>
							</a>
					</span> 
					<span style="float:right">
						<button class="btn btn-primary btn-small" type="button" onclick="loadRmb2()">查询</button>&nbsp;
						<!-- 	<button class="btn btn-primary btn-small" type="submit">查询</button>&nbsp;
							<button class="btn btn-small clear" type="button">清空</button>&nbsp; -->
					</span>
			
				</div>
			</form>
				</div>
		    	<table id="${param.rel }11_datagrid"  border="true"></table>
		    </div>
		    
		    
		  <div  title="等级明细" style="overflow:hidden;width: auto;height:auto;" id="q12">
		   <div class="search-div" id="toolbar2">
				<form onsubmit="return datagridSearch(this,'${param.rel }12_datagrid');" >
				<div class="search-toolbar">
					<span style="float: left;"> 
					
						<a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userEmpiricalLog/addPage2.do?rel=${param.rel }" title="添加经验" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>添加经验</span>
						</a>
						 </span> 
		
				</div>
			</form>
				</div>
		    	<table id="${param.rel }12_datagrid"  border="true"></table>
		    </div>
		    
		  <div  title="积分明细" style="overflow:hidden;width: auto;height:auto;" id="q13">
		   <div class="search-div" id="toolbar2">
				<form onsubmit="return datagridSearch(this,'${param.rel }13_datagrid');" >
				<div class="search-toolbar">
					<span style="float: left;"> 
					     <a class="easyui-linkbutton" icon="icon-add" plain="true"
								href="userIntegralLog/addPage2.do?rel=${param.rel }" title="添加积分" target="dialog"
								width="800" height="600" rel="${param.rel}_add"><span>添加积分</span>
						</a>
						 </span> 
		
		
				</div>
			</form>
				</div>
		    	<table id="${param.rel }13_datagrid"  border="true"></table>
		    </div>
		    
		    
		    
		    
		</div>
	</div> 
</div>
				

<script type="text/javascript">	
    var pid = ""; 
    var userHonorId="";
	$(function() {
		
		loadMain();			
	});
	
	function loadMain(){
		$('#<%=request.getParameter("rel")%>_datagrid').datagrid({
				url : "userhonor/query.do", 
				fitColumns: false,
				frozenColumns:[[
					 {
							field : "nickname",
							title : "用户姓名",
							width : 80,
							align : "center"
					 },
					 {
							field : "mobile",
							title : "注册电话",
							width : 100,
							align : "center"
					 },
					 {
							field : "com_name",
							title : "公司名称",
							width : 120,
							align : "center",
					},
					{
						field : "job_name",
						title : "职位",
						width : 80,
						align : "center"
					}  
	            ]],  
				columns : [ [
						{
							field : "province_name",
							title : "地区",
							width : 180,
							align : "center",
							formatter: function(value,row,index){
								if(row.province_name!=null&&row.city_name!=null&&row.county_name!=null){
									return row.province_name+row.city_name+row.county_name;
								}else if(row.province_name!=null&&row.city_name!=null){
									return row.province_name+row.city_name;
								}else if(row.province_name!=null){
									return row.province_name;
								}
							}
						},
						
						//{
						//	field : "address",
						//	title : "具体地址",
						//	width : 180,
						//	align : "center"
						//},
						{
							field : "bind_phone",
							title : "app绑定手机号",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "recon_mobile",
							title : "认证手机号",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "recon_status",
							title : "审核状态",
							width : 80,
							align : "center",
							formatter: function(value,row,index){
								if (value=='0') {
									value="";
								}else if (value=='1') {
									value="提交审核";
								}else if (value=='2') {
									value="审核通过";
								}else if (value=='3') {
									value="审核不通过";
								}else{
									value="";
								}
								return value;
							}
						},
						{
							field : "availableBalance",
							title : "RMB余额",
							width : 100,
							align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0.00";
								}else if(0 == value){
									return "0.00";
								}else{
									return value;
								}
							}
						},
						{
							field : "jb_amount",
							title : "J币余额",
							width : 100,
					 		align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0";
								}else{
									return value;
								}
							}
					    },
					    {
							field : "hzb_amount",
							title : "互助宝余额",
							width : 100,
					 		align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0.00";
								}else if(0 == value){
									return "0.00";
								}else{
									return value;
								}
							}
					    },
					    {
							field : "level",
							title : "等级",
							width : 100,
					 		align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "LV1";
								}else{
									return value;
								}
							}
					    },
					    {
							field : "integral_total",
							title : "积分",
							width : 100,
					 		align : "center",
							formatter: function(value,row,index){
								if(null == value){
									return "0";
								}else if(0 == value){
									return "0";
								}else{
									return value;
								}
							}
					    },
					    {
							field : "addTime",
							title : "注册时间",
							width : 200,
							align : "center",
							formatter : function(value, row, index) {
								if(null == value){
									return "";
								}else{
									return new Date(value)
											.format("yyyy-MM-dd HH:mm");
								}
							}
						},
					    {
							field : "lastLoginDate",
							title : "最后登录时间",
							width : 200,
							align : "center",
							formatter : function(value, row, index) {
								if(null == value){
									return "";
								}else{
									return new Date(value)
											.format("yyyy-MM-dd HH:mm");
								}
							}
						},
						{
							field : "op",
							title : "操作",
							width : 80,
							align : "center",
							formatter: function(value,row,index){
								return '<a href="userhonor/user_view.do?id='+row.id+'&rel=<%=request.getParameter("rel")%>" target="dialog" width="1000" height="550"  rel="<%=request.getParameter("rel")%>_show"  title="详情" >查看</a>';
							}
						}
						
						
				] ],
				onSelect : function(index, row){
					pid = row.id;
					//loadUserHonor();
					loadUserFriends();
					loadUserGroups();
					loadDynamic();
					loadActivity();
					loadActivityUser();
					loadBusiness();
					loadBusinessUser();
					loadTopic();
					loadTopicUser();
					loadReward();
					loadRewardUser();
					loadComment();
					loadRmb();
					loadHzb();
					loadJb();
					loadIntegralLog();
					loadEmpiricalLog();
				}
				
			});
	}
	
	function loadActivity2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadActivity').submit();
		}
	}
	function loadActivityUser2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadActivityUser').submit();
		}
	}
	function loadBusiness2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadBusiness').submit();
		}
	}
	function loadBusinessUser2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadBusinessUser').submit();
		}
	}
	function loadTopic2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadTopic').submit();
		}
	}
	function loadTopicUser2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadTopicUser').submit();
		}
	}
	function loadReward2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadReward').submit();
		}
	}

	function loadEmpiricalLog2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadEmpiricalLog').submit();
		}
	}
	function loadIntegralLog2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadIntegralLog').submit();
		}
	}
	function loadJb2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadJb').submit();
		}
	}
	function loadHzb2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadHzb').submit();
		}
	}
	function loadRmb2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadRmb').submit();
		}
	}
	function loadComment2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadComment').submit();
		}
	}
	function loadRewardUser2(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}else{
			$('#loadRewardUser').submit();
		}
	}
	

	
	//编辑
	function addDk(){
		var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('新增大咖','daka/addPage.do?rel=userList',"dakaaddpage",params);
	}
	
	function loadUserHonor(){
		$('#<%=request.getParameter("rel")%>0_datagrid').datagrid({
			url : "userhonor/query2.do?userId="+pid, 
			columns : [ [
			{
				field : "ck",
				title : "勾选",
				checkbox : true
			},
			{
				field : "title",
				title : "勋章",
				width : $(this).width() * 0.15,
				align : "center"
			}
			] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('计算得分明细','pm/kpi/KpiPingFen/defendetail.do?pid='+pid+'&id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update2',{width:1000,height:600});			
				
			},
			onSelect : function(index, row){
				userHonorId=row.id;
			}
		});
	}
	
	
	function userhonor_add(){
		if(pid == ""){
			Msg.topCenter({
				title:"提示",
				msg:'请先选择一条主数据!',
				msgType:"warning"
			});
		}else{
			MUI.openDialog('新增','userhonor/addPage.do?userId='+pid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>0_update',{width:1000,height:500}, 'loadLiangBiaodetail()');	
		}
	}
		
	function userhonor_update(){
		if(pid == ""){
			Msg.topCenter({
				title:"提示",
				msg:'请先选择一条数据!',
				msgType:"warning"
			});
			//return false;
		}else{
			MUI.openDialog('修改','userhonor/updatePage.do?&id='+userHonorId+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>0_update',{width:1000,height:500}, 'loadLiangBiaodetail()');	
		}	
	}
	//获取好友列表
	function loadUserFriends(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}
		$('#<%=request.getParameter("rel")%>1_datagrid').datagrid({
			url : "im/queryFriendList.do?userId="+pid, 
			rownumbers: true,
			queryParams: { 
				nickname: $("#nickname").val(),
				comName: $("#comName").val(),
				jobName: $("#jobName").val(),
				cityName: $("#cityName").val(),
				industryName: $("#industryName").val(),
				mobile: $("#mobile").val()
			},
			columns : [ [
			{
				field : "ck",
				title : "勾选",
				checkbox : true
			},
			{
					field : "nickname",
					title : "用户姓名",
					width : $(this).width() * 0.15,
					align : "center"
			 },
			 {
					field : "com_name",
					title : "公司名称",
					width : $(this).width() * 0.15,
					align : "center",
			},
			{
				field : "job_name",
				title : "职位",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "industry_name",
				title : "行业",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "mobile",
				title : "电话",
				width : $(this).width() * 0.15,
				align : "center"
			},
			{
				field : "province_name",
				title : "地区",
				width : $(this).width() * 0.15,
				align : "center",
				formatter: function(value,row,index){
					if(row.province_name!=null&&row.city_name!=null){
						return row.province_name+row.city_name;
					}else if(row.province_name!=null){
						return row.province_name;
					}else if(row.city_name!=null){
						return row.city_name;
					}
				}
			}
			] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('计算得分明细','pm/kpi/KpiPingFen/defendetail.do?pid='+pid+'&id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update2',{width:1000,height:600});			
				
			},
			onSelect : function(index, row){
				
			}
		});
	}
	//获取用户群组
	function loadUserGroups(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}
		$('#<%=request.getParameter("rel")%>2_datagrid').datagrid({
			url : "im/queryGroupList.do?userId="+pid, 
			rownumbers: true,
			queryParams: { 
				groupName: $("#groupName").val(),
				userCount: $("#userCount").val(),
				groupType: $("#groupType").val()
			},
			columns : [ [
			{
				field : "ck",
				title : "勾选",
				checkbox : true
			},
			{
				field : "group_name",
				title : "群名称",
				width : $(this).width() * 0.15,
				align : "center"
		 	},
		 	{
				field : "group_userCount",
				title : "群人数",
				width : $(this).width() * 0.15,
				align : "center",
			},
			{
				field : "group_type",
				title : "私密群",
				width : $(this).width() * 0.15,
				align : "center",
				formatter: function(value,row,index){
					if(row.group_type == 1){
						return "否";
					}else if(row.group_type == 2){
						return "是";
					}
				}
			}
			] ],
			onDblClickRow:function(rowIndex, rowData){
				///MUI.openDialog('计算得分明细','pm/kpi/KpiPingFen/defendetail.do?pid='+pid+'&id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update2',{width:1000,height:600});			
				
			},
			onSelect : function(index, row){
				userHonorId=row.id;
			}
		});
	}
	
	function loadDynamic(){
		if(isEmpty(pid)){
			alert("请选择一个用户!");
			return;
		}
		$("#<%=request.getParameter("rel")%>3_datagrid").datagrid({
			url : "dynamic/dynamicquery.do",
			rownumbers: true,
			queryParams: { 
				dyContent: $("#").val(),
				user_id: pid,
				visitQuantity: $("#visitQuantity").val(),
				issuedDate: $("#issuedDate").val()
			},
			columns : [[
				 {
					field : "dy_content",
					title : "动态内容",
					width : 180,
					align : "center"
				},{
					field : "nickname",
					title : "发布人",
					width : 60,
					align : "center"
				},{
					field : "visit_quantity",
					title : "查看数",
					width : 60,
					align : "center"
				},
				{
					field : "commentCount",
					title : "评论数",
					width : 60,
					align : "center"
				},
				{
					field : "lickCount",
					title : "点赞数",
					width : 60,
					align : "center"
				},
				{
					field : "issued_date",
					title : "创建时间",
					width : 100,
					align : "center",
					formatter : function(value, row, index) {
						return new Date(row.issued_date)
								.format("yyyy-MM-dd HH:mm");
					}
				},
				{
					field : "op",
					title : "操作",
					align:"center",
					width : 120,
					formatter: function(value, row, index){
								return "<a href = 'javaScript:void(0)' onclick = detailMetting('"+row.id+"')>查看</a>&nbsp;&nbsp;"
								+"<a href = 'javaScript:void(0)' onclick = deleteDynamic('"+row.id+"')>删除</a>"
					}
				}
			]],
		});
	}
	
	//发布
	function addDynamic(){
		var params = { onClose:function(){  
	        $("#dynamicaddpage").dialog('destroy');  
	    }, closed: false,cache: false,modal:true,width:800,height:400,collapsible:false,minimizable:false,maximizable:false};
		MUI.openDialog('发布动态','dynamic/dynamicaddpage.do',"dynamicaddpage",params)
	}

	//删除
	function deleteDynamic(fid){
		confirmx("确定删除吗？",function(s){
			if(s==1){
				$.ajax({
					url:"dynamic/dynamicdel.do",
					type: 'post',	
					data: {id:fid},
					cache: false,
					dataType:"json",
					success:function(json){
						if(json&&json["code"]==="0"){
							alert("操作成功");
							$("#<%=request.getParameter("rel")%>3_datagrid").datagrid('reload');
						}else{
							alert("操作失败");
						}			
					}
				});
			}
		})
	}
	
	//明细
	function detailMetting(fid){
		var params = { closed: false,cache: false,modal:true,width:1000,height:600,collapsible:false,minimizable:false,maximizable:false};
		//MUI.openDialog('动态明细','dynamic/dynamicdetail.do?id='+fid,"dynamicdetailpage",params);
		MUI.openDialog('动态明细','dynamic/dynamicdetailview.do?id='+fid,"dynamicdetailviewpage",{width:350,height:650});
	}
	//发布活动
	function loadActivity(){
		$('#<%=request.getParameter("rel")%>4_datagrid').datagrid(
			{
				url : "activity/query.do?userId="+pid,  
				columns : [ [
						/* {
							field:"ck",
							title : "勾选",
							checkbox:true
						}, */   
						{
								field : "titles",
								title : "活动标题",
								width : $(this).width() * 0.2,
								align : "center",
						},
						{
								field : "createUserName",
								title : "发起者",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
								field : "classId",
								title : "活动类别",
								width : $(this).width() * 0.1,
								align : "center",
								formatter : function(value, row, index) {
									switch(value){
									 case  '1' :
									 		return "以玩会友";
									 case  '2' :
									 		return "以书会友";
									}
								}
						},
						{
								field : "pattern",
								title : "活动方式",
								width : $(this).width() * 0.1,
								align : "center",
								formatter : function(value, row, index) {
									switch(value){
									 case  1 :
									 		return "线下参与";
									 case  2 :
									 		return "线上参与";
									}
								}
						},
						{
							field : "tagName",
							title : "活动标签",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "times",
							title : "活动开始时间",
							width : 320,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime).format("yyyy-MM-dd HH:mm")+"至"+
								new Date(rowData.endTime).format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "status",
							title : "活动状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(rowIndex, rowData) {
								
								switch(rowData.status){
								 case  1 :
								 		return "未审核";
								 case  2 : 
								 		return "报名中";
								 case  3 :
								 		return "报名结束";
								 case  4 :
								 		return "进行中";
								 case  5 :
								 		return "已结束";
								 case  6 :
								 		return "已取消";
								}
							}
						},
						{
							field : "showEnable",
							title : "显示",
							width : $(this).width() * 0.05,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "recommendEnable",
							title : "推荐首页",
							width : $(this).width() * 0.05,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "recommendList",
							title : "推荐列表",
							width : $(this).width() * 0.05,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  0 :
								 		return "否";
								 case  1 :
								 		return "是";
								}
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss");
							}
						},
						{
							field : "op",
							title : "操作",
							width : $(this).width() * 0.2,
							align : "center",
							formatter : function(rowIndex, rowData) {
								var status= rowData.status;//活动状态
								var isdelay= rowData.isdelay;//延期申请
								var iscancel= rowData.iscancel;//取消申请
								var iscod=rowData.iscod;//是否结算
								var str="";
								if(status==1){
									str+="<a href = 'javaScript:void(0)' onclick = checkactivity('"+rowData.id+"',2)>同意</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = updateactivity('"+rowData.id+"')>编辑</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = cancelactivity('"+rowData.id+"')>取消</a>";
								}else if(status==2||status==3||status==4){
									str+="<a href = 'javaScript:void(0)' onclick = checkactivity('"+rowData.id+"',1)>不同意</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = updateactivity('"+rowData.id+"')>编辑</a>"+
										"&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = cancelactivity('"+rowData.id+"')>取消</a>";
								}else if(status==5){
									
								}
								
								str+="&nbsp;&nbsp;&nbsp;<a href = 'javaScript:void(0)' onclick = viewPage('"+rowData.id+"')>查看</a>";
								
								return str;
							}
						}
						
				] ],
				
			});
	}
	
	
	function updateactivity(fid){
		MUI.openDialog('编辑活动','activity/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:600});
	}
	function viewPage(fid){
		MUI.openDialog('查看活动','activity/viewPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_view',{width:800,height:600});
	}
	function checkcancel(fid){
		MUI.openDialog('取消审核','activity/cancelPage.do?acid='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_cancel',{width:600,height:700});
	}
	function checkdelay(fid){
		MUI.openDialog('延期审核','activity/delayPage.do?acid='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_delay',{width:600,height:700});
	}
	function deleteactivity(id){
		Msg.confirm('提示',"确定要删除吗？<br/>", function(r){
	          if (r){
				var pm = {"id":id};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activity/del.do",pm,dg,'');
	          }
	     });
	}
	function delay(id,type){
				var pm = {"id":id,"type":type};
				var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				selectedTodo_doPost("activity/delay.do",pm,dg,'');
	}
	function checkactivity(id,status){
		var str="确定要同意吗？<br/>";
		if(status==1){
			str="确定要不同意吗？<br/>";
		}
		Msg.confirm('提示',str, function(r){
	          if (r){
	        	  var pm = {"id":id,"status":status};
				  var dg = $('#<%=request.getParameter("rel")%>_datagrid');
				  selectedTodo_doPost("activity/checkactivity.do",pm,dg,'');
	          }
	     });
	}
	function cancelactivity(id){
		Msg.confirm('提示',"确定要取消活动吗？<br/>", function(r){
	          if (r){
	        	  var pm = {"id":id};
					var dg = $('#<%=request.getParameter("rel")%>_datagrid');
					selectedTodo_doPost("activity/cancelactivity.do",pm,dg,'');
	          }
	     });
				
	}
	
	
	function loadActivityUser(){
		$('#<%=request.getParameter("rel")%>41_datagrid').datagrid(
			{
				url : "activityUser/query2.do?userId="+pid, 
				singleSelect:true,
				selectOnCheck:true,
				columns : [ [
						{
								field : "titles",
								title : "活动标题",
								width : $(this).width() * 0.1,
								align : "center",
						},
						{
								field : "classId",
								title : "活动类别",
								width : $(this).width() * 0.1,
								align : "center",
								formatter : function(value, row, index) {
									switch(value){
									 case  '1' :
									 		return "以玩会友";
									 case  '2' :
									 		return "以书会友";
									}
								}
						},
						{
							field : "userName",
							title : "姓名",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "phone",
							title : "手机号码",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "price",
							title : "门票价格",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								if(value==0){
									return "免费";
								}else{
									return value;
								}
							}
						},
						{
							field : "company",
							title : "公司名称",
							width : $(this).width() * 0.1,
							align : "center"
						},
						{
							field : "createTime",
							title : "报名时间",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:ss");
							}
						},
						{
							field : "status",
							title : "报名状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  1 :
								 		return "未确认";
								 case  2 :
								 		return "用户取消";
								 case  3 :
								 		return "活动取消";
								 case  4 :
								 		return "已确认";
								 case  5 :
								 		return "已拒绝";
								}
							}
						}/* ,
						{
							field : "orderStatus",
							title : "订单状态",
							width : $(this).width() * 0.1,
							align : "center",
							formatter : function(value, row, index) {
								
								switch(value){
								 case  1 :
								 		return "未支付";
								 case  2 :
								 		return "已支付";
								 case  3 :
								 		return "已退款";
								}
							}
						} */
						
				] ]
				
			});
	}
	
	function loadTopic(){
		$('#<%=request.getParameter("rel")%>5_datagrid').datagrid(
			{
				url : "topic/query.do?createUserId="+pid, 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "topicDesc",
								title : "PK话题",
								width : 300,
								align : "center",
						},
						{
							field : "createUserName",
							title : "发布人",
							width : 100,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "蓝方观点",
							width : 160,
							align : "center"
						},{
							field : "redPoint",
							title : "红方观点",
							width : 160,
							align : "center"
						},{
							field : "status",
							title : "话题状态",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "显示";
								}else if(rowData.status==2){
									str = "不显示";
								}
								return str;
							}
						},{
							field : "allowComment",
							title : "是否允许评论",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.allowComment==1){
									str = "允许";
								}else if(rowData.allowComment==2){
									str = "不允许";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "是否推荐",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 80,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 60,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewt('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	function loadTopicUser(){
		$('#<%=request.getParameter("rel")%>51_datagrid').datagrid(
			{
				url : "topic/query.do?userId="+pid, 
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "topicDesc",
								title : "PK话题",
								width : 300,
								align : "center",
						},
						{
							field : "createUserName",
							title : "发布人",
							width : 100,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "蓝方观点",
							width : 160,
							align : "center"
						},{
							field : "redPoint",
							title : "红方观点",
							width : 160,
							align : "center"
						},{
							field : "status",
							title : "话题状态",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "显示";
								}else if(rowData.status==2){
									str = "不显示";
								}
								return str;
							}
						},{
							field : "allowComment",
							title : "是否允许评论",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.allowComment==1){
									str = "允许";
								}else if(rowData.allowComment==2){
									str = "不允许";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "是否推荐",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "发布时间",
							width : 80,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 60,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewt('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewt(fid){
		MUI.openDialog('查看详情','topic/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:650,height:450});
	}

	
	function loadBusiness(){
		$('#<%=request.getParameter("rel")%>6_datagrid').datagrid(
			{
				url : "business/query.do?createUserId="+pid,  
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "className",
								title : "合作类别",
								width : 80,
								align : "center",
						},
						{
							field : "createUserName",
							title : "发布人",
							width : 100,
							align : "center",
						},
						 {
								field : "titles",
								title : "合作标题",
								width : 150,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "合作有效期",
							width : 180,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},{
							field : "status",
							title : "合作状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "审核中";
								}else if(rowData.status==2){
									str = "审核成功";
								}else if(rowData.status==3){
									str = "审核失败";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "推荐",
							width : 80,
							align : "center",
							formatter : function(rowIndex, rowData)  {
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},{
							field : "appealType",
							title : "诉求分类",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.appealType==1){
									str = "供给";
								}else if(rowData.appealType==2){
									str = "需求";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	function loadBusinessUser(){
		$('#<%=request.getParameter("rel")%>61_datagrid').datagrid(
			{
				url : "business/query2.do?userId="+pid,  
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "className",
								title : "合作类别",
								width : 80,
								align : "center",
						},
						{
							field : "createUserName",
							title : "发布人",
							width : 100,
							align : "center",
						},
						 {
								field : "titles",
								title : "合作标题",
								width : 150,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "bluePoint",
							title : "合作有效期",
							width : 180,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},{
							field : "status",
							title : "合作状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "审核中";
								}else if(rowData.status==2){
									str = "审核成功";
								}else if(rowData.status==3){
									str = "审核失败";
								}
								return str;
							}
						},{
							field : "isRecommend",
							title : "推荐",
							width : 80,
							align : "center",
							formatter : function(rowIndex, rowData)  {
								var str = "";
								if(rowData.isRecommend==1){
									str = "推荐";
								}else if(rowData.isRecommend==2){
									str = "不推荐";
								}
								return str;
							}
						},{
							field : "appealType",
							title : "诉求分类",
							width : 60,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.appealType==1){
									str = "供给";
								}else if(rowData.appealType==2){
									str = "需求";
								}
								return str;
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewb('"+rowData.id+"')>编辑</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewb(fid){
		MUI.openDialog('编辑','business/updatePage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:650,height:550});
	}
	
	
	
	
	
	function loadReward(){
		$('#<%=request.getParameter("rel")%>7_datagrid').datagrid(
			{
				url : "reward/query.do?createUserId="+pid,   
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "className",
								title : "行业",
								width : 80,
								align : "center",
						},{
							field : "question",
							title : "问题",
							width : 200,
							align : "center",
						},
						{
							field : "nickname",
							title : "用户姓名",
							width : 100,
							align : "center",
						},
						{
							field : "mobile",
							title : "注册电话",
							width : 100,
							align : "center",
						},
						{
							field : "status",
							title : "状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "待审核";
								}else if(rowData.status==2){
									str = "审核通过";
								}else if(rowData.status==3){
									str = "已取消";
								}else if(rowData.status==4){
									str = "已完成";
								}else if(rowData.status==5){
									str = "已过期";
								}
								return str;
							}
						},
						{
								field : "countNum",
								title : "应答",
								width : 80,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "yxq",
							title : "悬赏有效期",
							width : 150,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewr('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	function loadRewardUser(){
		$('#<%=request.getParameter("rel")%>71_datagrid').datagrid(
			{
				url : "reward/query2.do?userId="+pid,   
				fitColumns: false,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "className",
								title : "行业",
								width : 80,
								align : "center",
						},{
							field : "question",
							title : "问题",
							width : 200,
							align : "center",
						},
						{
							field : "nickname",
							title : "用户姓名",
							width : 100,
							align : "center",
						},
						{
							field : "mobile",
							title : "注册电话",
							width : 100,
							align : "center",
						},
						{
							field : "status",
							title : "状态",
							width : 80,
							align : "center",
							formatter: function(rowIndex, rowData){
								var str = "";
								if(rowData.status==1){
									str = "待审核";
								}else if(rowData.status==2){
									str = "审核通过";
								}else if(rowData.status==3){
									str = "已取消";
								}else if(rowData.status==4){
									str = "已完成";
								}else if(rowData.status==5){
									str = "已过期";
								}
								return str;
							}
						},
						{
								field : "countNum",
								title : "应答",
								width : 80,
								align : "center",
						},
						{
							field : "reward",
							title : "悬赏J币",
							width : 80,
							align : "center"
						},
						{
							field : "yxq",
							title : "悬赏有效期",
							width : 150,
							align : "center",
							formatter : function(rowIndex, rowData) {
								return new Date(rowData.startTime)
										.format("yyyy-MM-dd")+"至"+new Date(rowData.endTime)
										.format("yyyy-MM-dd");
							}
						},
						
						{
							field : "createTime",
							title : "创建时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						{
							field : "op",
							title : "操作",
							align:"center",
							width : 80,
							formatter: function(rowIndex, rowData){
								return  "<a href = 'javaScript:void(0)' onclick = showviewr('"+rowData.id+"')>查看</a>";
							}
						}
						
				] ],
				
			});
	}
	
	function showviewr(fid){
		MUI.openDialog('详情','reward/showPage.do?id='+fid+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_show',{width:800,height:550});
	}
	
	

	function loadComment(){
		$('#<%=request.getParameter("rel")%>8_datagrid').datagrid(
			{
				url : "comment/query.do?userId="+pid, 
			    ///nowrap:false,//允许换行
			    fitColumns:true,//宽度自适应
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						 {
								field : "cme_type2",
								title : "评论类别",
								width : 80,
								align : "center",
								formatter: function(value,row,index){ 
									if(value=='1'){
										return '资讯';
									}else if(value=='2'){
										return '合作';
									}else if(value=='3'){
										return '话题';
									}else if(value=='4'){
										return '活动';
									}else if(value=='5'){
										return '动态';
									}else if(value=='10'){
										return '资讯回复';
									}else if(value=='20'){
										return '合作回复';
									}else if(value=='30'){
										return '话题回复';
									}else if(value=='40'){
										return '活动回复';
									}else if(value=='50'){
										return '动态回复';
									}else{
										return ' ';
									}
									     
								}
						},
						{
							field : "trueName",
							title : "评论人",
							width : 100,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : 100,
							align : "center",
						},
						 {
								field : "cme_desc",
								title : "评论内容",
								width : 450,
								align : "center",
								formatter: function(value,row,index){  
									   return '<span title='+value+'>'+value+'</span>'  
								}
						},
						{
							field : "create_time",
							title : "评论时间",
							width : 130,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm");
							}
						},
						 {
							field : "title",
							title : "评论主题内容",
							width : 550,
							align : "center"
						}
						
				] ],
				
			});
	}
	
	
	function loadHzb2(){
		$('#<%=request.getParameter("rel")%>9_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "walletLog/hzb_query.do?userId="+pid, 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "nickname",
							title : "用户姓名",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "amount",
							title : "余额变化",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(row.pd_type == 1){
										return "+"+value;
								}else if(row.pd_type == 2){
									return "-"+value;
								}
							}
						},
						{
							field : "ye_amount",
							title : "余额",
							width : $(this).width() * 0.15,
							align : "center"
						},
						//{
						//	field : "pay_status",
						//	title : "支付状态",
						//	width : $(this).width() * 0.15,
						//	align : "center",
						//	formatter : function(value, row, index) {
						//		if(row.state == 1){
						//			if(value == 1){
						//				return "成功";
						//			}else{
							//			return "失败";
								//	}
							//	}else{
						//			return "/";
						//		}
						//	}
						//},
						{
							field : "create_time",
							title : "操作时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
								.format("yyyy-MM-dd HH:mm:ss ");
							}
						}
				] ],
				
			});
	}
	
	var loadHzb=function(){
		$('#<%=request.getParameter("rel")%>9_datagrid').datagrid(
				{
			url : "hzb/managerLog/query.do?pid="+pid,
			columns:[[
				{
					field : "managerUserName",
					title : "操作人",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						var v = "";
						if(row.type != 7 && row.type != 8){
							v = value;
						}else{
							v = "本人";
						}
						if(isEmpty(v)){
							v = "系统";
						}
						return v;
					}
				},
				{
					field : "type",
					title : "操作项",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						var v = "";
						switch(value){
							case 1:
								v = "开通互助宝";
								break;
							case 2:
								v = "停用互助宝";
								break;
							case 3:
								v = "启用互助宝";
								break;
							case 4:
								v = "增加互助宝余额";
								break;
							case 5:
								v = "扣除互助宝余额";
								break;
							case 6:
								v = "调整互助宝等级";
								break;
							case 7:
								v = "客户充值";
								break;
							case 8:
								v = "客户消费";
								break;
							case 9:
								v = "订单退款";
								break;
						}
						return v;
					}
				},
				{
					field : "managerTime",
					title : "操作时间",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(value == null || value.length == 0){
							v = "";
						}else{
							v = new Date(value).format("yyyy-MM-dd HH:mm");
						}
						return v;
					}
				},
				{
					field : "managerMoney",
					title : "余额变化",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.type == 1 || row.type == 2 || row.type == 3 || row.type == 6){
							v = "/";
						}else{
							if(row.type == 4 || row.type == 7 || row.type == 9){
								v = "+" + number_format(value,0);
							}else{
								v = "-" + number_format(value,0);
							}
						}
						return v;
					}
				},
				{
					field : "currYe",
					title : "当前余额",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.type == 1 || row.type == 2 || row.type == 3 || row.type == 6){
							v = "/";
						}else{
							v = number_format(value,0);
						}
						return v;
					}
				},
				{
					field : "remark",
					title : "备注",
					width : 300,
					align:"center",
					formatter: function(value,row,index){
						return value;
					}
				}
            ]],
		});
	};
	
	
	function loadHzb22(){
		$('#<%=request.getParameter("rel")%>9_datagrid').datagrid(
				{
			url : "hzb/payLog/query.do?userId="+pid,
			//singleSelect:true,
			//checkOnSelect:true,
			//selectOnCheck:true,
			columns:[[
	            {
			    	field:"chk",
			    	title : "勾选",
			    	checkbox:true
			    },
				{
					field : "fkType",
					title : "付款类型",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						var v = "";
						if(value == 1){
							v = "线上付款";
						}else{
							v = "线下付款";
						}
						return v;
					}
				},
				{
					field : "fkMoney",
					title : "支付金额",
					width : 100,
					align : "center",
					formatter: function(value,row,index){
						return number_format(value,0);
					}
				},
				{
					field : "fkTime",
					title : "付款时间",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(value == null || value.length == 0){
							v = "";
						}else{
							v = new Date(value).format("yyyy-MM-dd HH:mm");
						}
						return v;
					}
				},
				{
					field : "fkPz",
					title : "线下付款凭证",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						if(row.fkType == 1){
							return "/";
						}else{
							//'<a href="ims/pm/general/tixing/view.do?rel=hzbPayLog&id=' + row.id + '" rel="hzbPayLog_tixing_view" title="日常提醒-查看" target="dialog" width="1000" height="500">' + value + '</a>'
							return '<a href="javascript:viewPz(\''+value+'\');">点击查看付款凭证</a>';
						}
					}
				},
				{
					field : "xsFkType",
					title : "线上支付方式",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							if(value == 1){
								v = "微信支付";
							}else if(value == 2){
								v = "财付通";
							}else{
								v = "/";
							}
						}else{
							v = "/";
						}
						return v;
					}
				},
				{
					field : "fkStatus",
					title : "线上支付状态",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							if(value == 0){
								v = "待支付";
							}else if(value == 1){
								v = "支付成功";
							}else if(value == 2){
								v = "支付失败";
							}
						}else{
							v = "/";
						}
						return v;
					}
				},
				{
					field : "shStatus",
					title : "审核状态",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							v = "/";
						}else{
							if(value == 0){
								v = "待审核";
							}else if(value == 1){
								v = "审核通过";
							}else if(value == 2){
								v = "驳回";
							}
						}
						return v;
					}
				},
				{
					field : "shUserName",
					title : "审核人",
					width : 100,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							v = "/";
						}else{
							v = value;
						}
						return v;
					}
				},
				{
					field : "shTime",
					title : "审核时间",
					width : 120,
					align:"center",
					formatter: function(value,row,index){
						var v = "";
						if(row.fkType == 1){
							v = "/";
						}else{
							if(value == null || value.length == 0){
								v = "";
							}else{
								v = new Date(value).format("yyyy-MM-dd HH:mm");
							}
						}
						return v;
					}
				}
            ]],
			onClickRow:function(rowIndex, rowData){
				
			},
			onBeforeLoad:function(rowIndex, rowData){
				///$(".datagrid-header-check").html("");
			}
		});
	};
	
	function viewPz(url){
		var url = "${ossurl}"+url;
		Msg.alert("查看付款凭证",'<img src="'+url+'"/>');
	}
	
	function loadJb(){
		$('#<%=request.getParameter("rel")%>10_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "walletLog/jb_query.do?userId="+pid, 
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "nickname",
							title : "用户姓名",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "amount",
							title : "余额变化",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(!isEmpty(row.pay_status)){
									if(row.pay_status == 1){
										if(row.pd_type == 1){
											return "+"+value;
										}else if(row.pd_type == 2){
											return "-"+value;
										}
									}else{
										return "/";
									}
								}else{
									if(row.pd_type == 1){
										return "+"+value;
									}else if(row.pd_type == 2){
										return "-"+value;
									}
								}
							}
						},
						{
							field : "ye_amount",
							title : "余额",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "pay_status",
							title : "支付状态",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(isEmpty(value)){
									return "成功";
								}else{
									if(value == 1){
										return "成功";
									}else{
										return "失败";
									}
								}
							}
						},
						{
							field : "create_time",
							title : "操作时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
										.format("yyyy-MM-dd HH:mm:ss ");
							}
						}
				] ],
				
			});
	}
	
	function loadRmb(){
		$('#<%=request.getParameter("rel")%>11_datagrid').datagrid(
			{
				border:true,
				nowrap : false,
				url : "walletLog/rmb_query.do?userId="+pid,
				columns : [ [
			             {
						    	field:"ck",
						    	title : "勾选",
						    	checkbox:true
						 },
						{
							field : "nickname",
							title : "用户姓名",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "mobile",
							title : "手机号",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.15,
							align : "center",
						},
						{
							field : "amount",
							title : "余额变化",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(!isEmpty(row.pay_status)){
									if(row.pay_status == 1){
										if(row.pd_type == 1){
											return "+"+value;
										}else if(row.pd_type == 2){
											return "-"+value;
										}
									}else{
										//提现失败的需要显示余额变化
										if(row.business_type == 2){
											if(row.pd_type == 1){
												return "+"+value;
											}else if(row.pd_type == 2){
												return "-"+value;
											}
										}
										return "/";
									}
								}else{
									if(row.pd_type == 1){
										return "+"+value;
									}else if(row.pd_type == 2){
										return "-"+value;
									}
								}
							}
						},
						{
							field : "ye_amount",
							title : "余额",
							width : $(this).width() * 0.15,
							align : "center"
						},
						{
							field : "pay_status",
							title : "支付状态",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								if(isEmpty(value)){
									return "成功";
								}else{
									if(value == 1){
										return "成功";
									}else{
										return "失败";
									}
								}
							}
						},
						{
							field : "create_time",
							title : "操作时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter : function(value, row, index) {
								return new Date(value)
								.format("yyyy-MM-dd HH:mm:ss ");
							}
						}
				] ],
				
			});
	}
	
	function loadEmpiricalLog(){
		$('#<%=request.getParameter("rel")%>12_datagrid').datagrid(
			{
				url : "userEmpiricalLog/query.do?userId="+pid, 
				frozenColumns:[[
					             {
								    	field:"ck",
								    	title : "勾选",
								    	checkbox:true
								 },
								 {
										field : "nickname",
										title : "用户姓名",
										width : 80,
										align : "center"
								 },
								 {
										field : "mobile",
										title : "注册电话",
										width : 100,
										align : "center"
								 },
								 {
										field : "com_name",
										title : "公司名称",
										width : 120,
										align : "center",
								},
								{
									field : "job_name",
									title : "职位",
									width : 80,
									align : "center"
								}  
				            ]],  
				columns : [ [
						{
							field : "province_name",
							title : "地区",
							width : 180,
							align : "center",
							formatter: function(value,row,index){
								if(row.province_name!=null&&row.city_name!=null&&row.county_name!=null){
									return row.province_name+row.city_name+row.county_name;
								}else if(row.province_name!=null&&row.city_name!=null){
									return row.province_name+row.city_name;
								}else if(row.province_name!=null){
									return row.province_name;
								}
							}
						},
						
						{
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.25,
							align : "center"
			
						},
						{
							field : "empirical",
							title : "经验",
							width : $(this).width() * 0.10,
							align : "center",
							formatter : function(value, row, index) {
								if(row.empirical_type == 1){
									return "+"+value;
								}else if(row.empirical_type == 2){
									return "-"+value;
								}else{
									return "+"+value;
								}
							}
			
						},
						{
							field : "ye_empirical",
							title : "经验总量",
							width : $(this).width() * 0.10,
							align : "center"
						},
						{
							field : "level",
							title : "等级",
							width : $(this).width() * 0.10,
							align : "center"
						},
						{
							field : "create_time",
							title : "修改时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								if(null==value){
									return "";
								}else{
									return new Date(value).format("yyyy-MM-dd HH:mm");
								}
								
							}
						}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					//MUI.openDialog('修改','userEmpiricalLog/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
				}
			});
	}
	
	
	function loadIntegralLog(){
		$('#<%=request.getParameter("rel")%>13_datagrid').datagrid(
				{
				url : "userIntegralLog/query.do?userId="+pid,
				frozenColumns:[[
					             {
								    	field:"ck",
								    	title : "勾选",
								    	checkbox:true
								 },
								 {
										field : "nickname",
										title : "用户姓名",
										width : 80,
										align : "center"
								 },
								 {
										field : "mobile",
										title : "注册电话",
										width : 100,
										align : "center"
								 },
								 {
										field : "com_name",
										title : "公司名称",
										width : 120,
										align : "center",
								},
								{
									field : "job_name",
									title : "职位",
									width : 80,
									align : "center"
								}  
				            ]],  
				columns : [ [
						{
							field : "province_name",
							title : "地区",
							width : 180,
							align : "center",
							formatter: function(value,row,index){
								if(row.province_name!=null&&row.city_name!=null&&row.county_name!=null){
									return row.province_name+row.city_name+row.county_name;
								}else if(row.province_name!=null&&row.city_name!=null){
									return row.province_name+row.city_name;
								}else if(row.province_name!=null){
									return row.province_name;
								}
							}
						},
						
						{
							field : "remark",
							title : "操作明细",
							width : $(this).width() * 0.25,
							align : "center"
			
						},
						{
							field : "integral",
							title : "积分",
							width : $(this).width() * 0.10,
							align : "center",
							formatter : function(value, row, index) {
								if(row.integral_type == 1){
									return "+"+value;
								}else if(row.integral_type == 2){
									return "-"+value;
								}else{
									return "+"+value;
								}
							}
			
						},
						{
							field : "ye_integral",
							title : "积分总量",
							width : $(this).width() * 0.10,
							align : "center"
						},
						{
							field : "create_time",
							title : "创建时间",
							width : $(this).width() * 0.15,
							align : "center",
							formatter: function(value,row,index){
								if(null==value){
									return "";
								}else{
									return new Date(value).format("yyyy-MM-dd HH:mm");
								}
								
							}
						}
				] ],
				onDblClickRow:function(rowIndex, rowData){
					//MUI.openDialog('修改','userIntegralLog/updatePage.do?id='+rowData.id+'&rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:1000,height:500});
				},
				onSelect : function(index, row){
					pid = row.id;
				}
			});
	}
	
	
</script>














