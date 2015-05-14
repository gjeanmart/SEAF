package com.seaf.core.service.model.comparator;

import java.util.Comparator;

import com.seaf.core.service.model.ThreadDto;

public class ThreadDtoComparator  implements Comparator<ThreadDto> {
	
	private String sortAttribute;
	private String sortDirection;

	public ThreadDtoComparator(String sortAttribute, String sortDirection) {
		this.sortAttribute = sortAttribute;
		this.sortDirection = sortDirection;
	}

	@Override
	public int compare(ThreadDto o1, ThreadDto o2) {
		if (sortAttribute.equals("name")) {
			if(sortDirection.equals("desc")) {
				return o2.getName().compareTo(o1.getName());
			} else {
				return o1.getName().compareTo(o2.getName());
			}
			
		} else if (sortAttribute.equals("state")) {
			if(sortDirection.equals("desc")) {
				return o2.getState().compareTo(o1.getState());
			} else {
				return o1.getState().compareTo(o2.getState());
			}
			
		} else if (sortAttribute.equals("groupName")) {
			if(sortDirection.equals("desc")) {
				return o2.getGroupName().compareTo(o1.getGroupName());
			} else {
				return o1.getGroupName().compareTo(o2.getGroupName());
			}
			
		} else if (sortAttribute.equals("parentName")) {
			if(sortDirection.equals("desc")) {
				return o2.getParentName().compareTo(o1.getParentName());
			} else {
				return o1.getParentName().compareTo(o2.getParentName());
			}
			
		} else if (sortAttribute.equals("priority")) {
			if(sortDirection.equals("desc")) {
				return (int) (o2.getPriority() - o1.getPriority());
			} else {
				return (int) (o1.getPriority() - o2.getPriority());
			}
			
		}  else if (sortAttribute.equals("daemon")) {
			if(sortDirection.equals("desc")) {
				return new Boolean(o2.isDaemon()).compareTo(new Boolean (o1.isDaemon()));
			} else {
				return new Boolean(o1.isDaemon()).compareTo(new Boolean (o2.isDaemon()));
			}
			
		} else {
			if(sortDirection.equals("desc")) {
				return (int) (o2.getId() - o1.getId());
			} else {
				return (int) (o1.getId() - o2.getId());
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
