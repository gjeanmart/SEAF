package com.seaf.core.domain.dao;

import java.util.List;

import com.seaf.core.domain.entity.User;

public interface UserDao {
	
	public List<User> 		selectUsers				(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public List<User> 		selectUsers				(String searchQuery, String sortAttribute, String sortDirection);
	public List<User> 		selectUsers				(String searchQuery, int pageNumber, int pageSize);
	public List<User> 		selectUsers				(String searchQuery);
	
	public List<User> 		selectUsers				(int pageNumber, int pageSize, String sortAttribute, String sortDirection);
	public List<User> 		selectUsers				(int pageNumber, int pageSize);
	public List<User> 		selectUsers				(String sortAttribute, String sortDirection);
	public List<User> 		selectUsers				();
	
	public long		 		countUsers				(String searchQuery);
	public long		 		countUsers				();
	
	public User 			selectUserById			(int userId);
	
	public User 			selectUserByEmail		(String userEmail);
	
	public void 			insertUser				(User user);
	
	public void 			updateUser				(User user);
	
	public void				deleteUser				(User user);

}
