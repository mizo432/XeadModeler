package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * ロールノードのビュー
 * タスクリストを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class RoleView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelRole = new JPanel();
    private final JSplitPane jSplitPaneRole = new JSplitPane();
    private final JScrollPane jScrollPaneTaskList = new JScrollPane();
    private final JViewport jViewportTaskList = new JViewport();
    private final JTable jTableTaskList = new JTable();
    private final JPanel jPanelRole2 = new JPanel();
    private final JPanel jPanelRole3 = new JPanel();
    private final JPanel jPanelRole4 = new JPanel();
    private final JPanel jPanelRole5 = new JPanel();
    private final JScrollPane jScrollPaneRoleDescriptions = new JScrollPane();
    
    // Labels
    private final JLabel jLabelRoleName = new JLabel();
    private final JLabel jLabelRoleDescriptions = new JLabel();
    private final JLabel jLabelRoleSortKey = new JLabel();
    private final JLabel jLabelDepartment = new JLabel();
    
    // Fields
    private final JTextField jTextFieldRoleName = new JTextField();
    private final JTextField jTextFieldRoleSortKey = new JTextField();
    private final JTextArea jTextAreaRoleDescriptions = new JTextArea();
    private final JComboBox<String> jComboBoxDepartment = new JComboBox<>();
    
    // Table model
    private final TableModelTaskList tableModelTaskList = new TableModelTaskList();
    
    // Columns
    private javax.swing.table.TableColumn column0;
    private javax.swing.table.TableColumn column1;
    private javax.swing.table.TableColumn column2;
    private javax.swing.table.TableColumn column3;
    private javax.swing.table.TableColumn column4;
    private javax.swing.table.TableColumn column5;
    
    // Renderers
    private final DefaultTableCellRenderer rendererAlignmentCenter = new DefaultTableCellRenderer();
    private final DefaultTableCellRenderer rendererAlignmentLeft = new DefaultTableCellRenderer();
    private DefaultTableCellRenderer rendererTableHeader;

    public RoleView() {
        jTableTaskList.setModel(tableModelTaskList);
        initComponents();
    }

    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jPanelRole.setLayout(new BorderLayout());
        
        jSplitPaneRole.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneRole.setBorder(null);
        jSplitPaneRole.setDividerLocation(100);
        
        jScrollPaneTaskList.setBorder(null);
        
        jPanelRole5.setLayout(null);
        jPanelRole5.setPreferredSize(new Dimension(140, 80));
        jPanelRole5.setMinimumSize(new Dimension(100, 10));
        
        jPanelRole2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelRole2.setPreferredSize(new Dimension(10, 100));
        jPanelRole2.setLayout(new BorderLayout());
        
        jPanelRole4.setPreferredSize(new Dimension(10, 38));
        jPanelRole4.setLayout(null);
        
        jPanelRole3.setLayout(new BorderLayout());
        
        jLabelRoleName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelRoleName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelRoleName.setText(res.getString("S348"));
        jLabelRoleName.setBounds(new Rectangle(5, 6, 130, 20));
        
        jTextFieldRoleName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldRoleName.setBounds(new Rectangle(0, 6, 300, 25));
        
        jLabelRoleSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelRoleSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelRoleSortKey.setText(res.getString("S201"));
        jLabelRoleSortKey.setBounds(new Rectangle(310, 6, 130, 20));
        
        jTextFieldRoleSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldRoleSortKey.setBounds(new Rectangle(445, 6, 100, 25));
        
        jLabelDepartment.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelDepartment.setRequestFocusEnabled(true);
        jLabelDepartment.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelDepartment.setText(res.getString("S356"));
        jLabelDepartment.setBounds(new Rectangle(555, 6, 130, 20));
        
        jComboBoxDepartment.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxDepartment.setActionCommand("comboBoxChanged");
        jComboBoxDepartment.setBounds(new Rectangle(690, 6, 200, 25));
        
        jLabelRoleDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelRoleDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelRoleDescriptions.setText(res.getString("S204"));
        jLabelRoleDescriptions.setBounds(new Rectangle(5, 37, 130, 20));
        
        jTextAreaRoleDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaRoleDescriptions.setLineWrap(true);
        
        // Assemble components
        jPanelRole.add(jSplitPaneRole, BorderLayout.CENTER);
        jSplitPaneRole.add(jScrollPaneTaskList, JSplitPane.BOTTOM);
        jSplitPaneRole.add(jPanelRole2, JSplitPane.TOP);
        jPanelRole2.add(jPanelRole5, BorderLayout.WEST);
        jPanelRole2.add(jPanelRole3, BorderLayout.CENTER);
        jViewportTaskList.add(jTableTaskList, null);
        jScrollPaneTaskList.setViewport(jViewportTaskList);
        jPanelRole3.add(jPanelRole4, BorderLayout.NORTH);
        jPanelRole3.add(jScrollPaneRoleDescriptions, BorderLayout.CENTER);
        jPanelRole3.setPreferredSize(new Dimension(1000, 60));
        jScrollPaneRoleDescriptions.getViewport().add(jTextAreaRoleDescriptions, null);
        jPanelRole4.add(jTextFieldRoleName, null);
        jPanelRole4.add(jTextFieldRoleSortKey, null);
        jPanelRole4.add(jComboBoxDepartment, null);
        jPanelRole4.add(jLabelRoleSortKey, null);
        jPanelRole4.add(jLabelDepartment, null);
        jPanelRole5.add(jLabelRoleName, null);
        jPanelRole5.add(jLabelRoleDescriptions, null);
        
        // Initialize table
        initTable();
    }
    
    /**
     * タスクリストテーブルを初期化します。
     */
    private void initTable() {
        jTableTaskList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableTaskList.setBackground(SystemColor.control);
        jTableTaskList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableTaskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableTaskList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        
        // Columns
        tableModelTaskList.addColumn("No.");
        tableModelTaskList.addColumn(res.getString("S201"));
        tableModelTaskList.addColumn(res.getString("S360"));
        tableModelTaskList.addColumn(res.getString("S361"));
        tableModelTaskList.addColumn(res.getString("S362"));
        tableModelTaskList.addColumn(res.getString("S204"));
        
        column0 = jTableTaskList.getColumnModel().getColumn(0);
        column1 = jTableTaskList.getColumnModel().getColumn(1);
        column2 = jTableTaskList.getColumnModel().getColumn(2);
        column3 = jTableTaskList.getColumnModel().getColumn(3);
        column4 = jTableTaskList.getColumnModel().getColumn(4);
        column5 = jTableTaskList.getColumnModel().getColumn(5);
        
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(100);
        column2.setPreferredWidth(250);
        column3.setPreferredWidth(200);
        column4.setPreferredWidth(200);
        column5.setPreferredWidth(700);
        
        // Cell renderers
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(rendererAlignmentLeft);
        column5.setCellRenderer(rendererAlignmentLeft);
        
        // Header
        jTableTaskList.getTableHeader().setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableTaskList.getTableHeader().getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public JPanel getPanel() {
        return jPanelRole;
    }

    public JSplitPane getSplitPane() {
        return jSplitPaneRole;
    }

    public JScrollPane getTaskListScrollPane() {
        return jScrollPaneTaskList;
    }

    public JTable getTaskListTable() {
        return jTableTaskList;
    }

    public TableModelTaskList getTaskListTableModel() {
        return tableModelTaskList;
    }

    public JTextField getNameField() {
        return jTextFieldRoleName;
    }

    public JTextField getSortKeyField() {
        return jTextFieldRoleSortKey;
    }

    public JTextArea getDescriptionsArea() {
        return jTextAreaRoleDescriptions;
    }

    public JComboBox<String> getDepartmentCombo() {
        return jComboBoxDepartment;
    }

    /**
     * タスクリストのテーブルモデル
     */
    public static class TableModelTaskList extends DefaultTableModel {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}
