package com.javamegvan.tc.ui;

import java.io.File;

import com.javamegvan.tc.task.UnzipTask;

public class ZipTools {
	public static void makeZip(MainFrame frame){
		
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
