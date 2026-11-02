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
     * ModelerのFunctionListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelFunctionList.setLayout(new BorderLayout());
        m.jPanelFunctionList.setBackground(SystemColor.control);
        m.jScrollPaneFunctionList.setBorder(null);
        m.jScrollPaneForeignFunctionList.setBorder(null);
        m.jPanelFunctionList.add(m.jTabbedPaneFunctionList, BorderLayout.CENTER);
        m.jViewportFunctionList.add(m.jTableFunctionList, null);
        m.jScrollPaneFunctionList.setViewport(m.jViewportFunctionList);
        m.jScrollPaneFunctionList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneFunctionList_mouseClicked(e); } });
        m.jTableFunctionList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableFunctionList_mouseClicked(e); } });
        m.jTableFunctionList.addFocusListener(new Modeler_FocusListener());
        m.jTableFunctionList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableFunctionList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jViewportForeignFunctionList.add(m.jTableForeignFunctionList, null);
        m.jScrollPaneForeignFunctionList.setViewport(m.jViewportForeignFunctionList);
        m.jScrollPaneForeignFunctionList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneForeignFunctionList_mouseClicked(e); } });
        m.jTableForeignFunctionList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableForeignFunctionList_mouseClicked(e); } });
        m.jTableForeignFunctionList.addFocusListener(new Modeler_FocusListener());
        m.jTableForeignFunctionList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jTabbedPaneFunctionList.addTab(res.getString("S632"), m.imageIconFunction,
            m.jScrollPaneFunctionList);
        m.jTabbedPaneFunctionList.addTab(res.getString("S633"), m.imageIconForeignFunction,
            m.jScrollPaneForeignFunctionList);
        m.jTabbedPaneFunctionList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableFunctionList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableFunctionList.setBackground(SystemColor.control);
        m.jTableFunctionList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableFunctionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.tableModelFunctionList.addColumn("No.");
        m.tableModelFunctionList.addColumn(res.getString("S201"));
        m.tableModelFunctionList.addColumn(res.getString("S638"));
        m.tableModelFunctionList.addColumn(res.getString("S639"));
        m.tableModelFunctionList.addColumn(res.getString("S640"));
        m.tableModelFunctionList.addColumn(res.getString("S641"));
        m.tableModelFunctionList.addColumn(res.getString("S642"));
        m.tableModelFunctionList.addColumn(res.getString("S667"));
        m.column0 = m.jTableFunctionList.getColumnModel().getColumn(0);
        m.column1 = m.jTableFunctionList.getColumnModel().getColumn(1);
        m.column2 = m.jTableFunctionList.getColumnModel().getColumn(2);
        m.column3 = m.jTableFunctionList.getColumnModel().getColumn(3);
        m.column4 = m.jTableFunctionList.getColumnModel().getColumn(4);
        m.column5 = m.jTableFunctionList.getColumnModel().getColumn(5);
        m.column6 = m.jTableFunctionList.getColumnModel().getColumn(6);
        m.column7 = m.jTableFunctionList.getColumnModel().getColumn(7);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(150);
        m.column2.setPreferredWidth(250);
        m.column3.setPreferredWidth(230);
        m.column4.setPreferredWidth(600);
        m.column5.setPreferredWidth(250);
        m.column6.setPreferredWidth(150);
        m.column7.setPreferredWidth(150);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        m.column5.setCellRenderer(m.rendererAlignmentLeft);
        m.column6.setCellRenderer(m.rendererAlignmentLeft);
        m.column7.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableFunctionList.getTableHeader().setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableFunctionList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        m.jTableForeignFunctionList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableForeignFunctionList.setBackground(SystemColor.control);
        m.jTableForeignFunctionList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableForeignFunctionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.tableModelForeignFunctionList.addColumn("No.");
        m.tableModelForeignFunctionList.addColumn(res.getString("S436"));
        m.tableModelForeignFunctionList.addColumn(res.getString("S647"));
        m.tableModelForeignFunctionList.addColumn(res.getString("S648"));
        m.tableModelForeignFunctionList.addColumn(res.getString("S649"));
        m.tableModelForeignFunctionList.addColumn(res.getString("S650"));
        m.column0 = m.jTableForeignFunctionList.getColumnModel().getColumn(0);
        m.column1 = m.jTableForeignFunctionList.getColumnModel().getColumn(1);
        m.column2 = m.jTableForeignFunctionList.getColumnModel().getColumn(2);
        m.column3 = m.jTableForeignFunctionList.getColumnModel().getColumn(3);
        m.column4 = m.jTableForeignFunctionList.getColumnModel().getColumn(4);
        m.column5 = m.jTableForeignFunctionList.getColumnModel().getColumn(5);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(250);
        m.column2.setPreferredWidth(150);
        m.column3.setPreferredWidth(250);
        m.column4.setPreferredWidth(200);
        m.column5.setPreferredWidth(600);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        m.column5.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableForeignFunctionList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableForeignFunctionList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
