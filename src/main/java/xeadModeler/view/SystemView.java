package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;
import xeadModeler.model.json.DataType;
import xeadModeler.model.json.FunctionType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * システムノードのビュー
 * システム情報、部門リスト、タスクタイプリスト、テーブルタイプリスト、データタイプリスト、
 * 関数タイプリスト、用語リスト、メンテナンスログなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SystemView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelSystem = new JPanel();
    private final JPanel jPanelSystem6 = new JPanel();
    private final JLabel jLabelSystemName = new JLabel();
    private final KanjiTextField jTextFieldSystemName = new KanjiTextField();
    private final JLabel jLabelSystemVersion = new JLabel();
    private final JTextField jTextFieldSystemVersion = new JTextField();
    private final JLabel jLabelSystemReferingModelFiles = new JLabel();
    private final JTextField jTextFieldSystemReferingModelFiles = new JTextField();
    private final JLabel jLabelSystemDocFileFormat = new JLabel();
    private final JTextField jTextFieldSystemDocFileFormat = new JTextField();
    private final JTabbedPane jTabbedPaneSystem = new JTabbedPane();
    private final JScrollPane jScrollPaneSystemDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemDescriptions = new JTextArea();
    
    // SystemDepartmentList
    private int selectedRow_jTableSystemDepartmentList;
    private final JSplitPane jSplitPaneSystemDepartment = new JSplitPane();
    private final JScrollPane jScrollPaneSystemDepartmentList = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemDepartmentList = new TableModelReadOnlyList();
    private final JTable jTableSystemDepartmentList = new JTable(tableModelSystemDepartmentList);
    private final JPanel jPanelSystemDepartment = new JPanel();
    private final JPanel jPanelSystemDepartment1 = new JPanel();
    private final JScrollPane jScrollPaneSystemDepartmentWhereUsedList = new JScrollPane();
    private final JLabel jLabelSystemDepartmentName = new JLabel();
    private final KanjiTextField jTextFieldSystemDepartmentName = new KanjiTextField();
    private final JLabel jLabelSystemDepartmentSortKey = new JLabel();
    private final JTextField jTextFieldSystemDepartmentSortKey = new JTextField();
    private final JLabel jLabelSystemDepartmentDescriptions = new JLabel();
    private final JScrollPane jScrollPaneSystemDepartmentDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemDepartmentDescriptions = new JTextArea();
    private final TableModelReadOnlyList tableModelSystemDepartmentWhereUsedList = new TableModelReadOnlyList();
    private final JTable jTableSystemDepartmentWhereUsedList = new JTable(tableModelSystemDepartmentWhereUsedList);
    
    // SystemTaskTypeList
    private int selectedRow_jTableSystemTaskTypeList;
    private final JSplitPane jSplitPaneSystemTaskType = new JSplitPane();
    private final JScrollPane jScrollPaneSystemTaskTypeList = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemTaskTypeList = new TableModelReadOnlyList();
    private final JTable jTableSystemTaskTypeList = new JTable(tableModelSystemTaskTypeList);
    private final JPanel jPanelSystemTaskType = new JPanel();
    private final JPanel jPanelSystemTaskType1 = new JPanel();
    private final JScrollPane jScrollPaneSystemTaskTypeWhereUsedList = new JScrollPane();
    private final JLabel jLabelSystemTaskTypeName = new JLabel();
    private final KanjiTextField jTextFieldSystemTaskTypeName = new KanjiTextField();
    private final JLabel jLabelSystemTaskTypeSortKey = new JLabel();
    private final JTextField jTextFieldSystemTaskTypeSortKey = new JTextField();
    private final JLabel jLabelSystemTaskTypeDescriptions = new JLabel();
    private final JScrollPane jScrollPaneSystemTaskTypeDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemTaskTypeDescriptions = new JTextArea();
    private final TableModelReadOnlyList tableModelSystemTaskTypeWhereUsedList = new TableModelReadOnlyList();
    private final JTable jTableSystemTaskTypeWhereUsedList = new JTable(tableModelSystemTaskTypeWhereUsedList);
    
    // SystemTableTypeList
    private int selectedRow_jTableSystemTableTypeList;
    private final JSplitPane jSplitPaneSystemTableType = new JSplitPane();
    private final JScrollPane jScrollPaneSystemTableTypeList = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemTableTypeList = new TableModelReadOnlyList();
    private final JTable jTableSystemTableTypeList = new JTable(tableModelSystemTableTypeList);
    private final JPanel jPanelSystemTableType = new JPanel();
    private final JPanel jPanelSystemTableType1 = new JPanel();
    private final JScrollPane jScrollPaneSystemTableTypeWhereUsedList = new JScrollPane();
    private final JLabel jLabelSystemTableTypeName = new JLabel();
    private final KanjiTextField jTextFieldSystemTableTypeName = new KanjiTextField();
    private final JLabel jLabelSystemTableTypeSortKey = new JLabel();
    private final JTextField jTextFieldSystemTableTypeSortKey = new JTextField();
    private final JLabel jLabelSystemTableTypeDescriptions = new JLabel();
    private final JScrollPane jScrollPaneSystemTableTypeDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemTableTypeDescriptions = new JTextArea();
    private final TableModelReadOnlyList tableModelSystemTableTypeWhereUsedList = new TableModelReadOnlyList();
    private final JTable jTableSystemTableTypeWhereUsedList = new JTable(tableModelSystemTableTypeWhereUsedList);
    
    // SystemDataTypeList
    private int selectedRow_jTableSystemDataTypeList;
    private final JSplitPane jSplitPaneSystemDataType = new JSplitPane();
    private final JScrollPane jScrollPaneSystemDataTypeList = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemDataTypeList = new TableModelReadOnlyList();
    private final JTable jTableSystemDataTypeList = new JTable(tableModelSystemDataTypeList);
    private final JPanel jPanelSystemDataType = new JPanel();
    private final JPanel jPanelSystemDataType1 = new JPanel();
    private final JScrollPane jScrollPaneSystemDataTypeWhereUsedList = new JScrollPane();
    private final JLabel jLabelSystemDataTypeName = new JLabel();
    private final KanjiTextField jTextFieldSystemDataTypeName = new KanjiTextField();
    private final JLabel jLabelSystemDataTypeSortKey = new JLabel();
    private final JTextField jTextFieldSystemDataTypeSortKey = new JTextField();
    private final JLabel jLabelSystemDataTypeBasicType = new JLabel();
    private final DefaultComboBoxModel comboBoxModelDataTypeBasicType = new DefaultComboBoxModel();
    private final JComboBox jComboBoxSystemDataTypeBasicType = new JComboBox(comboBoxModelDataTypeBasicType);
    private final JLabel jLabelSystemDataTypeLength = new JLabel();
    private final JTextField jTextFieldSystemDataTypeLength = new JTextField();
    private final JLabel jLabelSystemDataTypeDecimal = new JLabel();
    private final JTextField jTextFieldSystemDataTypeDecimal = new JTextField();
    private final JLabel jLabelSystemSQLExpression = new JLabel();
    private final JTextField jTextFieldSystemSQLExpression = new JTextField();
    private final TableModelReadOnlyList tableModelSystemDataTypeWhereUsedList = new TableModelReadOnlyList();
    private final JTable jTableSystemDataTypeWhereUsedList = new JTable(tableModelSystemDataTypeWhereUsedList);
    private final HashMap<String, DataType> dataTypeElementMap = new HashMap<String, DataType>();
    
    // SystemFunctionTypeList
    private int selectedRow_jTableSystemFunctionTypeList;
    private final JSplitPane jSplitPaneSystemFunctionType = new JSplitPane();
    private final JScrollPane jScrollPaneSystemFunctionTypeList = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemFunctionTypeList = new TableModelReadOnlyList();
    private final JTable jTableSystemFunctionTypeList = new JTable(tableModelSystemFunctionTypeList);
    private final JPanel jPanelSystemFunctionType = new JPanel();
    private final JPanel jPanelSystemFunctionType1 = new JPanel();
    private final JScrollPane jScrollPaneSystemFunctionTypeWhereUsedList = new JScrollPane();
    private final JLabel jLabelSystemFunctionTypeName = new JLabel();
    private final KanjiTextField jTextFieldSystemFunctionTypeName = new KanjiTextField();
    private final JLabel jLabelSystemFunctionTypeSortKey = new JLabel();
    private final JTextField jTextFieldSystemFunctionTypeSortKey = new JTextField();
    private final JLabel jLabelSystemFunctionTypeDescriptions = new JLabel();
    private final JScrollPane jScrollPaneSystemFunctionTypeDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemFunctionTypeDescriptions = new JTextArea();
    private final TableModelReadOnlyList tableModelSystemFunctionTypeWhereUsedList = new TableModelReadOnlyList();
    private final JTable jTableSystemFunctionTypeWhereUsedList = new JTable(tableModelSystemFunctionTypeWhereUsedList);
    private final HashMap<String, FunctionType> functionTypeElementMap = new HashMap<String, FunctionType>();
    
    // SystemTermsList
    private int selectedRow_jTableSystemTermsList;
    private final JSplitPane jSplitPaneSystemTermsList = new JSplitPane();
    private final JScrollPane jScrollPaneSystemTermsList = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemTermsList = new TableModelReadOnlyList();
    private final JTable jTableSystemTermsList = new JTable(tableModelSystemTermsList);
    private final JPanel jPanelSystemTermsList = new JPanel();
    private final JPanel jPanelSystemTermsList1 = new JPanel();
    private final JPanel jPanelSystemTermsList2 = new JPanel();
    private final JPanel jPanelSystemTermsList3 = new JPanel();
    private final JLabel jLabelSystemTermsHeadder = new JLabel();
    private final KanjiTextField jTextFieldSystemTermsHeadder = new KanjiTextField();
    private final JLabel jLabelSystemTermsSortKey = new JLabel();
    private final JTextField jTextFieldSystemTermsSortKey = new JTextField();
    private final JLabel jLabelSystemTermsHtmlFileName = new JLabel();
    private final JTextField jTextFieldSystemTermsHtmlFileName = new JTextField();
    private final JLabel jLabelSystemTermsDescriptions = new JLabel();
    private final JScrollPane jScrollPaneSystemTermsDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemTermsDescriptions = new JTextArea();
    private final JEditorPane jEditorPaneSystemTermsDescriptions = new JEditorPane();
    
    // SystemMaintenanceLog
    private int selectedRow_jTableSystemMaintenanceLog;
    private final JSplitPane jSplitPaneSystemMaintenanceLog = new JSplitPane();
    private final JScrollPane jScrollPaneSystemMaintenanceLog = new JScrollPane();
    private final TableModelReadOnlyList tableModelSystemMaintenanceLog = new TableModelReadOnlyList();
    private final JTable jTableSystemMaintenanceLog = new JTable(tableModelSystemMaintenanceLog);
    private final JPanel jPanelSystemMaintenanceLog = new JPanel();
    private final JPanel jPanelSystemMaintenanceLog1 = new JPanel();
    private final JPanel jPanelSystemMaintenanceLog2 = new JPanel();
    private final JPanel jPanelSystemMaintenanceLog3 = new JPanel();
    private final JLabel jLabelSystemMaintenanceLogHeadder = new JLabel();
    private final JTextField jTextFieldSystemMaintenanceLogHeadder = new JTextField();
    private final JLabel jLabelSystemMaintenanceLogSortKey = new JLabel();
    private final JTextField jTextFieldSystemMaintenanceLogSortKey = new JTextField();
    private final JLabel jLabelSystemMaintenanceLogDescriptions = new JLabel();
    private final JScrollPane jScrollPaneSystemMaintenanceLogDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSystemMaintenanceLogDescriptions = new JTextArea();
    
    // Renderers
    private DefaultTableCellRenderer rendererAlignmentCenter;
    private DefaultTableCellRenderer rendererAlignmentLeft;
    private DefaultTableCellRenderer rendererAlignmentRight;
    private DefaultTableCellRenderer rendererTableHeader;
    
    // Columns
    private javax.swing.table.TableColumn column0;
    private javax.swing.table.TableColumn column1;
    private javax.swing.table.TableColumn column2;
    private javax.swing.table.TableColumn column3;
    private javax.swing.table.TableColumn column4;
    private javax.swing.table.TableColumn column5;
    private javax.swing.table.TableColumn column6;
    
    private Modeler modeler;
    
    /**
     * システム操作リスナーインターフェース
     */
    public interface SystemListener {
        void onSystemChanged();
    }
    
    private SystemListener systemListener;
    
    public SystemView(Modeler modeler, SystemListener listener) {
        this.modeler = modeler;
        this.systemListener = listener;
        initComponents(modeler);
    }
    
    public JPanel getPanel() {
        return jPanelSystem;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    public void initComponents(Modeler m) {
        jPanelSystem.setLayout(new BorderLayout());
        jPanelSystem6.setBorder(BorderFactory.createEtchedBorder());
        jPanelSystem6.setPreferredSize(new Dimension(10, 74));
        jPanelSystem6.setToolTipText("");
        jPanelSystem6.setLayout(null);
        
        jLabelSystemName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemName.setText(res.getString("S189"));
        jLabelSystemName.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemName.setBounds(new Rectangle(140, 9, 330, 25));
        
        jLabelSystemVersion.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemVersion.setRequestFocusEnabled(true);
        jLabelSystemVersion.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemVersion.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemVersion.setText(res.getString("S192"));
        jLabelSystemVersion.setBounds(new Rectangle(475, 12, 130, 20));
        
        jTextFieldSystemVersion.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemVersion.setBounds(new Rectangle(610, 9, 105, 25));
        
        jLabelSystemReferingModelFiles.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemReferingModelFiles.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemReferingModelFiles.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemReferingModelFiles.setText(res.getString("S193"));
        jLabelSystemReferingModelFiles.setBounds(new Rectangle(5, 43, 130, 20));
        
        jTextFieldSystemReferingModelFiles.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemReferingModelFiles.setBounds(new Rectangle(140, 40, 470, 25));
        
        jLabelSystemDocFileFormat.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDocFileFormat.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDocFileFormat.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDocFileFormat.setText(res.getString("S194"));
        jLabelSystemDocFileFormat.setBounds(new Rectangle(615, 43, 170, 20));
        
        jTextFieldSystemDocFileFormat.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDocFileFormat.setBounds(new Rectangle(790, 40, 500, 25));
        
        jPanelSystem.add(jPanelSystem6, BorderLayout.NORTH);
        jPanelSystem6.add(jLabelSystemName, null);
        jPanelSystem6.add(jLabelSystemVersion, null);
        jPanelSystem6.add(jLabelSystemReferingModelFiles, null);
        jPanelSystem6.add(jLabelSystemDocFileFormat, null);
        jPanelSystem6.add(jTextFieldSystemName, null);
        jPanelSystem6.add(jTextFieldSystemVersion, null);
        jPanelSystem6.add(jTextFieldSystemReferingModelFiles, null);
        jPanelSystem6.add(jTextFieldSystemDocFileFormat, null);
        
        jPanelSystem.add(jTabbedPaneSystem, BorderLayout.CENTER);
        
        // (SystemDescriptions)
        jTextAreaSystemDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemDescriptions.setLineWrap(true);
        jTabbedPaneSystem.add(jScrollPaneSystemDescriptions, res.getString("S195"));
        jScrollPaneSystemDescriptions.getViewport().add(jTextAreaSystemDescriptions, null);
        
        // (SystemDepartmentList)
        selectedRow_jTableSystemDepartmentList = -1;
        jTabbedPaneSystem.add(jSplitPaneSystemDepartment, res.getString("S196"));
        jSplitPaneSystemDepartment.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemDepartment.setDividerLocation(200);
        jSplitPaneSystemDepartment.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemDepartment.setFocusable(false);
        jScrollPaneSystemDepartmentList.setBorder(null);
        
        jPanelSystemDepartment.setLayout(new BorderLayout());
        jPanelSystemDepartment.setBorder(null);
        jPanelSystemDepartment1.setLayout(null);
        jPanelSystemDepartment1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemDepartment1.setBorder(BorderFactory.createEtchedBorder());
        
        jLabelSystemDepartmentName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDepartmentName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDepartmentName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDepartmentName.setText(res.getString("S198"));
        jLabelSystemDepartmentName.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemDepartmentName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDepartmentName.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemDepartmentSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDepartmentSortKey.setRequestFocusEnabled(true);
        jLabelSystemDepartmentSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDepartmentSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDepartmentSortKey.setText(res.getString("S201"));
        jLabelSystemDepartmentSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        
        jTextFieldSystemDepartmentSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDepartmentSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        
        jLabelSystemDepartmentDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDepartmentDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDepartmentDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDepartmentDescriptions.setText(res.getString("S204"));
        jLabelSystemDepartmentDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        
        jTextAreaSystemDepartmentDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemDepartmentDescriptions.setLineWrap(true);
        jScrollPaneSystemDepartmentDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemDepartmentDescriptions.getViewport().add(jTextAreaSystemDepartmentDescriptions, null);
        
        jPanelSystemDepartment1.add(jLabelSystemDepartmentName, null);
        jPanelSystemDepartment1.add(jTextFieldSystemDepartmentName, null);
        jPanelSystemDepartment1.add(jLabelSystemDepartmentSortKey, null);
        jPanelSystemDepartment1.add(jTextFieldSystemDepartmentSortKey, null);
        jPanelSystemDepartment1.add(jLabelSystemDepartmentDescriptions, null);
        jPanelSystemDepartment1.add(jScrollPaneSystemDepartmentDescriptions, null);
        
        jSplitPaneSystemDepartment.add(jScrollPaneSystemDepartmentList, JSplitPane.TOP);
        jSplitPaneSystemDepartment.add(jPanelSystemDepartment, JSplitPane.BOTTOM);
        jPanelSystemDepartment.add(jPanelSystemDepartment1, BorderLayout.NORTH);
        jPanelSystemDepartment.add(jScrollPaneSystemDepartmentWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemDepartmentList.getViewport().add(jTableSystemDepartmentList, null);
        jScrollPaneSystemDepartmentList.setBorder(null);
        jScrollPaneSystemDepartmentWhereUsedList.getViewport().add(jTableSystemDepartmentWhereUsedList, null);
        jScrollPaneSystemDepartmentWhereUsedList.setBorder(null);
        
        // (SystemTaskTypeList)
        selectedRow_jTableSystemTaskTypeList = -1;
        jTabbedPaneSystem.add(jSplitPaneSystemTaskType, res.getString("S209"));
        jSplitPaneSystemTaskType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemTaskType.setDividerLocation(200);
        jSplitPaneSystemTaskType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemTaskType.setFocusable(false);
        jScrollPaneSystemTaskTypeList.setBorder(null);
        
        jPanelSystemTaskType.setLayout(new BorderLayout());
        jPanelSystemTaskType.setBorder(null);
        jPanelSystemTaskType1.setLayout(null);
        jPanelSystemTaskType1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemTaskType1.setBorder(BorderFactory.createEtchedBorder());
        
        jLabelSystemTaskTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTaskTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTaskTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTaskTypeName.setText(res.getString("S210"));
        jLabelSystemTaskTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemTaskTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTaskTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemTaskTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTaskTypeSortKey.setRequestFocusEnabled(true);
        jLabelSystemTaskTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTaskTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTaskTypeSortKey.setText(res.getString("S201"));
        jLabelSystemTaskTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        
        jTextFieldSystemTaskTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTaskTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        
        jLabelSystemTaskTypeDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTaskTypeDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTaskTypeDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTaskTypeDescriptions.setText(res.getString("S204"));
        jLabelSystemTaskTypeDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        
        jTextAreaSystemTaskTypeDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemTaskTypeDescriptions.setLineWrap(true);
        jScrollPaneSystemTaskTypeDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemTaskTypeDescriptions.getViewport().add(jTextAreaSystemTaskTypeDescriptions, null);
        
        jPanelSystemTaskType1.add(jLabelSystemTaskTypeName, null);
        jPanelSystemTaskType1.add(jTextFieldSystemTaskTypeName, null);
        jPanelSystemTaskType1.add(jLabelSystemTaskTypeSortKey, null);
        jPanelSystemTaskType1.add(jTextFieldSystemTaskTypeSortKey, null);
        jPanelSystemTaskType1.add(jLabelSystemTaskTypeDescriptions, null);
        jPanelSystemTaskType1.add(jScrollPaneSystemTaskTypeDescriptions, null);
        
        jSplitPaneSystemTaskType.add(jScrollPaneSystemTaskTypeList, JSplitPane.TOP);
        jSplitPaneSystemTaskType.add(jPanelSystemTaskType, JSplitPane.BOTTOM);
        jPanelSystemTaskType.add(jPanelSystemTaskType1, BorderLayout.NORTH);
        jPanelSystemTaskType.add(jScrollPaneSystemTaskTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemTaskTypeList.getViewport().add(jTableSystemTaskTypeList, null);
        jScrollPaneSystemTaskTypeList.setBorder(null);
        jScrollPaneSystemTaskTypeWhereUsedList.getViewport().add(jTableSystemTaskTypeWhereUsedList, null);
        jScrollPaneSystemTaskTypeWhereUsedList.setBorder(null);
        
        // (SystemTableTypeList)
        jTabbedPaneSystem.add(jSplitPaneSystemTableType, res.getString("S206"));
        jSplitPaneSystemTableType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemTableType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemTableType.setDividerLocation(200);
        jSplitPaneSystemTableType.setFocusable(false);
        jScrollPaneSystemTableTypeList.setBorder(null);
        
        jPanelSystemTableType.setLayout(new BorderLayout());
        jPanelSystemTableType.setBorder(null);
        jPanelSystemTableType1.setLayout(null);
        jPanelSystemTableType1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemTableType1.setBorder(BorderFactory.createEtchedBorder());
        
        jLabelSystemTableTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTableTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTableTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTableTypeName.setText(res.getString("S208"));
        jLabelSystemTableTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemTableTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTableTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemTableTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTableTypeSortKey.setRequestFocusEnabled(true);
        jLabelSystemTableTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTableTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTableTypeSortKey.setText(res.getString("S201"));
        jLabelSystemTableTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        
        jTextFieldSystemTableTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTableTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        
        jLabelSystemTableTypeDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTableTypeDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTableTypeDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTableTypeDescriptions.setText(res.getString("S204"));
        jLabelSystemTableTypeDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        
        jTextAreaSystemTableTypeDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemTableTypeDescriptions.setLineWrap(true);
        jScrollPaneSystemTableTypeDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemTableTypeDescriptions.getViewport().add(jTextAreaSystemTableTypeDescriptions, null);
        
        jPanelSystemTableType1.add(jLabelSystemTableTypeName, null);
        jPanelSystemTableType1.add(jTextFieldSystemTableTypeName, null);
        jPanelSystemTableType1.add(jLabelSystemTableTypeSortKey, null);
        jPanelSystemTableType1.add(jTextFieldSystemTableTypeSortKey, null);
        jPanelSystemTableType1.add(jLabelSystemTableTypeDescriptions, null);
        jPanelSystemTableType1.add(jScrollPaneSystemTableTypeDescriptions, null);
        
        jSplitPaneSystemTableType.add(jScrollPaneSystemTableTypeList, JSplitPane.TOP);
        jSplitPaneSystemTableType.add(jPanelSystemTableType, JSplitPane.BOTTOM);
        jPanelSystemTableType.add(jPanelSystemTableType1, BorderLayout.NORTH);
        jPanelSystemTableType.add(jScrollPaneSystemTableTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemTableTypeList.getViewport().add(jTableSystemTableTypeList, null);
        jScrollPaneSystemTableTypeList.setBorder(null);
        jScrollPaneSystemTableTypeWhereUsedList.getViewport().add(jTableSystemTableTypeWhereUsedList, null);
        jScrollPaneSystemTableTypeWhereUsedList.setBorder(null);
        
        // (SystemDataTypeList)
        jTabbedPaneSystem.add(jSplitPaneSystemDataType, res.getString("S216"));
        jSplitPaneSystemDataType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemDataType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemDataType.setDividerLocation(200);
        jSplitPaneSystemDataType.setFocusable(false);
        jScrollPaneSystemDataTypeList.setBorder(null);
        
        jPanelSystemDataType.setLayout(new BorderLayout());
        jPanelSystemDataType.setBorder(null);
        jPanelSystemDataType1.setLayout(null);
        jPanelSystemDataType1.setPreferredSize(new Dimension(10, 74));
        jPanelSystemDataType1.setBorder(BorderFactory.createEtchedBorder());
        
        jLabelSystemDataTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeName.setText(res.getString("S218"));
        jLabelSystemDataTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemDataTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemDataTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeSortKey.setText(res.getString("S201"));
        jLabelSystemDataTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        
        jTextFieldSystemDataTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        
        jLabelSystemSQLExpression.setText(res.getString("S223"));
        jLabelSystemSQLExpression.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemSQLExpression.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemSQLExpression.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemSQLExpression.setBounds(new Rectangle(700, 12, 130, 20));
        
        jTextFieldSystemSQLExpression.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemSQLExpression.setBounds(new Rectangle(835, 9, 105, 25));
        
        jLabelSystemDataTypeBasicType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeBasicType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeBasicType.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeBasicType.setText(res.getString("S227"));
        jLabelSystemDataTypeBasicType.setBounds(new Rectangle(5, 43, 130, 20));
        
        jComboBoxSystemDataTypeBasicType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxSystemDataTypeBasicType.setActionCommand("comboBoxChanged");
        jComboBoxSystemDataTypeBasicType.setBounds(new Rectangle(140, 40, 180, 25));
        
        comboBoxModelDataTypeBasicType.addElement("SignedNumber");
        comboBoxModelDataTypeBasicType.addElement("UnsignedNumber");
        comboBoxModelDataTypeBasicType.addElement("String");
        comboBoxModelDataTypeBasicType.addElement("Date");
        comboBoxModelDataTypeBasicType.addElement("Boolean");
        comboBoxModelDataTypeBasicType.addElement("Object");
        
        jLabelSystemDataTypeLength.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeLength.setRequestFocusEnabled(true);
        jLabelSystemDataTypeLength.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeLength.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeLength.setText(res.getString("S237"));
        jLabelSystemDataTypeLength.setBounds(new Rectangle(450, 43, 130, 20));
        
        jTextFieldSystemDataTypeLength.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeLength.setBounds(new Rectangle(585, 40, 40, 25));
        jTextFieldSystemDataTypeLength.setHorizontalAlignment(SwingConstants.RIGHT);
        
        jLabelSystemDataTypeDecimal.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeDecimal.setRequestFocusEnabled(true);
        jLabelSystemDataTypeDecimal.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeDecimal.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeDecimal.setText(res.getString("S240"));
        jLabelSystemDataTypeDecimal.setBounds(new Rectangle(700, 43, 130, 20));
        
        jTextFieldSystemDataTypeDecimal.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeDecimal.setBounds(new Rectangle(835, 40, 40, 25));
        jTextFieldSystemDataTypeDecimal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        jPanelSystemDataType1.add(jLabelSystemDataTypeName, null);
        jPanelSystemDataType1.add(jTextFieldSystemDataTypeName, null);
        jPanelSystemDataType1.add(jLabelSystemDataTypeSortKey, null);
        jPanelSystemDataType1.add(jTextFieldSystemDataTypeSortKey, null);
        jPanelSystemDataType1.add(jLabelSystemSQLExpression, null);
        jPanelSystemDataType1.add(jTextFieldSystemSQLExpression, null);
        jPanelSystemDataType1.add(jLabelSystemDataTypeBasicType, null);
        jPanelSystemDataType1.add(jComboBoxSystemDataTypeBasicType, null);
        jPanelSystemDataType1.add(jLabelSystemDataTypeLength, null);
        jPanelSystemDataType1.add(jTextFieldSystemDataTypeLength, null);
        jPanelSystemDataType1.add(jLabelSystemDataTypeDecimal, null);
        jPanelSystemDataType1.add(jTextFieldSystemDataTypeDecimal, null);
        
        jSplitPaneSystemDataType.add(jScrollPaneSystemDataTypeList, JSplitPane.TOP);
        jSplitPaneSystemDataType.add(jPanelSystemDataType, JSplitPane.BOTTOM);
        jPanelSystemDataType.add(jPanelSystemDataType1, BorderLayout.NORTH);
        jPanelSystemDataType.add(jScrollPaneSystemDataTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemDataTypeList.getViewport().add(jTableSystemDataTypeList, null);
        jScrollPaneSystemDataTypeList.setBorder(null);
        jScrollPaneSystemDataTypeWhereUsedList.getViewport().add(jTableSystemDataTypeWhereUsedList, null);
        jScrollPaneSystemDataTypeWhereUsedList.setBorder(null);
        
        // (SystemFunctionTypeList)
        jTabbedPaneSystem.add(jSplitPaneSystemFunctionType, res.getString("S242"));
        jSplitPaneSystemFunctionType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemFunctionType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemFunctionType.setDividerLocation(200);
        jSplitPaneSystemFunctionType.setFocusable(false);
        jScrollPaneSystemFunctionTypeList.setBorder(null);
        
        jPanelSystemFunctionType.setLayout(new BorderLayout());
        jPanelSystemFunctionType.setBorder(null);
        jPanelSystemFunctionType1.setLayout(null);
        jPanelSystemFunctionType1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemFunctionType1.setBorder(BorderFactory.createEtchedBorder());
        
        jLabelSystemFunctionTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemFunctionTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemFunctionTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemFunctionTypeName.setText(res.getString("S244"));
        jLabelSystemFunctionTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemFunctionTypeName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemFunctionTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemFunctionTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemFunctionTypeSortKey.setRequestFocusEnabled(true);
        jLabelSystemFunctionTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemFunctionTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemFunctionTypeSortKey.setText(res.getString("S201"));
        jLabelSystemFunctionTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        
        jTextFieldSystemFunctionTypeSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemFunctionTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        
        jLabelSystemFunctionTypeDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemFunctionTypeDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemFunctionTypeDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemFunctionTypeDescriptions.setText(res.getString("S204"));
        jLabelSystemFunctionTypeDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        
        jTextAreaSystemFunctionTypeDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemFunctionTypeDescriptions.setLineWrap(true);
        jScrollPaneSystemFunctionTypeDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemFunctionTypeDescriptions.getViewport().add(jTextAreaSystemFunctionTypeDescriptions, null);
        
        jPanelSystemFunctionType1.add(jLabelSystemFunctionTypeName, null);
        jPanelSystemFunctionType1.add(jTextFieldSystemFunctionTypeName, null);
        jPanelSystemFunctionType1.add(jLabelSystemFunctionTypeSortKey, null);
        jPanelSystemFunctionType1.add(jTextFieldSystemFunctionTypeSortKey, null);
        jPanelSystemFunctionType1.add(jLabelSystemFunctionTypeDescriptions, null);
        jPanelSystemFunctionType1.add(jScrollPaneSystemFunctionTypeDescriptions, null);
        
        jSplitPaneSystemFunctionType.add(jScrollPaneSystemFunctionTypeList, JSplitPane.TOP);
        jSplitPaneSystemFunctionType.add(jPanelSystemFunctionType, JSplitPane.BOTTOM);
        jPanelSystemFunctionType.add(jPanelSystemFunctionType1, BorderLayout.NORTH);
        jPanelSystemFunctionType.add(jScrollPaneSystemFunctionTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemFunctionTypeList.getViewport().add(jTableSystemFunctionTypeList, null);
        jScrollPaneSystemFunctionTypeList.setBorder(null);
        jScrollPaneSystemFunctionTypeWhereUsedList.getViewport().add(jTableSystemFunctionTypeWhereUsedList, null);
        jScrollPaneSystemFunctionTypeWhereUsedList.setBorder(null);
        
        // (SystemTermsList)
        jTabbedPaneSystem.add(jSplitPaneSystemTermsList, res.getString("S245"));
        jSplitPaneSystemTermsList.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemTermsList.setBorder(null);
        jSplitPaneSystemTermsList.setDividerLocation(200);
        jSplitPaneSystemTermsList.setFocusable(false);
        jScrollPaneSystemTermsList.setBorder(null);
        
        jPanelSystemTermsList.setLayout(new BorderLayout());
        jPanelSystemTermsList.setBorder(BorderFactory.createEtchedBorder());
        jPanelSystemTermsList1.setLayout(null);
        jPanelSystemTermsList1.setBorder(null);
        jPanelSystemTermsList1.setPreferredSize(new Dimension(10, 42));
        jPanelSystemTermsList2.setLayout(new BorderLayout());
        jPanelSystemTermsList2.setBorder(null);
        jPanelSystemTermsList3.setLayout(null);
        jPanelSystemTermsList3.setBorder(null);
        jPanelSystemTermsList3.setPreferredSize(new Dimension(140, 10));
        
        jLabelSystemTermsSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsSortKey.setText(res.getString("S248"));
        jLabelSystemTermsSortKey.setBounds(new Rectangle(460, 12, 130, 20));
        
        jTextFieldSystemTermsSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTermsSortKey.setBounds(new Rectangle(595, 9, 150, 25));
        
        jLabelSystemTermsHeadder.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsHeadder.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsHeadder.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsHeadder.setText(res.getString("S246"));
        jLabelSystemTermsHeadder.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemTermsHeadder.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTermsHeadder.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemTermsHtmlFileName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsHtmlFileName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsHtmlFileName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsHtmlFileName.setText(res.getString("S249"));
        jLabelSystemTermsHtmlFileName.setBounds(new Rectangle(750, 12, 130, 20));
        
        jTextFieldSystemTermsHtmlFileName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTermsHtmlFileName.setBounds(new Rectangle(885, 9, 300, 25));
        
        jLabelSystemTermsDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsDescriptions.setText(res.getString("S247"));
        jLabelSystemTermsDescriptions.setBounds(new Rectangle(5, 2, 130, 20));
        
        jTextAreaSystemTermsDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemTermsDescriptions.setLineWrap(true);
        
        jEditorPaneSystemTermsDescriptions.setBorder(BorderFactory.createRaisedBevelBorder());
        jEditorPaneSystemTermsDescriptions.setEditable(false);
        jEditorPaneSystemTermsDescriptions.setContentType("text/html");
        
        jScrollPaneSystemTermsDescriptions.setBounds(new Rectangle(140, 40, 600, 42));
        jScrollPaneSystemTermsDescriptions.getViewport().add(jTextAreaSystemTermsDescriptions, null);
        
        jPanelSystemTermsList.add(jPanelSystemTermsList1, BorderLayout.NORTH);
        jPanelSystemTermsList.add(jPanelSystemTermsList2, BorderLayout.CENTER);
        jPanelSystemTermsList1.add(jLabelSystemTermsHeadder, null);
        jPanelSystemTermsList1.add(jTextFieldSystemTermsHeadder, null);
        jPanelSystemTermsList1.add(jLabelSystemTermsSortKey, null);
        jPanelSystemTermsList1.add(jTextFieldSystemTermsSortKey, null);
        jPanelSystemTermsList1.add(jLabelSystemTermsHtmlFileName, null);
        jPanelSystemTermsList1.add(jTextFieldSystemTermsHtmlFileName, null);
        jPanelSystemTermsList2.add(jPanelSystemTermsList3, BorderLayout.WEST);
        jPanelSystemTermsList2.add(jScrollPaneSystemTermsDescriptions, BorderLayout.CENTER);
        jPanelSystemTermsList3.add(jLabelSystemTermsDescriptions, null);
        jScrollPaneSystemTermsList.getViewport().add(jTableSystemTermsList, null);
        jSplitPaneSystemTermsList.add(jScrollPaneSystemTermsList, JSplitPane.TOP);
        jSplitPaneSystemTermsList.add(jPanelSystemTermsList, JSplitPane.BOTTOM);
        
        // (SystemMaintenanceLog)
        jTabbedPaneSystem.add(jSplitPaneSystemMaintenanceLog, res.getString("S252"));
        jSplitPaneSystemMaintenanceLog.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemMaintenanceLog.setBorder(null);
        jSplitPaneSystemMaintenanceLog.setDividerLocation(200);
        jSplitPaneSystemMaintenanceLog.setFocusable(false);
        jScrollPaneSystemMaintenanceLog.setBorder(null);
        
        jPanelSystemMaintenanceLog.setLayout(new BorderLayout());
        jPanelSystemMaintenanceLog.setBorder(BorderFactory.createEtchedBorder());
        jPanelSystemMaintenanceLog1.setLayout(null);
        jPanelSystemMaintenanceLog1.setBorder(null);
        jPanelSystemMaintenanceLog1.setPreferredSize(new Dimension(10, 42));
        jPanelSystemMaintenanceLog2.setLayout(new BorderLayout());
        jPanelSystemMaintenanceLog2.setBorder(null);
        jPanelSystemMaintenanceLog3.setLayout(null);
        jPanelSystemMaintenanceLog3.setBorder(null);
        jPanelSystemMaintenanceLog3.setPreferredSize(new Dimension(140, 10));
        
        jLabelSystemMaintenanceLogHeadder.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemMaintenanceLogHeadder.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemMaintenanceLogHeadder.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemMaintenanceLogHeadder.setText(res.getString("S254"));
        jLabelSystemMaintenanceLogHeadder.setBounds(new Rectangle(5, 12, 130, 20));
        
        jTextFieldSystemMaintenanceLogHeadder.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemMaintenanceLogHeadder.setBounds(new Rectangle(140, 9, 300, 25));
        
        jLabelSystemMaintenanceLogSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemMaintenanceLogSortKey.setRequestFocusEnabled(true);
        jLabelSystemMaintenanceLogSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemMaintenanceLogSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemMaintenanceLogSortKey.setText(res.getString("S257"));
        jLabelSystemMaintenanceLogSortKey.setBounds(new Rectangle(450, 12, 150, 20));
        
        jTextFieldSystemMaintenanceLogSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemMaintenanceLogSortKey.setBounds(new Rectangle(605, 9, 180, 25));
        
        jLabelSystemMaintenanceLogDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemMaintenanceLogDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemMaintenanceLogDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemMaintenanceLogDescriptions.setText(res.getString("S260"));
        jLabelSystemMaintenanceLogDescriptions.setBounds(new Rectangle(5, 2, 130, 20));
        
        jTextAreaSystemMaintenanceLogDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemMaintenanceLogDescriptions.setLineWrap(true);
        
        jScrollPaneSystemMaintenanceLogDescriptions.setBounds(new Rectangle(140, 40, 600, 42));
        jScrollPaneSystemMaintenanceLogDescriptions.getViewport().add(jTextAreaSystemMaintenanceLogDescriptions, null);
        
        jPanelSystemMaintenanceLog.add(jPanelSystemMaintenanceLog1, BorderLayout.NORTH);
        jPanelSystemMaintenanceLog.add(jPanelSystemMaintenanceLog2, BorderLayout.CENTER);
        jPanelSystemMaintenanceLog1.add(jLabelSystemMaintenanceLogHeadder, null);
        jPanelSystemMaintenanceLog1.add(jTextFieldSystemMaintenanceLogHeadder, null);
        jPanelSystemMaintenanceLog1.add(jLabelSystemMaintenanceLogSortKey, null);
        jPanelSystemMaintenanceLog1.add(jTextFieldSystemMaintenanceLogSortKey, null);
        jPanelSystemMaintenanceLog2.add(jPanelSystemMaintenanceLog3, BorderLayout.WEST);
        jPanelSystemMaintenanceLog2.add(jScrollPaneSystemMaintenanceLogDescriptions, BorderLayout.CENTER);
        jPanelSystemMaintenanceLog3.add(jLabelSystemMaintenanceLogDescriptions, null);
        jScrollPaneSystemMaintenanceLog.getViewport().add(jTableSystemMaintenanceLog, null);
        jSplitPaneSystemMaintenanceLog.add(jScrollPaneSystemMaintenanceLog, JSplitPane.TOP);
        jSplitPaneSystemMaintenanceLog.add(jPanelSystemMaintenanceLog, JSplitPane.BOTTOM);
        
        // Initialize table models
        initTableModels();
    }
    
    /**
     * テーブルを初期化します。
     */
    public void initTables() {
        // Initialize renderers
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        
        rendererAlignmentRight = new DefaultTableCellRenderer();
        rendererAlignmentRight.setHorizontalAlignment(SwingConstants.RIGHT);

        // Initialize table models
        initTableModels();
    }
    
    /**
     * テーブルモデルを初期化します。
     */
    private void initTableModels() {
        // jTableSystemDepartmentList
        tableModelSystemDepartmentList.addColumn("No.");
        tableModelSystemDepartmentList.addColumn(res.getString("S201"));
        tableModelSystemDepartmentList.addColumn(res.getString("S265"));
        tableModelSystemDepartmentList.addColumn(res.getString("S204"));
        
        // jTableSystemDepartmentWhereUsedList
        tableModelSystemDepartmentWhereUsedList.addColumn("No.");
        tableModelSystemDepartmentWhereUsedList.addColumn("ID");
        tableModelSystemDepartmentWhereUsedList.addColumn(res.getString("S271"));
        tableModelSystemDepartmentWhereUsedList.addColumn(res.getString("S204"));
        
        // jTableSystemTaskTypeList
        tableModelSystemTaskTypeList.addColumn("No.");
        tableModelSystemTaskTypeList.addColumn(res.getString("S201"));
        tableModelSystemTaskTypeList.addColumn(res.getString("S362"));
        tableModelSystemTaskTypeList.addColumn(res.getString("S204"));
        
        // jTableSystemTaskTypeWhereUsedList
        tableModelSystemTaskTypeWhereUsedList.addColumn("No.");
        tableModelSystemTaskTypeWhereUsedList.addColumn("ID");
        tableModelSystemTaskTypeWhereUsedList.addColumn(res.getString("S363"));
        tableModelSystemTaskTypeWhereUsedList.addColumn(res.getString("S340"));
        tableModelSystemTaskTypeWhereUsedList.addColumn(res.getString("S204"));
        
        // jTableSystemTableTypeList
        tableModelSystemTableTypeList.addColumn("No.");
        tableModelSystemTableTypeList.addColumn(res.getString("S201"));
        tableModelSystemTableTypeList.addColumn(res.getString("S277"));
        tableModelSystemTableTypeList.addColumn(res.getString("S204"));
        
        // jTableSystemTableTypeWhereUsedList
        tableModelSystemTableTypeWhereUsedList.addColumn("No.");
        tableModelSystemTableTypeWhereUsedList.addColumn("ID");
        tableModelSystemTableTypeWhereUsedList.addColumn(res.getString("S283"));
        tableModelSystemTableTypeWhereUsedList.addColumn(res.getString("S284"));
        
        // jTableSystemDataTypeList
        tableModelSystemDataTypeList.addColumn("No.");
        tableModelSystemDataTypeList.addColumn(res.getString("S201"));
        tableModelSystemDataTypeList.addColumn(res.getString("S289"));
        tableModelSystemDataTypeList.addColumn(res.getString("S290"));
        tableModelSystemDataTypeList.addColumn(res.getString("S291"));
        tableModelSystemDataTypeList.addColumn(res.getString("S292"));
        tableModelSystemDataTypeList.addColumn(res.getString("S293"));
        
        // jTableSystemDataTypeWhereUsedList
        tableModelSystemDataTypeWhereUsedList.addColumn("No.");
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S297"));
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S298"));
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S299"));
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S300"));
        
        // jTableSystemFunctionTypeList
        tableModelSystemFunctionTypeList.addColumn("No.");
        tableModelSystemFunctionTypeList.addColumn(res.getString("S201"));
        tableModelSystemFunctionTypeList.addColumn(res.getString("S305"));
        tableModelSystemFunctionTypeList.addColumn(res.getString("S204"));
        
        // jTableSystemFunctionTypeWhereUsedList
        tableModelSystemFunctionTypeWhereUsedList.addColumn("No.");
        tableModelSystemFunctionTypeWhereUsedList.addColumn("ID");
        tableModelSystemFunctionTypeWhereUsedList.addColumn(res.getString("S311"));
        tableModelSystemFunctionTypeWhereUsedList.addColumn(res.getString("S312"));
        tableModelSystemFunctionTypeWhereUsedList.addColumn(res.getString("S313"));
        
        // jTableSystemTermsList
        tableModelSystemTermsList.addColumn("No.");
        tableModelSystemTermsList.addColumn(res.getString("S248"));
        tableModelSystemTermsList.addColumn(res.getString("S246"));
        tableModelSystemTermsList.addColumn(res.getString("S247"));
        tableModelSystemTermsList.addColumn(res.getString("S249"));
        
        // jTableSystemMaintenanceLog
        tableModelSystemMaintenanceLog.addColumn("No.");
        tableModelSystemMaintenanceLog.addColumn(res.getString("S317"));
        tableModelSystemMaintenanceLog.addColumn(res.getString("S318"));
        tableModelSystemMaintenanceLog.addColumn(res.getString("S319"));
    }
    
    /**
     * 読み取り専用のテーブルモデル
     */
    public static class TableModelReadOnlyList extends DefaultTableModel {
        private static final long serialVersionUID = 1L;
        
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
    
    // Getters for components
    public JPanel getJPanelSystem() { return jPanelSystem; }
    public JTabbedPane getJTabbedPaneSystem() { return jTabbedPaneSystem; }
    public KanjiTextField getJTextFieldSystemName() { return jTextFieldSystemName; }
    public JTextField getJTextFieldSystemVersion() { return jTextFieldSystemVersion; }
    public JTextField getJTextFieldSystemReferingModelFiles() { return jTextFieldSystemReferingModelFiles; }
    public JTextField getJTextFieldSystemDocFileFormat() { return jTextFieldSystemDocFileFormat; }
    public JTextArea getJTextAreaSystemDescriptions() { return jTextAreaSystemDescriptions; }
    public JTable getJTableSystemDepartmentList() { return jTableSystemDepartmentList; }
    public TableModelReadOnlyList getTableModelSystemDepartmentList() { return tableModelSystemDepartmentList; }
    public JTable getJTableSystemTaskTypeList() { return jTableSystemTaskTypeList; }
    public TableModelReadOnlyList getTableModelSystemTaskTypeList() { return tableModelSystemTaskTypeList; }
    public JTable getJTableSystemTableTypeList() { return jTableSystemTableTypeList; }
    public TableModelReadOnlyList getTableModelSystemTableTypeList() { return tableModelSystemTableTypeList; }
    public JTable getJTableSystemDataTypeList() { return jTableSystemDataTypeList; }
    public TableModelReadOnlyList getTableModelSystemDataTypeList() { return tableModelSystemDataTypeList; }
    public JTable getJTableSystemFunctionTypeList() { return jTableSystemFunctionTypeList; }
    public TableModelReadOnlyList getTableModelSystemFunctionTypeList() { return tableModelSystemFunctionTypeList; }
    public JTable getJTableSystemTermsList() { return jTableSystemTermsList; }
    public TableModelReadOnlyList getTableModelSystemTermsList() { return tableModelSystemTermsList; }
    public JTable getJTableSystemMaintenanceLog() { return jTableSystemMaintenanceLog; }
    public TableModelReadOnlyList getTableModelSystemMaintenanceLog() { return tableModelSystemMaintenanceLog; }
    public HashMap<String, DataType> getDataTypeElementMap() { return dataTypeElementMap; }
    public HashMap<String, FunctionType> getFunctionTypeElementMap() { return functionTypeElementMap; }
}
