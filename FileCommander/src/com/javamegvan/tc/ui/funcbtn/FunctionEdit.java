package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import com.javamegvan.tc.ui.FileInspectorDialog;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

public class FunctionEdit implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F4;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}
	
	public String getTextShortcut() {
		return "F4";
	}

	public String getName() {
		return "Szerkeszt�s";
	}

	public void doFunction(MainFrame frame) {
		File file = frame.getFocusedFile();
		if(file != null){
			if(file.exists() && file.isFile()){
				new FileInspectorDialog(file, true);
			}else{
				Utils.createMessageBox(frame, "Nem f�jl van kiv�lasztva!", "Szerkeszt�s");
			}
		}else{
			Utils.createMessageBox(frame, "Nincs f�jl kiv�lasztva!", "Szerkeszt�s");
		}
	}
}

