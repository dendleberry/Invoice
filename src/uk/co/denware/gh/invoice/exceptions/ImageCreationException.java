package uk.co.denware.gh.invoice.exceptions;

public class ImageCreationException extends Exception {

	private static final long serialVersionUID = 7718828512143293558L;
	
	public ImageCreationException() {
		super();
	}

	public ImageCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageCreationException(String message) {
		super(message);
	}

	public ImageCreationException(Throwable cause) {
		super(cause);
	}
}