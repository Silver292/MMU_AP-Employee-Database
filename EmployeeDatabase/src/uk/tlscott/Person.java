package uk.tlscott;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
	private String name;
	private char gender;
	private String natInscNo;
	private String dob;
	private String address;
	private String postcode;
	
	public Person() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * @return the natInscNo
	 */
	public String getNatInscNo() {
		return natInscNo;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * @param natInscNo the natInscNo to set
	 */
	public void setNatInscNo(String natInscNo) throws IllegalArgumentException{
		// Validate national insurance number
		natInscNo = natInscNo.trim().toUpperCase().replace(" ", "");
		Pattern NINPattern = Pattern.compile("^(?!BG|GB|NK|KN|TN|NT|ZZ)[ABCEGHJ-PRSTW-Z][ABCEGHJ-NPRSTW-Z]\\d{6}[A-D]$");
		Matcher match = NINPattern.matcher(natInscNo);
		if (match.matches()) {
			this.natInscNo = natInscNo;
			return;
		}
		throw new IllegalArgumentException("Invalid national insurance number");
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param postcode the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
