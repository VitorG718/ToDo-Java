package app.exceptions;

public class InvalidUserException extends RuntimeException {

	private static final long serialVersionUID = -7720637906216769063L;
	
	public InvalidUserException(String message) {
		super(message);
	}

}
