package uk.tlscott;

import java.io.File;
import java.time.LocalDate;

public class Employee extends Person {

	private static final long MINIMUM_WORKING_AGE = 16;
	private String id;
	private String salary;
	private LocalDate startDate;
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
	public void setEmployeeDob(String dob) throws UnderMinimumAgeException {
		// if null set to null
		if (dob == null){
			this.dob = null;
			return;
		}
		
		LocalDate date = LocalDate.parse(dob, dateFormat);
		
		// check over minimum working age
		if(underWorkingAge(date, this.startDate)) {
			throw new UnderMinimumAgeException("Employees must be over " + MINIMUM_WORKING_AGE + " years old.");	
		}
		
		super.setDob(dob);
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
	 * @throws StartWorkDateException 
	 * @throws UnderMinimumAgeException 
	 */
	public void setStartDate(String startDate) throws UnderMinimumAgeException {
		if(startDate == null){
			this.startDate = null;
			return;
		}
		
		LocalDate date = LocalDate.parse(startDate, dateFormat);
				
		// check over minimum working age
		if(underWorkingAge(this.dob, date)) {
			throw new UnderMinimumAgeException("Employees must be over " + MINIMUM_WORKING_AGE + " years old.");	
		}
		
		this.startDate = date;
	}
	

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
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

	// Checks that the employee is at least minimum working age. 
	// Employees can start work on their birthday
	public boolean underWorkingAge(LocalDate dob, LocalDate startDate) {
		if(dob == null || startDate == null) return false; // can't check without both
		
		return dob.isAfter(startDate.minusYears(MINIMUM_WORKING_AGE));
	}
	
}
