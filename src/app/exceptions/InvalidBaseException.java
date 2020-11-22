package app.exceptions;

public class InvalidBaseException extends RuntimeException {

	private static final long serialVersionUID = -608073651287813789L;
	
	public InvalidBaseException(String message) {
		super(message);
	}
}
