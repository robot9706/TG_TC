package com.javamegvan.tc.ui.filetable;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;

public class FileIconTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		FileRow file = (FileRow)table.getModel().getValueAt(row, col);
		JLabel l = new JLabel(FileSystemView.getFileSystemView().getSystemIcon(file.TargetFile));
		l.setText((file.IsRootFolder ? ".." : file.TargetFile.getName()));
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.LEFT);
		
		if (table.getSelectedRow() == row) {
			l.setBackground(FileBrowseTable.BackgroundSelectionColor);
			l.setForeground(FileBrowseTable.TextSelectionColor);
		} else {
			l.setBackground(FileBrowseTable.BackgroundNonSelectionColor);
			l.setForeground(FileBrowseTable.TextNonSelectionColor);
		}

		return l;
	}
}
