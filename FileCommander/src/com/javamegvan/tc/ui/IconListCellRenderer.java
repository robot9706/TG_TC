package com.javamegvan.tc.ui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.filechooser.FileSystemView;

public class IconListCellRenderer extends DefaultListCellRenderer
{ 
	private Color textSelectionColor = Color.BLACK;
	private Color backgroundSelectionColor = new Color(135, 206, 250);
	private Color textNonSelectionColor = Color.BLACK;
	private Color backgroundNonSelectionColor = Color.WHITE;
	
	 @Override
	 public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean expanded) {
		 JLabel l = new JLabel(String.valueOf(value));
		 l.setOpaque(true);
		 l.setIcon(FileSystemView.getFileSystemView().getSystemIcon(new File("C:\\Users\\")));
		 
		 if (selected) {
			 l.setBackground(backgroundSelectionColor);
			 l.setForeground(textSelectionColor);
		 } else {
			 l.setBackground(backgroundNonSelectionColor);
			 l.setForeground(textNonSelectionColor);
		 }

		 
		 return l;
	 }
}