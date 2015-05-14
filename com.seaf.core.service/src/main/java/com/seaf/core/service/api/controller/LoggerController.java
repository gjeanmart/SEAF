package com.seaf.core.service.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seaf.core.service.api.exception.ApiException;
import com.seaf.core.service.business.LoggerService;
import com.seaf.core.service.business.exception.LoggerException;
import com.seaf.core.service.model.LoggerDto;
import com.seaf.core.service.model.utils.EnvelopeList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1")
@Api(value="logger", description="Operations on backend logger")
public class LoggerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);

	public static final String 	URI_GETALLLOGGER				= "/logger";
	public static final String 	URI_CHANGELOGLEVEL				= "/logger/{loggerName}/{loglevel}";
	public static final String 	URI_GETLOGFILES					= "/log";
//	public static final String 	URI_READLOGFILE					= "/log";
//	public static final String 	URI_STOPREADLOGFILE				= "/log";
	public static final String 	URI_GETTHREAD					= "/thread";
	
	@Autowired
	private LoggerService loggerService;

	@RequestMapping(value = URI_GETALLLOGGER, method = RequestMethod.GET)
	@ApiOperation(value = "List all loggers")
	public EnvelopeList getLogger(			
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) throws Exception {

		try {
			LOGGER.debug("[START] Get all loggers");
			
			EnvelopeList allLoggers = loggerService.getLogger(pageNumber, pageSize, sortAttribute, sortDirection);
			
			LOGGER.debug("[END] Get all loggers");
			
			return allLoggers;
			
		} catch (LoggerException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	
	
    @RequestMapping(value = URI_CHANGELOGLEVEL, method = RequestMethod.PUT)
    @ApiOperation(value = "Change log level")
    public @ResponseBody LoggerDto changeLogLevel(
    		@ApiParam(name="loggerName", value="The name of the logger", required=true) @PathVariable(value = "loggerName") String loggerName,
    		@ApiParam(name="loglevel", value="The level wanted", required=true) @PathVariable(value = "loglevel") String loglevel) 
    				throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Change log level to {} for logger {}", loglevel, loggerName);
	    	
	    	LoggerDto loggerDto = new LoggerDto();
	    	loggerDto.setLoggerName(loggerName);
	    	loggerDto.setLogLevel(loglevel);
	    	
	    	
			loggerService.setLogLevel(loggerDto);
	
			LOGGER.debug("[END] Change log level to {} for logger {}", loglevel, loggerName);
	 
	        return loggerDto;
	        
		} catch (LoggerException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
    }
    
	@RequestMapping(value = URI_GETLOGFILES, method = RequestMethod.GET)
	@ApiOperation(value = "List all log files")
	public List<String> getLogsFile() throws Exception {

		try {
			LOGGER.debug("[START] Get log files");
			
			List<String> result = loggerService.getLogFiles();
			
			LOGGER.debug("[END] Get log files");
			
			return result;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
//    @RequestMapping(value = URI_READLOGFILE, method = RequestMethod.POST)
//    @ApiOperation(value = "Read log file - Activate WebSocket")
//    public void readLogFile(
//    		@ApiParam(name="logFile", value="The file name of the log", required=true) @RequestBody String logFile) 
//    				throws Exception {
//    	
//    	try {
//    		LOGGER.debug("[START] Read log file {}", logFile);
//
//	    	loggerService.startTailerOnFile(logFile);
//	        
//	    	LOGGER.debug("[END] Read log file {}", logFile);
//	 
//		} catch (Exception e) {
//			throw e;
//		}
//    }
//    
//    @RequestMapping(value = URI_STOPREADLOGFILE, method = RequestMethod.DELETE)
//    @ApiOperation(value = "Stop Read log file - Desactivate WebSocket")
//    public void stopReadLogFile() 
//    				throws Exception {
//    	
//    	try {
//    		LOGGER.debug("[START] Stop read log file ");
//	        
//	    	loggerService.stopTailerOnFile();
//	        
//	    	LOGGER.debug("[END] Stop read log file ");
//	 
//		} catch (Exception e) {
//			throw e;
//		}
//    }
//    

    
	@ExceptionHandler(LoggerException.class)
	public @ResponseStatus(HttpStatus.BAD_REQUEST) @ResponseBody ApiException loggerExceptionHandler(LoggerException ex, HttpServletResponse response, HttpServletRequest request){
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.BAD_REQUEST.value());
		apiException.setHttpStatus(HttpStatus.BAD_REQUEST.name());
		apiException.setMessage(ex.getMessage());
		apiException.setUrl(request.getRequestURL().toString());
		apiException.setInternalErrorCode(LoggerException.EXCEPTION_CODE_BADREQUEST);
        
	    return apiException;
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) @ResponseBody ApiException ExceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request){
		LOGGER.error("Error while requesting URL '"+request.getRequestURL().toString()+"'", ex);
		
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
		apiException.setMessage(ex.getMessage());
        apiException.setUrl(request.getRequestURL().toString());
        apiException.setInternalErrorCode(LoggerException.EXCEPTION_CODE_INTERNALERROR);
        
	    return apiException;
	}
	
	
}
