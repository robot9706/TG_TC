package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;

import com.javamegvan.tc.ui.MainFrame;

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
		return "M�sol�s";
	}

	public void doFunction(MainFrame frame) {
		//TODO: Implement
		//frame.getFocusedFile()
	}
}
