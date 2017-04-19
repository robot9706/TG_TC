package com.javamegvan.tc.ui.filetable;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class FileSizeTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	private static long[] _divisors = new long[] { 1024, 1024 * 1024, 1024 * 1024 * 1024 };
	private static String[] _sizeNames = new String[] { "KB", "MB", "GB" };
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		long fileSize = (long)table.getModel().getValueAt(row, col);
		
		String text = (fileSize < 0 ? "<DIR>" : String.valueOf(fileSize) + " B");
		
		if(fileSize > -1){
			int idx;
			for(idx = 0; idx < _divisors.length; idx++){
				if(fileSize < _divisors[idx]){
					break;
				}
			}
			if(idx != 0){
				float newSize = (float)fileSize / _divisors[idx - 1];
				newSize = (float)Math.round(newSize * 100.0f) / 100.0f;
				
				text = String.valueOf(newSize) + " " + _sizeNames[idx - 1];
			}
		}
		
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
