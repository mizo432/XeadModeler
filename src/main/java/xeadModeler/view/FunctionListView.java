package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * 機能リストノードのビュー
 * 機能の一覧表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class FunctionListView {

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

    public FunctionListView() {
        jTable.setModel(tableModel);
        initComponents();
    }

    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(SystemColor.control);
        jPanel.add(jScrollPane, BorderLayout.CENTER);
        jScrollPane.setBorder(null);
        jScrollPane.getViewport().add(jTable, null);

        jTable.addMouseListener(new TableMouseAdapter());
        jTable.addFocusListener(new TableFocusListener());
        jTable.addKeyListener(new TableKeyAdapter());
        jTable.setRowHeight(Modeler.TABLE_ROW_HEIGHT);

        initTable();
    }

    private void initTable() {
        jTable.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTable.setBackground(SystemColor.control);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel.addColumn("No.");
        tableModel.addColumn(res.getString("S201"));
        tableModel.addColumn(res.getString("S204"));
        tableModel.addColumn(res.getString("S324"));

        column0 = jTable.getColumnModel().getColumn(0);
        column1 = jTable.getColumnModel().getColumn(1);
        column2 = jTable.getColumnModel().getColumn(2);
        column3 = jTable.getColumnModel().getColumn(3);

        column0.setPreferredWidth(40);
        column1.setPreferredWidth(100);
        column2.setPreferredWidth(300);
        column3.setPreferredWidth(700);

        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);

        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);

        jTable.getTableHeader().setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(SwingConstants.LEFT);
    }

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
}
