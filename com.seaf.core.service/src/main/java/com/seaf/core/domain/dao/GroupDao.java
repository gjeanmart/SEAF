package com.seaf.core.domain.dao;

import java.util.List;

import com.seaf.core.domain.entity.Group;

public interface GroupDao {
	
	public List<Group> 		selectGroups			(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public List<Group> 		selectGroups			(String searchQuery, String sortAttribute, String sortDirection);
	public List<Group> 		selectGroups			(String searchQuery, int pageNumber, int pageSize);
	public List<Group> 		selectGroups			(String searchQuery);
	
	public List<Group> 		selectGroups			(int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public List<Group> 		selectGroups			(int pageNumber, int pageSize);
	public List<Group> 		selectGroups			(String sortAttribute, String sortDirection);
	public List<Group> 		selectGroups			();
	
	public long		 		countGroups				(String searchQuery);
	public long		 		countGroups				();

	public Group 			selectGroupById			(int groupId);
	public Group 			selectGroupByName		(String groupName);
	
	public void 			insertGroup				(Group group);
	public void 			updateGroup				(Group group);
	public void				deleteGroup				(Group group);

}
