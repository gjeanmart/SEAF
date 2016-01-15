package com.seaf.core.utils;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class SpringStartup implements InitializingBean {
	
	@Value("${logs.level}")
	private String 	logLevel;
	@Value("${logs.pattern}")
	private String 	logPattern;
	@Value("${logs.fileName}")
	private String 	logFileName;
	@Value("${logs.archive.directory}")
	private String 	archiveDirectory;
	@Value("${logs.archive.fileNamePattern}")
	private String 	archiveFileNamePattern;
	@Value("${logs.archive.maxHistoryDay}")
	private int 	archiveMaxHistory;
	@Value("${logs.archives.maxFileSize}")
	private String 	archiveMaxFileSize;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init() {
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
		
	    PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
	    logEncoder = new PatternLayoutEncoder();
	    logEncoder.setContext(logCtx);
	    logEncoder.setPattern(logPattern);
	    logEncoder.start();
		
		RollingFileAppender logFileAppender = new RollingFileAppender();
	    logFileAppender.setContext(logCtx);
	    logFileAppender.setName("logFile");
	    logFileAppender.setEncoder(logEncoder);
	    logFileAppender.setAppend(true);
	    logFileAppender.setFile(logFileName);		
	    
	    SizeAndTimeBasedFNATP sizeAndTimeBasedFNATP = new SizeAndTimeBasedFNATP();
	    sizeAndTimeBasedFNATP.setMaxFileSize(archiveMaxFileSize);
	    
		TimeBasedRollingPolicy logFilePolicy = new TimeBasedRollingPolicy();
		logFilePolicy.setContext(logCtx);
		logFilePolicy.setParent(logFileAppender);
		logFilePolicy.setFileNamePattern(archiveDirectory+archiveFileNamePattern);
		logFilePolicy.setMaxHistory(archiveMaxHistory);
		logFilePolicy.setTimeBasedFileNamingAndTriggeringPolicy(sizeAndTimeBasedFNATP);
		logFilePolicy.start();
		 

	    logFileAppender.setRollingPolicy(logFilePolicy);
	    logFileAppender.start();
	    
	    Logger log = logCtx.getLogger("ROOT");
	    log.setAdditive(true);
	    log.setLevel(Level.toLevel(logLevel));
	    log.addAppender(logFileAppender);
	    
	}
	

}
