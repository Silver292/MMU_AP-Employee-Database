import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Ignore;
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
	public void employeeSelectedByNameMatchesExpected() throws Exception {
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
