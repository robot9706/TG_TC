package com.javamegvan.tc.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MoveFilesTask extends Task {
	private ArrayList<File> _files;
	public File _targetFolder;
	private boolean _move;
	
	public MoveFilesTask(ArrayList<File> sourceFiles, File targetFolder, boolean move){
		_files = sourceFiles;
		_targetFolder = targetFolder;
		_move = move;
	}
	
	public void doTask() {
		try{
			int total = 0;
			
			super.taskDone();
			getMain().refreshFileEntries();
		}catch(Exception ex){
			super.taskFailed(ex);
		}
	}
	
	public void copy(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);

				copy(srcFile, destFile);
			}
		} else {
			
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			long expectedBytes = src.length();
			byte[] buffer = new byte[1024];

			
			long totalBytesCopied=0;
			int length;
			int progress=0;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
				
				totalBytesCopied+= length;
			    progress = (int)Math.round(((double)totalBytesCopied / (double)expectedBytes)*100);
				//pB.setProgress(progress);
				//pB.setNote("asd");
			}
			in.close();
			out.close();
		}
	}
	
	public void move(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);

				move(srcFile, destFile);
			}
		} else {

			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
	
	public void deleteAll(File f) {

		if (f.isDirectory()) {
			String[] entries = f.list();
			for (String s : entries) {
				File current = new File(f.getPath(), s);
				deleteAll(current);
			}
		}
		f.delete();
	}
}
