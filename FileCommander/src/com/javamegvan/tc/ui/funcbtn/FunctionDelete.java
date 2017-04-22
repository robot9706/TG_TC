package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import com.javamegvan.tc.ui.FileBrowserComponent;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

public class FunctionDelete implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F8;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}

	public boolean isShortcutOnly() {
		return false;
	}
	
	public String getTextShortcut() {
		return "F8";
	}

	public String getName() {
		return "Törlés";
	}

	public void doFunction(MainFrame frame) {
		FileBrowserComponent focus = frame.getFocusedBrowser();
		ArrayList<File> files = focus.getAllSelectedFiles();
		if(files.size() == 0 && focus.getFocusedFile() != null){
			files.add(focus.getFocusedFile());
		}
		
		if(Utils.createYesNoDialog(frame, "Biztosan törölni akar " + String.valueOf(files.size()) + " fájlt?", "Biztos?")){
			for(File f : files){
				if(f.isFile()){
					f.delete();
				}else if(f.isDirectory()){
					deleteAll(f);
				}
			}	
			
			frame.smartRefreshFileEntries(focus);
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
