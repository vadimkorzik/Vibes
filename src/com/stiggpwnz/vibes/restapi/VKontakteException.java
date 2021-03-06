package com.stiggpwnz.vibes.restapi;

public class VKontakteException extends Exception {

	private static final long serialVersionUID = 1773332531752352104L;

	public static final int UNKNOWN_ERROR_OCCURED = 1;
	public static final int APPLICATION_IS_DISABLED = 2;
	public static final int INCORRECT_SIGNATURE = 4;
	public static final int USER_AUTHORIZATION_FAILED = 5;
	public static final int TOO_MANY_REQUESTS_PER_SECOND = 6;
	public static final int PERMISSION_TO_PERFORM_THIS_ACTION_IS_DENIED_BY_USER = 7;
	public static final int ONE_OF_THE_PARAMETERS_IS_MISSING_OR_INVALID = 100;
	public static final int INVALID_MESSAGE = 120;
	public static final int ACCESS_DENIED = 201;
	public static final int CACHE_EXPIRED = 202;

	private int code;

	public VKontakteException(int code) {
		this.code = code;
	}

	public VKontakteException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
