package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.event.TableColumnModelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

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

    /**
     * ModelerのIOTableコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jSplitPaneIOTable.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOTable.setBorder(null);
        m.jSplitPaneIOTable.add(m.jPanelIOTable1, JSplitPane.TOP);
        m.jSplitPaneIOTable.add(m.jScrollPaneIOTableFieldList, JSplitPane.BOTTOM);
        m.jSplitPaneIOTable.setDividerLocation(160);
        m.jLabelIOTableName.setBounds(new Rectangle(5, 8, 130, 20));
        m.jLabelIOTableName.setText(res.getString("S471"));
        m.jLabelIOTableName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOTableName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //m.jTextFieldIOTableName.setEditable(false);
        m.jTextFieldIOTableName.setBorder(m.jTextFieldIOTablePK.getBorder());
        m.jTextFieldIOTableName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOTableName.setBounds(new Rectangle(0, 5, 535, 25));
        m.jTextFieldIOTableName.addMouseListener(new MouseAdapter() { public void mouseEntered(MouseEvent e) { m.jTextFieldIOTableName_mouseEntered(e); } public void mouseClicked(MouseEvent e) { m.jTextFieldIOTableName_mouseClicked(e); } });
        //jLabelIOTableID.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jLabelIOTableID.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelIOTableID.setText("ID");
        //jLabelIOTableID.setBounds(new Rectangle(360, 8, 70, 20));
        //jTextFieldIOTableID.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jTextFieldIOTableID.setEditable(false);
        //jTextFieldIOTableID.setBounds(new Rectangle(435, 5, 170, 25));
        //jButtonIOTableJump.setBounds(new Rectangle(610, 4, 291, 26));
        //jButtonIOTableJump.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jButtonIOTableJump.setText(res.getString("S761"));
        //jButtonIOTableJump.addActionListener(m.jButtonIOTableJump_actionPerformed);
        m.jLabelIOTablePK.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOTablePK.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOTablePK.setText(res.getString("S739"));
        m.jLabelIOTablePK.setBounds(new Rectangle(5, 39, 130, 20));
        m.jTextFieldIOTablePK.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOTablePK.setPreferredSize(new Dimension(81, 22));
        m.jTextFieldIOTablePK.setEditable(false);
        m.jTextFieldIOTablePK.setBounds(new Rectangle(0, 36, 685, 25));
        m.jLabelIOTablePosition.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOTablePosition.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOTablePosition.setText(res.getString("S750"));
        m.jLabelIOTablePosition.setBounds(new Rectangle(420, 70, 110, 20));
        m.jComboBoxIOTablePosition.setBounds(new Rectangle(535, 67, 150, 25));
        m.jComboBoxIOTablePosition.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jComboBoxIOTablePosition.addItem(res.getString("S751")); //0:Primary Table//
        m.jComboBoxIOTablePosition.addItem(res.getString("S752")); //1:Header Table//
        m.jComboBoxIOTablePosition.addItem(res.getString("S753")); //2:Detail Table//
        m.jComboBoxIOTablePosition.addItem(res.getString("S754")); //3:Referred Table//
        m.jComboBoxIOTablePosition.addItem(res.getString("S755")); //4: Other //
        m.jLabelIOTableCRUD.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOTableCRUD.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOTableCRUD.setText(res.getString("S745"));
        m.jLabelIOTableCRUD.setBounds(new Rectangle(5, 70, 130, 20));
        m.jPanelIOTable4.setLayout(null);
        m.jPanelIOTable4.setBounds(new Rectangle(0, 67, 417, 26));
        m.jPanelIOTable4.setBorder(BorderFactory.createEtchedBorder());
        m.jCheckBoxIOTableC.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxIOTableC.setText("Create");
        m.jCheckBoxIOTableC.setBounds(new Rectangle(2, 1, 100, 23));
        m.jCheckBoxIOTableC.addActionListener(e -> m.jCheckBoxIOTableC_actionPerformed(e));
        m.jCheckBoxIOTableR.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxIOTableR.setText("Read");
        m.jCheckBoxIOTableR.setBounds(new Rectangle(102, 1, 100, 23));
        m.jCheckBoxIOTableU.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxIOTableU.setText("Update");
        m.jCheckBoxIOTableU.setBounds(new Rectangle(202, 1, 105, 23));
        m.jCheckBoxIOTableU.addActionListener(e -> m.jCheckBoxIOTableU_actionPerformed(e));
        m.jCheckBoxIOTableD.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jCheckBoxIOTableD.setText("Delete");
        m.jCheckBoxIOTableD.setBounds(new Rectangle(307, 1, 100, 23));
        m.jCheckBoxIOTableD.addActionListener(e -> m.jCheckBoxIOTableD_actionPerformed(e));
        //jLabelIOTableSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jLabelIOTableSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelIOTableSortKey.setText(res.getString("S540"));
        //jLabelIOTableSortKey.setBounds(new Rectangle(420, 70, 90, 20));
        //jTextFieldIOTableSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jTextFieldIOTableSortKey.setBounds(new Rectangle(515, 67, 80, 25));
        m.jLabelIOTableNameExtension.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOTableNameExtension.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOTableNameExtension.setText(res.getString("S742"));
        m.jLabelIOTableNameExtension.setBounds(new Rectangle(540, 8, 80, 20));
        m.jTextFieldIOTableNameExtension.setBounds(new Rectangle(625, 5, 60, 25));
        m.jTextFieldIOTableNameExtension.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOTableDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOTableDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOTableDescriptions.setText(res.getString("S204"));
        m.jLabelIOTableDescriptions.setBounds(new Rectangle(5, 101, 130, 20));
        m.jTextAreaIOTableDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaIOTableDescriptions.setLineWrap(true);
        m.jScrollPaneIOTableDescriptions.getViewport().add(m.jTextAreaIOTableDescriptions, null);
        m.jPanelIOTable5.setLayout(null);
        m.jPanelIOTable5.setPreferredSize(new Dimension(140, 80));
        m.jPanelIOTable5.setMinimumSize(new Dimension(140, 10));
        m.jPanelIOTable5.add(m.jLabelIOTableName, null);
        m.jPanelIOTable5.add(m.jLabelIOTablePK, null);
        m.jPanelIOTable5.add(m.jLabelIOTableCRUD, null);
        m.jPanelIOTable5.add(m.jLabelIOTableDescriptions, null);
        m.jPanelIOTable.setLayout(new BorderLayout());
        m.jPanelIOTable2.setLayout(new BorderLayout());
        m.jPanelIOTable2.setPreferredSize(new Dimension(1000, 60));
        m.jPanelIOTable1.setLayout(new BorderLayout());
        m.jPanelIOTable1.setPreferredSize(new Dimension(10, 130));
        m.jPanelIOTable1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOTable1.setMinimumSize(new Dimension(100, 34));
        m.jPanelIOTable3.setLayout(null);
        m.jPanelIOTable3.setPreferredSize(new Dimension(10, 98));
        m.jScrollPaneIOTableFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jScrollPaneIOTableFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneIOTableFieldList_mouseClicked(e); } });
        m.jPanelIOTable2.add(m.jScrollPaneIOTableDescriptions, BorderLayout.CENTER);
        m.jPanelIOTable2.add(m.jPanelIOTable3, BorderLayout.NORTH);
        m.jPanelIOTable3.add(m.jTextFieldIOTableName, null);
        m.jPanelIOTable1.add(m.jPanelIOTable5, BorderLayout.WEST);
        m.jPanelIOTable1.add(m.jPanelIOTable2, BorderLayout.CENTER);
        m.jPanelIOTable.add(m.jSplitPaneIOTable, BorderLayout.CENTER);
        m.jScrollPaneIOTableFieldList.getViewport().add(m.jTableIOTableFieldList, null);
        //m.jPanelIOTable3.add(m.jLabelIOTableID, null);
        //m.jPanelIOTable3.add(m.jTextFieldIOTableID, null);
        m.jPanelIOTable3.add(m.jTextFieldIOTablePK, null);
        m.jPanelIOTable3.add(m.jComboBoxIOTablePosition, null);
        m.jPanelIOTable3.add(m.jLabelIOTablePosition, null);
        m.jPanelIOTable3.add(m.jTextFieldIOTableNameExtension, null);
        m.jPanelIOTable3.add(m.jLabelIOTableNameExtension, null);
        m.jPanelIOTable3.add(m.jPanelIOTable4, null);
        m.jPanelIOTable4.add(m.jCheckBoxIOTableC, null);
        m.jPanelIOTable4.add(m.jCheckBoxIOTableR, null);
        m.jPanelIOTable4.add(m.jCheckBoxIOTableU, null);
        m.jPanelIOTable4.add(m.jCheckBoxIOTableD, null);
        //m.jPanelIOTable3.add(jLabelIOTableSortKey, null);
        //m.jPanelIOTable3.add(jTextFieldIOTableSortKey, null);
        //m.jPanelIOTable3.add(jButtonIOTableJump, null);
        //(jTableIOTableFieldList)//
        m.jTableIOTableFieldList.addFocusListener(new Modeler_FocusListener());
        m.jTableIOTableFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableIOTableFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableIOTableFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableIOTableFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT_EDITABLE);
        m.jTableIOTableFieldList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableIOTableFieldList_valueChanged(e));
        m.jTableIOTableFieldList.getColumnModel()
            .addColumnModelListener(new TableColumnModelListener() { public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) { m.jTableIOTableFieldList_columnSelectionChanged(e); } public void columnMarginChanged(javax.swing.event.ChangeEvent e) {} public void columnMoved(javax.swing.event.TableColumnModelEvent e) {} public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {} public void columnAdded(javax.swing.event.TableColumnModelEvent e) {} });
        m.jTableIOTableFieldList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableIOTableFieldList_mouseClicked(e); } });
    }
}
