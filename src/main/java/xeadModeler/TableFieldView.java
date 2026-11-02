package xeadModeler;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * テーブルフィールドノードのビュー
 * テーブルフィールド情報、使用リスト、外部キーリストなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableFieldView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelTableField = new JPanel();
    private final JSplitPane jSplitPaneTableField1 = new JSplitPane();
    private final JScrollPane jScrollPaneTableField1 = new JScrollPane();
    private final JPanel jPanelTableField1 = new JPanel();
    private final JPanel jPanelTableField2 = new JPanel();
    private final JPanel jPanelTableField3 = new JPanel();
    private final JPanel jPanelTableField4 = new JPanel();
    private final JPanel jPanelTableField5 = new JPanel();
    private final JPanel jPanelTableField6 = new JPanel();
    
    // Labels
    private final JLabel jLabelTableFieldName = new JLabel();
    private final JLabel jLabelTableFieldAlias = new JLabel();
    private final JLabel jLabelTableFieldDataType = new JLabel();
    private final JLabel jLabelTableFieldAttributeType = new JLabel();
    private final JLabel jLabelTableFieldDefault = new JLabel();
    private final JLabel jLabelTableFieldDescriptions = new JLabel();
    
    // Fields
    private final KanjiTextField jTextFieldTableFieldName = new KanjiTextField();
    private final KanjiTextField jTextFieldTableFieldAlias = new KanjiTextField();
    private final JTextField jTextFieldTableFieldDataType = new JTextField();
    private final JTextField jTextFieldTableFieldDefault = new JTextField();
    private final JButton jButtonTableFieldDataTypeChange = new JButton();
    private final JMenuItem jMenuItemAddDataType = new JMenuItem();
    
    // Checkboxes
    private final JCheckBox jCheckBoxTableFieldNotNull = new JCheckBox();
    private final JCheckBox jCheckBoxTableFieldNoUpdate = new JCheckBox();
    private final JCheckBox jCheckBoxTableFieldShowOnModel = new JCheckBox();
    
    // Radio buttons
    private final JRadioButton jRadioButtonTableFieldAttributeTypeNATIVE = new JRadioButton();
    private final JRadioButton jRadioButtonTableFieldAttributeTypeINHERITED = new JRadioButton();
    private final JRadioButton jRadioButtonTableFieldAttributeTypeDERIVABLE = new JRadioButton();
    private final ButtonGroup buttonGroupTableFieldAttributeType = new ButtonGroup();
    
    // Descriptions
    private final JScrollPane jScrollPaneTableFieldDescriptions = new JScrollPane();
    private final JTextArea jTextAreaTableFieldDescriptions = new JTextArea();
    
    // Usage list
    private final JTabbedPane jTabbedPaneTableField = new JTabbedPane();
    private final JScrollPane jScrollPaneTableFieldUsageList = new JScrollPane();
    private final JScrollPane jScrollPaneTableFieldForeignUsageList = new JScrollPane();
    
    // Tables
    private final JTable jTableTableFieldUsageList;
    private final JTable jTableTableFieldForeignUsageList;
    
    // State
    private int selectedRow_jTableTableFieldUsageList;
    private int selectedRow_jTableTableFieldForeignUsageList;
    
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
     * テーブルフィールド操作リスナーインターフェース
     */
    public interface TableFieldListener {
        void onTableFieldChanged();
    }
    
    private TableFieldListener tableFieldListener;
    
    public TableFieldView(Modeler modeler, TableFieldListener listener) {
        this.modeler = modeler;
        this.tableFieldListener = listener;
        jTableTableFieldUsageList = modeler.jTableTableFieldUsageList;
        jTableTableFieldForeignUsageList = modeler.jTableTableFieldForeignUsageList;
    }
    
    public JPanel getPanel() {
        return jPanelTableField;
    }
    
    public JTabbedPane getTabbedPane() {
        return jTabbedPaneTableField;
    }
    
    public JTable getUsageList() {
        return jTableTableFieldUsageList;
    }
    
    public JTable getForeignUsageList() {
        return jTableTableFieldForeignUsageList;
    }
    
    /**
     * コンポーネントを初期化します。
     */

    /**
     * ModelerのTableFieldコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jSplitPaneTableField1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneTableField1.setBorder(null);
        m.jSplitPaneTableField1.setDividerLocation(190);
        //jScrollPaneTask1.setBorder(null);
        //jScrollPaneTask1.getViewport().add(jPanelTask1);
        m.jLabelTableFieldName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableFieldName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableFieldName.setText(res.getString("S577"));
        m.jLabelTableFieldName.setBounds(new Rectangle(5, 9, 130, 20));
        m.jTextFieldTableFieldName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableFieldName.setBounds(new Rectangle(140, 6, 250, 25));
        m.jLabelTableFieldAlias.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableFieldAlias.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableFieldAlias.setText(res.getString("S297"));
        m.jLabelTableFieldAlias.setBounds(new Rectangle(465, 9, 130, 20));
        m.jTextFieldTableFieldAlias.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableFieldAlias.setBounds(new Rectangle(600, 6, 300, 25));
        m.jCheckBoxTableFieldNotNull.setBounds(new Rectangle(920, 8, 120, 25));
        m.jCheckBoxTableFieldNotNull.setText("Not Null");
        m.jCheckBoxTableFieldNotNull.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxTableFieldNoUpdate.setBounds(new Rectangle(1060, 8, 130, 25));
        m.jCheckBoxTableFieldNoUpdate.setText(res.getString("S582"));
        m.jCheckBoxTableFieldNoUpdate.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxTableFieldShowOnModel.setBounds(new Rectangle(1190, 8, 160, 25));
        m.jCheckBoxTableFieldShowOnModel.setText(res.getString("S583"));
        m.jCheckBoxTableFieldShowOnModel.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxTableFieldShowOnModel.setToolTipText(res.getString("S585"));
        m.jLabelTableFieldDataType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableFieldDataType.setRequestFocusEnabled(true);
        m.jLabelTableFieldDataType.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableFieldDataType.setText(res.getString("S556"));
        m.jLabelTableFieldDataType.setBounds(new Rectangle(5, 40, 130, 20));
        m.jTextFieldTableFieldDataType.setBounds(new Rectangle(140, 37, 250, 25));
        m.jTextFieldTableFieldDataType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableFieldDataType.setBackground(SystemColor.control);
        m.jTextFieldTableFieldDataType.setEditable(false);
        m.jButtonTableFieldDataTypeChange.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jButtonTableFieldDataTypeChange.setText("Edit");
        m.jButtonTableFieldDataTypeChange.setBounds(new Rectangle(390, 36, 70, 27));
        m.jButtonTableFieldDataTypeChange.addActionListener(
            e -> m.jButtonTableFieldDataTypeChange_actionPerformed(e));
        m.jMenuItemAddDataType.setText(res.getString("S550"));
        m.jMenuItemAddDataType.addActionListener(e -> m.jMenuItemAddDataType_actionPerformed(e));
        m.jLabelTableFieldAttributeType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableFieldAttributeType.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableFieldAttributeType.setText(res.getString("S554"));
        m.jLabelTableFieldAttributeType.setBounds(new Rectangle(465, 40, 130, 20));
        m.jPanelTableField3.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelTableField3.setBounds(new Rectangle(600, 36, 410, 27));
        m.jPanelTableField3.setLayout(null);
        m.jRadioButtonTableFieldAttributeTypeNATIVE.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jRadioButtonTableFieldAttributeTypeNATIVE.setText(res.getString("S558"));
        m.jRadioButtonTableFieldAttributeTypeNATIVE.setToolTipText(res.getString("S559"));
        m.jRadioButtonTableFieldAttributeTypeNATIVE.setBounds(new Rectangle(2, 2, 130, 22));
        m.jRadioButtonTableFieldAttributeTypeINHERITED.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jRadioButtonTableFieldAttributeTypeINHERITED.setText(res.getString("S561"));
        m.jRadioButtonTableFieldAttributeTypeINHERITED.setToolTipText(res.getString("S562"));
        m.jRadioButtonTableFieldAttributeTypeINHERITED.setBounds(new Rectangle(135, 2, 130, 22));
        m.jRadioButtonTableFieldAttributeTypeDERIVABLE.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jRadioButtonTableFieldAttributeTypeDERIVABLE.setText(res.getString("S564"));
        m.jRadioButtonTableFieldAttributeTypeDERIVABLE.setToolTipText(res.getString("S565"));
        m.jRadioButtonTableFieldAttributeTypeDERIVABLE.setBounds(new Rectangle(268, 2, 130, 22));
        m.buttonGroupTableFieldAttributeType.add(m.jRadioButtonTableFieldAttributeTypeNATIVE);
        m.buttonGroupTableFieldAttributeType.add(m.jRadioButtonTableFieldAttributeTypeINHERITED);
        m.buttonGroupTableFieldAttributeType.add(m.jRadioButtonTableFieldAttributeTypeDERIVABLE);
        m.jLabelTableFieldDefault.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelTableFieldDefault.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableFieldDefault.setText(res.getString("S569"));
        m.jLabelTableFieldDefault.setBounds(new Rectangle(1010, 40, 100, 20));
        m.jTextFieldTableFieldDefault.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldTableFieldDefault.setBounds(new Rectangle(1115, 37, 70, 25));
        m.jLabelTableFieldDescriptions.setText(res.getString("S204"));
        m.jLabelTableFieldDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelTableFieldDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaTableFieldDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaTableFieldDescriptions.setLineWrap(true);
        m.jPanelTableField4.setLayout(new BorderLayout());
        m.jPanelTableField4.setBorder(null);
        m.jPanelTableField4.setMinimumSize(new Dimension(100, 50));
        m.jPanelTableField5.setBorder(null);
        m.jPanelTableField5.setPreferredSize(new Dimension(140, 80));
        m.jPanelTableField5.setLayout(null);
        m.jPanelTableField6.setLayout(new BorderLayout());
        m.jPanelTableField6.setBorder(null);
        m.jLabelTableFieldDescriptions.setBounds(new Rectangle(5, 2, 130, 20));
        m.jPanelTableField.setLayout(new BorderLayout());
        m.jTableTableFieldUsageList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableFieldUsageList.setBackground(SystemColor.control);
        m.jTableTableFieldUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableFieldUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableTableFieldUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableFieldUsageList_mouseClicked(e); } });
        m.jTableTableFieldUsageList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableFieldUsageList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jTabbedPaneTableField.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableFieldForeignUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableTableFieldForeignUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableTableFieldForeignUsageList.setBackground(SystemColor.control);
        m.jTableTableFieldForeignUsageList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableTableFieldForeignUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTableFieldForeignUsageList_mouseClicked(e); } });
        m.jTableTableFieldForeignUsageList.addFocusListener(new Modeler_FocusListener());
        m.jTableTableFieldForeignUsageList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.jPanelTableField2.setPreferredSize(new Dimension(10, 68));
        m.jPanelTableField2.setLayout(null);
        m.jScrollPaneTableField1.setBorder(null);
        m.jScrollPaneTableField1.getViewport().add(m.jPanelTableField1);
        m.jPanelTableField1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelTableField1.setMinimumSize(new Dimension(100, 38));
        m.jPanelTableField1.setPreferredSize(new Dimension(1330, 130));
        m.jPanelTableField1.setLayout(new BorderLayout());
        m.jTabbedPaneTableField.addTab(res.getString("S519"), m.imageIconFunction,
            m.jScrollPaneTableFieldUsageList);
        m.jTabbedPaneTableField.addTab(res.getString("S520"), m.imageIconForeignFunction,
            m.jScrollPaneTableFieldForeignUsageList);
        m.jSplitPaneTableField1.add(m.jScrollPaneTableField1, JSplitPane.TOP);
        m.jSplitPaneTableField1.add(m.jTabbedPaneTableField, JSplitPane.BOTTOM);
        m.jPanelTableField.add(m.jSplitPaneTableField1, BorderLayout.CENTER);
        m.jPanelTableField1.add(m.jPanelTableField2, BorderLayout.NORTH);
        m.jPanelTableField1.add(m.jPanelTableField4, BorderLayout.CENTER);
        m.jPanelTableField2.add(m.jLabelTableFieldName);
        m.jPanelTableField2.add(m.jTextFieldTableFieldName);
        m.jPanelTableField2.add(m.jLabelTableFieldDataType);
        m.jPanelTableField2.add(m.jTextFieldTableFieldDataType);
        m.jPanelTableField2.add(m.jLabelTableFieldAlias);
        m.jPanelTableField2.add(m.jTextFieldTableFieldAlias);
        m.jPanelTableField2.add(m.jButtonTableFieldDataTypeChange);
        m.jPanelTableField2.add(m.jLabelTableFieldAttributeType);
        m.jPanelTableField2.add(m.jCheckBoxTableFieldNotNull);
        m.jPanelTableField2.add(m.jCheckBoxTableFieldNoUpdate);
        m.jPanelTableField2.add(m.jCheckBoxTableFieldShowOnModel);
        m.jPanelTableField2.add(m.jLabelTableFieldDefault);
        m.jPanelTableField2.add(m.jTextFieldTableFieldDefault);
        m.jPanelTableField2.add(m.jPanelTableField3);
        m.jPanelTableField3.add(m.jRadioButtonTableFieldAttributeTypeDERIVABLE);
        m.jPanelTableField3.add(m.jRadioButtonTableFieldAttributeTypeNATIVE);
        m.jPanelTableField3.add(m.jRadioButtonTableFieldAttributeTypeINHERITED);
        m.jPanelTableField4.add(m.jPanelTableField5, BorderLayout.WEST);
        m.jPanelTableField4.add(m.jScrollPaneTableFieldDescriptions, BorderLayout.CENTER);
        m.jPanelTableField5.add(m.jLabelTableFieldDescriptions);
        m.jScrollPaneTableFieldUsageList.getViewport().add(m.jTableTableFieldUsageList);
        m.jScrollPaneTableFieldUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableFieldUsageList_mouseClicked(e); } });
        m.jScrollPaneTableFieldForeignUsageList.getViewport().add(m.jTableTableFieldForeignUsageList, null);
        m.jScrollPaneTableFieldForeignUsageList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTableFieldForeignUsageList_mouseClicked(e); } });
        m.jScrollPaneTableFieldDescriptions.getViewport().add(m.jTextAreaTableFieldDescriptions, null);
        //(jTableTableFieldUsageList)//
        m.tableModelTableFieldUsageList.addColumn("No.");
        m.tableModelTableFieldUsageList.addColumn("ID");
        m.tableModelTableFieldUsageList.addColumn(res.getString("S592"));
        m.tableModelTableFieldUsageList.addColumn(res.getString("S750"));
        m.tableModelTableFieldUsageList.addColumn("C");
        m.tableModelTableFieldUsageList.addColumn("R");
        m.tableModelTableFieldUsageList.addColumn("U");
        m.tableModelTableFieldUsageList.addColumn("D");
        m.tableModelTableFieldUsageList.addColumn(res.getString("S597"));
        m.column0 = m.jTableTableFieldUsageList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableFieldUsageList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableFieldUsageList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableFieldUsageList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableFieldUsageList.getColumnModel().getColumn(4);
        m.column5 = m.jTableTableFieldUsageList.getColumnModel().getColumn(5);
        m.column6 = m.jTableTableFieldUsageList.getColumnModel().getColumn(6);
        m.column7 = m.jTableTableFieldUsageList.getColumnModel().getColumn(7);
        m.column8 = m.jTableTableFieldUsageList.getColumnModel().getColumn(8);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(150);
        m.column2.setPreferredWidth(400);
        m.column3.setPreferredWidth(130);
        m.column4.setPreferredWidth(30);
        m.column5.setPreferredWidth(30);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column8.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableTableFieldUsageList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableFieldUsageList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableTableFieldForeignUsageList)//
        m.tableModelTableFieldForeignUsageList.addColumn("No.");
        m.tableModelTableFieldForeignUsageList.addColumn(res.getString("S436"));
        m.tableModelTableFieldForeignUsageList.addColumn("ID");
        m.tableModelTableFieldForeignUsageList.addColumn(res.getString("S602"));
        m.tableModelTableFieldForeignUsageList.addColumn(res.getString("S750"));
        m.tableModelTableFieldForeignUsageList.addColumn("C");
        m.tableModelTableFieldForeignUsageList.addColumn("R");
        m.tableModelTableFieldForeignUsageList.addColumn("U");
        m.tableModelTableFieldForeignUsageList.addColumn("D");
        m.tableModelTableFieldForeignUsageList.addColumn(res.getString("S607"));
        m.column0 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(0);
        m.column1 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(1);
        m.column2 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(2);
        m.column3 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(3);
        m.column4 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(4);
        m.column5 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(5);
        m.column6 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(6);
        m.column7 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(7);
        m.column8 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(8);
        m.column9 = m.jTableTableFieldForeignUsageList.getColumnModel().getColumn(9);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(300);
        m.column2.setPreferredWidth(150);
        m.column3.setPreferredWidth(350);
        m.column4.setPreferredWidth(130);
        m.column5.setPreferredWidth(30);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column8.setPreferredWidth(30);
        m.column9.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentCenter);
        m.column9.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableTableFieldForeignUsageList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableTableFieldForeignUsageList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
