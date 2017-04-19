package com.javamegvan.tc.ui.filetable;

import java.io.File;

public class FileRow {
	public File TargetFile;
	public boolean IsRootFolder;
	
	public FileRow(File target, boolean root){
		TargetFile = target;
		IsRootFolder = root;
	}
}
