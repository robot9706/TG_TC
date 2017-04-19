package com.javamegvan.tc.ui;

import java.io.File;

import javax.swing.JDialog;

public class FileInspectorDialog extends JDialog {
	private static final long serialVersionUID = 4309012935275715140L;

	public FileInspectorDialog(MainFrame frame, File file, boolean editMode){
		super(frame, true);
				
		super.setSize(500, 300);
		Utils.centerJForm(this);
		
		//TODO: Implement
	}
}
