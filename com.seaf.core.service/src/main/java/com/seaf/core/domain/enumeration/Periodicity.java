package com.seaf.core.domain.enumeration;

public enum Periodicity {

	REALTIME	(0, "HOURLY"),
	HOURLY		(1, "HOURLY"), 
	DAILY		(2, "DAILY"), 
	WEEKLY		(3, "WEEKLY"), 
	MONTHLY		(4, "MONTHLY");

	private int id = 0;
	private String name;

	Periodicity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
