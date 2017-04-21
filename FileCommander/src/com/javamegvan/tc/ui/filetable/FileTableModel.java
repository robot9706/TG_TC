package com.javamegvan.tc.ui.filetable;

import javax.swing.table.DefaultTableModel;

public class FileTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 2L;

	public static String NameColumn = "Név";
	public static String ExtensionColumn = "Kit.";
	public static String SizeColumn = "Méret";
	public static String DateColumn = "Dátum";
	
	public FileTableModel(){ 
		super.addColumn(NameColumn);
		super.addColumn(ExtensionColumn);
		super.addColumn(SizeColumn);
		super.addColumn(DateColumn);
	}
	
	public boolean isCellEditable(int row, int column){  
		return (column == 0);  
	}
	
    public Class<?> getColumnClass(int column) {
    	switch(column){
    	case 0: //Name
    		return FileRow.class;
    	case 1: //Ext.
    		return String.class;
    	case 2: //Size
    		return Long.class;
    	case 3: //Date
    		return String.class;
    	}

        return super.getColumnClass(column);
    }
}
