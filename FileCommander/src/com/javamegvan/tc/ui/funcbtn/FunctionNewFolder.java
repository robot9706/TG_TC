package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;

import com.javamegvan.tc.ui.FileBrowserComponent;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

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
		FileBrowserComponent cp = frame.getFocusedBrowser();

		String response = JOptionPane.showInputDialog("Az új mappa neve?");
		if (response != null && response.length() > 0) {
			 File newdir = new File(cp.getCurrentFolder(), response);
			if (newdir.exists()) {
				Utils.createMessageBox("Ilyan nevû mappa már létezik!", "Hiba");
			} else {
				if(!newdir.mkdir()){
					Utils.createMessageBox("Hiba a mappa létrehozása közben!", "Hiba");
				}
			}
		}else{
			Utils.createMessageBox("Érvénytelen név!", "Hiba");
		}
		cp.navigateTo(cp.getCurrentFolder());
	}
}
