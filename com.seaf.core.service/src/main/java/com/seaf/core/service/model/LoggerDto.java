package com.seaf.core.service.model;


public class LoggerDto {
	
	private String 	loggerName;
	private String 	logLevel;
	
	public String getLoggerName() {
		return loggerName;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	@Override
	public String toString() {
		return "LoggerSeaf [logger=" + loggerName + ", logLevel=" + logLevel + "]";
	}

}
