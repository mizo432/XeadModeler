package xeadModeler;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;
import xeadModeler.model.json.DataType;
import xeadModeler.model.json.FunctionType;
import xeadModeler.view.TabbedPaneChangeListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

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
    }
    
    public JPanel getPanel() {
        return jPanelSystem;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    
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

    /**
     * ModelerのSystemコンポーネントを初期化します。
     */
    public void initComponents(Modeler m) {
        jTabbedPaneSystem.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTabbedPaneSystem.addChangeListener(new TabbedPaneChangeListener(m));
        jPanelSystem.setLayout(new BorderLayout());
        jPanelSystem6.setBorder(BorderFactory.createEtchedBorder());
        //jPanelSystem6.setPreferredSize(new Dimension(10, 43));
        jPanelSystem6.setPreferredSize(new Dimension(10, 74));
        jPanelSystem6.setToolTipText("");
        jPanelSystem6.setLayout(null);
        jLabelSystemName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemName.setText(res.getString("S189"));
        jLabelSystemName.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemName.setBounds(new Rectangle(140, 9, 330, 25));
        jLabelSystemVersion.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemVersion.setRequestFocusEnabled(true);
        jLabelSystemVersion.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemVersion.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemVersion.setText(res.getString("S192"));
        jLabelSystemVersion.setBounds(new Rectangle(475, 12, 130, 20));
        jTextFieldSystemVersion.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemVersion.setBounds(new Rectangle(610, 9, 105, 25));
        jLabelSystemReferingModelFiles.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemReferingModelFiles.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemReferingModelFiles.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemReferingModelFiles.setText(res.getString("S193"));
        jLabelSystemReferingModelFiles.setBounds(new Rectangle(5, 43, 130, 20));
        jTextFieldSystemReferingModelFiles.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemReferingModelFiles.setBounds(new Rectangle(140, 40, 470, 25));
        jLabelSystemDocFileFormat.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDocFileFormat.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDocFileFormat.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDocFileFormat.setText(res.getString("S194"));
        jLabelSystemDocFileFormat.setBounds(new Rectangle(615, 43, 170, 20));
        jTextFieldSystemDocFileFormat.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDocFileFormat.setBounds(new Rectangle(790, 40, 500, 25));
        jPanelSystem.add(m.jPanelSystem6, BorderLayout.NORTH);
        jPanelSystem6.add(m.jLabelSystemName, null);
        jPanelSystem6.add(m.jLabelSystemVersion, null);
        jPanelSystem6.add(m.jLabelSystemReferingModelFiles, null);
        jPanelSystem6.add(m.jLabelSystemDocFileFormat, null);
        jPanelSystem6.add(m.jTextFieldSystemName, null);
        jPanelSystem6.add(m.jTextFieldSystemVersion, null);
        jPanelSystem6.add(m.jTextFieldSystemReferingModelFiles, null);
        jPanelSystem6.add(m.jTextFieldSystemDocFileFormat, null);
        jPanelSystem.add(m.jTabbedPaneSystem, BorderLayout.CENTER);
        //(SystemDescriptions)//
        jTextAreaSystemDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemDescriptions.setLineWrap(true);
        jTabbedPaneSystem.add(m.jScrollPaneSystemDescriptions, res.getString("S195"));
        jTabbedPaneSystem.setIconAt(0, m.imageIconSystem);
        jScrollPaneSystemDescriptions.getViewport().add(m.jTextAreaSystemDescriptions, null);
        //(SystemDepartmentList)//
        selectedRow_jTableSystemDepartmentList = -1;
        jTabbedPaneSystem.add(m.jSplitPaneSystemDepartment, res.getString("S196"));
        jTabbedPaneSystem.setIconAt(1, m.imageIconRoleList);
        jSplitPaneSystemDepartment.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemDepartment.setDividerLocation(200);
        jSplitPaneSystemDepartment.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemDepartment.setFocusable(false);
        jScrollPaneSystemDepartmentList.setBorder(null);
        jScrollPaneSystemDepartmentList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemDepartmentList_mouseClicked(e); } });
        jPanelSystemDepartment.setLayout(new BorderLayout());
        jPanelSystemDepartment.setBorder(null);
        jPanelSystemDepartment1.setLayout(null);
        jPanelSystemDepartment1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemDepartment1.setBorder(BorderFactory.createEtchedBorder());
        jLabelSystemDepartmentName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDepartmentName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDepartmentName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDepartmentName.setText(res.getString("S198"));
        jLabelSystemDepartmentName.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemDepartmentName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDepartmentName.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemDepartmentSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDepartmentSortKey.setRequestFocusEnabled(true);
        jLabelSystemDepartmentSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDepartmentSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDepartmentSortKey.setText(res.getString("S201"));
        jLabelSystemDepartmentSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        jTextFieldSystemDepartmentSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDepartmentSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        jLabelSystemDepartmentDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDepartmentDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDepartmentDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDepartmentDescriptions.setText(res.getString("S204"));
        jLabelSystemDepartmentDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        jTextAreaSystemDepartmentDescriptions.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemDepartmentDescriptions.setLineWrap(true);
        jScrollPaneSystemDepartmentDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemDepartmentDescriptions.getViewport()
            .add(m.jTextAreaSystemDepartmentDescriptions, null);
        jPanelSystemDepartment1.add(m.jLabelSystemDepartmentName, null);
        jPanelSystemDepartment1.add(m.jTextFieldSystemDepartmentName, null);
        jPanelSystemDepartment1.add(m.jLabelSystemDepartmentSortKey, null);
        jPanelSystemDepartment1.add(m.jTextFieldSystemDepartmentSortKey, null);
        jPanelSystemDepartment1.add(m.jLabelSystemDepartmentDescriptions, null);
        jPanelSystemDepartment1.add(m.jScrollPaneSystemDepartmentDescriptions, null);
        jSplitPaneSystemDepartment.add(m.jScrollPaneSystemDepartmentList, JSplitPane.TOP);
        jSplitPaneSystemDepartment.add(m.jPanelSystemDepartment, JSplitPane.BOTTOM);
        jPanelSystemDepartment.add(m.jPanelSystemDepartment1, BorderLayout.NORTH);
        jPanelSystemDepartment.add(m.jScrollPaneSystemDepartmentWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemDepartmentList.getViewport().add(m.jTableSystemDepartmentList, null);
        jScrollPaneSystemDepartmentList.setBorder(null);
        jScrollPaneSystemDepartmentWhereUsedList.getViewport()
            .add(m.jTableSystemDepartmentWhereUsedList, null);
        jScrollPaneSystemDepartmentWhereUsedList.setBorder(null);
        //(SystemTaskTypeList)//
        selectedRow_jTableSystemTaskTypeList = -1;
        jTabbedPaneSystem.add(m.jSplitPaneSystemTaskType, res.getString("S209"));
        jTabbedPaneSystem.setIconAt(2, m.imageIconTask);
        jSplitPaneSystemTaskType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemTaskType.setDividerLocation(200);
        jSplitPaneSystemTaskType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemTaskType.setFocusable(false);
        jScrollPaneSystemTaskTypeList.setBorder(null);
        jScrollPaneSystemTaskTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemTaskTypeList_mouseClicked(e); } });
        jPanelSystemTaskType.setLayout(new BorderLayout());
        jPanelSystemTaskType.setBorder(null);
        jPanelSystemTaskType1.setLayout(null);
        jPanelSystemTaskType1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemTaskType1.setBorder(BorderFactory.createEtchedBorder());
        jLabelSystemTaskTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTaskTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTaskTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTaskTypeName.setText(res.getString("S210"));
        jLabelSystemTaskTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemTaskTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTaskTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemTaskTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTaskTypeSortKey.setRequestFocusEnabled(true);
        jLabelSystemTaskTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTaskTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTaskTypeSortKey.setText(res.getString("S201"));
        jLabelSystemTaskTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        jTextFieldSystemTaskTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTaskTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        jLabelSystemTaskTypeDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTaskTypeDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTaskTypeDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTaskTypeDescriptions.setText(res.getString("S204"));
        jLabelSystemTaskTypeDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        jTextAreaSystemTaskTypeDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemTaskTypeDescriptions.setLineWrap(true);
        jScrollPaneSystemTaskTypeDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemTaskTypeDescriptions.getViewport()
            .add(m.jTextAreaSystemTaskTypeDescriptions, null);
        jPanelSystemTaskType1.add(m.jLabelSystemTaskTypeName, null);
        jPanelSystemTaskType1.add(m.jTextFieldSystemTaskTypeName, null);
        jPanelSystemTaskType1.add(m.jLabelSystemTaskTypeSortKey, null);
        jPanelSystemTaskType1.add(m.jTextFieldSystemTaskTypeSortKey, null);
        jPanelSystemTaskType1.add(m.jLabelSystemTaskTypeDescriptions, null);
        jPanelSystemTaskType1.add(m.jScrollPaneSystemTaskTypeDescriptions, null);
        jSplitPaneSystemTaskType.add(m.jScrollPaneSystemTaskTypeList, JSplitPane.TOP);
        jSplitPaneSystemTaskType.add(m.jPanelSystemTaskType, JSplitPane.BOTTOM);
        jPanelSystemTaskType.add(m.jPanelSystemTaskType1, BorderLayout.NORTH);
        jPanelSystemTaskType.add(m.jScrollPaneSystemTaskTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemTaskTypeList.getViewport().add(m.jTableSystemTaskTypeList, null);
        jScrollPaneSystemTaskTypeList.setBorder(null);
        jScrollPaneSystemTaskTypeWhereUsedList.getViewport()
            .add(m.jTableSystemTaskTypeWhereUsedList, null);
        jScrollPaneSystemTaskTypeWhereUsedList.setBorder(null);
        //(SystemTableTypeList)//
        jTabbedPaneSystem.add(m.jSplitPaneSystemTableType, res.getString("S206"));
        jTabbedPaneSystem.setIconAt(3, m.imageIconTable);
        jSplitPaneSystemTableType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemTableType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemTableType.setDividerLocation(200);
        jSplitPaneSystemTableType.setFocusable(false);
        jScrollPaneSystemTableTypeList.setBorder(null);
        jScrollPaneSystemTableTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemTableTypeList_mouseClicked(e); } });
        jPanelSystemTableType.setLayout(new BorderLayout());
        jPanelSystemTableType.setBorder(null);
        jPanelSystemTableType1.setLayout(null);
        jPanelSystemTableType1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemTableType1.setBorder(BorderFactory.createEtchedBorder());
        jLabelSystemTableTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTableTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTableTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTableTypeName.setText(res.getString("S208"));
        jLabelSystemTableTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemTableTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTableTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemTableTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTableTypeSortKey.setRequestFocusEnabled(true);
        jLabelSystemTableTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTableTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTableTypeSortKey.setText(res.getString("S201"));
        jLabelSystemTableTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        jTextFieldSystemTableTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTableTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        jLabelSystemTableTypeDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTableTypeDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTableTypeDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTableTypeDescriptions.setText(res.getString("S204"));
        jLabelSystemTableTypeDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        jTextAreaSystemTableTypeDescriptions.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemTableTypeDescriptions.setLineWrap(true);
        jScrollPaneSystemTableTypeDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemTableTypeDescriptions.getViewport()
            .add(m.jTextAreaSystemTableTypeDescriptions, null);
        jPanelSystemTableType1.add(m.jLabelSystemTableTypeName, null);
        jPanelSystemTableType1.add(m.jTextFieldSystemTableTypeName, null);
        jPanelSystemTableType1.add(m.jLabelSystemTableTypeSortKey, null);
        jPanelSystemTableType1.add(m.jTextFieldSystemTableTypeSortKey, null);
        jPanelSystemTableType1.add(m.jLabelSystemTableTypeDescriptions, null);
        jPanelSystemTableType1.add(m.jScrollPaneSystemTableTypeDescriptions, null);
        jSplitPaneSystemTableType.add(m.jScrollPaneSystemTableTypeList, JSplitPane.TOP);
        jSplitPaneSystemTableType.add(m.jPanelSystemTableType, JSplitPane.BOTTOM);
        jPanelSystemTableType.add(m.jPanelSystemTableType1, BorderLayout.NORTH);
        jPanelSystemTableType.add(m.jScrollPaneSystemTableTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemTableTypeList.getViewport().add(m.jTableSystemTableTypeList, null);
        jScrollPaneSystemTableTypeList.setBorder(null);
        jScrollPaneSystemTableTypeWhereUsedList.getViewport()
            .add(m.jTableSystemTableTypeWhereUsedList, null);
        jScrollPaneSystemTableTypeWhereUsedList.setBorder(null);
        //(SystemDataTypeList)//
        jTabbedPaneSystem.add(m.jSplitPaneSystemDataType, res.getString("S216"));
        jTabbedPaneSystem.setIconAt(4, m.imageIconField);
        jSplitPaneSystemDataType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemDataType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemDataType.setDividerLocation(200);
        jSplitPaneSystemDataType.setFocusable(false);
        jScrollPaneSystemDataTypeList.setBorder(null);
        jScrollPaneSystemDataTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemDataTypeList_mouseClicked(e); } });
        jPanelSystemDataType.setLayout(new BorderLayout());
        jPanelSystemDataType.setBorder(null);
        jPanelSystemDataType1.setLayout(null);
        jPanelSystemDataType1.setPreferredSize(new Dimension(10, 74));
        jPanelSystemDataType1.setBorder(BorderFactory.createEtchedBorder());
        jLabelSystemDataTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeName.setText(res.getString("S218"));
        jLabelSystemDataTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemDataTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemDataTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeSortKey.setText(res.getString("S201"));
        jLabelSystemDataTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        jTextFieldSystemDataTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        jLabelSystemSQLExpression.setText(res.getString("S223"));
        jLabelSystemSQLExpression.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemSQLExpression.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemSQLExpression.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemSQLExpression.setBounds(new Rectangle(700, 12, 130, 20));
        jTextFieldSystemSQLExpression.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemSQLExpression.setBounds(new Rectangle(835, 9, 105, 25));
        jLabelSystemDataTypeBasicType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeBasicType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeBasicType.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeBasicType.setText(res.getString("S227"));
        jLabelSystemDataTypeBasicType.setBounds(new Rectangle(5, 43, 130, 20));
        jComboBoxSystemDataTypeBasicType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxSystemDataTypeBasicType.setActionCommand("comboBoxChanged");
        jComboBoxSystemDataTypeBasicType.setBounds(new Rectangle(140, 40, 180, 25));
        comboBoxModelDataTypeBasicType.addElement("SignedNumber");
        comboBoxModelDataTypeBasicType.addElement("UnsignedNumber");
        comboBoxModelDataTypeBasicType.addElement("String");
        comboBoxModelDataTypeBasicType.addElement("Date");
        comboBoxModelDataTypeBasicType.addElement("Boolean");
        comboBoxModelDataTypeBasicType.addElement("Object");
        jLabelSystemDataTypeLength.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeLength.setRequestFocusEnabled(true);
        jLabelSystemDataTypeLength.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeLength.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeLength.setText(res.getString("S237"));
        jLabelSystemDataTypeLength.setBounds(new Rectangle(450, 43, 130, 20));
        jTextFieldSystemDataTypeLength.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeLength.setBounds(new Rectangle(585, 40, 40, 25));
        jTextFieldSystemDataTypeLength.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeDecimal.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemDataTypeDecimal.setRequestFocusEnabled(true);
        jLabelSystemDataTypeDecimal.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemDataTypeDecimal.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemDataTypeDecimal.setText(res.getString("S240"));
        jLabelSystemDataTypeDecimal.setBounds(new Rectangle(700, 43, 130, 20));
        jTextFieldSystemDataTypeDecimal.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemDataTypeDecimal.setBounds(new Rectangle(835, 40, 40, 25));
        jTextFieldSystemDataTypeDecimal.setHorizontalAlignment(SwingConstants.RIGHT);
        jPanelSystemDataType1.add(m.jLabelSystemDataTypeName, null);
        jPanelSystemDataType1.add(m.jTextFieldSystemDataTypeName, null);
        jPanelSystemDataType1.add(m.jLabelSystemDataTypeSortKey, null);
        jPanelSystemDataType1.add(m.jTextFieldSystemDataTypeSortKey, null);
        jPanelSystemDataType1.add(m.jLabelSystemSQLExpression, null);
        jPanelSystemDataType1.add(m.jTextFieldSystemSQLExpression, null);
        jPanelSystemDataType1.add(m.jLabelSystemDataTypeBasicType, null);
        jPanelSystemDataType1.add(m.jComboBoxSystemDataTypeBasicType, null);
        jPanelSystemDataType1.add(m.jLabelSystemDataTypeLength, null);
        jPanelSystemDataType1.add(m.jTextFieldSystemDataTypeLength, null);
        jPanelSystemDataType1.add(m.jLabelSystemDataTypeDecimal, null);
        jPanelSystemDataType1.add(m.jTextFieldSystemDataTypeDecimal, null);
        jSplitPaneSystemDataType.add(m.jScrollPaneSystemDataTypeList, JSplitPane.TOP);
        jSplitPaneSystemDataType.add(m.jPanelSystemDataType, JSplitPane.BOTTOM);
        jPanelSystemDataType.add(m.jPanelSystemDataType1, BorderLayout.NORTH);
        jPanelSystemDataType.add(m.jScrollPaneSystemDataTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemDataTypeList.getViewport().add(m.jTableSystemDataTypeList, null);
        jScrollPaneSystemDataTypeList.setBorder(null);
        jScrollPaneSystemDataTypeWhereUsedList.getViewport()
            .add(m.jTableSystemDataTypeWhereUsedList, null);
        jScrollPaneSystemDataTypeWhereUsedList.setBorder(null);
        //(SystemFunctionTypeList)//
        jTabbedPaneSystem.add(m.jSplitPaneSystemFunctionType, res.getString("S242"));
        jTabbedPaneSystem.setIconAt(5, m.imageIconFunction);
        jSplitPaneSystemFunctionType.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemFunctionType.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneSystemFunctionType.setDividerLocation(200);
        jSplitPaneSystemFunctionType.setFocusable(false);
        jScrollPaneSystemFunctionTypeList.setBorder(null);
        jScrollPaneSystemFunctionTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemFunctionTypeList_mouseClicked(e); } });
        jPanelSystemFunctionType.setLayout(new BorderLayout());
        jPanelSystemFunctionType.setBorder(null);
        jPanelSystemFunctionType1.setLayout(null);
        jPanelSystemFunctionType1.setPreferredSize(new Dimension(10, 98));
        jPanelSystemFunctionType1.setBorder(BorderFactory.createEtchedBorder());
        jLabelSystemFunctionTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemFunctionTypeName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemFunctionTypeName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemFunctionTypeName.setText(res.getString("S244"));
        jLabelSystemFunctionTypeName.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemFunctionTypeName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemFunctionTypeName.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemFunctionTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemFunctionTypeSortKey.setRequestFocusEnabled(true);
        jLabelSystemFunctionTypeSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemFunctionTypeSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemFunctionTypeSortKey.setText(res.getString("S201"));
        jLabelSystemFunctionTypeSortKey.setBounds(new Rectangle(450, 12, 130, 20));
        jTextFieldSystemFunctionTypeSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemFunctionTypeSortKey.setBounds(new Rectangle(585, 9, 105, 25));
        jLabelSystemFunctionTypeDescriptions.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemFunctionTypeDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemFunctionTypeDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemFunctionTypeDescriptions.setText(res.getString("S204"));
        jLabelSystemFunctionTypeDescriptions.setBounds(new Rectangle(5, 43, 130, 20));
        jTextAreaSystemFunctionTypeDescriptions.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemFunctionTypeDescriptions.setLineWrap(true);
        jScrollPaneSystemFunctionTypeDescriptions.setBounds(new Rectangle(140, 40, 700, 48));
        jScrollPaneSystemFunctionTypeDescriptions.getViewport()
            .add(m.jTextAreaSystemFunctionTypeDescriptions, null);
        jPanelSystemFunctionType1.add(m.jLabelSystemFunctionTypeName, null);
        jPanelSystemFunctionType1.add(m.jTextFieldSystemFunctionTypeName, null);
        jPanelSystemFunctionType1.add(m.jLabelSystemFunctionTypeSortKey, null);
        jPanelSystemFunctionType1.add(m.jTextFieldSystemFunctionTypeSortKey, null);
        jPanelSystemFunctionType1.add(m.jLabelSystemFunctionTypeDescriptions, null);
        jPanelSystemFunctionType1.add(m.jScrollPaneSystemFunctionTypeDescriptions, null);
        jSplitPaneSystemFunctionType.add(m.jScrollPaneSystemFunctionTypeList, JSplitPane.TOP);
        jSplitPaneSystemFunctionType.add(m.jPanelSystemFunctionType, JSplitPane.BOTTOM);
        jPanelSystemFunctionType.add(m.jPanelSystemFunctionType1, BorderLayout.NORTH);
        jPanelSystemFunctionType.add(m.jScrollPaneSystemFunctionTypeWhereUsedList, BorderLayout.CENTER);
        jScrollPaneSystemFunctionTypeList.getViewport().add(m.jTableSystemFunctionTypeList, null);
        jScrollPaneSystemFunctionTypeList.setBorder(null);
        jScrollPaneSystemFunctionTypeWhereUsedList.getViewport()
            .add(m.jTableSystemFunctionTypeWhereUsedList, null);
        jScrollPaneSystemFunctionTypeWhereUsedList.setBorder(null);
        //(SystemTermsList)//
        jTabbedPaneSystem.add(m.jSplitPaneSystemTermsList, res.getString("S245"));
        jTabbedPaneSystem.setIconAt(6, m.imageIconBook);
        jSplitPaneSystemTermsList.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemTermsList.setBorder(null);
        jSplitPaneSystemTermsList.setDividerLocation(200);
        jSplitPaneSystemTermsList.setFocusable(false);
        jScrollPaneSystemTermsList.setBorder(null);
        jScrollPaneSystemTermsList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemTermsList_mouseClicked(e); } });
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
        jLabelSystemTermsSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsSortKey.setText(res.getString("S248"));
        jLabelSystemTermsSortKey.setBounds(new Rectangle(460, 12, 130, 20));
        jTextFieldSystemTermsSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTermsSortKey.setBounds(new Rectangle(595, 9, 150, 25));
        jLabelSystemTermsHeadder.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsHeadder.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsHeadder.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsHeadder.setText(res.getString("S246"));
        jLabelSystemTermsHeadder.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemTermsHeadder.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTermsHeadder.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemTermsHtmlFileName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsHtmlFileName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsHtmlFileName.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsHtmlFileName.setText(res.getString("S249"));
        jLabelSystemTermsHtmlFileName.setBounds(new Rectangle(750, 12, 130, 20));
        jTextFieldSystemTermsHtmlFileName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemTermsHtmlFileName.setBounds(new Rectangle(885, 9, 300, 25));
        jLabelSystemTermsDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemTermsDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemTermsDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemTermsDescriptions.setText(res.getString("S247"));
        jLabelSystemTermsDescriptions.setBounds(new Rectangle(5, 2, 130, 20));
        jTextAreaSystemTermsDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemTermsDescriptions.setLineWrap(true);
        jEditorPaneSystemTermsDescriptions.setBorder(BorderFactory.createRaisedBevelBorder());
        jEditorPaneSystemTermsDescriptions.setEditable(false);
        jEditorPaneSystemTermsDescriptions.setContentType("text/html");
        jScrollPaneSystemTermsDescriptions.setBounds(new Rectangle(140, 40, 600, 42));
        jScrollPaneSystemTermsDescriptions.getViewport().add(m.jTextAreaSystemTermsDescriptions, null);
        jPanelSystemTermsList.add(m.jPanelSystemTermsList1, BorderLayout.NORTH);
        jPanelSystemTermsList.add(m.jPanelSystemTermsList2, BorderLayout.CENTER);
        jPanelSystemTermsList1.add(m.jLabelSystemTermsHeadder, null);
        jPanelSystemTermsList1.add(m.jTextFieldSystemTermsHeadder, null);
        jPanelSystemTermsList1.add(m.jLabelSystemTermsSortKey, null);
        jPanelSystemTermsList1.add(m.jTextFieldSystemTermsSortKey, null);
        jPanelSystemTermsList1.add(m.jLabelSystemTermsHtmlFileName, null);
        jPanelSystemTermsList1.add(m.jTextFieldSystemTermsHtmlFileName, null);
        jPanelSystemTermsList2.add(m.jPanelSystemTermsList3, BorderLayout.WEST);
        jPanelSystemTermsList2.add(m.jScrollPaneSystemTermsDescriptions, BorderLayout.CENTER);
        jPanelSystemTermsList3.add(m.jLabelSystemTermsDescriptions, null);
        jScrollPaneSystemTermsList.getViewport().add(m.jTableSystemTermsList, null);
        jSplitPaneSystemTermsList.add(m.jScrollPaneSystemTermsList, JSplitPane.TOP);
        jSplitPaneSystemTermsList.add(m.jPanelSystemTermsList, JSplitPane.BOTTOM);
        //(SystemMaintenanceLog)//
        jTabbedPaneSystem.add(m.jSplitPaneSystemMaintenanceLog, res.getString("S252"));
        jTabbedPaneSystem.setIconAt(7, m.imageIconText);
        jSplitPaneSystemMaintenanceLog.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSystemMaintenanceLog.setBorder(null);
        jSplitPaneSystemMaintenanceLog.setDividerLocation(200);
        jSplitPaneSystemMaintenanceLog.setFocusable(false);
        jScrollPaneSystemMaintenanceLog.setBorder(null);
        jScrollPaneSystemMaintenanceLog.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemMaintenanceLog_mouseClicked(e); } });
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
        jLabelSystemMaintenanceLogHeadder.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemMaintenanceLogHeadder.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemMaintenanceLogHeadder.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemMaintenanceLogHeadder.setText(res.getString("S254"));
        jLabelSystemMaintenanceLogHeadder.setBounds(new Rectangle(5, 12, 130, 20));
        jTextFieldSystemMaintenanceLogHeadder.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemMaintenanceLogHeadder.setBounds(new Rectangle(140, 9, 300, 25));
        jLabelSystemMaintenanceLogSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemMaintenanceLogSortKey.setRequestFocusEnabled(true);
        jLabelSystemMaintenanceLogSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemMaintenanceLogSortKey.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemMaintenanceLogSortKey.setText(res.getString("S257"));
        jLabelSystemMaintenanceLogSortKey.setBounds(new Rectangle(450, 12, 150, 20));
        jTextFieldSystemMaintenanceLogSortKey.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSystemMaintenanceLogSortKey.setBounds(new Rectangle(605, 9, 180, 25));
        jLabelSystemMaintenanceLogDescriptions.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSystemMaintenanceLogDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSystemMaintenanceLogDescriptions.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabelSystemMaintenanceLogDescriptions.setText(res.getString("S260"));
        jLabelSystemMaintenanceLogDescriptions.setBounds(new Rectangle(5, 2, 130, 20));
        jTextAreaSystemMaintenanceLogDescriptions.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSystemMaintenanceLogDescriptions.setLineWrap(true);
        jScrollPaneSystemMaintenanceLogDescriptions.setBounds(new Rectangle(140, 40, 600, 42));
        jScrollPaneSystemMaintenanceLogDescriptions.getViewport()
            .add(m.jTextAreaSystemMaintenanceLogDescriptions, null);
        jPanelSystemMaintenanceLog.add(m.jPanelSystemMaintenanceLog1, BorderLayout.NORTH);
        jPanelSystemMaintenanceLog.add(m.jPanelSystemMaintenanceLog2, BorderLayout.CENTER);
        jPanelSystemMaintenanceLog1.add(m.jLabelSystemMaintenanceLogHeadder, null);
        jPanelSystemMaintenanceLog1.add(m.jTextFieldSystemMaintenanceLogHeadder, null);
        jPanelSystemMaintenanceLog1.add(m.jLabelSystemMaintenanceLogSortKey, null);
        jPanelSystemMaintenanceLog1.add(m.jTextFieldSystemMaintenanceLogSortKey, null);
        jPanelSystemMaintenanceLog2.add(m.jPanelSystemMaintenanceLog3, BorderLayout.WEST);
        jPanelSystemMaintenanceLog2.add(m.jScrollPaneSystemMaintenanceLogDescriptions,
            BorderLayout.CENTER);
        jPanelSystemMaintenanceLog3.add(m.jLabelSystemMaintenanceLogDescriptions, null);
        jScrollPaneSystemMaintenanceLog.getViewport().add(m.jTableSystemMaintenanceLog, null);
        jSplitPaneSystemMaintenanceLog.add(m.jScrollPaneSystemMaintenanceLog, JSplitPane.TOP);
        jSplitPaneSystemMaintenanceLog.add(m.jPanelSystemMaintenanceLog, JSplitPane.BOTTOM);
        //(jTableSystemDepartmentList)//
        jTableSystemDepartmentList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemDepartmentList.setBackground(SystemColor.control);
        jTableSystemDepartmentList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemDepartmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemDepartmentList.getSelectionModel().addListSelectionListener(
            e -> m.jTableSystemDepartmentList_valueChanged(e));
        jTableSystemDepartmentList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemDepartmentList_mouseClicked(e); } });
        jTableSystemDepartmentList.addFocusListener(new Modeler_FocusListener());
        jTableSystemDepartmentList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemDepartmentList.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemDepartmentList.addColumn("No.");
        tableModelSystemDepartmentList.addColumn(res.getString("S201"));
        tableModelSystemDepartmentList.addColumn(res.getString("S265"));
        tableModelSystemDepartmentList.addColumn(res.getString("S204"));
        m.column0 = jTableSystemDepartmentList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemDepartmentList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemDepartmentList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemDepartmentList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemDepartmentList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemDepartmentList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemDepartmentWhereUsedList)//
        jTableSystemDepartmentWhereUsedList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemDepartmentWhereUsedList.setBackground(SystemColor.control);
        jTableSystemDepartmentWhereUsedList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemDepartmentWhereUsedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemDepartmentWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemDepartmentWhereUsedList_mouseClicked(e); } });
        jTableSystemDepartmentWhereUsedList.addFocusListener(new Modeler_FocusListener());
        jTableSystemDepartmentWhereUsedList.setRowHeight(m.TABLE_ROW_HEIGHT);
        jScrollPaneSystemDepartmentWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemDepartmentWhereUsedList_mouseClicked(e); } });
        tableModelSystemDepartmentWhereUsedList.addColumn("No.");
        tableModelSystemDepartmentWhereUsedList.addColumn("ID");
        tableModelSystemDepartmentWhereUsedList.addColumn(res.getString("S271"));
        tableModelSystemDepartmentWhereUsedList.addColumn(res.getString("S204"));
        m.column0 = jTableSystemDepartmentWhereUsedList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemDepartmentWhereUsedList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemDepartmentWhereUsedList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemDepartmentWhereUsedList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(60);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemDepartmentWhereUsedList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemDepartmentWhereUsedList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemTaskTypeList)//
        jTableSystemTaskTypeList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemTaskTypeList.setBackground(SystemColor.control);
        jTableSystemTaskTypeList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemTaskTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemTaskTypeList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableSystemTaskTypeList_valueChanged(e));
        jTableSystemTaskTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemTaskTypeList_mouseClicked(e); } });
        jTableSystemTaskTypeList.addFocusListener(new Modeler_FocusListener());
        jTableSystemTaskTypeList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemTaskTypeList.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemTaskTypeList.addColumn("No.");
        tableModelSystemTaskTypeList.addColumn(res.getString("S201"));
        tableModelSystemTaskTypeList.addColumn(res.getString("S362"));
        tableModelSystemTaskTypeList.addColumn(res.getString("S204"));
        m.column0 = jTableSystemTaskTypeList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemTaskTypeList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemTaskTypeList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemTaskTypeList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemTaskTypeList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemTaskTypeList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemTaskTypeWhereUsedList)//
        jTableSystemTaskTypeWhereUsedList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemTaskTypeWhereUsedList.setBackground(SystemColor.control);
        jTableSystemTaskTypeWhereUsedList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemTaskTypeWhereUsedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemTaskTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemTaskTypeWhereUsedList_mouseClicked(e); } });
        jTableSystemTaskTypeWhereUsedList.addFocusListener(new Modeler_FocusListener());
        jTableSystemTaskTypeWhereUsedList.setRowHeight(m.TABLE_ROW_HEIGHT);
        jScrollPaneSystemTaskTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemTaskTypeWhereUsedList_mouseClicked(e); } });
        tableModelSystemTaskTypeWhereUsedList.addColumn("No.");
        tableModelSystemTaskTypeWhereUsedList.addColumn("ID");
        tableModelSystemTaskTypeWhereUsedList.addColumn(res.getString("S363"));
        tableModelSystemTaskTypeWhereUsedList.addColumn(res.getString("S340"));
        tableModelSystemTaskTypeWhereUsedList.addColumn(res.getString("S204"));
        m.column0 = jTableSystemTaskTypeWhereUsedList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemTaskTypeWhereUsedList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemTaskTypeWhereUsedList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemTaskTypeWhereUsedList.getColumnModel().getColumn(3);
        m.column4 = jTableSystemTaskTypeWhereUsedList.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(60);
        m.column2.setPreferredWidth(250);
        m.column3.setPreferredWidth(250);
        m.column4.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemTaskTypeWhereUsedList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemTaskTypeWhereUsedList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemTableTypeList)//
        jTableSystemTableTypeList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemTableTypeList.setBackground(SystemColor.control);
        jTableSystemTableTypeList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemTableTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemTableTypeList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableSystemTableTypeList_valueChanged(e));
        jTableSystemTableTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemTableTypeList_mouseClicked(e); } });
        jTableSystemTableTypeList.addFocusListener(new Modeler_FocusListener());
        jTableSystemTableTypeList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemTableTypeList.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemTableTypeList.addColumn("No.");
        tableModelSystemTableTypeList.addColumn(res.getString("S201"));
        tableModelSystemTableTypeList.addColumn(res.getString("S277"));
        tableModelSystemTableTypeList.addColumn(res.getString("S204"));
        m.column0 = jTableSystemTableTypeList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemTableTypeList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemTableTypeList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemTableTypeList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemTableTypeList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemTableTypeList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemTableTypeWhereUsedList)//
        jTableSystemTableTypeWhereUsedList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemTableTypeWhereUsedList.setBackground(SystemColor.control);
        jTableSystemTableTypeWhereUsedList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemTableTypeWhereUsedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemTableTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemTableTypeWhereUsedList_mouseClicked(e); } });
        jTableSystemTableTypeWhereUsedList.addFocusListener(new Modeler_FocusListener());
        jTableSystemTableTypeWhereUsedList.setRowHeight(m.TABLE_ROW_HEIGHT);
        jScrollPaneSystemTableTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemTableTypeWhereUsedList_mouseClicked(e); } });
        tableModelSystemTableTypeWhereUsedList.addColumn("No.");
        tableModelSystemTableTypeWhereUsedList.addColumn("ID");
        tableModelSystemTableTypeWhereUsedList.addColumn(res.getString("S283"));
        tableModelSystemTableTypeWhereUsedList.addColumn(res.getString("S284"));
        m.column0 = jTableSystemTableTypeWhereUsedList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemTableTypeWhereUsedList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemTableTypeWhereUsedList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemTableTypeWhereUsedList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(250);
        m.column3.setPreferredWidth(350);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemTableTypeWhereUsedList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemTableTypeWhereUsedList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemDataTypeList)//
        jTableSystemDataTypeList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemDataTypeList.setBackground(SystemColor.control);
        jTableSystemDataTypeList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemDataTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemDataTypeList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableSystemDataTypeList_valueChanged(e));
        jTableSystemDataTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemDataTypeList_mouseClicked(e); } });
        jTableSystemDataTypeList.addFocusListener(new Modeler_FocusListener());
        jTableSystemDataTypeList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemDataTypeList.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemDataTypeList.addColumn("No.");
        tableModelSystemDataTypeList.addColumn(res.getString("S201"));
        tableModelSystemDataTypeList.addColumn(res.getString("S289"));
        tableModelSystemDataTypeList.addColumn(res.getString("S290"));
        tableModelSystemDataTypeList.addColumn(res.getString("S291"));
        tableModelSystemDataTypeList.addColumn(res.getString("S292"));
        tableModelSystemDataTypeList.addColumn(res.getString("S293"));
        m.column0 = jTableSystemDataTypeList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemDataTypeList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemDataTypeList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemDataTypeList.getColumnModel().getColumn(3);
        m.column4 = jTableSystemDataTypeList.getColumnModel().getColumn(4);
        m.column5 = jTableSystemDataTypeList.getColumnModel().getColumn(5);
        m.column6 = jTableSystemDataTypeList.getColumnModel().getColumn(6);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(130);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(150);
        m.column4.setPreferredWidth(50);
        m.column5.setPreferredWidth(65);
        m.column6.setPreferredWidth(150);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(rendererAlignmentRight);
        m.column5.setCellRenderer(rendererAlignmentRight);
        m.column6.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemDataTypeList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemDataTypeList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemDataTypeWhereUsedList)//
        jTableSystemDataTypeWhereUsedList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemDataTypeWhereUsedList.setBackground(SystemColor.control);
        jTableSystemDataTypeWhereUsedList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemDataTypeWhereUsedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemDataTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemDataTypeWhereUsedList_mouseClicked(e); } });
        jTableSystemDataTypeWhereUsedList.addFocusListener(new Modeler_FocusListener());
        jTableSystemDataTypeWhereUsedList.setRowHeight(m.TABLE_ROW_HEIGHT);
        jScrollPaneSystemDataTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemDataTypeWhereUsedList_mouseClicked(e); } });
        tableModelSystemDataTypeWhereUsedList.addColumn("No.");
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S297"));
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S298"));
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S299"));
        tableModelSystemDataTypeWhereUsedList.addColumn(res.getString("S300"));
        m.column0 = jTableSystemDataTypeWhereUsedList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemDataTypeWhereUsedList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemDataTypeWhereUsedList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemDataTypeWhereUsedList.getColumnModel().getColumn(3);
        m.column4 = jTableSystemDataTypeWhereUsedList.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(130);
        m.column2.setPreferredWidth(150);
        m.column3.setPreferredWidth(300);
        m.column4.setPreferredWidth(250);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemDataTypeWhereUsedList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemDataTypeWhereUsedList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemFunctionTypeList)//
        jTableSystemFunctionTypeList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemFunctionTypeList.setBackground(SystemColor.control);
        jTableSystemFunctionTypeList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemFunctionTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemFunctionTypeList.getSelectionModel().addListSelectionListener(
            e -> m.jTableSystemFunctionTypeList_valueChanged(e));
        jTableSystemFunctionTypeList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemFunctionTypeList_mouseClicked(e); } });
        jTableSystemFunctionTypeList.addFocusListener(new Modeler_FocusListener());
        jTableSystemFunctionTypeList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemFunctionTypeList.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemFunctionTypeList.addColumn("No.");
        tableModelSystemFunctionTypeList.addColumn(res.getString("S201"));
        tableModelSystemFunctionTypeList.addColumn(res.getString("S305"));
        tableModelSystemFunctionTypeList.addColumn(res.getString("S204"));
        m.column0 = jTableSystemFunctionTypeList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemFunctionTypeList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemFunctionTypeList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemFunctionTypeList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(200);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemFunctionTypeList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemFunctionTypeList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemFunctionTypeWhereUsedList)//
        jTableSystemFunctionTypeWhereUsedList.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemFunctionTypeWhereUsedList.setBackground(SystemColor.control);
        jTableSystemFunctionTypeWhereUsedList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemFunctionTypeWhereUsedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemFunctionTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemFunctionTypeWhereUsedList_mouseClicked(e); } });
        jTableSystemFunctionTypeWhereUsedList.addFocusListener(new Modeler_FocusListener());
        jTableSystemFunctionTypeWhereUsedList.setRowHeight(m.TABLE_ROW_HEIGHT);
        jScrollPaneSystemFunctionTypeWhereUsedList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSystemFunctionTypeWhereUsedList_mouseClicked(e); } });
        tableModelSystemFunctionTypeWhereUsedList.addColumn("No.");
        tableModelSystemFunctionTypeWhereUsedList.addColumn("ID");
        tableModelSystemFunctionTypeWhereUsedList.addColumn(res.getString("S311"));
        tableModelSystemFunctionTypeWhereUsedList.addColumn(res.getString("S312"));
        tableModelSystemFunctionTypeWhereUsedList.addColumn(res.getString("S313"));
        m.column0 = jTableSystemFunctionTypeWhereUsedList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemFunctionTypeWhereUsedList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemFunctionTypeWhereUsedList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemFunctionTypeWhereUsedList.getColumnModel().getColumn(3);
        m.column4 = jTableSystemFunctionTypeWhereUsedList.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(90);
        m.column2.setPreferredWidth(350);
        m.column3.setPreferredWidth(250);
        m.column4.setPreferredWidth(600);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemFunctionTypeWhereUsedList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemFunctionTypeWhereUsedList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemTermsList)//
        jTableSystemTermsList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemTermsList.setBackground(SystemColor.control);
        jTableSystemTermsList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemTermsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemTermsList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableSystemTermsList_valueChanged(e));
        jTableSystemTermsList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemTermsList_mouseClicked(e); } });
        jTableSystemTermsList.addFocusListener(new Modeler_FocusListener());
        jTableSystemTermsList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemTermsList.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemTermsList.addColumn("No.");
        tableModelSystemTermsList.addColumn(res.getString("S248"));
        tableModelSystemTermsList.addColumn(res.getString("S246"));
        tableModelSystemTermsList.addColumn(res.getString("S247"));
        tableModelSystemTermsList.addColumn(res.getString("S249"));
        m.column0 = jTableSystemTermsList.getColumnModel().getColumn(0);
        m.column1 = jTableSystemTermsList.getColumnModel().getColumn(1);
        m.column2 = jTableSystemTermsList.getColumnModel().getColumn(2);
        m.column3 = jTableSystemTermsList.getColumnModel().getColumn(3);
        m.column4 = jTableSystemTermsList.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(150);
        m.column2.setPreferredWidth(300);
        m.column3.setPreferredWidth(550);
        m.column4.setPreferredWidth(300);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemTermsList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemTermsList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableSystemMaintenanceLog)//
        jTableSystemMaintenanceLog.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableSystemMaintenanceLog.setBackground(SystemColor.control);
        jTableSystemMaintenanceLog.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSystemMaintenanceLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableSystemMaintenanceLog.getSelectionModel().addListSelectionListener(
            e -> m.jTableSystemMaintenanceLog_valueChanged(e));
        jTableSystemMaintenanceLog.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSystemMaintenanceLog_mouseClicked(e); } });
        jTableSystemMaintenanceLog.addFocusListener(new Modeler_FocusListener());
        jTableSystemMaintenanceLog.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        jTableSystemMaintenanceLog.setRowHeight(m.TABLE_ROW_HEIGHT);
        tableModelSystemMaintenanceLog.addColumn("No.");
        tableModelSystemMaintenanceLog.addColumn(res.getString("S317"));
        tableModelSystemMaintenanceLog.addColumn(res.getString("S318"));
        tableModelSystemMaintenanceLog.addColumn(res.getString("S319"));
        m.column0 = jTableSystemMaintenanceLog.getColumnModel().getColumn(0);
        m.column1 = jTableSystemMaintenanceLog.getColumnModel().getColumn(1);
        m.column2 = jTableSystemMaintenanceLog.getColumnModel().getColumn(2);
        m.column3 = jTableSystemMaintenanceLog.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(150);
        m.column2.setPreferredWidth(130);
        m.column3.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableSystemMaintenanceLog.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableSystemMaintenanceLog.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
