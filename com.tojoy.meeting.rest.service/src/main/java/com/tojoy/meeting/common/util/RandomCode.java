package com.tojoy.meeting.common.util;

import java.util.Random;

public class RandomCode
{
	private static String a = "0,1,2,3,4,5,6,7,8,9";
	private static String[] rr = a.split(",");

	public static String getRandomCode(int position)
	{
		StringBuffer b = new StringBuffer();
		int k;
		for (int i = 0; i < position; i++)
		{
			Random r = new Random();
			k = r.nextInt();
			b.append(String.valueOf(rr[Math.abs(k % 10)]));
		}
		return b.toString();
	}

}
