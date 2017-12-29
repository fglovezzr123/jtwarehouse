package com.tojoy.business.common.common.report.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CommandInfo
{
	private String commandName;

	private Map data;
}
