package com.sqtoon.socket.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configure
{
	public static String center = "0.0.0.0:7071";

	public Configure()
	{

	}

	public synchronized static void Initialize()
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream("servicesetting.properties");
			Properties properties = new Properties();
			properties.load(fis);
			initialize(properties);
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fis != null)
					fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void initialize(Properties properties)
	{
		center = properties.getProperty("center");
	}
}
