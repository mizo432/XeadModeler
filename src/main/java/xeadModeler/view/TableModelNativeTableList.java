package xeadModeler.view;

import javax.swing.table.DefaultTableModel;

/**
 * ネイティブテーブルリストのTableModel（3列目のみ編集可能）
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableModelNativeTableList extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public boolean isCellEditable(int row, int col) {
        if (col != 3) {
            return false;
        } else {
            return true;
        }
    }
}
