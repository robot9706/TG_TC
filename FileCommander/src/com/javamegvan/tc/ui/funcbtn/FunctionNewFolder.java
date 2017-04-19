package com.javamegvan.tc.ui.funcbtn;

import java.awt.event.KeyEvent;
import java.io.File;

import com.javamegvan.tc.ui.MainFrame;

public class FunctionNewFolder implements FunctionButton {
	public int getKeyShortcut() {
		return KeyEvent.VK_F7;
	}

	public int getKeyShortcutModifier() {
		return 0;
	}
	
	public String getTextShortcut() {
		return "F7";
	}

	public String getName() {
		return "�j mappa";
	}

	public void doFunction(MainFrame frame) {

		
		String path = frame.getFocusedBrowser()._table.CurrentFolder.getPath();
		path=new StringBuffer(path).insert(path.length(),"\\").toString();
		
		
	    File newdir = new File(path+"�j mappa");
	    if(newdir.exists()){
	     //TODO:ha l�tezik m�s nev�t l�trehozni.
	    }else{
	    	newdir.mkdir();  
	    }
	  //TODO:Friss�teni az ablakot.
	   
	}
}
