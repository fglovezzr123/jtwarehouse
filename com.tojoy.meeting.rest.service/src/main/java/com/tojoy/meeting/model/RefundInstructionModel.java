package com.tojoy.meeting.model;

import com.tojoy.meeting.report.ResponseReport;

public class RefundInstructionModel extends ResponseReport
{
	private static final long serialVersionUID = 1L;

	public RefundInstructionModel()
	{
		super();
	}

	public RefundInstructionModel(Byte responseCode)
	{
		super(responseCode);
	}

	private  String instruction;

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
