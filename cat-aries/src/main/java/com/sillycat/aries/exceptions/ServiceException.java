package com.sillycat.aries.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 2978421674087620303L;

	public ServiceException() {
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable nested) {
		super(nested);
	}

	public ServiceException(String msg, Throwable nested) {
		super(msg, nested);
	}
}
