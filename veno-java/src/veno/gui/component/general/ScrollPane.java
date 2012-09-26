package veno.gui.component.general;

import java.awt.Component;

import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {
	private static final long serialVersionUID = 964033451549553391L;

	public ScrollPane() {
		super();
		init();
	}
	
	public ScrollPane(Component view) {
		super(view);
		init();
	}
	
	public ScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		init();
	}
	
	public ScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		init();
	}
	
	private void init() {
		getHorizontalScrollBar().setUnitIncrement(10);
		getVerticalScrollBar().setUnitIncrement(10);
	}
}
