import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDAO {
	Connection c;
	Statement s;
	ResultSet r;
	
	public EmployeeDAO() {
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:empdb.sqlite");
	      s = c.createStatement();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	public Statement getConnection() throws SQLException{
		return c.createStatement();
	}
	
	public void closeConnection() throws SQLException {
		c.close();
	}
	
	public ArrayList<Employee> selectAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			s = this.getConnection();
			r = s.executeQuery("SELECT * FROM employees;");
			while(r.next()) {
				Employee emp = new Employee();
				emp.setId(Integer.toString(r.getInt("ID")));
				emp.setName(r.getString("Name"));
				emp.setGender(r.getString("Gender").charAt(0));
				emp.setDob(r.getString("DOB"));
				emp.setAddress(r.getString("Address"));
				emp.setPostcode(r.getString("Postcode"));
				emp.setNatInscNo(r.getString("NIN"));
				emp.setTitle(r.getString("JobTitle"));
				emp.setStartDate(r.getString("Salary"));
				emp.setSalary("StartDate");
				emp.setEmail(r.getString("Email"));
				employees.add(emp);
			}
			
			s.close();
			r.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return employees;
	}
	
	public Employee selectEmployeeByName(String name) throws SQLException {
		String sqlStatement = "SELECT * FROM employees WHERE Name = '" + name + "';";
		s = this.getConnection();
		r = s.executeQuery(sqlStatement);
		Employee emp = new Employee();
		
		while(r.next()) {
			emp.setId(Integer.toString(r.getInt("ID")));
			emp.setName(r.getString("Name"));
			emp.setGender(r.getString("Gender").charAt(0));
			emp.setDob(r.getString("DOB"));
			emp.setAddress(r.getString("Address"));
			emp.setPostcode(r.getString("Postcode"));
			emp.setNatInscNo(r.getString("NIN"));
			emp.setTitle(r.getString("JobTitle"));
			emp.setStartDate(r.getString("Salary"));
			emp.setSalary("StartDate");
			emp.setEmail(r.getString("Email"));
		}

		return emp;
	}
	
	public boolean insertEmployee(Employee emp) {
		String sql = String.format("INSERT INTO EMPLOYEES "
				+ "(Name, Gender, DOB, Address, Postcode, NIN, JobTitle, StartDate, Salary, Email) "
				+ "VALUES "
				+ "('%s', '%c', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s') ;", 
				emp.getName(), emp.getGender(), emp.getDob(), emp.getAddress(), emp.getPostcode(),
				emp.getNatInscNo(), emp.getTitle(), emp.getStartDate(), emp.getSalary(), emp.getEmail());
		try {
			s = this.getConnection();
			s.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean insertEmployeeAtID(Employee emp, String id) {
		// convert string id to an integer for use with database
		int empID = Integer.parseInt(id);
		try {
			String sql = String.format("UPDATE employees "
					+ "SET Name = '%s', "
					+ "Gender = '%c', "
					+ "DOB = '%s', "
					+ "Address = '%s', "
					+ "Postcode = '%s', "
					+ "NIN = '%s', "
					+ "JobTitle = '%s', "
					+ "StartDate = '%s', "
					+ "Salary = '%s', "
					+ "Email = '%s'"
					+ "WHERE ID = %d;", 
					emp.getName(), emp.getGender(), emp.getDob(), emp.getAddress(), emp.getPostcode(),
					emp.getNatInscNo(), emp.getTitle(), emp.getStartDate(), emp.getSalary(), emp.getEmail(), empID);
			s = this.getConnection();
			s.execute(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean deleteEmployeeById(Employee emp, String id) {
		// convert string id to an integer for use with database
		int empID = Integer.parseInt(id);
		String sql = String.format("DELETE FROM employees WHERE ID = %d;", empID);
		
		try {
			s = this.getConnection();
			s.execute(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
}
