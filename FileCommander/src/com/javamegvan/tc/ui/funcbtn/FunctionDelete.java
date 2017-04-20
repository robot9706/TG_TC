package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import com.javamegvan.tc.ui.MainFrame;

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
			Object[] options = { "Yes", "No" };
			int result = JOptionPane.showOptionDialog(null, "Biztos benne?", null, JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
			if (result == JOptionPane.YES_OPTION) {
				for (File f : frame.getFocusedBrowser().getSelectedFiles(false)) {
					deleteAll(f);
				}
				frame.getFocusedBrowser().navigateTo(frame.getFocusedBrowser()._table.CurrentFolder);
			}
		} else {

			if (!(frame.getFocusedFile() == null)) {
				File focused = frame.getFocusedFile();
				Object[] options = { "Yes", "No" };
				int result = JOptionPane.showOptionDialog(null, "Biztos benne?", null, JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

				if (result == JOptionPane.YES_OPTION) {
					deleteAll(focused);
				}

				frame.getFocusedBrowser().navigateTo(frame.getFocusedBrowser()._table.CurrentFolder);
			} else {
				JOptionPane.showMessageDialog(null, "Nincs fájl kijelölve!", null, JOptionPane.PLAIN_MESSAGE);
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
