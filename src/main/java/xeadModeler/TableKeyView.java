package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.event.TableColumnModelListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

/**
 * テーブルキーノードのビュー
 * キー情報、フィールドリスト、リレーションシップリストなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableKeyView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelTableKey = new JPanel();
    private final JPanel jPanelTableKey1 = new JPanel();
    private final JPanel jPanelTableKey2 = new JPanel();
    private final JPanel jPanelTableKey3 = new JPanel();
    private final JPanel jPanelTableKey4 = new JPanel();
    private final JPanel jPanelTableKey5 = new JPanel();
    private final JPanel jPanelTableKey6 = new JPanel();
    private final JSplitPane jSplitPaneTableKey = new JSplitPane();
    
    // Labels
    private final JLabel jLabelKeyType = new JLabel();
    private final JLabel jLabelTableKeySortKey = new JLabel();
    private final JLabel jLabelTableKeyFieldList = new JLabel();
    private final JLabel jLabelRelationshipList = new JLabel();
    
    // Fields
    private final JTextField jTextFieldTableKeyType = new JTextField();
    private final JTextField jTextFieldTableKeySortKey = new JTextField();
    
    // Scroll panes
    private final JScrollPane jScrollPaneTableKeyFieldList = new JScrollPane();
    private final JScrollPane jScrollPaneRelationshipList = new JScrollPane();
    private final JViewport jViewportTableKeyFieldList = new JViewport();
    private final JViewport jViewportRelationshipList = new JViewport();
    
    // Tables
    private final JTable jTableTableKeyFieldList;
    private final JTable jTableRelationshipList;
    
    // State
    private int selectedRow_jTableTableKeyFieldList;
    private int selectedRow_jTableRelationshipList;
    
    // Renderers
    private DefaultTableCellRenderer rendererAlignmentCenter;
    private DefaultTableCellRenderer rendererAlignmentLeft;
    private DefaultTableCellRenderer rendererTableHeader;
    
    // Columns
    private javax.swing.table.TableColumn column0;
    private javax.swing.table.TableColumn column1;
    private javax.swing.table.TableColumn column2;
    private javax.swing.table.TableColumn column3;
    private javax.swing.table.TableColumn column4;
    
    private Modeler modeler;
    
    /**
     * テーブルキー操作リスナーインターフェース
     */
    public interface TableKeyListener {
        void onTableKeyChanged();
    }
    
    private TableKeyListener tableKeyListener;
    
    public TableKeyView(Modeler modeler, TableKeyListener listener) {
        this.modeler = modeler;
        this.tableKeyListener = listener;
        jTableTableKeyFieldList = modeler.jTableTableKeyFieldList;
        jTableRelationshipList = modeler.jTableRelationshipList;
    }
    
    public JPanel getPanel() {
        return jPanelTableKey;
    }
    
    public JTable getFieldList() {
        return jTableTableKeyFieldList;
    }
    
    public JTable getRelationshipList() {
        return jTableRelationshipList;
    }
    
    /**
     * コンポーネントを初期化します。
     */

    /**
     * ModelerのTableKeyコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelTableKey5.setBackground(SystemColor.control);
        m.jScrollPaneTableKeyFieldList.getViewport().setBackground(SystemColor.control);
        m.jScrollPaneRelationshipList.getViewport().setBackground(SystemColor.control);
        m.jPanelTableKey.setLayout(new BorderLayout());
        m.jPanelTableKey1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelTableKey1.setMinimumSize(new Dimension(10, 10));
        m.jPanelTableKey1.setPreferredSize(new Dimension(10, 43));
        m.jPanelTableKey1.setLayout(null);
        m.jLabelKeyType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelKeyType.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelKeyType.setText(res.getString("S616"));
        m.jLabelKeyType.setBounds(new Rectangle(5, 12, 130, 20));
        m.jTextFieldTableKeyType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableKeyType.setEditable(false);
        m.jTextFieldTableKeyType.setBounds(new Rectangle(140, 10, 200, 25));
        m.jLabelTableKeySortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableKeySortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableKeySortKey.setText(res.getString("S540"));
        m.jLabelTableKeySortKey.setBounds(new Rectangle(350, 11, 130, 15));
        m.jTextFieldTableKeySortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableKeySortKey.setBounds(new Rectangle(485, 10, 90, 25));
        m.jScrollPaneTableKeyFieldList.setBorder(null);
        m.jLabelTableKeyFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableKeyFieldList.setText(res.getString("S613"));
        m.jLabelTableKeyFieldList.setBounds(new Rectangle(5, 1, 130, 20));
        m.jLabelRelationshipList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelRelationshipList.setText(res.getString("S624"));
        m.jLabelRelationshipList.setBounds(new Rectangle(5, 1, 130, 20));
        m.jScrollPaneRelationshipList.setBorder(null);
        m.jSplitPaneTableKey.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneTableKey.setBorder(null);
        m.jSplitPaneTableKey.setDividerLocation(300);
        m.jPanelTableKey2.setLayout(new BorderLayout());
        m.jPanelTableKey2.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelTableKey3.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelTableKey3.setPreferredSize(new Dimension(10, 300));
        m.jPanelTableKey3.setLayout(new BorderLayout());
        m.jPanelTableKey4.setLayout(null);
        m.jPanelTableKey4.setPreferredSize(new Dimension(1, 22));
        m.jPanelTableKey5.setPreferredSize(new Dimension(1, 22));
        m.jPanelTableKey5.setLayout(null);
        m.jPanelTableKey6.setLayout(new BorderLayout());
        m.jSplitPaneTableKey.add(m.jPanelTableKey2, JSplitPane.TOP);
        m.jSplitPaneTableKey.add(m.jPanelTableKey3, JSplitPane.BOTTOM);
        m.jPanelTableKey.add(m.jPanelTableKey1, BorderLayout.NORTH);
        m.jPanelTableKey1.add(m.jLabelKeyType, null);
        m.jPanelTableKey1.add(m.jTextFieldTableKeyType, null);
        m.jPanelTableKey1.add(m.jLabelTableKeySortKey, null);
        m.jPanelTableKey1.add(m.jTextFieldTableKeySortKey, null);
        m.jPanelTableKey.add(m.jPanelTableKey6, BorderLayout.CENTER);
        m.jPanelTableKey6.add(m.jSplitPaneTableKey, BorderLayout.CENTER);
        m.jPanelTableKey2.add(m.jPanelTableKey4, BorderLayout.NORTH);
        m.jPanelTableKey4.add(m.jLabelTableKeyFieldList, null);
        m.jPanelTableKey2.add(m.jScrollPaneTableKeyFieldList, BorderLayout.CENTER);
        m.jPanelTableKey3.add(m.jPanelTableKey5, BorderLayout.NORTH);
        m.jPanelTableKey5.add(m.jLabelRelationshipList, null);
        m.jPanelTableKey3.add(m.jScrollPaneRelationshipList, BorderLayout.CENTER);
        m.jViewportRelationshipList.add(m.jTableRelationshipList, null);
        m.jScrollPaneRelationshipList.setViewport(m.jViewportRelationshipList);
        m.jViewportTableKeyFieldList.add(m.jTableTableKeyFieldList, null);
        m.jScrollPaneTableKeyFieldList.setViewport(m.jViewportTableKeyFieldList);
        m.jScrollPaneTableKeyFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableKeyFieldList_mouseClicked(e); } });
        m.jTableTableKeyFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableKeyFieldList_mouseClicked(e); } public void mousePressed(MouseEvent e) { m.jTableTableKeyFieldList_mousePressed(e); } public void mouseReleased(MouseEvent e) { m.jTableTableKeyFieldList_mouseReleased(e); } });
        m.jTableTableKeyFieldList.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jTableTableKeyFieldList_mouseDragged(e); } });
        m.jTableTableKeyFieldList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableKeyFieldList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableTableKeyFieldList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jScrollPaneRelationshipList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneRelationshipList_mouseClicked(e); } });
        m.jTableRelationshipList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableRelationshipList_mouseClicked(e); } });
        m.jTableRelationshipList.addFocusListener(new Modeler_FocusListener());
        //(jTableKeyFieldList)//
        m.jTableTableKeyFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableKeyFieldList.setBackground(SystemColor.control);
        m.jTableTableKeyFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableKeyFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.tableModelTableKeyFieldList.addColumn("No.");
        m.tableModelTableKeyFieldList.addColumn(res.getString("S297"));
        m.tableModelTableKeyFieldList.addColumn(res.getString("S542"));
        m.tableModelTableKeyFieldList.addColumn(res.getString("S544"));
        m.tableModelTableKeyFieldList.addColumn("A/D");
        m.column0 = m.jTableTableKeyFieldList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableKeyFieldList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableKeyFieldList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableKeyFieldList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableKeyFieldList.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(200);
        m.column2.setPreferredWidth(250);
        m.column3.setPreferredWidth(150);
        m.column4.setPreferredWidth(50);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableTableKeyFieldList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableKeyFieldList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableRelationshipList)//
        m.jTableRelationshipList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableRelationshipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableRelationshipList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableRelationshipList.setRowHeight(m.TABLE_ROW_HEIGHT_EDITABLE);
        m.jTableRelationshipList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableRelationshipList_valueChanged(e));
        m.jTableRelationshipList.getColumnModel()
            .addColumnModelListener(new TableColumnModelListener() { public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) { m.jTableRelationshipList_columnSelectionChanged(e); } public void columnMarginChanged(javax.swing.event.ChangeEvent e) {} public void columnMoved(javax.swing.event.TableColumnModelEvent e) {} public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {} public void columnAdded(javax.swing.event.TableColumnModelEvent e) {} });
    }
}
