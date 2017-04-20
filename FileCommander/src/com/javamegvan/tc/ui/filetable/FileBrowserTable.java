package com.javamegvan.tc.ui.filetable;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

public class FileBrowserTable extends JTable implements MouseListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 3L;

	static Color _textSelectedColor = Color.RED;
	static Color _textColor = Color.BLACK;
	static Color _backgroundSelectionColor = new Color(225, 225, 225);
	static Color _backgroundHoverSelectionColor = new Color(135, 206, 250);
	static Color _backgroundNonSelectionColor = Color.WHITE;
	static float _nonFocusBrightness = 0.15f;

	private static SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy. dd. MM.  HH:mm:ss");

	private FileTableModel _model = new FileTableModel();

	private FileBrowserEventListener _eventListener;

	public File CurrentFolder;
	public ArrayList<FileRow> Selection = new ArrayList<FileRow>();

	private boolean _doMouseSelection = false;
	private boolean _doRangeSelection = false;
	private int _lastSelectedItem = -1;

	public FileBrowserTable() {
		super.setModel(_model);
		super.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		super.setShowGrid(false);
		super.setRowSelectionAllowed(true);
		super.addMouseListener(this);

		super.setFocusable(true);
		super.addFocusListener(this);
		super.addKeyListener(this);

		super.setBackground(_backgroundNonSelectionColor);
		super.setFillsViewportHeight(true);

		super.setDefaultRenderer(FileRow.class, new FileIconTableCellRenderer());
		super.setDefaultRenderer(Long.class, new FileSizeTableCellRenderer());
		super.setDefaultRenderer(String.class, new FileStringTableCellRenderer());

		// Column sizes
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

	public void setTableEventListener(FileBrowserEventListener l) {
		_eventListener = l;
	}

	public void navigateTo(File root) {
		CurrentFolder = root;

		Selection.clear();
		_lastSelectedItem = -1;

		if (_eventListener != null) {
			_eventListener.onPathChange(this);
		}

		for (int x = _model.getRowCount() - 1; x >= 0; x--) {
			_model.removeRow(x);
		}
		_model.setRowCount(0);

		if (root.getParentFile() != null) {
			addRowEntry(new FileRow(root.getParentFile(), true));
		}

		if (root.listFiles() != null) {
			for (File f : root.listFiles()) {
				if (f.isDirectory()) {
					addRowEntry(new FileRow(f, false));
				}
			}

			for (File f : root.listFiles()) {
				if (!f.isDirectory()) {
					addRowEntry(new FileRow(f, false));
				}
			}
		}
	}

	private void addRowEntry(FileRow row) {
		String ext = "-";
		if (row.TargetFile.isFile()) {
			ext = Utils.getFileExtension(row.TargetFile);
		}

		_model.addRow(new Object[] { row, ext, (row.TargetFile.isDirectory() ? -1 : row.TargetFile.length()),
				_dateFormat.format(row.TargetFile.lastModified()) });
	}

	private void redrawComponent() {
		super.invalidate();
		super.validate();
		super.repaint();
	}

	private void updateSelection(FileRow row, boolean redraw) {
		if (Selection.contains(row)) {
			Selection.remove(row);
		} else {
			Selection.add(row);
		}

		if (_eventListener != null) {
			_eventListener.onSelectionChange(this);
		}

		if (redraw) {
			redrawComponent();
		}
	}

	public void mousePressed(MouseEvent me) {
		Point p = me.getPoint();
		int row = super.rowAtPoint(p);
		if (row != -1) {
			if (me.getClickCount() % 2 == 0) {
				FileRow f = (FileRow) super.getValueAt(row, 0);
				if (f.TargetFile.isDirectory()) {
					navigateTo(f.TargetFile);
				} else {
					try {
						Desktop.getDesktop().open(f.TargetFile);
					} catch (IOException e) {
						// e.printStackTrace();
						Utils.createMessageBox("Nem található társított alkalmazás!", "Megnyitás");
					}
				}
			} else if (me.getClickCount() < 2) {
				if (_doMouseSelection) {
					if (_doRangeSelection) {
						int to = Math.max(row, _lastSelectedItem);
						for (int x = Math.min(row, _lastSelectedItem); x <= to; x++) {
							updateSelection((FileRow) super.getValueAt(x, 0), (x == to));
						}
					} else {
						updateSelection((FileRow) super.getValueAt(row, 0), true);
					}
				}

				_lastSelectedItem = row;
			}
			if (me.getButton() == MouseEvent.BUTTON3) {
				int r = super.rowAtPoint(me.getPoint());
				if (r >= 0 && r < super.getRowCount()) {
					super.setRowSelectionInterval(r, r);
				} else {
					super.clearSelection();
				}
				updateSelection((FileRow) super.getValueAt(super.getSelectedRow(), 0), true);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (super.getSelectedRow() > -1) {
				updateSelection((FileRow) super.getValueAt(super.getSelectedRow(), 0), true);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_SHIFT) {
			_doMouseSelection = true;
			_doRangeSelection = (e.getKeyCode() == KeyEvent.VK_SHIFT);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_SHIFT) {
			_doMouseSelection = false;
			_doRangeSelection = false;
		}
	}

	public void setLabelColors(JLabel lbl, boolean hoverSelection, boolean itemSelected) {
		lbl.setForeground(itemSelected ? _textSelectedColor : _textColor);

		Color bg;
		if (hoverSelection) {
			bg = _backgroundHoverSelectionColor;
		} else {
			bg = (itemSelected ? _backgroundSelectionColor : _backgroundNonSelectionColor);
		}

		if (!super.isFocusOwner() && !itemSelected) {
			bg = Utils.getBrighterColor(bg, _nonFocusBrightness);
		}
		lbl.setBackground(bg);
	}

	public void focusLost(FocusEvent arg0) {
		super.getTableHeader().setBackground(null);
		
		redrawComponent();
	}

	public void focusGained(FocusEvent arg0) {
		super.getTableHeader().setBackground(_backgroundSelectionColor);
		
		redrawComponent();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
