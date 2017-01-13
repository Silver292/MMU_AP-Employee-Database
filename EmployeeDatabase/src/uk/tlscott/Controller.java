package uk.tlscott;
import java.util.ArrayList;

import uk.tlscott.exceptions.InvalidNationalInsuranceException;
import uk.tlscott.exceptions.UnderMinimumAgeException;

public class Controller {

    public static void main(String[] args) {
        // Create database object
        EmployeeDAO db = new EmployeeDAO();
        Employee emp = new Employee();
        
        System.out.println("\n------TESTING SELECT ALL OPERATION-------\n");
        // Array list to store employees
        ArrayList<Employee> employees;

        // Test selectAllEmployees
        System.out.println("Selecting all employees: ");
        employees = db.selectAllEmployees();
        for (Employee e : employees) {
            System.out.println(e);
        }
        
        /*---- CREATE -----*/
        
        System.out.println("\n------TESTING CREATE OPERATION-------\n");
        
        // create employee
        emp.setName("Jack Henderson");
        emp.setGender('M');
        try {
            emp.setEmployeeDob("1965-05-25");
            emp.setStartDate("2012-01-08");
        } catch (UnderMinimumAgeException e1) {
            System.out.println(e1.getMessage());
        } 
        emp.setAddress("York");
        emp.setPostcode("HG4 2RD");
        try {
            emp.setNatInscNo("AB185469A");
        } catch (InvalidNationalInsuranceException e1) {
            System.out.println(e1.getMessage());
        }
        emp.setTitle("Administrator");
        emp.setSalary("23000");
        emp.setEmail("Jack@example.com");
        
        // Test insert employee
        int insertedID =  db.insertEmployee(emp);
        if (insertedID != 0) {
            System.out.println("SUCCESS: Employee " + emp.getName() + " inserted.");
        } else {
            System.out.println("FAILURE: Employee " + emp.getName() + " insertion failed.");
        }
        
        /*---- UPDATE -----*/
        System.out.println("\n------TESTING UPDATE OPERATION-------\n");
        
        emp.setId(String.valueOf(insertedID));
        
        System.out.println(emp.getName() + " address: " + emp.getAddress());
        
        String city = "Manchester";
        System.out.println("Changing address to " + city);
        emp.setAddress(city);
        db.updateEmployee(emp);
        
        emp = db.selectEmployeeByName(emp.getName());
        System.out.println(emp.getName() + " address: " + emp.getAddress());
        
        
        /*----  READ  -----*/
        System.out.println("\n------TESTING READ OPERATION-------\n");
        
        // Test select employee by name
        String empName = "Fred Bloggs";
        System.out.println("Selecting employee : " + empName);
        // Test select employee by name
        emp = db.selectEmployeeByName(empName);
        
        if(emp != null) {
            System.out.println("SUCCESS: Employee " + empName + " selected.");
        } else {
            System.out.println("FAILURE: Employee " + empName + " could not be selected.");
        }
        
        /*---- DELETE -----*/
        System.out.println("\n------TESTING DELETE OPERATION-------\n");
        
        // select created employee
        String id = db.selectEmployeeByName("Jack Henderson").getId();
        
        // Test deleteEmployeeAtId
        if (db.deleteEmployeeById(id)) {
            System.out.println("SUCCESS: Employee " + emp.getName() + " at ID = " + id + " deleted.");
        } else {
            System.out.println("FAILURE: Employee " + emp.getName() + " at ID = " + id + " deletion failed.");
        }
        
        db.closeConnection();
    }

}
