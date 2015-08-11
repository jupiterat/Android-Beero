package com.framework.network;

public class ResponseError extends RootItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String StatusCode;
	/** The error message. */
	private String message;

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return StatusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}

	/**
	 * @return the mMessage
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the mMessage to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
