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
	private EmployeeRecordPanel employeePanel;
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
		
		setUpMenuBar();
	}

	/**
	 * @param pane
	 */
	private void setUpController(JPanel pane) {
		CardLayout cardLayout = new CardLayout();
		
		pane.setLayout(cardLayout);

		employeePanel = new EmployeeRecordPanel(dao);
		SearchPanel searchPane = new SearchPanel();
				
		controller = new ViewController(pane, cardLayout);
		controller.addView(employeePanel, ViewController.RECORD_VIEW);
		searchPane.addViewController(controller);
		controller.addView(searchPane, ViewController.SEARCH_VIEW);

		controller.showRecordOf(dao.selectFirstEmployee());
	}

	private void setUpMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu   = new JMenu("File");
		JMenu recordMenu = new JMenu("Record");
		
		setUpFileMenu(fileMenu);
		setUpRecordMenu(recordMenu);
		
		menuBar.add(fileMenu);
		menuBar.add(recordMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void setUpFileMenu(JMenu fileMenu) {
		JMenuItem exitItem = new JMenuItem("Exit");
		
		addExitItemListener(exitItem);
		
		fileMenu.add(exitItem);
	}

	private void addExitItemListener(JMenuItem exitItem) {
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", 
						"Exit Confirmation", JOptionPane.CANCEL_OPTION);
				
				if(returnValue == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
	}

	private void setUpRecordMenu(JMenu recordMenu) {
		JMenuItem displayItem = new JMenuItem("Display");
		JMenuItem addFileItem = new JMenuItem("Add Image");
		JMenuItem searchItem = new JMenuItem("Search");
		
		addDisplayItemListener(displayItem);
		addSearchListener(searchItem);
		addFileItemListener(addFileItem);
		
		recordMenu.add(displayItem);
		recordMenu.add(searchItem);
		recordMenu.add(addFileItem);
	}


	private void addDisplayItemListener(JMenuItem displayItem) {
		displayItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Employee employee = dao.selectFirstEmployee();
				
				boolean errorGettingEmployee = employee == null;
				if (errorGettingEmployee) {
					JOptionPane.showMessageDialog(null, "There was a problem getting the first employee", "Database Error", JOptionPane.ERROR_MESSAGE);
				}
				
				controller.showRecordOf(employee);
			}
		});
	}

	private void addSearchListener(JMenuItem showSearch) {
		showSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showSearchView();
			}
		});
	}

	private void addFileItemListener(JMenuItem addFileItem) {
		addFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(employeePanel.isVisible()) {
					createFileChooser();

				} else {
					JOptionPane.showMessageDialog(null, "To add image to employee go to Record -> Display", "Image Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}

			private void createFileChooser() {
				JFileChooser chooser = new JFileChooser();
				setFilters(chooser);
				chooser.setDialogTitle("Add Image");

				int returnVal = chooser.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					employeePanel.setImage(chooser.getSelectedFile());
				}
			}

			private void setFilters(JFileChooser chooser) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
			}
		});
	}

}
