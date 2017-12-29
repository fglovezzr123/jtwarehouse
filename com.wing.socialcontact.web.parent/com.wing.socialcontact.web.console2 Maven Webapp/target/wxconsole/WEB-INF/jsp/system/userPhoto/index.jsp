<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<div id="toolbar" class="search-div">
    <div class="search-content">
        <input type="hidden" name="ossUrl" id="ossUrl" value="${ossUrl}"/>
		<span>
			<label style="width: 80px;">用户名称：</label>
			<input type="text" id="trueName" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">审核状态：</label>
			<select id="status" style="width:70px;">
                <option value="">全部</option>
                <option value="1">通过</option>
                <option value="0">未通过</option>
            </select>
		</span>
		<span>
			<label style="width: 100px;">创建时间：</label>
			<input id="createTime" style="width:90px;" size=15 class="Wdate time-field"
                   onfocus="WdatePicker({readOnly:true})"
                   onClick="WdatePicker({readOnly:true})"
                   readonly="readonly"/>
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="load()">查询</button>&nbsp;
		</span>
        <%--<span style="padding-left:20px;">
            <button class="btn btn-primary btn-small" type="button" onclick="deleteData()">刪除</button>&nbsp;
        </span>--%>
    </div>
</div>
<table id="dg" toolbar="#toolbar"></table>
<script type="text/javascript">

    //审核
    function examineVerify(fid) {
        confirmx("确定操作吗？", function (s) {
            if (s == 1) {
                $.ajax({
                    url: "userPhoto/examineVerify.do",
                    type: 'post',
                    data: {id: fid},
                    cache: false,
                    dataType: "json",
                    success: function (json) {
                        if (json && json["code"] === "0") {
                            $("#dg").datagrid('reload');
                        } else {
                            alert("操作失败");
                        }
                    }
                });
            }
        })
    }

    $(function () {
        load();
    });
    function load() {
        $("#dg").datagrid({
            url: "userPhoto/queryUserPhotoList.do",
            rownumbers: true,  //行号
            /*		singleSelect: false, //允许选择多行
             selectOnCheck: true,//true勾选会选择行，false勾选不选择行, 1.3以后有此选项。重点在这里
             checkOnSelect: false, //true选择行勾选，false选择行不勾选, 1.3以后有此选项*/
            queryParams: {
                trueName: $("#trueName").val(),
                status: $("#status").val(),
                createTime: $("#createTime").val()
            },
            fitColumns: false,
            columns: [[
                /*{field:'ck',checkbox:true},*/
                {
                    field: "trueName",
                    title: "姓名",
                    width: 150,
                    align: "center"
                },
                {
                    field: 'photoUrl',
                    title: '照片',
                    align: 'center',
                    width: 250,
                    formatter: function (value, row) {
                        var str = "";
                        if (value != "" || value != null) {
                            var ossUrl =$("#ossUrl").val();
                            str = "<img style=\"height: 80px;width: 150px;\" src=\"" + ossUrl +value + "\"/>";
                            return str;
                        }
                    }
                },
                {
                    field: "createTime",
                    title: "提交时间",
                    width: 150,
                    align: "center",
                    formatter: function (value, row, index) {
                        return value == null ? "" : new Date(value).format("yyyy-MM-dd HH:mm");
                    }
                },
                {
                    field: "status",
                    title: "审核状态",
                    width: 100,
                    align: "center",
                    formatter: function (value, row, index) {
                        return 1 == value ? "通过" : "未通过";
                    }
                },
                {
                    field: "op",
                    title: "操作",
                    align: "center",
                    align: "center",
                    width: 240,
                    formatter: function (value, row, index) {
                        if (row.status == 1) {
                            return  "<a href = 'javaScript:void(0)' onclick = examineVerify('" + row.id + "')>不通过</a>|"
                            + "<a href = 'javaScript:void(0)' onclick = view('" + row.id + "')>查看</a>";
                        } else {
                            return  "<a href = 'javaScript:void(0)' onclick = examineVerify('" + row.id + "')>通过</a>|"
                            + "<a href = 'javaScript:void(0)' onclick = view('" + row.id + "')>查看</a>";
                            ;
                        }

                    }
                }
            ]],
        });
    }

    //详细
    function view(fid) {
        var params = {
            closed: false,
            cache: false,
            modal: true,
            width: 700,
            height: 650,
            collapsible: false,
            minimizable: false,
            maximizable: false
        };
        MUI.openDialog('详细', 'userPhoto/detail.do?id=' + fid, "detail", params)
    }
</script>