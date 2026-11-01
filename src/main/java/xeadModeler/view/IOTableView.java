package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * IOテーブルノードのビュー
 * IOテーブル情報、フィールドリスト、CRUDチェックボックスなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOTableView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelIOTable = new JPanel();
    private final JPanel jPanelIOTable1 = new JPanel();
    private final JPanel jPanelIOTable2 = new JPanel();
    private final JPanel jPanelIOTable3 = new JPanel();
    private final JPanel jPanelIOTable4 = new JPanel();
    private final JPanel jPanelIOTable5 = new JPanel();
    private final JSplitPane jSplitPaneIOTable = new JSplitPane();
    
    // Labels
    private final JLabel jLabelIOTableName = new JLabel();
    private final JLabel jLabelIOTablePK = new JLabel();
    private final JLabel jLabelIOTablePosition = new JLabel();
    private final JLabel jLabelIOTableCRUD = new JLabel();
    private final JLabel jLabelIOTableNameExtension = new JLabel();
    private final JLabel jLabelIOTableDescriptions = new JLabel();
    
    // Fields
    private final JTextField jTextFieldIOTableName = new JTextField();
    private final JTextField jTextFieldIOTablePK = new JTextField();
    private final JTextField jTextFieldIOTableNameExtension = new JTextField();
    private final JComboBox jComboBoxIOTablePosition = new JComboBox();
    
    // Checkboxes
    private final JCheckBox jCheckBoxIOTableC = new JCheckBox();
    private final JCheckBox jCheckBoxIOTableR = new JCheckBox();
    private final JCheckBox jCheckBoxIOTableU = new JCheckBox();
    private final JCheckBox jCheckBoxIOTableD = new JCheckBox();
    
    // Descriptions
    private final JScrollPane jScrollPaneIOTableDescriptions = new JScrollPane();
    private final JTextArea jTextAreaIOTableDescriptions = new JTextArea();
    
    // Field list
    private final JScrollPane jScrollPaneIOTableFieldList = new JScrollPane();
    private final JTable jTableIOTableFieldList;
    
    // State
    private int selectedRow_jTableIOTableFieldList;
    
    private Modeler modeler;
    
    /**
     * IOテーブル操作リスナーインターフェース
     */
    public interface IOTableListener {
        void onIOTableChanged();
    }
    
    private IOTableListener ioTableListener;
    
    public IOTableView(Modeler modeler, IOTableListener listener) {
        this.modeler = modeler;
        this.ioTableListener = listener;
        jTableIOTableFieldList = modeler.jTableIOTableFieldList;
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelIOTable;
    }
    
    public JTable getFieldList() {
        return jTableIOTableFieldList;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jSplitPaneIOTable.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOTable.setBorder(null);
        jSplitPaneIOTable.add(jPanelIOTable1, JSplitPane.TOP);
        jSplitPaneIOTable.add(jScrollPaneIOTableFieldList, JSplitPane.BOTTOM);
        jSplitPaneIOTable.setDividerLocation(160);
        jLabelIOTableName.setBounds(new Rectangle(5, 8, 130, 20));
        jLabelIOTableName.setText(res.getString("S471"));
        jLabelIOTableName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOTableName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOTableName.setBorder(jTextFieldIOTablePK.getBorder());
        jTextFieldIOTableName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOTableName.setBounds(new Rectangle(0, 5, 535, 25));
        jLabelIOTablePK.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOTablePK.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOTablePK.setText(res.getString("S739"));
        jLabelIOTablePK.setBounds(new Rectangle(5, 39, 130, 20));
        jTextFieldIOTablePK.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOTablePK.setPreferredSize(new Dimension(81, 22));
        jTextFieldIOTablePK.setEditable(false);
        jTextFieldIOTablePK.setBounds(new Rectangle(0, 36, 685, 25));
        jLabelIOTablePosition.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOTablePosition.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOTablePosition.setText(res.getString("S750"));
        jLabelIOTablePosition.setBounds(new Rectangle(420, 70, 110, 20));
        jComboBoxIOTablePosition.setBounds(new Rectangle(535, 67, 150, 25));
        jComboBoxIOTablePosition.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxIOTablePosition.addItem(res.getString("S751"));
        jComboBoxIOTablePosition.addItem(res.getString("S752"));
        jComboBoxIOTablePosition.addItem(res.getString("S753"));
        jComboBoxIOTablePosition.addItem(res.getString("S754"));
        jComboBoxIOTablePosition.addItem(res.getString("S755"));
        jLabelIOTableCRUD.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOTableCRUD.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOTableCRUD.setText(res.getString("S745"));
        jLabelIOTableCRUD.setBounds(new Rectangle(5, 70, 130, 20));
        jPanelIOTable4.setLayout(null);
        jPanelIOTable4.setBounds(new Rectangle(0, 67, 417, 26));
        jPanelIOTable4.setBorder(BorderFactory.createEtchedBorder());
        jCheckBoxIOTableC.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxIOTableC.setText("Create");
        jCheckBoxIOTableC.setBounds(new Rectangle(2, 1, 100, 23));
        jCheckBoxIOTableR.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxIOTableR.setText("Read");
        jCheckBoxIOTableR.setBounds(new Rectangle(102, 1, 100, 23));
        jCheckBoxIOTableU.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxIOTableU.setText("Update");
        jCheckBoxIOTableU.setBounds(new Rectangle(202, 1, 105, 23));
        jCheckBoxIOTableD.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jCheckBoxIOTableD.setText("Delete");
        jCheckBoxIOTableD.setBounds(new Rectangle(307, 1, 100, 23));
        jLabelIOTableNameExtension.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOTableNameExtension.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOTableNameExtension.setText(res.getString("S742"));
        jLabelIOTableNameExtension.setBounds(new Rectangle(540, 8, 80, 20));
        jTextFieldIOTableNameExtension.setBounds(new Rectangle(625, 5, 60, 25));
        jTextFieldIOTableNameExtension.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOTableDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOTableDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOTableDescriptions.setText(res.getString("S204"));
        jLabelIOTableDescriptions.setBounds(new Rectangle(5, 101, 130, 20));
        jTextAreaIOTableDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaIOTableDescriptions.setLineWrap(true);
        jScrollPaneIOTableDescriptions.getViewport().add(jTextAreaIOTableDescriptions, null);
        jPanelIOTable5.setLayout(null);
        jPanelIOTable5.setPreferredSize(new Dimension(140, 80));
        jPanelIOTable5.setMinimumSize(new Dimension(140, 10));
        jPanelIOTable5.add(jLabelIOTableName, null);
        jPanelIOTable5.add(jLabelIOTablePK, null);
        jPanelIOTable5.add(jLabelIOTableCRUD, null);
        jPanelIOTable5.add(jLabelIOTableDescriptions, null);
        jPanelIOTable.setLayout(new BorderLayout());
        jPanelIOTable2.setLayout(new BorderLayout());
        jPanelIOTable2.setPreferredSize(new Dimension(1000, 60));
        jPanelIOTable1.setLayout(new BorderLayout());
        jPanelIOTable1.setPreferredSize(new Dimension(10, 130));
        jPanelIOTable1.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOTable1.setMinimumSize(new Dimension(100, 34));
        jPanelIOTable3.setLayout(null);
        jPanelIOTable3.setPreferredSize(new Dimension(10, 98));
        jScrollPaneIOTableFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanelIOTable2.add(jScrollPaneIOTableDescriptions, BorderLayout.CENTER);
        jPanelIOTable2.add(jPanelIOTable3, BorderLayout.NORTH);
        jPanelIOTable3.add(jTextFieldIOTableName, null);
        jPanelIOTable1.add(jPanelIOTable5, BorderLayout.WEST);
        jPanelIOTable1.add(jPanelIOTable2, BorderLayout.CENTER);
        jPanelIOTable.add(jSplitPaneIOTable, BorderLayout.CENTER);
        jScrollPaneIOTableFieldList.getViewport().add(jTableIOTableFieldList, null);
        jPanelIOTable3.add(jTextFieldIOTablePK, null);
        jPanelIOTable3.add(jComboBoxIOTablePosition, null);
        jPanelIOTable3.add(jLabelIOTablePosition, null);
        jPanelIOTable3.add(jTextFieldIOTableNameExtension, null);
        jPanelIOTable3.add(jLabelIOTableNameExtension, null);
        jPanelIOTable3.add(jPanelIOTable4, null);
        jPanelIOTable4.add(jCheckBoxIOTableC, null);
        jPanelIOTable4.add(jCheckBoxIOTableR, null);
        jPanelIOTable4.add(jCheckBoxIOTableU, null);
        jPanelIOTable4.add(jCheckBoxIOTableD, null);
        //(jTableIOTableFieldList)//
        jTableIOTableFieldList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableIOTableFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableIOTableFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableIOTableFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT_EDITABLE);
    }
}
