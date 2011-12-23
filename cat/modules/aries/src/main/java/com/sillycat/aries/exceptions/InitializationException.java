package com.sillycat.aries.exceptions;


public class InitializationException extends ServiceException {

	private static final long serialVersionUID = 1132817945549745311L;

    public InitializationException(String msg) {
        super(msg);
    }

    public InitializationException(String msg, Throwable t) {
        super(msg, t);
    }
	
}
