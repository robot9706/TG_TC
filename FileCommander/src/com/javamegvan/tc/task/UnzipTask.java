package com.javamegvan.tc.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipTask extends Task {
	private File _zip;
	private File _targetFolder;
	
	public UnzipTask(File sourceZip, File targetFolder){
		_zip = sourceZip;
		_targetFolder = targetFolder;
	}
	
	public void doTask() {
		byte[] buffer = new byte[1024];
		
		try{
	    	if(!_targetFolder.exists()){
	    		_targetFolder.mkdir();
	    	}
	    	
    		ZipEntry ent;
	    	int numEntries = 0;
	    	{
	    		ZipInputStream zis = new ZipInputStream(new FileInputStream(_zip));
	    		while((ent = zis.getNextEntry()) != null)
	    			numEntries++;
	    		zis.close();
	    	}
	    	
	    	super.setTotalMax(numEntries);
	    	super.setTotalProgress(0);
	    	
	    	{
	    		int entryIndex = 0;
	    		
	    		ZipInputStream zis = new ZipInputStream(new FileInputStream(_zip));
	    		
	    		while((ent = zis.getNextEntry()) != null){
	    			entryIndex++;
	    			super.setTotalProgress(entryIndex);
	    			
	    			String fileName = ent.getName();
	    			super.setMessage("Kicsomagolás: " + fileName);
	    			
	    			File newFile = new File(_targetFolder, fileName);

	    			newFile.getParentFile().mkdirs();

	    			FileOutputStream fos = new FileOutputStream(newFile);
	    		
	    			super.setPartMax((int)(ent.getSize() / 1024));
	    			
	    			int prog = 0;
	    			int len;
	    			while ((len = zis.read(buffer)) > 0) {
	    				fos.write(buffer, 0, len);
	    				
	    				prog += len;
	    				super.setPartProgress((int)Math.ceil(prog / 1024.0));
	    			}

	    			fos.close();
	    			zis.closeEntry();
	    			
	    			if(super.isCancelRequested()){
	    				super.taskDone();
	    				getMain().refreshFileEntries();
	    				
	    				return;
	    			}
	    		}
	    		
	    		zis.close();
	    	}
	    	
			super.taskDone();
			getMain().refreshFileEntries();	
		}catch(Exception ex){
			super.taskFailed(ex);
		}
	}
}
