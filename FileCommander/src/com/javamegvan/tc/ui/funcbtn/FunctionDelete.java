package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

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
		return "Törlés";
	}

	public void doFunction(MainFrame frame) {
		if (!(frame.getFocusedBrowser().getSelectedFiles(false).isEmpty())) {
			if (Utils.createYesNoDialog("Biztos benne?", "Törlés")) {
				for (File f : frame.getFocusedBrowser().getSelectedFiles(false)) {
					deleteAll(f);
				}
				frame.getFocusedBrowser().navigateTo(frame.getFocusedFolder());
			}
		} else {

			if (!(frame.getFocusedFile() == null)) {
				File focused = frame.getFocusedFile();
				if (Utils.createYesNoDialog("Biztos benne?", "Törlés")) {
					deleteAll(focused);
				}

				frame.getFocusedBrowser().navigateTo(frame.getFocusedFolder());
			} else {
				Utils.createMessageBox("Nincs fájl kijelölve!", "Hiba");
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
