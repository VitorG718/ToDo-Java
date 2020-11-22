package app.exceptions;

public class InvalidTaskException extends RuntimeException {

	private static final long serialVersionUID = -4073817343330607643L;

	public InvalidTaskException(String message) {
		super(message);
	}
}
