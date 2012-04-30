package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainWindow extends JFrame{

	public MainWindow() {
		super();
		
		this.setTitle("Veno");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		this.setVisible(true);
		JPanel panel = new JPanel();
		
		JButton button = new JButton("OK");
		panel.add(button);

		this.setContentPane(panel);

	}
}
