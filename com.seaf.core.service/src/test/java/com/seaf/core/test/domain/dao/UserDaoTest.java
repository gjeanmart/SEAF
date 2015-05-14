package com.seaf.core.test.domain.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.seaf.core.domain.dao.UserDao;
import com.seaf.core.domain.entity.User;
import com.seaf.core.test.utils.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/spring-config.test.xml" })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class UserDaoTest {

	@Autowired
	protected UserDao userDao = null;
	
    @After
    public void tearDown() {
        for (User user : userDao.selectUsers()) {
        	userDao.deleteUser(user);
        }
    }		

	@Test
	public void insertUsersTest() {
		User user1 = new User();
		user1.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user1.setLastName(UtilsTest.USER1_LASTNAME);
		user1.setEmail(UtilsTest.USER1_EMAIL);
		user1.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));

		User user2 = new User();
		user2.setFirstName(UtilsTest.USER2_FIRSTNAME);
		user2.setLastName(UtilsTest.USER2_LASTNAME);
		user2.setEmail(UtilsTest.USER2_EMAIL);
		user2.setBirthDate(UtilsTest.getDate(UtilsTest.USER2_BIRTH));

		userDao.insertUser(user1);
		userDao.insertUser(user2);

		List<User> users = userDao.selectUsers();
		assertEquals(users.contains(user1), true);
		assertEquals(users.contains(user2), true);

		user1 = userDao.selectUserByEmail(UtilsTest.USER1_EMAIL);
		assertEquals(user1.getEmail(), UtilsTest.USER1_EMAIL);
		assertEquals(user1.getFirstName(), UtilsTest.USER1_FIRSTNAME);
		assertEquals(user1.getLastName(), UtilsTest.USER1_LASTNAME);
		assertEquals(user1.getBirthDate().getTime(), UtilsTest.getDate(UtilsTest.USER1_BIRTH).getTime());

		user2 = userDao.selectUserByEmail(UtilsTest.USER2_EMAIL);
		assertEquals(user2.getEmail(), UtilsTest.USER2_EMAIL);
		assertEquals(user2.getFirstName(), UtilsTest.USER2_FIRSTNAME);
		assertEquals(user2.getLastName(), UtilsTest.USER2_LASTNAME);
		assertEquals(user2.getBirthDate().getTime(), UtilsTest.getDate(UtilsTest.USER2_BIRTH).getTime());
	}

	@Test
	public void UpdateUserTest() {
		User user = new User();
		user.setFirstName(UtilsTest.USER2_FIRSTNAME);
		user.setLastName(UtilsTest.USER2_LASTNAME);
		user.setEmail(UtilsTest.USER2_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER2_BIRTH));

		userDao.insertUser(user);

		user = userDao.selectUserByEmail(UtilsTest.USER2_EMAIL);
		user.setFirstName(UtilsTest.USER2_FIRSTNAME2);

		userDao.updateUser(user);

		User userUpdate = userDao.selectUserByEmail(UtilsTest.USER2_EMAIL);
		assertEquals(userUpdate.getEmail(), UtilsTest.USER2_EMAIL);
		assertEquals(userUpdate.getFirstName(), UtilsTest.USER2_FIRSTNAME2);
	}

	@Test
	public void DeleteUserTest() {
		User user = new User();
		user.setFirstName(UtilsTest.USER1_FIRSTNAME);
		user.setLastName(UtilsTest.USER1_LASTNAME);
		user.setEmail(UtilsTest.USER1_EMAIL);
		user.setBirthDate(UtilsTest.getDate(UtilsTest.USER1_BIRTH));

		userDao.insertUser(user);

		user = userDao.selectUserByEmail(UtilsTest.USER1_EMAIL);
		userDao.deleteUser(user);

		User userUpdate = userDao.selectUserByEmail(UtilsTest.USER1_EMAIL);
		assertEquals(userUpdate, null);

		List<User> users = userDao.selectUsers();
		assertEquals(users.contains(userUpdate), false);
	}

}
