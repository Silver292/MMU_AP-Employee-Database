package uk.tlscott;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EmployeeDAO {
	Connection c;
	Statement s;
	PreparedStatement pstmt;
	ResultSet r;
	
	// Logging variables
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	
	public EmployeeDAO() {}
	
	public Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:empdb.sqlite");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error connecting to database", e);
		}
		
		return c;
	}
	
	public void closeConnection() {
		closeQuietly(r);
		closeQuietly(s);
		closeQuietly(pstmt);
		closeQuietly(c);
	}
	
	/**
	 * Closes an <code>AutoClosable</code> unequivocally.
	 * <br>
	 * Equivalent to {@link java.lang.AutoClosable#close() AutoClosable.close()}, except any exceptions will be ignored
	 * @param input -  the object to close, may be null or already closed
	 */
	public void closeQuietly(AutoCloseable input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (Exception e) {
			// ignored
		}
	}
	
	public boolean insertEmployee(Employee emp) {
		String sql = "INSERT INTO employees "
				+ "(Name, Gender, DOB, Address, Postcode, NIN, JobTitle, StartDate, Salary, Email, Image) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
		try {
			this.getConnection();
			pstmt = c.prepareStatement(sql);
	        setPreparedStatement(emp);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error inserting employee", e);
		} finally {
			this.closeConnection();
		}
		return false;
	}

	public boolean updateEmployee(Employee emp) {
		String sql = "UPDATE employees SET "
				+ "Name = ?, "
	            + "Gender = ?, "
	            + "DOB = ?, "
	            + "Address = ?, "
	            + "Postcode = ?, "
	            + "NIN = ?, "
	            + "JobTitle = ?, "
	            + "StartDate = ?, "
	            + "Salary = ?, "
	            + "Email = ?, "
	            + "Image = ? "
	            + "WHERE ID = ?;";
		// convert string id to an integer for use with database
		int idAsInt = Integer.parseInt(emp.getId());
		try {
			this.getConnection();
			pstmt = c.prepareStatement(sql);
	        setPreparedStatement(emp);
	        pstmt.setInt(12, idAsInt);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error updating employee record", e);
		} finally {
			this.closeConnection();
		}
		return false;
	}

	public ArrayList<Employee> selectAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			this.getConnection();
			s = c.createStatement();
			r = s.executeQuery("SELECT * FROM employees;");
			while(r.next()) {
				employees.add(setEmployeeFields());
			}
			
			this.closeConnection();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting all employees", e);
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
				emp = setEmployeeFields();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting employee named: " + name, e);
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
				emp = setEmployeeFields();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting employee id: " + id, e);
		} finally {
			this.closeConnection();
		}
		return emp;
	}

	public Employee selectFirstEmployee() {
		Employee emp = null;
		String sql = "SELECT * FROM employees WHERE id = (SELECT MIN(ID) FROM employees);";
		try {
			this.getConnection();
			s = c.createStatement();
			r = s.executeQuery(sql);
			if(r.next()) {
				emp = setEmployeeFields();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting first employee", e);
			return emp;
		} finally {
			this.closeConnection();
		}
		
		return emp;
	}

	/**
	 * Selects next employee in table based on employee id.
	 * <br>
	 * If at the end of the table will wrap around and return the first employee in the table.
	 * @param id of the current employee.
	 * @return Employee next employee in the table or first employee if there are no ids higher.
	 */
	public Employee getNextEmployee(String id) {
		int idAsInt = Integer.parseInt(id);
	
		// check if there are any records with an id higher than current.
		String sql = "SELECT COUNT(*) AS count FROM employees WHERE ID > ?;";
		
		Employee emp = null;
		try {
			this.getConnection();		
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, idAsInt);
			r = pstmt.executeQuery();
	
			// if count > 0 get next employee
			int count = r.getInt("count");
			if (count > 0 ) {
				sql = "SELECT * FROM employees WHERE ID > ? ORDER BY ID LIMIT 1;";
				pstmt = c.prepareStatement(sql);
				pstmt.setInt(1, idAsInt);
				r = pstmt.executeQuery();
			} else {
				// otherwise wrap around
				sql = "SELECT * FROM employees WHERE id = (SELECT MIN(ID) FROM employees);";
				s = c.createStatement();
				r = s.executeQuery(sql);
			}
	
			// fill in employee information
			if (r.next()) {
				emp = setEmployeeFields();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting employee id: " + id, e);
		} finally {
			this.closeConnection();
		}
		return emp;
	}

	/**
	 * Selects previous employee in table based on employee id.
	 * <br>
	 * If at the beginning of the table will wrap around and return the last employee in the table.
	 * @param id of the current employee.
	 * @return Employee previous employee in the table or last employee if there are no lower ids.
	 */
	public Employee getPreviousEmployee(String id) {
		int idAsInt = Integer.parseInt(id);
	
		// check if there are any records with an id lower than current.
		String sql = "SELECT COUNT(*) AS count FROM employees WHERE ID < ?;";
		
		Employee emp = null;
		try {
			this.getConnection();		
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, idAsInt);
			r = pstmt.executeQuery();
	
			// if count > 0 get previous employee
			int count = r.getInt("count");
			if (count > 0 ) {
				sql = "SELECT * FROM employees WHERE ID < ? ORDER BY ID DESC LIMIT 1;";
				pstmt = c.prepareStatement(sql);
				pstmt.setInt(1, idAsInt);
				r = pstmt.executeQuery();
			} else {
				// otherwise wrap around
				sql = "SELECT * FROM employees WHERE id = (SELECT MAX(ID) FROM employees);";
				s = c.createStatement();
				r = s.executeQuery(sql);
			}
	
			// fill in employee information
			if (r.next()) {
				emp = setEmployeeFields();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting employee id: " + id, e);
		} finally {
			this.closeConnection();
		}
		return emp;
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
			LOGGER.log(Level.WARNING, "Error deleting employee id: " + id, e);
			return false;
		} finally {
			this.closeConnection();
		}
		
	}
	
/**
	 * @return
	 * @throws SQLException
	 */
	private Employee setEmployeeFields() throws SQLException {
		Employee emp = new Employee();
		emp.setId(Integer.toString(r.getInt("ID")));
		emp.setName(r.getString("Name"));
		emp.setGender(r.getString("Gender").charAt(0));
		emp.setDob(r.getString("DOB"));
		emp.setAddress(r.getString("Address"));
		emp.setPostcode(r.getString("Postcode"));
		emp.setNatInscNo(r.getString("NIN"));
		emp.setTitle(r.getString("JobTitle"));
		try {
			emp.setStartDate(r.getString("StartDate"));
		} catch (UnderMinimumAgeException e) {
			LOGGER.log(Level.INFO, e.getMessage(), e);
		}
		emp.setSalary(r.getString("Salary"));
		emp.setEmail(r.getString("Email"));
		emp.setImage(getImageFile(emp));
		return emp;
	}

	/**
	 * @param emp
	 * @throws SQLException
	 */
	private void setPreparedStatement(Employee emp) throws SQLException {
		pstmt.setString(1, emp.getName());
		pstmt.setString(2, String.valueOf(emp.getGender()));
		pstmt.setString(3, emp.getDob().toString());
		pstmt.setString(4, emp.getAddress());
		pstmt.setString(5, emp.getPostcode());
		pstmt.setString(6, emp.getNatInscNo());
		pstmt.setString(7, emp.getTitle());
		pstmt.setString(8, emp.getStartDate().toString());
		pstmt.setString(9, emp.getSalary());
		pstmt.setString(10, emp.getEmail());
        pstmt.setBytes(11, readFile(emp.getImageFile()));
	}

	/**
	 * @param emp
	 * @throws SQLException
	 */
	private File getImageFile(Employee emp) throws SQLException {
		File file = new File("./assets/empImage" + emp.getId() + ".jpg");
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			InputStream input = r.getBinaryStream("Image");
			if(input != null) {
				// write to file from database
				byte[] buffer = new byte[1024];
				while (input.read(buffer) > 0) {
					fos.write(buffer);
				}
				return file;
			} 
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error creating tempory image file", e);
		}
	
		return null;
	}

	/**
     * Read the file and returns the byte array
     * @param file
     * @return the bytes of the file
     */
    private byte[] readFile(File file) {
    	if (file == null) return null;
    	
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Error reading image file", e);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading image file", e);
        }
        return bos != null ? bos.toByteArray() : null;
    }
}
