<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
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
</style>

<div style="width:100%;">						
<form method="post" action="" id="dataForm">
	<table class="table table-bordered" style="margin-top:0px;width:99%;">
		<tr>
			<th style="width: 15%;vertical-align:middle">项目封面：</th>
			<td colspan=3>
				<div class="hyjb_box" style="margin:0 10px;">
					<ul class="hyjb_dl">
				    	<li>
				    		<dl>
				            	<dt>
				            		<a href="javascript:void(0)">
		            			<c:choose>
			            			<c:when test="${not empty obj.coverImg and obj.coverImg!=''}">
				            			<img status="1" style="width:100%;height:100%;" 
				            				src="${obj.coverImg}" id="hb"/>
			            			</c:when>
			            			<c:otherwise>
				            			<img status="0" style="width:100%;height:100%;" 
				            				src="../../resource/images/tjy/djsc.png" id="hb"/>
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
			<th style="width:15%;">项目标题：</th>
			<td colspan=3>${obj.titles}</td>
		</tr>
		<tr>
			<th style="width:15%;">项目名称2：</th>
			<td colspan=3>${obj.titles2}</td>
		</tr>
		<tr>
			<th style="width:15%;">项目类别：</th>
			<td>
				<c:if test="${8006001==obj.prjType}">项目联营</c:if>
				<c:if test="${8006002==obj.prjType}">项目融资</c:if>
			</td>
			<th>融资主体：</th>
			<td>${obj.financingSubject}</td>
		</tr>
		<tr>
			<th>融资金额：</th>
			<td><c:if test="${not empty obj.financingAmount}">${fns:fixed(obj.financingAmount)}</c:if> 万元</td>
			<th>融资方式：</th>
			<td>${obj.financingType}</td>
		</tr>
		<tr>
			<th>融资用途：</th>
			<td colspan="3">${obj.financingPurpose}</td>
		</tr>
		<tr>
			<th>所在地点：</th>
			<td>${obj.region}</td>
			<th>所在行业：</th>
			<td>${obj.industry}</td>
		</tr>
		<tr>
			<th>联系电话：</th>
			<td>${obj.mobiles}</td>
			<th>视频ID：</th>
			<td>${obj.webinarId}</td>
		</tr>
		<tr>
			<th>可提供资料：</th>
			<td colspan=3>${obj.availableData}</td>
		</tr>
		<tr>
			<th>项目时间：</th>
			<td colspan=3>${fns:fmt(obj.startTime,'yyyy-MM-dd')}~${fns:fmt(obj.endTime,'yyyy-MM-dd')}</td>
		</tr>
		<tr>
			<th style="width:120px;">是否推荐：</th>
			<td style="width:120px;">
				<c:if test="${0==obj.isRecommend}">否</c:if>
				<c:if test="${1==obj.isRecommend}">是</c:if>
			</td>
			<th style="width:120px;">是否显示：</th>
			<td style="width:120px;">
				<c:if test="${0==obj.showEnable}">否</c:if>
				<c:if test="${1==obj.showEnable}">是</c:if>
			</td>
		</tr>
		<tr>
			<th>项目介绍：</th>
			<td colspan=3>${obj.prjPres}</td>
		</tr>
		<tr>
			<th>项目详细：</th>
			<td colspan=3>
				<textarea id="prjmd_myEditor" name="prjDesc" style="width:700px;height:100px;">${obj.prjDesc}</textarea>
			</td>
		</tr>
		<tr>
			<th>项目图片：</th>
			<td colspan=3>
				<div class="hyjb_box">
    				<div class="hyjb_tj">
   					<c:if test="${not empty obj}">
   						<c:forEach items="${obj.projectImages}" var="item">
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
</form>
<span class="typefile" id="filePicker"></span>
</div>
<script>

var options = {
		cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'qfy/entry/upload.do?ysStyle=YS640',
		width : '320px',
		height:'600px',
		resizeType : 1,
		allowImageUpload : true,
		allowFlashUpload : false,
		allowMediaUpload : false,
		allowFileManager : false,
		syncType:"form",
		afterCreate : function() {
							var self = this;
							self.sync();
						},
		afterChange : function() {
					var self = this;
					self.sync();
		},
		afterBlur : function() {
							var self = this;
							self.sync();
						},
						items:['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
								'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
								'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
								'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
								'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
								'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about']
};
$(function(){
	editor = KindEditor.create('#prjmd_myEditor',options);
})


</script>