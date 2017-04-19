package com.javamegvan.tc.ui;

import java.io.File;

import javax.swing.JOptionPane;

public class Utils {
	public static String getFileExtension(File f){
		String ext = "";
		if(f.isFile()){
			String[] parts = f.getName().split("\\.");
			if(parts.length > 1){
				ext = parts[parts.length - 1];
			}
		}
		return ext;
	}
	
	public static String getFileNameWithoutExtension(File f){
		String name = f.getName();
		if(f.isFile()){
			if(name.indexOf('.') == -1){
				return name;
			}
			name = "";
			
			String[] parts = f.getName().split("\\.");
			int nameParts = parts.length - 1;
			for(int x = 0; x < nameParts; x++){
				name += parts[x] + (x < nameParts - 1 ? "." : "");
			}
		}
		return name;
	}
	
	public static void createMessageBox(String message, String title){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
