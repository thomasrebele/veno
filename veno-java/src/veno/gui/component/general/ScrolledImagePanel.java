package veno.gui.component;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScrolledImagePanel extends JPanel {
	private static final long serialVersionUID = -238932412090389073L;
	private JScrollPane scrollPane;
	private JLabel imageLabel;
	private JPanel contentPanel;
	private JPanel toolPanel;
	private JSpinner scaleSpinner;
	private SpinnerNumberModel scaleModel;
	private BufferedImage originalImage;
	private double imageScale = 0;
	private BufferedImage scaledImage;
	private int imageOrientation = 0;
	private int nextImageOrientation = 0;
	private BufferedImage rotatedImage;
	
	public ScrolledImagePanel() {

		//contentPanel = new JPanel();
		contentPanel = this;
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		toolPanel = new JPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.X_AXIS));
		toolPanel.setMaximumSize(new Dimension(10000,40));
		initializeToolPanel();
		contentPanel.add(toolPanel);

		imageLabel = new JLabel();

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.setWheelScrollingEnabled(true);
		//scrollPane.getViewport().setMaximumSize(new Dimension(200,200));
		scrollPane.getViewport().add(imageLabel);
		contentPanel.add(scrollPane);
		//this.add(contentPanel);
	}
	
	private void initializeToolPanel() {
		// buttons for scaling of image
		scaleModel = new SpinnerNumberModel(100.0, 1.0, 1000.0, 10.0);
		scaleSpinner = new JSpinner(scaleModel);
		scaleSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				applySettings();
			}});
		toolPanel.add(scaleSpinner);
		
		// direction buttons
		ButtonGroup directionButtonsGroup = new ButtonGroup();
		String buttonCaptions[] = {"<", "^", ">", "_"};
		final JToggleButton directionButtons[] = new JToggleButton[4];
		for(int i=0; i<4; i++) {
			final int tmpi = i;
			directionButtons[i] = new JToggleButton();
			directionButtons[i].setText(buttonCaptions[i]);
			directionButtons[i].addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(directionButtons[tmpi].isSelected()) {
						int lastOrientation = nextImageOrientation;
						nextImageOrientation = ((tmpi-1)*90 + 360) % 360;
						if(lastOrientation != nextImageOrientation) applySettings();
					}
				}});
			toolPanel.add(directionButtons[i]);
			directionButtonsGroup.add(directionButtons[i]);
		}
		
		directionButtons[1].setSelected(true);
	}
	
	public ScrolledImagePanel(String imagePath) {
		this();
		
		try {
			originalImage = ImageIO.read(new File(imagePath));
			applySettings();
		} catch (IOException e) {
			System.out.println("ScrolledImage: couldn't load image '" + imagePath + "'");
			e.printStackTrace();
		}
		//getViewport().add(imageLabel);
	}
	
	private BufferedImage resizeImage(BufferedImage image, double scale) {
		if(scale < 0 || image == null) {
			return null;
		}
		int width = (int)(scale*image.getWidth());
		int height = (int)(scale*image.getHeight());
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
	 
		return resizedImage;
	}
	
	private BufferedImage rotateImage(BufferedImage image, double degree) {
		if(image == null) {
			return null;
		}
		double radian = Math.toRadians(degree);
		int width = image.getWidth();
		int height = image.getHeight();
		int widthhalf = width / 2;
		int heighthalf = height / 2;

		int newwidth = (int)Math.abs(width * Math.cos(radian)) + (int)Math.abs(height * Math.sin(radian));
		int newheight = (int)Math.abs(width * Math.sin(radian)) + (int)Math.abs(height * Math.cos(radian));
		int newwidthhalf = newwidth / 2;
		int newheighthalf = newheight / 2;
		
		AffineTransform shift = AffineTransform.getTranslateInstance(newwidthhalf - widthhalf, newheighthalf - heighthalf);
		AffineTransform rotate = AffineTransform.getRotateInstance(radian, newwidthhalf, newheighthalf);
		
		AffineTransform total = new AffineTransform();
		total.concatenate(rotate);
		total.concatenate(shift);
		AffineTransformOp op = new AffineTransformOp(total, AffineTransformOp.TYPE_BICUBIC);
		
		if(newwidth <= 0 || newheight <= 0) {
			System.out.println("error: new width " + newwidth + " height " + newheight);
			return null;
		}
		BufferedImage rotatedImage = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = rotatedImage.createGraphics();
		g.drawImage(op.filter(image,null), 0, 0, null);
		g.dispose();
		
		return rotatedImage;
	}
	
	private void applySettings() {
		boolean redo = false;
		
		double scale = (Double)scaleModel.getNumber()/100;
		if(scale != imageScale) {
			scaledImage = resizeImage(originalImage, scale);
			if(scaledImage == null) return;
			imageScale = scale;
			redo = true;
		}

		System.out.println("orientation " + imageOrientation + " next " + nextImageOrientation);
		if(imageOrientation != nextImageOrientation || redo) {
			rotatedImage = rotateImage(scaledImage, nextImageOrientation);
			if(rotatedImage == null) return;
			imageOrientation = nextImageOrientation;
			redo = true;
		}
		
		if(rotatedImage == null) return;
		imageLabel.setIcon(new ImageIcon(rotatedImage));
		
	}
}
