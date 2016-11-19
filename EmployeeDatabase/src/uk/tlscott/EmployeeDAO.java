package uk.tlscott;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	private static FileHandler fileHandler; 
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final String LOG_FILE = "./log/database.log";
	private final int LOG_FILE_SIZE_BYTES = 1000000;
	private final int LOG_FILE_COUNT = 1;
	
	public EmployeeDAO() {
		startLog();
	}
	
	public Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:empdb.sqlite");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error connecting to database", e);
		}
		
		return c;
	}

	/**
	 * Initialises log for database controller
	 */
	private void startLog() {
		try {
			fileHandler = new FileHandler(LOG_FILE, LOG_FILE_SIZE_BYTES, LOG_FILE_COUNT);
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		fileHandler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fileHandler);
		LOGGER.setLevel(Level.FINEST);
		LOGGER.setUseParentHandlers(false);
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
				emp.setStartDate(r.getString("StartDate"));
				emp.setSalary(r.getString("Salary"));
				emp.setEmail(r.getString("Email"));
				employees.add(emp);
			}
			
			this.closeConnection();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting all employees", e);
			return null;
		}
		
		return employees;
	}

	//TODO Read image from database
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
			LOGGER.log(Level.WARNING, "Error selecting employee named: " + name, e);
		} finally {
			this.closeConnection();
		}
		return emp;
	}
	
	//TODO read image from database
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
				emp.setStartDate(r.getString("StartDate"));
				emp.setSalary(r.getString("Salary"));
				emp.setEmail(r.getString("Email"));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting employee id: " + id, e);
		} finally {
			this.closeConnection();
		}
		return emp;
	}

	public boolean insertEmployee(Employee emp) {
		String sql = "INSERT INTO employees "
				+ "(Name, Gender, DOB, Address, Postcode, NIN, JobTitle, StartDate, Salary, Email, Image) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
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
            pstmt.setBytes(11, readFile(emp.getImageFile()));
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error inserting employee", e);
		} finally {
			this.closeConnection();
		}
		return false;
	}
	
	// TODO add image insertion
	public boolean insertEmployeeAtID(Employee emp, String id) {
		String sql = "INSERT INTO employees "
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
			LOGGER.log(Level.WARNING, "Error inserting employee at id: " + id, e);
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
			LOGGER.log(Level.WARNING, "Error deleting employee id: " + id, e);
			return false;
		} finally {
			this.closeConnection();
		}
		
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
				emp = new Employee();
				emp.setId(Integer.toString(r.getInt("ID")));
				emp.setName(r.getString("Name"));
				emp.setGender(r.getString("Gender").charAt(0));
				emp.setDob(r.getString("DOB"));
				emp.setAddress(r.getString("Address"));
				emp.setPostcode(r.getString("Postcode"));
				emp.setNatInscNo(r.getString("NIN"));
				emp.setTitle(r.getString("JobTitle"));
				emp.setStartDate(r.getString("StartDate"));
				emp.setSalary(r.getString("Salary"));
				emp.setEmail(r.getString("Email"));
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
				emp = new Employee();
				emp.setId(Integer.toString(r.getInt("ID")));
				emp.setName(r.getString("Name"));
				emp.setGender(r.getString("Gender").charAt(0));
				emp.setDob(r.getString("DOB"));
				emp.setAddress(r.getString("Address"));
				emp.setPostcode(r.getString("Postcode"));
				emp.setNatInscNo(r.getString("NIN"));
				emp.setTitle(r.getString("JobTitle"));
				emp.setStartDate(r.getString("StartDate"));
				emp.setSalary(r.getString("Salary"));
				emp.setEmail(r.getString("Email"));
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
				emp = new Employee();
				emp.setId(Integer.toString(r.getInt("ID")));
				emp.setName(r.getString("Name"));
				emp.setGender(r.getString("Gender").charAt(0));
				emp.setDob(r.getString("DOB"));
				emp.setAddress(r.getString("Address"));
				emp.setPostcode(r.getString("Postcode"));
				emp.setNatInscNo(r.getString("NIN"));
				emp.setTitle(r.getString("JobTitle"));
				emp.setStartDate(r.getString("StartDate"));
				emp.setSalary(r.getString("Salary"));
				emp.setEmail(r.getString("Email"));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error selecting employee id: " + id, e);
		} finally {
			this.closeConnection();
		}
		return emp;
	}
	
	//TODO: Write our own
	/**
     * Read the file and returns the byte array
     * @param file
     * @return the bytes of the file
     */
    private byte[] readFile(File file) {
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
