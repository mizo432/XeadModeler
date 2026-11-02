package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.event.TreeExpansionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

/**
 * サブシステムリストノードのビュー
 * サブシステムの一覧表示を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubsystemListView {

    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanel = new JPanel();
    private final JScrollPane jScrollPane = new JScrollPane();
    private final JTable jTable = new JTable();
    private final TableModelReadOnlyList tableModel = new TableModelReadOnlyList();

    // Column references
    private TableColumn column0;
    private TableColumn column1;
    private TableColumn column2;
    private TableColumn column3;

    // Renderers
    private final DefaultTableCellRenderer rendererAlignmentCenter = new DefaultTableCellRenderer();
    private final DefaultTableCellRenderer rendererAlignmentLeft = new DefaultTableCellRenderer();
    private DefaultTableCellRenderer rendererTableHeader;

    public SubsystemListView() {
    }

    /**
     * コンポーネントを初期化します。
     */

    public JPanel getPanel() {
        return jPanel;
    }

    public JTable getTable() {
        return jTable;
    }

    public TableModelReadOnlyList getTableModel() {
        return tableModel;
    }

    class TableFocusListener extends java.awt.event.FocusAdapter {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
        }
    }

    class TableMouseAdapter extends java.awt.event.MouseAdapter {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (e.getClickCount() == 2) {
                // Double click handling
            }
        }
    }

    class TableKeyAdapter extends java.awt.event.KeyAdapter {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
        }
    }

    /**
     * 読み取り専用のテーブルモデル
     */
    public static class TableModelReadOnlyList extends DefaultTableModel {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    /**
     * ModelerのSubsystemListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelSubsystemList.setLayout(new BorderLayout());
        m.jPanelSubsystemList.setBackground(SystemColor.control);
        m.jScrollPaneSubsystemList.setBorder(null);
        m.jTreeFunctionsStructure.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTreeFunctionsStructure.setShowsRootHandles(true);
        m.jTreeFunctionsStructure.addTreeWillExpandListener(
            new TreeWillExpandListener() { public void treeWillExpand(TreeExpansionEvent e) { m.jTreeFunctionsStructure_treeWillExpand(e); } public void treeWillCollapse(TreeExpansionEvent e) {} });
        m.jTreeFunctionsStructure.addTreeSelectionListener(
            e -> m.jTreeFunctionsStructure_valueChanged(e));
        m.jTreeFunctionsStructure.addMouseListener(
            new MouseAdapter() { public void mouseReleased(MouseEvent e) { m.jTreeFunctionsStructure_mouseReleased(e); } });
        m.jTreeFunctionsStructure.getSelectionModel()
            .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        m.jTreeFunctionsStructure.setRowHeight(Modeler.TREE_ROW_HEIGHT);
        m.jScrollPaneFunctionsStructure.getViewport().add(m.jTreeFunctionsStructure, null);
        m.jPanelSubsystemList.add(m.jTabbedPaneSubsystemList, BorderLayout.CENTER);
        m.jScrollPaneSubsystemList.getViewport().add(m.jTableSubsystemList, null);
        m.jScrollPaneSubsystemList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneSubsystemList_mouseClicked(e); } });
        m.jTableSubsystemList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableSubsystemList_mouseClicked(e); } });
        m.jTableSubsystemList.addFocusListener(new Modeler_FocusListener());
        m.jTableSubsystemList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableFunctionsStructureTableIOList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableFunctionsStructureTableIOList_mouseClicked(e); } });
        m.jTableFunctionsStructureTableIOList.addFocusListener(new Modeler_FocusListener());
        m.jTabbedPaneSubsystemList.addTab(res.getString("S407"), m.imageIconSubsystem,
            m.jScrollPaneSubsystemList);
        m.jTabbedPaneSubsystemList.addTab(res.getString("S408"), m.imageIconTree,
            m.jSplitPaneFunctionsStructure1);
        m.jTabbedPaneSubsystemList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.isRequiredToSetupFunctionsStructre = true;
        m.jPanelFunctionsStructure.setLayout(null);
        m.jLabelFunctionsStructure1.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelFunctionsStructure1.setText(res.getString("S411"));
        m.jLabelFunctionsStructure1.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelFunctionsStructure1.setBounds(new Rectangle(7, 9, 130, 20));
        m.jLabelFunctionsStructure2.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelFunctionsStructure2.setText(res.getString("S413"));
        m.jLabelFunctionsStructure2.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelFunctionsStructure2.setBounds(new Rectangle(7, 40, 130, 20));
        m.jLabelFunctionsStructure3.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelFunctionsStructure3.setText(res.getString("S415"));
        m.jLabelFunctionsStructure3.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelFunctionsStructure3.setBounds(new Rectangle(7, 71, 130, 20));
        m.jLabelFunctionsStructure4.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelFunctionsStructure4.setText(res.getString("S417"));
        m.jLabelFunctionsStructure4.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelFunctionsStructure4.setBounds(new Rectangle(7, 102, 130, 20));
        m.jLabelFunctionsStructure5.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelFunctionsStructure5.setText(res.getString("S419"));
        m.jLabelFunctionsStructure5.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelFunctionsStructure5.setBounds(new Rectangle(7, 133, 130, 20));
        m.jLabelFunctionsStructure6.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelFunctionsStructure6.setText(res.getString("S421"));
        m.jLabelFunctionsStructure6.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelFunctionsStructure6.setBounds(new Rectangle(7, 164, 130, 20));
        m.jTextFieldFunctionsStructureLaunchEvent.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureLaunchEvent.setBounds(new Rectangle(143, 8, 600, 25));
        m.jTextFieldFunctionsStructureLaunchEvent.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureLaunchEvent.setEditable(false);
        m.jTextFieldFunctionsStructureSubsystemName.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureSubsystemName.setBounds(new Rectangle(143, 39, 400, 25));
        m.jTextFieldFunctionsStructureSubsystemName.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureSubsystemName.setEditable(false);
        m.jTextFieldFunctionsStructureFunctionName.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureFunctionName.setBounds(new Rectangle(143, 70, 400, 25));
        m.jTextFieldFunctionsStructureFunctionName.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureFunctionName.setEditable(false);
        m.jTextFieldFunctionsStructureFunctionType.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureFunctionType.setBounds(new Rectangle(143, 101, 300, 25));
        m.jTextFieldFunctionsStructureFunctionType.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureFunctionType.setEditable(false);
        m.jTextFieldFunctionsStructureFunctionSummary.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureFunctionSummary.setBounds(new Rectangle(143, 132, 757, 25));
        m.jTextFieldFunctionsStructureFunctionSummary.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureFunctionSummary.setEditable(false);
        m.jTextFieldFunctionsStructureFunctionParameters.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureFunctionParameters.setBounds(new Rectangle(143, 163, 500, 25));
        m.jTextFieldFunctionsStructureFunctionParameters.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureFunctionParameters.setEditable(false);
        m.jTextFieldFunctionsStructureFunctionReturn.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldFunctionsStructureFunctionReturn.setBounds(new Rectangle(650, 163, 250, 25));
        m.jTextFieldFunctionsStructureFunctionReturn.setBackground(SystemColor.control);
        m.jTextFieldFunctionsStructureFunctionReturn.setEditable(false);
        m.jSplitPaneFunctionsStructure1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        m.jSplitPaneFunctionsStructure1.setDividerLocation(350);
        m.jSplitPaneFunctionsStructure2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneFunctionsStructure2.setDividerLocation(196);
        m.jSplitPaneFunctionsStructure2.setBorder(null);
        m.jPanelFunctionsStructure.setBorder(BorderFactory.createEtchedBorder());
        m.jSplitPaneFunctionsStructure1.add(m.jScrollPaneFunctionsStructure, JSplitPane.LEFT);
        m.jSplitPaneFunctionsStructure1.add(m.jSplitPaneFunctionsStructure2, JSplitPane.RIGHT);
        m.jSplitPaneFunctionsStructure2.add(m.jPanelFunctionsStructure, JSplitPane.TOP);
        m.jSplitPaneFunctionsStructure2.add(m.jTabbedPaneFunctionsStructureIO, JSplitPane.BOTTOM);
        m.jPanelFunctionsStructure.add(m.jLabelFunctionsStructure1, null);
        m.jPanelFunctionsStructure.add(m.jLabelFunctionsStructure2, null);
        m.jPanelFunctionsStructure.add(m.jLabelFunctionsStructure3, null);
        m.jPanelFunctionsStructure.add(m.jLabelFunctionsStructure4, null);
        m.jPanelFunctionsStructure.add(m.jLabelFunctionsStructure5, null);
        m.jPanelFunctionsStructure.add(m.jLabelFunctionsStructure6, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureLaunchEvent, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureSubsystemName, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureFunctionName, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureFunctionType, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureFunctionSummary, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureFunctionParameters, null);
        m.jPanelFunctionsStructure.add(m.jTextFieldFunctionsStructureFunctionReturn, null);
        m.jTabbedPaneFunctionsStructureIO.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTabbedPaneFunctionsStructureIO.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        m.jTextPaneFunctionsStructureIOImageArray[0] = m.jTextPaneFunctionsStructureIOImage0;
        m.jTextPaneFunctionsStructureIOImageArray[1] = m.jTextPaneFunctionsStructureIOImage1;
        m.jTextPaneFunctionsStructureIOImageArray[2] = m.jTextPaneFunctionsStructureIOImage2;
        m.jTextPaneFunctionsStructureIOImageArray[3] = m.jTextPaneFunctionsStructureIOImage3;
        m.jTextPaneFunctionsStructureIOImageArray[4] = m.jTextPaneFunctionsStructureIOImage4;
        m.jTextPaneFunctionsStructureIOImageArray[5] = m.jTextPaneFunctionsStructureIOImage5;
        m.jTextPaneFunctionsStructureIOImageArray[6] = m.jTextPaneFunctionsStructureIOImage6;
        m.jTextPaneFunctionsStructureIOImageArray[7] = m.jTextPaneFunctionsStructureIOImage7;
        m.jTextPaneFunctionsStructureIOImageArray[8] = m.jTextPaneFunctionsStructureIOImage8;
        m.jTextPaneFunctionsStructureIOImageArray[9] = m.jTextPaneFunctionsStructureIOImage9;
        m.jEditorPaneFunctionsStructureIOImageArray[0] = m.jEditorPaneFunctionsStructureIOImage0;
        m.jEditorPaneFunctionsStructureIOImageArray[1] = m.jEditorPaneFunctionsStructureIOImage1;
        m.jEditorPaneFunctionsStructureIOImageArray[2] = m.jEditorPaneFunctionsStructureIOImage2;
        m.jEditorPaneFunctionsStructureIOImageArray[3] = m.jEditorPaneFunctionsStructureIOImage3;
        m.jEditorPaneFunctionsStructureIOImageArray[4] = m.jEditorPaneFunctionsStructureIOImage4;
        m.jEditorPaneFunctionsStructureIOImageArray[5] = m.jEditorPaneFunctionsStructureIOImage5;
        m.jEditorPaneFunctionsStructureIOImageArray[6] = m.jEditorPaneFunctionsStructureIOImage6;
        m.jEditorPaneFunctionsStructureIOImageArray[7] = m.jEditorPaneFunctionsStructureIOImage7;
        m.jEditorPaneFunctionsStructureIOImageArray[8] = m.jEditorPaneFunctionsStructureIOImage8;
        m.jEditorPaneFunctionsStructureIOImageArray[9] = m.jEditorPaneFunctionsStructureIOImage9;
        m.jPanelFunctionsStructureIOImageArray[0] = m.jPanelFunctionsStructureIOImage0;
        m.jPanelFunctionsStructureIOImageArray[1] = m.jPanelFunctionsStructureIOImage1;
        m.jPanelFunctionsStructureIOImageArray[2] = m.jPanelFunctionsStructureIOImage2;
        m.jPanelFunctionsStructureIOImageArray[3] = m.jPanelFunctionsStructureIOImage3;
        m.jPanelFunctionsStructureIOImageArray[4] = m.jPanelFunctionsStructureIOImage4;
        m.jPanelFunctionsStructureIOImageArray[5] = m.jPanelFunctionsStructureIOImage5;
        m.jPanelFunctionsStructureIOImageArray[6] = m.jPanelFunctionsStructureIOImage6;
        m.jPanelFunctionsStructureIOImageArray[7] = m.jPanelFunctionsStructureIOImage7;
        m.jPanelFunctionsStructureIOImageArray[8] = m.jPanelFunctionsStructureIOImage8;
        m.jPanelFunctionsStructureIOImageArray[9] = m.jPanelFunctionsStructureIOImage9;
        m.jLabelFunctionsStructureIOImageArray[0] = m.jLabelFunctionsStructureIOImage0;
        m.jLabelFunctionsStructureIOImageArray[1] = m.jLabelFunctionsStructureIOImage1;
        m.jLabelFunctionsStructureIOImageArray[2] = m.jLabelFunctionsStructureIOImage2;
        m.jLabelFunctionsStructureIOImageArray[3] = m.jLabelFunctionsStructureIOImage3;
        m.jLabelFunctionsStructureIOImageArray[4] = m.jLabelFunctionsStructureIOImage4;
        m.jLabelFunctionsStructureIOImageArray[5] = m.jLabelFunctionsStructureIOImage5;
        m.jLabelFunctionsStructureIOImageArray[6] = m.jLabelFunctionsStructureIOImage6;
        m.jLabelFunctionsStructureIOImageArray[7] = m.jLabelFunctionsStructureIOImage7;
        m.jLabelFunctionsStructureIOImageArray[8] = m.jLabelFunctionsStructureIOImage8;
        m.jLabelFunctionsStructureIOImageArray[9] = m.jLabelFunctionsStructureIOImage9;
        m.jScrollPaneFunctionsStructureIOImageArray[0] = m.jScrollPaneFunctionsStructureIOImage0;
        m.jScrollPaneFunctionsStructureIOImageArray[1] = m.jScrollPaneFunctionsStructureIOImage1;
        m.jScrollPaneFunctionsStructureIOImageArray[2] = m.jScrollPaneFunctionsStructureIOImage2;
        m.jScrollPaneFunctionsStructureIOImageArray[3] = m.jScrollPaneFunctionsStructureIOImage3;
        m.jScrollPaneFunctionsStructureIOImageArray[4] = m.jScrollPaneFunctionsStructureIOImage4;
        m.jScrollPaneFunctionsStructureIOImageArray[5] = m.jScrollPaneFunctionsStructureIOImage5;
        m.jScrollPaneFunctionsStructureIOImageArray[6] = m.jScrollPaneFunctionsStructureIOImage6;
        m.jScrollPaneFunctionsStructureIOImageArray[7] = m.jScrollPaneFunctionsStructureIOImage7;
        m.jScrollPaneFunctionsStructureIOImageArray[8] = m.jScrollPaneFunctionsStructureIOImage8;
        m.jScrollPaneFunctionsStructureIOImageArray[9] = m.jScrollPaneFunctionsStructureIOImage9;
        for (int i = 0; i < 10; i++) {
          m.jTextPaneFunctionsStructureIOImageArray[i].setBorder(BorderFactory.createRaisedBevelBorder());
          m.jTextPaneFunctionsStructureIOImageArray[i].setPreferredSize(new Dimension(500, 400));
          m.jTextPaneFunctionsStructureIOImageArray[i].setBackground(SystemColor.control);
          m.jTextPaneFunctionsStructureIOImageArray[i].setFont(new java.awt.Font(m.ioImageFontName, 0, 12));
          m.jTextPaneFunctionsStructureIOImageArray[i].setContentType("text/plain");
          m.jTextPaneFunctionsStructureIOImageArray[i].setEditable(false);
          m.jTextPaneFunctionsStructureIOImageArray[i].setBounds(10, 10, 500, 400);
          m.jEditorPaneFunctionsStructureIOImageArray[i].setBorder(
              BorderFactory.createRaisedBevelBorder());
          m.jEditorPaneFunctionsStructureIOImageArray[i].setEditable(false);
          m.jEditorPaneFunctionsStructureIOImageArray[i].setContentType("text/html");
          m.jPanelFunctionsStructureIOImageArray[i].setBackground(m.DESKTOP_COLOR);
          m.jPanelFunctionsStructureIOImageArray[i].setLayout(null);
          m.jPanelFunctionsStructureIOImageArray[i].setPreferredSize(new Dimension(520, 420));
          m.jScrollPaneFunctionsStructureIOImageArray[i].getViewport()
              .add(m.jPanelFunctionsStructureIOImageArray[i], null);
          m.jTextPaneFunctionsStructureIOImageArray[i].addMouseListener(
              new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneFunctionsStructureIOImage_mouseClicked(e); } });
          m.jEditorPaneFunctionsStructureIOImageArray[i].addMouseListener(
              new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneFunctionsStructureIOImage_mouseClicked(e); } });
          m.jLabelFunctionsStructureIOImageArray[i].addMouseListener(
              new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jLabelFunctionsStructureIOImage_mouseClicked(e); } });
        }
        m.jScrollPaneFunctionsStructureTableIOList.getViewport()
            .add(m.jTableFunctionsStructureTableIOList, null);
        //(jTableSubsystemList)//
        m.jTableSubsystemList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableSubsystemList.setBackground(SystemColor.control);
        m.jTableSubsystemList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableSubsystemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableSubsystemList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.tableModelSubsystemList.addColumn("No.");
        m.tableModelSubsystemList.addColumn(res.getString("S201"));
        m.tableModelSubsystemList.addColumn(res.getString("S436"));
        m.tableModelSubsystemList.addColumn(res.getString("S437"));
        m.tableModelSubsystemList.addColumn(res.getString("S438"));
        m.tableModelSubsystemList.addColumn(res.getString("S439"));
        m.tableModelSubsystemList.addColumn(res.getString("S440"));
        m.tableModelSubsystemList.addColumn(res.getString("S441"));
        m.tableModelSubsystemList.addColumn(res.getString("S442"));
        m.tableModelSubsystemList.addColumn(res.getString("S443"));
        m.tableModelSubsystemList.addColumn(res.getString("S204"));
        m.column0 = m.jTableSubsystemList.getColumnModel().getColumn(0);
        m.column1 = m.jTableSubsystemList.getColumnModel().getColumn(1);
        m.column2 = m.jTableSubsystemList.getColumnModel().getColumn(2);
        m.column3 = m.jTableSubsystemList.getColumnModel().getColumn(3);
        m.column4 = m.jTableSubsystemList.getColumnModel().getColumn(4);
        m.column5 = m.jTableSubsystemList.getColumnModel().getColumn(5);
        m.column6 = m.jTableSubsystemList.getColumnModel().getColumn(6);
        m.column7 = m.jTableSubsystemList.getColumnModel().getColumn(7);
        m.column8 = m.jTableSubsystemList.getColumnModel().getColumn(8);
        m.column9 = m.jTableSubsystemList.getColumnModel().getColumn(9);
        m.columnA = m.jTableSubsystemList.getColumnModel().getColumn(10);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(250);
        m.column3.setPreferredWidth(35);
        m.column4.setPreferredWidth(35);
        m.column5.setPreferredWidth(42);
        m.column6.setPreferredWidth(42);
        m.column7.setPreferredWidth(42);
        m.column8.setPreferredWidth(42);
        m.column9.setPreferredWidth(47);
        m.columnA.setPreferredWidth(700);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentCenter);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentCenter);
        m.column9.setCellRenderer(m.rendererAlignmentCenter);
        m.columnA.setCellRenderer(m.rendererAlignmentLeft);
        m.jTableSubsystemList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableSubsystemList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(m.jTableFunctionsStructureTableIOList)//
        m.jTableFunctionsStructureTableIOList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableFunctionsStructureTableIOList.setBackground(SystemColor.control);
        m.jTableFunctionsStructureTableIOList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableFunctionsStructureTableIOList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableFunctionsStructureTableIOList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.tableModelFunctionsStructureTableIOList.addColumn("No.");
        m.tableModelFunctionsStructureTableIOList.addColumn(res.getString("S448"));
        m.tableModelFunctionsStructureTableIOList.addColumn(res.getString("S449"));
        m.tableModelFunctionsStructureTableIOList.addColumn(res.getString("S750"));
        m.tableModelFunctionsStructureTableIOList.addColumn("C");
        m.tableModelFunctionsStructureTableIOList.addColumn("R");
        m.tableModelFunctionsStructureTableIOList.addColumn("U");
        m.tableModelFunctionsStructureTableIOList.addColumn("D");
        m.column0 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(0);
        m.column1 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(1);
        m.column2 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(2);
        m.column3 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(3);
        m.column4 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(4);
        m.column5 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(5);
        m.column6 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(6);
        m.column7 = m.jTableFunctionsStructureTableIOList.getColumnModel().getColumn(7);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(100);
        m.column2.setPreferredWidth(300);
        m.column3.setPreferredWidth(130);
        m.column4.setPreferredWidth(30);
        m.column5.setPreferredWidth(30);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableFunctionsStructureTableIOList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableFunctionsStructureTableIOList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
    }
}
