package com.javamegvan.tc.ui;

import java.io.File;

import com.javamegvan.tc.task.UnzipTask;

public class ZipTools {
	public static void makeZip(MainFrame frame){
		
	}
	
	public static void uncompressZip(MainFrame frame){
		File selected = frame.getFocusedFile();
		
		if(selected == null){
			Utils.createMessageBox("Nincs f�jl kiv�lasztva!", "Zip kicsomagol�s");
			return;
		}
		
		if(!selected.isFile()){
			Utils.createMessageBox("Csak zip file csomagolhat� ki!", "Zip kicsomagol�s");
			return;
		}
		
		if(!Utils.getFileExtension(selected).toLowerCase().equalsIgnoreCase("zip")){
			Utils.createMessageBox(selected.getName() + " nem csomagolhat� ki!", "Zip kicsomagol�s");
			return;
		}
		
		File target = frame.getNonFocusedBrowser().getCurrentFolder();
		
		if(Utils.createYesNoDialog("Ki szeretn� csomagolni a \"" + selected.getName() + "\"-et a k�vetkez� helyre: \"" +
			target.getPath() + "\" ?", "Zip kicsomagol�s")) {
			ProgressBarDialog pd = new ProgressBarDialog(new UnzipTask(selected, target), frame);
			pd.showAndExecuteTask();
		}
	}
}
