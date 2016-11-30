package uk.tlscott;

import java.io.File;
import java.time.LocalDate;

public class Employee extends Person {

	private static final long MINIMUM_WORKING_AGE = 16;
	private String id;
	private String salary;
	private String startDate;
	private String title;
	private String email;
	private File   imageFile;

	public Employee() {
		super();
	}

	/**
	 * Sets the employees date of birth.
	 * <br>
	 * Date of birth can be null.<br>
	 * 
	 * Will check the employee is of a legal age to work and if not will throw an {@link UnderMinimumAgeException}.
	 * 
	 * Checks that the employees date of birth is before the employees start date. <br>
	 * If the employee was born after starting work will throw a {@link StartWorkDateException}
	 * 
	 * @param dob  date of birth to set
	 * @throws UnderMinimumAgeException
	 * @throws StartWorkDateException
	 */
	public void setEmployeeDob(String dob) throws UnderMinimumAgeException, StartWorkDateException {
		// if null set to null
		if (dob == null){
			this.dob = null;
			return;
		}
		
		LocalDate date = LocalDate.parse(dob, dateFormat);
		
		// if under age throw error
		if (isUnderLegalAge(date)) {
			throw new UnderMinimumAgeException("Employees must be over " + MINIMUM_WORKING_AGE + " years old.");	
		}
		
		// if born after starting work throw error
		if (startDate != null && date.isAfter(LocalDate.parse(this.startDate, dateFormat))) {
			throw new StartWorkDateException("Employees must be born before starting work.");
		}
		
		super.setDob(dob);
	}
	
	// Checks that the employee is at least minimum working age. 
	// Employees can start work on their birthday
	private boolean isUnderLegalAge(LocalDate dob) {
		LocalDate minBirthDate = LocalDate.now().minusYears(MINIMUM_WORKING_AGE);
		return minBirthDate.isBefore(dob);
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	

	/**
	 * @return the salary
	 */
	public String getSalary() {
		return salary;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	public void setImage(File file) {
		this.imageFile = file;
	}

	public File getImageFile() {
		return this.imageFile;
	}

	public boolean hasImage() {
		return this.imageFile != null;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\n\tI.D: " + id + "\n\tSalary: " + salary + "\n\tStart Date: " + startDate + "\n\tTitle: "
				+ title + "\n\tEmail: " + email;
	}

}
