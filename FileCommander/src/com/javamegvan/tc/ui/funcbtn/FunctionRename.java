package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.javamegvan.tc.ui.FileBrowserComponent;
import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.Utils;

public class FunctionRename implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F2;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}

	public boolean isShortcutOnly() {
		return true;
	}

	public String getTextShortcut() {
		return "ALT+F6";
	}

	public String getName() {
		return "�tnevez�s";
	}

	public void doFunction(MainFrame frame) {
		FileBrowserComponent focus = frame.getFocusedBrowser();
		ArrayList<File> files = focus.getAllSelectedFiles();
		if(files.size() == 0 && focus.getFocusedFile() != null){
			files.add(focus.getFocusedFile());
		}
		
		if(files.size() == 0){
			Utils.createMessageBox(frame, "Nincs f�jl kiv�lasztva!", "�tnevez�s");
			return;
		}
		
		String title;
		if(files.size() == 1){
			title = files.get(0).getName() + " �tnevez�se";
		}else{
			title = String.valueOf(files.size()) + " f�jl �tnevez�se";
		}
		
		String response = JOptionPane.showInputDialog(frame, "�j n�v?", title);
		if (response != null && response.length() > 0) {
			boolean fail = false;
			
			if(files.size() == 1){
				if(!files.get(0).renameTo(new File(files.get(0).getParentFile(), response))){
					fail = true;
				}
			}else{
				String baseName = Utils.getFileNameWithoutExtension(response);
				String ext = Utils.getFileExtension(response);
				
				String nameFormat = baseName + " ({0})";
				if(ext != null && ext.length() > 0){
					nameFormat += "." + ext;
				}
				
				for(int x = 0;x<files.size();x++){
					File f = files.get(x);
					
					String newName = (x == 0 ? response : nameFormat.replace("{0}", String.valueOf(x)));
					
					if(!f.renameTo(new File(f.getParentFile(), newName))){
						fail = true;
					}
				}
			}
			
			if(fail){
				Utils.createMessageBox(frame, "Sikertelen �tnevez�s!", "�tnevez�s");
			}
		}
		
		frame.smartRefreshFileEntries(focus);
	}
}
