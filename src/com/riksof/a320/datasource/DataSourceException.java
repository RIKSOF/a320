package com.riksof.a320.datasource;

public class DataSourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor for Custom DAOs Exception
	public DataSourceException() {
		super();
	}

	// Constructor with Custom Message
	public DataSourceException(String message) {
		super(message);
	}

	// Constructor with Custom Message and Throwable
	DataSourceException(String message, Throwable cause) {
		super(message, cause);
	}
}