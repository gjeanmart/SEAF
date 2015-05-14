package com.seaf.core.service.model.comparator;

import java.util.Comparator;

import com.seaf.core.service.model.KeyValueDto;

public class KeyValueDtoComparator  implements Comparator<KeyValueDto> {
	
	private String sortAttribute;
	private String sortDirection;
	
	public KeyValueDtoComparator(String sortAttribute, String sortDirection) {
		this.sortAttribute = sortAttribute;
		this.sortDirection = sortDirection;
	}

	@Override
	public int compare(KeyValueDto o1, KeyValueDto o2) {
		if (sortAttribute.equals("name")) {
			if(sortDirection.equals("desc")) {
				return o2.getValue().compareTo(o1.getValue());
			} else {
				return o1.getValue().compareTo(o2.getValue());
			}
		} else {
			if(sortDirection.equals("desc")) {
				return o2.getKey().compareTo(o1.getKey());
			} else {
				return o1.getKey().compareTo(o2.getKey());
			}
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
