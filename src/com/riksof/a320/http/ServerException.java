package com.riksof.a320.http;

public class ServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor for Custom DAOs Exception
	public ServerException() {
		super();
	}

	// Constructor with Custom Message
	public ServerException(String message) {
		super(message);
	}

	// Constructor with Custom Message and Throwable
	ServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
