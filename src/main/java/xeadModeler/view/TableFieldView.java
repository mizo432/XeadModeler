package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

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
        initComponents();
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
    private void initComponents() {
        jSplitPaneTableField1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneTableField1.setBorder(null);
        jSplitPaneTableField1.setDividerLocation(190);
        jLabelTableFieldName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableFieldName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableFieldName.setText(res.getString("S577"));
        jLabelTableFieldName.setBounds(new Rectangle(5, 9, 130, 20));
        jTextFieldTableFieldName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableFieldName.setBounds(new Rectangle(140, 6, 250, 25));
        jLabelTableFieldAlias.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableFieldAlias.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableFieldAlias.setText(res.getString("S297"));
        jLabelTableFieldAlias.setBounds(new Rectangle(465, 9, 130, 20));
        jTextFieldTableFieldAlias.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableFieldAlias.setBounds(new Rectangle(600, 6, 300, 25));
        jCheckBoxTableFieldNotNull.setBounds(new Rectangle(920, 8, 120, 25));
        jCheckBoxTableFieldNotNull.setText("Not Null");
        jCheckBoxTableFieldNotNull.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxTableFieldNoUpdate.setBounds(new Rectangle(1060, 8, 130, 25));
        jCheckBoxTableFieldNoUpdate.setText(res.getString("S582"));
        jCheckBoxTableFieldNoUpdate.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxTableFieldShowOnModel.setBounds(new Rectangle(1190, 8, 160, 25));
        jCheckBoxTableFieldShowOnModel.setText(res.getString("S583"));
        jCheckBoxTableFieldShowOnModel.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxTableFieldShowOnModel.setToolTipText(res.getString("S585"));
        jLabelTableFieldDataType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableFieldDataType.setRequestFocusEnabled(true);
        jLabelTableFieldDataType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableFieldDataType.setText(res.getString("S556"));
        jLabelTableFieldDataType.setBounds(new Rectangle(5, 40, 130, 20));
        jTextFieldTableFieldDataType.setBounds(new Rectangle(140, 37, 250, 25));
        jTextFieldTableFieldDataType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableFieldDataType.setBackground(SystemColor.control);
        jTextFieldTableFieldDataType.setEditable(false);
        jButtonTableFieldDataTypeChange.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jButtonTableFieldDataTypeChange.setText("Edit");
        jButtonTableFieldDataTypeChange.setBounds(new Rectangle(390, 36, 70, 27));
        jMenuItemAddDataType.setText(res.getString("S550"));
        jLabelTableFieldAttributeType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableFieldAttributeType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableFieldAttributeType.setText(res.getString("S554"));
        jLabelTableFieldAttributeType.setBounds(new Rectangle(465, 40, 130, 20));
        jPanelTableField3.setBorder(BorderFactory.createEtchedBorder());
        jPanelTableField3.setBounds(new Rectangle(600, 36, 410, 27));
        jPanelTableField3.setLayout(null);
        jRadioButtonTableFieldAttributeTypeNATIVE.setFont(
            new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jRadioButtonTableFieldAttributeTypeNATIVE.setText(res.getString("S558"));
        jRadioButtonTableFieldAttributeTypeNATIVE.setToolTipText(res.getString("S559"));
        jRadioButtonTableFieldAttributeTypeNATIVE.setBounds(new Rectangle(2, 2, 130, 22));
        jRadioButtonTableFieldAttributeTypeINHERITED.setFont(
            new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jRadioButtonTableFieldAttributeTypeINHERITED.setText(res.getString("S561"));
        jRadioButtonTableFieldAttributeTypeINHERITED.setToolTipText(res.getString("S562"));
        jRadioButtonTableFieldAttributeTypeINHERITED.setBounds(new Rectangle(135, 2, 130, 22));
        jRadioButtonTableFieldAttributeTypeDERIVABLE.setFont(
            new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jRadioButtonTableFieldAttributeTypeDERIVABLE.setText(res.getString("S564"));
        jRadioButtonTableFieldAttributeTypeDERIVABLE.setToolTipText(res.getString("S565"));
        jRadioButtonTableFieldAttributeTypeDERIVABLE.setBounds(new Rectangle(268, 2, 130, 22));
        buttonGroupTableFieldAttributeType.add(jRadioButtonTableFieldAttributeTypeNATIVE);
        buttonGroupTableFieldAttributeType.add(jRadioButtonTableFieldAttributeTypeINHERITED);
        buttonGroupTableFieldAttributeType.add(jRadioButtonTableFieldAttributeTypeDERIVABLE);
        jLabelTableFieldDefault.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTableFieldDefault.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableFieldDefault.setText(res.getString("S569"));
        jLabelTableFieldDefault.setBounds(new Rectangle(1010, 40, 100, 20));
        jTextFieldTableFieldDefault.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTableFieldDefault.setBounds(new Rectangle(1115, 37, 70, 25));
        jLabelTableFieldDescriptions.setText(res.getString("S204"));
        jLabelTableFieldDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTableFieldDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTableFieldDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTableFieldDescriptions.setLineWrap(true);
        jPanelTableField4.setLayout(new BorderLayout());
        jPanelTableField4.setBorder(null);
        jPanelTableField4.setMinimumSize(new Dimension(100, 50));
        jPanelTableField5.setBorder(null);
        jPanelTableField5.setPreferredSize(new Dimension(140, 80));
        jPanelTableField5.setLayout(null);
        jPanelTableField6.setLayout(new BorderLayout());
        jPanelTableField6.setBorder(null);
        jLabelTableFieldDescriptions.setBounds(new Rectangle(5, 2, 130, 20));
        jPanelTableField.setLayout(new BorderLayout());
        jTableTableFieldUsageList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableFieldUsageList.setBackground(SystemColor.control);
        jTableTableFieldUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTableFieldUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTableFieldUsageList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        jTabbedPaneTableField.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableFieldForeignUsageList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTableFieldForeignUsageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTableFieldForeignUsageList.setBackground(SystemColor.control);
        jTableTableFieldForeignUsageList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTableFieldForeignUsageList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        jPanelTableField2.setPreferredSize(new Dimension(10, 68));
        jPanelTableField2.setLayout(null);
        jScrollPaneTableField1.setBorder(null);
        jScrollPaneTableField1.getViewport().add(jPanelTableField1);
        jPanelTableField1.setBorder(BorderFactory.createEtchedBorder());
        jPanelTableField1.setMinimumSize(new Dimension(100, 38));
        jPanelTableField1.setPreferredSize(new Dimension(1330, 130));
        jPanelTableField1.setLayout(new BorderLayout());
        jTabbedPaneTableField.addTab(res.getString("S519"), modeler.imageIconFunction,
            jScrollPaneTableFieldUsageList);
        jTabbedPaneTableField.addTab(res.getString("S520"), modeler.imageIconForeignFunction,
            jScrollPaneTableFieldForeignUsageList);
        jSplitPaneTableField1.add(jScrollPaneTableField1, JSplitPane.TOP);
        jSplitPaneTableField1.add(jTabbedPaneTableField, JSplitPane.BOTTOM);
        jPanelTableField.add(jSplitPaneTableField1, BorderLayout.CENTER);
        jPanelTableField1.add(jPanelTableField2, BorderLayout.NORTH);
        jPanelTableField1.add(jPanelTableField4, BorderLayout.CENTER);
        jPanelTableField2.add(jLabelTableFieldName);
        jPanelTableField2.add(jTextFieldTableFieldName);
        jPanelTableField2.add(jLabelTableFieldDataType);
        jPanelTableField2.add(jTextFieldTableFieldDataType);
        jPanelTableField2.add(jLabelTableFieldAlias);
        jPanelTableField2.add(jTextFieldTableFieldAlias);
        jPanelTableField2.add(jButtonTableFieldDataTypeChange);
        jPanelTableField2.add(jLabelTableFieldAttributeType);
        jPanelTableField2.add(jCheckBoxTableFieldNotNull);
        jPanelTableField2.add(jCheckBoxTableFieldNoUpdate);
        jPanelTableField2.add(jCheckBoxTableFieldShowOnModel);
        jPanelTableField2.add(jLabelTableFieldDefault);
        jPanelTableField2.add(jTextFieldTableFieldDefault);
        jPanelTableField2.add(jPanelTableField3);
        jPanelTableField3.add(jRadioButtonTableFieldAttributeTypeDERIVABLE);
        jPanelTableField3.add(jRadioButtonTableFieldAttributeTypeNATIVE);
        jPanelTableField3.add(jRadioButtonTableFieldAttributeTypeINHERITED);
        jPanelTableField4.add(jPanelTableField5, BorderLayout.WEST);
        jPanelTableField4.add(jScrollPaneTableFieldDescriptions, BorderLayout.CENTER);
        jPanelTableField5.add(jLabelTableFieldDescriptions);
        jScrollPaneTableFieldUsageList.getViewport().add(jTableTableFieldUsageList);
        jScrollPaneTableFieldForeignUsageList.getViewport().add(jTableTableFieldForeignUsageList, null);
        jScrollPaneTableFieldDescriptions.getViewport().add(jTextAreaTableFieldDescriptions, null);
        //(jTableTableFieldUsageList)//
        modeler.tableModelTableFieldUsageList.addColumn("No.");
        modeler.tableModelTableFieldUsageList.addColumn("ID");
        modeler.tableModelTableFieldUsageList.addColumn(res.getString("S592"));
        modeler.tableModelTableFieldUsageList.addColumn(res.getString("S750"));
        modeler.tableModelTableFieldUsageList.addColumn("C");
        modeler.tableModelTableFieldUsageList.addColumn("R");
        modeler.tableModelTableFieldUsageList.addColumn("U");
        modeler.tableModelTableFieldUsageList.addColumn("D");
        modeler.tableModelTableFieldUsageList.addColumn(res.getString("S597"));
        column0 = jTableTableFieldUsageList.getColumnModel().getColumn(0);
        column1 = jTableTableFieldUsageList.getColumnModel().getColumn(1);
        column2 = jTableTableFieldUsageList.getColumnModel().getColumn(2);
        column3 = jTableTableFieldUsageList.getColumnModel().getColumn(3);
        column4 = jTableTableFieldUsageList.getColumnModel().getColumn(4);
        column5 = jTableTableFieldUsageList.getColumnModel().getColumn(5);
        column6 = jTableTableFieldUsageList.getColumnModel().getColumn(6);
        column7 = jTableTableFieldUsageList.getColumnModel().getColumn(7);
        column8 = jTableTableFieldUsageList.getColumnModel().getColumn(8);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(150);
        column2.setPreferredWidth(400);
        column3.setPreferredWidth(130);
        column4.setPreferredWidth(30);
        column5.setPreferredWidth(30);
        column6.setPreferredWidth(30);
        column7.setPreferredWidth(30);
        column8.setPreferredWidth(700);
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentCenter);
        column5.setCellRenderer(rendererAlignmentCenter);
        column6.setCellRenderer(rendererAlignmentCenter);
        column7.setCellRenderer(rendererAlignmentCenter);
        column8.setCellRenderer(rendererAlignmentLeft);
        jTableTableFieldUsageList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTableFieldUsageList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableTableFieldForeignUsageList)//
        modeler.tableModelTableFieldForeignUsageList.addColumn("No.");
        modeler.tableModelTableFieldForeignUsageList.addColumn(res.getString("S436"));
        modeler.tableModelTableFieldForeignUsageList.addColumn("ID");
        modeler.tableModelTableFieldForeignUsageList.addColumn(res.getString("S602"));
        modeler.tableModelTableFieldForeignUsageList.addColumn(res.getString("S750"));
        modeler.tableModelTableFieldForeignUsageList.addColumn("C");
        modeler.tableModelTableFieldForeignUsageList.addColumn("R");
        modeler.tableModelTableFieldForeignUsageList.addColumn("U");
        modeler.tableModelTableFieldForeignUsageList.addColumn("D");
        modeler.tableModelTableFieldForeignUsageList.addColumn(res.getString("S607"));
        column0 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(0);
        column1 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(1);
        column2 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(2);
        column3 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(3);
        column4 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(4);
        column5 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(5);
        column6 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(6);
        column7 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(7);
        column8 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(8);
        column9 = jTableTableFieldForeignUsageList.getColumnModel().getColumn(9);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(300);
        column2.setPreferredWidth(150);
        column3.setPreferredWidth(350);
        column4.setPreferredWidth(130);
        column5.setPreferredWidth(30);
        column6.setPreferredWidth(30);
        column7.setPreferredWidth(30);
        column8.setPreferredWidth(30);
        column9.setPreferredWidth(700);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentLeft);
        column5.setCellRenderer(rendererAlignmentCenter);
        column6.setCellRenderer(rendererAlignmentCenter);
        column7.setCellRenderer(rendererAlignmentCenter);
        column8.setCellRenderer(rendererAlignmentCenter);
        column9.setCellRenderer(rendererAlignmentLeft);
        jTableTableFieldForeignUsageList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTableFieldForeignUsageList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
