package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;

import com.javamegvan.tc.ui.MainFrame;

public class FunctionMove implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F6;
	}
	
	public int getKeyShortcutModifier() {
		return 0;
	}

	public String getTextShortcut() {
		return "F6";
	}

	public String getName() {
		return "Áthelyezés";
	}

	public void doFunction(MainFrame frame) {
		//TODO: Implement
		//frame.getFocusedFile()
	}
}
