package com.framework.exception;

/**
 * When unexpected error is happened,the exceptions are thrown.
 */
public class UnexpectedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnexpectedException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public UnexpectedException(String unexpectedExceptionMsg) {
		super(unexpectedExceptionMsg);
	}

	public UnexpectedException() {
	}
}
