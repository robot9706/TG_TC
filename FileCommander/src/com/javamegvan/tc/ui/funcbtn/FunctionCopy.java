package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

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
	

	ProgressMonitor pB = new ProgressMonitor(null,"Copy..","",0,100);
	
	public void doFunction(MainFrame frame) {
		// TODO: CSÍK tökéletesítés
		
		pB.setProgress(0);
		
		File source = new File(frame.getFocusedFile().getPath());
		File dest = new File(frame.getunFocusedBrowser().getCurrentFolder().getPath(),
				frame.getFocusedFile().getName());

		try {
			if (frame.getFocusedBrowser().getSelectedFiles(false).isEmpty()) {
				@SuppressWarnings("rawtypes")
				SwingWorker worker = new SwingWorker(){

					@Override
					protected Object doInBackground() throws Exception {
						copy(source, dest);
						frame.getunFocusedBrowser().navigateTo(frame.getunFocusedBrowser().getCurrentFolder());
						return null;
					}
				};
				worker.execute();
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

			long expectedBytes = src.length();
			byte[] buffer = new byte[1024];

			
			long totalBytesCopied=0;
			int length;
			int progress=0;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
				
				totalBytesCopied+= length;
			    progress = (int)Math.round(((double)totalBytesCopied / (double)expectedBytes)*100);
				pB.setProgress(progress);
				pB.setNote("asd");
			}
			in.close();
			out.close();
		}
	}
}
