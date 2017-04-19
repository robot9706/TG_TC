package com.javamegvan.tc.ui.filetable;

import javax.swing.ImageIcon;

public class IconCache {
	public static ImageIcon FolderIcon;
	public static ImageIcon FileIcon;
	public static ImageIcon RootIcon;
	
	public static void Load(){
		FolderIcon = new ImageIcon(IconCache.class.getResource("/resources/icon_folder.png"));
		FileIcon = new ImageIcon(IconCache.class.getResource("/resources/icon_file.png"));
		RootIcon = new ImageIcon(IconCache.class.getResource("/resources/icon_root.png"));
	}
}
