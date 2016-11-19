package uk.tlscott;

import java.io.File;

public class Employee extends Person {

	private String id;
	private String salary;
	private String startDate;
	private String title;
	private String email;
	private File imageFile;

	public Employee() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the salary
	 */
	public String getSalary() {
		return salary;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	public File getImageFile() {
		return this.imageFile;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public void setImage(File file) {
		this.imageFile = file;
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
