package uk.tlscott.spike;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class DateSpike {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		Calendar cal = Calendar.getInstance();
		
		String date = "23-12-1996";
		
		System.out.println(df.parse(date));
		
		LocalDate testDate = LocalDate.of(2016, 02, 20);
		System.out.println(testDate);
	}
	
}
