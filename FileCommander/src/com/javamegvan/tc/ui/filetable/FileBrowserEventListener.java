package com.javamegvan.tc.ui.filetable;

public interface FileBrowserEventListener {
	void onPathChange(FileBrowserTable source);
	void onSelectionChange(FileBrowserTable source);
	void onSwitchSide();
}
