package uk.tlscott;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.tlscott.exceptions.InvalidNationalInsuranceException;

public class Person {
	private String name;
	private char gender;
	private String natInscNo;
	protected LocalDate dob;
	private String address;
	private String postcode;
	
	protected DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public Person() {
		super();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	
	/**
	 * Sets the national insurance number of the person.
	 * <br>
	 * Throws exception if national insurance number is invalid.
	 * 
	 * @param natInscNo	national insurance number to set.
	 * @throws InvalidNationalInsuranceException if passed an invalid NIN.
	 */
	public void setNatInscNo(String natInscNo) throws InvalidNationalInsuranceException{
		boolean hasText = natInscNo != null && !natInscNo.isEmpty();
		if (hasText) {
			natInscNo = natInscNo.trim().toUpperCase().replace(" ", "");
			validateNIN(natInscNo);
		}
		this.natInscNo = natInscNo;
	}

	/**
	 * Validates national insurance number.
	 * 
	 * @param natInscNo String to validate.
	 * @return true if string is valid national insurance number, false otherwise
	 */
	private void validateNIN(String natInscNo) throws InvalidNationalInsuranceException{
		Pattern NINPattern = Pattern.compile("^(?!BG|GB|NK|KN|TN|NT|ZZ)[ABCEGHJ-PRSTW-Z][ABCEGHJ-NPRSTW-Z]\\d{6}[A-D]$");
		Matcher match = NINPattern.matcher(natInscNo);
		if(match.matches()) {
			return;
		} else {
			throw new InvalidNationalInsuranceException("Invalid national insurance number");
		}
	}
	

	/**
	 * @return the natInscNo
	 */
	public String getNatInscNo() {
		return natInscNo;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob == null ? null : LocalDate.parse(dob, dateFormat);
	}

	/**
	 * @return the dob
	 */
	public LocalDate getDob() {
		return dob;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param postcode the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name: " + name + "\n\tGender: " + gender + "\n\tNIN: " + natInscNo + "\n\tD.O.B: " + dob
				+ "\n\tAddress: " + address + "\n\tPostcode: " + postcode;
	}
	
	
}
