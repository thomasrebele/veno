package veno.model.treetable.file;


import java.io.File;

import javax.swing.UIManager;

import org.netbeans.swing.outline.RenderDataProvider;

import veno.model.treetable.aggregated.AggregatedTreeModelItem;

public class FileDataProvider implements RenderDataProvider {
	private File cast(Object o) {
		if(o instanceof File) return (File)o;
		if(o instanceof AggregatedTreeModelItem) {
			File f = (File)((AggregatedTreeModelItem) o).unpack();
			if(f==null) 
				return new File("/");
			else 
				return f;
		}
		return (File) o;
	}
	
	public java.awt.Color getBackground(Object o) {
		return null;
	}
	
	public String getDisplayName(Object o) {
		return cast(o).getName();
	}
	
	public java.awt.Color getForeground(Object o) {
		File f = cast(o);
		if (!f.isDirectory() && !f.canWrite()) {
			return UIManager.getColor ("controlShadow");
		}
		return null;
	}
	
	public javax.swing.Icon getIcon(Object o) {
		return null;
	}
	
	public String getTooltipText(Object o) {
		return cast(o).getAbsolutePath();
	}
	
	public boolean isHtmlDisplayName(Object o) {
		return false;
	}
  }