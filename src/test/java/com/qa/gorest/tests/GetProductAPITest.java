package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatusCodes;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

public class GetProductAPITest extends BaseTest {
	
	
	@BeforeMethod
	public void getProductSetUp()
	{
		rs=new RestClient(prop, baseURI);
		
	}
	
	@Test
	public void getAllProductInfoTest()
	{
		Response response=rs.getRequest(FAKESTORE_PRODUCT_ENDPOINT, true, false);
		
		int statusCode=response.statusCode();
		
		Assert.assertEquals(statusCode, APIHttpStatusCodes.OK_200.getCode());
		
		JsonPathValidator js=new JsonPathValidator();
		List<Float> rateList=js.readJsonList(response, "$..[?(@.rate>3)].rate");
		
		System.out.println("Rate List is : "+rateList);
		
		Assert.assertTrue(rateList.contains(3.9));
		
		List<String> titleList=js.readJsonList(response, "$..[?(@.price>100)].[\"title\"]");
		
		System.out.println("Title and Category List is : "+titleList);
		
		Assert.assertTrue(titleList.contains("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops"));

		
	}
	
	

}
