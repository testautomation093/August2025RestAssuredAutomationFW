package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatusCodes;
import com.qa.gorest.pojo.UserPOJO;
import com.qa.gorest.utils.ExcelUtils;
import com.qa.gorest.utils.StringUtils;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {
	
//	RestClient resPost;
//	RestClient resGet;
	
	@BeforeMethod
	public void createUserSetUp()
	{
		rs=new RestClient(prop, baseURI);
		
	}
	
//	@DataProvider
//	public Object[][] getUserTestData()
//	{
//		Object obj[][]= {{"Mohan","male","active"},{"Meena","female","inactive"},{"Ayush","male","active"}};
//		
//		return obj;
//
//	}
	
	@DataProvider
	public Object getUserTestSheetData()
	{
		Object obj[][]=ExcelUtils.getTestDataFromSheet(APIConstants.TEST_SHEET_NAME);
		return obj;
		
	}
	
	@Test(dataProvider = "getUserTestSheetData")
	public void createUserTest(String name, String gender, String status)
	{
		
	// 1. Create a User Test	
		UserPOJO up=new UserPOJO(name, StringUtils.generateEmailId(), gender, status);
		
		//resPost=new RestClient();
		
		Integer userid=rs.postRequest(GOREST_ENDPOINT, "json", up, true, true)
		   .then().log().all()
		      .assertThat().statusCode(APIHttpStatusCodes.CREATED_201.getCode())
		        .extract().path("id");
		
		System.out.println("User id is : "+userid);
		
		
	// 2. Fetch the Created User Details :
		
		//resGet=new RestClient();
		RestClient resGet=new RestClient(prop, baseURI);
		resGet.getRequest(GOREST_ENDPOINT+userid, true, true)
		  .then().log().all()
		    .assertThat().statusCode(APIHttpStatusCodes.OK_200.getCode())
		      .body("id", equalTo(userid));
		
	}	
	

}
