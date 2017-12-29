<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<div id="toolbar" class="search-div">
    <div class="search-content">
        <span>
			<label style="width: 80px;">表(表名)：</label>
			<input type="text" id="tableName" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">操作用户：</label>
			<input type="text" id="trueName" style="width:120px;"/>
		</span>
		<span>
			<label style="width: 80px;">操作类型：</label>
			<select id="type" style="width:70px;">
                <option value="">全部</option>
                <option value="1">新增</option>
                <option value="2">更新</option>
                <option value="3">删除</option>
            </select>
		</span>
		<span>
			<label style="width: 100px;">操作时间：</label>
			<input id="createTime" style="width:90px;" size=15 class="Wdate time-field"
                   onfocus="WdatePicker({readOnly:true})"
                   onClick="WdatePicker({readOnly:true})"
                   readonly="readonly"/>
		</span>
		<span style="padding-left:20px;">
			<button class="btn btn-primary btn-small" type="button" onclick="loadLog()">查询</button>&nbsp;
		</span>
    </div>
</div>
<table id="dg" toolbar="#toolbar"></table>
<script type="text/javascript">

    $(function () {
        loadLog();
    });
    function loadLog() {
        $("#dg").datagrid({
            url: "tbLog/queryTbLogList.do",
            rownumbers: true,  //行号
            queryParams: {
                tableName: $("#tableName").val(),
                trueName: $("#trueName").val(),
                type: $("#type").val(),
                createTime: $("#createTime").val()
            },
            fitColumns: false,
            columns: [[
                {
                    field: "tableName",
                    title: "表",
                    width: 150,
                    align: "center"
                },
                {
                    field: "comment",
                    title: "表名",
                    width: 150,
                    align: "center"
                },
                {
                    field: "createTime",
                    title: "操作时间",
                    width: 150,
                    align: "center",
                    formatter: function (value, row, index) {
                        return value == null ? "" : new Date(value).format("yyyy-MM-dd HH:mm");
                    }
                },
                {
                    field: "type",
                    title: "操作类型",
                    width: 100,
                    align: "center",
                    formatter: function (value, row, index) {
                        if(value == 1 ){
                            return "新建";
                        }else if(value ==2){
                            return "更新";
                        }else{
                            return "删除";
                        }
                    }
                },
                {
                    field: "trueName",
                    title: "操作人",
                    width: 150,
                    align: "center"
                },
                {
                    field: "op",
                    title: "操作",
                    align: "center",
                    align: "center",
                    width: 240,
                    formatter: function (value, row, index) {
                        return  "<a href = 'javaScript:void(0)' onclick = view('" + row.id + "')>查看详细</a>";
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
            width: 850,
            height: 600,
            collapsible: false,
            minimizable: false,
            maximizable: false
        };
        MUI.openDialog('详细', 'tbLog/detail.do?id=' + fid, "detail", params)
    }

</script>