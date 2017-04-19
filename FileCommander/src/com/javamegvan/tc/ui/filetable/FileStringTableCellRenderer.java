package com.javamegvan.tc.ui.filetable;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class FileStringTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 4L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		String text = (String)table.getModel().getValueAt(row, col);
		
		JLabel l = new JLabel(text);
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.LEFT);
		
		l.setForeground(FileBrowseTable.TextColor);
		if (table.getSelectedRow() == row) {
			l.setBackground(FileBrowseTable.BackgroundSelectionColor);
		} else {
			l.setBackground(FileBrowseTable.BackgroundNonSelectionColor);
		}

		return l;
	}
}