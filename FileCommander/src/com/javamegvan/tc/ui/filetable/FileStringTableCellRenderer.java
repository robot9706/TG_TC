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
		
		FileBrowserTable bTable = (FileBrowserTable)table;
		if(bTable.Selection.contains((FileRow)table.getModel().getValueAt(row, 0))){
			l.setForeground(FileBrowserTable.TextSelectedColor);
		}else{
			l.setForeground(FileBrowserTable.TextColor);
		}
		
		if (table.getSelectedRow() == row) {
			l.setBackground(FileBrowserTable.BackgroundSelectionColor);
		} else {
			l.setBackground(FileBrowserTable.BackgroundNonSelectionColor);
		}

		return l;
	}
}