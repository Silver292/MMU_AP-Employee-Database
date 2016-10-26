import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ControllerGUI {

	static BufferedImage image;
	static JLabel imageLabel;
	
	static JLabel titleLabel = new JLabel("Employee Information");
	static JLabel nameLabel = new JLabel("Employee Name");                
	static JLabel genderLabel = new JLabel("Employee Gender");            
	static JLabel dobLabel = new JLabel("Employee D.O.B");                
	static JLabel salaryLabel = new JLabel("Employee Salary");            
	static JLabel ninLabel = new JLabel("National Insurance Number");     
	static JLabel emailLabel = new JLabel("Employee Email");              
	static JLabel startDateLabel = new JLabel("Employee Start Date");     
	static JLabel jobTitleLabel = new JLabel("Employee Title");           
	static JLabel empIdLabel = new JLabel("Employee No");                 
	
    static JTextField nameTextBox = new JTextField(16);
    static JTextField genderTextBox = new JTextField(16);
    static JTextField dobTextBox = new JTextField(16);
    static JTextField salaryTextBox = new JTextField(16);
    static JTextField ninTextBox = new JTextField(16);
    static JTextField emailTextBox = new JTextField(16);
    static JTextField startDateTextBox = new JTextField(16);
    static JTextField jobTitleTextBox = new JTextField(16);
    static JTextField empIdTextBox = new JTextField(16);
	
	public static void main(String[] args) {

		//TODO: remove this
		try {
			image = ImageIO.read(new File("assets/default.png"));
			imageLabel = new JLabel(new ImageIcon(image));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
							.addComponent(empIdLabel)
							)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(titleLabel)
							.addComponent(nameTextBox)     
							.addComponent(genderTextBox)   
							.addComponent(dobTextBox)      
							.addComponent(salaryTextBox)   
							.addComponent(ninTextBox)      
							.addComponent(emailTextBox)    
							.addComponent(startDateTextBox)
							.addComponent(jobTitleTextBox) 
							.addComponent(empIdTextBox)    
							)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(imageLabel)
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
	                                        .addComponent(genderTextBox)
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
	                                		.addComponent(empIdLabel)
	                                		.addComponent(empIdTextBox)
	                                		)
	                                )
                                .addComponent(imageLabel)
                        		)
                        );
                
                frame.pack();
                
				frame.setVisible(true);
			}

			private void setTextFields(Employee emp) {
			    nameTextBox.setText(emp.getName());
			    genderTextBox.setText(String.valueOf(emp.getGender()));
			    dobTextBox.setText(emp.getDob());
			    salaryTextBox.setText(emp.getSalary());
			    ninTextBox.setText(emp.getNatInscNo());
			    emailTextBox.setText(emp.getEmail());
			    startDateTextBox.setText(emp.getStartDate());
			    jobTitleTextBox.setText(emp.getTitle());
			    empIdTextBox.setText(emp.getId());
			}
		});
	}
	
}
