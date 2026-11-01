package xeadModeler.view;

import javax.swing.table.DefaultTableModel;

/**
 * 読み取り専用のTableModel
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableModelReadOnlyList extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
