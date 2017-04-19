package com.javamegvan.tc.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
			
			//Szerkesztõ gombok
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
	    
		//File menü
		{
			JMenuBar j = new JMenuBar();
	        	
			JMenu m = new JMenu("Fájl");
			j.add(m);
			m.add(new JMenuItem("Kilépés"));
			
			super.setJMenuBar(j);	
		}
	}
	
	private JScrollPane CreateFileBrowseList(){
		/*JList list = new JList(new Object[]{ "..", "Mappa 1", "Mappa 2", "File 1", "File 2" });
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new IconListCellRenderer());*/
		
		FileTableModel mdl = new FileTableModel();
		JTable table = new JTable(mdl);
		table.setShowGrid(false);
		table.setDefaultRenderer(File.class, new FileIconTableCellRenderer());
		{
			TableColumn kit = table.getColumn("Kit.");
			kit.setMinWidth(50);
			kit.setMaxWidth(50);
			
			kit = table.getColumn("Méret");
			kit.setMinWidth(100);
			kit.setMaxWidth(100);
			
			kit = table.getColumn("Dátum");
			kit.setMinWidth(150);
			kit.setMaxWidth(150);
		}
		
		File root = new File("C:");
		for(File f : root.listFiles()){
			if(f.isDirectory()){
				mdl.addRow(new Object[] { f, "<DIR>", "", "MA" });		
			}
		}
		
		for(File f : root.listFiles()){
			if(!f.isDirectory()){
				mdl.addRow(new Object[] { f, "exe", "0B", "MA" });		
			}
		}
		
		table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
			
			public void mousePressed(MouseEvent me) {
				JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (row != -1 && me.getClickCount() == 2) {
		            File f = (File)table.getValueAt(row, 0);
		            System.out.println(f.getAbsolutePath());
		        }
			}
		});
		
		JScrollPane pane = new JScrollPane(table);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		return pane;
	}
}
