package com.javamegvan.tc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileInspectorDialog extends JFrame implements ActionListener, DocumentListener, WindowListener {
	private static final long serialVersionUID = 4309012935275715140L;

	private File _file;
	private boolean _modified;
	
	private JTextArea _text;
	
	private JCheckBoxMenuItem _menuLineWrap;
	private JMenuItem _menuSave;
	private JMenuItem _menuClose;
	
	public FileInspectorDialog(File file, boolean editMode){		
		super.setSize(750, 500);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Utils.centerJForm(this);
		
		if(editMode){
			_file = file;
			
			super.addWindowListener(this);
		}
		
		//Menu
		{
			JMenuBar bar = new JMenuBar();
	    	
			JMenu menu = new JMenu("F�jl");
			bar.add(menu);
			
			_menuLineWrap = new JCheckBoxMenuItem("Automatikus sort�r�s"); 			
			_menuLineWrap.setSelected(false);
			_menuLineWrap.addActionListener(this);
			menu.add(_menuLineWrap);
			menu.add(new JSeparator());
			
			if(editMode){
				menu.add(_menuSave = new JMenuItem("Ment�s"));
				_menuSave.addActionListener(this);	
			}
			
			menu.add(_menuClose = new JMenuItem("Bez�r"));
			_menuClose.addActionListener(this);
			
			super.setJMenuBar(bar);	
		}
		
		_text = new JTextArea(1,50);
		_text.setLineWrap(false);
		_text.setEditable(editMode);
		_text.getDocument().addDocumentListener(this);

		final JScrollPane scroll = new JScrollPane(_text);
		add(scroll);
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line;
			while((line = br.readLine()) != null){
				_text.append(line + "\r\n");
			}
			
			br.close();
		}catch(Exception ex){
			ex.printStackTrace();
			
			Utils.createMessageBox(this, "Hiba a f�jl megnyit�sa k�zben: \"" + ex.getMessage() + "\"", "Hiba!");
			close();
			return;
		}
		
		super.setTitle(file.getName() + (editMode ? "" : " - Csak olvas�s"));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				scroll.getVerticalScrollBar().setValue(0);
			}
		});
		
		_modified = false;
		super.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _menuLineWrap){
			JCheckBoxMenuItem check = (JCheckBoxMenuItem)e.getSource();
			
			_text.setLineWrap(check.isSelected());
		}else if(e.getSource() == _menuClose){
			super.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}else if(e.getSource() == _menuSave && _file != null && _modified){
			saveFile();
		}
	}
	
	private void saveFile(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(_file));
			
			bw.write(_text.getText());
			
			bw.close();
			
			_modified = false;
			updateTitle();
		}catch(Exception ex){
			ex.printStackTrace();
			
			Utils.createMessageBox(this, "Hiba a f�jl ment�se k�zben: \"" + ex.getMessage() + "\"", "Hiba!");
		}
	}
	
	private void close(){
		super.setVisible(false);
		super.dispose();
	}

	private void updateTitle(){
		super.setTitle(_file.getName() + (_modified ? " - M�dos�tva" : ""));
	}
	
	private void onModified(){
		if(_file != null){
			_modified = true;
			
			updateTitle();
		}
	}

	public void windowClosing(WindowEvent e) {
		if(_file != null && _modified){
			if (Utils.createYesNoDialog(this, "Menti a v�ltoztat�sokat?", "Ment�s?")) {
				saveFile();
			}
		}
	}
	
	public void changedUpdate(DocumentEvent e) {
		onModified();
	}

	public void insertUpdate(DocumentEvent e) {
		onModified();
	}

	public void removeUpdate(DocumentEvent e) {
		onModified();
	}


	
	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}
}
