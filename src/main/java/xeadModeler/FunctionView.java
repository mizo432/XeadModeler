package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import xeadModeler.view.SortableXeadTreeNodeComboBoxModel;
import javax.swing.event.TableColumnModelListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

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
    private SortableXeadTreeNodeComboBoxModel sortableComboBoxModelFunctionType;
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

    /**
     * ModelerのFunctionコンポーネントを初期化します。
     */
    public void initComponents(Modeler m) {
        jSplitPaneFunction.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneFunction.setBorder(null);
        jSplitPaneFunction.setBottomComponent(m.jTabbedPaneFunction);
        jSplitPaneFunction.setDividerLocation(300);
        jPanelFunction4.setPreferredSize(new Dimension(140, 10));
        jPanelFunction4.setLayout(null);
        jPanelFunction2.setBorder(null);
        jLabelFunctionName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionName.setText(res.getString("S664"));
        jLabelFunctionName.setBounds(new Rectangle(5, 8, 130, 20));
        jTextFieldFunctionName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionName.setBounds(new Rectangle(0, 5, 300, 25));
        jLabelFunctionSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionSortKey.setText(res.getString("S201"));
        jLabelFunctionSortKey.setBounds(new Rectangle(310, 8, 130, 20));
        jTextFieldFunctionSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionSortKey.setBounds(new Rectangle(445, 5, 200, 25));
        jLabelFunctionType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionType.setRequestFocusEnabled(true);
        jLabelFunctionType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionType.setText(res.getString("S662"));
        jLabelFunctionType.setBounds(new Rectangle(650, 8, 110, 20));
        jComboBoxFunctionType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxFunctionType.setActionCommand("comboBoxChanged");
        jComboBoxFunctionType.setModel(sortableComboBoxModelFunctionType);
        jComboBoxFunctionType.setBounds(new Rectangle(765, 6, 250, 25));
        jComboBoxFunctionType.setMaximumRowCount(40);
        jLabelFunctionSummary.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionSummary.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionSummary.setText(res.getString("S668"));
        jLabelFunctionSummary.setBounds(new Rectangle(5, 39, 130, 20));
        jTextFieldFunctionSummary.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionSummary.setBounds(new Rectangle(0, 36, 1015, 25));
        jLabelFunctionParameters.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionParameters.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionParameters.setText(res.getString("S666"));
        jLabelFunctionParameters.setBounds(new Rectangle(5, 70, 130, 20));
        jTextFieldFunctionParameters.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionParameters.setBounds(new Rectangle(0, 67, 360, 25));
        jTextFieldFunctionReturn.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldFunctionReturn.setBounds(new Rectangle(365, 67, 280, 25));
        jLabelFunctionDocFile.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionDocFile.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionDocFile.setText(res.getString("S667"));
        jLabelFunctionDocFile.setBounds(new Rectangle(650, 70, 110, 20));
        jComboBoxFunctionDocFile.setFont(new java.awt.Font(m.mainFontName, 0, 12));
        jComboBoxFunctionDocFile.setBounds(new Rectangle(765, 67, 340, 25));
        jComboBoxFunctionDocFile.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jComboBoxFunctionDocFile_mouseClicked(e); } public void mouseEntered(MouseEvent e) { m.jComboBoxFunctionDocFile_mouseEntered(e); } public void mouseExited(MouseEvent e) { m.jComboBoxFunctionDocFile_mouseExited(e); } });
        jButtonFunctionDocFile.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jButtonFunctionDocFile.setBounds(new Rectangle(1105, 66, 70, 27));
        jButtonFunctionDocFile.setText("Edit");
        jButtonFunctionDocFile.addActionListener(
            e -> m.jButtonFunctionDocFile_actionPerformed(e));
        jPanelFunction3.setLayout(null);
        jPanelFunction3.setMinimumSize(new Dimension(100, 99));
        jPanelFunction3.setPreferredSize(new Dimension(10, 99));
        jLabelFunctionDescriptions.setBounds(new Rectangle(5, 101, 130, 20));
        jLabelFunctionDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelFunctionDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunctionDescriptions.setText(res.getString("S204"));
        jTextAreaFunctionDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaFunctionDescriptions.setLineWrap(true);
        jTabbedPaneFunction.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTabbedPaneFunction.setMinimumSize(new Dimension(100, 2));
        jPanelFunction2.setLayout(new BorderLayout());
        jPanelFunction2.setMinimumSize(new Dimension(100, 20));
        jPanelFunction2.setPreferredSize(new Dimension(100, 80));
        jScrollPaneFunction1.setBorder(null);
        jScrollPaneFunction1.getViewport().add(m.jPanelFunction1);
        jPanelFunction1.setLayout(new BorderLayout());
        jPanelFunction1.setBorder(BorderFactory.createEtchedBorder());
        jPanelFunction1.setMinimumSize(new Dimension(100, 30));
        jPanelFunction1.setPreferredSize(new Dimension(1330, 80));
        jPanelFunction.setLayout(new BorderLayout());
        jPanelFunction.add(m.jSplitPaneFunction, BorderLayout.CENTER);
        jPanelFunction1.add(m.jPanelFunction4, BorderLayout.WEST);
        jPanelFunction1.add(m.jPanelFunction2, BorderLayout.CENTER);
        jSplitPaneFunction.add(m.jScrollPaneFunction1, JSplitPane.TOP);
        jSplitPaneFunction.add(m.jTabbedPaneFunction, JSplitPane.BOTTOM);
        jPanelFunction2.add(m.jPanelFunction3, BorderLayout.NORTH);
        jPanelFunction2.add(m.jScrollPaneFunctionDescriptions, BorderLayout.CENTER);
        jPanelFunction3.add(m.jLabelFunctionType, null);
        jPanelFunction3.add(m.jComboBoxFunctionType, null);
        jPanelFunction3.add(m.jTextFieldFunctionSummary, null);
        jPanelFunction3.add(m.jTextFieldFunctionParameters, null);
        jPanelFunction3.add(m.jTextFieldFunctionReturn, null);
        jPanelFunction3.add(m.jComboBoxFunctionDocFile, null);
        jPanelFunction3.add(m.jButtonFunctionDocFile, null);
        jPanelFunction3.add(m.jTextFieldFunctionName, null);
        jPanelFunction3.add(m.jLabelFunctionSortKey, null);
        jPanelFunction3.add(m.jTextFieldFunctionSortKey, null);
        jPanelFunction3.add(m.jLabelFunctionDocFile, null);
        jPanelFunction4.add(m.jLabelFunctionName, null);
        jPanelFunction4.add(m.jLabelFunctionParameters, null);
        jPanelFunction4.add(m.jLabelFunctionSummary, null);
        jPanelFunction4.add(m.jLabelFunctionDescriptions, null);
        jTabbedPaneFunction.addTab(res.getString("S673"), m.imageIconIOPanel, jScrollPaneFunctionIOList);
        jTabbedPaneFunction.addTab(res.getString("S674"), m.imageIconFunction,
            jScrollPaneFunctionsUsedByThis);
        jTabbedPaneFunction.addTab(res.getString("S675"), m.imageIconFunction,
            jScrollPaneFunctionsUsingThis);
        jTabbedPaneFunction.addTab(res.getString("S676"), m.imageIconTask, jScrollPaneTasksUsingThis);
        jScrollPaneTasksUsingThis.getViewport().add(m.jTableTasksUsingThis, null);
        jScrollPaneFunctionsUsingThis.getViewport().add(m.jTableFunctionsUsingThis, null);
        jViewportFunctionsUsedByThis.add(m.jTableFunctionsUsedByThis, null);
        jScrollPaneFunctionsUsedByThis.setViewport(m.jViewportFunctionsUsedByThis);
        jScrollPaneFunctionsUsedByThis.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneFunctionsUsedByThis_mouseClicked(e); } });
        jTableFunctionsUsedByThis.addMouseListener(
            new MouseAdapter() { public void mouseReleased(MouseEvent e) { m.jTableFunctionsUsedByThis_mouseReleased(e); } public void mousePressed(MouseEvent e) { m.jTableFunctionsUsedByThis_mousePressed(e); } public void mouseClicked(MouseEvent e) { m.jTableFunctionsUsedByThis_mouseClicked(e); } });
        jTableFunctionsUsedByThis.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jTableFunctionsUsedByThis_mouseDragged(e); } });
        jTableFunctionsUsedByThis.addFocusListener(new Modeler_FocusListener());
        jViewportFunctionIOList.add(m.jTableFunctionIOList, null);
        jScrollPaneFunctionIOList.setViewport(m.jViewportFunctionIOList);
        jScrollPaneFunctionDescriptions.getViewport().add(m.jTextAreaFunctionDescriptions, null);
        jScrollPaneFunctionIOList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneFunctionIOList_mouseClicked(e); } });
        jTableFunctionIOList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableFunctionIOList_mouseClicked(e); } public void mousePressed(MouseEvent e) { m.jTableFunctionIOList_mousePressed(e); } public void mouseReleased(MouseEvent e) { m.jTableFunctionIOList_mouseReleased(e); } });
        jTableFunctionIOList.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jTableFunctionIOList_mouseDragged(e); } });
        jTableFunctionIOList.addFocusListener(new Modeler_FocusListener());
        jTableFunctionIOList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        //(jTableFunctionIOList)//
        jTableFunctionIOList.setBackground(SystemColor.control);
        jTableFunctionIOList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionIOList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionIOList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFunctionIOList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.tableModelFunctionIOList.addColumn("No.");
        //m.tableModelFunctionIOList.addColumn(res.getString("S201"));
        m.tableModelFunctionIOList.addColumn(res.getString("S680"));
        m.tableModelFunctionIOList.addColumn(res.getString("S204"));
        m.column0 = jTableFunctionIOList.getColumnModel().getColumn(0);
        m.column1 = jTableFunctionIOList.getColumnModel().getColumn(1);
        m.column2 = jTableFunctionIOList.getColumnModel().getColumn(2);
        //m.column3 = jTableFunctionIOList.getColumnModel().getColumn(3);
        m.column0.setPreferredWidth(40);
        //m.column1.setPreferredWidth(90);
        m.column1.setPreferredWidth(400);
        m.column2.setPreferredWidth(450);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        //m.column3.setCellRenderer(m.rendererAlignmentLeft);
        jTableFunctionIOList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableFunctionIOList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableFunctionsUsedByThis)//
        jTableFunctionsUsedByThis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionsUsedByThis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFunctionsUsedByThis.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionsUsedByThis.setRowHeight(m.TABLE_ROW_HEIGHT_EDITABLE);
        jTableFunctionsUsedByThis.getSelectionModel()
            .addListSelectionListener(e -> m.jTableFunctionsUsedByThis_valueChanged(e));
        jTableFunctionsUsedByThis.getColumnModel()
            .addColumnModelListener(new TableColumnModelListener() { public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) { m.jTableFunctionsUsedByThis_columnSelectionChanged(e); } public void columnMarginChanged(javax.swing.event.ChangeEvent e) {} public void columnMoved(javax.swing.event.TableColumnModelEvent e) {} public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {} public void columnAdded(javax.swing.event.TableColumnModelEvent e) {} });
        jTableFunctionsUsedByThis.setRowHeight(m.TABLE_ROW_HEIGHT);
        //(jTableFunctionsUsingThis)//
        jScrollPaneFunctionsUsingThis.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneFunctionsUsingThis_mouseClicked(e); } });
        jTableFunctionsUsingThis.setBackground(SystemColor.control);
        jTableFunctionsUsingThis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionsUsingThis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableFunctionsUsingThis.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionsUsingThis.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableFunctionsUsingThis_mouseClicked(e); } });
        jTableFunctionsUsingThis.addFocusListener(new Modeler_FocusListener());
        jTableFunctionsUsingThis.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.tableModelFunctionsUsingThis.addColumn("No.");
        m.tableModelFunctionsUsingThis.addColumn(res.getString("S436"));
        m.tableModelFunctionsUsingThis.addColumn("ID");
        m.tableModelFunctionsUsingThis.addColumn(res.getString("S688"));
        m.tableModelFunctionsUsingThis.addColumn(res.getString("S689"));
        m.column0 = jTableFunctionsUsingThis.getColumnModel().getColumn(0);
        m.column1 = jTableFunctionsUsingThis.getColumnModel().getColumn(1);
        m.column2 = jTableFunctionsUsingThis.getColumnModel().getColumn(2);
        m.column3 = jTableFunctionsUsingThis.getColumnModel().getColumn(3);
        m.column4 = jTableFunctionsUsingThis.getColumnModel().getColumn(4);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(300);
        m.column2.setPreferredWidth(150);
        m.column3.setPreferredWidth(300);
        m.column4.setPreferredWidth(600);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        jTableFunctionsUsingThis.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableFunctionsUsingThis.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableTasksUsingThis)//
        jTableTasksUsingThis.setBackground(SystemColor.control);
        jTableTasksUsingThis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTasksUsingThis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTasksUsingThis.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableTasksUsingThis.setRowHeight(m.TABLE_ROW_HEIGHT);
        jScrollPaneTasksUsingThis.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneTasksUsingThis_mouseClicked(e); } });
        jTableTasksUsingThis.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableTasksUsingThis_mouseClicked(e); } });
        jTableTasksUsingThis.addFocusListener(new Modeler_FocusListener());
        m.tableModelTasksUsingThis.addColumn("No.");
        m.tableModelTasksUsingThis.addColumn(res.getString("S693"));
        m.tableModelTasksUsingThis.addColumn(res.getString("S694"));
        m.tableModelTasksUsingThis.addColumn("ID");
        m.tableModelTasksUsingThis.addColumn(res.getString("S696"));
        m.tableModelTasksUsingThis.addColumn(res.getString("S697"));
        m.column0 = jTableTasksUsingThis.getColumnModel().getColumn(0);
        m.column1 = jTableTasksUsingThis.getColumnModel().getColumn(1);
        m.column2 = jTableTasksUsingThis.getColumnModel().getColumn(2);
        m.column3 = jTableTasksUsingThis.getColumnModel().getColumn(3);
        m.column4 = jTableTasksUsingThis.getColumnModel().getColumn(4);
        m.column5 = jTableTasksUsingThis.getColumnModel().getColumn(5);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(200);
        m.column2.setPreferredWidth(150);
        m.column3.setPreferredWidth(100);
        m.column4.setPreferredWidth(300);
        m.column5.setPreferredWidth(600);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentLeft);
        m.column5.setCellRenderer(m.rendererAlignmentLeft);
        jTableTasksUsingThis.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableTasksUsingThis.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
