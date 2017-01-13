package uk.tlscott.exceptions;

public class InvalidNationalInsuranceException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidNationalInsuranceException(String msg) {
		super(msg);
	}
}
