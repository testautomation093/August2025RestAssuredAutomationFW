package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatusCodes;
import com.qa.gorest.utils.XMLPathValidator;

import io.restassured.response.Response;

public class GetUserTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetUp()
	{
		rs=new RestClient(prop, baseURI);
		
	}
	
		
	@Test(priority = 3, description="This test case is all about Fetch All User Details")
	public void getAllUserTest()
	{
		
		rs.getRequest(GOREST_ENDPOINT, true, true)
	     .then().log().all()
	       .assertThat().statusCode(APIHttpStatusCodes.OK_200.getCode());
	
	}
	
	@Test(priority = 2)
	public void getSingleUser()
	{
		
		rs.getRequest(GOREST_ENDPOINT+8272253, true, true)
		  .then().log().all()
		     .assertThat()
		        .statusCode(APIHttpStatusCodes.OK_200.getCode())
		          .and()
		            .assertThat()
		              .body("id", equalTo(8272253))
		                .and()
		                  .body("name",equalTo("Manish"));
		
		
	}
	
	@Test(priority = 1)
	public void getUserWithQueryParam()
	{
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "inactive");
		map.put("gender", "male");
		
		rs.getRequest(GOREST_ENDPOINT, null, map, true, true)
		  .then().log().all()
		    .assertThat().statusCode(APIHttpStatusCodes.OK_200.getCode());
		
	}
	
	
	@Test (priority=4, enabled=false)
	public void getAllUsersXMLTest()
	{
		Response res=rs.getRequest(GOREST_ENDPOINT_XML, true, true);
		int actStatus=res.statusCode();
		
		Assert.assertEquals(actStatus, APIHttpStatusCodes.OK_200.getCode());
		
		XMLPathValidator xml=new XMLPathValidator();
		
		List<Object> xmlNameList = xml.readXMLList(res, "objects.object.name");
		
		System.out.println("Name List is : "+xmlNameList);
		
		Assert.assertTrue(xmlNameList.contains("Aparna"));
		
	}
	
	
	
	
	

}
