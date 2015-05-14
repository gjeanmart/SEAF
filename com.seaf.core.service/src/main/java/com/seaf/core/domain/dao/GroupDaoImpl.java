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

import com.seaf.core.domain.entity.Group;

@Component
@Transactional
public class GroupDaoImpl implements GroupDao {
	
    @Autowired
    private SessionFactory sessionFactory;
    
	@SuppressWarnings("unchecked")
	private List<Group> selectGroupsGeneric(int pageNumber, int pageSize,	String sortAttribute, String sortDirection) {
		
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Group.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
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
			
		return (List<Group>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<Group> selectGroupsGeneric(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		FullTextSession fullTextSession = Search.getFullTextSession(this.sessionFactory.getCurrentSession());

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Group.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.keyword()
				.fuzzy()
				.withPrefixLength(1)
				.onField("name")
				.matching(searchQuery.toLowerCase())
				.createQuery();
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Group.class);
					
					
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
	public List<Group> selectGroups(String searchQuery, int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		return this.selectGroupsGeneric(searchQuery, pageNumber, pageSize, sortAttribute, sortDirection);
	}

	@Override
	public List<Group> selectGroups(String searchQuery, String sortAttribute, String sortDirection) {
		return this.selectGroupsGeneric(searchQuery, 0, 0, sortAttribute, sortDirection);
	}

	@Override
	public List<Group> selectGroups(String searchQuery) {
		return this.selectGroupsGeneric(searchQuery, 0, 0, null, null);
	}
	
	@Override
	public List<Group> selectGroups(String searchQuery, int pageNumber, int pageSize) {
		return this.selectGroupsGeneric(searchQuery, pageNumber, pageSize, null, null);
	}

	@Override
	public List<Group> selectGroups(int pageNumber, int pageSize, String sortAttribute, String sortDirection) {
		return this.selectGroupsGeneric(pageNumber, pageSize, sortAttribute, sortDirection);
	}

	@Override
	public List<Group> selectGroups(int pageNumber, int pageSize) {
		return this.selectGroupsGeneric(pageNumber, pageSize, null, null);
	}

	@Override
	public List<Group> selectGroups(String sortAttribute, String sortDirection) {
		return this.selectGroupsGeneric(0, 0, sortAttribute, sortDirection);
	}

	@Override
	public List<Group> selectGroups() {
		return this.selectGroupsGeneric(0, 0, null, null);
	}

	@Override
	public long countGroups() {
		Query countQuery = this.sessionFactory.getCurrentSession().createQuery("Select count (g.id) from Group g");
		
		return (long) countQuery.uniqueResult();
	}

	@Override
	public long countGroups(String searchQuery) {
		FullTextSession fullTextSession = Search.getFullTextSession(this.sessionFactory.getCurrentSession());
        
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Group.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder
			.keyword()
			.fuzzy()
			.withPrefixLength(1)
			.onField("name")
			.matching(searchQuery.toLowerCase()).createQuery();
	
		org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Group.class);
	         
		return fullTextQuery.list().size();
	}
    
    
 
	@Override
	public Group selectGroupById(int groupId) {
		Group group = null;
		
		try {
			group = (Group) this.sessionFactory.getCurrentSession().load(Group.class, groupId);
		} catch (ObjectNotFoundException e) {
			return null;
		}
        return group;
	}

	@Override
	public Group selectGroupByName(String groupName) {
        Query query = this.sessionFactory
        		.getCurrentSession()
        		.createQuery("from Group g where g.name=:name");
        query.setParameter("name", groupName);

        @SuppressWarnings("unchecked")
        List<Group> groups = query.list();

        if (groups == null || groups.isEmpty()) {
            return null;
        }
 
        return groups.get(0);
	}

	@Override
	public void insertGroup(Group group) {
		sessionFactory.getCurrentSession().save(group);
	}

	@Override
	public void updateGroup(Group group) {
		sessionFactory.getCurrentSession().update(group);
	}

	@Override
	public void deleteGroup(Group group) {
		sessionFactory.getCurrentSession().delete(group);
	}


}
