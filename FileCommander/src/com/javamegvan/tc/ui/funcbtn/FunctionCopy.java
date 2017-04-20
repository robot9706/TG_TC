package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.javamegvan.tc.ui.MainFrame;

public class FunctionCopy implements FunctionButton {

	public int getKeyShortcut() {
		return KeyEvent.VK_F5;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}

	public String getTextShortcut() {
		return "F5";
	}

	public String getName() {
		return "Másolás";
	}

	public void doFunction(MainFrame frame) {
		// TODO: CSÍK

		File source = new File(frame.getFocusedFile().getPath());
		File dest = new File(frame.getunFocusedBrowser().getCurrentFolder().getPath(),
				frame.getFocusedFile().getName());

		try {
			if (frame.getFocusedBrowser().getSelectedFiles(false).isEmpty()) {
				copy(source, dest);
			} else {
				for (File f : frame.getFocusedBrowser().getSelectedFiles(false)) {
					File d = new File(frame.getunFocusedBrowser().getCurrentFolder().getPath(), f.getName());
					copy(f, d);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.getunFocusedBrowser().navigateTo(frame.getunFocusedBrowser().getCurrentFolder());

	}

	public void copy(File src, File dest) throws IOException {

		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);

				copy(srcFile, destFile);
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
}
