package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import com.javamegvan.tc.task.MoveFilesTask;
import com.javamegvan.tc.ui.FileBrowserComponent;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.ProgressBarDialog;
import com.javamegvan.tc.ui.Utils;

public class FunctionCopy implements FunctionButton {

	public int getKeyShortcut() {
		return KeyEvent.VK_F5;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}

	public String getTextShortcut() {
		return "F5";
	}

	public String getName() {
		return "Másolás";
	}
	
	public void doFunction(MainFrame frame) {
		FileBrowserComponent source = frame.getFocusedBrowser();
		FileBrowserComponent target = frame.getNonFocusedBrowser();
		
		ArrayList<File> src = source.getAllSelectedFiles();
		if(src.size() == 0 && source.getFocusedFile() != null){
			src.add(source.getFocusedFile());
		}
		
		if(src.size() == 0){
			Utils.createMessageBox(frame, "Nincs fájl kiválasztva!", "Másolás");
			return;
		}
		
		ProgressBarDialog pd = new ProgressBarDialog(new MoveFilesTask(src, source.getCurrentFolder(),
				target.getCurrentFolder(), false), frame);
		pd.showAndExecuteTask();
	}
}
