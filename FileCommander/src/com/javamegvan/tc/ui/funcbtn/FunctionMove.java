package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

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

		File source = new File(frame.getFocusedFile().getPath());
		File dest = new File(frame.getNonFocusedBrowser().getCurrentFolder().getPath(),
				frame.getFocusedFile().getName());

		try {
			if (frame.getFocusedBrowser().getSelectedFiles(false).isEmpty()) {
				move(source, dest);
				deleteAll(source);
			} else {
				for (File f : frame.getFocusedBrowser().getSelectedFiles(false)) {
					File d = new File(frame.getNonFocusedBrowser().getCurrentFolder().getPath(), f.getName());
					move(f, d);
					deleteAll(f);
				}
			}
		} catch (IOException e) {
			Utils.createMessageBox("Hiba az áthelyezés közben: " + e.getMessage(), "Hiba");
			e.printStackTrace();
		}

		frame.BrowserB.navigateTo(frame.BrowserB.getCurrentFolder());
		frame.BrowserA.navigateTo(frame.BrowserA.getCurrentFolder());
	}

	public void move(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);

				move(srcFile, destFile);
			}
		} else {

			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
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
