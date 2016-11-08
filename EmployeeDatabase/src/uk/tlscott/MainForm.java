package uk.tlscott;
import javax.swing.JFrame;

public class MainForm extends JFrame{

	EmployeeDAO dao = new EmployeeDAO();

	private static final long serialVersionUID = 1L;

	public MainForm() {
		super("Employee Record System");

	}

}
