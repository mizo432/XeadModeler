package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ResourceBundle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

/**
 * テーブルキーリストノードのビュー
 * テーブルのキー一覧表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableKeyListView {

    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanel = new JPanel();
    private final JScrollPane jScrollPane = new JScrollPane();
    private final JTable jTable = new JTable();
    private final TableModelReadOnlyList tableModel = new TableModelReadOnlyList();

    // Column references
    private TableColumn column0;
    private TableColumn column1;
    private TableColumn column2;
    private TableColumn column3;

    // Renderers
    private final DefaultTableCellRenderer rendererAlignmentCenter = new DefaultTableCellRenderer();
    private final DefaultTableCellRenderer rendererAlignmentLeft = new DefaultTableCellRenderer();
    private DefaultTableCellRenderer rendererTableHeader;

    public TableKeyListView() {
    }

    /**
     * コンポーネントを初期化します。
     */

    public JPanel getPanel() {
        return jPanel;
    }

    public JTable getTable() {
        return jTable;
    }

    public TableModelReadOnlyList getTableModel() {
        return tableModel;
    }

    class TableFocusListener extends java.awt.event.FocusAdapter {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
        }
    }

    class TableMouseAdapter extends java.awt.event.MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (e.getClickCount() == 2) {
                // Double click handling
            }
        }
    }

    class TableKeyAdapter extends java.awt.event.KeyAdapter {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
        }
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

    /**
     * ModelerのTableKeyListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelTableKeyList.setLayout(new BorderLayout());
        m.jPanelTableKeyList.setBackground(SystemColor.control);
        m.jScrollPaneTableKeyList.setBorder(null);
        m.jPanelTableKeyList.add(m.jScrollPaneTableKeyList, BorderLayout.CENTER);
        m.jScrollPaneTableKeyList.getViewport().add(m.jTableTableKeyList, null);
        m.jScrollPaneTableKeyList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableKeyList_mouseClicked(e); } });
        m.jTableTableKeyList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableKeyList_mouseClicked(e); } });
        m.jTableTableKeyList.setRowHeight(m.TABLE_ROW_HEIGHT);
        //(jTableKeyList)//
        m.jTableTableKeyList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableKeyList.setBackground(SystemColor.control);
        m.jTableTableKeyList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableKeyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableTableKeyList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableKeyList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.tableModelTableKeyList.addColumn("No.");
        m.tableModelTableKeyList.addColumn(res.getString("S540"));
        m.tableModelTableKeyList.addColumn(res.getString("S612"));
        m.tableModelTableKeyList.addColumn(res.getString("S613"));
        m.column0 = m.jTableTableKeyList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableKeyList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableKeyList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableKeyList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(70);
        m.column2.setPreferredWidth(70);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentCenter);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableTableKeyList.getTableHeader().setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableKeyList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
