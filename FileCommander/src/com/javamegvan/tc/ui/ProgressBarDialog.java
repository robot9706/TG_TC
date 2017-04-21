package com.javamegvan.tc.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.javamegvan.tc.task.Task;

public class ProgressBarDialog extends JFrame implements WindowListener, ActionListener {
	private static final long serialVersionUID = -1808061901995825308L;
	
	private JLabel _text;
	private JProgressBar _totalProgress;
	private JProgressBar _partProgress;
	private JButton _cancelButton;
	
	private Task _task;
	
	private MainFrame _owner;
	
	public ProgressBarDialog(Task task, MainFrame owner){
		_owner = owner;
		
		super.setSize(450, 150);
		super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Utils.centerJForm(this);
		
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		{
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 1.0f;
			
			_text = new JLabel("...", SwingConstants.CENTER);
			content.add(_text, c);
			
			c.gridy = 1;
			_totalProgress = new JProgressBar();
			_totalProgress.setMaximum(100);
			_totalProgress.setValue(50);	
			_totalProgress.setBorder(new EmptyBorder(10, 15, 0, 15));
			_totalProgress.setStringPainted(true);
			content.add(_totalProgress, c);
			
			c.gridy = 2;
			_partProgress = new JProgressBar();
			_partProgress.setMaximum(100);
			_partProgress.setValue(50);
			_partProgress.setBorder(new EmptyBorder(10, 15, 10, 15));
			_partProgress.setStringPainted(true);
			content.add(_partProgress, c);
			
			c.fill = GridBagConstraints.NONE;
			c.gridy = 3;
			_cancelButton = new JButton("Mégse");
			_cancelButton.setMinimumSize(new Dimension(100, 10));
			_cancelButton.addActionListener(this);
			content.add(_cancelButton, c);
		}

		super.add(content);
		
		super.addWindowListener(this);
		
		setTotalMax(0);
		setTotalProgress(0);
		
		setPartMax(0);
		setPartProgress(0);
		
		_task = task;
		_task.setDialog(this);
	}
	
	public void showAndExecuteTask(){
		super.setVisible(true);
		
		_task.executeTask();
	}
	
	public void setMessage(String msg){
		_text.setText(msg);
	}
	
	public void setTotalMax(int max){
		_totalProgress.setMaximum(max);
	}
	
	public void setTotalProgress(int p){
		if(p < 0){
			p = 0;
		}
		if(p > _totalProgress.getMaximum()){
			p = _totalProgress.getMaximum();
		}
		
		_totalProgress.setValue(p);
		
		_totalProgress.setString(String.valueOf(_totalProgress.getValue()) + " / " + String.valueOf(_totalProgress.getMaximum()));
	}
	
	public void setPartMax(int max){
		_partProgress.setMaximum(max);
	}
	
	public void setPartProgress(int p){
		if(p < 0){
			p = 0;
		}
		if(p > _partProgress.getMaximum()){
			p = _partProgress.getMaximum();
		}
		
		_partProgress.setValue(p);
		_partProgress.setString(String.valueOf((int)(((float)_partProgress.getValue() / _partProgress.getMaximum()) * 100.0f)) + "%");
	}

	public MainFrame getOwner(){
		return _owner;
	}
	
	public void windowClosing(WindowEvent e) {
		_task.requestCancel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == _cancelButton){
			_task.requestCancel();
		}
	}
	
	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}
}
