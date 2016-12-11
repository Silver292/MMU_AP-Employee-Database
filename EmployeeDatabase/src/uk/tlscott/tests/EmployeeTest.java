package uk.tlscott.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import uk.tlscott.Employee;
import uk.tlscott.StartWorkDateException;
import uk.tlscott.UnderMinimumAgeException;

public class EmployeeTest {

	private Employee emp = new Employee();
	
	// NIN
	
	@Test(expected = IllegalArgumentException.class)
	public void incorrectNINThrowsError() {
		emp.setNatInscNo("123456789");
		fail("Expected IllegalArgumentException");
	}
	
	@Test
	public void exceptionContainsUsefuleMessage() {
		String badNin = "1235asdfa";
		
		try {
			emp.setNatInscNo(badNin);
		} catch(IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Invalid national insurance number");
		}
		
	}
	
	@Test
	public void correctNationalInsuranceNumberisSet() {
		String natInscNo = "CB125635D";
		emp.setNatInscNo(natInscNo);
		assertEquals(natInscNo, emp.getNatInscNo());
	}
	
	@Test
	public void NationalInsuranceNumberSets_WhenPassedWithWhitespace() {
		String whitespace = "CB 12 56 35 D";
		emp.setNatInscNo(whitespace);
		assertEquals("CB125635D", emp.getNatInscNo());
	}
	
	@Test
	public void NationalInsuranceNumberSets_WhenPassedWithLowercase() {
		String lowercase = "cb 12 56 35 d";
		emp.setNatInscNo(lowercase);
		assertEquals("CB125635D", emp.getNatInscNo());
	}
	
	@Test
	public void NationalInsuranceNumberSets_WhenPassedWithTrailingWhitespace() {
		String trailingWhitespace = "CB 12 56 35 D         ";
		emp.setNatInscNo(trailingWhitespace);
		assertEquals("CB125635D", emp.getNatInscNo());
	}
	
	@Test
	public void nationalInsuranceNumber_CanBeSetToNull() throws Exception {
		emp.setNatInscNo(null);
		assertEquals(null, emp.getNatInscNo());
	}
	
	@Test
	public void nationalInsuranceNumber_CanBeSetToEmptyString() throws Exception {
		emp.setNatInscNo("");
		assertEquals("", emp.getNatInscNo());
	}
	
	
	// DOB
	
	@Test
	public void canSetEmployeeDOB() throws Exception {
		String date = "1964-01-01";
		emp.setEmployeeDob(date);
		assertEquals(date, emp.getDob().toString());
	}
	
	@Test
	public void DOBCanBe_Null() throws Exception {
		emp.setEmployeeDob(null);
		assertEquals(null, emp.getDob());
	}
	
	@Test(expected = UnderMinimumAgeException.class)
	public void employeeMustBeOverSixteen() throws Exception {
		String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		emp.setEmployeeDob(now);
		fail("employee must be over sixteen");
	}
	
	@Test(expected = StartWorkDateException.class)
	public void employeeMustBeBornBeforeStartingWork() throws Exception {
		emp.setStartDate("1950-01-01");
		emp.setEmployeeDob("1960-01-01");
		fail("Employee should be born before start date");
	}
}
