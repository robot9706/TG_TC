package com.javamegvan.tc.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.javamegvan.tc.ui.filetable.IconCache;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 5L;
	
	private FileBrowserComponent _browserA;
	private FileBrowserComponent _browserB;

	public MainFrame(){
		super.setTitle("Andiamo A Comandare");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(1280, 720);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
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
				
				list.add(_browserA = new FileBrowserComponent());
				list.add(_browserB = new FileBrowserComponent());
				
				_browserA.navigateTo(new File("C:\\"));
				_browserB.navigateTo(new File("C:\\"));
				
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
				
				String[] buttons = new String[] { "Nézegetõ", "Szerkesztés", "Másolás", "Mozgatás", "Új mappa", "Törlés", "Kilépés" };
				for(int x = 0;x<7;x++){
					pl.add(new JButton(buttons[x]));
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
			m.add(new JMenuItem("Kilépés"));
			
			super.setJMenuBar(j);	
		}
	}
}
