package com.seaf.core.service.model.converter;

import com.seaf.core.domain.entity.Group;
import com.seaf.core.domain.entity.User;
import com.seaf.core.service.model.GroupDto;
import com.seaf.core.service.model.UserDto;

public class UserConverter {
	
	public static UserDto convertUserToUserDto(User user) {
		UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setBirthDate(user.getBirthDate());
		userDto.setAddedDate(user.getAddedDate());
		userDto.setLastModifiedDate(user.getLastModifiedDate());
		
		for(Group group : user.getGroups()) {
			userDto.getGroups().add(GroupConverter.convertGroupToGroupDto(group));
		}
		
		return userDto;
	}
	
	public static User convertUserDtoToUser(UserDto userDto) {
		User user = new User();
		
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setPassword(user.getPassword());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setBirthDate(userDto.getBirthDate());
		user.setAddedDate(userDto.getAddedDate());
		user.setLastModifiedDate(userDto.getLastModifiedDate());
		
		for(GroupDto groupDto : userDto.getGroups()) {
			user.getGroups().add(GroupConverter.convertroGroupDtoToGroup(groupDto));
		}
		
		return user;		
	}	

}
