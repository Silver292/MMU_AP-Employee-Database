package uk.tlscott.spike;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateSpike {

	private static final long MINIMUM_WORKING_AGE = 16;

	public static void main(String[] args) throws ParseException {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d-MM-yyyy");
		String dateString = "03-05-1988";
		
		LocalDate date = LocalDate.parse(dateString, dateFormat);
		
		
		System.out.println(date);
		
		date = LocalDate.of(2016, 02, 29);
		System.out.println(date.format(dateFormat));
		
		System.out.println(date);
		
		// test time between
		LocalDate youngDOB = LocalDate.of(2011, 01, 01);
		LocalDate birthdayDOB = LocalDate.of(2000, 11, 30);
		LocalDate oldDOB = LocalDate.of(1988, 05, 30);
		
		System.out.println("Test young");
		canWork(youngDOB);
		System.out.println("Test bday");
		canWork(birthdayDOB);
		System.out.println("Test old");
		canWork(oldDOB);

		
	}
	
	// can work on their 16th birthday
	public static boolean canWork(LocalDate dob) {
		LocalDate minBirthDate = LocalDate.now().minusYears(MINIMUM_WORKING_AGE);
		return !minBirthDate.isBefore(dob);
	}
}
