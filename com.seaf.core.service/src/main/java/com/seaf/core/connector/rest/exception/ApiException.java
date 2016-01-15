package com.seaf.core.connector.rest.exception;

public class ApiException {
	
	private int 	httpCode;
	private String	httpStatus;
	private String 	url;
    private String 	message;
    private int 	internalErrorCode;
    
	public int getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}
	public String getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getInternalErrorCode() {
		return internalErrorCode;
	}
	public void setInternalErrorCode(int internalErrorCode) {
		this.internalErrorCode = internalErrorCode;
	}
	@Override
	public String toString() {
		return "ExceptionInfo [httpCode=" + httpCode + ", httpStatus="
				+ httpStatus + ", url=" + url + ", message=" + message
				+ ", internalErrorCode=" + internalErrorCode + "]";
	}

}
