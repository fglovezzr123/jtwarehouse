package com.tojoy.business.common.common.report;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class BaseObject implements Serializable
{

	private static final long serialVersionUID = -1913147427843546255L;

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean equals(Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
