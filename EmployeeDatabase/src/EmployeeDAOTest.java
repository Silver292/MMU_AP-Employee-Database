import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

public class EmployeeDAOTest {

	private EmployeeDAO db;
	private IDatabaseTester databaseTester;
	
	@Before
	public void setUp() throws Exception{
		// initialize your database connection here
		Class.forName("org.sqlite.JDBC");
		databaseTester = new JdbcDatabaseTester("org.sqlite.JDBC", "jdbc:sqlite:empdb.sqlite");

		// initialize your dataset here
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("testdb.xml"));
		// ...

		databaseTester.setDataSet( dataSet );
		// will call default setUpOperation
		databaseTester.onSetup();
		
		db = new EmployeeDAO();
	}

    @Test
	public void selectEmployeeByNameMatchesName() throws Exception {
		Employee emp = db.selectEmployeeByName("Fred Bloggs");
		assertNotNull("vo shouldn't be null", emp);
		assertEquals("Fred Bloggs", emp.getName());
	}

    @Test
	public void selectAllEmployeesReturnsExpected() {
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
		assertEquals("dob should match", "14-03-1960", testEmp.getDob());
		assertEquals("address should match", "Leeds", testEmp.getAddress());
		assertEquals("postcode should match", "LS1 1HE", testEmp.getPostcode());
		assertEquals("nin should match", "QQ123456C", testEmp.getNatInscNo());
		assertEquals("job title should match", "Lecturer", testEmp.getTitle());
		assertEquals("start date should match", "01-09-2010", testEmp.getStartDate());
		assertEquals("salary should match", "30000", testEmp.getSalary());
		assertEquals("email should match", "alan@example.com", testEmp.getEmail());
	}
	
	@Test
	public void employeeInsertionAddsEmployee() throws Exception {
		Employee newEmp = getTestEmployee();
		
		assertTrue(db.insertEmployee(newEmp));
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
	public void insertEmployeeAtIDInsertsAtCorrectID() throws Exception {
		Employee tempEmp = getTestEmployee();
		Employee selectedEmp;
		
		assertTrue(db.insertEmployeeAtID(tempEmp, "16"));
		assertNotNull("Returns an employee", selectedEmp = db.selectEmployeeById(16));
		assertEquals(tempEmp.getName(), selectedEmp.getName());
	}

	@Test
	public void checkConnectionsAreClosedAfterOperation() throws Exception {
		Employee emp = db.selectEmployeeById(1);
		ArrayList<Employee> employees = db.selectAllEmployees();
		assertTrue("result set should be closed", db.r.isClosed());
		assertTrue("statement should be closed", db.s.isClosed());
		assertTrue("prepared statement should be closed", db.pstmt.isClosed());
		assertTrue("connection should be closed", db.c.isClosed());
	}

	private Employee getTestEmployee() {
		Employee testEmp = new Employee();
		testEmp.setName("Test Name");
		testEmp.setAddress("Manchester");
		testEmp.setDob("25-10-1990");
		testEmp.setEmail("test@example.com");
		testEmp.setGender('M');
		testEmp.setId("16");
		testEmp.setNatInscNo("GH852468J");
		testEmp.setStartDate("05-04-2010");
		testEmp.setSalary("16000");
		testEmp.setTitle("Intern");
		return testEmp;
	}

	public void tearDown() throws Exception
	{
		// will call default tearDownOperation
	    databaseTester.onTearDown();
	    db.closeConnection();
	}
}
