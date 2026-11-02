package xeadModeler;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import xeadModeler.view.SortableXeadTreeNodeComboBoxModel;
import xeadModeler.view.TaskActionTextFieldListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

/**
 * タスクノードのビュー
 * アクションツリー、関数IOイメージ、タブペインなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TaskView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelTask = new JPanel();
    private final JSplitPane jSplitPaneTask = new JSplitPane();
    private final JScrollPane jScrollPaneTask1 = new JScrollPane();
    private final JPanel jPanelTask1 = new JPanel();
    private final JPanel jPanelTask2 = new JPanel();
    private final JPanel jPanelTask3 = new JPanel();
    private final JPanel jPanelTask4 = new JPanel();
    private final JPanel jPanelTask5 = new JPanel();
    private final JPanel jPanelTask6 = new JPanel();
    
    // Labels
    private final JLabel jLabelTaskEvent = new JLabel();
    private final JLabel jLabelTaskType = new JLabel();
    private final JLabel jLabelTaskDescriptions = new JLabel();
    private final JLabel jLabelTaskName = new JLabel();
    private final JLabel jLabelTaskSortKey = new JLabel();
    private final JLabel jLabelSubjectAreaWithThisTask = new JLabel();
    private final JLabel jLabelTaskActions = new JLabel();
    
    // Fields
    private final KanjiTextField jTextFieldTaskEvent = new KanjiTextField();
    private final SortableXeadTreeNodeComboBoxModel sortableComboBoxModelTaskType = new SortableXeadTreeNodeComboBoxModel();
    private final JComboBox jComboBoxTaskType = new JComboBox<>(sortableComboBoxModelTaskType);
    private final KanjiTextField jTextFieldTaskName = new KanjiTextField();
    private final JTextField jTextFieldTaskSortKey = new JTextField();
    
    // Subject area list
    private final DefaultListModel listModelSubjectAreaWithThisTask = new DefaultListModel();
    private final JList jListSubjectAreaWithThisTask = new JList(listModelSubjectAreaWithThisTask);
    private final JScrollPane jScrollPaneSubjectAreaWithThisTask = new JScrollPane();
    private final ArrayList<String> subjectAreaIDList = new ArrayList<String>();
    
    // Descriptions
    private final JScrollPane jScrollPaneTaskDescriptions = new JScrollPane();
    private final JTextArea jTextAreaTaskDescriptions = new JTextArea();
    
    // Split panes
    private final JSplitPane jSplitPaneTask2 = new JSplitPane();
    private final JSplitPane jSplitPaneTask3 = new JSplitPane();
    private final JSplitPane jSplitPaneTask4 = new JSplitPane();
    private final JSplitPane jSplitPaneTask5 = new JSplitPane();
    
    // Task actions
    private final JScrollPane jScrollPaneTaskActions = new JScrollPane();
    private final JTree jTreeTaskActions = new JTree();
    private DefaultTreeModel treeModelTaskActions;
    
    // Function IO Image
    private final JTextPane jTextPaneTaskFunctionIOImage = new JTextPane();
    private final JLabel jLabelTaskFunctionIOImage = new JLabel();
    private final JScrollPane jScrollPaneTaskFunctionIOImage = new JScrollPane();
    private final JEditorPane jEditorPaneTaskFunctionIOImage = new JEditorPane();
    private final JPanel jPanelTaskFunctionIOImageSurface = new JPanel();
    private final JPanel jPanelTaskFunctionIOImage = new JPanel();
    
    // Task action panels
    private final JPanel jPanelTask15 = new JPanel();
    private final JPanel jPanelTask16 = new JPanel();
    private final JPanel jPanelTask17 = new JPanel();
    private final JPanel jPanelTask18 = new JPanel();
    private final JPanel jPanelTask19 = new JPanel();
    private final JScrollPane jScrollPaneTaskActionDescriptions = new JScrollPane();
    private final JTextArea jTextAreaTaskActionDescriptions = new JTextArea();
    private final JLabel jLabelTaskActionDescriptions = new JLabel();
    private final JLabel jLabelTaskActionExecuteIf = new JLabel();
    private final JLabel jLabelTaskActionLabel = new JLabel();
    private final KanjiTextField jTextFieldTaskActionExecuteIf = new KanjiTextField();
    private final KanjiTextField jTextFieldTaskActionLabel = new KanjiTextField();
    
    // Function IO panels
    private final JPanel jPanelTask20 = new JPanel();
    private final JPanel jPanelTask21 = new JPanel();
    private final JPanel jPanelTask22 = new JPanel();
    private final JPanel jPanelTask23 = new JPanel();
    private final JLabel jLabelTaskFunctionIOSortKey = new JLabel();
    private final JLabel jLabelTaskFunctionIOOperations = new JLabel();
    private final JScrollPane jScrollPaneTaskFunctionIOOperations = new JScrollPane();
    private final JTextArea jTextAreaTaskFunctionIOOperations = new JTextArea();
    private final JLabel jLabelTaskFunctionIODescriptions = new JLabel();
    private final JScrollPane jScrollPaneTaskFunctionIODescriptions = new JScrollPane();
    private final JTextArea jTextAreaTaskFunctionIODescriptions = new JTextArea();
    private final JTabbedPane jTabbedPaneTaskFunctionIO = new JTabbedPane();
    private final JPanel jPanelTaskFunctionIODummy = new JPanel();
    
    // Function IO Image labels
    private final JLabel jLabelTaskFunctionIOImageFunction = new JLabel();
    private final JTextField jTextFieldTaskFunctionIOImageFunction = new JTextField();
    private final JLabel jLabelTaskFunctionIOImageFunctionRemarks = new JLabel();
    private final JTextField jTextFieldTaskFunctionIOImageFunctionSummary = new JTextField();
    private final JLabel jLabelTaskFunctionIOImageTable = new JLabel();
    private final JTextField jTextFieldTaskFunctionIOImageTable = new JTextField();
    private final JLabel jLabelTaskFunctionIOImageTableRemarks = new JLabel();
    private final JScrollPane jScrollPaneTaskFunctionIOImageTableRemarks = new JScrollPane();
    private final JTextArea jTextAreaTaskFunctionIOImageTableRemarks = new JTextArea();
    private final JLabel jLabelTaskFunctionIOImageFunctionParms = new JLabel();
    private final JTextField jTextFieldTaskFunctionIOImageFunctionParms = new JTextField();
    private final JLabel jLabelTaskFunctionIOImageFunctionReturn = new JLabel();
    private final JTextField jTextFieldTaskFunctionIOImageFunctionReturn = new JTextField();
    
    // Function IO Image list
    private final JScrollPane jScrollPaneFunctionIOImageIOList = new JScrollPane();
    private final TableModelFunctionIOImageIOList tableModelFunctionIOImageIOList = new TableModelFunctionIOImageIOList();
    private final JTable jTableFunctionIOImageIOList = new JTable(tableModelFunctionIOImageIOList);
    
    // Renderers
    private DefaultTableCellRenderer rendererAlignmentCenter;
    private DefaultTableCellRenderer rendererAlignmentLeft;
    private DefaultTableCellRenderer rendererTableHeader;
    
    // Columns
    private javax.swing.table.TableColumn column0;
    private javax.swing.table.TableColumn column1;
    private javax.swing.table.TableColumn column2;

    public TaskView() {
    }

    /**
     * コンポーネントを初期化します。
     */
    
    /**
     * IOリストテーブルを初期化します。
     */
    private void initIOListTable() {
        jTableFunctionIOImageIOList.setBackground(SystemColor.control);
        jTableFunctionIOImageIOList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionIOImageIOList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionIOImageIOList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        jTableFunctionIOImageIOList.setRowSelectionAllowed(false);
        
        tableModelFunctionIOImageIOList.addColumn("No.");
        tableModelFunctionIOImageIOList.addColumn(res.getString("S680"));
        tableModelFunctionIOImageIOList.addColumn(res.getString("S204"));
        
        column0 = jTableFunctionIOImageIOList.getColumnModel().getColumn(0);
        column1 = jTableFunctionIOImageIOList.getColumnModel().getColumn(1);
        column2 = jTableFunctionIOImageIOList.getColumnModel().getColumn(2);
        
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(285);
        column2.setPreferredWidth(497);
        
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        
        jTableFunctionIOImageIOList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        
        rendererTableHeader = (DefaultTableCellRenderer) jTableFunctionIOImageIOList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(SwingConstants.LEFT);
        
        jScrollPaneFunctionIOImageIOList.setBorder(null);
        jScrollPaneFunctionIOImageIOList.getViewport().add(jTableFunctionIOImageIOList);
        jScrollPaneFunctionIOImageIOList.setBounds(5, 130, 855, 200);
    }

    public JPanel getPanel() {
        return jPanelTask;
    }

    public JSplitPane getSplitPane() {
        return jSplitPaneTask;
    }

    public JTextField getNameField() {
        return jTextFieldTaskName;
    }

    public JTextField getSortKeyField() {
        return jTextFieldTaskSortKey;
    }

    public JTextField getEventField() {
        return jTextFieldTaskEvent;
    }

    public JComboBox getTaskTypeCombo() {
        return jComboBoxTaskType;
    }

    public JTextArea getDescriptionsArea() {
        return jTextAreaTaskDescriptions;
    }

    public JList getSubjectAreaList() {
        return jListSubjectAreaWithThisTask;
    }

    public DefaultListModel getSubjectAreaListModel() {
        return listModelSubjectAreaWithThisTask;
    }

    public ArrayList<String> getSubjectAreaIDList() {
        return subjectAreaIDList;
    }

    public JTree getTaskActionsTree() {
        return jTreeTaskActions;
    }

    public DefaultTreeModel getTaskActionsTreeModel() {
        return treeModelTaskActions;
    }

    public void setTaskActionsTreeModel(DefaultTreeModel model) {
        this.treeModelTaskActions = model;
    }

    public JTabbedPane getFunctionIOTabbedPane() {
        return jTabbedPaneTaskFunctionIO;
    }

    public JTextPane getFunctionIOImageTextPane() {
        return jTextPaneTaskFunctionIOImage;
    }

    public JEditorPane getFunctionIOImageEditorPane() {
        return jEditorPaneTaskFunctionIOImage;
    }

    public JPanel getFunctionIOImagePanel() {
        return jPanelTaskFunctionIOImage;
    }

    public JPanel getFunctionIODummyPanel() {
        return jPanelTaskFunctionIODummy;
    }

    public JTable getFunctionIOImageIOListTable() {
        return jTableFunctionIOImageIOList;
    }

    public TableModelFunctionIOImageIOList getFunctionIOImageIOListTableModel() {
        return tableModelFunctionIOImageIOList;
    }

    /**
     * 読み取り専用のテーブルモデル
     */
    public static class TableModelFunctionIOImageIOList extends javax.swing.table.DefaultTableModel {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }


    /**
     * ModelerのTaskコンポーネントを初期化します。
     */
    public void initComponents(Modeler m) {
        jPanelTask.setLayout(new BorderLayout());
        jSplitPaneTask.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneTask.setBorder(null);
        jSplitPaneTask.setDividerLocation(120);
        jPanelTask4.setLayout(null);
        jPanelTask4.setPreferredSize(new Dimension(140, 80));
        jPanelTask4.setMinimumSize(new Dimension(100, 10));
        jScrollPaneTask1.setBorder(null);
        jScrollPaneTask1.getViewport().add(m.jPanelTask1);
        jPanelTask1.setBorder(BorderFactory.createEtchedBorder());
        jPanelTask1.setPreferredSize(new Dimension(1230, 120));
        jPanelTask1.setLayout(new BorderLayout());
        jPanelTask3.setPreferredSize(new Dimension(10, 69));
        jPanelTask3.setLayout(null);
        jLabelTaskName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskName.setText(res.getString("S372"));
        jLabelTaskName.setBounds(new Rectangle(5, 6, 130, 20));
        jTextFieldTaskName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskName.setBounds(new Rectangle(0, 6, 300, 25));
        jLabelTaskSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskSortKey.setText(res.getString("S201"));
        jLabelTaskSortKey.setBounds(new Rectangle(310, 6, 130, 20));
        jTextFieldTaskSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskSortKey.setBounds(new Rectangle(445, 6, 100, 25));
        jLabelTaskEvent.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskEvent.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskEvent.setText(res.getString("S361"));
        jLabelTaskEvent.setToolTipText(res.getString("S368"));
        jLabelTaskEvent.setBounds(new Rectangle(5, 37, 130, 20));
        jTextFieldTaskEvent.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskEvent.setBounds(new Rectangle(0, 37, 300, 25));
        jTextFieldTaskEvent.setToolTipText(res.getString("S368"));
        jLabelTaskType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskType.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskType.setText(res.getString("S362"));
        jLabelTaskType.setBounds(new Rectangle(310, 37, 130, 20));
        jComboBoxTaskType.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxTaskType.setActionCommand("comboBoxChanged");
        jComboBoxTaskType.setBounds(new Rectangle(445, 37, 200, 25));
        jLabelSubjectAreaWithThisTask.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubjectAreaWithThisTask.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubjectAreaWithThisTask.setText(res.getString("S380"));
        jLabelSubjectAreaWithThisTask.setBounds(new Rectangle(575, 6, 200, 20));
        jListSubjectAreaWithThisTask.setBorder(null);
        jListSubjectAreaWithThisTask.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jListSubjectAreaWithThisTask.setSelectionBackground(m.jPanelTask.getBackground());
        jListSubjectAreaWithThisTask.setBackground(m.jPanelTask.getBackground());
        jListSubjectAreaWithThisTask.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jListSubjectAreaWithThisTask_mouseClicked(e); } public void mouseExited(MouseEvent e) { m.jListSubjectAreaWithThisTask_mouseExited(e); } });
        jListSubjectAreaWithThisTask.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseMoved(MouseEvent e) { m.jListSubjectAreaWithThisTask_mouseMoved(e); } });
        jScrollPaneSubjectAreaWithThisTask.setBorder(BorderFactory.createEtchedBorder());
        jScrollPaneSubjectAreaWithThisTask.setBounds(new Rectangle(780, 6, 300, 56));
        jScrollPaneSubjectAreaWithThisTask.getViewport().add(m.jListSubjectAreaWithThisTask, null);
        jLabelTaskDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskDescriptions.setText(res.getString("S204"));
        jLabelTaskDescriptions.setBounds(new Rectangle(5, 68, 130, 25));
        jTextAreaTaskDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTaskDescriptions.setLineWrap(true);
        jPanelTask2.setLayout(new BorderLayout());
        jSplitPaneTask2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneTask3.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        jSplitPaneTask4.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneTask5.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        jPanelTask5.setPreferredSize(new Dimension(10, 10));
        jPanelTask5.setLayout(new BorderLayout());
        jPanelTask6.setLayout(null);
        jPanelTask6.setPreferredSize(new Dimension(1, 20));
        jLabelTaskActions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskActions.setText(res.getString("S375"));
        jLabelTaskActions.setBounds(new Rectangle(5, 0, 130, 20));
        jSplitPaneTask2.setDividerLocation(150);
        jSplitPaneTask3.setDividerLocation(800);
        jSplitPaneTask4.setDividerLocation(200);
        jSplitPaneTask4.setMinimumSize(new Dimension(50, 10));
        jSplitPaneTask5.setDividerLocation(950);
        jPanelTaskFunctionIOImage.setLayout(null);
        jPanelTaskFunctionIOImage.setBorder(null);
        jPanelTaskFunctionIOImage.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelTaskFunctionIOImage_mouseClicked(e); } });
        jPanelTaskFunctionIODummy.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanelTaskFunctionIODummy.setBackground(SystemColor.control);
        jPanelTaskFunctionIODummy.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelTaskFunctionIODummy_mouseClicked(e); } });
        jTextPaneTaskFunctionIOImage.setBorder(BorderFactory.createRaisedBevelBorder());
        jTextPaneTaskFunctionIOImage.setPreferredSize(new Dimension(500, 400));
        jTextPaneTaskFunctionIOImage.setBackground(SystemColor.control);
        jTextPaneTaskFunctionIOImage.setFont(new java.awt.Font(m.ioImageFontName, 0, 12));
        jTextPaneTaskFunctionIOImage.setContentType("text/plain");
        jTextPaneTaskFunctionIOImage.setEditable(false);
        jTextPaneTaskFunctionIOImage.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        jTextPaneTaskFunctionIOImage.addKeyListener(
            new KeyAdapter() { public void keyTyped(KeyEvent e) { m.jTextPaneTaskFunctionIOImage_keyTyped(e); } });
        jLabelTaskFunctionIOImage.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jLabelTaskFunctionIOImage_mouseClicked(e); } });
        jEditorPaneTaskFunctionIOImage.setBorder(BorderFactory.createRaisedBevelBorder());
        jEditorPaneTaskFunctionIOImage.setEditable(false);
        jEditorPaneTaskFunctionIOImage.setContentType("text/html");
        jEditorPaneTaskFunctionIOImage.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jEditorPaneTaskFunctionIOImage_mouseClicked(e); } });
        jPanelTaskFunctionIOImageSurface.setOpaque(false);
        jTreeTaskActions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTreeTaskActions.setRowHeight(m.TREE_ROW_HEIGHT);
        jTreeTaskActions.setShowsRootHandles(true);
        jTreeTaskActions.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        jSplitPaneTask2.setBorder(null);
        jSplitPaneTask3.setBorder(null);
        jSplitPaneTask4.setBorder(null);
        jSplitPaneTask5.setBorder(null);
        jPanelTask15.setBorder(BorderFactory.createEtchedBorder());
        jPanelTask15.setLayout(new BorderLayout());
        jPanelTask19.setLayout(new BorderLayout());
        jPanelTask16.setLayout(null);
        jPanelTask16.setPreferredSize(new Dimension(1, 69));
        jPanelTask17.setLayout(new BorderLayout());
        jPanelTask18.setPreferredSize(new Dimension(140, 10));
        jPanelTask18.setLayout(null);
        jPanelTask22.setLayout(new BorderLayout());
        jTextAreaTaskActionDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTaskActionDescriptions.setLineWrap(true);
        jLabelTaskActionDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskActionDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskActionDescriptions.setText(res.getString("S390"));
        jLabelTaskActionDescriptions.setBounds(new Rectangle(5, 0, 130, 20));
        jLabelTaskActionExecuteIf.setBounds(new Rectangle(5, 9, 130, 20));
        jLabelTaskActionExecuteIf.setText(res.getString("S391"));
        jLabelTaskActionExecuteIf.setToolTipText(res.getString("S392"));
        jLabelTaskActionExecuteIf.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskActionExecuteIf.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskActionLabel.setBounds(new Rectangle(5, 40, 130, 20));
        jLabelTaskActionLabel.setText(res.getString("S394"));
        jLabelTaskActionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelTaskActionLabel.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskActionExecuteIf.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskActionExecuteIf.setBounds(new Rectangle(140, 6, 600, 25));
        jTextFieldTaskActionExecuteIf.setToolTipText(res.getString("S392"));
        jTextFieldTaskActionExecuteIf.addFocusListener(new TaskActionTextFieldListener(m));
        jTextFieldTaskActionLabel.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskActionLabel.setBounds(new Rectangle(140, 37, 600, 25));
        jTextFieldTaskActionLabel.addFocusListener(new TaskActionTextFieldListener(m));
        jPanelTask20.setPreferredSize(new Dimension(10, 20));
        jPanelTask20.setLayout(null);
        jPanelTask19.setBorder(BorderFactory.createEtchedBorder());
        jLabelTaskFunctionIOSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOSortKey.setOpaque(false);
        jLabelTaskFunctionIOSortKey.setForeground(Color.WHITE);
        jLabelTaskFunctionIOOperations.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOOperations.setText(res.getString("S400"));
        jLabelTaskFunctionIOOperations.setBounds(new Rectangle(5, 0, 180, 20));
        jTextAreaTaskFunctionIOOperations.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTaskFunctionIOOperations.setLineWrap(true);
        jPanelTask22.setBorder(BorderFactory.createEtchedBorder());
        jPanelTask23.setPreferredSize(new Dimension(10, 20));
        jPanelTask23.setLayout(null);
        jLabelTaskFunctionIODescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIODescriptions.setText(res.getString("S403"));
        jLabelTaskFunctionIODescriptions.setBounds(new Rectangle(5, 0, 200, 20));
        jTextAreaTaskFunctionIODescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTaskFunctionIODescriptions.setLineWrap(true);
        jTextAreaTaskFunctionIODescriptions.setEditable(false);
        jTextAreaTaskFunctionIODescriptions.setBackground(SystemColor.control);
        jTreeTaskActions.addMouseListener(new MouseAdapter() { public void mouseReleased(MouseEvent e) { m.jTreeTaskActions_mouseReleased(e); } });
        jTreeTaskActions.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTreeTaskActions_keyReleased(e); } });
        jTabbedPaneTaskFunctionIO.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTabbedPaneTaskFunctionIO.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneTaskFunctionIO.addChangeListener(
            e -> m.jTabbedPaneTaskFunctionIO_stateChanged(e));
        jTabbedPaneTaskFunctionIO.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTabbedPaneTaskFunctionIO_mouseClicked(e); } });
        //
        jPanelTask.add(m.jSplitPaneTask, BorderLayout.CENTER);
        jSplitPaneTask.add(m.jScrollPaneTask1, JSplitPane.TOP);
        jSplitPaneTask.add(m.jSplitPaneTask2, JSplitPane.BOTTOM);
        jPanelTask3.add(m.jTextFieldTaskName);
        jPanelTask3.add(m.jLabelTaskSortKey);
        jPanelTask3.add(m.jTextFieldTaskSortKey);
        jPanelTask3.add(m.jLabelSubjectAreaWithThisTask);
        jPanelTask3.add(m.jScrollPaneSubjectAreaWithThisTask);
        jPanelTask1.add(m.jPanelTask4, BorderLayout.WEST);
        jPanelTask1.add(m.jPanelTask2, BorderLayout.CENTER);
        jPanelTask4.add(m.jLabelTaskName);
        jPanelTask4.add(m.jLabelTaskDescriptions);
        jPanelTask4.add(m.jLabelTaskEvent);
        jPanelTask3.add(m.jLabelTaskType);
        jPanelTask2.add(m.jPanelTask3, BorderLayout.NORTH);
        jPanelTask2.add(m.jScrollPaneTaskDescriptions, BorderLayout.CENTER);
        jScrollPaneTaskDescriptions.getViewport().add(m.jTextAreaTaskDescriptions);
        jScrollPaneTaskActions.getViewport().add(m.jTreeTaskActions);
        jPanelTask3.add(m.jTextFieldTaskEvent);
        jPanelTask3.add(m.jComboBoxTaskType);
        jSplitPaneTask2.add(m.jSplitPaneTask3, JSplitPane.TOP);
        jSplitPaneTask2.add(m.jTabbedPaneTaskFunctionIO, JSplitPane.BOTTOM);
        jSplitPaneTask3.add(m.jPanelTask5, JSplitPane.LEFT);
        jSplitPaneTask3.add(m.jPanelTask15, JSplitPane.RIGHT);
        jPanelTask15.add(m.jPanelTask16, BorderLayout.NORTH);
        jSplitPaneTask5.add(m.jScrollPaneTaskFunctionIOImage, JSplitPane.LEFT);
        jSplitPaneTask5.add(m.jSplitPaneTask4, JSplitPane.RIGHT);
        jSplitPaneTask4.add(m.jPanelTask22, JSplitPane.TOP);
        jSplitPaneTask4.add(m.jPanelTask19, JSplitPane.BOTTOM);
        jPanelTask5.add(m.jPanelTask6, BorderLayout.NORTH);
        jPanelTask5.add(m.jScrollPaneTaskActions, BorderLayout.CENTER);
        jPanelTask6.add(m.jLabelTaskActions);
        //
        jPanelTask15.add(m.jPanelTask17, BorderLayout.CENTER);
        jPanelTask17.add(m.jPanelTask18, BorderLayout.WEST);
        jPanelTask18.add(m.jLabelTaskActionDescriptions);
        jPanelTask17.add(m.jScrollPaneTaskActionDescriptions, BorderLayout.CENTER);
        jScrollPaneTaskActionDescriptions.getViewport().add(m.jTextAreaTaskActionDescriptions);
        jPanelTask16.add(m.jLabelTaskActionExecuteIf);
        jPanelTask16.add(m.jLabelTaskActionLabel);
        jPanelTask16.add(m.jTextFieldTaskActionExecuteIf);
        jPanelTask16.add(m.jTextFieldTaskActionLabel);
        jPanelTask19.add(m.jPanelTask20, BorderLayout.NORTH);
        jPanelTask20.add(m.jLabelTaskFunctionIOOperations);
        jPanelTask19.add(m.jScrollPaneTaskFunctionIOOperations, BorderLayout.CENTER);
        jScrollPaneTaskFunctionIOOperations.getViewport().add(m.jTextAreaTaskFunctionIOOperations);
        jPanelTask22.add(m.jPanelTask23, BorderLayout.NORTH);
        jPanelTask23.add(m.jLabelTaskFunctionIODescriptions);
        jPanelTask22.add(m.jScrollPaneTaskFunctionIODescriptions, BorderLayout.CENTER);
        jScrollPaneTaskFunctionIODescriptions.getViewport().add(m.jTextAreaTaskFunctionIODescriptions);
        //
        jLabelTaskFunctionIOImageFunction.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOImageFunction.setBounds(new Rectangle(5, 6, 130, 20));
        jLabelTaskFunctionIOImageFunction.setText(res.getString("S643"));
        jLabelTaskFunctionIOImageFunction.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTaskFunctionIOImageFunction.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskFunctionIOImageFunction.setBounds(new Rectangle(140, 6, 720, 25));
        jTextFieldTaskFunctionIOImageFunction.setOpaque(false);
        jTextFieldTaskFunctionIOImageFunction.setEditable(false);
        jTextFieldTaskFunctionIOImageFunction.setFocusable(false);
        jTextFieldTaskFunctionIOImageFunction.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        jLabelTaskFunctionIOImageFunctionRemarks.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOImageFunctionRemarks.setBounds(new Rectangle(5, 37, 130, 20));
        jLabelTaskFunctionIOImageFunctionRemarks.setText(res.getString("S644"));
        jLabelTaskFunctionIOImageFunctionRemarks.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTaskFunctionIOImageFunctionSummary.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskFunctionIOImageFunctionSummary.setBounds(new Rectangle(140, 37, 720, 25));
        jTextFieldTaskFunctionIOImageFunctionSummary.setOpaque(false);
        jTextFieldTaskFunctionIOImageFunctionSummary.setEditable(false);
        jTextFieldTaskFunctionIOImageFunctionSummary.setFocusable(false);
        jTextFieldTaskFunctionIOImageFunctionSummary.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        jLabelTaskFunctionIOImageTable.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOImageTable.setBounds(new Rectangle(5, 68, 130, 20));
        jLabelTaskFunctionIOImageTable.setText(res.getString("S645"));
        jLabelTaskFunctionIOImageTable.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTaskFunctionIOImageTable.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskFunctionIOImageTable.setBounds(new Rectangle(140, 68, 720, 25));
        jTextFieldTaskFunctionIOImageTable.setOpaque(false);
        jTextFieldTaskFunctionIOImageTable.setEditable(false);
        jTextFieldTaskFunctionIOImageTable.setFocusable(false);
        jTextFieldTaskFunctionIOImageTable.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        jLabelTaskFunctionIOImageTableRemarks.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOImageTableRemarks.setBounds(new Rectangle(5, 99, 130, 20));
        jLabelTaskFunctionIOImageTableRemarks.setText(res.getString("S646"));
        jLabelTaskFunctionIOImageTableRemarks.setHorizontalAlignment(SwingConstants.RIGHT);
        jScrollPaneTaskFunctionIOImageTableRemarks.setBounds(new Rectangle(140, 99, 720, 150));
        jScrollPaneTaskFunctionIOImageTableRemarks.getViewport()
            .add(m.jTextAreaTaskFunctionIOImageTableRemarks, null);
        jScrollPaneTaskFunctionIOImageTableRemarks.setOpaque(false);
        jTextAreaTaskFunctionIOImageTableRemarks.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaTaskFunctionIOImageTableRemarks.setOpaque(false);
        jTextAreaTaskFunctionIOImageTableRemarks.setLineWrap(true);
        jTextAreaTaskFunctionIOImageTableRemarks.setEditable(false);
        jTextAreaTaskFunctionIOImageTableRemarks.setFocusable(false);
        jTextAreaTaskFunctionIOImageTableRemarks.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        //
        jLabelTaskFunctionIOImageFunctionParms.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOImageFunctionParms.setBounds(new Rectangle(5, 68, 130, 20));
        jLabelTaskFunctionIOImageFunctionParms.setText(res.getString("S313"));
        jLabelTaskFunctionIOImageFunctionParms.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTaskFunctionIOImageFunctionParms.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskFunctionIOImageFunctionParms.setBounds(new Rectangle(140, 68, 720, 25));
        jTextFieldTaskFunctionIOImageFunctionParms.setOpaque(false);
        jTextFieldTaskFunctionIOImageFunctionParms.setEditable(false);
        jTextFieldTaskFunctionIOImageFunctionParms.setFocusable(false);
        jTextFieldTaskFunctionIOImageFunctionParms.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        jLabelTaskFunctionIOImageFunctionReturn.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelTaskFunctionIOImageFunctionReturn.setBounds(new Rectangle(5, 99, 130, 20));
        jLabelTaskFunctionIOImageFunctionReturn.setText(res.getString("S642"));
        jLabelTaskFunctionIOImageFunctionReturn.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTaskFunctionIOImageFunctionReturn.setFont(
            new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldTaskFunctionIOImageFunctionReturn.setBounds(new Rectangle(140, 99, 720, 25));
        jTextFieldTaskFunctionIOImageFunctionReturn.setOpaque(false);
        jTextFieldTaskFunctionIOImageFunctionReturn.setEditable(false);
        jTextFieldTaskFunctionIOImageFunctionReturn.setFocusable(false);
        jTextFieldTaskFunctionIOImageFunctionReturn.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        //
        jTableFunctionIOImageIOList.setBackground(SystemColor.control);
        jTableFunctionIOImageIOList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableFunctionIOImageIOList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTableFunctionIOImageIOList.setRowHeight(m.TABLE_ROW_HEIGHT);
        jTableFunctionIOImageIOList.setRowSelectionAllowed(false);
        jTableFunctionIOImageIOList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
        tableModelFunctionIOImageIOList.addColumn("No.");
        tableModelFunctionIOImageIOList.addColumn(res.getString("S680"));
        tableModelFunctionIOImageIOList.addColumn(res.getString("S204"));
        m.column0 = jTableFunctionIOImageIOList.getColumnModel().getColumn(0);
        m.column1 = jTableFunctionIOImageIOList.getColumnModel().getColumn(1);
        m.column2 = jTableFunctionIOImageIOList.getColumnModel().getColumn(2);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(285);
        m.column2.setPreferredWidth(497);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        jTableFunctionIOImageIOList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) jTableFunctionIOImageIOList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        jScrollPaneFunctionIOImageIOList.setBorder(null);
        jScrollPaneFunctionIOImageIOList.getViewport().add(m.jTableFunctionIOImageIOList);
        jScrollPaneFunctionIOImageIOList.setBounds(5, 130, 855, 200);
        jScrollPaneFunctionIOImageIOList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTextPaneTaskFunctionIOImage_mouseClicked(e); } });
    }
}
