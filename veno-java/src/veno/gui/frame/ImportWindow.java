package veno.gui;


import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import veno.gui.component.FileSelectPanel;
import veno.gui.component.ScrolledImagePanel;

public class ImportWindow extends JFrame {
	private static final long serialVersionUID = -3372705110837747317L;
	
	public ImportWindow() {
		super();
		this.setTitle("Veno - Import");
		this.setSize(600,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// file choosing
		JPanel fileSelectPanel = new FileSelectPanel();
		
		// Image
		ScrolledImagePanel image = new ScrolledImagePanel("score001.gif");
		
		JPanel actionPanel = new JPanel();
		actionPanel.add(image);

		JPanel panelRight = new JPanel();
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		panelRight.add(actionPanel);
		panelRight.add(image);
		
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, fileSelectPanel, panelRight);
		splitPane.setContinuousLayout(true);
		
		
		this.setContentPane(splitPane);
		this.setVisible(true);
	}
}
