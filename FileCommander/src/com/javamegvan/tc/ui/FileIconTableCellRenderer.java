package com.javamegvan.tc.ui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;

public class FileIconTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	private Color textSelectionColor = Color.BLACK;
	private Color backgroundSelectionColor = new Color(135, 206, 250);
	private Color textNonSelectionColor = Color.BLACK;
	private Color backgroundNonSelectionColor = Color.WHITE;
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		Object valueAt = table.getModel().getValueAt(row, col);
		String s = "";
		if (valueAt != null) {
			File f = (File)valueAt;
			s = f.getName();
		}

		Icon i = FileSystemView.getFileSystemView().getSystemIcon((File)valueAt);
		JLabel l = new JLabel(i);
		l.setText(s);
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.LEFT);

		if (hasFocus) {
			l.setBackground(backgroundSelectionColor);
			l.setForeground(textSelectionColor);
		} else {
			l.setBackground(backgroundNonSelectionColor);
			l.setForeground(textNonSelectionColor);
		}

		return l;
	}
}
