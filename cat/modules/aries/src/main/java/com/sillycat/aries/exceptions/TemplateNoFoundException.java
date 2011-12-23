package com.sillycat.aries.exceptions;

public class TemplateNoFoundException extends Exception {

	private static final long serialVersionUID = -4086390184495485572L;

	public TemplateNoFoundException() {
	}

	public TemplateNoFoundException(String msg) {
		super(msg);
	}

	public TemplateNoFoundException(Throwable nested) {
		super(nested);
	}

	public TemplateNoFoundException(String msg, Throwable nested) {
		super(msg, nested);
	}
}
