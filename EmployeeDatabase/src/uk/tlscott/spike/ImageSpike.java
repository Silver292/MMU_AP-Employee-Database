package uk.tlscott.spike;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.tlscott.ImagePanel;

public class ImageSpike {
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame frame = new JFrame();
				ImagePanel p = new ImagePanel();
				frame.getContentPane().add(p);
				frame.setSize(400,300);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}

		});
	}
	
	private static class SpikeImagePane extends JPanel {
		
		private BufferedImage image;
		
		public SpikeImagePane() {
			try {
				image = ImageIO.read(new File("assets/default.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}
}
