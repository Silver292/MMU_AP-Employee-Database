package uk.tlscott;

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

public class SearchPanel extends JPanel{
	
	private static final long serialVersionUID = 6949675877066685266L;
	
	JButton searchButton = new JButton("Search");
	JButton clearButton = new JButton("Clear");
	
	private JLabel nameLabel = new JLabel("Name");
	private JLabel idLabel = new JLabel("ID");
	
	private JTextField nameField = new JTextField(16);
	private JTextField idField = new JTextField(16);
	
	private JTable resultTable;
	
	private EmployeeDAO dao = new EmployeeDAO();
	private ArrayList<Employee> searchResults = new ArrayList<Employee>();
	private ViewController controller;
	
	public SearchPanel() {
		setLayout(new BorderLayout());
		
		resultTable = getTableWithUneditableCells();
		fillTableWithEmployees();
		
		JScrollPane scrollPane = new JScrollPane(resultTable);
		JPanel searchPanel = new JPanel(new FlowLayout());
		
		setUpSearchPanel(searchPanel);
		addResultTableListener();
		
		add(searchPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	private JTable getTableWithUneditableCells() {
		return new JTable(){
			private static final long serialVersionUID = 1L;
			  public boolean isCellEditable(int row, int column) {                
	              return false;               
	      };
		};
	}

	private void fillTableWithEmployees() {
		String[] columnNames = {"ID", "Name", "Gender", "DOB", "Address", "Postcode", "National Insurance Number", 
				"Title", "Start Date", "Salary", "Email" };
	
		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
		resultTable.setModel(tableModel);
		resultTable.setFillsViewportHeight(true);
		
		searchResults = dao.selectAllEmployees();
		updateTableModel(searchResults);
	}

	private void setUpSearchPanel(JPanel searchPanel) {
		searchPanel.add(nameLabel);
		searchPanel.add(nameField);
		searchPanel.add(idLabel);
		searchPanel.add(idField);
		
		searchPanel.add(searchButton);
		searchPanel.add(clearButton);
		
		addSearchButtonListener();
		addClearButtonListener();
	}

	private void addSearchButtonListener() {
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText().trim();
				String name = nameField.getText().trim();
				
				
				getResultsFromFields(name, id);
				updateTableModel(searchResults);
			}
		});
	}

	private ArrayList<Employee> getResultsFromFields(String name, String id) {
		boolean nameFieldIsEmpty = name.isEmpty();
		boolean idFieldIsEmpty = id.isEmpty();
		
		boolean bothEmpty = nameFieldIsEmpty && idFieldIsEmpty;
		if(bothEmpty)  return dao.selectAllEmployees();
		
		boolean idFilled = nameFieldIsEmpty && !idFieldIsEmpty;
		if(idFilled) return dao.searchByID(id);
		
		boolean nameFilled = !nameFieldIsEmpty && idFieldIsEmpty;
		if(nameFilled) return dao.searchByName(name);
		
		return dao.searchByNameAndID(name, id);
	}
	
	private void addClearButtonListener() {
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				idField.setText("");
			}
		});
	}

	private void addResultTableListener() {
		resultTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				boolean doubleClicked = e.getClickCount() == 2;
				if(doubleClicked) {
					
					if(controller == null) return;
					
					int row = resultTable.getSelectedRow();
					System.out.println(searchResults.get(row));
					controller.showRecordOf(searchResults.get(row));
				}
			}
		});
	}

	private void updateTableModel(ArrayList<Employee> employees) {
		if (employees == null) return;
		
		DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
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