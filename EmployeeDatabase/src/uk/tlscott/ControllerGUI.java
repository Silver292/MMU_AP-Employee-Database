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

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ControllerGUI {

	static Image  image;
    static JLabel imageLabel;
    
    static JButton enterButton   = new JButton("Enter");
    static JButton clearButton   = new JButton("Clear");
    static JButton backButton    = new JButton("Back");
    static JButton forwardButton = new JButton("Forward");
    
    
    static JLabel titleLabel     = new JLabel("Enter Employee Information");
    static JLabel nameLabel      = new JLabel("Employee Name");                
    static JLabel genderLabel    = new JLabel("Employee Gender");            
    static JLabel dobLabel       = new JLabel("Employee D.O.B");                
    static JLabel salaryLabel    = new JLabel("Employee Salary");            
    static JLabel ninLabel       = new JLabel("National Insurance Number");     
    static JLabel emailLabel     = new JLabel("Employee Email");              
    static JLabel startDateLabel = new JLabel("Employee Start Date");     
    static JLabel jobTitleLabel  = new JLabel("Enter Job Title");           
    static JLabel empIdTextLabel = new JLabel("Employee No: ");                 
    static JLabel empIdLabel     = new JLabel("");
    
    static JTextField nameTextBox      = new JTextField(16);
    static JTextField genderTextBox    = new JTextField(16);
    static JTextField dobTextBox       = new JTextField(16);
    static JTextField salaryTextBox    = new JTextField(16);
    static JTextField ninTextBox       = new JTextField(16);
    static JTextField emailTextBox     = new JTextField(16);
    static JTextField startDateTextBox = new JTextField(16);
    static JTextField jobTitleTextBox  = new JTextField(16);
    static ButtonGroup genderGroup     = new ButtonGroup();
    
    static JRadioButton maleRadio   = new JRadioButton("Male");
    static JRadioButton femaleRadio = new JRadioButton("Female");
    
	public static void main(String[] args) {

		//TODO: remove this
		try {
			image = ImageIO.read(new File("assets/default.png"));
			image = image.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
			imageLabel = new JLabel(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainForm frame = new MainForm();
				frame.setSize(600,400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// set fonts 
				setFonts();
				
				
				// Fill text fields
				setTextFields(frame.dao.selectEmployeeById(1));
				
				//createGroupLayout(frame);
                
				setGridBagLayout(frame); 
				
				// add radio buttons to button group
				genderGroup.add(maleRadio);
				genderGroup.add(femaleRadio);
				
				
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
				
				
				
				frame.setJMenuBar(menuBar);
				
				
                frame.pack();
                
				frame.setVisible(true);
			}

		});
	}
	
	/**
	 * Sets the fonts of text components.
	 */
	private static void setFonts() {
		Font baseFont = new Font("DejaVu Sans", Font.PLAIN, 12);
		Font boldFont = new Font("DejaVu Sans", Font.BOLD, 12);
		Font titleFont = new Font("DejaVu Sans", Font.BOLD|Font.ITALIC, 16);
		
		JComponent[] textBoxes = {genderTextBox, dobTextBox, salaryTextBox, ninTextBox, emailTextBox, startDateTextBox, jobTitleTextBox};
		
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
	 * Sets text fields in form using details from employee.
	 * 
	 * @param emp
	 */
	private static void setTextFields(Employee emp) {
		nameTextBox.setText(emp.getName());
		// set gender radio button
		if (emp.getGender() == 'M') maleRadio.setSelected(true);
		else femaleRadio.setSelected(true);
		
		dobTextBox.setText(emp.getDob());
		salaryTextBox.setText(emp.getSalary());
		ninTextBox.setText(emp.getNatInscNo());
		emailTextBox.setText(emp.getEmail());
		startDateTextBox.setText(emp.getStartDate());
		jobTitleTextBox.setText(emp.getTitle());
		empIdLabel.setText(emp.getId());
	}
	
	/**
	 * Lays out the frame using {@link GroupLayout}.
	 * 
	 * @param frame
	 */
	@SuppressWarnings("unused")
	private static void createGroupLayout(MainForm frame) {
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		frame.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		setTextFields(frame.dao.selectEmployeeById(1));
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(nameLabel)
						.addComponent(genderLabel)   
						.addComponent(dobLabel)      
						.addComponent(salaryLabel)   
						.addComponent(ninLabel)      
						.addComponent(emailLabel)    
						.addComponent(startDateLabel)
						.addComponent(jobTitleLabel) 
						.addComponent(enterButton)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(titleLabel)
						.addComponent(nameTextBox)     
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(maleRadio)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(femaleRadio)    
										)
								)
						.addComponent(dobTextBox)      
						.addComponent(salaryTextBox)   
						.addComponent(ninTextBox)      
						.addComponent(emailTextBox)    
						.addComponent(startDateTextBox)
						.addComponent(jobTitleTextBox) 
						.addComponent(clearButton)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(empIdTextLabel)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(empIdLabel)    
										)
								)
						.addComponent(imageLabel)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(backButton)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(forwardButton)
										)
								)
						)
				);
		
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(titleLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(nameLabel)
										.addComponent(nameTextBox)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(genderLabel)
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(maleRadio)
														.addComponent(femaleRadio)
														)
												)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(dobLabel)
										.addComponent(dobTextBox)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(salaryLabel)
										.addComponent(salaryTextBox)
										)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(ninLabel)
										.addComponent(ninTextBox)  
										)                        
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(emailLabel)
										.addComponent(emailTextBox) 
										)                        
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(startDateLabel)
										.addComponent(startDateTextBox)
										)                        
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jobTitleLabel)
										.addComponent(jobTitleTextBox) 
										)                        
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(enterButton)
										.addComponent(clearButton)
										)
								)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(empIdTextLabel)
										.addComponent(empIdLabel)
										)
								.addComponent(imageLabel)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(backButton)
												.addComponent(forwardButton)
												)
										)
								)
						)
				);
	}
	
	/**
	 * Lays out the frame using {@link GridBagLayout}.
	 * 
	 * @param frame
	 */
	private static void setGridBagLayout(MainForm frame) {
		JPanel mainPanel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(mainPanel);
		
		GridBagConstraints c = new GridBagConstraints();
		
		JComponent[] columnOne = {nameLabel, genderLabel, dobLabel, salaryLabel, 
				ninLabel, emailLabel, startDateLabel, jobTitleLabel};
		
		JComponent[] columnTwo = {dobTextBox, salaryTextBox, ninTextBox, emailTextBox, 
				startDateTextBox, jobTitleTextBox};
		
		
		// Add title
		c.insets = new Insets(4, 4, 4, 4);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.NORTH;
		mainPanel.add(titleLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_START;
		
		for (int i = 0; i < columnOne.length; i++) {
			mainPanel.add(columnOne[i], c);
			c.gridy++;
		}
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(enterButton, c);
		
		// Column two
		c.ipady = 10;
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		mainPanel.add(nameTextBox, c);
		
		c.gridy++;
		mainPanel.add(maleRadio, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		mainPanel.add(femaleRadio, c); 
		
		c.gridy++;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		
		for (int i = 0; i < columnTwo.length; i++) {
			mainPanel.add(columnTwo[i], c);
			c.gridy++;
		}
		
		c.ipady = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		mainPanel.add(clearButton, c);
		
		// Column three
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		mainPanel.add(empIdTextLabel, c);
		
		c.insets = new Insets(4, 105, 4, 4);
		mainPanel.add(empIdLabel, c);
		
		c.insets = new Insets(4, 4, 4, 4);
		c.gridy++;
		c.gridheight = 7;
		mainPanel.add(imageLabel, c);
		
		c.ipadx = 60;
		c.gridheight = 1;
		c.gridy+=7;
		backButton.setPreferredSize(new Dimension(60, 25));
		mainPanel.add(backButton, c);
		
		c.ipadx = 0;
		c.weightx = 1.0;
		c.insets = new Insets(4, 135, 4, 4);
		forwardButton.setPreferredSize(new Dimension(120, 25));
		mainPanel.add(forwardButton, c);
	}
	
}
