package uk.tlscott;

public class UnderMinimumAgeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UnderMinimumAgeException(String message) {
		super(message);
	}

}
