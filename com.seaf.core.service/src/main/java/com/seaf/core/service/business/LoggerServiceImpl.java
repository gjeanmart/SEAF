package com.seaf.core.service.business;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import ch.qos.logback.classic.LoggerContext;

import com.seaf.core.service.business.exception.LoggerException;
import com.seaf.core.service.model.LoggerDto;
import com.seaf.core.service.model.comparator.LoggerDtoComparator;
import com.seaf.core.service.model.utils.EnvelopeList;
import com.seaf.core.service.websocket.KeyWebSocketTailer;

@Component
public class LoggerServiceImpl implements LoggerService {

	public static final String 	LOGBACK_CLASSIC 			= "ch.qos.logback.classic";
	public static final String 	LOGBACK_CLASSIC_LOGGER 		= "ch.qos.logback.classic.Logger";
	public static final String 	LOGBACK_CLASSIC_LEVEL 		= "ch.qos.logback.classic.Level";
	public static final int		TAIL_DEFAULT_DELAY_MILIS	= 2000;
	private static final Logger LOGGER 						= LoggerFactory.getLogger(LoggerServiceImpl.class);
	private static final String SEPRATOR_FILE_MESSAGE		= "[###]";
	
	private Map<KeyWebSocketTailer, Tailer>		tailers				= new ConcurrentHashMap<KeyWebSocketTailer, Tailer>();
	private int									indexTailer			= 1;
	private Set<WebSocketSession> 				webSocketSessions 	= java.util.Collections.synchronizedSet(new HashSet<WebSocketSession>());

	@Value("${logs.fileName}")
	private String 	logFileName;
	
	@Value("${logs.archive.directory}")
	private String 	archiveDirectory;
	
	
	@Override
	public EnvelopeList getLogger(int pageNumber, int pageSize, String sortAttribute, String sortDirection) throws LoggerException {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		List<LoggerDto> loggerList = new ArrayList<LoggerDto>();

		List<ch.qos.logback.classic.Logger> loggers = loggerContext.getLoggerList();
		for (ch.qos.logback.classic.Logger logger : loggers) {
			LoggerDto loggerSeaf = new LoggerDto();
			loggerSeaf.setLoggerName(logger.getName());
			loggerSeaf.setLogLevel(logger.getLevel() != null ? logger.getLevel().toString() : "");

			loggerList.add(loggerSeaf);
		}
		
		//Sorting
		if(sortAttribute != null && sortDirection != null) {
			Collections.sort(loggerList, new LoggerDtoComparator(sortAttribute, sortDirection));
		}
		
		//Paging
		List<LoggerDto> data = null;
		if(pageNumber != 0 && pageSize != 0) {
			data = loggerList.subList((pageNumber - 1)*pageSize, (pageNumber*pageSize>loggerList.size()) ? loggerList.size() : pageNumber*pageSize);
		} else {
			data = loggerList;
		}
		
		EnvelopeList envelope = new EnvelopeList();
		envelope.setData(data);
		envelope.setTotal(loggerList.size());

		return envelope;
	}

	@Override
	public void setLogLevel(LoggerDto loggerSeaf) throws LoggerException {
		String logLevelUpper = (loggerSeaf.getLogLevel() == null) ? "OFF" : loggerSeaf.getLogLevel().toString().toUpperCase();

		try {
			Package logbackPackage = Package.getPackage(LOGBACK_CLASSIC);
			if (logbackPackage == null) {
				LOGGER.info("Logback is not in the classpath!");
				throw new LoggerException("Logback is not in the classpath!");
			}

			// Use ROOT logger if given logger name is blank.
			if ((loggerSeaf.getLoggerName() == null) || loggerSeaf.getLoggerName().trim().isEmpty() || loggerSeaf.getLoggerName().equals("root")) {
				loggerSeaf.setLoggerName((String) getFieldValue(LOGBACK_CLASSIC_LOGGER, "ROOT_LOGGER_NAME"));
			}

			// Obtain logger by the name
			Logger loggerObtained = LoggerFactory.getLogger(loggerSeaf.getLoggerName());
			if (loggerObtained == null) {
				// I don't know if this case occurs
				LOGGER.warn("No logger for the name: {}", loggerSeaf.getLoggerName());
				throw new LoggerException("No logger for the name: " + loggerSeaf.getLoggerName());
			}

			Object logLevelObj = getFieldValue(LOGBACK_CLASSIC_LEVEL, logLevelUpper);
			
			if (logLevelObj == null) {
				LOGGER.warn("No such log level: {}", logLevelUpper);
				throw new LoggerException("No such log level: " + logLevelUpper);
			}

			Class<?>[] paramTypes = { logLevelObj.getClass() };
			Object[] params = { logLevelObj };

			Class<?> clz = Class.forName(LOGBACK_CLASSIC_LOGGER);
			Method method = clz.getMethod("setLevel", paramTypes);
			method.invoke(loggerObtained, params);

			LOGGER.debug("Log level set to {} for the logger '{}'", logLevelUpper, loggerSeaf.getLoggerName());

		} catch (Exception e) {
			LOGGER.warn("Couldn't set log level to {} for the logger '{}'", logLevelUpper, loggerSeaf.getLoggerName(), e);
			throw new LoggerException("Couldn't set log level to " + logLevelUpper + " for the logger '" + loggerSeaf.getLoggerName() + "'", e);
		}
	}
	
	@Override
	public List<String> getLogFiles() {
		List<String> logFiles = new ArrayList<String>();
		
		logFiles.add(logFileName); 
		
		File folder = new File(archiveDirectory);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				logFiles.add(archiveDirectory+listOfFiles[i].getName()); 
			} 
		}

		return logFiles;
	}
	
	@Override
	public void registerOpenConnection(WebSocketSession session) {
		LOGGER.debug("registerOpenConnection");
		webSocketSessions.add(session);
	}

	@Override
	public void registerCloseConnection(WebSocketSession session) {
		LOGGER.debug("registerCloseConnection");
		
		webSocketSessions.remove(session);

		for (Map.Entry<KeyWebSocketTailer, Tailer> tailer : tailers.entrySet()) {
			if(tailer.getKey().getSession() == session) {
				tailers.get(tailer.getKey()).stop();
				tailers.remove(tailer.getKey());
				
				indexTailer--;
			}
		}
	}
	
	@Override
	public void sendLogMessage(WebSocketSession session, String logFile, String logMessage) {
         try {
            session.sendMessage(new TextMessage(logFile + SEPRATOR_FILE_MESSAGE + logMessage));
            
         } catch (IOException e) {
            	LOGGER.error("Error when sending message message '"+logMessage+"'", e);
         }
	}
	
	@Override
	public void startTailerOnFile(WebSocketSession session, String logFile) {
		if(!tailers.containsKey(new KeyWebSocketTailer(session, logFile))) {
			LOGGER.debug("startTailerOnFile " + logFile);
			
			TailerListener listener = new LoggerListener(this, session, logFile);     
	    	Tailer tailer = new Tailer(new File(logFile), listener, TAIL_DEFAULT_DELAY_MILIS);  
	    	
	    	Thread t = new Thread(tailer, "Tailer-" + indexTailer);
	    	t.start();
	    	
	    	tailers.put(new KeyWebSocketTailer(session, logFile), tailer);
	    	
	    	indexTailer++;
		}
    }

	@Override
	public void stopTailerOnFile(WebSocketSession session, String logFile) {
		LOGGER.debug("stopTailerOnFile " + logFile);
		
    	this.tailers.get(new KeyWebSocketTailer(session, logFile)).stop();
    	this.tailers.remove(new KeyWebSocketTailer(session, logFile));
    	
    	indexTailer--;
    }

	private Object getFieldValue(String fullClassName, String fieldName) {
		try {
			Class<?> clazz = Class.forName(fullClassName);
			Field field = clazz.getField(fieldName);
			return field.get(null);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ignored) {
			return null;
		}
	}


}
