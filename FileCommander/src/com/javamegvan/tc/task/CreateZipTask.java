package com.javamegvan.tc.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZipTask extends Task {
	private File _target;
	private File _sourceFolder;
	private ArrayList<File> _filesToCompress;
	
	public CreateZipTask(File targetZip, File sourceFolder, ArrayList<File> toCompress){
		_target = targetZip;
		_sourceFolder = sourceFolder;
		_filesToCompress = toCompress;
	}
	
	public void doTask() {
		byte[] buffer = new byte[1024];
		
		try{
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(_target));
			
			super.setTotalMax(_filesToCompress.size());
			
			URI baseFolder = _sourceFolder.toURI();
			
			int entryIndex = 0;
			for(File f : _filesToCompress){
				entryIndex ++;
				super.setTotalProgress(entryIndex);
				super.setMessage("Tömörítés: \"" + f.getName() + "\"");
				
				String zipEntryName = baseFolder.relativize(f.toURI()).getPath();
				
				ZipEntry e = new ZipEntry(zipEntryName);
				out.putNextEntry(e);

				FileInputStream fis = new FileInputStream(f);
				
				super.setPartMax(fis.available() / 1024);
				
				int prog = 0;
    			int len;
    			while ((len = fis.read(buffer)) > 0) {
    				out.write(buffer, 0, len);
    				
    				prog += len;
    				super.setPartProgress((int)Math.ceil(prog / 1024.0));
    			}
    			
    			fis.close();
    			out.closeEntry();
			}
			
			out.close();
			
			super.taskDone();
			getMain().refreshFileEntries();	
		}catch(Exception ex){
			super.taskFailed(ex);
		}
	}
	
	
}
