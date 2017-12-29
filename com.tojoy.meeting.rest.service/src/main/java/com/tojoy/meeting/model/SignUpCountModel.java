package com.tojoy.meeting.model;

import com.tojoy.meeting.report.ResponseReport;

public class SignUpCountModel extends ResponseReport
{
	private static final long serialVersionUID = 1L;

	public SignUpCountModel()
	{
		super();
	}

	public SignUpCountModel(Byte responseCode)
	{
		super(responseCode);
	}

	private  long signUpCount;

	public long getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(long signUpCount) {
		this.signUpCount = signUpCount;
	}
}
