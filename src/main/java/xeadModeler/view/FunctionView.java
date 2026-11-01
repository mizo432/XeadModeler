package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * 機能ノードのビュー
 * 機能情報、IOリスト、使用された機能、使用している機能、タスクなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class FunctionView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelFunction = new JPanel();
    private final JPanel jPanelFunction1 = new JPanel();
    private final JPanel jPanelFunction2 = new JPanel();
    private final JPanel jPanelFunction3 = new JPanel();
    private final JPanel jPanelFunction4 = new JPanel();
    private final JSplitPane jSplitPaneFunction = new JSplitPane();
    private final JScrollPane jScrollPaneFunction1 = new JScrollPane();
    
    // Labels
    private final JLabel jLabelFunctionName = new JLabel();
    private final JLabel jLabelFunctionSortKey = new JLabel();
    private final JLabel jLabelFunctionType = new JLabel();
    private final JLabel jLabelFunctionSummary = new JLabel();
    private final JLabel jLabelFunctionParameters = new JLabel();
    private final JLabel jLabelFunctionDocFile = new JLabel();
    private final JLabel jLabelFunctionDescriptions = new JLabel();
    
    // Fields
    private final JTextField jTextFieldFunctionName = new JTextField();
    private final JTextField jTextFieldFunctionSortKey = new JTextField();
    private final JTextField jTextFieldFunctionSummary = new JTextField();
    private final JTextField jTextFieldFunctionParameters = new JTextField();
    private final JTextField jTextFieldFunctionReturn = new JTextField();
    private final JButton jButtonFunctionDocFile = new JButton();
    
    // Combo boxes
    private Modeler.SortableXeadTreeNodeComboBoxModel sortableComboBoxModelFunctionType;
    private final JComboBox jComboBoxFunctionType = new JComboBox();
    private final JComboBox jComboBoxFunctionDocFile = new JComboBox();
    
    // Descriptions
    private final JScrollPane jScrollPaneFunctionDescriptions = new JScrollPane();
    private final JTextArea jTextAreaFunctionDescriptions = new JTextArea();
    
    // Tabbed pane
    private final JTabbedPane jTabbedPaneFunction = new JTabbedPane();
    private final JScrollPane jScrollPaneFunctionIOList = new JScrollPane();
    private final JScrollPane jScrollPaneFunctionsUsedByThis = new JScrollPane();
    private final JScrollPane jScrollPaneFunctionsUsingThis = new JScrollPane();
    private final JScrollPane jScrollPaneTasksUsingThis = new JScrollPane();
    private final JViewport jViewportFunctionIOList = new JViewport();
    private final JViewport jViewportFunctionsUsedByThis = new JViewport();
    
    // Tables
    private final JTable jTableFunctionIOList;
    private final JTable jTableFunctionsUsedByThis;
    private final JTable jTableFunctionsUsingThis;
    private final JTable jTableTasksUsingThis;
    
    // State
    private int selectedRow_jTableFunctionIOList;
    private int selectedRow_jTableFunctionsUsedByThis;
    private int selectedRow_jTableFunctionsUsingThis;
    private int selectedRow_jTableTasksUsingThis;
    
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
    
    private Modeler modeler;
    
    /**
     * 機能操作リスナーインターフェース
     */
    public interface FunctionListener {
        void onFunctionChanged();
    }
    
    private FunctionListener functionListener;
    
    public FunctionView(Modeler modeler, FunctionListener listener) {
        this.modeler = modeler;
        this.functionListener = listener;
        jTableFunctionIOList = modeler.jTableFunctionIOList;
        jTableFunctionsUsedByThis = modeler.jTableFunctionsUsedByThis;
        jTableFunctionsUsingThis = modeler.jTableFunctionsUsingThis;
        jTableTasksUsingThis = modeler.jTableTasksUsingThis;
        sortableComboBoxModelFunctionType = modeler.sortableComboBoxModelFunctionType;
        jComboBoxFunctionType.setModel(sortableComboBoxModelFunctionType);
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelFunction;
    }
    
    public JTabbedPane getTabbedPane() {
        return jTabbedPaneFunction;
    }
    
    public JTable getIOList() {
        return jTableFunctionIOList;
    }
    
    public JTable getFunctionsUsedByThis() {
        return jTableFunctionsUsedByThis;
    }
    
    public JTable getFunctionsUsingThis() {
        return jTableFunctionsUsingThis;
    }
    
    public JTable getTasksUsingThis() {
        return jTableTasksUsingThis;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jSplitPaneFunction.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneFunction.setBorder(null);
        jSplitPaneFunction.setBottomComponent(jTabbedPaneFunction);
        jSplitPaneFunction.setDividerLocation(300);
        jPanelFunction4.setPreferredSize(new Dimension(140, 10));
        jPanelFunction4.setLayout(null);
        jPanelFunction2.setBorder(null);
        jLabelFunctionName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionName.setText(res.getString("S664"));
        jLabelFunctionName.setBounds(new Rectangle(5, 8, 130, 20));
        jTextFieldFunctionName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionName.setBounds(new Rectangle(0, 5, 300, 25));
        jLabelFunctionSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionSortKey.setText(res.getString("S201"));
        jLabelFunctionSortKey.setBounds(new Rectangle(310, 8, 130, 20));
        jTextFieldFunctionSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionSortKey.setBounds(new Rectangle(445, 5, 200, 25));
        jLabelFunctionType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionType.setRequestFocusEnabled(true);
        jLabelFunctionType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionType.setText(res.getString("S662"));
        jLabelFunctionType.setBounds(new Rectangle(650, 8, 110, 20));
        jComboBoxFunctionType.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxFunctionType.setActionCommand("comboBoxChanged");
        jComboBoxFunctionType.setBounds(new Rectangle(765, 6, 250, 25));
        jComboBoxFunctionType.setMaximumRowCount(40);
        jLabelFunctionSummary.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionSummary.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionSummary.setText(res.getString("S668"));
        jLabelFunctionSummary.setBounds(new Rectangle(5, 39, 130, 20));
        jTextFieldFunctionSummary.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionSummary.setBounds(new Rectangle(0, 36, 1015, 25));
        jLabelFunctionParameters.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionParameters.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionParameters.setText(res.getString("S666"));
        jLabelFunctionParameters.setBounds(new Rectangle(5, 70, 130, 20));
        jTextFieldFunctionParameters.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionParameters.setBounds(new Rectangle(0, 67, 360, 25));
        jTextFieldFunctionReturn.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionReturn.setBounds(new Rectangle(365, 67, 280, 25));
        jLabelFunctionDocFile.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionDocFile.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionDocFile.setText(res.getString("S667"));
        jLabelFunctionDocFile.setBounds(new Rectangle(650, 70, 110, 20));
        jComboBoxFunctionDocFile.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, 12));
        jComboBoxFunctionDocFile.setBounds(new Rectangle(765, 67, 340, 25));
        jButtonFunctionDocFile.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jButtonFunctionDocFile.setBounds(new Rectangle(1105, 66, 70, 27));
        jButtonFunctionDocFile.setText("Edit");
        jPanelFunction3.setLayout(null);
        jPanelFunction3.setMinimumSize(new Dimension(100, 99));
        jPanelFunction3.setPreferredSize(new Dimension(10, 99));
        jLabelFunctionDescriptions.setBounds(new Rectangle(5, 101, 130, 20));
        jLabelFunctionDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionDescriptions.setText(res.getString("S204"));
        jTextAreaFunctionDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaFunctionDescriptions.setLineWrap(true);
        jTabbedPaneFunction.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTabbedPaneFunction.setMinimumSize(new Dimension(100, 2));
        jPanelFunction2.setLayout(new BorderLayout());
        jPanelFunction2.setMinimumSize(new Dimension(100, 20));
        jPanelFunction2.setPreferredSize(new Dimension(100, 80));
        jScrollPaneFunction1.setBorder(null);
        jScrollPaneFunction1.getViewport().add(jPanelFunction1);
        jPanelFunction1.setLayout(new BorderLayout());
        jPanelFunction1.setBorder(BorderFactory.createEtchedBorder());
        jPanelFunction1.setMinimumSize(new Dimension(100, 30));
        jPanelFunction1.setPreferredSize(new Dimension(1330, 80));
        jPanelFunction.setLayout(new BorderLayout());
        jPanelFunction.add(jSplitPaneFunction, BorderLayout.CENTER);
        jPanelFunction1.add(jPanelFunction4, BorderLayout.WEST);
        jPanelFunction1.add(jPanelFunction2, BorderLayout.CENTER);
        jSplitPaneFunction.add(jScrollPaneFunction1, JSplitPane.TOP);
        jSplitPaneFunction.add(jTabbedPaneFunction, JSplitPane.BOTTOM);
        jPanelFunction2.add(jPanelFunction3, BorderLayout.NORTH);
        jPanelFunction2.add(jScrollPaneFunctionDescriptions, BorderLayout.CENTER);
        jPanelFunction3.add(jLabelFunctionType, null);
        jPanelFunction3.add(jComboBoxFunctionType, null);
        jPanelFunction3.add(jTextFieldFunctionSummary, null);
        jPanelFunction3.add(jTextFieldFunctionParameters, null);
        jPanelFunction3.add(jTextFieldFunctionReturn, null);
        jPanelFunction3.add(jComboBoxFunctionDocFile, null);
        jPanelFunction3.add(jButtonFunctionDocFile, null);
        jPanelFunction3.add(jTextFieldFunctionName, null);
        jPanelFunction3.add(jLabelFunctionSortKey, null);
        jPanelFunction3.add(jTextFieldFunctionSortKey, null);
        jPanelFunction3.add(jLabelFunctionDocFile, null);
        jPanelFunction4.add(jLabelFunctionName, null);
        jPanelFunction4.add(jLabelFunctionParameters, null);
        jPanelFunction4.add(jLabelFunctionSummary, null);
        jPanelFunction4.add(jLabelFunctionDescriptions, null);
        jTabbedPaneFunction.addTab(res.getString("S673"), modeler.imageIconIOPanel, jScrollPaneFunctionIOList);
        jTabbedPaneFunction.addTab(res.getString("S674"), modeler.imageIconFunction,
            jScrollPaneFunctionsUsedByThis);
        jTabbedPaneFunction.addTab(res.getString("S675"), modeler.imageIconFunction,
            jScrollPaneFunctionsUsingThis);
        jTabbedPaneFunction.addTab(res.getString("S676"), modeler.imageIconTask, jScrollPaneTasksUsingThis);
        jScrollPaneTasksUsingThis.getViewport().add(jTableTasksUsingThis, null);
        jScrollPaneFunctionsUsingThis.getViewport().add(jTableFunctionsUsingThis, null);
        jViewportFunctionsUsedByThis.add(jTableFunctionsUsedByThis, null);
        jScrollPaneFunctionsUsedByThis.setViewport(jViewportFunctionsUsedByThis);
        jViewportFunctionIOList.add(jTableFunctionIOList, null);
        jScrollPaneFunctionIOList.setViewport(jViewportFunctionIOList);
        jScrollPaneFunctionDescriptions.getViewport().add(jTextAreaFunctionDescriptions, null);
        jTableFunctionIOList.setBackground(SystemColor.control);
        jTableFunctionIOList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionIOList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionIOList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFunctionIOList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        modeler.tableModelFunctionIOList.addColumn("No.");
        modeler.tableModelFunctionIOList.addColumn(res.getString("S680"));
        modeler.tableModelFunctionIOList.addColumn(res.getString("S204"));
        column0 = jTableFunctionIOList.getColumnModel().getColumn(0);
        column1 = jTableFunctionIOList.getColumnModel().getColumn(1);
        column2 = jTableFunctionIOList.getColumnModel().getColumn(2);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(400);
        column2.setPreferredWidth(450);
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        jTableFunctionIOList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableFunctionIOList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableFunctionsUsedByThis)//
        jTableFunctionsUsedByThis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionsUsedByThis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFunctionsUsedByThis.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionsUsedByThis.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        //(jTableFunctionsUsingThis)//
        jTableFunctionsUsingThis.setBackground(SystemColor.control);
        jTableFunctionsUsingThis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionsUsingThis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFunctionsUsingThis.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionsUsingThis.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        modeler.tableModelFunctionsUsingThis.addColumn("No.");
        modeler.tableModelFunctionsUsingThis.addColumn(res.getString("S436"));
        modeler.tableModelFunctionsUsingThis.addColumn("ID");
        modeler.tableModelFunctionsUsingThis.addColumn(res.getString("S688"));
        modeler.tableModelFunctionsUsingThis.addColumn(res.getString("S689"));
        column0 = jTableFunctionsUsingThis.getColumnModel().getColumn(0);
        column1 = jTableFunctionsUsingThis.getColumnModel().getColumn(1);
        column2 = jTableFunctionsUsingThis.getColumnModel().getColumn(2);
        column3 = jTableFunctionsUsingThis.getColumnModel().getColumn(3);
        column4 = jTableFunctionsUsingThis.getColumnModel().getColumn(4);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(300);
        column2.setPreferredWidth(150);
        column3.setPreferredWidth(300);
        column4.setPreferredWidth(600);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentLeft);
        jTableFunctionsUsingThis.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableFunctionsUsingThis.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableTasksUsingThis)//
        jTableTasksUsingThis.setBackground(SystemColor.control);
        jTableTasksUsingThis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTasksUsingThis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTasksUsingThis.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTasksUsingThis.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        modeler.tableModelTasksUsingThis.addColumn("No.");
        modeler.tableModelTasksUsingThis.addColumn(res.getString("S693"));
        modeler.tableModelTasksUsingThis.addColumn(res.getString("S694"));
        modeler.tableModelTasksUsingThis.addColumn("ID");
        modeler.tableModelTasksUsingThis.addColumn(res.getString("S696"));
        modeler.tableModelTasksUsingThis.addColumn(res.getString("S697"));
        column0 = jTableTasksUsingThis.getColumnModel().getColumn(0);
        column1 = jTableTasksUsingThis.getColumnModel().getColumn(1);
        column2 = jTableTasksUsingThis.getColumnModel().getColumn(2);
        column3 = jTableTasksUsingThis.getColumnModel().getColumn(3);
        column4 = jTableTasksUsingThis.getColumnModel().getColumn(4);
        column5 = jTableTasksUsingThis.getColumnModel().getColumn(5);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(200);
        column2.setPreferredWidth(150);
        column3.setPreferredWidth(100);
        column4.setPreferredWidth(300);
        column5.setPreferredWidth(600);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentLeft);
        column5.setCellRenderer(rendererAlignmentLeft);
        jTableTasksUsingThis.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTasksUsingThis.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
