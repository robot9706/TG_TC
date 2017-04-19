package com.javamegvan.tc.ui.filetable;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.javamegvan.tc.ui.Utils;

public class FileBrowserTable extends JTable implements MouseListener, KeyListener {
	private static final long serialVersionUID = 3L;
	
	public static Color TextSelectedColor = Color.RED;
	public static Color TextColor = Color.BLACK;
	public static Color BackgroundSelectionColor = Color.LIGHT_GRAY;
	public static Color BackgroundHoverSelectionColor = new Color(135, 206, 250);
	public static Color BackgroundNonSelectionColor = Color.WHITE;
	
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy. dd. MM.  HH:mm:ss");
	
	private FileTableModel _model = new FileTableModel();
	
	private PathChangedListener _pathChangeEvent;
	
	public File CurrentFolder;
	public ArrayList<FileRow> Selection = new ArrayList<FileRow>();
	
	public FileBrowserTable(){
		super.setModel(_model);
		super.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		super.setShowGrid(false);
		super.setRowSelectionAllowed(true);
		super.addMouseListener(this);
		
		super.setFocusable(true);
		super.addKeyListener(this);
		
		super.setBackground(BackgroundNonSelectionColor);
		super.setFillsViewportHeight(true);
		
		super.setDefaultRenderer(FileRow.class, new FileIconTableCellRenderer());
		super.setDefaultRenderer(Long.class, new FileSizeTableCellRenderer());
		super.setDefaultRenderer(String.class, new FileStringTableCellRenderer());
		
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
	
	public void setPathChangedListener(PathChangedListener l){
		_pathChangeEvent = l;
	}
	
	public void navigateTo(File root){
		CurrentFolder = root;
		
		Selection.clear();
		
		if(_pathChangeEvent != null){
			_pathChangeEvent.onPathChange(this);
		}
		
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
			ext = Utils.getFileExtension(row.TargetFile);
		}
		
		_model.addRow(new Object[] { row, ext, (row.TargetFile.isDirectory() ? -1 : row.TargetFile.length()),
				_dateFormat.format(row.TargetFile.lastModified()) });
	}

	public void mousePressed(MouseEvent me) {
        Point p = me.getPoint();
        int row = super.rowAtPoint(p);
        if (row != -1 && me.getClickCount() % 2 == 0) {
            FileRow f = (FileRow)super.getValueAt(row, 0);
            if(f.TargetFile.isDirectory()){
            	navigateTo(f.TargetFile);
            }else{
            	try {
					Desktop.getDesktop().open(f.TargetFile);
				} catch (IOException e) {
					//e.printStackTrace();
					Utils.createMessageBox("Nem található társított alkalmazás!", "Megnyitás");
				}
            }
        }        
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			FileRow row = (FileRow)super.getValueAt(super.getSelectedRow(), 0);
			if(Selection.contains(row)){
				Selection.remove(row);
			}else{
				Selection.add(row);
			}
			
			super.invalidate();
			super.validate();
			super.repaint();
		}
	}
	
	public void setLabelColors(JLabel lbl, boolean hoverSelection, boolean itemSelected){
		lbl.setForeground(itemSelected ? TextSelectedColor : TextColor);
		
		if(hoverSelection){
			lbl.setBackground(BackgroundHoverSelectionColor);
		}else{
			lbl.setBackground(itemSelected ? BackgroundSelectionColor : BackgroundNonSelectionColor);
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

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
