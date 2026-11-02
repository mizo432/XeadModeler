package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;
import xeadModeler.model.json.Relationship;
import xeadModeler.model.json.TableKey;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.DefaultCellEditor;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * テーブルリストノードのビュー
 * ネイティブテーブルリスト、外部テーブルリスト、データモデルスライドショーなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableListView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelTableList = new JPanel();
    private final JScrollPane jScrollPaneNativeTableList = new JScrollPane();
    private final JViewport jViewportNativeTableList = new JViewport();
    private final JTabbedPane jTabbedPaneTableList = new JTabbedPane();
    private final JScrollPane jScrollPaneForeignTableList = new JScrollPane();
    private final JViewport jViewportForeignTableList = new JViewport();
    private final JSplitPane jSplitPaneDatamodel = new JSplitPane();
    private final JPanel jPanelDatamodelTop = new JPanel();
    private final JPanel jPanelDatamodelDescriptions = new JPanel();
    private final JLabel jLabelDatamodelDescriptions = new JLabel();
    private final JScrollPane jScrollPaneDatamodelDescriptions = new JScrollPane();
    private final JTextArea jTextAreaDatamodelDescriptions = new JTextArea();
    private final JScrollPane jScrollPaneDatamodel = new JScrollPane();
    
    // Data model components
    private final JPanel jPanelDatamodel = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            if (isToShowGridsOnDataModel) {
                if (isNormalColorConfigOnDataModel) {
                    g2.setColor(Modeler.GRID_COLOR1);
                } else {
                    g2.setColor(Modeler.GRID_COLOR2);
                }
                if (modelSize.equals("S")) {
                    int gridPos = 4;
                    int height = this.getPreferredSize().height + 100;
                    if (height < 2000) {
                        height = 2000;
                    }
                    int width = this.getPreferredSize().width + 100;
                    if (width < 2500) {
                        width = 2500;
                    }
                    while (gridPos < width) {
                        g2.draw(new Line2D.Double(gridPos, 0, gridPos, height));
                        gridPos = gridPos + 8;
                    }
                    gridPos = 4;
                    while (gridPos < height) {
                        g2.draw(new Line2D.Double(0, gridPos, width, gridPos));
                        gridPos = gridPos + 8;
                    }
                }
                if (modelSize.equals("M")) {
                    int gridPos = 8;
                    int height = this.getPreferredSize().height + 100;
                    if (height < 2000) {
                        height = 2000;
                    }
                    int width = this.getPreferredSize().width + 100;
                    if (width < 2500) {
                        width = 2500;
                    }
                    while (gridPos < width) {
                        g2.draw(new Line2D.Double(gridPos, 0, gridPos, height));
                        gridPos = gridPos + 16;
                    }
                    gridPos = 8;
                    while (gridPos < height) {
                        g2.draw(new Line2D.Double(0, gridPos, width, gridPos));
                        gridPos = gridPos + 16;
                    }
                }
                if (modelSize.equals("L")) {
                    int gridPos = 12;
                    int height = this.getPreferredSize().height + 100;
                    if (height < 2000) {
                        height = 2000;
                    }
                    int width = this.getPreferredSize().width + 100;
                    if (width < 2500) {
                        width = 2500;
                    }
                    while (gridPos < width) {
                        g2.draw(new Line2D.Double(gridPos, 0, gridPos, height));
                        gridPos = gridPos + 24;
                    }
                    gridPos = 12;
                    while (gridPos < height) {
                        g2.draw(new Line2D.Double(0, gridPos, width, gridPos));
                        gridPos = gridPos + 24;
                    }
                }
            }

            if (isNormalColorConfigOnDataModel) {
                g2.setColor(Modeler.RELATIONSHIP_COLOR1);
            } else {
                g2.setColor(Modeler.RELATIONSHIP_COLOR2);
            }

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Data model drawing is handled by Modeler
            // This panel delegates to Modeler for entity boxes and relationship lines
        }
    };
    
    // Slide show components
    private final JDialog jDialogDatamodelSlideShow = new JDialog();
    private final JPanel jPanelDatamodelSlideShow1 = new JPanel();
    private final JPanel jPanelDatamodelSlideShow2 = new JPanel() {
        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            if (isNormalColorConfigOnDataModel) {
                g2.setColor(Modeler.RELATIONSHIP_COLOR1);
            } else {
                g2.setColor(Modeler.RELATIONSHIP_COLOR2);
            }

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Slide show drawing is handled by Modeler
            // This panel delegates to Modeler for entity boxes and relationship lines
        }
    };
    private final JLabel jLabelDatamodelSlideShowPageGuide = new JLabel();
    
    // Tables (referenced from Modeler)
    private JTable jTableNativeTableList;
    private JTable jTableForeignTableList;
    
    // Data model arrays (accessed via Modeler)
    private final ArrayList<Relationship> relationshipElementArray = new ArrayList<>();
    
    // State
    private int previousSelectedIndex_jTabbedPaneTableList = 0;
    private int selectedRow_jTableNativeTableList;
    private int selectedRow_jTableForeignTableList;
    private boolean sizeOfTableOnModelChanged = false;
    private TableKey draggingKeyElement = null;
    private int datamodelSlideNumber = 0;
    private int datamodelSlideTotalNumber = 0;
    private boolean isShowingInstance = false;
    private String datamodelPageTitle;
    public String initialModelSize = "M";
    public String modelSize = "M";
    public boolean isToShowGridsOnDataModel = true;
    public boolean isNormalColorConfigOnDataModel = true;
    
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
    private javax.swing.table.TableColumn columnA;
    private javax.swing.table.TableColumn columnB;
    private javax.swing.table.TableColumn columnC;
    private javax.swing.table.TableColumn columnD;
    private javax.swing.table.TableColumn columnE;
    
    private Modeler modeler;
    
    /**
     * テーブルリスト操作リスナーインターフェース
     */
    public interface TableListListener {
        void onTableListChanged();
    }
    
    private TableListListener tableListListener;
    
    public TableListView(Modeler modeler, TableListListener listener) {
        this.modeler = modeler;
        this.tableListListener = listener;
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelTableList;
    }
    
    public JTabbedPane getTabbedPane() {
        return jTabbedPaneTableList;
    }
    
    public JTable getNativeTableList() {
        return jTableNativeTableList;
    }
    
    public JTable getForeignTableList() {
        return jTableForeignTableList;
    }
    
    public JSplitPane getDatamodelSplitPane() {
        return jSplitPaneDatamodel;
    }
    
    public JPanel getDatamodelPanel() {
        return jPanelDatamodel;
    }
    
    public JDialog getSlideShowDialog() {
        return jDialogDatamodelSlideShow;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jPanelTableList.setMinimumSize(new Dimension(103, 70));
        jPanelTableList.setPreferredSize(new Dimension(459, 430));
        jPanelTableList.setLayout(new BorderLayout());
        jPanelDatamodel.setBackground(Modeler.DESKTOP_COLOR);
        jPanelDatamodel.setBorder(null);
        jPanelDatamodel.setLayout(null);
        jPanelTableList.add(jTabbedPaneTableList, BorderLayout.CENTER);
        jTabbedPaneTableList.addTab(res.getString("S464"), modeler.imageIconTable, jScrollPaneNativeTableList);
        jTabbedPaneTableList.addTab(res.getString("S465"), modeler.imageIconForeignTable,
            jScrollPaneForeignTableList);
        jTabbedPaneTableList.addTab(res.getString("S466"), modeler.imageIconModel, jSplitPaneDatamodel);
        jTabbedPaneTableList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableNativeTableList = modeler.jTableNativeTableList;
        jTableForeignTableList = modeler.jTableForeignTableList;
        jViewportNativeTableList.add(jTableNativeTableList, null);
        jScrollPaneNativeTableList.setViewport(jViewportNativeTableList);
        jViewportForeignTableList.add(jTableForeignTableList, null);
        jScrollPaneForeignTableList.setViewport(jViewportForeignTableList);
        //(jTableNativeTableList)//
        jTableNativeTableList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableNativeTableList.setBackground(SystemColor.control);
        jTableNativeTableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableNativeTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableNativeTableList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        column0 = jTableNativeTableList.getColumnModel().getColumn(0);
        column1 = jTableNativeTableList.getColumnModel().getColumn(1);
        column2 = jTableNativeTableList.getColumnModel().getColumn(2);
        column3 = jTableNativeTableList.getColumnModel().getColumn(3);
        column4 = jTableNativeTableList.getColumnModel().getColumn(4);
        column5 = jTableNativeTableList.getColumnModel().getColumn(5);
        column6 = jTableNativeTableList.getColumnModel().getColumn(6);
        column7 = jTableNativeTableList.getColumnModel().getColumn(7);
        column8 = jTableNativeTableList.getColumnModel().getColumn(8);
        column9 = jTableNativeTableList.getColumnModel().getColumn(9);
        columnA = jTableNativeTableList.getColumnModel().getColumn(10);
        columnB = jTableNativeTableList.getColumnModel().getColumn(11);
        columnC = jTableNativeTableList.getColumnModel().getColumn(12);
        columnD = jTableNativeTableList.getColumnModel().getColumn(13);
        columnE = jTableNativeTableList.getColumnModel().getColumn(14);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(120);
        column2.setPreferredWidth(310);
        column3.setPreferredWidth(75);
        column4.setPreferredWidth(30);
        column5.setPreferredWidth(30);
        column6.setPreferredWidth(30);
        column7.setPreferredWidth(30);
        column8.setPreferredWidth(120);
        column9.setPreferredWidth(350);
        columnA.setPreferredWidth(45);
        columnB.setPreferredWidth(42);
        columnC.setPreferredWidth(42);
        columnD.setPreferredWidth(42);
        columnE.setPreferredWidth(42);
        rendererAlignmentCenter = new DefaultTableCellRenderer();
        rendererAlignmentCenter.setHorizontalAlignment(SwingConstants.CENTER);
        rendererAlignmentLeft = new DefaultTableCellRenderer();
        rendererAlignmentLeft.setHorizontalAlignment(SwingConstants.LEFT);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        DefaultTableCellRenderer checkBoxRenderer = new DefaultTableCellRenderer();
        checkBoxRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        column3.setCellRenderer(checkBoxRenderer);
        DefaultCellEditor editorWithCheckBox = new DefaultCellEditor(
            new JCheckBox(res.getString("S483")));
        column3.setCellEditor(editorWithCheckBox);
        column4.setCellRenderer(rendererAlignmentCenter);
        column5.setCellRenderer(rendererAlignmentCenter);
        column6.setCellRenderer(rendererAlignmentCenter);
        column7.setCellRenderer(rendererAlignmentCenter);
        column8.setCellRenderer(rendererAlignmentLeft);
        column9.setCellRenderer(rendererAlignmentLeft);
        columnA.setCellRenderer(rendererAlignmentCenter);
        columnB.setCellRenderer(rendererAlignmentCenter);
        columnC.setCellRenderer(rendererAlignmentCenter);
        columnD.setCellRenderer(rendererAlignmentCenter);
        columnE.setCellRenderer(rendererAlignmentCenter);
        jTableNativeTableList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableNativeTableList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableForeignTableList)//
        jTableForeignTableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableForeignTableList.setBackground(SystemColor.control);
        jTableForeignTableList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableForeignTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableForeignTableList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
        column0 = jTableForeignTableList.getColumnModel().getColumn(0);
        column1 = jTableForeignTableList.getColumnModel().getColumn(1);
        column2 = jTableForeignTableList.getColumnModel().getColumn(2);
        column3 = jTableForeignTableList.getColumnModel().getColumn(3);
        column4 = jTableForeignTableList.getColumnModel().getColumn(4);
        column5 = jTableForeignTableList.getColumnModel().getColumn(5);
        column6 = jTableForeignTableList.getColumnModel().getColumn(6);
        column7 = jTableForeignTableList.getColumnModel().getColumn(7);
        column8 = jTableForeignTableList.getColumnModel().getColumn(8);
        column9 = jTableForeignTableList.getColumnModel().getColumn(9);
        columnA = jTableForeignTableList.getColumnModel().getColumn(10);
        columnB = jTableForeignTableList.getColumnModel().getColumn(11);
        column0.setPreferredWidth(40);
        column1.setPreferredWidth(220);
        column2.setPreferredWidth(120);
        column3.setPreferredWidth(310);
        column4.setPreferredWidth(75);
        column5.setPreferredWidth(30);
        column6.setPreferredWidth(30);
        column7.setPreferredWidth(30);
        column8.setPreferredWidth(30);
        column9.setPreferredWidth(150);
        columnA.setPreferredWidth(350);
        columnB.setPreferredWidth(45);
        column0.setCellRenderer(rendererAlignmentCenter);
        column1.setCellRenderer(rendererAlignmentLeft);
        column2.setCellRenderer(rendererAlignmentLeft);
        column3.setCellRenderer(rendererAlignmentLeft);
        column4.setCellRenderer(checkBoxRenderer);
        column4.setCellEditor(editorWithCheckBox);
        column5.setCellRenderer(rendererAlignmentCenter);
        column6.setCellRenderer(rendererAlignmentCenter);
        column7.setCellRenderer(rendererAlignmentCenter);
        column8.setCellRenderer(rendererAlignmentCenter);
        column9.setCellRenderer(rendererAlignmentLeft);
        columnA.setCellRenderer(rendererAlignmentLeft);
        columnB.setCellRenderer(rendererAlignmentCenter);
        jTableForeignTableList.getTableHeader()
            .setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        rendererTableHeader = (DefaultTableCellRenderer) jTableForeignTableList.getTableHeader()
            .getDefaultRenderer();
        rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jSplitPaneDatamodel)//
        jSplitPaneDatamodel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneDatamodel.setBorder(null);
        jSplitPaneDatamodel.setDividerLocation(80);
        jSplitPaneDatamodel.add(jPanelDatamodelTop, JSplitPane.TOP);
        jSplitPaneDatamodel.add(jScrollPaneDatamodel, JSplitPane.BOTTOM);
        jPanelDatamodelTop.setBorder(BorderFactory.createEtchedBorder());
        jPanelDatamodelTop.setMinimumSize(new Dimension(500, 0));
        jPanelDatamodelTop.setLayout(new BorderLayout());
        jPanelDatamodelTop.add(jPanelDatamodelDescriptions, BorderLayout.NORTH);
        jPanelDatamodelTop.add(jScrollPaneDatamodelDescriptions, BorderLayout.CENTER);
        jPanelDatamodelDescriptions.setLayout(null);
        jPanelDatamodelDescriptions.setPreferredSize(new Dimension(500, 20));
        jPanelDatamodelDescriptions.add(jLabelDatamodelDescriptions, null);
        jLabelDatamodelDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelDatamodelDescriptions.setText(res.getString("S467"));
        jLabelDatamodelDescriptions.setBounds(new Rectangle(5, 0, 300, 20));
        jTextAreaDatamodelDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaDatamodelDescriptions.setLineWrap(true);
        jScrollPaneDatamodelDescriptions.getViewport().add(jTextAreaDatamodelDescriptions, null);
        jScrollPaneDatamodel.getViewport().add(jPanelDatamodel, null);
        jScrollPaneDatamodel.getVerticalScrollBar().setUnitIncrement(25);
        //(Slide Show Screen Dialog)//
        jDialogDatamodelSlideShow.setResizable(false);
        jDialogDatamodelSlideShow.setFocusable(true);
        jDialogDatamodelSlideShow.setUndecorated(true);
        jPanelDatamodelSlideShow1.setLayout(null);
        jPanelDatamodelSlideShow1.setBounds(new Rectangle(0, 0, 2000, 1500));
        jPanelDatamodelSlideShow1.setBackground(Modeler.DESKTOP_COLOR);
        jPanelDatamodelSlideShow2.setBackground(Modeler.DESKTOP_COLOR);
        jPanelDatamodelSlideShow2.setBounds(new Rectangle(0, 20, 2000, 1500));
        jPanelDatamodelSlideShow2.setLayout(null);
        jLabelDatamodelSlideShowPageGuide.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, 17));
        jLabelDatamodelSlideShowPageGuide.setForeground(Color.lightGray);
        jLabelDatamodelSlideShowPageGuide.setBounds(new Rectangle(15, 15, 600, 20));
        jDialogDatamodelSlideShow.getContentPane().add(jPanelDatamodelSlideShow1, null);
        jPanelDatamodelSlideShow1.add(jLabelDatamodelSlideShowPageGuide, null);
        jPanelDatamodelSlideShow1.add(jPanelDatamodelSlideShow2, null);
    }
}
