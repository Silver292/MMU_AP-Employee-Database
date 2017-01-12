package uk.tlscott;

public class ControllerGUI {
    
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("uk.tlscott.tests.AllTests");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainForm frame = new MainForm();
		
				frame.setVisible(true);
			}

		});
	}
}
