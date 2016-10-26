import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDAO {
	Connection c;
	Statement s;
	PreparedStatement pstmt;
	ResultSet r;
	
	public EmployeeDAO() {}
	
	public Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:empdb.sqlite");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public void closeConnection() {
		try {
			r.close();
			s.close();
			pstmt.close();
			c.close();
		} catch (Exception e) {
			/* Close quietly */
		}
	}
	
	public ArrayList<Employee> selectAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			this.getConnection();
			s = c.createStatement();
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
			
			this.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return employees;
	}

	public Employee selectEmployeeByName(String name) {
		String sqlStatement = "SELECT * FROM employees WHERE Name = ?";
		Employee emp = null;
		
		try {
			this.getConnection();
			pstmt = c.prepareStatement(sqlStatement);
			pstmt.setString(1,  name);
			r = pstmt.executeQuery();
			
			while(r.next()) {
				emp = new Employee();
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return emp;
	}
	
	public Employee selectEmployeeById(int id) {
		String sql = "SELECT * FROM employees WHERE ID = ?";
		Employee emp = null;
		
		try {
			this.getConnection();		
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			r = pstmt.executeQuery();
			
			if (r.next()) {
				emp = new Employee();
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
		return emp;
	}

	public boolean insertEmployee(Employee emp) {
		String sql = "INSERT INTO EMPLOYEES "
				+ "(Name, Gender, DOB, Address, Postcode, NIN, JobTitle, StartDate, Salary, Email) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
		try {
			this.getConnection();
			pstmt = c.prepareStatement(sql);
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, String.valueOf(emp.getGender()));
            pstmt.setString(3, emp.getDob());
            pstmt.setString(4, emp.getAddress());
            pstmt.setString(5, emp.getPostcode());
            pstmt.setString(6, emp.getNatInscNo());
            pstmt.setString(7, emp.getTitle());
            pstmt.setString(8, emp.getStartDate());
            pstmt.setString(9, emp.getSalary());
            pstmt.setString(10, emp.getEmail());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			this.closeConnection();
		}
	}
	
	public boolean insertEmployeeAtID(Employee emp, String id) {
		String sql = "INSERT INTO EMPLOYEES "
				+ "(Name, Gender, DOB, Address, Postcode, NIN, JobTitle, StartDate, Salary, Email, ID) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		// convert string id to an integer for use with database
		int idAsInt = Integer.parseInt(id);
		try {
			this.getConnection();
			pstmt = c.prepareStatement(sql);
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, String.valueOf(emp.getGender()));
            pstmt.setString(3, emp.getDob());
            pstmt.setString(4, emp.getAddress());
            pstmt.setString(5, emp.getPostcode());
            pstmt.setString(6, emp.getNatInscNo());
            pstmt.setString(7, emp.getTitle());
            pstmt.setString(8, emp.getStartDate());
            pstmt.setString(9, emp.getSalary());
            pstmt.setString(10, emp.getEmail());
            pstmt.setInt(11, idAsInt);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			this.closeConnection();
		}
	}
	
	public boolean deleteEmployeeById(String id) {
		int idAsInt = Integer.parseInt(id);
		String sql = "DELETE FROM employees WHERE ID = ?;";
		
		try {
			this.getConnection();
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, idAsInt);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			this.closeConnection();
		}
		
	}
	
}
