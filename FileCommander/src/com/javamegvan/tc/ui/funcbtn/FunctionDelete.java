package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;

import com.javamegvan.tc.ui.MainFrame;

public class FunctionDelete implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F8;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}
	
	public String getTextShortcut() {
		return "F8";
	}

	public String getName() {
		return "Törlés";
	}

	public void doFunction(MainFrame frame) {
		//TODO: Implement
		//frame.getFocusedFile()
		//frame.BrowserA.getSelectedFiles(true/false);
		//frame.BrowserB.getSelectedFiles(true/false);
	}
}
