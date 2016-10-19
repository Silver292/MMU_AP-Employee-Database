import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeDAO {
	Connection c;
	Statement s;
	ResultSet r;
	
	public EmployeeDAO() {
		
	}
	
	public Statement getConnection(){
		return null;
	}
	
	public void closeConnection() {
		
	}
	
	public ArrayList<Employee> selectAllEmployees() {
		return null;
	}
	
	public Employee selectEmployeeByName(String name) {
		return null;
	}
	
	public boolean insertEmployee(Employee emp) {
		return false;
	}
	
	public boolean insertEmployeeAtID(Employee emp, String id) {
		return false;
	}
	
	public boolean deleteEmployeeById(Employee emp, String id) {
		return false;
	}
	
}
