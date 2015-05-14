package com.seaf.core.service.model;


public class ThreadDto {
	
	private long id;
	private String name;
	private int priority;
	private boolean daemon;
	private boolean interrupted;
	private String state;
	private boolean alive;
	private String groupName;
	private String parentName;
	
	public ThreadDto(long id, String name, int priority, boolean daemon, boolean interrupted, String state, boolean alive, String groupName, String parentName) {
		this.id = id;
		this.name = name;
		this.priority = priority;
		this.daemon = daemon;
		this.interrupted = interrupted;
		this.state = state;
		this.alive = alive;
		this.groupName = groupName;
		this.parentName = parentName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean isDaemon() {
		return daemon;
	}
	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}
	public boolean isInterrupted() {
		return interrupted;
	}
	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "ThreadDto [id=" + id + ", name=" + name + ", priority="
				+ priority + ", daemon=" + daemon + ", interrupted="
				+ interrupted + ", state=" + state + ", alive=" + alive
				+ ", groupName=" + groupName + ", parentName=" + parentName
				+ "]";
	}


	
}
