package com.javamegvan.tc.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(1280, 720);
		
		Test();
	}
	
	private JScrollPane  CreateList(){
		JList list = new JList(new Object[]{ "..", "Mappa 1", "Mappa 2", "File 1", "File 2" });
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new IconListCellRenderer());
		
		JScrollPane pane = new JScrollPane(list);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		return pane;
	}
	
	private void Test(){
		Container content = super.getContentPane();
		
		//UI
		{
			content.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
				
			{
				JPanel list = new JPanel();
				list.setLayout(new GridLayout(1,2));
				
				list.add(CreateList());
				list.add(CreateList());
					
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				c.weighty = 1.0f;
				c.gridx = 0;
				c.gridy = 0;
				content.add(list, c);
			}
			
			{
				JPanel pl = new JPanel();
				pl.setLayout(new GridLayout(1,7));
				
				for(int x = 0;x<7;x++){
					pl.add(new JButton(String.valueOf(x)));
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
	        	
			JMenu m = new JMenu("File");
			j.add(m);
			m.add(new JMenuItem("Exit"));
			
			super.setJMenuBar(j);	
		}
	}
}
