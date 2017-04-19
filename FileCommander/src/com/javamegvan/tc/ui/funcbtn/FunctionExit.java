package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;

import com.javamegvan.tc.ui.MainFrame;

public class FunctionExit implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F4;
	}

	public int getKeyShortcutModifier() {
		return KeyEvent.ALT_MASK;
	}
	
	public String getTextShortcut() {
		return "Alt+F4";
	}

	public String getName() {
		return "Kilépés";
	}

	public void doFunction(MainFrame frame) {
		System.exit(0);
	}
}
