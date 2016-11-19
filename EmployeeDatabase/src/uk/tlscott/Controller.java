package uk.tlscott;
import java.util.ArrayList;

public class Controller {

	public static void main(String[] args) {
		// Create database object
		EmployeeDAO db = new EmployeeDAO();
		Employee emp = new Employee();
		
		// Array list to store employees
		ArrayList<Employee> employees;

		// Test selectAllEmployees
		employees = db.selectAllEmployees();
		for (Employee e : employees) {
			System.out.println(e);
		}
		
		
		// Test select employee by name
		String empName = "Fred Bloggs";
		System.out.println("\nSelecting employee : " + empName);
		// Test select employee by name
		emp = db.selectEmployeeByName(empName);
		
		if(emp != null) {
			System.out.println("SUCCESS: Employee " + empName + " selected.");
		} else {
			System.out.println("FAILURE: Employee " + empName + " could not be selected.");
		}

		// Update employee
		emp.setId("4");
		emp.setName("Jack Henderson");
		emp.setGender('M');
		emp.setDob("25-04-1965");
		emp.setAddress("York");
		emp.setPostcode("HG4 2RD");
		emp.setNatInscNo("TG185469J");
		emp.setTitle("Administrator");
		emp.setStartDate("01-08-2010");
		emp.setSalary("23000");
		emp.setEmail("Jack@example.com");
		
		
		String id = "4";
		
		// Test insert employee
		if (db.insertEmployee(emp)) {
			System.out.println("SUCCESS: Employee " + emp.getName() + " inserted.");
		} else {
			System.out.println("FAILURE: Employee " + emp.getName() + " insertion failed.");
		}
		
		// Test deleteEmployeeAtId
		if (db.deleteEmployeeById(id)) {
			System.out.println("SUCCESS: Employee " + emp.getName() + " at ID = " + id + " deleted.");
		} else {
			System.out.println("FAILURE: Employee " + emp.getName() + " at ID = " + id + " deletion failed.");
		}
		
		//TODO Find out if insert at id is just update
//		id = "10";
//		
//		// Test insert employeeAtId
//		if (db.insertEmployeeAtID(emp, id)) {
//			System.out.println("SUCCESS: Employee " + emp.getName() + " inserted at ID=" + id);
//		} else {
//			System.out.println("FAILURE: Employee " + emp.getName() + " insertion failed at ID=" +id);
//		}
//		

		
		db.closeConnection();
	}

}
