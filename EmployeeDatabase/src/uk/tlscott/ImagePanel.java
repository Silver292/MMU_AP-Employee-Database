package uk.tlscott;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ImagePane extends JLabel{
	private static final long serialVersionUID = 1L;
	private Icon defaultImage;

	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	
	public ImagePane() {
		super();
		// set default image
		try {
			defaultImage = new ImageIcon(ImageIO.read(new File("assets/default.png")));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Default image failed to load", e);
		}
	}
	
	public void setImage(File file) {
		
		try {
			// get image from file
			Image image = ImageIO.read(file);
			image = image.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
			this.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Image failed to load", e);
			JOptionPane.showMessageDialog(null, "Error loading image", "Image Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setDefaultImage() {
		this.setIcon(this.defaultImage);
	}
}
