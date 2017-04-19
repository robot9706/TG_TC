package com.javamegvan.tc.ui;

import java.io.File;

import javax.swing.table.DefaultTableModel;

public class FileTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 2L;

	public FileTableModel(){ 
		super.addColumn("Név");
		super.addColumn("Kit.");
		super.addColumn("Méret");
		super.addColumn("Dátum");
	}
	
	public boolean isCellEditable(int row, int column){  
		return false;  
	}
	
    public Class<?> getColumnClass(int column) {
    	if(column == 0){
    		return File.class;
    	}
        
        return super.getColumnClass(column);
    }
}
