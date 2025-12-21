package com.qa.gorest.utils;

public class StringUtils {

	
	public static String generateEmailId()
	{
		String email="apiAutomation"+Math.random()+"@gmail.com";
		return email;
	}
	
}
