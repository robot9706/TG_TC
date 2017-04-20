package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;

import com.javamegvan.tc.ui.FileBrowserComponent;
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
		
		FileBrowserComponent cp = frame.getFocusedBrowser();
		
		System.out.println(cp.getCurrentFolder());
		
		String response = JOptionPane.showInputDialog("Az új mappa neve?");
		if (!(response == null || response.equals(""))) {
			 File newdir = new File(cp.getCurrentFolder(), response);
			if (newdir.exists()) {
				JOptionPane.showMessageDialog(null, "Ilyen nevû már létezik!", "Létezik", JOptionPane.PLAIN_MESSAGE,
						null);
			} else {
				newdir.mkdir();
			}
		}
		  cp.navigateTo(cp.getCurrentFolder());
	
		//TODO: Implement
		//frame.getFocusedFile()
		//frame.BrowserA.getSelectedFiles(true/false);
		//frame.BrowserB.getSelectedFiles(true/false);
	}
}
