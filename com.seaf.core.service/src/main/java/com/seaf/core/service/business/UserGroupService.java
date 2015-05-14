package com.seaf.core.service.business;

import java.util.List;

import com.seaf.core.service.business.exception.UserGroupException;
import com.seaf.core.service.model.GroupDto;
import com.seaf.core.service.model.UserDto;
import com.seaf.core.service.model.utils.EnvelopeList;

public interface UserGroupService {
	
	public EnvelopeList 	getUsers		(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public UserDto 			getUser			(int userId) 					throws UserGroupException;
	public UserDto 			addUser			(UserDto user)  				throws UserGroupException;
	public UserDto 			modifyUser		(int userId, UserDto user)		throws UserGroupException;
	public void 			deleteUser		(int userId)					throws UserGroupException;

	public EnvelopeList 	getGroups		(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public GroupDto 		getGroup		(int groupId) 					throws UserGroupException;
	public GroupDto 		addGroup		(GroupDto group)  				throws UserGroupException;
	public GroupDto 		modifyGroup		(int groupId, GroupDto group)	throws UserGroupException;
	public void 			deleteGroup		(int groupId)					throws UserGroupException;
	
	public GroupDto			subscribeGroup	(int groupId, int userId)		throws UserGroupException;
	public void				unsubscribeGroup(int groupId, int userId)		throws UserGroupException;
	
	public List<GroupDto>	getGroupOfUser	(int userId)					throws UserGroupException;
	public List<UserDto> 	getUserOfGroup	(int groupId) 					throws UserGroupException;
}
