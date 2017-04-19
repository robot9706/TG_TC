package com.javamegvan.tc.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MainFrame extends JFrame {
	public MainFrame(){
		super.setTitle("Vmi olasz cucc");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(1280, 720);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		BuildUI();
	}
	
	private void BuildUI(){
		Container content = super.getContentPane();
		
		//UI
		{
			content.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
				
			//A-B panel
			{
				JPanel list = new JPanel();
				list.setLayout(new GridLayout(1,2));
				
				list.add(CreateFileBrowseList());
				list.add(CreateFileBrowseList());
					
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				c.weighty = 1.0f;
				c.gridx = 0;
				c.gridy = 0;
				content.add(list, c);
			}
			
			//Szerkeszt� gombok
			{
				JPanel pl = new JPanel();
				pl.setLayout(new GridLayout(1,7));
				
				String[] buttons = new String[] { "N�zeget�", "Szerkeszt�s", "M�sol�s", "Mozgat�s", "�j mappa", "T�rl�s", "Kil�p�s" };
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
	    
		//File men�
		{
			JMenuBar j = new JMenuBar();
	        	
			JMenu m = new JMenu("F�jl");
			j.add(m);
			m.add(new JMenuItem("Kil�p�s"));
			
			super.setJMenuBar(j);	
		}
	}
	
	private JScrollPane CreateFileBrowseList(){
		JList list = new JList(new Object[]{ "..", "Mappa 1", "Mappa 2", "File 1", "File 2" });
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new IconListCellRenderer());
		
		JScrollPane pane = new JScrollPane(list);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		return pane;
	}
}
