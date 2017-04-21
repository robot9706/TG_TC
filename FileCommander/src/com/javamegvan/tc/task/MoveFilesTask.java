package com.javamegvan.tc.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MoveFilesTask extends Task {
	private ArrayList<File> _files;
	private File _sourceFolder;
	private File _targetFolder;
	private boolean _move;
	
	public MoveFilesTask(ArrayList<File> sourceFiles, File sourceFolder, File targetFolder, boolean move){
		_files = sourceFiles;
		_sourceFolder = sourceFolder;
		_targetFolder = targetFolder;
		_move = move;
	}
	
	public void doTask() {
		try {
			URI baseUri = _sourceFolder.toURI();
			
			ArrayList<File> allFiles = new ArrayList<File>();
			
			for(File f : _files){
				discoverFiles(f, allFiles);
			}
			
			super.setTotalMax(allFiles.size());
			
			boolean overwriteAll = false;
			boolean overwriteChoiceMade = false;
			
			int fileIndex = 0;
			for(File f : allFiles){
				fileIndex++;
				super.setTotalProgress(fileIndex);
				
				super.setMessage((_move ? "Áthelyezés: " : "Másolás: ") + f.getName());
				
				File target = new File(_targetFolder, baseUri.relativize(f.toURI()).getPath());
				if(f.isFile()){
					f.getParentFile().mkdirs();
					
					boolean copy = false;
					
					if(target.exists()){
						if(overwriteChoiceMade){
							copy = overwriteAll;
						}else{
							String[] options = new String[] { "Felülír", "Mindent felülírja", "Ne írja felül", "Egyiket se írja felül", "Mégse" };
							switch(JOptionPane.showOptionDialog(super.getMain(), "Ilyen nevû fájl már létezik: " + target.getName(),
									"Fájl már létezik", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[4]))
							{
							case 0:
								copy = true;
								break;
							case 1:
								copy = true;
								overwriteAll = true;
								overwriteChoiceMade = true;
								break;
							case 2:
								copy = false;
								break;
							case 3:
								copy = false;
								overwriteAll = false;
								overwriteChoiceMade = true;
								break;
							case 4:
								super.requestCancel();
								break;
							}
						}			
					}else{
						copy = true;
					}
					
					if(super.isCancelRequested()){
						super.taskDone();
						getMain().refreshFileEntries();
						return;
					}
					
					if(copy){
						copyFile(f, target);
						
						if(super.isCancelRequested()){
							super.taskDone();
							getMain().refreshFileEntries();
							return;
						}
					}
				}else{
					target.mkdir();
				}
			}
			
			if(_move){
				for(File f : allFiles){
					deleteAll(f);
				}
			}
			
			super.taskDone();
			getMain().refreshFileEntries();
		} catch(Exception ex) {
			super.taskFailed(ex);
		}
	}
	
	private void discoverFiles(File root, ArrayList<File> target){
		target.add(root);
		
		if(root.listFiles() != null){
			for(File f : root.listFiles()){
				if(f.isFile()){
					target.add(f);
				}else if(f.isDirectory()){
					discoverFiles(f, target);
				}
			}
		}
	}
	
	private void copyFile(File source, File target) throws Exception{
		FileOutputStream fo = new FileOutputStream(target);
		FileInputStream fi = new FileInputStream(source);
		
		byte[] buffer = new byte [1024];
		int length;
		long total = source.length();
		long progress = 0;
		
		super.setPartMax((int)(total / 1024));
		
		while ((length = fi.read(buffer)) > 0) {
			fo.write(buffer, 0, length);
			
			progress += length;
			super.setPartProgress((int)(progress / 1024));
			
			if(super.isCancelRequested()){
				if(target.exists()){
					target.delete();
				}
				
				fi.close();
				fo.close();
				
				return;
			}
		}
		
		fi.close();
		fo.close();
	}
	
	public void deleteAll(File f) {
		if(f.isDirectory() && f.listFiles() != null){
			for(File s : f.listFiles()){
				s.delete();
			}
		}
		f.delete();
	}
}
