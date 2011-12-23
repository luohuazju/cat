package com.sillycat.aries.exceptions;

public class ServiceRunTimeException extends RuntimeException {
	private static final long serialVersionUID = -5346982745031770547L;

	public ServiceRunTimeException() {
	}

	public ServiceRunTimeException(String msg) {
		super(msg);
	}

	public ServiceRunTimeException(Throwable nested) {
		super(nested);
	}

	public ServiceRunTimeException(String msg, Throwable nested) {
		super(msg, nested);
	}
}
