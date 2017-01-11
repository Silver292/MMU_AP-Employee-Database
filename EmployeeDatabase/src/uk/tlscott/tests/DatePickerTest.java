package uk.tlscott.tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.tlscott.DatePicker;

public class DatePickerTest {

	private DatePicker datePicker;
	LocalDate date;

	@Before
	public void setUp() {
		datePicker = new DatePicker();
		date = LocalDate.of(2010, 06, 19);
	}
	
	@Test
	public void newDatePickerReturnsLocalDateToday() throws Exception {
		LocalDate today = LocalDate.now();
		assertEquals(today, datePicker.getLocalDate());
	}
	
	@Test
	public void newDatePickerReturnsStringToday() throws Exception {
		LocalDate today = LocalDate.now();
		String todayString = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		assertEquals(todayString, datePicker.getDateAsString());
	}

	@Test
	public void setDateReturnsCorrectDate() throws Exception {
		datePicker.setDate(date);
		assertEquals(date, datePicker.getLocalDate());
	}
	
	@Test
	public void newDatePickerReturnsDefaultFormat() throws Exception {
		String dateString = date.format(datePicker.getDateReturnFormat());
		
		datePicker.setDate(date);
		
		assertEquals(dateString, datePicker.getDateAsString());
	}
	
	@Test
	public void whenGivenFormatDateIsFormattedCorrectly() throws Exception {
		datePicker.setDate(date);
		assertEquals("should be default format",date.format(datePicker.getDateReturnFormat()), datePicker.getDateAsString());
		
		DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		datePicker.setDateReturnFormat(newFormat);
		assertEquals("should be new format", date.format(newFormat), datePicker.getDateAsString());
	}
}
