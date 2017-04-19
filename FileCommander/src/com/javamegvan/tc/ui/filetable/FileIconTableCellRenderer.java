package com.javamegvan.tc.ui.filetable;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.javamegvan.tc.ui.Utils;

public class FileIconTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		
		FileRow file = (FileRow)table.getModel().getValueAt(row, col);
		
		Icon icon = null;
		if(file.IsRootFolder){
			icon = IconCache.RootIcon;
		}else if(file.TargetFile.isDirectory()){
			icon = IconCache.FolderIcon;
		}else if(file.TargetFile.isFile()){
			if(file.TargetFile.isHidden()){
				icon = IconCache.HiddenFileIcon;
			}else{
				icon = IconCache.FileIcon;
			}
		}
		
		JLabel l = new JLabel(icon);
		
		String text = (file.IsRootFolder ? ".." : Utils.getFileNameWithoutExtension(file.TargetFile));
		if(file.TargetFile.isDirectory()){
			text = "[" + text + "]";
		}
		
		l.setText(text);
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.LEFT);
		
		FileBrowserTable bTable = (FileBrowserTable)table;
		bTable.setLabelColors(l, table.getSelectedRow() == row, bTable.Selection.contains(file));
		
		return l;
	}
}
