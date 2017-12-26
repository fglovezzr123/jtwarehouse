<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<div id="cc" class="easyui-layout" data-options="fit:true">
	<div id="center" data-options="region:'center',title:'',split:true" style="" border="false">
		<div style="width: 95%; margin: 0 auto;">
			<form method="post" onsubmit="return validateSubmitForm(this)">
				<table class="table table-bordered ">
						
					<tr>
						<th width="15%">
							模板描述：
						</th>
						<td width="85%">
							${dto.info}
						</td>
					</tr>
					<tr>
						<th width="15%">
							模板标题：
						</th>
						<td width="85%">
							${dto.title}
						</td>
					</tr>
					<tr>
						<th width="15%">
							模板标识：
						</th>
						<td width="85%">
							${dto.mark}
						</td>
					</tr>
					<tr>
						<th>
							模板类型：
						</th>
						<td>
		
							<select class="combox required" class="easyui-validatebox" required="true" name="type" sValue="${dto.type }">
									<option value="1">手机短信模板</option>
									<option value="2">微信模板</option>
							</select>
		
						</td>
					</tr>
					<tr>
						<th>
							内容：
						</th>
						<td >
							<textarea class="editor" name="content" rows="6" cols="100" style="width: 98%;"><c:out value="${dto.content}"/></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>	
	
</div>			
<script type="text/javascript">	

</script>