package xeadModeler.view;

import javax.swing.table.DefaultTableModel;

/**
 * 編集可能なTableModel（1列目は読み取り専用）
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableModelEditableList extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return false;
        } else {
            return true;
        }
    }
}
