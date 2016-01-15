package com.seaf.core.connector.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seaf.core.connector.rest.exception.ApiException;
import com.seaf.core.service.business.UserGroupService;
import com.seaf.core.service.business.exception.UserGroupException;
import com.seaf.core.service.model.GroupDto;
import com.seaf.core.service.model.UserDto;
import com.seaf.core.service.model.utils.EnvelopeList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * UserController
 * @author Greg
 *
 */
@RestController
@RequestMapping("/api/v1")
@Api(value = "UserGroupApi", description = "Operations on users and groups")
public class UserGroupController {

    private static final Logger LOGGER  						= LoggerFactory.getLogger(UserGroupController.class);

	public static final String 	URI_GETUSER 					= "/user/{userId}";
	public static final String 	URI_GETALLUSERS 				= "/user";
	public static final String 	URI_CREATEUSER 					= "/user";
	public static final String 	URI_MODIFYUSER 					= "/user/{userId}";
	public static final String 	URI_DELETEUSER 					= "/user/{userId}";
	public static final String 	URI_GROUPSOFUSER 				= "/user/{userId}/group";
	public static final String 	URI_GETGROUP 					= "/group/{groupId}";
	public static final String 	URI_GETALLGROUPS 				= "/group";
	public static final String 	URI_CREATEGROUP 				= "/group";
	public static final String 	URI_MODIFYGROUP 				= "/group/{groupId}";
	public static final String 	URI_DELETEGROUP 				= "/group/{groupId}";
	public static final String 	URI_USERSOFGROUP 				= "/group/{groupId}/user";
	public static final String 	URI_SUBSCRIBEGROUP 				= "/user/{userId}/group/{groupId}";
	public static final String 	URI_UNSUBSCRIBEGROUP 			= "/user/{userId}/group/{groupId}";
	
	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = URI_GETALLUSERS, method = RequestMethod.GET)
	@ApiOperation(value = "List users")
	public EnvelopeList getUser(
			@ApiParam(name="search", 	value="The query for search", 						required=false) @RequestParam(value = "search", required=false) String searchQuery,
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) {
		LOGGER.debug("[START] Get users with search query '{}'", searchQuery);

		if(searchQuery != null && searchQuery.equals(""))
			searchQuery = null;
		
		EnvelopeList envelope = userGroupService.getUsers(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);

		LOGGER.debug("[END] Get users with search query '{}' : {} result(s)", searchQuery, envelope.getTotal());

		return envelope;
	}

	@RequestMapping(value = URI_GETUSER, method = RequestMethod.GET)
	@ApiOperation(value = "Detail of a user")
	public UserDto getUser(
			@ApiParam(name="userId", value="The ID of the user", required=true) @PathVariable(value = "userId") int userId) 
					throws UserGroupException {
		try {
			LOGGER.debug("[START] Get user with id {}", userId);

			UserDto user = userGroupService.getUser(userId);

			LOGGER.debug("[END] Get user with id {} : {}", userId, user);

			return user;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
	}

    @RequestMapping(value = URI_CREATEUSER, method = RequestMethod.POST)
    @ApiOperation(value = "Add a user")
    public @ResponseStatus(HttpStatus.CREATED) @ResponseBody UserDto createUser(
    		@ApiParam(name="user", value="The user added", required=true) @RequestBody UserDto user) 
    				throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Add user {}", user);

    		UserDto userInserted = userGroupService.addUser(user);

	    	LOGGER.debug("[END] Add user {}", userInserted);

	        return userInserted;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }

    @RequestMapping(value = URI_MODIFYUSER, method = RequestMethod.PUT)
    @ApiOperation(value = "Modify a user")
    public @ResponseBody UserDto modifyUser(
    		@ApiParam(name="userId", value="The ID of the user", required=true) @PathVariable(value = "userId") int userId, 
    		@ApiParam(name="user", value="The user modified", required=true) @RequestBody UserDto user) 
    				throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Modify user [id={}] : {}", userId, user);

    		UserDto userInserted = userGroupService.modifyUser(userId, user);

	    	LOGGER.debug("[END] Modify user [id={}] : {}", userId, user);

	        return userInserted;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }

    @RequestMapping(value = URI_DELETEUSER, method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a user")
    public void deleteUser(
			@ApiParam(name="userId", value="The ID of the user", required=true) @PathVariable(value = "userId") int userId) 
					throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Delete user [id={}]", userId);

    		userGroupService.deleteUser(userId);

	    	LOGGER.debug("[END] Delete user [id={}]", userId);

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }
    
	@RequestMapping(value = URI_GETALLGROUPS, method = RequestMethod.GET)
	@ApiOperation(value = "List all group")
	public EnvelopeList getGroup(
			@ApiParam(name="search", 	value="The query for search", 						required=false) @RequestParam(value = "search", required=false) String searchQuery,
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) {
		LOGGER.debug("[START] Get groups with search query '{}'", searchQuery);

		if(searchQuery != null && searchQuery.equals(""))
			searchQuery = null;
		
		EnvelopeList result = userGroupService.getGroups(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);

		LOGGER.debug("[END] Get groups  with search query '{}'  : {} result(s)", searchQuery, result.getTotal());

		return result;
	}

	@RequestMapping(value = URI_GETGROUP, method = RequestMethod.GET)
	@ApiOperation(value = "Detail of a group")
	public GroupDto getGroup(
			@ApiParam(name="groupId", value="The ID of the group", required=true) @PathVariable(value = "groupId") int groupId) 
					throws UserGroupException {
		try {
			LOGGER.debug("[START] Get group id {}", groupId);

			GroupDto group = userGroupService.getGroup(groupId);

			LOGGER.debug("[END] Get group with id {} : {}", groupId, group);

			return group;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
	}

    @RequestMapping(value = URI_CREATEGROUP, method = RequestMethod.POST)
    @ApiOperation(value = "Add a group")
    public @ResponseStatus(HttpStatus.CREATED) @ResponseBody GroupDto createGroup(
    		@ApiParam(name="group", value="The group added", required=true) @RequestBody GroupDto group) 
    				throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Add group {}", group);

    		GroupDto groupInserted = userGroupService.addGroup(group);

	    	LOGGER.debug("[END] Add group {}", groupInserted);

	        return groupInserted;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }

    @RequestMapping(value = URI_MODIFYGROUP, method = RequestMethod.PUT)
    @ApiOperation(value = "Modify a group")
    public @ResponseBody GroupDto modifyGroup(
    		@ApiParam(name="groupId", value="The ID of the group", required=true) @PathVariable(value = "groupId") int groupId, 
    		@ApiParam(name="group", value="The group modified", required=true) @RequestBody GroupDto group) 
    				throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Modify group [id={}] : {}", groupId, group);

    		GroupDto groupInserted = userGroupService.modifyGroup(groupId, group);

	    	LOGGER.debug("[END] Modify group [id={}] : {}", groupId, group);

	        return groupInserted;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }

    @RequestMapping(value = URI_DELETEGROUP, method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a group")
    public void deleteGroup(
			@ApiParam(name="groupId", value="The ID of the group", required=true) @PathVariable(value = "groupId") int groupId) 
					throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Delete group [id={}]", groupId);

    		userGroupService.deleteGroup(groupId);

	    	LOGGER.debug("[END] Modify group [id={}]", groupId);

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }
    
    @RequestMapping(value = URI_GROUPSOFUSER, method = RequestMethod.GET)
    @ApiOperation(value = "Get groups of user")
    public @ResponseBody List<GroupDto> getGroupsOfUser(
			@ApiParam(name="userId", value="The ID of the user", required=true) @PathVariable(value = "userId") int userId) 
					throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Get group of user [id={}]", userId);

    		List<GroupDto> groups = userGroupService.getGroupOfUser(userId);

	    	LOGGER.debug("[END] Get group of user [id={}]", userId);
    		
    		return groups;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }  
    
    @RequestMapping(value = URI_USERSOFGROUP, method = RequestMethod.GET)
    @ApiOperation(value = "Get users of group")
    public @ResponseBody List<UserDto> getUsersOfGroup(
			@ApiParam(name="groupId", value="The ID of the group", required=true) @PathVariable(value = "groupId") int groupId) 
					throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Get users of group [id={}]", groupId);

    		List<UserDto> users = userGroupService.getUserOfGroup(groupId);

	    	LOGGER.debug("[END] Get users of group  [id={}]", groupId);
    		
    		return users;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
    }     
    
    @RequestMapping(value = URI_SUBSCRIBEGROUP, method = RequestMethod.PUT)
    @ApiOperation(value = "Subscribe to a group")
    public @ResponseBody GroupDto subscribeGroup(
    		@ApiParam(name="groupId", value="The ID of the group", required=true) @PathVariable(value = "groupId") int groupId, 
    		@ApiParam(name="userId", value="The ID of the user", required=true) @PathVariable(value = "userId") int userId) 
    				throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Subscribe to a group [id={}] for user [id={}]", groupId, userId);

    		GroupDto group = userGroupService.subscribeGroup(groupId, userId);

	    	LOGGER.debug("[END] Subscribe to a group [id={}] for user [id={}]", groupId, userId);

	        return group;

		} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		} 
    }

    @RequestMapping(value = URI_UNSUBSCRIBEGROUP, method = RequestMethod.DELETE)
    @ApiOperation(value = "Unsubscribe to a group")
    public void unsubscribeGroup(
    		@ApiParam(name="groupId", value="The ID of the group", required=true) @PathVariable(value = "groupId") int groupId, 
    		@ApiParam(name="userId", value="The ID of the user", required=true) @PathVariable(value = "userId") int userId) 
    				throws UserGroupException {
    	try {
    		LOGGER.debug("[START] Unsubscribe to a group [id={}] for user [id={}]", groupId, userId);

    		userGroupService.unsubscribeGroup(groupId, userId);

	    	LOGGER.debug("[END] Unsubscribe to a group [id={}] for user [id={}]", groupId, userId);

    	} catch (UserGroupException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		} 
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * 
     * EXCEPTION HANDLER
     * * * * * * * * * * * * * * * * * * * * * */

	@ExceptionHandler(UserGroupException.class)
	public @ResponseStatus(HttpStatus.NOT_FOUND) @ResponseBody ApiException userGroupExceptionHandler(UserGroupException ex, HttpServletResponse response, HttpServletRequest request){
		
		ApiException apiException = new ApiException();
		
		switch (ex.getInternalCode()) {
	        case UserGroupException.EXCEPTION_CODE_USERNOTEXIST:  		
	        	apiException.setHttpCode(HttpStatus.NOT_FOUND.value());
				apiException.setHttpStatus(HttpStatus.NOT_FOUND.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_USERALREADYEXIST:   		
	        	apiException.setHttpCode(HttpStatus.CONFLICT.value());
				apiException.setHttpStatus(HttpStatus.CONFLICT.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_GROUPNOTEXIST:   		
	        	apiException.setHttpCode(HttpStatus.NOT_FOUND.value());
				apiException.setHttpStatus(HttpStatus.NOT_FOUND.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_GROUPALREADYEXIST:   		
	        	apiException.setHttpCode(HttpStatus.CONFLICT.value());
				apiException.setHttpStatus(HttpStatus.CONFLICT.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_MEMBERSHINOTEXIST:   		
	        	apiException.setHttpCode(HttpStatus.NOT_FOUND.value());
				apiException.setHttpStatus(HttpStatus.NOT_FOUND.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_MEMBERSHIPALREADYEXIST:   		
	        	apiException.setHttpCode(HttpStatus.CONFLICT.value());
				apiException.setHttpStatus(HttpStatus.CONFLICT.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_USERNOTVALID:   		
	        	apiException.setHttpCode(HttpStatus.BAD_REQUEST.value());
				apiException.setHttpStatus(HttpStatus.BAD_REQUEST.name());
				break;
	        case UserGroupException.EXCEPTION_CODE_INTERNALERROR:   		
	        	apiException.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				break;
	        default: 
	        	apiException.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        	apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
	        	break;
		}
		
		apiException.setMessage(ex.getMessage());
		apiException.setInternalErrorCode(ex.getInternalCode());
		apiException.setUrl(request.getRequestURL().toString());
		
	    return apiException;
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) @ResponseBody ApiException ExceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request){
		LOGGER.error("Error while requesting URL '"+request.getRequestURL().toString()+"'", ex);
		
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
		apiException.setMessage(ex.getMessage());
        apiException.setUrl(request.getRequestURL().toString());
        apiException.setInternalErrorCode(UserGroupException.EXCEPTION_CODE_INTERNALERROR);

	    return apiException;
	}
}
