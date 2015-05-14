package com.seaf.core.service.model;

import java.util.List;

public class CpuMonitoringDto {
	
	private List<Object[]> systemCpuLoad;
	private List<Object[]> processCpuLoad;
	
	public CpuMonitoringDto(List<Object[]> systemCpuLoad,
			List<Object[]> processCpuLoad) {
		super();
		this.systemCpuLoad = systemCpuLoad;
		this.processCpuLoad = processCpuLoad;
	}
	
	public List<Object[]> getSystemCpuLoad() {
		return systemCpuLoad;
	}
	public void setSystemCpuLoad(List<Object[]> systemCpuLoad) {
		this.systemCpuLoad = systemCpuLoad;
	}
	public List<Object[]> getProcessCpuLoad() {
		return processCpuLoad;
	}
	public void setProcessCpuLoad(List<Object[]> processCpuLoad) {
		this.processCpuLoad = processCpuLoad;
	}

	
}
