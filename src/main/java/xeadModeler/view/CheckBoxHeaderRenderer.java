package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * チェックボックスヘッダーのレンダラー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class CheckBoxHeaderRenderer extends JCheckBox implements TableCellRenderer, MouseListener {

    private static final long serialVersionUID = 1L;
    protected CheckBoxHeaderRenderer rendererComponent;
    protected int column;
    protected boolean mousePressed = false;
    private Modeler modeler;

    public CheckBoxHeaderRenderer(Modeler modeler, CheckBoxHeaderListener itemListener) {
        this.modeler = modeler;
        rendererComponent = this;
        rendererComponent.addItemListener(itemListener);
        itemListener.setRenderer(this);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      if (table != null) {
        JTableHeader header = table.getTableHeader();
        if (header != null) {
          header.addMouseListener(rendererComponent);
        }
      }
      setColumn(column);
      rendererComponent.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
      rendererComponent.setText(modeler.res.getString("S472"));
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      return rendererComponent;
    }

    protected void setColumn(int column) {
      this.column = column;
    }

    public int getColumn() {
      return column;
    }

    protected void handleClickEvent(MouseEvent e) {
      if (mousePressed) {
        mousePressed = false;
        JTableHeader header = (JTableHeader) (e.getSource());
        JTable tableView = header.getTable();
        TableColumnModel columnModel = tableView.getColumnModel();
        int viewColumn = columnModel.getColumnIndexAtX(e.getX());
        int column = tableView.convertColumnIndexToModel(viewColumn);
        if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {
          doClick();
        }
      }
    }

    public void mouseClicked(MouseEvent e) {
      handleClickEvent(e);
      ((JTableHeader) e.getSource()).repaint();
    }

    public void mousePressed(MouseEvent e) {
      mousePressed = true;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
