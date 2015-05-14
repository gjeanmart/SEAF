package com.seaf.core.domain.entity.key;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MonitoringKey implements Serializable {

	private static final long serialVersionUID = -6128803574419510248L;
	
	@Column(name="MONITORING_DATE", nullable = false)
	private Date date;
	
	@Column(name="MONITORING_KEY", nullable = false)
	private String key;
	
	public MonitoringKey() {

	}
	
	public MonitoringKey(Date date, String key) {
		this.date = date;
		this.key = key;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitoringKey other = (MonitoringKey) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MonitoringKey [date=" + date + ", key=" + key + "]";
	}
	
	
}
