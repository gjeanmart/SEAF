package com.seaf.core.service.model.validation;

import java.util.regex.Pattern;

import com.seaf.core.domain.dao.UserDao;
import com.seaf.core.domain.entity.User;
import com.seaf.core.service.business.exception.UserGroupException;
import com.seaf.core.service.model.UserDto;

public final class UserDtoValidation {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	
	public static void validateUserDto(UserDto userDto) throws UserGroupException {
		
		if(userDto == null)
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "User cannot be null");
		
		if(userDto.getFirstName() == null || userDto.getFirstName().trim().equals(""))
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "User first name cannot be empty");
		
		if(userDto.getLastName() == null || userDto.getLastName().trim().equals(""))
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "User last name cannot be empty");
		
		if(userDto.getEmail() == null || userDto.getEmail().trim().equals(""))
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "User email cannot be empty");
		
		//TODO Check birthdate
		
		if(!pattern.matcher(userDto.getEmail()).matches())
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTVALID, "User email ["+userDto.getEmail()+"] is not in valid format");
		
	}
	
	public static void checkDuplicateUserByEmail(UserDao userDao, String userEmail) throws UserGroupException {
		User user = userDao.selectUserByEmail(userEmail);
		
		if(user != null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERALREADYEXIST, "User [email="+userEmail+"] already exists");
		}
	}
	
	public static User checkNotExistUserById(UserDao userDao, int userId) throws UserGroupException {
		User user = userDao.selectUserById(userId);
		
		if(user == null) {
			throw new UserGroupException(UserGroupException.EXCEPTION_CODE_USERNOTEXIST, "User [id="+userId+"] doesn't exist");
		}
		
		return user;
	}

}
