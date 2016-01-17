package com.seaf.core.service.business;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.seaf.core.domain.dao.MonitoringDao;
import com.seaf.core.domain.entity.Monitoring;
import com.seaf.core.domain.entity.enumeration.Periodicity;
import com.seaf.core.service.model.CpuMonitoringDto;
import com.seaf.core.service.model.HeapMonitoringDto;
import com.seaf.core.service.model.KeyValueDto;
import com.seaf.core.service.model.ThreadDto;
import com.seaf.core.service.model.comparator.KeyValueDtoComparator;
import com.seaf.core.service.model.comparator.ThreadDtoComparator;
import com.seaf.core.service.model.utils.EnvelopeList;
import com.seaf.core.utils.SpringPropertiesUtil;
import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
@Component
@EnableScheduling
public class JavaServiceImpl implements JavaService {
	
	public static final String			MONITORING_KEY_MEMORY_USED 		= "memory_used";
	public static final String			MONITORING_KEY_MEMORY_TOTAL 	= "memory_total";
	public static final String			MONITORING_KEY_MEMORY_MAX 		= "memory_max";
	public static final String			MONITORING_KEY_CPU_SYSTEM 		= "cpu_system";
	public static final String			MONITORING_KEY_CPU_PROCESS 		= "cpu_process";
	
	@Value("${monitor.activate}")
	private boolean 					activateMonitoring;
	
	@Value("${monitor.data.retention.day}")
	private int 						monitoringDataRetentionInDay;	
	
	@Autowired
	private MonitoringDao				monitoringDao;
	
	
	@Override
	@Scheduled(cron = "${monitor.data.retention.pool.cron}")
	public void deleteMonitoringData() {
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date beforeDate = new Date(System.currentTimeMillis() - (monitoringDataRetentionInDay * DAY_IN_MS));
		
		monitoringDao.deleteMonitoringValue(beforeDate);
	}
	
	@Override
	@Scheduled(cron = "${monitor.cron}")
	public void getCurrentHeap() {
		if(activateMonitoring) {
			int mb = 1024*1024;
			Runtime runtime = Runtime.getRuntime();
			
			monitoringDao.insertMonitoringValue(new Monitoring(new Date(), MONITORING_KEY_MEMORY_USED, (runtime.totalMemory() - runtime.freeMemory()) / mb));
			monitoringDao.insertMonitoringValue(new Monitoring(new Date(), MONITORING_KEY_MEMORY_TOTAL, runtime.totalMemory() / mb));
			monitoringDao.insertMonitoringValue(new Monitoring(new Date(), MONITORING_KEY_MEMORY_MAX, runtime.maxMemory() / mb));
		}
	}
	
	@Override
	public HeapMonitoringDto getMonitoringHeap(Periodicity periodicity, Date startDate, Date endDate) {
		List<Monitoring> usedMemoryMonitoringValue = monitoringDao.selectMonitoringValueGroupByDate(MONITORING_KEY_MEMORY_USED, periodicity, startDate, endDate);
		List<Monitoring> totalMemoryMonitoringValue = monitoringDao.selectMonitoringValueGroupByDate(MONITORING_KEY_MEMORY_TOTAL, periodicity, startDate, endDate);
		List<Monitoring> maxMemoryMonitoringValue = monitoringDao.selectMonitoringValueGroupByDate(MONITORING_KEY_MEMORY_MAX, periodicity, startDate, endDate);
		
		List<Object[]> usedMemoryMonitoringValueObjList = new ArrayList<Object[]>();
		for(Monitoring m : usedMemoryMonitoringValue) {
			Object[] o = new Object[2];
			o[0] = m.getDate();
			o[1] = m.getValue();
			
			usedMemoryMonitoringValueObjList.add(o);
		}
		
		List<Object[]> totalMemoryMonitoringValueObjList = new ArrayList<Object[]>();
		for(Monitoring m : totalMemoryMonitoringValue) {
			Object[] o = new Object[2];
			o[0] = m.getDate();
			o[1] = m.getValue();
			
			totalMemoryMonitoringValueObjList.add(o);
		}
		
		List<Object[]> maxMemoryMonitoringValueObjList = new ArrayList<Object[]>();
		for(Monitoring m : maxMemoryMonitoringValue) {
			Object[] o = new Object[2];
			o[0] = m.getDate();
			o[1] = m.getValue();
			
			maxMemoryMonitoringValueObjList.add(o);
		}
		
		return new HeapMonitoringDto(usedMemoryMonitoringValueObjList, totalMemoryMonitoringValueObjList, maxMemoryMonitoringValueObjList);
	}
	
	@Override
	@Scheduled(cron = "${monitor.cron}")
	public void getCurrentCpu() {
		if(activateMonitoring) {
			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

			double systemCpuLoad = (osBean.getSystemCpuLoad() < 0) ? 0 : osBean.getSystemCpuLoad();
			double processCpuLoad = (osBean.getProcessCpuLoad() < 0) ? 0 : osBean.getProcessCpuLoad();

			monitoringDao.insertMonitoringValue(new Monitoring(new Date(), MONITORING_KEY_CPU_PROCESS, processCpuLoad));
			monitoringDao.insertMonitoringValue(new Monitoring(new Date(), MONITORING_KEY_CPU_SYSTEM, systemCpuLoad));
		}
	}
	
	@Override
	public CpuMonitoringDto getMonitoringCpu(Periodicity periodicity, Date startDate, Date endDate) {
		
		List<Monitoring> systemCpuMonitoringValue = monitoringDao.selectMonitoringValueGroupByDate(MONITORING_KEY_CPU_SYSTEM, periodicity, startDate, endDate);
		List<Monitoring> processCpuMonitoringValue = monitoringDao.selectMonitoringValueGroupByDate(MONITORING_KEY_CPU_PROCESS, periodicity, startDate, endDate);
		
		List<Object[]> systemCpuMonitoringValueObjList = new ArrayList<Object[]>();
		for(Monitoring m : systemCpuMonitoringValue) {
			Object[] o = new Object[2];
			o[0] = m.getDate();
			o[1] = m.getValue();
			
			systemCpuMonitoringValueObjList.add(o);
		}
		
		List<Object[]> processCpuMonitoringValueObjList = new ArrayList<Object[]>();
		for(Monitoring m : processCpuMonitoringValue) {
			Object[] o = new Object[2];
			o[0] = m.getDate();
			o[1] = m.getValue();
			
			processCpuMonitoringValueObjList.add(o);
		}
		
		return new CpuMonitoringDto(systemCpuMonitoringValueObjList, processCpuMonitoringValueObjList);
	}
	
	
	@Override
	public EnvelopeList getThreads(int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		List<ThreadDto> threadsDtoList = new ArrayList<ThreadDto>();
		
		Set<Thread> threadKeys = Thread.getAllStackTraces().keySet();
        for (Thread t : threadKeys) {
            ThreadDto threadDto = new ThreadDto(
            		t.getId(), 
            		t.getName(), 
            		t.getPriority(), 
            		t.isDaemon(),  
            		t.isInterrupted(), 
            		t.getState().toString(), 
            		t.isAlive(),
            		t.getThreadGroup().getName(),
            		(t.getThreadGroup().getParent() != null) ? t.getThreadGroup().getParent().getName() : null);
            
            threadsDtoList.add(threadDto);
         }
		
		//Sorting
		if(sortAttribute != null && sortDirection != null) {
			Collections.sort(threadsDtoList, new ThreadDtoComparator(sortAttribute, sortDirection));
		}
		
		//Paging
		List<ThreadDto> data = null;
		if(pageNumber != 0 && pageSize != 0) {
			data = threadsDtoList.subList((pageNumber - 1)*pageSize, (pageNumber*pageSize>threadsDtoList.size()) ? threadsDtoList.size() : pageNumber*pageSize);
		} else {
			data = threadsDtoList;
		}
		
		return new EnvelopeList(data, threadsDtoList.size());
	}

	@Override
	public EnvelopeList getSystemProperties(int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		List<KeyValueDto> systemProperties = new ArrayList<KeyValueDto>();
		
		Properties properties = System.getProperties();
        Set<Object>  sysPropertiesKeys = properties.keySet();
        for (Object key : sysPropertiesKeys) {
           KeyValueDto keyValue = new KeyValueDto(key.toString(), properties.getProperty((String)key));
           systemProperties.add(keyValue);
        }
        
		//Sorting
		if(sortAttribute != null && sortDirection != null) {
			Collections.sort(systemProperties, new KeyValueDtoComparator(sortAttribute, sortDirection));
		}
		
		//Paging
		List<KeyValueDto> data = null;
		if(pageNumber != 0 && pageSize != 0) {
			data = systemProperties.subList((pageNumber - 1)*pageSize, (pageNumber*pageSize>systemProperties.size()) ? systemProperties.size() : pageNumber*pageSize);
		} else {
			data = systemProperties;
		}
		
		return new EnvelopeList(data, systemProperties.size());
	}

	@Override
	public EnvelopeList getJVMArguments(int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		List<KeyValueDto> jvmArgumentsList = new ArrayList<KeyValueDto>();
		
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArgs = runtimeMXBean.getInputArguments();
		for (String jvmArg : jvmArgs) {
			String key = null;
			String value = null;
			
			Pattern jvmArgPatt = Pattern.compile("^(-.*)=(.*)$");
			Matcher m = jvmArgPatt.matcher(jvmArg);
			
			if (m.matches()) {
				key = m.group(1);
				value = m.group(2);
			} else {
				key = jvmArg;
			}
			
			KeyValueDto keyValue = new KeyValueDto(key, value);
			jvmArgumentsList.add(keyValue);

		}
		
		//Sorting
		if(sortAttribute != null && sortDirection != null) {
			Collections.sort(jvmArgumentsList, new KeyValueDtoComparator(sortAttribute, sortDirection));
		}
		
		//Paging
		List<KeyValueDto> data = null;
		if(pageNumber != 0 && pageSize != 0) {
			data = jvmArgumentsList.subList((pageNumber - 1)*pageSize, (pageNumber*pageSize>jvmArgumentsList.size()) ? jvmArgumentsList.size() : pageNumber*pageSize);
		} else {
			data = jvmArgumentsList;
		}
		
		return new EnvelopeList(data, jvmArgumentsList.size());
		
	}

	@Override
	public EnvelopeList getApplicationProperties(int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		List<KeyValueDto> appProperties = new ArrayList<KeyValueDto>();
		
		Map<String, String>  appPropertiesMap = SpringPropertiesUtil.getProperties();
		
		for (Map.Entry<String, String> appPropertyKey : appPropertiesMap.entrySet()) {
			KeyValueDto keyValue = new KeyValueDto(appPropertyKey.getKey(), appPropertyKey.getValue());
			appProperties.add(keyValue);
		}
        
		//Sorting
		if(sortAttribute != null && sortDirection != null) {
			Collections.sort(appProperties, new KeyValueDtoComparator(sortAttribute, sortDirection));
		}
		
		//Paging
		List<KeyValueDto> data = null;
		if(pageNumber != 0 && pageSize != 0) {
			data = appProperties.subList((pageNumber - 1)*pageSize, (pageNumber*pageSize>appProperties.size()) ? appProperties.size() : pageNumber*pageSize);
		} else {
			data = appProperties;
		}
		
		return new EnvelopeList(data, appProperties.size());
	}

}
