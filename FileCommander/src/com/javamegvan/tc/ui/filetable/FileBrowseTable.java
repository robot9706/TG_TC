package com.javamegvan.tc.ui.filetable;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.javamegvan.tc.ui.FileUtils;

public class FileBrowseTable extends JTable implements MouseListener {
	private static final long serialVersionUID = 3L;
	
	public static Color TextSelectionColor = Color.BLACK;
	public static Color BackgroundSelectionColor = new Color(135, 206, 250);;
	public static Color TextNonSelectionColor = Color.BLACK;
	public static Color BackgroundNonSelectionColor = Color.WHITE;
	
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy. dd. MM.  HH:mm:ss");
	
	private FileTableModel _model = new FileTableModel();
	
	public FileBrowseTable(){
		super.setModel(_model);
		super.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		super.setShowGrid(false);
		super.setRowSelectionAllowed(true);
		super.setDefaultRenderer(FileRow.class, new FileIconTableCellRenderer());
		super.setDefaultRenderer(Long.class, new FileSizeTableCellRenderer());
		super.setDefaultRenderer(String.class, new FileStringTableCellRenderer());
		super.addMouseListener(this);
		
		//Column sizes
		{
			TableColumn kit = getColumn(FileTableModel.ExtensionColumn);
			kit.setMinWidth(50);
			kit.setMaxWidth(50);
			
			kit = getColumn(FileTableModel.SizeColumn);
			kit.setMinWidth(100);
			kit.setMaxWidth(100);
			
			kit = getColumn(FileTableModel.DateColumn);
			kit.setMinWidth(150);
			kit.setMaxWidth(150);
		}
	}
	
	/*public void addRootFile(File f){
		addRowEntry(new FileRow(f, true));
	}
	
	public void addFileRow(File f){
		addRowEntry(new FileRow(f, false));
	}*/
	
	public void navigateTo(File root){
		for(int x = _model.getRowCount() - 1;x >= 0; x--){
			_model.removeRow(x);
		}
		_model.setRowCount(0);
		
		if(root.getParentFile() != null){
			addRowEntry(new FileRow(root.getParentFile(), true));
		}
		
		if(root.listFiles() != null){
			for(File f : root.listFiles()){
				if(f.isDirectory()){
					addRowEntry(new FileRow(f, false));
				}
			}

			for(File f : root.listFiles()){
				if(!f.isDirectory()){
					addRowEntry(new FileRow(f, false));
				}
			}
		}
	}
	
	private void addRowEntry(FileRow row){
		String ext = "-";
		if(row.TargetFile.isFile()){
			ext = FileUtils.getFileExtension(row.TargetFile);
		}
		_model.addRow(new Object[] { row, ext, (row.TargetFile.isDirectory() ? -1 : row.TargetFile.length()),
				_dateFormat.format(row.TargetFile.lastModified()) });
	}

	public void mousePressed(MouseEvent me) {
        Point p = me.getPoint();
        int row = super.rowAtPoint(p);
        if (row != -1 && me.getClickCount() == 2) {
            FileRow f = (FileRow)super.getValueAt(row, 0);
            if(f.TargetFile.isDirectory()){
            	navigateTo(f.TargetFile);
            }
        }        
	}
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
