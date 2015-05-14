package com.seaf.core.domain.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.seaf.core.domain.entity.Monitoring;
import com.seaf.core.domain.enumeration.Periodicity;

@Component
@Transactional
public class MonitoringDaoImpl implements MonitoringDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringDaoImpl.class);
	
    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public void insertMonitoringValue(Monitoring monitoring) {
		LOGGER.debug("[START] Insertion of {}", monitoring);
		
		sessionFactory.getCurrentSession().save(monitoring);
		
		LOGGER.debug("[END] Insertion of {}", monitoring);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Monitoring> selectMonitoringValueGroupByDate(String key, Periodicity periodicity, Date startDate, Date endDate) {

		LOGGER.debug("[START] Select Monitoring Values for key {} between {} and {} with periodicity {}", key, startDate, endDate, periodicity.getName());
		
		StringBuilder strQuery = new StringBuilder(); 
		
		if(periodicity.equals(Periodicity.REALTIME)) {
			strQuery.append(" SELECT  TO_TIMESTAMP(CONCAT(EXTRACT(DAY  from monitoring_date), '/', EXTRACT(MONTH from monitoring_date),'/', EXTRACT(YEAR from monitoring_date),' ', EXTRACT(HOUR from monitoring_date), ':', EXTRACT(MINUTE from monitoring_date)), 'DD/MM/YYYY HH24:MI') as date, monitoring_key as key, avg(monitoring_value) as value ");
		} else if(periodicity.equals(Periodicity.HOURLY)) {
			strQuery.append(" SELECT  TO_TIMESTAMP(CONCAT(EXTRACT(DAY  from monitoring_date), '/', EXTRACT(MONTH from monitoring_date),'/', EXTRACT(YEAR from monitoring_date),' ', EXTRACT(HOUR from monitoring_date)), 'DD/MM/YYYY HH24') as date, monitoring_key as key, avg(monitoring_value) as value ");
		} else if(periodicity.equals(Periodicity.DAILY)) {
			strQuery.append(" SELECT  TO_TIMESTAMP(CONCAT(EXTRACT(DAY  from monitoring_date), '/', EXTRACT(MONTH from monitoring_date),'/', EXTRACT(YEAR from monitoring_date)), 'DD/MM/YYYY') as date, monitoring_key as key, avg(monitoring_value) as value ");
		} else if(periodicity.equals(Periodicity.WEEKLY)) {
			strQuery.append(" SELECT  TO_TIMESTAMP(CONCAT(EXTRACT(DAY  from monitoring_date), '/', EXTRACT(MONTH from monitoring_date),'/', EXTRACT(YEAR from monitoring_date)), 'DD/MM/YYYY') as date, monitoring_key as key, avg(monitoring_value) as value ");
		} else if(periodicity.equals(Periodicity.MONTHLY)) {
			strQuery.append(" SELECT  TO_TIMESTAMP(CONCAT(EXTRACT(DAY  from monitoring_date), '/', EXTRACT(MONTH from monitoring_date)), 'DD/MM') as date, monitoring_key as key, avg(monitoring_value) as value ");
		} else {
			strQuery.append(" SELECT  TO_TIMESTAMP(CONCAT(EXTRACT(DAY  from monitoring_date), '/', EXTRACT(MONTH from monitoring_date),'/', EXTRACT(YEAR from monitoring_date),' ', EXTRACT(HOUR from monitoring_date)), 'DD/MM/YYYY HH24') as date, monitoring_key as key, avg(monitoring_value) as value ");
		}
		
		strQuery.append(" FROM SEAF_MONITORING WHERE monitoring_key = :key ");
		strQuery.append(" AND monitoring_date between :startdate AND :enddate ");
		strQuery.append(" GROUP BY date, key ORDER BY date");

		SQLQuery query = this.sessionFactory.getCurrentSession().createSQLQuery(strQuery.toString());

		query.addScalar("date", StandardBasicTypes.TIMESTAMP );
		query.addScalar("key", StandardBasicTypes.STRING );
		query.addScalar("value", StandardBasicTypes.DOUBLE );

		query.setParameter("key", key);
		query.setParameter("startdate", startDate);
		query.setParameter("enddate", endDate);
		
		query.setResultTransformer(Transformers.aliasToBean(Monitoring.class));
		
		// Execute the query
		List<Monitoring> result = query.list();
		
		LOGGER.debug("[END] Select Monitoring Values for key {} between {} and {} with periodicity {} : {} rows selected", key, startDate, endDate, periodicity.getName(), result.size());
		
		return result;
	}

	@Override
	public void deleteMonitoringValue(Date beforeDate) {
		LOGGER.debug("[START] Delete monitoring values before {}", beforeDate);
		
		Query query = sessionFactory.getCurrentSession().createQuery("delete Monitoring m where m.monitoringKey.date < :date");
		query.setDate("date", beforeDate);
		
		int resultSize = query.executeUpdate();
		
		LOGGER.debug("[END] Delete monitoring values before {} : {} rows removed", beforeDate, resultSize);
	}

}
