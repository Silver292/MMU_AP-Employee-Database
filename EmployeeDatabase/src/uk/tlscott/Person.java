package uk.tlscott;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
	private String name;
	private char gender;
	private String natInscNo;
	protected LocalDate dob;
	private String address;
	private String postcode;
	
	protected DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
	
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
	 * @param natInscNo the natInscNo to set
	 */
	public void setNatInscNo(String natInscNo) throws IllegalArgumentException{
		// Allow field to be null or empty string
		if (natInscNo == null || natInscNo.equals("")) {
			this.natInscNo = natInscNo;
			return;
		}
		
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
	public String getDob() {
		return dob == null ? null : dob.format(dateFormat);
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
