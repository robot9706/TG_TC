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
		
		if (hasFocus) {
			l.setBackground(FileBrowseTable.BackgroundSelectionColor);
			l.setForeground(FileBrowseTable.TextSelectionColor);
		} else {
			l.setBackground(FileBrowseTable.BackgroundNonSelectionColor);
			l.setForeground(FileBrowseTable.TextNonSelectionColor);
		}

		return l;
	}
}