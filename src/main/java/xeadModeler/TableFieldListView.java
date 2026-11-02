package xeadModeler;

import xeadModeler.Modeler;
import xeadModeler.view.CheckBoxHeaderRenderer;
import xeadModeler.view.CheckBoxHeaderListener;
import xeadModeler.view.CheckBoxRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ResourceBundle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

/**
 * テーブルフィールドリストノードのビュー
 * テーブルのフィールド一覧表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableFieldListView {

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

    public TableFieldListView() {
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
     * ModelerのTableFieldListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelTableFieldList.setLayout(new BorderLayout());
        m.jPanelTableFieldList.setBackground(SystemColor.control);
        m.jScrollPaneTableFieldList.setBorder(null);
        m.jPanelTableFieldList.add(m.jScrollPaneTableFieldList, BorderLayout.CENTER);
        m.jViewportTableFieldList.add(m.jTableTableFieldList, null);
        m.jScrollPaneTableFieldList.setViewport(m.jViewportTableFieldList);
        m.jScrollPaneTableFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableFieldList_mouseClicked(e); } });
        m.jTableTableFieldList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableFieldList_mouseClicked(e); } public void mousePressed(MouseEvent e) { m.jTableTableFieldList_mousePressed(e); } public void mouseReleased(MouseEvent e) { m.jTableTableFieldList_mouseReleased(e); } });
        m.jTableTableFieldList.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jTableTableFieldList_mouseDragged(e); } });
        m.jTableTableFieldList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableFieldList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableTableFieldList.setRowHeight(m.TABLE_ROW_HEIGHT);
        //(m.jTableTableFieldList)//
        m.jTableTableFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableFieldList.setBackground(SystemColor.control);
        m.jTableTableFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.tableModelTableFieldList.addColumn("No.");
        m.tableModelTableFieldList.addColumn(res.getString("S297"));
        m.tableModelTableFieldList.addColumn(res.getString("S542"));
        m.tableModelTableFieldList.addColumn(res.getString("S547"));
        m.tableModelTableFieldList.addColumn(res.getString("S543"));
        m.tableModelTableFieldList.addColumn(res.getString("S544"));
        m.tableModelTableFieldList.addColumn("Null");
        m.tableModelTableFieldList.addColumn(res.getString("S581"));
        m.tableModelTableFieldList.addColumn(res.getString("S546"));
        m.tableModelTableFieldList.addColumn(res.getString("S204"));
        m.column0 = m.jTableTableFieldList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableFieldList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableFieldList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableFieldList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableFieldList.getColumnModel().getColumn(4);
        m.column5 = m.jTableTableFieldList.getColumnModel().getColumn(5);
        m.column6 = m.jTableTableFieldList.getColumnModel().getColumn(6);
        m.column7 = m.jTableTableFieldList.getColumnModel().getColumn(7);
        m.column8 = m.jTableTableFieldList.getColumnModel().getColumn(8);
        m.column9 = m.jTableTableFieldList.getColumnModel().getColumn(9);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(200);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(80);
        m.column4.setPreferredWidth(93);
        m.column5.setPreferredWidth(150);
        m.column6.setPreferredWidth(45);
        m.column7.setPreferredWidth(45);
        m.column8.setPreferredWidth(70);
        m.column9.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        CheckBoxHeaderRenderer checkBoxHeaderRenderer = new CheckBoxHeaderRenderer(
            m, new CheckBoxHeaderListener(m));
        m.column3.setHeaderRenderer(checkBoxHeaderRenderer);
        CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer(m);
        m.column3.setCellRenderer(checkBoxRenderer);
        DefaultCellEditor editorWithCheckBox = new DefaultCellEditor(
            new JCheckBox(res.getString("S483")));
        m.column3.setCellEditor(editorWithCheckBox);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.column5.setCellRenderer(m.rendererAlignmentLeft);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentCenter);
        m.column9.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableTableFieldList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableFieldList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
