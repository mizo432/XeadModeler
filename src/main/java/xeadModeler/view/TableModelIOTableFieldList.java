package xeadModeler.view;

import javax.swing.table.DefaultTableModel;

/**
 * IOテーブルフィールドリストのTableModel（4列目以降のみ編集可能）
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableModelIOTableFieldList extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public boolean isCellEditable(int row, int col) {
        if (col <= 3) {
            return false;
        } else {
            return true;
        }
    }
}
