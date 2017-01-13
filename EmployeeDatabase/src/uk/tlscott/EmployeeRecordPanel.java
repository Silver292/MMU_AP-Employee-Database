package uk.tlscott;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import uk.tlscott.exceptions.InvalidNationalInsuranceException;
import uk.tlscott.exceptions.UnderMinimumAgeException;

public class EmployeeRecordPanel extends JPanel{
	private static final long serialVersionUID = -5570407357068482978L;
	
    private ImagePanel imagePanel = new ImagePanel();
    
    private JButton enterButton   = new JButton("Enter");
    private JButton clearButton   = new JButton("Clear");
    private JButton backButton    = new JButton("Back");
    private JButton forwardButton = new JButton("Forward");
    
    private JLabel titleLabel     = new JLabel("Enter Employee Information");
    private JLabel nameLabel      = new JLabel("Employee Name");                
    private JLabel genderLabel    = new JLabel("Employee Gender");            
    private JLabel dobLabel       = new JLabel("Employee D.O.B");                
    private JLabel salaryLabel    = new JLabel("Employee Salary");            
    private JLabel ninLabel       = new JLabel("National Insurance Number");     
    private JLabel emailLabel     = new JLabel("Employee Email");              
    private JLabel startDateLabel = new JLabel("Employee Start Date");     
    private JLabel jobTitleLabel  = new JLabel("Enter Job Title");           
    private JLabel empIdTextLabel = new JLabel("Employee No: ");                 
    private JLabel empIdLabel     = new JLabel("");
    
    private JTextField nameTextBox      = new JTextField(16);
    private JTextField genderTextBox    = new JTextField(16);
    private JTextField salaryTextBox    = new JTextField(16);
    private JTextField ninTextBox       = new JTextField(16);
    private JTextField emailTextBox     = new JTextField(16);
    private JTextField jobTitleTextBox  = new JTextField(16);
    private ButtonGroup genderGroup     = new ButtonGroup();
    
    private JRadioButton maleRadio   = new JRadioButton("Male");
    private JRadioButton femaleRadio = new JRadioButton("Female");
    
	private DatePicker dobDate   = new DatePicker();
	private DatePicker startDate = new DatePicker();

	private Employee employee;
	private EmployeeDAO dao;
	
	public EmployeeRecordPanel(EmployeeDAO dao) {
		this.dao = dao;
		setFonts();
		setLayout(new GridBagLayout());
		addComponents();
		addActionListeners();
	}

	/**
	 * Sets the fonts of text components.
	 */
	private void setFonts() {
		Font baseFont = new Font("DejaVu Sans", Font.PLAIN, 12);
		Font boldFont = new Font("DejaVu Sans", Font.BOLD, 12);
		Font titleFont = new Font("DejaVu Sans", Font.BOLD|Font.ITALIC, 16);
		
		JComponent[] textBoxes = {genderTextBox, dobDate, salaryTextBox, ninTextBox, emailTextBox, startDate, jobTitleTextBox};
		
		JComponent[] labels = {enterButton, clearButton, backButton, forwardButton, nameLabel, 
				genderLabel, dobLabel, salaryLabel, ninLabel, emailLabel, startDateLabel, jobTitleLabel, 
				empIdTextLabel, empIdLabel, maleRadio, femaleRadio };
		
		
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(new Color(24, 0, 204));
		
		for (int i = 0; i < textBoxes.length; i++) {
			textBoxes[i].setFont(baseFont);
		}
		
		for (int i = 0; i < labels.length; i++) {
			labels[i].setFont(boldFont);
		}
	}

	/**
	 * Adds components to panel.
	 */
	private void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		
		JComponent[] columnOne = {nameLabel, genderLabel, dobLabel, salaryLabel, 
				ninLabel, emailLabel, startDateLabel, jobTitleLabel};
		
		JComponent[] columnTwo = {salaryTextBox, ninTextBox, emailTextBox};
		
		// common constraints
		Insets defaultInsets = new Insets(4, 4, 4, 4);
		int leftAlign = GridBagConstraints.LINE_START;
		int centerAlign = GridBagConstraints.CENTER;
		int fillHorizontal = GridBagConstraints.HORIZONTAL;
	
		/*
		 * Column one
		 */
		
		// Add title
		c.insets = defaultInsets;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.NORTH;
		add(titleLabel, c);
	
		// add column one members
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = leftAlign;
		for (int i = 0; i < columnOne.length; i++) {
			add(columnOne[i], c);
			c.gridy++;
		}
		
		// add enter button
		c.fill = fillHorizontal;
		c.anchor = centerAlign;
		add(enterButton, c);
		
		/*
		 *  Column two
		 */
		
		// adds name box
		c.ipady = 10;
		c.gridx = 1;
		c.gridy = 1;
		int noAnchor = GridBagConstraints.NONE;
		c.fill = noAnchor;
		c.anchor = leftAlign;
		add(nameTextBox, c);
	
		// adds radio buttons
		c.gridy++;
		add(maleRadio, c);
		c.anchor = GridBagConstraints.LINE_END;
		add(femaleRadio, c); 
	
		// add d.o.b date picker
		c.gridy++;
		c.anchor = leftAlign;
		c.fill = fillHorizontal;
		c.ipady = 0;
		add(dobDate, c);
	
		// add column two members
		c.ipady = 10;
		c.gridy++;
		c.anchor = leftAlign;
		c.fill = noAnchor;
		for (int i = 0; i < columnTwo.length; i++) {
			add(columnTwo[i], c);
			c.gridy++;
		}
		
		// adds start date picker
		c.ipady = 0;
		c.anchor = leftAlign;
		c.fill = fillHorizontal;
		add(startDate, c);
		
		// adds job title text box
		c.gridy++;
		c.ipady = 10;
		c.anchor = leftAlign;
		c.fill = noAnchor;
		add(jobTitleTextBox, c);
		
		// adds clear button
		c.gridy++;
		c.ipady = 0;
		c.fill = fillHorizontal;
		c.anchor = centerAlign;
		add(clearButton, c);
		
		/*
		 *  Column three
		 */
		
		// add employee id
		c.gridx = 2;
		c.gridy = 1;
		c.fill = noAnchor;
		c.anchor = leftAlign;
		add(empIdTextLabel, c);
		
		c.insets = new Insets(4, 105, 4, 4);
		add(empIdLabel, c);
		
		// adds image
		int imageHeight = 7;
		c.insets = defaultInsets;
		c.gridy++;
		c.gridheight = imageHeight;
		add(imagePanel, c);
		
		// adds back button
		c.ipadx = 60;
		c.gridheight = 1;
		c.gridy+=imageHeight;
		backButton.setPreferredSize(new Dimension(60, 25));
		add(backButton, c);
		
		// adds forward button
		c.ipadx = 0;
		c.weightx = 1.0;
		c.insets = new Insets(4, 135, 4, 4);
		forwardButton.setPreferredSize(new Dimension(120, 25));
		add(forwardButton, c);
		
		// add radio buttons to button group
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
	}

	private void addActionListeners() {
		addEnterActionListener();
		addClearActionListener();
		addForwardButtonActionListener();
		addBackButtonActionListener();
	}

	private void addEnterActionListener() {
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check for empty fields and return if user cancels
				if(!checkForEmptyFields())
					return;
					
				try {
					fillEmployeeDetails();
				} catch (InvalidNationalInsuranceException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				} catch (UnderMinimumAgeException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				}
				
				boolean employeeInDatabase = employee.getId() != null;
				if(employeeInDatabase) {
					updateEmployee(dao);
				} else {
					addNewEmployee(dao);
				}
			}
	
			/**
			 * Checks for empty fields in the form.
			 * <br>
			 * Creates JOptionPane to inform user that fields are empty.
			 * <br>
			 * If all fields are filled will return true.
			 *<br> 
			 * If there are any empty fields the user is informed using a {@link javax.swing.JOptionPane#showConfirmDialog confirm dialog}
			 * if the user chooses to proceed with empty fields method will return true otherwise will return false. 
			 * 
			 * @return boolean true if all fields are filled or the user wants to proceed with empty fields, false if otherwise.
			 */
			private boolean checkForEmptyFields() {
				ArrayList<String> emptyFieldList = new ArrayList<String>();
				
				addFieldsIfEmpty(emptyFieldList);
				
				if(emptyFieldList.isEmpty()) {
					return true;
				} else {
					return usersResponse(emptyFieldList);
				}
			}

			private boolean usersResponse(ArrayList<String> emptyFieldList) {
				String emptyFieldsMessage = createMessage(emptyFieldList);
				
				int userResponse = JOptionPane.showConfirmDialog(null, emptyFieldsMessage);
				
				if(userResponse == JOptionPane.YES_OPTION) {
					return true;
				} else {
					return false;
				}
			}

			private String createMessage(ArrayList<String> emptyFieldList) {
				String emptyFields = "";
			
				// add empty field to string comma separated
				int listSize = emptyFieldList.size();
				for (int i = 0; i < listSize; i++) {
					emptyFields += emptyFieldList.get(i);
					
					if (i < listSize - 1)
						emptyFields += ", ";
				}
				
				// create formatted error message
				String emptyFieldsMessage = String.format("The %s %s %s empty. Continue?", 
						listSize > 1 ? "fields" : "field", 
						emptyFields, 
						listSize > 1 ? "are" : "is");
				return emptyFieldsMessage;
			}

			private void addFieldsIfEmpty(ArrayList<String> emptyFieldList) {
				if(nameTextBox.getText().trim().isEmpty()) emptyFieldList.add("name");
				if(salaryTextBox.getText().trim().isEmpty()) emptyFieldList.add("salary");
				if(ninTextBox.getText().trim().isEmpty()) emptyFieldList.add("national insurance");
				if(emailTextBox.getText().trim().isEmpty()) emptyFieldList.add("email");
				if(jobTitleTextBox.getText().trim().isEmpty()) emptyFieldList.add("job title");
			}

			private void fillEmployeeDetails() throws InvalidNationalInsuranceException,
			UnderMinimumAgeException {
				char gender =  maleRadio.isSelected() ? 'M' : 'F';
				employee.setGender(gender);
				employee.setName(nameTextBox.getText());
				employee.setSalary(salaryTextBox.getText());
				employee.setNatInscNo(ninTextBox.getText());
				employee.setEmail(emailTextBox.getText());
				employee.setEmployeeDob(dobDate.getDateAsString());
				employee.setStartDate(startDate.getDateAsString());
				employee.setTitle(jobTitleTextBox.getText());
			}

			private void addNewEmployee(EmployeeDAO dao) {
				int generatedID = dao.insertEmployee(employee);
				if (generatedID == -1) {
					JOptionPane.showMessageDialog(null, "There was a problem creating the employee record", "Database Error", JOptionPane.ERROR_MESSAGE);
				}
				employee.setId(String.valueOf(generatedID));
			}
	
			private void updateEmployee(EmployeeDAO dao) {
				boolean updateSuccessful = dao.updateEmployee(employee);
				if (!updateSuccessful) {
					JOptionPane.showMessageDialog(null, "There was a problem updating the employee record", "Database Error", JOptionPane.ERROR_MESSAGE);
				}
			}
	
		});
	}

	private void addClearActionListener() {
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
	}

	private void addForwardButtonActionListener() {
		forwardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if employee is null (clear button has been pressed) return first employee
				changeEmployee("next");
			}
		});
	}

	private void addBackButtonActionListener() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeEmployee("previous");
			}
		});
	}

	private void changeEmployee(String direction) {
		if(employee.getId() == null) {
			employee = dao.selectFirstEmployee();
		} else {
			employee = direction.equals("next") ? 
					dao.getNextEmployee(employee.getId()) : dao.getPreviousEmployee(employee.getId());
		}
		
		if (employee == null) {
			String message = String.format("There was a getting the %s employee", direction);
			JOptionPane.showMessageDialog(null, message, "Database Error", JOptionPane.ERROR_MESSAGE);
		}
		
		setEmployee(employee);
	}

	/**
	 * Sets the fields to the employees details.
	 * 
	 * @param emp employee to set details to.
	 */
	public void setEmployee(Employee emp) {
		if(emp == null) {
			clearFields();
			return;
		} 
		
		this.employee = emp;
		setFields();
	}

	/**
	 * Sets text fields in form using details from employee.
	 * 
	 * @param emp
	 */
	private void setFields() {
		nameTextBox.setText(employee.getName());
		// set gender radio button
		if (employee.getGender() == 'M') maleRadio.setSelected(true);
		else femaleRadio.setSelected(true);
	
		dobDate.setDate(employee.getDob());
		salaryTextBox.setText(employee.getSalary());
		ninTextBox.setText(employee.getNatInscNo());
		emailTextBox.setText(employee.getEmail());
		startDate.setDate(employee.getStartDate());
		jobTitleTextBox.setText(employee.getTitle());
		empIdLabel.setText(employee.getId());
		imagePanel.setResizedImage(employee.getImageFile());
	}
	
	/**
	 * Clears fields.
	 */
	private void clearFields() {
		employee = new Employee();
		String empty = "";
		nameTextBox.setText(empty);
		maleRadio.setSelected(true);
		dobDate.setDate(null);
		salaryTextBox.setText(empty);
		ninTextBox.setText(empty);
		emailTextBox.setText(empty);
		startDate.setDate(null);
		jobTitleTextBox.setText(empty);
		empIdLabel.setText(empty);
		imagePanel.setDefaultImage();
	}
	
	/**
	 * Sets employee image, shows image in pane.
	 * @param file image file to display
	 */
	public void setImage(File file) {
		imagePanel.setResizedImage(file);
		employee.setImage(file);
	}
}
