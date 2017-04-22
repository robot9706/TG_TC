package com.javamegvan.tc.ui.funcbtn;

import com.javamegvan.tc.ui.MainFrame;

public interface FunctionButton {
	int getKeyShortcut();
	int getKeyShortcutModifier();
	boolean isShortcutOnly();
	String getTextShortcut();
	String getName();
	
	void doFunction(MainFrame frame);
}
