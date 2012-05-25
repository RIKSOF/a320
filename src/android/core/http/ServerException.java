package android.core.http;

public class ServerException extends Exception {

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
