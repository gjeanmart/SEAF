package com.seaf.core.test.domain.dao;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.seaf.core.domain.dao.MonitoringDao;
import com.seaf.core.domain.entity.Monitoring;
import com.seaf.core.domain.entity.key.MonitoringKey;
import com.seaf.core.domain.entity.enumeration.Periodicity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-config.test.xml" })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class MonitoringDaoTest {
	
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
		@Autowired
		protected MonitoringDao monitoringDao;

	    @After
	    public void tearDown() throws ParseException {
	    	monitoringDao.deleteMonitoringValue(dateformat.parse("2099-01-01 00:00"));
	    }	   
	   
	   @Test
	   public void insertMonitoringValueTest() throws ParseException {
		   monitoringDao.insertMonitoringValue(getObject(1));
		   monitoringDao.insertMonitoringValue(getObject(2));
		   monitoringDao.insertMonitoringValue(getObject(3));
	   }
	   
	   @Test
	   public void deleteMonitoringValueByRetentionTest() throws ParseException {
		   monitoringDao.insertMonitoringValue(getObject(1));
		   monitoringDao.insertMonitoringValue(getObject(2));
		   monitoringDao.insertMonitoringValue(getObject(3));
		   
		   monitoringDao.deleteMonitoringValue(dateformat.parse("2014-11-18 09:00"));
		   
		   List<Monitoring> values = monitoringDao.selectMonitoringValueGroupByDate(
				   "memory", 
				   Periodicity.HOURLY, 
				   dateformat.parse("2014-11-18 00:00"), 
				   dateformat.parse("2014-11-18 23:59"));
		   
			assertEquals(values.size(), 1);
	   }
	   
	   @Test
	   public void getMonitoringValueByRetentionTest() throws ParseException {
		   monitoringDao.insertMonitoringValue(getObject(1));
		   monitoringDao.insertMonitoringValue(getObject(2));
		   monitoringDao.insertMonitoringValue(getObject(3));
		   
		   List<Monitoring> values = monitoringDao.selectMonitoringValueGroupByDate(
				   "memory", 
				   Periodicity.HOURLY, 
				   dateformat.parse("2014-11-18 00:00"), 
				   dateformat.parse("2014-11-18 23:59"));
		   
			assertEquals(values.size(), 2);
	   }	   
	   
	   private Monitoring getObject(int id) throws ParseException {
		   
		   Monitoring m = new Monitoring();
		   
		   switch (id) {
			case 1:
				   m.setMonitoringKey(new MonitoringKey(dateformat.parse("2014-11-18 08:10"), "memory"));
				   m.setValue(10);
				break;
			case 2:
				   m.setMonitoringKey(new MonitoringKey(dateformat.parse("2014-11-18 08:15"), "memory"));
				   m.setValue(20);
				break;
			case 3:
				 m.setMonitoringKey(new MonitoringKey(dateformat.parse("2014-11-18 09:05"), "memory"));
				   m.setValue(15);
				break;
			default:
				 m.setMonitoringKey(new MonitoringKey(dateformat.parse("2014-11-18 08:10"), "memory"));
				 m.setValue(10);
				break;
			}
		   
			return m;
	   }

	   
}
