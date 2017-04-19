package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import com.javamegvan.tc.ui.FileInspectorDialog;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

public class FunctionInspect implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F3;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}
	
	public String getTextShortcut() {
		return "F3";
	}

	public String getName() {
		return "N�z�ke";
	}

	public void doFunction(MainFrame frame) {
		File file = frame.getFocusedFile();
		if(file != null){
			if(file.exists() && file.isFile()){
				new FileInspectorDialog(frame, file, true).setVisible(true);
			}else{
				Utils.createMessageBox("Nem f�jl van kiv�lasztva!", "N�z�ke");
			}
		}else{
			Utils.createMessageBox("Nincs f�jl kiv�lasztva!", "N�z�ke");
		}
	}
}
