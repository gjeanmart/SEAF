package com.seaf.core.service.business.exception;

public class LoggerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final int		EXCEPTION_CODE_BADREQUEST		= 2000;
	public static final int		EXCEPTION_CODE_INTERNALERROR	= 2001;
	
	public LoggerException() {
		super();
	}

	public LoggerException(String m) {
		super(m);
	}

	public LoggerException(String m, Exception e) {
		super(m, e);
	}

}
