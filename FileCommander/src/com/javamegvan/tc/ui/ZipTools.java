package com.javamegvan.tc.ui;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.javamegvan.tc.task.CreateZipTask;
import com.javamegvan.tc.task.UnzipTask;

public class ZipTools {
	public static void makeZip(MainFrame frame){
		FileBrowserComponent c = frame.getFocusedBrowser();
		FileBrowserComponent nonFocused = frame.getNonFocusedBrowser();
		
		ArrayList<File> files = c.getAllSelectedFiles();
		ArrayList<File> filesToCompress = collectFiles(files);
		
		if(files.size() == 0 || filesToCompress.size() == 0){
			Utils.createMessageBox(frame, "Nincs mit t�m�r�teni!", "T�m�r�t�s");
			return;
		}
		
		String zipName = JOptionPane.showInputDialog("Zip file neve?");
		if (zipName != null && zipName.length() > 0) {
			if(!zipName.toLowerCase().endsWith(".zip")){
				zipName += ".zip";
			}
			
			File targetZip = new File(c.getCurrentFolder(), zipName);
			if(targetZip.exists()){
				Utils.createMessageBox(frame, "A c�l f�jl m�r l�tezik. \"" + targetZip.getPath() + "\"", "Hiba");
				return;
			}
			
			if(Utils.createYesNoDialog(frame, "Szeretne " + String.valueOf(filesToCompress.size()) + " f�jlt t�m�r�teni a k�vetkez� f�jlba: \"" +
					targetZip.getPath() + "\"?", "T�m�r�t�s")) {
				ProgressBarDialog pd = new ProgressBarDialog(new CreateZipTask(targetZip, nonFocused.getCurrentFolder(), filesToCompress), frame);
				pd.showAndExecuteTask();
			}
		}
	}
	
	private static ArrayList<File> collectFiles(ArrayList<File> files){
		ArrayList<File> target = new ArrayList<File>();
		
		for(File f : files){
			if(f.isFile()){
				target.add(f);
			}else if(f.isDirectory()){
				collectFiles(f, target);
			}
		}
		
		return target;
	}
	
	private static int collectFiles(File root, ArrayList<File> target){
		int num = 0;
		
		for(File f : root.listFiles()){
			if(f.isFile()){
				target.add(f);
			}else if(f.isDirectory()){
				collectFiles(f, target);
			}
		}
		
		return num;
	}
	
	public static void uncompressZip(MainFrame frame){
		File selected = frame.getFocusedFile();
		
		if(selected == null){
			Utils.createMessageBox(frame, "Nincs f�jl kiv�lasztva!", "Zip kicsomagol�s");
			return;
		}
		
		if(!selected.isFile()){
			Utils.createMessageBox(frame, "Csak zip file csomagolhat� ki!", "Zip kicsomagol�s");
			return;
		}
		
		if(!Utils.getFileExtension(selected).toLowerCase().equalsIgnoreCase("zip")){
			Utils.createMessageBox(frame, selected.getName() + " nem csomagolhat� ki!", "Zip kicsomagol�s");
			return;
		}
		
		File target = frame.getNonFocusedBrowser().getCurrentFolder();
		
		if(Utils.createYesNoDialog(frame, "Ki szeretn� csomagolni a \"" + selected.getName() + "\"-et a k�vetkez� helyre: \"" +
			target.getPath() + "\" ?", "Zip kicsomagol�s")) {
			ProgressBarDialog pd = new ProgressBarDialog(new UnzipTask(selected, target), frame);
			pd.showAndExecuteTask();
		}
	}
}
