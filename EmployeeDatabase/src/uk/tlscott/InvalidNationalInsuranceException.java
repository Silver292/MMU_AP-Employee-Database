package uk.tlscott;

public class InvalidNationalInsuranceException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidNationalInsuranceException(String msg) {
		super(msg);
	}
}
