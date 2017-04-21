package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import com.javamegvan.tc.ui.FileBrowserComponent;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

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
		return "T�rl�s";
	}

	public void doFunction(MainFrame frame) {
		FileBrowserComponent focus = frame.getFocusedBrowser();
		
		if (!focus.getSelectedFiles(false).isEmpty()) {
			if (Utils.createYesNoDialog("Biztos benne?", "T�rl�s")) {
				for (File f : focus.getSelectedFiles(false)) {
					deleteAll(f);
				}
				
				frame.smartRefreshFileEntries(focus);
			}
		}else{
			if (!(focus.getFocusedFile() == null)) {
				File focused = focus.getFocusedFile();
				if (Utils.createYesNoDialog("Biztos benne?", "T�rl�s")) {
					deleteAll(focused);
				}

				frame.smartRefreshFileEntries(focus);
			} else {
				Utils.createMessageBox("Nincs f�jl kijel�lve!", "Hiba");
			}
		}
	}

	public void deleteAll(File f) {
		if (f.isDirectory()) {
			String[] entries = f.list();
			for (String s : entries) {
				File current = new File(f.getPath(), s);
				deleteAll(current);
			}
		}
		f.delete();
	}
}
