package veno.gui.icon;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IconLoader {
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	final static String prefix = "veno/gui/icon/";
	public enum Icons {
		ADD,
		EDIT,
		DELETE,
		DIRECTION_UP,
		DIRECTION_DOWN,
		DIRECTION_LEFT,
		DIRECTION_RIGHT,
		IMPORT,
		EXPORT
	}
	static Map<Icons, String> map;
	
	private IconLoader() {
		IconLoader.init();
	}
	
	public static void init() {
		map = new HashMap<Icons, String>();
		map.put(Icons.ADD, "list-add.png");
		map.put(Icons.EDIT, "edit.png");
		map.put(Icons.DELETE, "list-remove.png");
		map.put(Icons.DIRECTION_UP, "direction-up.png");
		map.put(Icons.DIRECTION_DOWN, "direction-down.png");
		map.put(Icons.DIRECTION_LEFT, "direction-left.png");
		map.put(Icons.DIRECTION_RIGHT, "direction-right.png");
		map.put(Icons.IMPORT, "import.png");
		map.put(Icons.EXPORT, "export.png");
		
	}

	public static ImageIcon getIcon(Icons val) {
		if(map == null) init();
		String filename = map.get(val);
		if(filename == null) filename = "unknown.png";
		filename = prefix + "size16/" + filename;
		System.out.println(filename);
		URL url = ClassLoader.getSystemResource(filename);
		return new ImageIcon(url);
	}
	
	public static ImageIcon getIcon(Icons val, int size) {
		/*ImageIcon icon = getIcon(val);
		icon.setImage(icon.getImage().getScaledInstance(size, size, 0));
		return icon;*/

		if(map == null) init();
		String filename = map.get(val);
		if(filename == null) filename = "unknown.png";
		filename = prefix + "size" + size + "/" + filename;
		System.out.println(filename);
		URL url = ClassLoader.getSystemResource(filename);
		return new ImageIcon(url);
	}
	
	public static JButton getButton(Icons val) {
		JButton button = new JButton();
		button.setIcon(IconLoader.getIcon(val));
		return button;
	}
}
