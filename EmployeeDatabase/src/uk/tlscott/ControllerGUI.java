package uk.tlscott;

public class ControllerGUI {
    
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainForm frame = new MainForm();
		
                frame.pack();
                
				frame.setVisible(true);
			}

		});
	}
}
