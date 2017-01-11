package uk.tlscott;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainForm extends JFrame{

	protected EmployeeDAO dao = new EmployeeDAO();
	private EmployeeUpdatePanel employeePanel;
	private ViewController controller;
	
	private static final long serialVersionUID = 1L;

	public MainForm() {
		super("Employee Record System");

		setSize(665, 425);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		// centre frame on screen
		
		JPanel pane = new JPanel();
		setUpController(pane);
		
		add(pane);
		
		setUpMenu();
	}

	/**
	 * @param pane
	 */
	private void setUpController(JPanel pane) {
		CardLayout cardLayout = new CardLayout();
		
		pane.setLayout(cardLayout);

		employeePanel = new EmployeeUpdatePanel(dao);
		SearchPanel searchPane = new SearchPanel();
				
		controller = new ViewController(pane, cardLayout);
		controller.addView(employeePanel, ViewController.RECORD_VIEW);
		searchPane.addViewController(controller);
		controller.addView(searchPane, ViewController.SEARCH_VIEW);

		controller.showRecordOf(dao.selectFirstEmployee());
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
		JMenuItem addFileItem = new JMenuItem("Add Image");
		JMenuItem showSearch = new JMenuItem("Search");
		
		
		// Add exit item to file menu
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", 
						"Exit Confirmation", JOptionPane.CANCEL_OPTION);
				
				if(returnValue == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		
		
		// Add display item to the Record menus
		displayItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee employee = dao.selectFirstEmployee();
				
				// show error if one has occured
				if (employee == null) {
					JOptionPane.showMessageDialog(null, "There was a getting the first employee", "Database Error", JOptionPane.ERROR_MESSAGE);
				}
				
				controller.showRecordOf(employee);
			}
		});
		
		// Add an image to employee record
		addFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// set up file chooser
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png");
				chooser.setFileFilter(filter);
				chooser.setDialogTitle("Add Image");
				
				int returnVal = chooser.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					employeePanel.setImage(chooser.getSelectedFile());
				}
			}
		});
		
		showSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showSearchView();
			}
		});
		
		fileMenu.add(exitItem);
		
		recordMenu.add(displayItem);
		recordMenu.add(showSearch);
		recordMenu.add(addFileItem);
		
		menuBar.add(fileMenu);
		menuBar.add(recordMenu);
		
		setJMenuBar(menuBar);
	}

}
