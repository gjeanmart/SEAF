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

import com.seaf.core.domain.dao.GroupDao;
import com.seaf.core.domain.entity.Group;
import com.seaf.core.test.utils.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-config.test.xml" })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class GroupDaoTest {
	
	   @Autowired
	   protected GroupDao groupDao = null;

	    @After
	    public void tearDown() {
	        for (Group group : groupDao.selectGroups(UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE)) {
	        	groupDao.deleteGroup(group);
	        }
	    }	   
	   
	   @Test
	   public void insertGroupsTest() {
		   Group group1 = new Group();
		   group1.setName(UtilsTest.GROUP1_NAME);
		   group1.setDescription(UtilsTest.GROUP1_DESCRIPTION);
		   
		   Group group2 = new Group();
		   group2.setName(UtilsTest.GROUP2_NAME);
		   group2.setDescription(UtilsTest.GROUP2_DESCRIPTION);
		   
		   groupDao.insertGroup(group1);
		   groupDao.insertGroup(group2);
		   
		   List<Group> groups = groupDao.selectGroups(UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE);
		   assertEquals(groups.contains(group1), true);
		   assertEquals(groups.contains(group2), true);
		   
		   group1 = groupDao.selectGroupByName(UtilsTest.GROUP1_NAME);
		   assertEquals(group1.getDescription(), UtilsTest.GROUP1_DESCRIPTION);
		   assertEquals(group1.getName(), UtilsTest.GROUP1_NAME);
		   group2 = groupDao.selectGroupByName(UtilsTest.GROUP2_NAME);
		   assertEquals(group2.getDescription(), UtilsTest.GROUP2_DESCRIPTION);
		   assertEquals(group2.getName(), UtilsTest.GROUP2_NAME);
	   }
	   
	   
	   @Test
	   public void UpdateGroupTest() {
		   Group group3 = new Group();
		   group3.setName(UtilsTest.GROUP1_NAME);
		   group3.setDescription(UtilsTest.GROUP1_DESCRIPTION);
		   
		   groupDao.insertGroup(group3);		   
		   
		   group3 = groupDao.selectGroupByName(UtilsTest.GROUP1_NAME);
		   group3.setDescription(UtilsTest.GROUP1_DESCRIPTION2);
		   
		   groupDao.updateGroup(group3);
		   
		   Group groupUpdate = groupDao.selectGroupByName(UtilsTest.GROUP1_NAME);
		   assertEquals(groupUpdate.getDescription(), UtilsTest.GROUP1_DESCRIPTION2);
		   assertEquals(groupUpdate.getName(), UtilsTest.GROUP1_NAME);
	   }
	   
	   @Test
	   public void DeleteGroupTest() {
		   Group group4 = new Group();
		   group4.setName(UtilsTest.GROUP1_NAME);
		   group4.setDescription(UtilsTest.GROUP1_DESCRIPTION);
		   
		   groupDao.insertGroup(group4);
		   
		   group4 = groupDao.selectGroupByName(UtilsTest.GROUP1_NAME);
		   groupDao.deleteGroup(group4);
		   
		   Group groupUpdate = groupDao.selectGroupByName(UtilsTest.GROUP1_NAME);
		   assertEquals(groupUpdate, null);
		   
		   List<Group> groups = groupDao.selectGroups(UtilsTest.PAGE_NUMBER, UtilsTest.PAGE_SIZE);
		   assertEquals(groups.contains(groupUpdate), false);
	   }
	   
}
