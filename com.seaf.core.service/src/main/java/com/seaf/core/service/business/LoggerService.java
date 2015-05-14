package com.seaf.core.service.business;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.seaf.core.service.business.exception.LoggerException;
import com.seaf.core.service.model.LoggerDto;
import com.seaf.core.service.model.utils.EnvelopeList;

public interface LoggerService {
	
	public EnvelopeList 	getLogger(int pageNumber, int pageSize, String sortAttribute, String sortDirection) throws LoggerException;
	public void 			setLogLevel(LoggerDto logger) 	throws LoggerException;
	
	public List<String>		getLogFiles();
	public void 			startTailerOnFile(WebSocketSession session, String logFile);
	public void 			stopTailerOnFile(WebSocketSession session, String logFile);
	public void				registerOpenConnection(WebSocketSession session);
	public void 			registerCloseConnection(WebSocketSession session);
	public void				sendLogMessage(WebSocketSession session, String logFile, String logMessage);
}
