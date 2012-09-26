package veno.gui.component;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import veno.gui.component.general.ScrollPane;
import veno.model.music.Piece;
import veno.model.property.AbstractPropertyContainer;
import veno.model.property.PiecePropertyContainer;
import veno.model.property.PropertyContainerTableModel;

public class PropertyContainerPanel extends JPanel {
	private static final long serialVersionUID = -3488294289152833189L;
	private AbstractPropertyContainer container = null;
	private PropertyContainerTableModel tablemodel;
	private JTable table;
	
	public PropertyContainerPanel() {
		table = new JTable();
		tablemodel = new PropertyContainerTableModel(null);

		this.add(new ScrollPane(table));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void showPropertyContainer(AbstractPropertyContainer c) {
		this.container = c;
		tablemodel.setContainer(c);
		table.setModel(tablemodel);

		DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
		singleclick.setClickCountToStart(1);
		table.getColumnModel().getColumn(1).setCellEditor(singleclick);

		table.repaint();
		//table.getColumnModel().getColumn(0).setCellRenderer(new VariableRowHeightRenderer());
	}
}

class VariableRowHeightRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = -2698224548911608470L;

	public VariableRowHeightRenderer() {
		super();
		setOpaque(true);
		setHorizontalAlignment(JLabel.CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		if (isSelected) {
			setBackground(UIManager.getColor("Table.selectionBackground"));
		}

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

			if (table.isCellEditable(row, column)) {
				super.setForeground(UIManager.getColor("Table.focusCellForeground"));
				super.setBackground(UIManager.getColor("Table.focusCellBackground"));
			}
		} else {
			setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		}
		setText((String) (value));
		table.setRowHeight(row, getPreferredSize().height + row * 10);
		return this;
	}
}

/*
class PieceInfoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -7213012469477599960L;
	public String[] m_colNames = { "Variable Dimension" };
	public Class[] m_colTypes = { String.class };
	
	private Piece piece = null;

	public PieceInfoTableModel() {
		super();
	}
	
	public void setPiece(Piece p) {
		piece = p;
	}

	public int getColumnCount() {
		return m_colNames.length;
	}

	public int getRowCount() {
		return 3;
	}

	public String getColumnName(int col) {
		return "" + col;
	}

	public Object getValueAt(int row, int col) {
		return "aa";
	}
}*/