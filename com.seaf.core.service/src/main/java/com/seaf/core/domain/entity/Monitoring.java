package com.seaf.core.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.seaf.core.domain.entity.key.MonitoringKey;

@Entity
@Proxy(lazy=false)
@Table(name = "SEAF_MONITORING")
public class Monitoring implements Serializable{

	private static final long serialVersionUID = 6803287582623888577L;
	
	public Monitoring(MonitoringKey monitoringKey, double value) {
		this.monitoringKey = monitoringKey;
		this.value = value;
	}
	
	public Monitoring(Date date, String key, double value) {
		this.monitoringKey = new MonitoringKey(date, key);
		this.value = value;
	}
	
	public Monitoring() {

	}

	@Id
	@EmbeddedId
	private MonitoringKey monitoringKey = new MonitoringKey();
	
	@Column(name="MONITORING_VALUE", nullable = false)
	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public MonitoringKey getMonitoringKey() {
		return monitoringKey;
	}

	public void setMonitoringKey(MonitoringKey monitoringKey) {
		this.monitoringKey = monitoringKey;
	}
	
	public Date getDate() {
		return this.monitoringKey.getDate();
	}
	public void setDate(Date date) {
		this.monitoringKey.setDate(date);
	}
	public String getKey() {
		return this.monitoringKey.getKey();
	}
	public void setKey(String key) {
		this.monitoringKey.setKey(key);
	}

	@Override
	public String toString() {
		return "Monitoring [monitoringKey=" + monitoringKey + ", value="
				+ value + "]";
	}
 
}
