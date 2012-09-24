package veno.model.treetable;

import java.awt.Color;

import javax.swing.Icon;

import org.netbeans.swing.outline.RenderDataProvider;

public class StringDataProvider implements RenderDataProvider {

	@Override
	public String getDisplayName(Object o) {
		if(o instanceof AggregatedTreeModelItem) {
			o = ((AggregatedTreeModelItem) o).unpack();
		}
		if(o == null) return "(null)";
		return o.toString();
	}

	@Override
	public boolean isHtmlDisplayName(Object o) {
		return false;
	}

	@Override
	public Color getBackground(Object o) {
		return null;
	}

	@Override
	public Color getForeground(Object o) {
		return null;
	}

	@Override
	public String getTooltipText(Object o) {
		return null;
	}

	@Override
	public Icon getIcon(Object o) {
		return null;
	}
}