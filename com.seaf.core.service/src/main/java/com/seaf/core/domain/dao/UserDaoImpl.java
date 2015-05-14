package com.seaf.core.domain.dao;

import java.util.List;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.seaf.core.domain.entity.User;

@Component
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    

	@SuppressWarnings("unchecked")
	private List<User> selectUsersGeneric(int pageNumber, int pageSize,	String sortAttribute, String sortDirection) {
		
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		// Pagination
		if(pageNumber != 0 && pageSize != 0) {
			criteria.setFirstResult((pageNumber - 1) * pageSize);
			criteria.setMaxResults(pageSize);
		}
		
		// Sorting
		if(sortAttribute != null) {
			Order order = null;
			if(sortDirection != null && sortDirection.equals("asc")) {
				order = Order.asc(sortAttribute);
			} else {
				order = Order.desc(sortAttribute);
			}
			criteria.addOrder(order);
		}
			
		return (List<User>) criteria.list();
	}
	
	@Override
	public List<User> selectUsers() {
		return selectUsersGeneric(0,0,null,null);
	}
	
	@Override
	public List<User> selectUsers(int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		return selectUsersGeneric(pageNumber,pageSize,sortAttribute,sortDirection);
	}

	@Override
	public List<User> selectUsers(int pageNumber, int pageSize) {
		return selectUsersGeneric(pageNumber,pageSize,null,null);
	}
	
	@Override
	public List<User> selectUsers(String sortAttribute, String sortDirection) {
		return selectUsersGeneric(0,0,sortAttribute,sortDirection);
	}
	
	@SuppressWarnings("unchecked")
	private List<User> selectUsersGeneric(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		FullTextSession fullTextSession = Search.getFullTextSession(this.sessionFactory.getCurrentSession());

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.keyword()
				.fuzzy()
				.withPrefixLength(1)
				.onField("firstName")
				.andField("lastName")
				.andField("email")
				.matching(searchQuery.toLowerCase())
				.createQuery();
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, User.class);
					
					
		// Pagination
		if(pageNumber != 0 && pageSize != 0) {
			fullTextQuery.setFirstResult((pageNumber - 1) * pageSize);
			fullTextQuery.setMaxResults(pageSize);
		}
					
		// Sorting
		if(sortAttribute != null) {
			SortField field = null;	
			if(sortDirection != null && sortDirection.equals("asc")) {
				field = new SortField(sortAttribute, SortField.Type.STRING, false);
			} else {
				field = new SortField(sortAttribute, SortField.Type.STRING, true);
			}
			Sort sort = new Sort(field);
			fullTextQuery.setSort(sort);
		}
	         
		return fullTextQuery.list();
	}
	 
	@Override
	public List<User> selectUsers(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		return selectUsersGeneric(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);
	}
	
	@Override
	public List<User> selectUsers(String searchQuery, String sortAttribute, String sortDirection) {
		return selectUsersGeneric(searchQuery, 0, 0, sortAttribute, sortDirection);
	}

	@Override
	public List<User> selectUsers(String searchQuery, int pageNumber, int pageSize) {
		return selectUsersGeneric(searchQuery, pageNumber, pageSize, null, null);
	}

	@Override
	public List<User> selectUsers(String searchQuery) {
		return selectUsersGeneric(searchQuery, 0, 0, null, null);
	}

	
	@Override
	public long countUsers() {
		Query countQuery = this.sessionFactory.getCurrentSession().createQuery("Select count (u.id) from User u");
		
		return (long) countQuery.uniqueResult();
	}
	
	@Override
	public long countUsers(String searchQuery) {
		FullTextSession fullTextSession = Search.getFullTextSession(this.sessionFactory.getCurrentSession());
	         
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder
			.keyword()
			.fuzzy()
			.withPrefixLength(1)
			.onField("firstName")
			.andField("lastName")
			.andField("email")
			.matching(searchQuery.toLowerCase()).createQuery();
	
		org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, User.class);
	         
		return fullTextQuery.list().size();
	}

	@Override
	public User selectUserById(int userId) {
		User user = null;
		
		try {
			user = (User) this.sessionFactory.getCurrentSession().load(User.class, userId);
		} catch (ObjectNotFoundException e) {
			return null;
		}

        return user;
	}
	
	@Override
	public User selectUserByEmail(String userEmail) {
        Query query = this.sessionFactory.getCurrentSession().createQuery("from User u where u.email=:email");
        query.setParameter("email", userEmail);

        @SuppressWarnings("unchecked")
        List<User> users = query.list();

        if (users == null || users.isEmpty()) {
            return null;
        }

        return users.get(0);
	}

	@Override
	public void insertUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().delete(user);
	}



}
