package uk.tlscott.spike;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.tlscott.SearchPanel;

public class TestMain {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				
				frame.add(new SearchPanel());
				
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}
