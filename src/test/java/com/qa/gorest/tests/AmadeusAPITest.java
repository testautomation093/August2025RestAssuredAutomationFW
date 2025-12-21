package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatusCodes;

import static org.hamcrest.Matchers.*;

public class AmadeusAPITest extends BaseTest {

	public String token;
	@Parameters({"grantType","clientId","clientSecret"})
	@BeforeMethod
	public void getAccessTokenSetup(String grantType, String clientId, String clientSecret)
	{
		rs=new RestClient(prop, baseURI);
		token=rs.getAmadeusOAuth2TokenGeneration(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
	
	}
	
	@Test
	public void getFlightDetails()
	{
		RestClient rest2=new RestClient(prop, baseURI);
		
		Map<String, String> headersMap=new HashMap<String, String>();
		headersMap.put("Authorization", "Bearer " +token);
		
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("origin", "PAR");
		queryMap.put("maxPrice","200");
		
		rest2.getRequest(AMADEUS_FLIGHTBOOKING_ENDPOINT, headersMap, queryMap, true, false)
		    .then().log().all()
		     .assertThat()
		       .statusCode(APIHttpStatusCodes.OK_200.getCode())
		         .and()
		            .body("data[0].price.total",equalTo("110.0"));
	
		
	}
	
	
	
	
	
}
