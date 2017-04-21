package com.javamegvan.tc.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.javamegvan.tc.ui.filetable.IconCache;
import com.javamegvan.tc.ui.funcbtn.*;

public class MainFrame extends JFrame implements KeyEventDispatcher, ActionListener {
	private static final long serialVersionUID = 5L;
	
	private FunctionButton[] _functionButtons = new FunctionButton[]{
		new FunctionInspect(),
		new FunctionEdit(),
		new FunctionCopy(),
		new FunctionMove(),
		new FunctionNewFolder(),
		new FunctionDelete(),
		new FunctionExit()	
	};
	
	private boolean _browserAFocus = false;
	
	public FileBrowserComponent BrowserA;
	public FileBrowserComponent BrowserB;
	
	private JMenuItem _newFile;
	private JMenuItem _createZipButton;
	private JMenuItem _unZipButton;
	private JMenuItem _exitButton;

	public MainFrame(){
		super.setTitle("Andiamo A Comandare");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(1280, 720);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

		Utils.centerJForm(this);
		
		IconCache.Load();
		
		buildUI();
	}
	
	private void buildUI(){
		Container content = super.getContentPane();
		
		//UI
		{
			content.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
				
			//A-B panel
			{
				JPanel list = new JPanel();
				list.setLayout(new GridLayout(1,2));
				
				list.add(BrowserA = new FileBrowserComponent(this));
				list.add(BrowserB = new FileBrowserComponent(this));
				
				BrowserA.navigateTo(new File("C:\\"));
				BrowserB.navigateTo(new File("C:\\"));
				
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				c.weighty = 1.0f;
				c.gridx = 0;
				c.gridy = 0;
				content.add(list, c);
			}
			
			//Edit buttons
			{
				JPanel pl = new JPanel();
				pl.setLayout(new GridLayout(1,7));
				
				final MainFrame frame = this;
				for(int x = 0;x<_functionButtons.length;x++){
					JButton btn = new JButton(_functionButtons[x].getTextShortcut() + " " + _functionButtons[x].getName());
					final int index = x;
					btn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							_functionButtons[index].doFunction(frame);
						}
					});
					pl.add(btn);
				}
				
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weighty = 0.0;
				c.anchor = GridBagConstraints.PAGE_END;
				c.gridx = 0;
				c.gridwidth = 1;
				c.gridy = 1;
				content.add(pl, c);
			}
		}
	    
		//File menu
		{
			JMenuBar j = new JMenuBar();
	        	
			JMenu m = new JMenu("Fájl");
			j.add(m);
			
			m.add(_newFile = new JMenuItem("Új fájl"));
			m.add(new JSeparator());
			m.add(_createZipButton = new JMenuItem("Fájlok tömörítése"));
			m.add(_unZipButton = new JMenuItem("Kicsomagolás"));
			m.add(new JSeparator());
			m.add(_exitButton = new JMenuItem("Kilépés"));
			
			_newFile.addActionListener(this);
			_createZipButton.addActionListener(this);
			_unZipButton.addActionListener(this);
			_exitButton.addActionListener(this);
			
			super.setJMenuBar(j);	
		}
	}

	public void toggleSideFocus(){
		if(BrowserA.hasFocus()){
			BrowserB.doFocus();
		}else{
			BrowserA.doFocus();
		}
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() == KeyEvent.KEY_PRESSED){
			if(e.getKeyCode() == KeyEvent.VK_TAB){
				e.consume();
				toggleSideFocus();
				return true;
			}
			
			for(int x = 0;x<_functionButtons.length;x++){
				if(e.getKeyCode() == _functionButtons[x].getKeyShortcut() && e.getModifiers() == _functionButtons[x].getKeyShortcutModifier()){
					e.consume();
					_functionButtons[x].doFunction(this);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public File getFocusedFile(){
		if (_browserAFocus){
			return BrowserA.getFocusedFile();
		}
		
		return BrowserB.getFocusedFile();
	}
	
	public FileBrowserComponent getFocusedBrowser(){	
		if(_browserAFocus){
			return BrowserA;
		}
		
		return BrowserB;
	}
	
	public FileBrowserComponent getNonFocusedBrowser(){	
		if(_browserAFocus){
			return BrowserB;
		}
		
		return BrowserA;
	}
	
	public File getFocusedFolder(){
		if (_browserAFocus){
			return BrowserA.getCurrentFolder();
		}
		
		return BrowserB.getCurrentFolder();
	}
	
	public void onSideGotFocus(FileBrowserComponent c){
		_browserAFocus = (c == BrowserA);
		BrowserA.setFocus(_browserAFocus);
		BrowserB.setFocus(!_browserAFocus);
	}
	
	public void refreshFileEntries(){
		BrowserA.refreshTableEntries();
		BrowserB.refreshTableEntries();
	}
	
	public void smartRefreshFileEntries(FileBrowserComponent source){
		source.refreshTableEntries();
		
		if(source == BrowserA && source.getCurrentFolder().equals(BrowserB.getCurrentFolder())){
			BrowserB.refreshTableEntries();
		}
		if(source == BrowserB && source.getCurrentFolder().equals(BrowserA.getCurrentFolder())){
			BrowserA.refreshTableEntries();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _exitButton){
			System.exit(0);
		}else if(e.getSource() == _createZipButton){
			ZipTools.makeZip(this);
		}else if(e.getSource() == _unZipButton){
			ZipTools.uncompressZip(this);
		}else if(e.getSource() == _newFile){
			Utils.createNewEmptyFile(this);
		}
	}
}
