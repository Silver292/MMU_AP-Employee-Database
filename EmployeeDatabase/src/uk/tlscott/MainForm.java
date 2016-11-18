package uk.tlscott;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainForm extends JFrame{

	protected EmployeeDAO dao = new EmployeeDAO();
	private EmployeeUpdatePanel panel = new EmployeeUpdatePanel(dao);

	private static final long serialVersionUID = 1L;

	public MainForm() {
		super("Employee Record System");

		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// centre frame on screen
		setLocationRelativeTo(null);
		
		setUpMenu();

		panel.setEmployee(dao.selectEmployeeById(1));
		
		add(panel);
		
	}

	/**
	 * 
	 */
	private void setUpMenu() {
		// menu setup
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu   = new JMenu("File");
		JMenu recordMenu = new JMenu("Record");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem displayItem = new JMenuItem("Display");
		
		// Add exit item to file menu
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", 
						"Exit Confirmation", JOptionPane.CANCEL_OPTION);
				
				if(returnValue == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		
		fileMenu.add(exitItem);
		
		// Add display item to the Record menus
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Select first employee in database and call setTextFields
			}
		});
		
		recordMenu.add(displayItem);
		
		menuBar.add(fileMenu);
		menuBar.add(recordMenu);
		
		
		
		setJMenuBar(menuBar);
	}

}
