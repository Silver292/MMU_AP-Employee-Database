package uk.tlscott.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import uk.tlscott.Employee;

public class EmployeeTest {

	private Employee emp = new Employee();
	
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
	
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void employeeMustBeOverSixteen() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("d-M-y");
		LocalDate date = LocalDate.now();
		emp.setDob(date);
		fail("employee must be over sixteen");
	}
	
	@Test
	public void canSetEmployeeDOB() throws Exception {
		String date = "23-11-2016";
		emp.setDob(date);
		assertEquals(date, emp.getDob());
	}
	
	@Test
	public void DOBCanBe_Null() throws Exception {
		emp.setDob(null);
		assertEquals(null, emp.getDob());
	}
	
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void employeeMustBeBornBeforeStartingWork() {
		emp.setStartDate("01-01-2016");
		emp.setDob("30-12-2017");
		fail("Employee should be born before start date");
	}
}
