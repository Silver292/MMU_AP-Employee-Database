package uk.tlscott;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class EmployeeUpdatePanel extends JPanel{
	private static final long serialVersionUID = -5570407357068482978L;
	
	private Image  image;
    private JLabel imageLabel = new JLabel();
    
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
	
	public EmployeeUpdatePanel(EmployeeDAO dao) {

		this.dao = dao;
		
		//TODO: remove this and add images to database
		try {
			image = ImageIO.read(new File("assets/default.png"));
			image = image.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
			imageLabel = new JLabel(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setFonts();
		
		setLayout(new GridBagLayout());
		addComponents();
		
		/*
		 * Action Listeners
		 */
		
		// Update employee fields
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check for empty fields and return if user cancels
				if(!checkForEmptyFields())
					return;
					
				// set gender
				char gender =  maleRadio.isSelected() ? 'M' : 'F';
				employee.setGender(gender);
				
				employee.setName(nameTextBox.getText());
				employee.setDob(dobDate.getDate());
				employee.setSalary(salaryTextBox.getText());
				employee.setNatInscNo(ninTextBox.getText());
				employee.setEmail(emailTextBox.getText());
				employee.setStartDate(startDate.getDate());
				employee.setTitle(jobTitleTextBox.getText());
				
				// check if employee has an id, if not create new employee
				if(employee.getId() == null) {
					boolean success = dao.insertEmployee(employee);
					if (!success) {
						JOptionPane.showMessageDialog(null, "There was a problem creating the employee record", "Database Error", JOptionPane.ERROR_MESSAGE);
					}
					return;
				}
				
				// update existing employee and check for success
				boolean success = dao.updateEmployee(employee, employee.getId());
				if (!success) {
					JOptionPane.showMessageDialog(null, "There was a problem updating the employee record", "Database Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		
		// Clear form
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
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
		
		// add empty fields to list
		if(nameTextBox.getText().trim().isEmpty()) emptyFieldList.add("name");
		if(salaryTextBox.getText().trim().isEmpty()) emptyFieldList.add("salary");
		if(ninTextBox.getText().trim().isEmpty()) emptyFieldList.add("national insurance");
		if(emailTextBox.getText().trim().isEmpty()) emptyFieldList.add("email");
		if(jobTitleTextBox.getText().trim().isEmpty()) emptyFieldList.add("job title");
		
		// return if all are filled
		if(emptyFieldList.isEmpty()) return true;
		
		// create error message
		String emptyFields = "";

		// add empty field to string comma seperated
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
		
		// show dialog and get user response
		int userResponse = JOptionPane.showConfirmDialog(null, emptyFieldsMessage);
		
		// if user clicks yes
		if(userResponse == JOptionPane.YES_OPTION) return true;
		
		// if user clicks 'No', 'Cancel' or closes the dialog 
		return false;
		
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
		setFields(emp);
	}

	/**
	 * Sets text fields in form using details from employee.
	 * 
	 * @param emp
	 */
	private void setFields(Employee emp) {
		nameTextBox.setText(emp.getName());
		// set gender radio button
		if (emp.getGender() == 'M') maleRadio.setSelected(true);
		else femaleRadio.setSelected(true);
	
		dobDate.setDate(emp.getDob());
		salaryTextBox.setText(emp.getSalary());
		ninTextBox.setText(emp.getNatInscNo());
		emailTextBox.setText(emp.getEmail());
		startDate.setDate(emp.getStartDate());
		jobTitleTextBox.setText(emp.getTitle());
		empIdLabel.setText(emp.getId());
	}
	
	/**
	 * Clears fields.
	 */
	private void clearFields() {
		employee = new Employee();
		String empty = "";
		String emptyDate = "1-1-2016";
		nameTextBox.setText(empty);
		maleRadio.setSelected(true);
		dobDate.setDate(emptyDate);
		salaryTextBox.setText(empty);
		ninTextBox.setText(empty);
		emailTextBox.setText(empty);
		startDate.setDate(emptyDate);
		jobTitleTextBox.setText(empty);
		empIdLabel.setText(empty);
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
		add(imageLabel, c);
		
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
	
}
