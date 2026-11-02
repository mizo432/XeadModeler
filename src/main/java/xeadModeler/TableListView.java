package xeadModeler;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;
import xeadModeler.model.json.Relationship;
import xeadModeler.model.json.TableKey;
import xeadModeler.view.TabbedPaneChangeListener;
import xeadModeler.view.CheckBoxRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.DefaultCellEditor;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

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

    /**
     * ModelerのTableListコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelTableList.setMinimumSize(new Dimension(103, 70));
        m.jPanelTableList.setPreferredSize(new Dimension(459, 430));
        m.jPanelTableList.setLayout(new BorderLayout());
        m.jPanelDatamodel.setBackground(m.DESKTOP_COLOR);
        m.jPanelDatamodel.setBorder(null);
        m.jPanelDatamodel.setLayout(null);
        m.jPanelDatamodel.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelDatamodel_mouseClicked(e); } public void mousePressed(MouseEvent e) { m.jPanelDatamodel_mousePressed(e); } public void mouseReleased(MouseEvent e) { m.jPanelDatamodel_mouseReleased(e); } });
        m.jPanelDatamodel.addMouseMotionListener(new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jPanelDatamodel_mouseDragged(e); } });
        m.jPanelDatamodel.addMouseWheelListener(e -> m.mouseWheelMoved(e));
        m.jPanelTableList.add(m.jTabbedPaneTableList, BorderLayout.CENTER);
        m.jTabbedPaneTableList.addTab(res.getString("S464"), m.imageIconTable, m.jScrollPaneNativeTableList);
        m.jTabbedPaneTableList.addTab(res.getString("S465"), m.imageIconForeignTable,
            m.jScrollPaneForeignTableList);
        m.jTabbedPaneTableList.addTab(res.getString("S466"), m.imageIconModel, m.jSplitPaneDatamodel);
        m.jTabbedPaneTableList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTabbedPaneTableList.addChangeListener(new TabbedPaneChangeListener(m));
        m.jViewportNativeTableList.add(m.jTableNativeTableList, null);
        m.jScrollPaneNativeTableList.setViewport(m.jViewportNativeTableList);
        m.jScrollPaneNativeTableList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneNativeTableList_mouseClicked(e); } });
        m.jTableNativeTableList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableNativeTableList_mouseClicked(e); } });
        m.jTableNativeTableList.addFocusListener(new Modeler_FocusListener());
        m.jTableNativeTableList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jViewportForeignTableList.add(m.jTableForeignTableList, null);
        m.jScrollPaneForeignTableList.setViewport(m.jViewportForeignTableList);
        m.jScrollPaneForeignTableList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneForeignTableList_mouseClicked(e); } });
        m.jTableForeignTableList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableForeignTableList_mouseClicked(e); } });
        m.jTableForeignTableList.addFocusListener(new Modeler_FocusListener());
        m.jTableForeignTableList.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) { m.jTableKeyReleased(e); } });
        m.jTableNativeTableList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableNativeTableList.setBackground(SystemColor.control);
        m.jTableNativeTableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableNativeTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableNativeTableList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.tableModelNativeTableList.addColumn("No.");
        m.tableModelNativeTableList.addColumn(res.getString("S201"));
        m.tableModelNativeTableList.addColumn(res.getString("S471"));
        m.tableModelNativeTableList.addColumn(res.getString("S472"));
        m.tableModelNativeTableList.addColumn("C");
        m.tableModelNativeTableList.addColumn("R");
        m.tableModelNativeTableList.addColumn("U");
        m.tableModelNativeTableList.addColumn("D");
        m.tableModelNativeTableList.addColumn(res.getString("S477"));
        m.tableModelNativeTableList.addColumn(res.getString("S478"));
        m.tableModelNativeTableList.addColumn("FLD");
        m.tableModelNativeTableList.addColumn(res.getString("S479"));
        m.tableModelNativeTableList.addColumn(res.getString("S480"));
        m.tableModelNativeTableList.addColumn(res.getString("S481"));
        m.tableModelNativeTableList.addColumn(res.getString("S482"));
        m.column0 = m.jTableNativeTableList.getColumnModel().getColumn(0);
        m.column1 = m.jTableNativeTableList.getColumnModel().getColumn(1);
        m.column2 = m.jTableNativeTableList.getColumnModel().getColumn(2);
        m.column3 = m.jTableNativeTableList.getColumnModel().getColumn(3);
        m.column4 = m.jTableNativeTableList.getColumnModel().getColumn(4);
        m.column5 = m.jTableNativeTableList.getColumnModel().getColumn(5);
        m.column6 = m.jTableNativeTableList.getColumnModel().getColumn(6);
        m.column7 = m.jTableNativeTableList.getColumnModel().getColumn(7);
        m.column8 = m.jTableNativeTableList.getColumnModel().getColumn(8);
        m.column9 = m.jTableNativeTableList.getColumnModel().getColumn(9);
        m.columnA = m.jTableNativeTableList.getColumnModel().getColumn(10);
        m.columnB = m.jTableNativeTableList.getColumnModel().getColumn(11);
        m.columnC = m.jTableNativeTableList.getColumnModel().getColumn(12);
        m.columnD = m.jTableNativeTableList.getColumnModel().getColumn(13);
        m.columnE = m.jTableNativeTableList.getColumnModel().getColumn(14);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(120);
        m.column2.setPreferredWidth(310);
        m.column3.setPreferredWidth(75);
        m.column4.setPreferredWidth(30);
        m.column5.setPreferredWidth(30);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column8.setPreferredWidth(120);
        m.column9.setPreferredWidth(350);
        m.columnA.setPreferredWidth(45);
        m.columnB.setPreferredWidth(42);
        m.columnC.setPreferredWidth(42);
        m.columnD.setPreferredWidth(42);
        m.columnE.setPreferredWidth(42);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer(m);
        m.column3.setCellRenderer(checkBoxRenderer);
        DefaultCellEditor editorWithCheckBox = new DefaultCellEditor(
            new JCheckBox(res.getString("S483")));
        m.column3.setCellEditor(editorWithCheckBox);
        m.column4.setCellRenderer(m.rendererAlignmentCenter);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentLeft);
        m.column9.setCellRenderer(m.rendererAlignmentLeft);
        m.columnA.setCellRenderer(m.rendererAlignmentCenter);
        m.columnB.setCellRenderer(m.rendererAlignmentCenter);
        m.columnC.setCellRenderer(m.rendererAlignmentCenter);
        m.columnD.setCellRenderer(m.rendererAlignmentCenter);
        m.columnE.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableNativeTableList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableNativeTableList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jTableForeignTableList)//
        m.jTableForeignTableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableForeignTableList.setBackground(SystemColor.control);
        m.jTableForeignTableList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableForeignTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableForeignTableList.setRowHeight(m.TABLE_ROW_HEIGHT);
        m.tableModelForeignTableList.addColumn("No.");
        m.tableModelForeignTableList.addColumn(res.getString("S436"));
        m.tableModelForeignTableList.addColumn(res.getString("S448"));
        m.tableModelForeignTableList.addColumn(res.getString("S471"));
        m.tableModelForeignTableList.addColumn(res.getString("S490"));
        m.tableModelForeignTableList.addColumn("C");
        m.tableModelForeignTableList.addColumn("R");
        m.tableModelForeignTableList.addColumn("U");
        m.tableModelForeignTableList.addColumn("D");
        m.tableModelForeignTableList.addColumn(res.getString("S495"));
        m.tableModelForeignTableList.addColumn(res.getString("S496"));
        m.tableModelForeignTableList.addColumn("FLD");
        m.column0 = m.jTableForeignTableList.getColumnModel().getColumn(0);
        m.column1 = m.jTableForeignTableList.getColumnModel().getColumn(1);
        m.column2 = m.jTableForeignTableList.getColumnModel().getColumn(2);
        m.column3 = m.jTableForeignTableList.getColumnModel().getColumn(3);
        m.column4 = m.jTableForeignTableList.getColumnModel().getColumn(4);
        m.column5 = m.jTableForeignTableList.getColumnModel().getColumn(5);
        m.column6 = m.jTableForeignTableList.getColumnModel().getColumn(6);
        m.column7 = m.jTableForeignTableList.getColumnModel().getColumn(7);
        m.column8 = m.jTableForeignTableList.getColumnModel().getColumn(8);
        m.column9 = m.jTableForeignTableList.getColumnModel().getColumn(9);
        m.columnA = m.jTableForeignTableList.getColumnModel().getColumn(10);
        m.columnB = m.jTableForeignTableList.getColumnModel().getColumn(11);
        m.column0.setPreferredWidth(40);
        m.column1.setPreferredWidth(220);
        m.column2.setPreferredWidth(120);
        m.column3.setPreferredWidth(310);
        m.column4.setPreferredWidth(75);
        m.column5.setPreferredWidth(30);
        m.column6.setPreferredWidth(30);
        m.column7.setPreferredWidth(30);
        m.column8.setPreferredWidth(30);
        m.column9.setPreferredWidth(150);
        m.columnA.setPreferredWidth(350);
        m.columnB.setPreferredWidth(45);
        m.column0.setCellRenderer(m.rendererAlignmentCenter);
        m.column1.setCellRenderer(m.rendererAlignmentLeft);
        m.column2.setCellRenderer(m.rendererAlignmentLeft);
        m.column3.setCellRenderer(m.rendererAlignmentLeft);
        m.column4.setCellRenderer(checkBoxRenderer);
        m.column4.setCellEditor(editorWithCheckBox);
        m.column5.setCellRenderer(m.rendererAlignmentCenter);
        m.column6.setCellRenderer(m.rendererAlignmentCenter);
        m.column7.setCellRenderer(m.rendererAlignmentCenter);
        m.column8.setCellRenderer(m.rendererAlignmentCenter);
        m.column9.setCellRenderer(m.rendererAlignmentLeft);
        m.columnA.setCellRenderer(m.rendererAlignmentLeft);
        m.columnB.setCellRenderer(m.rendererAlignmentCenter);
        m.jTableForeignTableList.getTableHeader()
            .setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.rendererTableHeader = (DefaultTableCellRenderer) m.jTableForeignTableList.getTableHeader()
            .getDefaultRenderer();
        m.rendererTableHeader.setHorizontalAlignment(2); //LEFT//
        //(jSplitPaneDatamodel)//
        m.jSplitPaneDatamodel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneDatamodel.setBorder(null);
        m.jSplitPaneDatamodel.setDividerLocation(80);
        m.jSplitPaneDatamodel.add(m.jPanelDatamodelTop, JSplitPane.TOP);
        m.jSplitPaneDatamodel.add(m.jScrollPaneDatamodel, JSplitPane.BOTTOM);
        m.jPanelDatamodelTop.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelDatamodelTop.setMinimumSize(new Dimension(500, 0));
        m.jPanelDatamodelTop.setLayout(new BorderLayout());
        m.jPanelDatamodelTop.add(m.jPanelDatamodelDescriptions, BorderLayout.NORTH);
        m.jPanelDatamodelTop.add(m.jScrollPaneDatamodelDescriptions, BorderLayout.CENTER);
        m.jPanelDatamodelDescriptions.setLayout(null);
        m.jPanelDatamodelDescriptions.setPreferredSize(new Dimension(500, 20));
        m.jPanelDatamodelDescriptions.add(m.jLabelDatamodelDescriptions, null);
        m.jLabelDatamodelDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelDatamodelDescriptions.setText(res.getString("S467"));
        m.jLabelDatamodelDescriptions.setBounds(new Rectangle(5, 0, 300, 20));
        m.jTextAreaDatamodelDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaDatamodelDescriptions.setLineWrap(true);
        m.jScrollPaneDatamodelDescriptions.getViewport().add(m.jTextAreaDatamodelDescriptions, null);
        m.jScrollPaneDatamodel.getViewport().add(m.jPanelDatamodel, null);
        m.jScrollPaneDatamodel.getVerticalScrollBar().setUnitIncrement(25);
        //(Slide Show Screen Dialog)//
        m.jDialogDatamodelSlideShow.setResizable(false);
        m.jDialogDatamodelSlideShow.setFocusable(true);
        m.jDialogDatamodelSlideShow.setUndecorated(true);
        m.jDialogDatamodelSlideShow.addKeyListener(
            new KeyAdapter() { public void keyPressed(KeyEvent e) { m.jDialogDatamodelSlideShow_keyPressed(e); } });
        m.jPanelDatamodelSlideShow1.setLayout(null);
        m.jPanelDatamodelSlideShow1.setBounds(new Rectangle(0, 0, 2000, 1500));
        m.jPanelDatamodelSlideShow1.setBackground(m.DESKTOP_COLOR);
        m.jPanelDatamodelSlideShow1.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelDatamodelSlideShow2_mouseClicked(e); } });
        m.jPanelDatamodelSlideShow2.setBackground(m.DESKTOP_COLOR);
        m.jPanelDatamodelSlideShow2.setBounds(new Rectangle(0, 20, 2000, 1500));
        m.jPanelDatamodelSlideShow2.setLayout(null);
        m.jPanelDatamodelSlideShow2.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelDatamodelSlideShow2_mouseClicked(e); } });
        m.jLabelDatamodelSlideShowPageGuide.setFont(new java.awt.Font(m.mainFontName, 0, 17));
        m.jLabelDatamodelSlideShowPageGuide.setForeground(Color.lightGray);
        m.jLabelDatamodelSlideShowPageGuide.setBounds(new Rectangle(15, 15, 600, 20));
        m.jDialogDatamodelSlideShow.getContentPane().add(m.jPanelDatamodelSlideShow1, null);
        m.jPanelDatamodelSlideShow1.add(m.jLabelDatamodelSlideShowPageGuide, null);
        m.jPanelDatamodelSlideShow1.add(m.jPanelDatamodelSlideShow2, null);
    }
}
