package com.seaf.core.service.business;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class LoggerListener extends TailerListenerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerListener.class);
	
	private LoggerService 		loggerService;
	private WebSocketSession 	webSocketSession;
	private String				logFile;

	public LoggerListener(LoggerService loggerService, WebSocketSession webSocketSession, String logFile) {
		super();
		this.loggerService = loggerService;
		this.webSocketSession = webSocketSession;
		this.logFile = logFile;
	}


	@Override
    public void handle(String line) {
        loggerService.sendLogMessage(webSocketSession, logFile, line);
    }

	@Override
	public void init(Tailer tailer) {
		LOGGER.info("Initialize Tailer [FileName=" + tailer.getFile().getAbsolutePath() + ", delay="+tailer.getDelay() + "]");
		super.init(tailer);
	}

	@Override
	public void fileNotFound() {
		LOGGER.warn("Tailer : FileNotFound");
		super.fileNotFound();
	}

	@Override
	public void fileRotated() {
		LOGGER.info("Tailer : FileRotated");
		super.fileRotated();
	}

	@Override
	public void handle(Exception ex) {
		LOGGER.error("Tailer : Exception", ex);
		super.handle(ex);
	}
	
	
}
