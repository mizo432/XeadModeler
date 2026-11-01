package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * チェックボックスのレンダラー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private Modeler modeler;

    public CheckBoxRenderer(Modeler modeler) {
        this.modeler = modeler;
        setText(modeler.res.getString("S483"));
        setHorizontalAlignment(JLabel.LEFT);
        setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
    }

    public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      }
      setSelected((value != null && ((Boolean) value).booleanValue()));
      return this;
    }
}
