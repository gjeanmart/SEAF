package com.seaf.core.service.model.converter;

import com.seaf.core.domain.entity.Group;
import com.seaf.core.service.model.GroupDto;

public class GroupConverter {
	
	public static GroupDto convertGroupToGroupDto(Group group) {
		GroupDto groupDto = new GroupDto();
		groupDto.setId(group.getId());
		groupDto.setName(group.getName());
		groupDto.setDescription(group.getDescription());
		groupDto.setAddedDate(group.getAddedDate());
		groupDto.setLastModifiedDate(group.getLastModifiedDate());
		
		return groupDto;
	}
	
	public static Group convertroGroupDtoToGroup(GroupDto groupDto) {
		Group group = new Group();
		group.setId(groupDto.getId());
		group.setName(groupDto.getName());
		group.setDescription(groupDto.getDescription());
		group.setAddedDate(groupDto.getAddedDate());
		group.setLastModifiedDate(groupDto.getLastModifiedDate());
		
		return group;
	}

}
