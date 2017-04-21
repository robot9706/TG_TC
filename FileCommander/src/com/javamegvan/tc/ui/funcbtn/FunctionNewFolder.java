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
		return "�j mappa";
	}

	public void doFunction(MainFrame frame) {
		FileBrowserComponent cp = frame.getFocusedBrowser();

		String response = JOptionPane.showInputDialog("Az �j mappa neve?");
		if (response != null && response.length() > 0) {
			 File newdir = new File(cp.getCurrentFolder(), response);
			if (newdir.exists()) {
				Utils.createMessageBox("Ilyan nev� mappa m�r l�tezik!", "Hiba");
			} else {
				if(!newdir.mkdir()){
					Utils.createMessageBox("Hiba a mappa l�trehoz�sa k�zben!", "Hiba");
				}
			}
		}
		
		cp.navigateTo(cp.getCurrentFolder());
	}
}
