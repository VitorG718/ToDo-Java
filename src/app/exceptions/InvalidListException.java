package app.exceptions;

public class InvalidListException extends RuntimeException {

	private static final long serialVersionUID = 2700101030895186400L;
	
	public InvalidListException(String message) {
		super(message);
	}

}
