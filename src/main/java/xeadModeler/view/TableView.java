package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * テーブルノードのビュー
 * テーブル情報、使用リスト、外部使用リスト、参照ファイルリストなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelTable = new JPanel();
    private final JSplitPane jSplitPaneTable = new JSplitPane();
    private final JScrollPane jScrollPaneTable1 = new JScrollPane();
    private final JPanel jPanelTable1 = new JPanel();
    private final JPanel jPanelTable2 = new JPanel();
    private final JPanel jPanelTable3 = new JPanel();
    private final JPanel jPanelTable4 = new JPanel();
    
    // Labels
    private final JLabel jLabelTableName = new JLabel();
    private final JLabel jLabelTableSortKey = new JLabel();
    private final JLabel jLabelTableAlias = new JLabel();
    private final JLabel jLabelTableType = new JLabel();
    private final JLabel jLabelTablePK = new JLabel();
    private final JLabel jLabelTableSynchronize = new JLabel();
    private final JLabel jLabelTableDescriptions = new JLabel();
    
    // Fields
    private final KanjiTextField jTextFieldTableName = new KanjiTextField();
    private final JTextField jTextFieldTableSortKey = new JTextField();
    private final JTextField jTextFieldTableAlias = new JTextField();
    private final JTextField jTextFieldTablePK = new JTextField();
    private final JTextField jTextFieldTableSynchronize = new JTextField();
    private final JButton jButtonTableSynchronize = new JButton();
    
    // Table type combo
    private Modeler.SortableXeadTreeNodeComboBoxModel sortableComboBoxModelTableType;
    private final JComboBox jComboBoxTableType = new JComboBox();
    
    // Descriptions
    private final JScrollPane jScrollPaneTableDescriptions = new JScrollPane();
    private final JTextArea jTextAreaTableDescriptions = new JTextArea();
    private final JScrollPane jScrollPaneTableCreateStatement = new JScrollPane();
    private final JTextArea jTextAreaTableCreateStatement = new JTextArea();
    
    // Usage list
    private final JTabbedPane jTabbedPaneTable = new JTabbedPane();
    private final JScrollPane jScrollPaneTableUsageList = new JScrollPane();
    private final JScrollPane jScrollPaneTableForeignUsageList = new JScrollPane();
    private final JScrollPane jScrollPaneTableReferringFileList = new JScrollPane();
    
    // Tables
    private final JTable jTableTableUsageList;
    private final JTable jTableTableForeignUsageList;
    private final JTable jTableTableReferringFileList;
    
    // State
    private int selectedRow_jTableTableUsageList;
    private int selectedRow_jTableTableForeignUsageList;
    
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
    private javax.swing.table.TableColumn column5;
    private javax.swing.table.TableColumn column6;
    private javax.swing.table.TableColumn column7;
    private javax.swing.table.TableColumn column8;
    private javax.swing.table.TableColumn column9;
    
    private Modeler modeler;
    
    /**
     * テーブル操作リスナーインターフェース
     */
    public interface TableListener {
        void onTableChanged();
    }
    
    private TableListener tableListener;
    
    public TableView(Modeler modeler, TableListener listener) {
        this.modeler = modeler;
        this.tableListener = listener;
        jTableTableUsageList = modeler.jTableTableUsageList;
        jTableTableForeignUsageList = modeler.jTableTableForeignUsageList;
        jTableTableReferringFileList = modeler.jTableTableReferringFileList;
        sortableComboBoxModelTableType = modeler.sortableComboBoxModelTableType;
        jComboBoxTableType.setModel(sortableComboBoxModelTableType);
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelTable;
    }
    
    public JTabbedPane getTabbedPane() {
        return jTabbedPaneTable;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jPanelTable.setMinimumSize(new Dimension(103, 70));
        jPanelTable.setPreferredSize(new Dimension(459, 430));
        jPanelTable.setLayout(new BorderLayout());
        jSplitPaneTable.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneTable.setBorder(null);
        jSplitPaneTable.setDividerLocation(180);
        jPanelTable2.setLayout(null);
        jPanelTable2.setMinimumSize(new Dimension(100, 20));
        jPanelTable2.setPreferredSize(new Dimension(140, 80));
        jPanelTable4.setLayout(null);
        jPanelTable4.setPreferredSize(new Dimension(10, 99));
        jLabelTableName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableName.setText(res.getString("S471"));
        jLabelTableName.setBounds(new Rectangle(5, 9, 130, 20));
        jTextFieldTableName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableName.setBounds(new Rectangle(0, 6, 350, 25));
        jLabelTableSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableSortKey.setText(res.getString("S201"));
        jLabelTableSortKey.setBounds(new Rectangle(360, 9, 130, 20));
        jTextFieldTableSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableSortKey.setBounds(new Rectangle(495, 6, 245, 25));
        jLabelTableAlias.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableAlias.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableAlias.setText(res.getString("S473"));
        jLabelTableAlias.setBounds(new Rectangle(745, 9, 100, 20));
        jTextFieldTableAlias.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableAlias.setBounds(new Rectangle(850, 6, 200, 25));
        jLabelTableType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableType.setRequestFocusEnabled(true);
        jLabelTableType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableType.setText(res.getString("S502"));
        jLabelTableType.setBounds(new Rectangle(745, 40, 100, 20));
        jComboBoxTableType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxTableType.setActionCommand("comboBoxChanged");
        jComboBoxTableType.setBounds(new Rectangle(850, 37, 200, 25));
        jLabelTablePK.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTablePK.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTablePK.setText(res.getString("S508"));
        jLabelTablePK.setBounds(new Rectangle(5, 40, 130, 20));
        jTextFieldTablePK.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTablePK.setEditable(false);
        jTextFieldTablePK.setBounds(new Rectangle(0, 37, 740, 25));
        jLabelTableSynchronize.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableSynchronize.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableSynchronize.setText(res.getString("S510"));
        jLabelTableSynchronize.setBounds(new Rectangle(5, 71, 130, 20));
        jTextFieldTableSynchronize.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableSynchronize.setBounds(new Rectangle(0, 68, 740, 25));
        jButtonTableSynchronize.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jButtonTableSynchronize.setText(res.getString("S511"));
        jButtonTableSynchronize.setBounds(new Rectangle(745, 67, 180, 27));
        jLabelTableDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableDescriptions.setText(res.getString("S204"));
        jLabelTableDescriptions.setBounds(new Rectangle(5, 99, 130, 20));
        jTextAreaTableDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTableDescriptions.setLineWrap(true);
        jTextAreaTableCreateStatement.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTableCreateStatement.setEditable(false);
        jTextAreaTableCreateStatement.setBackground(SystemColor.control);
        jScrollPaneTable1.setBorder(null);
        jScrollPaneTable1.getViewport().add(jPanelTable1);
        jPanelTable1.setBorder(BorderFactory.createEtchedBorder());
        jPanelTable1.setMinimumSize(new Dimension(100, 38));
        jPanelTable1.setPreferredSize(new Dimension(1200, 140));
        jPanelTable1.setLayout(new BorderLayout());
        jPanelTable3.setLayout(new BorderLayout());
        jTableTableUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTableUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTableUsageList.setBackground(SystemColor.control);
        jTableTableUsageList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableUsageList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        jTabbedPaneTable.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableForeignUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTableForeignUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTableForeignUsageList.setBackground(SystemColor.control);
        jTableTableForeignUsageList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableForeignUsageList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        jTableTableReferringFileList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTableReferringFileList.setBackground(SystemColor.control);
        jTableTableReferringFileList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableReferringFileList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        jPanelTable.add(jSplitPaneTable, BorderLayout.CENTER);
        jSplitPaneTable.add(jTabbedPaneTable, JSplitPane.BOTTOM);
        jSplitPaneTable.add(jScrollPaneTable1, JSplitPane.TOP);
        jPanelTable1.add(jPanelTable2, BorderLayout.WEST);
        jPanelTable1.add(jPanelTable3, BorderLayout.CENTER);
        jPanelTable2.add(jLabelTableName);
        jPanelTable2.add(jLabelTablePK);
        jPanelTable2.add(jLabelTableSynchronize);
        jPanelTable2.add(jLabelTableDescriptions);
        jPanelTable3.add(jScrollPaneTableDescriptions, BorderLayout.CENTER);
        jPanelTable3.add(jPanelTable4, BorderLayout.NORTH);
        jPanelTable4.add(jTextFieldTableName);
        jPanelTable4.add(jTextFieldTableAlias);
        jPanelTable4.add(jTextFieldTablePK);
        jPanelTable4.add(jTextFieldTableSynchronize);
        jPanelTable4.add(jButtonTableSynchronize);
        jPanelTable4.add(jLabelTableSortKey, null);
        jPanelTable4.add(jLabelTableAlias);
        jPanelTable4.add(jTextFieldTableSortKey, null);
        jPanelTable4.add(jLabelTableType, null);
        jPanelTable4.add(jComboBoxTableType, null);
        jTabbedPaneTable.addTab(res.getString("S518"), modeler.imageIconTable, jScrollPaneTableCreateStatement);
        jTabbedPaneTable.addTab(res.getString("S519"), modeler.imageIconFunction, jScrollPaneTableUsageList);
        jTabbedPaneTable.addTab(res.getString("S520"), modeler.imageIconForeignFunction,
            jScrollPaneTableForeignUsageList);
        jTabbedPaneTable.addTab(res.getString("S521"), modeler.imageIconSystem,
            jScrollPaneTableReferringFileList);
        jScrollPaneTableForeignUsageList.getViewport().add(jTableTableForeignUsageList, null);
        jScrollPaneTableReferringFileList.getViewport().add(jTableTableReferringFileList, null);
        jScrollPaneTableUsageList.getViewport().add(jTableTableUsageList, null);
        jScrollPaneTableDescriptions.getViewport().add(jTextAreaTableDescriptions, null);
        jScrollPaneTableCreateStatement.getViewport().add(jTextAreaTableCreateStatement, null);
        //(jTableTableUsageList)//
        modeler.tableModelTableUsageList.addColumn("No.");
        modeler.tableModelTableUsageList.addColumn("ID");
        modeler.tableModelTableUsageList.addColumn(res.getString("S523"));
        modeler.tableModelTableUsageList.addColumn(res.getString("S639"));
        modeler.tableModelTableUsageList.addColumn(res.getString("S750"));
        modeler.tableModelTableUsageList.addColumn("C");
        modeler.tableModelTableUsageList.addColumn("R");
        modeler.tableModelTableUsageList.addColumn("U");
        modeler.tableModelTableUsageList.addColumn("D");
        column0 = jTableTableUsageList.getColumnModel().getColumn(0);
        column1 = jTableTableUsageList.getColumnModel().getColumn(1);
        column2 = jTableTableUsageList.getColumnModel().getColumn(2);
        column3 = jTableTableUsageList.getColumnModel().getColumn(3);
        column4 = jTableTableUsageList.getColumnModel().getColumn(4);
        column5 = jTableTableUsageList.getColumnModel().getColumn(5);
        column6 = jTableTableUsageList.getColumnModel().getColumn(6);
        column7 = jTableTableUsageList.getColumnModel().getColumn(7);
        column8 = jTableTableUsageList.getColumnModel().getColumn(8);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(150);
        column2.setPreferredWidth(350);
        column3.setPreferredWidth(230);
        column4.setPreferredWidth(160);
        column5.setPreferredWidth(30);
        column6.setPreferredWidth(30);
        column7.setPreferredWidth(30);
        column8.setPreferredWidth(30);
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentLeft);
        column5.setCellRenderer(rendererAlignmentCenter);
        column6.setCellRenderer(rendererAlignmentCenter);
        column7.setCellRenderer(rendererAlignmentCenter);
        column8.setCellRenderer(rendererAlignmentCenter);
        jTableTableUsageList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTableUsageList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableTableForeignUsageList)//
        modeler.tableModelTableForeignUsageList.addColumn("No.");
        modeler.tableModelTableForeignUsageList.addColumn(res.getString("S436"));
        modeler.tableModelTableForeignUsageList.addColumn("ID");
        modeler.tableModelTableForeignUsageList.addColumn(res.getString("S532"));
        modeler.tableModelTableForeignUsageList.addColumn(res.getString("S639"));
        modeler.tableModelTableForeignUsageList.addColumn(res.getString("S750"));
        modeler.tableModelTableForeignUsageList.addColumn("C");
        modeler.tableModelTableForeignUsageList.addColumn("R");
        modeler.tableModelTableForeignUsageList.addColumn("U");
        modeler.tableModelTableForeignUsageList.addColumn("D");
        column0 = jTableTableForeignUsageList.getColumnModel().getColumn(0);
        column1 = jTableTableForeignUsageList.getColumnModel().getColumn(1);
        column2 = jTableTableForeignUsageList.getColumnModel().getColumn(2);
        column3 = jTableTableForeignUsageList.getColumnModel().getColumn(3);
        column4 = jTableTableForeignUsageList.getColumnModel().getColumn(4);
        column5 = jTableTableForeignUsageList.getColumnModel().getColumn(5);
        column6 = jTableTableForeignUsageList.getColumnModel().getColumn(6);
        column7 = jTableTableForeignUsageList.getColumnModel().getColumn(7);
        column8 = jTableTableForeignUsageList.getColumnModel().getColumn(8);
        column9 = jTableTableForeignUsageList.getColumnModel().getColumn(9);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(350);
        column2.setPreferredWidth(150);
        column3.setPreferredWidth(350);
        column4.setPreferredWidth(230);
        column5.setPreferredWidth(160);
        column6.setPreferredWidth(30);
        column7.setPreferredWidth(30);
        column8.setPreferredWidth(30);
        column9.setPreferredWidth(30);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentLeft);
        column5.setCellRenderer(rendererAlignmentLeft);
        column6.setCellRenderer(rendererAlignmentCenter);
        column7.setCellRenderer(rendererAlignmentCenter);
        column8.setCellRenderer(rendererAlignmentCenter);
        column9.setCellRenderer(rendererAlignmentCenter);
        jTableTableForeignUsageList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTableForeignUsageList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableTableReferringFileList)//
        modeler.tableModelTableReferringFileList.addColumn("No.");
        modeler.tableModelTableReferringFileList.addColumn(res.getString("S435"));
        modeler.tableModelTableReferringFileList.addColumn(res.getString("S49"));
        modeler.tableModelTableReferringFileList.addColumn(res.getString("S436"));
        modeler.tableModelTableReferringFileList.addColumn(res.getString("S434"));
        column0 = jTableTableReferringFileList.getColumnModel().getColumn(0);
        column1 = jTableTableReferringFileList.getColumnModel().getColumn(1);
        column2 = jTableTableReferringFileList.getColumnModel().getColumn(2);
        column3 = jTableTableReferringFileList.getColumnModel().getColumn(3);
        column4 = jTableTableReferringFileList.getColumnModel().getColumn(4);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(250);
        column2.setPreferredWidth(400);
        column3.setPreferredWidth(400);
        column4.setPreferredWidth(90);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentCenter);
        jTableTableReferringFileList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTableReferringFileList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
