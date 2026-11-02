package xeadModeler;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import xeadModeler.view.SortableXeadTreeNodeComboBoxModel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

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
    private SortableXeadTreeNodeComboBoxModel sortableComboBoxModelTableType;
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

    /**
     * ModelerのTableコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelTable.setMinimumSize(new Dimension(103, 70));
        m.jPanelTable.setPreferredSize(new Dimension(459, 430));
        m.jPanelTable.setLayout(new BorderLayout());
        m.jSplitPaneTable.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneTable.setBorder(null);
        m.jSplitPaneTable.setDividerLocation(180);
        m.jPanelTable2.setLayout(null);
        m.jPanelTable2.setMinimumSize(new Dimension(100, 20));
        m.jPanelTable2.setPreferredSize(new Dimension(140, 80));
        m.jPanelTable4.setLayout(null);
        m.jPanelTable4.setPreferredSize(new Dimension(10, 99));
        m.jLabelTableName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableName.setText(res.getString("S471"));
        m.jLabelTableName.setBounds(new Rectangle(5, 9, 130, 20));
        m.jTextFieldTableName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableName.setBounds(new Rectangle(0, 6, 350, 25));
        m.jLabelTableSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableSortKey.setText(res.getString("S201"));
        m.jLabelTableSortKey.setBounds(new Rectangle(360, 9, 130, 20));
        m.jTextFieldTableSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableSortKey.setBounds(new Rectangle(495, 6, 245, 25));
        m.jLabelTableAlias.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableAlias.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableAlias.setText(res.getString("S473"));
        m.jLabelTableAlias.setBounds(new Rectangle(745, 9, 100, 20));
        m.jTextFieldTableAlias.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableAlias.setBounds(new Rectangle(850, 6, 200, 25));
        m.jLabelTableType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableType.setRequestFocusEnabled(true);
        m.jLabelTableType.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableType.setText(res.getString("S502"));
        m.jLabelTableType.setBounds(new Rectangle(745, 40, 100, 20));
        m.jComboBoxTableType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jComboBoxTableType.setActionCommand("comboBoxChanged");
        m.jComboBoxTableType.setBounds(new Rectangle(850, 37, 200, 25));
        m.jLabelTablePK.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTablePK.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTablePK.setText(res.getString("S508"));
        m.jLabelTablePK.setBounds(new Rectangle(5, 40, 130, 20));
        m.jTextFieldTablePK.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTablePK.setEditable(false);
        m.jTextFieldTablePK.setBounds(new Rectangle(0, 37, 740, 25));
        m.jLabelTableSynchronize.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableSynchronize.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableSynchronize.setText(res.getString("S510"));
        m.jLabelTableSynchronize.setBounds(new Rectangle(5, 71, 130, 20));
        m.jTextFieldTableSynchronize.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableSynchronize.setBounds(new Rectangle(0, 68, 740, 25));
        m.jTextFieldTableSynchronize.addKeyListener(
            new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTextFieldTableSynchronize_keyReleased(e); } });
        m.jButtonTableSynchronize.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jButtonTableSynchronize.setText(res.getString("S511"));
        m.jButtonTableSynchronize.setBounds(new Rectangle(745, 67, 180, 27));
        m.jButtonTableSynchronize.addActionListener(
            e -> m.jButtonTableSynchronize_actionPerformed(e));
        m.jLabelTableDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableDescriptions.setText(res.getString("S204"));
        m.jLabelTableDescriptions.setBounds(new Rectangle(5, 99, 130, 20));
        m.jTextAreaTableDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaTableDescriptions.setLineWrap(true);
        m.jTextAreaTableCreateStatement.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaTableCreateStatement.setEditable(false);
        m.jTextAreaTableCreateStatement.setBackground(SystemColor.control);
        m.jScrollPaneTable1.setBorder(null);
        m.jScrollPaneTable1.getViewport().add(m.jPanelTable1);
        m.jPanelTable1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelTable1.setMinimumSize(new Dimension(100, 38));
        m.jPanelTable1.setPreferredSize(new Dimension(1200, 140));
        m.jPanelTable1.setLayout(new BorderLayout());
        m.jPanelTable3.setLayout(new BorderLayout());
        m.jTableTableUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableTableUsageList.setBackground(SystemColor.control);
        m.jTableTableUsageList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableUsageList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableUsageList_mouseClicked(e); } });
        m.jTableTableUsageList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableUsageList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jTabbedPaneTable.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableForeignUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableForeignUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableTableForeignUsageList.setBackground(SystemColor.control);
        m.jTableTableForeignUsageList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableForeignUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableForeignUsageList_mouseClicked(e); } });
        m.jTableTableForeignUsageList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableForeignUsageList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jTableTableReferringFileList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableReferringFileList.setBackground(SystemColor.control);
        m.jTableTableReferringFileList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableReferringFileList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableReferringFileList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jPanelTable.add(m.jSplitPaneTable, BorderLayout.CENTER);
        m.jSplitPaneTable.add(m.jTabbedPaneTable, JSplitPane.BOTTOM);
        m.jSplitPaneTable.add(m.jScrollPaneTable1, JSplitPane.TOP);
        m.jPanelTable1.add(m.jPanelTable2, BorderLayout.WEST);
        m.jPanelTable1.add(m.jPanelTable3, BorderLayout.CENTER);
        m.jPanelTable2.add(m.jLabelTableName);
        m.jPanelTable2.add(m.jLabelTablePK);
        m.jPanelTable2.add(m.jLabelTableSynchronize);
        m.jPanelTable2.add(m.jLabelTableDescriptions);
        m.jPanelTable3.add(m.jScrollPaneTableDescriptions, BorderLayout.CENTER);
        m.jPanelTable3.add(m.jPanelTable4, BorderLayout.NORTH);
        m.jPanelTable4.add(m.jTextFieldTableName);
        m.jPanelTable4.add(m.jTextFieldTableAlias);
        m.jPanelTable4.add(m.jTextFieldTablePK);
        m.jPanelTable4.add(m.jTextFieldTableSynchronize);
        m.jPanelTable4.add(m.jButtonTableSynchronize);
        m.jPanelTable4.add(m.jLabelTableSortKey, null);
        m.jPanelTable4.add(m.jLabelTableAlias);
        m.jPanelTable4.add(m.jTextFieldTableSortKey, null);
        m.jPanelTable4.add(m.jLabelTableType, null);
        m.jPanelTable4.add(m.jComboBoxTableType, null);
        m.jTabbedPaneTable.addTab(res.getString("S518"), m.imageIconTable, m.jScrollPaneTableCreateStatement);
        m.jTabbedPaneTable.addTab(res.getString("S519"), m.imageIconFunction, m.jScrollPaneTableUsageList);
        m.jTabbedPaneTable.addTab(res.getString("S520"), m.imageIconForeignFunction,
            m.jScrollPaneTableForeignUsageList);
        m.jTabbedPaneTable.addTab(res.getString("S521"), m.imageIconSystem,
            m.jScrollPaneTableReferringFileList);
        m.jScrollPaneTableForeignUsageList.getViewport().add(m.jTableTableForeignUsageList, null);
        m.jScrollPaneTableForeignUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableForeignUsageList_mouseClicked(e); } });
        m.jScrollPaneTableReferringFileList.getViewport().add(m.jTableTableReferringFileList, null);
        m.jScrollPaneTableReferringFileList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableReferringFileList_mouseClicked(e); } });
        m.jScrollPaneTableUsageList.getViewport().add(m.jTableTableUsageList, null);
        m.jScrollPaneTableUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableUsageList_mouseClicked(e); } });
        m.jScrollPaneTableDescriptions.getViewport().add(m.jTextAreaTableDescriptions, null);
        m.jScrollPaneTableCreateStatement.getViewport().add(m.jTextAreaTableCreateStatement, null);
        //(jTableTableUsageList)//
        m.tableModelTableUsageList.addColumn("No.");
        m.tableModelTableUsageList.addColumn("ID");
        m.tableModelTableUsageList.addColumn(res.getString("S523"));
        m.tableModelTableUsageList.addColumn(res.getString("S639"));
        m.tableModelTableUsageList.addColumn(res.getString("S750"));
        m.tableModelTableUsageList.addColumn("C");
        m.tableModelTableUsageList.addColumn("R");
        m.tableModelTableUsageList.addColumn("U");
        m.tableModelTableUsageList.addColumn("D");
        m.column0 = m.jTableTableUsageList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableUsageList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableUsageList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableUsageList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableUsageList.getColumnModel().getColumn(4);
        m.column5 = m.jTableTableUsageList.getColumnModel().getColumn(5);
        m.column6 = m.jTableTableUsageList.getColumnModel().getColumn(6);
        m.column7 = m.jTableTableUsageList.getColumnModel().getColumn(7);
        m.column8 = m.jTableTableUsageList.getColumnModel().getColumn(8);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(150);
        m.column2.setPreferredWidth(350);
        m.column3.setPreferredWidth(230);
        m.column4.setPreferredWidth(160);
        m.column5.setPreferredWidth(30);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column8.setPreferredWidth(30);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableTableUsageList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableUsageList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(m.jTableTableForeignUsageList)//
        m.tableModelTableForeignUsageList.addColumn("No.");
        m.tableModelTableForeignUsageList.addColumn(res.getString("S436"));
        m.tableModelTableForeignUsageList.addColumn("ID");
        m.tableModelTableForeignUsageList.addColumn(res.getString("S532"));
        m.tableModelTableForeignUsageList.addColumn(res.getString("S639"));
        m.tableModelTableForeignUsageList.addColumn(res.getString("S750"));
        m.tableModelTableForeignUsageList.addColumn("C");
        m.tableModelTableForeignUsageList.addColumn("R");
        m.tableModelTableForeignUsageList.addColumn("U");
        m.tableModelTableForeignUsageList.addColumn("D");
        m.column0 = m.jTableTableForeignUsageList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableForeignUsageList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableForeignUsageList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableForeignUsageList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableForeignUsageList.getColumnModel().getColumn(4);
        m.column5 = m.jTableTableForeignUsageList.getColumnModel().getColumn(5);
        m.column6 = m.jTableTableForeignUsageList.getColumnModel().getColumn(6);
        m.column7 = m.jTableTableForeignUsageList.getColumnModel().getColumn(7);
        m.column8 = m.jTableTableForeignUsageList.getColumnModel().getColumn(8);
        m.column9 = m.jTableTableForeignUsageList.getColumnModel().getColumn(9);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(350);
        m.column2.setPreferredWidth(150);
        m.column3.setPreferredWidth(350);
        m.column4.setPreferredWidth(230);
        m.column5.setPreferredWidth(160);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column8.setPreferredWidth(30);
        m.column9.setPreferredWidth(30);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        m.column5.setCellRenderer(m.rendererAlignmentLeft);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentCenter);
        m.column9.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableTableForeignUsageList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableForeignUsageList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(m.jTableTableReferringFileList)//
        m.tableModelTableReferringFileList.addColumn("No.");
        m.tableModelTableReferringFileList.addColumn(res.getString("S435"));
        m.tableModelTableReferringFileList.addColumn(res.getString("S49"));
        m.tableModelTableReferringFileList.addColumn(res.getString("S436"));
        m.tableModelTableReferringFileList.addColumn(res.getString("S434"));
        m.column0 = m.jTableTableReferringFileList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableReferringFileList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableReferringFileList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableReferringFileList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableReferringFileList.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(250);
        m.column2.setPreferredWidth(400);
        m.column3.setPreferredWidth(400);
        m.column4.setPreferredWidth(90);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableTableReferringFileList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableReferringFileList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
