package com.seaf.core.service.model;

import java.util.List;

public class HeapMonitoringDto {
	
	private List<Object[]> usedMemory;
	private List<Object[]> totalMemory;
	private List<Object[]> maxMemory;

	public HeapMonitoringDto(List<Object[]> usedMemory, List<Object[]> totalMemory, List<Object[]> maxMemory) {
		this.usedMemory = usedMemory;
		this.totalMemory = totalMemory;
		this.maxMemory = maxMemory;
	}
	
	public List<Object[]> getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(List<Object[]> usedMemory) {
		this.usedMemory = usedMemory;
	}
	public List<Object[]> getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(List<Object[]> totalMemory) {
		this.totalMemory = totalMemory;
	}
	public List<Object[]> getMaxMemory() {
		return maxMemory;
	}
	public void setMaxMemory(List<Object[]> maxMemory) {
		this.maxMemory = maxMemory;
	}

}
