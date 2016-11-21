package uk.tlscott.spike;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	JTable table = new JTable();
	EmployeeDAO dao = new EmployeeDAO();

	public SearchPanel() {
		String[] columnNames = {"ID", "Name", "Gender", "DOB", "Address", "Postcode", "National Insurance Number", 
				"Title", "Start Date", "Salary", "Email" };

		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);

		ArrayList<Employee> allEmployees = dao.selectAllEmployees();

		for (int i = 0; i < allEmployees.size(); i++) {
			Employee tempEmp = allEmployees.get(i);
			tableModel.addRow(new Object[] {tempEmp.getId(), tempEmp.getName(), tempEmp.getGender(), tempEmp.getDob(), tempEmp.getAddress(), 
					tempEmp.getPostcode(), tempEmp.getNatInscNo(), tempEmp.getTitle(), tempEmp.getStartDate(), tempEmp.getSalary(), tempEmp.getEmail()});
		}

		table.setModel(tableModel);
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		
		setLayout(new BorderLayout());
		
		JPanel searchPanel = new JPanel(new FlowLayout());
		
		searchPanel.add(nameLabel);
		searchPanel.add(nameField);
		searchPanel.add(idLabel);
		searchPanel.add(idField);
		
		searchPanel.add(searchButton);
		searchPanel.add(clearButton);
		
		// search code
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO dao returns and arraylist of employees, need to update table model to show these.
				switch (checkFields()) {
				case BOTH_FIELDS_EMPTY:
					updateTableModel(dao.selectAllEmployees());
					break;
					
				case ONLY_ID_FILLED:
					updateTableModel(dao.searchByID(idField.getText().trim()));
					break;
					
				case ONLY_NAME_FILLED:
					updateTableModel(dao.searchByName(nameField.getText().trim()));
					break;
					
				case BOTH_FIELDS_FILLED:
					updateTableModel(dao.searchByNameAndID(nameField.getText().trim(), idField.getText().trim()));
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
		this.add(scrollPane, BorderLayout.CENTER);
	}

	private int checkFields() {
		if(nameField.getText().trim().isEmpty() && idField.getText().trim().isEmpty()) return BOTH_FIELDS_EMPTY;
		if(nameField.getText().trim().isEmpty() && !idField.getText().trim().isEmpty()) return ONLY_ID_FILLED;
		if(!nameField.getText().trim().isEmpty() && idField.getText().trim().isEmpty()) return ONLY_NAME_FILLED;
		return BOTH_FIELDS_FILLED;
	}
	
	private void updateTableModel(ArrayList<Employee> employees) {
		if (employees == null) return;
		if (employees.size() == 0) return; // TODO no employee found
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		
		for (int i = 0; i < employees.size(); i++) {
			String[] data = new String[11];
			
			data[0] = employees.get(i).getId();
			data[1] = employees.get(i).getName();
			data[2] = String.valueOf(employees.get(i).getGender());
			data[3] = employees.get(i).getDob();
			data[4] = employees.get(i).getAddress();
			data[5] = employees.get(i).getPostcode();
			data[6] = employees.get(i).getNatInscNo();
			data[7] = employees.get(i).getTitle();
			data[8] = employees.get(i).getStartDate();
			data[9] = employees.get(i).getSalary();
			data[10] = employees.get(i).getEmail();
			
			tableModel.addRow(data);
		}
		
		tableModel.fireTableDataChanged();
	}
}