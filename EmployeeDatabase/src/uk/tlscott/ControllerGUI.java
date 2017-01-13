package uk.tlscott;

public class ControllerGUI {
    
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MainFrame frame = new MainFrame();
		
				frame.setVisible(true);
			}

		});
	}
}
