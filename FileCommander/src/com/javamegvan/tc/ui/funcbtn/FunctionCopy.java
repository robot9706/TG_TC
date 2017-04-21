package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import com.javamegvan.tc.task.MoveFilesTask;
import com.javamegvan.tc.task.UnzipTask;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.ProgressBarDialog;

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
		ProgressBarDialog pd = new ProgressBarDialog(new MoveFilesTask(null, null, false), frame);
		pd.showAndExecuteTask();
		
		/*pB.setProgress(0);
		
		File source = new File(frame.getFocusedFile().getPath());
		File dest = new File(frame.getNonFocusedBrowser().getCurrentFolder().getPath(),
				frame.getFocusedFile().getName());

		try {
			if (frame.getFocusedBrowser().getSelectedFiles(false).isEmpty()) {
				@SuppressWarnings("rawtypes")
				SwingWorker worker = new SwingWorker(){

					@Override
					protected Object doInBackground() throws Exception {
						//copy(source, dest);
						frame.getNonFocusedBrowser().navigateTo(frame.getNonFocusedBrowser().getCurrentFolder());
						return null;
					}
				};
				worker.execute();
			} else {
				for (File f : frame.getFocusedBrowser().getSelectedFiles(false)) {
					File d = new File(frame.getNonFocusedBrowser().getCurrentFolder().getPath(), f.getName());
					//copy(f, d);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
