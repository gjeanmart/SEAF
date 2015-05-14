package com.seaf.core.service.model.comparator;

import java.util.Comparator;

import com.seaf.core.service.model.LoggerDto;

public class LoggerDtoComparator  implements Comparator<LoggerDto> {
	
	private String sortAttribute;
	private String sortDirection;
	
	public LoggerDtoComparator(String sortAttribute, String sortDirection) {
		this.sortAttribute = sortAttribute;
		this.sortDirection = sortDirection;
	}
	
	@Override
	public int compare(LoggerDto o1, LoggerDto o2) {

		if(sortDirection.equals("desc")) {
			return o2.getLoggerName().compareTo(o1.getLoggerName());
		} else {
			return o1.getLoggerName().compareTo(o2.getLoggerName());
		}
	}

	public String getSortAttribute() {
		return sortAttribute;
	}


	public void setSortAttribute(String sortAttribute) {
		this.sortAttribute = sortAttribute;
	}


	public String getSortDirection() {
		return sortDirection;
	}


	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	
}
