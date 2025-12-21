package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatusCodes;

public class HttpBinOrgAPITest extends BaseTest {
	
	@BeforeMethod
	public void httpBinOrgUserSetUp()
	{
		rs=new RestClient(prop, baseURI);
		
	}
	
	@Test
	public void getDataFromBinOrg()
	{
		
		rs.getRequest(BINORG_ENDPOINT, true, false)
		  .then().log().all().
		        assertThat().statusCode(APIHttpStatusCodes.OK_200.getCode());
		
	}

}
