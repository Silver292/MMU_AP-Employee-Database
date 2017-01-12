package uk.tlscott.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DatePickerTest.class, EmployeeDAOTest.class, EmployeeTest.class })
public class AllTests {

}
