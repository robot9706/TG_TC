package com.javamegvan.tc.ui;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.javamegvan.tc.task.CreateZipTask;
import com.javamegvan.tc.task.UnzipTask;

public class ZipTools {
	public static void makeZip(MainFrame frame){
		FileBrowserComponent c = frame.getFocusedBrowser();
		
		ArrayList<File> files = c.getAllSelectedFiles();
		ArrayList<File> filesToCompress = collectFiles(files);
		
		if(files.size() == 0 || filesToCompress.size() == 0){
			Utils.createMessageBox("Nincs mit tömöríteni!", "Tömörítés");
			return;
		}
		
		String zipName = JOptionPane.showInputDialog("Zip file neve?");
		if (zipName != null && zipName.length() > 0) {
			if(!zipName.toLowerCase().endsWith(".zip")){
				zipName += ".zip";
			}
			
			File targetZip = new File(c.getCurrentFolder(), zipName);
			if(targetZip.exists()){
				Utils.createMessageBox("A cél fájl már létezik. \"" + targetZip.getPath() + "\"", "Hiba");
				return;
			}
			
			if(Utils.createYesNoDialog("Szeretne " + String.valueOf(filesToCompress.size()) + " fájlt tömöríteni a következõ fájlba: \"" +
					targetZip.getPath() + "\"?", "Tömörítés")) {
				ProgressBarDialog pd = new ProgressBarDialog(new CreateZipTask(targetZip, c.getCurrentFolder(), filesToCompress), frame);
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
			Utils.createMessageBox("Nincs fájl kiválasztva!", "Zip kicsomagolás");
			return;
		}
		
		if(!selected.isFile()){
			Utils.createMessageBox("Csak zip file csomagolható ki!", "Zip kicsomagolás");
			return;
		}
		
		if(!Utils.getFileExtension(selected).toLowerCase().equalsIgnoreCase("zip")){
			Utils.createMessageBox(selected.getName() + " nem csomagolható ki!", "Zip kicsomagolás");
			return;
		}
		
		File target = frame.getNonFocusedBrowser().getCurrentFolder();
		
		if(Utils.createYesNoDialog("Ki szeretné csomagolni a \"" + selected.getName() + "\"-et a következõ helyre: \"" +
			target.getPath() + "\" ?", "Zip kicsomagolás")) {
			ProgressBarDialog pd = new ProgressBarDialog(new UnzipTask(selected, target), frame);
			pd.showAndExecuteTask();
		}
	}
}
