package com.seaf.core.connector.rest.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.seaf.core.domain.entity.enumeration.Periodicity;
import com.seaf.core.connector.rest.exception.ApiException;
import com.seaf.core.service.business.JavaService;
import com.seaf.core.service.business.exception.LoggerException;
import com.seaf.core.service.model.CpuMonitoringDto;
import com.seaf.core.service.model.HeapMonitoringDto;
import com.seaf.core.service.model.utils.EnvelopeList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1")
@Api(value="java", description="Java Utils functions")
public class JavaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JavaController.class);

	public static final String 	URI_GETTHREAD					= "/thread";
	public static final String 	URI_GETSYSTEMPROPERTIES			= "/systemprops";
	public static final String 	URI_GETJVMARGUMENTS				= "/jvmarguments";
	public static final String 	URI_GETAPPPROPERTIES			= "/appproperties";
	public static final String 	URI_GETMONITORINGHEAP			= "/heap";
	public static final String 	URI_GETMONITORINGCPU			= "/cpu";
	
	@Autowired
	private JavaService javaService;
	
    @RequestMapping(value = URI_GETMONITORINGCPU, method = RequestMethod.GET)
    @ApiOperation(value = "Get monitoring data about heap")
    public CpuMonitoringDto getMonitoringCpu(			
			@ApiParam(name="periodicity", 	value="Periodicity [HOURLY, DAILY, WEEKLY, MONTHLY]", 	required=true) 	@RequestParam(value = "periodicity", 	required=true) 												String periodicity,
			@ApiParam(name="startDate", 	value="startDate : Inbound time interval", 				required=true) 	@RequestParam(value = "startDate", 		required=true) 	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date startDate,
			@ApiParam(name="endDate", 		value="endDate : Outbound time interval", 				required=true) 	@RequestParam(value = "endDate", 		required=true) 	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endDate) throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Get monitoring data about CPU");
	        
    		CpuMonitoringDto result = javaService.getMonitoringCpu(Periodicity.valueOf(periodicity), startDate, endDate);
	        
	    	LOGGER.debug("[END] Get monitoring data about CPU");
	    	
	    	return result;
	 
		} catch (Exception e) {
			throw e;
		}
    }
	
    @RequestMapping(value = URI_GETMONITORINGHEAP, method = RequestMethod.GET)
    @ApiOperation(value = "Get monitoring data about heap")
    public HeapMonitoringDto getMonitoringHeap(			
			@ApiParam(name="periodicity", 	value="Periodicity [HOURLY, DAILY, WEEKLY, MONTHLY]", 	required=true) 	@RequestParam(value = "periodicity", 	required=true) 												String periodicity,
			@ApiParam(name="startDate", 	value="startDate : Inbound time interval", 				required=true) 	@RequestParam(value = "startDate", 		required=true) 	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date startDate,
			@ApiParam(name="endDate", 		value="endDate : Outbound time interval", 				required=true) 	@RequestParam(value = "endDate", 		required=true) 	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endDate) throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Get monitoring data about heap");
	        
    		HeapMonitoringDto result = javaService.getMonitoringHeap(Periodicity.valueOf(periodicity), startDate, endDate);
	        
	    	LOGGER.debug("[END] Get monitoring data about heap");
	    	
	    	return result;
	 
		} catch (Exception e) {
			throw e;
		}
    }

    @RequestMapping(value = URI_GETTHREAD, method = RequestMethod.GET)
    @ApiOperation(value = "Get thread info")
    public EnvelopeList getThread(			
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Get threads ");
	        
    		EnvelopeList result = javaService.getThreads(pageNumber, pageSize, sortAttribute, sortDirection);
	        
	    	LOGGER.debug("[END] Get threads ");
	    	
	    	return result;
	 
		} catch (Exception e) {
			throw e;
		}
    }
    
    @RequestMapping(value = URI_GETSYSTEMPROPERTIES, method = RequestMethod.GET)
    @ApiOperation(value = "Get system properties")
    public EnvelopeList getSystemProperties(			
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Get system properties");
	        
    		EnvelopeList result = javaService.getSystemProperties(pageNumber, pageSize, sortAttribute, sortDirection);
	        
	    	LOGGER.debug("[END] Get system properties");
	    	
	    	return result;
	 
		} catch (Exception e) {
			throw e;
		}
    }
    
    @RequestMapping(value = URI_GETJVMARGUMENTS, method = RequestMethod.GET)
    @ApiOperation(value = "Get JVM arguments")
    public EnvelopeList getJVMArguments(			
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Get JVM arguments");
	        
    		EnvelopeList result = javaService.getJVMArguments(pageNumber, pageSize, sortAttribute, sortDirection);
	        
	    	LOGGER.debug("[END] Get JVM arguments");
	    	
	    	return result;
	 
		} catch (Exception e) {
			throw e;
		}
    }
    
    @RequestMapping(value = URI_GETAPPPROPERTIES, method = RequestMethod.GET)
    @ApiOperation(value = "Get application properties")
    public EnvelopeList getAppProperties(			
			@ApiParam(name="page", 		value="Pagination : n° of page",					required=true) 	@RequestParam(value = "page", 	required=true) 	int pageNumber,
			@ApiParam(name="size", 		value="Pagination : size of page", 					required=true) 	@RequestParam(value = "size", 	required=true) 	int pageSize,
			@ApiParam(name="sort", 		value="Sort : Attribute for sorting result", 		required=true) 	@RequestParam(value = "sort", 	required=true) 	String sortAttribute,
			@ApiParam(name="dir", 		value="Direction [asc/desc] for sorting result", 	required=true) 	@RequestParam(value = "dir", 	required=true) 	String sortDirection) throws Exception {
    	
    	try {
    		LOGGER.debug("[START] Get application properties");
	        
    		EnvelopeList result = javaService.getApplicationProperties(pageNumber, pageSize, sortAttribute, sortDirection);
	        
	    	LOGGER.debug("[END] Get application properties");
	    	
	    	return result;
	 
		} catch (Exception e) {
			throw e;
		}
    }
    
    
//	@ExceptionHandler(LoggerException.class)
//	public @ResponseStatus(HttpStatus.BAD_REQUEST) @ResponseBody ApiException loggerExceptionHandler(LoggerException ex, HttpServletResponse response, HttpServletRequest request){
//		ApiException apiException = new ApiException();
//		apiException.setHttpCode(HttpStatus.BAD_REQUEST.value());
//		apiException.setHttpStatus(HttpStatus.BAD_REQUEST.name());
//		apiException.setMessage(ex.getMessage());
//		apiException.setUrl(request.getRequestURL().toString());
//		apiException.setInternalErrorCode(LoggerException.EXCEPTION_CODE_BADREQUEST);
//        
//	    return apiException;
//	}
	
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
