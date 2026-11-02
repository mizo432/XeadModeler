package xeadModeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ResourceBundle;

/**
 * 主題領域リストノードのビュー
 * 主題領域の一覧表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubjectAreaListView {

    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

    public SubjectAreaListView() {
    }

    /**
     * ModelerのSubjectAreaListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelSubjectAreaList.setLayout(new BorderLayout());
        m.jPanelSubjectAreaList.setBackground(SystemColor.control);
        m.jPanelSubjectAreaList.add(m.jScrollPaneSubjectAreaList, BorderLayout.CENTER);
        m.jScrollPaneSubjectAreaList.setBorder(null);
        m.jScrollPaneSubjectAreaList.getViewport().add(m.jTableSubjectAreaList, null);
        m.jScrollPaneSubjectAreaList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSubjectAreaList_mouseClicked(e); } });
        m.jTableSubjectAreaList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSubjectAreaList_mouseClicked(e); } });
        m.jTableSubjectAreaList.addFocusListener(new Modeler_FocusListener());
        m.jTableSubjectAreaList.addKeyListener(
            new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableSubjectAreaList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jTableSubjectAreaList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableSubjectAreaList.setBackground(SystemColor.control);
        m.jTableSubjectAreaList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableSubjectAreaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.tableModelSubjectAreaList.addColumn("No.");
        m.tableModelSubjectAreaList.addColumn(res.getString("S201"));
        m.tableModelSubjectAreaList.addColumn(res.getString("S324"));
        m.tableModelSubjectAreaList.addColumn(res.getString("S204"));
        m.column0 = m.jTableSubjectAreaList.getColumnModel().getColumn(0);
        m.column1 = m.jTableSubjectAreaList.getColumnModel().getColumn(1);
        m.column2 = m.jTableSubjectAreaList.getColumnModel().getColumn(2);
        m.column3 = m.jTableSubjectAreaList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(300);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableSubjectAreaList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableSubjectAreaList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }

    /**
     * 読み取り専用のテーブルモデル
     */
    public static class TableModelReadOnlyList extends DefaultTableModel {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}
