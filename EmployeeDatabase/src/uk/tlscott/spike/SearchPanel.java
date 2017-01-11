package uk.tlscott.spike;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import uk.tlscott.Employee;
import uk.tlscott.EmployeeDAO;
import uk.tlscott.ViewController;

public class SearchPanel extends JPanel{
	
	private static final long serialVersionUID = 6949675877066685266L;
	private static final int BOTH_FIELDS_EMPTY = 0;
	private static final int ONLY_NAME_FILLED = 1;
	private static final int ONLY_ID_FILLED = 2;
	private static final int BOTH_FIELDS_FILLED = 3;
	
	JButton searchButton = new JButton("Search");
	JButton clearButton = new JButton("Clear");
	
	private JLabel nameLabel = new JLabel("Name");
	private JLabel idLabel = new JLabel("ID");
	
	private JTextField nameField = new JTextField(16);
	private JTextField idField = new JTextField(16);
	
//TODO	 refactor anonymous jtable?
	private JTable table = new JTable(){

		private static final long serialVersionUID = 1L;
		
		  public boolean isCellEditable(int row, int column) {                
              return false;               
      };
	};
	
	private EmployeeDAO dao = new EmployeeDAO();
	private ArrayList<Employee> searchResults = new ArrayList<Employee>();
	private ViewController controller;
	
	public SearchPanel() {
		String[] columnNames = {"ID", "Name", "Gender", "DOB", "Address", "Postcode", "National Insurance Number", 
				"Title", "Start Date", "Salary", "Email" };

		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
		table.setModel(tableModel);
		table.setFillsViewportHeight(true);
		
		searchResults = dao.selectAllEmployees();
		updateTableModel(searchResults);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		setLayout(new BorderLayout());
		JPanel searchPanel = new JPanel(new FlowLayout());
		
		searchPanel.add(nameLabel);
		searchPanel.add(nameField);
		searchPanel.add(idLabel);
		searchPanel.add(idField);
		
		searchPanel.add(searchButton);
		searchPanel.add(clearButton);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2) {
					
					if(controller == null) return;
					
					int row = table.getSelectedRow();
					System.out.println(searchResults.get(row));
					controller.showRecordOf(searchResults.get(row));
				}
			}
		});
		
		// search code
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText().trim();
				String name = nameField.getText().trim();
				
				switch (checkFields()) {
				case BOTH_FIELDS_EMPTY:
					searchResults = dao.selectAllEmployees();
					updateTableModel(searchResults);
					break;
					
				case ONLY_ID_FILLED:
					searchResults = dao.searchByID(id);
					updateTableModel(searchResults);
					break;
					
				case ONLY_NAME_FILLED:
					searchResults = dao.searchByName(name);
					updateTableModel(searchResults);
					break;
					
				case BOTH_FIELDS_FILLED:
					searchResults = dao.searchByNameAndID(name, id);
					updateTableModel(searchResults);
					break;
					
				default:
					break;
				}
				
			}
		});
		
		// clear code
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				idField.setText("");
			}
		});
		
		add(searchPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private int checkFields() {
		if(nameField.getText().trim().isEmpty() && idField.getText().trim().isEmpty()) return BOTH_FIELDS_EMPTY;
		if(nameField.getText().trim().isEmpty() && !idField.getText().trim().isEmpty()) return ONLY_ID_FILLED;
		if(!nameField.getText().trim().isEmpty() && idField.getText().trim().isEmpty()) return ONLY_NAME_FILLED;
		return BOTH_FIELDS_FILLED;
	}
	
	private void updateTableModel(ArrayList<Employee> employees) {
		if (employees == null) return;
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		
		if (employees.size() == 0) return; // show empty if no results
		
		for (int i = 0; i < employees.size(); i++) {
			String[] data = new String[11];
			
			data[0] = employees.get(i).getId();
			data[1] = employees.get(i).getName();
			data[2] = String.valueOf(employees.get(i).getGender());
			data[3] = employees.get(i).getDob().toString();
			data[4] = employees.get(i).getAddress();
			data[5] = employees.get(i).getPostcode();
			data[6] = employees.get(i).getNatInscNo();
			data[7] = employees.get(i).getTitle();
			data[8] = employees.get(i).getStartDate().toString();
			data[9] = employees.get(i).getSalary();
			data[10] = employees.get(i).getEmail();
			
			tableModel.addRow(data);
		}
		
		tableModel.fireTableDataChanged();
	}
	
	public void addViewController(ViewController controller){
		this.controller = controller;
	}
}