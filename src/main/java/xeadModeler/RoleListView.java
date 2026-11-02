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
 * ロールリストノードのビュー
 * ロールの一覧表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class RoleListView {

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

    public RoleListView() {
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
     * ModelerのRoleListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelRoleList.setLayout(new BorderLayout());
        m.jScrollPaneRoleList.setBorder(null);
        m.jPanelRoleList.add(m.jScrollPaneRoleList, BorderLayout.CENTER);
        m.jScrollPaneRoleList.getViewport().add(m.jTableRoleList, null);
        m.jScrollPaneRoleList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneRoleList_mouseClicked(e); } });
        m.jTableRoleList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableRoleList_mouseClicked(e); } });
        m.jTableRoleList.addFocusListener(new Modeler_FocusListener());
        m.jTableRoleList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableRoleList.setRowHeight(m.TABLE_ROW_HEIGHT);
        //(m.jTableRoleList)//
        m.jTableRoleList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableRoleList.setBackground(SystemColor.control);
        m.jTableRoleList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.tableModelRoleList.addColumn("No.");
        m.tableModelRoleList.addColumn(res.getString("S201"));
        m.tableModelRoleList.addColumn(res.getString("S340"));
        m.tableModelRoleList.addColumn(res.getString("S341"));
        m.tableModelRoleList.addColumn(res.getString("S342"));
        m.tableModelRoleList.addColumn(res.getString("S204"));
        m.column0 = m.jTableRoleList.getColumnModel().getColumn(0);
        m.column1 = m.jTableRoleList.getColumnModel().getColumn(1);
        m.column2 = m.jTableRoleList.getColumnModel().getColumn(2);
        m.column3 = m.jTableRoleList.getColumnModel().getColumn(3);
        m.column4 = m.jTableRoleList.getColumnModel().getColumn(4);
        m.column5 = m.jTableRoleList.getColumnModel().getColumn(5);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(250);
        m.column3.setPreferredWidth(150);
        m.column4.setPreferredWidth(40);
        m.column5.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.column5.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableRoleList.getTableHeader().setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableRoleList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
