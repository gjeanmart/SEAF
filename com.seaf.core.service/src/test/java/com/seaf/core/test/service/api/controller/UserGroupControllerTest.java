/*package com.seaf.core.test.service.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaf.core.service.business.UserGroupService;
import com.seaf.core.service.model.UserDto;
import com.seaf.core.test.utils.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/spring-config.test.xml" })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserGroupControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupControllerTest.class);
	
	private static final String HOST 				= "localhost";
	private static final int 	PORT 				= 8080;
	private static final String APPLICATION_ID 		= "com.seaf.core.service"; //
	private static final String CONTEXT_PATH 		= "/" + APPLICATION_ID;
	private static final String USERNAME 			= "admin";
	private static final String PASSWORD 			= "secret";

	private static Server 		server;
	private static String 		baseUrl;
	private ObjectMapper 		mapper = new ObjectMapper();
	private static RestTemplate template = null;
	private static HttpHeaders	headers = null;
	
	@Autowired
	private UserGroupService userGroupService;

	@BeforeClass
	public static void startJetty() throws Exception {
        
		// Start JETTY
		WebAppContext  context = new WebAppContext ("src/main/webapp", CONTEXT_PATH);
        context.setResourceBase("src/main/webapp");
        server = new Server(PORT);
        server.setHandler(context);
        server.start();
	        
		int actualPort = ((ServerConnector) server.getConnectors()[0]).getLocalPort();
		baseUrl = "http://" + HOST + ":" + actualPort + CONTEXT_PATH + "/api/v1/";
		LOGGER.info(" ********************** BASE URL = " + baseUrl);
		
		// BASIC AUTH
		BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
		ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);

		template = new RestTemplate(rf);
		
		
		// HEADER
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	

	@AfterClass
	public static void stopJetty() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@After
	public void tearDown() throws UserGroupException {
		for (UserDto user : (List<UserDto>)userGroupService.getUsers(null, UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE,null,null).getData()) {
			userGroupService.deleteUser(user.getId());
		}

		for (GroupDto group : (List<GroupDto>)userGroupService.getGroups(null, UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE, null, null).getData()) {
			userGroupService.deleteGroup(group.getId());
		}
	}

	@Test
	public void createUserTest() throws JsonProcessingException {
		UserDto user = new UserDto();
		user.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user.setLastName(UtilsTest.USER1_LASTNAME);
		user.setEmail(UtilsTest.USER1_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(mapper.writeValueAsString(user), headers);

		ResponseEntity<UserDto> entity = template.postForEntity(baseUrl + "user", requestEntity, UserDto.class);
		UserDto userInserted = entity.getBody();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertEquals(UtilsTest.USER1_EMAIL, userInserted.getEmail());
		assertEquals(UtilsTest.USER1_FIRSTNAME, userInserted.getFirstName());

		HttpEntity<String> requestEntity2 = new HttpEntity<String>(headers);
		ResponseEntity<UserDto> entity2 = template.exchange(baseUrl + "user/"+userInserted.getId(), HttpMethod.GET, requestEntity2, UserDto.class);
		UserDto userSearched = entity2.getBody();
		
		assertEquals(HttpStatus.OK, entity2.getStatusCode());
		assertEquals(UtilsTest.USER1_EMAIL, userSearched.getEmail());
		assertEquals(UtilsTest.USER1_FIRSTNAME, userSearched.getFirstName());
		assertEquals(userInserted.getId(), userSearched.getId());		
	}

	@Test
	public void updateUserTest() throws JsonProcessingException {
		UserDto user = new UserDto();
		user.setFirstName(UtilsTest.USER2_FIRSTNAME);
		user.setLastName(UtilsTest.USER2_LASTNAME);
		user.setEmail(UtilsTest.USER2_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER2_BIRTH));
		HttpEntity<String> requestEntity = new HttpEntity<String>(mapper.writeValueAsString(user), headers);
		ResponseEntity<UserDto> entity = template.postForEntity(baseUrl + "user", requestEntity, UserDto.class);
		UserDto userInserted = entity.getBody();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertEquals(UtilsTest.USER2_EMAIL, userInserted.getEmail());
		assertEquals(UtilsTest.USER2_FIRSTNAME, userInserted.getFirstName());
		
		user.setId(userInserted.getId());
		user.setFirstName(UtilsTest.USER2_FIRSTNAME2);
		HttpEntity<String> requestEntity2 = new HttpEntity<String>(mapper.writeValueAsString(user), headers);
		ResponseEntity<UserDto> entity2 = template.exchange(baseUrl + "user/" + userInserted.getId(), HttpMethod.PUT, requestEntity2, UserDto.class);
		UserDto userUpdated = entity2.getBody();

		assertEquals(HttpStatus.OK, entity2.getStatusCode());
		assertEquals(UtilsTest.USER2_EMAIL, userUpdated.getEmail());
		assertEquals(UtilsTest.USER2_FIRSTNAME2, userUpdated.getFirstName());		
		
		HttpEntity<String> requestEntity3 = new HttpEntity<String>(headers);
		ResponseEntity<UserDto> entity3 = template.exchange(baseUrl + "user/"+userInserted.getId(), HttpMethod.GET, requestEntity3, UserDto.class);
		UserDto userSearched = entity3.getBody();
		
		assertEquals(HttpStatus.OK, entity2.getStatusCode());
		assertEquals(UtilsTest.USER2_EMAIL, userSearched.getEmail());
		assertEquals(UtilsTest.USER2_FIRSTNAME2, userSearched.getFirstName());
		assertEquals(userUpdated.getId(), userSearched.getId());
	}	
	
	@Test
	public void deleteUserTest() throws JsonProcessingException {
	
		UserDto user = new UserDto();
		user.setFirstName(UtilsTest.USER3_FIRSTNAME);
		user.setLastName(UtilsTest.USER3_LASTNAME);
		user.setEmail(UtilsTest.USER3_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER3_BIRTH));
		HttpEntity<String> requestEntity = new HttpEntity<String>(mapper.writeValueAsString(user), headers);
		ResponseEntity<UserDto> entity = template.postForEntity(baseUrl + "user", requestEntity, UserDto.class);
		UserDto userInserted = entity.getBody();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertEquals(UtilsTest.USER3_EMAIL, userInserted.getEmail());
		assertEquals(UtilsTest.USER3_FIRSTNAME, userInserted.getFirstName());
		
		HttpEntity<String> requestEntity2 = new HttpEntity<String>(headers);
		//ResponseEntity<String> entity2 = template.exchange(baseUrl + "user/" + userInserted.getId(), HttpMethod.DELETE, requestEntity2, String.class);
		template.delete(baseUrl + "user/" + userInserted.getId());

		//assertEquals(HttpStatus.OK, entity2.getStatusCode());	
		
		HttpEntity<String> requestEntity3 = new HttpEntity<String>(headers);
		try {
			template.exchange(baseUrl + "user/"+userInserted.getId(), HttpMethod.GET, requestEntity3, ApiException.class);
			fail("Should throw a 404 HTTPClientErrorException");	
		} catch (HttpClientErrorException ex)   {
		    assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}

	}


}
*/