package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

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
        initComponents();
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
    private void initComponents() {
        jPanelTableKey5.setBackground(SystemColor.control);
        jScrollPaneTableKeyFieldList.getViewport().setBackground(SystemColor.control);
        jScrollPaneRelationshipList.getViewport().setBackground(SystemColor.control);
        jPanelTableKey.setLayout(new BorderLayout());
        jPanelTableKey1.setBorder(BorderFactory.createEtchedBorder());
        jPanelTableKey1.setMinimumSize(new Dimension(10, 10));
        jPanelTableKey1.setPreferredSize(new Dimension(10, 43));
        jPanelTableKey1.setLayout(null);
        jLabelKeyType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelKeyType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelKeyType.setText(res.getString("S616"));
        jLabelKeyType.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldTableKeyType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableKeyType.setEditable(false);
        jTextFieldTableKeyType.setBounds(new Rectangle(140, 10, 200, 25));
        jLabelTableKeySortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableKeySortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableKeySortKey.setText(res.getString("S540"));
        jLabelTableKeySortKey.setBounds(new Rectangle(350, 11, 130, 15));
        jTextFieldTableKeySortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableKeySortKey.setBounds(new Rectangle(485, 10, 90, 25));
        jScrollPaneTableKeyFieldList.setBorder(null);
        jLabelTableKeyFieldList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableKeyFieldList.setText(res.getString("S613"));
        jLabelTableKeyFieldList.setBounds(new Rectangle(5, 1, 130, 20));
        jLabelRelationshipList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelRelationshipList.setText(res.getString("S624"));
        jLabelRelationshipList.setBounds(new Rectangle(5, 1, 130, 20));
        jScrollPaneRelationshipList.setBorder(null);
        jSplitPaneTableKey.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneTableKey.setBorder(null);
        jSplitPaneTableKey.setDividerLocation(300);
        jPanelTableKey2.setLayout(new BorderLayout());
        jPanelTableKey2.setBorder(BorderFactory.createEtchedBorder());
        jPanelTableKey3.setBorder(BorderFactory.createEtchedBorder());
        jPanelTableKey3.setPreferredSize(new Dimension(10, 300));
        jPanelTableKey3.setLayout(new BorderLayout());
        jPanelTableKey4.setLayout(null);
        jPanelTableKey4.setPreferredSize(new Dimension(1, 22));
        jPanelTableKey5.setPreferredSize(new Dimension(1, 22));
        jPanelTableKey5.setLayout(null);
        jPanelTableKey6.setLayout(new BorderLayout());
        jSplitPaneTableKey.add(jPanelTableKey2, JSplitPane.TOP);
        jSplitPaneTableKey.add(jPanelTableKey3, JSplitPane.BOTTOM);
        jPanelTableKey.add(jPanelTableKey1, BorderLayout.NORTH);
        jPanelTableKey1.add(jLabelKeyType, null);
        jPanelTableKey1.add(jTextFieldTableKeyType, null);
        jPanelTableKey1.add(jLabelTableKeySortKey, null);
        jPanelTableKey1.add(jTextFieldTableKeySortKey, null);
        jPanelTableKey.add(jPanelTableKey6, BorderLayout.CENTER);
        jPanelTableKey6.add(jSplitPaneTableKey, BorderLayout.CENTER);
        jPanelTableKey2.add(jPanelTableKey4, BorderLayout.NORTH);
        jPanelTableKey4.add(jLabelTableKeyFieldList, null);
        jPanelTableKey2.add(jScrollPaneTableKeyFieldList, BorderLayout.CENTER);
        jPanelTableKey3.add(jPanelTableKey5, BorderLayout.NORTH);
        jPanelTableKey5.add(jLabelRelationshipList, null);
        jPanelTableKey3.add(jScrollPaneRelationshipList, BorderLayout.CENTER);
        jViewportRelationshipList.add(jTableRelationshipList, null);
        jScrollPaneRelationshipList.setViewport(jViewportRelationshipList);
        jViewportTableKeyFieldList.add(jTableTableKeyFieldList, null);
        jScrollPaneTableKeyFieldList.setViewport(jViewportTableKeyFieldList);
        jTableTableKeyFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        //(jTableKeyFieldList)//
        jTableTableKeyFieldList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableKeyFieldList.setBackground(SystemColor.control);
        jTableTableKeyFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTableKeyFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modeler.tableModelTableKeyFieldList.addColumn("No.");
        modeler.tableModelTableKeyFieldList.addColumn(res.getString("S297"));
        modeler.tableModelTableKeyFieldList.addColumn(res.getString("S542"));
        modeler.tableModelTableKeyFieldList.addColumn(res.getString("S544"));
        modeler.tableModelTableKeyFieldList.addColumn("A/D");
        column0 = jTableTableKeyFieldList.getColumnModel().getColumn(0);
        column1 = jTableTableKeyFieldList.getColumnModel().getColumn(1);
        column2 = jTableTableKeyFieldList.getColumnModel().getColumn(2);
        column3 = jTableTableKeyFieldList.getColumnModel().getColumn(3);
        column4 = jTableTableKeyFieldList.getColumnModel().getColumn(4);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(200);
        column2.setPreferredWidth(250);
        column3.setPreferredWidth(150);
        column4.setPreferredWidth(50);
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentCenter);
        jTableTableKeyFieldList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTableKeyFieldList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableRelationshipList)//
        jTableRelationshipList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableRelationshipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableRelationshipList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableRelationshipList.setRowHeight(Modeler.TABLE_ROW_HEIGHT_EDITABLE);
    }
}
