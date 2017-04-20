package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;

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

		String path = frame.getFocusedBrowser()._table.CurrentFolder.getPath();
		path = new StringBuffer(path).insert(path.length(), "\\").toString();

		String response = JOptionPane.showInputDialog("Az új mappa neve?");
		if (!(response == null || response.equals(""))) {
			File newdir = new File(path + response);
			if (newdir.exists()) {
				JOptionPane.showMessageDialog(null, "Ilyen nevû már létezik!", "Létezik", JOptionPane.PLAIN_MESSAGE,
						null);
				doFunction(frame);

			} else {
				newdir.mkdir();
			}
		}
		frame.getFocusedBrowser().navigateTo(frame.getFocusedBrowser()._table.CurrentFolder);
	}
}
