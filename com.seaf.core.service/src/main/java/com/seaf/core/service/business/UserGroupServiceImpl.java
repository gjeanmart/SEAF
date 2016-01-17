package com.seaf.core.service.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seaf.core.domain.dao.GroupDao;
import com.seaf.core.domain.dao.UserDao;
import com.seaf.core.domain.entity.Group;
import com.seaf.core.domain.entity.User;
import com.seaf.core.service.business.exception.UserGroupException;
import com.seaf.core.service.model.GroupDto;
import com.seaf.core.service.model.UserDto;
import com.seaf.core.service.model.utils.EnvelopeList;
import com.seaf.core.service.model.validation.GroupDtoValidation;
import com.seaf.core.service.model.validation.UserDtoValidation;

@Component
public class UserGroupServiceImpl implements UserGroupService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupDao groupDao;

	@Autowired
	private Mapper mapper;

	public EnvelopeList getUsers(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		List<UserDto>		result 		= new ArrayList<UserDto>();
		List<User> 			userList	= null;
		long				count		= 0;
		
		if(searchQuery == null) {
			userList 	= userDao.selectUsers(pageNumber, pageSize, sortAttribute, sortDirection);
			count		= userDao.countUsers();
		} else {
			userList 	= userDao.selectUsers(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);
			count		= userDao.countUsers(searchQuery);	
		}

		
		for(User user : userList) {
			result.add(mapper.map(user, UserDto.class, "User-Model2DTO"));
		}
		
		return new EnvelopeList(result, count);
	}

	public UserDto getUser(int userId) throws UserGroupException {
		User user = userDao.selectUserById(userId);
		
		if(user == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTEXIST, "User [id="+userId+"] doesn't exist");
		}

		return mapper.map(user, UserDto.class);
	}


	@Override
	public UserDto addUser(UserDto userDto) throws UserGroupException {
		UserDtoValidation.validateUserDto(userDto);
		UserDtoValidation.checkDuplicateUserByEmail(userDao, userDto.getEmail());
		
		userDto.setAddedDate(new Date());
		userDto.setLastModifiedDate(new Date());
		userDao.insertUser(mapper.map(userDto, User.class, "User-Model2DTO"));
		
		User userInserted = userDao.selectUserByEmail(userDto.getEmail());
		
		return mapper.map(userInserted, UserDto.class, "User-Model2DTO");
	}

	@Override
	public UserDto modifyUser(int userId, UserDto userDto) throws UserGroupException {
		UserDtoValidation.validateUserDto(userDto);
		User user = UserDtoValidation.checkNotExistUserById(userDao, userId);
		if(!user.getEmail().equals(userDto.getEmail())) // Uniquement s'il y a changement d'email
			UserDtoValidation.checkDuplicateUserByEmail(userDao, userDto.getEmail());
		
		userDto.setAddedDate(user.getAddedDate());
		userDto.setLastModifiedDate(new Date());
		userDao.updateUser(mapper.map(userDto, User.class, "User-Model2DTO"));
		
		User userUpdated = userDao.selectUserById(userId);
		
		return mapper.map(userUpdated, UserDto.class, "User-Model2DTO");
	}

	@Override
	public void deleteUser(int userId) throws UserGroupException {
		User user = UserDtoValidation.checkNotExistUserById(userDao, userId);
		
		userDao.deleteUser(user);
	}

	@Override
	public EnvelopeList getGroups(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		List<GroupDto>		result 		= new ArrayList<GroupDto>();
		List<Group> 		groupList	= null;
		long				count;
		
		if(searchQuery == null) {
			groupList 	= groupDao.selectGroups(pageNumber, pageSize, sortAttribute, sortDirection);
			count		= groupDao.countGroups();
		} else {
			groupList 	= groupDao.selectGroups(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);
			count		= groupDao.countGroups(searchQuery);	
		}

		
		for(Group group : groupList) {
			result.add(mapper.map(group, GroupDto.class, "Group-Model2DTO"));
		}
		
		return new EnvelopeList(result, count);
	}

	@Override
	public GroupDto getGroup(int groupId) throws UserGroupException {
		Group group = groupDao.selectGroupById(groupId);
		
		if(group == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPNOTEXIST, "Group [id="+group+"] doesn't exist");
		}

		return mapper.map(group, GroupDto.class, "Group-Model2DTO");
	}

	@Override
	public GroupDto addGroup(GroupDto group) throws UserGroupException {
		GroupDtoValidation.validateGroupDto(group);
		GroupDtoValidation.checkDuplicateGroupByName(groupDao, group.getName());
		
		group.setAddedDate(new Date());
		group.setLastModifiedDate(new Date());
		groupDao.insertGroup(mapper.map(group, Group.class, "Group-Model2DTO"));
		
		Group groupInserted = groupDao.selectGroupByName(group.getName());
		
		return mapper.map(groupInserted, GroupDto.class, "Group-Model2DTO");
	}

	@Override
	public GroupDto modifyGroup(int groupId, GroupDto groupDto) throws UserGroupException {
		GroupDtoValidation.validateGroupDto(groupDto);
		Group group= GroupDtoValidation.checkNotExistGroupById(groupDao, groupId);
		if(!group.getName().equals(groupDto.getName())) // Uniquement s'il y a changement d'email
			GroupDtoValidation.checkDuplicateGroupByName(groupDao, groupDto.getName());
		
		
		groupDto.setAddedDate(group.getAddedDate());
		groupDto.setLastModifiedDate(new Date());
		groupDao.updateGroup(mapper.map(groupDto, Group.class, "Group-Model2DTO"));
		
		Group groupUpdated = groupDao.selectGroupById(group.getId());

        return mapper.map(groupUpdated, GroupDto.class, "Group-Model2DTO");
	}

	@Override
	public void deleteGroup(int groupId) throws UserGroupException {
		Group group = GroupDtoValidation.checkNotExistGroupById(groupDao, groupId);
		GroupDtoValidation.checkGroupAsNoUser(groupDao, group);
		
		groupDao.deleteGroup(group);
	}
	
	@Override
	public List<GroupDto> getGroupOfUser(int userId) throws UserGroupException {
		List<GroupDto>		result 		= new ArrayList<GroupDto>();
		
		User 				user 		= userDao.selectUserById(userId);
		
		if(user == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTEXIST, "User [id="+userId+"] doesn't exist");
		}
		
		for(Group group: user.getGroups()) {
			result.add(mapper.map(group, GroupDto.class, "Group-Model2DTO"));
		}

		return result;
	}

	@Override
	public List<UserDto> getUserOfGroup(int groupId) throws UserGroupException {
		List<UserDto>		result 		= new ArrayList<UserDto>();
		
		Group 				group 		= groupDao.selectGroupById(groupId);
		
		if(group == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPNOTEXIST, "Group [id="+groupId+"] doesn't exist");
		}
		
		for(User user : group.getUsers()) {
			result.add(mapper.map(user, UserDto.class, "User-Model2DTO"));
		}

		return result;
	}

	@Override
	public GroupDto subscribeGroup(int groupId, int userId) throws UserGroupException {
		Group group = groupDao.selectGroupById(groupId);
		
		if(group == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPNOTEXIST, "Group [id="+groupId+"] doesn't exist");
		}
		
		User user = userDao.selectUserById(userId);
		
		if(user == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTEXIST, "User [id="+userId+"] doesn't exist");
		}
		
		if(user.getGroups().contains(group)) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_MEMBERSHIPALREADYEXIST, "Membership of User [id="+userId+"] to group [id="+groupId+"] already exists");
		}
		
		user.getGroups().add(group);
		userDao.updateUser(user);
		
		return mapper.map(group, GroupDto.class, "Group-Model2DTO");
	}

	@Override
	public void unsubscribeGroup(int groupId, int userId) throws UserGroupException {
		Group group = groupDao.selectGroupById(groupId);
		
		if(group == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_GROUPNOTEXIST, "Group [id="+groupId+"] doesn't exist");
		}
		
		User user = userDao.selectUserById(userId);
		
		if(user == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTEXIST, "User [id="+userId+"] doesn't exist");
		}
		
		if(!user.getGroups().contains(group)) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_MEMBERSHINOTEXIST, "Membership of User [id="+userId+"] to group [id="+groupId+"] doesn't exist");
		}
		
		user.getGroups().remove(group);
		userDao.updateUser(user);
	}

}
