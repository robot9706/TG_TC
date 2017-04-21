package com.javamegvan.tc.task;

import javax.swing.SwingUtilities;

import com.javamegvan.tc.ui.MainFrame;
import com.javamegvan.tc.ui.ProgressBarDialog;
import com.javamegvan.tc.ui.Utils;

public abstract class Task implements Runnable {
	private ProgressBarDialog _dialog;
	private Thread _taskThread;
	
	private boolean _cancelRequested = false;
	
	public void setDialog(ProgressBarDialog dialog){
		_dialog = dialog;
	}
	
	protected void setTotalMax(int max){
		SwingUtilities.invokeLater(new Runnable(){
            public void run(){
            	_dialog.setTotalMax(max);
            }
		});
	}
	
	protected void setTotalProgress(int p){
		SwingUtilities.invokeLater(new Runnable(){
            public void run(){
            	_dialog.setTotalProgress(p);
            }
		});
	}
	
	protected void setPartMax(int max){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_dialog.setPartMax(max);
			}
		});
	}
	
	protected void setPartProgress(int p){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_dialog.setPartProgress(p);
			}
		});
	}
	
	protected void setMessage(String msg){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_dialog.setMessage(msg);
			}
		});
	}
	
	public MainFrame getMain(){
		return _dialog.getOwner();
	}
	
	protected void taskDone(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_dialog.setVisible(false);
				_dialog.dispose();
			}
		});
	}
	
	protected void taskFailed(Exception ex){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				_dialog.setVisible(false);
				_dialog.dispose();
				
				Utils.createMessageBox(_dialog, "Sikertelen mûvelet: " + ex.getMessage(), "Hiba");
			}
		});
	}
	
	public void requestCancel(){
		_cancelRequested = true;
	}
	
	public boolean isCancelRequested(){
		return _cancelRequested;
	}
	
	public void executeTask(){
		if(_taskThread == null){
			_taskThread = new Thread(this);
			_taskThread.start();
		}
	}
	
	public void run(){
		try{
			doTask();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public abstract void doTask();
}
