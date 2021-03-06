package com.javamegvan.tc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Utils {
	public static String getFileExtension(File f){
		String ext = "-";
		if(f.isFile()){
			ext = getFileExtension(f.getName());
		}
		
		if(ext.length() == 0){
			ext = "-";
		}
		
		return ext;
	}
	
	public static String getFileExtension(String name){
		String ext = "";

		if(name.charAt(0) == '.'){
			name = name.substring(1);
		}
			
		String[] parts = name.split("\\.");
		if(parts.length > 1){
			ext = parts[parts.length - 1];
		}

		return ext;
	}
	
	public static String getFileNameWithoutExtension(File f){
		String name = f.getName();
		if(name == null || name.length() == 0){
			name = f.getPath();
			if(name.endsWith("/") || name.endsWith("\\")){
				name = name.substring(0, name.length() - 1);
			}
		}
		if(f.isFile()){
			if(name.charAt(0) == '.'){
				name = name.substring(1);
			}
			
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
	
	public static String getFileNameWithoutExtension(String name){
		if(name.charAt(0) == '.'){
			name = name.substring(1);
		}
			
		if(name.indexOf('.') == -1){
			return name;
		}

		String[] parts = name.split("\\.");
		
		name = "";
		int nameParts = parts.length - 1;
		for(int x = 0; x < nameParts; x++){
			name += parts[x] + (x < nameParts - 1 ? "." : "");
		}

		return name;
	}
	
	public static String getDriveLetter(File f){
		do{
			if(f.getParentFile() == null)
				return f.getPath();
			f = f.getParentFile();
		}while(f.getParentFile() != null);
		
		return f.getPath();
	}
	
	public static void createMessageBox(Component owner, String message, String title){
		JOptionPane.showMessageDialog(owner, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static Color getBrighterColor(Color original, float brightness){
		brightness += 1.0f;
		
		return new Color(Math.min((int)(original.getRed() * brightness), 255),
						 Math.min((int)(original.getGreen() * brightness), 255),
						 Math.min((int)(original.getBlue() * brightness), 255));
	}
	
	public static void centerJForm(Component f){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation((dim.width / 2)- (f.getSize().width / 2), (dim.height / 2) - (f.getSize().height / 2));
	}
	
	public static boolean createYesNoDialog(Component owner, String question, String title){
		return (JOptionPane.showOptionDialog(owner, question, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, new String[] { "Igen", "Nem" }, "Nem") == JOptionPane.YES_OPTION);
	}
	
	public static void createNewEmptyFile(MainFrame frame){
		FileBrowserComponent cp = frame.getFocusedBrowser();

		String response = JOptionPane.showInputDialog("Az �j f�jl neve?");
		if (response != null && response.length() > 0) {
			File newFile = new File(cp.getCurrentFolder(), response);
			if (newFile.exists()) {
				Utils.createMessageBox(frame, "Ilyen nev� f�jl m�r l�tezik!", "Hiba");
			} else {
				try {
					if(!newFile.createNewFile()){
						Utils.createMessageBox(frame, "Sikertelen a f�jl l�trehoz�sa!", "�j f�jl");
					}
				} catch (IOException e) {
					e.printStackTrace();
					Utils.createMessageBox(frame, "Sikertelen a f�jl l�trehoz�sa: " + e.getMessage(), "�j f�jl");
				}
			}
		}
		
		frame.smartRefreshFileEntries(cp);
	}
}
