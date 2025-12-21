package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatusCodes;
import com.qa.gorest.pojo.UserPOJO;
import com.qa.gorest.utils.StringUtils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class APISchemaValidationTest extends BaseTest {

	
	@BeforeMethod
	public void schemaUserSetUp()
	{
		rs=new RestClient(prop, baseURI);
		
	}
	
	
	@Test
	public void checkSchemaValidationTest()
	{
		UserPOJO up=new UserPOJO("Riyan",StringUtils.generateEmailId(), "male","active");
		
		rs.postRequest(GOREST_ENDPOINT,"json", up, true,true)
		.then().log().all()
		  .assertThat().statusCode(APIHttpStatusCodes.CREATED_201.getCode())
		     .body(matchesJsonSchemaInClasspath("createUser.json"));
		
		
	}
	
	
}
