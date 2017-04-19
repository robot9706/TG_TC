package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;

import com.javamegvan.tc.ui.MainFrame;

public class FunctionNewFolder implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F7;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}
	
	public String getTextShortcut() {
		return "F7";
	}

	public String getName() {
		return "Új mappa";
	}

	public void doFunction(MainFrame frame) {
		//TODO: Implement
		//frame.getFocusedFile()
	}
}
