package veno.gui.frame;

import javax.swing.JDialog;

import veno.gui.component.PropertyContainerPanel;
import veno.model.property.AbstractPropertyContainer;

public class PropertyContainerDialog extends JDialog {
	private static final long serialVersionUID = -7922321362433192296L;
	private static AbstractPropertyContainer container;
	PropertyContainerPanel containerPanel;
	
	public PropertyContainerDialog(AbstractPropertyContainer template) {
		this.setSize(400, 600);
		containerPanel = new PropertyContainerPanel();
		container = template;
		containerPanel.showPropertyContainer(container);
		template.setEditable(AbstractPropertyContainer.Editable.TRUE);
		this.add(containerPanel);
		this.setLocationRelativeTo(null);
	}
}
