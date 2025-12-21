package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLPathValidator {
	
	
	private String getXMLResponseAsString(Response res)
	{
		String response=res.getBody().asString();
	     return response;
	}
	
	public <T> T readXML(Response res, String path)
	{
		String response=getXMLResponseAsString(res);
		XmlPath xml=new XmlPath(response);
		
		return xml.get(path);
		
	}
	
	public <T> List<T> readXMLList(Response res, String path)
	{
		String response=getXMLResponseAsString(res);
		XmlPath xml=new XmlPath(response);
		
		return xml.getList(path);
		
	}

}
