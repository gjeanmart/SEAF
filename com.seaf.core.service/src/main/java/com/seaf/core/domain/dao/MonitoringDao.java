package com.seaf.core.domain.dao;

import java.util.Date;
import java.util.List;

import com.seaf.core.domain.entity.Monitoring;
import com.seaf.core.domain.enumeration.Periodicity;

public interface MonitoringDao {
	
	public void 			insertMonitoringValue				(Monitoring monitoring);
	public List<Monitoring> selectMonitoringValueGroupByDate	(String key, Periodicity periodicity, Date startDate, Date endDate);
	public void				deleteMonitoringValue				(Date beforeDate);

}
