package com.seaf.core.service.business.exception;

public class UserGroupException extends Exception {
	
	private static final long 	serialVersionUID = 1L;
	
	public static final int		EXCEPTION_CODE_INTERNALERROR			= 1001;
	public static final int		EXCEPTION_CODE_USERNOTEXIST				= 1002;
	public static final int		EXCEPTION_CODE_USERALREADYEXIST			= 1003;
	public static final int		EXCEPTION_CODE_GROUPNOTEXIST			= 1004;
	public static final int		EXCEPTION_CODE_GROUPALREADYEXIST		= 1005;
	public static final int		EXCEPTION_CODE_MEMBERSHIPALREADYEXIST 	= 1006;
	public static final int		EXCEPTION_CODE_MEMBERSHINOTEXIST		= 1007;
	public static final int		EXCEPTION_CODE_USERNOTVALID				= 1008;
	public static final int		EXCEPTION_CODE_GROUPNOTVALID			= 1009;
	public static final int		EXCEPTION_CODE_GROUPASUSERS				= 1010;
	
	private int					internalCode;
	
	
	public UserGroupException(int internalCode, String m) {
		super(m);
		this.internalCode = internalCode;
	}

	

	public int getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(int internalCode) {
		this.internalCode = internalCode;
	}
}
