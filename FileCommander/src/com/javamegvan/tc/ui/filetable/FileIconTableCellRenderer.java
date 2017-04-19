package com.javamegvan.tc.ui.filetable;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.javamegvan.tc.ui.FileUtils;

public class FileIconTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		
		FileRow file = (FileRow)table.getModel().getValueAt(row, col);
		
		Icon icon = IconCache.FileIcon;
		if(file.IsRootFolder){
			icon = IconCache.RootIcon;
		}else if(file.TargetFile.isDirectory()){
			icon = IconCache.FolderIcon;
		}
		
		JLabel l = new JLabel(icon);
		
		String text = (file.IsRootFolder ? ".." : FileUtils.getFileNameWithoutExtension(file.TargetFile));
		if(file.TargetFile.isDirectory()){
			text = "[" + text + "]";
		}
		
		l.setText(text);
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
