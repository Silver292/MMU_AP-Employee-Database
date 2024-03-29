package uk.tlscott.tests;
import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.tlscott.Employee;
import uk.tlscott.EmployeeDAO;

public class EmployeeDAOTest {

	private static EmployeeDAO db;
	private static IDatabaseTester databaseTester;
	
	@BeforeClass
	public static void setUp() throws Exception{
		Class.forName("org.sqlite.JDBC");
		databaseTester = new JdbcDatabaseTester("org.sqlite.JDBC", "jdbc:sqlite:empdb.sqlite");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("testdb.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
		db = new EmployeeDAO();
	}

    @Test
	public void selectEmployeeByNameMatchesName() throws Exception {
		Employee emp = db.selectEmployeeByName("Fred Bloggs");
		assertNotNull("Employee shouldn't be null", emp);
		assertEquals("Fred Bloggs", emp.getName());
	}

    @Test
	public void selectAllEmployeesReturnsExpected() throws Exception {
		ArrayList<Employee> empList = new ArrayList<Employee>();
		empList = db.selectAllEmployees();
		assertNotNull(empList);
		assertEquals(3, empList.size());
	}
	
	@Test
	public void selectEmployeeAtIdReturnsEmployee() throws Exception {
		Employee testEmp = db.selectEmployeeById(1);
		assertNotNull("Should not be null", testEmp);
		assertEquals("Should match name", "Alan Crispin", testEmp.getName());
		assertEquals("id should match", "1", testEmp.getId());
		assertEquals("name should match", "Alan Crispin", testEmp.getName());
		assertEquals("gender should match", 'M', testEmp.getGender());
		assertEquals("dob should match", "1960-03-14", testEmp.getDob().toString());
		assertEquals("address should match", "Leeds", testEmp.getAddress());
		assertEquals("postcode should match", "LS1 1HE", testEmp.getPostcode());
		assertEquals("nin should match", "LR338036D", testEmp.getNatInscNo());
		assertEquals("job title should match", "Lecturer", testEmp.getTitle());
		assertEquals("start date should match", "2010-09-01", testEmp.getStartDate().toString());
		assertEquals("salary should match", "30000", testEmp.getSalary());
		assertEquals("email should match", "alan@example.com", testEmp.getEmail());
	}

	@Test
	public void employeeInsertionAddsEmployee() throws Exception {
		Employee newEmp = getTestEmployee();
		
		assertNotEquals(-1, db.insertEmployee(newEmp));
		Employee tempEmp = db.selectEmployeeByName(newEmp.getName());
		assertEquals("selected employee name matches", newEmp.getName(), tempEmp.getName());
	}
	
	@Test
	public void deleteEmployeeAtIDRemovesEmployee() throws Exception {
		Employee toBeDeleted = db.selectEmployeeByName("Mary Poppins");
		assertNotNull("selection works", toBeDeleted);
		assertEquals("employee exists and is selected", "Mary Poppins", toBeDeleted.getName());
		
		assertTrue("employee returns true if deleted", db.deleteEmployeeById(toBeDeleted.getId()));
		assertNull("returns null as not found", db.selectEmployeeByName(toBeDeleted.getName()));
	}
	
	@Test
	public void checkConnectionsAreClosedAfterOperation() throws Exception {
		@SuppressWarnings("unused")
		Employee emp = db.selectEmployeeById(1);
		@SuppressWarnings("unused")
		ArrayList<Employee> employees = db.selectAllEmployees();
		assertTrue("connection should be closed", db.isClosed());
	}
	
	@Test
	public void updateEmployeeShouldChangeDetailsOnDatabase() throws Exception {
		Employee testEmp = db.selectEmployeeById(1);
		assertEquals("salary should be ", "30000", testEmp.getSalary());
		testEmp.setSalary("50000");
		assertEquals("salary should change locally", "50000", testEmp.getSalary());
		boolean updateResult = db.updateEmployee(testEmp);
		assertTrue("update succeeds", updateResult);
		testEmp = db.selectEmployeeById(1);
		assertEquals("salary should be ", "50000", testEmp.getSalary());
	}

	private Employee getTestEmployee() throws Exception  {
		Employee testEmp = new Employee();
		testEmp.setName("Test Name");
		testEmp.setAddress("Manchester");
		testEmp.setEmployeeDob("1990-10-25");
		testEmp.setEmail("test@example.com");
		testEmp.setGender('M');
		testEmp.setId("16");
		testEmp.setNatInscNo("TX339203D");
		testEmp.setStartDate("2010-04-05");
		testEmp.setSalary("16000");
		testEmp.setTitle("Intern");
		return testEmp;
	}

	@AfterClass
	public static void tearDown() throws Exception
	{
	    databaseTester.onTearDown();
	    db.closeConnection();
	}

}
