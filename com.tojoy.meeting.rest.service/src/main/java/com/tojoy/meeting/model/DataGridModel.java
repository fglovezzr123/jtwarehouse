package com.tojoy.meeting.model;

import com.tojoy.common.model.DataGrid;
import com.tojoy.service.wx.bean.Meeting;

import java.util.List;

public class DataGridModel
{
	private static final long serialVersionUID = 1L;

	private DataGrid dataGrid;

	public DataGrid getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(com.tojoy.common.model.DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}
}
