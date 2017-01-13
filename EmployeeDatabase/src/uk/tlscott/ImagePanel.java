package uk.tlscott;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_IMAGE_PATH = "assets/default.png";
	private static final int DEFAULT_WIDTH = 250;
	private static final int DEFAULT_HEIGHT = 250;
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	
	private BufferedImage image;
	private BufferedImage defaultImage;
	
	public ImagePanel() {
		super();
		Dimension size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		try {
			image = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
			defaultImage = image;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Default image failed to load", e);
		}
	}
	
	public void setImage(File file) {
		if(file == null) {
			image = defaultImage;
		} else {
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Image failed to load", e);
				JOptionPane.showMessageDialog(null, "Error loading image", "Image Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void setDefaultImage() {
		image = defaultImage;
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}

	
	public void setResizedImage(File file) {
		setImage(file);
		image = resizeImage(image);
		repaint();
	}

	private BufferedImage resizeImage(BufferedImage image) {
		int type = BufferedImage.TYPE_INT_ARGB;
		
		BufferedImage resizedImage = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
		g.dispose();
		
		return resizedImage;
	}
}
