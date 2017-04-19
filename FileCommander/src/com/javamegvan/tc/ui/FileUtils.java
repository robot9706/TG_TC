package com.javamegvan.tc.ui;

import java.io.File;

public class FileUtils {
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
			name = "";
			
			String[] parts = f.getName().split("\\.");
			int nameParts = parts.length - 1;
			for(int x = 0; x < nameParts; x++){
				name += parts[x] + (x < nameParts - 1 ? "." : "");
			}
		}
		return name;
	}
}
