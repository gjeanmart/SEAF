package com.seaf.core.service.model.validation;

import com.seaf.core.domain.dao.GroupDao;
import com.seaf.core.domain.entity.Group;
import com.seaf.core.service.business.exception.UserGroupException;
import com.seaf.core.service.model.GroupDto;

public final class GroupDtoValidation {
	
	public static void validateGroupDto(GroupDto groupDto) throws UserGroupException {
		
		if(groupDto == null)
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPNOTVALID, "Group cannot be null");
		
		if(groupDto.getName() == null || groupDto.getName().trim().equals(""))
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "Group name cannot be empty");
		
		if(groupDto.getDescription() == null || groupDto.getDescription().trim().equals(""))
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "Group description cannot be empty");
		
	}
	
	public static void checkDuplicateGroupByName(GroupDao groupDao, String groupName) throws UserGroupException {
		Group group= groupDao.selectGroupByName(groupName);
		
		if(group != null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPALREADYEXIST, "Group [name="+groupName+"] already exists");
		}
	}
	
	public static Group checkNotExistGroupById(GroupDao groupDao, int groupId) throws UserGroupException {
		Group group= groupDao.selectGroupById(groupId);
		
		if(group == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPNOTEXIST, "Group [id="+groupId+"] doesn't exist");
		}
		
		return group;
	}
	
	public static void checkGroupAsNoUser(GroupDao groupDao, int groupId) throws UserGroupException {
		Group group= groupDao.selectGroupById(groupId);
		
		if(group.getUsers().size() > 0) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPASUSERS, "Group [id="+groupId+"] has " + group.getUsers().size() + " users linked");
		}
	}

	public static void checkGroupAsNoUser(GroupDao groupDao, Group group) throws UserGroupException {
		if(group.getUsers().size() > 0) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPASUSERS, "Group [id="+group.getId()+"] has " + group.getUsers().size() + " users linked");
		}
	}
	
}
