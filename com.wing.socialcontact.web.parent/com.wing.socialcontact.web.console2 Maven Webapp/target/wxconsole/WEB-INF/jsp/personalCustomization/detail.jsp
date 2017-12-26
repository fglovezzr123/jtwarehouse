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
	<form  id="dataForm">


		<table class="table table-bordered" style="margin-top:0px;width:99%;">

			<tr>
				<th style="width:15%;">服务标题：</th>
				<td colspan=3>
					${obj.title}
				</td>
			</tr>
			<tr>
				<th>权重：</th>
				<td>
					${obj.weight}
				</td>
			</tr>
			<tr>
				<th>服务内容：</th>
				<td colspan=3>
					<textarea id="content"  name="content" style="width:700px;height:100px;">${obj.content}</textarea>
				</td>
			</tr>
			<tr>
				<th style="width: 15%;vertical-align:middle">服务封面：</th>
				<td colspan=3>
					<div class="hyjb_box"  style="margin:0 10px;height:110px;">
						<ul class="hyjb_dl" style="margin:0px;">
							<li>
								<dl style="margin:0px;">
									<dt>
										<a href="javascript:void(0)">
											<c:choose>
												<c:when test="${not empty obj.imageUrl and obj.imageUrl!=''}">
													<img status="1" style="width:100%;height:100%;" name = "imageUrl"
														 src="${obj.imageUrl}" id="hb"/>
												</c:when>
												<c:otherwise>
													<img status="0" style="width:100%;height:100%;"
														 src="${path}/resource/images/tjy/djsc.png" name = "imageUrl" id="hb" onclick="uploadImg('hb')" />
												</c:otherwise>
											</c:choose>
										</a>
									</dt>
									<dd>

									</dd>
								</dl>
							</li>
						</ul>
					</div>
				</td>
			</tr>
			<tr>
				<th>视频：</th>
				<td>
					${obj.webinarSubject}
				</td>
			</tr>
			<tr>
				<th style="width: 100px">上传音频文件：</th>
				<td>
					<div id= "deladbutton">
						<c:if test="${obj.voiceUrl!=null && obj.voiceUrl!=''}">
							<video  controls="controls" width="320" height="50">
								<source src="${obj.voiceUrl}" />
							</video>

						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<th>是否显示：</th>
				<td>
					<c:if test="${0==obj.isShow}">否</c:if>
					<c:if test="${1==obj.isShow}">是</c:if>
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
	    uploadJson:'news/upload.do?ysStyle=YS640&moduleName=personalCustomization',
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
	editor = KindEditor.create('#content',options);
})


</script>