package uk.tlscott;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ControllerGUI {

	static BufferedImage image;
	static JLabel imageLabel;
	
	static JButton enterButton = new JButton("Enter");
	static JButton clearButton = new JButton("Clear");
	static JButton backButton = new JButton("Back");
	static JButton forwardButton = new JButton("Forward");
	
	
	static JLabel titleLabel = new JLabel("Employee Information");
	static JLabel nameLabel = new JLabel("Employee Name");                
	static JLabel genderLabel = new JLabel("Employee Gender");            
	static JLabel dobLabel = new JLabel("Employee D.O.B");                
	static JLabel salaryLabel = new JLabel("Employee Salary");            
	static JLabel ninLabel = new JLabel("National Insurance Number");     
	static JLabel emailLabel = new JLabel("Employee Email");              
	static JLabel startDateLabel = new JLabel("Employee Start Date");     
	static JLabel jobTitleLabel = new JLabel("Employee Title");           
	static JLabel empIdTextLabel = new JLabel("Employee No: ");                 
	static JLabel empIdLabel;
	
    static JTextField nameTextBox = new JTextField(16);
    static JTextField genderTextBox = new JTextField(16);
    static JTextField dobTextBox = new JTextField(16);
    static JTextField salaryTextBox = new JTextField(16);
    static JTextField ninTextBox = new JTextField(16);
    static JTextField emailTextBox = new JTextField(16);
    static JTextField startDateTextBox = new JTextField(16);
    static JTextField jobTitleTextBox = new JTextField(16);
	
    static JRadioButton maleRadio = new JRadioButton("Male");
    static JRadioButton femaleRadio = new JRadioButton("Female");
    
	public static void main(String[] args) {

		//TODO: remove this
		try {
			image = ImageIO.read(new File("assets/default.png"));
			imageLabel = new JLabel(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainForm frame = new MainForm();
				frame.setSize(600,400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
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
                
                frame.pack();
                
				frame.setVisible(true);
			}

			private void setTextFields(Employee emp) {
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
			    empIdLabel = new JLabel(emp.getId());
			}
		});
	}
	
}
