package veno.gui.component.general;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class FileSelectPanel extends JPanel {
	public FileSelectPanel() {
		
		TreeNode root = new DefaultMutableTreeNode("Dateien");
		JTree tree = new JTree(root);
		this.add(tree);
	}
}
