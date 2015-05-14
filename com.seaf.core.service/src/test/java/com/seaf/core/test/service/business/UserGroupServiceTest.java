package com.seaf.core.test.service.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.seaf.core.service.business.UserGroupService;
import com.seaf.core.service.business.exception.UserGroupException;
import com.seaf.core.service.model.GroupDto;
import com.seaf.core.service.model.UserDto;
import com.seaf.core.test.utils.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/spring-config.test.xml" })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class UserGroupServiceTest {

	@Autowired
	private UserGroupService userGroupService;

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
	public void insertUsersTest() throws UserGroupException {
		UserDto user1 = new UserDto();
		user1.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user1.setLastName(UtilsTest.USER1_LASTNAME);
		user1.setEmail(UtilsTest.USER1_EMAIL);
		user1.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));

		UserDto user2 = new UserDto();
		user2.setFirstName(UtilsTest.USER2_FIRSTNAME);
		user2.setLastName(UtilsTest.USER2_LASTNAME);
		user2.setEmail(UtilsTest.USER2_EMAIL);
		user2.setBirthDate(UtilsTest.getDate(UtilsTest.USER2_BIRTH));

		UserDto user1Inserted = userGroupService.addUser(user1);
		UserDto user2Inserted = userGroupService.addUser(user2);

		UserDto user1Search = userGroupService.getUser(user1Inserted.getId());

		assertEquals("User 1 hasn't be inserted", user1Inserted.getEmail(), user1.getEmail());
		assertEquals("User 1 hasn't be inserted", user1Search.getEmail(), user1.getEmail());

		UserDto user2Search = userGroupService.getUser(user2Inserted.getId());

		assertEquals("User 2 hasn't be inserted", user2Inserted.getEmail(), user2.getEmail());
		assertEquals("User 2 hasn't be inserted", user2Search.getEmail(), user2.getEmail());
	}

	@Test
	public void insertUsersAlreadyExistTest() throws UserGroupException {
		UserDto user1 = new UserDto();
		user1.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user1.setLastName(UtilsTest.USER1_LASTNAME);
		user1.setEmail(UtilsTest.USER1_EMAIL);
		user1.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));

		UserDto user2 = new UserDto();
		user2.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user2.setLastName(UtilsTest.USER1_LASTNAME);
		user2.setEmail(UtilsTest.USER1_EMAIL);
		user2.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));
		
		userGroupService.addUser(user1);
		
		try {
			userGroupService.addUser(user2);
			fail("No UserGroupException throw");
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void updateUsersTest() throws UserGroupException {
		UserDto user = new UserDto();
		user.setFirstName(UtilsTest.USER2_FIRSTNAME);
		user.setLastName(UtilsTest.USER2_LASTNAME);
		user.setEmail(UtilsTest.USER2_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER2_BIRTH));

		UserDto userInserted = userGroupService.addUser(user);

		userInserted.setFirstName(UtilsTest.USER2_FIRSTNAME2);

		UserDto userUpdated = userGroupService.modifyUser(userInserted.getId(), userInserted);

		UserDto userSearch = userGroupService.getUser(userInserted.getId());

		assertEquals("User 2 hasn't be updated", userSearch.getEmail(), user.getEmail());
		assertEquals("User 2 hasn't be updated", userSearch.getFirstName(), userUpdated.getFirstName());
	}
	
	@Test
	public void deleteUsersTest()  throws UserGroupException {
		UserDto user = new UserDto();
		user.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user.setLastName(UtilsTest.USER1_LASTNAME);
		user.setEmail(UtilsTest.USER1_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));

		UserDto userInserted = userGroupService.addUser(user);

		userGroupService.deleteUser(userInserted.getId());

		UserDto userDeleted = null;
		try {
			userDeleted = userGroupService.getUser(userInserted.getId());
			fail("No UserGroupException throw");
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
		assertNull("User 1 hasn't be deleted", userDeleted);
	}
	
	@Test
	public void searchUsersTest() throws UserGroupException {
		UserDto user1 = new UserDto();
		user1.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user1.setLastName(UtilsTest.USER1_LASTNAME);
		user1.setEmail(UtilsTest.USER1_EMAIL);
		user1.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));

		UserDto user2 = new UserDto();
		user2.setFirstName(UtilsTest.USER2_FIRSTNAME);
		user2.setLastName(UtilsTest.USER2_LASTNAME);
		user2.setEmail(UtilsTest.USER2_EMAIL);
		user2.setBirthDate(UtilsTest.getDate(UtilsTest.USER2_BIRTH));

		UserDto user1Inserted = userGroupService.addUser(user1);
		UserDto user2Inserted = userGroupService.addUser(user2);

		List<UserDto> userList = (List<UserDto>) userGroupService.getUsers(UtilsTest.USER1_FIRSTNAME.split(" ")[0], UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE,null,null).getData();

		assertTrue("User 1 not found", userList.contains(user1Inserted));
		assertFalse("User 2 found", userList.contains(user2Inserted));
	}	
	
	@Test
	public void searchUsersNotExitTest()  throws UserGroupException {
		UserDto user = new UserDto();
		user.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user.setLastName(UtilsTest.USER1_LASTNAME);
		user.setEmail(UtilsTest.USER1_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));
		userGroupService.addUser(user);

		try {
			userGroupService.getUser(100);
			fail("No UserGroupException throw");
		} catch (UserGroupException e) {
			e.printStackTrace();
		}
		
		List<UserDto> userList = (List<UserDto>) userGroupService.getUsers(UtilsTest.USER2_FIRSTNAME, UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE,null,null).getData();
		assertEquals(userList.size(), 0);
	}

}
