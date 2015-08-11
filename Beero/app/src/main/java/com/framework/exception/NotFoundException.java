package com.framework.exception;

public class NotFoundException extends Exception {
	/**
	 * when NotFoundException
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public NotFoundException(String error) {
		super(error);
	}

	public NotFoundException() {
	}
}