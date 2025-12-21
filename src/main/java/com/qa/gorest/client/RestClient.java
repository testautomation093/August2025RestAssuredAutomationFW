package com.qa.gorest.client;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.constants.APIHttpStatusCodes;
import com.qa.gorest.frameworkExceptions.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

//	private static final String BASE_URI="https://gorest.co.in";
//	private static final String BEARER_TOKEN="Bearer 5eacf1500a6f560ce889fce62cb41c691cb38ed175df154db2310d1663f856e1";

	private static RequestSpecBuilder specBuilder;
	private Properties prop;
	private String baseURI;
	
	private boolean isAuthorizationAdded=false;
	
	
	public RestClient(Properties prop, String baseURI)
	{
		specBuilder=new RequestSpecBuilder();
		this.prop =prop;
		this.baseURI=baseURI;
		
	}
	
// ******************Common Re-Usuable Methods *********************
	
	public void addAuthorization()
	{
		if(!isAuthorizationAdded)
		{
		specBuilder.addHeader("Authorization",prop.getProperty("tokenId"));
		isAuthorizationAdded=true;
		}
	}
	
	public void setRequestContentType(String contentType)
	{
		System.out.println("Content type is : " +contentType);
		
		switch (contentType.toLowerCase().trim()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
			
		case "text":
			
			specBuilder.setContentType(ContentType.TEXT);
			break;
			
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
			
		case "html":
			specBuilder.setContentType(ContentType.HTML);
			break;
			
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;

		default:
			System.out.println("Content Type is not supported");
			
			throw new APIFrameworkException("UNSUPPORTED CONTENT TYPE");
		}
		
		
	}
	
	
	
// ****************** Request Specification Methods*****************	
	
	
	public RequestSpecification createRequestSpec(boolean includeAuth)
	{
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}
		return specBuilder.build();
		
	}
	
	/**
	 * When the method expects some Headers map along with baseURI and authorization
	 * This is my over loaded method
	 * @param headersMap
	 * @return
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap,boolean includeAuth)
	{
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}		if(headersMap!=null)
		{
		specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
		
	}
	
	/**
	 * When the method expects some Headers map and map of query params along with baseURI and authorization
	 * This is my over loaded method
	 * @param headersMap
	 * @return
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryMap, boolean includeAuth)
	{
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}		if(headersMap!=null)
		{
		specBuilder.addHeaders(headersMap);
		}
		if(queryMap!=null)
		{
		specBuilder.addQueryParams(queryMap);
		}
		return specBuilder.build();
		
	}
	
	/**
	 * This method will mostly take care of the Post request with Body data and body type
	 * @param requestBody
	 * @param contentType
	 * @return
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth)
	{
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}		setRequestContentType(contentType);
		if(requestBody!=null)
		{
		specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
		
	}
	
	/**
	 * This method will mostly take care of the Post request with Body data and body type along with headers Map.
	 * @param requestBody
	 * @param contentType
	 * @return
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth)
	{
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}		setRequestContentType(contentType);
		if(requestBody!=null)
		{
		specBuilder.setBody(requestBody);
		}
		if(headersMap!=null)
		{
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
		
	}
	
	
	/**
	 * This method will mostly take care of the Post request with Body data 
	 * and body type along with headers Map and some query Parameters.
	 * @param requestBody
	 * @param contentType
	 * @return
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, Map<String, String> queryMap, boolean includeAuth)
	{
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}		setRequestContentType(contentType);
		if(requestBody!=null)
		{
		specBuilder.setBody(requestBody);
		}
		if(headersMap!=null)
		{
			specBuilder.addHeaders(headersMap);
		}
		if(queryMap!=null)
		{
			specBuilder.addQueryParams(queryMap);
		}
		return specBuilder.build();
		
	}

// ********Common HTTP GET Methods which will be called directly in test cases**********************	
	
	/**
	 * Only Plain Get call with service Url
	 * @param serviceUrl
	 * @param log
	 * @return
	 */
	public Response getRequest(String serviceUrl, boolean log, boolean includeAuth)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()  
		      .spec(createRequestSpec(includeAuth))
		      .when()
		      .get(serviceUrl);
		
		return res;
		
		}
		else
		{
			Response res=RestAssured.given() 
				      .spec(createRequestSpec(includeAuth))
				      .when()
				      .get(serviceUrl);
				
				return res;
	
		}
		
	}
	
	/**
	 * Over loading this method with get call for passing service url and some headers.
	 * @param serviceUrl
	 * @param headersMap
	 * @param log
	 * @return
	 */
	public Response getRequest(String serviceUrl,Map<String, String> headersMap, boolean log, boolean includeAuth)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()  
		      .spec(createRequestSpec(headersMap, includeAuth))
		      .when()
		      .get(serviceUrl);
		
		return res;
		
		}
		else
		{
			Response res=RestAssured.given() 
				      .spec(createRequestSpec(headersMap, includeAuth))
				      .when()
				      .get(serviceUrl);
				
				return res;
	
		}
		
	}
	
	/**
	 * Over loaded Get call with service Url, Headers map and query maps.
	 * @param serviceUrl
	 * @param headersMap
	 * @param queryMap
	 * @param log
	 * @return
	 */
	public Response getRequest(String serviceUrl,Map<String, String> headersMap, Map<String, String> queryMap,boolean log, boolean includeAuth)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()  
		      .spec(createRequestSpec(headersMap, queryMap, includeAuth))
		      .when()
		      .get(serviceUrl);
		
		return res;
		
		}
		else
		{
			Response res=RestAssured.given() 
				      .spec(createRequestSpec(headersMap, queryMap, includeAuth))
				      .when()
				      .get(serviceUrl);
				
				return res;
	
		}
		
	}	
	
//********************HTTP Post method to be called via Test Cases************
	
	public Response postRequest(String serviceUrl,String contentType, Object requestBody, boolean log, boolean includeAuth)
	{
		
		if(log)
		{
			
			Response res=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, includeAuth))
			   .when()
			    .post(serviceUrl);
			
			return res;
			
		}
		
		else
		{
			
			Response res=RestAssured.given()
						  .spec(createRequestSpec(requestBody, contentType, includeAuth))
						   .when()
						    .post(serviceUrl);
						
			return res;
			
		}

	}
	
	public Response postRequest(String serviceUrl,String contentType, Object requestBody,Map<String, String> headersMap, boolean log, boolean includeAuth)
	{
		
		if(log)
		{
			
			Response res=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
			   .when()
			    .post(serviceUrl);
			
			return res;
			
		}
		
		else
		{
			
			Response res=RestAssured.given()
						  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
						   .when()
						    .post(serviceUrl);
						
			return res;
			
		}
	
	}
	
	public Response postRequest(String serviceUrl,String contentType, Object requestBody,Map<String, String> headersMap, Map<String, String> queryMap,boolean log, boolean includeAuth)
	{
		
		if(log)
		{
			
			Response res=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
			   .when()
			    .post(serviceUrl);
			
			return res;
			
		}
		
		else
		{
			
			Response res=RestAssured.given()
						  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
						   .when()
						    .post(serviceUrl);
						
			return res;
			
		}
	
	}	
	
//*****************HTTP PUT method for methods to be called via Test Cases******************************
	
	public Response putRequest(String serviceUrl,String contentType, Object requestBody, boolean log, boolean includeAuth)
	{
		
		if(log)
		{
			
			Response res=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, includeAuth))
			   .when()
			    .put(serviceUrl);
			
			return res;
			
		}
		
		else
		{
			
			Response res=RestAssured.given()
						  .spec(createRequestSpec(requestBody, contentType, includeAuth))
						   .when()
						    .put(serviceUrl);
						
			return res;
			
		}

	}
	
	public Response putRequest(String serviceUrl,String contentType, Object requestBody,Map<String, String> headersMap, boolean log, boolean includeAuth)
	{
		
		if(log)
		{
			
			Response res=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
			   .when()
			    .put(serviceUrl);
			
			return res;
			
		}
		
		else
		{
			
			Response res=RestAssured.given()
						  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
						   .when()
						    .put(serviceUrl);
						
			return res;
			
		}
	
	}
	
	public Response putRequest(String serviceUrl,String contentType, Object requestBody,Map<String, String> headersMap, Map<String, String> queryMap,boolean log, boolean includeAuth)
	{
		
		if(log)
		{
			
			Response res=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
			   .when()
			    .put(serviceUrl);
			
			return res;
			
		}
		
		else
		{
			
			Response res=RestAssured.given()
						  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
						   .when()
						    .put(serviceUrl);
						
			return res;
			
		}
	
	}	
	
	
//*****************HTTP PATCH method for methods to be called via Test Cases******************************
	
		public Response patchRequest(String serviceUrl,String contentType, Object requestBody, boolean log, boolean includeAuth)
		{
			
			if(log)
			{
				
				Response res=RestAssured.given().log().all()
				  .spec(createRequestSpec(requestBody, contentType, includeAuth))
				   .when()
				    .patch(serviceUrl);
				
				return res;
				
			}
			
			else
			{
				
				Response res=RestAssured.given()
							  .spec(createRequestSpec(requestBody, contentType, includeAuth))
							   .when()
							    .patch(serviceUrl);
							
				return res;
				
			}

		}
		
		public Response patchRequest(String serviceUrl,String contentType, Object requestBody,Map<String, String> headersMap, boolean log, boolean includeAuth)
		{
			
			if(log)
			{
				
				Response res=RestAssured.given().log().all()
				  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
				   .when()
				    .patch(serviceUrl);
				
				return res;
				
			}
			
			else
			{
				
				Response res=RestAssured.given()
							  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							   .when()
							    .patch(serviceUrl);
							
				return res;
				
			}
		
		}
		
		public Response patchRequest(String serviceUrl,String contentType, Object requestBody,Map<String, String> headersMap, Map<String, String> queryMap,boolean log, boolean includeAuth)
		{
			
			if(log)
			{
				
				Response res=RestAssured.given().log().all()
				  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
				   .when()
				    .patch(serviceUrl);
				
				return res;
				
			}
			
			else
			{
				
				Response res=RestAssured.given()
							  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
							   .when()
							    .patch(serviceUrl);
							
				return res;
				
			}
		
		}	

		
//***************************HTTP Delete Method********************************
		
	public Response deleteRequest(String serviceUrl, boolean log, boolean includeAuth)	
	{
		if(log)
		{
			Response res=RestAssured.given().log().all()
			     .spec(createRequestSpec(includeAuth))
				    .when()
				     .delete(serviceUrl);
			
			return res;
				    
		}
		
		else
		{
			Response res=RestAssured.given()
			     .spec(createRequestSpec(includeAuth))
				    .when()
				     .delete(serviceUrl);
			
			return res;
				    
		}
		
	}

//*********************OAuth 2.0 Token Generation Common Method*******************
	
	public String getAmadeusOAuth2TokenGeneration(String serviceUrl, String grantType, String clientId, String clientSecret)
	{
		
		RestAssured.baseURI="https://test.api.amadeus.com";
		
		String authToken=given().log().all()
		   .contentType(ContentType.URLENC)
		      .formParam("grant_type", grantType)
		       .formParam("client_id", clientId)
		        .formParam("client_secret",clientSecret)
		          .when()
		            .post(serviceUrl)
		              .then().log().all()
		                 .assertThat() .statusCode(APIHttpStatusCodes.OK_200.getCode())
		                   .extract().path("access_token");
		              
		
	System.out.println("OAuth 2.0 Token is : "+authToken);	
	
	return authToken;
	
	
	
	}
}

