package com.seaf.core.service.api.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/errors")
public class ApiExceptionHandler {
	
	public static final int EXCEPTION_CODE_UNAUTHORIZED			= 0001;
	public static final int EXCEPTION_CODE_METHODNOTALLOWED		= 0002;
	public static final int EXCEPTION_CODE_INTERNALSERVERERROR	= 0003;
	public static final int EXCEPTION_CODE_NOTFOUND				= 0004;
	
    @RequestMapping("notFound")
    public @ResponseStatus(HttpStatus.NOT_FOUND) @ResponseBody ApiException notFoundExceptionHandler(HttpServletRequest request) throws Exception {
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.NOT_FOUND.value());
		apiException.setHttpStatus(HttpStatus.NOT_FOUND.name());
		apiException.setMessage("The requested resource could not be found.");
		apiException.setUrl(request.getRequestURL().toString());
        apiException.setInternalErrorCode(EXCEPTION_CODE_NOTFOUND);
        
        return apiException;
    }
	
    @RequestMapping("unauthorised")
    public @ResponseStatus(HttpStatus.UNAUTHORIZED) @ResponseBody ApiException unAuthorisedExceptionHandler(HttpServletRequest request) throws Exception {
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.UNAUTHORIZED.value());
		apiException.setHttpStatus(HttpStatus.UNAUTHORIZED.name());
		apiException.setMessage("Unauthorized access is denied due to invalid credentials. Authentication is required and has failed or has not yet been provided.");
		apiException.setUrl(request.getRequestURL().toString());
        apiException.setInternalErrorCode(EXCEPTION_CODE_UNAUTHORIZED);
        
        return apiException;
    }
    
    @RequestMapping("methodNotAllowed")
    public @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) @ResponseBody ApiException methodNotAllowedExceptionHandler(HttpServletRequest request) throws Exception {
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.METHOD_NOT_ALLOWED.value());
		apiException.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED.name());
		apiException.setMessage("A request was made of a resource using a request method not supported by that resource; for example, using GET on a form which requires data to be presented via POST, or using PUT on a read-only resource.");
		apiException.setUrl(request.getRequestURL().toString());
        apiException.setInternalErrorCode(EXCEPTION_CODE_METHODNOTALLOWED);
        
        return apiException;
    }
    
    @RequestMapping("intertalError")
    public @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) @ResponseBody ApiException internalServerErrorExceptionHandler(HttpServletRequest request) throws Exception {
		ApiException apiException = new ApiException();
		apiException.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
		apiException.setMessage("Internal Server Error. Please contact admin !");
		apiException.setUrl(request.getRequestURL().toString());
        apiException.setInternalErrorCode(EXCEPTION_CODE_INTERNALSERVERERROR);
        
        return apiException;
    }
    
}
