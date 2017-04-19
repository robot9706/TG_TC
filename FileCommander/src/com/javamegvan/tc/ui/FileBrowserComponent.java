package com.javamegvan.tc.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.javamegvan.tc.ui.filetable.FileBrowserTable;
import com.javamegvan.tc.ui.filetable.FileRow;
import com.javamegvan.tc.ui.filetable.FileBrowserEventListener;

public class FileBrowserComponent extends JPanel implements ActionListener, FileBrowserEventListener {
	private static final long serialVersionUID = -4963834230107742761L;

	private FileBrowserTable _table;
	
	private JComboBox<File> _driveSelector;
	private JLabel _driveInfo;
	private JLabel _pathInfo;
	private JLabel _selectionInfo;
	
	public FileBrowserComponent(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Top row
		{
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new GridBagLayout());
			topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
			
			GridBagConstraints cn = new GridBagConstraints();			
			cn.fill = GridBagConstraints.BOTH;
			cn.gridx = 0;
			cn.gridy = 0;
			cn.weightx = 0.1;
			topPanel.add(_driveSelector = new JComboBox<File>(), cn);
			_driveSelector.addActionListener(this);
									
			cn.gridx = 1;
			cn.gridy = 0;
			cn.gridwidth = 3;
			cn.weightx = 1.0;
			cn.insets = new Insets(0,10,0,0);
			topPanel.add(_driveInfo = new JLabel(), cn);
			
			cn.gridx = 0;
			cn.gridy = 1;
			cn.gridwidth = 4;
			cn.weightx = 1.0;
			topPanel.add(_pathInfo = new JLabel("Út: -"), cn);
			_pathInfo.setBorder(new EmptyBorder(5, 0, 5, 0));
			
			add(topPanel);
		}
		
		//File browser table row
		{
			_table = new FileBrowserTable();
			_table.setTableEventListener(this);
			
			JScrollPane pane = new JScrollPane(_table);
			pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
			add(pane);
		}
		
		//Bottom row
		{
			JPanel bottomRow = new JPanel();
			bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.X_AXIS));
			bottomRow.setBorder(new EmptyBorder(0, 5, 0, 0));
			bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50 ));
			
			_selectionInfo = new JLabel("0KB / 200KB - 0 / 17 fájl - 0 / 4 mappa");
			_selectionInfo.setHorizontalAlignment(SwingConstants.LEFT);
			
			bottomRow.add(_selectionInfo);
			add(bottomRow);
		}
		
		//Init drive selector
		for(File f : File.listRoots()){
			_driveSelector.addItem(f);
		}
	}
	
	public void navigateTo(File file){
		_table.navigateTo(file);
		
		String drive = Utils.getDriveLetter(file);
		for(int x = 0; x < _driveSelector.getItemCount(); x++){
			File item = _driveSelector.getItemAt(x);
			if(item.getPath().equalsIgnoreCase(drive)){
				_driveSelector.setSelectedIndex(x);
				break;
			}
		}
	}

	private String formatDriveSize(long size){
		size /= 1024;
		
		String text = String.valueOf(size);
		StringBuilder builder = new StringBuilder();
		
		for(int x = text.length() - 1; x >= 0; x--){
			builder.append(text.charAt(x));
			if((text.length() - x) % 3 == 0 && x > 0)
				builder.append(" ");
		}
		
		return builder.reverse().toString() + "k";
	}
	
	private void updateDriveInfo(){
		File drive = (File)_driveSelector.getSelectedItem();
		
		_driveInfo.setText(formatDriveSize(drive.getFreeSpace()) + " szabad " + formatDriveSize(drive.getTotalSpace()) + "-ból.");
	}
	
	private boolean isFileSelected(File f){
		for(FileRow r : _table.Selection){
			if(r.TargetFile.equals(f)){
				return true;
			}
		}
		
		return false;
	}
	
	private void updateSelectionText(){
		long selectionSize = 0;
		long totalSize = 0;
		int selectedFiles = 0;
		int totalFiles = 0;
		int selectedFolders = 0;
		int totalFolders = 0;
		
		for(File f : _table.CurrentFolder.listFiles()){
			if(f.isFile()){
				totalSize += f.length();
				totalFiles++;
				
				if(isFileSelected(f)){
					selectedFiles++;
					selectionSize += f.length();
				}
			}else if(f.isDirectory()){
				totalFolders++;
				
				if(isFileSelected(f)){
					selectedFolders++;
				}
			}
		}
		
		_selectionInfo.setText(String.valueOf(selectionSize / 1024) + "KB / " + String.valueOf(totalSize / 1024) + "KB - " +
				String.valueOf(selectedFiles) + " / " + String.valueOf(totalFiles) + " fájl - " +
				String.valueOf(selectedFolders) + " / " + String.valueOf(totalFolders) + " mappa");
	}
	
	public void actionPerformed(ActionEvent arg) {
		if(_table.CurrentFolder == null)
			return;
		
		String drive = Utils.getDriveLetter(_table.CurrentFolder);
		File selected = (File)_driveSelector.getSelectedItem();
		if(!drive.equalsIgnoreCase(selected.getPath())){
			_table.navigateTo(selected);
		}
		
		updateDriveInfo();
	}

	public void onPathChange(FileBrowserTable source) {
		if(source.CurrentFolder == null){
			_pathInfo.setText("Út: -");
		}else{
			_pathInfo.setText("Út: " + source.CurrentFolder.getPath());
			updateSelectionText();
		}
	}

	public void onSelectionChange(FileBrowserTable source) {
		updateSelectionText();
	}
	
	public boolean hasFocus(){
		return _table.isFocusOwner();
	}
	
	public void doFocus(){
		_table.requestFocus();
	}
	
	public File getFocusedFile(){
		if(_table.getSelectedRow() > -1){
			return ((FileRow)_table.getValueAt(_table.getSelectedRow(), 0)).TargetFile;
		}
		
		return null;
	}
	
	public ArrayList<File> getSelectedFiles(boolean filesOnly){
		ArrayList<File> files = new ArrayList<File>();
		
		for(FileRow s : _table.Selection){
			if(filesOnly && s.TargetFile.isFile()){
				files.add(s.TargetFile);
			}else if(!filesOnly && s.TargetFile.isDirectory()){
				files.add(s.TargetFile);
			}
		}
		
		return files;
	}
}
