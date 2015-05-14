package com.seaf.core.service.business;

import java.util.Date;

import com.seaf.core.domain.enumeration.Periodicity;
import com.seaf.core.service.model.CpuMonitoringDto;
import com.seaf.core.service.model.HeapMonitoringDto;
import com.seaf.core.service.model.utils.EnvelopeList;

public interface JavaService {
	
	public EnvelopeList			getThreads(int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public EnvelopeList			getSystemProperties(int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public EnvelopeList			getJVMArguments(int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public EnvelopeList			getApplicationProperties(int pageNumber, int pageSize, String sortAttribute, String sortDirection);

	public void 				getCurrentHeap();
	public HeapMonitoringDto 	getMonitoringHeap(Periodicity periodicity, Date startDate, Date endDate);
	
	public void 				getCurrentCpu();
	public CpuMonitoringDto 	getMonitoringCpu(Periodicity periodicity, Date startDate, Date endDate);
	
	public void					deleteMonitoringData();
}
